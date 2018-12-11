package com.xkcoding.rbac.security.util;

import cn.hutool.json.JSONUtil;
import com.xkcoding.rbac.security.SpringBootDemoRbacSecurityApplicationTests;
import com.xkcoding.rbac.security.common.Consts;
import com.xkcoding.rbac.security.common.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 测试RedisUtil
 * </p>
 *
 * @package: com.xkcoding.rbac.security.util
 * @description: 测试RedisUtil
 * @author: yangkai.shen
 * @date: Created in 2018-12-11 20:44
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
public class RedisUtilTest extends SpringBootDemoRbacSecurityApplicationTests {
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void findKeysForPage() {
        PageResult pageResult = redisUtil.findKeysForPage(Consts.REDIS_JWT_KEY_PREFIX + Consts.SYMBOL_STAR, 2, 1);
        log.info("【pageResult】= {}", JSONUtil.toJsonStr(pageResult));
    }
}