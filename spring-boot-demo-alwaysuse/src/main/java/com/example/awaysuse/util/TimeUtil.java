/**
 * Copyright (C), 2018-2018, jk有限公司
 * FileName: TimeUtil
 * Author:  常路通
 * Date:     2018/11/16 18:38
 * Description:
 * /**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TimeUtil
 * Author:   chang
 * Date:     2018/11/16 18:38
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.awaysuse.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * 时间获取和处理
 * 〈〉
 *
 * @author chang
 * @create 2018/11/16
 * @since 1.0.0
 */
public class TimeUtil {
    public static Long getFormatedDateString(float timeZoneOffset) throws ParseException {
        if (timeZoneOffset > 13 || timeZoneOffset < -12) {
            timeZoneOffset = 0;
        }
        int newTime = (int) (timeZoneOffset * 60 * 60 * 1000);
        TimeZone timeZone;
        String[] ids = TimeZone.getAvailableIDs(newTime);
        if (ids.length == 0) {
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = new SimpleTimeZone(newTime, ids[0]);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(timeZone);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.parse(sdf.format(new Date())).getTime();
    }

    // 获取上个月
    public static String getLastMonth() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = sdf.format(m);
       // System.out.println("过去一个月：" + mon);
        return mon.replace("-", "");
    }

    // 获取当前月份信息
    public static String getNowMonth() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        c.setTime(new Date());
        // c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = sdf.format(m);
      //  System.out.println("现在月：" + mon);
        return mon.replace("-", "");
    }

    //获取前一天的时间
    public static String getLastDay(Integer i) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        c.setTime(new Date());
        c.add(Calendar.DATE, i);
        Date m = c.getTime();
        String mon = sdf.format(m);
       // System.out.println("现在月：" + mon);
        return mon.replace("-", "");
        //return "20190423";
    }


    public static String getDay(Date parse, int i,String format) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        c.setTime(parse);
        c.add(Calendar.DATE, i);
        Date m = c.getTime();
        String mon = sdf.format(m);
       // System.out.println("过去一个月：" + mon);
        return mon;
    }
    public static Long getDayTime(Date parse, int i,String format) {
        Calendar c = Calendar.getInstance();
        c.setTime(parse);
        c.add(Calendar.DATE, i);
        Date m = c.getTime();
        return m.getTime();
    }

    public static long getHourTime(Date parse, int i, String format) {
        Calendar c = Calendar.getInstance();
        c.setTime(parse);
        c.add(Calendar.HOUR, i);
        Date m = c.getTime();
        return m.getTime();
    }
    // Date转String
    public static String DateToStr(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    // 获取几天之前的数据
    public static String getLastDay(String time, int i) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = null;
        try {
            parse = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(parse);
        c.add(Calendar.DATE, i);
        Date time1 = c.getTime();
        return sdf.format(time1).replace("-","");
    }

    public static void main(String[] args) {
        System.out.println(getFormatedDate(8));
    }

    //获得哪个时区的时间
    public static String getFormatedDate(float timeZoneOffset){
        if (timeZoneOffset > 13 || timeZoneOffset < -12) {
            timeZoneOffset = 0;
        }

        int newTime=(int)(timeZoneOffset * 60 * 60 * 1000);
        TimeZone timeZone;
        String[] ids = TimeZone.getAvailableIDs(newTime);
        if (ids.length == 0) {
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = new SimpleTimeZone(newTime, ids[0]);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(timeZone);
        return sdf.format(new Date());
    }

    // 获取几小时之前的数据
    public static String getLastHourTime(int date,int n){
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        ca.set(Calendar.HOUR_OF_DAY, ca.get(Calendar.HOUR_OF_DAY)-n);
        ca.set(Calendar.DATE, ca.get(Calendar.DATE)-date);
        return sdf.format(ca.getTime());
    }

    // 获取几小时之前的数据 字符串类型
    public static String getLastHourStr(Date date,int n,String format){
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        ca.set(Calendar.HOUR_OF_DAY, ca.get(Calendar.HOUR_OF_DAY)-n);
        return sdf.format(ca.getTime());
    }

    public static String getNowDay(int i) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("GMT+8"));
        ZonedDateTime yesterday = now.minusDays(-i);
        String nowStr = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return nowStr;
    }
    public static String[] getPerfectDays(String time) {
        ZonedDateTime now = ZonedDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.of("GMT-0")));
        ZonedDateTime yesterday = now.minusDays(1);
        ZonedDateTime tomorrow = now.plusDays(1);
        String nowStr = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String yesterdayStr = yesterday.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String tomorrowStr = tomorrow.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return new String[]{nowStr,yesterdayStr,tomorrowStr};
    }
    public static String getPerfectDay(String time,int i) {
        ZonedDateTime now = ZonedDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.of("GMT-0")));
        ZonedDateTime yesterday = now.minusDays(-i);
        String yesterdayStr = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return yesterdayStr;
    }

    public static  String getTimeStr(String time){
        String[] times = time.split(" ");
        return times[1] + "时";
    }

    // Java8 获取最近几小时的时间
    public static   String  getNearTimes(int time){
        LocalDateTime dateTime=LocalDateTime.now();
        DateTimeFormatter  dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateBefore = dateTime.minusHours(time);
        String format = dateBefore.format(dateTimeFormatter);
        return  format;
    }

    // 获取当前时间
    public static   String  getNowTime(){
        LocalDateTime dateTime=LocalDateTime.now();
        DateTimeFormatter  dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = dateTime.format(dateTimeFormatter);
        return  format;
    }

    public static String computeDateTime(String dateTime, Integer hours) {
        String forecastTime = new String();
        if (StringUtils.isNotEmpty(dateTime)) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = simpleDateFormat.parse(dateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            calendar.setTime(date);
            calendar.add(Calendar.HOUR_OF_DAY,hours);
            forecastTime = simpleDateFormat.format(calendar.getTime());
        }
        return forecastTime;
    }


}
