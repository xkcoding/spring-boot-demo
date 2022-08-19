package com.xkcoding.common.exception;

import com.xkcoding.common.enums.CommonStatus;
import com.xkcoding.common.enums.base.IStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通用业务异常
 *
 * @author 一珩（沈扬凯 yk.shen@tuya.com）
 * @date 2022-08-20 01:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommonBizException extends RuntimeException {
    public Integer code;
    private String message;

    public CommonBizException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public CommonBizException(Throwable cause) {
        super(cause);
        this.code = CommonStatus.SERVER_ERROR.getCode();
        this.message = cause.getMessage();
    }

    public CommonBizException(String message) {
        super(message);
        this.code = CommonStatus.SERVER_ERROR.getCode();
        this.message = message;
    }

    public CommonBizException(IStatus status) {
        super(status.getDesc());
        this.code = status.getCode();
        this.message = status.getDesc();
    }
}
