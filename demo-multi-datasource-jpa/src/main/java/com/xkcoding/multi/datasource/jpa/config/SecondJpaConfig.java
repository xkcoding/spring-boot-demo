package com.xkcoding.multi.datasource.jpa.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * <p>
 * JPA多数据源配置 - 次 JPA 配置
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-01-17 16:54
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    // repository包名
    basePackages = SecondJpaConfig.REPOSITORY_PACKAGE,
    // 实体管理bean名称
    entityManagerFactoryRef = "secondEntityManagerFactory",
    // 事务管理bean名称
    transactionManagerRef = "secondTransactionManager")
public class SecondJpaConfig {
    static final String REPOSITORY_PACKAGE = "com.xkcoding.multi.datasource.jpa.repository.second";
    private static final String ENTITY_PACKAGE = "com.xkcoding.multi.datasource.jpa.entity.second";


    /**
     * 扫描spring.jpa.second开头的配置信息
     *
     * @return jpa配置信息
     */
    @Bean(name = "secondJpaProperties")
    @ConfigurationProperties(prefix = "spring.jpa.second")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    /**
     * 获取主库实体管理工厂对象
     *
     * @param secondDataSource 注入名为secondDataSource的数据源
     * @param jpaProperties    注入名为secondJpaProperties的jpa配置信息
     * @param builder          注入EntityManagerFactoryBuilder
     * @return 实体管理工厂对象
     */
    @Bean(name = "secondEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("secondDataSource") DataSource secondDataSource, @Qualifier("secondJpaProperties") JpaProperties jpaProperties, EntityManagerFactoryBuilder builder) {
        return builder
            // 设置数据源
            .dataSource(secondDataSource)
            // 设置jpa配置
            .properties(jpaProperties.getProperties())
            // 设置实体包名
            .packages(ENTITY_PACKAGE)
            // 设置持久化单元名，用于@PersistenceContext注解获取EntityManager时指定数据源
            .persistenceUnit("secondPersistenceUnit").build();
    }

    /**
     * 获取实体管理对象
     *
     * @param factory 注入名为secondEntityManagerFactory的bean
     * @return 实体管理对象
     */
    @Bean(name = "secondEntityManager")
    public EntityManager entityManager(@Qualifier("secondEntityManagerFactory") EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

    /**
     * 获取主库事务管理对象
     *
     * @param factory 注入名为secondEntityManagerFactory的bean
     * @return 事务管理对象
     */
    @Bean(name = "secondTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("secondEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

}
