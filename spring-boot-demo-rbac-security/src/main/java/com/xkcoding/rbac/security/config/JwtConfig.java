package com.xkcoding.rbac.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * JWT 配置
 * </p>
 *
 * @package: com.xkcoding.rbac.security.config
 * @description: JWT 配置
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 13:42
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@ConfigurationProperties(prefix = "jwt.config")
@Data
public class JwtConfig {
    /**
     * jwt 加密 key, 默认值：xkcoding.
     */
    private String key = "xkcoding";

    /**
     * jwt 过期时间, 默认值：600000 {@code 10 分钟}.
     */
    private Long ttl = 600000L;
}
