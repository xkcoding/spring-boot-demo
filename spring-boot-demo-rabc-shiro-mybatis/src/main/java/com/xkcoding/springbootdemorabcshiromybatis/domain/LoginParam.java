package com.xkcoding.springbootdemorabcshiromybatis.domain;

import lombok.Data;

/**
 * <p>
 * 登录参数
 * </p>
 *
 * @package: com.xkcoding.springbootdemorabcshiromybatis.domain
 * @description： 登录参数
 * @author: yangkai.shen
 * @date: Created in 2017/12/7 下午3:45
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Data
public class LoginParam {
	private String username;
	private String password;
	private String kaptcha;
	private Boolean rememberMe;
}
