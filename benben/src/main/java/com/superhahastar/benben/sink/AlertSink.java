package com.superhahastar.benben.sink;

import com.superhahastar.benben.pojo.Alert;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

/**
 * @author lijie.zhang
 * @date 2021/10/25 15:18
 */
public class AlertSink extends RichSinkFunction<Alert> {

    /**
     * open方法在sink第一次启动时调用，一般用于sink的初始化操作
     */
    @Override
    public void open(Configuration parameters) throws Exception {

        super.open(parameters);
    }

    /**
     * invoke方法是sink数据处理逻辑的方法，source端传来的数据都在invoke方法中进行处理
     * 其中invoke方法中第一个参数类型与RichSinkFunction<String>中的泛型对应。第二个参数
     * 为一些上下文信息
     */
    @Override
    public void invoke(Alert value, Context context) throws Exception {
        System.out.println(value.toString());
    }

    /**
     * close方法在sink结束时调用，一般用于资源的回收操作
     */
    @Override
    public void close() throws Exception {

        super.close();
    }

}
