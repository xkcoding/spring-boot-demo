package com.xkcoding.springbootdemotemplatefreemarker.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户实体类
 *
 * @package: com.xkcoding.springbootdemotemplatefreemarker
 * @description： 用户实体类
 * @author: yangkai.shen
 * @date: Created in 2017/11/26 下午6:50
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Data
public class User implements Serializable {
	private String name;
	private String password;
}
