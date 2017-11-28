package com.xkcoding.springbootdemoormmybatis.controller;

import com.google.common.collect.Lists;
import com.xkcoding.springbootdemoormmybatis.model.MybatisUser;
import com.xkcoding.springbootdemoormmybatis.service.MybatisUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class MybatisUserController {

	@Autowired
	private MybatisUserService mybatisUserService;

	@GetMapping("/save")
	public MybatisUser save() {
		MybatisUser mybatisUser = new MybatisUser();
		mybatisUser.setName("xkcoding");
		mybatisUser.setTel("18600000000");
		mybatisUser.setCreateTime(new Date());
		return mybatisUserService.save(mybatisUser);
	}

	@GetMapping("/saveList")
	public List<MybatisUser> saveList() {
		ArrayList<MybatisUser> userList = Lists.newArrayList();
		for (int i = 0; i < 20; i++) {
			MybatisUser mybatisUser = new MybatisUser();
			mybatisUser.setName("xkcoding" + i);
			mybatisUser.setTel("186111111" + ((i < 10) ? ("0" + i) : i));
			mybatisUser.setCreateTime(new Date());
			userList.add(mybatisUser);
		}
		return mybatisUserService.saveList(userList);
	}

	@GetMapping("/update")
	public MybatisUser update() {
		MybatisUser mybatisUser = new MybatisUser();
		mybatisUser.setId(2L);
		mybatisUser.setName(" 修改后的名字 ");
		mybatisUserService.update(mybatisUser);

		return mybatisUserService.update(mybatisUser);
	}

	@GetMapping("/delete")
	public MybatisUser delete() {
		MybatisUser mybatisUser = new MybatisUser();
		mybatisUser.setId(1L);
		mybatisUserService.delete(mybatisUser);

		return mybatisUserService.findById(1L);
	}

	@GetMapping("/find/{id}")
	public MybatisUser findById(@PathVariable Long id) {
		return mybatisUserService.findById(id);
	}

	@GetMapping("/find")
	public MybatisUser findByName(@RequestParam String name) {
		return mybatisUserService.findByName(name);
	}

	@GetMapping({"", "/"})
	public List<MybatisUser> findAll() {
		return mybatisUserService.findAll();
	}

	@GetMapping("/page")
	public List<MybatisUser> findByPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {
		return mybatisUserService.findMybatisUserByPage(pageNum, pageSize);
	}
}
