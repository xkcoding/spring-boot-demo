package com.xkcoding.logback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 启动类
 * </p>
 *
 * @package: com.xkcoding.logback
 * @description: 启动类
 * @author: yangkai.shen
 * @date: Created in 2018/9/30 11:16 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@SpringBootApplication
@Slf4j
public class SpringBootDemoLogbackApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootDemoLogbackApplication.class, args);
		int length = context.getBeanDefinitionNames().length;
		log.trace("Spring boot启动初始化了 {} 个 Bean", length);
		log.debug("Spring boot启动初始化了 {} 个 Bean", length);
		log.info("Spring boot启动初始化了 {} 个 Bean", length);
		log.warn("Spring boot启动初始化了 {} 个 Bean", length);
		log.error("Spring boot启动初始化了 {} 个 Bean", length);
		try {
			int i = 0;
			int j = 1 / i;
			log.debug(j + "");
		} catch (Exception e) {
			log.error("【SpringBootDemoLogbackApplication】启动异常：", e);
		}
	}
}
