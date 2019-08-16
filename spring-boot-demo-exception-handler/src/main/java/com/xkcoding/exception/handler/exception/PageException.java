package com.xkcoding.exception.handler.exception;

import com.xkcoding.exception.handler.constant.Status;
import lombok.Getter;

/**
 * <p>
 * 页面异常
 * </p>
 *
 * @package: com.xkcoding.exception.handler.exception
 * @description: 页面异常
 * @author: yangkai.shen
 * @date: Created in 2018/10/2 9:18 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Getter
public class PageException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PageException(Status status) {
		super(status);
	}

	public PageException(Integer code, String message) {
		super(code, message);
	}
}
