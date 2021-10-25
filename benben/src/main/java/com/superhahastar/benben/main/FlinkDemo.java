package com.superhahastar.benben.main;

import com.alibaba.fastjson.JSONObject;
import com.superhahastar.benben.constants.Constant;
import com.superhahastar.benben.util.EnvUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.regex.Pattern;

/**
 * @author lijie.zhang
 * @date 2021/10/21 19:17
 */
public class FlinkDemo {

    private static String BROKERS = "172.17.187.148:9092,172.17.187.149:9092,172.17.187.150:9092,172.17.187.151:9092,172.17.187.152:9092";
    private static String TOPIC_REGEX = "ods_gamelog_1065_stream";
    private static String CONSUMER_GROUP_ID = "test_zlj";

    public static void main(String[] args) {

        // 环境初始化
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(1000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.setParallelism(1);

        // kafka-source
        KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
                .setBootstrapServers(BROKERS)
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setTopicPattern(Pattern.compile(TOPIC_REGEX))
                .setGroupId(CONSUMER_GROUP_ID)
                .setValueOnlyDeserializer(new SimpleStringSchema())
//                .setProperties(properties)
                .build();
        DataStream<String> inputStream = env.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "Kafka Source");

        SingleOutputStreamOperator<String> filter = inputStream.filter(new FilterFunction<String>() {
            @Override
            public boolean filter(String value) throws Exception {
                JSONObject data = JSONObject.parseObject(value);
                String event_time2 = data.getString("event_time2");
                if (StringUtils.isNotBlank(event_time2)) {
                    return true;
                } else {
                    return false;
                }
            }
        });


    }
}
