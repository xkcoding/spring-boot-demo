package com.xkcoding.distributed.lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * 启动器
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-05 21:55
 */
@SpringBootApplication
public class MysqlDistributedLockApplication {
    public static void main(String[] args) {
        SpringApplication.run(MysqlDistributedLockApplication.class, args);
    }
}
