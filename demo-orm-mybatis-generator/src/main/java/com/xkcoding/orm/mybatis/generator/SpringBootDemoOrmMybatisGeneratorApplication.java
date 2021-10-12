package com.xkcoding.orm.mybatis.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * 启动类
 * </p>
 *
 * @author qilihui
 * @date 2021/10/11 9:50 下午
 */
@MapperScan(basePackages = {"com.xkcoding.orm.mybatis.generator.mapper"})
@SpringBootApplication
public class SpringBootDemoOrmMybatisGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoOrmMybatisGeneratorApplication.class, args);
    }
}
