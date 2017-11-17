package com.xkcoding.springbootdemocacheredis.service;

import com.xkcoding.springbootdemocacheredis.entity.User;

import java.util.List;

public interface UserService {
	User save();

	User update(Long id);

	User delete(Long id);

	List<User> find();

	User find(Long id);

	User find(String name);
}
