package com.xkcoding.common.enums.base;

/**
 * 状态抽象
 *
 * @author yangkai.shen
 * @date 2022-08-20 01:49
 */
public interface IStatus {
    /**
     * 状态码
     *
     * @return 状态码
     */
    Integer getCode();

    /**
     * 描述
     *
     * @return 描述
     */
    String getDesc();
}
