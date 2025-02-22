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
