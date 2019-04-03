package com.xkcoding.social.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * 社交登录配置类
 * </p>
 *
 * @package: com.xkcoding.social.config
 * @description: 社交登录配置类
 * @author: yangkai.shen
 * @date: Created in 2019-02-21 14:12
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
@ConfigurationProperties(prefix = "demo.social")
public class SocialProperties {
    private String filterProcessUrl = "/auth";

    private QQProperties qq = new QQProperties();
}
