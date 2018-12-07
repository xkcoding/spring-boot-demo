package com.xkcoding.rbac.security.common;

import lombok.Getter;

/**
 * <p>
 * 通用状态码
 * </p>
 *
 * @package: com.xkcoding.rbac.security.common
 * @description: 通用状态码
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 14:31
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Getter
public enum Status implements IStatus {
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 操作异常
     */
    ERROR(500, "操作异常"),

    /**
     * 退出成功
     */
    LOGOUT(200, "退出成功");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    Status(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Status fromCode(Integer code) {
        Status[] statuses = Status.values();
        for (Status status : statuses) {
            if (status.getCode()
                    .equals(code)) {
                return status;
            }
        }
        return SUCCESS;
    }

    @Override
    public String toString() {
        return String.format(" Status:{code=%s, message=%s} ", getCode(), getMessage());
    }

}
