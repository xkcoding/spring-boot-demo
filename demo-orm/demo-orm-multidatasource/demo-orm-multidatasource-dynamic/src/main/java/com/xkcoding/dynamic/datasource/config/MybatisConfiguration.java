package com.xkcoding.dynamic.datasource.config;

import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * <p>
 * mybatis配置
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-09-04 16:20
 */
@Configuration
@MapperScan(basePackages = "com.xkcoding.dynamic.datasource.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisConfiguration {
    /**
     * 创建会话工厂。
     *
     * @param dataSource 数据源
     * @return 会话工厂
     */
    @Bean(name = "sqlSessionFactory")
    @SneakyThrows
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }
}
