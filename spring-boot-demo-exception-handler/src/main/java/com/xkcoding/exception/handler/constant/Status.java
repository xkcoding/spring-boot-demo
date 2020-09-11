package com.xkcoding.exception.handler.constant;

import lombok.Getter;

/**
 * <p>
 * 状态码封装
 * </p>
 *
 * @package: com.xkcoding.exception.handler.constant
 * @description: 状态码封装
 * @author: yangkai.shen
 * @date: Created in 2018/10/2 9:02 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Getter
public enum Status {
	/**
	 * 操作成功
	 */
	OK(200, "操作成功"),

	/**
	 * 未知异常
	 */
	UNKNOWN_ERROR(500, "服务器出错啦"),

    /**
     * 请求资源未找到
     */
	NOT_FOUND(404, "请求资源未找到");

	/**
	 * 状态码
	 */
	private Integer code;

	/**
	 * 内容
	 */
	private String message;

	Status(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
