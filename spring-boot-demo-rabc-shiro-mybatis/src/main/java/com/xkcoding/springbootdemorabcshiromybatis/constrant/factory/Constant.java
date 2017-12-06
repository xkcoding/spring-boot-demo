package com.xkcoding.springbootdemorabcshiromybatis.constrant.factory;

import java.util.List;

/**
 * <p>
 * 常量生产工厂的接口
 * </p>
 *
 * @package: com.xkcoding.springbootdemorabcshiromybatis.constrant.factory
 * @description： 常量生产工厂的接口
 * @author: yangkai.shen
 * @date: Created in 2017/12/6 下午3:51
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
public interface Constant {

	String ADMIN_NAME = "超级管理员";

	String COMMA = ",";
	String SEMI = ";";
	String SEPARATE = "|";
	String PATH_SEPARATE = "/";
	String PERCENT = "%";

	/**
	 * 根据用户id获取用户名称
	 *
	 * @param userId 用户id
	 * @return 用户名称
	 */
	String getRealNameById(Integer userId);

	/**
	 * 根据用户id获取用户账号
	 *
	 * @param userId 用户id
	 * @return 用户账号
	 */
	String getUsernameById(Integer userId);

	/**
	 * 根据角色id获取角色名称
	 *
	 * @param roleId 角色id
	 * @return 角色名称
	 */
	String getRoleName(Integer roleId);

	/**
	 * 根据角色id列表获取角色名称列表
	 *
	 * @param roleIds 角色id列表
	 * @return 角色名称列表
	 */
	List<String> getRoleNames(List<Integer> roleIds);

	/**
	 * 根据用户id获取角色id列表
	 *
	 * @param userId 用户id
	 * @return 角色id列表
	 */
	List<Integer> getRoleIds(Integer userId);

	/**
	 * 根据部门id获取部门名称
	 *
	 * @param deptId 部门id
	 * @return 部门名称
	 */
	String getDeptName(Integer deptId);

	/**
	 * 根据权限id获取权限名称
	 *
	 * @param aclId 权限id
	 * @return 权限名称
	 */
	String getAclName(Integer aclId);

	/**
	 * 根据权限编号获取权限名称
	 *
	 * @param code 权限编号
	 * @return 权限名称
	 */
	String getAclNameByCode(String code);

	/**
	 * 根据状态码获取用户登录状态
	 *
	 * @param code 状态码
	 * @return 状态
	 */
	String getUserStatusName(Integer code);

	/**
	 * 根据状态码获取权限状态
	 *
	 * @param code 状态码
	 * @return 状态
	 */
	String getAclStatusName(Integer code);

	/**
	 * 获取子部门id
	 *
	 * @param deptId 当前部门id
	 * @return 所有子部门id
	 */
	List<Integer> getSubDeptId(Integer deptId);

	/**
	 * 获取所有父部门id
	 *
	 * @param deptId 当前部门id
	 * @return 所有父部门id
	 */
	List<Integer> getParentDeptIds(Integer deptId);

}
