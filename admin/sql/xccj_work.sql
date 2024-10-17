/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80034 (8.0.34)
 Source Host           : localhost:3306
 Source Schema         : xccj_work

 Target Server Type    : MySQL
 Target Server Version : 80034 (8.0.34)
 File Encoding         : 65001

 Date: 17/10/2024 16:40:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bus_abnormal
-- ----------------------------
DROP TABLE IF EXISTS `bus_abnormal`;
CREATE TABLE `bus_abnormal`  (
  `abnormal_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单类型：见类 OrderConstants',
  `order_id` bigint NOT NULL DEFAULT 0 COMMENT '工单id，order_type对应的表',
  `abnormal_info` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '异常描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`abnormal_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工单异常表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_abnormal
-- ----------------------------

-- ----------------------------
-- Table structure for bus_abnormal_img
-- ----------------------------
DROP TABLE IF EXISTS `bus_abnormal_img`;
CREATE TABLE `bus_abnormal_img`  (
  `image_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `abnormal_id` bigint NOT NULL DEFAULT 0 COMMENT '异常id，表xccj_work.bus_abnormal',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '图片地址',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`image_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '异常图片表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_abnormal_img
-- ----------------------------

-- ----------------------------
-- Table structure for bus_allot
-- ----------------------------
DROP TABLE IF EXISTS `bus_allot`;
CREATE TABLE `bus_allot`  (
  `allot_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `allot_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '类型：见类 OrderConstants',
  `allot_way` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单分配方式：0=自动分配，1=管理员分配，2=抢单',
  `user_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '人员类型：对应表xccj_community.bus_staff.work_post，英文逗号拼接',
  `assist_num` int NOT NULL DEFAULT 0 COMMENT '协助人数',
  `gap_hour` int NOT NULL DEFAULT 0 COMMENT '间隔时长',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`allot_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分配设置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_allot
-- ----------------------------
INSERT INTO `bus_allot` VALUES (1, 1, '0', '2', '0', 1, 1, '15288888888', '2023-05-15 17:59:31', '15288888888', '2023-05-15 18:53:38', '0');
INSERT INTO `bus_allot` VALUES (2, 1, '1', '2', '0', 1, 1, '15288888888', '2023-05-15 17:59:37', '15288888888', '2023-05-15 19:02:50', '0');
INSERT INTO `bus_allot` VALUES (3, 1, '2', '2', '0', 1, 1, '15288888888', '2023-05-15 17:59:54', '15288888888', '2023-05-23 14:48:40', '0');
INSERT INTO `bus_allot` VALUES (4, 1, '3', '2', '0', 0, 1, '15288888888', '2023-05-15 18:00:02', '15288888888', '2023-05-23 15:41:41', '0');
INSERT INTO `bus_allot` VALUES (5, 1, '4', '0', '0', 1, 1, '15288888888', '2023-05-15 18:00:11', '15288888888', '2023-05-15 18:00:11', '0');
INSERT INTO `bus_allot` VALUES (6, 1, '5', '0', '0', 1, 1, '15288888888', '2023-05-15 18:00:21', '15288888888', '2023-05-15 18:00:21', '0');
INSERT INTO `bus_allot` VALUES (7, 1, '6', '0', '0', 1, 1, '15288888888', '2023-05-15 18:00:33', '15288888888', '2023-05-23 15:41:36', '0');
INSERT INTO `bus_allot` VALUES (8, 1, '7', '2', '0', 1, 1, '15288888888', '2023-05-15 18:00:46', '15288888888', '2023-05-16 14:28:52', '0');
INSERT INTO `bus_allot` VALUES (9, 1, '8', '0', '0', 1, 1, '15288888888', '2023-05-15 18:00:54', '15288888888', '2023-05-23 15:40:51', '0');
INSERT INTO `bus_allot` VALUES (10, 6, '1', '0', '0', 1, 5, '15656332018', '2023-05-31 10:53:03', '15656332018', '2023-05-31 10:53:03', '0');
INSERT INTO `bus_allot` VALUES (11, 6, '3', '0', '0', 1, 12, '15656332018', '2023-05-31 10:53:28', '15656332018', '2023-05-31 10:53:28', '0');
INSERT INTO `bus_allot` VALUES (12, 6, '0', '0', '0', 1, 12, '15656332018', '2023-06-09 09:43:39', '15656332018', '2023-06-09 09:43:39', '0');
INSERT INTO `bus_allot` VALUES (13, 6, '2', '0', '0', 1, 12, '15656332018', '2023-06-09 09:44:04', '15656332018', '2023-06-09 09:44:04', '0');
INSERT INTO `bus_allot` VALUES (14, 6, '7', '0', '0', 0, 1, '15656332018', '2023-06-09 14:20:54', '15656332018', '2023-06-09 14:20:54', '0');
INSERT INTO `bus_allot` VALUES (15, 5, '7', '0', '0', 1, 1, '15200000000', '2023-06-09 14:30:29', '15200000000', '2023-06-09 14:35:36', '0');
INSERT INTO `bus_allot` VALUES (16, 5, '0', '0', '0', 1, 1, '15200000000', '2023-06-09 14:33:29', '15200000000', '2023-06-09 14:33:29', '0');
INSERT INTO `bus_allot` VALUES (17, 5, '1', '0', '0', 1, 1, '15200000000', '2023-06-09 14:33:37', '15200000000', '2023-06-09 14:33:37', '0');
INSERT INTO `bus_allot` VALUES (18, 5, '2', '0', '0', 1, 1, '15200000000', '2023-06-09 14:33:43', '15200000000', '2023-06-09 14:33:43', '0');
INSERT INTO `bus_allot` VALUES (19, 5, '3', '0', '0', 1, 1, '15200000000', '2023-06-09 14:33:51', '15200000000', '2023-06-09 14:33:51', '0');
INSERT INTO `bus_allot` VALUES (20, 5, '4', '0', '0', 1, 1, '15200000000', '2023-06-09 14:33:58', '15200000000', '2023-06-09 14:33:58', '0');
INSERT INTO `bus_allot` VALUES (21, 5, '5', '0', '0', 1, 1, '15200000000', '2023-06-09 14:34:09', '15200000000', '2023-06-09 14:34:09', '0');
INSERT INTO `bus_allot` VALUES (22, 5, '6', '0', '0', 1, 1, '15200000000', '2023-06-09 14:34:40', '15200000000', '2023-06-09 14:34:40', '0');
INSERT INTO `bus_allot` VALUES (23, 5, '8', '0', '0', 1, 1, '15200000000', '2023-06-09 14:37:10', '15200000000', '2023-06-09 14:37:10', '0');
INSERT INTO `bus_allot` VALUES (24, 7, '0', '1', '0', 1, 24, '18888888888', '2023-06-26 16:58:41', '18888888888', '2023-06-26 16:58:41', '0');
INSERT INTO `bus_allot` VALUES (25, 7, '1', '1', '0', 1, 24, '18888888888', '2023-06-26 17:11:45', '18888888888', '2023-06-26 17:11:45', '0');
INSERT INTO `bus_allot` VALUES (26, 7, '2', '1', '0', 1, 24, '18888888888', '2023-06-26 17:13:24', '18888888888', '2023-06-26 17:13:24', '0');
INSERT INTO `bus_allot` VALUES (27, 7, '3', '1', '0', 1, 24, '18888888888', '2023-06-26 17:13:47', '18888888888', '2023-06-26 17:13:47', '0');
INSERT INTO `bus_allot` VALUES (28, 7, '4', '1', '0', 1, 24, '18888888888', '2023-06-26 17:15:33', '18888888888', '2023-06-26 17:15:33', '0');
INSERT INTO `bus_allot` VALUES (29, 7, '5', '1', '0', 1, 24, '18888888888', '2023-06-26 17:15:54', '18888888888', '2023-06-29 09:11:46', '0');
INSERT INTO `bus_allot` VALUES (30, 7, '6', '1', '0', 1, 24, '18888888888', '2023-06-26 17:16:11', '18888888888', '2023-06-26 17:16:11', '0');
INSERT INTO `bus_allot` VALUES (31, 7, '7', '1', '0', 1, 24, '18888888888', '2023-06-26 17:16:43', '18888888888', '2023-06-26 17:16:43', '0');
INSERT INTO `bus_allot` VALUES (32, 7, '8', '1', '0', 1, 24, '18888888888', '2023-06-26 17:17:32', '18888888888', '2023-06-26 17:17:32', '0');

-- ----------------------------
-- Table structure for bus_allot_post
-- ----------------------------
DROP TABLE IF EXISTS `bus_allot_post`;
CREATE TABLE `bus_allot_post`  (
  `allot_id` bigint NOT NULL DEFAULT 0 COMMENT '分配设置id，表xccj_work.bus_allot',
  `user_post` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '人员岗位：对应表xccj_community.bus_staff.work_post'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分配设置-人员岗位（角色）关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_allot_post
-- ----------------------------
INSERT INTO `bus_allot_post` VALUES (5, '2');
INSERT INTO `bus_allot_post` VALUES (6, '1');
INSERT INTO `bus_allot_post` VALUES (1, '0');
INSERT INTO `bus_allot_post` VALUES (2, '0');
INSERT INTO `bus_allot_post` VALUES (8, '0');
INSERT INTO `bus_allot_post` VALUES (3, '0');
INSERT INTO `bus_allot_post` VALUES (9, '0');
INSERT INTO `bus_allot_post` VALUES (7, '1');
INSERT INTO `bus_allot_post` VALUES (4, '4');
INSERT INTO `bus_allot_post` VALUES (10, '0');
INSERT INTO `bus_allot_post` VALUES (11, '4');
INSERT INTO `bus_allot_post` VALUES (12, '0');
INSERT INTO `bus_allot_post` VALUES (13, '1');
INSERT INTO `bus_allot_post` VALUES (14, '0');
INSERT INTO `bus_allot_post` VALUES (16, '0');
INSERT INTO `bus_allot_post` VALUES (17, '0');
INSERT INTO `bus_allot_post` VALUES (18, '0');
INSERT INTO `bus_allot_post` VALUES (19, '4');
INSERT INTO `bus_allot_post` VALUES (20, '2');
INSERT INTO `bus_allot_post` VALUES (21, '1');
INSERT INTO `bus_allot_post` VALUES (22, '1');
INSERT INTO `bus_allot_post` VALUES (15, '0');
INSERT INTO `bus_allot_post` VALUES (23, '0');
INSERT INTO `bus_allot_post` VALUES (24, '0');
INSERT INTO `bus_allot_post` VALUES (24, '3');
INSERT INTO `bus_allot_post` VALUES (25, '0');
INSERT INTO `bus_allot_post` VALUES (26, '1');
INSERT INTO `bus_allot_post` VALUES (26, '2');
INSERT INTO `bus_allot_post` VALUES (27, '4');
INSERT INTO `bus_allot_post` VALUES (28, '2');
INSERT INTO `bus_allot_post` VALUES (28, '1');
INSERT INTO `bus_allot_post` VALUES (28, '0');
INSERT INTO `bus_allot_post` VALUES (30, '1');
INSERT INTO `bus_allot_post` VALUES (31, '0');
INSERT INTO `bus_allot_post` VALUES (31, '1');
INSERT INTO `bus_allot_post` VALUES (31, '4');
INSERT INTO `bus_allot_post` VALUES (32, '0');
INSERT INTO `bus_allot_post` VALUES (32, '1');
INSERT INTO `bus_allot_post` VALUES (32, '4');
INSERT INTO `bus_allot_post` VALUES (29, '0');
INSERT INTO `bus_allot_post` VALUES (29, '1');
INSERT INTO `bus_allot_post` VALUES (29, '4');

-- ----------------------------
-- Table structure for bus_clean
-- ----------------------------
DROP TABLE IF EXISTS `bus_clean`;
CREATE TABLE `bus_clean`  (
  `clean_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `clean_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '工单编号',
  `clean_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '工单名称',
  `data_source` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单来源：0=计划生成，1=后台添加，2=其他工单，3=场馆使用',
  `plan_id` bigint NOT NULL DEFAULT 0 COMMENT '计划id，表xccj_work.bus_clean_plan（data_source=0有值）',
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '其他工单编号（data_source=2有值）',
  `venue_id` bigint NOT NULL DEFAULT 0 COMMENT '场馆id，表xccj_community.bus_venue（data_source=3有值）',
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '保洁地点',
  `allot_way` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单分配方式：0=自动分配，1=管理员分配，2=抢单',
  `patrol_num` int NOT NULL DEFAULT 0 COMMENT '保洁次数/天次',
  `gap_time` decimal(10, 2) NOT NULL COMMENT '每次保洁间隔时间（一日多次时有值）',
  `start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
  `staff_id` bigint NOT NULL DEFAULT 0 COMMENT '负责人id，表xccj_community.bus_staff',
  `assist_num` int NOT NULL DEFAULT 0 COMMENT '协助人数',
  `transfer_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '转交状态：0=无需转交，1=转交待确认',
  `allot_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '分配状态：0=待分配，1=已分配',
  `accept_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '接单状态：0=待接单，1=已接单',
  `execute_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '执行状态：0=待执行，1=执行中，2=已完成，3=超时完成，4=超时未完成',
  `execute_id` bigint NOT NULL DEFAULT 0 COMMENT '执行人id，表xccj_community.bus_staff',
  `execute_time` datetime NULL DEFAULT NULL COMMENT '执行时间',
  `finish_time` datetime NULL DEFAULT NULL COMMENT '完成时间',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '完成图片',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`clean_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '保洁工单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_clean
-- ----------------------------
INSERT INTO `bus_clean` VALUES (1, 5, 'BJ-20230515-508110', '测试无人小区保洁工单是否分配', '1', 0, '', 0, '保洁地点', '1', 1, 0.00, '2023-05-15 00:00:00', '2023-05-15 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '东东', '15288888888', '2023-05-15 19:26:39', '', '2023-05-15 19:26:28', '1');
INSERT INTO `bus_clean` VALUES (2, 1, 'BJ-20230515-850029', '测试保洁工单01', '1', 0, '', 0, '1栋小区', '1', 0, 0.00, '2023-05-15 08:00:00', '2023-05-08 00:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '15288888888', '2023-05-15 19:28:14', '', '2023-05-15 19:28:03', '0');
INSERT INTO `bus_clean` VALUES (3, 1, 'BJ-20230515-317691', '工单名称:', '1', 0, '', 0, '保洁地点:', '1', 1, 0.00, '2023-05-15 00:00:00', '2023-05-01 00:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '备注', '15288888888', '2023-05-15 19:29:38', '15288888888', '2023-05-15 19:29:58', '0');
INSERT INTO `bus_clean` VALUES (4, 1, 'BJ-20230516-262134', '保洁计划一日多次', '0', 1, '', 0, '公区', '0', 2, 2.00, '2023-05-16 09:00:00', '2023-05-16 17:00:00', 10, 1, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-15 23:59:50', '', '2023-05-15 23:59:50', '0');
INSERT INTO `bus_clean` VALUES (5, 1, 'BJ-20230516-574171', '保洁计划多日一次', '0', 2, '', 0, '1幢楼栋公区', '0', 1, 2.00, '2023-05-16 09:00:00', '2023-05-16 17:00:00', 10, 1, '0', '1', '1', '3', 10, '2023-05-16 17:04:47', '2023-05-16 17:04:58', 'http://file.szcjzhxq.com:8084/statics/2023/05/16/20230308093745_20230516170455A258.jpg', '保洁执行完成', '', '2023-05-15 23:59:50', '', '2023-05-15 23:59:50', '0');
INSERT INTO `bus_clean` VALUES (6, 1, 'BJ-20230518-703254', '保洁计划一日多次', '0', 1, '', 0, '公区', '0', 2, 2.00, '2023-05-18 09:00:00', '2023-05-18 17:00:00', 9, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-17 23:59:49', '', '2023-05-17 23:59:49', '0');
INSERT INTO `bus_clean` VALUES (7, 1, 'BJ-20230519-211219', '保洁计划一日多次', '0', 1, '', 0, '公区', '0', 2, 2.00, '2023-05-19 09:00:00', '2023-05-19 17:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-18 23:59:48', '', '2023-05-18 23:59:48', '0');
INSERT INTO `bus_clean` VALUES (8, 1, 'BJ-20230520-479794', '保洁计划一日多次', '0', 1, '', 0, '公区', '0', 2, 2.00, '2023-05-20 09:00:00', '2023-05-20 17:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-19 23:59:48', '', '2023-05-19 23:59:48', '0');
INSERT INTO `bus_clean` VALUES (9, 1, 'BJ-20230521-902371', '保洁计划一日多次', '0', 1, '', 0, '公区', '0', 2, 2.00, '2023-05-21 09:00:00', '2023-05-21 17:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-20 23:59:47', '', '2023-05-20 23:59:47', '0');
INSERT INTO `bus_clean` VALUES (10, 1, 'BJ-20230522-103085', '保洁计划一日多次', '0', 1, '', 0, '公区', '0', 2, 2.00, '2023-05-22 09:00:00', '2023-05-22 17:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-21 23:59:47', '', '2023-05-21 23:59:47', '0');
INSERT INTO `bus_clean` VALUES (11, 1, 'BJ-20230523-792750', '保洁计划一日多次', '0', 1, '', 0, '公区', '0', 2, 2.00, '2023-05-23 09:00:00', '2023-05-23 17:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-22 23:59:47', '', '2023-05-22 23:59:47', '0');
INSERT INTO `bus_clean` VALUES (12, 1, 'BJ-20230523-405848', '二栋公区单次保洁', '1', 0, '', 0, '保洁设置', '2', 1, 0.00, '2023-05-23 00:00:00', '2023-05-30 00:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '周保洁计划', '15288888888', '2023-05-23 15:42:31', '', '2023-05-23 15:42:17', '0');
INSERT INTO `bus_clean` VALUES (13, 6, 'BJ-20230610-528348', '小区内绿化场地垃圾清理', '0', 4, '', 0, '小区内所有绿化', '0', 1, 0.00, '2023-06-10 08:00:00', '2023-06-10 20:00:00', 25, 1, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-09 23:59:38', '', '2023-06-09 23:59:38', '0');
INSERT INTO `bus_clean` VALUES (14, 7, 'BJ-20230627-713083', 'Y1#2023-6-27保洁工单', '1', 0, '', 0, 'Y1#', '1', 2, 0.00, '2023-06-27 00:00:00', '2023-06-28 00:00:00', 50, 0, '0', '1', '1', '2', 50, '2023-06-27 08:53:07', '2023-06-27 08:53:49', 'http://file.szcjzhxq.com/statics/2023/06/27/女_20230627085323A368.png', '已完成保洁任务', '18888888888', '2023-06-27 08:52:27', '18888888888', '2023-06-27 08:52:39', '0');
INSERT INTO `bus_clean` VALUES (15, 7, 'BJ-20230629-491896', 'Y1#2023-6-29保洁工单', '1', 0, '', 0, 'Y1#', '1', 2, 0.00, '2023-06-29 09:40:08', '2023-07-01 00:00:00', 50, 0, '0', '1', '1', '3', 50, '2023-07-03 14:49:38', '2023-07-03 14:50:05', 'http://file.szcjzhxq.com/statics/2023/07/03/4_20230703145003A391.jpg', '超时完成', '18888888888', '2023-06-29 09:41:36', '18888888888', '2023-06-29 09:41:44', '0');

-- ----------------------------
-- Table structure for bus_clean_plan
-- ----------------------------
DROP TABLE IF EXISTS `bus_clean_plan`;
CREATE TABLE `bus_clean_plan`  (
  `plan_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `plan_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '计划名称',
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '保洁地点',
  `plan_order` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '是否生成工单：0=否，1=是',
  `plan_model` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '模式 ：0=一日多次，1=多日一次',
  `plan_num` int NOT NULL DEFAULT 0 COMMENT '保洁次数/天次',
  `start_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '计划开始（格式：plan_model=0时HH:mm，plan_model=1时yyyy-MM-dd）',
  `end_time` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '计划结束（格式：plan_model=0时HH:mm，plan_model=1时yyyy-MM-dd）',
  `gap_time` decimal(10, 2) NOT NULL COMMENT '每次保洁间隔时间（plan_model=0）',
  `allot_way` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单分配方式：0=自动分配，1=管理员分配，2=抢单',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`plan_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '保洁计划表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_clean_plan
-- ----------------------------
INSERT INTO `bus_clean_plan` VALUES (1, 1, '保洁计划一日多次', '公区', '1', '0', 2, '09:00', '17:00', 2.00, '0', '15288888888', '2023-05-15 19:22:32', '15288888888', '2023-05-15 19:22:36', '1');
INSERT INTO `bus_clean_plan` VALUES (2, 1, '保洁计划多日一次', '1幢楼栋公区', '1', '1', 1, '2023-05-15', '2024-05-15', 0.00, '0', '15288888888', '2023-05-15 19:23:07', '15288888888', '2023-06-10 16:53:12', '0');
INSERT INTO `bus_clean_plan` VALUES (3, 1, '一栋公区保洁计划', '一栋公区', '0', '1', 1, '16:00', '17:00', 24.00, '0', '15288888888', '2023-05-23 14:50:33', '', '2023-05-23 14:50:18', '0');
INSERT INTO `bus_clean_plan` VALUES (4, 6, '小区内绿化场地垃圾清理', '小区内所有绿化', '1', '1', 1, '08:00', '20:00', 6.00, '0', '15656332018', '2023-06-09 10:20:08', '15656332018', '2023-06-09 10:20:12', '0');

-- ----------------------------
-- Table structure for bus_decorate_order
-- ----------------------------
DROP TABLE IF EXISTS `bus_decorate_order`;
CREATE TABLE `bus_decorate_order`  (
  `order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `decorate_id` bigint NOT NULL DEFAULT 0 COMMENT '装修id，表xccj_community.bus_decorate',
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '工单编号',
  `order_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '工单名称',
  `order_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单类型：0=装修中评定，1=装修后评定',
  `allot_way` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单分配方式：0=自动分配，1=管理员分配，2=抢单',
  `start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
  `staff_id` bigint NOT NULL DEFAULT 0 COMMENT '负责人id，表xccj_community.bus_staff',
  `assist_num` int NOT NULL DEFAULT 0 COMMENT '协助人数',
  `transfer_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '转交状态：0=无需转交，1=转交待确认',
  `allot_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '分配状态：0=待分配，1=已分配',
  `accept_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '接单状态：0=待接单，1=已接单',
  `execute_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '执行状态：0=待执行，1=执行中，2=已完成，3=超时完成，4=超时未完成',
  `assess_result` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '评定结果：0=待评定，1=合格，2=违规，3=整改完成',
  `execute_id` bigint NOT NULL DEFAULT 0 COMMENT '执行人id，表xccj_community.bus_staff',
  `execute_time` datetime NULL DEFAULT NULL COMMENT '执行时间',
  `finish_time` datetime NULL DEFAULT NULL COMMENT '完成时间',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '完成图片',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  `damage_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '住户是否损坏公共区域：0=否，1=是',
  `neat_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '住户装修楼道整洁度：1=脏乱，2=干净，3=无暇',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '装修评定工单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_decorate_order
-- ----------------------------
INSERT INTO `bus_decorate_order` VALUES (1, 1, 3, 'ZXPD-20230516-966907', '测试装修评定工单', '0', '0', '2023-05-16 17:58:11', '2023-05-18 00:00:00', 6, 1, '0', '1', '1', '2', '3', 6, '2023-05-16 17:59:25', '2023-05-16 18:01:05', 'http://file.szcjzhxq.com:8084/statics/2023/05/16/20220120024601461_20230516180046A265.jpg', '评定违规，破坏区域。', '15288888888', '2023-05-16 17:57:59', '', '2023-05-16 17:57:59', '1', '1', '1');
INSERT INTO `bus_decorate_order` VALUES (2, 1, 4, 'ZXPD-20230523-131696', '1907二次装修评定工单', '0', '0', '2023-05-23 17:35:16', '2023-05-23 17:36:00', 6, 1, '0', '1', '0', '0', '0', 0, NULL, NULL, '', '', '15288888888', '2023-05-23 17:35:01', '', '2023-05-23 17:35:01', '0', '1', '1');
INSERT INTO `bus_decorate_order` VALUES (3, 1, 5, 'ZXPD-20230523-218833', '1907第三次装修评定', '0', '0', '2023-05-23 17:37:09', '2023-05-24 00:00:00', 6, 1, '0', '1', '1', '3', '2', 6, '2023-05-24 15:44:54', '2023-05-24 15:45:30', 'http://file.szcjzhxq.com:8084/statics/2023/05/24/房间内图_20230524154526A847.jpg', '装修严重违规，不符合评定标准', '15288888888', '2023-05-23 17:36:54', '', '2023-05-23 17:36:54', '0', '1', '1');
INSERT INTO `bus_decorate_order` VALUES (4, 7, 13, 'ZXPD-20230629-357357', 'Y1#-1单元101', '0', '1', '2023-06-29 10:09:27', '2023-09-27 00:00:00', 48, 1, '0', '1', '1', '2', '1', 48, '2023-07-03 14:52:03', '2023-07-03 14:52:32', 'http://file.szcjzhxq.com/statics/2023/07/03/3_20230703145229A392.jpg', '合格', '18888888888', '2023-06-29 10:08:55', '', '2023-06-29 10:08:55', '0', '0', '2');
INSERT INTO `bus_decorate_order` VALUES (5, 7, 13, 'ZXPD-20230629-888574', ' Y1#-1单元101装修评定', '0', '1', '2023-06-29 10:15:26', '2023-07-01 00:00:00', 48, 1, '0', '1', '1', '3', '2', 48, '2023-07-03 14:51:51', '2023-07-03 14:52:59', 'http://file.szcjzhxq.com/statics/2023/07/03/人像面_20230703145256A393.png', '不合格', '18888888888', '2023-06-29 10:14:54', '', '2023-06-29 10:14:54', '0', '1', '1');
INSERT INTO `bus_decorate_order` VALUES (6, 7, 13, 'ZXPD-20230703-941616', 'Y1#-1单元101装修评定2023-7-3', '0', '1', '2023-07-03 14:42:55', '2023-07-04 00:00:00', 51, 1, '0', '1', '1', '2', '1', 51, '2023-07-03 14:43:11', '2023-07-03 14:43:49', 'http://file.szcjzhxq.com/statics/2023/07/03/男_20230703144345A390.png', '评定完成，合格', '18888888888', '2023-07-03 14:42:20', '', '2023-07-03 14:42:20', '0', '0', '2');

-- ----------------------------
-- Table structure for bus_device_order
-- ----------------------------
DROP TABLE IF EXISTS `bus_device_order`;
CREATE TABLE `bus_device_order`  (
  `order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '工单编号',
  `order_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '工单名称',
  `device_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '设备类型：见类 DeviceConstants',
  `index_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '设备唯一标识，device_type对应的表',
  `plan_start` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '计划开始时间',
  `plan_end` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '计划结束时间',
  `allot_way` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单分配方式：0=自动分配，1=管理员分配，2=抢单',
  `staff_id` bigint NOT NULL DEFAULT 0 COMMENT '负责人id，表xccj_community.bus_staff',
  `assist_num` int NOT NULL DEFAULT 0 COMMENT '协助人数',
  `transfer_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '转交状态：0=无需转交，1=转交待确认',
  `allot_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '分配状态：0=待分配，1=已分配',
  `accept_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '接单状态：0=待接单，1=已接单',
  `execute_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '执行状态：0=待执行，1=执行中，2=已完成，3=超时完成，4=超时未完成',
  `assess_result` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '评定结果：0=合格，1=违规，2=整改完成',
  `execute_id` bigint NOT NULL DEFAULT 0 COMMENT '执行人id，表xccj_community.bus_staff',
  `execute_time` datetime NULL DEFAULT NULL COMMENT '执行时间',
  `finish_time` datetime NULL DEFAULT NULL COMMENT '完成时间',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '完成图片',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '设备维修工单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_device_order
-- ----------------------------
INSERT INTO `bus_device_order` VALUES (1, 1, 'SB-20230515-398009', '监控设备维修', 'camera', '503ca1de8c9f4d1b81533b217354b629', '2023-05-15 00:00:00', '2023-05-16 00:00:00', '0', 3, 0, '0', '1', '1', '3', '0', 3, '2023-05-16 17:18:40', '2023-05-16 17:18:56', '', '', '15288888888', '2023-05-15 19:34:08', '', '2023-05-15 19:34:08', '0');
INSERT INTO `bus_device_order` VALUES (2, 1, 'SB-20230516-222914', '测试维修工单', 'camera', 'c46dc43dab5e488bbc53cd75b8522766', '2023-05-16 00:00:00', '2023-05-17 00:00:00', '0', 3, 0, '0', '1', '1', '2', '0', 3, '2023-05-16 17:19:46', '2023-05-16 17:19:59', 'http://file.szcjzhxq.com:8084/statics/2023/05/16/210F2130512J47-0-lp_20230516171956A259.jpg', '执行完成！', '15288888888', '2023-05-16 17:19:16', '', '2023-05-16 17:19:16', '0');
INSERT INTO `bus_device_order` VALUES (3, 1, 'SB-20230523-464177', '10栋可视化设备检修', 'visDevice', '93bae532b9454e83afa04037fbc9919a', '2023-05-23 00:00:00', '2023-05-24 00:00:00', '2', 0, 0, '0', '0', '0', '0', '0', 0, NULL, NULL, '', '', '15288888888', '2023-05-23 15:41:28', '', '2023-05-23 15:41:28', '0');
INSERT INTO `bus_device_order` VALUES (4, 7, 'SB-20230627-869500', '防火门门禁无法开门', 'acsDevice', '0bc214622a8b44698248268430ce39e6', '2023-06-27 00:00:00', '2023-06-28 00:00:00', '1', 49, 0, '0', '1', '1', '2', '0', 49, '2023-06-27 09:14:20', '2023-06-27 09:16:21', 'http://file.szcjzhxq.com/statics/2023/06/27/tmp_026fadbdefa095b7caff9a3eafa0eac5_20230627091618A369.jpg', '设备维修完成，已恢复正常', '18888888888', '2023-06-27 09:11:37', '', '2023-06-27 09:11:37', '0');
INSERT INTO `bus_device_order` VALUES (5, 7, 'SB-20230813-755796', '1221', 'visDevice', 'fa34037cf3eb424cb274dfe472e897aa', '2023-08-13 00:00:00', '2023-08-14 00:00:00', '1', 0, 0, '0', '0', '0', '0', '0', 0, NULL, NULL, '', '', '18888888888', '2023-08-13 16:05:37', '', '2023-08-13 16:05:37', '0');

-- ----------------------------
-- Table structure for bus_disaster
-- ----------------------------
DROP TABLE IF EXISTS `bus_disaster`;
CREATE TABLE `bus_disaster`  (
  `disaster_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `estate_id` bigint NOT NULL DEFAULT 0 COMMENT '物业id，表xccj_estate_sys.bus_estate',
  `disaster_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '维修工种名称',
  `disaster_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '状态：0=启用，1=停用',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '说明',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（发布时间）',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`disaster_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '灾害类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_disaster
-- ----------------------------
INSERT INTO `bus_disaster` VALUES (1, 2, '灾害报事类型01', '0', '', '15288888888', '2023-05-15 17:31:06', '15288888888', '2023-05-15 17:31:06', '0');
INSERT INTO `bus_disaster` VALUES (2, 2, '灾害报事类型02', '0', '', '15288888888', '2023-05-15 17:31:12', '15288888888', '2023-05-15 17:31:12', '0');
INSERT INTO `bus_disaster` VALUES (3, 2, '灾害报事类型03', '0', '灾害报事类型03', '15288888888', '2023-05-15 17:31:28', '15288888888', '2023-05-15 17:31:28', '0');
INSERT INTO `bus_disaster` VALUES (4, 5, '自然灾害', '0', '水灾、火灾等。', '15656332018', '2023-06-09 10:39:51', '', '2023-06-09 10:39:51', '0');
INSERT INTO `bus_disaster` VALUES (5, 5, '人为灾害', '0', '燃气泄漏、煤气泄漏等', '15656332018', '2023-06-09 10:40:30', '', '2023-06-09 10:40:30', '0');
INSERT INTO `bus_disaster` VALUES (6, 6, '自然灾害', '0', '洪涝、台风等。', '18888888888', '2023-06-27 09:26:02', '18888888888', '2023-06-27 09:26:02', '0');
INSERT INTO `bus_disaster` VALUES (7, 6, '人为灾害', '0', '火灾、燃气泄漏等。', '18888888888', '2023-06-27 09:26:25', '18888888888', '2023-06-27 09:26:25', '0');

-- ----------------------------
-- Table structure for bus_disaster_audit
-- ----------------------------
DROP TABLE IF EXISTS `bus_disaster_audit`;
CREATE TABLE `bus_disaster_audit`  (
  `audit_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `audit_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '申请名称',
  `owner_id` bigint NOT NULL DEFAULT 0 COMMENT '业主id，表xccj_community.bus_owner',
  `disaster_id` bigint NOT NULL DEFAULT 0 COMMENT '灾害类型id，表xccj_work.bus_disaster',
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '地址',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `audit_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '审核状态：0=审核中，1=通过，2=驳回',
  `audit_time` datetime NULL DEFAULT NULL COMMENT '审核时间',
  `reject_reason` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '驳回理由',
  `audit_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '审批人',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`audit_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '灾害报事申请表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_disaster_audit
-- ----------------------------
INSERT INTO `bus_disaster_audit` VALUES (1, 1, '灾害报事01', 11, 1, '地址', '灾害报事备注', '1', '2023-05-15 20:04:42', '', '15288888888', '0');
INSERT INTO `bus_disaster_audit` VALUES (2, 1, '22', 12, 1, '33', '44', '2', '2023-05-16 14:38:13', '胡乱输入信息，驳回！', '15288888888', '0');
INSERT INTO `bus_disaster_audit` VALUES (3, 1, '灾害报事驳回', 11, 3, '驳回灾害的地址', '驳回灾害的备注', '2', '2023-05-15 20:04:39', '虚假信息驳回', '15288888888', '0');
INSERT INTO `bus_disaster_audit` VALUES (4, 1, '02灾害报事', 11, 1, '02地址', '备注', '1', '2023-05-17 13:59:44', '', '15288888888', '0');
INSERT INTO `bus_disaster_audit` VALUES (5, 1, '03灾害报事', 11, 3, '03灾害报事地址', '03灾害报事备注', '1', '2023-05-17 13:59:42', '', '15288888888', '0');
INSERT INTO `bus_disaster_audit` VALUES (6, 1, '暴雨下水道堵塞路面积水', 29, 1, '小区', '暴雨下水道堵塞路面积水', '1', '2023-05-23 15:40:40', '', '15288888888', '0');
INSERT INTO `bus_disaster_audit` VALUES (7, 1, '6-12灾害报事', 11, 1, '社区19栋', '备注', '1', '2023-06-12 15:15:46', '', '15288888888', '0');
INSERT INTO `bus_disaster_audit` VALUES (8, 1, '6-12-2灾害', 11, 2, '社区', '权限差了很多', '1', '2023-06-12 15:41:50', '', '15288888888', '0');
INSERT INTO `bus_disaster_audit` VALUES (9, 7, '南门口有棵树被风吹倒了', 74, 6, '彩金湖畔南门口', '请物业尽快处理', '1', '2023-06-27 14:49:10', '', '18888888888', '0');
INSERT INTO `bus_disaster_audit` VALUES (10, 7, '小区南大门口垃圾桶起火', 74, 7, '南大门口', '12341324', '1', '2023-06-27 15:56:51', '', '18888888888', '0');

-- ----------------------------
-- Table structure for bus_disaster_order
-- ----------------------------
DROP TABLE IF EXISTS `bus_disaster_order`;
CREATE TABLE `bus_disaster_order`  (
  `order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `audit_id` bigint NOT NULL DEFAULT 0 COMMENT '灾害报事申请id，表xccj_work.bus_disaster_audit',
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '工单编号',
  `order_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '工单名称',
  `plan_start` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '计划开始时间',
  `plan_end` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '计划结束时间',
  `allot_way` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单分配方式：0=自动分配，1=管理员分配，2=抢单',
  `staff_id` bigint NOT NULL DEFAULT 0 COMMENT '负责人id，表xccj_community.bus_staff',
  `assist_num` int NOT NULL DEFAULT 0 COMMENT '协助人数',
  `transfer_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '转交状态：0=无需转交，1=转交待确认',
  `allot_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '分配状态：0=待分配，1=已分配',
  `accept_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '接单状态：0=待接单，1=已接单',
  `execute_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '执行状态：0=待执行，1=执行中，2=已完成，3=超时完成，4=超时未完成',
  `execute_id` bigint NOT NULL DEFAULT 0 COMMENT '执行人id，表xccj_community.bus_staff',
  `execute_time` datetime NULL DEFAULT NULL COMMENT '执行时间',
  `finish_time` datetime NULL DEFAULT NULL COMMENT '完成时间',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '完成图片',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `appraise_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '评价状态：0=未评价，1=已评价',
  `appraise_star` int NOT NULL DEFAULT 0 COMMENT '评价星级：0~5',
  `appraise_remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '评价备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '灾害报事工单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_disaster_order
-- ----------------------------
INSERT INTO `bus_disaster_order` VALUES (1, 1, 1, 'ZH-20230515-165270', '灾害报事01', '2023-05-15 00:00:00', '2023-05-16 00:00:00', '1', 1, 0, '0', '1', '1', '3', 1, '2023-05-16 14:38:25', '2023-05-16 14:39:03', 'http://file.szcjzhxq.com:8084/statics/2023/05/16/20230308093745_20230516143859A256.jpg', '执行完成', '1', 5, '', '15288888888', '2023-05-15 20:33:28', '业主戊', '2023-05-16 14:39:15', '0');
INSERT INTO `bus_disaster_order` VALUES (2, 1, 5, 'ZH-20230517-29046', '03灾害报事', '2023-05-17 00:00:00', '2023-05-20 00:00:00', '1', 1, 0, '0', '1', '1', '3', 1, '2023-05-22 17:37:34', '2023-05-22 17:38:10', 'http://file.szcjzhxq.com:8084/statics/2023/05/22/cehnggong_20230522173807A729.png', '03灾害报事完成描述', '1', 0, '态度恶劣', '15288888888', '2023-05-17 14:00:11', '业主戊', '2023-05-22 17:42:15', '0');
INSERT INTO `bus_disaster_order` VALUES (3, 1, 4, 'ZH-20230517-216369', '02灾害报事', '2023-05-20 00:00:00', '2023-05-20 00:00:00', '1', 1, 0, '0', '1', '1', '3', 1, '2023-05-17 14:00:56', '2023-06-06 08:51:43', 'http://file.szcjzhxq.com/statics/2023/06/06/cehnggong_20230606085141A069.png', '超时执行完成', '1', 5, '满意', '15288888888', '2023-05-17 14:00:06', '业主戊', '2023-06-08 10:02:02', '0');
INSERT INTO `bus_disaster_order` VALUES (4, 1, 6, 'ZH-20230523-356440', '暴雨下水道堵塞路面积水', '2023-05-23 15:40:55', '2023-05-24 15:40:55', '2', 40, 0, '1', '1', '1', '0', 0, NULL, NULL, '', '', '0', 0, '', '', '2023-05-23 15:40:40', '', '2023-05-23 15:40:40', '0');
INSERT INTO `bus_disaster_order` VALUES (5, 1, 7, 'ZH-20230612-25886', '6-12灾害报事', '2023-06-12 19:00:00', '2023-06-12 23:00:00', '1', 1, 0, '1', '1', '0', '0', 0, NULL, '2023-06-14 00:00:00', '', '提前备注', '0', 0, '', '15288888888', '2023-06-12 15:23:56', '15288888888', '2023-06-12 15:23:56', '0');
INSERT INTO `bus_disaster_order` VALUES (6, 1, 8, 'ZH-20230612-915178', '6-12-2灾害', '2023-06-13 00:00:00', '2023-06-16 00:00:00', '1', 1, 0, '0', '1', '1', '0', 0, NULL, NULL, '', '', '0', 0, '', '15288888888', '2023-06-13 17:36:13', '15288888888', '2023-06-13 17:36:13', '0');
INSERT INTO `bus_disaster_order` VALUES (7, 7, 9, 'ZH-20230627-768099', '南门口有棵树被风吹倒了', '2023-06-28 00:00:00', '2023-06-30 00:00:00', '1', 48, 0, '1', '1', '0', '0', 0, NULL, NULL, '', '', '0', 0, '', '18888888888', '2023-06-27 14:49:52', '18888888888', '2023-06-27 14:49:52', '0');
INSERT INTO `bus_disaster_order` VALUES (8, 7, 10, 'ZH-20230627-514545', '小区南大门口垃圾桶起火', '2023-06-27 00:00:00', '2023-06-30 00:00:00', '1', 50, 0, '1', '1', '0', '0', 0, NULL, NULL, '', '', '0', 0, '', '18888888888', '2023-06-27 15:57:15', '18888888888', '2023-06-27 15:57:15', '0');

-- ----------------------------
-- Table structure for bus_home_order
-- ----------------------------
DROP TABLE IF EXISTS `bus_home_order`;
CREATE TABLE `bus_home_order`  (
  `order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '工单编号',
  `order_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '工单名称',
  `room_id` bigint NULL DEFAULT NULL COMMENT '房间id',
  `owner_id` bigint NOT NULL DEFAULT 0 COMMENT '业主id，表xccj_community.bus_owner',
  `repair_id` bigint NOT NULL DEFAULT 0 COMMENT '维修类型id，表xccj_work.bus_home_repair',
  `deposit` decimal(10, 2) NOT NULL COMMENT '定金',
  `plan_start` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '计划开始时间',
  `plan_end` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '计划结束时间',
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '地址',
  `allot_way` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单分配方式：0=自动分配，1=管理员分配，2=抢单',
  `staff_id` bigint NOT NULL DEFAULT 0 COMMENT '负责人id，表xccj_community.bus_staff',
  `assist_num` int NOT NULL DEFAULT 0 COMMENT '协助人数',
  `transfer_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '转交状态：0=无需转交，1=转交待确认',
  `allot_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '分配状态：0=待分配，1=已分配',
  `accept_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '接单状态：0=待接单，1=已接单',
  `execute_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '执行状态：0=待执行，1=执行中，2=已完成，3=超时完成，4=超时未完成',
  `assess_result` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '评定结果：0=合格，1=违规，2=整改完成',
  `execute_id` bigint NOT NULL DEFAULT 0 COMMENT '执行人id，表xccj_community.bus_staff',
  `execute_time` datetime NULL DEFAULT NULL COMMENT '执行时间',
  `finish_time` datetime NULL DEFAULT NULL COMMENT '完成时间',
  `appoint_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '状态：0=未支付，1=已支付',
  `pay_amount` decimal(10, 2) NOT NULL COMMENT '支付金额',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '完成图片',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `appraise_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '评价状态：0=未评价，1=已评价',
  `appraise_star` int NOT NULL DEFAULT 0 COMMENT '评价星级：0~5',
  `appraise_remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '评价备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '居家维修工单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_home_order
-- ----------------------------
INSERT INTO `bus_home_order` VALUES (1, 1, 'JJ-20230515-610267', '空调维修', NULL, 12, 1, 0.00, '2023-05-16 10:54:17', '2023-05-16 20:54:23', '主卧', '0', 3, 0, '0', '1', '1', '0', '0', 0, NULL, NULL, '0', 0.00, '', '速来', '0', 0, '', '', '2023-05-15 18:13:55', '', '2023-05-15 18:13:55', '0');
INSERT INTO `bus_home_order` VALUES (2, 1, 'JJ-20230515-253592', '空调维修', NULL, 12, 1, 0.00, '2023-05-16 10:54:17', '2023-05-16 20:54:23', '主卧', '0', 4, 0, '0', '1', '0', '0', '0', 0, NULL, NULL, '0', 0.00, '', '速来', '0', 0, '', '', '2023-05-15 18:15:20', '', '2023-05-15 18:15:20', '0');
INSERT INTO `bus_home_order` VALUES (3, 1, 'JJ-20230515-959279', '空调维修', NULL, 12, 1, 0.00, '2023-05-16 10:54:17', '2023-05-16 20:54:23', '主卧', '0', 0, 0, '0', '0', '0', '0', '0', 0, NULL, NULL, '0', 0.00, '', '速来', '0', 0, '', '', '2023-05-15 18:16:04', '', '2023-05-15 18:16:04', '0');
INSERT INTO `bus_home_order` VALUES (4, 1, 'JJ-20230515-126666', '空调维修', NULL, 12, 1, 0.00, '2023-05-16 10:54:17', '2023-05-16 20:54:23', '主卧', '0', 0, 0, '0', '0', '0', '0', '0', 0, NULL, NULL, '0', 0.00, '', '速来', '0', 0, '', '', '2023-05-15 18:16:52', '', '2023-05-15 18:16:52', '0');
INSERT INTO `bus_home_order` VALUES (5, 1, 'JJ-20230515-193246', '空调维修', NULL, 12, 1, 0.00, '2023-05-16 10:54:17', '2023-05-16 20:54:23', '主卧', '0', 0, 0, '0', '0', '0', '0', '0', 0, NULL, NULL, '0', 0.00, '', '速来', '0', 0, '', '', '2023-05-15 18:17:03', '', '2023-05-15 18:17:03', '0');
INSERT INTO `bus_home_order` VALUES (6, 1, 'JJ-20230515-798468', '11', NULL, 12, 1, 0.00, '2023-05-15 18:22:42', '2023-05-15 20:21:44', '33', '0', 0, 0, '0', '0', '0', '0', '0', 0, NULL, NULL, '0', 0.00, '', '66', '0', 0, '', '', '2023-05-15 18:22:26', '', '2023-05-15 18:22:26', '0');
INSERT INTO `bus_home_order` VALUES (7, 1, 'JJ-20230515-728500', '居家维修01', NULL, 11, 1, 0.00, '2023-05-01 00:00:00', '2023-05-02 00:00:00', '居家维修01地址', '0', 3, 0, '0', '1', '1', '3', '0', 3, '2023-05-16 14:52:25', '2023-05-16 14:52:34', '0', 0.00, '', '', '1', 0, '', '', '2023-05-15 20:00:14', '', '2023-05-15 20:00:14', '0');
INSERT INTO `bus_home_order` VALUES (8, 1, 'JJ-20230515-914696', '居家维修01', NULL, 11, 1, 0.00, '2023-05-16 00:00:00', '2023-05-17 00:00:00', '居家维修01地址', '0', 3, 0, '0', '1', '1', '2', '0', 3, '2023-05-16 14:41:23', '2023-05-16 14:41:56', '0', 0.00, '', '', '1', 5, '', '', '2023-05-15 20:00:19', '', '2023-05-15 20:00:19', '0');
INSERT INTO `bus_home_order` VALUES (9, 1, 'JJ-20230523-715146', '家具维修', NULL, 11, 1, 0.00, '2023-05-23 18:38:19', '2023-05-24 18:38:16', '小区', '0', 37, 0, '0', '1', '1', '2', '0', 37, '2023-05-23 18:39:50', '2023-05-23 18:40:07', '0', 0.00, '', '无执行图片', '1', 0, '', '', '2023-05-23 18:38:52', '', '2023-05-23 18:38:52', '0');
INSERT INTO `bus_home_order` VALUES (10, 1, 'JJ-20230524-612877', '居家维修', NULL, 26, 1, 0.00, '2023-05-24 14:59:14', '2023-05-24 15:59:17', '居家维修', '0', 37, 0, '0', '1', '0', '0', '0', 0, NULL, NULL, '0', 0.00, '', '备注', '0', 0, '', '', '2023-05-24 14:59:51', '', '2023-05-24 14:59:51', '0');
INSERT INTO `bus_home_order` VALUES (11, 1, 'JJ-20230603-369704', '城建住房的冰箱损坏', NULL, 11, 1, 0.00, '2023-06-01 00:00:00', '2023-06-03 00:00:00', '小区', '0', 3, 0, '0', '1', '1', '0', '0', 0, NULL, NULL, '0', 0.00, '', '维修', '0', 0, '', '', '2023-06-03 13:45:12', '', '2023-06-03 13:45:12', '0');
INSERT INTO `bus_home_order` VALUES (12, 5, 'JJ-20230609-611490', '居家维修', NULL, 64, 7, 0.00, '2023-06-09 18:55:03', '2023-06-10 16:55:07', '居家微信地址', '0', 42, 0, '0', '1', '0', '0', '0', 0, NULL, NULL, '0', 0.00, '', '尽快修复', '0', 0, '', '', '2023-06-09 16:55:55', '', '2023-06-09 16:55:55', '0');
INSERT INTO `bus_home_order` VALUES (13, 1, 'JJ-20230610-143021', '居家微信ts', NULL, 11, 1, 0.00, '2023-06-10 11:25:26', '2023-06-11 10:25:28', '地址ts', '0', 0, 0, '0', '0', '0', '0', '0', 0, NULL, NULL, '0', 0.00, '', '盎格鲁撒克逊人', '0', 0, '', '', '2023-06-10 10:26:26', '', '2023-06-10 10:26:26', '0');
INSERT INTO `bus_home_order` VALUES (14, 7, 'JJ-20230627-739455', '修理', 885, 74, 8, 0.00, '2023-06-29 00:00:00', '2023-07-01 00:00:00', '城建·彩金湖畔-A区Y-1#-1单元-101', '1', 49, 0, '0', '1', '1', '2', '0', 49, '2023-06-27 14:45:13', '2023-06-27 14:45:29', '0', 0.00, 'http://file.szcjzhxq.com/statics/2023/06/27/3bb99edb-f5c6-45ae-8450-8daff3f385b1_20230627144525A387.jpg', '已完成维修', '1', 5, '', '', '2023-06-27 14:40:21', '', '2023-06-27 14:40:21', '0');
INSERT INTO `bus_home_order` VALUES (15, 7, 'JJ-20230911-673969', '水管漏水', 885, 0, 10, 100.00, '2023-09-11 00:00:00', '2023-10-02 00:00:00', '城建·彩金湖畔-A区Y-1#-1单元-101', '1', 49, 0, '0', '1', '1', '2', '0', 49, '2023-09-11 15:29:52', '2023-09-11 15:30:16', '0', 0.00, 'http://file.szcjzhxq.com/statics/2023/09/11/男_20230911153013A813.png', '已维修', '0', 0, '', '18888888888', '2023-09-11 15:28:30', '', '2023-09-11 15:28:30', '0');
INSERT INTO `bus_home_order` VALUES (16, 7, 'JJ-20230914-979067', '测试工单', 885, 0, 10, 100.00, '2023-09-14 00:00:00', '2023-09-16 00:00:00', '城建·彩金湖畔-A区Y-1#-1单元-101', '1', 49, 0, '0', '1', '1', '2', '0', 49, '2023-09-14 11:32:49', '2023-09-14 11:33:53', '0', 0.00, '', '', '0', 0, '', '18888888888', '2023-09-14 11:29:26', '', '2023-09-14 11:29:26', '0');

-- ----------------------------
-- Table structure for bus_home_repair
-- ----------------------------
DROP TABLE IF EXISTS `bus_home_repair`;
CREATE TABLE `bus_home_repair`  (
  `repair_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `estate_id` bigint NOT NULL DEFAULT 0 COMMENT '物业id，表xccj_estate_sys.bus_estate',
  `repair_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `deposit` decimal(10, 2) NOT NULL COMMENT '定金',
  `repair_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '状态：0=启用，1=停用',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '说明',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（发布时间）',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`repair_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '居家维修类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_home_repair
-- ----------------------------
INSERT INTO `bus_home_repair` VALUES (1, 2, '家具维修', 100.00, '0', '', '15288888888', '2023-05-15 17:29:51', '15288888888', '2023-05-15 17:29:51', '0');
INSERT INTO `bus_home_repair` VALUES (2, 2, '家电维修', 100.00, '0', '', '15288888888', '2023-05-15 17:29:56', '15288888888', '2023-05-15 17:29:56', '0');
INSERT INTO `bus_home_repair` VALUES (3, 2, '房屋维修', 100.00, '0', '', '15288888888', '2023-05-15 17:30:02', '15288888888', '2023-05-15 17:30:02', '0');
INSERT INTO `bus_home_repair` VALUES (4, 5, '家电维修', 50.00, '0', '冰箱、洗衣机、电视机等家电设备维修', '15656332018', '2023-06-09 10:24:48', '15656332018', '2023-06-09 10:24:48', '0');
INSERT INTO `bus_home_repair` VALUES (5, 5, '其他维修', 50.00, '0', '其他维修', '15656332018', '2023-06-09 10:26:30', '15656332018', '2023-06-09 10:26:30', '0');
INSERT INTO `bus_home_repair` VALUES (6, 4, '居家维修类型', 100.00, '0', '', '15200000000', '2023-06-09 16:46:11', '15200000000', '2023-06-09 16:46:11', '0');
INSERT INTO `bus_home_repair` VALUES (7, 4, '居家维修类型2', 200.00, '0', '居家维修说明', '15200000000', '2023-06-09 16:46:22', '15200000000', '2023-06-09 16:46:22', '0');
INSERT INTO `bus_home_repair` VALUES (8, 6, '家电维修', 100.00, '0', '', '18888888888', '2023-06-27 09:06:22', '18888888888', '2023-06-27 09:06:22', '0');
INSERT INTO `bus_home_repair` VALUES (9, 6, '家具维修', 100.00, '0', '', '18888888888', '2023-06-27 09:06:40', '18888888888', '2023-06-27 09:06:40', '0');
INSERT INTO `bus_home_repair` VALUES (10, 6, '水电维修', 100.00, '0', '', '18888888888', '2023-06-27 09:06:50', '18888888888', '2023-06-27 09:06:50', '0');
INSERT INTO `bus_home_repair` VALUES (11, 6, '门窗维修', 100.00, '0', '', '18888888888', '2023-06-27 09:07:24', '18888888888', '2023-06-27 09:07:24', '0');
INSERT INTO `bus_home_repair` VALUES (12, 6, '其他维修', 100.00, '0', '', '18888888888', '2023-06-27 09:09:57', '18888888888', '2023-06-27 09:09:57', '0');

-- ----------------------------
-- Table structure for bus_inspect
-- ----------------------------
DROP TABLE IF EXISTS `bus_inspect`;
CREATE TABLE `bus_inspect`  (
  `inspect_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `inspect_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '工单编号',
  `inspect_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '工单名称',
  `data_source` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单来源：0=计划生成，1=后台添加，2=其他工单',
  `plan_id` bigint NOT NULL DEFAULT 0 COMMENT '计划id，表xccj_work.bus_inspect_plan（data_source=0有值）',
  `tour_id` bigint NOT NULL DEFAULT 0 COMMENT '巡检类型id，表xccj_work.bus_tour（tour_type=2）',
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '其他工单编号（data_source=2有值）',
  `allot_way` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单分配方式：0=自动分配，1=管理员分配，2=抢单',
  `patrol_num` int NOT NULL DEFAULT 0 COMMENT '巡更次数/天次',
  `gap_time` decimal(10, 2) NOT NULL COMMENT '每次巡更间隔时间（一日多查时有值）',
  `start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
  `staff_id` bigint NOT NULL DEFAULT 0 COMMENT '负责人id，表xccj_community.bus_staff',
  `assist_num` int NOT NULL DEFAULT 0 COMMENT '协助人数',
  `transfer_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '转交状态：0=无需转交，1=转交待确认',
  `allot_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '分配状态：0=待分配，1=已分配',
  `accept_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '接单状态：0=待接单，1=已接单',
  `execute_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '执行状态：0=待执行，1=执行中，2=已完成，3=超时完成，4=超时未完成',
  `execute_id` bigint NOT NULL DEFAULT 0 COMMENT '执行人id，表xccj_community.bus_staff',
  `execute_time` datetime NULL DEFAULT NULL COMMENT '执行时间',
  `finish_time` datetime NULL DEFAULT NULL COMMENT '完成时间',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '完成图片',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`inspect_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡检工单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_inspect
-- ----------------------------
INSERT INTO `bus_inspect` VALUES (1, 1, 'XJ-20230516-583257', '测试转交协助功能', '1', 0, 7, '', '1', 0, 0.00, '2023-05-16 00:00:00', '2023-05-17 00:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '测试备注', '10', '2023-05-16 16:24:39', '10', '2023-05-16 16:25:14', '0');
INSERT INTO `bus_inspect` VALUES (2, 1, 'XJ-20230516-452248', '工单人数限制', '1', 0, 8, '', '0', 0, 0.00, '2023-05-16 00:00:00', '2023-05-16 01:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '10', '2023-05-16 16:26:09', '', '2023-05-16 16:26:09', '0');
INSERT INTO `bus_inspect` VALUES (3, 6, 'XJ-20230609-667560', '园区内人行通道门禁设备巡检', '1', 0, 14, '', '1', 0, 0.00, '2023-06-09 09:57:56', '2023-06-09 17:30:00', 12, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '27', '2023-06-09 09:58:42', '27', '2023-06-09 09:59:49', '0');
INSERT INTO `bus_inspect` VALUES (4, 1, 'XJ-20230613-708847', 'TS613工单', '1', 0, 10, '', '1', 0, 0.00, '2023-06-13 00:00:00', '2023-06-14 00:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '10', '2023-06-13 16:25:21', '10', '2023-06-13 16:26:06', '0');
INSERT INTO `bus_inspect` VALUES (5, 1, 'XJ-20230613-334049', 'TS613工单02', '1', 0, 10, '', '2', 0, 0.00, '2023-06-13 00:00:00', '2023-06-14 00:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '10', '2023-06-13 16:27:11', '', '2023-06-13 16:27:11', '0');
INSERT INTO `bus_inspect` VALUES (6, 7, 'XJ-20230626-834331', '2023-6-26日消防巡检', '1', 0, 20, '', '1', 0, 0.00, '2023-06-26 17:20:18', '2023-06-30 00:00:00', 49, 0, '0', '1', '1', '1', 0, '2023-06-26 17:31:19', NULL, '', '', '65', '2023-06-26 17:21:01', '65', '2023-06-26 17:29:18', '0');

-- ----------------------------
-- Table structure for bus_inspect_device
-- ----------------------------
DROP TABLE IF EXISTS `bus_inspect_device`;
CREATE TABLE `bus_inspect_device`  (
  `inspect_id` bigint NOT NULL DEFAULT 0 COMMENT '巡检工单id，表xccj_work.bus_inspect',
  `device_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '设备类型：见类 DeviceConstants',
  `index_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '设备唯一标识，device_type对应的表',
  `inspect_result` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '巡检结果：0=未检，1=正常，2=异常',
  `result_info` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '结果说明'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡检计划-设备表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_inspect_device
-- ----------------------------
INSERT INTO `bus_inspect_device` VALUES (1, 'camera', 'a41cadd097424f7f805734a06f210a64', '0', '');
INSERT INTO `bus_inspect_device` VALUES (2, 'camera', 'c46dc43dab5e488bbc53cd75b8522766', '0', '');
INSERT INTO `bus_inspect_device` VALUES (3, 'cloud', '8P7122', '0', '');
INSERT INTO `bus_inspect_device` VALUES (4, 'cloud', '8P7122', '0', '');
INSERT INTO `bus_inspect_device` VALUES (5, 'cloud', '8P7122', '0', '');
INSERT INTO `bus_inspect_device` VALUES (6, 'acsDevice', '0bc214622a8b44698248268430ce39e6', '0', '');

-- ----------------------------
-- Table structure for bus_inspect_plan
-- ----------------------------
DROP TABLE IF EXISTS `bus_inspect_plan`;
CREATE TABLE `bus_inspect_plan`  (
  `plan_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `plan_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '计划名称',
  `tour_id` bigint NOT NULL DEFAULT 0 COMMENT '巡检类型id，表xccj_work.bus_tour（tour_type=2）',
  `plan_order` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '是否生成工单：0=否，1=是',
  `plan_model` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '巡查模式 ：0=一日多查，1=多日一查',
  `watchman_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '巡更顺序：0=顺序，1=乱序',
  `plan_num` int NOT NULL DEFAULT 0 COMMENT '巡更次数/天次',
  `start_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '计划开始（格式：plan_model=0时HH:mm，plan_model=1时yyyy-MM-dd）',
  `end_time` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '计划结束（格式：plan_model=0时HH:mm，plan_model=1时yyyy-MM-dd）',
  `gap_time` decimal(10, 2) NOT NULL COMMENT '每次巡更间隔时间（plan_model=0）',
  `allot_way` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单分配方式：0=自动分配，1=管理员分配，2=抢单',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`plan_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡更计划表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_inspect_plan
-- ----------------------------
INSERT INTO `bus_inspect_plan` VALUES (1, 1, '测试巡检计划01', 7, '1', '1', '0', 1, '2023-05-16', '2024-05-16', 0.00, '0', '10', '2023-05-16 16:23:21', '10', '2023-06-09 10:15:28', '0');
INSERT INTO `bus_inspect_plan` VALUES (2, 1, '测计划删除', 7, '1', '1', '0', 1, '2023-05-16', '2023-05-17', 0.00, '0', '10', '2023-05-16 16:23:37', '', '2023-05-16 16:23:37', '1');
INSERT INTO `bus_inspect_plan` VALUES (3, 6, '园区设备巡检计划', 15, '1', '1', '0', 5, '2023-06-10', '2023-07-10', 0.00, '0', '27', '2023-06-09 09:56:07', '', '2023-06-09 09:56:07', '0');

-- ----------------------------
-- Table structure for bus_order_assist
-- ----------------------------
DROP TABLE IF EXISTS `bus_order_assist`;
CREATE TABLE `bus_order_assist`  (
  `audit_id` bigint NOT NULL DEFAULT 0 COMMENT '请求协助审核id，表xccj_work.bus_order_audit（audit_type=1）',
  `staff_id` bigint NOT NULL DEFAULT 0 COMMENT '协助人员id，表xccj_community.bus_staff'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工单协助人员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_order_assist
-- ----------------------------
INSERT INTO `bus_order_assist` VALUES (4, 2);
INSERT INTO `bus_order_assist` VALUES (6, 2);
INSERT INTO `bus_order_assist` VALUES (7, 2);
INSERT INTO `bus_order_assist` VALUES (8, 2);
INSERT INTO `bus_order_assist` VALUES (13, 4);
INSERT INTO `bus_order_assist` VALUES (14, 4);
INSERT INTO `bus_order_assist` VALUES (15, 5);
INSERT INTO `bus_order_assist` VALUES (19, 2);
INSERT INTO `bus_order_assist` VALUES (32, 2);
INSERT INTO `bus_order_assist` VALUES (33, 2);

-- ----------------------------
-- Table structure for bus_order_audit
-- ----------------------------
DROP TABLE IF EXISTS `bus_order_audit`;
CREATE TABLE `bus_order_audit`  (
  `audit_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `audit_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '审核类型:0=工单转交,1=工单协助',
  `order_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单类型：见类 OrderConstants',
  `order_id` bigint NOT NULL DEFAULT 0 COMMENT '工单id，order_type对应的表',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `user_id` bigint NULL DEFAULT 0 COMMENT '审批人 表（xccj_estate_sys.sys_user）',
  `audit_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '审核状态：0=审核中，1=通过，2=驳回',
  `audit_time` datetime NULL DEFAULT NULL COMMENT '审核时间',
  `reject_reason` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '驳回理由',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`audit_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工单审核表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_order_audit
-- ----------------------------
INSERT INTO `bus_order_audit` VALUES (1, '0', '7', 1, '', 10, '2', '2023-05-16 11:13:05', '', '2023-05-16 10:56:30', '0');
INSERT INTO `bus_order_audit` VALUES (2, '0', '7', 1, '', 10, '2', '2023-05-16 11:13:47', '', '2023-05-16 11:13:05', '0');
INSERT INTO `bus_order_audit` VALUES (3, '0', '7', 1, '', 10, '2', '2023-05-16 11:22:34', '只限你完成', '2023-05-16 11:13:59', '0');
INSERT INTO `bus_order_audit` VALUES (4, '1', '7', 1, '', 10, '2', '2023-05-16 11:30:13', '', '2023-05-16 11:29:06', '0');
INSERT INTO `bus_order_audit` VALUES (5, '0', '7', 7, '', 0, '0', NULL, '', '2023-05-16 14:21:28', '0');
INSERT INTO `bus_order_audit` VALUES (6, '1', '7', 8, '', 10, '2', '2023-05-16 14:23:41', '无', '2023-05-16 14:23:12', '0');
INSERT INTO `bus_order_audit` VALUES (7, '1', '7', 8, '', 10, '1', '2023-05-16 14:24:04', '', '2023-05-16 14:23:39', '0');
INSERT INTO `bus_order_audit` VALUES (8, '1', '8', 1, '', 0, '1', NULL, '', '2023-05-16 14:37:42', '0');
INSERT INTO `bus_order_audit` VALUES (9, '0', '0', 1, '', 10, '2', '2023-05-16 16:01:55', '拒绝', '2023-05-16 16:01:24', '0');
INSERT INTO `bus_order_audit` VALUES (10, '0', '2', 1, '', 10, '2', '2023-05-16 16:25:29', '拒绝', '2023-05-16 16:25:09', '0');
INSERT INTO `bus_order_audit` VALUES (11, '0', '2', 1, '', 10, '1', '2023-05-16 16:25:42', '', '2023-05-16 16:25:24', '0');
INSERT INTO `bus_order_audit` VALUES (12, '0', '3', 4, '', 10, '1', '2023-05-16 17:04:33', '', '2023-05-16 17:04:03', '0');
INSERT INTO `bus_order_audit` VALUES (13, '1', '6', 1, '', 10, '1', '2023-05-16 17:19:43', '', '2023-05-16 17:18:35', '0');
INSERT INTO `bus_order_audit` VALUES (14, '1', '6', 2, '', 10, '2', '2023-05-16 17:20:29', '已完成无需协助', '2023-05-16 17:19:41', '0');
INSERT INTO `bus_order_audit` VALUES (15, '1', '4', 1, '', 10, '1', '2023-05-16 17:59:48', '', '2023-05-16 17:59:32', '0');
INSERT INTO `bus_order_audit` VALUES (16, '0', '8', 5, '', 0, '1', NULL, '', '2023-06-12 15:28:26', '0');
INSERT INTO `bus_order_audit` VALUES (17, '0', '8', 4, '', 0, '1', NULL, '', '2023-06-12 15:43:45', '0');
INSERT INTO `bus_order_audit` VALUES (18, '0', '0', 37, '', 10, '1', '2023-06-12 15:52:14', '', '2023-06-12 15:49:18', '0');
INSERT INTO `bus_order_audit` VALUES (19, '1', '0', 51, '', 10, '1', '2023-06-13 10:24:03', '', '2023-06-13 10:23:10', '0');
INSERT INTO `bus_order_audit` VALUES (20, '0', '2', 4, '', 10, '1', '2023-06-13 16:28:54', '', '2023-06-13 16:26:25', '0');
INSERT INTO `bus_order_audit` VALUES (21, '0', '2', 5, '', 10, '1', '2023-06-13 16:28:53', '', '2023-06-13 16:28:06', '0');
INSERT INTO `bus_order_audit` VALUES (22, '0', '2', 5, '', 10, '1', '2023-06-13 16:29:40', '', '2023-06-13 16:28:57', '0');
INSERT INTO `bus_order_audit` VALUES (23, '0', '2', 5, '', 10, '1', '2023-06-13 16:29:53', '', '2023-06-13 16:29:19', '0');
INSERT INTO `bus_order_audit` VALUES (24, '0', '2', 5, '', 10, '1', '2023-06-13 16:30:05', '', '2023-06-13 16:29:36', '0');
INSERT INTO `bus_order_audit` VALUES (25, '0', '2', 5, '', 10, '1', '2023-06-13 16:33:48', '', '2023-06-13 16:29:45', '0');
INSERT INTO `bus_order_audit` VALUES (26, '0', '2', 4, '', 10, '2', '2023-06-13 16:33:46', '拒绝', '2023-06-13 16:30:11', '0');
INSERT INTO `bus_order_audit` VALUES (27, '0', '0', 3, '', 10, '2', '2023-06-13 16:39:46', '拒绝', '2023-06-13 16:30:30', '0');
INSERT INTO `bus_order_audit` VALUES (28, '0', '2', 5, '', 10, '2', '2023-06-13 16:35:18', '拒绝', '2023-06-13 16:34:39', '0');
INSERT INTO `bus_order_audit` VALUES (29, '0', '2', 5, '', 10, '2', '2023-06-13 16:38:16', '不允许转交', '2023-06-13 16:35:47', '0');
INSERT INTO `bus_order_audit` VALUES (30, '0', '1', 47, '', 10, '2', '2023-06-13 16:44:24', '拒绝', '2023-06-13 16:43:32', '0');
INSERT INTO `bus_order_audit` VALUES (31, '0', '1', 47, '', 0, '0', NULL, '', '2023-06-13 16:44:57', '0');
INSERT INTO `bus_order_audit` VALUES (32, '1', '1', 46, '', 0, '0', NULL, '', '2023-06-13 17:01:10', '0');
INSERT INTO `bus_order_audit` VALUES (33, '1', '7', 9, '', 0, '0', NULL, '', '2023-06-13 17:09:00', '0');
INSERT INTO `bus_order_audit` VALUES (34, '0', '8', 7, '', 0, '1', NULL, '', '2023-06-27 14:51:10', '0');
INSERT INTO `bus_order_audit` VALUES (35, '0', '8', 8, '', 0, '1', NULL, '', '2023-06-27 15:57:51', '0');
INSERT INTO `bus_order_audit` VALUES (36, '0', '8', 8, '', 0, '1', NULL, '', '2023-06-29 09:35:51', '0');
INSERT INTO `bus_order_audit` VALUES (37, '0', '8', 7, '', 0, '1', NULL, '', '2023-06-29 09:38:01', '0');
INSERT INTO `bus_order_audit` VALUES (38, '0', '3', 15, '', 65, '1', '2023-07-03 14:48:10', '', '2023-06-29 09:41:24', '0');
INSERT INTO `bus_order_audit` VALUES (39, '0', '4', 5, '', 65, '1', '2023-07-03 14:45:14', '', '2023-06-30 10:45:08', '0');
INSERT INTO `bus_order_audit` VALUES (40, '0', '4', 4, '', 65, '1', '2023-07-03 14:45:11', '', '2023-06-30 10:45:27', '0');

-- ----------------------------
-- Table structure for bus_order_image
-- ----------------------------
DROP TABLE IF EXISTS `bus_order_image`;
CREATE TABLE `bus_order_image`  (
  `image_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '图片地址',
  `image_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '图片类型：0=居家维修工单,1=园区报事申请,2灾害报事申请',
  `table_id` bigint NOT NULL DEFAULT 0 COMMENT '主表id，根据图片类型对应表',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`image_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 83 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工单图片表表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_order_image
-- ----------------------------
INSERT INTO `bus_order_image` VALUES (1, 'http://file.szcjzhxq.com/statics/2023/05/15/426_20230515180443A200.jpg', '2', 1, '0');
INSERT INTO `bus_order_image` VALUES (2, 'http://file.szcjzhxq.com/statics/2023/05/15/chuanggaun_20230515175456A198.png', '0', 3, '0');
INSERT INTO `bus_order_image` VALUES (3, 'http://file.szcjzhxq.com/statics/2023/05/15/dangan_20230515175456A199.png', '0', 3, '0');
INSERT INTO `bus_order_image` VALUES (4, 'http://file.szcjzhxq.com/statics/2023/05/15/房间内图_20230425040701A469_20230515182125A205.jpg', '1', 1, '0');
INSERT INTO `bus_order_image` VALUES (5, 'http://file.szcjzhxq.com/statics/2023/05/15/dianfei_20230515182144A206.png', '2', 2, '0');
INSERT INTO `bus_order_image` VALUES (6, 'http://file.szcjzhxq.com/statics/2023/05/15/dengji_20230515182222A207.png', '0', 6, '0');
INSERT INTO `bus_order_image` VALUES (7, 'http://file.szcjzhxq.com/statics/2023/05/15/426_20230515195937A222.jpg', '1', 2, '0');
INSERT INTO `bus_order_image` VALUES (8, 'http://file.szcjzhxq.com/statics/2023/05/15/a - 副本_20230515200009A223.jpg', '0', 7, '0');
INSERT INTO `bus_order_image` VALUES (9, 'http://file.szcjzhxq.com/statics/2023/05/15/a_20230515200010A224.jpg', '0', 7, '0');
INSERT INTO `bus_order_image` VALUES (10, 'http://file.szcjzhxq.com/statics/2023/05/15/b - 副本_20230515200010A225.jpg', '0', 7, '0');
INSERT INTO `bus_order_image` VALUES (11, 'http://file.szcjzhxq.com/statics/2023/05/15/a - 副本_20230515200009A223.jpg', '0', 8, '0');
INSERT INTO `bus_order_image` VALUES (12, 'http://file.szcjzhxq.com/statics/2023/05/15/a_20230515200010A224.jpg', '0', 8, '0');
INSERT INTO `bus_order_image` VALUES (13, 'http://file.szcjzhxq.com/statics/2023/05/15/b - 副本_20230515200010A225.jpg', '0', 8, '0');
INSERT INTO `bus_order_image` VALUES (14, 'http://file.szcjzhxq.com/statics/2023/05/15/a - 副本_20230515200406A226.jpg', '2', 3, '0');
INSERT INTO `bus_order_image` VALUES (15, 'http://file.szcjzhxq.com/statics/2023/05/15/20230308093745_20230515200409A227.jpg', '2', 3, '0');
INSERT INTO `bus_order_image` VALUES (16, 'http://file.szcjzhxq.com/statics/2023/05/15/20220120024601461_20230515200412A228.jpg', '2', 3, '0');
INSERT INTO `bus_order_image` VALUES (17, 'http://file.szcjzhxq.com/statics/2023/05/15/20230308093745_20230515200516A229.jpg', '1', 3, '0');
INSERT INTO `bus_order_image` VALUES (18, 'http://file.szcjzhxq.com/statics/2023/05/16/a - 副本_20230516134945A240.jpg', '1', 4, '0');
INSERT INTO `bus_order_image` VALUES (19, 'http://file.szcjzhxq.com/statics/2023/05/16/a - 副本_20230516134950A241.jpg', '1', 4, '0');
INSERT INTO `bus_order_image` VALUES (20, 'http://file.szcjzhxq.com/statics/2023/05/16/b - 副本_20230516135009A243.jpg', '1', 4, '0');
INSERT INTO `bus_order_image` VALUES (21, 'http://file.szcjzhxq.com/statics/2023/05/16/20230308093745_20230516141411A245.jpg', '1', 5, '0');
INSERT INTO `bus_order_image` VALUES (22, 'http://file.szcjzhxq.com/statics/2023/05/16/file-1684217840427_20230516141740A246.jpeg', '1', 6, '0');
INSERT INTO `bus_order_image` VALUES (23, 'http://file.szcjzhxq.com/statics/2023/05/16/国徽面_20230516141852A247.jpeg', '1', 7, '0');
INSERT INTO `bus_order_image` VALUES (24, 'http://file.szcjzhxq.com/statics/2023/05/16/国徽面_20230516141908A248.jpeg', '1', 8, '0');
INSERT INTO `bus_order_image` VALUES (25, 'http://file.szcjzhxq.com/statics/2023/05/16/20230308093745_20230516141952A249.jpg', '1', 9, '0');
INSERT INTO `bus_order_image` VALUES (26, 'http://file.szcjzhxq.com/statics/2023/05/16/20220120024601461_20230516142156A250.jpg', '1', 10, '0');
INSERT INTO `bus_order_image` VALUES (27, 'http://file.szcjzhxq.com/statics/2023/05/16/20220120024601461_20230516142915A255.jpg', '1', 11, '0');
INSERT INTO `bus_order_image` VALUES (28, 'http://file.szcjzhxq.com/statics/2023/05/17/20230308093745_20230517135906A313.jpg', '2', 4, '0');
INSERT INTO `bus_order_image` VALUES (29, 'http://file.szcjzhxq.com/statics/2023/05/17/a - 副本_20230517135928A314.jpg', '2', 5, '0');
INSERT INTO `bus_order_image` VALUES (30, 'http://file.szcjzhxq.com/statics/2023/05/17/a_20230517135928A315.jpg', '2', 5, '0');
INSERT INTO `bus_order_image` VALUES (31, 'http://file.szcjzhxq.com/statics/2023/05/17/b - 副本_20230517135929A316.jpg', '2', 5, '0');
INSERT INTO `bus_order_image` VALUES (32, 'http://file.szcjzhxq.com/statics/2023/05/17/210F2130512J47-0-lp_20230517140246A319.jpg', '1', 12, '0');
INSERT INTO `bus_order_image` VALUES (33, 'http://file.szcjzhxq.com/statics/2023/05/17/426_20230517140246A320.jpg', '1', 12, '0');
INSERT INTO `bus_order_image` VALUES (34, 'http://file.szcjzhxq.com/statics/2023/05/17/6981e2664738ed6dc5a77c8ca448d95e_20230517140246A321.jpeg', '1', 12, '0');
INSERT INTO `bus_order_image` VALUES (35, 'http://file.szcjzhxq.com/statics/2023/05/17/0230308094048_20230517140325A322.png', '1', 13, '0');
INSERT INTO `bus_order_image` VALUES (36, 'http://file.szcjzhxq.com/statics/2023/05/17/0230308094634_20230517140325A323.png', '1', 13, '0');
INSERT INTO `bus_order_image` VALUES (37, 'http://file.szcjzhxq.com/statics/2023/05/17/0230308094701_20230517140325A324.png', '1', 13, '0');
INSERT INTO `bus_order_image` VALUES (38, 'http://file.szcjzhxq.com/statics/2023/05/19/小区道路脏乱图片_20230519084535A505.jpg', '1', 14, '0');
INSERT INTO `bus_order_image` VALUES (39, 'http://file.szcjzhxq.com/statics/2023/05/20/小区道路脏乱图片_20230520104519A618.jpg', '1', 15, '0');
INSERT INTO `bus_order_image` VALUES (40, 'http://file.szcjzhxq.com/statics/2023/05/22/Frame 28589_20230522170042A718.png', '1', 16, '0');
INSERT INTO `bus_order_image` VALUES (41, 'http://file.szcjzhxq.com/statics/2023/05/22/小区道路脏乱图片_20230522170216A719.jpg', '1', 17, '0');
INSERT INTO `bus_order_image` VALUES (42, 'http://file.szcjzhxq.com/statics/2023/05/22/426_20230522171147A723.jpg', '1', 18, '0');
INSERT INTO `bus_order_image` VALUES (43, 'http://file.szcjzhxq.com/statics/2023/05/22/默认_20230522171526A726.jpg', '1', 19, '0');
INSERT INTO `bus_order_image` VALUES (44, 'http://file.szcjzhxq.com/statics/2023/05/23/工具_20230523153306A793.png', '1', 20, '0');
INSERT INTO `bus_order_image` VALUES (45, 'http://file.szcjzhxq.com/statics/2023/05/23/426_20230523154010A794.jpg', '2', 6, '0');
INSERT INTO `bus_order_image` VALUES (46, 'http://file.szcjzhxq.com/statics/2023/05/23/20230308094038_20230523154851A795.png', '1', 21, '0');
INSERT INTO `bus_order_image` VALUES (47, 'http://file.szcjzhxq.com/statics/2023/05/23/工具_20230523155235A796.png', '1', 22, '0');
INSERT INTO `bus_order_image` VALUES (48, 'http://file.szcjzhxq.com/statics/2023/05/23/小区道路脏乱图片_20230523165711A798.jpg', '1', 23, '0');
INSERT INTO `bus_order_image` VALUES (49, 'http://file.szcjzhxq.com/statics/2023/05/23/a_20230523182432A805.jpg', '1', 24, '0');
INSERT INTO `bus_order_image` VALUES (50, 'http://file.szcjzhxq.com/statics/2023/05/23/b - 副本_20230523182432A806.jpg', '1', 24, '0');
INSERT INTO `bus_order_image` VALUES (51, 'http://file.szcjzhxq.com/statics/2023/05/23/b_20230523182432A807.jpg', '1', 24, '0');
INSERT INTO `bus_order_image` VALUES (52, 'http://file.szcjzhxq.com/statics/2023/05/23/房间内图_20230523183849A811.jpg', '0', 9, '0');
INSERT INTO `bus_order_image` VALUES (53, 'http://file.szcjzhxq.com/statics/2023/05/24/小区道路脏乱图片_20230524144251A837.jpg', '1', 25, '0');
INSERT INTO `bus_order_image` VALUES (54, 'http://file.szcjzhxq.com/statics/2023/05/24/走道垃圾_20230524144257A838.jpg', '1', 25, '0');
INSERT INTO `bus_order_image` VALUES (55, 'http://file.szcjzhxq.com/statics/2023/05/24/426_20230524145949A839.jpg', '0', 10, '0');
INSERT INTO `bus_order_image` VALUES (56, 'http://file.szcjzhxq.com/statics/2023/06/03/工具_20230603134509A013.png', '0', 11, '0');
INSERT INTO `bus_order_image` VALUES (57, 'http://file.szcjzhxq.com/statics/2023/06/09/tmp_f0d9e085b76c82bbd944779a9bbb5b64a6bdf84c0cce267d_20230609140936A174.jpg', '1', 26, '0');
INSERT INTO `bus_order_image` VALUES (58, 'http://file.szcjzhxq.com/statics/2023/06/09/tmp_0f53b872892a7d7d2671da49ef75c02450d850af6c9c733d_20230609140936A175.jpg', '1', 26, '0');
INSERT INTO `bus_order_image` VALUES (59, 'http://file.szcjzhxq.com/statics/2023/06/09/tmp_0c671ef8ab7ac6fb32c576e028299ec423034df684f54e07_20230609141040A177.jpg', '1', 27, '0');
INSERT INTO `bus_order_image` VALUES (60, 'http://file.szcjzhxq.com/statics/2023/06/09/tmp_c1f02cb044bbff4365d6ea07471abffd45bd1f79f4ff59a9_20230609141040A178.jpg', '1', 27, '0');
INSERT INTO `bus_order_image` VALUES (61, 'http://file.szcjzhxq.com/statics/2023/06/09/tmp_3f61c1ab2471320e13b88fc989c479fba72423f316749218_20230609163637A192.jpg', '1', 28, '0');
INSERT INTO `bus_order_image` VALUES (62, 'http://file.szcjzhxq.com/statics/2023/06/09/tmp_4c7f53a81460404d16c49ca37bc467fec2c14d10f885f1e1_20230609163641A193.jpg', '1', 28, '0');
INSERT INTO `bus_order_image` VALUES (63, 'http://file.szcjzhxq.com/statics/2023/06/09/tmp_59db18de567c83f4700339270607fbb0fecf7fda8a688d30_20230609165549A195.jpg', '0', 12, '0');
INSERT INTO `bus_order_image` VALUES (64, 'http://file.szcjzhxq.com/statics/2023/06/09/tmp_eaa5e03b22001fc348ac9d3eea2a372b10d3cd619af02c3a_20230609165549A196.jpg', '0', 12, '0');
INSERT INTO `bus_order_image` VALUES (65, 'http://file.szcjzhxq.com/statics/2023/06/09/tmp_4239ec2e70b0e3b7d763e862ce9d6236162116e0696b239c_20230609165553A197.jpg', '0', 12, '0');
INSERT INTO `bus_order_image` VALUES (66, 'http://file.szcjzhxq.com/statics/2023/06/10/1686361130222_wx_camera_1686135721691_20230610093918A209.jpg', '1', 29, '0');
INSERT INTO `bus_order_image` VALUES (67, 'http://file.szcjzhxq.com/statics/2023/06/10/1686361130329_wx_camera_1686196824588_20230610093918A210.jpg', '1', 29, '0');
INSERT INTO `bus_order_image` VALUES (68, 'http://file.szcjzhxq.com/statics/2023/06/10/1686363954508_wx_camera_1686196824588_20230610102623A213.jpg', '0', 13, '0');
INSERT INTO `bus_order_image` VALUES (69, 'http://file.szcjzhxq.com/statics/2023/06/10/1686363954657_Screenshot_2023-06-07-23-22-58-014_com.ss.android.ugc.aweme.lite-edit_20230610102623A214.jpg', '0', 13, '0');
INSERT INTO `bus_order_image` VALUES (70, 'http://file.szcjzhxq.com/statics/2023/06/12/工具_20230612151432A305.png', '2', 7, '0');
INSERT INTO `bus_order_image` VALUES (71, 'http://file.szcjzhxq.com/statics/2023/06/12/轮椅_20230612151438A306.jpg', '2', 7, '0');
INSERT INTO `bus_order_image` VALUES (72, 'http://file.szcjzhxq.com/statics/2023/06/12/雨伞_20230612151439A307.jpg', '2', 7, '0');
INSERT INTO `bus_order_image` VALUES (73, 'http://file.szcjzhxq.com/statics/2023/06/12/雨伞_20230612154035A308.jpg', '2', 8, '0');
INSERT INTO `bus_order_image` VALUES (74, 'http://file.szcjzhxq.com/statics/2023/06/13/a - 副本_20230613171130A357.jpg', '1', 30, '0');
INSERT INTO `bus_order_image` VALUES (75, 'http://file.szcjzhxq.com/statics/2023/06/13/b - 副本_20230613171130A358.jpg', '1', 30, '0');
INSERT INTO `bus_order_image` VALUES (76, 'http://file.szcjzhxq.com/statics/2023/06/27/男_20230627110338A380.png', '1', 31, '0');
INSERT INTO `bus_order_image` VALUES (77, 'http://file.szcjzhxq.com/statics/2023/06/27/女_20230627144010A383.png', '0', 14, '0');
INSERT INTO `bus_order_image` VALUES (78, 'http://file.szcjzhxq.com/statics/2023/06/27/男_20230627144014A384.png', '0', 14, '0');
INSERT INTO `bus_order_image` VALUES (79, 'http://file.szcjzhxq.com/statics/2023/06/27/女_20230627144203A385.png', '2', 9, '0');
INSERT INTO `bus_order_image` VALUES (80, 'http://file.szcjzhxq.com/statics/2023/06/27/人像面_20230627144206A386.png', '2', 9, '0');
INSERT INTO `bus_order_image` VALUES (81, 'http://file.szcjzhxq.com/statics/2023/06/27/6_20230627155630A388.jpg', '2', 10, '0');
INSERT INTO `bus_order_image` VALUES (82, 'http://file.szcjzhxq.com/statics/2023/10/24/tmp_1abc0aef1d073368944faac7b322cfec_20231024171204A864.jpg', '1', 34, '0');

-- ----------------------------
-- Table structure for bus_order_record
-- ----------------------------
DROP TABLE IF EXISTS `bus_order_record`;
CREATE TABLE `bus_order_record`  (
  `order_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单类型：见类 OrderConstants',
  `order_id` bigint NOT NULL DEFAULT 0 COMMENT '工单id，order_type对应的表',
  `data_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '数据类型：0=其他，1=执行',
  `order_result` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '结果',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '提交人姓名',
  `relevant` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '相关人员姓名（多人）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工单执行情况表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_order_record
-- ----------------------------
INSERT INTO `bus_order_record` VALUES ('0', 1, '0', '自动分配', '测试安防甲', '', '2023-05-15 18:48:39');
INSERT INTO `bus_order_record` VALUES ('0', 2, '0', '自动分配', '测试装修甲', '', '2023-05-15 18:53:20');
INSERT INTO `bus_order_record` VALUES ('0', 3, '0', '自动分配', '测试安防乙', '', '2023-05-15 18:54:45');
INSERT INTO `bus_order_record` VALUES ('1', 1, '0', '自动分配', '', '', '2023-05-15 19:01:44');
INSERT INTO `bus_order_record` VALUES ('1', 2, '0', '自动分配', '', '', '2023-05-15 19:03:35');
INSERT INTO `bus_order_record` VALUES ('6', 1, '0', '自动分配', '15288888888', '', '2023-05-15 19:34:08');
INSERT INTO `bus_order_record` VALUES ('8', 0, '1', '灾害报事工单申请: 驳回', '15288888888', '', '2023-05-15 20:04:39');
INSERT INTO `bus_order_record` VALUES ('7', 1, '1', '分配方式：管理员分配', '15288888888', '', '2023-05-15 20:33:09');
INSERT INTO `bus_order_record` VALUES ('8', 1, '0', '分配方式：管理员分配', '15288888888', '', '2023-05-15 20:33:28');
INSERT INTO `bus_order_record` VALUES ('5', 1, '0', '接单', '测试维修甲', '', '2023-05-15 20:33:59');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '测试保洁甲', '', '2023-05-15 23:59:50');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '测试保洁乙', '', '2023-05-15 23:59:50');
INSERT INTO `bus_order_record` VALUES ('7', 1, '0', '接单', '测试安防甲', '', '2023-05-16 11:25:30');
INSERT INTO `bus_order_record` VALUES ('7', 1, '1', '开始执行', '测试安防甲', '', '2023-05-16 11:33:28');
INSERT INTO `bus_order_record` VALUES ('7', 1, '1', '完成工单', '测试安防甲', '', '2023-05-16 11:55:45');
INSERT INTO `bus_order_record` VALUES ('7', 2, '1', '分配方式：管理员分配', '15288888888', '', '2023-05-16 14:04:53');
INSERT INTO `bus_order_record` VALUES ('7', 2, '0', '接单', '测试安防甲', '', '2023-05-16 14:05:02');
INSERT INTO `bus_order_record` VALUES ('7', 2, '1', '开始执行', '测试安防甲', '', '2023-05-16 14:05:07');
INSERT INTO `bus_order_record` VALUES ('7', 2, '1', '完成工单', '测试安防甲', '', '2023-05-16 14:05:29');
INSERT INTO `bus_order_record` VALUES ('7', 3, '1', '分配方式：管理员分配', '15288888888', '', '2023-05-16 14:16:50');
INSERT INTO `bus_order_record` VALUES ('7', 3, '0', '接单', '测试安防甲', '', '2023-05-16 14:17:00');
INSERT INTO `bus_order_record` VALUES ('7', 3, '1', '开始执行', '测试安防甲', '', '2023-05-16 14:17:05');
INSERT INTO `bus_order_record` VALUES ('7', 4, '1', '分配方式：管理员分配', '15288888888', '', '2023-05-16 14:18:22');
INSERT INTO `bus_order_record` VALUES ('7', 7, '1', '分配方式：管理员分配', '15288888888', '', '2023-05-16 14:20:13');
INSERT INTO `bus_order_record` VALUES ('7', 6, '1', '分配方式：管理员分配', '15288888888', '', '2023-05-16 14:20:20');
INSERT INTO `bus_order_record` VALUES ('7', 5, '1', '分配方式：管理员分配', '15288888888', '', '2023-05-16 14:20:27');
INSERT INTO `bus_order_record` VALUES ('7', 7, '0', '接单', '测试安防甲', '', '2023-05-16 14:20:31');
INSERT INTO `bus_order_record` VALUES ('7', 5, '0', '接单', '测试安防甲', '', '2023-05-16 14:20:35');
INSERT INTO `bus_order_record` VALUES ('7', 5, '1', '开始执行', '测试安防甲', '', '2023-05-16 14:20:40');
INSERT INTO `bus_order_record` VALUES ('7', 6, '0', '接单', '测试安防甲', '', '2023-05-16 14:21:17');
INSERT INTO `bus_order_record` VALUES ('7', 8, '1', '分配方式：管理员分配', '15288888888', '', '2023-05-16 14:22:16');
INSERT INTO `bus_order_record` VALUES ('7', 8, '0', '接单', '测试安防甲', '', '2023-05-16 14:22:30');
INSERT INTO `bus_order_record` VALUES ('7', 8, '1', '开始执行', '测试安防甲', '', '2023-05-16 14:22:37');
INSERT INTO `bus_order_record` VALUES ('7', 8, '0', '工单协助', '测试安防甲', '测试安防乙', '2023-05-16 14:23:52');
INSERT INTO `bus_order_record` VALUES ('7', 8, '1', '完成工单', '测试安防甲', '', '2023-05-16 14:24:14');
INSERT INTO `bus_order_record` VALUES ('7', 8, '0', '园区申请评价', '业主戊', '', '2023-05-16 14:25:23');
INSERT INTO `bus_order_record` VALUES ('7', 1, '0', '园区申请评价', '业主戊', '', '2023-05-16 14:26:15');
INSERT INTO `bus_order_record` VALUES ('7', 2, '0', '园区申请评价', '业主戊', '', '2023-05-16 14:37:14');
INSERT INTO `bus_order_record` VALUES ('8', 1, '0', '接单', '测试安防甲', '', '2023-05-16 14:37:34');
INSERT INTO `bus_order_record` VALUES ('8', 0, '1', '灾害报事工单申请: 驳回', '15288888888', '', '2023-05-16 14:38:13');
INSERT INTO `bus_order_record` VALUES ('8', 1, '1', '工单协助', '15288888888', '', '2023-05-16 14:38:19');
INSERT INTO `bus_order_record` VALUES ('8', 1, '1', '开始执行', '测试安防甲', '', '2023-05-16 14:38:25');
INSERT INTO `bus_order_record` VALUES ('8', 1, '1', '完成工单', '测试安防甲', '', '2023-05-16 14:39:03');
INSERT INTO `bus_order_record` VALUES ('8', 1, '0', '灾害申请评价', '业主戊', '', '2023-05-16 14:39:15');
INSERT INTO `bus_order_record` VALUES ('5', 8, '1', '开始执行', '测试维修甲', '', '2023-05-16 14:41:23');
INSERT INTO `bus_order_record` VALUES ('5', 8, '1', '完成工单', '测试维修甲', '', '2023-05-16 14:41:56');
INSERT INTO `bus_order_record` VALUES ('5', 7, '1', '开始执行', '测试维修甲', '', '2023-05-16 14:52:25');
INSERT INTO `bus_order_record` VALUES ('5', 7, '1', '完成工单', '测试维修甲', '', '2023-05-16 14:52:34');
INSERT INTO `bus_order_record` VALUES ('0', 1, '0', '接单', '测试安防甲', '', '2023-05-16 16:01:50');
INSERT INTO `bus_order_record` VALUES ('0', 1, '1', '开始执行', '测试安防甲', '', '2023-05-16 16:01:58');
INSERT INTO `bus_order_record` VALUES ('2', 1, '0', '工单分配方式: 管理员分配', '', '', '2023-05-16 16:25:02');
INSERT INTO `bus_order_record` VALUES ('2', 1, '0', '工单转交', '测试安防甲', '测试安防乙', '2023-05-16 16:25:30');
INSERT INTO `bus_order_record` VALUES ('3', 4, '0', '接单', '测试保洁甲', '', '2023-05-16 17:02:46');
INSERT INTO `bus_order_record` VALUES ('3', 4, '0', '工单转交', '测试保洁甲', '测试保洁乙', '2023-05-16 17:04:21');
INSERT INTO `bus_order_record` VALUES ('3', 5, '0', '接单', '测试保洁乙', '', '2023-05-16 17:04:43');
INSERT INTO `bus_order_record` VALUES ('3', 5, '1', '开始执行', '测试保洁乙', '', '2023-05-16 17:04:47');
INSERT INTO `bus_order_record` VALUES ('3', 5, '1', '完成工单', '测试保洁乙', '', '2023-05-16 17:04:58');
INSERT INTO `bus_order_record` VALUES ('6', 1, '0', '接单', '测试维修甲', '', '2023-05-16 17:18:29');
INSERT INTO `bus_order_record` VALUES ('6', 1, '1', '开始执行', '测试维修甲', '', '2023-05-16 17:18:40');
INSERT INTO `bus_order_record` VALUES ('6', 1, '1', '完成工单', '测试维修甲', '', '2023-05-16 17:18:56');
INSERT INTO `bus_order_record` VALUES ('6', 2, '0', '自动分配', '15288888888', '', '2023-05-16 17:19:16');
INSERT INTO `bus_order_record` VALUES ('6', 2, '0', '接单', '测试维修甲', '', '2023-05-16 17:19:23');
INSERT INTO `bus_order_record` VALUES ('6', 1, '0', '工单协助', '测试维修甲', '测试维修乙', '2023-05-16 17:19:31');
INSERT INTO `bus_order_record` VALUES ('6', 2, '1', '开始执行', '测试维修甲', '', '2023-05-16 17:19:46');
INSERT INTO `bus_order_record` VALUES ('6', 2, '1', '完成工单', '测试维修甲', '', '2023-05-16 17:19:59');
INSERT INTO `bus_order_record` VALUES ('4', 1, '0', '接单', '测试装修乙', '', '2023-05-16 17:58:39');
INSERT INTO `bus_order_record` VALUES ('4', 1, '1', '开始执行', '测试装修乙', '', '2023-05-16 17:59:25');
INSERT INTO `bus_order_record` VALUES ('4', 1, '0', '工单协助', '测试装修乙', '测试装修甲', '2023-05-16 17:59:36');
INSERT INTO `bus_order_record` VALUES ('4', 1, '1', '完成工单', '测试装修乙', '', '2023-05-16 18:01:05');
INSERT INTO `bus_order_record` VALUES ('4', 1, '0', '装修评定工单整改', '15288888888', '', '2023-05-16 18:03:17');
INSERT INTO `bus_order_record` VALUES ('7', 9, '1', '分配方式：管理员分配', '15288888888', '', '2023-05-17 11:51:46');
INSERT INTO `bus_order_record` VALUES ('7', 6, '1', '开始执行', '测试安防甲', '', '2023-05-17 13:56:20');
INSERT INTO `bus_order_record` VALUES ('7', 4, '0', '接单', '测试安防甲', '', '2023-05-17 13:57:14');
INSERT INTO `bus_order_record` VALUES ('8', 3, '0', '分配方式：管理员分配', '15288888888', '', '2023-05-17 14:00:06');
INSERT INTO `bus_order_record` VALUES ('8', 2, '0', '分配方式：管理员分配', '15288888888', '', '2023-05-17 14:00:11');
INSERT INTO `bus_order_record` VALUES ('8', 3, '0', '接单', '测试安防甲', '', '2023-05-17 14:00:42');
INSERT INTO `bus_order_record` VALUES ('8', 3, '1', '开始执行', '测试安防甲', '', '2023-05-17 14:00:56');
INSERT INTO `bus_order_record` VALUES ('7', 10, '1', '分配方式：管理员分配', '15288888888', '', '2023-05-17 14:04:06');
INSERT INTO `bus_order_record` VALUES ('1', 2, '0', '接单', '测试安防甲', '', '2023-05-17 15:04:25');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '测试保洁甲', '', '2023-05-17 23:59:49');
INSERT INTO `bus_order_record` VALUES ('0', 6, '0', '接单', '测试安防甲', '', '2023-05-18 10:42:58');
INSERT INTO `bus_order_record` VALUES ('0', 5, '0', '接单', '测试安防甲', '', '2023-05-18 10:48:28');
INSERT INTO `bus_order_record` VALUES ('1', 5, '0', '接单', '测试安防甲', '', '2023-05-18 11:16:27');
INSERT INTO `bus_order_record` VALUES ('0', 9, '0', '接单', '测试安防甲', '', '2023-05-20 09:32:42');
INSERT INTO `bus_order_record` VALUES ('0', 9, '1', '开始执行', '测试安防甲', '', '2023-05-20 09:33:11');
INSERT INTO `bus_order_record` VALUES ('0', 9, '1', '完成工单', '测试安防甲', '', '2023-05-20 09:34:04');
INSERT INTO `bus_order_record` VALUES ('7', 12, '0', '接单', '测试安防甲', '', '2023-05-20 10:50:30');
INSERT INTO `bus_order_record` VALUES ('7', 12, '1', '开始执行', '测试安防甲', '', '2023-05-20 10:50:52');
INSERT INTO `bus_order_record` VALUES ('7', 12, '1', '完成工单', '测试安防甲', '', '2023-05-20 10:51:55');
INSERT INTO `bus_order_record` VALUES ('7', 12, '0', '园区申请评价', '业主戊', '', '2023-05-20 10:53:39');
INSERT INTO `bus_order_record` VALUES ('0', 12, '0', '接单', '测试安防甲', '', '2023-05-22 16:47:32');
INSERT INTO `bus_order_record` VALUES ('0', 12, '1', '开始执行', '测试安防甲', '', '2023-05-22 16:47:38');
INSERT INTO `bus_order_record` VALUES ('0', 12, '1', '完成工单', '测试安防甲', '', '2023-05-22 16:48:08');
INSERT INTO `bus_order_record` VALUES ('7', 13, '0', '接单', '测试安防甲', '', '2023-05-22 17:07:29');
INSERT INTO `bus_order_record` VALUES ('7', 13, '1', '开始执行', '测试安防甲', '', '2023-05-22 17:07:32');
INSERT INTO `bus_order_record` VALUES ('7', 13, '1', '完成工单', '测试安防甲', '', '2023-05-22 17:07:51');
INSERT INTO `bus_order_record` VALUES ('7', 13, '0', '园区申请评价', '业主戊', '', '2023-05-22 17:08:22');
INSERT INTO `bus_order_record` VALUES ('7', 14, '1', '分配方式：管理员分配', '15288888888', '', '2023-05-22 17:12:43');
INSERT INTO `bus_order_record` VALUES ('7', 14, '0', '接单', '测试安防甲', '', '2023-05-22 17:13:11');
INSERT INTO `bus_order_record` VALUES ('7', 14, '1', '开始执行', '测试安防甲', '', '2023-05-22 17:13:19');
INSERT INTO `bus_order_record` VALUES ('7', 14, '1', '完成工单', '测试安防甲', '', '2023-05-22 17:13:51');
INSERT INTO `bus_order_record` VALUES ('7', 14, '0', '园区申请评价', '业主戊', '', '2023-05-22 17:14:37');
INSERT INTO `bus_order_record` VALUES ('7', 15, '1', '分配方式：管理员分配', '15288888888', '', '2023-05-22 17:15:51');
INSERT INTO `bus_order_record` VALUES ('7', 15, '0', '接单', '测试安防甲', '', '2023-05-22 17:16:18');
INSERT INTO `bus_order_record` VALUES ('7', 15, '1', '开始执行', '测试安防甲', '', '2023-05-22 17:17:24');
INSERT INTO `bus_order_record` VALUES ('7', 15, '1', '完成工单', '测试安防甲', '', '2023-05-22 17:17:39');
INSERT INTO `bus_order_record` VALUES ('7', 15, '0', '园区申请评价', '业主戊', '', '2023-05-22 17:18:03');
INSERT INTO `bus_order_record` VALUES ('8', 2, '0', '接单', '测试安防甲', '', '2023-05-22 17:37:28');
INSERT INTO `bus_order_record` VALUES ('8', 2, '1', '开始执行', '测试安防甲', '', '2023-05-22 17:37:34');
INSERT INTO `bus_order_record` VALUES ('8', 2, '1', '完成工单', '测试安防甲', '', '2023-05-22 17:38:10');
INSERT INTO `bus_order_record` VALUES ('8', 2, '0', '灾害申请评价', '业主戊', '', '2023-05-22 17:42:15');
INSERT INTO `bus_order_record` VALUES ('7', 4, '1', '开始执行', '测试安防甲', '', '2023-05-23 09:02:55');
INSERT INTO `bus_order_record` VALUES ('7', 4, '1', '完成工单', '测试安防甲', '', '2023-05-23 09:03:28');
INSERT INTO `bus_order_record` VALUES ('7', 4, '0', '园区申请评价', '业主戊', '', '2023-05-23 09:12:32');
INSERT INTO `bus_order_record` VALUES ('7', 10, '0', '接单', '测试安防甲', '', '2023-05-23 09:18:12');
INSERT INTO `bus_order_record` VALUES ('7', 10, '1', '开始执行', '测试安防甲', '', '2023-05-23 09:18:18');
INSERT INTO `bus_order_record` VALUES ('7', 10, '1', '完成工单', '测试安防甲', '', '2023-05-23 09:18:45');
INSERT INTO `bus_order_record` VALUES ('7', 10, '0', '园区申请评价', '业主戊', '', '2023-05-23 09:23:20');
INSERT INTO `bus_order_record` VALUES ('1', 5, '1', '开始执行', '测试安防甲', '', '2023-05-23 16:02:56');
INSERT INTO `bus_order_record` VALUES ('0', 14, '0', '接单', '测试安防甲', '', '2023-05-23 16:29:54');
INSERT INTO `bus_order_record` VALUES ('0', 14, '1', '开始执行', '测试安防甲', '', '2023-05-23 16:30:08');
INSERT INTO `bus_order_record` VALUES ('0', 6, '1', '开始执行', '测试安防甲', '', '2023-05-23 16:30:14');
INSERT INTO `bus_order_record` VALUES ('0', 14, '1', '完成工单', '测试安防甲', '', '2023-05-23 16:30:40');
INSERT INTO `bus_order_record` VALUES ('7', 20, '0', '接单', '测试安防甲', '', '2023-05-23 16:58:31');
INSERT INTO `bus_order_record` VALUES ('7', 20, '1', '开始执行', '测试安防甲', '', '2023-05-23 16:58:34');
INSERT INTO `bus_order_record` VALUES ('7', 20, '1', '完成工单', '测试安防甲', '', '2023-05-23 16:58:59');
INSERT INTO `bus_order_record` VALUES ('7', 20, '0', '园区申请评价', '业主戊', '', '2023-05-23 16:59:29');
INSERT INTO `bus_order_record` VALUES ('7', 21, '0', '接单', '测试安防甲', '', '2023-05-23 18:25:31');
INSERT INTO `bus_order_record` VALUES ('5', 9, '0', '接单', '入驻维修', '', '2023-05-23 18:39:47');
INSERT INTO `bus_order_record` VALUES ('5', 9, '1', '开始执行', '入驻维修', '', '2023-05-23 18:39:50');
INSERT INTO `bus_order_record` VALUES ('5', 9, '1', '完成工单', '入驻维修', '', '2023-05-23 18:40:07');
INSERT INTO `bus_order_record` VALUES ('4', 3, '0', '接单', '测试装修乙', '', '2023-05-24 15:44:36');
INSERT INTO `bus_order_record` VALUES ('4', 3, '1', '开始执行', '测试装修乙', '', '2023-05-24 15:44:54');
INSERT INTO `bus_order_record` VALUES ('4', 3, '1', '完成工单', '测试装修乙', '', '2023-05-24 15:45:30');
INSERT INTO `bus_order_record` VALUES ('1', 19, '0', '自动分配', '', '', '2023-05-31 14:58:14');
INSERT INTO `bus_order_record` VALUES ('8', 3, '1', '完成工单', '测试安防甲', '', '2023-06-06 08:51:43');
INSERT INTO `bus_order_record` VALUES ('7', 21, '1', '开始执行', '测试安防甲', '', '2023-06-06 15:16:17');
INSERT INTO `bus_order_record` VALUES ('1', 2, '1', '开始执行', '测试安防甲', '', '2023-06-06 15:38:22');
INSERT INTO `bus_order_record` VALUES ('0', 33, '0', '接单', '测试安防甲', '', '2023-06-06 16:08:38');
INSERT INTO `bus_order_record` VALUES ('7', 21, '1', '完成工单', '测试安防甲', '', '2023-06-07 16:14:55');
INSERT INTO `bus_order_record` VALUES ('8', 3, '0', '灾害申请评价', '业主戊', '', '2023-06-08 10:02:02');
INSERT INTO `bus_order_record` VALUES ('2', 3, '0', '工单分配方式: 管理员分配', '', '', '2023-06-09 09:59:26');
INSERT INTO `bus_order_record` VALUES ('7', 21, '0', '园区申请评价', '业主戊', '', '2023-06-09 11:22:23');
INSERT INTO `bus_order_record` VALUES ('7', 23, '0', '接单', '员工李', '', '2023-06-09 16:41:57');
INSERT INTO `bus_order_record` VALUES ('7', 23, '1', '开始执行', '员工李', '', '2023-06-09 16:42:08');
INSERT INTO `bus_order_record` VALUES ('7', 23, '1', '完成工单', '员工李', '', '2023-06-09 16:42:42');
INSERT INTO `bus_order_record` VALUES ('7', 23, '0', '园区申请评价', '陈先生', '', '2023-06-09 16:43:49');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '李金花', '', '2023-06-09 23:59:38');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '王维春', '', '2023-06-10 23:59:38');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '谢梅花', '', '2023-06-11 23:59:37');
INSERT INTO `bus_order_record` VALUES ('8', 5, '0', '分配方式：管理员分配', '15288888888', '', '2023-06-12 15:23:56');
INSERT INTO `bus_order_record` VALUES ('0', 45, '0', '接单', '测试安防甲', '', '2023-06-12 15:32:45');
INSERT INTO `bus_order_record` VALUES ('1', 42, '0', '接单', '测试安防甲', '', '2023-06-12 15:32:54');
INSERT INTO `bus_order_record` VALUES ('8', 5, '1', '工单转交', '15288888888', '', '2023-06-12 15:36:00');
INSERT INTO `bus_order_record` VALUES ('7', 19, '0', '接单', '员工程', '', '2023-06-12 15:37:16');
INSERT INTO `bus_order_record` VALUES ('8', 4, '0', '接单', '员工程', '', '2023-06-12 15:43:10');
INSERT INTO `bus_order_record` VALUES ('8', 4, '1', '工单转交', '15288888888', '', '2023-06-12 15:44:25');
INSERT INTO `bus_order_record` VALUES ('0', 37, '0', '工单转交', '员工程', '测试安防甲', '2023-06-12 15:51:50');
INSERT INTO `bus_order_record` VALUES ('7', 18, '0', '接单', '测试安防甲', '', '2023-06-12 15:52:59');
INSERT INTO `bus_order_record` VALUES ('7', 17, '0', '接单', '测试安防甲', '', '2023-06-12 16:00:08');
INSERT INTO `bus_order_record` VALUES ('0', 43, '0', '接单', '测试安防甲', '', '2023-06-12 16:11:48');
INSERT INTO `bus_order_record` VALUES ('0', 41, '0', '接单', '测试安防甲', '', '2023-06-12 16:12:32');
INSERT INTO `bus_order_record` VALUES ('0', 39, '0', '接单', '测试安防甲', '', '2023-06-12 16:12:45');
INSERT INTO `bus_order_record` VALUES ('0', 35, '0', '接单', '员工程', '', '2023-06-12 16:13:17');
INSERT INTO `bus_order_record` VALUES ('0', 31, '0', '接单', '测试安防甲', '', '2023-06-12 16:13:40');
INSERT INTO `bus_order_record` VALUES ('1', 42, '1', '开始执行', '测试安防甲', '', '2023-06-12 16:16:23');
INSERT INTO `bus_order_record` VALUES ('0', 29, '0', '接单', '测试安防甲', '', '2023-06-12 16:20:26');
INSERT INTO `bus_order_record` VALUES ('7', 16, '0', '接单', '测试安防甲', '', '2023-06-12 16:22:29');
INSERT INTO `bus_order_record` VALUES ('1', 40, '0', '接单', '测试安防甲', '', '2023-06-12 16:29:42');
INSERT INTO `bus_order_record` VALUES ('0', 27, '0', '接单', '测试安防甲', '', '2023-06-12 17:08:10');
INSERT INTO `bus_order_record` VALUES ('0', 25, '0', '接单', '测试安防甲', '', '2023-06-12 17:13:35');
INSERT INTO `bus_order_record` VALUES ('0', 47, '0', '接单', '测试安防甲', '', '2023-06-12 17:34:55');
INSERT INTO `bus_order_record` VALUES ('0', 47, '1', '开始执行', '测试安防甲', '', '2023-06-12 17:35:11');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '吴玉兰', '', '2023-06-12 23:59:37');
INSERT INTO `bus_order_record` VALUES ('0', 48, '0', '接单', '测试安防甲', '', '2023-06-13 09:07:32');
INSERT INTO `bus_order_record` VALUES ('0', 23, '0', '接单', '测试安防甲', '', '2023-06-13 09:07:56');
INSERT INTO `bus_order_record` VALUES ('0', 43, '1', '开始执行', '测试安防甲', '', '2023-06-13 09:16:07');
INSERT INTO `bus_order_record` VALUES ('0', 48, '1', '开始执行', '测试安防甲', '', '2023-06-13 09:20:35');
INSERT INTO `bus_order_record` VALUES ('0', 37, '0', '接单', '测试安防甲', '', '2023-06-13 09:21:15');
INSERT INTO `bus_order_record` VALUES ('1', 40, '1', '开始执行', '测试安防甲', '', '2023-06-13 09:21:31');
INSERT INTO `bus_order_record` VALUES ('7', 11, '0', '接单', '测试安防甲', '', '2023-06-13 09:22:10');
INSERT INTO `bus_order_record` VALUES ('7', 18, '1', '开始执行', '测试安防甲', '', '2023-06-13 09:22:35');
INSERT INTO `bus_order_record` VALUES ('7', 17, '1', '开始执行', '测试安防甲', '', '2023-06-13 09:22:47');
INSERT INTO `bus_order_record` VALUES ('7', 16, '1', '开始执行', '测试安防甲', '', '2023-06-13 09:23:14');
INSERT INTO `bus_order_record` VALUES ('7', 9, '0', '接单', '测试安防甲', '', '2023-06-13 09:23:32');
INSERT INTO `bus_order_record` VALUES ('0', 50, '0', '接单', '测试安防甲', '', '2023-06-13 10:16:40');
INSERT INTO `bus_order_record` VALUES ('0', 50, '1', '开始执行', '测试安防甲', '', '2023-06-13 10:17:58');
INSERT INTO `bus_order_record` VALUES ('0', 51, '0', '接单', '测试安防甲', '', '2023-06-13 10:18:50');
INSERT INTO `bus_order_record` VALUES ('0', 51, '1', '开始执行', '测试安防甲', '', '2023-06-13 10:18:53');
INSERT INTO `bus_order_record` VALUES ('0', 51, '0', '工单协助', '测试安防甲', '测试安防乙', '2023-06-13 10:23:38');
INSERT INTO `bus_order_record` VALUES ('0', 52, '0', '接单', '测试安防甲', '', '2023-06-13 15:59:26');
INSERT INTO `bus_order_record` VALUES ('0', 52, '1', '开始执行', '测试安防甲', '', '2023-06-13 15:59:38');
INSERT INTO `bus_order_record` VALUES ('1', 46, '0', '自动分配', '', '', '2023-06-13 16:03:22');
INSERT INTO `bus_order_record` VALUES ('1', 46, '0', '接单', '测试安防甲', '', '2023-06-13 16:05:57');
INSERT INTO `bus_order_record` VALUES ('1', 46, '1', '开始执行', '测试安防甲', '', '2023-06-13 16:06:23');
INSERT INTO `bus_order_record` VALUES ('2', 4, '0', '工单分配方式: 管理员分配', '', '', '2023-06-13 16:25:42');
INSERT INTO `bus_order_record` VALUES ('2', 5, '0', '接单', '测试安防甲', '', '2023-06-13 16:27:15');
INSERT INTO `bus_order_record` VALUES ('2', 5, '0', '工单转交', '测试安防甲', '测试安防乙', '2023-06-13 16:28:28');
INSERT INTO `bus_order_record` VALUES ('2', 4, '0', '工单转交', '测试安防甲', '测试安防乙', '2023-06-13 16:28:29');
INSERT INTO `bus_order_record` VALUES ('2', 5, '0', '工单转交', '测试安防乙', '测试安防甲', '2023-06-13 16:29:15');
INSERT INTO `bus_order_record` VALUES ('2', 5, '0', '工单转交', '测试安防甲', '测试安防乙', '2023-06-13 16:29:28');
INSERT INTO `bus_order_record` VALUES ('2', 5, '0', '工单转交', '测试安防乙', '测试安防甲', '2023-06-13 16:29:40');
INSERT INTO `bus_order_record` VALUES ('2', 5, '0', '工单转交', '测试安防甲', '测试安防乙', '2023-06-13 16:33:23');
INSERT INTO `bus_order_record` VALUES ('1', 47, '0', '自动分配', '', '', '2023-06-13 16:43:15');
INSERT INTO `bus_order_record` VALUES ('8', 6, '0', '分配方式：管理员分配', '15288888888', '', '2023-06-13 17:36:13');
INSERT INTO `bus_order_record` VALUES ('8', 6, '0', '接单', '测试安防甲', '', '2023-06-13 17:36:45');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '冯桂花', '', '2023-06-14 23:59:36');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '时玉莲', '', '2023-06-15 23:59:35');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈银连', '', '2023-06-16 23:59:35');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈先荣', '', '2023-06-17 23:59:34');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '王维春', '', '2023-06-18 23:59:34');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '汪春子', '', '2023-06-19 23:59:33');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '王维春', '', '2023-06-21 23:59:32');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '冯桂花', '', '2023-06-22 23:59:32');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '刘贵珍', '', '2023-06-23 23:59:31');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '王维春', '', '2023-06-24 23:59:31');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '汪春子', '', '2023-06-25 23:59:30');
INSERT INTO `bus_order_record` VALUES ('0', 79, '0', '工单分配:管理员分配', '18888888888', '', '2023-06-26 16:59:57');
INSERT INTO `bus_order_record` VALUES ('0', 79, '0', '接单', '秩序督察员', '', '2023-06-26 17:03:06');
INSERT INTO `bus_order_record` VALUES ('0', 79, '1', '开始执行', '秩序督察员', '', '2023-06-26 17:03:54');
INSERT INTO `bus_order_record` VALUES ('2', 6, '0', '工单分配方式: 管理员分配', '', '', '2023-06-26 17:28:46');
INSERT INTO `bus_order_record` VALUES ('2', 6, '0', '接单', '工程督察员', '', '2023-06-26 17:30:45');
INSERT INTO `bus_order_record` VALUES ('2', 6, '1', '开始执行', '工程督察员', '', '2023-06-26 17:31:19');
INSERT INTO `bus_order_record` VALUES ('3', 14, '0', '工单分配', '保洁督察员', '', '2023-06-27 08:52:07');
INSERT INTO `bus_order_record` VALUES ('3', 14, '0', '接单', '保洁督察员', '', '2023-06-27 08:52:57');
INSERT INTO `bus_order_record` VALUES ('3', 14, '1', '开始执行', '保洁督察员', '', '2023-06-27 08:53:07');
INSERT INTO `bus_order_record` VALUES ('3', 14, '1', '完成工单', '保洁督察员', '', '2023-06-27 08:53:49');
INSERT INTO `bus_order_record` VALUES ('6', 4, '0', '接单', '工程督察员', '', '2023-06-27 09:14:15');
INSERT INTO `bus_order_record` VALUES ('6', 4, '1', '开始执行', '工程督察员', '', '2023-06-27 09:14:20');
INSERT INTO `bus_order_record` VALUES ('6', 4, '1', '完成工单', '工程督察员', '', '2023-06-27 09:16:21');
INSERT INTO `bus_order_record` VALUES ('7', 24, '1', '分配方式：管理员分配', '18888888888', '', '2023-06-27 11:04:57');
INSERT INTO `bus_order_record` VALUES ('7', 24, '0', '接单', '秩序督察员', '', '2023-06-27 11:06:15');
INSERT INTO `bus_order_record` VALUES ('7', 24, '1', '开始执行', '秩序督察员', '', '2023-06-27 11:06:22');
INSERT INTO `bus_order_record` VALUES ('7', 24, '1', '完成工单', '秩序督察员', '', '2023-06-27 11:07:27');
INSERT INTO `bus_order_record` VALUES ('7', 24, '0', '园区申请评价', '傅强', '', '2023-06-27 11:07:57');
INSERT INTO `bus_order_record` VALUES ('5', 14, '1', '开始执行', '工程督察员', '', '2023-06-27 14:45:13');
INSERT INTO `bus_order_record` VALUES ('5', 14, '1', '完成工单', '工程督察员', '', '2023-06-27 14:45:29');
INSERT INTO `bus_order_record` VALUES ('8', 7, '0', '分配方式：管理员分配', '18888888888', '', '2023-06-27 14:49:52');
INSERT INTO `bus_order_record` VALUES ('8', 8, '0', '分配方式：管理员分配', '18888888888', '', '2023-06-27 15:57:15');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '吴玉兰', '', '2023-06-27 23:59:29');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈先荣', '', '2023-06-28 23:59:29');
INSERT INTO `bus_order_record` VALUES ('3', 15, '0', '工单分配', '叶佳敏', '', '2023-06-29 09:41:11');
INSERT INTO `bus_order_record` VALUES ('4', 4, '0', '装修评定工单分配', '18888888888', '', '2023-06-29 10:09:04');
INSERT INTO `bus_order_record` VALUES ('4', 5, '0', '装修评定工单分配', '18888888888', '', '2023-06-29 10:15:01');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈春香', '', '2023-06-29 23:59:28');
INSERT INTO `bus_order_record` VALUES ('4', 4, '0', '接单', '装修督察员', '', '2023-06-30 10:45:14');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈银连', '', '2023-06-30 23:59:28');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈银连', '', '2023-07-01 23:59:27');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '汪春子', '', '2023-07-02 23:59:27');
INSERT INTO `bus_order_record` VALUES ('4', 6, '0', '装修评定工单分配', '18888888888', '', '2023-07-03 14:42:39');
INSERT INTO `bus_order_record` VALUES ('4', 6, '0', '接单', '装修督察员', '', '2023-07-03 14:43:01');
INSERT INTO `bus_order_record` VALUES ('4', 6, '1', '开始执行', '装修督察员', '', '2023-07-03 14:43:11');
INSERT INTO `bus_order_record` VALUES ('4', 6, '1', '完成工单', '装修督察员', '', '2023-07-03 14:43:49');
INSERT INTO `bus_order_record` VALUES ('4', 4, '0', '工单转交', '装修督察员', '秩序督察员', '2023-07-03 14:44:36');
INSERT INTO `bus_order_record` VALUES ('4', 5, '0', '工单转交', '装修督察员', '秩序督察员', '2023-07-03 14:44:39');
INSERT INTO `bus_order_record` VALUES ('8', 7, '1', '工单转交', '18888888888', '', '2023-07-03 14:45:16');
INSERT INTO `bus_order_record` VALUES ('8', 8, '1', '工单转交', '18888888888', '', '2023-07-03 14:45:17');
INSERT INTO `bus_order_record` VALUES ('8', 8, '1', '工单转交', '18888888888', '', '2023-07-03 14:45:19');
INSERT INTO `bus_order_record` VALUES ('8', 7, '1', '工单转交', '18888888888', '', '2023-07-03 14:45:21');
INSERT INTO `bus_order_record` VALUES ('3', 15, '0', '工单转交', '叶佳敏', '保洁督察员', '2023-07-03 14:47:35');
INSERT INTO `bus_order_record` VALUES ('3', 15, '0', '接单', '保洁督察员', '', '2023-07-03 14:49:33');
INSERT INTO `bus_order_record` VALUES ('3', 15, '1', '开始执行', '保洁督察员', '', '2023-07-03 14:49:38');
INSERT INTO `bus_order_record` VALUES ('3', 15, '1', '完成工单', '保洁督察员', '', '2023-07-03 14:50:05');
INSERT INTO `bus_order_record` VALUES ('4', 5, '0', '接单', '秩序督察员', '', '2023-07-03 14:51:45');
INSERT INTO `bus_order_record` VALUES ('4', 5, '1', '开始执行', '秩序督察员', '', '2023-07-03 14:51:51');
INSERT INTO `bus_order_record` VALUES ('4', 4, '0', '接单', '秩序督察员', '', '2023-07-03 14:51:59');
INSERT INTO `bus_order_record` VALUES ('4', 4, '1', '开始执行', '秩序督察员', '', '2023-07-03 14:52:03');
INSERT INTO `bus_order_record` VALUES ('4', 4, '1', '完成工单', '秩序督察员', '', '2023-07-03 14:52:32');
INSERT INTO `bus_order_record` VALUES ('4', 5, '1', '完成工单', '秩序督察员', '', '2023-07-03 14:52:59');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '谢梅花', '', '2023-07-03 23:59:26');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈先荣', '', '2023-07-04 23:59:25');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈先荣', '', '2023-07-05 23:59:25');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '冯桂花', '', '2023-07-06 23:59:24');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '时玉莲', '', '2023-07-07 23:59:24');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈先荣', '', '2023-07-08 23:59:23');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '时玉莲', '', '2023-07-09 23:59:23');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '冯桂花', '', '2023-07-10 23:59:22');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈银连', '', '2023-07-11 23:59:22');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '谢梅花', '', '2023-07-12 23:59:21');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '吴玉兰', '', '2023-07-13 23:59:21');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '刘贵珍', '', '2023-07-14 23:59:20');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈先荣', '', '2023-07-15 23:59:20');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '辛明娣', '', '2023-07-16 23:59:19');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '吴玉兰', '', '2023-07-17 23:59:19');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈银连', '', '2023-07-18 23:59:18');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '冯桂花', '', '2023-07-19 23:59:18');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '谢梅花', '', '2023-07-20 23:59:17');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '时玉莲', '', '2023-07-21 23:59:17');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '时玉莲', '', '2023-07-22 23:59:16');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '刘贵珍', '', '2023-07-23 23:59:16');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈先荣', '', '2023-07-24 23:59:15');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '刘贵珍', '', '2023-07-26 23:59:14');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '时玉莲', '', '2023-07-27 23:59:14');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '王维春', '', '2023-07-28 23:59:13');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '冯桂花', '', '2023-07-30 23:59:12');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '吴玉兰', '', '2023-07-31 23:59:11');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '辛明娣', '', '2023-08-01 23:59:11');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '时玉莲', '', '2023-08-02 23:59:10');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈春香', '', '2023-08-03 23:59:10');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '谢梅花', '', '2023-08-04 23:59:09');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '汪春子', '', '2023-08-05 23:59:09');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '刘贵珍', '', '2023-08-06 23:59:08');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈先荣', '', '2023-08-07 23:59:08');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '时玉莲', '', '2023-08-08 23:59:07');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '刘贵珍', '', '2023-08-10 23:59:06');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '王维春', '', '2023-08-11 23:59:06');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '吴玉兰', '', '2023-08-13 23:59:04');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈银连', '', '2023-08-14 23:59:04');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈先荣', '', '2023-08-15 23:59:03');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '辛明娣', '', '2023-08-16 23:59:03');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '辛明娣', '', '2023-08-17 23:59:02');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '冯桂花', '', '2023-08-18 23:59:02');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈先荣', '', '2023-08-19 23:59:01');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '时玉莲', '', '2023-08-20 23:59:01');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '谢梅花', '', '2023-08-21 23:59:00');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '刘贵珍', '', '2023-08-22 23:59:00');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈先荣', '', '2023-08-23 23:58:59');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '辛明娣', '', '2023-08-24 23:58:59');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈先荣', '', '2023-08-25 23:58:58');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '冯桂花', '', '2023-08-26 23:58:58');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '吴玉兰', '', '2023-08-27 23:58:57');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '吴玉兰', '', '2023-08-28 23:58:57');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈先荣', '', '2023-08-29 23:58:56');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈银连', '', '2023-08-30 23:58:56');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '刘贵珍', '', '2023-08-31 23:58:55');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '汪春子', '', '2023-09-01 23:58:54');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '时玉莲', '', '2023-09-02 23:58:54');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈银连', '', '2023-09-03 23:58:53');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈春香', '', '2023-09-04 23:58:53');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '汪春子', '', '2023-09-05 23:58:52');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '冯桂花', '', '2023-09-06 23:58:52');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '汪春子', '', '2023-09-07 23:58:51');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '辛明娣', '', '2023-09-08 23:58:51');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '时玉莲', '', '2023-09-10 23:58:50');
INSERT INTO `bus_order_record` VALUES ('5', 15, '1', '开始执行', '工程督察员', '', '2023-09-11 15:29:52');
INSERT INTO `bus_order_record` VALUES ('5', 15, '1', '完成工单', '工程督察员', '', '2023-09-11 15:30:16');
INSERT INTO `bus_order_record` VALUES ('0', 234, '0', '工单分配:管理员分配', '18888888888', '', '2023-09-11 16:32:41');
INSERT INTO `bus_order_record` VALUES ('0', 234, '0', '接单', '秩序督察员', '', '2023-09-11 16:33:34');
INSERT INTO `bus_order_record` VALUES ('0', 234, '1', '开始执行', '秩序督察员', '', '2023-09-11 16:33:39');
INSERT INTO `bus_order_record` VALUES ('0', 234, '1', '完成工单', '秩序督察员', '', '2023-09-11 16:33:50');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '李金花', '', '2023-09-11 23:58:49');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '冯桂花', '', '2023-09-12 23:58:49');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '王维春', '', '2023-09-13 23:58:48');
INSERT INTO `bus_order_record` VALUES ('0', 236, '0', '工单分配:管理员分配', '18888888888', '', '2023-09-14 10:41:28');
INSERT INTO `bus_order_record` VALUES ('5', 16, '1', '开始执行', '工程督察员', '', '2023-09-14 11:32:49');
INSERT INTO `bus_order_record` VALUES ('5', 16, '1', '完成工单', '工程督察员', '', '2023-09-14 11:33:53');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '谢梅花', '', '2023-09-14 23:58:48');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈先荣', '', '2023-09-15 23:58:47');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '辛明娣', '', '2023-09-16 23:58:47');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '辛明娣', '', '2023-09-17 23:58:46');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '时玉莲', '', '2023-09-18 23:58:46');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '谢梅花', '', '2023-09-19 23:58:45');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '汪春子', '', '2023-09-20 23:58:45');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈先荣', '', '2023-09-21 23:58:44');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '李金花', '', '2023-09-22 23:58:44');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '李金花', '', '2023-09-23 23:58:43');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '王维春', '', '2023-09-24 23:58:43');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '吴玉兰', '', '2023-09-25 23:58:42');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '辛明娣', '', '2023-09-26 23:58:42');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈先荣', '', '2023-09-27 23:58:41');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈先荣', '', '2023-09-28 23:58:41');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '冯桂花', '', '2023-09-29 23:58:40');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '辛明娣', '', '2023-09-30 23:58:40');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈先荣', '', '2023-10-01 23:58:39');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '王维春', '', '2023-10-03 23:58:38');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '王维春', '', '2023-10-04 23:58:38');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '李金花', '', '2023-10-05 23:58:37');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '辛明娣', '', '2023-10-06 23:58:37');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '吴玉兰', '', '2023-10-08 23:58:36');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈先荣', '', '2023-10-10 23:58:35');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '刘贵珍', '', '2023-10-11 23:58:34');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈银连', '', '2023-10-12 23:58:34');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '刘贵珍', '', '2023-10-13 23:58:33');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '李金花', '', '2023-10-14 23:58:33');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '谢梅花', '', '2023-10-15 23:58:32');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '吴玉兰', '', '2023-10-16 23:58:32');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈先荣', '', '2023-10-17 23:58:31');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '刘贵珍', '', '2023-10-18 23:58:31');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '吴玉兰', '', '2023-10-19 23:58:30');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '辛明娣', '', '2023-10-20 23:58:30');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈春香', '', '2023-10-22 23:58:29');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '辛明娣', '', '2023-10-23 23:58:28');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '辛明娣', '', '2023-10-24 23:58:28');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '汪春子', '', '2023-10-25 23:58:27');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈银连', '', '2023-10-27 23:58:26');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '时玉莲', '', '2023-10-29 23:58:25');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '陈春香', '', '2023-10-30 23:58:25');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '冯桂花', '', '2023-10-31 23:58:24');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '李金花', '', '2023-11-01 23:58:24');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '辛明娣', '', '2023-11-02 23:58:23');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '王维春', '', '2023-11-03 23:58:23');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '王维春', '', '2023-11-04 23:58:23');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '吴玉兰', '', '2023-11-05 23:58:22');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '刘贵珍', '', '2023-11-06 23:58:22');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '谢梅花', '', '2023-11-07 23:58:21');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '时玉莲', '', '2023-11-08 23:58:21');
INSERT INTO `bus_order_record` VALUES ('3', 0, '0', '工单分配', '时玉莲', '', '2023-11-09 23:58:20');

-- ----------------------------
-- Table structure for bus_order_transfer
-- ----------------------------
DROP TABLE IF EXISTS `bus_order_transfer`;
CREATE TABLE `bus_order_transfer`  (
  `transfer_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `audit_id` bigint NOT NULL DEFAULT 0 COMMENT '审核id',
  `order_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单类型：见类 OrderConstants',
  `order_id` bigint NOT NULL DEFAULT 0 COMMENT '工单id，order_type对应的表',
  `source_id` bigint NOT NULL DEFAULT 0 COMMENT '转交人id，表xccj_community.bus_staff',
  `target_id` bigint NOT NULL DEFAULT 0 COMMENT '接收人id，表xccj_community.bus_staff',
  `submit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  `accept_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '接收状态：0=待同意，1=同意，2=不同意',
  `reject_reason` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '不同意理由',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`transfer_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工单转交记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_order_transfer
-- ----------------------------
INSERT INTO `bus_order_transfer` VALUES (1, 1, '7', 1, 1, 2, '2023-05-16 10:56:30', '2', '', '2023-05-16 10:56:30', '0');
INSERT INTO `bus_order_transfer` VALUES (2, 2, '7', 1, 1, 2, '2023-05-16 11:13:05', '2', '', '2023-05-16 11:13:05', '0');
INSERT INTO `bus_order_transfer` VALUES (3, 3, '7', 1, 1, 2, '2023-05-16 11:13:59', '2', '只限你完成', '2023-05-16 11:13:59', '0');
INSERT INTO `bus_order_transfer` VALUES (4, 5, '7', 7, 1, 2, '2023-05-16 14:21:28', '0', '', '2023-05-16 14:21:28', '0');
INSERT INTO `bus_order_transfer` VALUES (5, 9, '0', 1, 1, 2, '2023-05-16 16:01:24', '2', '拒绝', '2023-05-16 16:01:24', '0');
INSERT INTO `bus_order_transfer` VALUES (6, 10, '2', 1, 1, 2, '2023-05-16 16:25:09', '2', '拒绝', '2023-05-16 16:25:09', '0');
INSERT INTO `bus_order_transfer` VALUES (7, 11, '2', 1, 1, 2, '2023-05-16 16:25:24', '1', '', '2023-05-16 16:25:24', '0');
INSERT INTO `bus_order_transfer` VALUES (8, 12, '3', 4, 9, 10, '2023-05-16 17:04:03', '1', '', '2023-05-16 17:04:03', '0');
INSERT INTO `bus_order_transfer` VALUES (9, 16, '8', 5, 1, 40, '2023-06-12 15:28:26', '0', '', '2023-06-12 15:28:26', '0');
INSERT INTO `bus_order_transfer` VALUES (10, 17, '8', 4, 40, 2, '2023-06-12 15:43:45', '0', '', '2023-06-12 15:43:45', '0');
INSERT INTO `bus_order_transfer` VALUES (11, 18, '0', 37, 40, 1, '2023-06-12 15:49:18', '1', '', '2023-06-12 15:49:18', '0');
INSERT INTO `bus_order_transfer` VALUES (12, 20, '2', 4, 1, 2, '2023-06-13 16:26:25', '1', '', '2023-06-13 16:26:25', '0');
INSERT INTO `bus_order_transfer` VALUES (13, 21, '2', 5, 1, 2, '2023-06-13 16:28:06', '1', '', '2023-06-13 16:28:06', '0');
INSERT INTO `bus_order_transfer` VALUES (14, 22, '2', 5, 2, 1, '2023-06-13 16:28:57', '1', '', '2023-06-13 16:28:57', '0');
INSERT INTO `bus_order_transfer` VALUES (15, 23, '2', 5, 1, 2, '2023-06-13 16:29:19', '1', '', '2023-06-13 16:29:19', '0');
INSERT INTO `bus_order_transfer` VALUES (16, 24, '2', 5, 2, 1, '2023-06-13 16:29:36', '1', '', '2023-06-13 16:29:36', '0');
INSERT INTO `bus_order_transfer` VALUES (17, 25, '2', 5, 1, 2, '2023-06-13 16:29:45', '1', '', '2023-06-13 16:29:45', '0');
INSERT INTO `bus_order_transfer` VALUES (18, 26, '2', 4, 2, 1, '2023-06-13 16:30:11', '2', '拒绝', '2023-06-13 16:30:11', '0');
INSERT INTO `bus_order_transfer` VALUES (19, 27, '0', 3, 2, 1, '2023-06-13 16:30:30', '2', '拒绝', '2023-06-13 16:30:30', '0');
INSERT INTO `bus_order_transfer` VALUES (20, 28, '2', 5, 2, 1, '2023-06-13 16:34:39', '2', '拒绝', '2023-06-13 16:34:39', '0');
INSERT INTO `bus_order_transfer` VALUES (21, 29, '2', 5, 2, 1, '2023-06-13 16:35:47', '2', '不允许转交', '2023-06-13 16:35:47', '0');
INSERT INTO `bus_order_transfer` VALUES (22, 30, '1', 47, 1, 2, '2023-06-13 16:43:32', '2', '拒绝', '2023-06-13 16:43:32', '0');
INSERT INTO `bus_order_transfer` VALUES (23, 31, '1', 47, 1, 2, '2023-06-13 16:44:57', '0', '', '2023-06-13 16:44:57', '0');
INSERT INTO `bus_order_transfer` VALUES (26, 36, '8', 8, 50, 48, '2023-06-29 09:35:51', '0', '', '2023-06-29 09:35:51', '0');
INSERT INTO `bus_order_transfer` VALUES (27, 37, '8', 7, 48, 47, '2023-06-29 09:38:01', '0', '', '2023-06-29 09:38:01', '0');
INSERT INTO `bus_order_transfer` VALUES (28, 38, '3', 15, 47, 50, '2023-06-29 09:41:24', '1', '', '2023-06-29 09:41:24', '0');
INSERT INTO `bus_order_transfer` VALUES (29, 39, '4', 5, 51, 48, '2023-06-30 10:45:08', '1', '', '2023-06-30 10:45:08', '0');
INSERT INTO `bus_order_transfer` VALUES (30, 40, '4', 4, 51, 48, '2023-06-30 10:45:27', '1', '', '2023-06-30 10:45:27', '0');

-- ----------------------------
-- Table structure for bus_park_audit
-- ----------------------------
DROP TABLE IF EXISTS `bus_park_audit`;
CREATE TABLE `bus_park_audit`  (
  `audit_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `audit_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '申请名称',
  `owner_id` bigint NOT NULL DEFAULT 0 COMMENT '业主id，表xccj_community.bus_owner',
  `repair_id` bigint NOT NULL DEFAULT 0 COMMENT '园区报事类型id，表xccj_work.bus_park_repair',
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '地址',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `audit_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '审核状态：0=审核中，1=通过，2=驳回',
  `audit_time` datetime NULL DEFAULT NULL COMMENT '审核时间',
  `reject_reason` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '驳回理由',
  `audit_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '审批人',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`audit_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '园区报事申请表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_park_audit
-- ----------------------------
INSERT INTO `bus_park_audit` VALUES (1, 1, '111', 12, 1, '222', '333', '0', NULL, '', '', '0');
INSERT INTO `bus_park_audit` VALUES (2, 1, '园区报事01', 11, 1, '园区报事01地址', '园区报事01备注', '1', '2023-05-15 20:03:37', '园区报事01备注', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (3, 1, '测试园区驳回', 11, 1, '园区报事地址', '园区报事备注', '2', '2023-05-15 20:05:38', '测试驳回理由', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (4, 1, '园区报事02', 11, 2, '园区02报事地址', '园区02 备注', '1', '2023-05-16 14:04:04', '园区02 备注', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (5, 1, '03园区报事', 11, 3, '园区地址', '03园区地址', '1', '2023-05-16 14:14:51', '03园区地址', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (6, 1, '04园区报事', 11, 1, '04园区报事地址', '04园区报事备注', '1', '2023-05-16 14:18:03', '04园区报事备注', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (7, 1, '05园区报事', 11, 1, '05园区报事', '05园区报事备注', '1', '2023-05-16 14:20:03', '05园区报事备注', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (8, 1, '06园区报事', 11, 1, '06园区报事', '06园区报事备注', '1', '2023-05-16 14:20:01', '06园区报事备注', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (9, 1, '07园区报事', 11, 1, '07园区报事地址', '07园区报事备注', '1', '2023-05-16 14:20:00', '07园区报事备注', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (10, 1, '08园区工单', 11, 1, '08园区工单地址', '08园区工单备注', '1', '2023-05-16 14:22:07', '08园区工单备注', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (11, 1, '抢单园区01', 11, 1, '抢单地址', '抢单备注', '1', '2023-05-16 14:29:24', '抢单备注', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (12, 1, '待分配抢单工单', 11, 1, '抢单地址', '抢单备注', '1', '2023-05-17 14:03:33', '抢单备注', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (13, 1, '长期执行中园区工单', 11, 1, '园区地址', '园区备注', '1', '2023-05-17 14:03:32', '园区备注', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (14, 1, '园区杏芳园一号道路损坏', 11, 1, '银城龙湖社区-Y-1#-1单元道路', '园区道路损坏', '1', '2023-05-23 15:50:51', '园区道路损坏', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (15, 1, '道路垃圾堆积', 11, 1, '园区01号路线', '垃圾堆积', '1', '2023-05-20 10:48:20', '垃圾堆积', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (16, 1, '12', 11, 1, '12', '12', '0', NULL, '', '', '0');
INSERT INTO `bus_park_audit` VALUES (17, 1, '12', 11, 1, '1', '1', '1', '2023-05-22 17:03:59', '1', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (18, 1, '园区路边不整洁', 11, 2, '园区19栋', '及时清扫', '1', '2023-05-22 17:12:23', '及时清扫', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (19, 1, '园区路边积水', 11, 1, '地址', '路边积水', '1', '2023-05-22 17:15:36', '路边积水', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (20, 1, '一栋旁水管破损', 29, 1, '一栋一单元附件', '一栋旁水管破损', '1', '2023-05-23 15:34:44', '一栋旁水管破损', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (21, 1, '小区晚上楼下有人打麻将很吵', 29, 1, '二栋一楼', '小区晚上楼下有人打麻将很吵', '1', '2023-05-23 15:49:14', '小区晚上楼下有人打麻将很吵', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (22, 1, '小区停车位都被电动车占了', 29, 1, '停车库', '小区停车位都被电动车占了', '1', '2023-05-23 15:53:32', '小区停车位都被电动车占了', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (23, 1, '楼道垃圾', 11, 1, '园区01号道路', '道路垃圾', '1', '2023-05-23 16:57:44', '道路垃圾', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (24, 1, '5-23园区报事工', 11, 1, '园区', '5-23园区报事工', '1', '2023-05-23 18:24:43', '5-23园区报事工', '15288888888', '0');
INSERT INTO `bus_park_audit` VALUES (25, 1, '园区道路垃圾堆积', 29, 1, '园区001号道路', '道路垃圾', '0', NULL, '', '', '0');
INSERT INTO `bus_park_audit` VALUES (26, 6, '园区环境不整洁', 11, 1, '园区地址详情', '园区备注详情', '2', '2023-06-09 14:21:53', '不符合申请要求', '15656332018', '0');
INSERT INTO `bus_park_audit` VALUES (27, 6, '园区卫生标准不合格', 11, 1, '园区19栋楼下', '及时清理', '1', '2023-06-09 14:20:58', '及时清理', '15656332018', '0');
INSERT INTO `bus_park_audit` VALUES (28, 5, '报事名称', 64, 12, '测试地址', '备注', '1', '2023-06-09 16:37:25', '备注', '15200000000', '0');
INSERT INTO `bus_park_audit` VALUES (29, 1, '园区报事名称6－10', 11, 1, '6.10地址', '6.10备注', '0', NULL, '', '', '0');
INSERT INTO `bus_park_audit` VALUES (30, 1, '613园区工单', 11, 1, '地址1111', '备注111', '0', NULL, '', '', '0');
INSERT INTO `bus_park_audit` VALUES (31, 7, 'Y1# 西电梯内有人推电瓶车', 74, 14, 'Y1#西电梯', '请物业尽快处理', '1', '2023-06-27 11:04:32', '请物业尽快处理', '18888888888', '0');
INSERT INTO `bus_park_audit` VALUES (32, 7, 'G7地下室报修', 0, 16, 'null', '地下停车库电瓶车充电地方墙面大面积水迹，电梯间门口瓷砖破损', '1', '2023-09-22 16:55:17', '', '18888888888', '0');
INSERT INTO `bus_park_audit` VALUES (33, 7, '车位车牌号修改', 0, 17, 'null', '413号车位的车牌更改成皖P61AC1', '1', '2023-09-22 16:59:19', '', '18888888888', '0');
INSERT INTO `bus_park_audit` VALUES (34, 5, '测试', 206, 13, '测试', '无\n', '1', '2023-10-28 09:47:46', '无\n', '15555555555', '0');

-- ----------------------------
-- Table structure for bus_park_order
-- ----------------------------
DROP TABLE IF EXISTS `bus_park_order`;
CREATE TABLE `bus_park_order`  (
  `order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `audit_id` bigint NOT NULL DEFAULT 0 COMMENT '园区报事申请id，表xccj_work.bus_park_audit',
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '工单编号',
  `order_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '工单名称',
  `plan_start` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '计划开始时间',
  `plan_end` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '计划结束时间',
  `allot_way` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单分配方式：0=自动分配，1=管理员分配，2=抢单',
  `staff_id` bigint NOT NULL DEFAULT 0 COMMENT '负责人id，表xccj_community.bus_staff',
  `assist_num` int NOT NULL DEFAULT 0 COMMENT '协助人数',
  `transfer_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '转交状态：0=无需转交，1=转交待确认',
  `allot_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '分配状态：0=待分配，1=已分配',
  `accept_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '接单状态：0=待接单，1=已接单',
  `execute_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '执行状态：0=待执行，1=执行中，2=已完成，3=超时完成，4=超时未完成',
  `assess_result` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '评定结果：0=合格，1=违规，2=整改完成',
  `execute_id` bigint NOT NULL DEFAULT 0 COMMENT '执行人id，表xccj_community.bus_staff',
  `execute_time` datetime NULL DEFAULT NULL COMMENT '执行时间',
  `finish_time` datetime NULL DEFAULT NULL COMMENT '完成时间',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '完成图片',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `appraise_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '评价状态：0=未评价，1=已评价',
  `appraise_star` int NOT NULL DEFAULT 0 COMMENT '评价星级：0~5',
  `appraise_remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '评价备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '园区报事工单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_park_order
-- ----------------------------
INSERT INTO `bus_park_order` VALUES (1, 1, 2, 'YQ-20230515-85517', '园区报事01', '2023-05-15 00:00:00', '2023-05-17 00:00:00', '0', 1, 0, '0', '1', '1', '3', '0', 1, '2023-05-16 11:33:28', '2023-05-16 11:55:45', 'http://file.szcjzhxq.com/statics/2023/05/16/b - 副本_20230516115537A239.jpg', '描述', '1', 0, '无星差评', '15288888888', '2023-05-15 20:03:37', '业主戊', '2023-05-16 14:26:15', '0');
INSERT INTO `bus_park_order` VALUES (2, 1, 4, 'YQ-20230516-51890', '园区报事02', '2023-05-16 00:00:00', '2023-05-17 00:00:00', '0', 1, 0, '0', '1', '1', '2', '0', 1, '2023-05-16 14:05:07', '2023-05-16 14:05:29', '', '02执行完成', '1', 5, '完成信息及评价都不传图片测试', '15288888888', '2023-05-16 14:04:04', '业主戊', '2023-05-16 14:37:14', '0');
INSERT INTO `bus_park_order` VALUES (3, 1, 5, 'YQ-20230516-312929', '03园区报事', '2023-05-16 00:00:00', '2023-05-16 01:00:00', '0', 1, 0, '0', '1', '1', '1', '0', 0, '2023-05-16 14:17:05', NULL, '', '03园区地址', '0', 0, '', '15288888888', '2023-05-16 14:14:51', '15288888888', '2023-05-16 14:16:50', '0');
INSERT INTO `bus_park_order` VALUES (4, 1, 6, 'YQ-20230516-409513', '04园区报事', '2023-05-16 00:00:00', '2024-05-01 00:00:00', '0', 1, 0, '0', '1', '1', '2', '0', 1, '2023-05-23 09:02:55', '2023-05-23 09:03:28', 'http://file.szcjzhxq.com/statics/2023/05/23/chuanggaun_20230523090323A731.png', '04园区报事执行描述', '1', 0, '敷衍', '15288888888', '2023-05-16 14:18:03', '业主戊', '2023-05-23 09:12:32', '0');
INSERT INTO `bus_park_order` VALUES (5, 1, 9, 'YQ-20230516-825719', '07园区报事', '2023-05-16 00:00:00', '2023-05-17 00:00:00', '0', 1, 0, '0', '1', '1', '1', '0', 0, '2023-05-16 14:20:40', NULL, '', '07园区报事备注', '0', 0, '', '15288888888', '2023-05-16 14:20:00', '15288888888', '2023-05-16 14:20:27', '0');
INSERT INTO `bus_park_order` VALUES (6, 1, 8, 'YQ-20230516-841349', '06园区报事', '2023-05-16 00:00:00', '2023-05-17 00:00:00', '0', 1, 0, '0', '1', '1', '1', '0', 0, '2023-05-17 13:56:20', NULL, '', '06园区报事备注', '0', 0, '', '15288888888', '2023-05-16 14:20:01', '15288888888', '2023-05-16 14:20:20', '0');
INSERT INTO `bus_park_order` VALUES (7, 1, 7, 'YQ-20230516-113534', '05园区报事', '2023-05-16 00:00:00', '2023-05-17 00:00:00', '0', 1, 0, '1', '1', '1', '0', '0', 0, NULL, NULL, '', '05园区报事备注', '0', 0, '', '15288888888', '2023-05-16 14:20:03', '15288888888', '2023-05-16 14:20:13', '0');
INSERT INTO `bus_park_order` VALUES (8, 1, 10, 'YQ-20230516-157888', '08园区工单', '2023-05-16 00:00:00', '2023-05-17 00:00:00', '0', 1, 0, '0', '1', '1', '2', '0', 1, '2023-05-16 14:22:37', '2023-05-16 14:24:14', 'http://file.szcjzhxq.com/statics/2023/05/16/脆皮_20230516142405A252.jpg', '脆皮五花图片备注', '1', 5, '五星好评对应08工单，五花执行图片。评价图片二维码。防ID错乱。', '15288888888', '2023-05-16 14:22:07', '业主戊', '2023-05-16 14:25:23', '0');
INSERT INTO `bus_park_order` VALUES (9, 1, 11, 'YQ-20230516-861258', '勿抢，勿抢，测试单，', '2022-04-05 00:00:00', '2022-05-02 00:00:00', '2', 1, 0, '0', '1', '1', '0', '0', 0, NULL, NULL, '', '抢单备注', '0', 0, '', '15288888888', '2023-05-16 14:29:24', '15288888888', '2023-05-17 11:51:46', '0');
INSERT INTO `bus_park_order` VALUES (10, 1, 13, 'YQ-20230517-362355', '长期执行中园区工单', '2023-05-17 00:00:00', '2023-07-17 00:00:00', '2', 1, 0, '0', '1', '1', '2', '0', 1, '2023-05-23 09:18:18', '2023-05-23 09:18:45', 'http://file.szcjzhxq.com/statics/2023/05/23/chuanggaun_20230523091842A735.png', '执行描述备注0523', '1', 4, '基本满意，处理速度再快点就更好了', '15288888888', '2023-05-17 14:03:32', '业主戊', '2023-05-23 09:23:20', '0');
INSERT INTO `bus_park_order` VALUES (11, 1, 12, 'YQ-20230517-452806', '待分配抢单工单', '2023-05-17 14:03:33', '2023-05-18 14:03:46', '2', 1, 0, '0', '1', '1', '0', '0', 0, NULL, NULL, '', '抢单备注', '0', 0, '', '15288888888', '2023-05-17 14:03:33', '15288888888', '2023-05-17 14:03:33', '0');
INSERT INTO `bus_park_order` VALUES (12, 1, 15, 'YQ-20230520-463218', '道路垃圾堆积', '2023-05-20 10:48:20', '2023-05-21 10:48:34', '2', 1, 0, '0', '1', '1', '2', '0', 1, '2023-05-20 10:50:52', '2023-05-20 10:51:55', 'http://file.szcjzhxq.com/statics/2023/05/20/社区_20230520105151A619.png', '已清理', '1', 5, '很不满意', '15288888888', '2023-05-20 10:48:20', '业主戊', '2023-05-20 10:53:39', '0');
INSERT INTO `bus_park_order` VALUES (13, 1, 17, 'YQ-20230522-26735', '12', '2023-05-22 17:03:59', '2023-05-23 17:04:13', '2', 1, 0, '0', '1', '1', '2', '0', 1, '2023-05-22 17:07:32', '2023-05-22 17:07:51', 'http://file.szcjzhxq.com/statics/2023/05/22/社区_20230522170741A720.png', '执行完成', '1', 5, '清理不干净', '15288888888', '2023-05-22 17:03:59', '业主戊', '2023-05-22 17:08:22', '0');
INSERT INTO `bus_park_order` VALUES (14, 1, 18, 'YQ-20230522-553615', '园区路边不整洁', '2023-05-22 00:00:00', '2023-05-22 23:00:00', '2', 1, 0, '0', '1', '1', '2', '0', 1, '2023-05-22 17:13:19', '2023-05-22 17:13:51', 'http://file.szcjzhxq.com/statics/2023/05/22/房间内图_20230522171348A724.jpg', '执行描述', '1', 5, '投诉', '15288888888', '2023-05-22 17:12:23', '业主戊', '2023-05-22 17:14:37', '0');
INSERT INTO `bus_park_order` VALUES (15, 1, 19, 'YQ-20230522-860756', '园区路边积水', '2023-05-22 00:00:00', '2023-05-22 23:00:00', '2', 1, 0, '0', '1', '1', '2', '0', 1, '2023-05-22 17:17:24', '2023-05-22 17:17:39', 'http://file.szcjzhxq.com/statics/2023/05/22/默认_20230522171736A727.jpg', '评价执行', '1', 1, '评价', '15288888888', '2023-05-22 17:15:36', '业主戊', '2023-05-22 17:18:03', '0');
INSERT INTO `bus_park_order` VALUES (16, 1, 20, 'YQ-20230523-636268', '一栋旁水管破损', '2023-05-23 15:34:44', '2023-05-24 15:34:59', '2', 1, 0, '0', '1', '1', '1', '0', 0, '2023-06-13 09:23:14', NULL, '', '一栋旁水管破损', '0', 0, '', '15288888888', '2023-05-23 15:34:44', '15288888888', '2023-05-23 15:34:44', '0');
INSERT INTO `bus_park_order` VALUES (17, 1, 21, 'YQ-20230523-615891', '小区晚上楼下有人打麻将很吵', '2023-05-23 15:49:14', '2023-05-24 15:49:28', '2', 1, 0, '0', '1', '1', '1', '0', 0, '2023-06-13 09:22:47', NULL, '', '小区晚上楼下有人打麻将很吵', '0', 0, '', '15288888888', '2023-05-23 15:49:14', '15288888888', '2023-05-23 15:49:14', '0');
INSERT INTO `bus_park_order` VALUES (18, 1, 14, 'YQ-20230523-45876', '园区杏芳园一号道路损坏', '2023-05-23 15:50:51', '2023-05-24 15:51:06', '2', 1, 0, '0', '1', '1', '1', '0', 0, '2023-06-13 09:22:35', NULL, '', '园区道路损坏', '0', 0, '', '15288888888', '2023-05-23 15:50:51', '15288888888', '2023-05-23 15:50:51', '0');
INSERT INTO `bus_park_order` VALUES (19, 1, 22, 'YQ-20230523-881997', '小区停车位都被电动车占了', '2023-05-23 15:53:32', '2023-05-24 15:53:47', '2', 40, 0, '0', '1', '1', '0', '0', 0, NULL, NULL, '', '小区停车位都被电动车占了', '0', 0, '', '15288888888', '2023-05-23 15:53:32', '15288888888', '2023-05-23 15:53:32', '0');
INSERT INTO `bus_park_order` VALUES (20, 1, 23, 'YQ-20230523-477640', '楼道垃圾', '2023-05-23 16:57:44', '2023-05-24 16:57:59', '2', 1, 0, '0', '1', '1', '2', '0', 1, '2023-05-23 16:58:34', '2023-05-23 16:58:59', 'http://file.szcjzhxq.com/statics/2023/05/23/社区_20230523165855A799.png', '清理完成', '1', 0, '不满意', '15288888888', '2023-05-23 16:57:44', '业主戊', '2023-05-23 16:59:29', '0');
INSERT INTO `bus_park_order` VALUES (21, 1, 24, 'YQ-20230523-939650', '5-23园区报事工', '2023-05-23 18:24:43', '2023-05-24 18:24:57', '2', 1, 0, '0', '1', '1', '3', '0', 1, '2023-06-06 15:16:17', '2023-06-07 16:14:55', 'http://file.szcjzhxq.com/statics/2023/06/07/工具_20230607161453A115.png', '执行完成', '1', 5, '可以', '15288888888', '2023-05-23 18:24:43', '业主戊', '2023-06-09 11:22:23', '0');
INSERT INTO `bus_park_order` VALUES (22, 6, 27, 'YQ-20230609-559963', '园区卫生标准不合格', '2023-06-09 14:20:58', '2023-06-10 14:21:21', '0', 18, 0, '0', '1', '0', '0', '0', 0, NULL, NULL, '', '及时清理', '0', 0, '', '15656332018', '2023-06-09 14:20:58', '15656332018', '2023-06-09 14:20:58', '0');
INSERT INTO `bus_park_order` VALUES (23, 5, 28, 'YQ-20230609-768128', '报事名称', '2023-06-09 16:37:25', '2023-06-10 16:37:48', '0', 41, 0, '0', '1', '1', '2', '0', 41, '2023-06-09 16:42:08', '2023-06-09 16:42:42', 'http://file.szcjzhxq.com/statics/2023/06/09/61874bdf3c2654991504c4b22fea5da1_20230609164233A194.jpg', '员工执行完成的描述', '1', 0, '态度不好', '15200000000', '2023-06-09 16:37:25', '陈先生', '2023-06-09 16:43:49', '0');
INSERT INTO `bus_park_order` VALUES (24, 7, 31, 'YQ-20230627-788490', 'Y1# 西电梯内有人推电瓶车', '2023-06-27 00:00:00', '2023-06-28 00:00:00', '1', 48, 0, '0', '1', '1', '2', '0', 48, '2023-06-27 11:06:22', '2023-06-27 11:07:27', 'http://file.szcjzhxq.com/statics/2023/06/27/女_20230627110723A381.png', '已经处理，劝阻业主', '1', 5, '处理的很及时', '18888888888', '2023-06-27 11:04:32', '傅强', '2023-06-27 11:07:57', '0');
INSERT INTO `bus_park_order` VALUES (25, 7, 32, 'YQ-20230922-168744', 'G7地下室报修', '2023-08-14 00:00:00', '2023-09-01 00:00:00', '1', 0, 0, '0', '0', '0', '0', '0', 0, NULL, NULL, '', '地下停车库电瓶车充电地方墙面大面积水迹，电梯间门口瓷砖破损', '0', 0, '', '18888888888', '2023-09-22 16:55:17', '18888888888', '2023-09-22 16:55:17', '0');
INSERT INTO `bus_park_order` VALUES (26, 7, 33, 'YQ-20230922-650965', '车位车牌号修改', '2023-08-14 00:00:00', '2023-08-15 00:00:00', '1', 0, 0, '0', '0', '0', '0', '0', 0, NULL, NULL, '', '413号车位的车牌更改成皖P61AC1', '0', 0, '', '18888888888', '2023-09-22 16:59:19', '18888888888', '2023-09-22 16:59:19', '0');
INSERT INTO `bus_park_order` VALUES (27, 5, 34, 'YQ-20231028-343888', '测试', '2023-10-28 09:47:46', '2023-10-29 09:49:21', '0', 57, 0, '0', '1', '0', '0', '0', 0, NULL, NULL, '', '无\n', '0', 0, '', '15555555555', '2023-10-28 09:47:46', '15555555555', '2023-10-28 09:47:46', '0');

-- ----------------------------
-- Table structure for bus_park_repair
-- ----------------------------
DROP TABLE IF EXISTS `bus_park_repair`;
CREATE TABLE `bus_park_repair`  (
  `repair_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `estate_id` bigint NOT NULL DEFAULT 0 COMMENT '物业id，表xccj_estate_sys.bus_estate',
  `repair_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '维修工种名称',
  `repair_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '状态：0=启用，1=停用',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '说明',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（发布时间）',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`repair_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '园区报事类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_park_repair
-- ----------------------------
INSERT INTO `bus_park_repair` VALUES (1, 2, '园区报事类型01', '0', '园区报事类型01', '15288888888', '2023-05-15 18:00:50', '15288888888', '2023-05-15 18:00:50', '0');
INSERT INTO `bus_park_repair` VALUES (2, 2, '园区报事类型02', '0', '园区报事类型02', '15288888888', '2023-05-15 18:02:20', '15288888888', '2023-05-15 18:02:20', '0');
INSERT INTO `bus_park_repair` VALUES (3, 2, '园区报事类型03', '0', '园区报事类型03', '15288888888', '2023-05-15 18:02:25', '15288888888', '2023-05-15 18:02:25', '0');
INSERT INTO `bus_park_repair` VALUES (4, 1, '园区报事01', '0', '园区报事01', '15288888888', '2023-05-16 11:00:16', '15288888888', '2023-05-16 12:48:10', '0');
INSERT INTO `bus_park_repair` VALUES (5, 1, '报事类型01', '0', '报事类型01', '15288888888', '2023-05-16 11:03:13', '15288888888', '2023-05-16 11:03:13', '0');
INSERT INTO `bus_park_repair` VALUES (6, 1, '报事类型02', '0', '报事类型02', '15288888888', '2023-05-16 11:03:18', '15288888888', '2023-05-16 11:03:18', '0');
INSERT INTO `bus_park_repair` VALUES (7, 1, '园区报事02', '0', '园区报事02', '15288888888', '2023-05-16 12:47:46', '15288888888', '2023-05-16 12:48:03', '0');
INSERT INTO `bus_park_repair` VALUES (8, 2, '园区报事01', '0', '园区报事01', '15288888888', '2023-05-16 13:46:26', '15288888888', '2023-05-16 13:46:26', '1');
INSERT INTO `bus_park_repair` VALUES (9, 2, '报事类型01', '0', '报事类型01', '15288888888', '2023-05-16 13:46:40', '15288888888', '2023-05-16 13:46:40', '1');
INSERT INTO `bus_park_repair` VALUES (10, 5, '违法事件', '0', '违法事件', '15656332018', '2023-06-09 10:50:42', '15656332018', '2023-06-09 10:50:42', '0');
INSERT INTO `bus_park_repair` VALUES (11, 5, '违规事件', '0', '违规事件', '15656332018', '2023-06-09 10:51:07', '15656332018', '2023-06-09 10:51:07', '0');
INSERT INTO `bus_park_repair` VALUES (12, 4, '报事类型1', '0', '报事类型1', '15200000000', '2023-06-09 16:33:48', '15200000000', '2023-06-09 16:33:48', '0');
INSERT INTO `bus_park_repair` VALUES (13, 4, '报事类型2', '0', '报事类型2', '15200000000', '2023-06-09 16:33:51', '15200000000', '2023-06-09 16:33:51', '0');
INSERT INTO `bus_park_repair` VALUES (14, 6, '违规事件', '0', '园区违规行为；如电梯内进入电瓶车、违规遛狗等。', '18888888888', '2023-06-27 09:30:15', '18888888888', '2023-06-27 09:30:15', '0');
INSERT INTO `bus_park_repair` VALUES (15, 6, '违法事件', '0', '园区内违法行为，如：私自拆卸园区内设备、破坏公共物品等。', '18888888888', '2023-06-27 09:30:59', '18888888888', '2023-06-27 09:30:59', '0');
INSERT INTO `bus_park_repair` VALUES (16, 6, '维修事件', '0', '记录园区需要维修事件', '18888888888', '2023-09-22 16:53:48', '18888888888', '2023-09-22 16:53:48', '0');
INSERT INTO `bus_park_repair` VALUES (17, 6, '业主报事', '0', '记录业主反馈情况.', '18888888888', '2023-09-22 16:58:26', '18888888888', '2023-09-22 16:58:26', '0');

-- ----------------------------
-- Table structure for bus_patrol
-- ----------------------------
DROP TABLE IF EXISTS `bus_patrol`;
CREATE TABLE `bus_patrol`  (
  `patrol_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `room_id` bigint NULL DEFAULT 0 COMMENT '房间id',
  `patrol_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '工单编号',
  `patrol_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '工单名称',
  `tour_id` bigint NOT NULL DEFAULT 0 COMMENT '巡查类型id，表xccj_work.bus_tour（tour_type=0）',
  `point_id` bigint NOT NULL DEFAULT 0 COMMENT '巡查点id，表xccj_work.bus_point（point_type!=2）',
  `data_source` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单来源：0=计划生成，1=后台添加，2=其他工单',
  `plan_id` bigint NOT NULL DEFAULT 0 COMMENT '计划id，表xccj_work.bus_patrol_plan（data_source=0有值）',
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '其他工单编号（data_source=2有值）',
  `allot_way` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单分配方式：0=自动分配，1=管理员分配，2=抢单',
  `patrol_num` int NOT NULL DEFAULT 0 COMMENT '巡查次数/天次',
  `gap_time` decimal(10, 2) NOT NULL COMMENT '每次巡查间隔时间（一日多查时有值）',
  `start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
  `staff_id` bigint NOT NULL DEFAULT 0 COMMENT '负责人id，表xccj_community.bus_staff',
  `assist_num` int NOT NULL DEFAULT 0 COMMENT '协助人数',
  `transfer_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '转交状态：0=无需转交，1=转交待确认',
  `allot_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '分配状态：0=待分配，1=已分配',
  `accept_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '接单状态：0=待接单，1=已接单',
  `execute_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '执行状态：0=待执行，1=执行中，2=已完成，3=超时完成，4=超时未完成',
  `execute_id` bigint NOT NULL DEFAULT 0 COMMENT '执行人id，表xccj_community.bus_staff',
  `execute_time` datetime NULL DEFAULT NULL COMMENT '执行时间',
  `finish_time` datetime NULL DEFAULT NULL COMMENT '完成时间',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '完成图片',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`patrol_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 357 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡查工单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_patrol
-- ----------------------------
INSERT INTO `bus_patrol` VALUES (1, 1, 0, 'XC-20230515-5917', '手动新增巡查工单', 1, 1, '1', 0, '', '0', 0, 1.00, '2023-05-15 17:00:00', '2023-05-15 18:00:00', 1, 0, '0', '1', '1', '1', 0, '2023-05-16 16:01:58', NULL, '', '后台手动新增的工单', '15288888888', '2023-05-15 18:48:39', '15288888888', '2023-05-15 18:48:39', '0');
INSERT INTO `bus_patrol` VALUES (2, 1, 0, 'XC-20230515-805005', '巡查工单02', 2, 5, '1', 0, '', '2', 0, 1.00, '2023-05-15 00:00:00', '2023-05-15 10:00:00', 5, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '无', '15288888888', '2023-05-15 18:53:20', '15288888888', '2023-05-15 18:53:20', '1');
INSERT INTO `bus_patrol` VALUES (3, 1, 0, 'XC-20230515-206885', '巡查工单03', 3, 5, '1', 0, '', '2', 0, 1.00, '2023-05-15 00:00:00', '2023-05-15 11:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '无备注信息', '15288888888', '2023-05-15 18:54:45', '15288888888', '2023-05-15 18:54:45', '0');
INSERT INTO `bus_patrol` VALUES (4, 1, 0, 'XC-20230516-251565', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-05-16 10:00:00', '2023-05-16 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-15 23:59:50', '', '2023-05-15 23:59:50', '1');
INSERT INTO `bus_patrol` VALUES (5, 1, 0, 'XC-20230517-462747', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-05-17 10:00:00', '2023-05-17 11:00:00', 1, 0, '0', '1', '1', '0', 0, NULL, NULL, '', '', '', '2023-05-16 23:59:49', '', '2023-05-16 23:59:49', '0');
INSERT INTO `bus_patrol` VALUES (6, 1, 0, 'XC-20230518-697344', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-05-18 10:00:00', '2023-05-18 11:00:00', 1, 0, '0', '1', '1', '1', 0, '2023-05-23 16:30:14', NULL, '', '', '', '2023-05-17 23:59:49', '', '2023-05-17 23:59:49', '0');
INSERT INTO `bus_patrol` VALUES (7, 1, 0, 'XC-20230519-970923', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-05-19 10:00:00', '2023-05-19 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-18 23:59:48', '', '2023-05-18 23:59:48', '1');
INSERT INTO `bus_patrol` VALUES (8, 1, 0, 'XC-20230520-588787', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-05-20 10:00:00', '2023-05-20 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-19 23:59:48', '', '2023-05-19 23:59:48', '1');
INSERT INTO `bus_patrol` VALUES (9, 1, 0, 'XC-20230520-971335', '园区池塘旁边有小孩', 3, 8, '1', 0, '', '2', 0, 1.00, '2023-05-20 00:00:00', '2023-05-24 00:00:00', 1, 0, '0', '1', '1', '2', 1, '2023-05-20 09:33:11', '2023-05-20 09:34:04', 'http://file.szcjzhxq.com:8084/statics/2023/05/20/Frame 28589_20230520093324A613.png', '已联系小孩家长，带走小孩', '15288888888', '2023-05-20 09:28:30', '15288888888', '2023-05-20 09:28:30', '0');
INSERT INTO `bus_patrol` VALUES (10, 1, 0, 'XC-20230521-145260', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-05-21 10:00:00', '2023-05-21 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-20 23:59:47', '', '2023-05-20 23:59:47', '1');
INSERT INTO `bus_patrol` VALUES (11, 1, 0, 'XC-20230522-608231', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-05-22 10:00:00', '2023-05-22 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-21 23:59:47', '', '2023-05-21 23:59:47', '1');
INSERT INTO `bus_patrol` VALUES (12, 1, 0, 'XC-20230522-960589', '1', 3, 8, '1', 0, '', '2', 0, 1.00, '2023-05-22 00:00:00', '2023-05-23 00:00:00', 1, 0, '0', '1', '1', '2', 1, '2023-05-22 16:47:38', '2023-05-22 16:48:08', 'http://file.szcjzhxq.com:8084/statics/2023/05/22/20201006080351_917e1_20230522164805A717.jpg', '维修完成', '15288888888', '2023-05-22 16:46:09', '15288888888', '2023-05-22 16:46:09', '0');
INSERT INTO `bus_patrol` VALUES (13, 1, 0, 'XC-20230523-699140', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-05-23 10:00:00', '2023-05-23 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-22 23:59:47', '', '2023-05-22 23:59:47', '1');
INSERT INTO `bus_patrol` VALUES (14, 1, 0, 'XC-20230523-1799', '2023年5月23日下午园区巡查', 3, 8, '1', 0, '', '2', 0, 1.00, '2023-05-23 00:00:00', '2023-05-24 00:00:00', 1, 0, '0', '1', '1', '2', 1, '2023-05-23 16:30:08', '2023-05-23 16:30:40', 'http://file.szcjzhxq.com:8084/statics/2023/05/23/社区_20230523163029A797.png', '巡查完成', '15288888888', '2023-05-23 16:22:59', '15288888888', '2023-05-23 16:22:59', '0');
INSERT INTO `bus_patrol` VALUES (15, 1, 0, 'XC-20230524-302794', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-05-24 10:00:00', '2023-05-24 11:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-23 23:59:46', '', '2023-05-23 23:59:46', '0');
INSERT INTO `bus_patrol` VALUES (16, 1, 0, 'XC-20230525-725447', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-05-25 10:00:00', '2023-05-25 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-24 23:59:46', '', '2023-05-24 23:59:46', '1');
INSERT INTO `bus_patrol` VALUES (17, 1, 0, 'XC-20230526-753173', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-05-26 10:00:00', '2023-05-26 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-25 23:59:45', '', '2023-05-25 23:59:45', '1');
INSERT INTO `bus_patrol` VALUES (18, 1, 0, 'XC-20230527-615017', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-05-27 10:00:00', '2023-05-27 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-26 23:59:45', '', '2023-05-26 23:59:45', '1');
INSERT INTO `bus_patrol` VALUES (19, 1, 0, 'XC-20230528-106523', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-05-28 10:00:00', '2023-05-28 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-27 23:59:44', '', '2023-05-27 23:59:44', '1');
INSERT INTO `bus_patrol` VALUES (20, 1, 0, 'XC-20230529-421747', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-05-29 10:00:00', '2023-05-29 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-28 23:59:44', '', '2023-05-28 23:59:44', '1');
INSERT INTO `bus_patrol` VALUES (21, 1, 0, 'XC-20230530-986730', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-05-30 10:00:00', '2023-05-30 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-29 23:59:43', '', '2023-05-29 23:59:43', '1');
INSERT INTO `bus_patrol` VALUES (22, 1, 0, 'XC-20230531-923130', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-05-31 10:00:00', '2023-05-31 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-30 23:59:43', '', '2023-05-30 23:59:43', '1');
INSERT INTO `bus_patrol` VALUES (23, 1, 0, 'XC-20230601-663889', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-01 10:00:00', '2023-06-01 11:00:00', 1, 0, '0', '1', '1', '0', 0, NULL, NULL, '', '', '', '2023-05-31 23:59:42', '', '2023-05-31 23:59:42', '0');
INSERT INTO `bus_patrol` VALUES (24, 6, 0, 'XC-20230601-724027', '巡查工单', 12, 12, '0', 2, '', '1', 1, 1.00, '2023-06-01 15:00:00', '2023-06-01 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-31 23:59:42', '', '2023-05-31 23:59:42', '1');
INSERT INTO `bus_patrol` VALUES (25, 1, 0, 'XC-20230602-367416', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-02 10:00:00', '2023-06-02 11:00:00', 1, 0, '0', '1', '1', '0', 0, NULL, NULL, '', '', '', '2023-06-01 23:59:42', '', '2023-06-01 23:59:42', '0');
INSERT INTO `bus_patrol` VALUES (26, 6, 0, 'XC-20230602-485690', '巡查工单', 12, 12, '0', 2, '', '1', 1, 1.00, '2023-06-02 15:00:00', '2023-06-02 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-01 23:59:42', '', '2023-06-01 23:59:42', '1');
INSERT INTO `bus_patrol` VALUES (27, 1, 0, 'XC-20230603-63303', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-03 10:00:00', '2023-06-03 11:00:00', 1, 0, '0', '1', '1', '0', 0, NULL, NULL, '', '', '', '2023-06-02 23:59:42', '', '2023-06-02 23:59:42', '0');
INSERT INTO `bus_patrol` VALUES (28, 6, 0, 'XC-20230603-978016', '巡查工单', 12, 12, '0', 2, '', '1', 1, 1.00, '2023-06-03 15:00:00', '2023-06-03 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-02 23:59:42', '', '2023-06-02 23:59:42', '1');
INSERT INTO `bus_patrol` VALUES (29, 1, 0, 'XC-20230604-649206', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-04 10:00:00', '2023-06-04 11:00:00', 1, 0, '0', '1', '1', '0', 0, NULL, NULL, '', '', '', '2023-06-03 23:59:41', '', '2023-06-03 23:59:41', '0');
INSERT INTO `bus_patrol` VALUES (30, 6, 0, 'XC-20230604-527355', '巡查工单', 12, 12, '0', 2, '', '1', 1, 1.00, '2023-06-04 15:00:00', '2023-06-04 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-03 23:59:41', '', '2023-06-03 23:59:41', '1');
INSERT INTO `bus_patrol` VALUES (31, 1, 0, 'XC-20230605-451308', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-05 10:00:00', '2023-06-05 11:00:00', 1, 0, '0', '1', '1', '0', 0, NULL, NULL, '', '', '', '2023-06-04 23:59:41', '', '2023-06-04 23:59:41', '0');
INSERT INTO `bus_patrol` VALUES (32, 6, 0, 'XC-20230605-213793', '巡查工单', 12, 12, '0', 2, '', '1', 1, 1.00, '2023-06-05 15:00:00', '2023-06-05 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-04 23:59:41', '', '2023-06-04 23:59:41', '1');
INSERT INTO `bus_patrol` VALUES (33, 1, 0, 'XC-20230606-919913', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-06 10:00:00', '2023-06-06 11:00:00', 1, 0, '0', '1', '1', '0', 0, NULL, NULL, '', '', '', '2023-06-05 23:59:40', '', '2023-06-05 23:59:40', '0');
INSERT INTO `bus_patrol` VALUES (34, 6, 0, 'XC-20230606-980671', '巡查工单', 12, 12, '0', 2, '', '1', 1, 1.00, '2023-06-06 15:00:00', '2023-06-06 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-05 23:59:40', '', '2023-06-05 23:59:40', '1');
INSERT INTO `bus_patrol` VALUES (35, 1, 0, 'XC-20230607-861095', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-07 10:00:00', '2023-06-07 11:00:00', 40, 0, '0', '1', '1', '0', 0, NULL, NULL, '', '', '', '2023-06-06 23:59:40', '', '2023-06-06 23:59:40', '0');
INSERT INTO `bus_patrol` VALUES (36, 6, 0, 'XC-20230607-823986', '巡查工单', 12, 12, '0', 2, '', '1', 1, 1.00, '2023-06-07 15:00:00', '2023-06-07 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-06 23:59:40', '', '2023-06-06 23:59:40', '1');
INSERT INTO `bus_patrol` VALUES (37, 1, 0, 'XC-20230608-789710', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-08 10:00:00', '2023-06-08 11:00:00', 1, 0, '0', '1', '1', '0', 0, NULL, NULL, '', '', '', '2023-06-07 23:59:39', '', '2023-06-07 23:59:39', '0');
INSERT INTO `bus_patrol` VALUES (38, 6, 0, 'XC-20230608-668545', '巡查工单', 12, 12, '0', 2, '', '1', 1, 1.00, '2023-06-08 15:00:00', '2023-06-08 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-07 23:59:39', '', '2023-06-07 23:59:39', '1');
INSERT INTO `bus_patrol` VALUES (39, 1, 0, 'XC-20230609-906055', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-09 10:00:00', '2023-06-09 11:00:00', 1, 0, '0', '1', '1', '0', 0, NULL, NULL, '', '', '', '2023-06-08 23:59:39', '', '2023-06-08 23:59:39', '0');
INSERT INTO `bus_patrol` VALUES (40, 6, 0, 'XC-20230609-98371', '巡查工单', 12, 12, '0', 2, '', '1', 1, 1.00, '2023-06-09 15:00:00', '2023-06-09 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-08 23:59:39', '', '2023-06-08 23:59:39', '1');
INSERT INTO `bus_patrol` VALUES (41, 1, 0, 'XC-20230610-635302', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-10 10:00:00', '2023-06-10 11:00:00', 1, 0, '0', '1', '1', '0', 0, NULL, NULL, '', '', '', '2023-06-09 23:59:38', '', '2023-06-09 23:59:38', '0');
INSERT INTO `bus_patrol` VALUES (42, 6, 0, 'XC-20230610-285060', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-06-10 15:00:00', '2023-06-10 16:00:00', 18, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-09 23:59:38', '', '2023-06-09 23:59:38', '0');
INSERT INTO `bus_patrol` VALUES (43, 1, 0, 'XC-20230611-624384', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-11 10:00:00', '2023-06-11 11:00:00', 1, 0, '0', '1', '1', '1', 0, '2023-06-13 09:16:07', NULL, '', '', '', '2023-06-10 23:59:38', '', '2023-06-10 23:59:38', '0');
INSERT INTO `bus_patrol` VALUES (44, 6, 0, 'XC-20230611-272500', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-06-11 15:00:00', '2023-06-11 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-10 23:59:38', '', '2023-06-10 23:59:38', '0');
INSERT INTO `bus_patrol` VALUES (45, 1, 0, 'XC-20230612-506046', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-12 10:00:00', '2023-06-12 11:00:00', 1, 0, '0', '1', '1', '0', 0, NULL, NULL, '', '', '', '2023-06-11 23:59:37', '', '2023-06-11 23:59:37', '0');
INSERT INTO `bus_patrol` VALUES (46, 6, 0, 'XC-20230612-482089', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-06-12 15:00:00', '2023-06-12 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-11 23:59:37', '', '2023-06-11 23:59:37', '0');
INSERT INTO `bus_patrol` VALUES (47, 1, 0, 'XC-20230612-154622', '巡查工单', 1, 15, '1', 0, '', '2', 0, 0.00, '2023-06-12 00:00:00', '2023-06-13 00:00:00', 1, 0, '0', '1', '1', '1', 0, '2023-06-12 17:35:11', NULL, '', '', '15288888888', '2023-06-12 17:34:49', '15288888888', '2023-06-12 17:34:49', '0');
INSERT INTO `bus_patrol` VALUES (48, 1, 0, 'XC-20230613-287945', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-13 10:00:00', '2023-06-13 11:00:00', 1, 0, '0', '1', '1', '1', 0, '2023-06-13 09:20:35', NULL, '', '', '', '2023-06-12 23:59:37', '', '2023-06-12 23:59:37', '0');
INSERT INTO `bus_patrol` VALUES (49, 6, 0, 'XC-20230613-451537', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-06-13 15:00:00', '2023-06-13 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-12 23:59:37', '', '2023-06-12 23:59:37', '0');
INSERT INTO `bus_patrol` VALUES (50, 1, 0, 'XC-20230613-596700', '6-13巡查01', 1, 15, '1', 0, '', '2', 0, 1.00, '2023-06-13 00:00:00', '2023-06-14 00:00:00', 1, 0, '0', '1', '1', '1', 0, '2023-06-13 10:17:58', NULL, '', '备注字段', '15288888888', '2023-06-13 09:50:38', '15288888888', '2023-06-13 09:50:38', '0');
INSERT INTO `bus_patrol` VALUES (51, 1, 0, 'XC-20230613-739672', '613工单01', 1, 24, '1', 0, '', '2', 0, 0.00, '2023-06-13 00:00:00', '2023-06-14 00:00:00', 1, 0, '0', '1', '1', '1', 0, '2023-06-13 10:18:53', NULL, '', '巡查备注', '15288888888', '2023-06-13 10:09:49', '15288888888', '2023-06-13 10:09:49', '0');
INSERT INTO `bus_patrol` VALUES (52, 1, 0, 'XC-20230613-114123', 'TS613工单01', 1, 25, '1', 0, '', '2', 0, 0.00, '2023-06-13 00:00:00', '2023-06-14 00:00:00', 1, 0, '0', '1', '1', '1', 0, '2023-06-13 15:59:38', NULL, '', '', '15288888888', '2023-06-13 15:58:25', '15288888888', '2023-06-13 15:58:25', '0');
INSERT INTO `bus_patrol` VALUES (53, 1, 0, 'XC-20230614-407529', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-14 10:00:00', '2023-06-14 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-13 23:59:36', '', '2023-06-13 23:59:36', '0');
INSERT INTO `bus_patrol` VALUES (54, 6, 0, 'XC-20230614-686201', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-06-14 15:00:00', '2023-06-14 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-13 23:59:36', '', '2023-06-13 23:59:36', '0');
INSERT INTO `bus_patrol` VALUES (55, 1, 0, 'XC-20230615-285490', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-15 10:00:00', '2023-06-15 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-14 23:59:36', '', '2023-06-14 23:59:36', '0');
INSERT INTO `bus_patrol` VALUES (56, 6, 0, 'XC-20230615-500940', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-06-15 15:00:00', '2023-06-15 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-14 23:59:36', '', '2023-06-14 23:59:36', '0');
INSERT INTO `bus_patrol` VALUES (57, 1, 0, 'XC-20230616-177423', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-16 10:00:00', '2023-06-16 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-15 23:59:35', '', '2023-06-15 23:59:35', '0');
INSERT INTO `bus_patrol` VALUES (58, 6, 0, 'XC-20230616-912348', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-06-16 15:00:00', '2023-06-16 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-15 23:59:35', '', '2023-06-15 23:59:35', '0');
INSERT INTO `bus_patrol` VALUES (59, 1, 0, 'XC-20230617-928943', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-17 10:00:00', '2023-06-17 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-16 23:59:35', '', '2023-06-16 23:59:35', '0');
INSERT INTO `bus_patrol` VALUES (60, 6, 0, 'XC-20230617-267076', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-06-17 15:00:00', '2023-06-17 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-16 23:59:35', '', '2023-06-16 23:59:35', '0');
INSERT INTO `bus_patrol` VALUES (61, 1, 0, 'XC-20230618-975959', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-18 10:00:00', '2023-06-18 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-17 23:59:34', '', '2023-06-17 23:59:34', '0');
INSERT INTO `bus_patrol` VALUES (62, 6, 0, 'XC-20230618-950650', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-06-18 15:00:00', '2023-06-18 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-17 23:59:34', '', '2023-06-17 23:59:34', '0');
INSERT INTO `bus_patrol` VALUES (63, 1, 0, 'XC-20230619-358068', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-19 10:00:00', '2023-06-19 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-18 23:59:34', '', '2023-06-18 23:59:34', '0');
INSERT INTO `bus_patrol` VALUES (64, 6, 0, 'XC-20230619-158313', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-06-19 15:00:00', '2023-06-19 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-18 23:59:34', '', '2023-06-18 23:59:34', '0');
INSERT INTO `bus_patrol` VALUES (65, 1, 0, 'XC-20230620-792308', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-20 10:00:00', '2023-06-20 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-19 23:59:33', '', '2023-06-19 23:59:33', '0');
INSERT INTO `bus_patrol` VALUES (66, 6, 0, 'XC-20230620-298631', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-06-20 15:00:00', '2023-06-20 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-19 23:59:33', '', '2023-06-19 23:59:33', '0');
INSERT INTO `bus_patrol` VALUES (67, 1, 0, 'XC-20230621-69780', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-21 10:00:00', '2023-06-21 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-20 23:59:33', '', '2023-06-20 23:59:33', '0');
INSERT INTO `bus_patrol` VALUES (68, 6, 0, 'XC-20230621-611575', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-06-21 15:00:00', '2023-06-21 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-20 23:59:33', '', '2023-06-20 23:59:33', '0');
INSERT INTO `bus_patrol` VALUES (69, 1, 0, 'XC-20230622-233039', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-22 10:00:00', '2023-06-22 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-21 23:59:32', '', '2023-06-21 23:59:32', '0');
INSERT INTO `bus_patrol` VALUES (70, 6, 0, 'XC-20230622-510219', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-06-22 15:00:00', '2023-06-22 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-21 23:59:32', '', '2023-06-21 23:59:32', '0');
INSERT INTO `bus_patrol` VALUES (71, 1, 0, 'XC-20230623-741502', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-23 10:00:00', '2023-06-23 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-22 23:59:32', '', '2023-06-22 23:59:32', '0');
INSERT INTO `bus_patrol` VALUES (72, 6, 0, 'XC-20230623-126776', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-06-23 15:00:00', '2023-06-23 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-22 23:59:32', '', '2023-06-22 23:59:32', '0');
INSERT INTO `bus_patrol` VALUES (73, 1, 0, 'XC-20230624-410034', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-24 10:00:00', '2023-06-24 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-23 23:59:31', '', '2023-06-23 23:59:31', '0');
INSERT INTO `bus_patrol` VALUES (74, 6, 0, 'XC-20230624-757540', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-06-24 15:00:00', '2023-06-24 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-23 23:59:31', '', '2023-06-23 23:59:31', '0');
INSERT INTO `bus_patrol` VALUES (75, 1, 0, 'XC-20230625-919172', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-25 10:00:00', '2023-06-25 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-24 23:59:31', '', '2023-06-24 23:59:31', '0');
INSERT INTO `bus_patrol` VALUES (76, 6, 0, 'XC-20230625-315243', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-06-25 15:00:00', '2023-06-25 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-24 23:59:31', '', '2023-06-24 23:59:31', '0');
INSERT INTO `bus_patrol` VALUES (77, 1, 0, 'XC-20230626-866207', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-26 10:00:00', '2023-06-26 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-25 23:59:30', '', '2023-06-25 23:59:30', '0');
INSERT INTO `bus_patrol` VALUES (78, 6, 0, 'XC-20230626-137599', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-06-26 15:00:00', '2023-06-26 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-25 23:59:30', '', '2023-06-25 23:59:30', '0');
INSERT INTO `bus_patrol` VALUES (79, 7, 0, 'XC-20230626-258186', 'Y1#101房屋装修巡查工单', 18, 29, '1', 0, '', '1', 0, 0.00, '2023-06-26 00:00:00', '2023-06-27 00:00:00', 48, 0, '0', '1', '1', '1', 0, '2023-06-26 17:03:54', NULL, '', '', '18888888888', '2023-06-26 16:59:40', '18888888888', '2023-06-26 16:59:40', '0');
INSERT INTO `bus_patrol` VALUES (80, 1, 0, 'XC-20230627-91349', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-27 10:00:00', '2023-06-27 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-26 23:59:30', '', '2023-06-26 23:59:30', '0');
INSERT INTO `bus_patrol` VALUES (81, 6, 0, 'XC-20230627-728496', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-06-27 15:00:00', '2023-06-27 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-26 23:59:30', '', '2023-06-26 23:59:30', '0');
INSERT INTO `bus_patrol` VALUES (82, 1, 0, 'XC-20230628-235650', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-28 10:00:00', '2023-06-28 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-27 23:59:29', '', '2023-06-27 23:59:29', '0');
INSERT INTO `bus_patrol` VALUES (83, 6, 0, 'XC-20230628-856475', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-06-28 15:00:00', '2023-06-28 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-27 23:59:29', '', '2023-06-27 23:59:29', '0');
INSERT INTO `bus_patrol` VALUES (84, 1, 0, 'XC-20230629-508870', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-29 10:00:00', '2023-06-29 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-28 23:59:29', '', '2023-06-28 23:59:29', '0');
INSERT INTO `bus_patrol` VALUES (85, 6, 0, 'XC-20230629-867920', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-06-29 15:00:00', '2023-06-29 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-28 23:59:29', '', '2023-06-28 23:59:29', '0');
INSERT INTO `bus_patrol` VALUES (86, 1, 0, 'XC-20230630-464538', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-06-30 10:00:00', '2023-06-30 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-29 23:59:28', '', '2023-06-29 23:59:28', '0');
INSERT INTO `bus_patrol` VALUES (87, 6, 0, 'XC-20230630-378196', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-06-30 15:00:00', '2023-06-30 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-29 23:59:28', '', '2023-06-29 23:59:28', '0');
INSERT INTO `bus_patrol` VALUES (88, 1, 0, 'XC-20230701-293788', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-01 10:00:00', '2023-07-01 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-30 23:59:28', '', '2023-06-30 23:59:28', '0');
INSERT INTO `bus_patrol` VALUES (89, 6, 0, 'XC-20230701-263592', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-01 15:00:00', '2023-07-01 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-30 23:59:28', '', '2023-06-30 23:59:28', '0');
INSERT INTO `bus_patrol` VALUES (90, 1, 0, 'XC-20230702-394266', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-02 10:00:00', '2023-07-02 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-01 23:59:27', '', '2023-07-01 23:59:27', '0');
INSERT INTO `bus_patrol` VALUES (91, 6, 0, 'XC-20230702-978171', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-02 15:00:00', '2023-07-02 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-01 23:59:27', '', '2023-07-01 23:59:27', '0');
INSERT INTO `bus_patrol` VALUES (92, 1, 0, 'XC-20230703-292622', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-03 10:00:00', '2023-07-03 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-02 23:59:27', '', '2023-07-02 23:59:27', '0');
INSERT INTO `bus_patrol` VALUES (93, 6, 0, 'XC-20230703-133091', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-03 15:00:00', '2023-07-03 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-02 23:59:27', '', '2023-07-02 23:59:27', '0');
INSERT INTO `bus_patrol` VALUES (94, 1, 0, 'XC-20230704-544819', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-04 10:00:00', '2023-07-04 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-03 23:59:26', '', '2023-07-03 23:59:26', '0');
INSERT INTO `bus_patrol` VALUES (95, 6, 0, 'XC-20230704-941741', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-04 15:00:00', '2023-07-04 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-03 23:59:26', '', '2023-07-03 23:59:26', '0');
INSERT INTO `bus_patrol` VALUES (96, 1, 0, 'XC-20230705-33399', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-05 10:00:00', '2023-07-05 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-04 23:59:25', '', '2023-07-04 23:59:25', '0');
INSERT INTO `bus_patrol` VALUES (97, 6, 0, 'XC-20230705-591647', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-05 15:00:00', '2023-07-05 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-04 23:59:25', '', '2023-07-04 23:59:25', '0');
INSERT INTO `bus_patrol` VALUES (98, 1, 0, 'XC-20230706-814577', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-06 10:00:00', '2023-07-06 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-05 23:59:25', '', '2023-07-05 23:59:25', '0');
INSERT INTO `bus_patrol` VALUES (99, 6, 0, 'XC-20230706-760772', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-06 15:00:00', '2023-07-06 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-05 23:59:25', '', '2023-07-05 23:59:25', '0');
INSERT INTO `bus_patrol` VALUES (100, 1, 0, 'XC-20230707-559866', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-07 10:00:00', '2023-07-07 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-06 23:59:24', '', '2023-07-06 23:59:24', '0');
INSERT INTO `bus_patrol` VALUES (101, 6, 0, 'XC-20230707-171209', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-07 15:00:00', '2023-07-07 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-06 23:59:24', '', '2023-07-06 23:59:24', '0');
INSERT INTO `bus_patrol` VALUES (102, 1, 0, 'XC-20230708-833108', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-08 10:00:00', '2023-07-08 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-07 23:59:24', '', '2023-07-07 23:59:24', '0');
INSERT INTO `bus_patrol` VALUES (103, 6, 0, 'XC-20230708-205058', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-08 15:00:00', '2023-07-08 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-07 23:59:24', '', '2023-07-07 23:59:24', '0');
INSERT INTO `bus_patrol` VALUES (104, 1, 0, 'XC-20230709-712350', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-09 10:00:00', '2023-07-09 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-08 23:59:23', '', '2023-07-08 23:59:23', '0');
INSERT INTO `bus_patrol` VALUES (105, 6, 0, 'XC-20230709-846137', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-09 15:00:00', '2023-07-09 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-08 23:59:23', '', '2023-07-08 23:59:23', '0');
INSERT INTO `bus_patrol` VALUES (106, 1, 0, 'XC-20230710-480045', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-10 10:00:00', '2023-07-10 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-09 23:59:23', '', '2023-07-09 23:59:23', '0');
INSERT INTO `bus_patrol` VALUES (107, 6, 0, 'XC-20230710-337208', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-10 15:00:00', '2023-07-10 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-09 23:59:23', '', '2023-07-09 23:59:23', '0');
INSERT INTO `bus_patrol` VALUES (108, 1, 0, 'XC-20230711-664638', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-11 10:00:00', '2023-07-11 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-10 23:59:22', '', '2023-07-10 23:59:22', '0');
INSERT INTO `bus_patrol` VALUES (109, 6, 0, 'XC-20230711-666595', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-11 15:00:00', '2023-07-11 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-10 23:59:22', '', '2023-07-10 23:59:22', '0');
INSERT INTO `bus_patrol` VALUES (110, 1, 0, 'XC-20230712-917844', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-12 10:00:00', '2023-07-12 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-11 23:59:22', '', '2023-07-11 23:59:22', '0');
INSERT INTO `bus_patrol` VALUES (111, 6, 0, 'XC-20230712-876232', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-12 15:00:00', '2023-07-12 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-11 23:59:22', '', '2023-07-11 23:59:22', '0');
INSERT INTO `bus_patrol` VALUES (112, 1, 0, 'XC-20230713-981768', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-13 10:00:00', '2023-07-13 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-12 23:59:21', '', '2023-07-12 23:59:21', '0');
INSERT INTO `bus_patrol` VALUES (113, 6, 0, 'XC-20230713-633320', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-13 15:00:00', '2023-07-13 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-12 23:59:21', '', '2023-07-12 23:59:21', '0');
INSERT INTO `bus_patrol` VALUES (114, 1, 0, 'XC-20230714-833435', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-14 10:00:00', '2023-07-14 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-13 23:59:21', '', '2023-07-13 23:59:21', '0');
INSERT INTO `bus_patrol` VALUES (115, 6, 0, 'XC-20230714-727742', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-14 15:00:00', '2023-07-14 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-13 23:59:21', '', '2023-07-13 23:59:21', '0');
INSERT INTO `bus_patrol` VALUES (116, 1, 0, 'XC-20230715-93418', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-15 10:00:00', '2023-07-15 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-14 23:59:20', '', '2023-07-14 23:59:20', '0');
INSERT INTO `bus_patrol` VALUES (117, 6, 0, 'XC-20230715-628251', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-15 15:00:00', '2023-07-15 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-14 23:59:20', '', '2023-07-14 23:59:20', '0');
INSERT INTO `bus_patrol` VALUES (118, 1, 0, 'XC-20230716-75916', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-16 10:00:00', '2023-07-16 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-15 23:59:20', '', '2023-07-15 23:59:20', '0');
INSERT INTO `bus_patrol` VALUES (119, 6, 0, 'XC-20230716-856306', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-16 15:00:00', '2023-07-16 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-15 23:59:20', '', '2023-07-15 23:59:20', '0');
INSERT INTO `bus_patrol` VALUES (120, 1, 0, 'XC-20230717-558223', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-17 10:00:00', '2023-07-17 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-16 23:59:19', '', '2023-07-16 23:59:19', '0');
INSERT INTO `bus_patrol` VALUES (121, 6, 0, 'XC-20230717-40333', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-17 15:00:00', '2023-07-17 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-16 23:59:19', '', '2023-07-16 23:59:19', '0');
INSERT INTO `bus_patrol` VALUES (122, 1, 0, 'XC-20230718-817389', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-18 10:00:00', '2023-07-18 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-17 23:59:19', '', '2023-07-17 23:59:19', '0');
INSERT INTO `bus_patrol` VALUES (123, 6, 0, 'XC-20230718-883098', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-18 15:00:00', '2023-07-18 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-17 23:59:19', '', '2023-07-17 23:59:19', '0');
INSERT INTO `bus_patrol` VALUES (124, 1, 0, 'XC-20230719-128599', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-19 10:00:00', '2023-07-19 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-18 23:59:18', '', '2023-07-18 23:59:18', '0');
INSERT INTO `bus_patrol` VALUES (125, 6, 0, 'XC-20230719-150589', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-19 15:00:00', '2023-07-19 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-18 23:59:18', '', '2023-07-18 23:59:18', '0');
INSERT INTO `bus_patrol` VALUES (126, 1, 0, 'XC-20230720-113124', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-20 10:00:00', '2023-07-20 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-19 23:59:18', '', '2023-07-19 23:59:18', '0');
INSERT INTO `bus_patrol` VALUES (127, 6, 0, 'XC-20230720-129260', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-20 15:00:00', '2023-07-20 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-19 23:59:18', '', '2023-07-19 23:59:18', '0');
INSERT INTO `bus_patrol` VALUES (128, 1, 0, 'XC-20230721-837298', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-21 10:00:00', '2023-07-21 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-20 23:59:17', '', '2023-07-20 23:59:17', '0');
INSERT INTO `bus_patrol` VALUES (129, 6, 0, 'XC-20230721-137310', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-21 15:00:00', '2023-07-21 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-20 23:59:17', '', '2023-07-20 23:59:17', '0');
INSERT INTO `bus_patrol` VALUES (130, 1, 0, 'XC-20230722-869780', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-22 10:00:00', '2023-07-22 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-21 23:59:17', '', '2023-07-21 23:59:17', '0');
INSERT INTO `bus_patrol` VALUES (131, 6, 0, 'XC-20230722-19334', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-22 15:00:00', '2023-07-22 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-21 23:59:17', '', '2023-07-21 23:59:17', '0');
INSERT INTO `bus_patrol` VALUES (132, 1, 0, 'XC-20230723-955106', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-23 10:00:00', '2023-07-23 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-22 23:59:16', '', '2023-07-22 23:59:16', '0');
INSERT INTO `bus_patrol` VALUES (133, 6, 0, 'XC-20230723-43809', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-23 15:00:00', '2023-07-23 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-22 23:59:16', '', '2023-07-22 23:59:16', '0');
INSERT INTO `bus_patrol` VALUES (134, 1, 0, 'XC-20230724-43128', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-24 10:00:00', '2023-07-24 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-23 23:59:16', '', '2023-07-23 23:59:16', '0');
INSERT INTO `bus_patrol` VALUES (135, 6, 0, 'XC-20230724-539383', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-24 15:00:00', '2023-07-24 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-23 23:59:16', '', '2023-07-23 23:59:16', '0');
INSERT INTO `bus_patrol` VALUES (136, 1, 0, 'XC-20230725-659493', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-25 10:00:00', '2023-07-25 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-24 23:59:15', '', '2023-07-24 23:59:15', '0');
INSERT INTO `bus_patrol` VALUES (137, 6, 0, 'XC-20230725-235432', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-25 15:00:00', '2023-07-25 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-24 23:59:15', '', '2023-07-24 23:59:15', '0');
INSERT INTO `bus_patrol` VALUES (138, 1, 0, 'XC-20230726-609635', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-26 10:00:00', '2023-07-26 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-25 23:59:15', '', '2023-07-25 23:59:15', '0');
INSERT INTO `bus_patrol` VALUES (139, 6, 0, 'XC-20230726-89532', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-26 15:00:00', '2023-07-26 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-25 23:59:15', '', '2023-07-25 23:59:15', '0');
INSERT INTO `bus_patrol` VALUES (140, 1, 0, 'XC-20230727-332938', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-27 10:00:00', '2023-07-27 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-26 23:59:14', '', '2023-07-26 23:59:14', '0');
INSERT INTO `bus_patrol` VALUES (141, 6, 0, 'XC-20230727-174588', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-27 15:00:00', '2023-07-27 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-26 23:59:14', '', '2023-07-26 23:59:14', '0');
INSERT INTO `bus_patrol` VALUES (142, 1, 0, 'XC-20230728-569845', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-28 10:00:00', '2023-07-28 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-27 23:59:14', '', '2023-07-27 23:59:14', '0');
INSERT INTO `bus_patrol` VALUES (143, 6, 0, 'XC-20230728-431360', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-28 15:00:00', '2023-07-28 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-27 23:59:14', '', '2023-07-27 23:59:14', '0');
INSERT INTO `bus_patrol` VALUES (144, 1, 0, 'XC-20230729-786773', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-29 10:00:00', '2023-07-29 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-28 23:59:13', '', '2023-07-28 23:59:13', '0');
INSERT INTO `bus_patrol` VALUES (145, 6, 0, 'XC-20230729-457791', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-29 15:00:00', '2023-07-29 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-28 23:59:13', '', '2023-07-28 23:59:13', '0');
INSERT INTO `bus_patrol` VALUES (146, 1, 0, 'XC-20230730-969971', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-30 10:00:00', '2023-07-30 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-29 23:59:13', '', '2023-07-29 23:59:13', '0');
INSERT INTO `bus_patrol` VALUES (147, 6, 0, 'XC-20230730-327456', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-30 15:00:00', '2023-07-30 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-29 23:59:13', '', '2023-07-29 23:59:13', '0');
INSERT INTO `bus_patrol` VALUES (148, 1, 0, 'XC-20230731-244935', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-07-31 10:00:00', '2023-07-31 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-30 23:59:12', '', '2023-07-30 23:59:12', '0');
INSERT INTO `bus_patrol` VALUES (149, 6, 0, 'XC-20230731-851051', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-07-31 15:00:00', '2023-07-31 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-30 23:59:12', '', '2023-07-30 23:59:12', '0');
INSERT INTO `bus_patrol` VALUES (150, 1, 0, 'XC-20230801-364213', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-01 10:00:00', '2023-08-01 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-31 23:59:11', '', '2023-07-31 23:59:11', '0');
INSERT INTO `bus_patrol` VALUES (151, 6, 0, 'XC-20230801-530612', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-01 15:00:00', '2023-08-01 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-31 23:59:11', '', '2023-07-31 23:59:11', '0');
INSERT INTO `bus_patrol` VALUES (152, 1, 0, 'XC-20230802-134737', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-02 10:00:00', '2023-08-02 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-01 23:59:11', '', '2023-08-01 23:59:11', '0');
INSERT INTO `bus_patrol` VALUES (153, 6, 0, 'XC-20230802-426739', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-02 15:00:00', '2023-08-02 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-01 23:59:11', '', '2023-08-01 23:59:11', '0');
INSERT INTO `bus_patrol` VALUES (154, 1, 0, 'XC-20230803-859287', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-03 10:00:00', '2023-08-03 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-02 23:59:10', '', '2023-08-02 23:59:10', '0');
INSERT INTO `bus_patrol` VALUES (155, 6, 0, 'XC-20230803-165529', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-03 15:00:00', '2023-08-03 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-02 23:59:10', '', '2023-08-02 23:59:10', '0');
INSERT INTO `bus_patrol` VALUES (156, 1, 0, 'XC-20230804-481312', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-04 10:00:00', '2023-08-04 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-03 23:59:10', '', '2023-08-03 23:59:10', '0');
INSERT INTO `bus_patrol` VALUES (157, 6, 0, 'XC-20230804-326351', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-04 15:00:00', '2023-08-04 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-03 23:59:10', '', '2023-08-03 23:59:10', '0');
INSERT INTO `bus_patrol` VALUES (158, 1, 0, 'XC-20230805-676523', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-05 10:00:00', '2023-08-05 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-04 23:59:09', '', '2023-08-04 23:59:09', '0');
INSERT INTO `bus_patrol` VALUES (159, 6, 0, 'XC-20230805-836535', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-05 15:00:00', '2023-08-05 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-04 23:59:09', '', '2023-08-04 23:59:09', '0');
INSERT INTO `bus_patrol` VALUES (160, 1, 0, 'XC-20230806-717320', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-06 10:00:00', '2023-08-06 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-05 23:59:09', '', '2023-08-05 23:59:09', '0');
INSERT INTO `bus_patrol` VALUES (161, 6, 0, 'XC-20230806-652334', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-06 15:00:00', '2023-08-06 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-05 23:59:09', '', '2023-08-05 23:59:09', '0');
INSERT INTO `bus_patrol` VALUES (162, 1, 0, 'XC-20230807-877257', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-07 10:00:00', '2023-08-07 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-06 23:59:08', '', '2023-08-06 23:59:08', '0');
INSERT INTO `bus_patrol` VALUES (163, 6, 0, 'XC-20230807-841760', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-07 15:00:00', '2023-08-07 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-06 23:59:08', '', '2023-08-06 23:59:08', '0');
INSERT INTO `bus_patrol` VALUES (164, 1, 0, 'XC-20230808-43217', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-08 10:00:00', '2023-08-08 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-07 23:59:08', '', '2023-08-07 23:59:08', '0');
INSERT INTO `bus_patrol` VALUES (165, 6, 0, 'XC-20230808-855397', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-08 15:00:00', '2023-08-08 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-07 23:59:08', '', '2023-08-07 23:59:08', '0');
INSERT INTO `bus_patrol` VALUES (166, 1, 0, 'XC-20230809-122716', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-09 10:00:00', '2023-08-09 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-08 23:59:07', '', '2023-08-08 23:59:07', '0');
INSERT INTO `bus_patrol` VALUES (167, 6, 0, 'XC-20230809-308321', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-09 15:00:00', '2023-08-09 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-08 23:59:07', '', '2023-08-08 23:59:07', '0');
INSERT INTO `bus_patrol` VALUES (168, 1, 0, 'XC-20230810-681697', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-10 10:00:00', '2023-08-10 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-09 23:59:07', '', '2023-08-09 23:59:07', '0');
INSERT INTO `bus_patrol` VALUES (169, 6, 0, 'XC-20230810-480611', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-10 15:00:00', '2023-08-10 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-09 23:59:07', '', '2023-08-09 23:59:07', '0');
INSERT INTO `bus_patrol` VALUES (170, 1, 0, 'XC-20230811-444231', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-11 10:00:00', '2023-08-11 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-10 23:59:06', '', '2023-08-10 23:59:06', '0');
INSERT INTO `bus_patrol` VALUES (171, 6, 0, 'XC-20230811-957219', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-11 15:00:00', '2023-08-11 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-10 23:59:06', '', '2023-08-10 23:59:06', '0');
INSERT INTO `bus_patrol` VALUES (172, 1, 0, 'XC-20230812-386768', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-12 10:00:00', '2023-08-12 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-11 23:59:06', '', '2023-08-11 23:59:06', '0');
INSERT INTO `bus_patrol` VALUES (173, 6, 0, 'XC-20230812-845653', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-12 15:00:00', '2023-08-12 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-11 23:59:06', '', '2023-08-11 23:59:06', '0');
INSERT INTO `bus_patrol` VALUES (174, 1, 0, 'XC-20230813-565420', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-13 10:00:00', '2023-08-13 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-12 23:59:05', '', '2023-08-12 23:59:05', '0');
INSERT INTO `bus_patrol` VALUES (175, 6, 0, 'XC-20230813-816374', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-13 15:00:00', '2023-08-13 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-12 23:59:05', '', '2023-08-12 23:59:05', '0');
INSERT INTO `bus_patrol` VALUES (176, 1, 0, 'XC-20230814-493969', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-14 10:00:00', '2023-08-14 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-13 23:59:04', '', '2023-08-13 23:59:04', '0');
INSERT INTO `bus_patrol` VALUES (177, 6, 0, 'XC-20230814-89631', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-14 15:00:00', '2023-08-14 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-13 23:59:04', '', '2023-08-13 23:59:04', '0');
INSERT INTO `bus_patrol` VALUES (178, 1, 0, 'XC-20230815-605349', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-15 10:00:00', '2023-08-15 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-14 23:59:04', '', '2023-08-14 23:59:04', '0');
INSERT INTO `bus_patrol` VALUES (179, 6, 0, 'XC-20230815-664001', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-15 15:00:00', '2023-08-15 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-14 23:59:04', '', '2023-08-14 23:59:04', '0');
INSERT INTO `bus_patrol` VALUES (180, 1, 0, 'XC-20230816-496410', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-16 10:00:00', '2023-08-16 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-15 23:59:03', '', '2023-08-15 23:59:03', '0');
INSERT INTO `bus_patrol` VALUES (181, 6, 0, 'XC-20230816-191572', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-16 15:00:00', '2023-08-16 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-15 23:59:03', '', '2023-08-15 23:59:03', '0');
INSERT INTO `bus_patrol` VALUES (182, 1, 0, 'XC-20230817-252791', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-17 10:00:00', '2023-08-17 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-16 23:59:03', '', '2023-08-16 23:59:03', '0');
INSERT INTO `bus_patrol` VALUES (183, 6, 0, 'XC-20230817-222418', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-17 15:00:00', '2023-08-17 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-16 23:59:03', '', '2023-08-16 23:59:03', '0');
INSERT INTO `bus_patrol` VALUES (184, 1, 0, 'XC-20230818-8552', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-18 10:00:00', '2023-08-18 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-17 23:59:02', '', '2023-08-17 23:59:02', '0');
INSERT INTO `bus_patrol` VALUES (185, 6, 0, 'XC-20230818-551248', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-18 15:00:00', '2023-08-18 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-17 23:59:02', '', '2023-08-17 23:59:02', '0');
INSERT INTO `bus_patrol` VALUES (186, 1, 0, 'XC-20230819-204213', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-19 10:00:00', '2023-08-19 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-18 23:59:02', '', '2023-08-18 23:59:02', '0');
INSERT INTO `bus_patrol` VALUES (187, 6, 0, 'XC-20230819-263889', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-19 15:00:00', '2023-08-19 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-18 23:59:02', '', '2023-08-18 23:59:02', '0');
INSERT INTO `bus_patrol` VALUES (188, 1, 0, 'XC-20230820-964529', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-20 10:00:00', '2023-08-20 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-19 23:59:01', '', '2023-08-19 23:59:01', '0');
INSERT INTO `bus_patrol` VALUES (189, 6, 0, 'XC-20230820-356832', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-20 15:00:00', '2023-08-20 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-19 23:59:01', '', '2023-08-19 23:59:01', '0');
INSERT INTO `bus_patrol` VALUES (190, 1, 0, 'XC-20230821-184771', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-21 10:00:00', '2023-08-21 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-20 23:59:01', '', '2023-08-20 23:59:01', '0');
INSERT INTO `bus_patrol` VALUES (191, 6, 0, 'XC-20230821-617826', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-21 15:00:00', '2023-08-21 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-20 23:59:01', '', '2023-08-20 23:59:01', '0');
INSERT INTO `bus_patrol` VALUES (192, 1, 0, 'XC-20230822-373655', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-22 10:00:00', '2023-08-22 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-21 23:59:00', '', '2023-08-21 23:59:00', '0');
INSERT INTO `bus_patrol` VALUES (193, 6, 0, 'XC-20230822-779284', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-22 15:00:00', '2023-08-22 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-21 23:59:00', '', '2023-08-21 23:59:00', '0');
INSERT INTO `bus_patrol` VALUES (194, 1, 0, 'XC-20230823-945022', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-23 10:00:00', '2023-08-23 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-22 23:59:00', '', '2023-08-22 23:59:00', '0');
INSERT INTO `bus_patrol` VALUES (195, 6, 0, 'XC-20230823-330540', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-23 15:00:00', '2023-08-23 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-22 23:59:00', '', '2023-08-22 23:59:00', '0');
INSERT INTO `bus_patrol` VALUES (196, 1, 0, 'XC-20230824-237976', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-24 10:00:00', '2023-08-24 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-23 23:58:59', '', '2023-08-23 23:58:59', '0');
INSERT INTO `bus_patrol` VALUES (197, 6, 0, 'XC-20230824-231446', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-24 15:00:00', '2023-08-24 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-23 23:58:59', '', '2023-08-23 23:58:59', '0');
INSERT INTO `bus_patrol` VALUES (198, 1, 0, 'XC-20230825-414648', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-25 10:00:00', '2023-08-25 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-24 23:58:59', '', '2023-08-24 23:58:59', '0');
INSERT INTO `bus_patrol` VALUES (199, 6, 0, 'XC-20230825-310665', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-25 15:00:00', '2023-08-25 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-24 23:58:59', '', '2023-08-24 23:58:59', '0');
INSERT INTO `bus_patrol` VALUES (200, 1, 0, 'XC-20230826-754', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-26 10:00:00', '2023-08-26 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-25 23:58:58', '', '2023-08-25 23:58:58', '0');
INSERT INTO `bus_patrol` VALUES (201, 6, 0, 'XC-20230826-93343', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-26 15:00:00', '2023-08-26 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-25 23:58:58', '', '2023-08-25 23:58:58', '0');
INSERT INTO `bus_patrol` VALUES (202, 1, 0, 'XC-20230827-550288', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-27 10:00:00', '2023-08-27 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-26 23:58:58', '', '2023-08-26 23:58:58', '0');
INSERT INTO `bus_patrol` VALUES (203, 6, 0, 'XC-20230827-934105', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-27 15:00:00', '2023-08-27 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-26 23:58:58', '', '2023-08-26 23:58:58', '0');
INSERT INTO `bus_patrol` VALUES (204, 1, 0, 'XC-20230828-536330', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-28 10:00:00', '2023-08-28 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-27 23:58:57', '', '2023-08-27 23:58:57', '0');
INSERT INTO `bus_patrol` VALUES (205, 6, 0, 'XC-20230828-201394', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-28 15:00:00', '2023-08-28 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-27 23:58:57', '', '2023-08-27 23:58:57', '0');
INSERT INTO `bus_patrol` VALUES (206, 1, 0, 'XC-20230829-90089', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-29 10:00:00', '2023-08-29 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-28 23:58:57', '', '2023-08-28 23:58:57', '0');
INSERT INTO `bus_patrol` VALUES (207, 6, 0, 'XC-20230829-672026', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-29 15:00:00', '2023-08-29 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-28 23:58:57', '', '2023-08-28 23:58:57', '0');
INSERT INTO `bus_patrol` VALUES (208, 1, 0, 'XC-20230830-698134', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-30 10:00:00', '2023-08-30 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-29 23:58:56', '', '2023-08-29 23:58:56', '0');
INSERT INTO `bus_patrol` VALUES (209, 6, 0, 'XC-20230830-108846', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-30 15:00:00', '2023-08-30 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-29 23:58:56', '', '2023-08-29 23:58:56', '0');
INSERT INTO `bus_patrol` VALUES (210, 1, 0, 'XC-20230831-486522', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-08-31 10:00:00', '2023-08-31 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-30 23:58:56', '', '2023-08-30 23:58:56', '0');
INSERT INTO `bus_patrol` VALUES (211, 6, 0, 'XC-20230831-147419', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-08-31 15:00:00', '2023-08-31 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-30 23:58:56', '', '2023-08-30 23:58:56', '0');
INSERT INTO `bus_patrol` VALUES (212, 1, 0, 'XC-20230901-835627', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-01 10:00:00', '2023-09-01 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-31 23:58:55', '', '2023-08-31 23:58:55', '0');
INSERT INTO `bus_patrol` VALUES (213, 6, 0, 'XC-20230901-869996', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-01 15:00:00', '2023-09-01 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-31 23:58:55', '', '2023-08-31 23:58:55', '0');
INSERT INTO `bus_patrol` VALUES (214, 1, 0, 'XC-20230902-381301', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-02 10:00:00', '2023-09-02 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-01 23:58:55', '', '2023-09-01 23:58:55', '0');
INSERT INTO `bus_patrol` VALUES (215, 6, 0, 'XC-20230902-980910', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-02 15:00:00', '2023-09-02 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-01 23:58:55', '', '2023-09-01 23:58:55', '0');
INSERT INTO `bus_patrol` VALUES (216, 1, 0, 'XC-20230903-608922', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-03 10:00:00', '2023-09-03 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-02 23:58:54', '', '2023-09-02 23:58:54', '0');
INSERT INTO `bus_patrol` VALUES (217, 6, 0, 'XC-20230903-962879', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-03 15:00:00', '2023-09-03 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-02 23:58:54', '', '2023-09-02 23:58:54', '0');
INSERT INTO `bus_patrol` VALUES (218, 1, 0, 'XC-20230904-695004', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-04 10:00:00', '2023-09-04 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-03 23:58:53', '', '2023-09-03 23:58:53', '0');
INSERT INTO `bus_patrol` VALUES (219, 6, 0, 'XC-20230904-235500', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-04 15:00:00', '2023-09-04 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-03 23:58:53', '', '2023-09-03 23:58:53', '0');
INSERT INTO `bus_patrol` VALUES (220, 1, 0, 'XC-20230905-358673', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-05 10:00:00', '2023-09-05 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-04 23:58:53', '', '2023-09-04 23:58:53', '0');
INSERT INTO `bus_patrol` VALUES (221, 6, 0, 'XC-20230905-42564', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-05 15:00:00', '2023-09-05 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-04 23:58:53', '', '2023-09-04 23:58:53', '0');
INSERT INTO `bus_patrol` VALUES (222, 1, 0, 'XC-20230906-69675', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-06 10:00:00', '2023-09-06 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-05 23:58:52', '', '2023-09-05 23:58:52', '0');
INSERT INTO `bus_patrol` VALUES (223, 6, 0, 'XC-20230906-528767', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-06 15:00:00', '2023-09-06 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-05 23:58:52', '', '2023-09-05 23:58:52', '0');
INSERT INTO `bus_patrol` VALUES (224, 1, 0, 'XC-20230907-333478', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-07 10:00:00', '2023-09-07 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-06 23:58:52', '', '2023-09-06 23:58:52', '0');
INSERT INTO `bus_patrol` VALUES (225, 6, 0, 'XC-20230907-614230', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-07 15:00:00', '2023-09-07 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-06 23:58:52', '', '2023-09-06 23:58:52', '0');
INSERT INTO `bus_patrol` VALUES (226, 1, 0, 'XC-20230908-289381', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-08 10:00:00', '2023-09-08 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-07 23:58:51', '', '2023-09-07 23:58:51', '0');
INSERT INTO `bus_patrol` VALUES (227, 6, 0, 'XC-20230908-683382', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-08 15:00:00', '2023-09-08 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-07 23:58:51', '', '2023-09-07 23:58:51', '0');
INSERT INTO `bus_patrol` VALUES (228, 1, 0, 'XC-20230909-731801', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-09 10:00:00', '2023-09-09 11:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-08 23:58:51', '', '2023-09-08 23:58:51', '0');
INSERT INTO `bus_patrol` VALUES (229, 6, 0, 'XC-20230909-442648', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-09 15:00:00', '2023-09-09 16:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-08 23:58:51', '', '2023-09-08 23:58:51', '0');
INSERT INTO `bus_patrol` VALUES (230, 1, 0, 'XC-20230910-622674', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-10 10:00:00', '2023-09-10 11:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-09 23:58:50', '', '2023-09-09 23:58:50', '0');
INSERT INTO `bus_patrol` VALUES (231, 6, 0, 'XC-20230910-524145', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-10 15:00:00', '2023-09-10 16:00:00', 24, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-09 23:58:50', '', '2023-09-09 23:58:50', '0');
INSERT INTO `bus_patrol` VALUES (232, 1, 0, 'XC-20230911-160622', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-11 10:00:00', '2023-09-11 11:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-10 23:58:50', '', '2023-09-10 23:58:50', '0');
INSERT INTO `bus_patrol` VALUES (233, 6, 0, 'XC-20230911-582505', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-11 15:00:00', '2023-09-11 16:00:00', 15, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-10 23:58:50', '', '2023-09-10 23:58:50', '0');
INSERT INTO `bus_patrol` VALUES (234, 7, 0, 'XC-20230911-364788', '房屋装修巡查', 18, 29, '1', 0, '', '1', 0, 1.00, '2023-09-11 00:00:00', '2023-10-02 00:00:00', 48, 0, '0', '1', '1', '2', 48, '2023-09-11 16:33:39', '2023-09-11 16:33:50', 'http://file.szcjzhxq.com/statics/2023/09/11/女_20230911163346A814.png', '', '18888888888', '2023-09-11 16:23:54', '18888888888', '2023-09-11 16:23:54', '0');
INSERT INTO `bus_patrol` VALUES (235, 7, 0, 'XC-20230911-692729', '装修巡查工单', 23, 29, '1', 0, '', '1', 0, 1.00, '2023-09-11 00:00:00', '2023-10-01 00:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '11', '18888888888', '2023-09-11 17:24:20', '18888888888', '2023-09-11 17:24:20', '0');
INSERT INTO `bus_patrol` VALUES (236, 7, 885, 'XC-20230911-585305', '测试巡查工单', 24, 29, '1', 0, '', '1', 0, 1.00, '2023-09-11 00:00:00', '2023-10-01 00:00:00', 48, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '阿斯顿发', '18888888888', '2023-09-11 20:54:20', '18888888888', '2023-09-11 20:54:20', '0');
INSERT INTO `bus_patrol` VALUES (237, 1, 0, 'XC-20230912-193235', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-12 10:00:00', '2023-09-12 11:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-11 23:58:49', '', '2023-09-11 23:58:49', '0');
INSERT INTO `bus_patrol` VALUES (238, 6, 0, 'XC-20230912-382486', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-12 15:00:00', '2023-09-12 16:00:00', 20, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-11 23:58:49', '', '2023-09-11 23:58:49', '0');
INSERT INTO `bus_patrol` VALUES (239, 1, 0, 'XC-20230913-178532', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-13 10:00:00', '2023-09-13 11:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-12 23:58:49', '', '2023-09-12 23:58:49', '0');
INSERT INTO `bus_patrol` VALUES (240, 6, 0, 'XC-20230913-204231', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-13 15:00:00', '2023-09-13 16:00:00', 20, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-12 23:58:49', '', '2023-09-12 23:58:49', '0');
INSERT INTO `bus_patrol` VALUES (241, 1, 0, 'XC-20230914-700149', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-14 10:00:00', '2023-09-14 11:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-13 23:58:48', '', '2023-09-13 23:58:48', '0');
INSERT INTO `bus_patrol` VALUES (242, 6, 0, 'XC-20230914-298704', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-14 15:00:00', '2023-09-14 16:00:00', 14, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-13 23:58:48', '', '2023-09-13 23:58:48', '0');
INSERT INTO `bus_patrol` VALUES (243, 1, 0, 'XC-20230915-785065', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-15 10:00:00', '2023-09-15 11:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-14 23:58:48', '', '2023-09-14 23:58:48', '0');
INSERT INTO `bus_patrol` VALUES (244, 6, 0, 'XC-20230915-393699', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-15 15:00:00', '2023-09-15 16:00:00', 20, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-14 23:58:48', '', '2023-09-14 23:58:48', '0');
INSERT INTO `bus_patrol` VALUES (245, 1, 0, 'XC-20230916-703499', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-16 10:00:00', '2023-09-16 11:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-15 23:58:47', '', '2023-09-15 23:58:47', '0');
INSERT INTO `bus_patrol` VALUES (246, 6, 0, 'XC-20230916-750301', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-16 15:00:00', '2023-09-16 16:00:00', 21, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-15 23:58:47', '', '2023-09-15 23:58:47', '0');
INSERT INTO `bus_patrol` VALUES (247, 1, 0, 'XC-20230917-671671', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-17 10:00:00', '2023-09-17 11:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-16 23:58:47', '', '2023-09-16 23:58:47', '0');
INSERT INTO `bus_patrol` VALUES (248, 6, 0, 'XC-20230917-539110', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-17 15:00:00', '2023-09-17 16:00:00', 23, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-16 23:58:47', '', '2023-09-16 23:58:47', '0');
INSERT INTO `bus_patrol` VALUES (249, 1, 0, 'XC-20230918-533200', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-18 10:00:00', '2023-09-18 11:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-17 23:58:46', '', '2023-09-17 23:58:46', '0');
INSERT INTO `bus_patrol` VALUES (250, 6, 0, 'XC-20230918-402342', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-18 15:00:00', '2023-09-18 16:00:00', 20, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-17 23:58:46', '', '2023-09-17 23:58:46', '0');
INSERT INTO `bus_patrol` VALUES (251, 1, 0, 'XC-20230919-340244', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-19 10:00:00', '2023-09-19 11:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-18 23:58:46', '', '2023-09-18 23:58:46', '0');
INSERT INTO `bus_patrol` VALUES (252, 6, 0, 'XC-20230919-587227', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-19 15:00:00', '2023-09-19 16:00:00', 17, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-18 23:58:46', '', '2023-09-18 23:58:46', '0');
INSERT INTO `bus_patrol` VALUES (253, 1, 0, 'XC-20230920-307745', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-20 10:00:00', '2023-09-20 11:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-19 23:58:45', '', '2023-09-19 23:58:45', '0');
INSERT INTO `bus_patrol` VALUES (254, 6, 0, 'XC-20230920-513200', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-20 15:00:00', '2023-09-20 16:00:00', 21, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-19 23:58:45', '', '2023-09-19 23:58:45', '0');
INSERT INTO `bus_patrol` VALUES (255, 1, 0, 'XC-20230921-267920', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-21 10:00:00', '2023-09-21 11:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-20 23:58:45', '', '2023-09-20 23:58:45', '0');
INSERT INTO `bus_patrol` VALUES (256, 6, 0, 'XC-20230921-446582', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-21 15:00:00', '2023-09-21 16:00:00', 22, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-20 23:58:45', '', '2023-09-20 23:58:45', '0');
INSERT INTO `bus_patrol` VALUES (257, 1, 0, 'XC-20230922-47897', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-22 10:00:00', '2023-09-22 11:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-21 23:58:44', '', '2023-09-21 23:58:44', '0');
INSERT INTO `bus_patrol` VALUES (258, 6, 0, 'XC-20230922-182451', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-22 15:00:00', '2023-09-22 16:00:00', 18, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-21 23:58:44', '', '2023-09-21 23:58:44', '0');
INSERT INTO `bus_patrol` VALUES (259, 1, 0, 'XC-20230923-38309', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-23 10:00:00', '2023-09-23 11:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-22 23:58:44', '', '2023-09-22 23:58:44', '0');
INSERT INTO `bus_patrol` VALUES (260, 6, 0, 'XC-20230923-337756', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-23 15:00:00', '2023-09-23 16:00:00', 20, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-22 23:58:44', '', '2023-09-22 23:58:44', '0');
INSERT INTO `bus_patrol` VALUES (261, 1, 0, 'XC-20230924-814681', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-24 10:00:00', '2023-09-24 11:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-23 23:58:43', '', '2023-09-23 23:58:43', '0');
INSERT INTO `bus_patrol` VALUES (262, 6, 0, 'XC-20230924-117854', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-24 15:00:00', '2023-09-24 16:00:00', 20, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-23 23:58:43', '', '2023-09-23 23:58:43', '0');
INSERT INTO `bus_patrol` VALUES (263, 1, 0, 'XC-20230925-405188', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-25 10:00:00', '2023-09-25 11:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-24 23:58:43', '', '2023-09-24 23:58:43', '0');
INSERT INTO `bus_patrol` VALUES (264, 6, 0, 'XC-20230925-323158', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-25 15:00:00', '2023-09-25 16:00:00', 21, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-24 23:58:43', '', '2023-09-24 23:58:43', '0');
INSERT INTO `bus_patrol` VALUES (265, 1, 0, 'XC-20230926-134917', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-26 10:00:00', '2023-09-26 11:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-25 23:58:42', '', '2023-09-25 23:58:42', '0');
INSERT INTO `bus_patrol` VALUES (266, 6, 0, 'XC-20230926-302798', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-26 15:00:00', '2023-09-26 16:00:00', 14, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-25 23:58:42', '', '2023-09-25 23:58:42', '0');
INSERT INTO `bus_patrol` VALUES (267, 1, 0, 'XC-20230927-842893', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-27 10:00:00', '2023-09-27 11:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-26 23:58:42', '', '2023-09-26 23:58:42', '0');
INSERT INTO `bus_patrol` VALUES (268, 6, 0, 'XC-20230927-469113', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-27 15:00:00', '2023-09-27 16:00:00', 23, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-26 23:58:42', '', '2023-09-26 23:58:42', '0');
INSERT INTO `bus_patrol` VALUES (269, 1, 0, 'XC-20230928-761274', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-28 10:00:00', '2023-09-28 11:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-27 23:58:41', '', '2023-09-27 23:58:41', '0');
INSERT INTO `bus_patrol` VALUES (270, 6, 0, 'XC-20230928-655952', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-28 15:00:00', '2023-09-28 16:00:00', 22, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-27 23:58:41', '', '2023-09-27 23:58:41', '0');
INSERT INTO `bus_patrol` VALUES (271, 1, 0, 'XC-20230929-944693', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-29 10:00:00', '2023-09-29 11:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-28 23:58:41', '', '2023-09-28 23:58:41', '0');
INSERT INTO `bus_patrol` VALUES (272, 6, 0, 'XC-20230929-812113', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-29 15:00:00', '2023-09-29 16:00:00', 21, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-28 23:58:41', '', '2023-09-28 23:58:41', '0');
INSERT INTO `bus_patrol` VALUES (273, 1, 0, 'XC-20230930-247765', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-09-30 10:00:00', '2023-09-30 11:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-29 23:58:40', '', '2023-09-29 23:58:40', '0');
INSERT INTO `bus_patrol` VALUES (274, 6, 0, 'XC-20230930-902334', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-09-30 15:00:00', '2023-09-30 16:00:00', 18, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-29 23:58:40', '', '2023-09-29 23:58:40', '0');
INSERT INTO `bus_patrol` VALUES (275, 1, 0, 'XC-20231001-689209', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-01 10:00:00', '2023-10-01 11:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-30 23:58:40', '', '2023-09-30 23:58:40', '0');
INSERT INTO `bus_patrol` VALUES (276, 6, 0, 'XC-20231001-54447', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-01 15:00:00', '2023-10-01 16:00:00', 17, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-30 23:58:40', '', '2023-09-30 23:58:40', '0');
INSERT INTO `bus_patrol` VALUES (277, 1, 0, 'XC-20231002-793249', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-02 10:00:00', '2023-10-02 11:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-01 23:58:39', '', '2023-10-01 23:58:39', '0');
INSERT INTO `bus_patrol` VALUES (278, 6, 0, 'XC-20231002-795177', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-02 15:00:00', '2023-10-02 16:00:00', 24, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-01 23:58:39', '', '2023-10-01 23:58:39', '0');
INSERT INTO `bus_patrol` VALUES (279, 1, 0, 'XC-20231003-156971', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-03 10:00:00', '2023-10-03 11:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-02 23:58:39', '', '2023-10-02 23:58:39', '0');
INSERT INTO `bus_patrol` VALUES (280, 6, 0, 'XC-20231003-119300', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-03 15:00:00', '2023-10-03 16:00:00', 22, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-02 23:58:39', '', '2023-10-02 23:58:39', '0');
INSERT INTO `bus_patrol` VALUES (281, 1, 0, 'XC-20231004-216848', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-04 10:00:00', '2023-10-04 11:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-03 23:58:38', '', '2023-10-03 23:58:38', '0');
INSERT INTO `bus_patrol` VALUES (282, 6, 0, 'XC-20231004-723945', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-04 15:00:00', '2023-10-04 16:00:00', 17, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-03 23:58:38', '', '2023-10-03 23:58:38', '0');
INSERT INTO `bus_patrol` VALUES (283, 1, 0, 'XC-20231005-623914', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-05 10:00:00', '2023-10-05 11:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-04 23:58:38', '', '2023-10-04 23:58:38', '0');
INSERT INTO `bus_patrol` VALUES (284, 6, 0, 'XC-20231005-164170', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-05 15:00:00', '2023-10-05 16:00:00', 17, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-04 23:58:38', '', '2023-10-04 23:58:38', '0');
INSERT INTO `bus_patrol` VALUES (285, 1, 0, 'XC-20231006-526616', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-06 10:00:00', '2023-10-06 11:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-05 23:58:37', '', '2023-10-05 23:58:37', '0');
INSERT INTO `bus_patrol` VALUES (286, 6, 0, 'XC-20231006-690976', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-06 15:00:00', '2023-10-06 16:00:00', 23, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-05 23:58:37', '', '2023-10-05 23:58:37', '0');
INSERT INTO `bus_patrol` VALUES (287, 1, 0, 'XC-20231007-82191', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-07 10:00:00', '2023-10-07 11:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-06 23:58:37', '', '2023-10-06 23:58:37', '0');
INSERT INTO `bus_patrol` VALUES (288, 6, 0, 'XC-20231007-679712', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-07 15:00:00', '2023-10-07 16:00:00', 17, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-06 23:58:37', '', '2023-10-06 23:58:37', '0');
INSERT INTO `bus_patrol` VALUES (289, 1, 0, 'XC-20231008-930421', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-08 10:00:00', '2023-10-08 11:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-07 23:58:36', '', '2023-10-07 23:58:36', '0');
INSERT INTO `bus_patrol` VALUES (290, 6, 0, 'XC-20231008-417818', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-08 15:00:00', '2023-10-08 16:00:00', 14, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-07 23:58:36', '', '2023-10-07 23:58:36', '0');
INSERT INTO `bus_patrol` VALUES (291, 1, 0, 'XC-20231009-663046', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-09 10:00:00', '2023-10-09 11:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-08 23:58:36', '', '2023-10-08 23:58:36', '0');
INSERT INTO `bus_patrol` VALUES (292, 6, 0, 'XC-20231009-142775', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-09 15:00:00', '2023-10-09 16:00:00', 24, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-08 23:58:36', '', '2023-10-08 23:58:36', '0');
INSERT INTO `bus_patrol` VALUES (293, 1, 0, 'XC-20231010-577871', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-10 10:00:00', '2023-10-10 11:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-09 23:58:35', '', '2023-10-09 23:58:35', '0');
INSERT INTO `bus_patrol` VALUES (294, 6, 0, 'XC-20231010-654536', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-10 15:00:00', '2023-10-10 16:00:00', 15, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-09 23:58:35', '', '2023-10-09 23:58:35', '0');
INSERT INTO `bus_patrol` VALUES (295, 1, 0, 'XC-20231011-36498', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-11 10:00:00', '2023-10-11 11:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-10 23:58:35', '', '2023-10-10 23:58:35', '0');
INSERT INTO `bus_patrol` VALUES (296, 6, 0, 'XC-20231011-116969', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-11 15:00:00', '2023-10-11 16:00:00', 24, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-10 23:58:35', '', '2023-10-10 23:58:35', '0');
INSERT INTO `bus_patrol` VALUES (297, 1, 0, 'XC-20231012-430805', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-12 10:00:00', '2023-10-12 11:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-11 23:58:34', '', '2023-10-11 23:58:34', '0');
INSERT INTO `bus_patrol` VALUES (298, 6, 0, 'XC-20231012-459448', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-12 15:00:00', '2023-10-12 16:00:00', 17, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-11 23:58:34', '', '2023-10-11 23:58:34', '0');
INSERT INTO `bus_patrol` VALUES (299, 1, 0, 'XC-20231013-635940', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-13 10:00:00', '2023-10-13 11:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-12 23:58:34', '', '2023-10-12 23:58:34', '0');
INSERT INTO `bus_patrol` VALUES (300, 6, 0, 'XC-20231013-312164', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-13 15:00:00', '2023-10-13 16:00:00', 15, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-12 23:58:34', '', '2023-10-12 23:58:34', '0');
INSERT INTO `bus_patrol` VALUES (301, 1, 0, 'XC-20231014-665012', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-14 10:00:00', '2023-10-14 11:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-13 23:58:33', '', '2023-10-13 23:58:33', '0');
INSERT INTO `bus_patrol` VALUES (302, 6, 0, 'XC-20231014-119861', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-14 15:00:00', '2023-10-14 16:00:00', 14, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-13 23:58:33', '', '2023-10-13 23:58:33', '0');
INSERT INTO `bus_patrol` VALUES (303, 1, 0, 'XC-20231015-463073', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-15 10:00:00', '2023-10-15 11:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-14 23:58:33', '', '2023-10-14 23:58:33', '0');
INSERT INTO `bus_patrol` VALUES (304, 6, 0, 'XC-20231015-137245', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-15 15:00:00', '2023-10-15 16:00:00', 15, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-14 23:58:33', '', '2023-10-14 23:58:33', '0');
INSERT INTO `bus_patrol` VALUES (305, 1, 0, 'XC-20231016-759464', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-16 10:00:00', '2023-10-16 11:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-15 23:58:32', '', '2023-10-15 23:58:32', '0');
INSERT INTO `bus_patrol` VALUES (306, 6, 0, 'XC-20231016-686219', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-16 15:00:00', '2023-10-16 16:00:00', 23, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-15 23:58:32', '', '2023-10-15 23:58:32', '0');
INSERT INTO `bus_patrol` VALUES (307, 1, 0, 'XC-20231017-73348', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-17 10:00:00', '2023-10-17 11:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-16 23:58:32', '', '2023-10-16 23:58:32', '0');
INSERT INTO `bus_patrol` VALUES (308, 6, 0, 'XC-20231017-331843', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-17 15:00:00', '2023-10-17 16:00:00', 17, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-16 23:58:32', '', '2023-10-16 23:58:32', '0');
INSERT INTO `bus_patrol` VALUES (309, 1, 0, 'XC-20231018-98117', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-18 10:00:00', '2023-10-18 11:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-17 23:58:31', '', '2023-10-17 23:58:31', '0');
INSERT INTO `bus_patrol` VALUES (310, 6, 0, 'XC-20231018-733795', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-18 15:00:00', '2023-10-18 16:00:00', 21, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-17 23:58:31', '', '2023-10-17 23:58:31', '0');
INSERT INTO `bus_patrol` VALUES (311, 1, 0, 'XC-20231019-776754', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-19 10:00:00', '2023-10-19 11:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-18 23:58:31', '', '2023-10-18 23:58:31', '0');
INSERT INTO `bus_patrol` VALUES (312, 6, 0, 'XC-20231019-147482', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-19 15:00:00', '2023-10-19 16:00:00', 14, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-18 23:58:31', '', '2023-10-18 23:58:31', '0');
INSERT INTO `bus_patrol` VALUES (313, 1, 0, 'XC-20231020-24', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-20 10:00:00', '2023-10-20 11:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-19 23:58:30', '', '2023-10-19 23:58:30', '0');
INSERT INTO `bus_patrol` VALUES (314, 6, 0, 'XC-20231020-656453', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-20 15:00:00', '2023-10-20 16:00:00', 24, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-19 23:58:30', '', '2023-10-19 23:58:30', '0');
INSERT INTO `bus_patrol` VALUES (315, 1, 0, 'XC-20231021-669621', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-21 10:00:00', '2023-10-21 11:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-20 23:58:30', '', '2023-10-20 23:58:30', '0');
INSERT INTO `bus_patrol` VALUES (316, 6, 0, 'XC-20231021-422621', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-21 15:00:00', '2023-10-21 16:00:00', 24, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-20 23:58:30', '', '2023-10-20 23:58:30', '0');
INSERT INTO `bus_patrol` VALUES (317, 1, 0, 'XC-20231022-189217', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-22 10:00:00', '2023-10-22 11:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-21 23:58:29', '', '2023-10-21 23:58:29', '0');
INSERT INTO `bus_patrol` VALUES (318, 6, 0, 'XC-20231022-719392', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-22 15:00:00', '2023-10-22 16:00:00', 18, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-21 23:58:29', '', '2023-10-21 23:58:29', '0');
INSERT INTO `bus_patrol` VALUES (319, 1, 0, 'XC-20231023-223604', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-23 10:00:00', '2023-10-23 11:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-22 23:58:29', '', '2023-10-22 23:58:29', '0');
INSERT INTO `bus_patrol` VALUES (320, 6, 0, 'XC-20231023-319169', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-23 15:00:00', '2023-10-23 16:00:00', 21, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-22 23:58:29', '', '2023-10-22 23:58:29', '0');
INSERT INTO `bus_patrol` VALUES (321, 1, 0, 'XC-20231024-917072', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-24 10:00:00', '2023-10-24 11:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-23 23:58:28', '', '2023-10-23 23:58:28', '0');
INSERT INTO `bus_patrol` VALUES (322, 6, 0, 'XC-20231024-439690', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-24 15:00:00', '2023-10-24 16:00:00', 17, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-23 23:58:28', '', '2023-10-23 23:58:28', '0');
INSERT INTO `bus_patrol` VALUES (323, 1, 0, 'XC-20231025-110317', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-25 10:00:00', '2023-10-25 11:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-24 23:58:28', '', '2023-10-24 23:58:28', '0');
INSERT INTO `bus_patrol` VALUES (324, 6, 0, 'XC-20231025-384971', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-25 15:00:00', '2023-10-25 16:00:00', 23, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-24 23:58:28', '', '2023-10-24 23:58:28', '0');
INSERT INTO `bus_patrol` VALUES (325, 1, 0, 'XC-20231026-440917', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-26 10:00:00', '2023-10-26 11:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-25 23:58:27', '', '2023-10-25 23:58:27', '0');
INSERT INTO `bus_patrol` VALUES (326, 6, 0, 'XC-20231026-767397', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-26 15:00:00', '2023-10-26 16:00:00', 16, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-25 23:58:27', '', '2023-10-25 23:58:27', '0');
INSERT INTO `bus_patrol` VALUES (327, 1, 0, 'XC-20231027-253197', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-27 10:00:00', '2023-10-27 11:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-26 23:58:27', '', '2023-10-26 23:58:27', '0');
INSERT INTO `bus_patrol` VALUES (328, 6, 0, 'XC-20231027-806095', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-27 15:00:00', '2023-10-27 16:00:00', 15, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-26 23:58:27', '', '2023-10-26 23:58:27', '0');
INSERT INTO `bus_patrol` VALUES (329, 1, 0, 'XC-20231028-449359', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-28 10:00:00', '2023-10-28 11:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-27 23:58:26', '', '2023-10-27 23:58:26', '0');
INSERT INTO `bus_patrol` VALUES (330, 6, 0, 'XC-20231028-663909', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-28 15:00:00', '2023-10-28 16:00:00', 18, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-27 23:58:26', '', '2023-10-27 23:58:26', '0');
INSERT INTO `bus_patrol` VALUES (331, 1, 0, 'XC-20231029-922070', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-29 10:00:00', '2023-10-29 11:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-28 23:58:26', '', '2023-10-28 23:58:26', '0');
INSERT INTO `bus_patrol` VALUES (332, 6, 0, 'XC-20231029-727429', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-29 15:00:00', '2023-10-29 16:00:00', 23, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-28 23:58:26', '', '2023-10-28 23:58:26', '0');
INSERT INTO `bus_patrol` VALUES (333, 1, 0, 'XC-20231030-104319', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-30 10:00:00', '2023-10-30 11:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-29 23:58:25', '', '2023-10-29 23:58:25', '0');
INSERT INTO `bus_patrol` VALUES (334, 6, 0, 'XC-20231030-502898', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-30 15:00:00', '2023-10-30 16:00:00', 15, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-29 23:58:25', '', '2023-10-29 23:58:25', '0');
INSERT INTO `bus_patrol` VALUES (335, 1, 0, 'XC-20231031-929131', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-10-31 10:00:00', '2023-10-31 11:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-30 23:58:25', '', '2023-10-30 23:58:25', '0');
INSERT INTO `bus_patrol` VALUES (336, 6, 0, 'XC-20231031-157350', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-10-31 15:00:00', '2023-10-31 16:00:00', 15, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-30 23:58:25', '', '2023-10-30 23:58:25', '0');
INSERT INTO `bus_patrol` VALUES (337, 1, 0, 'XC-20231101-748995', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-11-01 10:00:00', '2023-11-01 11:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-31 23:58:24', '', '2023-10-31 23:58:24', '0');
INSERT INTO `bus_patrol` VALUES (338, 6, 0, 'XC-20231101-430429', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-11-01 15:00:00', '2023-11-01 16:00:00', 16, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-31 23:58:24', '', '2023-10-31 23:58:24', '0');
INSERT INTO `bus_patrol` VALUES (339, 1, 0, 'XC-20231102-152302', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-11-02 10:00:00', '2023-11-02 11:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-01 23:58:24', '', '2023-11-01 23:58:24', '0');
INSERT INTO `bus_patrol` VALUES (340, 6, 0, 'XC-20231102-28454', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-11-02 15:00:00', '2023-11-02 16:00:00', 20, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-01 23:58:24', '', '2023-11-01 23:58:24', '0');
INSERT INTO `bus_patrol` VALUES (341, 1, 0, 'XC-20231103-343419', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-11-03 10:00:00', '2023-11-03 11:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-02 23:58:24', '', '2023-11-02 23:58:24', '0');
INSERT INTO `bus_patrol` VALUES (342, 6, 0, 'XC-20231103-444461', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-11-03 15:00:00', '2023-11-03 16:00:00', 23, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-02 23:58:24', '', '2023-11-02 23:58:24', '0');
INSERT INTO `bus_patrol` VALUES (343, 1, 0, 'XC-20231104-730552', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-11-04 10:00:00', '2023-11-04 11:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-03 23:58:23', '', '2023-11-03 23:58:23', '0');
INSERT INTO `bus_patrol` VALUES (344, 6, 0, 'XC-20231104-544821', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-11-04 15:00:00', '2023-11-04 16:00:00', 18, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-03 23:58:23', '', '2023-11-03 23:58:23', '0');
INSERT INTO `bus_patrol` VALUES (345, 1, 0, 'XC-20231105-445149', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-11-05 10:00:00', '2023-11-05 11:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-04 23:58:23', '', '2023-11-04 23:58:23', '0');
INSERT INTO `bus_patrol` VALUES (346, 6, 0, 'XC-20231105-540962', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-11-05 15:00:00', '2023-11-05 16:00:00', 15, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-04 23:58:23', '', '2023-11-04 23:58:23', '0');
INSERT INTO `bus_patrol` VALUES (347, 1, 0, 'XC-20231106-795423', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-11-06 10:00:00', '2023-11-06 11:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-05 23:58:22', '', '2023-11-05 23:58:22', '0');
INSERT INTO `bus_patrol` VALUES (348, 6, 0, 'XC-20231106-926138', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-11-06 15:00:00', '2023-11-06 16:00:00', 14, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-05 23:58:22', '', '2023-11-05 23:58:22', '0');
INSERT INTO `bus_patrol` VALUES (349, 1, 0, 'XC-20231107-362362', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-11-07 10:00:00', '2023-11-07 11:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-06 23:58:22', '', '2023-11-06 23:58:22', '0');
INSERT INTO `bus_patrol` VALUES (350, 6, 0, 'XC-20231107-423968', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-11-07 15:00:00', '2023-11-07 16:00:00', 15, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-06 23:58:22', '', '2023-11-06 23:58:22', '0');
INSERT INTO `bus_patrol` VALUES (351, 1, 0, 'XC-20231108-335839', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-11-08 10:00:00', '2023-11-08 11:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-07 23:58:21', '', '2023-11-07 23:58:21', '0');
INSERT INTO `bus_patrol` VALUES (352, 6, 0, 'XC-20231108-262984', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-11-08 15:00:00', '2023-11-08 16:00:00', 14, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-07 23:58:21', '', '2023-11-07 23:58:21', '0');
INSERT INTO `bus_patrol` VALUES (353, 1, 0, 'XC-20231109-130890', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-11-09 10:00:00', '2023-11-09 11:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-08 23:58:21', '', '2023-11-08 23:58:21', '0');
INSERT INTO `bus_patrol` VALUES (354, 6, 0, 'XC-20231109-657326', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-11-09 15:00:00', '2023-11-09 16:00:00', 24, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-08 23:58:21', '', '2023-11-08 23:58:21', '0');
INSERT INTO `bus_patrol` VALUES (355, 1, 0, 'XC-20231110-675799', '巡查工单', 1, 1, '0', 1, '', '2', 1, 0.00, '2023-11-10 10:00:00', '2023-11-10 11:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-09 23:58:20', '', '2023-11-09 23:58:20', '0');
INSERT INTO `bus_patrol` VALUES (356, 6, 0, 'XC-20231110-715074', '巡查工单', 12, 12, '0', 2, '', '0', 1, 1.00, '2023-11-10 15:00:00', '2023-11-10 16:00:00', 14, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-09 23:58:20', '', '2023-11-09 23:58:20', '0');

-- ----------------------------
-- Table structure for bus_patrol_plan
-- ----------------------------
DROP TABLE IF EXISTS `bus_patrol_plan`;
CREATE TABLE `bus_patrol_plan`  (
  `plan_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `plan_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '计划名称',
  `tour_id` bigint NOT NULL DEFAULT 0 COMMENT '巡查类型id，表xccj_work.bus_tour（tour_type=0）',
  `point_id` bigint NOT NULL DEFAULT 0 COMMENT '巡查点id，表xccj_work.bus_point（point_type!=2）',
  `plan_order` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '是否生成工单：0=是，1=否',
  `plan_model` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '巡查模式 ：0=一日多查，1=多日一查',
  `plan_num` int NOT NULL DEFAULT 0 COMMENT '巡查次数/天次',
  `start_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '计划开始（格式：plan_model=0时HH:mm，plan_model=1时yyyy-MM-dd）',
  `end_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '计划结束（格式：plan_model=0时HH:mm，plan_model=1时yyyy-MM-dd）',
  `gap_time` decimal(10, 2) NOT NULL COMMENT '每次巡查间隔时间（plan_model=0）',
  `allot_way` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单分配方式：0=自动分配，1=管理员分配，2=抢单',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`plan_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡查计划表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_patrol_plan
-- ----------------------------
INSERT INTO `bus_patrol_plan` VALUES (1, 1, '巡查计划01', 1, 1, '0', '1', 1, '10:00:00', '11:00:00', 0.00, '0', '15288888888', '2023-05-15 18:44:32', '15288888888', '2023-05-15 18:44:32', '0');
INSERT INTO `bus_patrol_plan` VALUES (2, 6, '小区各巡查点自动巡查计划', 12, 12, '0', '1', 1, '15:00:00', '16:00:00', 1.00, '0', '15656332018', '2023-05-31 14:45:19', '15656332018', '2023-05-31 14:55:48', '0');

-- ----------------------------
-- Table structure for bus_plan_device
-- ----------------------------
DROP TABLE IF EXISTS `bus_plan_device`;
CREATE TABLE `bus_plan_device`  (
  `plan_id` bigint NOT NULL DEFAULT 0 COMMENT '巡检计划id，表xccj_work.bus_inspect_plan',
  `device_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '设备类型：见类 DeviceConstants',
  `index_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '设备唯一标识，device_type对应的表'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡检计划-设备表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_plan_device
-- ----------------------------
INSERT INTO `bus_plan_device` VALUES (2, 'camera', '120aec1f9f7b490c9bd08800f507cd97');
INSERT INTO `bus_plan_device` VALUES (2, 'camera', '0bda9321bb6b4cb180c14c8827db2556');
INSERT INTO `bus_plan_device` VALUES (3, 'acsDevice', '197067f578b047f38019bafa3cfdbd98');
INSERT INTO `bus_plan_device` VALUES (3, 'camera', '3a1ea325bc864594bac5396f7f3b1070');
INSERT INTO `bus_plan_device` VALUES (3, 'camera', '120aec1f9f7b490c9bd08800f507cd97');
INSERT INTO `bus_plan_device` VALUES (3, 'camera', '364aaf8987944cb0bcd75831ca0b4ba7');
INSERT INTO `bus_plan_device` VALUES (3, 'camera', 'c46dc43dab5e488bbc53cd75b8522766');
INSERT INTO `bus_plan_device` VALUES (3, 'camera', '86a9fdb53d27450c996863df8428b929');
INSERT INTO `bus_plan_device` VALUES (3, 'camera', '0bda9321bb6b4cb180c14c8827db2556');
INSERT INTO `bus_plan_device` VALUES (3, 'camera', '3805c437a8904f659c6b50af0892461d');
INSERT INTO `bus_plan_device` VALUES (3, 'camera', '2d5dfef84a4d4cc5911d1d65393685ab');
INSERT INTO `bus_plan_device` VALUES (3, 'camera', 'ee1855211f674252a87e1893351609b7');
INSERT INTO `bus_plan_device` VALUES (1, 'acsDevice', '7874647065ab4cefb90ca51a3ca17923');
INSERT INTO `bus_plan_device` VALUES (1, 'acsDevice', 'f6e082313d3e45c884c9996e3a99d5cb');
INSERT INTO `bus_plan_device` VALUES (1, 'acsDevice', '819479eac68a4041addd5e8dbaa1a78d');

-- ----------------------------
-- Table structure for bus_point
-- ----------------------------
DROP TABLE IF EXISTS `bus_point`;
CREATE TABLE `bus_point`  (
  `point_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `point_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '巡点名称',
  `point_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '类型：0=巡查点、巡更点，1=巡查点，2=巡更点',
  `point_site` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '巡点所在位置',
  `point_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '状态：0=启用，1=停用',
  `point_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '二维码路径',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`point_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡点管理表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_point
-- ----------------------------
INSERT INTO `bus_point` VALUES (1, 1, '巡查01', '1', '巡更01地址', '0', 'http://file.szcjzhxq.com:8084/statics/2023/05/15/qr1684145697588_20230515181444A202.jpg', '15288888888', '2023-05-15 18:14:46', '15288888888', '2023-05-15 18:40:43', '0');
INSERT INTO `bus_point` VALUES (2, 1, '巡更02', '2', '巡更02地址', '0', 'http://file.szcjzhxq.com:8084/statics/2023/05/15/qr1684145712580_20230515181459A203.jpg', '15288888888', '2023-05-15 18:15:01', '15288888888', '2023-05-15 18:41:33', '0');
INSERT INTO `bus_point` VALUES (3, 1, '巡更巡查01', '0', '巡更巡查01地址', '0', 'http://file.szcjzhxq.com:8084/statics/2023/05/15/qr1684145740000_20230515181527A204.jpg', '15288888888', '2023-05-15 18:15:29', '15288888888', '2023-05-15 18:40:53', '0');
INSERT INTO `bus_point` VALUES (4, 1, '一号巡查点', '1', '一号巡查点地址', '0', 'http://file.szcjzhxq.com:8084/statics/2023/05/15/qr1684147125004_20230515183832A208.jpg', '15288888888', '2023-05-15 18:38:34', '15288888888', '2023-06-12 16:20:17', '1');
INSERT INTO `bus_point` VALUES (5, 1, '巡查03', '1', '巡查03地址', '0', 'http://file.szcjzhxq.com:8084/statics/2023/05/15/qr1684147329010_20230515184156A209.jpg', '15288888888', '2023-05-15 18:41:58', '15288888888', '2023-05-15 18:41:58', '0');
INSERT INTO `bus_point` VALUES (6, 1, '巡查04', '1', '巡查04地址', '0', 'http://file.szcjzhxq.com:8084/statics/2023/05/15/qr1684147342828_20230515184209A210.jpg', '15288888888', '2023-05-15 18:42:11', '15288888888', '2023-05-15 18:42:11', '0');
INSERT INTO `bus_point` VALUES (7, 1, '三号巡查点', '1', '三号巡查点地址', '0', 'http://file.szcjzhxq.com:8084/statics/2023/05/15/qr1684147360075_20230515184227A211.jpg', '15288888888', '2023-05-15 18:42:29', '15288888888', '2023-06-12 16:20:50', '1');
INSERT INTO `bus_point` VALUES (8, 1, '巡更巡查02', '0', '巡更巡查02', '0', 'http://file.szcjzhxq.com:8084/statics/2023/05/17/qr1684303351825_20230517140218A317.jpg', '15288888888', '2023-05-17 14:02:20', '15288888888', '2023-05-17 14:02:20', '0');
INSERT INTO `bus_point` VALUES (9, 6, '南门', '0', '小区南门', '0', 'http://file.szcjzhxq.com:8084/statics/2023/05/31/qr1685502488583_20230531110749A114.jpg', '15656332018', '2023-05-31 11:07:50', '15656332018', '2023-05-31 11:07:50', '0');
INSERT INTO `bus_point` VALUES (10, 6, '西门', '0', '小区西门', '0', 'http://file.szcjzhxq.com:8084/statics/2023/05/31/qr1685502505590_20230531110805A115.jpg', '15656332018', '2023-05-31 11:08:07', '15656332018', '2023-05-31 11:08:07', '0');
INSERT INTO `bus_point` VALUES (11, 6, '东门', '0', '小区东门', '0', 'http://file.szcjzhxq.com:8084/statics/2023/05/31/qr1685502533873_20230531110834A116.jpg', '15656332018', '2023-05-31 11:08:36', '15656332018', '2023-05-31 11:08:36', '0');
INSERT INTO `bus_point` VALUES (12, 6, '北门', '0', '小区北门', '0', 'http://file.szcjzhxq.com:8084/statics/2023/05/31/qr1685502548945_20230531110849A117.jpg', '15656332018', '2023-05-31 11:08:51', '15656332018', '2023-05-31 11:08:51', '0');
INSERT INTO `bus_point` VALUES (13, 1, '6.12测试新增', '0', '测试地址', '0', 'http://file.szcjzhxq.com/statics/2023/06/12/qr1686557951431_20230612161845A309.jpg', '15288888888', '2023-06-12 16:18:47', '15288888888', '2023-06-12 16:18:47', '0');
INSERT INTO `bus_point` VALUES (14, 1, '二号巡查点', '1', '二号巡查点地址', '0', 'http://file.szcjzhxq.com/statics/2023/06/12/qr1686558059654_20230612162033A310.jpg', '15288888888', '2023-06-12 16:20:35', '15288888888', '2023-06-12 16:20:35', '1');
INSERT INTO `bus_point` VALUES (15, 1, '1号巡查点', '1', '1号巡查点地址', '0', 'http://file.szcjzhxq.com/statics/2023/06/12/qr1686559002836_20230612163617A311.jpg', '15288888888', '2023-06-12 16:36:19', '15288888888', '2023-06-13 09:49:20', '0');
INSERT INTO `bus_point` VALUES (16, 1, '2号巡更点', '2', '2号巡更点地址', '0', 'http://file.szcjzhxq.com/statics/2023/06/12/qr1686559012227_20230612163626A312.jpg', '15288888888', '2023-06-12 16:36:28', '15288888888', '2023-06-12 16:36:28', '0');
INSERT INTO `bus_point` VALUES (17, 1, '2号巡查点', '1', '2号巡查点地址', '0', 'http://file.szcjzhxq.com/statics/2023/06/12/qr1686559080598_20230612163734A313.jpg', '15288888888', '2023-06-12 16:37:36', '15288888888', '2023-06-13 09:49:17', '0');
INSERT INTO `bus_point` VALUES (18, 1, '1号巡更点', '2', '1号巡更点地址', '0', 'http://file.szcjzhxq.com/statics/2023/06/12/qr1686559098941_20230612163753A314.jpg', '15288888888', '2023-06-12 16:37:55', '15288888888', '2023-06-13 09:49:15', '0');
INSERT INTO `bus_point` VALUES (19, 1, '测试21', '2', '地址', '1', 'http://file.szcjzhxq.com/statics/2023/06/12/qr1686559112365_20230612163806A315.jpg', '15288888888', '2023-06-12 16:38:08', '15288888888', '2023-06-12 17:25:50', '1');
INSERT INTO `bus_point` VALUES (20, 1, '测试巡点', '1', '地址', '1', 'http://file.szcjzhxq.com/statics/2023/06/12/qr1686559122495_20230612163816A316.jpg', '15288888888', '2023-06-12 16:38:18', '15288888888', '2023-06-12 16:49:02', '1');
INSERT INTO `bus_point` VALUES (21, 1, '试社区_01', '0', '试社区_01', '0', 'http://file.szcjzhxq.com/statics/2023/06/12/qr1686561990467_20230612172604A330.jpg', '15288888888', '2023-06-12 17:26:06', '15288888888', '2023-06-12 17:26:06', '0');
INSERT INTO `bus_point` VALUES (22, 1, '试社区_02', '1', '试社区_01', '0', 'http://file.szcjzhxq.com/statics/2023/06/12/qr1686562002009_20230612172616A331.jpg', '15288888888', '2023-06-12 17:26:18', '15288888888', '2023-06-12 17:26:18', '1');
INSERT INTO `bus_point` VALUES (23, 1, '试社区_03', '1', '00', '0', 'http://file.szcjzhxq.com/statics/2023/06/12/qr1686562015119_20230612172629A332.jpg', '15288888888', '2023-06-12 17:26:31', '15288888888', '2023-06-12 17:26:31', '1');
INSERT INTO `bus_point` VALUES (24, 1, '613巡查点', '1', '613巡查点地址', '0', 'http://file.szcjzhxq.com/statics/2023/06/13/qr1686622178513_20230613100912A340.jpg', '15288888888', '2023-06-13 10:09:14', '15288888888', '2023-06-13 10:09:14', '0');
INSERT INTO `bus_point` VALUES (25, 1, '613巡查02', '1', '613巡查02地址', '0', 'http://file.szcjzhxq.com/statics/2023/06/13/qr1686639995262_20230613150609A352.jpg', '15288888888', '2023-06-13 15:06:11', '15288888888', '2023-06-13 15:06:11', '0');
INSERT INTO `bus_point` VALUES (26, 1, '613巡查03', '1', '613巡查03地址', '0', 'http://file.szcjzhxq.com/statics/2023/06/13/qr1686640007486_20230613150621A353.jpg', '15288888888', '2023-06-13 15:06:23', '15288888888', '2023-06-13 15:06:23', '0');
INSERT INTO `bus_point` VALUES (27, 1, '613巡更01', '2', '613巡更01地址', '0', 'http://file.szcjzhxq.com/statics/2023/06/13/qr1686640019180_20230613150633A354.jpg', '15288888888', '2023-06-13 15:06:35', '15288888888', '2023-06-13 15:06:35', '0');
INSERT INTO `bus_point` VALUES (28, 1, '613巡更02', '2', '613巡更02地址', '0', 'http://file.szcjzhxq.com/statics/2023/06/13/qr1686640031344_20230613150645A355.jpg', '15288888888', '2023-06-13 15:06:47', '15288888888', '2023-06-13 15:06:47', '0');
INSERT INTO `bus_point` VALUES (29, 7, 'Y1#101', '0', 'Y1#101户', '0', 'http://file.szcjzhxq.com/statics/2023/06/26/qr1687769715502_20230626165442A362.jpg', '18888888888', '2023-06-26 16:54:44', '18888888888', '2023-06-26 16:54:44', '0');

-- ----------------------------
-- Table structure for bus_route
-- ----------------------------
DROP TABLE IF EXISTS `bus_route`;
CREATE TABLE `bus_route`  (
  `route_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `route_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '路线名称',
  `route_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '状态：0=启用，1=停用',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`route_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡更路线管理表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_route
-- ----------------------------
INSERT INTO `bus_route` VALUES (1, 1, '巡更路线01', '1', '15288888888', '2023-05-15 18:42:53', '15288888888', '2023-05-15 18:43:36', '1');
INSERT INTO `bus_route` VALUES (2, 1, '巡更路线02', '1', '15288888888', '2023-05-15 18:43:01', '15288888888', '2023-05-15 18:43:31', '1');
INSERT INTO `bus_route` VALUES (3, 6, '小区各入口巡更路线', '0', '15656332018', '2023-05-31 11:10:27', '15656332018', '2023-05-31 11:10:27', '0');
INSERT INTO `bus_route` VALUES (4, 1, '一号巡更路线', '1', '15288888888', '2023-06-12 16:21:50', '15288888888', '2023-06-12 16:21:50', '1');
INSERT INTO `bus_route` VALUES (5, 1, '1号巡更路线', '1', '15288888888', '2023-06-12 16:38:40', '15288888888', '2023-06-12 16:38:40', '1');
INSERT INTO `bus_route` VALUES (6, 1, '2号巡更路线', '1', '15288888888', '2023-06-12 16:38:59', '15288888888', '2023-06-12 16:38:59', '1');
INSERT INTO `bus_route` VALUES (7, 1, '巡更路线', '0', '15288888888', '2023-06-13 15:41:31', '15288888888', '2023-06-13 15:41:31', '0');
INSERT INTO `bus_route` VALUES (8, 1, '巡更路线02', '0', '15288888888', '2023-06-13 15:42:56', '15288888888', '2023-06-13 15:42:56', '0');

-- ----------------------------
-- Table structure for bus_route_point
-- ----------------------------
DROP TABLE IF EXISTS `bus_route_point`;
CREATE TABLE `bus_route_point`  (
  `route_id` bigint NOT NULL DEFAULT 0 COMMENT '巡更路线id，表xccj_work.bus_route',
  `point_id` bigint NOT NULL DEFAULT 0 COMMENT '巡点id，表xccj_work.bus_point(point_type!=1)'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡更路线-巡点关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_route_point
-- ----------------------------
INSERT INTO `bus_route_point` VALUES (3, 9);
INSERT INTO `bus_route_point` VALUES (3, 11);
INSERT INTO `bus_route_point` VALUES (3, 12);
INSERT INTO `bus_route_point` VALUES (3, 10);
INSERT INTO `bus_route_point` VALUES (7, 27);
INSERT INTO `bus_route_point` VALUES (7, 28);
INSERT INTO `bus_route_point` VALUES (8, 27);

-- ----------------------------
-- Table structure for bus_tour
-- ----------------------------
DROP TABLE IF EXISTS `bus_tour`;
CREATE TABLE `bus_tour`  (
  `tour_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `estate_id` bigint NOT NULL DEFAULT 0 COMMENT '物业id，表xccj_estate_sys.bus_estate',
  `tour_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '类型：0=巡查类型，1=巡更类型，2=巡检类型',
  `tour_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '类型名称',
  `tour_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '状态：0=启用，1=停用',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`tour_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡X类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_tour
-- ----------------------------
INSERT INTO `bus_tour` VALUES (1, 2, '0', '测试巡查类型01', '0', '15288888888', '2023-05-15 17:58:16', '15288888888', '2023-05-15 17:58:16', '0');
INSERT INTO `bus_tour` VALUES (2, 2, '0', '测试巡查类型02', '0', '15288888888', '2023-05-15 17:58:19', '15288888888', '2023-05-15 17:58:19', '0');
INSERT INTO `bus_tour` VALUES (3, 2, '0', '测试巡查类型03', '0', '15288888888', '2023-05-15 17:58:23', '15288888888', '2023-05-15 17:58:23', '0');
INSERT INTO `bus_tour` VALUES (4, 2, '1', '巡更类型01', '0', '15288888888', '2023-05-15 19:00:32', '15288888888', '2023-05-15 19:00:32', '0');
INSERT INTO `bus_tour` VALUES (5, 2, '1', '巡更类型02', '0', '15288888888', '2023-05-15 19:00:35', '15288888888', '2023-05-15 19:00:35', '0');
INSERT INTO `bus_tour` VALUES (6, 2, '1', '巡更类型03', '0', '15288888888', '2023-05-15 19:00:38', '15288888888', '2023-05-15 19:00:38', '0');
INSERT INTO `bus_tour` VALUES (7, 2, '2', '巡检类型01', '0', '15288888888', '2023-05-15 19:06:27', '15288888888', '2023-05-15 19:06:27', '0');
INSERT INTO `bus_tour` VALUES (8, 2, '2', '巡检类型02', '0', '15288888888', '2023-05-15 19:06:30', '15288888888', '2023-05-15 19:06:30', '0');
INSERT INTO `bus_tour` VALUES (9, 2, '2', '巡检类型03', '0', '15288888888', '2023-05-15 19:06:33', '15288888888', '2023-05-15 19:06:33', '1');
INSERT INTO `bus_tour` VALUES (10, 2, '2', '巡检类型03', '0', '15288888888', '2023-05-15 19:08:24', '15288888888', '2023-05-15 19:08:24', '0');
INSERT INTO `bus_tour` VALUES (11, 5, '1', '自动任务巡更', '0', '15656332018', '2023-05-31 11:11:48', '15656332018', '2023-05-31 11:11:48', '0');
INSERT INTO `bus_tour` VALUES (12, 5, '0', '小区内各巡点巡查', '0', '15656332018', '2023-05-31 14:44:09', '15656332018', '2023-05-31 14:44:09', '0');
INSERT INTO `bus_tour` VALUES (13, 5, '1', '后台添加巡更任务', '0', '15656332018', '2023-05-31 14:56:53', '15656332018', '2023-05-31 14:56:53', '0');
INSERT INTO `bus_tour` VALUES (14, 5, '2', '后台添加巡检工单', '0', '15656332018', '2023-05-31 15:00:02', '15656332018', '2023-05-31 15:00:02', '0');
INSERT INTO `bus_tour` VALUES (15, 5, '2', '自动任务巡检工单', '0', '15656332018', '2023-05-31 15:00:13', '15656332018', '2023-05-31 15:00:13', '0');
INSERT INTO `bus_tour` VALUES (16, 6, '0', '公区巡查', '0', '18888888888', '2023-06-26 16:51:40', '18888888888', '2023-06-26 16:51:40', '0');
INSERT INTO `bus_tour` VALUES (17, 6, '0', '楼栋巡查', '0', '18888888888', '2023-06-26 16:51:59', '18888888888', '2023-06-26 16:51:59', '0');
INSERT INTO `bus_tour` VALUES (18, 6, '0', '装修巡查', '0', '18888888888', '2023-06-26 16:55:45', '18888888888', '2023-09-11 17:15:38', '1');
INSERT INTO `bus_tour` VALUES (19, 6, '2', '设备巡检', '0', '18888888888', '2023-06-26 17:18:39', '18888888888', '2023-06-26 17:18:39', '0');
INSERT INTO `bus_tour` VALUES (20, 6, '2', '消防巡检', '0', '18888888888', '2023-06-26 17:19:01', '18888888888', '2023-06-26 17:19:01', '0');
INSERT INTO `bus_tour` VALUES (21, 6, '2', '园区巡检', '0', '18888888888', '2023-06-26 17:19:10', '18888888888', '2023-06-26 17:19:10', '0');
INSERT INTO `bus_tour` VALUES (22, 6, '0', '巡查类型', '0', '18888888888', '2023-09-11 17:15:09', '18888888888', '2023-09-11 17:15:09', '1');
INSERT INTO `bus_tour` VALUES (23, 6, '0', '装修巡查', '0', '18888888888', '2023-09-11 17:23:44', '18888888888', '2023-09-11 17:23:44', '0');
INSERT INTO `bus_tour` VALUES (24, 6, '0', '房屋巡查', '0', '18888888888', '2023-09-11 20:52:47', '18888888888', '2023-09-11 20:52:47', '0');
INSERT INTO `bus_tour` VALUES (25, 4, '0', '12', '0', '15200000000', '2023-10-23 16:46:28', '15200000000', '2023-10-23 16:46:28', '0');

-- ----------------------------
-- Table structure for bus_watchman
-- ----------------------------
DROP TABLE IF EXISTS `bus_watchman`;
CREATE TABLE `bus_watchman`  (
  `watchman_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `watchman_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '工单编号',
  `watchman_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '工单名称',
  `tour_id` bigint NOT NULL DEFAULT 0 COMMENT '巡更类型id，表xccj_work.bus_tour（tour_type=1）',
  `route_id` bigint NOT NULL DEFAULT 0 COMMENT '巡更路线id，表xccj_work.bus_route',
  `data_source` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单来源：0=计划生成，1=后台添加，2=其他工单',
  `plan_id` bigint NOT NULL DEFAULT 0 COMMENT '计划id，表xccj_work.bus_watchman_plan（data_source=0有值）',
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '其他工单编号（data_source=2有值）',
  `allot_way` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单分配方式：0=自动分配，1=管理员分配，2=抢单',
  `watchman_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '巡更顺序：0=顺序，1=乱序',
  `patrol_num` int NOT NULL DEFAULT 0 COMMENT '巡更次数/天次',
  `gap_time` decimal(10, 2) NOT NULL COMMENT '每次巡更间隔时间（一日多查时有值）',
  `start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
  `staff_id` bigint NOT NULL DEFAULT 0 COMMENT '负责人id，表xccj_community.bus_staff',
  `assist_num` int NOT NULL DEFAULT 0 COMMENT '协助人数',
  `transfer_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '转交状态：0=无需转交，1=转交待确认',
  `allot_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '分配状态：0=待分配，1=已分配',
  `accept_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '接单状态：0=待接单，1=已接单',
  `execute_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '执行状态：0=待执行，1=执行中，2=已完成，3=超时完成，4=超时未完成',
  `execute_id` bigint NOT NULL DEFAULT 0 COMMENT '执行人id，表xccj_community.bus_staff',
  `execute_time` datetime NULL DEFAULT NULL COMMENT '执行时间',
  `finish_time` datetime NULL DEFAULT NULL COMMENT '完成时间',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '完成图片',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`watchman_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 348 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡更工单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_watchman
-- ----------------------------
INSERT INTO `bus_watchman` VALUES (1, 1, 'XG-20230515-287009', '巡更工单01', 4, 1, '1', 0, '', '0', '0', 0, 1.00, '2023-05-15 21:00:00', '2023-05-15 22:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '无巡更备注', '15288888888', '2023-05-15 19:01:44', '15288888888', '2023-05-15 19:01:44', '1');
INSERT INTO `bus_watchman` VALUES (2, 1, 'XG-20230515-577860', '巡更工单02', 5, 2, '1', 0, '', '2', '1', 0, 1.00, '2023-05-15 00:00:00', '2023-05-16 00:00:00', 1, 0, '0', '1', '1', '1', 0, '2023-06-06 15:38:22', NULL, '', '无巡更备注02', '15288888888', '2023-05-15 19:03:35', '15288888888', '2023-05-15 19:03:35', '0');
INSERT INTO `bus_watchman` VALUES (3, 1, 'XG-20230516-608174', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-05-16 19:00:00', '2023-05-16 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-15 23:59:50', '', '2023-05-15 23:59:50', '1');
INSERT INTO `bus_watchman` VALUES (4, 1, 'XG-20230517-538490', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-05-17 19:00:00', '2023-05-17 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-16 23:59:49', '', '2023-05-16 23:59:49', '1');
INSERT INTO `bus_watchman` VALUES (5, 1, 'XG-20230518-19261', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-05-18 19:00:00', '2023-05-18 20:00:00', 1, 0, '0', '1', '1', '1', 0, '2023-05-23 16:02:56', NULL, '', '', '', '2023-05-17 23:59:49', '', '2023-05-17 23:59:49', '0');
INSERT INTO `bus_watchman` VALUES (6, 1, 'XG-20230519-994670', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-05-19 19:00:00', '2023-05-19 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-18 23:59:48', '', '2023-05-18 23:59:48', '1');
INSERT INTO `bus_watchman` VALUES (7, 1, 'XG-20230520-893930', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-05-20 19:00:00', '2023-05-20 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-19 23:59:48', '', '2023-05-19 23:59:48', '1');
INSERT INTO `bus_watchman` VALUES (8, 1, 'XG-20230521-191827', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-05-21 19:00:00', '2023-05-21 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-20 23:59:47', '', '2023-05-20 23:59:47', '1');
INSERT INTO `bus_watchman` VALUES (9, 1, 'XG-20230522-975897', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-05-22 19:00:00', '2023-05-22 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-21 23:59:47', '', '2023-05-21 23:59:47', '1');
INSERT INTO `bus_watchman` VALUES (10, 1, 'XG-20230523-916506', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-05-23 19:00:00', '2023-05-23 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-22 23:59:47', '', '2023-05-22 23:59:47', '1');
INSERT INTO `bus_watchman` VALUES (11, 1, 'XG-20230524-378870', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-05-24 19:00:00', '2023-05-24 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-23 23:59:46', '', '2023-05-23 23:59:46', '1');
INSERT INTO `bus_watchman` VALUES (12, 1, 'XG-20230525-388834', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-05-25 19:00:00', '2023-05-25 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-24 23:59:46', '', '2023-05-24 23:59:46', '1');
INSERT INTO `bus_watchman` VALUES (13, 1, 'XG-20230526-209934', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-05-26 19:00:00', '2023-05-26 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-25 23:59:45', '', '2023-05-25 23:59:45', '1');
INSERT INTO `bus_watchman` VALUES (14, 1, 'XG-20230527-936090', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-05-27 19:00:00', '2023-05-27 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-26 23:59:45', '', '2023-05-26 23:59:45', '1');
INSERT INTO `bus_watchman` VALUES (15, 1, 'XG-20230528-409459', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-05-28 19:00:00', '2023-05-28 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-27 23:59:44', '', '2023-05-27 23:59:44', '1');
INSERT INTO `bus_watchman` VALUES (16, 1, 'XG-20230529-899775', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-05-29 19:00:00', '2023-05-29 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-28 23:59:44', '', '2023-05-28 23:59:44', '1');
INSERT INTO `bus_watchman` VALUES (17, 1, 'XG-20230530-149386', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-05-30 19:00:00', '2023-05-30 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-29 23:59:43', '', '2023-05-29 23:59:43', '1');
INSERT INTO `bus_watchman` VALUES (18, 1, 'XG-20230531-676826', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-05-31 19:00:00', '2023-05-31 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-30 23:59:43', '', '2023-05-30 23:59:43', '1');
INSERT INTO `bus_watchman` VALUES (19, 6, 'XG-20230531-500019', '2023/5/31日小区内巡更任务', 13, 3, '1', 0, '', '0', '0', 0, 1.00, '2023-05-31 00:00:00', '2023-06-01 00:00:00', 14, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '15656332018', '2023-05-31 14:58:14', '15656332018', '2023-05-31 14:58:14', '0');
INSERT INTO `bus_watchman` VALUES (20, 1, 'XG-20230601-491068', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-01 19:00:00', '2023-06-01 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-31 23:59:42', '', '2023-05-31 23:59:42', '1');
INSERT INTO `bus_watchman` VALUES (21, 6, 'XG-20230601-166209', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-01 08:30:00', '2023-06-01 12:00:00', 17, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-05-31 23:59:42', '', '2023-05-31 23:59:42', '0');
INSERT INTO `bus_watchman` VALUES (22, 1, 'XG-20230602-227985', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-02 19:00:00', '2023-06-02 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-01 23:59:42', '', '2023-06-01 23:59:42', '1');
INSERT INTO `bus_watchman` VALUES (23, 6, 'XG-20230602-544623', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-02 08:30:00', '2023-06-02 12:00:00', 15, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-01 23:59:42', '', '2023-06-01 23:59:42', '0');
INSERT INTO `bus_watchman` VALUES (24, 1, 'XG-20230603-909229', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-03 19:00:00', '2023-06-03 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-02 23:59:42', '', '2023-06-02 23:59:42', '1');
INSERT INTO `bus_watchman` VALUES (25, 6, 'XG-20230603-329140', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-03 08:30:00', '2023-06-03 12:00:00', 22, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-02 23:59:42', '', '2023-06-02 23:59:42', '0');
INSERT INTO `bus_watchman` VALUES (26, 1, 'XG-20230604-549417', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-04 19:00:00', '2023-06-04 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-03 23:59:41', '', '2023-06-03 23:59:41', '1');
INSERT INTO `bus_watchman` VALUES (27, 6, 'XG-20230604-975796', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-04 08:30:00', '2023-06-04 12:00:00', 21, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-03 23:59:41', '', '2023-06-03 23:59:41', '0');
INSERT INTO `bus_watchman` VALUES (28, 1, 'XG-20230605-524158', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-05 19:00:00', '2023-06-05 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-04 23:59:41', '', '2023-06-04 23:59:41', '1');
INSERT INTO `bus_watchman` VALUES (29, 6, 'XG-20230605-475', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-05 08:30:00', '2023-06-05 12:00:00', 20, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-04 23:59:41', '', '2023-06-04 23:59:41', '0');
INSERT INTO `bus_watchman` VALUES (30, 1, 'XG-20230606-532104', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-06 19:00:00', '2023-06-06 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-05 23:59:40', '', '2023-06-05 23:59:40', '1');
INSERT INTO `bus_watchman` VALUES (31, 6, 'XG-20230606-76518', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-06 08:30:00', '2023-06-06 12:00:00', 16, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-05 23:59:40', '', '2023-06-05 23:59:40', '0');
INSERT INTO `bus_watchman` VALUES (32, 1, 'XG-20230607-415772', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-07 19:00:00', '2023-06-07 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-06 23:59:40', '', '2023-06-06 23:59:40', '1');
INSERT INTO `bus_watchman` VALUES (33, 6, 'XG-20230607-388081', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-07 08:30:00', '2023-06-07 12:00:00', 19, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-06 23:59:40', '', '2023-06-06 23:59:40', '0');
INSERT INTO `bus_watchman` VALUES (34, 1, 'XG-20230608-432876', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-08 19:00:00', '2023-06-08 20:00:00', 39, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-07 23:59:39', '', '2023-06-07 23:59:39', '0');
INSERT INTO `bus_watchman` VALUES (35, 6, 'XG-20230608-809002', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-08 08:30:00', '2023-06-08 12:00:00', 24, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-07 23:59:39', '', '2023-06-07 23:59:39', '0');
INSERT INTO `bus_watchman` VALUES (36, 1, 'XG-20230609-604307', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-09 19:00:00', '2023-06-09 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-08 23:59:39', '', '2023-06-08 23:59:39', '1');
INSERT INTO `bus_watchman` VALUES (37, 6, 'XG-20230609-73107', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-09 08:30:00', '2023-06-09 12:00:00', 23, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-08 23:59:39', '', '2023-06-08 23:59:39', '0');
INSERT INTO `bus_watchman` VALUES (38, 1, 'XG-20230610-660798', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-10 19:00:00', '2023-06-10 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-09 23:59:38', '', '2023-06-09 23:59:38', '1');
INSERT INTO `bus_watchman` VALUES (39, 6, 'XG-20230610-206102', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-10 08:30:00', '2023-06-10 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-09 23:59:38', '', '2023-06-09 23:59:38', '0');
INSERT INTO `bus_watchman` VALUES (40, 1, 'XG-20230611-617089', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-11 19:00:00', '2023-06-11 20:00:00', 1, 0, '0', '1', '1', '1', 0, '2023-06-13 09:21:31', NULL, '', '', '', '2023-06-10 23:59:38', '', '2023-06-10 23:59:38', '0');
INSERT INTO `bus_watchman` VALUES (41, 6, 'XG-20230611-598631', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-11 08:30:00', '2023-06-11 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-10 23:59:38', '', '2023-06-10 23:59:38', '0');
INSERT INTO `bus_watchman` VALUES (42, 1, 'XG-20230612-825398', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-12 19:00:00', '2023-06-12 20:00:00', 1, 0, '0', '1', '1', '1', 0, '2023-06-12 16:16:23', NULL, '', '', '', '2023-06-11 23:59:37', '', '2023-06-11 23:59:37', '0');
INSERT INTO `bus_watchman` VALUES (43, 6, 'XG-20230612-252298', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-12 08:30:00', '2023-06-12 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-11 23:59:37', '', '2023-06-11 23:59:37', '0');
INSERT INTO `bus_watchman` VALUES (44, 1, 'XG-20230613-726378', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-13 19:00:00', '2023-06-13 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-12 23:59:37', '', '2023-06-12 23:59:37', '1');
INSERT INTO `bus_watchman` VALUES (45, 6, 'XG-20230613-463688', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-13 08:30:00', '2023-06-13 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-12 23:59:37', '', '2023-06-12 23:59:37', '0');
INSERT INTO `bus_watchman` VALUES (46, 1, 'XG-20230613-514815', 'TS613工单01', 6, 7, '1', 0, '', '2', '0', 0, 0.00, '2023-06-13 00:00:00', '2023-06-15 00:00:00', 1, 0, '0', '1', '1', '1', 0, '2023-06-13 16:06:23', NULL, '', '', '15288888888', '2023-06-13 16:03:22', '15288888888', '2023-06-13 16:03:22', '0');
INSERT INTO `bus_watchman` VALUES (47, 1, 'XG-20230613-47261', '巡更工单', 4, 7, '1', 0, '', '2', '1', 0, 0.00, '2023-06-13 00:00:00', '2023-06-14 00:00:00', 1, 0, '1', '1', '0', '0', 0, NULL, NULL, '', '', '15288888888', '2023-06-13 16:43:15', '15288888888', '2023-06-13 16:43:15', '0');
INSERT INTO `bus_watchman` VALUES (48, 1, 'XG-20230614-301763', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-14 19:00:00', '2023-06-14 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-13 23:59:36', '', '2023-06-13 23:59:36', '0');
INSERT INTO `bus_watchman` VALUES (49, 6, 'XG-20230614-55013', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-14 08:30:00', '2023-06-14 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-13 23:59:36', '', '2023-06-13 23:59:36', '0');
INSERT INTO `bus_watchman` VALUES (50, 1, 'XG-20230615-158428', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-15 19:00:00', '2023-06-15 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-14 23:59:36', '', '2023-06-14 23:59:36', '0');
INSERT INTO `bus_watchman` VALUES (51, 6, 'XG-20230615-249764', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-15 08:30:00', '2023-06-15 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-14 23:59:36', '', '2023-06-14 23:59:36', '0');
INSERT INTO `bus_watchman` VALUES (52, 1, 'XG-20230616-397765', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-16 19:00:00', '2023-06-16 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-15 23:59:35', '', '2023-06-15 23:59:35', '0');
INSERT INTO `bus_watchman` VALUES (53, 6, 'XG-20230616-198934', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-16 08:30:00', '2023-06-16 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-15 23:59:35', '', '2023-06-15 23:59:35', '0');
INSERT INTO `bus_watchman` VALUES (54, 1, 'XG-20230617-982969', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-17 19:00:00', '2023-06-17 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-16 23:59:35', '', '2023-06-16 23:59:35', '0');
INSERT INTO `bus_watchman` VALUES (55, 6, 'XG-20230617-158304', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-17 08:30:00', '2023-06-17 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-16 23:59:35', '', '2023-06-16 23:59:35', '0');
INSERT INTO `bus_watchman` VALUES (56, 1, 'XG-20230618-724921', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-18 19:00:00', '2023-06-18 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-17 23:59:34', '', '2023-06-17 23:59:34', '0');
INSERT INTO `bus_watchman` VALUES (57, 6, 'XG-20230618-76727', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-18 08:30:00', '2023-06-18 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-17 23:59:34', '', '2023-06-17 23:59:34', '0');
INSERT INTO `bus_watchman` VALUES (58, 1, 'XG-20230619-680334', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-19 19:00:00', '2023-06-19 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-18 23:59:34', '', '2023-06-18 23:59:34', '0');
INSERT INTO `bus_watchman` VALUES (59, 6, 'XG-20230619-202128', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-19 08:30:00', '2023-06-19 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-18 23:59:34', '', '2023-06-18 23:59:34', '0');
INSERT INTO `bus_watchman` VALUES (60, 1, 'XG-20230620-383073', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-20 19:00:00', '2023-06-20 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-19 23:59:33', '', '2023-06-19 23:59:33', '0');
INSERT INTO `bus_watchman` VALUES (61, 6, 'XG-20230620-290999', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-20 08:30:00', '2023-06-20 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-19 23:59:33', '', '2023-06-19 23:59:33', '0');
INSERT INTO `bus_watchman` VALUES (62, 1, 'XG-20230621-422842', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-21 19:00:00', '2023-06-21 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-20 23:59:33', '', '2023-06-20 23:59:33', '0');
INSERT INTO `bus_watchman` VALUES (63, 6, 'XG-20230621-916835', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-21 08:30:00', '2023-06-21 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-20 23:59:33', '', '2023-06-20 23:59:33', '0');
INSERT INTO `bus_watchman` VALUES (64, 1, 'XG-20230622-725478', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-22 19:00:00', '2023-06-22 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-21 23:59:32', '', '2023-06-21 23:59:32', '0');
INSERT INTO `bus_watchman` VALUES (65, 6, 'XG-20230622-6434', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-22 08:30:00', '2023-06-22 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-21 23:59:32', '', '2023-06-21 23:59:32', '0');
INSERT INTO `bus_watchman` VALUES (66, 1, 'XG-20230623-424474', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-23 19:00:00', '2023-06-23 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-22 23:59:32', '', '2023-06-22 23:59:32', '0');
INSERT INTO `bus_watchman` VALUES (67, 6, 'XG-20230623-521912', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-23 08:30:00', '2023-06-23 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-22 23:59:32', '', '2023-06-22 23:59:32', '0');
INSERT INTO `bus_watchman` VALUES (68, 1, 'XG-20230624-401756', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-24 19:00:00', '2023-06-24 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-23 23:59:31', '', '2023-06-23 23:59:31', '0');
INSERT INTO `bus_watchman` VALUES (69, 6, 'XG-20230624-702957', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-24 08:30:00', '2023-06-24 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-23 23:59:31', '', '2023-06-23 23:59:31', '0');
INSERT INTO `bus_watchman` VALUES (70, 1, 'XG-20230625-208072', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-25 19:00:00', '2023-06-25 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-24 23:59:31', '', '2023-06-24 23:59:31', '0');
INSERT INTO `bus_watchman` VALUES (71, 6, 'XG-20230625-838810', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-25 08:30:00', '2023-06-25 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-24 23:59:31', '', '2023-06-24 23:59:31', '0');
INSERT INTO `bus_watchman` VALUES (72, 1, 'XG-20230626-71878', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-26 19:00:00', '2023-06-26 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-25 23:59:30', '', '2023-06-25 23:59:30', '0');
INSERT INTO `bus_watchman` VALUES (73, 6, 'XG-20230626-215685', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-26 08:30:00', '2023-06-26 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-25 23:59:30', '', '2023-06-25 23:59:30', '0');
INSERT INTO `bus_watchman` VALUES (74, 1, 'XG-20230627-761481', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-27 19:00:00', '2023-06-27 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-26 23:59:30', '', '2023-06-26 23:59:30', '0');
INSERT INTO `bus_watchman` VALUES (75, 6, 'XG-20230627-656678', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-27 08:30:00', '2023-06-27 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-26 23:59:30', '', '2023-06-26 23:59:30', '0');
INSERT INTO `bus_watchman` VALUES (76, 1, 'XG-20230628-588563', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-28 19:00:00', '2023-06-28 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-27 23:59:29', '', '2023-06-27 23:59:29', '0');
INSERT INTO `bus_watchman` VALUES (77, 6, 'XG-20230628-389119', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-28 08:30:00', '2023-06-28 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-27 23:59:29', '', '2023-06-27 23:59:29', '0');
INSERT INTO `bus_watchman` VALUES (78, 1, 'XG-20230629-629336', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-29 19:00:00', '2023-06-29 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-28 23:59:29', '', '2023-06-28 23:59:29', '0');
INSERT INTO `bus_watchman` VALUES (79, 6, 'XG-20230629-239602', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-29 08:30:00', '2023-06-29 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-28 23:59:29', '', '2023-06-28 23:59:29', '0');
INSERT INTO `bus_watchman` VALUES (80, 1, 'XG-20230630-399283', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-06-30 19:00:00', '2023-06-30 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-29 23:59:28', '', '2023-06-29 23:59:28', '0');
INSERT INTO `bus_watchman` VALUES (81, 6, 'XG-20230630-828940', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-06-30 08:30:00', '2023-06-30 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-29 23:59:28', '', '2023-06-29 23:59:28', '0');
INSERT INTO `bus_watchman` VALUES (82, 1, 'XG-20230701-910113', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-01 19:00:00', '2023-07-01 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-30 23:59:28', '', '2023-06-30 23:59:28', '0');
INSERT INTO `bus_watchman` VALUES (83, 6, 'XG-20230701-669998', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-01 08:30:00', '2023-07-01 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-06-30 23:59:28', '', '2023-06-30 23:59:28', '0');
INSERT INTO `bus_watchman` VALUES (84, 1, 'XG-20230702-383291', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-02 19:00:00', '2023-07-02 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-01 23:59:27', '', '2023-07-01 23:59:27', '0');
INSERT INTO `bus_watchman` VALUES (85, 6, 'XG-20230702-457646', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-02 08:30:00', '2023-07-02 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-01 23:59:27', '', '2023-07-01 23:59:27', '0');
INSERT INTO `bus_watchman` VALUES (86, 1, 'XG-20230703-503025', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-03 19:00:00', '2023-07-03 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-02 23:59:27', '', '2023-07-02 23:59:27', '0');
INSERT INTO `bus_watchman` VALUES (87, 6, 'XG-20230703-468461', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-03 08:30:00', '2023-07-03 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-02 23:59:27', '', '2023-07-02 23:59:27', '0');
INSERT INTO `bus_watchman` VALUES (88, 1, 'XG-20230704-694290', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-04 19:00:00', '2023-07-04 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-03 23:59:26', '', '2023-07-03 23:59:26', '0');
INSERT INTO `bus_watchman` VALUES (89, 6, 'XG-20230704-239505', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-04 08:30:00', '2023-07-04 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-03 23:59:26', '', '2023-07-03 23:59:26', '0');
INSERT INTO `bus_watchman` VALUES (90, 1, 'XG-20230705-266529', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-05 19:00:00', '2023-07-05 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-04 23:59:25', '', '2023-07-04 23:59:25', '0');
INSERT INTO `bus_watchman` VALUES (91, 6, 'XG-20230705-486918', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-05 08:30:00', '2023-07-05 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-04 23:59:25', '', '2023-07-04 23:59:25', '0');
INSERT INTO `bus_watchman` VALUES (92, 1, 'XG-20230706-356095', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-06 19:00:00', '2023-07-06 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-05 23:59:25', '', '2023-07-05 23:59:25', '0');
INSERT INTO `bus_watchman` VALUES (93, 6, 'XG-20230706-207534', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-06 08:30:00', '2023-07-06 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-05 23:59:25', '', '2023-07-05 23:59:25', '0');
INSERT INTO `bus_watchman` VALUES (94, 1, 'XG-20230707-579631', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-07 19:00:00', '2023-07-07 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-06 23:59:24', '', '2023-07-06 23:59:24', '0');
INSERT INTO `bus_watchman` VALUES (95, 6, 'XG-20230707-910261', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-07 08:30:00', '2023-07-07 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-06 23:59:24', '', '2023-07-06 23:59:24', '0');
INSERT INTO `bus_watchman` VALUES (96, 1, 'XG-20230708-964104', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-08 19:00:00', '2023-07-08 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-07 23:59:24', '', '2023-07-07 23:59:24', '0');
INSERT INTO `bus_watchman` VALUES (97, 6, 'XG-20230708-136176', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-08 08:30:00', '2023-07-08 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-07 23:59:24', '', '2023-07-07 23:59:24', '0');
INSERT INTO `bus_watchman` VALUES (98, 1, 'XG-20230709-553756', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-09 19:00:00', '2023-07-09 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-08 23:59:23', '', '2023-07-08 23:59:23', '0');
INSERT INTO `bus_watchman` VALUES (99, 6, 'XG-20230709-817125', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-09 08:30:00', '2023-07-09 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-08 23:59:23', '', '2023-07-08 23:59:23', '0');
INSERT INTO `bus_watchman` VALUES (100, 1, 'XG-20230710-450481', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-10 19:00:00', '2023-07-10 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-09 23:59:23', '', '2023-07-09 23:59:23', '0');
INSERT INTO `bus_watchman` VALUES (101, 6, 'XG-20230710-263804', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-10 08:30:00', '2023-07-10 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-09 23:59:23', '', '2023-07-09 23:59:23', '0');
INSERT INTO `bus_watchman` VALUES (102, 1, 'XG-20230711-454607', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-11 19:00:00', '2023-07-11 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-10 23:59:22', '', '2023-07-10 23:59:22', '0');
INSERT INTO `bus_watchman` VALUES (103, 6, 'XG-20230711-27624', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-11 08:30:00', '2023-07-11 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-10 23:59:22', '', '2023-07-10 23:59:22', '0');
INSERT INTO `bus_watchman` VALUES (104, 1, 'XG-20230712-842736', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-12 19:00:00', '2023-07-12 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-11 23:59:22', '', '2023-07-11 23:59:22', '0');
INSERT INTO `bus_watchman` VALUES (105, 6, 'XG-20230712-998590', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-12 08:30:00', '2023-07-12 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-11 23:59:22', '', '2023-07-11 23:59:22', '0');
INSERT INTO `bus_watchman` VALUES (106, 1, 'XG-20230713-188630', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-13 19:00:00', '2023-07-13 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-12 23:59:21', '', '2023-07-12 23:59:21', '0');
INSERT INTO `bus_watchman` VALUES (107, 6, 'XG-20230713-196546', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-13 08:30:00', '2023-07-13 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-12 23:59:21', '', '2023-07-12 23:59:21', '0');
INSERT INTO `bus_watchman` VALUES (108, 1, 'XG-20230714-343100', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-14 19:00:00', '2023-07-14 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-13 23:59:21', '', '2023-07-13 23:59:21', '0');
INSERT INTO `bus_watchman` VALUES (109, 6, 'XG-20230714-977695', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-14 08:30:00', '2023-07-14 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-13 23:59:21', '', '2023-07-13 23:59:21', '0');
INSERT INTO `bus_watchman` VALUES (110, 1, 'XG-20230715-790632', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-15 19:00:00', '2023-07-15 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-14 23:59:20', '', '2023-07-14 23:59:20', '0');
INSERT INTO `bus_watchman` VALUES (111, 6, 'XG-20230715-700976', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-15 08:30:00', '2023-07-15 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-14 23:59:20', '', '2023-07-14 23:59:20', '0');
INSERT INTO `bus_watchman` VALUES (112, 1, 'XG-20230716-763200', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-16 19:00:00', '2023-07-16 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-15 23:59:20', '', '2023-07-15 23:59:20', '0');
INSERT INTO `bus_watchman` VALUES (113, 6, 'XG-20230716-761559', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-16 08:30:00', '2023-07-16 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-15 23:59:20', '', '2023-07-15 23:59:20', '0');
INSERT INTO `bus_watchman` VALUES (114, 1, 'XG-20230717-511280', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-17 19:00:00', '2023-07-17 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-16 23:59:19', '', '2023-07-16 23:59:19', '0');
INSERT INTO `bus_watchman` VALUES (115, 6, 'XG-20230717-854180', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-17 08:30:00', '2023-07-17 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-16 23:59:19', '', '2023-07-16 23:59:19', '0');
INSERT INTO `bus_watchman` VALUES (116, 1, 'XG-20230718-848397', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-18 19:00:00', '2023-07-18 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-17 23:59:19', '', '2023-07-17 23:59:19', '0');
INSERT INTO `bus_watchman` VALUES (117, 6, 'XG-20230718-440518', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-18 08:30:00', '2023-07-18 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-17 23:59:19', '', '2023-07-17 23:59:19', '0');
INSERT INTO `bus_watchman` VALUES (118, 1, 'XG-20230719-139812', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-19 19:00:00', '2023-07-19 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-18 23:59:18', '', '2023-07-18 23:59:18', '0');
INSERT INTO `bus_watchman` VALUES (119, 6, 'XG-20230719-413493', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-19 08:30:00', '2023-07-19 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-18 23:59:18', '', '2023-07-18 23:59:18', '0');
INSERT INTO `bus_watchman` VALUES (120, 1, 'XG-20230720-651045', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-20 19:00:00', '2023-07-20 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-19 23:59:18', '', '2023-07-19 23:59:18', '0');
INSERT INTO `bus_watchman` VALUES (121, 6, 'XG-20230720-269410', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-20 08:30:00', '2023-07-20 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-19 23:59:18', '', '2023-07-19 23:59:18', '0');
INSERT INTO `bus_watchman` VALUES (122, 1, 'XG-20230721-850095', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-21 19:00:00', '2023-07-21 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-20 23:59:17', '', '2023-07-20 23:59:17', '0');
INSERT INTO `bus_watchman` VALUES (123, 6, 'XG-20230721-569354', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-21 08:30:00', '2023-07-21 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-20 23:59:17', '', '2023-07-20 23:59:17', '0');
INSERT INTO `bus_watchman` VALUES (124, 1, 'XG-20230722-692881', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-22 19:00:00', '2023-07-22 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-21 23:59:17', '', '2023-07-21 23:59:17', '0');
INSERT INTO `bus_watchman` VALUES (125, 6, 'XG-20230722-454691', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-22 08:30:00', '2023-07-22 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-21 23:59:17', '', '2023-07-21 23:59:17', '0');
INSERT INTO `bus_watchman` VALUES (126, 1, 'XG-20230723-634798', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-23 19:00:00', '2023-07-23 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-22 23:59:16', '', '2023-07-22 23:59:16', '0');
INSERT INTO `bus_watchman` VALUES (127, 6, 'XG-20230723-981439', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-23 08:30:00', '2023-07-23 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-22 23:59:16', '', '2023-07-22 23:59:16', '0');
INSERT INTO `bus_watchman` VALUES (128, 1, 'XG-20230724-587287', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-24 19:00:00', '2023-07-24 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-23 23:59:16', '', '2023-07-23 23:59:16', '0');
INSERT INTO `bus_watchman` VALUES (129, 6, 'XG-20230724-772092', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-24 08:30:00', '2023-07-24 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-23 23:59:16', '', '2023-07-23 23:59:16', '0');
INSERT INTO `bus_watchman` VALUES (130, 1, 'XG-20230725-437403', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-25 19:00:00', '2023-07-25 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-24 23:59:15', '', '2023-07-24 23:59:15', '0');
INSERT INTO `bus_watchman` VALUES (131, 6, 'XG-20230725-507355', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-25 08:30:00', '2023-07-25 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-24 23:59:15', '', '2023-07-24 23:59:15', '0');
INSERT INTO `bus_watchman` VALUES (132, 1, 'XG-20230726-990930', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-26 19:00:00', '2023-07-26 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-25 23:59:15', '', '2023-07-25 23:59:15', '0');
INSERT INTO `bus_watchman` VALUES (133, 6, 'XG-20230726-467950', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-26 08:30:00', '2023-07-26 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-25 23:59:15', '', '2023-07-25 23:59:15', '0');
INSERT INTO `bus_watchman` VALUES (134, 1, 'XG-20230727-719780', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-27 19:00:00', '2023-07-27 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-26 23:59:14', '', '2023-07-26 23:59:14', '0');
INSERT INTO `bus_watchman` VALUES (135, 6, 'XG-20230727-28163', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-27 08:30:00', '2023-07-27 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-26 23:59:14', '', '2023-07-26 23:59:14', '0');
INSERT INTO `bus_watchman` VALUES (136, 1, 'XG-20230728-307596', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-28 19:00:00', '2023-07-28 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-27 23:59:14', '', '2023-07-27 23:59:14', '0');
INSERT INTO `bus_watchman` VALUES (137, 6, 'XG-20230728-454240', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-28 08:30:00', '2023-07-28 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-27 23:59:14', '', '2023-07-27 23:59:14', '0');
INSERT INTO `bus_watchman` VALUES (138, 1, 'XG-20230729-968311', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-29 19:00:00', '2023-07-29 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-28 23:59:13', '', '2023-07-28 23:59:13', '0');
INSERT INTO `bus_watchman` VALUES (139, 6, 'XG-20230729-235180', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-29 08:30:00', '2023-07-29 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-28 23:59:13', '', '2023-07-28 23:59:13', '0');
INSERT INTO `bus_watchman` VALUES (140, 1, 'XG-20230730-184262', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-30 19:00:00', '2023-07-30 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-29 23:59:13', '', '2023-07-29 23:59:13', '0');
INSERT INTO `bus_watchman` VALUES (141, 6, 'XG-20230730-231123', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-30 08:30:00', '2023-07-30 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-29 23:59:13', '', '2023-07-29 23:59:13', '0');
INSERT INTO `bus_watchman` VALUES (142, 1, 'XG-20230731-355435', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-07-31 19:00:00', '2023-07-31 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-30 23:59:12', '', '2023-07-30 23:59:12', '0');
INSERT INTO `bus_watchman` VALUES (143, 6, 'XG-20230731-353827', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-07-31 08:30:00', '2023-07-31 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-30 23:59:12', '', '2023-07-30 23:59:12', '0');
INSERT INTO `bus_watchman` VALUES (144, 1, 'XG-20230801-513855', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-01 19:00:00', '2023-08-01 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-31 23:59:11', '', '2023-07-31 23:59:11', '0');
INSERT INTO `bus_watchman` VALUES (145, 6, 'XG-20230801-835389', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-01 08:30:00', '2023-08-01 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-07-31 23:59:11', '', '2023-07-31 23:59:11', '0');
INSERT INTO `bus_watchman` VALUES (146, 1, 'XG-20230802-133032', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-02 19:00:00', '2023-08-02 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-01 23:59:11', '', '2023-08-01 23:59:11', '0');
INSERT INTO `bus_watchman` VALUES (147, 6, 'XG-20230802-690245', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-02 08:30:00', '2023-08-02 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-01 23:59:11', '', '2023-08-01 23:59:11', '0');
INSERT INTO `bus_watchman` VALUES (148, 1, 'XG-20230803-518743', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-03 19:00:00', '2023-08-03 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-02 23:59:10', '', '2023-08-02 23:59:10', '0');
INSERT INTO `bus_watchman` VALUES (149, 6, 'XG-20230803-608579', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-03 08:30:00', '2023-08-03 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-02 23:59:10', '', '2023-08-02 23:59:10', '0');
INSERT INTO `bus_watchman` VALUES (150, 1, 'XG-20230804-583467', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-04 19:00:00', '2023-08-04 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-03 23:59:10', '', '2023-08-03 23:59:10', '0');
INSERT INTO `bus_watchman` VALUES (151, 6, 'XG-20230804-791804', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-04 08:30:00', '2023-08-04 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-03 23:59:10', '', '2023-08-03 23:59:10', '0');
INSERT INTO `bus_watchman` VALUES (152, 1, 'XG-20230805-153517', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-05 19:00:00', '2023-08-05 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-04 23:59:09', '', '2023-08-04 23:59:09', '0');
INSERT INTO `bus_watchman` VALUES (153, 6, 'XG-20230805-997069', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-05 08:30:00', '2023-08-05 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-04 23:59:09', '', '2023-08-04 23:59:09', '0');
INSERT INTO `bus_watchman` VALUES (154, 1, 'XG-20230806-250030', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-06 19:00:00', '2023-08-06 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-05 23:59:09', '', '2023-08-05 23:59:09', '0');
INSERT INTO `bus_watchman` VALUES (155, 6, 'XG-20230806-177046', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-06 08:30:00', '2023-08-06 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-05 23:59:09', '', '2023-08-05 23:59:09', '0');
INSERT INTO `bus_watchman` VALUES (156, 1, 'XG-20230807-47127', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-07 19:00:00', '2023-08-07 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-06 23:59:08', '', '2023-08-06 23:59:08', '0');
INSERT INTO `bus_watchman` VALUES (157, 6, 'XG-20230807-63059', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-07 08:30:00', '2023-08-07 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-06 23:59:08', '', '2023-08-06 23:59:08', '0');
INSERT INTO `bus_watchman` VALUES (158, 1, 'XG-20230808-335131', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-08 19:00:00', '2023-08-08 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-07 23:59:08', '', '2023-08-07 23:59:08', '0');
INSERT INTO `bus_watchman` VALUES (159, 6, 'XG-20230808-469350', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-08 08:30:00', '2023-08-08 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-07 23:59:08', '', '2023-08-07 23:59:08', '0');
INSERT INTO `bus_watchman` VALUES (160, 1, 'XG-20230809-987304', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-09 19:00:00', '2023-08-09 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-08 23:59:07', '', '2023-08-08 23:59:07', '0');
INSERT INTO `bus_watchman` VALUES (161, 6, 'XG-20230809-632866', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-09 08:30:00', '2023-08-09 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-08 23:59:07', '', '2023-08-08 23:59:07', '0');
INSERT INTO `bus_watchman` VALUES (162, 1, 'XG-20230810-12904', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-10 19:00:00', '2023-08-10 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-09 23:59:07', '', '2023-08-09 23:59:07', '0');
INSERT INTO `bus_watchman` VALUES (163, 6, 'XG-20230810-169095', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-10 08:30:00', '2023-08-10 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-09 23:59:07', '', '2023-08-09 23:59:07', '0');
INSERT INTO `bus_watchman` VALUES (164, 1, 'XG-20230811-368080', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-11 19:00:00', '2023-08-11 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-10 23:59:06', '', '2023-08-10 23:59:06', '0');
INSERT INTO `bus_watchman` VALUES (165, 6, 'XG-20230811-302196', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-11 08:30:00', '2023-08-11 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-10 23:59:06', '', '2023-08-10 23:59:06', '0');
INSERT INTO `bus_watchman` VALUES (166, 1, 'XG-20230812-245416', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-12 19:00:00', '2023-08-12 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-11 23:59:06', '', '2023-08-11 23:59:06', '0');
INSERT INTO `bus_watchman` VALUES (167, 6, 'XG-20230812-296799', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-12 08:30:00', '2023-08-12 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-11 23:59:06', '', '2023-08-11 23:59:06', '0');
INSERT INTO `bus_watchman` VALUES (168, 1, 'XG-20230813-643316', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-13 19:00:00', '2023-08-13 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-12 23:59:05', '', '2023-08-12 23:59:05', '0');
INSERT INTO `bus_watchman` VALUES (169, 6, 'XG-20230813-442369', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-13 08:30:00', '2023-08-13 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-12 23:59:05', '', '2023-08-12 23:59:05', '0');
INSERT INTO `bus_watchman` VALUES (170, 1, 'XG-20230814-559020', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-14 19:00:00', '2023-08-14 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-13 23:59:04', '', '2023-08-13 23:59:04', '0');
INSERT INTO `bus_watchman` VALUES (171, 6, 'XG-20230814-753264', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-14 08:30:00', '2023-08-14 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-13 23:59:04', '', '2023-08-13 23:59:04', '0');
INSERT INTO `bus_watchman` VALUES (172, 1, 'XG-20230815-917904', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-15 19:00:00', '2023-08-15 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-14 23:59:04', '', '2023-08-14 23:59:04', '0');
INSERT INTO `bus_watchman` VALUES (173, 6, 'XG-20230815-808273', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-15 08:30:00', '2023-08-15 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-14 23:59:04', '', '2023-08-14 23:59:04', '0');
INSERT INTO `bus_watchman` VALUES (174, 1, 'XG-20230816-467681', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-16 19:00:00', '2023-08-16 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-15 23:59:03', '', '2023-08-15 23:59:03', '0');
INSERT INTO `bus_watchman` VALUES (175, 6, 'XG-20230816-326284', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-16 08:30:00', '2023-08-16 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-15 23:59:03', '', '2023-08-15 23:59:03', '0');
INSERT INTO `bus_watchman` VALUES (176, 1, 'XG-20230817-238227', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-17 19:00:00', '2023-08-17 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-16 23:59:03', '', '2023-08-16 23:59:03', '0');
INSERT INTO `bus_watchman` VALUES (177, 6, 'XG-20230817-267918', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-17 08:30:00', '2023-08-17 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-16 23:59:03', '', '2023-08-16 23:59:03', '0');
INSERT INTO `bus_watchman` VALUES (178, 1, 'XG-20230818-248839', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-18 19:00:00', '2023-08-18 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-17 23:59:02', '', '2023-08-17 23:59:02', '0');
INSERT INTO `bus_watchman` VALUES (179, 6, 'XG-20230818-59553', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-18 08:30:00', '2023-08-18 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-17 23:59:02', '', '2023-08-17 23:59:02', '0');
INSERT INTO `bus_watchman` VALUES (180, 1, 'XG-20230819-221165', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-19 19:00:00', '2023-08-19 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-18 23:59:02', '', '2023-08-18 23:59:02', '0');
INSERT INTO `bus_watchman` VALUES (181, 6, 'XG-20230819-353911', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-19 08:30:00', '2023-08-19 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-18 23:59:02', '', '2023-08-18 23:59:02', '0');
INSERT INTO `bus_watchman` VALUES (182, 1, 'XG-20230820-484517', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-20 19:00:00', '2023-08-20 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-19 23:59:01', '', '2023-08-19 23:59:01', '0');
INSERT INTO `bus_watchman` VALUES (183, 6, 'XG-20230820-229454', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-20 08:30:00', '2023-08-20 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-19 23:59:01', '', '2023-08-19 23:59:01', '0');
INSERT INTO `bus_watchman` VALUES (184, 1, 'XG-20230821-968831', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-21 19:00:00', '2023-08-21 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-20 23:59:01', '', '2023-08-20 23:59:01', '0');
INSERT INTO `bus_watchman` VALUES (185, 6, 'XG-20230821-863230', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-21 08:30:00', '2023-08-21 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-20 23:59:01', '', '2023-08-20 23:59:01', '0');
INSERT INTO `bus_watchman` VALUES (186, 1, 'XG-20230822-37563', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-22 19:00:00', '2023-08-22 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-21 23:59:00', '', '2023-08-21 23:59:00', '0');
INSERT INTO `bus_watchman` VALUES (187, 6, 'XG-20230822-607040', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-22 08:30:00', '2023-08-22 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-21 23:59:00', '', '2023-08-21 23:59:00', '0');
INSERT INTO `bus_watchman` VALUES (188, 1, 'XG-20230823-751298', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-23 19:00:00', '2023-08-23 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-22 23:59:00', '', '2023-08-22 23:59:00', '0');
INSERT INTO `bus_watchman` VALUES (189, 6, 'XG-20230823-610590', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-23 08:30:00', '2023-08-23 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-22 23:59:00', '', '2023-08-22 23:59:00', '0');
INSERT INTO `bus_watchman` VALUES (190, 1, 'XG-20230824-830218', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-24 19:00:00', '2023-08-24 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-23 23:58:59', '', '2023-08-23 23:58:59', '0');
INSERT INTO `bus_watchman` VALUES (191, 6, 'XG-20230824-815251', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-24 08:30:00', '2023-08-24 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-23 23:58:59', '', '2023-08-23 23:58:59', '0');
INSERT INTO `bus_watchman` VALUES (192, 1, 'XG-20230825-971017', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-25 19:00:00', '2023-08-25 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-24 23:58:59', '', '2023-08-24 23:58:59', '0');
INSERT INTO `bus_watchman` VALUES (193, 6, 'XG-20230825-990228', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-25 08:30:00', '2023-08-25 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-24 23:58:59', '', '2023-08-24 23:58:59', '0');
INSERT INTO `bus_watchman` VALUES (194, 1, 'XG-20230826-496370', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-26 19:00:00', '2023-08-26 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-25 23:58:58', '', '2023-08-25 23:58:58', '0');
INSERT INTO `bus_watchman` VALUES (195, 6, 'XG-20230826-993180', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-26 08:30:00', '2023-08-26 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-25 23:58:58', '', '2023-08-25 23:58:58', '0');
INSERT INTO `bus_watchman` VALUES (196, 1, 'XG-20230827-618283', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-27 19:00:00', '2023-08-27 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-26 23:58:58', '', '2023-08-26 23:58:58', '0');
INSERT INTO `bus_watchman` VALUES (197, 6, 'XG-20230827-275856', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-27 08:30:00', '2023-08-27 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-26 23:58:58', '', '2023-08-26 23:58:58', '0');
INSERT INTO `bus_watchman` VALUES (198, 1, 'XG-20230828-16805', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-28 19:00:00', '2023-08-28 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-27 23:58:57', '', '2023-08-27 23:58:57', '0');
INSERT INTO `bus_watchman` VALUES (199, 6, 'XG-20230828-143068', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-28 08:30:00', '2023-08-28 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-27 23:58:57', '', '2023-08-27 23:58:57', '0');
INSERT INTO `bus_watchman` VALUES (200, 1, 'XG-20230829-670477', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-29 19:00:00', '2023-08-29 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-28 23:58:57', '', '2023-08-28 23:58:57', '0');
INSERT INTO `bus_watchman` VALUES (201, 6, 'XG-20230829-68147', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-29 08:30:00', '2023-08-29 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-28 23:58:57', '', '2023-08-28 23:58:57', '0');
INSERT INTO `bus_watchman` VALUES (202, 1, 'XG-20230830-329541', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-30 19:00:00', '2023-08-30 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-29 23:58:56', '', '2023-08-29 23:58:56', '0');
INSERT INTO `bus_watchman` VALUES (203, 6, 'XG-20230830-766010', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-30 08:30:00', '2023-08-30 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-29 23:58:56', '', '2023-08-29 23:58:56', '0');
INSERT INTO `bus_watchman` VALUES (204, 1, 'XG-20230831-478122', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-08-31 19:00:00', '2023-08-31 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-30 23:58:56', '', '2023-08-30 23:58:56', '0');
INSERT INTO `bus_watchman` VALUES (205, 6, 'XG-20230831-441142', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-08-31 08:30:00', '2023-08-31 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-30 23:58:56', '', '2023-08-30 23:58:56', '0');
INSERT INTO `bus_watchman` VALUES (206, 1, 'XG-20230901-508903', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-01 19:00:00', '2023-09-01 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-31 23:58:55', '', '2023-08-31 23:58:55', '0');
INSERT INTO `bus_watchman` VALUES (207, 6, 'XG-20230901-619775', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-01 08:30:00', '2023-09-01 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-08-31 23:58:55', '', '2023-08-31 23:58:55', '0');
INSERT INTO `bus_watchman` VALUES (208, 1, 'XG-20230902-482082', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-02 19:00:00', '2023-09-02 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-01 23:58:55', '', '2023-09-01 23:58:55', '0');
INSERT INTO `bus_watchman` VALUES (209, 6, 'XG-20230902-740526', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-02 08:30:00', '2023-09-02 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-01 23:58:55', '', '2023-09-01 23:58:55', '0');
INSERT INTO `bus_watchman` VALUES (210, 1, 'XG-20230903-928897', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-03 19:00:00', '2023-09-03 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-02 23:58:54', '', '2023-09-02 23:58:54', '0');
INSERT INTO `bus_watchman` VALUES (211, 6, 'XG-20230903-801746', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-03 08:30:00', '2023-09-03 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-02 23:58:54', '', '2023-09-02 23:58:54', '0');
INSERT INTO `bus_watchman` VALUES (212, 1, 'XG-20230904-111010', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-04 19:00:00', '2023-09-04 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-03 23:58:53', '', '2023-09-03 23:58:53', '0');
INSERT INTO `bus_watchman` VALUES (213, 6, 'XG-20230904-454475', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-04 08:30:00', '2023-09-04 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-03 23:58:53', '', '2023-09-03 23:58:53', '0');
INSERT INTO `bus_watchman` VALUES (214, 1, 'XG-20230905-564476', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-05 19:00:00', '2023-09-05 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-04 23:58:53', '', '2023-09-04 23:58:53', '0');
INSERT INTO `bus_watchman` VALUES (215, 6, 'XG-20230905-151798', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-05 08:30:00', '2023-09-05 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-04 23:58:53', '', '2023-09-04 23:58:53', '0');
INSERT INTO `bus_watchman` VALUES (216, 1, 'XG-20230906-81598', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-06 19:00:00', '2023-09-06 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-05 23:58:52', '', '2023-09-05 23:58:52', '0');
INSERT INTO `bus_watchman` VALUES (217, 6, 'XG-20230906-675740', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-06 08:30:00', '2023-09-06 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-05 23:58:52', '', '2023-09-05 23:58:52', '0');
INSERT INTO `bus_watchman` VALUES (218, 1, 'XG-20230907-696688', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-07 19:00:00', '2023-09-07 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-06 23:58:52', '', '2023-09-06 23:58:52', '0');
INSERT INTO `bus_watchman` VALUES (219, 6, 'XG-20230907-133221', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-07 08:30:00', '2023-09-07 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-06 23:58:52', '', '2023-09-06 23:58:52', '0');
INSERT INTO `bus_watchman` VALUES (220, 1, 'XG-20230908-48741', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-08 19:00:00', '2023-09-08 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-07 23:58:51', '', '2023-09-07 23:58:51', '0');
INSERT INTO `bus_watchman` VALUES (221, 6, 'XG-20230908-524223', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-08 08:30:00', '2023-09-08 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-07 23:58:51', '', '2023-09-07 23:58:51', '0');
INSERT INTO `bus_watchman` VALUES (222, 1, 'XG-20230909-872618', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-09 19:00:00', '2023-09-09 20:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-08 23:58:51', '', '2023-09-08 23:58:51', '0');
INSERT INTO `bus_watchman` VALUES (223, 6, 'XG-20230909-595084', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-09 08:30:00', '2023-09-09 12:00:00', 0, 0, '0', '0', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-08 23:58:51', '', '2023-09-08 23:58:51', '0');
INSERT INTO `bus_watchman` VALUES (224, 1, 'XG-20230910-707881', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-10 19:00:00', '2023-09-10 20:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-09 23:58:51', '', '2023-09-09 23:58:51', '0');
INSERT INTO `bus_watchman` VALUES (225, 6, 'XG-20230910-421407', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-10 08:30:00', '2023-09-10 12:00:00', 24, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-09 23:58:51', '', '2023-09-09 23:58:51', '0');
INSERT INTO `bus_watchman` VALUES (226, 1, 'XG-20230911-340887', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-11 19:00:00', '2023-09-11 20:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-10 23:58:50', '', '2023-09-10 23:58:50', '0');
INSERT INTO `bus_watchman` VALUES (227, 6, 'XG-20230911-517441', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-11 08:30:00', '2023-09-11 12:00:00', 24, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-10 23:58:50', '', '2023-09-10 23:58:50', '0');
INSERT INTO `bus_watchman` VALUES (228, 1, 'XG-20230912-191337', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-12 19:00:00', '2023-09-12 20:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-11 23:58:49', '', '2023-09-11 23:58:49', '0');
INSERT INTO `bus_watchman` VALUES (229, 6, 'XG-20230912-969745', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-12 08:30:00', '2023-09-12 12:00:00', 19, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-11 23:58:49', '', '2023-09-11 23:58:49', '0');
INSERT INTO `bus_watchman` VALUES (230, 1, 'XG-20230913-7124', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-13 19:00:00', '2023-09-13 20:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-12 23:58:49', '', '2023-09-12 23:58:49', '0');
INSERT INTO `bus_watchman` VALUES (231, 6, 'XG-20230913-876112', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-13 08:30:00', '2023-09-13 12:00:00', 16, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-12 23:58:49', '', '2023-09-12 23:58:49', '0');
INSERT INTO `bus_watchman` VALUES (232, 1, 'XG-20230914-664300', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-14 19:00:00', '2023-09-14 20:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-13 23:58:48', '', '2023-09-13 23:58:48', '0');
INSERT INTO `bus_watchman` VALUES (233, 6, 'XG-20230914-707009', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-14 08:30:00', '2023-09-14 12:00:00', 15, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-13 23:58:48', '', '2023-09-13 23:58:48', '0');
INSERT INTO `bus_watchman` VALUES (234, 1, 'XG-20230915-778996', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-15 19:00:00', '2023-09-15 20:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-14 23:58:48', '', '2023-09-14 23:58:48', '0');
INSERT INTO `bus_watchman` VALUES (235, 6, 'XG-20230915-889101', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-15 08:30:00', '2023-09-15 12:00:00', 24, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-14 23:58:48', '', '2023-09-14 23:58:48', '0');
INSERT INTO `bus_watchman` VALUES (236, 1, 'XG-20230916-352939', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-16 19:00:00', '2023-09-16 20:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-15 23:58:47', '', '2023-09-15 23:58:47', '0');
INSERT INTO `bus_watchman` VALUES (237, 6, 'XG-20230916-247577', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-16 08:30:00', '2023-09-16 12:00:00', 22, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-15 23:58:47', '', '2023-09-15 23:58:47', '0');
INSERT INTO `bus_watchman` VALUES (238, 1, 'XG-20230917-189705', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-17 19:00:00', '2023-09-17 20:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-16 23:58:47', '', '2023-09-16 23:58:47', '0');
INSERT INTO `bus_watchman` VALUES (239, 6, 'XG-20230917-548148', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-17 08:30:00', '2023-09-17 12:00:00', 16, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-16 23:58:47', '', '2023-09-16 23:58:47', '0');
INSERT INTO `bus_watchman` VALUES (240, 1, 'XG-20230918-731186', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-18 19:00:00', '2023-09-18 20:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-17 23:58:46', '', '2023-09-17 23:58:46', '0');
INSERT INTO `bus_watchman` VALUES (241, 6, 'XG-20230918-118472', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-18 08:30:00', '2023-09-18 12:00:00', 22, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-17 23:58:46', '', '2023-09-17 23:58:46', '0');
INSERT INTO `bus_watchman` VALUES (242, 1, 'XG-20230919-45055', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-19 19:00:00', '2023-09-19 20:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-18 23:58:46', '', '2023-09-18 23:58:46', '0');
INSERT INTO `bus_watchman` VALUES (243, 6, 'XG-20230919-885318', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-19 08:30:00', '2023-09-19 12:00:00', 22, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-18 23:58:46', '', '2023-09-18 23:58:46', '0');
INSERT INTO `bus_watchman` VALUES (244, 1, 'XG-20230920-610458', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-20 19:00:00', '2023-09-20 20:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-19 23:58:45', '', '2023-09-19 23:58:45', '0');
INSERT INTO `bus_watchman` VALUES (245, 6, 'XG-20230920-954095', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-20 08:30:00', '2023-09-20 12:00:00', 23, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-19 23:58:45', '', '2023-09-19 23:58:45', '0');
INSERT INTO `bus_watchman` VALUES (246, 1, 'XG-20230921-140903', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-21 19:00:00', '2023-09-21 20:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-20 23:58:45', '', '2023-09-20 23:58:45', '0');
INSERT INTO `bus_watchman` VALUES (247, 6, 'XG-20230921-664049', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-21 08:30:00', '2023-09-21 12:00:00', 23, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-20 23:58:45', '', '2023-09-20 23:58:45', '0');
INSERT INTO `bus_watchman` VALUES (248, 1, 'XG-20230922-653506', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-22 19:00:00', '2023-09-22 20:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-21 23:58:44', '', '2023-09-21 23:58:44', '0');
INSERT INTO `bus_watchman` VALUES (249, 6, 'XG-20230922-256769', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-22 08:30:00', '2023-09-22 12:00:00', 17, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-21 23:58:44', '', '2023-09-21 23:58:44', '0');
INSERT INTO `bus_watchman` VALUES (250, 1, 'XG-20230923-710393', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-23 19:00:00', '2023-09-23 20:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-22 23:58:44', '', '2023-09-22 23:58:44', '0');
INSERT INTO `bus_watchman` VALUES (251, 6, 'XG-20230923-100622', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-23 08:30:00', '2023-09-23 12:00:00', 16, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-22 23:58:44', '', '2023-09-22 23:58:44', '0');
INSERT INTO `bus_watchman` VALUES (252, 1, 'XG-20230924-132819', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-24 19:00:00', '2023-09-24 20:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-23 23:58:43', '', '2023-09-23 23:58:43', '0');
INSERT INTO `bus_watchman` VALUES (253, 6, 'XG-20230924-155926', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-24 08:30:00', '2023-09-24 12:00:00', 23, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-23 23:58:43', '', '2023-09-23 23:58:43', '0');
INSERT INTO `bus_watchman` VALUES (254, 1, 'XG-20230925-18450', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-25 19:00:00', '2023-09-25 20:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-24 23:58:43', '', '2023-09-24 23:58:43', '0');
INSERT INTO `bus_watchman` VALUES (255, 6, 'XG-20230925-846126', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-25 08:30:00', '2023-09-25 12:00:00', 16, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-24 23:58:43', '', '2023-09-24 23:58:43', '0');
INSERT INTO `bus_watchman` VALUES (256, 1, 'XG-20230926-488606', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-26 19:00:00', '2023-09-26 20:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-25 23:58:42', '', '2023-09-25 23:58:42', '0');
INSERT INTO `bus_watchman` VALUES (257, 6, 'XG-20230926-823722', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-26 08:30:00', '2023-09-26 12:00:00', 23, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-25 23:58:42', '', '2023-09-25 23:58:42', '0');
INSERT INTO `bus_watchman` VALUES (258, 1, 'XG-20230927-110551', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-27 19:00:00', '2023-09-27 20:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-26 23:58:42', '', '2023-09-26 23:58:42', '0');
INSERT INTO `bus_watchman` VALUES (259, 6, 'XG-20230927-587849', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-27 08:30:00', '2023-09-27 12:00:00', 19, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-26 23:58:42', '', '2023-09-26 23:58:42', '0');
INSERT INTO `bus_watchman` VALUES (260, 1, 'XG-20230928-617682', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-28 19:00:00', '2023-09-28 20:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-27 23:58:41', '', '2023-09-27 23:58:41', '0');
INSERT INTO `bus_watchman` VALUES (261, 6, 'XG-20230928-546940', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-28 08:30:00', '2023-09-28 12:00:00', 19, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-27 23:58:41', '', '2023-09-27 23:58:41', '0');
INSERT INTO `bus_watchman` VALUES (262, 1, 'XG-20230929-40478', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-29 19:00:00', '2023-09-29 20:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-28 23:58:41', '', '2023-09-28 23:58:41', '0');
INSERT INTO `bus_watchman` VALUES (263, 6, 'XG-20230929-363212', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-29 08:30:00', '2023-09-29 12:00:00', 19, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-28 23:58:41', '', '2023-09-28 23:58:41', '0');
INSERT INTO `bus_watchman` VALUES (264, 1, 'XG-20230930-744387', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-09-30 19:00:00', '2023-09-30 20:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-29 23:58:40', '', '2023-09-29 23:58:40', '0');
INSERT INTO `bus_watchman` VALUES (265, 6, 'XG-20230930-496157', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-09-30 08:30:00', '2023-09-30 12:00:00', 20, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-29 23:58:40', '', '2023-09-29 23:58:40', '0');
INSERT INTO `bus_watchman` VALUES (266, 1, 'XG-20231001-794346', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-01 19:00:00', '2023-10-01 20:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-30 23:58:40', '', '2023-09-30 23:58:40', '0');
INSERT INTO `bus_watchman` VALUES (267, 6, 'XG-20231001-965368', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-01 08:30:00', '2023-10-01 12:00:00', 15, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-09-30 23:58:40', '', '2023-09-30 23:58:40', '0');
INSERT INTO `bus_watchman` VALUES (268, 1, 'XG-20231002-180955', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-02 19:00:00', '2023-10-02 20:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-01 23:58:39', '', '2023-10-01 23:58:39', '0');
INSERT INTO `bus_watchman` VALUES (269, 6, 'XG-20231002-878064', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-02 08:30:00', '2023-10-02 12:00:00', 18, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-01 23:58:39', '', '2023-10-01 23:58:39', '0');
INSERT INTO `bus_watchman` VALUES (270, 1, 'XG-20231003-615238', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-03 19:00:00', '2023-10-03 20:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-02 23:58:39', '', '2023-10-02 23:58:39', '0');
INSERT INTO `bus_watchman` VALUES (271, 6, 'XG-20231003-321248', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-03 08:30:00', '2023-10-03 12:00:00', 23, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-02 23:58:39', '', '2023-10-02 23:58:39', '0');
INSERT INTO `bus_watchman` VALUES (272, 1, 'XG-20231004-861820', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-04 19:00:00', '2023-10-04 20:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-03 23:58:38', '', '2023-10-03 23:58:38', '0');
INSERT INTO `bus_watchman` VALUES (273, 6, 'XG-20231004-407686', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-04 08:30:00', '2023-10-04 12:00:00', 18, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-03 23:58:38', '', '2023-10-03 23:58:38', '0');
INSERT INTO `bus_watchman` VALUES (274, 1, 'XG-20231005-418568', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-05 19:00:00', '2023-10-05 20:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-04 23:58:38', '', '2023-10-04 23:58:38', '0');
INSERT INTO `bus_watchman` VALUES (275, 6, 'XG-20231005-612993', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-05 08:30:00', '2023-10-05 12:00:00', 16, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-04 23:58:38', '', '2023-10-04 23:58:38', '0');
INSERT INTO `bus_watchman` VALUES (276, 1, 'XG-20231006-61925', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-06 19:00:00', '2023-10-06 20:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-05 23:58:37', '', '2023-10-05 23:58:37', '0');
INSERT INTO `bus_watchman` VALUES (277, 6, 'XG-20231006-329748', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-06 08:30:00', '2023-10-06 12:00:00', 18, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-05 23:58:37', '', '2023-10-05 23:58:37', '0');
INSERT INTO `bus_watchman` VALUES (278, 1, 'XG-20231007-497157', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-07 19:00:00', '2023-10-07 20:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-06 23:58:37', '', '2023-10-06 23:58:37', '0');
INSERT INTO `bus_watchman` VALUES (279, 6, 'XG-20231007-279631', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-07 08:30:00', '2023-10-07 12:00:00', 23, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-06 23:58:37', '', '2023-10-06 23:58:37', '0');
INSERT INTO `bus_watchman` VALUES (280, 1, 'XG-20231008-574101', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-08 19:00:00', '2023-10-08 20:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-07 23:58:36', '', '2023-10-07 23:58:36', '0');
INSERT INTO `bus_watchman` VALUES (281, 6, 'XG-20231008-523222', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-08 08:30:00', '2023-10-08 12:00:00', 20, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-07 23:58:36', '', '2023-10-07 23:58:36', '0');
INSERT INTO `bus_watchman` VALUES (282, 1, 'XG-20231009-949327', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-09 19:00:00', '2023-10-09 20:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-08 23:58:36', '', '2023-10-08 23:58:36', '0');
INSERT INTO `bus_watchman` VALUES (283, 6, 'XG-20231009-967535', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-09 08:30:00', '2023-10-09 12:00:00', 23, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-08 23:58:36', '', '2023-10-08 23:58:36', '0');
INSERT INTO `bus_watchman` VALUES (284, 1, 'XG-20231010-779292', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-10 19:00:00', '2023-10-10 20:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-09 23:58:35', '', '2023-10-09 23:58:35', '0');
INSERT INTO `bus_watchman` VALUES (285, 6, 'XG-20231010-964097', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-10 08:30:00', '2023-10-10 12:00:00', 18, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-09 23:58:35', '', '2023-10-09 23:58:35', '0');
INSERT INTO `bus_watchman` VALUES (286, 1, 'XG-20231011-422554', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-11 19:00:00', '2023-10-11 20:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-10 23:58:35', '', '2023-10-10 23:58:35', '0');
INSERT INTO `bus_watchman` VALUES (287, 6, 'XG-20231011-366121', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-11 08:30:00', '2023-10-11 12:00:00', 21, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-10 23:58:35', '', '2023-10-10 23:58:35', '0');
INSERT INTO `bus_watchman` VALUES (288, 1, 'XG-20231012-409506', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-12 19:00:00', '2023-10-12 20:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-11 23:58:34', '', '2023-10-11 23:58:34', '0');
INSERT INTO `bus_watchman` VALUES (289, 6, 'XG-20231012-677764', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-12 08:30:00', '2023-10-12 12:00:00', 21, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-11 23:58:34', '', '2023-10-11 23:58:34', '0');
INSERT INTO `bus_watchman` VALUES (290, 1, 'XG-20231013-866118', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-13 19:00:00', '2023-10-13 20:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-12 23:58:34', '', '2023-10-12 23:58:34', '0');
INSERT INTO `bus_watchman` VALUES (291, 6, 'XG-20231013-204886', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-13 08:30:00', '2023-10-13 12:00:00', 17, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-12 23:58:34', '', '2023-10-12 23:58:34', '0');
INSERT INTO `bus_watchman` VALUES (292, 1, 'XG-20231014-30913', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-14 19:00:00', '2023-10-14 20:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-13 23:58:33', '', '2023-10-13 23:58:33', '0');
INSERT INTO `bus_watchman` VALUES (293, 6, 'XG-20231014-145015', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-14 08:30:00', '2023-10-14 12:00:00', 24, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-13 23:58:33', '', '2023-10-13 23:58:33', '0');
INSERT INTO `bus_watchman` VALUES (294, 1, 'XG-20231015-454188', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-15 19:00:00', '2023-10-15 20:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-14 23:58:33', '', '2023-10-14 23:58:33', '0');
INSERT INTO `bus_watchman` VALUES (295, 6, 'XG-20231015-568380', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-15 08:30:00', '2023-10-15 12:00:00', 22, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-14 23:58:33', '', '2023-10-14 23:58:33', '0');
INSERT INTO `bus_watchman` VALUES (296, 1, 'XG-20231016-102042', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-16 19:00:00', '2023-10-16 20:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-15 23:58:32', '', '2023-10-15 23:58:32', '0');
INSERT INTO `bus_watchman` VALUES (297, 6, 'XG-20231016-356968', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-16 08:30:00', '2023-10-16 12:00:00', 18, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-15 23:58:32', '', '2023-10-15 23:58:32', '0');
INSERT INTO `bus_watchman` VALUES (298, 1, 'XG-20231017-598192', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-17 19:00:00', '2023-10-17 20:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-16 23:58:32', '', '2023-10-16 23:58:32', '0');
INSERT INTO `bus_watchman` VALUES (299, 6, 'XG-20231017-892299', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-17 08:30:00', '2023-10-17 12:00:00', 22, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-16 23:58:32', '', '2023-10-16 23:58:32', '0');
INSERT INTO `bus_watchman` VALUES (300, 1, 'XG-20231018-686078', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-18 19:00:00', '2023-10-18 20:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-17 23:58:31', '', '2023-10-17 23:58:31', '0');
INSERT INTO `bus_watchman` VALUES (301, 6, 'XG-20231018-553000', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-18 08:30:00', '2023-10-18 12:00:00', 18, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-17 23:58:31', '', '2023-10-17 23:58:31', '0');
INSERT INTO `bus_watchman` VALUES (302, 1, 'XG-20231019-588343', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-19 19:00:00', '2023-10-19 20:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-18 23:58:31', '', '2023-10-18 23:58:31', '0');
INSERT INTO `bus_watchman` VALUES (303, 6, 'XG-20231019-597489', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-19 08:30:00', '2023-10-19 12:00:00', 18, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-18 23:58:31', '', '2023-10-18 23:58:31', '0');
INSERT INTO `bus_watchman` VALUES (304, 1, 'XG-20231020-639642', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-20 19:00:00', '2023-10-20 20:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-19 23:58:30', '', '2023-10-19 23:58:30', '0');
INSERT INTO `bus_watchman` VALUES (305, 6, 'XG-20231020-49823', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-20 08:30:00', '2023-10-20 12:00:00', 15, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-19 23:58:30', '', '2023-10-19 23:58:30', '0');
INSERT INTO `bus_watchman` VALUES (306, 1, 'XG-20231021-78133', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-21 19:00:00', '2023-10-21 20:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-20 23:58:30', '', '2023-10-20 23:58:30', '0');
INSERT INTO `bus_watchman` VALUES (307, 6, 'XG-20231021-630356', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-21 08:30:00', '2023-10-21 12:00:00', 17, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-20 23:58:30', '', '2023-10-20 23:58:30', '0');
INSERT INTO `bus_watchman` VALUES (308, 1, 'XG-20231022-543537', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-22 19:00:00', '2023-10-22 20:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-21 23:58:29', '', '2023-10-21 23:58:29', '0');
INSERT INTO `bus_watchman` VALUES (309, 6, 'XG-20231022-177472', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-22 08:30:00', '2023-10-22 12:00:00', 15, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-21 23:58:29', '', '2023-10-21 23:58:29', '0');
INSERT INTO `bus_watchman` VALUES (310, 1, 'XG-20231023-232594', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-23 19:00:00', '2023-10-23 20:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-22 23:58:29', '', '2023-10-22 23:58:29', '0');
INSERT INTO `bus_watchman` VALUES (311, 6, 'XG-20231023-655357', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-23 08:30:00', '2023-10-23 12:00:00', 18, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-22 23:58:29', '', '2023-10-22 23:58:29', '0');
INSERT INTO `bus_watchman` VALUES (312, 1, 'XG-20231024-107360', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-24 19:00:00', '2023-10-24 20:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-23 23:58:28', '', '2023-10-23 23:58:28', '0');
INSERT INTO `bus_watchman` VALUES (313, 6, 'XG-20231024-273690', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-24 08:30:00', '2023-10-24 12:00:00', 24, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-23 23:58:28', '', '2023-10-23 23:58:28', '0');
INSERT INTO `bus_watchman` VALUES (314, 1, 'XG-20231025-765974', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-25 19:00:00', '2023-10-25 20:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-24 23:58:28', '', '2023-10-24 23:58:28', '0');
INSERT INTO `bus_watchman` VALUES (315, 6, 'XG-20231025-406779', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-25 08:30:00', '2023-10-25 12:00:00', 17, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-24 23:58:28', '', '2023-10-24 23:58:28', '0');
INSERT INTO `bus_watchman` VALUES (316, 1, 'XG-20231026-760304', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-26 19:00:00', '2023-10-26 20:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-25 23:58:27', '', '2023-10-25 23:58:27', '0');
INSERT INTO `bus_watchman` VALUES (317, 6, 'XG-20231026-77803', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-26 08:30:00', '2023-10-26 12:00:00', 17, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-25 23:58:27', '', '2023-10-25 23:58:27', '0');
INSERT INTO `bus_watchman` VALUES (318, 1, 'XG-20231027-466187', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-27 19:00:00', '2023-10-27 20:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-26 23:58:27', '', '2023-10-26 23:58:27', '0');
INSERT INTO `bus_watchman` VALUES (319, 6, 'XG-20231027-457994', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-27 08:30:00', '2023-10-27 12:00:00', 14, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-26 23:58:27', '', '2023-10-26 23:58:27', '0');
INSERT INTO `bus_watchman` VALUES (320, 1, 'XG-20231028-544589', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-28 19:00:00', '2023-10-28 20:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-27 23:58:26', '', '2023-10-27 23:58:26', '0');
INSERT INTO `bus_watchman` VALUES (321, 6, 'XG-20231028-641974', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-28 08:30:00', '2023-10-28 12:00:00', 14, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-27 23:58:26', '', '2023-10-27 23:58:26', '0');
INSERT INTO `bus_watchman` VALUES (322, 1, 'XG-20231029-585092', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-29 19:00:00', '2023-10-29 20:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-28 23:58:26', '', '2023-10-28 23:58:26', '0');
INSERT INTO `bus_watchman` VALUES (323, 6, 'XG-20231029-361549', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-29 08:30:00', '2023-10-29 12:00:00', 21, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-28 23:58:26', '', '2023-10-28 23:58:26', '0');
INSERT INTO `bus_watchman` VALUES (324, 1, 'XG-20231030-933961', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-30 19:00:00', '2023-10-30 20:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-29 23:58:25', '', '2023-10-29 23:58:25', '0');
INSERT INTO `bus_watchman` VALUES (325, 6, 'XG-20231030-266942', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-30 08:30:00', '2023-10-30 12:00:00', 17, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-29 23:58:25', '', '2023-10-29 23:58:25', '0');
INSERT INTO `bus_watchman` VALUES (326, 1, 'XG-20231031-456872', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-10-31 19:00:00', '2023-10-31 20:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-30 23:58:25', '', '2023-10-30 23:58:25', '0');
INSERT INTO `bus_watchman` VALUES (327, 6, 'XG-20231031-327632', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-10-31 08:30:00', '2023-10-31 12:00:00', 20, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-30 23:58:25', '', '2023-10-30 23:58:25', '0');
INSERT INTO `bus_watchman` VALUES (328, 1, 'XG-20231101-894794', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-11-01 19:00:00', '2023-11-01 20:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-31 23:58:24', '', '2023-10-31 23:58:24', '0');
INSERT INTO `bus_watchman` VALUES (329, 6, 'XG-20231101-226016', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-11-01 08:30:00', '2023-11-01 12:00:00', 23, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-10-31 23:58:24', '', '2023-10-31 23:58:24', '0');
INSERT INTO `bus_watchman` VALUES (330, 1, 'XG-20231102-989530', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-11-02 19:00:00', '2023-11-02 20:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-01 23:58:24', '', '2023-11-01 23:58:24', '0');
INSERT INTO `bus_watchman` VALUES (331, 6, 'XG-20231102-537992', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-11-02 08:30:00', '2023-11-02 12:00:00', 15, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-01 23:58:24', '', '2023-11-01 23:58:24', '0');
INSERT INTO `bus_watchman` VALUES (332, 1, 'XG-20231103-195897', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-11-03 19:00:00', '2023-11-03 20:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-02 23:58:24', '', '2023-11-02 23:58:24', '0');
INSERT INTO `bus_watchman` VALUES (333, 6, 'XG-20231103-730443', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-11-03 08:30:00', '2023-11-03 12:00:00', 23, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-02 23:58:24', '', '2023-11-02 23:58:24', '0');
INSERT INTO `bus_watchman` VALUES (334, 1, 'XG-20231104-96123', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-11-04 19:00:00', '2023-11-04 20:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-03 23:58:23', '', '2023-11-03 23:58:23', '0');
INSERT INTO `bus_watchman` VALUES (335, 6, 'XG-20231104-847677', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-11-04 08:30:00', '2023-11-04 12:00:00', 19, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-03 23:58:23', '', '2023-11-03 23:58:23', '0');
INSERT INTO `bus_watchman` VALUES (336, 1, 'XG-20231105-462767', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-11-05 19:00:00', '2023-11-05 20:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-04 23:58:23', '', '2023-11-04 23:58:23', '0');
INSERT INTO `bus_watchman` VALUES (337, 6, 'XG-20231105-580352', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-11-05 08:30:00', '2023-11-05 12:00:00', 17, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-04 23:58:23', '', '2023-11-04 23:58:23', '0');
INSERT INTO `bus_watchman` VALUES (338, 1, 'XG-20231106-799450', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-11-06 19:00:00', '2023-11-06 20:00:00', 1, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-05 23:58:22', '', '2023-11-05 23:58:22', '0');
INSERT INTO `bus_watchman` VALUES (339, 6, 'XG-20231106-747989', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-11-06 08:30:00', '2023-11-06 12:00:00', 16, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-05 23:58:22', '', '2023-11-05 23:58:22', '0');
INSERT INTO `bus_watchman` VALUES (340, 1, 'XG-20231107-774698', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-11-07 19:00:00', '2023-11-07 20:00:00', 2, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-06 23:58:22', '', '2023-11-06 23:58:22', '0');
INSERT INTO `bus_watchman` VALUES (341, 6, 'XG-20231107-865862', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-11-07 08:30:00', '2023-11-07 12:00:00', 16, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-06 23:58:22', '', '2023-11-06 23:58:22', '0');
INSERT INTO `bus_watchman` VALUES (342, 1, 'XG-20231108-274079', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-11-08 19:00:00', '2023-11-08 20:00:00', 38, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-07 23:58:21', '', '2023-11-07 23:58:21', '0');
INSERT INTO `bus_watchman` VALUES (343, 6, 'XG-20231108-591265', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-11-08 08:30:00', '2023-11-08 12:00:00', 17, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-07 23:58:21', '', '2023-11-07 23:58:21', '0');
INSERT INTO `bus_watchman` VALUES (344, 1, 'XG-20231109-447229', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-11-09 19:00:00', '2023-11-09 20:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-08 23:58:21', '', '2023-11-08 23:58:21', '0');
INSERT INTO `bus_watchman` VALUES (345, 6, 'XG-20231109-962033', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-11-09 08:30:00', '2023-11-09 12:00:00', 21, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-08 23:58:21', '', '2023-11-08 23:58:21', '0');
INSERT INTO `bus_watchman` VALUES (346, 1, 'XG-20231110-731538', '巡更工单', 4, 1, '0', 1, '', '2', '0', 1, 0.00, '2023-11-10 19:00:00', '2023-11-10 20:00:00', 40, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-09 23:58:20', '', '2023-11-09 23:58:20', '0');
INSERT INTO `bus_watchman` VALUES (347, 6, 'XG-20231110-220733', '巡更工单', 11, 3, '0', 2, '', '0', '0', 1, 0.00, '2023-11-10 08:30:00', '2023-11-10 12:00:00', 22, 0, '0', '1', '0', '0', 0, NULL, NULL, '', '', '', '2023-11-09 23:58:20', '', '2023-11-09 23:58:20', '0');

-- ----------------------------
-- Table structure for bus_watchman_plan
-- ----------------------------
DROP TABLE IF EXISTS `bus_watchman_plan`;
CREATE TABLE `bus_watchman_plan`  (
  `plan_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `plan_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '计划名称',
  `tour_id` bigint NOT NULL DEFAULT 0 COMMENT '巡更类型id，表xccj_work.bus_tour（tour_type=1）',
  `route_id` bigint NOT NULL DEFAULT 0 COMMENT '巡更路线id，表xccj_work.bus_route',
  `plan_order` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '是否生成工单：0=是，1=否',
  `plan_model` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '巡查模式 ：0=一日多查，1=多日一查',
  `watchman_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '巡更顺序：0=顺序，1=乱序',
  `plan_num` int NOT NULL DEFAULT 0 COMMENT '巡更次数/天次',
  `start_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '计划开始（格式：plan_model=0时HH:mm，plan_model=1时yyyy-MM-dd）',
  `end_time` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '计划结束（格式：plan_model=0时HH:mm，plan_model=1时yyyy-MM-dd）',
  `gap_time` decimal(10, 2) NOT NULL COMMENT '每次巡更间隔时间（plan_model=0）',
  `allot_way` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '工单分配方式：0=自动分配，1=管理员分配，2=抢单',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`plan_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡更计划表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_watchman_plan
-- ----------------------------
INSERT INTO `bus_watchman_plan` VALUES (1, 1, '巡更计划01', 4, 1, '0', '1', '0', 1, '19:00:00', '20:00:00', 0.00, '0', '15288888888', '2023-05-15 19:01:19', '', '2023-05-15 19:01:07', '0');
INSERT INTO `bus_watchman_plan` VALUES (2, 6, '小区各入口自动任务巡更', 11, 3, '0', '1', '0', 1, '08:30:00', '12:00:00', 0.00, '0', '15656332018', '2023-05-31 11:13:27', '', '2023-05-31 11:13:09', '0');

-- ----------------------------
-- Table structure for bus_watchman_point
-- ----------------------------
DROP TABLE IF EXISTS `bus_watchman_point`;
CREATE TABLE `bus_watchman_point`  (
  `watchman_id` bigint NOT NULL DEFAULT 0 COMMENT '巡更工单id，表xccj_work.bus_watchman',
  `point_id` bigint NOT NULL DEFAULT 0 COMMENT '巡更点id，表xccj_work.bus_point',
  `watchman_result` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '巡更结果：0=待巡更，1=正常，2=异常',
  `result_info` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '结果说明'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡更工单-巡更点表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_watchman_point
-- ----------------------------
INSERT INTO `bus_watchman_point` VALUES (1, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (1, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (2, 7, '0', '');
INSERT INTO `bus_watchman_point` VALUES (2, 2, '0', '');
INSERT INTO `bus_watchman_point` VALUES (3, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (3, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (4, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (4, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (5, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (5, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (6, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (6, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (7, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (7, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (8, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (8, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (9, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (9, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (10, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (10, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (11, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (11, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (12, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (12, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (13, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (13, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (14, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (14, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (15, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (15, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (16, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (16, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (17, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (17, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (18, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (18, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (19, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (19, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (19, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (19, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (20, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (20, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (21, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (21, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (21, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (21, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (22, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (22, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (23, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (23, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (23, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (23, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (24, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (24, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (25, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (25, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (25, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (25, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (26, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (26, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (27, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (27, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (27, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (27, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (28, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (28, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (29, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (29, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (29, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (29, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (30, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (30, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (31, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (31, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (31, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (31, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (32, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (32, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (33, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (33, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (33, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (33, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (34, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (34, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (35, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (35, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (35, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (35, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (36, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (36, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (37, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (37, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (37, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (37, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (38, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (38, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (39, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (39, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (39, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (39, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (40, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (40, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (41, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (41, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (41, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (41, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (42, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (42, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (43, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (43, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (43, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (43, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (44, 4, '0', '');
INSERT INTO `bus_watchman_point` VALUES (44, 3, '0', '');
INSERT INTO `bus_watchman_point` VALUES (45, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (45, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (45, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (45, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (46, 27, '0', '');
INSERT INTO `bus_watchman_point` VALUES (46, 28, '0', '');
INSERT INTO `bus_watchman_point` VALUES (47, 27, '0', '');
INSERT INTO `bus_watchman_point` VALUES (47, 28, '0', '');
INSERT INTO `bus_watchman_point` VALUES (49, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (49, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (49, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (49, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (51, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (51, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (51, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (51, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (53, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (53, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (53, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (53, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (55, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (55, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (55, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (55, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (57, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (57, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (57, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (57, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (59, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (59, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (59, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (59, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (61, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (61, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (61, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (61, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (63, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (63, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (63, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (63, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (65, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (65, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (65, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (65, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (67, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (67, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (67, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (67, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (69, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (69, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (69, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (69, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (71, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (71, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (71, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (71, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (73, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (73, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (73, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (73, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (75, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (75, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (75, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (75, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (77, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (77, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (77, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (77, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (79, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (79, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (79, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (79, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (81, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (81, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (81, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (81, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (83, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (83, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (83, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (83, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (85, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (85, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (85, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (85, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (87, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (87, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (87, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (87, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (89, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (89, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (89, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (89, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (91, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (91, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (91, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (91, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (93, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (93, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (93, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (93, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (95, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (95, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (95, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (95, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (97, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (97, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (97, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (97, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (99, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (99, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (99, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (99, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (101, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (101, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (101, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (101, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (103, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (103, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (103, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (103, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (105, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (105, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (105, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (105, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (107, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (107, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (107, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (107, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (109, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (109, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (109, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (109, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (111, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (111, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (111, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (111, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (113, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (113, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (113, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (113, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (115, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (115, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (115, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (115, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (117, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (117, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (117, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (117, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (119, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (119, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (119, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (119, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (121, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (121, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (121, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (121, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (123, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (123, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (123, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (123, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (125, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (125, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (125, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (125, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (127, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (127, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (127, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (127, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (129, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (129, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (129, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (129, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (131, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (131, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (131, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (131, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (133, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (133, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (133, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (133, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (135, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (135, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (135, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (135, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (137, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (137, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (137, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (137, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (139, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (139, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (139, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (139, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (141, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (141, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (141, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (141, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (143, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (143, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (143, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (143, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (145, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (145, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (145, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (145, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (147, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (147, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (147, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (147, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (149, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (149, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (149, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (149, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (151, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (151, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (151, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (151, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (153, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (153, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (153, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (153, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (155, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (155, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (155, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (155, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (157, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (157, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (157, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (157, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (159, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (159, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (159, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (159, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (161, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (161, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (161, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (161, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (163, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (163, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (163, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (163, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (165, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (165, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (165, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (165, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (167, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (167, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (167, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (167, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (169, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (169, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (169, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (169, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (171, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (171, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (171, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (171, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (173, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (173, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (173, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (173, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (175, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (175, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (175, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (175, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (177, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (177, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (177, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (177, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (179, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (179, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (179, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (179, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (181, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (181, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (181, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (181, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (183, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (183, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (183, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (183, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (185, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (185, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (185, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (185, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (187, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (187, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (187, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (187, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (189, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (189, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (189, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (189, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (191, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (191, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (191, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (191, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (193, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (193, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (193, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (193, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (195, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (195, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (195, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (195, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (197, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (197, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (197, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (197, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (199, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (199, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (199, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (199, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (201, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (201, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (201, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (201, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (203, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (203, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (203, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (203, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (205, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (205, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (205, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (205, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (207, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (207, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (207, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (207, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (209, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (209, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (209, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (209, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (211, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (211, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (211, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (211, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (213, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (213, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (213, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (213, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (215, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (215, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (215, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (215, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (217, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (217, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (217, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (217, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (219, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (219, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (219, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (219, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (221, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (221, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (221, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (221, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (223, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (223, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (223, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (223, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (225, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (225, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (225, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (225, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (227, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (227, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (227, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (227, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (229, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (229, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (229, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (229, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (231, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (231, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (231, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (231, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (233, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (233, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (233, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (233, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (235, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (235, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (235, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (235, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (237, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (237, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (237, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (237, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (239, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (239, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (239, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (239, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (241, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (241, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (241, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (241, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (243, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (243, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (243, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (243, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (245, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (245, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (245, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (245, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (247, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (247, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (247, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (247, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (249, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (249, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (249, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (249, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (251, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (251, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (251, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (251, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (253, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (253, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (253, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (253, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (255, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (255, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (255, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (255, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (257, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (257, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (257, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (257, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (259, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (259, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (259, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (259, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (261, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (261, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (261, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (261, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (263, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (263, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (263, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (263, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (265, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (265, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (265, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (265, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (267, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (267, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (267, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (267, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (269, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (269, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (269, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (269, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (271, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (271, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (271, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (271, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (273, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (273, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (273, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (273, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (275, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (275, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (275, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (275, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (277, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (277, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (277, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (277, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (279, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (279, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (279, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (279, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (281, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (281, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (281, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (281, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (283, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (283, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (283, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (283, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (285, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (285, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (285, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (285, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (287, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (287, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (287, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (287, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (289, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (289, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (289, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (289, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (291, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (291, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (291, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (291, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (293, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (293, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (293, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (293, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (295, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (295, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (295, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (295, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (297, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (297, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (297, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (297, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (299, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (299, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (299, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (299, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (301, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (301, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (301, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (301, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (303, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (303, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (303, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (303, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (305, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (305, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (305, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (305, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (307, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (307, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (307, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (307, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (309, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (309, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (309, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (309, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (311, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (311, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (311, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (311, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (313, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (313, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (313, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (313, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (315, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (315, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (315, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (315, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (317, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (317, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (317, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (317, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (319, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (319, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (319, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (319, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (321, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (321, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (321, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (321, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (323, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (323, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (323, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (323, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (325, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (325, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (325, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (325, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (327, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (327, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (327, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (327, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (329, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (329, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (329, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (329, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (331, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (331, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (331, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (331, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (333, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (333, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (333, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (333, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (335, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (335, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (335, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (335, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (337, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (337, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (337, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (337, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (339, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (339, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (339, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (339, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (341, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (341, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (341, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (341, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (343, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (343, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (343, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (343, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (345, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (345, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (345, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (345, 10, '0', '');
INSERT INTO `bus_watchman_point` VALUES (347, 9, '0', '');
INSERT INTO `bus_watchman_point` VALUES (347, 11, '0', '');
INSERT INTO `bus_watchman_point` VALUES (347, 12, '0', '');
INSERT INTO `bus_watchman_point` VALUES (347, 10, '0', '');

-- ----------------------------
-- View structure for view_unfinish_order
-- ----------------------------
DROP VIEW IF EXISTS `view_unfinish_order`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `view_unfinish_order` AS select `bus_patrol`.`patrol_no` AS `order_no`,`bus_patrol`.`patrol_name` AS `order_name`,`bus_patrol`.`staff_id` AS `staff_id`,`bus_patrol`.`start_time` AS `start_time`,`bus_patrol`.`end_time` AS `end_time`,`bus_patrol`.`execute_status` AS `execute_status` from `bus_patrol` where (`bus_patrol`.`execute_status` not in (2,3)) union all select `bus_watchman`.`watchman_no` AS `order_no`,`bus_watchman`.`watchman_name` AS `order_name`,`bus_watchman`.`staff_id` AS `staff_id`,`bus_watchman`.`start_time` AS `start_time`,`bus_watchman`.`end_time` AS `end_time`,`bus_watchman`.`execute_status` AS `execute_status` from `bus_watchman` where (`bus_watchman`.`execute_status` not in (2,3)) union all select `bus_inspect`.`inspect_no` AS `order_no`,`bus_inspect`.`inspect_name` AS `order_name`,`bus_inspect`.`staff_id` AS `staff_id`,`bus_inspect`.`start_time` AS `start_time`,`bus_inspect`.`end_time` AS `end_time`,`bus_inspect`.`execute_status` AS `execute_status` from `bus_inspect` where (`bus_inspect`.`execute_status` not in (2,3)) union all select `bus_clean`.`clean_no` AS `order_no`,`bus_clean`.`clean_name` AS `order_name`,`bus_clean`.`staff_id` AS `staff_id`,`bus_clean`.`start_time` AS `start_time`,`bus_clean`.`end_time` AS `end_time`,`bus_clean`.`execute_status` AS `execute_status` from `bus_clean` where (`bus_clean`.`execute_status` not in (2,3)) union all select `bus_home_order`.`order_no` AS `order_no`,`bus_home_order`.`order_name` AS `order_name`,`bus_home_order`.`staff_id` AS `staff_id`,`bus_home_order`.`plan_start` AS `start_time`,`bus_home_order`.`plan_end` AS `end_time`,`bus_home_order`.`execute_status` AS `execute_status` from `bus_home_order` where (`bus_home_order`.`execute_status` not in (2,3)) union all select `bus_disaster_order`.`order_no` AS `order_no`,`bus_disaster_order`.`order_name` AS `order_name`,`bus_disaster_order`.`staff_id` AS `staff_id`,`bus_disaster_order`.`plan_start` AS `start_time`,`bus_disaster_order`.`plan_end` AS `end_time`,`bus_disaster_order`.`execute_status` AS `execute_status` from `bus_disaster_order` where (`bus_disaster_order`.`execute_status` not in (2,3));

SET FOREIGN_KEY_CHECKS = 1;
