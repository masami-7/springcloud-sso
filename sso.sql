/*
Navicat MySQL Data Transfer

Source Server         : 服务器
Source Server Version : 50736
Source Host           : 39.108.125.165:3306
Source Database       : sso

Target Server Type    : MYSQL
Target Server Version : 50736
File Encoding         : 65001

Date: 2023-02-22 18:07:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_menus
-- ----------------------------
DROP TABLE IF EXISTS `tb_menus`;
CREATE TABLE `tb_menus` (
  `id` int(11) NOT NULL,
  `menu_name` varchar(255) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_menus
-- ----------------------------
INSERT INTO `tb_menus` VALUES ('1', 'select resource', 'sys:res:list');
INSERT INTO `tb_menus` VALUES ('2', 'upload resource', 'sys:res:create');
INSERT INTO `tb_menus` VALUES ('3', 'delete roles', 'sys:res:delete');

-- ----------------------------
-- Table structure for tb_role_menus
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_menus`;
CREATE TABLE `tb_role_menus` (
  `id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role_menus
-- ----------------------------
INSERT INTO `tb_role_menus` VALUES ('1', '1', '1');
INSERT INTO `tb_role_menus` VALUES ('2', '1', '2');
INSERT INTO `tb_role_menus` VALUES ('3', '1', '3');
INSERT INTO `tb_role_menus` VALUES ('4', '2', '1');

-- ----------------------------
-- Table structure for tb_roles
-- ----------------------------
DROP TABLE IF EXISTS `tb_roles`;
CREATE TABLE `tb_roles` (
  `id` int(11) NOT NULL,
  `role_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_roles
-- ----------------------------
INSERT INTO `tb_roles` VALUES ('1', '管理员');
INSERT INTO `tb_roles` VALUES ('2', '用户');

-- ----------------------------
-- Table structure for tb_user_roles
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_roles`;
CREATE TABLE `tb_user_roles` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_roles
-- ----------------------------
INSERT INTO `tb_user_roles` VALUES ('1', '1', '1');
INSERT INTO `tb_user_roles` VALUES ('2', '2', '2');

-- ----------------------------
-- Table structure for tb_users
-- ----------------------------
DROP TABLE IF EXISTS `tb_users`;
CREATE TABLE `tb_users` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_users
-- ----------------------------
INSERT INTO `tb_users` VALUES ('1', 'admin', '$2a$10$W0GScZv4nZaYm8QLpNyxEuud5N7Stzs9H5N7jR5iV0k45i9N1wI8m', null);
INSERT INTO `tb_users` VALUES ('2', 'user', '$2a$10$W0GScZv4nZaYm8QLpNyxEuud5N7Stzs9H5N7jR5iV0k45i9N1wI8m', null);
