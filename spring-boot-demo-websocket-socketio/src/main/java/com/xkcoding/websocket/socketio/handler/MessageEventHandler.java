package com.xkcoding.websocket.socketio.handler;

import cn.hutool.core.util.ObjectUtil;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.xkcoding.websocket.socketio.config.DbTemplate;
import com.xkcoding.websocket.socketio.config.Event;
import com.xkcoding.websocket.socketio.payload.BroadcastMessageRequest;
import com.xkcoding.websocket.socketio.payload.GroupMessageRequest;
import com.xkcoding.websocket.socketio.payload.JoinRequest;
import com.xkcoding.websocket.socketio.payload.SingleMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * <p>
 * 消息事件处理
 * </p>
 *
 * @package: com.xkcoding.websocket.socketio.handler
 * @description: 消息事件处理
 * @author: yangkai.shen
 * @date: Created in 2018-12-18 18:57
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Component
@Slf4j
public class MessageEventHandler {
    @Autowired
    private SocketIOServer server;

    @Autowired
    private DbTemplate dbTemplate;

    /**
     * 添加connect事件，当客户端发起连接时调用
     *
     * @param client 客户端对象
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        if (client != null) {
            String token = client.getHandshakeData().getSingleUrlParam("token");
            // 模拟用户id 和token一致
            String userId = client.getHandshakeData().getSingleUrlParam("token");
            UUID sessionId = client.getSessionId();

            dbTemplate.save(userId, sessionId);
            log.info("连接成功,【token】= {},【sessionId】= {}", token, sessionId);
        } else {
            log.error("客户端为空");
        }
    }

    /**
     * 添加disconnect事件，客户端断开连接时调用，刷新客户端信息
     *
     * @param client 客户端对象
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        if (client != null) {
            String token = client.getHandshakeData().getSingleUrlParam("token");
            // 模拟用户id 和token一致
            String userId = client.getHandshakeData().getSingleUrlParam("token");
            UUID sessionId = client.getSessionId();

            dbTemplate.deleteByUserId(userId);
            log.info("客户端断开连接,【token】= {},【sessionId】= {}", token, sessionId);
            client.disconnect();
        } else {
            log.error("客户端为空");
        }
    }

    /**
     * 加入群聊
     *
     * @param client  客户端
     * @param request 请求
     * @param data    群聊
     */
    @OnEvent(value = Event.JOIN)
    public void onJoinEvent(SocketIOClient client, AckRequest request, JoinRequest data) {
        log.info("用户：{} 已加入群聊：{}", data.getUserId(), data.getGroupId());
        client.joinRoom(data.getGroupId());

        server.getRoomOperations(data.getGroupId()).sendEvent(Event.JOIN, data);
    }


    @OnEvent(value = Event.CHAT)
    public void onChatEvent(SocketIOClient client, AckRequest request, SingleMessageRequest data) {
        Optional<UUID> toUser = dbTemplate.findByUserId(data.getToUid());
        if (toUser.isPresent()) {
            log.info("用户 {} 刚刚私信了用户 {}：{}", data.getFromUid(), data.getToUid(), data.getMessage());
            sendToSingle(toUser.get(), data);
            client.sendEvent(Event.CHAT_RECEIVED, "发送成功");
        } else {
            client.sendEvent(Event.CHAT_REFUSED, "发送失败，对方不想理你");
        }
    }

    @OnEvent(value = Event.GROUP)
    public void onGroupEvent(SocketIOClient client, AckRequest request, GroupMessageRequest data) {
        Collection<SocketIOClient> clients = server.getRoomOperations(data.getGroupId()).getClients();

        boolean inGroup = false;
        for (SocketIOClient socketIOClient : clients) {
            if (ObjectUtil.equal(socketIOClient.getSessionId(), client.getSessionId())) {
                inGroup = true;
                break;
            }
        }
        if (inGroup) {
            log.info("群号 {} 收到来自 {} 的群聊消息：{}", data.getGroupId(), data.getFromUid(), data.getMessage());
            sendToGroup(data);
        } else {
            request.sendAckData("请先加群！");
        }
    }

    /**
     * 单聊
     */
    public void sendToSingle(UUID sessionId, SingleMessageRequest message) {
        server.getClient(sessionId).sendEvent(Event.CHAT, message);
    }

    /**
     * 广播
     */
    public void sendToBroadcast(BroadcastMessageRequest message) {
        log.info("系统紧急广播一条通知：{}", message.getMessage());
        for (UUID clientId : dbTemplate.findAll()) {
            if (server.getClient(clientId) == null) {
                continue;
            }
            server.getClient(clientId).sendEvent(Event.BROADCAST, message);
        }
    }

    /**
     * 群聊
     */
    public void sendToGroup(GroupMessageRequest message) {
        server.getRoomOperations(message.getGroupId()).sendEvent(Event.GROUP, message);
    }
}
