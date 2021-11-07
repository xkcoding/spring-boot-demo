package com.xkcoding.task.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * <p>
 * 启动器
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-23 20:33
 */
@MapperScan(basePackages = {"com.xkcoding.task.quartz.mapper"})
@SpringBootApplication
public class SpringBootDemoTaskQuartzApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoTaskQuartzApplication.class, args);
    }
}
