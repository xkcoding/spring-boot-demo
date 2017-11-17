package com.xkcoding.springbootdemoswagger.controller;

import com.google.common.collect.Lists;
import com.xiaoleilu.hutool.util.StrUtil;
import com.xkcoding.springbootdemoswagger.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

	@ApiOperation("新增用户")
	@PostMapping({"", "/"})
	public User insert(@RequestBody @Valid User user) {
		return user;
	}

	@ApiIgnore
	@DeleteMapping("/{id}")
	public String deleteById(@PathVariable Long id) {
		return "已删除用户 --> " + id;
	}

	@ApiOperation("修改用户详情")
	@PutMapping("/{id}")
	public User update(@PathVariable Long id, @RequestBody @Valid User user) {
		user.setId(id);
		return user;
	}

	@ApiOperation("用户详情")
	@GetMapping("/{id}")
	public User findById(@PathVariable Long id) {
		return new User(id, "xkcoding" + id, 21, StrUtil.format("xkcoding{}@163.com", id), StrUtil.fill("186", id.toString().charAt(0), 11, false));
	}

	@ApiOperation("用户列表")
	@GetMapping({"", "/"})
	public List<User> index(@ApiParam("第几页") @RequestParam(defaultValue = "1") Integer pageNum, @ApiParam("每页的条目数") @RequestParam(defaultValue = "20") Integer pageSize) {
		List<User> users = Lists.newArrayList();
		users.add(new User(0L, "xkcoding0", 18, "xkcoding0@163.com", "18600000000"));
		users.add(new User(1L, "xkcoding1", 19, "xkcoding1@163.com", "18611111111"));
		return users;
	}

}
