package com.xkcoding.orm.mybatis.plus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xkcoding.orm.mybatis.plus.entity.User;
import org.springframework.stereotype.Component;

/**
 * <p>
 * UserMapper
 * </p>
 *
 * @package: com.xkcoding.orm.mybatis.plus.mapper
 * @description: UserMapper
 * @author: yangkai.shen
 * @date: Created in 2018/11/8 16:57
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Component
public interface UserMapper extends BaseMapper<User> {
}
