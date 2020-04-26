package com.xkcoding.sso.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/13 17:50
 * @description
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private static Map<String, User> USER_DB = new HashMap<>();


    static {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        USER_DB.put("user", new User("user", passwordEncoder.encode("111"),
            AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER")));
        USER_DB.put("admin", new User("admin", passwordEncoder.encode("222"),
            AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN")));
        USER_DB.put("super", new User("super", passwordEncoder.encode("333"),
            AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN,ROLE_USER")));

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中查询
        User user = USER_DB.get(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("用户%s不存在", username));
        }
        return user;
    }


}
