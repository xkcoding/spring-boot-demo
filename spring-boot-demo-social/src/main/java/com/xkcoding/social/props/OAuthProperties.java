package com.xkcoding.social.props;

import lombok.Data;
import me.zhyd.oauth.config.AuthConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 第三方登录配置
 * </p>
 *
 * @package: com.xkcoding.oauth.config.props
 * @description: 第三方登录配置
 * @author: yangkai.shen
 * @date: Created in 2019-05-17 15:33
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
@Component
@ConfigurationProperties(prefix = "oauth")
public class OAuthProperties {
    /**
     * QQ 配置
     */
    private AuthConfig qq;

    /**
     * github 配置
     */
    private AuthConfig github;

    /**
     * 微信 配置
     */
    private AuthConfig wechat;

    /**
     * Google 配置
     */
    private AuthConfig google;

    /**
     * Microsoft 配置
     */
    private AuthConfig microsoft;
}
