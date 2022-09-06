package com.xkcoding.orm.jdbctemplate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 表注解
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-10-15 11:23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Table {
    /**
     * 表名
     *
     * @return 表名
     */
    String name();
}
