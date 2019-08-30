package com.xkcoding.multi.datasource.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * 启动器
 * </p>
 *
 * @package: com.xkcoding.multi.datasource.mybatis
 * @description: 启动器
 * @author: yangkai.shen
 * @date: Created in 2019-01-21 14:19
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@SpringBootApplication
@MapperScan(basePackages = "com.xkcoding.multi.datasource.mybatis.mapper")
public class SpringBootDemoMultiDatasourceMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoMultiDatasourceMybatisApplication.class, args);
    }

}

