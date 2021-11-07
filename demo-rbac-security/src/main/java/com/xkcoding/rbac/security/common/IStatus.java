package com.xkcoding.rbac.security.common;

/**
 * <p>
 * REST API 错误码接口
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-07 14:35
 */
public interface IStatus {

    /**
     * 状态码
     *
     * @return 状态码
     */
    Integer getCode();

    /**
     * 返回信息
     *
     * @return 返回信息
     */
    String getMessage();

}
