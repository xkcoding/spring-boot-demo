package com.xkcoding.springbootdemohelloworld;

import com.google.common.collect.*;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@SpringBootApplication
@Configuration
public class SpringBootDemoHelloworldApplication {
	@Value("${spring.boot.demo.helloworld.data.version}")
	private String version;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoHelloworldApplication.class, args);
	}

	@GetMapping("/hello")
	public Map sayHello(@Value("${author}") String author, @Value("${exclusions}") String exclusions, @Value("${connectionProperties}") String connectionProperties) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("ret", true);
		result.put("msg", StrUtil.format("hello,world,{}", author));
		Map<String, Object> data = Maps.newHashMap();
		data.put("version", version);
		data.put("exclusions", exclusions.split(","));
		Map<String, Object> connectionProperty = Maps.newHashMap();
		for (String connection : connectionProperties.split(";")) {
			String[] conn = connection.split("=");
			connectionProperty.put(conn[0], conn[1]);
		}
		data.put("connectionProperties", connectionProperty);
		result.put("data", data);
		return result;
	}
}
