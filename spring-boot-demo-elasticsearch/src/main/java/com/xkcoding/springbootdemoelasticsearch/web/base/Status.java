package com.xkcoding.springbootdemoelasticsearch.web.base;

import lombok.Getter;

/**
 * <p>
 * 通用状态码
 * </p>
 *
 * @package: com.xkcoding.springbootdemoelasticsearch.web.base
 * @description： 通用状态码
 * @author: yangkai.shen
 * @date: Created in 2018/1/18 下午5:35
 * @copyright: Copyright (c) 2018
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Getter
public enum Status {
	SUCCESS(200, "OK"), BAD_REQUEST(400, "Bad Request"), NOT_FOUND(404, "Not Found"), INTERNAL_SERVER_ERROR(500, "Unknown Internal Error");

	private int code;
	private String msg;

	Status(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
