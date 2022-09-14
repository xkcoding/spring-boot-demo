package com.xkcoding.cache.caffeine.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * caffeine 缓存自动装配
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2022-09-14 15:48
 */
@EnableCaching
@Configuration(proxyBeanMethods = false)
public class CaffeineCacheAutoConfiguration {

}
