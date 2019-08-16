package com.xkcoding.rbac.security.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 异常基类
 * </p>
 *
 * @package: com.xkcoding.rbac.security.common
 * @description: 异常基类
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 14:57
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer code;
	private String message;
	private Object data;

	public BaseException(Status status) {
		super(status.getMessage());
		this.code = status.getCode();
		this.message = status.getMessage();
	}

	public BaseException(Status status, Object data) {
		this(status);
		this.data = data;
	}

	public BaseException(Integer code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public BaseException(Integer code, String message, Object data) {
		this(code, message);
		this.data = data;
	}
}
