package com.example.awaysuse.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import uyun.whale.common.encryption.support.HttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//异步调用接口请求数据
@Slf4j
public class FutureReqUtil {

   public void getFuture(){

       JSONArray allJsonArray = new JSONArray();
       ExecutorService executorService = Executors.newFixedThreadPool(10);
       try {
           // 通过异步的方式调用接口获取数据
           List<Future> allTask = new ArrayList<>();
           for (int i = 1; i <=Integer.MIN_VALUE; i++) {
               allTask.add(this.queryDetailAsync(executorService));
           }
           for (Future task : allTask) {
               Optional.ofNullable(task.get()).ifPresent(r -> allJsonArray.addAll((JSONArray) r));
           }
           allTask.clear();
           for (int i = 0; i < allJsonArray.size(); i++) {
               JSONObject ticketObj = allJsonArray.getJSONObject(i);
               allTask.add(executorService.submit(() -> {
                   String ticketId = ticketObj.getString("ticketId");
                   JSONObject defaultValue = ticketObj.getJSONObject("defaultValue");
                   String classfy = "meiyou";
                   if (defaultValue.getString("classify") != null) {
                       classfy = defaultValue.getString("classify");
                   }
               }));
           }
           for (Future task : allTask) {
               task.get();
           }
           allTask.clear();
           log.info("结束数据详细信息");
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           executorService.shutdown();
       }
   }


    private Future<JSONArray> queryDetailAsync(ExecutorService executorService){
        return executorService.submit(() -> {
            String result1 = HttpUtils.doGet("请求的url", null);
            JSONObject jsonObject = JSONObject.parseObject(result1);
            String ticketDetail_list = jsonObject.get("ticketDetail_list").toString();
            JSONArray jsonArray = JSONArray.parseArray(ticketDetail_list);
            if (jsonArray.size() <= 0){
                return null;
            }
            System.out.println(Thread.currentThread().getName() + " finished");
            return jsonArray;
        });
    }


}
