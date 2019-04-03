package com.xkcoding.admin.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 首页
 * </p>
 *
 * @package: com.xkcoding.admin.client.controller
 * @description: 首页
 * @author: yangkai.shen
 * @date: Created in 2018/10/8 2:15 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@RestController
public class IndexController {
	@GetMapping(value = {"", "/"})
	public String index() {
		return "This is a Spring Boot Admin Client.";
	}
}
