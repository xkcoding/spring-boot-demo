package com.xkcoding.springbootdemoormjdbctemplate.service;

import com.xkcoding.springbootdemoormjdbctemplate.model.User;

import java.util.List;

/**
 * <p>
 * UserService
 * </p>
 *
 * @package: com.xkcoding.springbootdemoormjdbctemplate.service
 * @description： UserService
 * @author: yangkai.shen
 * @date: Created in 2018/5/11 下午3:26
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public interface UserService {
	Integer save(User user);

	Integer update(User user);

	Integer delete(User user);

	User findById(Integer id);

	User findByName(String name);

	List<User> findAll();

	List<User> findUserByPage(Integer pageNum, Integer pageSize);
}
