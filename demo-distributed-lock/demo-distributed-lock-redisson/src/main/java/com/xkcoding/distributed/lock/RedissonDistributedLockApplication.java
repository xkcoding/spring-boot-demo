package com.xkcoding.distributed.lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * 启动器
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-02 22:34
 */
@SpringBootApplication
public class RedissonDistributedLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedissonDistributedLockApplication.class, args);
    }

}
