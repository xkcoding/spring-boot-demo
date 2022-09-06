package com.xkcoding.ldap.service;

import com.xkcoding.ldap.api.Result;
import com.xkcoding.ldap.entity.Person;
import com.xkcoding.ldap.request.LoginRequest;

/**
 * PersonService
 *
 * @author fxbin
 * @version v1.0
 * @since 2019-08-26 1:05
 */
public interface PersonService {

    /**
     * 登录
     *
     * @param request {@link LoginRequest}
     * @return {@link Result}
     */
    Result login(LoginRequest request);

    /**
     * 查询全部
     *
     * @return {@link Result}
     */
    Result listAllPerson();

    /**
     * 保存
     *
     * @param person {@link Person}
     */
    void save(Person person);

    /**
     * 删除
     *
     * @param person {@link Person}
     */
    void delete(Person person);

}
