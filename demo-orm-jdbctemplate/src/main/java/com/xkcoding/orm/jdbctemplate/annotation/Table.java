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
 * @package: com.xkcoding.orm.jdbctemplate.annotation
 * @description: 表注解
 * @author: yangkai.shen
 * @date: Created in 2018/10/15 11:23 AM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
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
