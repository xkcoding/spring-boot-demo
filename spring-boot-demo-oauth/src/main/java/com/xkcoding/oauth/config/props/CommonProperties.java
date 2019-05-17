package com.xkcoding.oauth.config.props;

import lombok.Data;

/**
 * <p>
 * 通用配置
 * </p>
 *
 * @package: com.xkcoding.oauth.config.props
 * @description: 通用配置
 * @author: yangkai.shen
 * @date: Created in 2019-05-17 16:02
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
public class CommonProperties {
    /**
     * clientId
     */
    private String clientId;
    /**
     * clientSecret
     */
    private String clientSecret;
    /**
     * 成功后的回调
     */
    private String redirectUri;
}
