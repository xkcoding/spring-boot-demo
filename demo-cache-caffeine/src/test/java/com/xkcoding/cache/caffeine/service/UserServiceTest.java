package com.xkcoding.cache.caffeine.service;

import com.xkcoding.cache.caffeine.SpringBootDemoCacheCaffeineApplicationTests;
import com.xkcoding.cache.caffeine.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * caffeine缓存测试
 * </p>
 *
 * @author ccposhing
 * @since 2025-07-16
 */
@Slf4j
public class UserServiceTest extends SpringBootDemoCacheCaffeineApplicationTests {

  @Autowired
  UserService userService;

  /**
   * 获取两次，查看日志验证缓存
   */
  @Test
  public void getTwice() {
    // 模拟查询id为1的用户
    User user1 = userService.get(1L);
    log.debug("【user1】= {}", user1);

    // 再次查询
    User user2 = userService.get(1L);
    log.debug("【user2】= {}", user2);

    //验证1: 断言 user1 和 user2是同一个java 对象，并未触发new操作重新创建对象; 验证2: 输出缓存命中日志
    // isSameAs 会比较对象内存地址是否一致，等于java表达式 user1 == user2
    Assertions.assertThat(user1).isSameAs(user2);
  }

  /**
   * 先存，再查询，查看日志验证缓存
   */
  @Test
  public void getAfterSave() {
    User savedUser = userService.saveOrUpdate(new User(4L, "user4"));

    //Cache entry for key '4' found in cache 'user-caffeine-cache1'
    User user = userService.get(4L);

    //Cache entry for key '4' found in cache 'user-caffeine-cache1'
    User user4 = userService.get(4L);

    // 验证存入的对象和取回的对象是同一个java 对象，并未触发new操作重新创建对象; 验证2: 输出缓存命中日志
    Assertions.assertThat(savedUser).isSameAs(user).isSameAs(user4);
  }

  /**
   * 测试删除，查看缓存命中
   */
  @Test
  public void deleteUser() {
    // 查询一次，使cache中存在缓存数据
    userService.get(1L);
    // 删除，查看cache是否存在缓存数据
    userService.delete(1L);
  }

  /**
   * 测试缓存过期策略
   */
  @Test
  public void cacheSpecExpireAfterAccess() throws InterruptedException {
    User user = userService.get(1L);
    // 通过spring boot 提供的caffeine.spec配置定义了缓存会5s过期，线程睡眠6s后所有缓存过期
    Thread.sleep(6000L);
    // 重新触发生成缓存
    User newUser = userService.get(1L);
    // 断言user 和 newUser并非同一对象，触发了new操作，两者内存地址有变化
    Assertions.assertThat(user).isNotSameAs(newUser);
  }

  /**
   * 测试缓存最大缓存条目数策略
   */
  @Test
  public void cacheSpecMaximumSize() throws InterruptedException {
    // 触发三次缓存操作，让缓存条目达到最大值 -> maximumSize=3
    User user1 = userService.get(1L);
    User user2 = userService.get(2L);
    User user3 = userService.get(3L);

    // 验证user1从缓存中移除
    // 触发第四次缓存操作，将会移除user1缓存。
    User user4 = userService.saveOrUpdate(new User(4L, "user4"));
    //等待缓存移除完成
    Thread.sleep(1000L);

    // 重新获取时，必然未命中缓存，触发new操作，重新创建，和user1对比，内存地址发生变化
    // 日志输出：No cache entry for key '1' in cache(s) [user-caffeine-cache1]
    // 并且触发第五次缓存操作，重新将user1缓存至队尾
    User newUser1 = userService.get(1L);
    Assertions.assertThat(user1).isNotSameAs(newUser1);

    // 验证user2,user3仍然保留在缓存中
    // 日志输出：Cache entry for key '2' found in cache 'user-caffeine-cache1'
    // 日志输出：Cache entry for key '3' found in cache 'user-caffeine-cache1'
    User newUser2 = userService.get(2L);
    Assertions.assertThat(user2).isSameAs(newUser2);
    User newUser3 = userService.get(3L);
    Assertions.assertThat(user3).isSameAs(newUser3);

    // 验证user4不存在于缓存中
    User newUser4 = userService.get(4L);
    Assertions.assertThat(user4).isNotSameAs(newUser4);
  }
}
