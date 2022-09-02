package com.xkcoding.distributed.lock.api.impl;

import com.xkcoding.distributed.lock.api.DistributedLock;
import com.xkcoding.distributed.lock.api.DistributedLockClient;
import com.xkcoding.distributed.lock.api.DistributedLockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * <p>
 * 分布式锁实现
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-02 21:41
 */
@Slf4j
@RequiredArgsConstructor
public class DistributedLockServiceImpl implements DistributedLockService {
    private final DistributedLockClient distributedLockClient;

    /**
     * 锁
     *
     * @param lockKey  锁
     * @param timeout  超时时间
     * @param timeUnit 超时单位
     * @param execute  执行逻辑
     * @return 返回值
     */
    @Override
    public <T> T lock(String lockKey, long timeout, TimeUnit timeUnit, Supplier<T> execute) {
        DistributedLock lock = distributedLockClient.getLock(lockKey, timeout, timeUnit);
        lock.lock();

        try {
            return execute.get();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        } finally {
            lock.unlock();
        }
    }
}
