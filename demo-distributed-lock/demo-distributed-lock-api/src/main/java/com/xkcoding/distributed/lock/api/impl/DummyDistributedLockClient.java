package com.xkcoding.distributed.lock.api.impl;

import com.xkcoding.distributed.lock.api.DistributedLock;
import com.xkcoding.distributed.lock.api.DistributedLockClient;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 获取一把虚拟锁
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-02 21:53
 */
public class DummyDistributedLockClient implements DistributedLockClient {
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
        return new DummyDistributedLock(lockKey, lockTime, timeUnit);
    }
}
