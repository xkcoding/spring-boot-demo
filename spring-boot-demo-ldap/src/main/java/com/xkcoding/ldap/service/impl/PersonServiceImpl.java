package com.xkcoding.ldap.service.impl;

import com.xkcoding.ldap.entity.Person;
import com.xkcoding.ldap.entity.Result;
import com.xkcoding.ldap.exception.ServiceException;
import com.xkcoding.ldap.repository.PersonRepository;
import com.xkcoding.ldap.request.LoginRequest;
import com.xkcoding.ldap.service.PersonService;
import com.xkcoding.ldap.util.LdapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * PersonServiceImpl
 *
 * @author fxbin
 * @version v1.0
 * @since 2019/8/26 1:05
 */
@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    @Resource
    private PersonRepository personRepository;

    @Override
    public Result login(LoginRequest request) {

        log.info("IN LDAP auth");

        Person user = personRepository.findByUid(request.getUsername());

        try {
            if(ObjectUtils.isEmpty(user)) {
                throw new ServiceException("用户名或密码错误，请重新尝试");
            } else {
                user.setUserPassword(LdapUtils.asciiToString(user.getUserPassword()));
                if (!LdapUtils.verify(user.getUserPassword(), request.getPassword())) {
                    throw new ServiceException("用户名或密码错误，请重新尝试");
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        log.info("user info:{}", user);
        return Result.success(user);
    }

    @Override
    public Result listAllPerson() {
        Iterable<Person> personList = personRepository.findAll();
        personList.forEach(person -> {
            person.setUserPassword(LdapUtils.asciiToString(person.getUserPassword()));
        });
        return Result.success(personList);
    }

    @Override
    public void save(Person person) {
        Person p = personRepository.save(person);
        log.info("用户{}保存成功", p.getUid());
    }

    @Override
    public void delete(Person person) {
        personRepository.delete(person);
        log.info("删除用户{}成功", person.getUid());
    }
}
