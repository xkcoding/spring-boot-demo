package com.example.awaysuse;

import cn.hutool.http.HttpRequest;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import com.example.awaysuse.model.Help;
import com.example.awaysuse.model.Person;
import com.example.awaysuse.util.VUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
//@SpringBootTest
public class AwaysuseApplicationTests {

    @Test
    void contextLoads() {
    }

    /*
       Assert断言工具类，通常用于数据合法性检查
     */
    @Test
    public void testAssert() {
        Object object = "null";
        //  要求参数 object 必须为非空（Not Null），否则抛出异常，不予放行
        Assert.notNull(object, "传递的参数不能是空");
        // 要求参数必须空（Null），否则抛出异常，不予『放行』。
        // 和 notNull() 方法断言规则相反
        Assert.isNull(null, "传递的参数必须是空");
        // 要求参数必须为真（True），否则抛出异常，不予『放行』。
        boolean expression = true;
        Assert.isTrue(expression, "要求参数必须为真");
        // 要求参数（List/Set）必须非空（Not Empty），否则抛出异常，不予放行
        List<String> collection = null;
        // Assert.notEmpty(collection,"要求参数List必须非空");
        // 要求参数（String）必须有长度（即，Not Empty），否则抛出异常，不予放行
        String text = "哈哈";
        Assert.hasLength(text, "要求字符串必须有长度");
        // 要求参数（String）必须有内容（即，Not Blank），否则抛出异常，不予放行
        String msg = "非空字符串";
        Assert.hasText(msg, "要求参数必有内容");
        // 要求参数是指定类型的实例，否则抛出异常，不予放行
        Object obj = "字符串";
        Person p = new Person();
        p.setAge(11);
        Assert.isInstanceOf(Person.class, p, "要求参数是指定类型的实例");
        // 要求参数 `subType` 必须是参数 superType 的子类或实现类，否则抛出异常，不予放行
        //void isAssignable(Class superType, Class subType, String message)

    }

    //# 对象、数组、集合
    @Test
    public void testObjectUtils() {
        Person p = new Person();
        //获取对象的类名。参数为 null 时，返回字符串："null"
        String s = ObjectUtils.nullSafeClassName(null);
        System.out.println(s);
        // 参数为 null 时，返回 0
        System.out.println(ObjectUtils.nullSafeHashCode("哈哈"));
        // 参数为 null 时，返回字符串："null"
        // String nullSafeToString(boolean[] array);
        // 获取对象 HashCode（十六进制形式字符串）。参数为 null 时，返回 0
        //String getIdentityHexString(Object obj);
        // 获取对象的类名和 HashCode。参数为 null 时，返回字符串：""
        //String identityToString(Object obj);
        // 相当于 toString()方法，但参数为 null 时，返回字符串：""
        //String getDisplayString(Object obj);

    }

    @Test
    public void getMapUtil() {
       /* MapUtils<String, Object> stringObjectMapUtils = new MapUtils<>();
        MapUtils<String, Object> put = stringObjectMapUtils.put("1", 1);
        System.out.println(put);*/

        //Integer integer = MapUtils.getInteger(map, "1");
        // System.out.println(map);
    }


    // 两个集合合并 java8
    @Test
    public void getListAll() {
        List<Help> list1 = new LinkedList<>();
        list1.add(new Help().setUserId(1));
        list1.add(new Help().setUserId(20));
        list1.add(new Help().setUserId(4));
        list1.add(new Help().setUserId(15));
        List<Help> list2 = new LinkedList<>();
        list2.add(new Help().setUserId(3));
        list2.add(new Help().setUserId(7));
        list2.add(new Help().setUserId(30));
        list2.add(new Help().setUserId(10));
        List<Help> list = new LinkedList<>();
        Stream.of(list1, list2).forEach(list::addAll);
        list.sort(Comparator.comparing(Help::getUserId));
        System.out.println(list);

    }

    //  java 8  分组
    @Test
    public void groupBy() {
        Order order1 = new Order();
        order1.setOrderId("123");
        order1.setGoodsId("S1");

        Order order2 = new Order();
        order2.setOrderId("123");
        order2.setGoodsId("S2");

        Order order3 = new Order();
        order3.setOrderId("1234");
        order3.setGoodsId("S3");

        List<Order> list = new ArrayList();
        list.add(order1);
        list.add(order2);
        list.add(order3);

        Map<String, List<Order>> map = list.stream().collect(Collectors.groupingBy(ord -> ord.getOrderId()));
        log.info("分组之后得到的结果是=={}", map);

    }

    //  Map 转list
    @Test
    public void convertMapToList() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "AAAA");
        map.put("2", "BBBB");
        map.put("3", "CCCC");
        map.put("4", "DDDD");
        map.put("5", "EEEE");
        List<Object> list = map.entrySet().stream().map(et -> et.getKey() + "_" + et.getValue()).collect(Collectors.toList());
        list.forEach(obj -> System.out.println(obj));
    }

    @Test
    public void getZoneTime() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("GMT+8"));
        String dateTime = now.format(DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.of("GMT+8")));
        System.out.println(dateTime);

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        System.out.println(time);
    }

}

class Order {
    String orderId;
    String goodsId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return "Order{" +
            "orderId='" + orderId + '\'' +
            ", goodsId='" + goodsId + '\'' +
            '}';
    }


    @Test
    public void functionTest() {
        VUtils.isTure(true).throwMessage("需要抛出异常");
    }

    @Test
    public void isTrueOrFalse() {
        VUtils.isTureOrFalse(true).trueOrFalseHandle(
            () -> {
                System.out.println("条件为真时输出");
            },
            () -> {
                System.out.println("条件为假时处理异常");
            }
        );
    }

    @Test
    void isBlankOrNoBlank() {
        /*VUtils.isBlankOrNoBlank("hello")
            .presentOrElseHandle(System.out::println,()->{
                System.out.println("这是空字符串");
            });*/
        String s = "63.9G";
        String g = s.split("G")[0];
        System.out.println(g);

    }


    @Test
    public void givenUsingTimer_whenSchedulingTaskOnce_thenCorrect() {
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("Task performed on: " + new Date() + "n" +
                    "Thread's name: " + Thread.currentThread().getName());
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 1000L;
        timer.schedule(task, delay);
    }


    @Test
    public void getMuly() {
       /* BigDecimal b1 = new BigDecimal(50);
        BigDecimal b2 = new BigDecimal(219);
        // if (!Objects.equals(b2, BigDecimal.ZERO)) {
            // 不能整除，数学上是无穷小数，抛出ArithmeticException异常
            //BigDecimal b3 = b1.divide(b2);
            // 指定计算结果的精度，保留到小数点后几位，以及舍入模式
            BigDecimal b3 = b2.divide(b1, 4, BigDecimal.ROUND_HALF_UP);
            System.out.println(b3);
       // }*/
        /*String percentFormat = getPercentFormat(2.0, 2, 2);
        System.out.println(percentFormat);*/
        String s = replaceBlank("jj\nkk");
        System.out.println(s);

    }

    // double保留两位小数
    public static String getPercentFormat(double date, int IntegerDigits, int FractionDigits) {
        NumberFormat nf = java.text.NumberFormat.getPercentInstance();
        nf.setMaximumIntegerDigits(IntegerDigits);//小数点前保留几位
        nf.setMinimumFractionDigits(FractionDigits);// 小数点后保留几位
        String str = nf.format(date);
        return str;
    }

    public static String replaceBlank(String content) {
        String dest = "";
        if (content != null) {
            //  过滤请求报文中的制表符及换行符等无关元素
//			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(content);
            dest = m.replaceAll("");
        }
        BigDecimal decimal = new BigDecimal(1);
        BigDecimal decima2 = new BigDecimal(3);
        BigDecimal divide = decimal.divide(decima2, 4, BigDecimal.ROUND_HALF_UP);

        String s1 = "2003-12-12 00:00:00";
        String s2 = "2004-04-01 00:00:00";
        int res = s1.compareTo(s2);


        return dest;
    }


    @Test
    public void splitTest() {
        String str = "17DAAFD5F99C4DE39AD487993F6098EF,54501,斋堂,1154130E,395827N,null,null,,null,null,null,否,是,是,null";
        String str1 = "4B8DA84E16A0441AB1824017FCED50BF,54421,上甸子,1170642E,403932N,null,null,,null,null,null,否,是,是,null";
        List<String> list = Arrays.asList(str.split(","));
        List<String> list1 = Arrays.asList(str1.split(","));
        System.out.println(list);
        System.out.println(list1);

        String s = "20220602";
        String substring = s.substring(0, 4);
        System.out.println(substring);

    }

    //获取当前天气信息
    @Test
    public void getWeather() {
        String httpRequest = HttpRequest.get("http://wthrcdn.etouch.cn/weather_mini?city=%E5%8C%97%E4%BA%AC").execute().body();
        System.out.println(httpRequest);

    }

    // java8  根据某个字段属性进行去重
    @Test
    public void personDistinct() {
       /* boolean b = compareTime("09:00:00", "18:00:00");
        System.out.println(b);*/
        List<Person> pr = new ArrayList<>();
        Person p1 = new Person();
        Person p2 = new Person();
        Person p3 = new Person();
        Person p4 = new Person();
        p1.setName("李二").setAge(15).setId(1L);
        p2.setName("张三").setAge(16).setId(2L);
        p3.setName("赵四").setAge(17).setId(3L);
        p4.setName("王五").setAge(18).setId(4L);
        pr.add(p1);
        pr.add(p2);
        pr.add(p3);
        pr.add(p4);

        //  根据name和age 进行去重
        ArrayList<Person> collect = pr.stream()
            .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(f -> f.getName() + f.getAge()))), ArrayList::new));
        // System.out.println(collect);

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < pr.size(); i++) {
            int sum = pr.stream().limit(i + 1).mapToInt(Person::getAge).sum();
            map.put(i, sum);

        }
        System.out.println(map);

    }

    @Test
    public void randomTest() {
        //打印 70到80之间的随机数
        int min = 70;
        int max = 90;
        Random random = new Random();
        // random.nextInt(10) 返回0 - 10 之间的伪随机数 [0 - 10 )，包括0  不包括10
        // 最小值：(80 - 70) + 70 = 0 + 70 = 70
        // 最大值：(80 - 70) + 70 = 9 + 70 = 79
        System.out.println(random.nextInt(max - min) + min + Math.random());
        double v = random.nextInt(max - min) + min + Math.random();
        String format = String.format("%.2f", v);
        System.out.println(format);
    }


    @Test
    public void itor() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "美好的周一");
        map.put(2, "美好的周二");
        map.put(3, "美好的周三");

        Set<Map.Entry<Integer, String>> entries = map.entrySet();
        for (Map.Entry entry : entries) {
            System.out.println("key:" + entry.getKey() + " "
                + "value:" + entry.getValue());
        }

    }

    static int j;

    public static class Mythread2 extends Thread {


        public Mythread2(int i) {
            j = i;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                j++;
            }
            System.out.println(j);
        }
    }

    public static void main(String[] args) {
        int i = 0;
        Mythread2 mythread2 = new Mythread2(i);
        Mythread2 mythread3 = new Mythread2(i);
        mythread2.start();
        mythread3.start();
    }

    // ArrayList使用中出现的坑
    @Test
    private static void asListAdd() {
        String[] arr = {"1", "2", "3"};
        List<String> strings = new ArrayList<>(Arrays.asList(arr));
        arr[2] = "4";
        System.out.println(strings.toString());
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()) {
            if ("4".equals(iterator.next())) {
                iterator.remove();
            }
        }
        strings.forEach(val -> {
            strings.remove("4");
            strings.add("3");
        });
        System.out.println(Arrays.asList(arr).toString());
    }

    //

    /**
     * 字符串转换成时区化的时间对象
     *
     * @return
     */

    @Test
    public void parseZonedDateStr() {
        ZonedDateTime zonedDateTime = parseZonedDate("2022-02-10 00:10:00");
        System.out.println(zonedDateTime);
    }

    public static ZonedDateTime parseZonedDate(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        if (dateTimeStr.length() == 10) {
            dateTimeStr += " 00:00:00.0";
        } else if (dateTimeStr.length() == 19) {
            dateTimeStr += ".0";
        }
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTimeStr, formatter.withZone(ZoneId.systemDefault()));

        return zonedDateTime;

    }


    public static List<String> getExecutionTimeByNum(String cronStr, Integer num) {
        CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.SPRING));
        Cron cron = parser.parse(cronStr);
        ExecutionTime time = ExecutionTime.forCron(cron);
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime next = getNext(time, now);
        List<ZonedDateTime> timeList = new ArrayList<>(num);
        timeList.add(next);
        for (int i = 1; i < num; i++) {
            next = getNext(time, next);
            timeList.add(next);
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<String> resultList = new ArrayList<>(num);
        for (ZonedDateTime item : timeList) {
            String result = item.format(format);
            resultList.add(result);
        }
        return resultList;
    }

    private static ZonedDateTime getNext(ExecutionTime time, ZonedDateTime current) {
        return time.nextExecution(current).get();
    }

    @Data
    public static class CustomCronField {
        private List<Integer> minutes;
        private List<Integer> hours;
        private List<Integer> weekdays;
    }

    //  获取cron未来10次  运行的时间
    @Test
    public void getTimes() {
        List<String> executionTimeByNum = getExecutionTimeByNum("0 0/1 * * * ?", 10);
        System.out.println(executionTimeByNum);
    }

    //  Java解析Cron表达式
    @Test
    public void getExecuteTime() {
        String cron = "";
        int size = 10;
        // 每月1号执行
        cron = "0 0 5,9,11,14,17,20,23 * * ?";
        CronSequenceGenerator g = new CronSequenceGenerator(cron);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<String> res = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            d = g.next(d);
            res.add(sdf.format(d));
        }
        res.forEach(System.out::println);
        String ss = "2023-01-06 10:00";
        String s = ss.substring(0, 10) + " 00:00";
        System.out.println(ZonedDateTime.from(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            .withZone(ZoneId.of("GMT+8")).parse(s)));
    }

    @Test
    public void checkTime() {
        String dataTimeStr = "2023-01-07 10:00:00";
        String dataTime = "2023-01-07 10:30:00";
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String befDateTime = ""; //TimeUtil.computeDateTime(dataTimeStr,-1);
        String endDateTime = ""; //TimeUtil.computeDateTime(dataTimeStr,1);

        LocalDateTime startLocalDateTime = LocalDateTime.parse(befDateTime, df);
        LocalDateTime endLocalDateTime = LocalDateTime.parse(endDateTime, df);
        LocalDateTime localdataTime = LocalDateTime.parse(dataTime, df);
        if (startLocalDateTime.isBefore(localdataTime) && endLocalDateTime.isAfter(localdataTime)) {
            System.out.println("在时间范围内");
        }
        System.out.println("不在时间范围内");
    }

    @Test
    public void subStr() {
        String dataTimeStr = "2023-01-07 10:00:00";
        String substring = dataTimeStr.substring(14, 19);
        String currentDate = dataTimeStr.substring(0, 10);
        String currentDate1 = dataTimeStr.substring(11,19);
        String currentDate2 = dataTimeStr.substring(0,19);
        System.out.println(substring);
    }

    /**
     * 获取下一个分钟值以0或者5结尾的时间点（单位：毫秒）
     *
     * @return
     */
    @Test
    public void getNextMillisEndWithMinute0or5() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int minute = 58;
        if (minute < 55) {
            //  如果是获取下一个整5分钟  add的值这样计算
            int add = minute % 10 < 5 ? 5 - minute % 10 : 10 - minute % 10;
            //int add = minute % 10 <= 5 ? minute % 10:  minute % 10-5;
            calendar.add(Calendar.MINUTE, -add);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            System.out.println(calendar.getTime());
        }
        // 当前时间+1小时
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date endTime = DateUtils.addHours(calendar.getTime(), 1);
        System.out.println(endTime);

    }

    /**
     * 获取下一个分钟值以0或者5结尾的时间点（单位：毫秒）
     *
     * @return
     */
    @Test
    public void getLastMillisEndWithMinute0or5() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int minute = calendar.get(Calendar.MINUTE);
        //  如果是获取下一个整5分钟  add的值这样计算
        int add = minute % 10 <= 5 ? minute % 10:  minute % 10-5;
        calendar.add(Calendar.MINUTE, -add);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        System.out.println(calendar.getTime());


    }

    @Test
    public void  sortMap(){

            Map<String, Integer> unsortMap = new HashMap<>();

            unsortMap.put("02:00", 10);
            unsortMap.put("06:00", 5);
            unsortMap.put("01:00", 6);
            unsortMap.put("02:00", 20);


        Map result = unsortMap.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        System.out.println(result.toString());

    }


    @Test
    public void testStr(){
        Map<Object, Object> objectObjectMap = Collections.synchronizedMap(new HashMap());
        String  s="1";
        s+=s;
        System.out.println(s);
    }

    @Test
    public void testBigdecimal(){
        BigDecimal b1 = new BigDecimal("0.1");
        BigDecimal b2 = BigDecimal.valueOf(0.1);
        System.out.println(b1);// 打印：0.1
        System.out.println(b2);// 打印：0.1000000000000000055511151231257827021181583404541015625
        System.out.println(b1.compareTo(b2));// -1，说明b1小于b2

    }

}
