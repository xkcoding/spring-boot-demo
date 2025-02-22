package com.xkcoding.mq.rocketmq.listener;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.xkcoding.mq.rocketmq.message.MessageStruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

@Slf4j
@RocketMQTransactionListener(rocketMQTemplateBeanName = "rocketMQTemplate")
public class TransactionListener implements RocketMQLocalTransactionListener {
    /**
     * 用于将字节数组转换为 MessageStruct 对象
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 执行本地事务
     *
     * @param msg 消息
     * @param arg 参数
     * @return RocketMQLocalTransactionState
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
            // 获取消息体
            byte[] payload = (byte[]) msg.getPayload();
            // 将字节数组转换为 MessageStruct 对象
            MessageStruct messageStruct = objectMapper.readValue(payload, MessageStruct.class);

            log.info("开始执行本地事务，消息ID：{}", messageStruct.getMessageId());

            // 执行本地事务
            boolean success = executeLocalTransactionLogic(messageStruct);

            if (success) {
                log.info("本地事务执行成功，消息ID：{}", messageStruct.getMessageId());
                return RocketMQLocalTransactionState.COMMIT;
            } else {
                log.info("本地事务执行失败，消息ID：{}", messageStruct.getMessageId());
                return RocketMQLocalTransactionState.ROLLBACK;
            }
        } catch (Exception e) {
            log.error("本地事务执行异常，需要回查", e);
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }

    /**
     * 回查本地事务状态
     *
     * @param msg 消息
     * @return RocketMQLocalTransactionState
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        try {
            // 获取消息体
            byte[] payload = (byte[]) msg.getPayload();
            // 将字节数组转换为 MessageStruct 对象
            MessageStruct messageStruct = objectMapper.readValue(payload, MessageStruct.class);

            String messageId = messageStruct.getMessageId();
            log.info("开始回查本地事务状态，消息ID：{}", messageId);

            // 回查本地事务状态
            boolean exists = checkLocalTransactionStatus(messageId);

            if (exists) {
                log.info("本地事务状态回查成功，消息ID：{}", messageId);
                return RocketMQLocalTransactionState.COMMIT;
            } else {
                log.info("本地事务状态回查失败，消息ID：{}", messageId);
                return RocketMQLocalTransactionState.ROLLBACK;
            }
        } catch (Exception e) {
            log.error("本地事务状态回查异常", e);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 执行本地事务
     * 这里需要实现具体的事务逻辑，例如数据库操作等
     */
    private boolean executeLocalTransactionLogic(MessageStruct messageStruct) {
        try {
            // 模拟执行本地事务
            log.info("执行本地事务逻辑，消息内容：{}", messageStruct.getMessageContent());
            Thread.sleep(500); // 模拟业务处理时间
            return true;
        } catch (Exception e) {
            log.error("本地事务执行失败", e);
            return false;
        }
    }

    private boolean checkLocalTransactionStatus(String messageId) {
        try {
            // 模拟查询本地事务状态
            log.info("查询本地事务状态，消息ID：{}", messageId);
            Thread.sleep(100); // 模拟查询时间
            return true;
        } catch (Exception e) {
            log.error("事务状态查询失败", e);
            return false;
        }
    }
}
