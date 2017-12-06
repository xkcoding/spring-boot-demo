package com.xkcoding.springbootdemorabcshiromybatis.dao;

import com.xkcoding.springbootdemorabcshiromybatis.model.MybatisShiroAcl;
import com.xkcoding.springbootdemorabcshiromybatis.util.MyMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MybatisShiroAclMapper extends MyMapper<MybatisShiroAcl> {
	/**
	 * 根据角色id获取权限列表
	 *
	 * @param roleId 角色id
	 * @return 权限列表
	 */
	List<String> getResUrlsByRoleId(Integer roleId);
}