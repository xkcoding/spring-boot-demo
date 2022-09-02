package com.xkcoding.distributed.lock.autoconfigure;

import com.xkcoding.distributed.lock.api.DistributedLock;
import org.jetbrains.annotations.NotNull;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Redisson分布式锁实现
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-03 01:18
 */
public class RedissonDistributedLock extends DistributedLock {
    private final RedissonClient redissonClient;

    protected RedissonDistributedLock(RedissonClient redissonClient, String lockKey, long lockTime, TimeUnit timeUnit) {
        super(lockKey, lockTime, timeUnit);
        this.redissonClient = redissonClient;
    }

    @Override
    public void lock() {
        redissonClient.getLock(lockKey).lock();
    }

    @Override
    public boolean tryLock() {
        try {
            return tryLock(lockTime, timeUnit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public boolean tryLock(long time, @NotNull TimeUnit unit) throws InterruptedException {
        return redissonClient.getLock(lockKey).tryLock(lockTime, timeUnit);
    }

    @Override
    public void unlock() {
        redissonClient.getLock(lockKey).unlock();
    }
}
