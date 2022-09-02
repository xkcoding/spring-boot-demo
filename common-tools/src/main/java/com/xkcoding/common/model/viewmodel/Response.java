package com.xkcoding.common.model.viewmodel;

import com.xkcoding.common.enums.CommonStatus;
import com.xkcoding.common.enums.base.IStatus;
import com.xkcoding.common.exception.CommonBizException;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用接口返回
 *
 * @author yangkai.shen
 * @date 2022-08-20 01:43
 */
@Data
public class Response<T> implements Serializable {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回内容
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    private Response() {
    }

    private Response(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Response<T> of(Integer code, String message, T data) {
        return new Response<>(code, message, data);
    }

    public static <T> Response<T> ofSuccess(T data) {
        return ofStatus(CommonStatus.OK, data);
    }

    public static <T> Response<T> ofStatus(IStatus status) {
        return ofStatus(status, null);
    }

    public static <T> Response<T> ofStatus(IStatus status, T data) {
        return of(status.getCode(), status.getDesc(), data);
    }

    public static <T> Response<T> ofError(CommonBizException exception, T data) {
        return of(exception.getCode(), exception.getMessage(), data);
    }

    public static <T> Response<T> ofError(CommonBizException exception) {
        return ofError(exception, null);
    }
}
