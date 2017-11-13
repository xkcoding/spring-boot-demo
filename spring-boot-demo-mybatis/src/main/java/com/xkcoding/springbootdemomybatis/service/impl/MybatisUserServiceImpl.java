package com.xkcoding.springbootdemomybatis.service.impl;

import com.github.pagehelper.PageHelper;
import com.xiaoleilu.hutool.util.PageUtil;
import com.xkcoding.springbootdemomybatis.mapper.MybatisUserMapper;
import com.xkcoding.springbootdemomybatis.model.MybatisUser;
import com.xkcoding.springbootdemomybatis.service.MybatisUserService;
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
	public Integer saveList(List<MybatisUser> userList) {
		return mybatisUserMapper.insertList(userList);
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
