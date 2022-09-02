package com.xkcoding.distributed.lock.autoconfigure;

import com.xkcoding.distributed.lock.api.DistributedLock;
import com.xkcoding.distributed.lock.api.DistributedLockClient;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 获取一把 Redisson 分布式锁
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-03 01:21
 */
@RequiredArgsConstructor
public class RedissonDistributedLockClient implements DistributedLockClient {
    private final RedissonClient redissonClient;

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
        return new RedissonDistributedLock(redissonClient, lockKey, lockTime, timeUnit);
    }
}
