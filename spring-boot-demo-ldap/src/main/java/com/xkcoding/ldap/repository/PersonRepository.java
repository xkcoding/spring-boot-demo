package com.xkcoding.ldap.repository;

import com.xkcoding.ldap.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.naming.Name;

/**
 * PersonRepository
 *
 * @author fxbin
 * @version v1.0
 * @since 2019/8/26 1:02
 */
@Repository
public interface PersonRepository extends CrudRepository<Person, Name> {

    /**
     * 根据用户名查找
     *
     * @param uid 用户名
     * @return com.xkcoding.ldap.entity.Person
     */
    Person findByUid(String uid);

    /**
     * 查询全部
     * @return
     */
    @Override
    Iterable<Person> findAll();

    /**
     * 保存
     * @param s
     * @param <S>
     * @return
     */
    @Override
    <S extends Person> S save(S s);

    /**
     * 删除
     * @param person
     */
    @Override
    void delete(Person person);
}
