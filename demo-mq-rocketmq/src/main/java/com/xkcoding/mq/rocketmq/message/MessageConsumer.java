package com.xkcoding.mq.rocketmq.message;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "TopicTest",consumerGroup="consumer-test")
public class MessageConsumer implements RocketMQListener<String> {

    @Override

 public void onMessage(String message) {
        System.out.printf("consumer name is %s data is %s%n", this.getClass().getSimpleName(), message);
    }
}
