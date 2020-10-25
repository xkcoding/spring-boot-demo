package com.xkcoding.admin.server;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * 启动类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-10-08 14:08
 */
@EnableAdminServer
@SpringBootApplication
public class SpringBootDemoAdminServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoAdminServerApplication.class, args);
	}
}
