package com.xkcoding.mq.rocketmq.controller;

import com.xkcoding.mq.rocketmq.message.MessageProducer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("sendMessage")
public class SendMsgController {
    @Autowired
    MessageProducer messageProducer;

    /**
     * 官方发送消息示例
     */
    @RequestMapping("official")
    public void sendMsgOfficial() throws Exception {
            //测试发送消息，实例化生产者producer
            // 实例化消息生产者Producer
            DefaultMQProducer producer = new DefaultMQProducer("test_message");
            // 设置NameServer的地址
            producer.setNamesrvAddr("192.168.1.18:9876");
            // 启动Producer实例
            producer.start();
            for (int i = 0; i < 50; i++) {
                // 创建消息，并指定Topic，Tag和消息体
                Message msg = new Message("TopicTest" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );
                // 发送消息到一个Broker
                SendResult sendResult = producer.send(msg);
                // 通过sendResult返回消息是否成功送达
                System.out.printf("%s%n", sendResult);
            }
            // 如果不再发送消息，关闭Producer实例。
            producer.shutdown();
    }

    /**
     * 发送普通消息
     */
    @RequestMapping("plain")
    public SendResult sendPlainMsg(){
        return messageProducer.sendPlainMsg("发送普通消息");
    }

    /**
     * 发送延迟消息，
     * messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     */
    @RequestMapping(value = "delay",method = RequestMethod.POST)
    public SendResult sendDelayMsg(@RequestParam(name = "delayLevel") int delayLevel){
        return messageProducer.sendDelayMsg("发送延迟消息",delayLevel);
    }

}
