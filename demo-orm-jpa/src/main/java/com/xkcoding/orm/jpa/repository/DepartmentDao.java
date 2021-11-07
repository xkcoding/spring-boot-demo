package com.xkcoding.orm.jpa.repository;

import com.xkcoding.orm.jpa.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p>
 * User Dao
 * </p>
 *
 * @author 76peter
 * @date Created in 2019-10-01 18:07
 */
@Repository
public interface DepartmentDao extends JpaRepository<Department, Long> {
    /**
     * 根据层级查询部门
     *
     * @param level 层级
     * @return 部门列表
     */
    List<Department> findDepartmentsByLevels(Integer level);
}
