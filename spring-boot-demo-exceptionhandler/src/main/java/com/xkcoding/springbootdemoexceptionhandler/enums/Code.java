package com.xkcoding.springbootdemoexceptionhandler.enums;

import lombok.Getter;

/**
 * 状态码
 *
 * @package: com.xkcoding.springbootdemoexceptionhandler.enums
 * @description： 状态码
 * @author: yangkai.shen
 * @date: Created in 2017/11/24 下午1:56
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Getter
public enum Code {
	SUCCESS(200);
	private Integer code;

	Code(Integer code) {
		this.code = code;
	}

}
