package com.xkcoding.properties.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 开发人员信息配置类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2022-08-12 21:50
 */
@Data
@ConfigurationProperties(prefix = "developer")
@Component
public class DeveloperProperty {
    private String name;
    private String website;
    private String email;
}
