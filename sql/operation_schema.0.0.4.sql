/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 50714
 Source Host           : 172.16.0.11:30600
 Source Schema         : productsdn

 Target Server Type    : MySQL
 Target Server Version : 50714
 File Encoding         : 65001

 Date: 10/03/2021 10:25:26
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for operation
-- ----------------------------
DROP TABLE IF EXISTS `operation`;
CREATE TABLE `operation`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `biz_key`     varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
    `is_invoke`   tinyint(1) NOT NULL DEFAULT '0' COMMENT '0代表未执行，1代表已执行',
    `invoke_time` datetime                                 DEFAULT NULL COMMENT '执行时间',
    `oper_status` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作状态（0:创建，1:已完成， 2：已取消，3. 异常）',
    `user_uuid`   varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作人员',
    `comment`     varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
    `is_done`     tinyint(1) NOT NULL COMMENT '操作是否结束，0代表未结束，1代表已结束',
    `type`        tinyint(4) NOT NULL COMMENT '0代表callback, 1代表定时任务',
    `caller`      varchar(255) COLLATE utf8mb4_unicode_ci  DEFAULT NULL,
    `create_time` datetime                               NOT NULL,
    `update_time` datetime                                 DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=211 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for operation_callback
-- ----------------------------
DROP TABLE IF EXISTS `operation_callback`;
CREATE TABLE `operation_callback`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT,
    `operation_id` int(11) NOT NULL,
    `receive_time` datetime NOT NULL,
    `success`      tinyint(1) NOT NULL,
    `data`         mediumblob,
    `msg`          varchar(1024) DEFAULT NULL,
    `properties`   mediumblob,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for operation_instance
-- ----------------------------
DROP TABLE IF EXISTS `operation_instance`;
CREATE TABLE `operation_instance`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `oper_id`       int(11) NOT NULL COMMENT '最新的oper',
    `instance_type` varchar(10) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '实例类型',
    `instance_id`   varchar(64) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '实例编号',
    `biz_key`       varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'biz_key',
    `oper_status`   varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作状态',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_operationInstancetype` (`oper_id`,`instance_id`,`instance_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=211 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for operation_instance_latest
-- ----------------------------
DROP TABLE IF EXISTS `operation_instance_latest`;
CREATE TABLE `operation_instance_latest`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `oper_id`       int(11) NOT NULL COMMENT '最新的oper',
    `instance_type` varchar(10) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '实例类型',
    `instance_id`   varchar(64) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '实例编号',
    `biz_key`       varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'biz_key',
    `oper_status`   varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作状态',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_instancetypeid` (`instance_id`,`instance_type`) USING BTREE
);

-- ----------------------------
-- Table structure for operation_parameters
-- ----------------------------
DROP TABLE IF EXISTS `operation_parameters`;
CREATE TABLE `operation_parameters`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT,
    `operation_id` int(11) NOT NULL,
    `params`       mediumblob,
    PRIMARY KEY (`id`) USING BTREE
);

-- ----------------------------
-- Table structure for operation_receiver
-- ----------------------------
DROP TABLE IF EXISTS `operation_receiver`;
CREATE TABLE `operation_receiver`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `operation_id`   int(11) NOT NULL,
    `receiver_class` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `receiver_name`  varchar(30) COLLATE utf8mb4_unicode_ci  DEFAULT NULL,
    `expire_mills`   bigint(20) NOT NULL,
    `expire_time`    datetime NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
);

-- ----------------------------
-- Table structure for operation_task
-- ----------------------------
DROP TABLE IF EXISTS `operation_task`;
CREATE TABLE `operation_task`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `operation_id`   int(11) NOT NULL,
    `task_id`        varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `task_caller`    varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `task_name`      varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `is_done`        tinyint(1) NOT NULL,
    `exception`      mediumblob,
    `last_call_time` datetime                               DEFAULT NULL,
    `period`         bigint(20) NOT NULL COMMENT '执行的周期毫秒',
    `task_status`    varchar(20) COLLATE utf8mb4_unicode_ci  NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
);

SET
FOREIGN_KEY_CHECKS = 1;
