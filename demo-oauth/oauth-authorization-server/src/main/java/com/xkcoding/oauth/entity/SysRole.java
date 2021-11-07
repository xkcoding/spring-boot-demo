package com.xkcoding.oauth.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

/**
 * 这里完全可以只用一个字段代替的
 * 但是想了想还是模拟实际的情况来把
 * 角色信息.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020-01-06 12:44
 */
@Data
@Table
@Entity
@EqualsAndHashCode(exclude = {"users"})
@ToString(exclude = "users")
public class SysRole {

    /**
     * 主键.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色名称，按照 spring security 规范
     * 需要以 ROLE_ 开头.
     */
    private String name;

    /**
     * 角色描述.
     */
    private String description;

    /**
     * 当前角色所有用户.
     */
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<SysUser> users;
}
