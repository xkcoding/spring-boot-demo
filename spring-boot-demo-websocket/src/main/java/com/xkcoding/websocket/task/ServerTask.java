package com.xkcoding.websocket.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.xkcoding.websocket.common.WebSocketConsts;
import com.xkcoding.websocket.model.Server;
import com.xkcoding.websocket.payload.ServerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>
 * 服务器定时推送任务
 * </p>
 *
 * @package: com.xkcoding.websocket.task
 * @description: 服务器定时推送任务
 * @author: yangkai.shen
 * @date: Created in 2018-12-14 16:04
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
@Component
public class ServerTask {
    @Autowired
    private SimpMessagingTemplate wsTemplate;

    /**
     * 按照标准时间来算，每隔 10s 执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void websocket() throws Exception {
        log.info("【推送消息】开始执行：{}", DateUtil.formatDateTime(new Date()));
        // 查询服务器状态
        Server server = new Server();
        server.copyTo();
        ServerVO serverVO = new ServerVO();
        serverVO.create(server);
        wsTemplate.convertAndSend(WebSocketConsts.PUSH_SERVER, JSONUtil.toJsonStr(serverVO));
        log.info("【推送消息】执行结束：{}", DateUtil.formatDateTime(new Date()));
    }
}
