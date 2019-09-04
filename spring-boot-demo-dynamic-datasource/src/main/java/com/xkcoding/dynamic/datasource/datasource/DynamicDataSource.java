package com.xkcoding.dynamic.datasource.datasource;

import com.xkcoding.dynamic.datasource.model.DatasourceConfig;
import com.xkcoding.dynamic.datasource.utils.SpringUtil;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p>
 * 动态数据源
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/9/4 10:41
 */
@Slf4j
public class DynamicDataSource extends HikariDataSource {
    @Override
    public Connection getConnection() throws SQLException {
        // 获取当前数据源 id
        Long id = DatasourceConfigContextHolder.getCurrentDatasourceConfig();
        // 根据当前id获取数据源
        HikariDataSource datasource = DatasourceHolder.INSTANCE.getDatasource(id);

        if (null == datasource) {
            datasource = initDatasource(id);
        }

        return datasource.getConnection();
    }

    private HikariDataSource initDatasource(Long id) {
        HikariDataSource dataSource = new HikariDataSource();

        if (DatasourceHolder.DEFAULT_ID.equals(id)) {
            DataSourceProperties properties = SpringUtil.getBean(DataSourceProperties.class);
            dataSource.setJdbcUrl(properties.getUrl());
            dataSource.setUsername(properties.getUsername());
            dataSource.setPassword(properties.getPassword());
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        } else {
            // 获取数据库的配置
            DatasourceConfig datasourceConfig = DatasourceConfigCache.INSTANCE.getConfig(id);

            if (datasourceConfig == null) {
                throw new RuntimeException("无此数据源");
            }

            dataSource.setJdbcUrl(datasourceConfig.buildJdbcUrl());
            dataSource.setUsername(datasourceConfig.getUsername());
            dataSource.setPassword(datasourceConfig.getPassword());
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        }
        DatasourceHolder.INSTANCE.addDatasource(id, dataSource);
        return dataSource;
    }
}
