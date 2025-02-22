package com.xkcoding.mq.rocketmq.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageStruct implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
    private String messageId;

    /**
     * 消息内容
     */
    private String messageContent;

    /**
     * 消息类型
     */
    private String messageType;
}
