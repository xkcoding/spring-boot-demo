package com.xkcoding.common.enums.base;

/**
 * 状态抽象
 *
 * @author 一珩（沈扬凯 yk.shen@tuya.com）
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
