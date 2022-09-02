package org.springframework.core;

import org.springframework.lang.Nullable;

import java.io.IOException;


/**
 * <p>
 * 1. springboot 3.x 版本依赖 spring-core 6.x，里面没有这个异常
 * 2. mybatis-plus 自动装配 {@see com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean} 又需要这个异常
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2022-09-03 00:42
 */
public class NestedIOException extends IOException {
    public NestedIOException(String msg) {
        super(msg);
    }

    public NestedIOException(@Nullable String msg, @Nullable Throwable cause) {
        super(msg, cause);
    }

    @Override
    @Nullable
    public String getMessage() {
        return NestedExceptionUtils.buildMessage(super.getMessage(), this.getCause());
    }

    static {
        NestedExceptionUtils.class.getName();
    }
}
