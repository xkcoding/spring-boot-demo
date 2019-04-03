package com.xkcoding.websocket.socketio.payload;

import lombok.Data;

/**
 * <p>
 * 广播消息载荷
 * </p>
 *
 * @package: com.xkcoding.websocket.socketio.payload
 * @description: 广播消息载荷
 * @author: yangkai.shen
 * @date: Created in 2018-12-18 20:01
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
public class BroadcastMessageRequest {
    /**
     * 消息内容
     */
    private String message;
}
