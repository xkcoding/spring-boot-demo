package com.xkcoding.exception.handler.exception;

import com.xkcoding.common.enums.base.IStatus;
import com.xkcoding.common.exception.CommonBizException;
import lombok.Getter;

/**
 * <p>
 * 页面异常
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2022-08-20 02:09
 */
@Getter
public class PageException extends CommonBizException {

    public PageException(IStatus status) {
        super(status);
    }

    public PageException(Integer code, String message) {
        super(code, message);
    }
}
