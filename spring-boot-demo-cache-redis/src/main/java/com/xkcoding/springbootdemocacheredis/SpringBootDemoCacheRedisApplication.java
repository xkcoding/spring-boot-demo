package com.xkcoding.springbootdemocacheredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringBootDemoCacheRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoCacheRedisApplication.class, args);
	}
}
