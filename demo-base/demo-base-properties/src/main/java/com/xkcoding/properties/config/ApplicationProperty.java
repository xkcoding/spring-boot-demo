package com.xkcoding.properties.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 项目信息配置类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2022-08-12 21:50
 */
@Data
@Component
public class ApplicationProperty {
    @Value("${application.name}")
    private String name;
    @Value("${application.version}")
    private String version;
}
