package com.xkcoding.dubbo.provider;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * 启动器
 * </p>
 *
 * @package: com.xkcoding.dubbo.provider
 * @description: 启动器
 * @author: yangkai.shen
 * @date: Created in 2018-12-25 16:49
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@EnableDubboConfiguration
@SpringBootApplication
public class SpringBootDemoDubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoDubboProviderApplication.class, args);
    }
}
