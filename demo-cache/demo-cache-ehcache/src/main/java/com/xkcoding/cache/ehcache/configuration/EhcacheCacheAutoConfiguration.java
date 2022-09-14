package com.xkcoding.cache.ehcache.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * ehcache 缓存自动装配
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2022-09-07 14:03
 */
@EnableCaching
@Configuration(proxyBeanMethods = false)
public class EhcacheCacheAutoConfiguration {

}
