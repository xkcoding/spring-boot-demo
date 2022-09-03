package com.xkcoding.distributed.lock.autoconfigure;

import com.xkcoding.distributed.lock.api.DistributedLock;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 基于 Curator 实现的分布式锁
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-03 13:03
 */
public class CuratorDistributedLock extends DistributedLock {
    private final CuratorFramework curatorFramework;
    private final InterProcessMutex mutex;
    private static final String ROOT_PATH = "/locks";

    protected CuratorDistributedLock(CuratorFramework curatorFramework, String lockKey, long lockTime, TimeUnit timeUnit) {
        super(lockKey, lockTime, timeUnit);
        this.curatorFramework = curatorFramework;
        mutex = new InterProcessMutex(curatorFramework, ROOT_PATH + "/" + lockKey);
    }

    @Override
    public void lock() {
        try {
            mutex.acquire();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        try {
            return mutex.acquire(time, unit);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void unlock() {
        try {
            mutex.release();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
