package com.example.awaysuse.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uyun.whale.common.encryption.support.HttpUtils;


import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 *     基于concurrentHash的本地缓存工具类
 *     缓存删除基于timer定时器
 * <pre>
 * @author hejianfeng
 * @date 2019/10/5
 * @param
 * @return
 * <pre>
 * 修改记录
 * 版本号      修订日期        修改人     bug编号       修改内容
 * 1.0.0      2019/10/5      hejianfeng                      新建
 * </pre>
 */
@Component
public class CacheUtil {

    //默认大小
    private static final int DEFAULT_CAPACITY = 1024;

    // 最大缓存大小
    private static final int MAX_CAPACITY = 10000;

    //默认缓存过期时间
    private static final long DEFAULT_TIMEOUT = 3600;

    //1000毫秒
    private static final long SECOND_TIME = 1000;

    //存储缓存的Map
    private static final ConcurrentHashMap<String, Object> map;

    private static final Timer timer;

    static {
        map = new ConcurrentHashMap<>(DEFAULT_CAPACITY);
        timer = new Timer();
    }

    //私有化构造方法
    private CacheUtil() {

    }

    /**
     * <pre>
     *     缓存任务清除类
     * <pre>
     * @author hejianfeng
     * @date 2019/10/5
     * @param
     * @return
     * <pre>
     * 修改记录
     * 版本号      修订日期        修改人     bug编号       修改内容
     * 1.0.0      2019/10/5      hejianfeng                      新建
     * </pre>
     */
    static class ClearTask extends TimerTask {
        private String key;

        public ClearTask(String key) {
            this.key = key;
        }

        @Override
        public void run() {
            CacheUtil.remove(key);
        }

    }

    //==================缓存的增删改查

    /**
     * <pre>
     *     添加缓存
     * <pre>
     * @author hejianfeng
     * @date 2019/10/5
     * @param
     * @param
     * @return void
     * <pre>
     * 修改记录
     * 版本号      修订日期        修改人     bug编号       修改内容
     * 1.0.0      2019/10/5      hejianfeng                      新建
     * </pre>
     */
    public   boolean put(String key,Object object) {
        if (checkCapacity()) {
          /*  String res = HttpUtils.doGet(uyun_alert_url, null);
            JSONObject jsonObject=JSONObject.parseObject(res);
            JSONArray recordsArray = jsonObject.getJSONArray("records");
            Map<String,Object>  alertMap=recordsArray.stream().collect(Collectors.toMap(item ->((JSONObject)item).getString("name"), Function.identity(), (item, item1) -> item));
*/

            //默认缓存时间
            timer.schedule(new ClearTask(key), DEFAULT_TIMEOUT);
            return true;
        }
        return false;
    }

    /**
     * <pre>
     *     添加缓存
     * <pre>
     * @author hejianfeng
     * @date 2019/10/5
     * @param key
     * @param object
     * @param time_out  ：缓存过期时间：单位秒
     * @return void
     * <pre>
     * 修改记录
     * 版本号      修订日期        修改人     bug编号       修改内容
     * 1.0.0      2019/10/5      hejianfeng                      新建
     * </pre>
     */
    public static boolean put(String key, Object object, int time_out) {
        if (checkCapacity()) {
            map.put(key, object);
            //默认缓存时间
            timer.schedule(new ClearTask(key), time_out * SECOND_TIME);
        }
        return false;
    }


    /**
     * <pre>
     *     判断容量大小
     * <pre>
     * @author hejianfeng
     * @date 2019/10/5
     * @param
     * @return boolean
     * <pre>
     * 修改记录
     * 版本号      修订日期        修改人     bug编号       修改内容
     * 1.0.0      2019/10/5      hejianfeng                      新建
     * </pre>
     */
    public static boolean checkCapacity() {
        return map.size() < MAX_CAPACITY;
    }

    /**
     * <pre>
     *     批量增加缓存
     * <pre>
     * @author hejianfeng
     * @date 2019/10/5
     * @param m
     * @param time_out
     * @return void
     * <pre>
     * 修改记录
     * 版本号      修订日期        修改人     bug编号       修改内容
     * 1.0.0      2019/10/5      hejianfeng                      新建
     * </pre>
     */
    public static boolean put(Map<String, Object> m, int time_out) {
        if (map.size() + m.size() <= MAX_CAPACITY) {
            map.putAll(map);
            for (String key : m.keySet()) {
                timer.schedule(new ClearTask(key), time_out * SECOND_TIME);
            }
            return true;
        }
        return false;
    }

    /**
     * <pre>
     *     删除缓存
     * <pre>
     * @author hejianfeng
     * @date 2019/10/5
     * @param key
     * @return void
     * <pre>
     * 修改记录
     * 版本号      修订日期        修改人     bug编号       修改内容
     * 1.0.0      2019/10/5      hejianfeng                      新建
     * </pre>
     */
    public static void remove(String key) {
        map.remove(key);
    }

    /**
     * <pre>
     *     清除所有缓存
     * <pre>
     * @author hejianfeng
     * @date 2019/10/5
     * @param
     * @return void
     * <pre>
     * 修改记录
     * 版本号      修订日期        修改人     bug编号       修改内容
     * 1.0.0      2019/10/5      hejianfeng                      新建
     * </pre>
     */
    public void clearAll() {
        if (map.size() > 0) {
            map.clear();
        }
        timer.cancel();
    }

    /**
     * <pre>
     *     获取缓存
     * <pre>
     * @author hejianfeng
     * @date 2019/10/5
     * @param key
     * @return java.lang.Object
     * <pre>
     * 修改记录
     * 版本号      修订日期        修改人     bug编号       修改内容
     * 1.0.0      2019/10/5      hejianfeng                      新建
     * </pre>
     */
    public static Object get(String key) {
        return map.get(key);
    }

    /**
     * <pre>
     *     是否包含某个缓存
     * <pre>
     * @author hejianfeng
     * @date 2019/10/5
     * @param key
     * @return boolean
     * <pre>
     * 修改记录
     * 版本号      修订日期        修改人     bug编号       修改内容
     * 1.0.0      2019/10/5      hejianfeng                      新建
     * </pre>
     */
    public static boolean isContain(String key) {
        return map.contains(key);
    }


    public static void main(String[] args) {
        /* Map<String,Object>  mp=new HashMap<>();
        mp.put("应用",0);
        mp.put("中间件",0);
        mp.put("数据库",0);
        mp.put("服务器",0);
        mp.put("网络设备",0);
        mp.put("全流程",0);
        mp.put("云管",0);
        mp.put("alertTotal",0);
        mp.put("安全事件",0);
        map.put("alertInfo", mp);
        CacheUtil.put("alertInfo",mp,20);*/



         Object alertInfo = CacheUtil.get("alertInfo");
         System.out.println(alertInfo);

    }



}
