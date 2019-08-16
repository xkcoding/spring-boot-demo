package com.xkcoding.codegen.utils;

import com.xkcoding.codegen.entity.TableRequest;
import com.zaxxer.hikari.HikariDataSource;

import lombok.experimental.UtilityClass;

/**
 * <p>
 * 数据库工具类
 * </p>
 *
 * @package: com.xkcoding.codegen.utils
 * @description: 数据库工具类
 * @author: yangkai.shen
 * @date: Created in 2019-03-22 10:26
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@UtilityClass
public class DbUtil {
    public HikariDataSource buildFromTableRequest(TableRequest request) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(request.getPrepend() + request.getUrl());
        dataSource.setUsername(request.getUsername());
        dataSource.setPassword(request.getPassword());
        return dataSource;
    }

}
