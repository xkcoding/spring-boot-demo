package com.xkcoding.exception.handler.exception;

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
public class BaseException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer code;
	private String message;

	public BaseException(Status status) {
		super(status.getMessage());
		this.code = status.getCode();
		this.message = status.getMessage();
	}

	public BaseException(Integer code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}
}
