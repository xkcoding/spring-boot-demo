package com.xkcoding.template.thymeleaf.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xkcoding.template.thymeleaf.model.User;

/**
 * <p>
 * 用户页面
 * </p>
 *
 * @package: com.xkcoding.template.thymeleaf.controller
 * @description: 用户页面
 * @author: yangkai.shen
 * @date: Created in 2018/10/10 10:11 AM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@PostMapping("/login")
	public ModelAndView login(User user, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		mv.addObject(user);
		mv.setViewName("redirect:/");

		request.getSession().setAttribute("user", user);
		return mv;
	}

	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("page/login");
	}
}
