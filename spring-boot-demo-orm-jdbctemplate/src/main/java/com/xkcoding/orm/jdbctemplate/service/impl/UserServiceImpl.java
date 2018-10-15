package com.xkcoding.orm.jdbctemplate.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.xkcoding.orm.jdbctemplate.constant.Const;
import com.xkcoding.orm.jdbctemplate.dao.UserDao;
import com.xkcoding.orm.jdbctemplate.entity.User;
import com.xkcoding.orm.jdbctemplate.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * User Service Implement
 * </p>
 *
 * @package: com.xkcoding.orm.jdbctemplate.service.impl
 * @description: User Service Implement
 * @author: yangkai.shen
 * @date: Created in 2018/10/15 1:53 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Service
public class UserServiceImpl implements IUserService {
	private final UserDao userDao;

	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 保存用户
	 *
	 * @param user 用户实体
	 * @return 保存成功 {@code true} 保存失败 {@code false}
	 */
	@Override
	public Boolean save(User user) {
		String rawPass = user.getPassword();
		String salt = IdUtil.simpleUUID();
		String pass = SecureUtil.md5(rawPass+ Const.SALT_PREFIX+salt);
		user.setPassword(pass);
		user.setSalt(salt);
		return userDao.insert(user) > 0;
	}
}
