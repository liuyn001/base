/*
Navicat MySQL Data Transfer

Source Server         : ip
Source Server Version : 50717
Source Host           : 192.168.0.212:33066
Source Database       : base

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-08-31 14:46:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tmenu
-- ----------------------------
DROP TABLE IF EXISTS `tmenu`;
CREATE TABLE `tmenu` (
  `id` varchar(36) NOT NULL,
  `text` varchar(100) DEFAULT NULL,
  `iconcls` varchar(50) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `pid` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4C3C3B3D59FABE6` (`pid`),
  CONSTRAINT `FK4C3C3B3D59FABE6` FOREIGN KEY (`pid`) REFERENCES `tmenu` (`id`),
  CONSTRAINT `FK974nv1km71bwb2yf1cii0ukq0` FOREIGN KEY (`pid`) REFERENCES `tmenu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tmenu
-- ----------------------------
INSERT INTO `tmenu` VALUES ('0', '首页', null, '', null);
INSERT INTO `tmenu` VALUES ('buggl', 'BUG管理', null, '', 'xtgl');
INSERT INTO `tmenu` VALUES ('cdgl', '菜单管理', null, '', 'xtgl');
INSERT INTO `tmenu` VALUES ('jsgl', '角色管理', null, '', 'xtgl');
INSERT INTO `tmenu` VALUES ('qxgl', '权限管理', null, '', 'xtgl');
INSERT INTO `tmenu` VALUES ('xtgl', '系统管理', null, '', '0');
INSERT INTO `tmenu` VALUES ('yhgl', '用户管理', null, '/admin/yhgl.jsp', 'xtgl');

-- ----------------------------
-- Table structure for tuser
-- ----------------------------
DROP TABLE IF EXISTS `tuser`;
CREATE TABLE `tuser` (
  `id` varchar(36) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tuser
-- ----------------------------
INSERT INTO `tuser` VALUES ('0', 'admin', '21232f297a57a5a743894a0e4a801fc3', null, '2018-08-30 15:40:48');
