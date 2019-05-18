package com.xkcoding.oauth.config.props;

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
     * github 配置
     */
    private CommonProperties github;
    private CommonProperties wechat;
}
