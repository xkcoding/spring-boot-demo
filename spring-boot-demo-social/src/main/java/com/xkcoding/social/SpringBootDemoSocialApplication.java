package com.xkcoding.social;

import com.xkcoding.social.config.SocialProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * <p>
 * 启动器
 * </p>
 *
 * @package: com.xkcoding.social
 * @description: 启动器
 * @author: yangkai.shen
 * @date: Created in 2019-02-19 16:04
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@SpringBootApplication
public class SpringBootDemoSocialApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoSocialApplication.class, args);
    }

}

