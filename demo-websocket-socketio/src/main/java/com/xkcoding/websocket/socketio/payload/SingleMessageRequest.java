package com.xkcoding.websocket.socketio.payload;

import lombok.Data;

/**
 * <p>
 * 私聊消息载荷
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-18 17:02
 */
@Data
public class SingleMessageRequest {
    /**
     * 消息发送方用户id
     */
    private String fromUid;

    /**
     * 消息接收方用户id
     */
    private String toUid;

    /**
     * 消息内容
     */
    private String message;
}
