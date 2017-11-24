package com.xkcoding.springbootdemoexceptionhandler.controller;

import com.xkcoding.springbootdemoexceptionhandler.exception.DemoPageException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 测试 page 异常处理
 *
 * @package: com.xkcoding.springbootdemoexceptionhandler.controller
 * @description： 测试 page 异常处理
 * @author: yangkai.shen
 * @date: Created in 2017/11/24 下午2:29
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Controller
@RequestMapping("/page")
public class PageController {

	@GetMapping({"", "/"})
	public ModelAndView index() {
		throw new DemoPageException(600, "DemoPageException");
	}
}
