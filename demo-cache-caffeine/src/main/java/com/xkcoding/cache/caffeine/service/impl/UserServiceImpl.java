package com.xkcoding.cache.caffeine.service.impl;

import com.xkcoding.cache.caffeine.entity.User;
import com.xkcoding.cache.caffeine.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * UserService
 * </p>
 *
 * @author ccposhing
 * @since 2025-07-16
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

  /**
   * 模拟数据库
   */
  private static final Map<Long, User> DATABASES = Collections.synchronizedMap(new HashMap<>());

  /*
   * 初始化数据
   */
  static {
    DATABASES.put(1L, new User(1L, "user1"));
    DATABASES.put(2L, new User(2L, "user2"));
    DATABASES.put(3L, new User(3L, "user3"));
  }

  /**
   * 保存或修改用户
   *
   * @param user 用户对象
   * @return 操作结果
   */
  @CachePut(value = "user-caffeine-cache1", key = "#user.id")
  @Override
  public User saveOrUpdate(User user) {
    DATABASES.put(user.getId(), user);
    log.info("保存用户【user】= {}", user);
    return user;
  }

  /**
   * 获取用户
   *
   * @param id key值
   * @return 返回结果
   */
  @Cacheable(value = "user-caffeine-cache1", key = "#id")
  @Override
  public User get(Long id) {
    log.info("查询用户【id】= {}", id);
    User user = DATABASES.get(id);
    //模拟每次database取回后会重新创建对象
    return new User(user.getId(), user.getName());
  }

  /**
   * 删除
   *
   * @param id key值
   */
  @CacheEvict(value = "user-caffeine-cache1", key = "#id")
  @Override
  public void delete(Long id) {
    DATABASES.remove(id);
    log.info("删除用户【id】= {}", id);
  }
}
