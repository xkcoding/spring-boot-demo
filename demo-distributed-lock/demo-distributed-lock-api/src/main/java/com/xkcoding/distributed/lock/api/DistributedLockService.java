package com.xkcoding.distributed.lock.api;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * <p>
 * 分布式锁实现
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-02 21:11
 */
public interface DistributedLockService {

    /**
     * 锁
     *
     * @param lockKey  锁的标识
     * @param lockTime 锁的时间
     * @param timeUnit 锁的时间单位
     * @param execute  执行逻辑
     * @param <T>      返回值类型
     * @return 返回值
     */
    <T> T lock(String lockKey, long lockTime, TimeUnit timeUnit, Supplier<T> execute);
}
