package com.xkcoding.springbootdemoormmybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.xkcoding.springbootdemoormmybatis.mapper"})
public class SpringBootDemoOrmMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoOrmMybatisApplication.class, args);
	}
}
