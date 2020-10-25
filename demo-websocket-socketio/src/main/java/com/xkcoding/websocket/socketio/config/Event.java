package com.xkcoding.websocket.socketio.config;

/**
 * <p>
 * 事件常量
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-18 19:36
 */
public interface Event {
    /**
     * 聊天事件
     */
    String CHAT = "chat" ;

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
