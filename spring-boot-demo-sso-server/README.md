


## 单点登录系统示例(sso)

> 使用 `oauth2` `spring security` 一个授权服务器 + 两个 客户端

流程:
1. 客户端1 , 客户端2 不同权限路径
    - `/free`  无需授权
    - `/user`  用户权限
    - `/admin` 管理员权限
2. 访问客户端1`/free` 正常访问
3. 访问客户端1`/user` 获取授权信息
    1. 未登录跳转授权服务器登录
    2. 登录有权限 正常访问 , 无权限 拒绝
4. 访问客户端2`/user` 如有权限自动登录
5. `/admin` 同理  , 客户端1 客户端2 访问顺序可随意调换 , 实现 一处登录处处访问


### 服务端

服务器端关键pom如下

```xml

    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-oauth2</artifactId>
      <version>2.0.1.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

```

**注意** 不同版本spring boot 对 `spring-cloud-starter-oauth2` 有不兼容情况 可以使用`spring-cloud-dependencies` 管理


1. 配置授权服务器 客户端


```java

package com.xkcoding.sso.config;

import jdk.internal.dynalink.support.NameCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/13 17:50
 * @description 配置授权认证
 */

@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties(SsoClientProperties.class)
public class SsoAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    // 引入 配置文件 也可以写死  正常应该是写在数据库中
    @Autowired
    private SsoClientProperties ssoClientProperties;

    /**
     * 客户端一些配置
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 从配置文件中读取 客户端授权信息 相当于把要接入单点系统的客户端id和密钥等信息存进来并设定权限
        ClientDetailsServiceBuilder<InMemoryClientDetailsServiceBuilder> memory = clients.inMemory();
        List<SsoClientProperties.SsoClient> client = ssoClientProperties.getClient();
        for (SsoClientProperties.SsoClient c : client) {
            try {
                memory = memory.withClient(c.getClientName())
                    .secret(passwordEncoder.encode(c.getClientPassword()))
                    .authorizedGrantTypes("authorization_code", "refresh_token")
                    .scopes("all")
                    .redirectUris(c.getUri())
                    .autoApprove(false).and();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 也可以使用数据库动态配置 , 如userDetail 一样
        // clients.withClientDetails()
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(jwtTokenStore()).accessTokenConverter(jwtAccessTokenConverter());
        DefaultTokenServices tokenServices = (DefaultTokenServices) endpoints.getDefaultAuthorizationServerTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.MINUTES.toSeconds(1));
        endpoints.tokenServices(tokenServices);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("isAuthenticated()");
    }

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("setSigningKey");
        return converter;
    }

}



```


2. 然后配置一下spring security

```java

package com.xkcoding.sso.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/13 17:50
 * @description 配置 spring security
 */
@Configuration
public class SsoSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setHideUserNotFoundExceptions(false);
        return authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .requestMatchers().antMatchers("/oauth/**", "/login/**", "/logout/**")
            .and()
            .authorizeRequests()
            .antMatchers("/oauth/**").authenticated()
            .and()
            .formLogin().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
}


```


3. 配置`userDetail`

```java

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


```

这样 授权服务器就配置完成

### 客户端1和客户端2

> 客户端1,2 相同 只是换了个端口 只讲一遍


1. 首先依赖

```xml

    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-oauth2</artifactId>
      <version>2.0.1.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

```

2.yml 中自动配置项
```yaml



# sso-server地址
auth-server: http://localhost:9900/uac

# 这个客户端的端口
server:
  port: 9902


security:
  oauth2:
    client:
#客户端id
#客户端密码
# 这两项务必要与授权服务器相同
      client-id: client2
      client-secret: client2
      #请求认证的地址
      user-authorization-uri: ${auth-server}/oauth/authorize
      #请求令牌的地址
      access-token-uri: ${auth-server}/oauth/token
    resource:
      jwt:
        #解析jwt令牌所需要密钥的地址
        key-uri: ${auth-server}/oauth/token_key

```


3. 客户端配置

```java

package com.xcoding.sso.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/16 9:41
 * @description
 */

@Configuration
@EnableOAuth2Sso
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ClientWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 这里把/free 放行
        http.antMatcher("/**").authorizeRequests().antMatchers("/free").permitAll()
            .anyRequest().authenticated();
    }
}

```

3. 写几个访问方法测试路径

```java

package com.xcoding.sso.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/14 17:25
 * @description
 */
@RestController
public class IndexController {


    @GetMapping("/free")
    public String normal( ) {
        return "不需要授权路径!";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String medium() {
        return "用户权限路径";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String admin() {
        return "管理员权限路径";
    }
}


```

最后启动,先启动授权服务器,然后启动客户端

