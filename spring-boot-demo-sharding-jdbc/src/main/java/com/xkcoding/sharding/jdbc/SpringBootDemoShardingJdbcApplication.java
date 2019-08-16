package com.xkcoding.sharding.jdbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>
 * 启动器
 * </p>
 *
 * @package: com.xkcoding.sharding.jdbc
 * @description: 启动器
 * @author: yangkai.shen
 * @date: Created in 2019-01-23 22:05
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan("com.xkcoding.sharding.jdbc.mapper")
public class SpringBootDemoShardingJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoShardingJdbcApplication.class, args);
    }

}

