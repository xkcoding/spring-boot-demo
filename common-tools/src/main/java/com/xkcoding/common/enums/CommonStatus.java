package com.xkcoding.common.enums;

import com.xkcoding.common.enums.base.IStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 常用状态
 *
 * @author yangkai.shen
 * @date 2022-08-20 01:49
 */
@Getter
@AllArgsConstructor
public enum CommonStatus implements IStatus {
    /**
     * 操作状态码
     */
    OK(200, "请求成功"),
    BASE_ERROR(202, "基础服务异常"),
    PARAM_ERROR(203, "请求参数缺失"),
    SERVER_ERROR(500, "系统错误，请联系管理员");

    private final Integer code;
    private final String desc;
}
