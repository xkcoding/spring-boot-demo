package com.xkcoding.mq.rocketmq;

import com.xkcoding.mq.rocketmq.producer.MessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.concurrent.TimeUnit;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDemoMqRocketmqApplicationTests {
  @Autowired
  private MessageProducer messageProducer;

  /**
   * 测试普通消息
   */
  @Test
  public void testSendNormalMessage() {
    for (int i = 1; i <= 3; i++) {
      messageProducer.sendNormalMessage("这是第" + i + "条普通消息");
    }
  }


  /**
   * 测试延时消息
   */
  @Test
  public void testSendDelayMessage() throws InterruptedException {
    for (int i = 1; i <= 3; i++) {
      messageProducer.sendDelayMessage("这是第" + i + "条延时消息", i);
      TimeUnit.MILLISECONDS.sleep(100);
    }
    // 等待消息消费
    TimeUnit.SECONDS.sleep(15);
  }

  /**
   * 测试顺序消息
   */
  @Test
  public void testSendOrderMessage() throws InterruptedException {
    String orderId = "order_" + System.currentTimeMillis();
    String[] steps = {"创建", "支付", "发货", "收货"};

    for (String step : steps) {
      messageProducer.sendOrderMessage(step, orderId);
      TimeUnit.MILLISECONDS.sleep(100);
    }
    // 等待消息消费
    TimeUnit.SECONDS.sleep(3);
  }

  /**
   * 测试事务消息
   */
  @Test
  public void testSendTransactionMessage() throws InterruptedException {
    for (int i = 1; i <= 3; i++) {
      messageProducer.sendTransactionMessage("这是第" + i + "条事务消息");
      TimeUnit.MILLISECONDS.sleep(200);
    }
    // 等待事务提交和消息消费
    TimeUnit.SECONDS.sleep(5);
  }



}

