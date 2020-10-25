package com.xkcoding.ratelimit.guava.handler;

import cn.hutool.core.lang.Dict;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 * 全局异常拦截
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-09-12 15:00
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public Dict handler(RuntimeException ex) {
        return Dict.create().set("msg", ex.getMessage());
    }
}
