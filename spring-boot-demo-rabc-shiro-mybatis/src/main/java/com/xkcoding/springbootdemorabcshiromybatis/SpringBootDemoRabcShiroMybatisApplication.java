package com.xkcoding.springbootdemorabcshiromybatis;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@MapperScan(basePackages = {"com.xkcoding.springbootdemorabcshiromybatis.dao"})
@Slf4j
@Controller
public class SpringBootDemoRabcShiroMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoRabcShiroMybatisApplication.class, args);
		log.info("SpringBootDemoRabcShiroMybatisApplication 启动成功。。。。");
	}

	@GetMapping("/login.page")
	public String index() {
		return "index";
	}
}
