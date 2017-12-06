package com.xkcoding.springbootdemorabcshiromybatis.shiro.factory;

import com.xkcoding.springbootdemorabcshiromybatis.constrant.factory.ConstantFactory;
import com.xkcoding.springbootdemorabcshiromybatis.dao.MybatisShiroAclMapper;
import com.xkcoding.springbootdemorabcshiromybatis.dao.MybatisShiroUserMapper;
import com.xkcoding.springbootdemorabcshiromybatis.enums.UserStatusEnum;
import com.xkcoding.springbootdemorabcshiromybatis.model.MybatisShiroUser;
import com.xkcoding.springbootdemorabcshiromybatis.shiro.ShiroUser;
import com.xkcoding.springbootdemorabcshiromybatis.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DependsOn("springContextHolder")
@Slf4j
public class ShiroFactroy implements Shiro {

	@Autowired
	private MybatisShiroUserMapper userMapper;

	@Autowired
	private MybatisShiroAclMapper aclMapper;

	public static Shiro me() {
		return SpringContextHolder.getBean(Shiro.class);
	}

	@Override
	public MybatisShiroUser user(String username) {

		MybatisShiroUser user = userMapper.findByUsername(username);

		// 账号不存在
		if (null == user) {
			log.error("【登录失败】：账号不存在");
			throw new CredentialsException("账号不存在");
		}
		// 账号被冻结
		if (user.getStatus().equals(UserStatusEnum.DISABLE.getCode())) {
			log.error("【登录失败】：用户已被冻结");
			throw new DisabledAccountException("登录失败，用户已被冻结");
		} else if (user.getStatus().equals(UserStatusEnum.DELETED.getCode())) {
			log.error("【登录失败】：没有该用户");
			throw new DisabledAccountException("登录失败，没有该用户");
		}
		return user;
	}

	@Override
	public ShiroUser shiroUser(MybatisShiroUser user) {
		ShiroUser shiroUser = new ShiroUser();

		shiroUser.setId(user.getId());
		shiroUser.setUsername(user.getUsername());
		shiroUser.setDeptId(user.getDeptId());
		shiroUser.setDeptName(ConstantFactory.me().getDeptName(user.getDeptId()));
		shiroUser.setRealname(user.getRealname());

		List<Integer> roleList = ConstantFactory.me().getRoleIds(user.getId());
		List<String> roleNameList = ConstantFactory.me().getRoleNames(roleList);
		shiroUser.setRoleList(roleList);
		shiroUser.setRoleNames(roleNameList);

		return shiroUser;
	}

	@Override
	public List<String> findAclsByRoleId(Integer roleId) {
		return aclMapper.getResUrlsByRoleId(roleId);
	}

	@Override
	public String findRoleNameByRoleId(Integer roleId) {
		return ConstantFactory.me().getRoleName(roleId);
	}

	@Override
	public SimpleAuthenticationInfo info(ShiroUser shiroUser, MybatisShiroUser user, String realmName) {
		String credentials = user.getPassword();
		// 密码加盐处理
		String source = user.getSalt();
		ByteSource credentialsSalt = new Md5Hash(source);
		return new SimpleAuthenticationInfo(shiroUser, credentials, credentialsSalt, realmName);
	}

}
