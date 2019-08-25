package com.xkcoding.ldap.service;

import com.xkcoding.ldap.entity.Person;
import com.xkcoding.ldap.entity.Result;
import com.xkcoding.ldap.request.LoginRequest;

/**
 * PersonService
 *
 * @author fxbin
 * @version v1.0
 * @since 2019/8/26 1:05
 */
public interface PersonService {

    /**
     * 登录
     * @param request com.xkcoding.ldap.request.LoginRequest
     * @return com.xkcoding.ldap.entity.Result
     */
    Result login(LoginRequest request);

    /**
     * 查询全部
     * @return com.xkcoding.ldap.entity.Result
     */
    Result listAllPerson();

    /**
     * 保存
     * @param person com.xkcoding.ldap.entity.Person
     */
    void save(Person person);

    /**
     * 删除
     * @param person com.xkcoding.ldap.entity.Person
     */
    void delete(Person person);

}
