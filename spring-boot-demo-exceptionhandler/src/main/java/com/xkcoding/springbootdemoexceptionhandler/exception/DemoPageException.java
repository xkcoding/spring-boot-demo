package com.xkcoding.springbootdemoexceptionhandler.exception;

import lombok.Getter;

/**
 * 统一的页面异常处理
 * <p>
 * 要继承 RuntimeException
 * </p>
 *
 * @package: com.xkcoding.springbootdemoexceptionhandler.exception
 * @description： 统一的页面异常处理
 * @author: yangkai.shen
 * @date: Created in 2017/11/24 下午1:32
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Getter
public class DemoPageException extends RuntimeException {
	public Integer code;

	public DemoPageException(Integer code, String message) {
		super(message);
		this.code = code;
	}
}
