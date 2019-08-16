package com.xkcoding.template.beetl.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xkcoding.template.beetl.model.User;

import cn.hutool.core.util.ObjectUtil;

/**
 * <p>
 * 主页
 * </p>
 *
 * @package: com.xkcoding.template.beetl.controller
 * @description: 主页
 * @author: yangkai.shen
 * @date: Created in 2018/10/10 11:17 AM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Controller
public class IndexController {

	@GetMapping(value = {"", "/"})
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		User user = (User) request.getSession().getAttribute("user");
		if (ObjectUtil.isNull(user)) {
			mv.setViewName("redirect:/user/login");
		} else {
			mv.setViewName("page/index.btl");
			mv.addObject(user);
		}

		return mv;
	}
}
