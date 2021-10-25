package com.superhahastar.benben.util;

import java.util.*;

public class StringUtils {
    public static List municipality=new ArrayList(Arrays.asList("北京市","天津市","上海市","重庆市"));//直辖市

    public static void main(String[] args) {
        Map<String, String> map = new HashMap(){
            {
                put("amount","double");
                put("event_id","string");
                put("event_time2","date");
            }
        };
        String flink_2_doris_test = addColumns_SQL("flink_2_doris_test", map);
        System.out.println(flink_2_doris_test);
    }

    /**
     * 拼接添加字段sql
     * @param table
     * @param columns
     * @return
     */
    public static String addColumns_SQL(String table,Map<String,String> columns){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("alter table "+ table+" ");
        stringBuffer.append("add COLUMN (");
        for (String key:columns.keySet()) {
            String s = key+" "+columns.get(key);
            stringBuffer.append(s);
            stringBuffer.append(",");

        }
        String substring ="";
        if (stringBuffer.length()>0){
            substring=stringBuffer.substring(0, stringBuffer.length() - 1);
        }

        if (org.apache.commons.lang3.StringUtils.isNotBlank(substring)){
            return substring+")";
        }else {
            return substring;
        }
    }


    // 判断是否为json字符串
    public static boolean getJSONType(String str) {
        boolean result = false;
        if (org.apache.commons.lang3.StringUtils.isNotBlank(str)) {
            str = str.trim();
            if (str.startsWith("{") && str.endsWith("}")) {
                result = true;
            } else if (str.startsWith("[") && str.endsWith("]")) {
                result = true;
            }
        }
        return result;
    }


//    省份：黑龙江省、辽宁省、吉林省、河北省、河南省、湖北省、湖南省、山东省、山西省、陕西省、
//    安徽省、浙江省、江苏省、福建省、广东省、海南省、四川省、云南省、贵州省、青海省、甘肃省、江西省、台湾省。
//
//    自治区：内蒙古自治区、宁夏回族自治区、新疆维吾尔自治区、西藏自治区、广西壮族自治区。
//
//    直辖市：北京市（北京为中国首都）、上海市、天津市、重庆市。
//
//    特别行政区：香港特别行政区、澳门特别行政区。
    public static String containProvince(String s){
        String province=null;

        if (s.contains("北京")){
            province="北京市";
        }
        else if (s.contains("上海")){
            province="上海市";
        }
        else if (s.contains("天津")){
            province="天津市";
        }
        else if (s.contains("重庆")){
            province="重庆市";
        }
        else if (s.contains("内蒙古")){
            province="内蒙古省";
        }
        else if (s.contains("宁夏")){
            province="宁夏省";
        }
        else if (s.contains("新疆")){
            province="新疆省";
        }
        else if (s.contains("西藏")){
            province="西藏省";
        }
        else if (s.contains("广西")){
            province="广西省";
        }
        else if (s.contains("黑龙江")){
            province="黑龙江省";
        }
        else if (s.contains("辽宁")){
            province="辽宁省";
        }
        else if (s.contains("吉林")){
            province="吉林省";
        }
        else if (s.contains("河北")){
            province="河北省";
        }
        else if (s.contains("河南")){
            province="河南省";
        }
        else if (s.contains("湖北")){
            province="湖北省";
        }
        else if (s.contains("湖南")){
            province="湖南省";
        }
        else if (s.contains("山东")){
            province="山东省";
        }
        else if (s.contains("山西")){
            province="山西省";
        }
        else if (s.contains("陕西")){
            province="陕西省";
        }
        else if (s.contains("安徽")){
            province="安徽省";
        }
        else if (s.contains("浙江")){
            province="安徽省";
        }
        else if (s.contains("江苏")){
            province="江苏省";
        }
        else if (s.contains("福建")){
            province="福建省";
        }
        else if (s.contains("广东")){
            province="广东省";
        }
        else if (s.contains("海南")){
            province="海南省";
        }
        else if (s.contains("四川")){
            province="四川省";
        }
        else if (s.contains("云南")){
            province="云南省";
        }
        else if (s.contains("贵州")){
            province="贵州省";
        }
        else if (s.contains("青海")){
            province="青海省";
        }
        else if (s.contains("甘肃")){
            province="甘肃省";
        }
        else if (s.contains("江西")){
            province="江西省";
        }
        return province;
    }


    /**
     * 纯真ip库解析ip信息  （包含国内外 、港澳台）
     * ip=>(国家、省份、城市、运营商)
     * @param info
     * @return
     */
    public static Map<String, String> parseIPInfo(String info) {

        Map<String, String> map = new HashMap<>();
        String country=""; // 国家
        String province =""; // 省份
        String city=""; // 城市
        String operator=""; // 运行商
        if (info.split(",")[0].contains("省")&&!info.split(",")[0].contains("市")){
            province=info.substring(0,info.indexOf("省")+1).trim();
            city=info.substring(info.indexOf("省")+1,info.indexOf(","));
            operator = info.substring(info.indexOf(",")+1,info.length()).trim();
            map.put("country",country);
            map.put("province",province);
            map.put("city",city);
            map.put("isp",operator);
        }else if (info.split(",")[0].contains("省")&&info.split(",")[0].contains("市")){
            province = info.substring(0,info.indexOf("省")+1);
            city = info.substring(info.indexOf("省")+1,info.indexOf("市")+1);
            operator = info.substring(info.indexOf(",")+1,info.length()).trim();
            map.put("country",country);
            map.put("province",province);
            map.put("city",city);
            map.put("isp",operator);
        }else if (!info.split(",")[0].contains("省")&&info.split(",")[0].contains("市")){

            province = containProvince(info);
            if (municipality.contains(province)){
                city = info.substring(info.indexOf(province),info.indexOf("市")+1);
            }else {
                city = info.substring(info.indexOf(province.charAt(province.length()-2))+1,info.indexOf("市")+1);
            }
            operator = info.substring(info.indexOf(",")+1,info.length()).trim();
            map.put("country",country);
            map.put("province",province);
            map.put("city",city);
            map.put("isp",operator);
        }else {
            country=info.split(",")[0];
            operator = info.substring(info.indexOf(",")+1,info.length()).trim();
            if (!country.contains("香港")&&!country.contains("澳门")&&!country.contains("台湾")){
                map.put("country",country);
                map.put("province",province);
            }else {
                map.put("country","");
                map.put("province",country);
            }
            map.put("city",city);
            map.put("isp",operator);
        }
        return map;
    }
}
