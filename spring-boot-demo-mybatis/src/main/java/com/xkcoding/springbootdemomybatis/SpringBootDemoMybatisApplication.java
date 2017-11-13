package com.xkcoding.springbootdemomybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.xkcoding.springbootdemomybatis.mapper"})
public class SpringBootDemoMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoMybatisApplication.class, args);
	}
}
