package com.xkcoding.springbootdemotemplatebeetl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 用户实体类
 * </p>
 *
 * @package: com.xkcoding.springbootdemotemplatebeetl.model
 * @description： 用户实体类
 * @author: yangkai.shen
 * @date: Created in 2018/4/26 下午4:46
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
	private Integer id;
	private String name;
	private String tel;
	private Boolean admin;
}
