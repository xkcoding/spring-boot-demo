package com.xkcoding.orm.jpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>
 * JPA配置类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-07 11:05
 */
@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.xkcoding.orm.jpa.repository")
public class JpaConfiguration {

}
