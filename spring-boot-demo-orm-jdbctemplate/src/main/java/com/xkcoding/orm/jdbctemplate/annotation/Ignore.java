package com.xkcoding.orm.jdbctemplate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 需要忽略的字段
 * </p>
 *
 * @package: com.xkcoding.orm.jdbctemplate.annotation
 * @description: 需要忽略的字段
 * @author: yangkai.shen
 * @date: Created in 2018/10/15 1:25 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Ignore {
}
