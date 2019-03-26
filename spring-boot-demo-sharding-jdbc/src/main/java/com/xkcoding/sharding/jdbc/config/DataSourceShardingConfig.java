package com.xkcoding.sharding.jdbc.config;

import com.zaxxer.hikari.HikariDataSource;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.api.config.strategy.NoneShardingStrategyConfiguration;
import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * sharding-jdbc 的数据源配置
 * </p>
 *
 * @package: com.xkcoding.sharding.jdbc.config
 * @description: sharding-jdbc 的数据源配置
 * @author: yangkai.shen
 * @date: Created in 2019-03-26 16:47
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Configuration
public class DataSourceShardingConfig {
    /**
     * 需要手动配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        // 设置分库策略
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));
        // 设置规则适配的表
        shardingRuleConfig.getBindingTableGroups().add("t_order");
        // 设置分表策略
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRule());
        shardingRuleConfig.setDefaultDataSourceName("ds0");
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new NoneShardingStrategyConfiguration());

        Properties properties = new Properties();
        properties.setProperty("sql.show", "true");

        return ShardingDataSourceFactory.createDataSource(dataSourceMap(), shardingRuleConfig, new ConcurrentHashMap<>(16), properties);
    }

    private TableRuleConfiguration orderTableRule() {
        TableRuleConfiguration tableRule = new TableRuleConfiguration();
        // 设置逻辑表名
        tableRule.setLogicTable("t_order");
        // ds${0..1}.t_order_${0..2} 也可以写成 ds$->{0..1}.t_order_$->{0..1}
        tableRule.setActualDataNodes("ds${0..1}.t_order_${0..2}");
        tableRule.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order_$->{order_id % 3}"));
        tableRule.setKeyGenerator(new DefaultKeyGenerator());
        tableRule.setKeyGeneratorColumnName("order_id");
        return tableRule;
    }

    private Map<String, DataSource> dataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>(16);

        // 配置第一个数据源
        HikariDataSource ds0 = new HikariDataSource();
        ds0.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds0.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/spring-boot-demo?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&failOverReadOnly=false&serverTimezone=GMT%2B8");
        ds0.setUsername("root");
        ds0.setPassword("root");

        // 配置第二个数据源
        HikariDataSource ds1 = new HikariDataSource();
        ds1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds1.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/spring-boot-demo-2?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&failOverReadOnly=false&serverTimezone=GMT%2B8");
        ds1.setUsername("root");
        ds1.setPassword("root");

        dataSourceMap.put("ds0", ds0);
        dataSourceMap.put("ds1", ds1);
        return dataSourceMap;
    }

}
