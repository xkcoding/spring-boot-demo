package com.xkcoding.cache.ehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * <p>
 * 启动类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-16 17:02
 */
@SpringBootApplication
@EnableCaching
public class SpringBootDemoCacheEhcacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoCacheEhcacheApplication.class, args);
    }
}
