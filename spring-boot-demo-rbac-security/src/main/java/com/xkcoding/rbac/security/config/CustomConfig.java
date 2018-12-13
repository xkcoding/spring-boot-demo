package com.xkcoding.rbac.security.config;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * <p>
 * 自定义配置
 * </p>
 *
 * @package: com.xkcoding.rbac.security.config
 * @description: 自定义配置
 * @author: yangkai.shen
 * @date: Created in 2018-12-13 10:56
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@ConfigurationProperties(prefix = "custom.config")
@Data
public class CustomConfig {
    /**
     * 不需要拦截的地址
     */
    private List<String> ignores = Lists.newArrayList();
}
