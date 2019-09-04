package com.xkcoding.dynamic.datasource;

import com.xkcoding.dynamic.datasource.datasource.DatasourceConfigCache;
import com.xkcoding.dynamic.datasource.datasource.DatasourceConfigContextHolder;
import com.xkcoding.dynamic.datasource.mapper.DatasourceConfigMapper;
import com.xkcoding.dynamic.datasource.model.DatasourceConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * <p>
 * 启动器
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/9/4 17:57
 */
@SpringBootApplication
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SpringBootDemoDynamicDatasourceApplication implements CommandLineRunner {
    private final DatasourceConfigMapper configMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoDynamicDatasourceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        DatasourceConfigContextHolder.setDefaultDatasource();
        List<DatasourceConfig> datasourceConfigs = configMapper.selectAll();
        System.out.println("加载其余数据源配置列表: " + datasourceConfigs);
        datasourceConfigs.forEach(config -> DatasourceConfigCache.INSTANCE.addConfig(config.getId(), config));
    }
}
