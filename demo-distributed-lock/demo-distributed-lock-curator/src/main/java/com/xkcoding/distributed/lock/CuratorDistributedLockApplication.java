package com.xkcoding.distributed.lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * 启动器
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-03 13:00
 */
@SpringBootApplication
public class CuratorDistributedLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(CuratorDistributedLockApplication.class, args);
    }

}
