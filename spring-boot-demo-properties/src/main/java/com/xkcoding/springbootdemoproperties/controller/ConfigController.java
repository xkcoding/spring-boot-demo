package com.xkcoding.springbootdemoproperties.controller;

import com.google.common.collect.Maps;
import com.xkcoding.springbootdemoproperties.config.ApplicationConfig;
import com.xkcoding.springbootdemoproperties.config.AuthorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/config")
public class ConfigController {
	@Autowired
	private ApplicationConfig applicationConfig;
	@Autowired
	private AuthorConfig authorConfig;

	@GetMapping({"", "/", "/index"})
	public Map index() {
		HashMap<Object, Object> ret = Maps.newHashMap();
		ret.put("applicationConfig", applicationConfig);
		ret.put("authorConfig", authorConfig);
		return ret;
	}
}
