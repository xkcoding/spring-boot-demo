package com.xkcoding.springbootdemoormjdbctemplate.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 实体类
 * </p>
 *
 * @package: com.xkcoding.springbootdemoormjdbctemplate.model
 * @description： 实体类
 * @author: yangkai.shen
 * @date: Created in 2018/5/11 下午3:24
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
public class User  implements Serializable {
	private int id;
	private String name;
	private String tel;
	private Date createTime;
}
