package com.xkcoding.exception.handler.exception;

import cn.hutool.core.lang.Dict;
import com.xkcoding.exception.handler.constant.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 异常基类
 * </p>
 *
 * @package: com.xkcoding.exception.handler.exception
 * @description: 异常基类
 * @author: yangkai.shen
 * @date: Created in 2018/10/2 9:31 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException implements IDataType {

    private Integer code;

    private String message;

    private Object data;

    /**
     * request.setAttribute所虚，一般为视图携带属性
     */
    private Dict attributes;

    public BaseException(Status status) {
        super(status.getMessage());
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public BaseException(Status status, Dict attributes) {
        super(status.getMessage());
        this.code = status.getCode();
        this.message = status.getMessage();
        this.attributes = attributes;
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseException(Integer code, String message, Object data) {
        super(message);
        this.message = message;
        this.data = data;
    }

    public BaseException(Integer code, String message, Object data, Dict attributes) {
        super(message);
        this.message = message;
        this.data = data;
        this.attributes = attributes;
    }

}
