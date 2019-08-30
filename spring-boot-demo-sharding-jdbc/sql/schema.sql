USE `spring-boot-demo`;
DROP TABLE IF EXISTS `t_order_0`;
CREATE TABLE `t_order_0`
(
  `id`       BIGINT NOT NULL COMMENT '主键',
  `user_id`  BIGINT NOT NULL COMMENT '用户id',
  `order_id` BIGINT NOT NULL COMMENT '订单id',
  `remark`     VARCHAR(200) DEFAULT '' COMMENT '备注',
  primary key (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='Spring Boot Demo 分库分表 系列示例表0';

DROP TABLE IF EXISTS `t_order_1`;
CREATE TABLE `t_order_1`
(
  `id`       BIGINT NOT NULL COMMENT '主键',
  `user_id`  BIGINT NOT NULL COMMENT '用户id',
  `order_id` BIGINT NOT NULL COMMENT '订单id',
  `remark`     VARCHAR(200) DEFAULT '' COMMENT '备注',
  primary key (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='Spring Boot Demo 分库分表 系列示例表1';

DROP TABLE IF EXISTS `t_order_2`;
CREATE TABLE `t_order_2`
(
  `id`       BIGINT NOT NULL COMMENT '主键',
  `user_id`  BIGINT NOT NULL COMMENT '用户id',
  `order_id` BIGINT NOT NULL COMMENT '订单id',
  `remark`     VARCHAR(200) DEFAULT '' COMMENT '备注',
  primary key (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='Spring Boot Demo 分库分表 系列示例表2';

USE `spring-boot-demo-2`;

DROP TABLE IF EXISTS `t_order_0`;
CREATE TABLE `t_order_0`
(
  `id`       BIGINT NOT NULL COMMENT '主键',
  `user_id`  BIGINT NOT NULL COMMENT '用户id',
  `order_id` BIGINT NOT NULL COMMENT '订单id',
  `remark`     VARCHAR(200) DEFAULT '' COMMENT '备注',
  primary key (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='Spring Boot Demo 分库分表 系列示例表0';

DROP TABLE IF EXISTS `t_order_1`;
CREATE TABLE `t_order_1`
(
  `id`       BIGINT NOT NULL COMMENT '主键',
  `user_id`  BIGINT NOT NULL COMMENT '用户id',
  `order_id` BIGINT NOT NULL COMMENT '订单id',
  `remark`     VARCHAR(200) DEFAULT '' COMMENT '备注',
  primary key (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='Spring Boot Demo 分库分表 系列示例表1';

DROP TABLE IF EXISTS `t_order_2`;
CREATE TABLE `t_order_2`
(
  `id`       BIGINT NOT NULL COMMENT '主键',
  `user_id`  BIGINT NOT NULL COMMENT '用户id',
  `order_id` BIGINT NOT NULL COMMENT '订单id',
  `remark`     VARCHAR(200) DEFAULT '' COMMENT '备注',
  primary key (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='Spring Boot Demo 分库分表 系列示例表2';