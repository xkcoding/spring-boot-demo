package com.xkcoding.ureport2.config;

import com.bstek.ureport.definition.datasource.BuildinDatasource;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * <p>
 * 内部数据源
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2020-10-26 22:32
 */
@Component
public class InnerDatasource implements BuildinDatasource {
    @Autowired
    private DataSource datasource;

    @Override
    public String name() {
        return "内部数据源";
    }

    @SneakyThrows
    @Override
    public Connection getConnection() {
        return datasource.getConnection();
    }
}
