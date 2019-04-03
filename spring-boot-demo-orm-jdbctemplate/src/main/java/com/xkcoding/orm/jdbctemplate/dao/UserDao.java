package com.xkcoding.orm.jdbctemplate.dao;

import com.xkcoding.orm.jdbctemplate.dao.base.BaseDao;
import com.xkcoding.orm.jdbctemplate.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * User Dao
 * </p>
 *
 * @package: com.xkcoding.orm.jdbctemplate.dao
 * @description: User Dao
 * @author: yangkai.shen
 * @date: Created in 2018/10/15 11:15 AM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Repository
public class UserDao extends BaseDao<User, Long> {

	@Autowired
	public UserDao(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	/**
	 * 保存用户
	 *
	 * @param user 用户对象
	 * @return 操作影响行数
	 */
	public Integer insert(User user) {
		return super.insert(user, true);
	}

	/**
	 * 根据主键删除用户
	 *
	 * @param id 主键id
	 * @return 操作影响行数
	 */
	public Integer delete(Long id) {
		return super.deleteById(id);
	}

	/**
	 * 更新用户
	 *
	 * @param user 用户对象
	 * @param id   主键id
	 * @return 操作影响行数
	 */
	public Integer update(User user, Long id) {
		return super.updateById(user, id, true);
	}

	/**
	 * 根据主键获取用户
	 *
	 * @param id 主键id
	 * @return id对应的用户
	 */
	public User selectById(Long id) {
		return super.findOneById(id);
	}

	/**
	 * 根据查询条件获取用户列表
	 *
	 * @param user 用户查询条件
	 * @return 用户列表
	 */
	public List<User> selectUserList(User user) {
		return super.findByExample(user);
	}
}
