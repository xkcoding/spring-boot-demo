package com.xkcoding.orm.mybatis.MapperAndPage.mapper;

import com.xkcoding.orm.mybatis.MapperAndPage.entity.User;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * <p>
 * UserMapper
 * </p>
 *
 * @package: com.xkcoding.orm.mybatis.MapperAndPage.mapper
 * @description: UserMapper
 * @author: yangkai.shen
 * @date: Created in 2018/11/8 14:15
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Component
public interface UserMapper extends Mapper<User>, MySqlMapper<User> {
}
