package com.xkcoding.rbac.security.repository;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import com.xkcoding.rbac.security.SpringBootDemoRbacSecurityApplicationTests;
import com.xkcoding.rbac.security.model.*;
import com.xkcoding.rbac.security.model.unionkey.RolePermissionKey;
import com.xkcoding.rbac.security.model.unionkey.UserRoleKey;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <p>
 * 数据初始化测试
 * </p>
 *
 * @package: com.xkcoding.rbac.security.repository
 * @description: 数据初始化测试
 * @author: yangkai.shen
 * @date: Created in 2018-12-10 11:26
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public class DataInitTest extends SpringBootDemoRbacSecurityApplicationTests {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Autowired
    private Snowflake snowflake;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    public void initTest() {
        init();
    }

    private void init() {
        User admin = createUser(true);
        User user = createUser(false);

        Role roleAdmin = createRole(true);
        Role roleUser = createRole(false);

        createUserRoleRelation(admin.getId(), roleAdmin.getId());
        createUserRoleRelation(user.getId(), roleUser.getId());

        // 页面权限
        Permission pagePerm = createPermission("/test", "测试页面", 1, null, 1, 0L);
        // 按钮权限
        Permission btnQueryPerm = createPermission(null, "测试页面-查询", 2, "test:query", 1, pagePerm.getId());
        Permission btnPermInsert = createPermission(null, "测试页面-添加", 2, "test:insert", 2, pagePerm.getId());

        createRolePermissionRelation(roleAdmin.getId(), pagePerm.getId());
        createRolePermissionRelation(roleUser.getId(), pagePerm.getId());
        createRolePermissionRelation(roleAdmin.getId(), btnQueryPerm.getId());
        createRolePermissionRelation(roleUser.getId(), btnQueryPerm.getId());
        createRolePermissionRelation(roleAdmin.getId(), btnPermInsert.getId());
    }

    private void createRolePermissionRelation(Long roleId, Long permissionId) {
        RolePermission adminPage = new RolePermission();
        RolePermissionKey adminPageKey = new RolePermissionKey();
        adminPageKey.setRoleId(roleId);
        adminPageKey.setPermissionId(permissionId);
        adminPage.setId(adminPageKey);
        rolePermissionDao.save(adminPage);
    }

    private Permission createPermission(String href, String name, Integer type, String permission, Integer sort, Long parentId) {
        // 页面权限
        Permission perm = new Permission();
        perm.setId(snowflake.nextId());
        perm.setHref(href);
        perm.setName(name);
        perm.setType(type);
        perm.setPermission(permission);
        perm.setSort(sort);
        perm.setParentId(parentId);
        permissionDao.save(perm);
        return perm;
    }

    private void createUserRoleRelation(Long userId, Long roleId) {
        UserRole userRole = new UserRole();
        UserRoleKey key = new UserRoleKey();
        key.setUserId(userId);
        key.setRoleId(roleId);
        userRole.setId(key);
        userRoleDao.save(userRole);
    }

    private Role createRole(boolean isAdmin) {
        Role role = new Role();
        role.setId(snowflake.nextId());
        role.setName(isAdmin ? "管理员" : "普通用户");
        role.setDescription(isAdmin ? "超级管理员" : "普通用户");
        role.setCreateTime(DateUtil.current(false));
        role.setUpdateTime(DateUtil.current(false));
        roleDao.save(role);
        return role;
    }

    private User createUser(boolean isAdmin) {
        User user = new User();
        user.setId(snowflake.nextId());
        user.setUsername(isAdmin ? "admin" : "user");
        user.setNickname(isAdmin ? "管理员" : "普通用户");
        user.setPassword(encoder.encode("123456"));
        user.setBirthday(DateTime.of("1994-11-22", "yyyy-MM-dd").getTime());
        user.setEmail((isAdmin ? "admin" : "user") + "@xkcoding.com");
        user.setPhone(isAdmin ? "17300000000" : "17300001111");
        user.setSex(1);
        user.setStatus(1);
        user.setCreateTime(DateUtil.current(false));
        user.setUpdateTime(DateUtil.current(false));
        userDao.save(user);
        return user;
    }

}
