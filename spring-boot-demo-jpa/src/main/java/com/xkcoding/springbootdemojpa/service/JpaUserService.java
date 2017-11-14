package com.xkcoding.springbootdemojpa.service;

import com.xkcoding.springbootdemojpa.entity.JpaUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JpaUserService {
	JpaUser findById(Long id);

	List<JpaUser> findAll();

	JpaUser insert(JpaUser user);

	JpaUser update(JpaUser user);

	void delete(JpaUser user);

	List<JpaUser> insertList(List<JpaUser> userList);

	JpaUser findJpaUser(String name);

	List<JpaUser> findJpaUsersByIdIn(List<Long> ids);

	Page<JpaUser> findByPage(Pageable pageable);
}
