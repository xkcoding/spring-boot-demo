package com.example.awaysuse;

import com.example.awaysuse.model.ReportTimeLimit;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.attribute.AclEntry;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PatternTest {
    public static void main(String[] args) throws Exception {
        // 在本地创建一个.txt文件
      /*  File file = new File("D://rz.txt");
        // 链接到需要提取内容的网页
        URL url = new URL("http://tieba.baidu.com");
        // 打开连接
        URLConnection conn = url.openConnection();
        // 设置连接网络超时时间
        conn.setConnectTimeout(1000 * 10);
        // 读取指定网络地址中的文件 （输入缓冲流）
        BufferedReader bufr = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        // 将内容保存到text.txt文件中（输出缓冲流）
        Writer wt = new FileWriter(file, true);
        // 将字符流包装成缓冲流
        PrintWriter pw = new PrintWriter(wt, true);

        String line = null;
        // 匹配email的正则
        String regex = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
        Pattern p = Pattern.compile(regex);
        while ((line = bufr.readLine()) != null) {
            Matcher m = p.matcher(line);
            while (m.find()) {
                // 写入到文件
                pw.write(m.group());
                // 获得匹配的email
                System.out.println(m.group());
            }
        }
        // 刷新输出流
        pw.flush();
        // 先关闭输出流
        pw.close();
        // 关闭输入流
        bufr.close();

    }*/

        LinkedHashMap map = new LinkedHashMap();
        map.put("1", 12);
        map.put("2", 3);
        map.put(435, 14);
        map.put("4", 12);
        map.entrySet().stream().forEach(m -> {
            System.out.println(m);
        });


        String content = "";
        try {
            URL url = new URL("https://tieba.baidu.com/p/4529628420?red_tag=0588423095");
            //打开连接
            URLConnection conn = url.openConnection();
            //打开输入流
            InputStream is = conn.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();//用于保存读取的数据
            //创建字节流--用于一次读取的长度
            byte[] bs = new byte[1024];
            int len;//读取长度
            //写数据
            while ((len = is.read(bs)) != -1) {
                outStream.write(bs, 0, len);//将数据写入到数据流缓存outStream中
            }
            byte[] bb = outStream.toByteArray();//获取写入的流数据--字节流
            content = new String(bb);//将字节流数据转化为字符串数据
            // 匹配email的正则
            String regex = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
            BufferedReader bufr = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            Pattern p = Pattern.compile(regex);
            // 将内容保存到text.txt文件中（输出缓冲流）
            File file = new File("D://rz.txt");
            Writer wt = new FileWriter(file, true);
            // 将字符流包装成缓冲流
            PrintWriter pw = new PrintWriter(wt, true);
            String line = null;

            Matcher m = p.matcher(content);
            while (m.find()) {
                // 写入到文件
                pw.write(m.group());
                // 获得匹配的email
                System.out.println(m.group());
            }

            System.out.println(content);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //java8 获取当天的开始时间和结束时间
    @Test
    public void getLocalTime() {
        //当天开始时间结束时间
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        String todayStartStr = todayStart.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(todayStartStr);
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        String todayEndStr = todayEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(todayEndStr);
        LocalDateTime now = LocalDateTime.now();
        LocalDate nowDate = LocalDate.now();
        String nowStr = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(nowStr);
        LocalDateTime plusHours = now.plusHours(-1);
        String plusHoursStr = plusHours.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(plusHoursStr);

        String ss = "张三";
        String[] split = ss.split(",");
        Arrays.stream(split).forEach(
            a -> {
                System.out.println(a);
            }
        );
    }


    @Test
    public void getTest() {

        ReportTimeLimit res = new ReportTimeLimit();
        long l = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        // res.currentLower = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay().toEpochSecond(ZoneOffset.of("+8"));

        // res.yoyUpper = LocalDateTime.now().minusYears(1L).toEpochSecond(ZoneOffset.of("+8"));
        //res.yoyLower = LocalDate.now().minusYears(1L).with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay().toEpochSecond(ZoneOffset.of("+8"));

        //res.momUpper = LocalDateTime.now().minusMonths(1L).toEpochSecond(ZoneOffset.of("+8"));
        //res.momLower = LocalDate.now().minusMonths(1L).with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay().toEpochSecond(ZoneOffset.of("+8"));
        System.out.println(l);

    }

    // 去当天的小时 分割时间
    @Test
    public void xDataLine() {
        long interval = 1l;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH");
        LocalTime max = LocalTime.now();
        long between = ChronoUnit.HOURS.between(LocalTime.MIN, LocalTime.now());
        long divide = between / interval;
        List<LocalTime> list = Stream.iterate(LocalTime.MIN, seed -> seed.plusHours(interval))
            .limit(divide + 1).collect(Collectors.toList());
        for (int i = 0; i < list.size(); i++) {
            String format = dtf.format(list.get(i));
        }
        System.out.println(list.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    public void getRanddouble() {
        //产生随机数
        double d = 70 + Math.random() * 100 % (100 - 70 + 1);
        StringBuilder sb = new StringBuilder();
        sb.append(d);
        sb.substring(0, 4);
        String div = sb.substring(0, 5);

    }

    @Test
    public void zhengze() {
        String content = "00时";
        //正则表达式，用于匹配非数字串，+号用于匹配出多个非数字串
        String regEx = "[^0-9]+";
        Pattern pattern = Pattern.compile(regEx);
        //用定义好的正则表达式拆分字符串，把字符串中的数字留出来
        String[] cs = pattern.split(content);
        System.out.println(Arrays.toString(cs));
    }

    //
    @Test
    public void UTCToCST() {

        try {
            UTCToCST("2022-07-01T18:06:48Z", "yyyy-MM-dd'T'HH:mm:ss'Z'");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date date = localToUTC("2022-08-26 15:30:00");
            String format = sdf.format(date);
            System.out.println(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void UTCToCST(String UTCStr, String format) throws ParseException {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        date = sdf.parse(UTCStr);
        System.out.println("UTC时间: " + date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
        //calendar.getTime() 返回的是Date类型，也可以使用calendar.getTimeInMillis()获取时间戳
        System.out.println("北京时间: " + calendar.getTime());
    }


    //  北京时间转UTC
    public static Date localToUTC(String localTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date localDate = null;
        try {
            localDate = sdf.parse(localTime);

        } catch (ParseException e) {
            e.printStackTrace();

        }
        long localTimeInMillis = localDate.getTime();
           /** long时间转换成Calendar */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(localTimeInMillis);
        /** 取得时间偏移量 */
        int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);
        /** 取得夏令时差 */
        int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);
        /** 从本地时间里扣除这些差量，即可以取得UTC时间*/
        calendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        /** 取得的时间就是UTC标准时间 */
        Date utcDate = new Date(calendar.getTimeInMillis());
        return utcDate;
    }


    @Test
    public void getBetweenList(){
        String  startTime="2022-12-20 00:00:00";
        String  endTime="2022-12-23 10:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 声明保存日期集合
        List<String> list = new ArrayList<String>();
        try {
            // 转化成日期类型
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);

            //用Calendar 进行日期比较判断
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime() <= endDate.getTime()) {
                // 把日期添加到集合
                list.add(sdf.format(startDate));
                // 设置日期
                calendar.setTime(startDate);
                //把日期增加一天
                calendar.add(Calendar.DATE, 1);
                // 获取增加后的日期
                startDate = calendar.getTime();
            }
            list.forEach(System.out::println);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }



    /***
     * @decription

     * 输入时间格式为：yyyy-MM-dd hh:mm:ss

     * @param time1 报警开始时间

     * @param time2 报警结束时间

     * @return  时间差（秒）

     */

    @Test
    public void diffTime(){
        String time1="2022-12-23 13:39:18 10";
        String time2="2022-12-23 13:39:18 999";
        Date date1 = StrToDate(time1);
        Date date2 = StrToDate(time2);
        Duration between = Duration.between(date1.toInstant(), date2.toInstant());
        System.out.println(between.toMillis() );

    }

    @Test
    public void testChengfa(){
        Integer a=1000;
        System.out.println(3*0.1);
        System.out.println(1*0.3);
    }

    /**

     * 字符串转换成日期

     * @param str

     * @return date

     */

    public static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        Date date = null;
        try {
            date = format.parse(str);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static long diffTime(String time1,String time2){
        Date date1 = StrToDate(time1);
        Date date2 = StrToDate(time2);
        Duration between = Duration.between(date1.toInstant(), date2.toInstant());
        return (between.toMillis());
    }



}
