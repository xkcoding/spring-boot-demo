package com.example.awaysuse.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ZonedDateTimeUtil {

    SimpleDateFormat FORMAT_YMD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public Date zonedDateTimeToDate(ZonedDateTime zonedDateTime){
        //格式转换
        String computeTime = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        //将ZonedDateTime类型转成Date
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("GMT-0"));
        Date nowDt = Date.from(now.toInstant());
        return nowDt;
    }

   //  当前时间 ZoneDateTime格式
  public ZonedDateTime  stringToZone(){

      ZonedDateTime zdt = null;
      try {
          String Str = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("GMT+8"))
          .format(Instant.now().minus(1, ChronoUnit.DAYS));
          //将String类型的时间转成Date类型
          Date yestoday = FORMAT_YMD.parse(Str);
          //将date类型转成 ZonedDateTime类型 使用了一个工具类
          zdt = toZonedDateTime(yestoday.getTime());
          System.out.println("现在时间" + zdt);

          //使用ZoneDateTime获取一天的开始时间

          ZonedDateTime zdt2 = zdt.toLocalDate().atStartOfDay(zdt.getZone());

          System.out.println("开始时间" + zdt2);

          //使用ZoneDateTime获取一天中的结束时间

          ZonedDateTime zonedDateTime1 = endOfDay(zdt2);

          System.out.println("结束时间" + zonedDateTime1);
          return zdt;
      } catch (ParseException e) {
          e.printStackTrace();
      }
        return  null;
  }

    //获取一天的结束时间的工具类

    public static ZonedDateTime endOfDay(ZonedDateTime curDate) {

        ZonedDateTime startOfTomorrow = curDate
            .toLocalDate()
            .plusDays(1)
            .atStartOfDay()
            .atZone(curDate.getZone())
            .withEarlierOffsetAtOverlap();
        return startOfTomorrow.minusSeconds(1);

    }



//将Date转成ZoneDateTime类型

    public static ZonedDateTime toZonedDateTime(Long time){
        String LONG_DATE = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(LONG_DATE);
        Date createDate = new Date(time);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String format = sdf.format(createDate);
        DateTimeFormatter beijingFormatter = DateTimeFormatter.ofPattern(LONG_DATE).withZone(ZoneId.of("GMT+8"));
        if(StringUtils.isBlank(format)){
            return null;
        }
        ZonedDateTime beijingDateTime = ZonedDateTime.parse(format, beijingFormatter);
        ZonedDateTime utc = beijingDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        return beijingDateTime;
    }


    public  ZonedDateTime  longToZone(){
        //Long类型转成指定时区的ZoneDateTime类型
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("GMT+8"));
        Date date = Date.from(now.toInstant());
        long diff = date.getTime() - Long.parseLong(String.valueOf(10*1000*60));
        String LONG_DATE = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(LONG_DATE);
        Date createDate = new Date(diff);
        //将时区转换到指定时区
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String format1 = sdf.format(createDate);
        DateTimeFormatter beijingFormatter=DateTimeFormatter.ofPattern(LONG_DATE).withZone(ZoneId.of("GMT+8"));
        ZonedDateTime beijingDateTime = ZonedDateTime.parse(format1, beijingFormatter);
        return beijingDateTime;

    }


}
