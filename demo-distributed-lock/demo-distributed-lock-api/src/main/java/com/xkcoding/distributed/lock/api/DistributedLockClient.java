package com.xkcoding.distributed.lock.api;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 锁客户端
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-02 21:48
 */
public interface DistributedLockClient {
    /**
     * 获取一把锁
     *
     * @param lockKey  锁的标识
     * @param lockTime 锁的时间
     * @param timeUnit 锁的时间单位
     * @return 锁
     */
    DistributedLock getLock(String lockKey, long lockTime, TimeUnit timeUnit);
}
