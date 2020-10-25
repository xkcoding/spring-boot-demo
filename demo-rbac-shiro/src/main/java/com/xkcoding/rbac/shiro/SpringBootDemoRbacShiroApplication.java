package com.xkcoding.rbac.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * 启动器
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-03-21 16:11
 */
@SpringBootApplication
@MapperScan("com.xkcoding.rbac.shiro.mapper")
public class SpringBootDemoRbacShiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoRbacShiroApplication.class, args);
    }
}
