package com.xkcoding.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * 启动器.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @version V1.0
 * @date 2020-01-09 11:38
 */
@EnableResourceServer
@SpringBootApplication
public class SpringBootDemoResourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoResourceApplication.class, args);
    }

}
