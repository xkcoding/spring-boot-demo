package com.xkcoding.springbootdemoswagger;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSwagger2Doc // 启用 Swagger
public class SpringBootDemoSwaggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoSwaggerApplication.class, args);
	}
}
