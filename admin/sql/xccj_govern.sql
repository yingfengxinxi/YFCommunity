/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80034 (8.0.34)
 Source Host           : localhost:3306
 Source Schema         : xccj_govern

 Target Server Type    : MySQL
 Target Server Version : 80034 (8.0.34)
 File Encoding         : 65001

 Date: 17/10/2024 16:40:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bus_bulletin
-- ----------------------------
DROP TABLE IF EXISTS `bus_bulletin`;
CREATE TABLE `bus_bulletin`  (
  `bulletin_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `estate_id` bigint NOT NULL DEFAULT 0 COMMENT '物业id，表xccj_estate_sys.bus_estate',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '姓名',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '头像',
  `gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '2' COMMENT '性别：0=男，1=女，2=未知',
  `user_age` int NOT NULL DEFAULT 0 COMMENT '年龄',
  `card_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'U' COMMENT '证件类型：见数据字典 sys_certificate',
  `card_no` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '证件号码',
  `crime_level` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'A' COMMENT '犯罪级别：A=A级，B=B级',
  `crime_reason` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '通报原因',
  `archive` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '归档：0=未归档，1=已归档',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`bulletin_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '人员通报表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_bulletin
-- ----------------------------
INSERT INTO `bus_bulletin` VALUES (1, 2, '找小城', 'http://file.szcjzhxq.com/statics/2023/05/19/。背面_20230519085353A515.png', '0', 27, 'I', '340123199703046778', 'A', '<p>盗窃金额高达十亿元人民币</p>', '0', '15288888888', '2023-05-19 08:55:40', '', '2023-05-19 08:55:40', '0');
INSERT INTO `bus_bulletin` VALUES (2, 2, '李琳', 'http://file.szcjzhxq.com/statics/2023/05/19/电信a_20230519085845A523.png', '0', 35, 'I', '320381198812252138', 'A', '<p><span style=\"color: rgb(0, 0, 0);\">电信网络诈骗集团重大头目和骨干人员</span></p>', '0', '15288888888', '2023-05-19 08:59:52', '', '2023-05-19 08:59:52', '0');
INSERT INTO `bus_bulletin` VALUES (3, 2, '赵攀峰', 'http://file.szcjzhxq.com/statics/2023/05/19/电信A1_20230519090154A527.png', '0', 29, 'I', '431321199412248636', 'A', '<p><span style=\"color: rgb(0, 0, 0);\">电信网络诈骗集团重大头目和骨干人员</span></p>', '1', '15288888888', '2023-05-19 09:02:21', '15288888888', '2023-05-19 09:03:41', '0');
INSERT INTO `bus_bulletin` VALUES (4, 5, '测试', 'http://file.szcjzhxq.com/statics/2023/06/06/southeast_20230606160234A088.jpg', '0', 34, 'I', '342502198008123233', 'B', '<p>测试测试测试测试测试测试测试测试测试测试测试测试</p>', '1', '15656332018', '2023-06-06 16:03:27', '15656332018', '2023-06-06 16:06:27', '0');
INSERT INTO `bus_bulletin` VALUES (5, 2, '权威', 'http://file.szcjzhxq.com/statics/2023/10/27/v2-bb2b871d102ff9dee80c3b7bd7d0485b_r_20231027155412A887.jpg', '0', 23, 'I', '342501199909090000', 'A', '<p>测试</p>', '1', '15288888888', '2023-10-27 15:54:33', '15288888888', '2023-10-27 15:54:40', '0');

-- ----------------------------
-- Table structure for bus_clue
-- ----------------------------
DROP TABLE IF EXISTS `bus_clue`;
CREATE TABLE `bus_clue`  (
  `clue_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `estate_id` bigint NOT NULL DEFAULT 0 COMMENT '物业id，表xccj_estate_sys.bus_estate',
  `bulletin_id` bigint NOT NULL DEFAULT 0 COMMENT '通报id，表xccj_govern.bus_bulletin',
  `account_id` bigint NOT NULL DEFAULT 0 COMMENT '举报人id，表xccj_community.bus_account',
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '地点',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '举报时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`clue_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '线索反馈表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_clue
-- ----------------------------
INSERT INTO `bus_clue` VALUES (1, 2, 3, 9, 'XX地看到', '17:22分在XX看到长相疑似他的', '2023-05-19 09:03:23', '0');
INSERT INTO `bus_clue` VALUES (2, 2, 2, 9, 'XX地看到', '17:22分在XX看到长相疑似他的', '2023-05-19 09:03:23', '0');
INSERT INTO `bus_clue` VALUES (3, 2, 3, 10, 'XX地看到', '17:22分在XX看到长相疑似他的', '2023-05-19 09:03:23', '0');

-- ----------------------------
-- Table structure for bus_committee
-- ----------------------------
DROP TABLE IF EXISTS `bus_committee`;
CREATE TABLE `bus_committee`  (
  `committee_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `committee_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '委员会名称',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`committee_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '业主委员会表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_committee
-- ----------------------------
INSERT INTO `bus_committee` VALUES (1, 1, '测试社区_01业主委员会', '15288888888', '2023-05-17 09:52:33', '', '2023-05-17 09:52:33', '0');
INSERT INTO `bus_committee` VALUES (2, 6, '城建·世纪阳江业主委员会', '15755177283', '2023-05-22 16:17:01', '', '2023-05-22 16:17:01', '0');
INSERT INTO `bus_committee` VALUES (3, 7, '彩金湖畔业主委员会', '18888888888', '2023-07-03 16:58:28', '', '2023-07-03 16:58:28', '0');

-- ----------------------------
-- Table structure for bus_committee_owner
-- ----------------------------
DROP TABLE IF EXISTS `bus_committee_owner`;
CREATE TABLE `bus_committee_owner`  (
  `committee_id` bigint NOT NULL DEFAULT 0 COMMENT '业主委员会id，表xccj_govern.bus_committee',
  `owner_id` bigint NOT NULL DEFAULT 0 COMMENT '业主id，表xccj_community.bus_owner'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '业主委员会-业主关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_committee_owner
-- ----------------------------
INSERT INTO `bus_committee_owner` VALUES (2, 16);
INSERT INTO `bus_committee_owner` VALUES (1, 29);
INSERT INTO `bus_committee_owner` VALUES (1, 11);
INSERT INTO `bus_committee_owner` VALUES (3, 74);

-- ----------------------------
-- Table structure for bus_decide
-- ----------------------------
DROP TABLE IF EXISTS `bus_decide`;
CREATE TABLE `bus_decide`  (
  `decide_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `estate_id` bigint NOT NULL DEFAULT 0 COMMENT '物业id，表xccj_estate_sys.bus_estate',
  `decide_title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`decide_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '自治表决表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_decide
-- ----------------------------
INSERT INTO `bus_decide` VALUES (1, 2, '停车费变更', '地面车位每户业主的第一辆车收费标准150元/月，每户业主的第二辆收费标准300元/月（已经拥有一个产权车位的业主，如还需租用小区车位，视同第二辆车）', '2023-05-19 00:00:00', '2023-05-31 00:00:00', '15288888888', '2023-05-19 09:05:30', '15288888888', '2023-05-19 09:05:58', '0');
INSERT INTO `bus_decide` VALUES (2, 2, '测试', NULL, '2023-10-27 00:00:00', '2023-10-31 00:00:00', '15288888888', '2023-10-27 15:58:55', '', '2023-10-27 15:58:55', '0');

-- ----------------------------
-- Table structure for bus_decide_item
-- ----------------------------
DROP TABLE IF EXISTS `bus_decide_item`;
CREATE TABLE `bus_decide_item`  (
  `item_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `statute_id` bigint NOT NULL DEFAULT 0 COMMENT '自治表决id，表xccj_govern.bus_decide',
  `item_title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `vote_num` int NOT NULL DEFAULT 0 COMMENT '选择人数',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '自治表决选项表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_decide_item
-- ----------------------------
INSERT INTO `bus_decide_item` VALUES (1, 1, '同意', 0, '15288888888', '2023-05-19 09:05:30', '15288888888', '2023-05-19 09:05:58', '0');
INSERT INTO `bus_decide_item` VALUES (2, 1, '不同意', 0, '15288888888', '2023-05-19 09:05:30', '15288888888', '2023-05-19 09:05:58', '0');
INSERT INTO `bus_decide_item` VALUES (3, 2, '11', 0, '15288888888', '2023-10-27 15:58:55', '', '2023-10-27 15:58:55', '0');
INSERT INTO `bus_decide_item` VALUES (4, 2, '22', 0, '15288888888', '2023-10-27 15:58:55', '', '2023-10-27 15:58:55', '0');

-- ----------------------------
-- Table structure for bus_decide_owner
-- ----------------------------
DROP TABLE IF EXISTS `bus_decide_owner`;
CREATE TABLE `bus_decide_owner`  (
  `decide_id` bigint NOT NULL DEFAULT 0 COMMENT '自治表决id，表xccj_govern.bus_decide',
  `owner_id` bigint NOT NULL DEFAULT 0 COMMENT '业主id，表xccj_community.bus_owner'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '自治表决-业主关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_decide_owner
-- ----------------------------

-- ----------------------------
-- Table structure for bus_decide_rang
-- ----------------------------
DROP TABLE IF EXISTS `bus_decide_rang`;
CREATE TABLE `bus_decide_rang`  (
  `statute_id` bigint NOT NULL DEFAULT 0 COMMENT '自治表决id，表xccj_govern.bus_decide',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '自治表决-小区关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_decide_rang
-- ----------------------------
INSERT INTO `bus_decide_rang` VALUES (1, 1);
INSERT INTO `bus_decide_rang` VALUES (2, 1);

-- ----------------------------
-- Table structure for bus_donation
-- ----------------------------
DROP TABLE IF EXISTS `bus_donation`;
CREATE TABLE `bus_donation`  (
  `donation_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `estate_id` bigint NOT NULL DEFAULT 0 COMMENT '物业id，表xccj_estate_sys.bus_estate',
  `donation_title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`donation_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '业主捐赠表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_donation
-- ----------------------------
INSERT INTO `bus_donation` VALUES (1, 2, '首台业主捐赠AED设备进驻社区', '<p><span style=\"color: rgb(34, 34, 34);\">2023年05月18日，社区迎来了通过业主参与公益项目落地的第一台AED（自动体外除颤器）设备和第一场急救培训。这意味着，社区日后如有发生心脏骤停（也称“心源/心脏性猝死”）等突发事件，学习过急救知识与技能的业主和物业工作人员等可以借助这台AED设备帮助患者进行紧急心脏除颤，有效提高心脏骤停患者的生存概率</span></p>', '15288888888', '2023-05-18 17:55:41', '15288888888', '2023-05-18 17:56:31', '0');
INSERT INTO `bus_donation` VALUES (2, 2, '“全国助残日”爱心物资捐赠仪式', '<p class=\"ql-align-justify\">在第三十三次“全国助残日”即将到来之际，5月17日上午，举行“全国助残日”爱心物资捐赠仪式。仪式上，业主XXX向有需求的残疾人捐赠包括轮椅、假肢、服装等在内总价值428万元的爱心物资。</p><p><br></p>', '15288888888', '2023-05-18 17:57:31', '', '2023-05-18 17:57:31', '0');
INSERT INTO `bus_donation` VALUES (3, 6, '2023年7月1日XXX为山区捐赠物资1000元', '<p>2023年7月1日XXX为山区捐赠物资1000元！</p>', '18888888888', '2023-07-03 14:34:28', '', '2023-07-03 14:34:28', '0');
INSERT INTO `bus_donation` VALUES (4, 2, '测试', '<p>测试</p>', '15288888888', '2023-10-27 16:00:24', '', '2023-10-27 16:00:24', '0');

-- ----------------------------
-- Table structure for bus_donation_rang
-- ----------------------------
DROP TABLE IF EXISTS `bus_donation_rang`;
CREATE TABLE `bus_donation_rang`  (
  `donation_id` bigint NOT NULL DEFAULT 0 COMMENT '捐赠id，表xccj_govern.bus_donation',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '业主捐赠-小区关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_donation_rang
-- ----------------------------
INSERT INTO `bus_donation_rang` VALUES (1, 1);
INSERT INTO `bus_donation_rang` VALUES (1, 4);
INSERT INTO `bus_donation_rang` VALUES (2, 1);
INSERT INTO `bus_donation_rang` VALUES (3, 7);
INSERT INTO `bus_donation_rang` VALUES (4, 1);

-- ----------------------------
-- Table structure for bus_featured_comment
-- ----------------------------
DROP TABLE IF EXISTS `bus_featured_comment`;
CREATE TABLE `bus_featured_comment`  (
  `comment_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `featured_id` bigint NOT NULL DEFAULT 0 COMMENT '特色板块表id，表xccj_govern.bus_featured_section',
  `user_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '评论人员类型：0=系统(sys_user)，1=业主(bus_account)，2=租客(bus_account)',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '评论人员id，user_type对应表id',
  `reply_id` bigint NOT NULL DEFAULT 0 COMMENT '评论id，表xccj_govern.bus_social_comment',
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '评论内容',
  `parent_comment_id` bigint NULL DEFAULT NULL COMMENT '父级id评论表主键(用于二级评论)',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '特色板块评论记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bus_featured_comment
-- ----------------------------

-- ----------------------------
-- Table structure for bus_featured_like
-- ----------------------------
DROP TABLE IF EXISTS `bus_featured_like`;
CREATE TABLE `bus_featured_like`  (
  `featured_id` bigint NOT NULL DEFAULT 0 COMMENT '特色板块id，表xccj_govern.bus_featured_section',
  `user_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '人员类型：0=系统(sys_user)，1=业主(bus_account)，2=租客(bus_account)',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '人员id，user_type对应表id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '社交点赞记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bus_featured_like
-- ----------------------------

-- ----------------------------
-- Table structure for bus_featured_rang
-- ----------------------------
DROP TABLE IF EXISTS `bus_featured_rang`;
CREATE TABLE `bus_featured_rang`  (
  `featured_id` bigint NOT NULL DEFAULT 0 COMMENT '特色板块id，表xccj_govern.bus_featured_section',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '社交-小区关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bus_featured_rang
-- ----------------------------

-- ----------------------------
-- Table structure for bus_featured_section
-- ----------------------------
DROP TABLE IF EXISTS `bus_featured_section`;
CREATE TABLE `bus_featured_section`  (
  `featured_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `estate_id` bigint NOT NULL DEFAULT 0 COMMENT '物业id，表xccj_estate_sys.bus_estate',
  `community_id` bigint NULL DEFAULT NULL COMMENT '所属小区',
  `featured_section_type_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '特色板块类型',
  `featured_url` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '图片地址',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `user_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '发布人类型：0=系统[物业]，1=业主，2=租客',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '发布人id，user_type对应表id',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '发布人姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`featured_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '特色板块表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bus_featured_section
-- ----------------------------

-- ----------------------------
-- Table structure for bus_featured_section_type
-- ----------------------------
DROP TABLE IF EXISTS `bus_featured_section_type`;
CREATE TABLE `bus_featured_section_type`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `estate_id` bigint NULL DEFAULT NULL COMMENT '物业id',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '板块名称',
  `posters_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '海报图片',
  `group_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '群二维码',
  `sort` int NOT NULL DEFAULT 0 COMMENT '顺序',
  `page_url` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '跳转路径',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '是否停用0=否1=是',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '特色板块类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bus_featured_section_type
-- ----------------------------

-- ----------------------------
-- Table structure for bus_grid
-- ----------------------------
DROP TABLE IF EXISTS `bus_grid`;
CREATE TABLE `bus_grid`  (
  `grid_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `estate_id` bigint NOT NULL DEFAULT 0 COMMENT '物业id，表xccj_estate_sys.bus_estate',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `grid_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '区域名称',
  `staff_id` bigint NOT NULL DEFAULT 0 COMMENT '管理员id，表xccj_community.bus_staff',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '区域位置描述',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`grid_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '网格区域管理表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_grid
-- ----------------------------
INSERT INTO `bus_grid` VALUES (1, 2, 1, '区域01', 1, '区域描述01', '15288888888', '2023-05-19 18:13:37', '', '2023-05-19 18:13:37', '0');
INSERT INTO `bus_grid` VALUES (2, 2, 1, '区域02', 2, '区域描述02', '15288888888', '2023-05-19 18:13:50', '', '2023-05-19 18:13:50', '0');
INSERT INTO `bus_grid` VALUES (3, 5, 6, '济川街道第三网格', 12, '济川街道第三网格', '15656332018', '2023-06-05 15:47:03', '15656332018', '2023-06-26 09:23:41', '0');
INSERT INTO `bus_grid` VALUES (4, 2, 1, '区域', 2, '测试测试测试', '15288888888', '2023-10-27 15:08:50', '', '2023-10-27 15:08:50', '0');
INSERT INTO `bus_grid` VALUES (5, 2, 1, '测试', 2, '测试1234', '15288888888', '2023-10-27 15:52:55', '', '2023-10-27 15:52:55', '0');

-- ----------------------------
-- Table structure for bus_member
-- ----------------------------
DROP TABLE IF EXISTS `bus_member`;
CREATE TABLE `bus_member`  (
  `member_id` bigint NOT NULL AUTO_INCREMENT COMMENT '党员id',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `building_id` bigint NOT NULL DEFAULT 0 COMMENT '楼栋id，表xccj_community.bus_building',
  `unit_id` bigint NOT NULL DEFAULT 0 COMMENT '单元id，表xccj_community.bus_unit',
  `room_id` bigint NOT NULL DEFAULT 0 COMMENT '当前所住房间id，表xccj_community.bus_room',
  `account_id` bigint NOT NULL DEFAULT 0 COMMENT '账号id，表xccj_community.bus_account',
  `join_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '入党时间',
  `party_id` bigint NOT NULL DEFAULT 0 COMMENT '所属党组织id，表xccj_govern.bus_party',
  `prove_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '证明图片',
  `audit_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '审核状态：0=审核中，1=通过，2=驳回',
  `audit_time` datetime NULL DEFAULT NULL COMMENT '审核时间',
  `reject_reason` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '驳回理由',
  `member_duty` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '党内职务',
  `party_fee` decimal(10, 2) NULL DEFAULT NULL COMMENT '个人党费',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除状态 0 未删除 1 已删除',
  `member_adress` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '党员详细地址',
  PRIMARY KEY (`member_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '党员管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_member
-- ----------------------------
INSERT INTO `bus_member` VALUES (1, 1, 13, 10, 231, 9, '2020-01-01 00:00:00', 2, 'http://file.szcjzhxq.com:8084/statics/2023/05/16/a - 副本_20230516094808A232.jpg', '1', '2023-05-17 17:27:49', '', '党员', 10.00, '业主戊', '2023-05-16 09:48:12', '', '2023-05-16 09:48:12', '0', NULL);
INSERT INTO `bus_member` VALUES (2, 1, 13, 10, 229, 10, '2020-01-02 00:00:00', 2, 'http://file.szcjzhxq.com:8084/statics/2023/05/16/国徽面_20230516095058A233.jpeghttp://file.szcjzhxq.com:8084/statics/2023/05/16/国徽面_20230516095058A233.jpeg&http://file.szcjzhxq.com:8084/statics/2023/05/16/a_20230516095102A234.jpghttp://file.szcjzhxq.com:8084/statics/2023/05/16/国徽面_20230516095058A233.jpeg&http://file.szcjzhxq.com:8084/statics/2023/05/16/a_20230516095102A234.jpg&http://file.szcjzhxq.com:8084/statics/2023/05/16/a - 副本_20230516095105A235.jpg', '1', '2023-10-27 15:05:37', '', '党员', 0.00, '人脸', '2023-05-16 09:51:17', '', '2023-05-16 09:51:17', '0', NULL);
INSERT INTO `bus_member` VALUES (3, 6, 43, 24, 773, 19, '2022-04-17 00:00:00', 4, 'http://file.szcjzhxq.com:8084/statics/2023/05/17/男_20230517171837A390.pnghttp://file.szcjzhxq.com:8084/statics/2023/05/17/男_20230517171837A390.png&http://file.szcjzhxq.com:8084/statics/2023/05/17/人像面_20230517171841A391.pnghttp://file.szcjzhxq.com:8084/statics/2023/05/17/男_20230517171837A390.png&http://file.szcjzhxq.com:8084/statics/2023/05/17/人像面_20230517171841A391.png&http://file.szcjzhxq.com:8084/statics/2023/05/17/国徽面_20230517171844A392.png', '1', '2023-05-18 10:21:25', '', '党员', 20.00, '傅强', '2023-05-17 17:18:47', '', '2023-05-17 17:18:47', '0', NULL);
INSERT INTO `bus_member` VALUES (4, 1, 13, 10, 232, 8, '2023-05-18 00:00:00', 1, 'http://file.szcjzhxq.com:8084/statics/2023/05/18/dengji_20230518174542A497.png', '2', '2023-05-18 17:46:59', '拒绝', '普通党员', 0.00, '甫', '2023-05-18 17:37:57', '甫', '2023-05-18 17:45:51', '0', NULL);
INSERT INTO `bus_member` VALUES (5, 1, 13, 9, 209, 41, '2023-05-24 00:00:00', 1, 'http://file.szcjzhxq.com:8084/statics/2023/05/24/。背面_20230524143315A836.png', '1', '2023-05-24 14:33:41', '', '无', 4.00, '王志国', '2023-05-24 14:33:18', '', '2023-05-24 14:33:18', '0', NULL);
INSERT INTO `bus_member` VALUES (6, 5, 85, 75, 2747, 79, '2023-06-07 00:00:00', 1, 'http://file.szcjzhxq.com/statics/2023/06/07/b_20230607153855A113.jpg', '1', '2023-06-10 17:40:19', '', '党员', 0.00, '陈先生', '2023-06-07 15:38:58', '', '2023-06-07 15:38:58', '0', NULL);
INSERT INTO `bus_member` VALUES (7, 1, 13, 10, 224, 86, '2023-06-10 00:00:00', 1, 'http://file.szcjzhxq.com/statics/2023/06/10/1686389930826_20230610173924A275.jpg', '2', '2024-07-31 17:08:54', '888', '党员', 0.00, '业主祖父', '2023-06-10 17:39:27', '', '2023-06-10 17:39:27', '0', NULL);
INSERT INTO `bus_member` VALUES (8, 7, 59, 25, 885, 94, '2012-07-03 00:00:00', 5, 'http://file.szcjzhxq.com/statics/2023/07/03/男_20230703172009A402.png', '1', '2023-07-03 17:20:25', '', '党员', 0.00, '傅强', '2023-07-03 17:20:12', '', '2023-07-03 17:20:12', '0', NULL);
INSERT INTO `bus_member` VALUES (9, 1, 165, 146, 2136, 93, '2023-10-30 00:00:00', 2, 'http://file.szcjzhxq.com/statics/2023/10/30/tmp_64a29fbb8782a9262789848cbdcc4a02_20231030222058A907.jpg', '1', '2023-10-30 22:21:25', '', '党员', 0.00, '田杰', '2023-10-30 22:21:02', '', '2023-10-30 22:21:02', '0', NULL);
INSERT INTO `bus_member` VALUES (10, 5, 85, 75, 2748, 239, '2023-10-31 00:00:00', 1, 'http://file.szcjzhxq.com/statics/2023/10/31/tmp_4ec610e8558038340c9134a9f0631cf5_20231031145754A912.jpg', '0', NULL, '', '党员', 0.00, '张珊', '2023-10-31 14:57:58', '', '2023-10-31 14:57:58', '0', NULL);
INSERT INTO `bus_member` VALUES (11, 1, 165, 146, 2138, 149, '2024-07-18 00:00:00', 2, '', '1', '2024-07-31 16:54:20', '', '党员', NULL, '', '2024-07-31 16:54:20', '', '2024-07-31 16:54:20', '0', NULL);

-- ----------------------------
-- Table structure for bus_member_act
-- ----------------------------
DROP TABLE IF EXISTS `bus_member_act`;
CREATE TABLE `bus_member_act`  (
  `activity_id` bigint NOT NULL AUTO_INCREMENT COMMENT '活动id',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `account_id` bigint NOT NULL DEFAULT 0 COMMENT '发起人账号id，表xccj_community.bus_account',
  `picture_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '活动图片',
  `act_theme` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '活动主题',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '活动地点',
  `start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '活动开始时间',
  `end_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '活动结束时间',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '活动状态 0 开启 1 结束',
  `plan_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '活动进度 0 活动待启动 1 活动报名中 2 活动结束',
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '活动详情',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除状态 0 未删除 1 已删除',
  `close_time` datetime NULL DEFAULT NULL COMMENT '报名截止时间',
  `view_num` int NULL DEFAULT 0 COMMENT '浏览次数',
  PRIMARY KEY (`activity_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '党员活动' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_member_act
-- ----------------------------
INSERT INTO `bus_member_act` VALUES (1, 1, 0, 'http://file.szcjzhxq.com/statics/2023/05/17/党建活动_20230517180127A398.png', '开展特色党建活动 ', '社区党组织', '2023-05-17 00:00:00', '2023-05-31 00:00:00', '0', '1', '4月13日，国网三河市供电公司纪委结合党建活动，组织公司纪检干部、各支部纪检委员、部分党员干部共计20余人参观中国共产党历史展览馆。\n\n“太震撼了，看着一幅幅经典的老照片，一件件珍贵的文物实物，我仿佛置身于那个战火纷飞的年代，感受到了英烈们冲锋陷阵的无畏精神。”“这一百年是我们党不懈奋斗、见证辉煌的一百年。作为党员干部、支部纪检委员，我深感责任重大，在今后的工作中一定要时常检视自己，做到自身正、自', '15277777777', '2023-05-17 18:01:30', '15277777777', '2023-05-17 18:01:30', '0', NULL, 0);
INSERT INTO `bus_member_act` VALUES (2, 1, 0, 'http://file.szcjzhxq.com/statics/2023/05/17/党建音乐_20230517180255A399.png', '社区党建丨举办“音乐中的民族印记”', '社区广场', '2023-05-17 00:00:00', '2023-05-31 00:00:00', '0', '0', '省委主题教育第22巡回指导组副组长钱忠宝、成员陈胜，院党委书记张志坚、院长陈向炜，院职能部门负责人、二级学院党委书记及师生代表400余人参加了活动。\n\n按照主题教育“学思想、强党性、重实践、建新功”的总要求，依托音乐国乐团、交响管乐团，通过音乐实践+理论宣讲的方式，传承红色基因、弘扬优秀文化、歌颂新时代十年伟大成就，用音乐语言宣讲党的光辉历程和创新理论，以“声”传情，以“乐”入心，', '15277777777', '2023-05-17 18:02:58', '15277777777', '2023-05-17 18:02:58', '0', NULL, 0);

-- ----------------------------
-- Table structure for bus_member_join
-- ----------------------------
DROP TABLE IF EXISTS `bus_member_join`;
CREATE TABLE `bus_member_join`  (
  `activity_id` bigint NOT NULL DEFAULT 0 COMMENT '党员活动id，表xccj_govern.bus_member_act',
  `member_id` bigint NOT NULL DEFAULT 0 COMMENT '党员id，表xccj_govern.bus_member',
  `join_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报名人姓名',
  `join_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报名人手机号',
  `audit_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '审核状态(默认审核通过)',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间(报名时间)'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '党员活动-党员关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bus_member_join
-- ----------------------------

-- ----------------------------
-- Table structure for bus_message
-- ----------------------------
DROP TABLE IF EXISTS `bus_message`;
CREATE TABLE `bus_message`  (
  `message_id` bigint NOT NULL AUTO_INCREMENT COMMENT '党员id',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `msg_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '咨询标题',
  `skip_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '跳转路径',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `look_number` bigint NOT NULL DEFAULT 0 COMMENT '阅读数',
  `sort_number` bigint NOT NULL DEFAULT 0 COMMENT '排序序号',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除状态 0 未删除 1 已删除',
  `active_picture` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动封面',
  `top` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '置顶0=否1=是',
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '党建资讯' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_message
-- ----------------------------
INSERT INTO `bus_message` VALUES (1, 1, '【党建资讯】快报：第二百三十四期 ', 'http://file.szcjzhxq.com/statics/2023/05/17/a476e7d9db8b4b3e9358c022cbc5add0_20230517174441A396.png', '<strong>01</strong><strong>市委编办开展“观历史文化·话美好生活”主题党日活动</strong>近日，市委编办联合浉河区社会社区开展“观历史文化·话美好生活”主题党日活动。党员们参观了城阳城遗址博物馆和平桥区五里镇乡建博览园，领略历史文化、感受乡村振兴日新月异的新农村建设。信阳独特的历史文化和火热的乡村振兴气象激发了大家的自豪感、使命感和责任感。<strong>02</strong><strong>市直工委举办2023年全市机关党建宣传骨干培训班</strong>日前，全市机关党建宣传骨干培训班在大别山两好学院新县基地开班。市直，中央、省驻信单位和县（区）直工委120余名机关党建宣传骨干参加培训。培训班为期3天，设置了新闻写作、新闻摄影等业务课程。邀请了河南日报社专家以《怎样讲故事》《摄影精神与实战能力》为题进行专业授课，市委党校专家就学习贯彻落实党的二十大精神作了专题辅导，培训班上对年度机关党建宣传工作要点进行了讲解阐释。<strong>03</strong><strong>市委网信办举行“周四大学堂”学习活动</strong>4月13日，市委网信办举行第13次集体学习暨第53期“周四大学堂”学习活动。深入学习了《全国干部教育通讯（第2期）》、中央保密委员会《保密要情》和省委、省政府印发的《关于做好2023年全面推进乡村振兴重点工作的实施意见》等精神。还传达了教育培训期间违反中央八项规定的典型案例通报，大家表示要以案为鉴、以案明纪，做到知敬畏、存戒惧、守底线。', 5, 0, '15277777777', '2023-05-17 17:44:48', '15277777777', '2023-05-17 17:44:48', '0', NULL, '1');
INSERT INTO `bus_message` VALUES (2, 1, '【党建资讯】快报：第二百二十九期 ', 'http://file.szcjzhxq.com/statics/2023/05/17/党建_20230517175703A397.png', '<strong>01</strong><strong>市委网信办举办学习宣传贯彻党的二十大精神辅导报告会</strong>近日，市委网信办举办学习贯彻党的二十大精神辅导报告会，围绕党的二十大的主题、主要成果，过去5年的工作和新时代10年的伟大变革，马克思主义中国化时代化，新时代新征程中国共产党的使命任务，全面建设社会主义现代化国家的目标任务，坚持党的全面领导和全面从严治党，应对风险挑战等七个方面做了宣讲。对党的二十大精神进行了深入解读阐释，并对做好机关党的建设提出要求。<strong>02</strong><strong>固始县召开2023年人才工作部署会</strong>3月28日，固始县召开2023年度人才工作部署会议。会议要求，各责任单位务必高度重视，明确分管领导为直接责任人，对照《2023年固始县人才工作要点》，结合本部门、本系统工作实际，制定任务清单，对任务进行再分解，落实落细。同时，要做好工作总结，推广好的经验做法，提升全县人才工作水平。<strong>03</strong><strong>潢川县乡村振兴专题培训班在浙江开班</strong>﻿3月27日，潢川县乡村振兴专题培训班（第一期）在浙大城市学院开班。专题培训班分2期进行，培训覆盖乡镇（街道）党政正职、巩固办主任、五星、四星村支部书记、乡村振兴扛旗相关单位主要领导、党建引领基层治理相关负责同志。以专题授课、现场教学和交流研讨相结合的方式开展教学，围绕农村产业发展、乡村规划与环境整治、美丽乡村、未来乡村建设和党建引领基层治理等主题设计教学课程，并组织学员走进桐庐、安吉余村、萧山梅林等地开展县城案例教学和交流研讨。<strong>责编：邬原园 编审：方应勇</strong>', 17, 0, '15277777777', '2023-05-17 17:57:07', '15277777777', '2023-05-17 17:57:07', '0', NULL, '1');
INSERT INTO `bus_message` VALUES (3, 1, '【党建资讯】快报：第二百三十三期', 'http://file.szcjzhxq.com/statics/2023/05/29/共产党_20230529141004A071.jpg', '<strong>01市委网信办举办学习宣传贯彻党的二十大精神辅导报告会</strong>		近日，市委网信办举办学习贯彻党的二十大精神辅导报告会，围绕党的二十大的主题、主要成果，过去5年的工作和新时代10年的伟大变革，马克思主义中国化时代化，新时代新征程中国共产党的使命任务，全面建设社会主义现代化国家的目标任务，坚持党的全面领导和全面从严治党，应对风险挑战等七个方面做了宣讲。对党的二十大精神进行了深入解读阐释，并对做好机关党的建设提出要求。<strong>02固始县召开2023年人才工作部署会</strong><strong>﻿</strong>		3月28日，固始县召开2023年度人才工作部署会议。会议要求，各责任单位务必高度重视，明确分管领导为直接责任人，对照《2023年固始县人才工作要点》，结合本部门、本系统工作实际，制定任务清单，对任务进行再分解，落实落细。同时，要做好工作总结，推广好的经验做法，提升全县人才工作水平。<strong>03潢川县乡村振兴专题培训班在浙江开班</strong>﻿		3月27日，潢川县乡村振兴专题培训班（第一期）在浙大城市学院开班。专题培训班分2期进行，培训覆盖乡镇（街道）党政正职、巩固办主任、五星、四星村支部书记、乡村振兴扛旗相关单位主要领导、党建引领基层治理相关负责同志。以专题授课、现场教学和交流研讨相结合的方式开展教学，围绕农村产业发展、乡村规划与环境整治、美丽乡村、未来乡村建设和党建引领基层治理等主题设计教学课程，并组织学员走进桐庐、安吉余村、萧山梅林等地开展县城案例教学和交流研讨。<strong>责编：邬原园 </strong><strong>编审：方应勇</strong>', 13, 0, '15288888888', '2023-05-29 14:10:18', '15288888888', '2023-05-29 14:10:18', '0', NULL, '1');

-- ----------------------------
-- Table structure for bus_msg_party
-- ----------------------------
DROP TABLE IF EXISTS `bus_msg_party`;
CREATE TABLE `bus_msg_party`  (
  `message_id` bigint NOT NULL DEFAULT 0 COMMENT '党建资讯id，表xccj_govern.bus_message',
  `party_id` bigint NOT NULL DEFAULT 0 COMMENT '党组织id，表xccj_govern.bus_party'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '党建资讯-党组织关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_msg_party
-- ----------------------------
INSERT INTO `bus_msg_party` VALUES (1, 1);
INSERT INTO `bus_msg_party` VALUES (2, 2);
INSERT INTO `bus_msg_party` VALUES (3, 1);

-- ----------------------------
-- Table structure for bus_party
-- ----------------------------
DROP TABLE IF EXISTS `bus_party`;
CREATE TABLE `bus_party`  (
  `party_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `party_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '党组织名称',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id,表xccj_community.bus_community',
  `top_party` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '顶层党组织：0=是,1=不是',
  `top_id` bigint NOT NULL DEFAULT 0 COMMENT '顶层党组织id',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '上级党组织id',
  `logo_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '党组织logo路径',
  `party_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '党组织状态：0=正常,1=关闭',
  `party_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '组织类型：1=行政村党组织,2=社区党组织,3=机关事业党组织,4=国有企业党组织,5=非公企业党组织,6=社会组织党组织,7=其他',
  `unit_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '单位名称',
  `unit_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '单位类型：1=城市街道,2=城市社区（居委会）,3=乡镇,4=农村社区（居委会）,5=建制村,6=国有经济控制,7=集团经济控制,8=非公有制经济控制,9=事业单位,10=机关,11=社会组织,12=其他',
  `unit_case` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '单位建党组织情况:1=建立党委,2=建立党总支,3=建立党支部,4=建立联合党支部',
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '党组织介绍',
  `admin_id` bigint NOT NULL DEFAULT 0 COMMENT '管理员id，表xccj_govern.bus_member',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除状态 0 未删除 1 已删除',
  PRIMARY KEY (`party_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '党组织管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_party
-- ----------------------------
INSERT INTO `bus_party` VALUES (1, '中国共产党', 1, '0', 0, 0, 'http://file.szcjzhxq.com:8084/statics/2023/05/16/共产党_20230516085817A230.jpg', '0', '7', '中国共产党', '1', '1', '党组织介绍:', 0, '2023-05-16 08:58:20', '15288888888', '2023-05-16 08:58:20', '15288888888', '0');
INSERT INTO `bus_party` VALUES (2, '测试党组织', 1, '1', 1, 1, 'http://file.szcjzhxq.com:8084/statics/2023/05/16/共产党_20230516085901A231.jpg', '0', '1', '测试单位', '1', '1', '党组织介绍:\n', 0, '2023-05-16 08:59:03', '15288888888', '2023-05-16 08:59:03', '15288888888', '0');
INSERT INTO `bus_party` VALUES (3, '东桥社区党组织', 6, '0', 0, 0, 'http://file.szcjzhxq.com/statics/2023/06/05/男_20230605152643A065.png', '0', '2', '东桥社区党组织', '2', '1', '东桥社区党组织', 0, '2023-06-05 15:26:46', '15656332018', '2023-06-05 15:26:46', '15656332018', '0');
INSERT INTO `bus_party` VALUES (4, '世纪阳江党组织', 6, '1', 3, 3, 'http://file.szcjzhxq.com/statics/2023/06/05/2_20230605155657A066.jpg', '0', '2', '世纪阳江党组织', '2', '3', '城建·世纪阳江党组织', 19, '2023-06-05 15:57:00', '15656332018', '2023-06-05 15:57:00', '15656332018', '0');
INSERT INTO `bus_party` VALUES (5, '彩金湖畔党组织', 7, '0', 0, 0, 'http://file.szcjzhxq.com/statics/2023/07/03/女_20230703171915A401.png', '0', '2', '无', '2', '3', '测试', 0, '2023-07-03 17:19:19', '18888888888', '2023-07-03 17:19:19', '18888888888', '0');
INSERT INTO `bus_party` VALUES (6, '测试', 1, '1', 1, 1, 'http://file.szcjzhxq.com/statics/2023/10/27/v2-bb2b871d102ff9dee80c3b7bd7d0485b_r_20231027150124A885.jpg', '0', '7', '测是', '12', '1', '2', 0, '2023-10-27 15:01:27', '15288888888', '2023-10-27 15:01:27', '15288888888', '1');

-- ----------------------------
-- Table structure for bus_party_pay
-- ----------------------------
DROP TABLE IF EXISTS `bus_party_pay`;
CREATE TABLE `bus_party_pay`  (
  `log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `party_id` bigint NOT NULL DEFAULT 0 COMMENT '所属党组织id，表xccj_govern.bus_party',
  `member_id` bigint NOT NULL DEFAULT 0 COMMENT '党员id，表xccj_govern.bus_member',
  `member_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '党员姓名',
  `member_phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '党员手机号',
  `pay_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '类型:1=正常,2=预缴,3=补缴',
  `party_fee` decimal(10, 2) NOT NULL COMMENT '缴费金额',
  `pay_month` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '缴费月份（yyyy-MM）',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '缴费时间',
  `pay_way` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '缴费方式:1=支付宝,2=微信,3=线下',
  `collect_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '归集状态:0=未归集,1=已归集',
  `collect_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '归集人',
  `collect_time` datetime NULL DEFAULT NULL COMMENT '归集时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除状态 0 未删除 1 已删除',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '党费缴费记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_party_pay
-- ----------------------------
INSERT INTO `bus_party_pay` VALUES (1, 1, 2, 1, '业主戊', '13600000000', '1', 0.00, '2023-05', '2023-05-18 08:39:37', '3', '0', '', NULL, '0');
INSERT INTO `bus_party_pay` VALUES (2, 1, 2, 1, '业主戊', '13600000000', '3', 1000.00, '2023-04', '2023-05-18 08:42:06', '3', '1', '15277777777', NULL, '0');
INSERT INTO `bus_party_pay` VALUES (3, 6, 4, 3, '傅强', '15656332018', '1', 0.00, '2023-06', '2023-06-05 15:59:27', '3', '1', '15656332018', NULL, '0');
INSERT INTO `bus_party_pay` VALUES (4, 6, 4, 3, '傅强', '15656332018', '2', 20.00, '2023-07', '2023-06-05 16:00:59', '3', '1', '15656332018', NULL, '0');
INSERT INTO `bus_party_pay` VALUES (5, 1, 1, 5, '王志国', '13600000020', '1', 4.00, '2023-10', '2023-10-27 15:07:48', '3', '0', '', NULL, '0');

-- ----------------------------
-- Table structure for bus_prevent
-- ----------------------------
DROP TABLE IF EXISTS `bus_prevent`;
CREATE TABLE `bus_prevent`  (
  `prevent_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `estate_id` bigint NOT NULL DEFAULT 0 COMMENT '物业id，表xccj_estate_sys.bus_estate',
  `prevent_title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '图片',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  `community_id` bigint NULL DEFAULT NULL COMMENT '小区id',
  `prevent_type_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型xccj_govern.bus_prevent_type',
  `brief_introduction` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`prevent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '防控信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_prevent
-- ----------------------------
INSERT INTO `bus_prevent` VALUES (1, 2, '精准打造治安防控体系 ', 'http://file.szcjzhxq.com/statics/2023/06/02/210924233115O14-0-lp_20230602092409A010.jpg', '<p><span style=\"color: rgb(25, 25, 25);\">持续发挥“机制建设年”的牵引作用，合理规划设置公安检查站，打造集信息采集、指挥调度、布控预警、智能核查拦截、综合管理为一体的战斗实体，全面提升公安检查站打击犯罪、反恐防暴、服务群众的能力和水平。</span></p>', '15288888888', '2023-05-18 18:00:54', '15288888888', '2023-06-02 09:24:12', '0', NULL, NULL, NULL);
INSERT INTO `bus_prevent` VALUES (2, 2, '强化治安整体防控多管齐下 ', 'http://file.szcjzhxq.com/statics/2023/06/02/20220120024601461_20230602092346A009.jpg', '<p><span style=\"color: rgb(25, 25, 25);\">建立强有力的社会矫正环境，使刑满释放人员安全回归社会。唐小龙，老默都是刑满释放人员。二人本来应该真心悔过，重新做人，但最后都成了高启强犯罪团伙的帮凶，唐小龙因罪大恶极，被执行死刑；老默为报答高启强的“恩情”而致命。二人悲惨的结局让人痛心，有其个人本性不改的原因，也有高启强黑恶势力胁迫的原因，还有社会矫正部门的失职。试想，如果二人所在的社区能够对他们施以援手，监督他们的日常行为，二人也许不会重新犯罪，走上不归路。</span></p><p><span style=\"color: rgb(25, 25, 25);\">建立廉洁高效的政治干部队伍，对扫黑除恶，打击犯罪意义重大。古人言，其身正，不令而行，其身不正，虽令不从。换言之，打铁的人必须是铁打的人。杨建，曾经的缉毒队队长，他采取非法手段当上强盛供电局副局长之后，与高启强沆瀣一气，攫取钱财，目的竟然是为了当区委书记孟德海女婿的面子。靠本事吃饭，过上富裕的生活，这本没错，但须以遵纪守法为底线。刑警队队长张彪为唐小龙违法犯罪通风</span></p>', '15288888888', '2023-05-18 18:02:32', '15288888888', '2023-06-02 09:23:49', '0', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for bus_prevent_rang
-- ----------------------------
DROP TABLE IF EXISTS `bus_prevent_rang`;
CREATE TABLE `bus_prevent_rang`  (
  `prevent_id` bigint NOT NULL DEFAULT 0 COMMENT '防控id，表xccj_govern.bus_prevent',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '防控信息-小区关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_prevent_rang
-- ----------------------------
INSERT INTO `bus_prevent_rang` VALUES (2, 1);
INSERT INTO `bus_prevent_rang` VALUES (1, 1);

-- ----------------------------
-- Table structure for bus_prevent_safety
-- ----------------------------
DROP TABLE IF EXISTS `bus_prevent_safety`;
CREATE TABLE `bus_prevent_safety`  (
  `prevent_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `estate_id` bigint NOT NULL DEFAULT 0 COMMENT '物业id，表xccj_estate_sys.bus_estate',
  `community_id` bigint NULL DEFAULT NULL COMMENT '小区id',
  `prevent_title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '图片',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `prevent_type_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '治安类型',
  `brief_introduction` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简介',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`prevent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '治安防控信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bus_prevent_safety
-- ----------------------------

-- ----------------------------
-- Table structure for bus_prevent_type
-- ----------------------------
DROP TABLE IF EXISTS `bus_prevent_type`;
CREATE TABLE `bus_prevent_type`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '名称',
  `backgroup_color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '背景颜色',
  `sort` int NOT NULL DEFAULT 0 COMMENT '顺序',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '防控信息类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bus_prevent_type
-- ----------------------------

-- ----------------------------
-- Table structure for bus_ques_answer
-- ----------------------------
DROP TABLE IF EXISTS `bus_ques_answer`;
CREATE TABLE `bus_ques_answer`  (
  `answer_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` bigint NOT NULL DEFAULT 0 COMMENT '答题人账号id，表xccj_community.bus_account',
  `table_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '主表类型：0=问卷调查（bus_questionnaire），1=党建调研（bus_research）',
  `table_id` bigint NOT NULL DEFAULT 0 COMMENT '对应表id',
  `question_id` bigint NOT NULL DEFAULT 0 COMMENT '题目id，表xccj_govern.bus_question',
  `user_answer` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '答案，若是选择题，存选项id英文逗号拼接，简答题存文字',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`answer_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 231 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '问卷答案表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_ques_answer
-- ----------------------------
INSERT INTO `bus_ques_answer` VALUES (216, 9, '0', 2, 11, '24', '0');
INSERT INTO `bus_ques_answer` VALUES (217, 9, '0', 2, 12, '29,28', '0');
INSERT INTO `bus_ques_answer` VALUES (218, 9, '0', 2, 13, '园区绿植需要多浇水', '0');
INSERT INTO `bus_ques_answer` VALUES (219, 41, '0', 4, 17, '41', '0');
INSERT INTO `bus_ques_answer` VALUES (220, 41, '0', 4, 18, '46', '0');
INSERT INTO `bus_ques_answer` VALUES (221, 41, '0', 4, 19, '暂无', '0');
INSERT INTO `bus_ques_answer` VALUES (222, 19, '0', 5, 20, '50', '0');
INSERT INTO `bus_ques_answer` VALUES (223, 19, '0', 5, 21, '53', '0');
INSERT INTO `bus_ques_answer` VALUES (224, 19, '0', 5, 22, '58,57,56', '0');
INSERT INTO `bus_ques_answer` VALUES (225, 19, '0', 5, 23, '暂无', '0');
INSERT INTO `bus_ques_answer` VALUES (226, 9, '1', 1, 1, '2', '0');
INSERT INTO `bus_ques_answer` VALUES (227, 9, '1', 1, 2, '4', '0');
INSERT INTO `bus_ques_answer` VALUES (228, 9, '1', 1, 3, '9', '0');
INSERT INTO `bus_ques_answer` VALUES (229, 9, '1', 1, 4, '同意', '0');
INSERT INTO `bus_ques_answer` VALUES (230, 9, '1', 1, 5, '赞成', '0');

-- ----------------------------
-- Table structure for bus_ques_community
-- ----------------------------
DROP TABLE IF EXISTS `bus_ques_community`;
CREATE TABLE `bus_ques_community`  (
  `questionnaire_id` bigint NOT NULL DEFAULT 0 COMMENT '问卷调查id，表xccj_govern.bus_questionnaire',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '问卷调查-小区关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_ques_community
-- ----------------------------
INSERT INTO `bus_ques_community` VALUES (1, 1);
INSERT INTO `bus_ques_community` VALUES (2, 1);
INSERT INTO `bus_ques_community` VALUES (2, 4);
INSERT INTO `bus_ques_community` VALUES (3, 1);
INSERT INTO `bus_ques_community` VALUES (4, 1);
INSERT INTO `bus_ques_community` VALUES (5, 6);
INSERT INTO `bus_ques_community` VALUES (6, 1);

-- ----------------------------
-- Table structure for bus_question
-- ----------------------------
DROP TABLE IF EXISTS `bus_question`;
CREATE TABLE `bus_question`  (
  `question_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `table_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '主表类型：0=问卷调查（bus_questionnaire），1=党建调研（bus_research）',
  `table_id` bigint NOT NULL DEFAULT 0 COMMENT '对应表id',
  `question_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '题目名称',
  `question_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '题目类型：0=单选，1=多选，2=简答',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`question_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '题目表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_question
-- ----------------------------
INSERT INTO `bus_question` VALUES (1, '1', 1, '1.世界上第一个无产阶级政党的第一部组织章程是什么？', '0', '0');
INSERT INTO `bus_question` VALUES (2, '1', 1, '世界上第一部无产阶级政党章程是谁参与制定的？', '0', '0');
INSERT INTO `bus_question` VALUES (3, '1', 1, '把入党誓词写入党章，始于(    )党章。', '0', '0');
INSERT INTO `bus_question` VALUES (4, '1', 1, '防范和破解机关党建调研“两张票”问题研究', '2', '0');
INSERT INTO `bus_question` VALUES (5, '1', 1, '紧扣“三线”确保“四不”', '2', '0');
INSERT INTO `bus_question` VALUES (6, '1', 2, '城市基层党建什么最重', '0', '0');
INSERT INTO `bus_question` VALUES (7, '1', 2, '加强城市基层党建是基层党建领域的全新课题。请结合我市街道社区、机关、高校、“两新”组织和国有企业等领域党建工作实际，提出你的思考', '2', '0');
INSERT INTO `bus_question` VALUES (8, '0', 1, '您选择该社区作为居住地的理由是：', '0', '0');
INSERT INTO `bus_question` VALUES (9, '0', 1, '以下服务项目，请选出您认为重要：', '1', '0');
INSERT INTO `bus_question` VALUES (10, '0', 1, '觉得我们小区还有哪些可以改进的地方。', '2', '0');
INSERT INTO `bus_question` VALUES (11, '0', 2, '请问您对本小区的整体服务满意度如何？', '0', '0');
INSERT INTO `bus_question` VALUES (12, '0', 2, '以下哪项社区服务对您最为重要？您可以选择多项。', '1', '0');
INSERT INTO `bus_question` VALUES (13, '0', 2, '请您分享您对社区服务质量的任何', '2', '0');
INSERT INTO `bus_question` VALUES (14, '0', 3, '社区服务质量', '0', '0');
INSERT INTO `bus_question` VALUES (15, '0', 3, '您认为小区的安保措施是否得到了有效的执行？', '1', '0');
INSERT INTO `bus_question` VALUES (16, '0', 3, '说出您的意见与建议', '2', '0');
INSERT INTO `bus_question` VALUES (17, '0', 4, '您目前对物业服务是否满意', '0', '0');
INSERT INTO `bus_question` VALUES (18, '0', 4, '您认为小区的安保措施是否得到了有效的执行？', '1', '0');
INSERT INTO `bus_question` VALUES (19, '0', 4, '您对物业有什么意见', '2', '0');
INSERT INTO `bus_question` VALUES (20, '0', 5, '是否赞成成立业主委员会？', '0', '0');
INSERT INTO `bus_question` VALUES (21, '0', 5, '业主委员会成员如何选定？', '0', '0');
INSERT INTO `bus_question` VALUES (22, '0', 5, '业主委员会组成结构', '1', '0');
INSERT INTO `bus_question` VALUES (23, '0', 5, '对业主委员会成立的意见？', '2', '0');
INSERT INTO `bus_question` VALUES (24, '0', 6, '测试', '0', '0');
INSERT INTO `bus_question` VALUES (25, '0', 6, '测试', '1', '0');
INSERT INTO `bus_question` VALUES (26, '0', 6, '测试', '2', '0');

-- ----------------------------
-- Table structure for bus_question_item
-- ----------------------------
DROP TABLE IF EXISTS `bus_question_item`;
CREATE TABLE `bus_question_item`  (
  `item_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `question_id` bigint NOT NULL DEFAULT 0 COMMENT '题目id，表xccj_govern.bus_question(question_type!=2)',
  `item_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '选项名称',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '题目选项表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_question_item
-- ----------------------------
INSERT INTO `bus_question_item` VALUES (1, 1, '国际工人协会共同章程', '0');
INSERT INTO `bus_question_item` VALUES (2, 1, '俄国社会民主工党章程', '0');
INSERT INTO `bus_question_item` VALUES (3, 1, '共产主义者同盟章程', '0');
INSERT INTO `bus_question_item` VALUES (4, 2, '马克思', '0');
INSERT INTO `bus_question_item` VALUES (5, 2, '马克思、恩格斯', '0');
INSERT INTO `bus_question_item` VALUES (6, 2, '列宁', '0');
INSERT INTO `bus_question_item` VALUES (7, 3, '党的八大', '0');
INSERT INTO `bus_question_item` VALUES (8, 3, '党的十二大', '0');
INSERT INTO `bus_question_item` VALUES (9, 3, '党的十四大', '0');
INSERT INTO `bus_question_item` VALUES (10, 6, 'A.重在思维', '0');
INSERT INTO `bus_question_item` VALUES (11, 6, 'B.重在统筹', '0');
INSERT INTO `bus_question_item` VALUES (12, 8, 'A、本地人', '0');
INSERT INTO `bus_question_item` VALUES (13, 8, 'B、距离近', '0');
INSERT INTO `bus_question_item` VALUES (14, 8, 'C、房租便宜', '0');
INSERT INTO `bus_question_item` VALUES (15, 8, 'D、来此地经商', '0');
INSERT INTO `bus_question_item` VALUES (16, 8, 'E、基础设施齐全', '0');
INSERT INTO `bus_question_item` VALUES (17, 8, 'F、环境比较好', '0');
INSERT INTO `bus_question_item` VALUES (18, 9, 'A、车辆存放', '0');
INSERT INTO `bus_question_item` VALUES (19, 9, 'B、健康咨询', '0');
INSERT INTO `bus_question_item` VALUES (20, 9, 'C、生活用品维修', '0');
INSERT INTO `bus_question_item` VALUES (21, 9, 'D、废品回收', '0');
INSERT INTO `bus_question_item` VALUES (22, 9, 'E、图书借阅', '0');
INSERT INTO `bus_question_item` VALUES (23, 11, '非常满意', '0');
INSERT INTO `bus_question_item` VALUES (24, 11, '满意', '0');
INSERT INTO `bus_question_item` VALUES (25, 11, '一般', '0');
INSERT INTO `bus_question_item` VALUES (26, 11, '不满意', '0');
INSERT INTO `bus_question_item` VALUES (27, 11, '非常不满意', '0');
INSERT INTO `bus_question_item` VALUES (28, 12, '绿化及环境卫生管理', '0');
INSERT INTO `bus_question_item` VALUES (29, 12, '安保和社区安全', '0');
INSERT INTO `bus_question_item` VALUES (30, 12, '设施维护和保养', '0');
INSERT INTO `bus_question_item` VALUES (31, 12, '社区活动组织', '0');
INSERT INTO `bus_question_item` VALUES (32, 12, '物业管理响应速度', '0');
INSERT INTO `bus_question_item` VALUES (33, 14, '非常满意', '0');
INSERT INTO `bus_question_item` VALUES (34, 14, '满意', '0');
INSERT INTO `bus_question_item` VALUES (35, 14, '较满意', '0');
INSERT INTO `bus_question_item` VALUES (36, 14, '不满意', '0');
INSERT INTO `bus_question_item` VALUES (37, 15, '1', '0');
INSERT INTO `bus_question_item` VALUES (38, 15, '2', '0');
INSERT INTO `bus_question_item` VALUES (39, 15, '3', '0');
INSERT INTO `bus_question_item` VALUES (40, 15, '4', '0');
INSERT INTO `bus_question_item` VALUES (41, 17, '非常满意', '0');
INSERT INTO `bus_question_item` VALUES (42, 17, '较为满意', '0');
INSERT INTO `bus_question_item` VALUES (43, 17, '满意', '0');
INSERT INTO `bus_question_item` VALUES (44, 17, '不满意', '0');
INSERT INTO `bus_question_item` VALUES (45, 17, '非常不满意', '0');
INSERT INTO `bus_question_item` VALUES (46, 18, '是的，非常有效', '0');
INSERT INTO `bus_question_item` VALUES (47, 18, '是的，有一定效果', '0');
INSERT INTO `bus_question_item` VALUES (48, 18, '一般，还有改进空间', '0');
INSERT INTO `bus_question_item` VALUES (49, 18, '不太有效', '0');
INSERT INTO `bus_question_item` VALUES (50, 20, '同意', '0');
INSERT INTO `bus_question_item` VALUES (51, 20, '反对', '0');
INSERT INTO `bus_question_item` VALUES (52, 20, '弃权', '0');
INSERT INTO `bus_question_item` VALUES (53, 21, '线上投票选举', '0');
INSERT INTO `bus_question_item` VALUES (54, 21, '线下报名', '0');
INSERT INTO `bus_question_item` VALUES (55, 21, '其他', '0');
INSERT INTO `bus_question_item` VALUES (56, 22, '会长', '0');
INSERT INTO `bus_question_item` VALUES (57, 22, '副会长', '0');
INSERT INTO `bus_question_item` VALUES (58, 22, '成员', '0');
INSERT INTO `bus_question_item` VALUES (59, 24, '1', '0');
INSERT INTO `bus_question_item` VALUES (60, 24, '3', '0');
INSERT INTO `bus_question_item` VALUES (61, 25, '1', '0');
INSERT INTO `bus_question_item` VALUES (62, 25, '2', '0');
INSERT INTO `bus_question_item` VALUES (63, 25, '3', '0');

-- ----------------------------
-- Table structure for bus_questionnaire
-- ----------------------------
DROP TABLE IF EXISTS `bus_questionnaire`;
CREATE TABLE `bus_questionnaire`  (
  `questionnaire_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `estate_id` bigint NOT NULL DEFAULT 0 COMMENT '物业id，表xccj_estate_sys.bus_estate',
  `ques_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '问卷调查名称',
  `ques_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '状态：0=启用，1=关闭',
  `start_date` date NULL DEFAULT NULL COMMENT '开始日期',
  `end_date` date NULL DEFAULT NULL COMMENT '结束日期',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`questionnaire_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '问卷调查表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_questionnaire
-- ----------------------------
INSERT INTO `bus_questionnaire` VALUES (1, 2, '社区问卷', '0', '2023-05-18', '2023-05-31', '15288888888', '2023-05-18 17:27:54', '', '2023-05-18 17:27:54', '0');
INSERT INTO `bus_questionnaire` VALUES (2, 2, '单选题、多选题、单行文本问答题', '0', '2023-05-20', '2023-05-24', '15288888888', '2023-05-20 10:27:03', '', '2023-05-20 10:27:03', '0');
INSERT INTO `bus_questionnaire` VALUES (3, 2, '2023年小区物业服务质量', '0', '2023-05-23', '2023-05-24', '15288888888', '2023-05-23 16:48:48', '', '2023-05-23 16:48:48', '0');
INSERT INTO `bus_questionnaire` VALUES (4, 2, '2023年物业服务调查问卷', '0', '2023-05-24', '2023-05-25', '15288888888', '2023-05-24 14:28:44', '', '2023-05-24 14:28:44', '0');
INSERT INTO `bus_questionnaire` VALUES (5, 5, '关于城建·世纪阳江小区是否需要建立业主委员会的问卷调查', '0', '2023-05-25', '2023-06-01', '15656332018', '2023-05-25 08:54:28', '', '2023-05-25 08:54:28', '0');
INSERT INTO `bus_questionnaire` VALUES (6, 2, '测试', '0', '2023-10-27', '2023-11-30', '15288888888', '2023-10-27 15:57:02', '', '2023-10-27 15:57:02', '1');

-- ----------------------------
-- Table structure for bus_rescue
-- ----------------------------
DROP TABLE IF EXISTS `bus_rescue`;
CREATE TABLE `bus_rescue`  (
  `rescue_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `rescue_title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '所在位置',
  `release_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '发布人类型：0=业主，1=租客',
  `release_id` bigint NOT NULL DEFAULT 0 COMMENT '发布人id，表xccj_community.bus_owner/bus_tenant',
  `need_num` bigint NOT NULL DEFAULT 0 COMMENT '需要人数',
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '具体内容',
  `start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
  `contact_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '紧急联系人',
  `contact_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '联系电话',
  `release_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`rescue_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '社区救助表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_rescue
-- ----------------------------
INSERT INTO `bus_rescue` VALUES (1, 2, '1', '2', '0', 5, 2, '1', '2023-05-15 10:24:28', '2023-05-15 10:34:31', '', '', '2023-05-15 10:21:56', '0');
INSERT INTO `bus_rescue` VALUES (2, 1, '生活不便', '小区1单元101室', '0', 11, 2, '生活不方便', '2023-05-19 09:00:00', '2023-05-19 12:49:57', '', '', '2023-05-19 08:50:31', '0');
INSERT INTO `bus_rescue` VALUES (3, 1, '一个人挪不动窗', '1栋二单元1907室', '0', 11, 1, '天热了，想把床挪到窗口边。', '2023-05-19 19:00:00', '2023-05-19 19:15:00', '', '', '2023-05-19 15:15:29', '0');
INSERT INTO `bus_rescue` VALUES (4, 1, '年纪大了，腿脚不便，不好下楼买菜', '1栋2单元19楼', '0', 10, 2, '年纪大了，腿脚不便，不好下楼买菜', '2023-05-19 17:00:00', '2023-05-19 18:00:00', '', '', '2023-05-19 15:42:27', '0');
INSERT INTO `bus_rescue` VALUES (5, 1, '生活不便希望协助', '1栋二单元', '0', 11, 2, '希望能帮忙买一下菜', '2023-05-22 09:00:00', '2023-05-22 10:00:00', '', '', '2023-05-20 15:49:41', '0');
INSERT INTO `bus_rescue` VALUES (6, 1, '帮助抬个桌子', '1栋2单元204室', '0', 11, 1, '需要帮助', '2023-06-25 10:00:00', '2023-06-25 11:00:00', '', '', '2023-05-23 08:41:46', '0');
INSERT INTO `bus_rescue` VALUES (7, 1, '钥匙好像丢在停车场了，有谁看到了吗', '停车产', '0', 10, 10, '帮忙找一下', '2023-05-23 08:48:32', '2023-09-23 08:47:38', '', '', '2023-05-23 08:48:15', '0');
INSERT INTO `bus_rescue` VALUES (8, 6, '刚刚', '1235', '0', 16, 1, '忽略', '2023-05-23 11:50:45', '2023-05-23 11:51:48', '', '', '2023-05-23 11:49:20', '0');
INSERT INTO `bus_rescue` VALUES (9, 1, '我要求助', '安徽智能产业园', '0', 11, 1, '快来救我', '2023-05-23 14:15:09', '2023-05-23 20:16:13', '', '', '2023-05-23 14:16:11', '0');
INSERT INTO `bus_rescue` VALUES (10, 1, '测试救助', '广场', '0', 11, 2, 'help me', '2023-06-07 11:44:06', '2023-06-07 12:43:14', '', '', '2023-06-07 11:43:54', '0');
INSERT INTO `bus_rescue` VALUES (11, 1, '测试自行查看', '1', '0', 42, 2, '1111', '2023-06-07 11:47:03', '2023-06-08 11:46:05', '', '', '2023-06-07 11:46:38', '0');
INSERT INTO `bus_rescue` VALUES (12, 7, '紧急救助！', '彩金湖畔Y1#单元门', '0', 74, 2, '需要紧急救助！', '2023-07-05 16:17:54', '2023-07-10 16:17:59', '', '', '2023-07-03 16:18:58', '0');

-- ----------------------------
-- Table structure for bus_rescue_user
-- ----------------------------
DROP TABLE IF EXISTS `bus_rescue_user`;
CREATE TABLE `bus_rescue_user`  (
  `rescue_id` bigint NOT NULL DEFAULT 0 COMMENT '社区救助id，表xccj_govern.bus_rescue',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '人员姓名',
  `user_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '手机号'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '社区救助-志愿者关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_rescue_user
-- ----------------------------
INSERT INTO `bus_rescue_user` VALUES (3, '业主戊', '13600000000');
INSERT INTO `bus_rescue_user` VALUES (4, '业主戊', '13600000000');
INSERT INTO `bus_rescue_user` VALUES (5, '小李', '13600000005');
INSERT INTO `bus_rescue_user` VALUES (5, '业主戊', '13600000000');
INSERT INTO `bus_rescue_user` VALUES (11, '业主戊', '13600000000');
INSERT INTO `bus_rescue_user` VALUES (7, '业主戊', '13600000000');

-- ----------------------------
-- Table structure for bus_research
-- ----------------------------
DROP TABLE IF EXISTS `bus_research`;
CREATE TABLE `bus_research`  (
  `research_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `research_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `research_intro` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '简介',
  `research_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '状态：0=启用，1=关闭',
  `deadline_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '截止时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '党建调研 状态0=未发布1=进行中2=已提交3=已结束',
  `start_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  PRIMARY KEY (`research_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '党建调研表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_research
-- ----------------------------
INSERT INTO `bus_research` VALUES (1, 1, '党建调研|', '开展工作调研 ', '1', '2023-05-31 00:00:00', '15277777777', '2023-05-17 18:15:52', '', '2023-05-17 18:15:52', '0', '1', '2024-07-26 15:10:54');
INSERT INTO `bus_research` VALUES (2, 1, '党建调研|党建工作', '党建调研|党建工作', '0', '2023-05-31 00:00:00', '15277777777', '2023-05-17 18:19:56', '', '2023-05-17 18:19:56', '0', '1', '2024-07-26 15:10:54');

-- ----------------------------
-- Table structure for bus_research_party
-- ----------------------------
DROP TABLE IF EXISTS `bus_research_party`;
CREATE TABLE `bus_research_party`  (
  `research_id` bigint NOT NULL DEFAULT 0 COMMENT '党建调研id，表xccj_govern.bus_research',
  `party_id` bigint NOT NULL DEFAULT 0 COMMENT '党组织id，表xccj_govern.bus_party'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '党建调研-党组织关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_research_party
-- ----------------------------
INSERT INTO `bus_research_party` VALUES (1, 2);
INSERT INTO `bus_research_party` VALUES (2, 1);

-- ----------------------------
-- Table structure for bus_social
-- ----------------------------
DROP TABLE IF EXISTS `bus_social`;
CREATE TABLE `bus_social`  (
  `social_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `estate_id` bigint NOT NULL DEFAULT 0 COMMENT '物业id，表xccj_estate_sys.bus_estate',
  `community_id` bigint NULL DEFAULT NULL COMMENT '所属小区',
  `social_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '图片地址',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `social_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '类型：0=议事堂，1=红黑榜',
  `user_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '发布人类型：0=系统，1=业主，2=租客',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '发布人id，user_type对应表id',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '发布人姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`social_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '社交表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_social
-- ----------------------------
INSERT INTO `bus_social` VALUES (1, 4, NULL, 'http://file.szcjzhxq.com/statics/2023/05/15/file-1684116220733_20230515100400A104.jpeg', '环境', '0', '1', 4, '', '2023-05-15 10:04:14', '', '2023-05-15 10:04:14', '0');
INSERT INTO `bus_social` VALUES (2, 1, NULL, 'http://file.szcjzhxq.com/statics/2023/05/15/出租房图片1_20230515105326A151.jpeg', '我的房间', '0', '1', 9, '', '2023-05-15 10:53:32', '', '2023-05-15 10:53:32', '0');
INSERT INTO `bus_social` VALUES (3, 2, NULL, 'http://file.szcjzhxq.com/statics/2023/05/19/210424133312-1-1200_20230519090738A528.jpg', '“五一”假期结束 六月份很快又到端午节啦 今年还有哪些假期? 国庆假期怎么安排? 放假通知来了！', '0', '0', 10, '15288888888', '2023-05-19 09:07:41', '', '2023-05-19 09:07:41', '0');
INSERT INTO `bus_social` VALUES (4, 2, NULL, 'http://file.szcjzhxq.com/statics/2023/05/19/4_20230519091126A533.png', '根据中央文明办、最高人民法院等八部门《“构建诚信、惩戒失信”合作备忘录》，现依据有关部门诚信评价办法，发布我市2022年第1期诚信“红黑榜”，并对失信被执行人发出限制令：一是禁止乘坐飞机、列车软卧；二是限制在金融机构贷款或办理信用卡；三是失信被执行人为自然人的，不得担任企业的法定代表人、董事、监事、高级管理人员等。', '1', '0', 10, '15288888888', '2023-05-19 09:09:33', '15288888888', '2023-05-19 09:11:29', '0');
INSERT INTO `bus_social` VALUES (5, 5, NULL, '', '开开心心', '0', '1', 19, '', '2023-05-22 15:31:10', '', '2023-05-22 15:31:10', '0');
INSERT INTO `bus_social` VALUES (6, 2, NULL, 'http://file.szcjzhxq.com/statics/2023/05/22/会议_20230522171054A722.jpg', '发布', '0', '1', 9, '', '2023-05-22 17:10:57', '', '2023-05-22 17:10:57', '0');
INSERT INTO `bus_social` VALUES (7, 5, NULL, 'http://file.szcjzhxq.com/statics/2023/05/23/1684812839532_IMG_20230519_161322_20230523113424A738.jpg', '嘎嘎嘎', '0', '1', 19, '', '2023-05-23 11:34:28', '', '2023-05-23 11:34:28', '0');
INSERT INTO `bus_social` VALUES (8, 2, NULL, 'http://file.szcjzhxq.com/statics/2023/05/23/借条_20230523171809A801.jpg', '一栋一单元住户，李援朝经济困难与2015年5日四日借款300000用于装修，至今未还。', '1', '1', 9, '', '2023-05-23 17:18:37', '', '2023-05-23 17:18:37', '0');
INSERT INTO `bus_social` VALUES (9, 2, NULL, 'http://file.szcjzhxq.com/statics/2023/05/23/借条_20230523172026A802.jpg', '业主丁向业主戊借款3000万用于装修，逾期五年未还，在此公示。', '1', '1', 9, '', '2023-05-23 17:20:32', '', '2023-05-23 17:20:32', '0');
INSERT INTO `bus_social` VALUES (10, 2, NULL, 'http://file.szcjzhxq.com/statics/2023/05/23/chaobiao_20230523172625A803.png', '123123', '1', '1', 11, '', '2023-05-23 17:26:31', '', '2023-05-23 17:26:31', '0');
INSERT INTO `bus_social` VALUES (11, 2, NULL, 'http://file.szcjzhxq.com/statics/2023/05/23/20220120024601461_20230523172838A804.jpg', '关于小区物业费增加大家的看法是什么', '0', '1', 9, '', '2023-05-23 17:28:43', '', '2023-05-23 17:28:43', '0');
INSERT INTO `bus_social` VALUES (12, 2, NULL, 'http://file.szcjzhxq.com/statics/2023/06/03/鱼汤_20230603155217A037.jpg', '物业对老年人态度怎么样', '0', '1', 63, '', '2023-06-03 15:52:20', '', '2023-06-03 15:52:20', '0');
INSERT INTO `bus_social` VALUES (13, 2, NULL, '', '“红黑榜”名单，是守信联合激励和失信联合惩戒制度。', '1', '1', 11, '', '2023-06-06 16:18:34', '', '2023-06-06 16:18:34', '0');
INSERT INTO `bus_social` VALUES (14, 2, NULL, 'http://file.szcjzhxq.com/statics/2023/06/08/tmp_48c0147573440c1b1feab694d7b9ebdcfe54b4c677aba9b1_20230608120053A143.jpg', '小区减少绿化修筑停车场大家怎么看。', '0', '1', 11, '', '2023-06-08 12:00:56', '', '2023-06-08 12:00:56', '0');
INSERT INTO `bus_social` VALUES (15, 2, NULL, 'http://file.szcjzhxq.com/statics/2023/06/10/人脸05_20230610114316A216.png', '社交功能是否满足要求', '0', '1', 11, '', '2023-06-10 11:43:20', '', '2023-06-10 11:43:20', '0');
INSERT INTO `bus_social` VALUES (16, 2, NULL, 'http://file.szcjzhxq.com/statics/2023/10/27/v2-bb2b871d102ff9dee80c3b7bd7d0485b_r_20231027155940A888.jpg', '1', '0', '0', 10, '15288888888', '2023-10-27 15:59:43', '', '2023-10-27 15:59:43', '1');
INSERT INTO `bus_social` VALUES (17, 4, NULL, '', '测试', '1', '1', 206, '', '2023-10-28 11:07:42', '', '2023-10-28 11:07:42', '0');
INSERT INTO `bus_social` VALUES (18, 2, NULL, 'http://file.szcjzhxq.com/statics/2023/10/30/tmp_ae022856ed673ecad6822dfe56174bf2_20231030221039A906.jpg', '共产党万岁', '0', '1', 208, '', '2023-10-30 22:10:44', '', '2023-10-30 22:10:44', '0');

-- ----------------------------
-- Table structure for bus_social_comment
-- ----------------------------
DROP TABLE IF EXISTS `bus_social_comment`;
CREATE TABLE `bus_social_comment`  (
  `comment_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `social_id` bigint NOT NULL DEFAULT 0 COMMENT '社交id，表xccj_govern.bus_social',
  `user_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '评论人员类型：0=系统(sys_user)，1=业主(bus_account)，2=租客(bus_account)',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '评论人员id，user_type对应表id',
  `reply_id` bigint NOT NULL DEFAULT 0 COMMENT '评论id，表xccj_govern.bus_social_comment',
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '评论内容',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  `parent_comment_id` bigint NULL DEFAULT NULL COMMENT '父级id评论表主键(用于二级评论)',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '社交评论记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_social_comment
-- ----------------------------
INSERT INTO `bus_social_comment` VALUES (1, 5, '1', 19, 0, '赞赞赞赞赞赞', '傅强', '2023-05-22 15:31:30', '', '2023-05-22 15:31:30', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (2, 0, '1', 19, 0, 'q1', '傅强', '2023-05-23 11:34:51', '', '2023-05-23 11:34:51', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (3, 3, '1', 9, 0, '666', '业主戊', '2023-05-25 11:05:37', '', '2023-05-25 11:05:37', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (4, 11, '1', 9, 0, '999', '业主戊', '2023-05-25 11:39:18', '', '2023-05-25 11:39:18', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (5, 11, '1', 9, 0, '666', '业主戊', '2023-05-25 11:41:58', '', '2023-05-25 11:41:58', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (6, 11, '1', 9, 0, '999', '业主戊', '2023-05-25 11:43:58', '', '2023-05-25 11:43:58', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (7, 10, '1', 9, 0, '666999', '业主戊', '2023-05-25 11:44:13', '', '2023-05-25 11:44:13', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (8, 11, '1', 9, 0, '666', '业主戊', '2023-05-25 14:30:57', '', '2023-05-25 14:30:57', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (9, 9, '1', 9, 0, '厉害了', '业主戊', '2023-05-25 14:33:54', '', '2023-05-25 14:33:54', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (10, 10, '1', 9, 0, '666', '业主戊', '2023-05-25 14:34:12', '', '2023-05-25 14:34:12', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (11, 8, '1', 58, 0, '欠债还钱，天经地义', '凤傲天', '2023-05-25 16:31:49', '', '2023-05-25 16:31:49', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (12, 6, '1', 58, 0, '111', '凤傲天', '2023-05-25 17:44:48', '', '2023-05-25 17:44:48', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (13, 6, '1', 58, 0, '222', '凤傲天', '2023-05-25 17:45:23', '', '2023-05-25 17:45:23', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (14, 11, '1', 54, 0, '不赞成', '程然', '2023-05-25 17:45:29', '', '2023-05-25 17:45:29', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (15, 11, '1', 54, 0, '建议下调物业费', '程然', '2023-05-25 17:46:10', '', '2023-05-25 17:46:10', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (16, 6, '1', 54, 0, '支持党做出的决定', '程然', '2023-05-25 17:47:10', '', '2023-05-25 17:47:10', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (17, 4, '1', 54, 0, '支持 ', '程然', '2023-05-25 17:47:19', '', '2023-05-25 17:47:19', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (18, 11, '1', 58, 0, '测试评论', '凤傲天', '2023-05-26 13:56:47', '', '2023-05-26 13:56:47', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (19, 14, '1', 9, 0, '可以的', '业主戊', '2023-06-08 12:01:18', '', '2023-06-08 12:01:18', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (20, 14, '1', 9, 0, '123', '业主戊', '2023-06-08 14:38:57', '', '2023-06-08 14:38:57', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (21, 14, '1', 9, 0, '456', '业主戊', '2023-06-08 14:41:04', '', '2023-06-08 14:41:04', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (22, 14, '1', 9, 0, '123', '业主戊', '2023-06-08 14:43:22', '', '2023-06-08 14:43:22', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (23, 14, '1', 9, 0, '999', '业主戊', '2023-06-08 14:47:11', '', '2023-06-08 14:47:11', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (24, 14, '1', 9, 0, '666666', '业主戊', '2023-06-08 14:47:37', '', '2023-06-08 14:47:37', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (25, 14, '1', 9, 0, '厉害了', '业主戊', '2023-06-08 14:50:55', '', '2023-06-08 14:50:55', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (26, 14, '1', 9, 0, '厉害了！！！', '业主戊', '2023-06-08 14:51:20', '', '2023-06-08 14:51:20', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (27, 14, '1', 9, 0, '123', '业主戊', '2023-06-08 15:08:47', '', '2023-06-08 15:08:47', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (28, 14, '1', 9, 0, '赞成', '业主戊', '2023-06-09 09:45:18', '', '2023-06-09 09:45:18', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (29, 14, '1', 9, 0, '不同意此项措施', '业主戊', '2023-06-09 09:45:36', '', '2023-06-09 09:45:36', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (30, 14, '1', 9, 0, '拒绝', '业主戊', '2023-06-09 09:45:59', '', '2023-06-09 09:45:59', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (31, 14, '1', 9, 0, '卡住了', '业主戊', '2023-06-09 09:47:06', '', '2023-06-09 09:47:06', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (32, 14, '1', 9, 0, '内助消息', '业主戊', '2023-06-09 09:47:33', '', '2023-06-09 09:47:33', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (33, 14, '1', 9, 0, '不同意', '业主戊', '2023-06-09 09:47:55', '', '2023-06-09 09:47:55', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (34, 14, '1', 58, 0, '不同意+1', '凤傲天', '2023-06-09 09:58:41', '', '2023-06-09 09:58:41', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (35, 14, '1', 58, 0, '不同意+2', '凤傲天', '2023-06-09 10:09:29', '', '2023-06-09 10:09:29', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (36, 14, '1', 58, 0, '不同意+3', '凤傲天', '2023-06-09 10:10:50', '', '2023-06-09 10:10:50', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (37, 14, '1', 58, 0, '不同意+4', '凤傲天', '2023-06-09 10:11:38', '', '2023-06-09 10:11:38', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (38, 14, '1', 58, 0, '不同意+5', '凤傲天', '2023-06-09 10:12:58', '', '2023-06-09 10:12:58', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (39, 14, '1', 9, 0, '不同意+6', '业主戊', '2023-06-10 11:08:52', '', '2023-06-10 11:08:52', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (40, 14, '1', 9, 0, '不同意X', '业主戊', '2023-06-10 11:10:07', '', '2023-06-10 11:10:07', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (41, 15, '1', 9, 0, '满足要求', '业主戊', '2023-06-10 15:45:40', '', '2023-06-10 15:45:40', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (42, 14, '1', 9, 0, '我同意！！！！！', '业主戊', '2023-06-10 15:46:46', '', '2023-06-10 15:46:46', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (43, 15, '1', 9, 0, '阿斯顿发送到', '业主戊', '2023-06-10 15:51:58', '', '2023-06-10 15:51:58', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (44, 15, '1', 9, 0, '阿斯顿', '业主戊', '2023-06-10 16:08:16', '', '2023-06-10 16:08:16', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (45, 15, '1', 9, 0, 'AAS', '业主戊', '2023-06-10 16:08:44', '', '2023-06-10 16:08:44', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (46, 15, '1', 53, 0, '测试评论', '老人戌男', '2023-06-12 11:38:02', '', '2023-06-12 11:38:02', '0', NULL);
INSERT INTO `bus_social_comment` VALUES (47, 6, '0', 10, 0, '123', '15288888888', '2023-10-27 15:59:14', '', '2023-10-27 15:59:14', '1', NULL);
INSERT INTO `bus_social_comment` VALUES (48, 6, '0', 10, 0, '444', '15288888888', '2023-10-27 15:59:19', '', '2023-10-27 15:59:19', '1', NULL);

-- ----------------------------
-- Table structure for bus_social_like
-- ----------------------------
DROP TABLE IF EXISTS `bus_social_like`;
CREATE TABLE `bus_social_like`  (
  `social_id` bigint NOT NULL DEFAULT 0 COMMENT '社交id，表xccj_govern.bus_social',
  `user_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '评论人员类型：0=系统(sys_user)，1=业主(bus_account)，2=租客(bus_account)',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '人员id，user_type对应表id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '社交点赞记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_social_like
-- ----------------------------
INSERT INTO `bus_social_like` VALUES (3, '1', 9, NULL);
INSERT INTO `bus_social_like` VALUES (11, '1', 52, NULL);
INSERT INTO `bus_social_like` VALUES (11, '1', 58, NULL);
INSERT INTO `bus_social_like` VALUES (13, '1', 9, NULL);
INSERT INTO `bus_social_like` VALUES (14, '1', 9, NULL);
INSERT INTO `bus_social_like` VALUES (5, '1', 19, NULL);
INSERT INTO `bus_social_like` VALUES (1, '1', 239, NULL);

-- ----------------------------
-- Table structure for bus_social_rang
-- ----------------------------
DROP TABLE IF EXISTS `bus_social_rang`;
CREATE TABLE `bus_social_rang`  (
  `social_id` bigint NOT NULL DEFAULT 0 COMMENT '社交id，表xccj_govern.bus_social',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '社交-小区关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_social_rang
-- ----------------------------
INSERT INTO `bus_social_rang` VALUES (3, 1);
INSERT INTO `bus_social_rang` VALUES (4, 1);

-- ----------------------------
-- Table structure for bus_statute
-- ----------------------------
DROP TABLE IF EXISTS `bus_statute`;
CREATE TABLE `bus_statute`  (
  `statute_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `estate_id` bigint NOT NULL DEFAULT 0 COMMENT '物业id，表xccj_estate_sys.bus_estate',
  `statute_title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `cover_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '封面',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`statute_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '自治规约表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_statute
-- ----------------------------
INSERT INTO `bus_statute` VALUES (1, 2, '小区自治管理规约', 'http://file.szcjzhxq.com/statics/2023/05/19/426_20230519085108A510.jpg', '小区自治管理规约序 言为维护全体业主的共有财产权和共同管理权，维护小区的生活环境秩序，根据《中华人民共和国宪法》、《中华人民共和国民法通则》、《中华人民共和国物权法》、《物业管理条例》等法律法规的规定，以依法办事、业主自愿、服从多数意见并尊重少数意见为基本原则，制订本管理规约。本管理规约经业主大会表决通过，对本建筑区划内全体业主、业主大会、业主委员会、物业使用人、物业服务企业均具有约束力。第一章 总则第一条　全体业主组成业主大会，依法行使共同管理权，对专有部分建筑面积以外的共有财产和共有秩序进行管理。第二条　业主的共同管理权，包括享有平等的建议权、选举权、被选举权、知情权、质询权及罢免权等；按其专有部分占全部建筑面积的份额享有对专有部分建筑面积以外的共有部分可供分配的公共收益的分配权。第三条　业主大会设立常设机构单元代表议事会和业主委员会，在业主大会闭会期间共同行使管理权。第四条　本物业区域内与共同管理权有关的事务，由全体业主共同决定。第五条　业主委员会经全体业主选举产生，依照本规约和业主大会议事规则的授权，代表全体业主处理本小区与物业管理相关的事务，包括但不限于协调业主与物业服务企业或其他管理人之间的关系、监督并检查物业服务质量、协调物业服务纠纷、调解相邻权纠纷等。第二章 物业基本情况第六条 物业基本情况物业管理区域内的物业构成:本小区所有竣工交付使用的住宅楼及其附属设施（以政府部门规划为准）。住宅总户数： 户。房屋总建筑面积： 万平方米。物业类型：商品住宅第七条　规划用地许可确定的区域为本物业区域，其共有部分指全体业主拥有土地使用权范围内的所有道路、空地、绿地，及占用道路、空地或绿地形成的且没有明确权属的地面停车场；不为单个业主拥有的所有建筑物、构筑物；共同使用的部位及设施设备，包括房屋的承重结构（基础、承重墙体、梁柱、楼盖等），楼顶、外墙面，公共门厅、走廊、过道、垃圾房、邮政信箱、避雷装置、地下室、楼梯间、变配电系统、照明灯具、电梯系统、消防系', '15288888888', '2023-05-19 08:51:30', '', '2023-05-19 08:51:30', '0');
INSERT INTO `bus_statute` VALUES (2, 2, '自制规约', 'http://file.szcjzhxq.com/statics/2023/05/19/社区_20230519085307A514.png', '社区居民公约与社区居民自治章程区别(1)约力不同1、社区居民公约:公约多依靠自觉履行，当然也可以在里面规定一些有强制力的条款，但必须要在法律许可的范围内 (例如不能有罚款、人身强制、人格侮辱等)。2、社区居民自治章程:章程有绝对的强制因素在里面(2)制度的主体与程序不同1、社区居民公约:严格来说是当事人的一种约定，通过协商达成一致，比如:国际公约,2、社区居民自治章程:要有制作资格的单位按照一定的程序来制作，比方:学校的章程,(3)内容不同1、社区居民公约:内容较广泛，只要不犯法想定什么定什么。2、社区居民自治章程:要受制定机关的职权职责影响，更多是对一些原则性的基本要求。', '15288888888', '2023-05-19 08:53:17', '', '2023-05-19 08:53:17', '0');
INSERT INTO `bus_statute` VALUES (3, 5, '城建·世纪阳江小区自治条约', 'http://file.szcjzhxq.com/statics/2023/05/22/sjyj_20230522145954A642.png', '为了规范业主大会和业主委员会在物业管理中的活动，维护业主的共同利益和合法权益，根据国务院《物业管理条例》、《民法典》以及住建部《业主大会和业主委员会指导规则》等规定，制定本业主大会议事规则（以下简称本规则）。业主大会依法通过的决议、决定对本物业管理区域内的全体业主具有约束力。第一条 （业主委员会性质）本业主委员会作为业主大会的执行机构，由业主大会依法选举产生，向业主大会报告工作，接受业主大会、业主的监督，并依法、依本物业管理区域管理规约和业主大会议事规则、本规则约定履行相应的工作职责。第二条 （业主委员会职责）业主委员会履行下列职责：（一）依法忠实履行职责，执行业主大会决议、决定；（二）召集业主大会会议，报告物业管理的实施情况；（三）根据业主的意见、建议和要求，拟订选聘、解聘物业服务企业的议案，提交业主大会会议决定；（四）代表业主与业主大会选聘的物业服务企业签订物业服务合同；（五）根据业主的意见、建议和要求，拟订《业主大会议事规则》、《业主委员会工作规则》和《管理规约》修改方案，提交业主大会会议决定；（六）根据业主的意见、建议和要求，拟订物业管理区域内物业共有部分的使用、经营、收益分配方案，提交业主大会会议决定；（七）根据业主的意见、建议和要求，拟订建筑物及其附属设施维修资金使用、续筹方案，提交业主大会会议决定；（八）督促违反物业服务合同约定逾期不交纳物业服务费用的业主，限期交纳物业服务费用；（九）督促未按照管理规约的约定缴存专项维修资金的业主，限期交纳专项维修资金；（十）提请业主大会决定终止业主委员会委员资格，增补业主委员会委员；（十一）监督物业服务企业履行物业服务合同，并每年对物业服务企业按照物业服务合同约定履行物业服务情况进行一次全面考核评定；（十二）监督管理规约的实施，对违反管理规约的行为进行处理；（十三）对有关档案资料、会议记录、印章及其他属于业主大会的财物进行妥善保管；（十四）及时了解业主、使用人的意见和建议，形成议案提交业主大会会议决定；（十五）协调业主与业主之间以及业主与物业服务企业之间的关系，调处业主与业主之间以及业主与物业服务企业之间的物业管理纠纷；（十六）建立业主委员会接待制度，接受业主、使用人的咨询、投诉和监督，并定期将工作情况以公告等形式向全体业主报告；（十七）积极参加房产行政主管部门组织的物业管理培训，接受县（区）房产行政主管部门、乡镇人民政府、街道办事处、村（居）民委员会的指导与监督；', '15755177283', '2023-05-22 15:00:34', '', '2023-05-22 15:00:34', '0');
INSERT INTO `bus_statute` VALUES (4, 4, '613测试规约', 'http://file.szcjzhxq.com/statics/2023/06/13/426_20230613171910A359.jpg', '<strong>居中</strong>			首行换行', '15200000000', '2023-06-13 17:21:47', '15200000000', '2023-06-13 17:24:50', '0');

-- ----------------------------
-- Table structure for bus_statute_rang
-- ----------------------------
DROP TABLE IF EXISTS `bus_statute_rang`;
CREATE TABLE `bus_statute_rang`  (
  `statute_id` bigint NOT NULL DEFAULT 0 COMMENT '自治规约id，表xccj_govern.bus_statute',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '自治规约-小区关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_statute_rang
-- ----------------------------
INSERT INTO `bus_statute_rang` VALUES (1, 1);
INSERT INTO `bus_statute_rang` VALUES (2, 1);
INSERT INTO `bus_statute_rang` VALUES (2, 4);
INSERT INTO `bus_statute_rang` VALUES (3, 6);
INSERT INTO `bus_statute_rang` VALUES (4, 5);

-- ----------------------------
-- Table structure for bus_voluntary
-- ----------------------------
DROP TABLE IF EXISTS `bus_voluntary`;
CREATE TABLE `bus_voluntary`  (
  `voluntary_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `estate_id` bigint NOT NULL DEFAULT 0 COMMENT '物业id，表xccj_estate_sys.bus_estate',
  `act_title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '活动名称',
  `cover_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '封面',
  `start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
  `need_num` bigint NOT NULL DEFAULT 0 COMMENT '需要人数',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '活动所在小区id，表xccj_community.bus_community',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`voluntary_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '志愿者活动表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_voluntary
-- ----------------------------
INSERT INTO `bus_voluntary` VALUES (1, 2, '志愿者踊跃参加服务活动', 'http://file.szcjzhxq.com/statics/2023/05/19/志愿者_20230519091229A534.jpg', '2023-05-19 12:00:00', '2023-05-31 12:00:00', 5, '<p><span style=\"color: rgb(25, 25, 25);\">5月19日，为营造文明和谐，平安出行的城市道路交通环境，进一步弘扬志愿服务精神，提升辖区居民的文明素质，展示良好文明形象，助力新时代文明实践工作，交警大队面向社会公开招募的首批交通文明劝导志愿者在辖区开展劝导活动。</span></p>', 1, '2023-05-19 09:13:43', '15288888888', '2023-05-19 09:13:43', '0');
INSERT INTO `bus_voluntary` VALUES (2, 2, '测试', 'http://file.szcjzhxq.com/statics/2023/10/27/v2-bb2b871d102ff9dee80c3b7bd7d0485b_r_20231027160116A890.jpg', '2023-10-27 12:00:00', '2023-10-31 12:00:00', 1, 'null', 1, '2023-10-27 16:01:34', '15288888888', '2023-10-27 16:01:34', '1');

-- ----------------------------
-- Table structure for bus_voluntary_rang
-- ----------------------------
DROP TABLE IF EXISTS `bus_voluntary_rang`;
CREATE TABLE `bus_voluntary_rang`  (
  `voluntary_id` bigint NOT NULL DEFAULT 0 COMMENT '志愿者活动id，表xccj_govern.bus_voluntary',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '志愿者活动-小区关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_voluntary_rang
-- ----------------------------
INSERT INTO `bus_voluntary_rang` VALUES (1, 1);

-- ----------------------------
-- Table structure for bus_voluntary_user
-- ----------------------------
DROP TABLE IF EXISTS `bus_voluntary_user`;
CREATE TABLE `bus_voluntary_user`  (
  `voluntary_id` bigint NOT NULL DEFAULT 0 COMMENT '志愿者活动id，表xccj_govern.bus_voluntary',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '人员姓名',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '头像',
  `gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '2' COMMENT '性别：0=男，1=女，2=未知',
  `user_age` int NOT NULL DEFAULT 0 COMMENT '年龄',
  `card_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'U' COMMENT '证件类型：见数据字典 sys_certificate',
  `card_no` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '证件号码',
  `user_phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '联系电话',
  `apply_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '志愿者活动-人员关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_voluntary_user
-- ----------------------------

-- ----------------------------
-- Table structure for bus_vote
-- ----------------------------
DROP TABLE IF EXISTS `bus_vote`;
CREATE TABLE `bus_vote`  (
  `vote_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `estate_id` bigint NOT NULL DEFAULT 0 COMMENT '物业id，表xccj_estate_sys.bus_estate',
  `vote_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '投票名称',
  `vote_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '状态：0=启用，1=关闭',
  `data_source` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '数据来源：0=物业，1=业主委员会',
  `start_time` date NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` date NULL DEFAULT NULL COMMENT '结束时间',
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `has_notice` tinyint(1) NULL DEFAULT 0 COMMENT '是否已发布通知，1已发布，0未发布',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`vote_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '投票表决表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_vote
-- ----------------------------
INSERT INTO `bus_vote` VALUES (1, 2, '社区投票01全区', '0', '0', '2023-05-17', '2023-05-18', '<p>社区投票01全区</p>', '15277777777', '2023-05-17 15:34:14', '', '2023-05-17 15:34:14', 0, '0');
INSERT INTO `bus_vote` VALUES (2, 2, '社区投票02一栋', '0', '0', '2023-05-17', '2023-05-31', '<p>社区投票02一栋</p>', '15277777777', '2023-05-17 15:34:39', '', '2023-05-17 15:34:39', 0, '0');
INSERT INTO `bus_vote` VALUES (3, 2, '社区03一栋一单元', '0', '0', '2023-05-17', '2023-05-31', '<p>社区03一栋一单元</p>', '15277777777', '2023-05-17 15:34:58', '', '2023-05-17 15:34:58', 0, '0');
INSERT INTO `bus_vote` VALUES (4, 2, '社区04一栋2单元', '0', '0', '2023-05-17', '2023-05-31', '<p>社区04一栋2单元</p>', '15277777777', '2023-05-17 15:35:17', '', '2023-05-17 15:35:17', 0, '0');
INSERT INTO `bus_vote` VALUES (5, 2, '关于2023年上半年物业服务质量的投票', '0', '0', '2023-05-19', '2023-05-31', '<p>关于2023年上半年物业服务质量的投票</p>', '15288888888', '2023-05-19 09:36:10', '', '2023-05-19 09:36:10', 0, '0');
INSERT INTO `bus_vote` VALUES (6, 2, '测试投票', '0', '0', '2023-05-22', '2023-05-22', '<p><strong style=\"color: rgb(96, 98, 102);\">内容</strong></p>', '15288888888', '2023-05-22 10:48:17', '', '2023-05-22 10:48:17', 0, '0');
INSERT INTO `bus_vote` VALUES (7, 2, '测试投票最后一条', '0', '0', '2023-05-22', '2023-05-22', '<p>11111</p>', '15288888888', '2023-05-22 10:48:49', '', '2023-05-22 10:48:49', 0, '0');
INSERT INTO `bus_vote` VALUES (8, 5, '关于城建·世纪阳江小区成立业主委员会事宜的投票', '0', '0', '2023-05-22', '2023-06-01', '<p>是否同意城建·世纪阳江小区成立业主委员会?</p>', '15755177283', '2023-05-22 15:04:38', '', '2023-05-22 15:04:38', 0, '0');
INSERT INTO `bus_vote` VALUES (9, 6, '测试投票', '0', '0', '2023-08-13', '2023-09-01', '<p>关于是否动用维修基金的投票确认</p>', '18888888888', '2023-08-13 16:12:38', '', '2023-08-13 16:12:38', 0, '0');
INSERT INTO `bus_vote` VALUES (10, 6, '测试', '0', '0', '2023-08-13', '2023-09-01', '<p>是否同意？</p>', '18888888888', '2023-08-13 16:14:22', '', '2023-08-13 16:14:22', 0, '0');

-- ----------------------------
-- Table structure for bus_vote_building
-- ----------------------------
DROP TABLE IF EXISTS `bus_vote_building`;
CREATE TABLE `bus_vote_building`  (
  `vote_id` bigint NOT NULL COMMENT '主键',
  `building_id` bigint NULL DEFAULT NULL COMMENT '楼栋id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '社区投票楼栋范围关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_vote_building
-- ----------------------------
INSERT INTO `bus_vote_building` VALUES (2, 13);
INSERT INTO `bus_vote_building` VALUES (3, 13);
INSERT INTO `bus_vote_building` VALUES (4, 13);
INSERT INTO `bus_vote_building` VALUES (6, 13);
INSERT INTO `bus_vote_building` VALUES (7, 13);
INSERT INTO `bus_vote_building` VALUES (9, 59);
INSERT INTO `bus_vote_building` VALUES (9, 60);
INSERT INTO `bus_vote_building` VALUES (9, 61);

-- ----------------------------
-- Table structure for bus_vote_community
-- ----------------------------
DROP TABLE IF EXISTS `bus_vote_community`;
CREATE TABLE `bus_vote_community`  (
  `vote_id` bigint NOT NULL DEFAULT 0 COMMENT '投票表决id，表xccj_govern.bus_vote',
  `community_id` bigint NOT NULL DEFAULT 0 COMMENT '小区id，表xccj_community.bus_community'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '投票表决-小区关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_vote_community
-- ----------------------------
INSERT INTO `bus_vote_community` VALUES (1, 1);
INSERT INTO `bus_vote_community` VALUES (2, 1);
INSERT INTO `bus_vote_community` VALUES (3, 1);
INSERT INTO `bus_vote_community` VALUES (4, 1);
INSERT INTO `bus_vote_community` VALUES (5, 1);
INSERT INTO `bus_vote_community` VALUES (5, 4);
INSERT INTO `bus_vote_community` VALUES (6, 1);
INSERT INTO `bus_vote_community` VALUES (7, 1);
INSERT INTO `bus_vote_community` VALUES (8, 6);
INSERT INTO `bus_vote_community` VALUES (9, 7);
INSERT INTO `bus_vote_community` VALUES (10, 7);

-- ----------------------------
-- Table structure for bus_vote_item
-- ----------------------------
DROP TABLE IF EXISTS `bus_vote_item`;
CREATE TABLE `bus_vote_item`  (
  `item_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `vote_id` bigint NOT NULL DEFAULT 0 COMMENT '投票表决id，表xccj_govern.bus_vote',
  `item_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '选项名称',
  `vote_num` int NOT NULL DEFAULT 0 COMMENT '选择人数',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记：0=未删除，1=已删除',
  PRIMARY KEY (`item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '投票表决选项表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_vote_item
-- ----------------------------
INSERT INTO `bus_vote_item` VALUES (1, 1, '同意', 0, '0');
INSERT INTO `bus_vote_item` VALUES (2, 1, '不同意', 0, '0');
INSERT INTO `bus_vote_item` VALUES (3, 2, '同意', 1, '0');
INSERT INTO `bus_vote_item` VALUES (4, 2, '不同意', 1, '0');
INSERT INTO `bus_vote_item` VALUES (5, 3, '同意', 0, '0');
INSERT INTO `bus_vote_item` VALUES (6, 3, '不同意', 0, '0');
INSERT INTO `bus_vote_item` VALUES (7, 4, '同意', 1, '0');
INSERT INTO `bus_vote_item` VALUES (8, 4, '不同意', 1, '0');
INSERT INTO `bus_vote_item` VALUES (9, 5, '同意', 1, '0');
INSERT INTO `bus_vote_item` VALUES (10, 5, '不同意', 0, '0');
INSERT INTO `bus_vote_item` VALUES (11, 6, '同意', 0, '0');
INSERT INTO `bus_vote_item` VALUES (12, 6, '不同意', 0, '0');
INSERT INTO `bus_vote_item` VALUES (13, 7, '同意', 0, '0');
INSERT INTO `bus_vote_item` VALUES (14, 7, '不同意', 0, '0');
INSERT INTO `bus_vote_item` VALUES (15, 8, '同意', 0, '0');
INSERT INTO `bus_vote_item` VALUES (16, 8, '不同意', 1, '0');
INSERT INTO `bus_vote_item` VALUES (17, 9, '同意', 0, '0');
INSERT INTO `bus_vote_item` VALUES (18, 9, '不同意', 0, '0');
INSERT INTO `bus_vote_item` VALUES (19, 10, '同意', 1, '0');
INSERT INTO `bus_vote_item` VALUES (20, 10, '不同意', 0, '0');

-- ----------------------------
-- Table structure for bus_vote_record
-- ----------------------------
DROP TABLE IF EXISTS `bus_vote_record`;
CREATE TABLE `bus_vote_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `vote_id` bigint NOT NULL COMMENT '投票id',
  `owner_id` bigint NOT NULL COMMENT '投票业主id',
  `item_id` bigint NOT NULL COMMENT '投票结果id',
  `vote_time` datetime NOT NULL COMMENT '投票时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '投票表决记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_vote_record
-- ----------------------------
INSERT INTO `bus_vote_record` VALUES (1, 4, 10, 8, '2023-05-17 15:54:20');
INSERT INTO `bus_vote_record` VALUES (2, 4, 11, 7, '2023-05-17 15:55:13');
INSERT INTO `bus_vote_record` VALUES (3, 2, 11, 4, '2023-05-17 16:09:39');
INSERT INTO `bus_vote_record` VALUES (4, 8, 16, 16, '2023-05-22 15:04:49');
INSERT INTO `bus_vote_record` VALUES (5, 2, 29, 3, '2023-05-23 15:55:29');
INSERT INTO `bus_vote_record` VALUES (6, 5, 10, 9, '2023-05-24 14:14:47');
INSERT INTO `bus_vote_record` VALUES (7, 10, 74, 19, '2023-08-13 16:15:21');

-- ----------------------------
-- Table structure for bus_vote_unit
-- ----------------------------
DROP TABLE IF EXISTS `bus_vote_unit`;
CREATE TABLE `bus_vote_unit`  (
  `vote_id` bigint NOT NULL COMMENT '主键',
  `unit_id` bigint NULL DEFAULT NULL COMMENT '单元id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '社区投票单元范围关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bus_vote_unit
-- ----------------------------
INSERT INTO `bus_vote_unit` VALUES (3, 9);
INSERT INTO `bus_vote_unit` VALUES (4, 10);
INSERT INTO `bus_vote_unit` VALUES (6, 9);

SET FOREIGN_KEY_CHECKS = 1;
