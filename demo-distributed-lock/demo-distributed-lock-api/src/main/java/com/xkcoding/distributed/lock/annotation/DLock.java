package com.xkcoding.distributed.lock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 分布式锁注解
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-02 15:47
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
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

    /**
     * @return 快速失败，true: 限流，拿不到锁，直接失败；false: 不限流，接收所有请求，阻塞执行
     */
    boolean fastFail() default false;
}
