package com.xkcoding.springbootdemorabcshiromybatis.shiro.factory;

import com.xkcoding.springbootdemorabcshiromybatis.model.MybatisShiroUser;
import com.xkcoding.springbootdemorabcshiromybatis.shiro.ShiroUser;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

import java.util.List;

/**
 * <p>
 * 定义 shiro realm 所需数据的接口
 * </p>
 *
 * @package: com.xkcoding.springbootdemorabcshiromybatis.shiro.factory
 * @description： 定义 shiro realm 所需数据的接口
 * @author: yangkai.shen
 * @date: Created in 2017/12/6 下午3:24
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
public interface Shiro {

	/**
	 * 根据用户名数据库存储的用户信息
	 *
	 * @param username 用户名
	 * @return 数据库存储的用户信息
	 */
	MybatisShiroUser user(String username);

	/**
	 * 根据系统用户获取 shiro 的用户
	 *
	 * @param user 数据库保存的用户
	 * @return 自定义的用户对象
	 */
	ShiroUser shiroUser(MybatisShiroUser user);

	/**
	 * 根据角色id获取权限列表
	 *
	 * @param roleId 角色id
	 * @return 权限列表
	 */
	List<String> findAclsByRoleId(Integer roleId);

	/**
	 * 根据角色id获取角色名称
	 *
	 * @param roleId 角色id
	 * @return 角色名称
	 */
	String findRoleNameByRoleId(Integer roleId);

	/**
	 * 获取 shiro 的认证信息
	 *
	 * @param shiroUser 自定义返回的 user 对象
	 * @param user      数据库保存的 user 对象
	 * @param realmName 真实姓名
	 * @return shiro的认证信息
	 */
	SimpleAuthenticationInfo info(ShiroUser shiroUser, MybatisShiroUser user, String realmName);

}
