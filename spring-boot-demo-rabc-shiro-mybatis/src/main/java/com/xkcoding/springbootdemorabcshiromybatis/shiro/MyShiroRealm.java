package com.xkcoding.springbootdemorabcshiromybatis.shiro;

import com.google.common.collect.Sets;
import com.xiaoleilu.hutool.util.StrUtil;
import com.xkcoding.springbootdemorabcshiromybatis.dao.MybatisShiroUserMapper;
import com.xkcoding.springbootdemorabcshiromybatis.model.MybatisShiroUser;
import com.xkcoding.springbootdemorabcshiromybatis.shiro.factory.Shiro;
import com.xkcoding.springbootdemorabcshiromybatis.shiro.factory.ShiroFactroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * shiro 身份校验
 *
 * @package: com.xkcoding.springbootdemorabcshiromybatis.shiro
 * @description： shiro 身份校验
 * @author: yangkai.shen
 * @date: Created in 2017/11/29 下午3:39
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {
	@Autowired
	private MybatisShiroUserMapper mybatisShiroUserMapper;

	/**
	 * 身份认证： Authentication 用来验证用户信息
	 *
	 * @param authenticationToken
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		log.info("【身份认证】：进入doGetAuthenticationInfo()");

		Shiro shiroFactory = ShiroFactroy.me();
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		MybatisShiroUser user = shiroFactory.user(token.getUsername());
		ShiroUser shiroUser = shiroFactory.shiroUser(user);
		SimpleAuthenticationInfo info = shiroFactory.info(shiroUser, user, super.getName());
		return info;

	}

	/**
	 * 授权验证
	 *
	 * @param principalCollection
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		log.info("【授权验证】：进入doGetAuthorizationInfo()");
		Shiro shiroFactory = ShiroFactroy.me();
		ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
		List<Integer> roleList = shiroUser.getRoleList();

		Set<String> aclSet = Sets.newHashSet();
		Set<String> roleNameSet = Sets.newHashSet(shiroUser.getRoleNames());

		for (Integer roleId : roleList) {
			List<String> acls = shiroFactory.findAclsByRoleId(roleId);
			for (String acl : acls) {
				if (StrUtil.isNotEmpty(acl)) {
					aclSet.add(acl);
				}
			}
		}

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(aclSet);
		info.addRoles(roleNameSet);
		return info;
	}

	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
		md5CredentialsMatcher.setHashAlgorithmName(ShiroUtil.HASH_ALGORITHM_NAME);
		md5CredentialsMatcher.setHashIterations(ShiroUtil.HASH_ITERATIONS);
		super.setCredentialsMatcher(md5CredentialsMatcher);
	}
}
