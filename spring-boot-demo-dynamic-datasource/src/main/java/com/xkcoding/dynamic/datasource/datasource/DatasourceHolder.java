package com.xkcoding.dynamic.datasource.datasource;

import com.zaxxer.hikari.HikariDataSource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 数据源管理
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/9/4 14:23
 */
public enum DatasourceHolder {
    /**
     * 当前实例
     */
    INSTANCE;

    /**
     * 启动执行，定时5分钟清理一次
     */
    DatasourceHolder() {
        DatasourceScheduler.INSTANCE.schedule(this::clearExpiredDatasource, 5 * 60 * 1000);
    }

    /**
     * 默认数据源的id
     */
    public static final Long DEFAULT_ID = -1L;

    /**
     * 管理动态数据源列表。
     */
    private static final Map<Long, DatasourceManager> DATASOURCE_CACHE = new ConcurrentHashMap<>();

    /**
     * 添加动态数据源
     *
     * @param id         数据源id
     * @param dataSource 数据源
     */
    public synchronized void addDatasource(Long id, HikariDataSource dataSource) {
        DatasourceManager datasourceManager = new DatasourceManager(dataSource);
        DATASOURCE_CACHE.put(id, datasourceManager);
    }

    /**
     * 查询动态数据源
     *
     * @param id 数据源id
     * @return 数据源
     */
    public synchronized HikariDataSource getDatasource(Long id) {
        if (DATASOURCE_CACHE.containsKey(id)) {
            DatasourceManager datasourceManager = DATASOURCE_CACHE.get(id);
            datasourceManager.refreshTime();
            return datasourceManager.getDataSource();
        }
        return null;
    }

    /**
     * 清除超时的数据源
     */
    public synchronized void clearExpiredDatasource() {
        DATASOURCE_CACHE.forEach((k, v) -> {
            // 排除默认数据源
            if (!DEFAULT_ID.equals(k)) {
                if (v.isExpired()) {
                    DATASOURCE_CACHE.remove(k);
                }
            }
        });
    }

    /**
     * 清除动态数据源
     * @param id 数据源id
     */
    public synchronized void removeDatasource(Long id) {
        if (DATASOURCE_CACHE.containsKey(id)) {
            // 关闭数据源
            DATASOURCE_CACHE.get(id).getDataSource().close();
            // 移除缓存
            DATASOURCE_CACHE.remove(id);
        }
    }
}
