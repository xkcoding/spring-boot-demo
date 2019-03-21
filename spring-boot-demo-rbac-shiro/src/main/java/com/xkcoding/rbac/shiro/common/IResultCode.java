package com.xkcoding.rbac.shiro.common;

/**
 * <p>
 * 统一状态码接口
 * </p>
 *
 * @package: com.xkcoding.rbac.shiro.common
 * @description: 统一状态码接口
 * @author: yangkai.shen
 * @date: Created in 2019-03-21 16:28
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
public interface IResultCode {
    /**
     * 获取状态码
     *
     * @return 状态码
     */
    Integer getCode();

    /**
     * 获取返回消息
     *
     * @return 返回消息
     */
    String getMessage();
}
