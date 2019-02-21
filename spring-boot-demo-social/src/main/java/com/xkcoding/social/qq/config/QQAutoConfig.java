package com.xkcoding.social.qq.config;

import com.xkcoding.social.config.QQProperties;
import com.xkcoding.social.config.SocialProperties;
import com.xkcoding.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;

/**
 * <p>
 * QQ社交登录自动装配类
 * </p>
 *
 * @package: com.xkcoding.social.qq.config
 * @description: QQ社交登录自动装配类
 * @author: yangkai.shen
 * @date: Created in 2019-02-21 14:08
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Configuration
@ConditionalOnProperty(prefix = "demo.social.qq", name = "clientId")
public class QQAutoConfig extends SocialConfigurerAdapter {
    @Autowired
    private SocialProperties socialProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        QQProperties qq = socialProperties.getQq();
        QQConnectionFactory qqConnectionFactory = new QQConnectionFactory(qq.getProviderId(), qq.getClientId(), qq.getClientSecret());
        connectionFactoryConfigurer.addConnectionFactory(qqConnectionFactory);
        super.addConnectionFactories(connectionFactoryConfigurer, environment);
    }
}
