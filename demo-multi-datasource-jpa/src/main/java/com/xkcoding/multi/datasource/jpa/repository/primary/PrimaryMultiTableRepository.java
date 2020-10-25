package com.xkcoding.multi.datasource.jpa.repository.primary;

import com.xkcoding.multi.datasource.jpa.entity.primary.PrimaryMultiTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 多数据源测试 repo
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-01-18 10:11
 */
@Repository
public interface PrimaryMultiTableRepository extends JpaRepository<PrimaryMultiTable, Long> {
}
