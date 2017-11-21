package com.xkcoding.springbootdemoormjpa.service.impl;

import com.xkcoding.springbootdemoormjpa.entity.JpaUser;
import com.xkcoding.springbootdemoormjpa.repository.JpaUserRepository;
import com.xkcoding.springbootdemoormjpa.service.JpaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaUserServiceImpl implements JpaUserService {
	@Autowired
	private JpaUserRepository jpaUserRepository;

	@Override
	public JpaUser findById(Long id) {
		return jpaUserRepository.findOne(id);
	}

	@Override
	public List<JpaUser> findAll() {
		return jpaUserRepository.findAll();
	}

	@Override
	public JpaUser insert(JpaUser user) {
		return jpaUserRepository.save(user);
	}

	@Override
	public JpaUser update(JpaUser user) {
		return jpaUserRepository.save(user);
	}

	@Override
	public void delete(JpaUser user) {
		jpaUserRepository.delete(user);
	}

	@Override
	public List<JpaUser> insertList(List<JpaUser> userList) {
		return jpaUserRepository.save(userList);
	}

	@Override
	public JpaUser findJpaUser(String name) {
		return jpaUserRepository.findJpaUser(name);
	}

	@Override
	public List<JpaUser> findJpaUsersByIdIn(List<Long> ids) {
		return jpaUserRepository.findJpaUsersByIdIn(ids);
	}

	@Override
	public Page<JpaUser> findByPage(Pageable pageable) {
		return jpaUserRepository.findAll(pageable);
	}
}
