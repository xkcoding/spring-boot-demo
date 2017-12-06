package com.xkcoding.springbootdemorabcshiromybatis.constrant.factory;

import com.google.common.collect.Lists;
import com.xiaoleilu.hutool.util.StrUtil;
import com.xkcoding.springbootdemorabcshiromybatis.dao.*;
import com.xkcoding.springbootdemorabcshiromybatis.enums.AclStatusEnum;
import com.xkcoding.springbootdemorabcshiromybatis.enums.UserStatusEnum;
import com.xkcoding.springbootdemorabcshiromybatis.model.*;
import com.xkcoding.springbootdemorabcshiromybatis.util.SpringContextHolder;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 常量的生产工厂
 * </p>
 *
 * @package: com.xkcoding.springbootdemorabcshiromybatis.constrant.factory
 * @description： 常量的生产工厂
 * @author: yangkai.shen
 * @date: Created in 2017/12/6 下午4:01
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Component
@DependsOn("springContextHolder")
public class ConstantFactory implements Constant {

	private MybatisShiroAclMapper aclMapper = SpringContextHolder.getBean(MybatisShiroAclMapper.class);
	private MybatisShiroDeptMapper deptMapper = SpringContextHolder.getBean(MybatisShiroDeptMapper.class);
	private MybatisShiroUserMapper userMapper = SpringContextHolder.getBean(MybatisShiroUserMapper.class);
	private MybatisShiroRoleMapper roleMapper = SpringContextHolder.getBean(MybatisShiroRoleMapper.class);
	private MybatisShiroRoleUserMapper roleUserMapper = SpringContextHolder.getBean(MybatisShiroRoleUserMapper.class);


	public static Constant me() {
		return SpringContextHolder.getBean("constantFactory");
	}

	@Override
	public String getRealNameById(Integer userId) {
		MybatisShiroUser user = userMapper.selectByPrimaryKey(userId);
		if (user != null) {
			return user.getRealname();
		}
		return null;
	}

	@Override
	public String getUsernameById(Integer userId) {
		MybatisShiroUser user = userMapper.selectByPrimaryKey(userId);
		if (user != null && StrUtil.isNotEmpty(user.getUsername())) {
			return user.getUsername();
		}
		return null;
	}

	@Override
	public String getRoleName(Integer roleId) {
		MybatisShiroRole role = roleMapper.selectByPrimaryKey(roleId);
		if (role != null && StrUtil.isNotEmpty(role.getName())) {
			return role.getName();
		}
		return null;
	}

	@Override
	public List<String> getRoleNames(List<Integer> roleIds) {
		return roleIds.stream().map(id -> getRoleName(id)).collect(Collectors.toList());
	}

	@Override
	public List<Integer> getRoleIds(Integer userId) {
		MybatisShiroRoleUser param = new MybatisShiroRoleUser();
		param.setUserId(userId);
		List<MybatisShiroRoleUser> roleUser = roleUserMapper.select(param);
		return roleUser.stream().map(v -> v.getRoleId()).collect(Collectors.toList());
	}

	@Override
	public String getDeptName(Integer deptId) {
		MybatisShiroDept dept = deptMapper.selectByPrimaryKey(deptId);
		if (dept != null && StrUtil.isNotEmpty(dept.getName())) {
			return dept.getName();
		}
		return null;
	}

	@Override
	public String getAclName(Integer aclId) {
		MybatisShiroAcl acl = aclMapper.selectByPrimaryKey(aclId);
		if (acl != null && StrUtil.isNotEmpty(acl.getName())) {
			return acl.getName();
		}
		return null;
	}

	@Override
	public String getAclNameByCode(String code) {
		if (StrUtil.isNotBlank(code)) {
			return null;
		} else {
			MybatisShiroAcl param = new MybatisShiroAcl();
			param.setCode(code);
			MybatisShiroAcl acl = aclMapper.selectOne(param);
			if (acl != null && StrUtil.isNotEmpty(acl.getName())) {
				return acl.getName();
			}
			return null;
		}
	}

	@Override
	public String getUserStatusName(Integer code) {
		return UserStatusEnum.valueOf(code);
	}

	@Override
	public String getAclStatusName(Integer code) {
		return AclStatusEnum.valueOf(code);
	}

	@Override
	public List<Integer> getSubDeptId(Integer deptId) {
		Example example = new Example(MybatisShiroDept.class);
		example.createCriteria().andLike("level", "%" + deptId + "%");

		List<MybatisShiroDept> deptList = deptMapper.selectByExample(example);

		ArrayList<Integer> deptIds = Lists.newArrayList();

		if (deptList != null || deptList.size() > 0) {
			for (MybatisShiroDept dept : deptList) {
				deptIds.add(dept.getId());
			}
		}

		return deptIds;
	}

	@Override
	public List<Integer> getParentDeptIds(Integer deptId) {
		MybatisShiroDept dept = deptMapper.selectByPrimaryKey(deptId);
		String level = dept.getLevel();
		ArrayList<Integer> parentIds = Lists.newArrayList();
		for (String parentId : level.split(COMMA)) {
			parentIds.add(Integer.valueOf(parentId));
		}
		return parentIds;
	}
}
