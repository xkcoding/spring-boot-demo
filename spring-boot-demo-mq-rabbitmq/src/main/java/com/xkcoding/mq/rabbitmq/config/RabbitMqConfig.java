package com.xkcoding.mq.rabbitmq.config;

import com.xkcoding.mq.rabbitmq.constants.RabbitConsts;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * RabbitMQ配置，主要是配置队列，如果提前存在该队列，可以省略本配置类
 * </p>
 *
 * @package: com.xkcoding.mq.rabbitmq.config
 * @description: RabbitMQ配置，主要是配置队列，如果提前存在该队列，可以省略本配置类
 * @author: yangkai.shen
 * @date: Created in 2018-12-29 17:03
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 直接模式队列
     */
    @Bean
    public DirectExchange directQueue() {
        return new DirectExchange(RabbitConsts.DIRECT_MODE_QUEUE);
    }

    /**
     * 分列模式队列
     */
    @Bean
    public FanoutExchange fanoutQueue() {
        return new FanoutExchange(RabbitConsts.FANOUT_MODE_QUEUE);
    }

    /**
     * 主题模式队列
     */
    @Bean
    public TopicExchange topicQueue() {
        return new TopicExchange(RabbitConsts.TOPIC_MODE_QUEUE);
    }

    /**
     * 延迟模式队列
     */
    @Bean
    public Queue delayQueue() {
        return new Queue(RabbitConsts.DELAY_MODE_QUEUE, true);
    }

}
