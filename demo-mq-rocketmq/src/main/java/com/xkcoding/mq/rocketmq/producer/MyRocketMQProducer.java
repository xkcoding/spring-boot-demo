package com.xkcoding.mq.rocketmq.producer;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class MyRocketMQProducer implements ApplicationRunner {

  private final RocketMQTemplate rocketMQTemplate;

  public MyRocketMQProducer(RocketMQTemplate rocketMQTemplate) {
    this.rocketMQTemplate = rocketMQTemplate;
  }

  public void sendMessage(String message) {
    rocketMQTemplate.send("my-topic", MessageBuilder.withPayload(message).build());
  }

  @Override
  public void run(ApplicationArguments args) {
    this.sendMessage("hello");
  }
}
