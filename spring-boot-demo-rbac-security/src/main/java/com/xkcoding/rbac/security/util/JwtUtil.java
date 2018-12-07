package com.xkcoding.rbac.security.util;

import cn.hutool.core.date.DateUtil;
import com.xkcoding.rbac.security.config.JwtConfig;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

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
    private final JwtConfig jwtConfig;

    @Autowired
    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    /**
     * 创建JWT
     *
     * @param id      用户id
     * @param subject 用户名
     * @param roles   用户角色
     * @return JWT
     */
    public String createJWT(String id, String subject, String roles) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getKey())
                .claim("roles", roles);
        if (jwtConfig.getTtl() > 0) {
            builder.setExpiration(DateUtil.offsetMillisecond(now, jwtConfig.getTtl()
                    .intValue()));
        }
        return builder.compact();
    }

    /**
     * 解析JWT
     *
     * @param jwt JWT
     * @return {@link Claims}
     */
    public Claims parseJWT(String jwt) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getKey())
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.error("Token 已过期");
        } catch (UnsupportedJwtException e) {
            log.error("不支持的 Token");
        } catch (MalformedJwtException e) {
            log.error("Token 无效");
        } catch (SignatureException e) {
            log.error("无效的 Token 签名");
        } catch (IllegalArgumentException e) {
            log.error("Token 参数不存在");
        }
        return claims;
    }

}
