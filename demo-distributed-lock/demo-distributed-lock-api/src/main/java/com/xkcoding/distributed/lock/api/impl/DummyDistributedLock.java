package com.xkcoding.distributed.lock.api.impl;

import com.xkcoding.distributed.lock.api.DistributedLock;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 无锁实现
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-02 21:32
 */
public class DummyDistributedLock extends DistributedLock {

    protected DummyDistributedLock(String lockKey, long lockTime, TimeUnit timeUnit) {
        super(lockKey, lockTime, timeUnit);
    }

    @Override
    public void lock() {
        // Do nothing.
    }

    @Override
    public boolean tryLock() {
        return true;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return true;
    }

    @Override
    public void unlock() {

    }
}
