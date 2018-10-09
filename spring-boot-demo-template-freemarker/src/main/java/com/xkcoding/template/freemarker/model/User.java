package com.xkcoding.template.freemarker.model;

import lombok.Data;

/**
 * <p>
 * 用户 model
 * </p>
 *
 * @package: com.xkcoding.template.freemarker.model
 * @description: 用户 model
 * @author: yangkai.shen
 * @date: Created in 2018/10/9 3:06 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
public class User {
	private String name;
	private String password;
}
