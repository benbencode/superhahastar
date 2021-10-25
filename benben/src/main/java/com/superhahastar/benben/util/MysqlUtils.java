package com.superhahastar.benben.util;

import com.superhahastar.benben.constants.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import static com.superhahastar.benben.util.StringUtils.addColumns_SQL;

public class MysqlUtils {
    static  final Logger logger = LoggerFactory.getLogger(MysqlUtils.class);
    public final static String driver="com.mysql.jdbc.Driver";
    public final static String url="jdbc:mysql://localhost:9030/test_db";
    public final static String username="root";
    public final static String password="";


    /**
     * 获取mysql链接
     * @param driver
     * @param url
     * @param username
     * @param password
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection(String driver,String url,String username,String password) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);
        return  connection;
    };

    /**
     * 向doris表中添加字段
     * @param connection
     * @throws SQLException
     */
    public static boolean addColumns(Connection connection,String table, Map<String, String> columns, String path) throws SQLException {
        try {
            String alert_sql = addColumns_SQL(table, columns);
            logger.info("alert table sql {} "+alert_sql );
            if (!columns.isEmpty()){
                Statement statement = connection.createStatement();
                boolean b = statement.execute(alert_sql);
                return b;
            }else {
                return true;
            }
        }catch (Exception e){
//            FileUtils.deleteFile(path);
            e.printStackTrace();
            return true;  // 上次任务没结束，给定返回值
        }
    }

    /**
     * 查询ALTER TABLE更新状态  PENDING->WAITING_TXN->RUNNING->终态(FINISHED/CANCELLED)
     * @param connection
     * @return
     * @throws Exception
     */
    public static String findUpdateColumnState(Connection connection ,String table) throws Exception {
        String flag="";
        int jobId=0;
        String finishTime =null;
        String state ="";
        String running = "SHOW ALTER TABLE COLUMN";   //获取更改状态
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(running);
        while (resultSet.next()){
            String tablename = resultSet.getString("TableName");  //表名
            if (table.equals(tablename)){

                Integer id = Integer.parseInt(resultSet.getString("JobId"));
                if (id>jobId) {
                    jobId=id;
                    state = resultSet.getString("State"); // 状态
                    finishTime = resultSet.getString("FinishTime");  //完成时间
                }

            }
        }
        if ("FINISHED".equals(state)){
            flag=state;
        }
        if ("CANCELLED".equals(state)&& org.apache.commons.lang3.StringUtils.isNotBlank(finishTime)){
            flag=state;
        }
        return flag;
    }

    /**
     * 获取doris表中的所有字段
     * @param connection
     * @param database
     * @param table
     * @return
     * @throws SQLException
     */
    public static Map<String, String> findTableAllColumns(Connection connection,String database, String table) throws SQLException {
        assert(database!=null&&table!=null);
        Map<String, String> map = new HashMap<>();
        String sql="select * from information_schema.columns where table_schema='"+database+"' and table_name='"+table+"'";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            String column_name = resultSet.getString("column_name");
            String data_type = resultSet.getString("column_type");
            map.put(column_name,data_type);
        }

        return map;
    }

    /**
     * 关闭链接
     * @param stmt
     * @param conn
     */
    public static void close(Statement stmt,Connection conn){
        if(stmt!=null){
            try{
                stmt.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try{
                conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws Exception {
//        Class.forName(driver);
//
//        Connection connection = DriverManager.getConnection(url, username, password);
//        System.out.println(connection);
//        String sql = "select * from flink_2_doris";
//        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        while (resultSet.next()){
//            String role_id = resultSet.getString("role_id");
//            String event_time2 = resultSet.getString("event_time2");
//            System.out.println(role_id);
//            System.out.println(event_time2);
//        }


//        String alert_sql = "alter table flink_2_doris_test add COLUMN (amount double)";
//        Statement statement = connection.createStatement();
//
//        System.out.println(statement.execute(alert_sql));


//        String running = "SHOW ALTER TABLE COLUMN";   //获取更改状态
//        Statement statement1 = connection.createStatement();
//        ResultSet resultSet1 = statement1.executeQuery(running);
//        while (resultSet1.next()){
//            String state = resultSet1.getString("State"); // 状态
//            String finishTime = resultSet1.getString("FinishTime");  //完成时间
//            System.out.println(state);
//            System.out.println(finishTime);
//        }


        //
        Connection connection = getConnection(Constant.DRIVERNAME, Constant.DB_URL, Constant.username, Constant.password);
        Map<String, String> tableAllColumns = findTableAllColumns(connection, "test_db", "flink_2_doris_test");
        System.out.println(tableAllColumns.toString());

        TreeSet<Integer> integers = new TreeSet<>();
        integers.add(56);
        integers.add(9);

        System.out.println(integers.toString());


    }



}
