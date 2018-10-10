package com.xkcoding.template.thymeleaf.model;

import lombok.Data;

/**
 * <p>
 * 用户 model
 * </p>
 *
 * @package: com.xkcoding.template.thymeleaf.model
 * @description: 用户 model
 * @author: yangkai.shen
 * @date: Created in 2018/10/10 10:11 AM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
public class User {
	private String name;
	private String password;
}
