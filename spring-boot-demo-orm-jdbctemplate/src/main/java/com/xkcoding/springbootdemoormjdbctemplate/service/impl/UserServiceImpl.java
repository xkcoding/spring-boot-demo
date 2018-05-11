package com.xkcoding.springbootdemoormjdbctemplate.service.impl;

import com.google.common.collect.Lists;
import com.xiaoleilu.hutool.bean.BeanUtil;
import com.xkcoding.springbootdemoormjdbctemplate.model.User;
import com.xkcoding.springbootdemoormjdbctemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * UserServiceImpl
 * </p>
 *
 * @package: com.xkcoding.springbootdemoormjdbctemplate.service.impl
 * @description： UserServiceImpl
 * @author: yangkai.shen
 * @date: Created in 2018/5/11 下午3:27
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Integer save(User user) {
		String sql = "INSERT INTO jdbctemplate_user (name,tel,create_time) VALUES(?,?,?)";
		return jdbcTemplate.update(sql, user.getName(), user.getTel(), user.getCreateTime());
	}

	@Override
	public Integer update(User user) {
		String sql = "UPDATE jdbctemplate_user SET name = ?,tel = ? where id = ?";
		return jdbcTemplate.update(sql, user.getName(), user.getTel(), user.getId());
	}

	@Override
	public Integer delete(User user) {
		String sql = "DELETE FROM jdbctemplate_user where id = ?";
		return jdbcTemplate.update(sql, user.getId());
	}

	@Override
	public User findById(Integer id) {
		String sql = "SELECT * FROM jdbctemplate_user where id = ?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		return jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
	}

	@Override
	public User findByName(String name) {
		String sql = "SELECT * FROM jdbctemplate_user where name = ?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		return jdbcTemplate.queryForObject(sql, new Object[]{name}, rowMapper);
	}

	@Override
	public List<User> findAll() {
		String sql = "SELECT * FROM jdbctemplate_user";
		List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
		List<User> ret = Lists.newArrayList();
		maps.forEach(map -> ret.add(BeanUtil.fillBeanWithMap(map, new User(), true, false)));
		return ret;
	}

	@Override
	public List<User> findUserByPage(Integer pageNum, Integer pageSize) {
		String sql = "SELECT * FROM jdbctemplate_user LIMIT ?,?";
		Integer offset = (pageNum - 1) * pageSize;
		List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, new Object[]{offset, pageSize});
		List<User> ret = Lists.newArrayList();
		maps.forEach(map -> ret.add(BeanUtil.fillBeanWithMap(map, new User(), true, false)));
		return ret;
	}
}
