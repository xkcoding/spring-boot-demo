package com.xkcoding.admin.server;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * 启动类
 * </p>
 *
 * @package: com.xkcoding.admin.server
 * @description: 启动类
 * @author: yangkai.shen
 * @date: Created in 2018/10/8 2:08 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@EnableAdminServer
@SpringBootApplication
public class SpringBootDemoAdminServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoAdminServerApplication.class, args);
	}
}
