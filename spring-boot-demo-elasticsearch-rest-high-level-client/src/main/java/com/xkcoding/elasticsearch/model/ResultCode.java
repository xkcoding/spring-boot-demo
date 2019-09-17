package com.xkcoding.elasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ResultCode
 *
 * @author fxbin
 * @version v1.0
 * @since 2019/8/26 1:47
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    /**
     * 接口调用成功
     */
    SUCCESS(0, "Request Successful"),

    /**
     * 服务器暂不可用，建议稍候重试。建议重试次数不超过3次。
     */
    FAILURE(-1, "System Busy");

    final int code;

    final String msg;

}
