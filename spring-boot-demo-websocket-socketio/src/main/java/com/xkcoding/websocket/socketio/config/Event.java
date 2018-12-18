package com.xkcoding.websocket.socketio.config;

/**
 * <p>
 * 事件常量
 * </p>
 *
 * @package: com.xkcoding.websocket.socketio.config
 * @description: 事件常量
 * @author: yangkai.shen
 * @date: Created in 2018-12-18 19:36
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public interface Event {
    /**
     * 聊天事件
     */
    String CHAT = "chat" ;

    /**
     * 收到消息
     */
    String CHAT_RECEIVED = "chat_received" ;

    /**
     * 拒收消息
     */
    String CHAT_REFUSED = "chat_refused" ;

    /**
     * 广播消息
     */
    String BROADCAST = "broadcast" ;

    /**
     * 群聊
     */
    String GROUP = "group" ;

    /**
     * 加入群聊
     */
    String JOIN = "join" ;

}
