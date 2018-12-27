package com.xkcoding.zookeeper.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Zookeeper分布式锁工具类
 * </p>
 *
 * @package: com.xkcoding.zookeeper.util
 * @description: Zookeeper分布式锁工具类
 * @author: yangkai.shen
 * @date: Created in 2018-12-27 14:55
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
@Component
public class ZkLockHelper {
    private final CuratorFramework zkClient;

    @Autowired
    public ZkLockHelper(CuratorFramework zkClient) {
        this.zkClient = zkClient;
    }

    /**
     * 尝试获取锁
     *
     * @param key     锁的键
     * @param timeout 超时时间
     * @param unit    单位
     * @return 是否获取锁
     */
    public boolean tryLock(String key, long timeout, TimeUnit unit) {
        InterProcessMutex lock = new InterProcessMutex(zkClient, key);
        try {
            return lock.acquire(timeout, unit);
        } catch (Exception e) {
            throw new RuntimeException("系统异常");
        }
    }

    /**
     * 释放锁
     *
     * @param key 锁的键
     */
    public void unLock(String key) {
        InterProcessMutex lock = new InterProcessMutex(zkClient, key);
        try {
            lock.release();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
