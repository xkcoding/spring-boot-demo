package com.xkcoding.rbac.security.config;

import com.xkcoding.rbac.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * <p>
 * Security 配置
 * </p>
 *
 * @package: com.xkcoding.rbac.security.config
 * @description: Security 配置
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 16:46
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()

                // 关闭 CSRF
                .and()
                .csrf()
                .disable()

                // 登录行为由自己实现，参考 AuthController#login
                .formLogin()
                .disable()
                .httpBasic()
                .disable()

                // 认证请求
                .authorizeRequests()
                // 放行 /api/auth/** 的所有请求，参见 AuthController
                .antMatchers("/**/api/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                // RBAC 动态 url 认证
                .anyRequest()
                .access("@rbacAuthorityService.hasPermission(request,authentication)")

                // 登出行为由自己实现，参考 AuthController#logout
                .and()
                .logout().disable()

                // Session 管理
                .sessionManagement()
                // 因为使用了JWT，所以这里不管理Session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 异常处理
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);

        // 添加自定义 JWT 过滤器
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
