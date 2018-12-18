package com.xkcoding.websocket.socketio.payload;

import lombok.Data;

/**
 * <p>
 * 群聊消息载荷
 * </p>
 *
 * @package: com.xkcoding.websocket.socketio.payload
 * @description: 群聊消息载荷
 * @author: yangkai.shen
 * @date: Created in 2018-12-18 16:59
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
public class GroupMessageRequest {
    /**
     * 消息发送方用户id
     */
    private String fromUid;

    /**
     * 群组id
     */
    private String groupId;

    /**
     * 消息内容
     */
    private String message;
}
