package com.xkcoding.distributed.lock.annotation;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 分布式锁注解
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-02 15:47
 */
public @interface DLock {
    /**
     * @return 锁的标识，支持 spel 表达式
     */
    String lockKey() default "lock";

    /**
     * @return 锁的时间
     */
    long lockTime() default 3000;

    /**
     * @return 锁的时间单位
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
