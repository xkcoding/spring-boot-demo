package com.xkcoding.orm.beetlsql.dao;

import com.xkcoding.orm.beetlsql.entity.User;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * UserDao
 * </p>
 *
 * @package: com.xkcoding.orm.beetlsql.dao
 * @description: UserDao
 * @author: yangkai.shen
 * @date: Created in 2018/11/14 16:18
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Component
public interface UserDao extends BaseMapper<User> {

}
