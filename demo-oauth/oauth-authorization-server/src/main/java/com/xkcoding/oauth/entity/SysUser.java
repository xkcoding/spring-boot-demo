package com.xkcoding.oauth.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Set;

/**
 * 用户实体.
 * 避免实体类耦合，所以不去实现 {@link UserDetails} 接口
 * 因为有且只有登录加载用户的时候才会需要这个接口
 * 我们就手动构建一个 {@link User} 的默认实现就可以了
 * 实现接口的方式可以参考 {@link SysClientDetails}
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/6 下午12:41
 */
@Data
@Table
@Entity
@EqualsAndHashCode(exclude = "roles")
@ToString(exclude = "roles")
public class SysUser {

    /**
     * 主键.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名.
     */
    private String username;

    /**
     * 密码.
     */
    private String password;

    /**
     * 当前用户所有角色.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<SysRole> roles;
}
