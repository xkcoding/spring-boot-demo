package com.xkcoding.springbootdemomybatis.service;

import com.xkcoding.springbootdemomybatis.model.MybatisUser;

import java.util.List;

public interface MybatisUserService {
	MybatisUser save(MybatisUser user);

	Integer saveList(List<MybatisUser> userList);

	MybatisUser update(MybatisUser user);

	Integer delete(MybatisUser user);

	MybatisUser findById(Long id);

	MybatisUser findByName(String name);

	List<MybatisUser> findAll();

	List<MybatisUser> findMybatisUserByPage(Integer pageNum, Integer pageSize);
}
