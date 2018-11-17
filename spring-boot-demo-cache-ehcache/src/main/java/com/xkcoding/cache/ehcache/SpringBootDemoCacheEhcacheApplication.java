package com.xkcoding.cache.ehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * <p>
 * 启动类
 * </p>
 *
 * @package: com.xkcoding.cache.ehcache
 * @description: 启动类
 * @author: yangkai.shen
 * @date: Created in 2018/11/16 17:02
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@SpringBootApplication
@EnableCaching
public class SpringBootDemoCacheEhcacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoCacheEhcacheApplication.class, args);
    }
}
