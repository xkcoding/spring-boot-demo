package com.xkcoding.distributed.lock.api;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * <p>
 * 分布式锁接口
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-02 21:30
 */
public abstract class DistributedLock implements Lock {
    /**
     * 锁的标识
     */
    private final String lockKey;
    /**
     * 锁的时间
     */
    private final long lockTime;
    /**
     * 锁的时间单位
     */
    private final TimeUnit timeUnit;

    protected DistributedLock(String lockKey, long lockTime, TimeUnit timeUnit) {
        this.lockKey = lockKey;
        this.lockTime = lockTime;
        this.timeUnit = timeUnit;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException("DistributedLock`s lockInterruptibly method is unsupported");
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException("DistributedLock`s newCondition method is unsupported");
    }
}
