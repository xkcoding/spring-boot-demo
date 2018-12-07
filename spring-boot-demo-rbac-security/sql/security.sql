# 用户表
CREATE TABLE IF NOT EXISTS `sec_user`
(
  `id`          BIGINT(64)  NOT NULL COMMENT '主键',
  `username`    VARCHAR(50) NOT NULL COMMENT '用户名',
  `password`    VARCHAR(60) NOT NULL COMMENT '密码',
  `nickname`    VARCHAR(255)         DEFAULT NULL COMMENT '昵称',
  `phone`       VARCHAR(11)          DEFAULT NULL COMMENT '手机',
  `email`       VARCHAR(50)          DEFAULT NULL COMMENT '邮箱',
  `birthday`    BIGINT(13)           DEFAULT NULL COMMENT '生日',
  `sex`         TINYINT(1)           DEFAULT NULL COMMENT '性别，男-1，女-2',
  `status`      TINYINT(1)  NOT NULL DEFAULT '1' COMMENT '状态，启用-1，禁用-0',
  `create_time` BIGINT(13)  NOT NULL COMMENT '创建时间',
  `update_time` BIGINT(13)  NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `email` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT '用户表';

# 角色表
CREATE TABLE IF NOT EXISTS `sec_role`
(
  `id`          BIGINT(64)  NOT NULL COMMENT '主键',
  `name`        VARCHAR(50) NOT NULL COMMENT '角色名',
  `description` VARCHAR(100) DEFAULT NULL COMMENT '描述',
  `create_time` BIGINT(13)  NOT NULL COMMENT '创建时间',
  `update_time` BIGINT(13)  NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT '角色表';

# 权限表
CREATE TABLE IF NOT EXISTS `sec_permission`
(
  `id`         BIGINT(64)  NOT NULL COMMENT '主键',
  `name`       VARCHAR(50) NOT NULL COMMENT '权限名',
  `href`       VARCHAR(1000) DEFAULT NULL COMMENT '页面地址',
  `type`       TINYINT(1)  NOT NULL COMMENT '权限类型，页面-1，按钮-2',
  `permission` VARCHAR(50)   DEFAULT NULL COMMENT '权限表达式',
  `sort`       INT(11)     NOT NULL COMMENT '排序',
  `parent_id`  BIGINT(64)  NOT NULL COMMENT '父级id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT '权限表';

# 用户角色关系表
CREATE TABLE IF NOT EXISTS `sec_user_role`
(
  `user_id` BIGINT(64) NOT NULL COMMENT '用户主键',
  `role_id` BIGINT(64) NOT NULL COMMENT '角色主键',
  PRIMARY KEY (`user_id`, `role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT '用户角色关系表';

# 角色权限关系表
CREATE TABLE IF NOT EXISTS `sec_role_permission`
(
  `role_id`       BIGINT(64) NOT NULL COMMENT '角色主键',
  `permission_id` BIGINT(64) NOT NULL COMMENT '权限主键',
  PRIMARY KEY (`role_id`, `permission_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT '角色权限关系表';
