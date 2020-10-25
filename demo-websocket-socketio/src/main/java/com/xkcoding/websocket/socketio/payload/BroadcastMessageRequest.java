package com.xkcoding.websocket.socketio.payload;

import lombok.Data;

/**
 * <p>
 * 广播消息载荷
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-18 20:01
 */
@Data
public class BroadcastMessageRequest {
    /**
     * 消息内容
     */
    private String message;
}
