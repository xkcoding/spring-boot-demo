package com.xkcoding.springbootdemotemplatefreemarker.controller;

import com.xkcoding.springbootdemotemplatefreemarker.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户 Controller
 *
 * @package: com.xkcoding.springbootdemotemplatefreemarker.controller
 * @description： 用户 Controller
 * @author: yangkai.shen
 * @date: Created in 2017/11/26 下午6:47
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

	@PostMapping("/login")
	public ModelAndView login(User user, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		mv.addObject(user);
		mv.setViewName("home");

		request.getSession().setAttribute("user", user);
		return mv;
	}

	@GetMapping("/login")
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			mv.setViewName("redirect:/");
		} else {
			mv.setViewName("home");
		}

		return mv;
	}
}
