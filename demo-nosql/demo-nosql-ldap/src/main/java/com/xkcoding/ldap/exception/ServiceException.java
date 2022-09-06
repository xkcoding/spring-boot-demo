package com.xkcoding.ldap.exception;

import com.xkcoding.ldap.api.ResultCode;
import lombok.Getter;

/**
 * ServiceException
 *
 * @author fxbin
 * @version v1.0
 * @since 2019-08-26 1:53
 */
public class ServiceException extends RuntimeException {

    @Getter
    private int errcode;

    @Getter
    private String errmsg;

    public ServiceException(ResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMsg());
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Integer errcode, String errmsg) {
        super(errmsg);
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
