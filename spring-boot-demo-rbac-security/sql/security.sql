/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : spring-boot-demo

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 12/12/2018 18:52:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sec_permission
-- ----------------------------
DROP TABLE IF EXISTS `sec_permission`;
CREATE TABLE `sec_permission`
(
  `id`         bigint(64)  NOT NULL COMMENT '主键',
  `name`       varchar(50) NOT NULL COMMENT '权限名',
  `url`        varchar(1000) DEFAULT NULL COMMENT '类型为页面时，代表前端路由地址，类型为按钮时，代表后端接口地址',
  `type`       int(2)      NOT NULL COMMENT '权限类型，页面-1，按钮-2',
  `permission` varchar(50)   DEFAULT NULL COMMENT '权限表达式',
  `method`     varchar(50)   DEFAULT NULL COMMENT '后端接口访问方式',
  `sort`       int(11)     NOT NULL COMMENT '排序',
  `parent_id`  bigint(64)  NOT NULL COMMENT '父级id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='权限表';

-- ----------------------------
-- Records of sec_permission
-- ----------------------------
BEGIN;
INSERT INTO `sec_permission`
VALUES (1072806379288399872, '测试页面', '/test', 1, 'page:test', NULL, 1, 0);
INSERT INTO `sec_permission`
VALUES (1072806379313565696, '测试页面-查询', '/**/test', 2, 'btn:test:query', 'GET', 1, 1072806379288399872);
INSERT INTO `sec_permission`
VALUES (1072806379330342912, '测试页面-添加', '/**/test', 2, 'btn:test:insert', 'POST', 2, 1072806379288399872);
INSERT INTO `sec_permission`
VALUES (1072806379342925824, '监控在线用户页面', '/monitor', 1, 'page:monitor:online', NULL, 2, 0);
INSERT INTO `sec_permission`
VALUES (1072806379363897344, '在线用户页面-查询', '/**/api/monitor/online/user', 2, 'btn:monitor:online:query', 'GET', 1,
        1072806379342925824);
INSERT INTO `sec_permission`
VALUES (1072806379384868864, '在线用户页面-踢出', '/**/api/monitor/online/user/kickout', 2, 'btn:monitor:online:kickout',
        'DELETE', 2, 1072806379342925824);
COMMIT;

-- ----------------------------
-- Table structure for sec_role
-- ----------------------------
DROP TABLE IF EXISTS `sec_role`;
CREATE TABLE `sec_role`
(
  `id`          bigint(64)  NOT NULL COMMENT '主键',
  `name`        varchar(50) NOT NULL COMMENT '角色名',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `create_time` bigint(13)  NOT NULL COMMENT '创建时间',
  `update_time` bigint(13)  NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='角色表';

-- ----------------------------
-- Records of sec_role
-- ----------------------------
BEGIN;
INSERT INTO `sec_role`
VALUES (1072806379208708096, '管理员', '超级管理员', 1544611947239, 1544611947239);
INSERT INTO `sec_role`
VALUES (1072806379238068224, '普通用户', '普通用户', 1544611947246, 1544611947246);
COMMIT;

-- ----------------------------
-- Table structure for sec_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sec_role_permission`;
CREATE TABLE `sec_role_permission`
(
  `role_id`       bigint(64) NOT NULL COMMENT '角色主键',
  `permission_id` bigint(64) NOT NULL COMMENT '权限主键',
  PRIMARY KEY (`role_id`, `permission_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='角色权限关系表';

-- ----------------------------
-- Records of sec_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `sec_role_permission`
VALUES (1072806379208708096, 1072806379288399872);
INSERT INTO `sec_role_permission`
VALUES (1072806379208708096, 1072806379313565696);
INSERT INTO `sec_role_permission`
VALUES (1072806379208708096, 1072806379330342912);
INSERT INTO `sec_role_permission`
VALUES (1072806379208708096, 1072806379342925824);
INSERT INTO `sec_role_permission`
VALUES (1072806379208708096, 1072806379363897344);
INSERT INTO `sec_role_permission`
VALUES (1072806379208708096, 1072806379384868864);
INSERT INTO `sec_role_permission`
VALUES (1072806379238068224, 1072806379288399872);
INSERT INTO `sec_role_permission`
VALUES (1072806379238068224, 1072806379313565696);
COMMIT;

-- ----------------------------
-- Table structure for sec_user
-- ----------------------------
DROP TABLE IF EXISTS `sec_user`;
CREATE TABLE `sec_user`
(
  `id`          bigint(64)  NOT NULL COMMENT '主键',
  `username`    varchar(50) NOT NULL COMMENT '用户名',
  `password`    varchar(60) NOT NULL COMMENT '密码',
  `nickname`    varchar(255)         DEFAULT NULL COMMENT '昵称',
  `phone`       varchar(11)          DEFAULT NULL COMMENT '手机',
  `email`       varchar(50)          DEFAULT NULL COMMENT '邮箱',
  `birthday`    bigint(13)           DEFAULT NULL COMMENT '生日',
  `sex`         int(2)               DEFAULT NULL COMMENT '性别，男-1，女-2',
  `status`      int(2)      NOT NULL DEFAULT '1' COMMENT '状态，启用-1，禁用-0',
  `create_time` bigint(13)  NOT NULL COMMENT '创建时间',
  `update_time` bigint(13)  NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `email` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户表';

-- ----------------------------
-- Records of sec_user
-- ----------------------------
BEGIN;
INSERT INTO `sec_user`
VALUES (1072806377661009920, 'admin', '$2a$10$64iuSLkKNhpTN19jGHs7xePvFsub7ZCcCmBqEYw8fbACGTE3XetYq', '管理员',
        '17300000000', 'admin@xkcoding.com', 785433600000, 1, 1, 1544611947032, 1544611947032);
INSERT INTO `sec_user`
VALUES (1072806378780889088, 'user', '$2a$10$OUDl4thpcHfs7WZ1kMUOb.ZO5eD4QANW5E.cexBLiKDIzDNt87QbO', '普通用户',
        '17300001111', 'user@xkcoding.com', 785433600000, 1, 1, 1544611947234, 1544611947234);
COMMIT;

-- ----------------------------
-- Table structure for sec_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sec_user_role`;
CREATE TABLE `sec_user_role`
(
  `user_id` bigint(64) NOT NULL COMMENT '用户主键',
  `role_id` bigint(64) NOT NULL COMMENT '角色主键',
  PRIMARY KEY (`user_id`, `role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户角色关系表';

-- ----------------------------
-- Records of sec_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sec_user_role`
VALUES (1072806377661009920, 1072806379208708096);
INSERT INTO `sec_user_role`
VALUES (1072806378780889088, 1072806379238068224);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
