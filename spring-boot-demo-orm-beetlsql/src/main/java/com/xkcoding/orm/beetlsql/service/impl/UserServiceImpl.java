package com.xkcoding.orm.beetlsql.service.impl;

import java.util.List;

import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xkcoding.orm.beetlsql.dao.UserDao;
import com.xkcoding.orm.beetlsql.entity.User;
import com.xkcoding.orm.beetlsql.service.UserService;

import cn.hutool.core.util.ObjectUtil;

/**
 * <p>
 * User Service
 * </p>
 *
 * @package: com.xkcoding.orm.beetlsql.service.impl
 * @description: User Service
 * @author: yangkai.shen
 * @date: Created in 2018/11/14 16:28
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;

	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 新增用户
	 *
	 * @param user 用户
	 */
	@Override
	public User saveUser(User user) {
		userDao.insert(user, true);
		return user;
	}

	/**
	 * 批量插入用户
	 *
	 * @param users 用户列表
	 */
	@Override
	public void saveUserList(List<User> users) {
		userDao.insertBatch(users);
	}

	/**
	 * 根据主键删除用户
	 *
	 * @param id 主键
	 */
	@Override
	public void deleteUser(Long id) {
		userDao.deleteById(id);
	}

	/**
	 * 更新用户
	 *
	 * @param user 用户
	 * @return 更新后的用户
	 */
	@Override
	public User updateUser(User user) {
		if (ObjectUtil.isNull(user)) {
			throw new RuntimeException("用户id不能为null");
		}
		userDao.updateTemplateById(user);
		return userDao.single(user.getId());
	}

	/**
	 * 查询单个用户
	 *
	 * @param id 主键id
	 * @return 用户信息
	 */
	@Override
	public User getUser(Long id) {
		return userDao.single(id);
	}

	/**
	 * 查询用户列表
	 *
	 * @return 用户列表
	 */
	@Override
	public List<User> getUserList() {
		return userDao.all();
	}

	/**
	 * 分页查询
	 *
	 * @param currentPage 当前页
	 * @param pageSize    每页条数
	 * @return 分页用户列表
	 */
	@Override
	public PageQuery<User> getUserByPage(Integer currentPage, Integer pageSize) {
		return userDao.createLambdaQuery().page(currentPage, pageSize);
	}
}
