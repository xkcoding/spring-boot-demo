package com.xkcoding.orm.jpa.repository;

import com.xkcoding.orm.jpa.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;


/**
 * <p>
 * User Dao
 * </p>
 *
 * @package: com.xkcoding.orm.jpa.repository
 * @description: Department Dao
 * @author: 76peter
 * @date: Created in 2019/10/1 18:07
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: 76peter
 */
@Repository
public interface DepartmentDao extends JpaRepository<Department, Long> {
    public Collection<Department> findDepartmentsByLevels(Integer level);
}
