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
