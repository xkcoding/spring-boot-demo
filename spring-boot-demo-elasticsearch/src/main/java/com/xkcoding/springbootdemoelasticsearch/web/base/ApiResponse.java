package com.xkcoding.springbootdemoelasticsearch.web.base;

import lombok.Data;

/**
 * <p>
 * 统一接口返回类型
 * </p>
 *
 * @package: com.xkcoding.springbootdemoelasticsearch
 * @description： 统一接口返回类型
 * @author: yangkai.shen
 * @date: Created in 2018/1/18 下午5:34
 * @copyright: Copyright (c) 2018
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Data
public class ApiResponse {
	private int code;
	private String message;
	private Object data;
	private boolean more;

	public ApiResponse(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public ApiResponse() {
		this.code = Status.SUCCESS.getCode();
		this.message = Status.SUCCESS.getMsg();
	}

	public static ApiResponse ofMessage(int code, String message) {
		return new ApiResponse(code, message, null);
	}

	public static ApiResponse ofSuccess(Object data) {
		return new ApiResponse(Status.SUCCESS.getCode(), Status.SUCCESS.getMsg(), data);
	}

	public static ApiResponse ofStatus(Status status) {
		return new ApiResponse(status.getCode(), status.getMsg(), null);
	}

}