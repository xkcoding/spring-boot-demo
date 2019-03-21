package com.xkcoding.rbac.shiro.common;

import lombok.Getter;

/**
 * <p>
 * 通用状态枚举
 * </p>
 *
 * @package: com.xkcoding.rbac.shiro.common
 * @description: 通用状态枚举
 * @author: yangkai.shen
 * @date: Created in 2019-03-21 16:31
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Getter
public enum ResultCode implements IResultCode {
    /**
     * 成功
     */
    OK(200, "成功"),
    /**
     * 失败
     */
    ERROR(500, "失败");

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
