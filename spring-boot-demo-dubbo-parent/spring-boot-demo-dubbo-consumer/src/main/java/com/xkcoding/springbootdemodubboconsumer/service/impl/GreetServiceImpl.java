package com.xkcoding.springbootdemodubboconsumer.service.impl;

import com.reger.dubbo.annotation.Inject;
import com.xkcoding.springbootdemodubboapi.service.HelloService;
import com.xkcoding.springbootdemodubboconsumer.service.GreetService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * GreetServiceImpl
 * </p>
 *
 * @package: com.xkcoding.springbootdemodubboconsumer.service.impl
 * @description： GreetServiceImpl
 * @author: yangkai.shen
 * @date: Created in 2018/4/17 下午5:31
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Service
public class GreetServiceImpl implements GreetService {
	@Inject
	private HelloService helloService;

	@Override
	public String greeting(String name) {
		return helloService.hello(name);
	}
}
