package com.xkcoding.springbootdemoormjdbctemplate.controller;

import com.xiaoleilu.hutool.util.StrUtil;
import com.xkcoding.springbootdemoormjdbctemplate.model.User;
import com.xkcoding.springbootdemoormjdbctemplate.service.UserService;
import com.xkcoding.springbootdemoormjdbctemplate.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * UserController
 * </p>
 *
 * @package: com.xkcoding.springbootdemoormjdbctemplate.controller
 * @description： UserController
 * @author: yangkai.shen
 * @date: Created in 2018/5/11 下午3:52
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/save")
	public R save() {
		User user = new User();
		user.setName("xkcoding");
		user.setTel("18600000000");
		user.setCreateTime(new Date());
		Integer save = userService.save(user);
		if (save > 0) {
			return R.builder().code(200).msg("添加成功").data(user).build();
		}
		return R.builder().code(500).msg("添加失败").data(null).build();
	}

	@GetMapping("/update")
	public R update() {
		User user = new User();
		user.setId(2);
		user.setName(" 修改后的名字 ");
		user.setTel("17300000000");
		Integer update = userService.update(user);
		if (update > 0) {
			return R.builder().code(200).msg("修改成功").data(userService.findById(2)).build();
		}
		return R.builder().code(500).msg("修改失败").data(null).build();
	}

	@GetMapping("/delete")
	public R delete() {
		User user = new User();
		user.setId(1);
		Integer delete = userService.delete(user);

		if (delete > 0) {
			return R.builder().code(200).msg("删除成功").data(null).build();
		}
		return R.builder().code(500).msg("删除失败").data(null).build();
	}

	@GetMapping("/find/{id}")
	public R findById(@PathVariable Integer id) {
		User user = userService.findById(id);
		return R.builder().code(user == null ? 404 : 200).msg(user == null ? StrUtil.format("没有ID为{}的用户", id) : "查询成功").data(user).build();
	}

	@GetMapping("/find")
	public R findByName(@RequestParam String name) {
		User user = userService.findByName(name);
		return R.builder().code(user == null ? 404 : 200).msg(user == null ? StrUtil.format("没有姓名为{}的用户", name) : "查询成功").data(user).build();
	}

	@GetMapping({"", "/"})
	public R findAll() {
		List<User> userList = userService.findAll();
		return R.builder().code(userList.isEmpty() ? 404 : 200).msg(userList.isEmpty() ? "暂无用户" : "查询成功").data(userList).build();
	}

	@GetMapping("/page")
	public R findByPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {
		List<User> userList = userService.findUserByPage(pageNum, pageSize);
		return R.builder().code(userList.isEmpty() ? 404 : 200).msg(userList.isEmpty() ? "暂无用户" : "查询成功").data(userList).build();
	}
}
