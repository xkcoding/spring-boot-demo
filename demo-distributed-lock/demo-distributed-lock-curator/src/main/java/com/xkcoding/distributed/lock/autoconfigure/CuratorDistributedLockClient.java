package com.xkcoding.distributed.lock.autoconfigure;

import com.xkcoding.distributed.lock.api.DistributedLock;
import com.xkcoding.distributed.lock.api.DistributedLockClient;
import lombok.RequiredArgsConstructor;
import org.apache.curator.framework.CuratorFramework;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 获得一把 Curator 分布式锁
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-03 13:04
 */
@RequiredArgsConstructor
public class CuratorDistributedLockClient implements DistributedLockClient {
    private final CuratorFramework curatorFramework;

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
        return new CuratorDistributedLock(curatorFramework, lockKey, lockTime, timeUnit);
    }
}
