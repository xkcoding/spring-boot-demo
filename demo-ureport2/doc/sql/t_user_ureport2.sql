/*
 Navicat Premium Data Transfer

 Source Server         : dev
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : localhost:3306
 Source Schema         : spring-boot-demo

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 26/10/2020 23:30:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user_ureport2
-- ----------------------------
DROP TABLE IF EXISTS `t_user_ureport2`;
CREATE TABLE `t_user_ureport2` (
  `id` bigint(13) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '姓名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` tinyint(4) NOT NULL COMMENT '是否禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_user_ureport2
-- ----------------------------
BEGIN;
INSERT INTO `t_user_ureport2` VALUES (1, '测试人员 1', '2020-10-22 09:01:58', 1);
INSERT INTO `t_user_ureport2` VALUES (2, '测试人员 2', '2020-10-22 09:02:00', 0);
INSERT INTO `t_user_ureport2` VALUES (3, '测试人员 3', '2020-10-23 03:02:00', 1);
INSERT INTO `t_user_ureport2` VALUES (4, '测试人员 4', '2020-10-23 23:02:00', 1);
INSERT INTO `t_user_ureport2` VALUES (5, '测试人员 5', '2020-10-23 23:02:00', 1);
INSERT INTO `t_user_ureport2` VALUES (6, '测试人员 6', '2020-10-24 11:02:00', 0);
INSERT INTO `t_user_ureport2` VALUES (7, '测试人员 7', '2020-10-24 20:02:00', 0);
INSERT INTO `t_user_ureport2` VALUES (8, '测试人员 8', '2020-10-25 08:02:00', 1);
INSERT INTO `t_user_ureport2` VALUES (9, '测试人员 9', '2020-10-25 09:02:00', 1);
INSERT INTO `t_user_ureport2` VALUES (10, '测试人员 10', '2020-10-25 13:02:00', 1);
INSERT INTO `t_user_ureport2` VALUES (11, '测试人员 11', '2020-10-26 21:02:00', 0);
INSERT INTO `t_user_ureport2` VALUES (12, '测试人员 12', '2020-10-26 23:02:00', 1);
INSERT INTO `t_user_ureport2` VALUES (13, '测试人员 13', '2020-10-26 23:02:00', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
