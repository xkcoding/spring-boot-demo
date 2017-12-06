package com.xkcoding.springbootdemorabcshiromybatis.dao;

import com.xkcoding.springbootdemorabcshiromybatis.model.MybatisShiroUser;
import com.xkcoding.springbootdemorabcshiromybatis.util.MyMapper;
import org.springframework.stereotype.Component;

@Component
public interface MybatisShiroUserMapper extends MyMapper<MybatisShiroUser> {
	/**
	 * 根据用户账号获得用户信息
	 *
	 * @param username 用户账号
	 * @return 用户对象
	 */
	MybatisShiroUser findByUsername(String username);
}