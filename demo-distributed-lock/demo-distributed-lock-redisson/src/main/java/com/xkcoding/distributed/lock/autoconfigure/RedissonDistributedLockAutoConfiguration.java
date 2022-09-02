package com.xkcoding.distributed.lock.autoconfigure;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 基于Redisson分布式锁自动装配类
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-03 01:12
 */
@Configuration(proxyBeanMethods = false)
public class RedissonDistributedLockAutoConfiguration {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);
    }

    @Bean
    public RedissonDistributedLockClient distributedLockClient(RedissonClient redissonClient) {
        return new RedissonDistributedLockClient(redissonClient);
    }
}
