package com.superhahastar.benben.source;

import com.superhahastar.benben.operation.FraudDetector;
import com.superhahastar.benben.pojo.Alert;
import com.superhahastar.benben.pojo.PayLog;
import com.superhahastar.benben.sink.AlertSink;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lijie.zhang
 * @date 2021/10/25 14:08
 */
public class FraudDetectionJob {

    private static Logger logger = LoggerFactory.getLogger(FraudDetectionJob.class);

    public static void main(String[] args) throws Exception {

        // env初始化
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 读取本地文件
        String filePath = "F:\\work\\project\\zlj2\\superhahastar\\benben\\src\\main\\resources\\paylog.txt";
        SingleOutputStreamOperator<String> fileSource = env.readTextFile(filePath).name("localfile-source");

        // map 原始数据切分转换为实体类
        SingleOutputStreamOperator<PayLog> payLogDS = fileSource.map(new MapFunction<String, PayLog>() {

            @Override
            public PayLog map(String s) throws Exception {
                String[] splits = s.split(",");
                PayLog payLog = new PayLog();
                payLog.setAccountId(Long.parseLong(splits[0]));
                payLog.setAmount(Double.parseDouble(splits[1]));
                payLog.setBuessTime(Long.parseLong(splits[2]));
                return payLog;
            }
        });

        // keyBy 保证同一个 task 处理同一个 key 的所有数据,process封装了对每一条key处理逻辑,获取到需要报警的账号id
        // TODO: 问：task如果增加，怎么保证呢？？？
        SingleOutputStreamOperator<Alert> alerts = payLogDS.keyBy(PayLog::getAccountId)
                .process(new FraudDetector())
                .name("fraud-detector");

        alerts.addSink(new AlertSink());


//        alerts.print("打印");
        env.execute("FraudDetectionJob");

    }


}

