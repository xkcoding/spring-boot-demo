package com.xkcoding.distributed.lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * 启动器
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2022-09-05 19:34
 */
@SpringBootApplication
public class ZookeeperDistributedLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZookeeperDistributedLockApplication.class, args);
    }

}
