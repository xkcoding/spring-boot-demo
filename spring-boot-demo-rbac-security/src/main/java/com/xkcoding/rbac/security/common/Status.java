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
     * 操作成功！
     */
    SUCCESS(200, "操作成功！"),

    /**
     * 操作异常！
     */
    ERROR(500, "操作异常！"),

    /**
     * 退出成功！
     */
    LOGOUT(200, "退出成功！"),

    /**
     * 请先登录！
     */
    UNAUTHORIZED(401, "请先登录！"),

    /**
     * 暂无权限访问！
     */
    ACCESS_DENIED(403, "权限不足！"),

    /**
     * 请求不存在！
     */
    REQUEST_NOT_FOUND(404, "请求不存在！"),

    /**
     * 请求方式不支持！
     */
    HTTP_BAD_METHOD(405, "请求方式不支持！"),

    /**
     * 请求异常！
     */
    BAD_REQUEST(400, "请求异常！"),

    /**
     * 参数不匹配！
     */
    PARAM_NOT_MATCH(400, "参数不匹配！"),

    /**
     * 参数不能为空！
     */
    PARAM_NOT_NULL(400, "参数不能为空！"),

    /**
     * 当前用户已被锁定，请联系管理员解锁！
     */
    USER_DISABLED(403, "当前用户已被锁定，请联系管理员解锁！"),

    /**
     * 用户名或密码错误！
     */
    USERNAME_PASSWORD_ERROR(5001, "用户名或密码错误！"),

    /**
     * token 已过期，请重新登录！
     */
    TOKEN_EXPIRED(5002, "token 已过期，请重新登录！"),

    /**
     * token 解析失败，请尝试重新登录！
     */
    TOKEN_PARSE_ERROR(5002, "token 解析失败，请尝试重新登录！"),

    /**
     * 当前用户已在别处登录，请尝试更改密码或重新登录！
     */
    TOKEN_OUT_OF_CTRL(5003,"当前用户已在别处登录，请尝试更改密码或重新登录！");

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
