package com.xkcoding.springbootdemorabcshiromybatis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 数据初始化，实现 CommandLineRunner 接口，启动 springboot 后自动执行，如果有多个这个类可以根据 @Order 来指定执行顺序
 * </p>
 *
 * @package: com.xkcoding.springbootdemorabcshiromybatis
 * @description： 数据初始化
 * @author: yangkai.shen
 * @date: Created in 2017/11/29 下午4:32
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Component
@Slf4j
public class DBInitConfig implements CommandLineRunner {

	@Override
	public void run(String... strings) throws Exception {
		log.info("正在初始化数据。。。");
		log.info("数据初始化完成。。。");
	}

}