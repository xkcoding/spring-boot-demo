package com.xkcoding.orm.jdbctemplate.service;

import com.xkcoding.orm.jdbctemplate.entity.User;

/**
 * <p>
 * User Service
 * </p>
 *
 * @package: com.xkcoding.orm.jdbctemplate.service
 * @description: User Service
 * @author: yangkai.shen
 * @date: Created in 2018/10/15 1:51 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public interface IUserService {
	/**
	 * 保存用户
	 *
	 * @param user 用户实体
	 * @return 保存成功 {@code true} 保存失败 {@code false}
	 */
	Boolean save(User user);
}
