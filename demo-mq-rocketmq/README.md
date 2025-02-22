## Spring Boot 集成 RocketMQ 示例文档

>项目介绍
本示例演示 Spring Boot 如何集成 RocketMQ 实现多种消息模式，包含：普通消息、延时消息、顺序消息、事务消息的收发功能。
> 部署方式
直接采用本地部署RocketMQ，直接下载安装即可
## pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <artifactId>demo-mq-rocketmq</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <packaging>jar</packaging>

  <name>demo-mq-rocketmq</name>
  <description>Demo project for Spring Boot</description>

  <parent>
    <groupId>com.xkcoding</groupId>
    <artifactId>spring-boot-demo</artifactId>
    <version>1.0.0-SNAPSHOT</version>
  </parent>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    <java.version>1.8</java.version>
  </properties>

  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter</artifactId>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>

    <!-- RocketMQ -->
    <dependency>
      <groupId>org.apache.rocketmq</groupId>
      <artifactId>rocketmq-spring-boot-starter</artifactId>
      <version>2.2.3</version>
    </dependency>

    <!-- lombok -->
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.30</version> <!-- 兼容JDK8~17的稳定版本 -->
      <scope>provided</scope>
    </dependency>


    <dependency>
      <groupId>org.apache.rocketmq</groupId>
      <artifactId>rocketmq-client-java</artifactId>
      <version>5.0.7</version>
    </dependency>
  </dependencies>

  <build>
    <finalName>demo-mq-rocketmq</finalName>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
    </plugins>
  </build>

</project>
```
## application.yml
```yaml

server:
  port: 8081
  servlet:
    context-path: /demo

spring:
  application:
    name: spring-boot-demo-mq-rocketmq

logging:
  level:
    root: info
    com.xkcoding.mq.rocketmq: debug
    org.apache.rocketmq: debug
    RocketmqClient: debug

rocketmq:
  name-server: 127.0.0.1:9876
  producer:
    group: demo-producer-group
    send-message-timeout: 3000
    retry-times-when-send-failed: 3
    retry-times-when-send-async-failed: 3
    retry-next-server: true
  consumer:
    access-key: # 访问密钥，可选
    secret-key: # 密钥，可选
```
## 启动RocketMQ
1. 下载RocketMQ
2.	D:\software\rocketmq\rocketmq-all-5.0.0-bin-release\bin 在 bin 目录下输入：cmd
    输入启动命令：start mqnamesrv
    注意：此 CMD 窗口不能关闭！！！关闭 CMD 窗口服务就会停掉。
3. 启动 broker
    D:\software\rocketmq\rocketmq-all-5.0.0-bin-release\bin 在 bin 目录下输入：cmd
    输入启动命令：start mqbroker.cmd -n 127.0.0.1:9876 autoCreateTopicEnable=true
##  消息生产者
```java
package com.xkcoding.mq.rocketmq.producer;

import com.xkcoding.mq.rocketmq.constants.RocketMQConstant;
import com.xkcoding.mq.rocketmq.message.MessageStruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;
/**
 * <p>
 * 消息生产者
 * </p>
 */
@Slf4j
@Component
public class MessageProducer {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${rocketmq.name-server}")
    private String nameServer;

    @PostConstruct
    public void init() {
        log.info("消息生产者初始化完成，nameServer={}", nameServer);
        try {
            // 检查 RocketMQ 连接
            Message<String> testMessage = MessageBuilder.withPayload("test")
                    .setHeader(RocketMQHeaders.TAGS, "test")
                    .build();
            SendResult result = rocketMQTemplate.syncSend(RocketMQConstant.TOPIC_TEST, testMessage);
            log.info("RocketMQ 连接测试成功：sendStatus={}, msgId={}, queueId={}, topic={}",
                result.getSendStatus(),
                result.getMsgId(),
                result.getMessageQueue().getQueueId(),
                result.getMessageQueue().getTopic());
        } catch (Exception e) {
            log.error("RocketMQ 连接测试失败，请检查 RocketMQ 服务是否启动：{}", e.getMessage(), e);
        }
    }

    /**
     * 发送普通消息
     */
    public void sendNormalMessage(String content) {
        String messageId = UUID.randomUUID().toString();
        log.info("开始发送普通消息：messageId={}, content={}", messageId, content);

        try {
            MessageStruct messageStruct = new MessageStruct();
            messageStruct.setMessageId(messageId);
            messageStruct.setMessageContent(content);
            messageStruct.setMessageType(RocketMQConstant.MessageType.NORMAL);

            Message<MessageStruct> message = MessageBuilder.withPayload(messageStruct)
                    .setHeader(RocketMQHeaders.TAGS, "normal")
                    .setHeader(RocketMQHeaders.KEYS, messageId)
                    .build();

            log.info("发送消息到主题：topic={}, tag={}", RocketMQConstant.TOPIC_TEST, "normal");

            SendResult sendResult = rocketMQTemplate.syncSend(RocketMQConstant.TOPIC_TEST, message);
            log.info("普通消息发送完成：messageId={}, sendStatus={}, msgId={}, queueId={}, topic={}",
                messageId,
                sendResult.getSendStatus(),
                sendResult.getMsgId(),
                sendResult.getMessageQueue().getQueueId(),
                sendResult.getMessageQueue().getTopic());
        } catch (Exception e) {
            log.error("普通消息发送失败：messageId={}, error={}", messageId, e.getMessage(), e);
            throw new RuntimeException("消息发送失败：" + e.getMessage(), e);
        }
    }

    /**
     * 发送延时消息
     */
    public void sendDelayMessage(String content, int delayLevel) {
        try {
            MessageStruct messageStruct = new MessageStruct();
            messageStruct.setMessageId(UUID.randomUUID().toString());
            messageStruct.setMessageContent(content);
            messageStruct.setMessageType(RocketMQConstant.MessageType.DELAY);

            Message<MessageStruct> message = MessageBuilder.withPayload(messageStruct)
                    .setHeader(RocketMQHeaders.TAGS, "delay")
                    .setHeader(MessageConst.PROPERTY_DELAY_TIME_LEVEL, delayLevel)
                    .build();

            rocketMQTemplate.send(RocketMQConstant.TOPIC_TEST, message);
            log.info("延时消息发送成功，message={}", messageStruct);
        } catch (Exception e) {
            log.error("延时消息发送失败", e);
        }
    }

    /**
     * 发送顺序消息
     */
    public void sendOrderMessage(String content, String orderId) {
        try {
            MessageStruct messageStruct = new MessageStruct();
            messageStruct.setMessageId(UUID.randomUUID().toString());
            messageStruct.setMessageContent(content);
            messageStruct.setMessageType(RocketMQConstant.MessageType.ORDER);

            Message<MessageStruct> message = MessageBuilder.withPayload(messageStruct)
                    .setHeader(RocketMQHeaders.TAGS, "order")
                    .build();

            rocketMQTemplate.syncSendOrderly(RocketMQConstant.TOPIC_TEST, message, orderId);
            log.info("顺序消息发送成功，message={}", messageStruct);
        } catch (Exception e) {
            log.error("顺序消息发送失败", e);
        }
    }

    /**
     * 发送事务消息
     */
    public void sendTransactionMessage(String content) {
        try {
            MessageStruct messageStruct = new MessageStruct();
            messageStruct.setMessageId(UUID.randomUUID().toString());
            messageStruct.setMessageContent(content);
            messageStruct.setMessageType(RocketMQConstant.MessageType.TRANSACTION);

            Message<MessageStruct> message = MessageBuilder.withPayload(messageStruct)
                    .setHeader(RocketMQHeaders.TAGS, "transaction")
                    .setHeader(RocketMQHeaders.TRANSACTION_ID, messageStruct.getMessageId())
                    .build();

            TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction(
                    RocketMQConstant.TOPIC_TEST,
                    message,
                    null
            );

            log.info("事务消息发送成功，messageId={}, sendResult={}", messageStruct.getMessageId(), sendResult);
        } catch (Exception e) {
            log.error("事务消息发送失败", e);
        }
    }
}
```
## 普通消息消费者
```java
package com.xkcoding.mq.rocketmq.consumer;

import com.xkcoding.mq.rocketmq.constants.RocketMQConstant;
import com.xkcoding.mq.rocketmq.message.MessageStruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
/**
 * <p>
 * 普通消息消费者
 * </p>
 *
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = RocketMQConstant.TOPIC_TEST,
        consumerGroup = RocketMQConstant.CONSUMER_GROUP + "_normal",
        selectorType = SelectorType.TAG,
        selectorExpression = "normal",
        consumeMode = ConsumeMode.CONCURRENTLY,
        messageModel = MessageModel.CLUSTERING,
        maxReconsumeTimes = 3
)

public class
MessageConsumer implements RocketMQListener<MessageStruct> {

    @PostConstruct
    public void init() {
        log.info("普通消息消费者初始化完成：topic={}, consumerGroup={}, tag={}, messageModel={}",
            RocketMQConstant.TOPIC_TEST,
            RocketMQConstant.CONSUMER_GROUP + "_normal",
            "normal",
            MessageModel.CLUSTERING);
    }

    @Override
    public void onMessage(MessageStruct message) {
        long startTime = System.currentTimeMillis();
        log.info("普通消息消费者开始处理消息：messageId={}, messageContent={}, messageType={}, startTime={}",
            message.getMessageId(),
            message.getMessageContent(),
            message.getMessageType(),
            startTime);

        try {
            // 模拟业务处理
            Thread.sleep(100);

            long endTime = System.currentTimeMillis();
            log.info("普通消息处理完成：messageId={}, 耗时={}ms",
                message.getMessageId(),
                (endTime - startTime));
        } catch (Exception e) {
            log.error("普通消息处理异常：messageId={}, error={}",
                message.getMessageId(),
                e.getMessage(),
                e);
            // 抛出异常以触发重试机制
            throw new RuntimeException("消息处理失败：" + e.getMessage(), e);
        }
    }
}

```
## 延时消息消费者 DelayMessageConsumer.java
```java
package com.xkcoding.mq.rocketmq.consumer;

import com.xkcoding.mq.rocketmq.constants.RocketMQConstant;
import com.xkcoding.mq.rocketmq.message.MessageStruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
/**
 * <p>
 * 延时消息消费者
 * </p>
 *
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = RocketMQConstant.TOPIC_TEST,
        consumerGroup = RocketMQConstant.CONSUMER_GROUP + "_delay",
        selectorType = SelectorType.TAG,
        selectorExpression = "delay"
)
// 延时消息消费
public class DelayMessageConsumer implements RocketMQListener<MessageStruct> {
  /**
   * 消费消息
   * @param message
   */
    @Override
    public void onMessage(MessageStruct message) {
        try {
            log.info("延时消息消费者收到消息：messageId={}, messageContent={}, messageType={}, 接收时间={}",
                message.getMessageId(),
                message.getMessageContent(),
                message.getMessageType(),
                System.currentTimeMillis());
            // 模拟业务处理
            Thread.sleep(100);
            log.info("延时消息处理完成：messageId={}", message.getMessageId());
        } catch (Exception e) {
            log.error("延时消息处理异常：messageId={}", message.getMessageId(), e);
        }
    }
}

```
## 事务消息消费者 TransactionMessageConsumer.java
```java
package com.xkcoding.mq.rocketmq.consumer;

import com.xkcoding.mq.rocketmq.constants.RocketMQConstant;
import com.xkcoding.mq.rocketmq.message.MessageStruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
/**
 * <p>
 * 事务消息消费者
 * </p>
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = RocketMQConstant.TOPIC_TEST,
        consumerGroup = RocketMQConstant.CONSUMER_GROUP + "_transaction",
        selectorType = SelectorType.TAG,
        selectorExpression = "transaction"
)
// 消费者组
public class TransactionMessageConsumer implements RocketMQListener<MessageStruct> {
    @Override
    public void onMessage(MessageStruct message) {
        try {
            log.info("事务消息消费者收到消息：messageId={}, messageContent={}, messageType={}",
                message.getMessageId(),
                message.getMessageContent(),
                message.getMessageType());
            // 模拟业务处理
            Thread.sleep(100);
            log.info("事务消息处理完成：messageId={}", message.getMessageId());
        } catch (Exception e) {
            log.error("事务消息处理异常：messageId={}", message.getMessageId(), e);
        }
    }
}
```
## 顺序消息消费者 OrderlyMessageConsumer.java
```java
package com.xkcoding.mq.rocketmq.consumer;

import com.xkcoding.mq.rocketmq.constants.RocketMQConstant;
import com.xkcoding.mq.rocketmq.message.MessageStruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RocketMQMessageListener(
        topic = RocketMQConstant.TOPIC_TEST,
        consumerGroup = RocketMQConstant.CONSUMER_GROUP + "_order",
        selectorType = SelectorType.TAG,
        selectorExpression = "order",
        consumeMode = ConsumeMode.ORDERLY
)
public class OrderMessageConsumer implements RocketMQListener<MessageStruct> {
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void onMessage(MessageStruct message) {
        try {
            int count = counter.incrementAndGet();
            log.info("顺序消息消费者收到消息：messageId={}, messageContent={}, messageType={}, 消息序号={}",
                message.getMessageId(),
                message.getMessageContent(),
                message.getMessageType(),
                count);
            // 模拟业务处理
            Thread.sleep(100);
            log.info("顺序消息处理完成：messageId={}, 消息序号={}", message.getMessageId(), count);
        } catch (Exception e) {
            log.error("顺序消息处理异常：messageId={}", message.getMessageId(), e);
        }
    }
}
```
## 事务消息监听器 (TransactionListener.java)
```java
package com.xkcoding.mq.rocketmq.listener;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.xkcoding.mq.rocketmq.message.MessageStruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

@Slf4j
@RocketMQTransactionListener(rocketMQTemplateBeanName = "rocketMQTemplate")
public class TransactionListener implements RocketMQLocalTransactionListener {
    /**
     * 用于将字节数组转换为 MessageStruct 对象
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 执行本地事务
     *
     * @param msg 消息
     * @param arg 参数
     * @return RocketMQLocalTransactionState
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
            // 获取消息体
            byte[] payload = (byte[]) msg.getPayload();
            // 将字节数组转换为 MessageStruct 对象
            MessageStruct messageStruct = objectMapper.readValue(payload, MessageStruct.class);

            log.info("开始执行本地事务，消息ID：{}", messageStruct.getMessageId());

            // 执行本地事务
            boolean success = executeLocalTransactionLogic(messageStruct);

            if (success) {
                log.info("本地事务执行成功，消息ID：{}", messageStruct.getMessageId());
                return RocketMQLocalTransactionState.COMMIT;
            } else {
                log.info("本地事务执行失败，消息ID：{}", messageStruct.getMessageId());
                return RocketMQLocalTransactionState.ROLLBACK;
            }
        } catch (Exception e) {
            log.error("本地事务执行异常，需要回查", e);
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }

    /**
     * 回查本地事务状态
     *
     * @param msg 消息
     * @return RocketMQLocalTransactionState
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        try {
            // 获取消息体
            byte[] payload = (byte[]) msg.getPayload();
            // 将字节数组转换为 MessageStruct 对象
            MessageStruct messageStruct = objectMapper.readValue(payload, MessageStruct.class);

            String messageId = messageStruct.getMessageId();
            log.info("开始回查本地事务状态，消息ID：{}", messageId);

            // 回查本地事务状态
            boolean exists = checkLocalTransactionStatus(messageId);

            if (exists) {
                log.info("本地事务状态回查成功，消息ID：{}", messageId);
                return RocketMQLocalTransactionState.COMMIT;
            } else {
                log.info("本地事务状态回查失败，消息ID：{}", messageId);
                return RocketMQLocalTransactionState.ROLLBACK;
            }
        } catch (Exception e) {
            log.error("本地事务状态回查异常", e);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 执行本地事务
     * 这里需要实现具体的事务逻辑，例如数据库操作等
     */
    private boolean executeLocalTransactionLogic(MessageStruct messageStruct) {
        try {
            // 模拟执行本地事务
            log.info("执行本地事务逻辑，消息内容：{}", messageStruct.getMessageContent());
            Thread.sleep(500); // 模拟业务处理时间
            return true;
        } catch (Exception e) {
            log.error("本地事务执行失败", e);
            return false;
        }
    }

    private boolean checkLocalTransactionStatus(String messageId) {
        try {
            // 模拟查询本地事务状态
            log.info("查询本地事务状态，消息ID：{}", messageId);
            Thread.sleep(100); // 模拟查询时间
            return true;
        } catch (Exception e) {
            log.error("事务状态查询失败", e);
            return false;
        }
    }
}
```
## 常量 RocketMQConstant.java
```java
package com.xkcoding.mq.rocketmq.constants;

public interface RocketMQConstant {
    /**
     * TOPIC
     */
    String TOPIC_TEST = "TEST-TOPIC";

  /**
     * 消费者组
     */
    String CONSUMER_GROUP = "demo-consumer-group";

    /**
     * 消息类型
     */
    interface MessageType {
        /**
         * 普通消息
         */
        String NORMAL = "NORMAL";

        /**
         * 定时消息
         */
        String DELAY = "DELAY";

        /**
         * 顺序消息
         */
        String ORDER = "ORDER";

        /**
         * 事务消息
         */
        String TRANSACTION = "TRANSACTION";
    }

}
```
## 启动类 SpringBootRocketMQApplication.java
```java
package com.xkcoding.mq.rocketmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootDemoMqRocketmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoMqRocketmqApplication.class, args);
    }

}
```
## 消息体 MessageStruct.java
```java
package com.xkcoding.mq.rocketmq.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageStruct implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
    private String messageId;

    /**
     * 消息内容
     */
    private String messageContent;

    /**
     * 消息类型
     */
    private String messageType;
}
```
## 测试类 SpringBootRocketMQApplicationTests.java
```java
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
  // @Autowired 注释可忽略
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


```
## 注意事项
确保 RocketMQ NameServer 已启动
事务消息需要实现本地事务检查逻辑
消息重试策略通过 maxReconsumeTimes 参数配置
生产环境建议配置 ACL 访问控制
