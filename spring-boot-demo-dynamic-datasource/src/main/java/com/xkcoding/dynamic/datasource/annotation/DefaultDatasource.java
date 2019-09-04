package com.xkcoding.dynamic.datasource.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 默认数据源
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/9/4 17:37
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DefaultDatasource {
}
