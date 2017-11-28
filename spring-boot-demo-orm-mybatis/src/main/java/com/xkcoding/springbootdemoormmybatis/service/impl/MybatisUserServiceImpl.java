package com.xkcoding.springbootdemoormmybatis.service.impl;

import com.github.pagehelper.PageHelper;
import com.xkcoding.springbootdemoormmybatis.mapper.MybatisUserMapper;
import com.xkcoding.springbootdemoormmybatis.model.MybatisUser;
import com.xkcoding.springbootdemoormmybatis.service.MybatisUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MybatisUserServiceImpl implements MybatisUserService {

	@Autowired
	private MybatisUserMapper mybatisUserMapper;

	@Override
	public MybatisUser save(MybatisUser user) {
		mybatisUserMapper.insertUseGeneratedKeys(user);
		return user;
	}

	@Override
	public List<MybatisUser> saveList(List<MybatisUser> userList) {
		mybatisUserMapper.insertList(userList);
		return userList;
	}

	@Override
	public MybatisUser update(MybatisUser user) {
		int i = mybatisUserMapper.updateByPrimaryKeySelective(user);
		if (i > 0) {
			log.info("【MybatisUserService】更新成功：{}条目", i);
		} else {
			log.error("【MybatisUserService】更新失败：{}", user);
		}
		return mybatisUserMapper.selectByPrimaryKey(user.getId());
	}

	@Override
	public Integer delete(MybatisUser user) {
		return mybatisUserMapper.deleteByPrimaryKey(user);
	}

	@Override
	public MybatisUser findById(Long id) {
		return mybatisUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public MybatisUser findByName(String name) {
		return mybatisUserMapper.findByName(name);
	}

	@Override
	public List<MybatisUser> findAll() {
		return mybatisUserMapper.selectAll();
	}

	@Override
	public List<MybatisUser> findMybatisUserByPage(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return mybatisUserMapper.selectAll();
	}
}
