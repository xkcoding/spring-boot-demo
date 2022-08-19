package com.xkcoding.exception.handler.exception;

import com.xkcoding.common.enums.base.IStatus;
import com.xkcoding.common.exception.CommonBizException;
import lombok.Getter;

/**
 * <p>
 * JSON异常
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2022-08-20 02:08
 */
@Getter
public class JsonException extends CommonBizException {

    public JsonException(IStatus status) {
        super(status);
    }

    public JsonException(Integer code, String message) {
        super(code, message);
    }
}
