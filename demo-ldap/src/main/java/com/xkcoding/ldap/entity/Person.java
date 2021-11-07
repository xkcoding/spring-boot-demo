package com.xkcoding.ldap.entity;

import lombok.Data;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;
import java.io.Serializable;

/**
 * People
 *
 * @author fxbin
 * @version v1.0
 * @since 2019-08-26 0:51
 */
@Data
@Entry(base = "ou=people", objectClasses = {"posixAccount", "inetOrgPerson", "top"})
public class Person implements Serializable {

    private static final long serialVersionUID = -7946768337975852352L;

    @Id
    private Name id;

    /**
     * 用户id
     */
    private String uidNumber;

    /**
     * 用户名
     */
    @DnAttribute(value = "uid", index = 1)
    private String uid;

    /**
     * 姓名
     */
    @Attribute(name = "cn")
    private String personName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 名字
     */
    private String givenName;

    /**
     * 姓氏
     */
    @Attribute(name = "sn")
    private String surname;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 职位
     */
    private String title;

    /**
     * 部门
     */
    private String departmentNumber;

    /**
     * 部门id
     */
    private String gidNumber;

    /**
     * 根目录
     */
    private String homeDirectory;

    /**
     * loginShell
     */
    private String loginShell;


}
