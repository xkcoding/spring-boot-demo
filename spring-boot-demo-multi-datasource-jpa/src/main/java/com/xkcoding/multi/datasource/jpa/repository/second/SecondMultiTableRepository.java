package com.xkcoding.multi.datasource.jpa.repository.second;

import com.xkcoding.multi.datasource.jpa.entity.second.SecondMultiTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 多数据源测试 repo
 * </p>
 *
 * @package: com.xkcoding.multi.datasource.jpa.repository.second
 * @description: 多数据源测试 repo
 * @author: yangkai.shen
 * @date: Created in 2019-01-18 10:11
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Repository
public interface SecondMultiTableRepository extends JpaRepository<SecondMultiTable, Long> {
}
