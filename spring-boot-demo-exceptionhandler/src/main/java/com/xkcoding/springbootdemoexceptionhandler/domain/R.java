package com.xkcoding.springbootdemoexceptionhandler.domain;

import com.xkcoding.springbootdemoexceptionhandler.enums.Code;
import com.xkcoding.springbootdemoexceptionhandler.enums.Status;
import com.xkcoding.springbootdemoexceptionhandler.exception.DemoJsonException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回的 json 对象
 *
 * @package: com.xkcoding.springbootdemoexceptionhandler
 * @description： 统一返回的 json 对象
 * @author: yangkai.shen
 * @date: Created in 2017/11/24 下午1:42
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {
	private Integer code;
	private String message;
	private T data;

	public R(Status status) {
		this.code = status.getCode();
		this.message = status.getMessage();
	}

	public static R success(Integer code, String message, Object data) {
		return new R(code, message, data);
	}

	public static R success() {
		return new R(Status.OK);
	}

	public static R success(String message) {
		return success(message, null);
	}

	public static R success(String message, Object data) {
		return success(Code.SUCCESS.getCode(), message, data);
	}

	public static R error(Integer code, String message, Object data) {
		return new R(code, message, data);
	}

	public static R error(Integer code, String message) {
		return error(code, message, null);
	}

	public static R error(DemoJsonException exception) {
		return error(exception.getCode(), exception.getMessage());
	}
}
