/*
 Navicat Premium Data Transfer

 Source Server         : 个人
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : bt.weihuazhou.top:3306
 Source Schema         : wonder-test

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 02/01/2024 15:56:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_table
-- ----------------------------
DROP TABLE IF EXISTS `base_table`;
CREATE TABLE `base_table`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` int NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新人',
  `deleted` bit(1) NULL DEFAULT NULL COMMENT '已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of base_table
-- ----------------------------

-- ----------------------------
-- Table structure for ex_book
-- ----------------------------
DROP TABLE IF EXISTS `ex_book`;
CREATE TABLE `ex_book`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '名称',
  `number` int NULL DEFAULT NULL COMMENT '数量',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` int NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新人',
  `deleted` bit(1) NULL DEFAULT NULL COMMENT '已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '图书管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ex_book
-- ----------------------------
INSERT INTO `ex_book` VALUES (1, '爆笑校园', 18, NULL, '2023-08-06 17:52:00', 1, '2023-08-06 17:52:00', 1, b'0');
INSERT INTO `ex_book` VALUES (2, '复活', 83, NULL, '2023-08-06 17:56:32', 1, '2023-08-06 17:56:32', 1, b'0');

-- ----------------------------
-- Table structure for ex_order
-- ----------------------------
DROP TABLE IF EXISTS `ex_order`;
CREATE TABLE `ex_order`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `receiver_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '收货人电话',
  `receiver_address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '收货地址',
  `order_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '订单金额-元',
  `order_status` tinyint NULL DEFAULT NULL COMMENT '0-待付款，1-已取消，2-已付款，3-已完成',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` int NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新人',
  `deleted` bit(1) NULL DEFAULT NULL COMMENT '已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '示例-订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ex_order
-- ----------------------------
INSERT INTO `ex_order` VALUES (1, '王五', '13500000000', '钦州市钦南区', 100.00, 0, '2023-08-04 10:06:37', 1, '2023-08-04 10:06:37', 1, b'0');
INSERT INTO `ex_order` VALUES (2, '老六', '13511111111', '钦州市钦南区', 50.00, 0, '2023-08-04 10:07:03', 1, '2023-08-04 10:07:03', 1, b'0');
INSERT INTO `ex_order` VALUES (3, '炮灰甲', '13522222222', '钦州市钦北区', 888.00, 1, '2023-08-04 10:07:47', 1, '2023-08-04 10:07:47', 1, b'0');
INSERT INTO `ex_order` VALUES (4, '路人甲', '13566666666', '钦州市钦北区', 520.16, 2, '2023-08-04 10:08:17', 4, '2023-08-04 10:08:17', 1, b'0');
INSERT INTO `ex_order` VALUES (5, '李回归', '13578954567', '南宁市江南区', 200.45, 3, '2023-08-04 10:17:32', 2, '2023-08-04 10:17:32', 1, b'0');
INSERT INTO `ex_order` VALUES (6, '赵辉', '13547595616', '南宁市西乡塘区', 78.99, 3, '2023-08-04 10:18:13', 2, '2023-08-04 10:18:13', 1, b'0');
INSERT INTO `ex_order` VALUES (7, 'name1', '13612345678', NULL, NULL, NULL, '2024-01-02 11:50:07', 1, '2024-01-02 11:50:07', 1, b'0');

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `table_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '表名',
  `table_comment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '表描述',
  `class_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '实体类名称',
  `module_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '模块名',
  `function_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '功能名称',
  `author_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '生成人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` int NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新人',
  `deleted` bit(1) NULL DEFAULT NULL COMMENT '已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '代码生成表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gen_table
-- ----------------------------

-- ----------------------------
-- Table structure for sys_data_scope
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_scope`;
CREATE TABLE `sys_data_scope`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `enabled` bit(1) NULL DEFAULT NULL,
  `scope_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `table_alias` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '表别名',
  `column_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '数据库字段名',
  `splice_type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '拼接类型 SpliceTypeEnum',
  `expression` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '表达式 ExpressionEnum',
  `provide_type` tinyint NULL DEFAULT NULL COMMENT 'ProvideTypeEnum 值提供类型，1-值，2-方法',
  `value1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '值1',
  `value2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '值2',
  `class_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '全限定类名',
  `method_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `formal_param` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '形参，分号隔开',
  `actual_param` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '实参，分号隔开',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` int NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新人',
  `deleted` bit(1) NULL DEFAULT NULL COMMENT '已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_scope_name`(`scope_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_data_scope
-- ----------------------------
INSERT INTO `sys_data_scope` VALUES (1, '无参测试', b'1', 'sn1', NULL, 'enabled', 'OR', 'IN', 2, NULL, NULL, 'com.gitee.whzzone.controller.A', 'getIds', NULL, NULL, NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_data_scope` VALUES (2, '查看创建人是当前登录用户的数据', b'0', 'sn2', NULL, 'create_by', 'AND', 'IN', 2, '', NULL, 'com.gitee.whzzone.util.SecurityUtil', 'loginUserId', NULL, NULL, NULL, NULL, '2023-07-15 17:53:42', 1, b'0');
INSERT INTO `sys_data_scope` VALUES (3, '带参注入测试', b'1', 'sn3', NULL, 'create_by', 'AND', 'IN', 2, NULL, NULL, 'com.gitee.whzzone.controller.A', 'getByName', 'java.lang.String;java.lang.Integer', 'hello world;18', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_data_scope` VALUES (4, '查看父级id为0的数据', b'1', 'sn5', NULL, 'parent_id', 'AND', 'EQ', 1, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_data_scope` VALUES (5, '查看编码不是work的角色', b'1', 'sn4', NULL, 'code', 'AND', 'NE', 1, 'worker', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'0');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `parent_id` int NULL DEFAULT 0,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `enabled` bit(1) NULL DEFAULT b'1',
  `sort` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` int NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, 0, '默认部门', b'1', 1, '2023-05-17 15:02:47', 1, '2023-05-17 15:02:47', 1, b'0');
INSERT INTO `sys_dept` VALUES (2, 0, '研发部', b'1', 2, '2023-05-17 22:32:24', 1, '2023-05-17 22:32:24', 1, b'0');
INSERT INTO `sys_dept` VALUES (3, 0, '运维部', b'1', 3, '2023-05-17 22:38:18', 1, '2023-07-09 15:45:00', 1, b'0');
INSERT INTO `sys_dept` VALUES (4, 1, '测试部门1-1', b'0', 1, '2023-07-08 21:16:00', 1, '2023-07-08 21:16:00', 1, b'0');
INSERT INTO `sys_dept` VALUES (5, 1, '测试部门1-2', b'1', 1, NULL, 1, '2023-07-09 15:12:37', 1, b'0');
INSERT INTO `sys_dept` VALUES (6, 2, '测试部门2-1', b'1', 3, NULL, 1, '2023-07-09 15:20:32', 1, b'0');
INSERT INTO `sys_dept` VALUES (7, 0, '硬件部', b'1', 4, '2023-07-12 20:36:07', 1, '2023-07-12 20:36:07', 1, b'0');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `dict_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '字典名称',
  `dict_code` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '字典编码（唯一）',
  `dict_type` tinyint NULL DEFAULT NULL COMMENT '字典类型，0-列表，1-树',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` int NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新人',
  `deleted` bit(1) NULL DEFAULT NULL COMMENT '已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '系统字典' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, '性别', 'sys_user_sex', 0, 1, '性别字典', '2023-08-08 11:50:40', 1, '2023-08-08 11:50:40', 1, b'0');
INSERT INTO `sys_dict` VALUES (2, '天气100', 'weather', 0, 2, '', '2023-08-08 16:21:38', 1, '2023-08-08 16:21:38', 1, b'0');
INSERT INTO `sys_dict` VALUES (3, '是/否', 'sys_yes_no', 0, 1, '', '2023-08-09 20:29:37', 1, '2023-08-09 20:29:37', 1, b'0');
INSERT INTO `sys_dict` VALUES (4, 'Class类型', 'sys_class', 0, 3, '自定义Class类型', '2023-08-09 21:59:18', 1, '2023-08-09 21:59:18', 1, b'0');
INSERT INTO `sys_dict` VALUES (5, '测试11', 'test1', 0, NULL, NULL, '2023-08-13 15:00:27', 1, '2023-08-13 18:00:38', 1, b'0');
INSERT INTO `sys_dict` VALUES (6, 'test22', 'test22', 0, 6, NULL, '2023-08-13 15:01:07', 1, '2023-08-13 17:59:57', 1, b'0');
INSERT INTO `sys_dict` VALUES (7, 'fdsafdsa', '1', 1, NULL, NULL, '2023-08-13 19:55:06', 1, '2023-08-13 19:55:06', 1, b'0');
INSERT INTO `sys_dict` VALUES (8, 's', 's', 1, 1, NULL, '2023-08-13 20:03:59', 1, '2023-08-13 20:03:59', 1, b'0');
INSERT INTO `sys_dict` VALUES (9, '字典类型', 'dict_type', NULL, 2, '字典类型的类型，请慎重改动', '2023-08-14 11:23:22', 1, '2023-08-14 11:23:22', 1, b'0');

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `dict_id` int NULL DEFAULT NULL COMMENT '字典id',
  `parent_id` int NULL DEFAULT 0 COMMENT '父级id',
  `dict_label` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '字典值',
  `list_class` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` int NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新人',
  `deleted` bit(1) NULL DEFAULT NULL COMMENT '已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '系统字典数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, 0, '男', '1', 'primary', '男生', 1, '2023-08-08 11:52:39', 1, '2023-08-08 11:52:39', 1, b'0');
INSERT INTO `sys_dict_data` VALUES (2, 1, 0, '女', '0', 'success', '女生', 2, '2023-08-08 11:53:06', 1, '2023-08-08 11:53:06', 1, b'0');
INSERT INTO `sys_dict_data` VALUES (3, 1, 0, '未知', '2', 'info', '', 3, '2023-08-08 16:18:51', 1, '2023-08-08 16:18:51', 1, b'0');
INSERT INTO `sys_dict_data` VALUES (4, 2, 0, '晴天', '0', 'primary', '', 1, '2023-08-08 16:22:04', 1, '2023-08-08 16:22:04', 1, b'0');
INSERT INTO `sys_dict_data` VALUES (5, 2, 0, '小雨', '1', 'warning', '', 1, '2023-08-08 16:22:33', 1, '2023-08-08 16:22:33', 1, b'0');
INSERT INTO `sys_dict_data` VALUES (6, 3, 0, '是', '1', 'primary', '', 1, '2023-08-09 20:30:52', 1, '2023-08-09 20:30:52', 1, b'0');
INSERT INTO `sys_dict_data` VALUES (7, 3, 0, '否', '0', 'warning', '', 2, '2023-08-09 20:31:22', 1, '2023-08-09 20:31:22', 1, b'0');
INSERT INTO `sys_dict_data` VALUES (8, 4, 0, '字符串', 'java.lang.String', '', '', 1, '2023-08-09 22:01:09', 1, '2023-08-09 22:01:09', 1, b'0');
INSERT INTO `sys_dict_data` VALUES (9, 4, 0, '整型', 'java.lang.Integer', '', '', 1, '2023-08-09 22:01:55', 1, '2023-08-09 22:01:55', 1, b'0');
INSERT INTO `sys_dict_data` VALUES (10, 4, 0, '长整型', 'java.lang.Long', '', '', 1, '2023-08-09 22:02:07', 1, '2023-08-09 22:02:07', 1, b'0');
INSERT INTO `sys_dict_data` VALUES (11, 4, 0, '布尔值', 'java.lang.Boolean', '', '', 1, '2023-08-09 22:02:26', 1, '2023-08-09 22:02:26', 1, b'0');
INSERT INTO `sys_dict_data` VALUES (12, 4, 0, '字节型', 'java.lang.Byte', '', '', 1, '2023-08-09 22:02:38', 1, '2023-08-09 22:02:38', 1, b'0');
INSERT INTO `sys_dict_data` VALUES (13, 4, 0, '短整型', 'java.lang.Short', '', '', 1, '2023-08-09 22:02:50', 1, '2023-08-09 22:02:50', 1, b'0');
INSERT INTO `sys_dict_data` VALUES (14, 4, 0, '单精度浮点型', 'java.lang.Float', '', '', 1, '2023-08-09 22:03:09', 1, '2023-08-09 22:03:09', 1, b'0');
INSERT INTO `sys_dict_data` VALUES (15, 4, 0, '双精度浮点型', 'java.lang.Double', '', '', 1, '2023-08-09 22:03:23', 1, '2023-08-09 22:03:23', 1, b'0');
INSERT INTO `sys_dict_data` VALUES (16, 4, 0, 'BigDecimal', 'java.math.BigDecimal', '', '', 1, '2023-08-09 22:04:11', 1, '2023-08-09 22:04:11', 1, b'0');
INSERT INTO `sys_dict_data` VALUES (17, 9, 0, '列表', '0', NULL, '', 1, '2023-08-14 11:23:58', 1, '2023-08-14 11:23:58', 1, b'0');
INSERT INTO `sys_dict_data` VALUES (18, 9, 0, '树形', '1', NULL, '', 2, '2023-08-14 11:24:08', 1, '2023-08-14 11:24:08', 1, b'0');

-- ----------------------------
-- Table structure for sys_file_upload
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_upload`;
CREATE TABLE `sys_file_upload`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `file_path` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '相对路径',
  `file_md5` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '文件md5',
  `file_size` bigint NULL DEFAULT NULL COMMENT '文件大小（字节数）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` int NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新人',
  `deleted` bit(1) NULL DEFAULT NULL COMMENT '已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '文件上传表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_file_upload
-- ----------------------------
INSERT INTO `sys_file_upload` VALUES (1, '2023/08/12', 'f98973b50de64e5757d111efb4cdf54e', 59116, '2023-08-12 12:44:36', 2, '2023-08-12 12:44:36', 2, b'0');

-- ----------------------------
-- Table structure for sys_mark
-- ----------------------------
DROP TABLE IF EXISTS `sys_mark`;
CREATE TABLE `sys_mark`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `enabled` bit(1) NULL DEFAULT NULL,
  `sort` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` int NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_scope_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_mark
-- ----------------------------
INSERT INTO `sys_mark` VALUES (1, 'sn1', '无参测试', b'1', 2, NULL, NULL, '2023-07-16 09:11:09', 1, b'0');
INSERT INTO `sys_mark` VALUES (2, 'sn2', '查看创建人是当前登录用户的数据', b'0', 3, NULL, NULL, '2023-07-15 17:53:42', 1, b'0');
INSERT INTO `sys_mark` VALUES (3, 'sn3', '带参注入测试', b'1', 4, NULL, NULL, '2023-07-15 17:53:42', NULL, b'0');
INSERT INTO `sys_mark` VALUES (4, 'sn5', '查看父级id为0的数据', b'1', 5, NULL, NULL, '2023-07-15 17:53:42', NULL, b'0');
INSERT INTO `sys_mark` VALUES (5, 'role-page', '角色分页接口', b'1', 1, NULL, NULL, '2023-07-15 17:53:42', 1, b'0');
INSERT INTO `sys_mark` VALUES (6, 'order-list', '订单列表接口', NULL, NULL, '2023-08-04 10:09:52', 1, '2023-08-04 10:09:52', 1, b'0');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `parent_id` int NULL DEFAULT NULL COMMENT '父权限',
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `icon` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图标',
  `sort` int NULL DEFAULT 999 COMMENT '排序',
  `route_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '路由名称',
  `path` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '路由路径',
  `enabled` bit(1) NULL DEFAULT b'1' COMMENT '是否禁用',
  `component` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `permission` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '权限字符',
  `menu_type` tinyint NULL DEFAULT NULL COMMENT '菜单类型，1-目录，2-菜单，3-按钮',
  `in_frame` bit(1) NULL DEFAULT NULL COMMENT '是否在框架中打开新页面',
  `is_url` bit(1) NULL DEFAULT NULL COMMENT '是否url链接',
  `keep_alive` bit(1) NULL DEFAULT NULL COMMENT '是否缓存',
  `description` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` int NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新人',
  `deleted` bit(1) NULL DEFAULT NULL COMMENT '已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '首页', 'el-icon-s-home', 1, NULL, '/home', b'1', 'home/index', 'sys:index:index', 2, b'1', b'0', b'1', NULL, '2023-05-23 09:13:59', NULL, '2023-07-11 20:31:56', 1, b'0');
INSERT INTO `sys_menu` VALUES (2, 0, '系统管理', 'el-icon-s-tools', 2, NULL, '', b'1', '', '', 1, NULL, NULL, NULL, '', '2023-05-23 09:12:12', 1, '2023-05-23 09:12:12', 1, b'0');
INSERT INTO `sys_menu` VALUES (3, 2, '用户管理', 'el-icon-user-solid', 1, NULL, '/system/user', b'1', 'system/user/index', 'sys:user:view', 2, b'1', b'0', b'1', '', '2023-05-23 09:12:12', 1, '2023-05-23 09:13:59', 1, b'0');
INSERT INTO `sys_menu` VALUES (4, 3, '查看用户', NULL, 1, NULL, NULL, b'1', NULL, '', 3, NULL, NULL, NULL, NULL, '2023-05-23 09:12:12', 1, '2023-07-31 09:37:40', 1, b'1');
INSERT INTO `sys_menu` VALUES (5, 3, '新增用户', NULL, 999, NULL, NULL, b'1', NULL, 'sys:user:add', 3, NULL, NULL, NULL, NULL, '2023-05-23 09:12:12', 1, '2023-05-23 09:13:59', 1, b'0');
INSERT INTO `sys_menu` VALUES (6, 3, '编辑用户', NULL, 999, NULL, NULL, b'1', NULL, 'sys:user:edit', 3, NULL, NULL, NULL, NULL, '2023-05-23 09:12:12', 1, '2023-05-23 09:13:59', 1, b'0');
INSERT INTO `sys_menu` VALUES (7, 3, '删除用户', NULL, 999, NULL, NULL, b'1', NULL, 'sys:user:del', 3, NULL, NULL, NULL, NULL, '2023-05-23 09:12:12', 1, '2023-05-23 09:13:59', 1, b'0');
INSERT INTO `sys_menu` VALUES (8, 2, '角色管理', 'el-icon-s-platform', 2, NULL, '/system/role', b'1', 'system/role/index', 'sys:role:view', 2, b'1', b'0', b'1', NULL, '2023-05-23 09:12:12', 1, '2023-05-23 09:13:59', 1, b'0');
INSERT INTO `sys_menu` VALUES (9, 8, '查看角色', NULL, 999, NULL, NULL, b'1', NULL, 'sys:role:view', 3, NULL, NULL, NULL, NULL, '2023-05-23 09:12:12', 1, '2023-07-31 09:38:16', 1, b'1');
INSERT INTO `sys_menu` VALUES (10, 8, '编辑角色', NULL, 999, NULL, NULL, b'1', NULL, 'sys:role:edit', 3, NULL, NULL, NULL, NULL, '2023-05-23 09:12:12', 1, '2023-05-23 09:13:59', 1, b'0');
INSERT INTO `sys_menu` VALUES (11, 8, '新增角色', NULL, 999, NULL, NULL, b'1', NULL, 'sys:role:add', 3, NULL, NULL, NULL, NULL, '2023-05-23 09:12:12', 1, '2023-05-23 09:13:59', 1, b'0');
INSERT INTO `sys_menu` VALUES (12, 8, '删除角色', NULL, 999, NULL, NULL, b'1', NULL, 'sys:role:del', 3, NULL, NULL, NULL, NULL, '2023-05-23 09:12:12', 1, '2023-05-23 09:13:59', 1, b'0');
INSERT INTO `sys_menu` VALUES (13, 2, '菜单管理', 'el-icon-menu', 3, NULL, '/system/menu', b'1', 'system/menu/index', 'sys:menu:view', 2, b'1', b'0', b'1', NULL, '2023-05-23 09:12:12', 1, '2023-05-23 09:13:59', 1, b'0');
INSERT INTO `sys_menu` VALUES (14, 13, '新增菜单', NULL, 999, NULL, NULL, b'1', NULL, 'sys:menu:add', 3, NULL, NULL, NULL, NULL, '2023-05-23 09:12:12', 1, '2023-05-23 09:13:59', 1, b'0');
INSERT INTO `sys_menu` VALUES (15, 2, '部门管理', 'el-icon-info', 4, NULL, '/system/dept', b'1', 'system/dept/index', 'sys:dept:view', 2, b'1', b'0', b'1', NULL, '2023-05-23 09:12:12', NULL, '2023-07-31 09:39:47', 1, b'0');
INSERT INTO `sys_menu` VALUES (16, 1, '测试按钮1', NULL, 2, NULL, NULL, b'1', NULL, 'test:1', 3, NULL, NULL, NULL, NULL, '2023-07-11 21:00:07', 1, '2023-07-12 15:17:51', 1, b'1');
INSERT INTO `sys_menu` VALUES (17, 3, '重置用户密码', NULL, 5, NULL, NULL, b'1', NULL, 'sys:user:resetPWD', 3, NULL, NULL, NULL, NULL, '2023-07-12 19:14:19', 1, '2023-07-12 19:14:19', 1, b'0');
INSERT INTO `sys_menu` VALUES (18, 2, '标记管理', 'el-icon-s-flag', 5, NULL, '/system/mark', b'1', 'system/mark/index', 'sys:mark:view', 2, b'1', b'0', b'1', NULL, '2023-05-23 09:12:12', NULL, '2023-07-31 09:40:09', 1, b'0');
INSERT INTO `sys_menu` VALUES (19, 18, '规则管理', NULL, 3, 'rule-index2', '/system/rule', b'1', 'system/rule/index', 'sys:mark:rule-mange', 3, b'1', NULL, NULL, NULL, '2023-08-01 20:41:50', 1, '2023-08-01 20:41:50', 1, b'0');
INSERT INTO `sys_menu` VALUES (20, 0, '百度', 'el-icon-s-promotion', 999, NULL, 'https://www.baidu.com', b'1', NULL, NULL, 2, b'0', b'1', NULL, NULL, '2023-08-02 09:55:06', 1, '2023-08-02 09:55:06', 1, b'0');
INSERT INTO `sys_menu` VALUES (21, 2, '字典管理', NULL, 8, 'dict-index', '/system/dict', b'1', 'system/dict/index', NULL, 2, b'1', NULL, b'1', NULL, '2023-08-13 10:28:05', 1, '2023-08-13 10:28:05', 1, b'0');
INSERT INTO `sys_menu` VALUES (22, 0, '业务', NULL, 3, NULL, NULL, b'1', NULL, NULL, 1, NULL, NULL, NULL, NULL, '2023-08-31 22:11:20', 1, '2023-08-31 22:11:20', 1, b'0');
INSERT INTO `sys_menu` VALUES (23, 22, '样品信息', NULL, 1, 'sample-index', '/sample-index', b'1', 'business/interview/sample/index', 'busi:sample:index', 2, b'1', NULL, b'1', NULL, '2023-08-31 22:13:25', 1, '2023-08-31 22:13:25', 1, b'0');

-- ----------------------------
-- Table structure for sys_request_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_request_log`;
CREATE TABLE `sys_request_log`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` int NULL DEFAULT NULL COMMENT '响应码',
  `user_id` int NULL DEFAULT NULL COMMENT '请求人',
  `url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '请求url',
  `desc` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '接口描述',
  `type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '请求类型',
  `method` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '接口方法',
  `ip` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '来源IP',
  `params` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '请求参数',
  `duration` bigint NULL DEFAULT NULL COMMENT '请求耗时ms',
  `result` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '响应数据',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` int NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新人',
  `deleted` bit(1) NULL DEFAULT NULL COMMENT '已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = 'API日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_request_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `parent_id` int NULL DEFAULT NULL COMMENT '父角色',
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `enabled` bit(1) NULL DEFAULT b'1' COMMENT '是否禁用',
  `description` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` int NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 0, '超级管理员', 'admin', b'1', '这是超级管理员', '2023-05-23 09:26:19', 1, '2023-05-23 09:26:19', 1, b'0');
INSERT INTO `sys_role` VALUES (2, NULL, '普通角色', 'user', b'1', '普通角色', '2023-05-23 09:27:09', 1, '2023-05-23 09:27:09', 1, b'0');
INSERT INTO `sys_role` VALUES (3, NULL, '工人', 'worker', b'1', '工人', '2023-07-10 22:37:42', 1, '2023-07-10 22:37:42', 1, b'0');
INSERT INTO `sys_role` VALUES (4, NULL, '测试人员', 'test-person', b'0', '', '2023-07-10 22:44:07', 1, '2023-07-11 12:42:32', 1, b'0');

-- ----------------------------
-- Table structure for sys_role_mark
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_mark`;
CREATE TABLE `sys_role_mark`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NULL DEFAULT NULL,
  `mark_id` int NULL DEFAULT NULL,
  `rule_id` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` int NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_mark
-- ----------------------------
INSERT INTO `sys_role_mark` VALUES (1, 1, 5, 1, NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_role_mark` VALUES (2, 2, 5, 2, '2023-08-03 10:39:32', 1, '2023-08-03 10:39:32', 1, b'1');
INSERT INTO `sys_role_mark` VALUES (3, 2, 5, 2, '2023-08-03 10:49:57', 1, '2023-08-03 10:49:57', 1, b'1');
INSERT INTO `sys_role_mark` VALUES (4, 2, 5, 3, '2023-08-03 17:40:53', 1, '2023-08-03 17:40:53', 1, b'1');
INSERT INTO `sys_role_mark` VALUES (5, 2, 5, 4, '2023-08-03 17:43:44', 1, '2023-08-03 17:43:44', 1, b'0');
INSERT INTO `sys_role_mark` VALUES (6, 2, 6, 7, '2023-08-04 10:14:00', 1, '2023-08-04 10:14:00', 1, b'1');
INSERT INTO `sys_role_mark` VALUES (7, 2, 6, 8, '2023-08-04 10:16:02', 1, '2023-08-04 10:16:02', 1, b'1');
INSERT INTO `sys_role_mark` VALUES (8, 2, 6, 5, '2023-08-04 10:18:22', 1, '2023-08-04 10:18:22', 1, b'1');
INSERT INTO `sys_role_mark` VALUES (9, 2, 6, 6, '2023-08-04 10:18:32', 1, '2023-08-04 10:18:32', 1, b'0');
INSERT INTO `sys_role_mark` VALUES (10, 2, 6, 9, '2023-08-04 11:07:40', 1, '2023-08-04 11:07:40', 1, b'1');
INSERT INTO `sys_role_mark` VALUES (11, 1, 6, 5, '2023-08-05 11:04:54', 1, '2023-08-05 11:04:54', 1, b'1');
INSERT INTO `sys_role_mark` VALUES (12, 1, 6, 7, '2023-08-05 11:06:08', 1, '2023-08-05 11:06:08', 1, b'1');
INSERT INTO `sys_role_mark` VALUES (13, 1, 6, 9, '2023-08-05 11:12:11', 1, '2023-08-05 11:12:11', 1, b'0');
INSERT INTO `sys_role_mark` VALUES (14, 2, 6, 8, '2023-08-10 11:12:42', 1, '2023-08-10 11:12:42', 1, b'1');
INSERT INTO `sys_role_mark` VALUES (15, 2, 6, 9, '2023-08-10 11:12:43', 1, '2023-08-10 11:12:43', 1, b'1');
INSERT INTO `sys_role_mark` VALUES (16, 2, 6, 10, '2023-08-10 14:40:34', 1, '2023-08-10 14:40:34', 1, b'1');
INSERT INTO `sys_role_mark` VALUES (17, 2, 6, 11, '2023-08-10 15:21:31', 1, '2023-08-10 15:21:31', 1, b'0');
INSERT INTO `sys_role_mark` VALUES (18, 2, 6, 9, '2023-08-14 21:04:27', 1, '2023-08-14 21:04:27', 1, b'1');
INSERT INTO `sys_role_mark` VALUES (19, 2, 6, 5, '2023-08-15 11:48:01', 1, '2023-08-15 11:48:01', 1, b'0');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NOT NULL COMMENT '角色 ID',
  `menu_id` int NOT NULL COMMENT '权限 ID',
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` int NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '角色权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 3, 1, '2023-07-10 22:45:39', 1, '2023-07-10 22:45:39', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (2, 2, 1, '2023-07-12 19:26:34', 1, '2023-07-12 19:26:34', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (3, 2, 2, '2023-07-12 19:26:34', 1, '2023-07-12 19:26:34', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (4, 2, 3, '2023-07-12 19:26:34', 1, '2023-07-12 19:26:34', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (5, 2, 8, '2023-07-12 19:26:34', 1, '2023-07-12 19:26:34', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (6, 2, 13, '2023-07-12 19:26:34', 1, '2023-07-12 19:26:34', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (7, 4, 1, '2023-08-02 22:27:46', 1, '2023-08-02 22:27:46', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (8, 4, 2, '2023-08-02 22:27:46', 1, '2023-08-02 22:27:46', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (9, 1, 1, '2023-08-31 22:13:46', 1, '2023-08-31 22:13:46', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (10, 1, 2, '2023-08-31 22:13:46', 1, '2023-08-31 22:13:46', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (11, 1, 3, '2023-08-31 22:13:46', 1, '2023-08-31 22:13:46', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (12, 1, 17, '2023-08-31 22:13:46', 1, '2023-08-31 22:13:46', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (13, 1, 7, '2023-08-31 22:13:46', 1, '2023-08-31 22:13:46', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (14, 1, 6, '2023-08-31 22:13:46', 1, '2023-08-31 22:13:46', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (15, 1, 5, '2023-08-31 22:13:46', 1, '2023-08-31 22:13:46', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (16, 1, 8, '2023-08-31 22:13:46', 1, '2023-08-31 22:13:46', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (17, 1, 10, '2023-08-31 22:13:46', 1, '2023-08-31 22:13:46', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (18, 1, 11, '2023-08-31 22:13:46', 1, '2023-08-31 22:13:46', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (19, 1, 12, '2023-08-31 22:13:46', 1, '2023-08-31 22:13:46', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (20, 1, 13, '2023-08-31 22:13:46', 1, '2023-08-31 22:13:46', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (21, 1, 14, '2023-08-31 22:13:46', 1, '2023-08-31 22:13:46', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (22, 1, 15, '2023-08-31 22:13:46', 1, '2023-08-31 22:13:46', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (23, 1, 18, '2023-08-31 22:13:46', 1, '2023-08-31 22:13:46', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (24, 1, 19, '2023-08-31 22:13:46', 1, '2023-08-31 22:13:46', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (25, 1, 21, '2023-08-31 22:13:46', 1, '2023-08-31 22:13:46', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (26, 1, 22, '2023-08-31 22:13:46', 1, '2023-08-31 22:13:46', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (27, 1, 23, '2023-08-31 22:13:46', 1, '2023-08-31 22:13:46', 1, b'0');
INSERT INTO `sys_role_menu` VALUES (28, 1, 20, '2023-08-31 22:13:46', 1, '2023-08-31 22:13:46', 1, b'0');

-- ----------------------------
-- Table structure for sys_rule
-- ----------------------------
DROP TABLE IF EXISTS `sys_rule`;
CREATE TABLE `sys_rule`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `mark_id` int NULL DEFAULT NULL,
  `table_alias` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '表别名',
  `column_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '数据库字段名',
  `splice_type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '拼接类型 SpliceTypeEnum',
  `expression` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '表达式 ExpressionEnum',
  `provide_type` tinyint NULL DEFAULT NULL COMMENT 'ProvideTypeEnum 值提供类型，1-值，2-方法',
  `value1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '值1',
  `value2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '值2',
  `class_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '全限定类名',
  `method_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `formal_param` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '形参，分号隔开',
  `actual_param` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '实参，分号隔开',
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` int NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '规则表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_rule
-- ----------------------------
INSERT INTO `sys_rule` VALUES (1, '查看编码不是work的角色', 5, NULL, 'code', 'AND', 'NE', 1, 'worker', NULL, NULL, NULL, NULL, NULL, '2023-07-16 09:11:54', 1, '2023-07-16 09:11:09', 1, b'0');
INSERT INTO `sys_rule` VALUES (2, '只查看编码work的角色', 5, NULL, 'code', 'AND', 'EQ', 1, 'worker', NULL, NULL, NULL, NULL, NULL, '2023-07-16 09:11:09', 1, '2023-07-16 09:11:09', 1, b'0');
INSERT INTO `sys_rule` VALUES (3, '查看状态为启用的角色', 5, NULL, 'enabled', 'AND', 'EQ', 1, '1', NULL, NULL, NULL, NULL, NULL, '2023-08-03 17:31:52', 1, '2023-08-03 17:31:52', 1, b'0');
INSERT INTO `sys_rule` VALUES (4, '只查看禁用的角色', 5, NULL, 'enabled', 'AND', 'EQ', 1, '0', NULL, NULL, NULL, NULL, NULL, '2023-08-03 17:43:35', 1, '2023-08-03 17:43:35', 1, b'0');
INSERT INTO `sys_rule` VALUES (5, '查看收货地址为钦南区的订单', 6, NULL, 'receiver_address', 'OR', 'RIGHT_LIKE', 1, '钦南', NULL, NULL, NULL, NULL, NULL, '2023-08-04 10:11:38', 1, '2023-08-04 10:11:38', 1, b'0');
INSERT INTO `sys_rule` VALUES (6, '查看收货地址为钦北区的订单', 6, NULL, 'receiver_address', 'OR', 'LIKE', 1, '钦北区', NULL, NULL, NULL, NULL, NULL, '2023-08-04 10:12:15', 1, '2023-08-04 10:12:15', 1, b'0');
INSERT INTO `sys_rule` VALUES (7, '查看订单金额大于800的订单', 6, NULL, 'order_amount', 'OR', 'GT', 1, '800', NULL, NULL, NULL, NULL, NULL, '2023-08-04 10:12:58', 1, '2023-08-04 10:12:58', 1, b'0');
INSERT INTO `sys_rule` VALUES (8, '查看订单金额小于等于100的订单', 6, NULL, 'order_amount', 'AND', 'LE', 1, '100', NULL, NULL, NULL, NULL, NULL, '2023-08-04 10:13:38', 1, '2023-08-04 10:13:38', 1, b'0');
INSERT INTO `sys_rule` VALUES (9, '查看订单金额大于100且小于500的订单', 6, NULL, 'id', 'OR', 'IN', 2, NULL, NULL, 'com.gitee.whzzone.admin.business.service.impl.OrderServiceImpl', 'limitAmountBetween', 'java.math.BigDecimal;java.math.BigDecimal', '100;500', '2023-08-04 10:33:01', 1, '2023-08-04 10:33:01', 1, b'0');
INSERT INTO `sys_rule` VALUES (10, '查看订单创建人的名称为- 普通用户1', 6, 'u', 'nickname', 'AND', 'EQ', 1, 'user04', NULL, NULL, NULL, NULL, NULL, '2023-08-10 14:40:16', 1, '2023-08-10 14:40:16', 1, b'0');
INSERT INTO `sys_rule` VALUES (11, '查看订单状态in (1,2)', 6, NULL, 'id', 'OR', 'IN', 1, '1,2', NULL, NULL, NULL, NULL, NULL, '2023-08-10 15:21:15', 1, '2023-08-10 15:21:15', 1, b'0');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `password` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '密码，加密存储',
  `phone` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '注册手机号',
  `open_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `union_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '注册邮箱',
  `enabled` bit(1) NULL DEFAULT b'1',
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` int NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '管理员', '$2a$10$M.cAsmqLLmMwG/.K.Dtr4en9XkE1c/7VFZpgtgRyk6EcrwYoHFqx.', '18276904847', 'oXNOh4jjNCN7SnhfnhsdUlNB_bGw', NULL, 'whz@live.com', b'1', '2023-05-17 15:02:47', NULL, '2023-05-17 15:02:47', 1, b'0');
INSERT INTO `sys_user` VALUES (2, 'user1', '普通用户1', '$2a$10$mTtXxlfpHXCfYkI3fE.Z2uFyPPv46NG1fNN.xmZ1xduAvoXrsVoWm', '15578701918', NULL, NULL, '2625219048@qq.com', b'1', '2023-05-17 22:32:24', 1, '2023-05-17 22:32:24', 1, b'0');
INSERT INTO `sys_user` VALUES (3, 'user2', '普通用户2', '$2a$10$.FUEa0YGbkS4BC7Tq8dlWuWOv3pATyT2hE6nPJclbYjbJ2pPBDroS', '13222222222', NULL, NULL, '1721675433@qq.com', b'1', '2023-05-17 22:38:18', 1, '2023-05-22 19:21:11', 1, b'0');
INSERT INTO `sys_user` VALUES (4, 'user04', 'user04', '$2a$10$9cA68ty8svlAaciZ3TBgYewZzb3qyanpPudF77rJKcAJx9vpmsJMO', '13566666666', NULL, NULL, '', b'1', '2023-07-10 16:53:51', 1, '2023-07-10 16:53:51', 1, b'0');
INSERT INTO `sys_user` VALUES (5, 'worker', 'worker3', '$2a$10$.N6rY3bEDoNqLLSwLrg6Geqqhecx/OU4IzBGhNGprpzWiCq1J5Elm', '13600000000', NULL, NULL, '', b'1', '2023-07-11 12:45:56', 1, '2023-07-11 12:45:56', 1, b'0');
INSERT INTO `sys_user` VALUES (6, 'work01', 'work01', '$2a$10$/BhynhfZX.LzlmlU3HCHC.NWtRF.RUwhm9U4pCIAjws9PphftTVKi', '13500001234', NULL, NULL, '', b'1', '2023-07-12 11:40:43', 1, '2023-07-12 15:39:23', 1, b'1');
INSERT INTO `sys_user` VALUES (7, 'user05', 'user05', '$2a$10$r0a/zfBh8rZho5I3HrJh7ObqVYQJTluwuDUXMCtytZN.1XfiOk5yW', '13200001234', NULL, NULL, NULL, b'1', '2023-07-30 20:13:01', 2, '2023-07-30 20:13:01', 2, b'0');

-- ----------------------------
-- Table structure for sys_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_dept`;
CREATE TABLE `sys_user_dept`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `dept_id` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` int NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_dept
-- ----------------------------
INSERT INTO `sys_user_dept` VALUES (1, 3, 2, '2023-07-10 16:21:15', 1, '2023-07-10 16:21:15', 1, b'0');
INSERT INTO `sys_user_dept` VALUES (2, 5, 3, '2023-07-12 11:31:27', 1, '2023-07-12 11:31:27', 1, b'0');
INSERT INTO `sys_user_dept` VALUES (3, 6, 3, '2023-07-12 11:40:43', 1, '2023-07-12 11:40:43', 1, b'0');
INSERT INTO `sys_user_dept` VALUES (4, 2, 2, '2023-07-30 19:38:18', 1, '2023-07-30 19:38:18', 1, b'0');
INSERT INTO `sys_user_dept` VALUES (5, 2, 3, '2023-07-30 19:38:18', 1, '2023-07-30 19:38:18', 1, b'0');
INSERT INTO `sys_user_dept` VALUES (6, 7, 2, '2023-07-30 20:13:01', 2, '2023-07-30 20:13:01', 2, b'0');
INSERT INTO `sys_user_dept` VALUES (7, 7, 3, '2023-07-30 20:13:01', 2, '2023-07-30 20:13:01', 2, b'0');
INSERT INTO `sys_user_dept` VALUES (8, 7, 7, '2023-07-30 20:13:01', 2, '2023-07-30 20:13:01', 2, b'0');
INSERT INTO `sys_user_dept` VALUES (9, 4, 3, '2023-07-30 20:28:26', 2, '2023-07-30 20:28:26', 2, b'0');
INSERT INTO `sys_user_dept` VALUES (10, 1, 1, '2023-08-10 08:55:50', 1, '2023-08-10 08:55:50', 1, b'0');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL COMMENT '用户 ID',
  `role_id` int NULL DEFAULT NULL COMMENT '角色 ID',
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` int NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 3, 2, '2023-07-10 16:21:02', 1, '2023-07-10 16:21:02', 1, b'0');
INSERT INTO `sys_user_role` VALUES (2, 5, 3, '2023-07-12 11:31:27', 1, '2023-07-12 11:31:27', 1, b'0');
INSERT INTO `sys_user_role` VALUES (3, 6, 3, '2023-07-12 11:40:43', 1, '2023-07-12 11:40:43', 1, b'0');
INSERT INTO `sys_user_role` VALUES (4, 2, 2, '2023-07-30 19:38:18', 1, '2023-07-30 19:38:18', 1, b'0');
INSERT INTO `sys_user_role` VALUES (5, 2, 3, '2023-07-30 19:38:18', 1, '2023-07-30 19:38:18', 1, b'0');
INSERT INTO `sys_user_role` VALUES (6, 7, 2, '2023-07-30 20:13:01', 2, '2023-07-30 20:13:01', 2, b'0');
INSERT INTO `sys_user_role` VALUES (7, 4, 2, '2023-07-30 20:28:25', 2, '2023-07-30 20:28:25', 2, b'0');
INSERT INTO `sys_user_role` VALUES (8, 1, 1, '2023-08-10 08:55:50', 1, '2023-08-10 08:55:50', 1, b'0');

SET FOREIGN_KEY_CHECKS = 1;
