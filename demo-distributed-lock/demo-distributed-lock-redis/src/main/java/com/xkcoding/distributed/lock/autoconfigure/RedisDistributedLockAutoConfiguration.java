package com.xkcoding.distributed.lock.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * <p>
 * 基于 RedisTemplate 分布式锁自动装配类
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-03 15:43
 */
@Configuration(proxyBeanMethods = false)
public class RedisDistributedLockAutoConfiguration {

    @Bean
    public RedisDistributedLockClient distributedLockClient(StringRedisTemplate redisTemplate) {
        return new RedisDistributedLockClient(redisTemplate);
    }

}
