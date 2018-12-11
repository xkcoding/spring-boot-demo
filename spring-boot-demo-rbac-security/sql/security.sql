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

 Date: 10/12/2018 14:46:38
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
VALUES (1072019441543417856, '测试页面', '/test', 1, 'page:test', NULL, 1, 0);
INSERT INTO `sec_permission`
VALUES (1072019441564389376, '测试页面-查询', '/**/test', 2, 'btn:test:query', 'GET', 1, 1072019441543417856);
INSERT INTO `sec_permission`
VALUES (1072019441576972288, '测试页面-添加', '/**/test', 2, 'btn:test:insert', 'POST', 2, 1072019441543417856);
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
VALUES (1072019441480503296, '管理员', '超级管理员', 1544424326664, 1544424326664);
INSERT INTO `sec_role`
VALUES (1072019441501474816, '普通用户', '普通用户', 1544424326669, 1544424326669);
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
VALUES (1072019441480503296, 1072019441543417856);
INSERT INTO `sec_role_permission`
VALUES (1072019441480503296, 1072019441564389376);
INSERT INTO `sec_role_permission`
VALUES (1072019441480503296, 1072019441576972288);
INSERT INTO `sec_role_permission`
VALUES (1072019441501474816, 1072019441543417856);
INSERT INTO `sec_role_permission`
VALUES (1072019441501474816, 1072019441564389376);
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
VALUES (1072019440205434880, 'admin', '$2a$10$TwdumjYKUYRQvc3VC8dleOlWr4Q2TysQtfmCMplOWygOzyfrKQee2', '管理员',
        '17300000000', 'admin@xkcoding.com', 785433600000, 1, 1, 1544424326483, 1544424326483);
INSERT INTO `sec_user`
VALUES (1072019441035907072, 'user', '$2a$10$8hL7INOxQCzDzI08GGYNCOFKV6mjDcOqhJ/7c1VeF9agO.wBS3ylq', '普通用户',
        '17300001111', 'user@xkcoding.com', 785433600000, 1, 1, 1544424326659, 1544424326659);
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
VALUES (1072019440205434880, 1072019441480503296);
INSERT INTO `sec_user_role`
VALUES (1072019441035907072, 1072019441501474816);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;