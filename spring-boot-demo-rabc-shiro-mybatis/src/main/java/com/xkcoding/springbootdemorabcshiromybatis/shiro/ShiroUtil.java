package com.xkcoding.springbootdemorabcshiromybatis.shiro;

import com.xkcoding.springbootdemorabcshiromybatis.constrant.factory.Constant;
import com.xkcoding.springbootdemorabcshiromybatis.constrant.factory.ConstantFactory;
import com.xkcoding.util.T;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import java.util.List;

/**
 * <p>
 * shiro 工具类
 * </p>
 *
 * @package: com.xkcoding.springbootdemorabcshiromybatis.shiro
 * @description： shiro 工具类
 * @author: yangkai.shen
 * @date: Created in 2017/12/1 下午6:02
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
public class ShiroUtil {

	/**
	 * 加盐参数
	 */
	public final static String HASH_ALGORITHM_NAME = "MD5";

	/**
	 * 循环次数
	 */
	public final static int HASH_ITERATIONS = 1;

	/**
	 * shiro密码加密工具类
	 *
	 * @param credentials 密码
	 * @param saltSource  密码盐
	 * @return 加密后的字符串
	 */
	public static String md5(String credentials, String saltSource) {
		ByteSource salt = new Md5Hash(saltSource);
		return new SimpleHash(HASH_ALGORITHM_NAME, credentials, salt, HASH_ITERATIONS).toString();
	}

	/**
	 * 获取随机盐值
	 *
	 * @return 获取随机盐值
	 */
	public static String getRandomSalt() {
		return T.UUID();
	}

	/**
	 * 获取当前 Subject
	 *
	 * @return Subject
	 */
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * 获取封装的 ShiroUser
	 *
	 * @return ShiroUser
	 */
	public static ShiroUser getUser() {
		if (isGuest()) {
			return null;
		} else {
			return (ShiroUser) getSubject().getPrincipals().getPrimaryPrincipal();
		}
	}

	/**
	 * 从shiro获取session
	 */
	public static Session getSession() {
		return getSubject().getSession();
	}

	/**
	 * 获取shiro指定的sessionKey
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSessionAttr(String key) {
		Session session = getSession();
		return session != null ? (T) session.getAttribute(key) : null;
	}

	/**
	 * 设置shiro指定的sessionKey
	 */
	public static void setSessionAttr(String key, Object value) {
		Session session = getSession();
		session.setAttribute(key, value);
	}

	/**
	 * 移除shiro指定的sessionKey
	 */
	public static void removeSessionAttr(String key) {
		Session session = getSession();
		if (session != null) {
			session.removeAttribute(key);
		}
	}

	/**
	 * 验证当前用户是否属于该角色？,使用时与lacksRole 搭配使用
	 *
	 * @param roleName 角色名
	 * @return 属于该角色：true，否则false
	 */
	public static boolean hasRole(String roleName) {
		return getSubject() != null && roleName != null && roleName.length() > 0 && getSubject().hasRole(roleName);
	}

	/**
	 * 与hasRole标签逻辑相反，当用户不属于该角色时验证通过。
	 *
	 * @param roleName 角色名
	 * @return 不属于该角色：true，否则false
	 */
	public static boolean lacksRole(String roleName) {
		return !hasRole(roleName);
	}

	/**
	 * 验证当前用户是否属于以下任意一个角色。
	 *
	 * @param roleNames 角色列表
	 * @return 属于:true,否则false
	 */
	public static boolean hasAnyRoles(String roleNames) {
		boolean hasAnyRole = false;
		Subject subject = getSubject();
		if (subject != null && roleNames != null && roleNames.length() > 0) {
			for (String role : roleNames.split(Constant.COMMA)) {
				if (subject.hasRole(role.trim())) {
					hasAnyRole = true;
					break;
				}
			}
		}
		return hasAnyRole;
	}

	/**
	 * 验证当前用户是否属于以下所有角色。
	 *
	 * @param roleNames 角色列表
	 * @return 属于:true,否则false
	 */
	public static boolean hasAllRoles(String roleNames) {
		boolean hasAllRole = true;
		Subject subject = getSubject();
		if (subject != null && roleNames != null && roleNames.length() > 0) {
			for (String role : roleNames.split(Constant.COMMA)) {
				if (!subject.hasRole(role.trim())) {
					hasAllRole = false;
					break;
				}
			}
		}
		return hasAllRole;
	}

	/**
	 * 验证当前用户是否拥有指定权限,使用时与lacksPermission 搭配使用
	 *
	 * @param permission 权限名
	 * @return 拥有权限：true，否则false
	 */
	public static boolean hasPermission(String permission) {
		return getSubject() != null && permission != null && permission.length() > 0 && getSubject().isPermitted(permission);
	}

	/**
	 * 与hasPermission标签逻辑相反，当前用户没有制定权限时，验证通过。
	 *
	 * @param permission 权限名
	 * @return 拥有权限：true，否则false
	 */
	public static boolean lacksPermission(String permission) {
		return !hasPermission(permission);
	}

	/**
	 * 已认证通过的用户。不包含已记住的用户，这是与user标签的区别所在。与notAuthenticated搭配使用
	 *
	 * @return 通过身份验证：true，否则false
	 */
	public static boolean isAuthenticated() {
		return getSubject() != null && getSubject().isAuthenticated();
	}

	/**
	 * 未认证通过用户，与authenticated标签相对应。与guest标签的区别是，该标签包含已记住用户。。
	 *
	 * @return 没有通过身份验证：true，否则false
	 */
	public static boolean notAuthenticated() {
		return !isAuthenticated();
	}

	/**
	 * 认证通过或已记住的用户。与guset搭配使用。
	 *
	 * @return 用户：true，否则 false
	 */
	public static boolean isUser() {
		return getSubject() != null && getSubject().getPrincipal() != null;
	}

	/**
	 * 验证当前用户是否为“访客”，即未认证（包含未记住）的用户。用user搭配使用
	 *
	 * @return 访客：true，否则false
	 */
	public static boolean isGuest() {
		return !isUser();
	}

	/**
	 * 输出当前用户信息，通常为登录帐号信息。
	 *
	 * @return 当前用户信息
	 */
	public static String principal() {
		if (getSubject() != null) {
			Object principal = getSubject().getPrincipal();
			return principal.toString();
		}
		return "";
	}

	/**
	 * 获取当前用户的部门数据范围的集合
	 */
	public static List<Integer> getDeptDataScope() {
		Integer deptId = getUser().getDeptId();
		List<Integer> subDeptIds = ConstantFactory.me().getSubDeptId(deptId);
		subDeptIds.add(deptId);
		return subDeptIds;
	}

	/**
	 * 判断当前用户是否是超级管理员
	 */
	public static boolean isAdmin() {
		List<String> roleNameList = getUser().getRoleNames();
		for (String roleName : roleNameList) {
			if (roleName.equals(Constant.ADMIN_NAME)) {
				return true;
			}
		}
		return false;
	}

}

