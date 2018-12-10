package com.xkcoding.rbac.security.config;

import cn.hutool.core.util.StrUtil;
import com.xkcoding.rbac.security.common.Status;
import com.xkcoding.rbac.security.exception.SecurityException;
import com.xkcoding.rbac.security.service.CustomUserDetailsService;
import com.xkcoding.rbac.security.util.JwtUtil;
import com.xkcoding.rbac.security.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * Jwt 认证过滤器
 * </p>
 *
 * @package: com.xkcoding.rbac.security.config
 * @description: Jwt 认证过滤器
 * @author: yangkai.shen
 * @date: Created in 2018-12-10 15:15
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        if (antPathMatcher.match("/**/api/auth/**", request.getRequestURI())) {
            filterChain.doFilter(request, response);
        } else {
            String jwt = getJwtFromRequest(request);

            if (StrUtil.isNotBlank(jwt)) {
                try {
                    String username = jwtUtil.getUsernameFromJWT(jwt);

                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                } catch (SecurityException e) {
                    ResponseUtil.renderJson(response, e);
                }
            } else {
                ResponseUtil.renderJson(response, Status.UNAUTHORIZED, null);
            }
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StrUtil.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
