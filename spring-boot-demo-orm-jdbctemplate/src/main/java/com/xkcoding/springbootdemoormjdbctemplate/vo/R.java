package com.xkcoding.springbootdemoormjdbctemplate.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 统一返回结果类型
 * </p>
 *
 * @package: com.xkcoding.springbootdemoormjdbctemplate.vo
 * @description： 统一返回结果类型
 * @author: yangkai.shen
 * @date: Created in 2018/5/11 下午3:53
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class R {
	private int code;
	private String msg;
	private Object data;
}
