package com.example.awaysuse.util;

import com.example.awaysuse.model.Person;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CompareTime {
    private static SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

    /**
     * 判断当前时间是否在某个时间段内
     * begin 开始时间字符串  String begin="09:00:00";
     * end  结束时间字符串    String end="12:00:00";
     */
    public static boolean compareTime(String begin, String end) {
        boolean result = false;
        //将时间字符串转化成时间

        try {
            //转换成时间格式
            Date beginTime = df.parse(begin);
            Date endTime = df.parse(end);
            //取出当前时间的时分秒编码再解码
            Date date = df.parse(df.format(new Date()));
            //通过日历形式开始比较
            Calendar b = Calendar.getInstance();
            b.setTime(beginTime);
            Calendar e = Calendar.getInstance();
            e.setTime(endTime);
            Calendar d = Calendar.getInstance();
            d.setTime(date);
            //当前时间晚于开始时间，早于结束时间则表明在指定的时间段内
            if (d.after(b) && d.before(e)) {
                result = true;
            }
        } catch (ParseException e1) {
            e1.printStackTrace();

        }
        return result;
    }

    public static void main(String[] args) {
       /* boolean b = compareTime("09:00:00", "18:00:00");
        System.out.println(b);*/


    }



}
