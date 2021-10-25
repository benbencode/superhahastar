package com.superhahastar.benben.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {
    public static final String  YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String  YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String  YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    public static final String  YYYY_MM_DD = "yyyy-MM-dd";


    /**
     * 时间错转化为日期
     * @param time
     * @param format
     * @return
     */
    public static String longTime2Date(long time,String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String dateString = simpleDateFormat.format(new Date(time));
        return dateString;
    }

    public static Date stringDate2Date(String dateString,String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }



    public static String dateToStamp(String s) {
        String res = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_SSS);
            Date date = simpleDateFormat.parse(s);
            long ts = date.getTime() / 1000;
            res = String.valueOf(ts);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }


    /**
     * 19位时间戳转换为日期
     * @param s
     * @return
     */
    public static String bit19StringToDate(String s) {
        long l = Long.parseLong(s);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            Date date1 = new Date(l/1000000);
            String date = simpleDateFormat.format(date1);
        System.out.println(date);
        return date;
    }

    public static void main(String[] args) throws ParseException {
        String s = longTime2Date(Long.parseLong("1625206611")*1000, YYYY_MM_DD_HH_MM_SS_SSS);
        System.out.println(s);

        Date date1 = stringDate2Date("2021-06-08", YYYY_MM_DD);
        Date date2 = stringDate2Date("2021-06-10", YYYY_MM_DD);
        System.out.println(date2.getTime()-date1.getTime());

        System.out.println(longTime2Date(new Date().getTime(),YYYY_MM_DD));

        //4_1612729069793766076_0_276
        //  155843950471100000
            bit19StringToDate("1612729069793766076");



        String timeStamp = "1612729069793766076";
        long unixNanoSeconds = Long.parseLong(timeStamp);
        Date date = new Date(unixNanoSeconds/1000000L);
// My preferred date format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSSZ");
        String formattedDate = sdf.format(date);
        System.out.println("The timestamp in your preferred format is: " +  formattedDate);



        String valueIn = "2019-02-19T23:28:04.434410+0800";
        String formatIn = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ";
        String formatOut = "yyyy-MM-dd HH:mm:ss";
        String formatOut2 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ";

        LocalDateTime ldt = LocalDateTime.parse(valueIn, DateTimeFormatter.ofPattern(formatIn));

        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        String out2 = DateTimeFormatter.ofPattern(formatOut).format(zdt);

        System.out.println("LocalDateTime out:" + out2);



        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
        Date parse = sf.parse("2021-02");
        System.out.println(parse.getTime());




    }
}
