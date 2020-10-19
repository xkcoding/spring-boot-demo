package com.xkcoding.websocket.socketio.payload;

import lombok.Data;

/**
 * <p>
 * 加群载荷
 * </p>
 *
 * @package: com.xkcoding.websocket.socketio.payload
 * @description: 加群载荷
 * @author: yangkai.shen
 * @date: Created in 2018-12-19 13:36
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
public class JoinRequest {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 群名称
     */
    private String groupId;
}
