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