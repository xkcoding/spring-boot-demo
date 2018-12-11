package com.xkcoding.rbac.security.config;

import cn.hutool.core.util.StrUtil;
import com.xkcoding.rbac.security.common.Consts;
import com.xkcoding.rbac.security.model.Permission;
import com.xkcoding.rbac.security.model.Role;
import com.xkcoding.rbac.security.repository.PermissionDao;
import com.xkcoding.rbac.security.repository.RoleDao;
import com.xkcoding.rbac.security.vo.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 动态路由认证
 * </p>
 *
 * @package: com.xkcoding.rbac.security.config
 * @description: 动态路由认证
 * @author: yangkai.shen
 * @date: Created in 2018-12-10 17:17
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Component
public class RbacAuthorityService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object userInfo = authentication.getPrincipal();
        boolean hasPermission = false;

        if (userInfo instanceof UserDetails) {
            UserPrincipal principal = (UserPrincipal) userInfo;
            Long userId = principal.getId();

            List<Role> roles = roleDao.selectByUserId(userId);
            List<Long> roleIds = roles.stream()
                    .map(Role::getId)
                    .collect(Collectors.toList());
            List<Permission> permissions = permissionDao.selectByRoleIdList(roleIds);

            //获取资源，前后端分离，所以过滤页面权限，只保留按钮权限
            List<Permission> btnPerms = permissions.stream()
                    // 过滤页面权限
                    .filter(permission -> Objects.equals(permission.getType(), Consts.BUTTON))
                    // 过滤 URL 为空
                    .filter(permission -> StrUtil.isNotBlank(permission.getUrl()))
                    // 过滤 METHOD 为空
                    .filter(permission -> StrUtil.isNotBlank(permission.getMethod()))
                    .collect(Collectors.toList());

            for (Permission btnPerm : btnPerms) {
                AntPathRequestMatcher antPathMatcher = new AntPathRequestMatcher(btnPerm.getUrl(), btnPerm.getMethod());
                if (antPathMatcher.matches(request)) {
                    hasPermission = true;
                    break;
                }
            }

            return hasPermission;
        } else {
            return false;
        }
    }
}