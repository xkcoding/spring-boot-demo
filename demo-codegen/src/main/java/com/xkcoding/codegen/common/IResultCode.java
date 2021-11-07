package com.xkcoding.codegen.common;

/**
 * <p>
 * 统一状态码接口
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-03-21 16:28
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
