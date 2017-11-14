package com.xkcoding.springbootdemoproperties.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "application.author")
@Component
public class AuthorConfig {
	private String name;
	private String website;
	private String qq;
	private String phoneNumber;
}
