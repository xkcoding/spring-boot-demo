package com.xkcoding.mq.rocketmq.constants;

public interface RocketMQConstant {
    /**
     * TOPIC
     */
    String TOPIC_TEST = "TEST-TOPIC";

  /**
     * 消费者组
     */
    String CONSUMER_GROUP = "demo-consumer-group";

    /**
     * 消息类型
     */
    interface MessageType {
        /**
         * 普通消息
         */
        String NORMAL = "NORMAL";

        /**
         * 定时消息
         */
        String DELAY = "DELAY";

        /**
         * 顺序消息
         */
        String ORDER = "ORDER";

        /**
         * 事务消息
         */
        String TRANSACTION = "TRANSACTION";
    }

}
