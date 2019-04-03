# spring-boot-demo-rbac-security

> 此 demo 主要演示了 Spring Boot 项目如何集成 Spring Security 完成权限拦截操作。本 demo 为基于**前后端分离**的后端权限管理部分，不同于其他博客里使用的模板技术，希望对大家有所帮助。

## 1. 主要功能

- [x] 基于 `RBAC` 权限模型设计，详情参考数据库表结构设计 [`security.sql`](./sql/security.sql)
- [x] 支持**动态权限管理**，详情参考 [`RbacAuthorityService.java`](./src/main/java/com/xkcoding/rbac/security/config/RbacAuthorityService.java)
- [x] **登录 / 登出**部分均使用自定义 Controller 实现，未使用 `Spring Security` 内部实现部分，适用于前后端分离项目，详情参考 [`SecurityConfig.java`](./src/main/java/com/xkcoding/rbac/security/config/SecurityConfig.java) 和 [`AuthController.java`](./src/main/java/com/xkcoding/rbac/security/controller/AuthController.java)
- [x] 持久化技术使用 `spring-data-jpa` 完成
- [x] 使用 `JWT` 实现安全验证，同时引入 `Redis` 解决 `JWT` 无法手动设置过期的弊端，并且保证同一用户在同一时间仅支持同一设备登录，不同设备登录会将，详情参考 [`JwtUtil.java`](./src/main/java/com/xkcoding/rbac/security/util/JwtUtil.java)
- [x] 在线人数统计，详情参考 [`MonitorService.java`](./src/main/java/com/xkcoding/rbac/security/service/MonitorService.java) 和 [`RedisUtil.java`](./src/main/java/com/xkcoding/rbac/security/util/RedisUtil.java)
- [x] 手动踢出用户，详情参考 [`MonitorService.java`](./src/main/java/com/xkcoding/rbac/security/service/MonitorService.java) 
- [x] 自定义配置不需要进行拦截的请求，详情参考 [`CustomConfig.java`](./src/main/java/com/xkcoding/rbac/security/config/CustomConfig.java) 和 [`application.yml`](./src/main/resources/application.yml)

## 2. 运行

### 2.1. 环境

1. JDK 1.8 以上
2. Maven 3.5 以上
3. Mysql 5.7 以上
4. Redis

### 2.2. 运行方式

1. 新建一个名为 `spring-boot-demo` 的数据库，字符集设置为 `utf-8`，如果数据库名不是 `spring-boot-demo` 需要在 `application.yml` 中修改 `spring.datasource.url`
2. 使用 [`security.sql`](./sql/security.sql) 这个 SQL 文件，创建数据库表和初始化RBAC数据
3. 运行 `SpringBootDemoRbacSecurityApplication`
4. 管理员账号：admin/123456 普通用户：user/123456
5. 登陆成功之后返回token，将获得的token放在具体请求的 Header 里，key 固定是 Authorization ，value 前缀为 Bearer 后面加空格再加token，并加上具体请求的参数，就可以了
6. enjoy ~​ :kissing_smiling_eyes:

## 3. 部分关键代码

### 3.1. pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-boot-demo-rbac-security</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-boot-demo-rbac-security</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <groupId>com.xkcoding</groupId>
        <artifactId>spring-boot-demo</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <jjwt.veersion>0.9.1</jjwt.veersion>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>

        <!-- 对象池，使用redis时必须引入 -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-pool2</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>${jjwt.veersion}</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>

    <build>
        <finalName>spring-boot-demo-rbac-security</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

### 3.2. JwtUtil.java

> JWT 工具类，主要功能：生成JWT并存入Redis、解析JWT并校验其准确性、从Request的Header中获取JWT

```java
/**
 * <p>
 * JWT 工具类
 * </p>
 *
 * @package: com.xkcoding.rbac.security.util
 * @description: JWT 工具类
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 13:42
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@EnableConfigurationProperties(JwtConfig.class)
@Configuration
@Slf4j
public class JwtUtil {
    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 创建JWT
     *
     * @param rememberMe  记住我
     * @param id          用户id
     * @param subject     用户名
     * @param roles       用户角色
     * @param authorities 用户权限
     * @return JWT
     */
    public String createJWT(Boolean rememberMe, Long id, String subject, List<String> roles, Collection<? extends GrantedAuthority> authorities) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setId(id.toString())
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getKey())
                .claim("roles", roles)
                .claim("authorities", authorities);

        // 设置过期时间
        Long ttl = rememberMe ? jwtConfig.getRemember() : jwtConfig.getTtl();
        if (ttl > 0) {
            builder.setExpiration(DateUtil.offsetMillisecond(now, ttl.intValue()));
        }

        String jwt = builder.compact();
        // 将生成的JWT保存至Redis
        stringRedisTemplate.opsForValue()
                .set(Consts.REDIS_JWT_KEY_PREFIX + subject, jwt, ttl, TimeUnit.MILLISECONDS);
        return jwt;
    }

    /**
     * 创建JWT
     *
     * @param authentication 用户认证信息
     * @param rememberMe     记住我
     * @return JWT
     */
    public String createJWT(Authentication authentication, Boolean rememberMe) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return createJWT(rememberMe, userPrincipal.getId(), userPrincipal.getUsername(), userPrincipal.getRoles(), userPrincipal.getAuthorities());
    }

    /**
     * 解析JWT
     *
     * @param jwt JWT
     * @return {@link Claims}
     */
    public Claims parseJWT(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getKey())
                    .parseClaimsJws(jwt)
                    .getBody();

            String username = claims.getSubject();
            String redisKey = Consts.REDIS_JWT_KEY_PREFIX + username;

            // 校验redis中的JWT是否存在
            Long expire = stringRedisTemplate.getExpire(redisKey, TimeUnit.MILLISECONDS);
            if (Objects.isNull(expire) || expire <= 0) {
                throw new SecurityException(Status.TOKEN_EXPIRED);
            }

            // 校验redis中的JWT是否与当前的一致，不一致则代表用户已注销/用户在不同设备登录，均代表JWT已过期
            String redisToken = stringRedisTemplate.opsForValue()
                    .get(redisKey);
            if (!StrUtil.equals(jwt, redisToken)) {
                throw new SecurityException(Status.TOKEN_OUT_OF_CTRL);
            }
            return claims;
        } catch (ExpiredJwtException e) {
            log.error("Token 已过期");
            throw new SecurityException(Status.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.error("不支持的 Token");
            throw new SecurityException(Status.TOKEN_PARSE_ERROR);
        } catch (MalformedJwtException e) {
            log.error("Token 无效");
            throw new SecurityException(Status.TOKEN_PARSE_ERROR);
        } catch (SignatureException e) {
            log.error("无效的 Token 签名");
            throw new SecurityException(Status.TOKEN_PARSE_ERROR);
        } catch (IllegalArgumentException e) {
            log.error("Token 参数不存在");
            throw new SecurityException(Status.TOKEN_PARSE_ERROR);
        }
    }

    /**
     * 设置JWT过期
     *
     * @param request 请求
     */
    public void invalidateJWT(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request);
        String username = getUsernameFromJWT(jwt);
        // 从redis中清除JWT
        stringRedisTemplate.delete(Consts.REDIS_JWT_KEY_PREFIX + username);
    }

    /**
     * 根据 jwt 获取用户名
     *
     * @param jwt JWT
     * @return 用户名
     */
    public String getUsernameFromJWT(String jwt) {
        Claims claims = parseJWT(jwt);
        return claims.getSubject();
    }

    /**
     * 从 request 的 header 中获取 JWT
     *
     * @param request 请求
     * @return JWT
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StrUtil.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
```

### 3.3. SecurityConfig.java

> Spring Security 配置类，主要功能：配置哪些URL不需要认证，哪些需要认证

```java
/**
 * <p>
 * Security 配置
 * </p>
 *
 * @package: com.xkcoding.rbac.security.config
 * @description: Security 配置
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 16:46
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(CustomConfig.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomConfig customConfig;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()

                // 关闭 CSRF
                .and()
                .csrf()
                .disable()

                // 登录行为由自己实现，参考 AuthController#login
                .formLogin()
                .disable()
                .httpBasic()
                .disable()

                // 认证请求
                .authorizeRequests()
                // 所有请求都需要登录访问
                .anyRequest()
                .authenticated()
                // RBAC 动态 url 认证
                .anyRequest()
                .access("@rbacAuthorityService.hasPermission(request,authentication)")

                // 登出行为由自己实现，参考 AuthController#logout
                .and()
                .logout()
                .disable()

                // Session 管理
                .sessionManagement()
                // 因为使用了JWT，所以这里不管理Session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 异常处理
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);

        // 添加自定义 JWT 过滤器
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 放行所有不需要登录就可以访问的请求，参见 AuthController
     * 也可以在 {@link #configure(HttpSecurity)} 中配置
     * {@code http.authorizeRequests().antMatchers("/api/auth/**").permitAll()}
     */
    @Override
    public void configure(WebSecurity web) {
        WebSecurity and = web.ignoring()
                .and();

        // 忽略 GET
        customConfig.getIgnores()
                .getGet()
                .forEach(url -> and.ignoring()
                        .antMatchers(HttpMethod.GET, url));

        // 忽略 POST
        customConfig.getIgnores()
                .getPost()
                .forEach(url -> and.ignoring()
                        .antMatchers(HttpMethod.POST, url));

        // 忽略 DELETE
        customConfig.getIgnores()
                .getDelete()
                .forEach(url -> and.ignoring()
                        .antMatchers(HttpMethod.DELETE, url));

        // 忽略 PUT
        customConfig.getIgnores()
                .getPut()
                .forEach(url -> and.ignoring()
                        .antMatchers(HttpMethod.PUT, url));

        // 忽略 HEAD
        customConfig.getIgnores()
                .getHead()
                .forEach(url -> and.ignoring()
                        .antMatchers(HttpMethod.HEAD, url));

        // 忽略 PATCH
        customConfig.getIgnores()
                .getPatch()
                .forEach(url -> and.ignoring()
                        .antMatchers(HttpMethod.PATCH, url));

        // 忽略 OPTIONS
        customConfig.getIgnores()
                .getOptions()
                .forEach(url -> and.ignoring()
                        .antMatchers(HttpMethod.OPTIONS, url));

        // 忽略 TRACE
        customConfig.getIgnores()
                .getTrace()
                .forEach(url -> and.ignoring()
                        .antMatchers(HttpMethod.TRACE, url));

        // 按照请求格式忽略
        customConfig.getIgnores()
                .getPattern()
                .forEach(url -> and.ignoring()
                        .antMatchers(url));

    }
}
```

### 3.4. RbacAuthorityService.java

> 路由动态鉴权类，主要功能：
>
> 1. 校验请求的合法性，排除404和405这两种异常请求
> 2. 根据当前请求路径与该用户可访问的资源做匹配，通过则可以访问，否则，不允许访问

```java
/**
 * <p>
 * 动态路由认证
 * </p>
 *
 * @package: com.xkcoding.rbac.security.config
 * @description: 动态路由认证
 * @author: yangkai.shen
 * @date: Created in 2018-12-10 17:17
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Component
public class RbacAuthorityService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RequestMappingHandlerMapping mapping;

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        checkRequest(request);

        Object userInfo = authentication.getPrincipal();
        boolean hasPermission = false;

        if (userInfo instanceof UserDetails) {
            UserPrincipal principal = (UserPrincipal) userInfo;
            Long userId = principal.getId();

            List<Role> roles = roleDao.selectByUserId(userId);
            List<Long> roleIds = roles.stream()
                    .map(Role::getId)
                    .collect(Collectors.toList());
            List<Permission> permissions = permissionDao.selectByRoleIdList(roleIds);

            //获取资源，前后端分离，所以过滤页面权限，只保留按钮权限
            List<Permission> btnPerms = permissions.stream()
                    // 过滤页面权限
                    .filter(permission -> Objects.equals(permission.getType(), Consts.BUTTON))
                    // 过滤 URL 为空
                    .filter(permission -> StrUtil.isNotBlank(permission.getUrl()))
                    // 过滤 METHOD 为空
                    .filter(permission -> StrUtil.isNotBlank(permission.getMethod()))
                    .collect(Collectors.toList());

            for (Permission btnPerm : btnPerms) {
                AntPathRequestMatcher antPathMatcher = new AntPathRequestMatcher(btnPerm.getUrl(), btnPerm.getMethod());
                if (antPathMatcher.matches(request)) {
                    hasPermission = true;
                    break;
                }
            }

            return hasPermission;
        } else {
            return false;
        }
    }

    /**
     * 校验请求是否存在
     *
     * @param request 请求
     */
    private void checkRequest(HttpServletRequest request) {
        // 获取当前 request 的方法
        String currentMethod = request.getMethod();
        Multimap<String, String> urlMapping = allUrlMapping();

        for (String uri : urlMapping.keySet()) {
            // 通过 AntPathRequestMatcher 匹配 url
            // 可以通过 2 种方式创建 AntPathRequestMatcher
            // 1：new AntPathRequestMatcher(uri,method) 这种方式可以直接判断方法是否匹配，因为这里我们把 方法不匹配 自定义抛出，所以，我们使用第2种方式创建
            // 2：new AntPathRequestMatcher(uri) 这种方式不校验请求方法，只校验请求路径
            AntPathRequestMatcher antPathMatcher = new AntPathRequestMatcher(uri);
            if (antPathMatcher.matches(request)) {
                if (!urlMapping.get(uri)
                        .contains(currentMethod)) {
                    throw new SecurityException(Status.HTTP_BAD_METHOD);
                } else {
                    return;
                }
            }
        }

        throw new SecurityException(Status.REQUEST_NOT_FOUND);
    }

    /**
     * 获取 所有URL Mapping，返回格式为{"/test":["GET","POST"],"/sys":["GET","DELETE"]}
     *
     * @return {@link ArrayListMultimap} 格式的 URL Mapping
     */
    private Multimap<String, String> allUrlMapping() {
        Multimap<String, String> urlMapping = ArrayListMultimap.create();

        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();

        handlerMethods.forEach((k, v) -> {
            // 获取当前 key 下的获取所有URL
            Set<String> url = k.getPatternsCondition()
                    .getPatterns();
            RequestMethodsRequestCondition method = k.getMethodsCondition();

            // 为每个URL添加所有的请求方法
            url.forEach(s -> urlMapping.putAll(s, method.getMethods()
                    .stream()
                    .map(Enum::toString)
                    .collect(Collectors.toList())));
        });

        return urlMapping;
    }
}
```

### 3.5. JwtAuthenticationFilter.java

> JWT 认证过滤器，主要功能：
>
> 1. 过滤不需要拦截的请求
> 2. 根据当前请求的JWT，认证用户身份信息

```java
/**
 * <p>
 * Jwt 认证过滤器
 * </p>
 *
 * @package: com.xkcoding.rbac.security.config
 * @description: Jwt 认证过滤器
 * @author: yangkai.shen
 * @date: Created in 2018-12-10 15:15
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomConfig customConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (checkIgnores(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = jwtUtil.getJwtFromRequest(request);

        if (StrUtil.isNotBlank(jwt)) {
            try {
                String username = jwtUtil.getUsernameFromJWT(jwt);

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } catch (SecurityException e) {
                ResponseUtil.renderJson(response, e);
            }
        } else {
            ResponseUtil.renderJson(response, Status.UNAUTHORIZED, null);
        }

    }

    /**
     * 请求是否不需要进行权限拦截
     *
     * @param request 当前请求
     * @return true - 忽略，false - 不忽略
     */
    private boolean checkIgnores(HttpServletRequest request) {
        String method = request.getMethod();

        HttpMethod httpMethod = HttpMethod.resolve(method);
        if (ObjectUtil.isNull(httpMethod)) {
            httpMethod = HttpMethod.GET;
        }

        Set<String> ignores = Sets.newHashSet();

        switch (httpMethod) {
            case GET:
                ignores.addAll(customConfig.getIgnores()
                        .getGet());
                break;
            case PUT:
                ignores.addAll(customConfig.getIgnores()
                        .getPut());
                break;
            case HEAD:
                ignores.addAll(customConfig.getIgnores()
                        .getHead());
                break;
            case POST:
                ignores.addAll(customConfig.getIgnores()
                        .getPost());
                break;
            case PATCH:
                ignores.addAll(customConfig.getIgnores()
                        .getPatch());
                break;
            case TRACE:
                ignores.addAll(customConfig.getIgnores()
                        .getTrace());
                break;
            case DELETE:
                ignores.addAll(customConfig.getIgnores()
                        .getDelete());
                break;
            case OPTIONS:
                ignores.addAll(customConfig.getIgnores()
                        .getOptions());
                break;
            default:
                break;
        }

        ignores.addAll(customConfig.getIgnores()
                .getPattern());

        if (CollUtil.isNotEmpty(ignores)) {
            for (String ignore : ignores) {
                AntPathRequestMatcher matcher = new AntPathRequestMatcher(ignore, method);
                if (matcher.matches(request)) {
                    return true;
                }
            }
        }

        return false;
    }

}
```

### 3.6. CustomUserDetailsService.java

> 实现 `UserDetailsService` 接口，主要功能：根据用户名查询用户信息

```java
/**
 * <p>
 * 自定义UserDetails查询
 * </p>
 *
 * @package: com.xkcoding.rbac.security.service
 * @description: 自定义UserDetails查询
 * @author: yangkai.shen
 * @date: Created in 2018-12-10 10:29
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmailOrPhone) throws UsernameNotFoundException {
        User user = userDao.findByUsernameOrEmailOrPhone(usernameOrEmailOrPhone, usernameOrEmailOrPhone, usernameOrEmailOrPhone)
                .orElseThrow(() -> new UsernameNotFoundException("未找到用户信息 : " + usernameOrEmailOrPhone));
        List<Role> roles = roleDao.selectByUserId(user.getId());
        List<Long> roleIds = roles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());
        List<Permission> permissions = permissionDao.selectByRoleIdList(roleIds);
        return UserPrincipal.create(user, roles, permissions);
    }
}
```

### 3.7. RedisUtil.java

> 主要功能：根据key的格式分页获取Redis存在的key列表

```java
/**
 * <p>
 * Redis工具类
 * </p>
 *
 * @package: com.xkcoding.rbac.security.util
 * @description: Redis工具类
 * @author: yangkai.shen
 * @date: Created in 2018-12-11 20:24
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Component
@Slf4j
public class RedisUtil {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 分页获取指定格式key，使用 scan 命令代替 keys 命令，在大数据量的情况下可以提高查询效率
     *
     * @param patternKey  key格式
     * @param currentPage 当前页码
     * @param pageSize    每页条数
     * @return 分页获取指定格式key
     */
    public PageResult<String> findKeysForPage(String patternKey, int currentPage, int pageSize) {
        ScanOptions options = ScanOptions.scanOptions()
                .match(patternKey)
                .build();
        RedisConnectionFactory factory = stringRedisTemplate.getConnectionFactory();
        RedisConnection rc = factory.getConnection();
        Cursor<byte[]> cursor = rc.scan(options);

        List<String> result = Lists.newArrayList();

        long tmpIndex = 0;
        int startIndex = (currentPage - 1) * pageSize;
        int end = currentPage * pageSize;
        while (cursor.hasNext()) {
            String key = new String(cursor.next());
            if (tmpIndex >= startIndex && tmpIndex < end) {
                result.add(key);
            }
            tmpIndex++;
        }

        try {
            cursor.close();
            RedisConnectionUtils.releaseConnection(rc, factory);
        } catch (Exception e) {
            log.warn("Redis连接关闭异常，", e);
        }

        return new PageResult<>(result, tmpIndex);
    }
}
```

### 3.8. MonitorService.java

> 监控服务，主要功能：查询当前在线人数分页列表，手动踢出某个用户

```java
package com.xkcoding.rbac.security.service;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.xkcoding.rbac.security.common.Consts;
import com.xkcoding.rbac.security.common.PageResult;
import com.xkcoding.rbac.security.model.User;
import com.xkcoding.rbac.security.repository.UserDao;
import com.xkcoding.rbac.security.util.RedisUtil;
import com.xkcoding.rbac.security.vo.OnlineUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 监控 Service
 * </p>
 *
 * @package: com.xkcoding.rbac.security.service
 * @description: 监控 Service
 * @author: yangkai.shen
 * @date: Created in 2018-12-12 00:55
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Service
public class MonitorService {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserDao userDao;

    public PageResult<OnlineUser> onlineUser(Integer page, Integer size) {
        PageResult<String> keys = redisUtil.findKeysForPage(Consts.REDIS_JWT_KEY_PREFIX + Consts.SYMBOL_STAR, page, size);
        List<String> rows = keys.getRows();
        Long total = keys.getTotal();

        // 根据 redis 中键获取用户名列表
        List<String> usernameList = rows.stream()
                .map(s -> StrUtil.subAfter(s, Consts.REDIS_JWT_KEY_PREFIX, true))
                .collect(Collectors.toList());
        // 根据用户名查询用户信息
        List<User> userList = userDao.findByUsernameIn(usernameList);

        // 封装在线用户信息
        List<OnlineUser> onlineUserList = Lists.newArrayList();
        userList.forEach(user -> onlineUserList.add(OnlineUser.create(user)));

        return new PageResult<>(onlineUserList, total);
    }
}
```

### 3.9. 其余代码参见本 demo

## 4. 参考

1. Spring Security 官方文档：https://docs.spring.io/spring-security/site/docs/5.1.1.RELEASE/reference/htmlsingle/
2. JWT 官网：https://jwt.io/
3. JJWT开源工具参考：https://github.com/jwtk/jjwt#quickstart
4. 授权部分参考官方文档：https://docs.spring.io/spring-security/site/docs/5.1.1.RELEASE/reference/htmlsingle/#authorization

4. 动态授权部分，参考博客：https://blog.csdn.net/larger5/article/details/81063438

