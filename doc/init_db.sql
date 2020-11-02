/*
 Source Server         : 139.196.160.176
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : 139.196.160.176:3306

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 2020-4-13 17:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_branch
-- ----------------------------
DROP TABLE IF EXISTS `sys_branch`;
CREATE TABLE `sys_branch`  (
  `branch_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '机构Id',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父级Id 顶级0',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编号',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构名称',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态 (1-启用 0-禁用)',
  `gmt_created` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `gmt_modified` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  `updater` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '删除标记 (0-正常 1-删除)',
  PRIMARY KEY (`branch_id`) USING BTREE,
  INDEX `index_sys_branch_parent_id`(`parent_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '组织机构' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_branch
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典Id',
   `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父级Id(顶级为0)',
  `tag` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典key值',
  `name` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_created` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `gmt_modified` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `updater` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------

-- ----------------------------
-- Table structure for sys_element
-- ----------------------------
DROP TABLE IF EXISTS `sys_element`;
CREATE TABLE `sys_element`  (
  `element_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '页面元素Id',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父级Id(顶级为0)',
  `type` tinyint(1) NULL DEFAULT 0 COMMENT '类型(0:其他 1:按钮 2:链接)',
  `authority` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `href` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路径',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态(0:无效 1:有效)',
  `gmt_created` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `gmt_modified` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `updater` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`element_id`) USING BTREE,
  INDEX `index_sys_element_parent_id`(`parent_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '页面元素表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_element
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色关系表主键',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '角色Id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单',
  `modules` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块名称',
  `operation` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户操作',
  `params` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `method` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `time_consuming` bigint(20) NULL DEFAULT NULL COMMENT '耗时',
  `gmt_created` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_element` VALUES (1, 2, 1, 'system:user:save', '保存用户', NULL, NULL, NULL, 1, '2020-04-17 07:46:50', 1, '2020-04-17 20:53:46', 0);
INSERT INTO `sys_element` VALUES (2, 2, 1, 'system:user:delete', '删除用户', NULL, NULL, NULL, 1, '2020-04-17 08:01:11', 1, '2020-04-17 08:01:11', 0);
INSERT INTO `sys_element` VALUES (3, 3, 1, 'system:menu:save', '保存菜单', NULL, NULL, NULL, 1, '2020-04-17 08:06:22', 1, '2020-04-17 08:06:22', 0);
INSERT INTO `sys_element` VALUES (4, 3, 1, 'system:menu:delete', '删除菜单', NULL, NULL, NULL, 1, '2020-04-17 08:06:48', 1, '2020-04-17 08:06:48', 0);
INSERT INTO `sys_element` VALUES (5, 3, 1, 'system:element:save', '保存元素', NULL, NULL, NULL, 1, '2020-04-17 08:06:48', 1, '2020-04-17 08:06:48', 0);
INSERT INTO `sys_element` VALUES (6, 3, 1, 'system:element:delete', '删除元素', NULL, NULL, NULL, 1, '2020-04-17 08:06:48', 1, '2020-04-17 08:06:48', 0);
INSERT INTO `sys_element` VALUES (7, 4, 1, 'system:role:save', '保存角色', NULL, NULL, NULL, 1, '2020-04-17 08:06:48', 1, '2020-04-17 08:06:48', 0);
INSERT INTO `sys_element` VALUES (8, 4, 1, 'system:role:delete', '保存角色', NULL, NULL, NULL, 1, '2020-04-17 08:06:48', 1, '2020-04-17 08:06:48', 0);
INSERT INTO `sys_element` VALUES (9, 5, 1, 'system:dict:save', '保存字典', NULL, NULL, NULL, 1, '2020-04-17 08:06:48', 1, '2020-04-17 08:06:48', 0);
INSERT INTO `sys_element` VALUES (10, 5, 1, 'system:dict:delete', '保存字典', NULL, NULL, NULL, 1, '2020-04-17 08:06:48', 1, '2020-04-17 21:17:09', 0);
INSERT INTO `sys_element` VALUES (11, 4, 1, 'system:role:relation', '关联菜单', NULL, NULL, NULL, 1, '2020-04-17 08:06:48', 1, '2020-04-17 21:17:09', 0);


-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单Id',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父级主键(顶级为0）',
  `name` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `href` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '链接',
  `component` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件',
  `icon` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `is_show` tinyint(1) NULL DEFAULT NULL COMMENT '是否显示(1-显示,0-不显示)',
  `authority` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识\r\n',
  `sort` bigint(20) NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_created` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `gmt_modified` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `updater` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '删除标记 (0-正常 1-删除)',
  PRIMARY KEY (`menu_id`) USING BTREE,
  INDEX `index_sys_element_parent_id`(`parent_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', 'system/manage', 'Layout', 'el-icon-setting', 1, 'system:manage', 1, '', '2020-04-17 08:50:32', 1, '2020-04-17 08:50:35', 1, 0);
INSERT INTO `sys_menu` VALUES (2, 1, '用户管理', 'system/manage/user', '@/views/system/user/index', 'el-icon-setting', 1, 'system:manage:user', 1, '', '2020-04-17 08:50:55', 1, '2020-04-17 08:50:57', 1, 0);
INSERT INTO `sys_menu` VALUES (3, 1, '菜单管理', 'system/manage/menu', '@/views/system/menu/index', 'el-icon-setting', 1, 'system:manage:menu', 2, NULL, '2020-04-17 08:51:38', 1, '2020-04-17 08:51:40', 1, 0);
INSERT INTO `sys_menu` VALUES (4, 1, '角色管理', 'system/manage/role', '@/views/system/role/index', 'el-icon-setting', 1, 'system:manage:role', 3, NULL, '2020-04-17 08:52:51', 1, '2020-04-17 08:52:51', 1, 0);
INSERT INTO `sys_menu` VALUES (5, 1, '字典管理', 'system/manage/dict', '@/views/system/dict/index', 'el-icon-setting', 1, 'system:manage:dict', 4, NULL, '2020-04-17 08:53:02', 1, '2020-04-17 08:53:02', 1, 0);
INSERT INTO `sys_menu` VALUES (6, 1, '系统日志', 'system/manage/log', 'Layout', 'el-icon-setting', 1, 'system:manage:log', 5, NULL, '2020-04-17 08:52:58', 1, '2020-04-17 08:52:58', 1, 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色Id',
  `name` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态(0-停用 1-启用)',
  `gmt_created` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `gmt_modified` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `updater` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '删除标记 (0-正常 1-删除)',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '系统管理员', 'system-manage', '超级管理员', '1', '2020-01-19 08:16:29', 1, '2020-01-19 08:16:31', 1, 0);

-- ----------------------------
-- Table structure for sys_role_link
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_link`;
CREATE TABLE `sys_role_link`  (
  `role_link_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色关系表主键',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色Id',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单',
  `element_id` bigint(20) NULL DEFAULT NULL COMMENT '页面元素Id',
  `type` tinyint(1) NULL DEFAULT 0 COMMENT '类型( 0:菜单 1:页面元素 )',
  `gmt_created` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`role_link_id`) USING BTREE,
  INDEX `index_sys_role_link_role_id`(`role_id`) USING BTREE,
  INDEX `index_sys_role_link_menu_id`(`menu_id`) USING BTREE,
  INDEX `index_sys_role_link_element_id`(`element_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_link
-- ----------------------------
INSERT INTO `sys_role_link` (role_id, menu_id, element_id, type, gmt_created) VALUES (1, 3, NULL, 0, '2019-11-25 17:33:08');
INSERT INTO `sys_role_link` (role_id, menu_id, element_id, type, gmt_created) VALUES (1, 1, NULL, 0, '2019-11-25 17:33:08');
INSERT INTO `sys_role_link` (role_id, menu_id, element_id, type, gmt_created) VALUES (1, 2, NULL, 0, '2019-11-25 09:27:02');
INSERT INTO `sys_role_link` (role_id, menu_id, element_id, type, gmt_created) VALUES (1, 4, NULL, 0, '2019-11-25 09:27:02');
INSERT INTO `sys_role_link` (role_id, menu_id, element_id, type, gmt_created) VALUES (1, 5, NULL, 0, '2019-11-25 09:27:02');
INSERT INTO `sys_role_link` (role_id, menu_id, element_id, type, gmt_created) VALUES (1, 6, NULL, 0, '2019-11-25 09:27:02');
INSERT INTO `sys_role_link` (role_id, menu_id, element_id, type, gmt_created) VALUES (1, NULL, 1, 1, '2019-11-25 09:27:02');
INSERT INTO `sys_role_link` (role_id, menu_id, element_id, type, gmt_created) VALUES (1, NULL, 2, 1, '2019-11-25 09:27:02');
INSERT INTO `sys_role_link` (role_id, menu_id, element_id, type, gmt_created) VALUES (1, NULL, 3, 1, '2019-11-25 09:27:02');
INSERT INTO `sys_role_link` (role_id, menu_id, element_id, type, gmt_created) VALUES (1, NULL, 4, 1, '2019-11-25 09:27:02');
INSERT INTO `sys_role_link` (role_id, menu_id, element_id, type, gmt_created) VALUES (1, NULL, 5, 1, '2019-11-25 09:27:02');
INSERT INTO `sys_role_link` (role_id, menu_id, element_id, type, gmt_created) VALUES (1, NULL, 6, 1, '2019-11-25 09:27:02');
INSERT INTO `sys_role_link` (role_id, menu_id, element_id, type, gmt_created) VALUES (1, NULL, 7, 1, '2019-11-25 09:27:02');
INSERT INTO `sys_role_link` (role_id, menu_id, element_id, type, gmt_created) VALUES (1, NULL, 8, 1, '2019-11-25 09:27:02');
INSERT INTO `sys_role_link` (role_id, menu_id, element_id, type, gmt_created) VALUES (1, NULL, 9, 1, '2019-11-25 09:27:02');
INSERT INTO `sys_role_link` (role_id, menu_id, element_id, type, gmt_created) VALUES (1, NULL, 10, 1, '2019-11-25 09:27:02');
INSERT INTO `sys_role_link` (role_id, menu_id, element_id, type, gmt_created) VALUES (1, NULL, 11, 1, '2019-11-25 09:27:02');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `branch_id` bigint(20) NULL DEFAULT NULL COMMENT '机构Id',
  `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '加盐',
  `email` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机',
  `introduction` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '介绍',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态值(1-正常, 0-禁用)',
  `gmt_created` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `gmt_modified` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  `updater` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '删除标记 (0-正常 1-删除)',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `userName`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 1, '以梅佐酒', 'https://jkeep.oss-cn-shanghai.aliyuncs.com/upload/muyelogo.jpg', 'admin', '1b3dd4c006bd883c6fcf5e80b07791d4', 'KEEP-MES', 'liangzhong.tan@outlook.com', '15111213963', 'Hello ', 1, '2020-04-17 05:38:36', 1, '2020-04-17 05:38:38', 1, 0);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户角色关系表主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户Id',
  `role_id` bigint(20) NOT NULL COMMENT '角色Id',
  `gmt_created` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`user_role_id`) USING BTREE,
  INDEX `index_sys_user_role_user_id`(`user_id`) USING BTREE,
  INDEX `index_sys_user_role_role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色关系表, 赋予用户的角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1, '2019-09-09 11:23:54');

SET FOREIGN_KEY_CHECKS = 1;
