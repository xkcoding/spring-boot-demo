package com.xkcoding.springbootdemodubboconsumer.controller;

import com.xkcoding.springbootdemodubboconsumer.service.GreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * GreetController
 * </p>
 *
 * @package: com.xkcoding.springbootdemodubboconsumer.controller
 * @description： GreetController
 * @author: yangkai.shen
 * @date: Created in 2018/4/17 下午5:29
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@RestController
@RequestMapping("/greet")
public class GreetController {
	@Autowired
	private GreetService greetService;

	@GetMapping("")
	public String hello(@RequestParam String name) {
		return greetService.greeting(name);
	}
}
