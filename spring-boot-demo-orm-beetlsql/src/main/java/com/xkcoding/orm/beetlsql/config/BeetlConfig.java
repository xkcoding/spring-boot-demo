package com.xkcoding.orm.beetlsql.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * <p>
 * Beetl数据源配置
 * </p>
 *
 * @package: com.xkcoding.orm.beetlsql.config
 * @description: Beetl数据源配置
 * @author: yangkai.shen
 * @date: Created in 2018/11/14 17:15
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Configuration
public class BeetlConfig {

    @Bean(name = "datasource")
    public DataSource getDataSource(Environment env){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }
}
