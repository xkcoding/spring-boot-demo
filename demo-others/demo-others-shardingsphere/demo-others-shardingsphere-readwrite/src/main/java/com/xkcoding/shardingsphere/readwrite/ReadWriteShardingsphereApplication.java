package com.xkcoding.shardingsphere.readwrite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * 启动器 - 读写分离
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-27 11:52
 */
@SpringBootApplication
public class ReadWriteShardingsphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadWriteShardingsphereApplication.class, args);
    }

}
