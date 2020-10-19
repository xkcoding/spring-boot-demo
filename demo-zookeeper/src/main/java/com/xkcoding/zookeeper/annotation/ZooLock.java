package com.xkcoding.zookeeper.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 基于Zookeeper的分布式锁注解
 * 在需要加锁的方法上打上该注解后，AOP会帮助你统一管理这个方法的锁
 * </p>
 *
 * @package: com.xkcoding.zookeeper.annotation
 * @description: 基于Zookeeper的分布式锁注解，在需要加锁的方法上打上该注解后，AOP会帮助你统一管理这个方法的锁
 * @author: yangkai.shen
 * @date: Created in 2018-12-27 14:11
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ZooLock {
    /**
     * 分布式锁的键
     */
    String key();

    /**
     * 锁释放时间，默认五秒
     */
    long timeout() default 5 * 1000;

    /**
     * 时间格式，默认：毫秒
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
