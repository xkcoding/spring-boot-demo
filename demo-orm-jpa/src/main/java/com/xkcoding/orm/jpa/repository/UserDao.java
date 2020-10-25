package com.xkcoding.orm.jpa.repository;

import com.xkcoding.orm.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * User Dao
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-07 14:07
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {

}
