package com.superhahastar.benben.constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Constant {
    // 公共
    public static Set set = new HashSet<String>(Arrays.asList("account_total_charge", "event_id", "event_uuid", "event_time", "event_time2", "appkey", "platform", "client_os", "server_id", "channel_id", "app_channel_id", "device_id", "user_id", "open_id", "role_id", "role_key", "transaction_id", "buess_time", "user_ip", "phone_mcc", "phone_mnc", "phone_cid", "phone_sid", "phone_lac", "session_id", "event_name", "error"));
    public static String ENV_FLAG="dev";



    // 生产环境


    // doris JDBC链接方式
    public final static String DRIVERNAME = "com.mysql.jdbc.Driver";
    public final static String DB_URL = "jdbc:mysql://localhost:9030/test_db";//doris链接地址
    public final static String username = "root";
    public final static String password = "";
    public final static String database = "";
    public final static String table = "";


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