package com.xkcoding.springbootdemotemplatebeetl.controller;

import com.google.common.collect.Lists;
import com.xkcoding.springbootdemotemplatebeetl.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <p>
 * 首页 Controller
 * </p>
 *
 * @package: com.xkcoding.springbootdemotemplatebeetl.controller
 * @description： 首页 Controller
 * @author: yangkai.shen
 * @date: Created in 2018/4/26 下午4:36
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Controller
public class HomeController {

	@GetMapping({"", "/", "/index"})
	public ModelAndView index() {
		ModelAndView view = new ModelAndView("/index.btl");
		User admin = new User(0, "admin", "phone0",true);
		List<User> userList = Lists.newArrayList(new User(1, "user1", "phone1",false), new User(2, "user2", "phone2",true), new User(3, "user3", "phone3",true),new User(4, "user4", "phone4",false));

		view.addObject("admin",admin);
		view.addObject("userList",userList);
		return view;
	}
}
