package com.xkcoding.social.qq.connect;

import com.xkcoding.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * <p>
 * QQ 连接工厂
 * </p>
 *
 * @package: com.xkcoding.social.qq.connect
 * @description: QQ 连接工厂
 * @author: yangkai.shen
 * @date: Created in 2019-02-21 13:50
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    public QQConnectionFactory(String providerId, String clientId, String clientSecret) {
        super(providerId, new QQServiceProvider(clientId, clientSecret), new QQAdapter());
    }
}
