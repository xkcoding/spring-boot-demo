package com.xkcoding.orm.jdbctemplate.controller;

import cn.hutool.core.lang.Dict;
import com.xkcoding.orm.jdbctemplate.entity.User;
import com.xkcoding.orm.jdbctemplate.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * User Controller
 * </p>
 *
 * @package: com.xkcoding.orm.jdbctemplate.controller
 * @description: User Controller
 * @author: yangkai.shen
 * @date: Created in 2018/10/15 1:58 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@RestController
@Slf4j
public class UserController {
	private final IUserService userService;

	@Autowired
	public UserController(IUserService userService) {
		this.userService = userService;
	}

	@PostMapping("/user")
	public Dict save(@RequestBody User user) {
		Boolean save = userService.save(user);
		return Dict.create().set("code", save ? 200 : 500).set("msg", save ? "成功" : "失败").set("data", save ? user : null);
	}
}
