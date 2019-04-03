package com.xkcoding.social.config;

import lombok.Data;

/**
 * <p>
 * QQ 配置
 * </p>
 *
 * @package: com.xkcoding.social.config
 * @description: QQ 配置
 * @author: yangkai.shen
 * @date: Created in 2019-02-21 14:20
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
public class QQProperties {
    /**
     * 第三方应用标识
     */
    private String providerId = "qq";

    /**
     * clientId
     */
    private String clientId;

    /**
     * clientSecret
     */
    private String clientSecret;
}
