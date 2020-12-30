package com.xkcoding.websocket.socketio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * WebSocket配置类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-18 16:41
 */
@ConfigurationProperties(prefix = "ws.server")
@Data
public class WsConfig {
    /**
     * 端口号
     */
    private Integer port;

    /**
     * host
     */
    private String host;
}
