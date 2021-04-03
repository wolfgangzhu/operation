/*
 Navicat Premium Data Transfer

 Source Server         : nacos_5.6.46
 Source Server Type    : MySQL
 Source Server Version : 50646
 Source Host           : localhost:3306
 Source Schema         : operation

 Target Server Type    : MySQL
 Target Server Version : 50646
 File Encoding         : 65001

 Date: 17/02/2020 19:56:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for operation
-- ----------------------------
DROP TABLE IF EXISTS `operation`;
CREATE TABLE `operation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `biz_key` varchar(50) DEFAULT NULL,
  `is_invoke` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0代表未执行，1代表已执行',
  `invoke_time` datetime DEFAULT NULL COMMENT '执行时间',
  `oper_status` varchar(10) NOT NULL COMMENT '操作状态（0:创建，1:已完成， 2：已取消，3. 异常）',
  `user_uuid` varchar(64) NOT NULL COMMENT '操作人员',
  `comment` varchar(1024) DEFAULT NULL COMMENT '备注',
  `is_done` tinyint(1) NOT NULL COMMENT '操作是否结束，0代表未结束，1代表已结束',
  `receiver` varchar(255) DEFAULT NULL,
  `caller` varchar(255) DEFAULT NULL,
  `receiver_name` varchar(30) DEFAULT NULL,
  `expire_mills` bigint(14) DEFAULT NULL,
  `expire_time` datetime DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for operation_instance
-- ----------------------------
DROP TABLE IF EXISTS `operation_instance`;
CREATE TABLE `operation_instance` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `oper_id` int(11) NOT NULL COMMENT '最新的oper',
  `instance_type` varchar(10) NOT NULL COMMENT '实例类型',
  `instance_id` varchar(30) NOT NULL COMMENT '实例编号',
  `biz_key` varchar(40) DEFAULT NULL COMMENT 'biz_key',
  `oper_status` varchar(255) NOT NULL COMMENT '操作状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_operationInstancetype` (`oper_id`,`instance_id`,`instance_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for operation_instance_latest
-- ----------------------------
DROP TABLE IF EXISTS `operation_instance_latest`;
CREATE TABLE `operation_instance_latest` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `oper_id` int(11) NOT NULL COMMENT '最新的oper',
  `instance_type` varchar(10) NOT NULL COMMENT '实例类型',
  `instance_id` varchar(30) NOT NULL COMMENT '实例编号',
  `biz_key` varchar(40) DEFAULT NULL COMMENT 'biz_key',
  `oper_status` varchar(255) NOT NULL COMMENT '操作状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_instancetypeid` (`instance_id`,`instance_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for operation_parameters
-- ----------------------------
DROP TABLE IF EXISTS `operation_parameters`;
CREATE TABLE `operation_parameters` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operation_id` int(11) NOT NULL,
  `params` mediumblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
