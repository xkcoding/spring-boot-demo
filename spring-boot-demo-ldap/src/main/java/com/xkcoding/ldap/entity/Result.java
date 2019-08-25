package com.xkcoding.ldap.entity;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * Result
 *
 * @author fxbin
 * @version v1.0
 * @since 2019/8/26 1:44
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1696194043024336235L;

    /**
     * 错误码
     */
    private int errcode;

    /**
     * 错误信息
     */
    private String errmsg;

    /**
     * 响应数据
     */
    private T data;

    public Result() {
    }

    private Result(ResultCode resultCode) {
        this(resultCode.code, resultCode.msg);
    }

    private Result(ResultCode resultCode, T data) {
        this(resultCode.code, resultCode.msg, data);
    }

    private Result(int errcode, String errmsg) {
        this(errcode, errmsg, null);
    }

    private Result(int errcode, String errmsg, T data) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.data = data;
    }



    /**
     * 返回成功
     *
     * @param <T> 泛型标记
     * @return 响应信息 {@code Result}
     */
    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS);
    }


    /**
     * 返回成功-携带数据
     *
     * @param data 响应数据
     * @param <T> 泛型标记
     * @return 响应信息 {@code Result}
     */
    public static <T> Result<T> success(@Nullable T data) {
        return new Result<>(ResultCode.SUCCESS, data);
    }





}
