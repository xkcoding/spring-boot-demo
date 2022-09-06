package com.xkcoding.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>
 * 启动器
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-14 14:58
 */
@SpringBootApplication
@EnableScheduling
public class SpringBootDemoWebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoWebsocketApplication.class, args);
    }

}

