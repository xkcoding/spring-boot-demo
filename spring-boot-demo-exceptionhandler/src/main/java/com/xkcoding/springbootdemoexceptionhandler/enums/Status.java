package com.xkcoding.springbootdemoexceptionhandler.enums;

import lombok.Getter;

/**
 * 返回状态
 *
 * @package: com.xkcoding.springbootdemoexceptionhandler.enums
 * @description： 返回状态
 * @author: yangkai.shen
 * @date: Created in 2017/11/24 下午1:47
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Getter
public enum Status {
	OK(200, "成功"), UNKNOW_ERROR(-1, "未知错误");
	private Integer code;
	private String message;

	Status(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
