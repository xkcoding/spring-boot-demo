package com.xkcoding.springbootdemoproperties.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ApplicationConfig {
	@Value("${application.name}")
	private String name;
	@Value("${application.version}")
	private String version;
}
