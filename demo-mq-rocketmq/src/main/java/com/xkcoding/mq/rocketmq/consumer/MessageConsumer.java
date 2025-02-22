package com.xkcoding.mq.rocketmq.consumer;

import com.xkcoding.mq.rocketmq.constants.RocketMQConstant;
import com.xkcoding.mq.rocketmq.message.MessageStruct;
import lombok.extern.slf4j.Slf4j;
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
