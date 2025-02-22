package com.xkcoding.mq.rocketmq.controller;

import com.xkcoding.mq.rocketmq.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * <p>
 * 消息控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageProducer messageProducer;

    @GetMapping("/normal")
    public String sendNormalMessage(@RequestParam String message) {
        messageProducer.sendNormalMessage(message);
        return "普通消息发送成功";
    }

    @GetMapping("/delay")
    public String sendDelayMessage(@RequestParam String message, @RequestParam Integer delayLevel) {
        messageProducer.sendDelayMessage(message, delayLevel);
        return "定时消息发送成功";
    }

    @GetMapping("/order")
    public String sendOrderMessage(@RequestParam String message, @RequestParam String orderId) {
        messageProducer.sendOrderMessage(message, orderId);
        return "顺序消息发送成功";
    }

    @GetMapping("/transaction")
    public String sendTransactionMessage(@RequestParam String message) {
        messageProducer.sendTransactionMessage(message);
        return "事务消息发送成功";
    }
}
