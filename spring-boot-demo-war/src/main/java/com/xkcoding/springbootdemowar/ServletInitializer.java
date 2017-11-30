package com.xkcoding.springbootdemowar;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 新建一个 ServletInitializer 类，继承 SpringBootServletInitializer
 * <p>打成 war 包修改的第 ③ 处</p>
 * @package: com.xkcoding.springbootdemowar
 * @description： 新建一个 ServletInitializer 类，继承 SpringBootServletInitializer
 * @author: yangkai.shen
 * @date: Created in 2017/11/30 下午1:26
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootDemoWarApplication.class);
	}

}
