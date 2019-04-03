package com.xkcoding.war;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * <p>
 * 启动器
 * </p>
 *
 * @package: com.xkcoding.war
 * @description: 启动器
 * @author: shenyangkai
 * @date: Created in 2018/10/30 19:37
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: shenyangkai
 */
@SpringBootApplication
public class SpringBootDemoWarApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoWarApplication.class, args);
    }

    /**
     * 若需要打成 war 包，则需要写一个类继承 {@link SpringBootServletInitializer} 并重写 {@link SpringBootServletInitializer#configure(SpringApplicationBuilder)}
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootDemoWarApplication.class);
    }
}
