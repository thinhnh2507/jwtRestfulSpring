/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : restful

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 14/04/2021 22:42:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for _group
-- ----------------------------
DROP TABLE IF EXISTS `_group`;
CREATE TABLE `_group`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `is_deleted` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of _group
-- ----------------------------
INSERT INTO `_group` VALUES (1, 'Admin', 'admin', 'N');
INSERT INTO `_group` VALUES (2, 'User', 'user', 'N');

-- ----------------------------
-- Table structure for group_permission
-- ----------------------------
DROP TABLE IF EXISTS `group_permission`;
CREATE TABLE `group_permission`  (
  `group_id` int(0) NOT NULL,
  `permission_id` int(0) NOT NULL,
  PRIMARY KEY (`group_id`, `permission_id`) USING BTREE,
  INDEX `permission_id`(`permission_id`) USING BTREE,
  CONSTRAINT `group_permission_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `_group` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `group_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_permission
-- ----------------------------
INSERT INTO `group_permission` VALUES (1, 1);
INSERT INTO `group_permission` VALUES (2, 1);
INSERT INTO `group_permission` VALUES (1, 2);
INSERT INTO `group_permission` VALUES (2, 2);
INSERT INTO `group_permission` VALUES (1, 3);
INSERT INTO `group_permission` VALUES (2, 3);
INSERT INTO `group_permission` VALUES (1, 4);
INSERT INTO `group_permission` VALUES (2, 4);
INSERT INTO `group_permission` VALUES (1, 5);
INSERT INTO `group_permission` VALUES (2, 5);
INSERT INTO `group_permission` VALUES (1, 6);
INSERT INTO `group_permission` VALUES (2, 6);
INSERT INTO `group_permission` VALUES (1, 7);
INSERT INTO `group_permission` VALUES (2, 7);
INSERT INTO `group_permission` VALUES (1, 8);
INSERT INTO `group_permission` VALUES (1, 9);
INSERT INTO `group_permission` VALUES (1, 10);
INSERT INTO `group_permission` VALUES (1, 11);
INSERT INTO `group_permission` VALUES (1, 12);
INSERT INTO `group_permission` VALUES (1, 13);
INSERT INTO `group_permission` VALUES (1, 14);
INSERT INTO `group_permission` VALUES (1, 15);
INSERT INTO `group_permission` VALUES (1, 16);
INSERT INTO `group_permission` VALUES (1, 17);
INSERT INTO `group_permission` VALUES (1, 18);
INSERT INTO `group_permission` VALUES (1, 19);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `is_deleted` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE,
  UNIQUE INDEX `url`(`uri`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 'create user', '/user/create', 'post', 'N');
INSERT INTO `permission` VALUES (2, 'get user', '/user/{id:\\d}', 'get', 'N');
INSERT INTO `permission` VALUES (3, 'update user', '/user/update', 'put', 'N');
INSERT INTO `permission` VALUES (4, 'list user', '/user/list', 'get', 'N');
INSERT INTO `permission` VALUES (5, 'deleted user', '/user/deleted/{id:\\d}', 'deleted', 'N');
INSERT INTO `permission` VALUES (6, 'change password user', '/user/changepassword', 'post', 'N');
INSERT INTO `permission` VALUES (7, 'import csv', '/user/import', 'post', 'N');
INSERT INTO `permission` VALUES (8, 'create group', '/group/create', 'post', 'N');
INSERT INTO `permission` VALUES (9, 'get group', '/group/{id:\\d}', 'get', 'N');
INSERT INTO `permission` VALUES (10, 'update group', '/group/update', 'put', 'N');
INSERT INTO `permission` VALUES (11, 'deleted group', '/group/deleted/{id:\\d}', 'delete', 'N');
INSERT INTO `permission` VALUES (12, 'user group mapping', '/group/ug/mapping', 'post', 'N');
INSERT INTO `permission` VALUES (13, 'gp mapping', '/group/gp/mapping', 'post', 'N');
INSERT INTO `permission` VALUES (14, 'permission create', '/permission/create', 'post', 'N');
INSERT INTO `permission` VALUES (15, 'get permission', '/permission/{id:\\d}', 'get', 'N');
INSERT INTO `permission` VALUES (16, 'list permission', '/permission/list', 'get', 'N');
INSERT INTO `permission` VALUES (17, 'update permission', '/permission/update', 'put', 'N');
INSERT INTO `permission` VALUES (18, 'deleted permission', '/permission/deleted/{id:\\d}', 'delete', 'N');
INSERT INTO `permission` VALUES (19, 'import permission', '/permission/import', 'post', 'N');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `first_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `last_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `is_active` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'Y',
  `is_deleted` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'superadmin', 'c3VwZXJhZG1pbg==', 'admin', 'admin', 'Y', 'N');
INSERT INTO `user` VALUES (2, 'thinh', 'dGhpbmg=', 'user', 'user', 'Y', 'N');
INSERT INTO `user` VALUES (3, 'abc@gmail.com', 'MTIzNDU2', 'thinh', 'thinh', 'Y', 'N');
INSERT INTO `user` VALUES (5, '123', 'MTIzMTIz', '123', '123', 'Y', 'N');

-- ----------------------------
-- Table structure for user_group
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group`  (
  `user_id` int(0) NOT NULL,
  `group_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_group
-- ----------------------------
INSERT INTO `user_group` VALUES (1, 1);
INSERT INTO `user_group` VALUES (2, 2);
INSERT INTO `user_group` VALUES (3, 1);

SET FOREIGN_KEY_CHECKS = 1;
