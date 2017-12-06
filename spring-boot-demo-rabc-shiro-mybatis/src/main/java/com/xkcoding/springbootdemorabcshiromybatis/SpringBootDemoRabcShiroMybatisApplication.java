package com.xkcoding.springbootdemorabcshiromybatis;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.xkcoding.springbootdemorabcshiromybatis.dao"})
@Slf4j
public class SpringBootDemoRabcShiroMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoRabcShiroMybatisApplication.class, args);
		log.info("SpringBootDemoRabcShiroMybatisApplication 启动成功。。。。");
	}
}
