package com.xkcoding.springbootdemoexceptionhandler.controller;

import com.xkcoding.springbootdemoexceptionhandler.domain.R;
import com.xkcoding.springbootdemoexceptionhandler.exception.DemoJsonException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 json 异常处理
 *
 * @package: com.xkcoding.springbootdemoexceptionhandler.controller
 * @description： 测试 json 异常处理
 * @author: yangkai.shen
 * @date: Created in 2017/11/24 下午2:08
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@RestController
@RequestMapping("/json")
public class JsonController {

	@GetMapping({"", "/"})
	public R index() {
		throw new DemoJsonException(501, "DemoJsonException");
	}
}
