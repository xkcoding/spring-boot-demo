package com.xkcoding.activiti.util;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 认证工具
 * </p>
 *
 * @package: com.xkcoding.activiti.util
 * @description: 认证工具
 * @author: yangkai.shen
 * @date: Created in 2019-07-01 18:38
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SecurityUtil {

	private final UserDetailsService userDetailsService;

	public void logInAs(String username) {

		UserDetails user = userDetailsService.loadUserByUsername(username);
		if (user == null) {
			throw new IllegalStateException("User " + username + " doesn't exist, please provide a valid user");
		}

		SecurityContextHolder.setContext(new SecurityContextImpl(new Authentication() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return user.getAuthorities();
			}

			@Override
			public Object getCredentials() {
				return user.getPassword();
			}

			@Override
			public Object getDetails() {
				return user;
			}

			@Override
			public Object getPrincipal() {
				return user;
			}

			@Override
			public boolean isAuthenticated() {
				return true;
			}

			@Override
			public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
				throw new UnsupportedOperationException();
			}

			@Override
			public String getName() {
				return user.getUsername();
			}
		}));
		org.activiti.engine.impl.identity.Authentication.setAuthenticatedUserId(username);
	}
}
