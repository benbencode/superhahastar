package com.superhahastar.benben.source;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * @author lijie.zhang
 * @date 2021/10/25 10:31
 */
public class SocketSource {
    static String HOST = "172.17.148.40";    // host主机
    static int PORT = 7777;               // 端口号

    public static void main(String[] args) throws Exception {
        // 设置流执行环境
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 读取socket数据源，并执行数据转换
        DataStream<String> dataStream = env
                .socketTextStream(HOST, PORT);

        System.out.println("begin------");
        dataStream.print();
        System.out.println("end------");

        env.execute("Window WordCount");
    }

    // 自定义flatMap函数
    public static class Splitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String sentence, Collector<Tuple2<String, Integer>> out) throws Exception {
            for (String word : sentence.split(" ")) {
                out.collect(new Tuple2<>(word, 1));
            }
        }
    }


}
