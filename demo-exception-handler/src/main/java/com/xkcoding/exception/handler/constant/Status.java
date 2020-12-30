package com.xkcoding.exception.handler.constant;

import lombok.Getter;

/**
 * <p>
 * 状态码封装
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-10-02 21:02
 */
@Getter
public enum Status {
    /**
     * 操作成功
     */
    OK(200, "操作成功"),

    /**
     * 未知异常
     */
    UNKNOWN_ERROR(500, "服务器出错啦");
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 内容
     */
    private String message;

    Status(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
