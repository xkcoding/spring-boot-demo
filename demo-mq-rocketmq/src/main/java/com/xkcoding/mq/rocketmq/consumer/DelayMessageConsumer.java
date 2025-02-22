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
