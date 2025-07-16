package com.xkcoding.cache.caffeine.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户表实体类
 *
 * @author ccposhing
 * @since 2025-07-16
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {

  private static final long serialVersionUID = 2892248514883451461L;
  /**
   * 主键id
   */
  private Long id;
  /**
   * 姓名
   */
  private String name;
}
