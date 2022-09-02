CREATE TABLE `db_stock`
(
  `id`    bigint(20) NOT NULL AUTO_INCREMENT,
  `name`  varchar(255) DEFAULT NULL COMMENT '货物名称',
  `count` bigint(11)   DEFAULT NULL COMMENT '库存量',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='Spring Boot Demo 分布式锁-模拟库存表';
