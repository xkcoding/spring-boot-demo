package com.xkcoding.springbootdemocacheredis.controller;

import com.xkcoding.springbootdemocacheredis.entity.User;
import com.xkcoding.springbootdemocacheredis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CacheConfig(cacheNames = "users") // 整体配置缓存的名称
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping({"", "/"})
	@Cacheable
	public List<User> index() {
		return userService.find();
	}

	@GetMapping("/find/{id}")
	@Cacheable(key = "#id", condition = "#id != null") // #id 以及 condition 里的语法是 SpEL 语法
	public User find(@PathVariable Long id) {
		return userService.find(id);
	}

	@GetMapping("/find")
	@Cacheable(key = "#name", condition = "#name != null")
	public User find(@RequestParam String name) {
		return userService.find(name);
	}

	@GetMapping("/save")
	@CacheEvict(allEntries = true, beforeInvocation = true) //@CacheEvict 清空缓存
	public User save() {
		return userService.save();
	}

	@GetMapping("/update/{id}")
	@CacheEvict(allEntries = true, beforeInvocation = true)
	public User update(@PathVariable Long id) {
		return userService.update(id);
	}

	@GetMapping("/delete/{id}")
	@CacheEvict(allEntries = true, beforeInvocation = true)
	public String delete(@PathVariable Long id) {
		User user = userService.delete(id);
		if (user == null) {
			return "用户不存在";
		} else {
			return user.getName() + "删除成功";
		}
	}
}
