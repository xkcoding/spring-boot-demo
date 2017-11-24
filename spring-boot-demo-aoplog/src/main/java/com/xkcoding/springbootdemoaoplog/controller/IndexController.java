package com.xkcoding.springbootdemoaoplog.controller;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * IndexController
 *
 * @package: com.xkcoding.springbootdemoaoplog.controller
 * @description：IndexController
 * @author: yangkai.shen
 * @date: Created in 2017/11/24 上午9:36
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Slf4j
@RestController
public class IndexController {

	@GetMapping({"", ""})
	public String index() {
		return "index";
	}

	@GetMapping({"/test"})
	public Map test(@RequestParam String name) {
		ConcurrentMap<String, Object> ret = Maps.newConcurrentMap();
		ret.put("name", name);
		return ret;
	}
}
