package com.xkcoding.mq.rocketmq.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class MessageProducer {
    @Resource
    RocketMQTemplate rocketMQTemplate;

    /**
     * 发送普通消息
     * @param msg 消息内容
     */
    public SendResult sendPlainMsg(String msg){
      return rocketMQTemplate.syncSend("TopicTest", MessageBuilder.withPayload(msg).build());
    }

    /**
     * 发送延迟消息
     * @param msg 消息内容
     * @param delayLevel 延迟级别，在broker配置文件里定义
     */
    public SendResult sendDelayMsg(String msg,int delayLevel){
        return rocketMQTemplate.syncSend("TopicTest",MessageBuilder.withPayload(msg).build(),2000,delayLevel);
    }
    public void sendAsyncMsg(String msg){
        rocketMQTemplate.asyncSend("TopicTest", MessageBuilder.withPayload(msg).build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("消息发送成功"+sendResult.getMsgId());
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("消息发送失败",throwable);
            }
        });
    }

    /**
     * 发送顺序消息
     * @param msg 消息体
     * @param index hashKey
     * @return 发送结果
     */
    public SendResult sendOrderMsg(String msg,int index){
        rocketMQTemplate.setMessageQueueSelector((list, message, o) -> {
            //分组规则
            return null;
        });
      return  rocketMQTemplate.syncSendOrderly("TopicTest",MessageBuilder.withPayload(msg).build(),String.valueOf(index));
    }
}
