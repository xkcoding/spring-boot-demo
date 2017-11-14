package com.xkcoding.springbootdemojpa.controller;

import com.google.common.collect.Lists;
import com.xkcoding.springbootdemojpa.entity.JpaUser;
import com.xkcoding.springbootdemojpa.service.JpaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class JpaUserController {
	@Autowired
	private JpaUserService jpaUserService;

	@GetMapping("/{id}")
	public JpaUser findById(@PathVariable Long id) {
		return jpaUserService.findById(id);
	}

	@GetMapping({"", "/", "/index"})
	public List<JpaUser> findAll() {
		return jpaUserService.findAll();
	}

	@GetMapping("/save")
	public JpaUser insert() {
		JpaUser user = new JpaUser();
		user.setName("xkcoding");
		user.setCreateTime(new Date());
		return jpaUserService.insert(user);
	}

	@GetMapping("/update")
	public JpaUser update() {
		JpaUser user = jpaUserService.findById(1L);
		user.setName("修改后的姓名");
		return jpaUserService.update(user);
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		if (id == null) {
			return "Id 不能为空";
		}
		JpaUser jpaUser = jpaUserService.findById(id.longValue());
		if (jpaUser == null) {
			return "用户不存在";
		}
		jpaUserService.delete(jpaUser);
		return jpaUser.getName() + "删除成功";
	}

	@GetMapping("/saveList")
	public List<JpaUser> insertList() {
		List<JpaUser> userList = Lists.newArrayList();
		for (int i = 0; i < 20; i++) {
			JpaUser jpaUser = new JpaUser();
			jpaUser.setName("xkcoding" + i);
			jpaUser.setCreateTime(new Date());
			userList.add(jpaUser);
		}
		return jpaUserService.insertList(userList);
	}

	@GetMapping("/find")
	public JpaUser findJpaUser(@RequestParam String name) {
		return jpaUserService.findJpaUser(name);
	}

	@GetMapping("/find/in")
	public List<JpaUser> findJpaUsersByIdIn() {
		List<Long> ids = Lists.newArrayList(1L, 2L, 3L);
		return jpaUserService.findJpaUsersByIdIn(ids);
	}

	@GetMapping("/page")
	public Page<JpaUser> findByPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
		PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize);
		return jpaUserService.findByPage(pageRequest);
	}
}
