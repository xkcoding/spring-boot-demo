package com.xkcoding.springbootdemodubboprovider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xkcoding.springbootdemodubboapi.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * <p>
 * HelloServiceImpl
 * </p>
 *
 * @package: com.xkcoding.springbootdemodubboprovider.service.impl
 * @description： HelloServiceImpl
 * @author: yangkai.shen
 * @date: Created in 2018/4/17 下午5:24
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Service
@Slf4j
public class HelloServiceImpl implements HelloService {
	@Value("${server.port}")
	private Integer port;

	@Override
	public String hello(String name) {
		log.info("Hello, {}, from port: {}",name, port);
		return "Hello, " + name + ", from port: " + port;
	}
}
