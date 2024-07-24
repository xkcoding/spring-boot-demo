package com.xkcoding.mq.kafka.controller;

import com.alibaba.fastjson.JSONObject;
import com.xkcoding.mq.kafka.constants.KafkaConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SendMessage {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 测试发送消息
     */
   @RequestMapping("/send")
    public void testSend() {
        System.out.println("发送消息");


       for (int i = 0; i <100000 ; i++) {
           JSONObject order=new JSONObject();
           order.put("userId", 123321999+i);
           order.put("amount", 10000.0+i);
           order.put("statement", "pay"+i);
           kafkaTemplate.send(KafkaConsts.TOPIC_TEST, order.toJSONString()).addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
               @Override
               public void onFailure(Throwable ex) {
                   System.out.println("发送消息失败：" + ex.getMessage());
               }

               @Override
               public void onSuccess(SendResult<String, String> result) {
                   String content = String.format("发送消息成功：%s-%s-%s",

                       result.getRecordMetadata().topic(),

                       result.getRecordMetadata().partition(),

                       result.getRecordMetadata().offset());

                   System.out.println(content);
               }

           });
       }



    }

}
