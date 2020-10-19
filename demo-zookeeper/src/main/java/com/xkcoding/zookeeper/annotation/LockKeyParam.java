package com.xkcoding.zookeeper.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 分布式锁动态key注解，配置之后key的值会动态获取参数内容
 * </p>
 *
 * @package: com.xkcoding.zookeeper.annotation
 * @description: 分布式锁动态key注解，配置之后key的值会动态获取参数内容
 * @author: yangkai.shen
 * @date: Created in 2018-12-27 14:17
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LockKeyParam {
    /**
     * 如果动态key在user对象中，那么就需要设置fields的值为user对象中的属性名可以为多个，基本类型则不需要设置该值
     * <p>例1：public void count(@LockKeyParam({"id"}) User user)
     * <p>例2：public void count(@LockKeyParam({"id","userName"}) User user)
     * <p>例3：public void count(@LockKeyParam String userId)
     */
    String[] fields() default {};
}
