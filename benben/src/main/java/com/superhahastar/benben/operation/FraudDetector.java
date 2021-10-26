package com.superhahastar.benben.operation;

import com.superhahastar.benben.pojo.Alert;
import com.superhahastar.benben.pojo.PayLog;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

/**
 * 欺诈监测
 * 步骤 1：
 * 仅当一个大额的交易紧随一个小额交易的情况发生时，这个大额交易才被认为是欺诈交易。
 * KeyedProcessFunction 的原因。 它能够同时提供对状态和时间的细粒度操作,
 * Flink 提供了一套支持容错状态的原语，这些原语几乎与常规成员变量一样易于使用:
 * ValueState 是一种 keyed state,ValueState 需要使用 ValueStateDescriptor 来创建.状态需要使用 open() 函数来注册状态。
 * <p>
 * 步骤 2：
 * 添加计时器，计时器可以在将来某个时间点执行回调函数
 *
 * @author lijie.zhang
 * @date 2021/10/25 15:05
 */
public class FraudDetector extends KeyedProcessFunction<Long, PayLog, Alert> {

    private static final long serialVersionUID = 1L;

    private static final double SMALL_AMOUNT = 1.00;
    private static final double LARGE_AMOUNT = 500.00;
    private static final long ONE_MINUTE = 60 * 1000;

    // 声明：存储key目前是否是小额交易,需要在open方法中通过RunTimeContext创建ValueStateDescriptor
    private transient ValueState<Boolean> flagState;

    // 设置定时器状态存储定时器时间 ( 定时器服务可以用于查询当前时间、注册定时器和删除定时器。)
    private transient ValueState<Long> timerState;

    // 创建状态：valueState (update 用于更新状态，value 用于获取状态值，还有 clear 用于清空状态。)
    @Override
    public void open(Configuration parameters) throws Exception {
        // 初始化小额标记状态
        ValueStateDescriptor<Boolean> flagDescriptor = new ValueStateDescriptor<>("flag", Types.BOOLEAN);
        flagState = getRuntimeContext().getState(flagDescriptor);
        // 初始化定时器状态
        ValueStateDescriptor<Long> timerStateDescriptor = new ValueStateDescriptor<>("timer-state", Types.LONG);
        timerState = getRuntimeContext().getState(timerStateDescriptor);
    }

    @Override
    public void processElement(PayLog payLog, KeyedProcessFunction<Long, PayLog, Alert>.Context context, Collector<Alert> collector) throws Exception {
// 测试
//        Alert alert = new Alert();
//        alert.setId(payLog.getAccountId());
//        collector.collect(alert);

        System.out.println("进入到processElement方法，数据打印： " + payLog.toString());

        // 获取当前key对应的状态信息,上次是否是小额交易
        Boolean lastTransactionWasSmall = flagState.value();

        // check if the flag is set
        if (lastTransactionWasSmall != null) {
            if (payLog.getAccountId() > LARGE_AMOUNT) {
                Alert alert = new Alert();
                alert.setId(payLog.getAccountId());
                collector.collect(alert);
            }
            // clean up our state 在检查之后，不论是什么状态，都需要被清空。 不管是当前交易触发了欺诈报警而造成模式的结束，还是当前交易没有触发报警而造成模式的中断，都需要重新开始新的模式检测。
            cleanUp(context);
        }

        // 标记小额交易
        if (payLog.getAmount() < SMALL_AMOUNT) {
            flagState.update(true);

            // 注册定时器
            long timer = context.timerService().currentProcessingTime() + ONE_MINUTE;  // 这里选的处理时间
            context.timerService().registerProcessingTimeTimer(timer);
            timerState.update(timer);
        }

    }

    // 回调函数 定时器触发是调用
    @Override
    public void onTimer(long timestamp, KeyedProcessFunction<Long, PayLog, Alert>.OnTimerContext ctx, Collector<Alert> out) throws Exception {
        // 定时器触发时，清除计时器的标记状态，同时清除小额交易状态
        timerState.clear();
        flagState.clear();
    }

    // 助手函数，取消定时器
    private void cleanUp(Context ctx) throws Exception {
        // delete timer
        Long timer = timerState.value();
        ctx.timerService().deleteProcessingTimeTimer(timer);  // TODO

        // clean up all state
        timerState.clear();
        flagState.clear();
    }
}
