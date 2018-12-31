package com.xkcoding.mq.rabbitmq.constants;

/**
 * <p>
 * RabbitMQ常量池
 * </p>
 *
 * @package: com.xkcoding.mq.rabbitmq.constants
 * @description: RabbitMQ常量池
 * @author: yangkai.shen
 * @date: Created in 2018-12-29 17:08
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public interface RabbitConsts {
    /**
     * 直接模式
     */
    String DIRECT_MODE_QUEUE = "queue_direct";

    /**
     * 分列模式
     */
    String FANOUT_MODE_QUEUE = "queue_fanout";

    /**
     * 主题模式
     */
    String TOPIC_MODE_QUEUE = "queue_topic";

    /**
     * 延迟模式
     */
    String DELAY_MODE_QUEUE = "delay_topic";
}
