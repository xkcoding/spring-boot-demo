package com.xkcoding.distributed.lock.autoconfigure;

import com.xkcoding.distributed.lock.api.DistributedLock;
import com.xkcoding.distributed.lock.api.DistributedLockClient;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 获取一把 RedisTemplate 实现的分布式锁
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-03 12:35
 */
public class RedisDistributedLockClient implements DistributedLockClient {
    private final StringRedisTemplate redisTemplate;

    /**
     * 唯一标识前缀，用于集群环境下的主机标识，会在 Bean 初始化到 Spring 容器的时候设置
     */
    private final String uniqueIdPrefix;

    public RedisDistributedLockClient(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        uniqueIdPrefix = UUID.randomUUID().toString();
    }


    /**
     * 获取一把锁
     *
     * @param lockKey  锁的标识
     * @param lockTime 锁的时间
     * @param timeUnit 锁的时间单位
     * @return 锁
     */
    @Override
    public DistributedLock getLock(String lockKey, long lockTime, TimeUnit timeUnit) {
        return new RedisDistributedLock(redisTemplate, uniqueIdPrefix, lockKey, lockTime, timeUnit);
    }
}
