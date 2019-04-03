package com.xkcoding.orm.jdbctemplate;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * 启动类
 * </p>
 *
 * @package: com.xkcoding.orm.jdbctemplate
 * @description: 启动类
 * @author: yangkai.shen
 * @date: Created in 2018/10/15 9:50 AM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@SpringBootApplication
public class SpringBootDemoOrmJdbctemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoOrmJdbctemplateApplication.class, args);
	}
}
