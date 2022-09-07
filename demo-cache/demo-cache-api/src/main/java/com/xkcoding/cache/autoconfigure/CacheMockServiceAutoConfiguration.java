package com.xkcoding.cache.autoconfigure;

import com.xkcoding.cache.api.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 缓存 mock 自动装配
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-07 14:31
 */
@Configuration(proxyBeanMethods = false)
public class CacheMockServiceAutoConfiguration {
    @Bean
    public UserService userService() {
        return new UserService();
    }
}
