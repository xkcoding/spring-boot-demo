package com.xkcoding.springbootdemorabcshiromybatis.config;

import com.google.common.collect.Maps;
import com.xkcoding.springbootdemorabcshiromybatis.shiro.MyShiroRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Shiro 配置
 *
 * @package: com.xkcoding.springbootdemorabcshiromybatis.config
 * @description： Shiro 配置
 * @author: yangkai.shen
 * @date: Created in 2017/11/29 下午3:24
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Configuration
@Slf4j
public class ShiroConfig {

	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 必须设置 securityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		// 设置登录页面，默认是 Web 工程目录下的"/login.jsp"
		shiroFilterFactoryBean.setLoginUrl("/login");
		// 设置登陆成功后的页面
		shiroFilterFactoryBean.setSuccessUrl("/index");
		// 设置未授权页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");

		// 配置拦截器链，注意配置的顺序，很关键
		// 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边
		// authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
		Map<String, String> filterChainDefinitionMap = Maps.newLinkedHashMap();
		// 配置可以被匿名访问的地址
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/ajaxLogin", "anon");
		// 配置 logout，登出部分逻辑由 shiro 为我们实现
		filterChainDefinitionMap.put("/logout", "logout");
		// 配置自定义权限
		filterChainDefinitionMap.put("/add", "perms[权限添加]");
		filterChainDefinitionMap.put("/**", "authc");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		log.info("ShiroFilterFactoryBean 注入成功");

		return shiroFilterFactoryBean;
	}

	@Bean
	public SecurityManager securityManager(MyShiroRealm myShiroRealm) {
		DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
		// 设置 Realm
		// 在 shiro 中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
		// 通常情况下，在 Realm 中会直接从我们的数据源中获取 shiro 需要的验证信息。
		// 可以说，Realm 是专用于安全框架的 DAO。
		defaultSecurityManager.setRealm(myShiroRealm);

		return defaultSecurityManager;
	}

	@Bean
	public MyShiroRealm myShiroRealm() {
		return new MyShiroRealm();
	}
}
