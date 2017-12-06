package com.xkcoding.springbootdemorabcshiromybatis.shiro;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
 * </p>
 *
 * @package: com.xkcoding.springbootdemorabcshiromybatis.shiro
 * @description： 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
 * @author: yangkai.shen
 * @date: Created in 2017/12/6 下午3:26
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Data
public class ShiroUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String username;
	private String realname;
	private Integer deptId;
	private String deptName;
	private List<Integer> roleList;
	private List<String> roleNames;

}
