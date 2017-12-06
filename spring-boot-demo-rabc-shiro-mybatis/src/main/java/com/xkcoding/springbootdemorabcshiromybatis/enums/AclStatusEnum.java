package com.xkcoding.springbootdemorabcshiromybatis.enums;

import lombok.Getter;

/**
 * <p>
 * 权限状态的枚举类
 * </p>
 *
 * @package: com.xkcoding.springbootdemorabcshiromybatis.enums
 * @description： 权限状态的枚举类
 * @author: yangkai.shen
 * @date: Created in 2017/12/6 下午3:38
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Getter
public enum AclStatusEnum {
	DISABLE(0, "禁用"), ENABLE(1, "启用");

	private Integer code;
	private String message;

	AclStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * 根据状态码返回状态
	 *
	 * @param code 状态码
	 * @return 状态
	 */
	public static String valueOf(Integer code) {
		if (code == null) {
			return null;
		} else {
			for (AclStatusEnum statusEnum : AclStatusEnum.values()) {
				if (statusEnum.getCode().equals(code)) {
					return statusEnum.getMessage();
				}
			}
			return null;
		}
	}
}
