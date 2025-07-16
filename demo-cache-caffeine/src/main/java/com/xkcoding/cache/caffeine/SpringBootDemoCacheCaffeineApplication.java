package com.xkcoding.cache.caffeine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * <p>
 * 启动类
 * </p>
 *
 * @author ccposhing
 * @since 2025-07-16
 */
@SpringBootApplication
@EnableCaching
public class SpringBootDemoCacheCaffeineApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootDemoCacheCaffeineApplication.class, args);
  }
}
