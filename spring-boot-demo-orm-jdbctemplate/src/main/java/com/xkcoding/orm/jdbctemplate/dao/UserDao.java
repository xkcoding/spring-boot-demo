package com.xkcoding.orm.jdbctemplate.dao;

import com.xkcoding.orm.jdbctemplate.dao.base.BaseDao;
import com.xkcoding.orm.jdbctemplate.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
public class UserDao extends BaseDao<User> {

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
}
