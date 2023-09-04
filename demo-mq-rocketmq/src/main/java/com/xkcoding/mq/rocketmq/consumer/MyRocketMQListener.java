package com.xkcoding.mq.rocketmq.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(topic = "my-topic", consumerGroup = "my-consumer-group")
public class MyRocketMQListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        // 处理收到的消息
        System.out.println("Received message: " + message);
    }
}
