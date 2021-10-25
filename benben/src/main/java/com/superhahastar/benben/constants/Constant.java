package com.superhahastar.benben.constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Constant {
    // 公共
    public static Set set = new HashSet<String>(Arrays.asList("account_total_charge", "event_id", "event_uuid", "event_time", "event_time2", "appkey", "platform", "client_os", "server_id", "channel_id", "app_channel_id", "device_id", "user_id", "open_id", "role_id", "role_key", "transaction_id", "buess_time", "user_ip", "phone_mcc", "phone_mnc", "phone_cid", "phone_sid", "phone_lac", "session_id", "event_name", "error"));
    public static String ENV_FLAG="dev";
    // 测试环境
    public static String KAFKA_BOOTSTRAP_SERVERS_DEV = "172.17.187.139:9092,172.17.187.140:9092,172.17.187.141:9092";
    public static final String HDFS_SERVER_DEV = "hdfs://emr-header-1.cluster-213641:9000";


    // 生产环境
    public static String KAFKA_BOOTSTRAP_SERVERS_ONLINE = "172.17.187.148:9092,172.17.187.149:9092,172.17.187.150:9092,172.17.187.151:9092,172.17.187.152:9092";
    public static final String HDFS_SERVER_ONLINE = "hdfs://emr-header-1.cluster-213641:9000";

    // doris JDBC链接方式
    public final static String DRIVERNAME = "com.mysql.jdbc.Driver";
    public final static String DB_URL = "jdbc:mysql://172.17.187.138:9030/test_db";//doris链接地址
    public final static String username = "root";
    public final static String password = "";
    public final static String database = "";
    public final static String table = "";

    // nacos配置中心
    public final static String SERVERADDS="172.17.187.139";
    public final static String DATAID="test.json";
    public final static String GROUP="DEFAULT_GROUP";

    // 数据落盘路径
    public static String ACCEPT_KAFKA_DATA_CACHE_PATH_DEV = "E:\\tmp\\kafka.json"; //数据落盘存储文件
    public static String DATA_STEAM_PATH_DEV = "E:\\tmp\\cache\\";       //数据存储位置

    public static String DATA_STEAM_ERROR_PATH_DEV = "E:\\tmp\\error\\";   //doris出现更新字段异常时，移动的目录
    public static String DATA_STEAM_ERROR_PATH_ONLINE = "E:\\tmp\\error\\";   //doris出现更新字段异常时，移动的目录

    public static String ACCEPT_KAFKA_DATA_CACHE_PATH_ONLINE = "/root/sdb/kafka.json"; //数据落盘存储文件
    public static String DATA_STEAM_PATH_ONLINE = "E:\\tmp\\cache\\";

    public static final String HDFS_FILE_PATH = "/sdb/qqwry.dat";
    public static final String ERROR_DATA_HDFS_PATH = "/sdb/data/flink"; //异常数据存储目录




}