package com.example.awaysuse.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Data
@ConfigurationProperties(prefix="custom")
public class IndexConfig {

    private Map<String, String> index;

}
