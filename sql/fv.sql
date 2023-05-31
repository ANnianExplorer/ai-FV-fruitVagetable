/*
 Navicat Premium Data Transfer

 Source Server         : yzh
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : fv

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 28/05/2023 21:59:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address_book
-- ----------------------------
DROP TABLE IF EXISTS `address_book`;
CREATE TABLE `address_book`  (
                                 `id` bigint NOT NULL COMMENT '主键',
                                 `user_id` bigint NOT NULL COMMENT '用户id',
                                 `consignee` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '收货人',
                                 `sex` tinyint NOT NULL COMMENT '性别 0 女 1 男',
                                 `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
                                 `province_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省级区划编号',
                                 `province_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省级名称',
                                 `city_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市级区划编号',
                                 `city_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市级名称',
                                 `district_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区级区划编号',
                                 `district_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区级名称',
                                 `detail` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '详细地址',
                                 `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签',
                                 `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '默认 0 否 1是',
                                 `create_time` datetime NOT NULL COMMENT '创建时间',
                                 `update_time` datetime NOT NULL COMMENT '更新时间',
                                 `create_user` bigint NOT NULL COMMENT '创建人',
                                 `update_user` bigint NOT NULL COMMENT '修改人',
                                 `is_deleted` int NOT NULL DEFAULT 0 COMMENT '是否删除',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '地址管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of address_book
-- ----------------------------
INSERT INTO `address_book` VALUES (1417414526093082626, 1417012167126876162, '小明', 1, '13812345678', NULL, NULL, NULL, NULL, NULL, NULL, '昌平区金燕龙办公楼', '公司', 1, '2021-07-20 17:22:12', '2021-07-20 17:26:33', 1417012167126876162, 1417012167126876162, 0);
INSERT INTO `address_book` VALUES (1417414926166769666, 1417012167126876162, '小李', 1, '13512345678', NULL, NULL, NULL, NULL, NULL, NULL, '测试', '家', 0, '2021-07-20 17:23:47', '2021-07-20 17:23:47', 1417012167126876162, 1417012167126876162, 0);
INSERT INTO `address_book` VALUES (1563168117342814209, 1563023776787914754, '龙', 1, '13412345678', NULL, NULL, NULL, NULL, NULL, NULL, '中国', '公司', 0, '2022-08-26 22:14:56', '2022-08-26 22:14:56', 1563023776787914754, 1563023776787914754, 0);
INSERT INTO `address_book` VALUES (1563375034338045953, 1563023776787914754, '龙', 1, '15038294881', NULL, NULL, NULL, NULL, NULL, NULL, '中国', '家', 1, '2022-08-27 11:57:09', '2022-08-27 11:57:11', 1563023776787914754, 1563023776787914754, 0);
INSERT INTO `address_book` VALUES (1591734769177047041, 1591734480315330561, 'long', 0, '15837240021', NULL, NULL, NULL, NULL, NULL, NULL, 'sfs', '公司', 0, '2022-11-13 18:08:37', '2022-11-13 18:08:37', 1591734480315330561, 1591734480315330561, 0);
INSERT INTO `address_book` VALUES (1658125507952119810, 1, '余文格', 1, '14384383456', NULL, NULL, NULL, NULL, NULL, NULL, '家', '家', 1, '2023-05-15 23:01:42', '2023-05-15 23:01:44', 1, 1, 0);
INSERT INTO `address_book` VALUES (1658134109513347074, 1658133953334243329, '余文格', 1, '17516136029', NULL, NULL, NULL, NULL, NULL, NULL, '河南大学', '学校', 1, '2023-05-15 23:35:53', '2023-05-15 23:35:55', 1658133953334243329, 1658133953334243329, 0);
INSERT INTO `address_book` VALUES (1660631374253641730, 1660254483675910146, '小明', 1, '13145215201', NULL, NULL, NULL, NULL, NULL, NULL, '[默认]福禄蔬菜店', '蔬菜店', 1, '2023-05-22 20:59:07', '2023-05-22 20:59:20', 1660254483675910146, 1660254483675910146, 0);
INSERT INTO `address_book` VALUES (1662441491194204162, 1662440622457368577, '11', 1, '13598573736', NULL, NULL, NULL, NULL, NULL, NULL, '[默认]福禄蔬菜店', '公司', 1, '2023-05-27 20:51:53', '2023-05-27 20:51:55', 1662440622457368577, 1662440622457368577, 0);
INSERT INTO `address_book` VALUES (1662744768192454658, 1662450171847495682, '11', 1, '13598573736', NULL, NULL, NULL, NULL, NULL, NULL, '[默认]福禄蔬菜店', '公司', 1, '2023-05-28 16:57:00', '2023-05-28 16:57:02', 1662450171847495682, 1662450171847495682, 0);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
                             `id` bigint NOT NULL COMMENT '主键',
                             `type` int NULL DEFAULT NULL COMMENT '类型   1 菜品分类 2 套餐分类',
                             `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '分类名称',
                             `sort` int NOT NULL DEFAULT 0 COMMENT '顺序',
                             `create_time` datetime NOT NULL COMMENT '创建时间',
                             `update_time` datetime NOT NULL COMMENT '更新时间',
                             `create_user` bigint NOT NULL COMMENT '创建人',
                             `update_user` bigint NOT NULL COMMENT '修改人',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `idx_category_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品及套餐分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1660616884568997889, 1, '茄果类', 1, '2023-05-22 20:01:33', '2023-05-22 20:01:33', 1, 1);
INSERT INTO `category` VALUES (1660616925413130242, 1, '叶菜类', 2, '2023-05-22 20:01:42', '2023-05-22 20:01:42', 1, 1);
INSERT INTO `category` VALUES (1660616958896259073, 1, '根菜类', 3, '2023-05-22 20:01:50', '2023-05-22 20:01:50', 1, 1);
INSERT INTO `category` VALUES (1660617006304477186, 1, '豆荚类', 4, '2023-05-22 20:02:02', '2023-05-22 20:02:02', 1, 1);
INSERT INTO `category` VALUES (1660618611955351553, 2, '组合1', 5, '2023-05-22 20:08:25', '2023-05-28 17:01:20', 1, 1662450171847495682);
INSERT INTO `category` VALUES (1660618651818016769, 2, '组合2', 6, '2023-05-22 20:08:34', '2023-05-28 17:01:26', 1, 1662450171847495682);
INSERT INTO `category` VALUES (1660618713402982402, 2, '组合3', 6, '2023-05-22 20:08:49', '2023-05-28 17:01:32', 1, 1662450171847495682);

-- ----------------------------
-- Table structure for dish
-- ----------------------------
DROP TABLE IF EXISTS `dish`;
CREATE TABLE `dish`  (
                         `id` bigint NOT NULL COMMENT '主键',
                         `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜品名称',
                         `category_id` bigint NOT NULL COMMENT '菜品分类id',
                         `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '菜品价格',
                         `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品码',
                         `image` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '图片',
                         `description` varchar(400) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述信息',
                         `status` int NOT NULL DEFAULT 1 COMMENT '0 停售 1 起售',
                         `sort` int NOT NULL DEFAULT 0 COMMENT '顺序',
                         `create_time` datetime NOT NULL COMMENT '创建时间',
                         `update_time` datetime NOT NULL COMMENT '更新时间',
                         `create_user` bigint NOT NULL COMMENT '创建人',
                         `update_user` bigint NOT NULL COMMENT '修改人',
                         `is_deleted` int NOT NULL DEFAULT 0 COMMENT '是否删除',
                         PRIMARY KEY (`id`) USING BTREE,
                         UNIQUE INDEX `idx_dish_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dish
-- ----------------------------
INSERT INTO `dish` VALUES (1660617379534618626, '番茄', 1660616884568997889, 500.00, '', '247c3995-9f32-43fc-9569-7dd13bf3e06f.jpg', '番茄好吃', 1, 0, '2023-05-22 20:03:31', '2023-05-27 19:13:00', 1, 1, 0);
INSERT INTO `dish` VALUES (1660617491816136706, '辣椒', 1660616884568997889, 600.00, '', 'ac8d05eb-3995-450d-afca-ca2ce931f0d3.jpg', '辣椒好吃', 1, 0, '2023-05-22 20:03:57', '2023-05-27 19:12:44', 1, 1, 0);
INSERT INTO `dish` VALUES (1660617575987429378, '白菜', 1660616925413130242, 300.00, '', 'ccf157c2-1489-416c-b4b0-410d54126508.jpg', '白菜好吃', 1, 0, '2023-05-22 20:04:18', '2023-05-27 19:12:26', 1, 1, 0);
INSERT INTO `dish` VALUES (1660617669176475650, '生菜', 1660616925413130242, 300.00, '', '6304ab48-0e4b-4b22-9670-6068acb8900a.jpg', '生菜好吃', 1, 0, '2023-05-22 20:04:40', '2023-05-27 19:12:16', 1, 1, 0);
INSERT INTO `dish` VALUES (1660617765574164482, '胡萝卜', 1660616958896259073, 600.00, '', 'b604b687-74bc-4028-bed2-3938c26e9d7e.jpg', '胡萝卜好吃', 1, 0, '2023-05-22 20:05:03', '2023-05-27 19:11:58', 1, 1, 0);
INSERT INTO `dish` VALUES (1660617864924643330, '莴苣', 1660616958896259073, 800.00, '', 'e636ebaa-f0ea-494c-9ea5-be1fd9599462.jpg', '莴苣好吃', 1, 0, '2023-05-22 20:05:26', '2023-05-27 19:11:47', 1, 1, 0);
INSERT INTO `dish` VALUES (1660617946629685250, '豌豆', 1660617006304477186, 700.00, '', 'c2cfb65e-3238-4231-b49e-610003b00110.jpg', '豌豆好吃', 1, 0, '2023-05-22 20:05:46', '2023-05-27 19:11:31', 1, 1, 0);
INSERT INTO `dish` VALUES (1660618014178951169, '四季豆', 1660617006304477186, 900.00, '', 'cbed3853-2f4d-45a6-963a-7bf60e556ad3.jpg', '四季豆好吃', 1, 0, '2023-05-22 20:06:02', '2023-05-27 19:11:19', 1, 1, 0);

-- ----------------------------
-- Table structure for dish_flavor
-- ----------------------------
DROP TABLE IF EXISTS `dish_flavor`;
CREATE TABLE `dish_flavor`  (
                                `id` bigint NOT NULL COMMENT '主键',
                                `dish_id` bigint NOT NULL COMMENT '菜品',
                                `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '口味名称',
                                `value` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '口味数据list',
                                `create_time` datetime NOT NULL COMMENT '创建时间',
                                `update_time` datetime NOT NULL COMMENT '更新时间',
                                `create_user` bigint NOT NULL COMMENT '创建人',
                                `update_user` bigint NOT NULL COMMENT '修改人',
                                `is_deleted` int NOT NULL DEFAULT 0 COMMENT '是否删除',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品口味关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dish_flavor
-- ----------------------------
INSERT INTO `dish_flavor` VALUES (1397849417888346113, 1397849417854791681, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:37:27', '2021-05-27 09:37:27', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397849936421761025, 1397849936404983809, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-27 09:39:30', '2021-05-27 09:39:30', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397849936438538241, 1397849936404983809, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:39:30', '2021-05-27 09:39:30', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397850630734262274, 1397850630700707841, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-27 09:42:16', '2021-05-27 09:42:16', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397850630755233794, 1397850630700707841, '辣度', '[\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:42:16', '2021-05-27 09:42:16', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397853423486414850, 1397853423461249026, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 09:53:22', '2021-05-27 09:53:22', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397854133632413697, 1397854133603053569, '温度', '[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]', '2021-05-27 09:56:11', '2021-05-27 09:56:11', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397855742303186946, 1397855742273826817, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 10:02:35', '2021-05-27 10:02:35', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397855906497605633, 1397855906468245506, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-27 10:03:14', '2021-05-27 10:03:14', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397856190573621250, 1397856190540066818, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 10:04:21', '2021-05-27 10:04:21', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397859056709316609, 1397859056684150785, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 10:15:45', '2021-05-27 10:15:45', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397859277837217794, 1397859277812051969, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 10:16:37', '2021-05-27 10:16:37', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397859487502086146, 1397859487476920321, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 10:17:27', '2021-05-27 10:17:27', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397859757061615618, 1397859757036449794, '甜味', '[\"无糖\",\"少糖\",\"半躺\",\"多糖\",\"全糖\"]', '2021-05-27 10:18:32', '2021-05-27 10:18:32', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397861135754506242, 1397861135733534722, '甜味', '[\"无糖\",\"少糖\",\"半躺\",\"多糖\",\"全糖\"]', '2021-05-27 10:24:00', '2021-05-27 10:24:00', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397861370035744769, 1397861370010578945, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-27 10:24:56', '2021-05-27 10:24:56', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397861898467717121, 1397861898438356993, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-27 10:27:02', '2021-05-27 10:27:02', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398089545865015297, 1398089545676271617, '温度', '[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]', '2021-05-28 01:31:38', '2021-05-28 01:31:38', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398089782323097601, 1398089782285348866, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:32:34', '2021-05-28 01:32:34', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398090003262255106, 1398090003228700673, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-28 01:33:27', '2021-05-28 01:33:27', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398090264554811394, 1398090264517062657, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-28 01:34:29', '2021-05-28 01:34:29', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398090455399837698, 1398090455324340225, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:35:14', '2021-05-28 01:35:14', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398090685449023490, 1398090685419663362, '温度', '[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]', '2021-05-28 01:36:09', '2021-05-28 01:36:09', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398090825358422017, 1398090825329061889, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-28 01:36:43', '2021-05-28 01:36:43', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398091007051476993, 1398091007017922561, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:37:26', '2021-05-28 01:37:26', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398091296164851713, 1398091296131297281, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:38:35', '2021-05-28 01:38:35', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398091546531246081, 1398091546480914433, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2021-05-28 01:39:35', '2021-05-28 01:39:35', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398091729809747969, 1398091729788776450, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:40:18', '2021-05-28 01:40:18', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398091889499484161, 1398091889449152513, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:40:56', '2021-05-28 01:40:56', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398092095179763713, 1398092095142014978, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:41:45', '2021-05-28 01:41:45', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398092283877306370, 1398092283847946241, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:42:30', '2021-05-28 01:42:30', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398094018939236354, 1398094018893099009, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:49:24', '2021-05-28 01:49:24', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398094391494094850, 1398094391456346113, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-05-28 01:50:53', '2021-05-28 01:50:53', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1399574026165727233, 1399305325713600514, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2021-06-01 03:50:25', '2021-06-01 03:50:25', 1399309715396669441, 1399309715396669441, 0);

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
                             `id` bigint NOT NULL COMMENT '主键',
                             `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '姓名',
                             `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
                             `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
                             `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
                             `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '性别',
                             `id_number` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '身份证号',
                             `status` int NOT NULL DEFAULT 1 COMMENT '状态 0:禁用，1:正常',
                             `create_time` datetime NOT NULL COMMENT '创建时间',
                             `update_time` datetime NOT NULL COMMENT '更新时间',
                             `create_user` bigint NOT NULL COMMENT '创建人',
                             `update_user` bigint NOT NULL COMMENT '修改人',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `idx_username`(`username` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '员工信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, '店长', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', 1, '2021-05-06 17:20:07', '2023-05-15 23:39:45', 1, 1658133953334243329);
INSERT INTO `employee` VALUES (1560803476323168258, '小龙', '13412345679', 'e10adc3949ba59abbe56e057f20f883e', '13412345679', '1', '112233445566778899', 0, '2022-08-20 09:38:42', '2023-05-15 23:39:30', 1, 1658133953334243329);
INSERT INTO `employee` VALUES (1658134937913552897, '余文格', '17516136029', 'e10adc3949ba59abbe56e057f20f883e', '17516136029', '1', '411511200308065641', 1, '2023-05-15 23:39:11', '2023-05-15 23:39:11', 1658133953334243329, 1658133953334243329);

-- ----------------------------
-- Table structure for info
-- ----------------------------
DROP TABLE IF EXISTS `info`;
CREATE TABLE `info`  (
                         `id` bigint NOT NULL,
                         `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '（通知标题）',
                         `text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '（通知内容）',
                         `create_time` datetime NULL DEFAULT NULL COMMENT '（创建时间）',
                         `create_user` bigint NULL DEFAULT NULL COMMENT '（创建人）',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通知表单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of info
-- ----------------------------
INSERT INTO `info` VALUES (1660621864885567490, '优惠来袭', '六一优惠劵上新啦，购买优惠', '2023-05-22 20:21:20', 1);

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
                                 `id` bigint NOT NULL COMMENT '主键',
                                 `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '名字',
                                 `image` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
                                 `order_id` bigint NOT NULL COMMENT '订单id',
                                 `dish_id` bigint NULL DEFAULT NULL COMMENT '菜品id',
                                 `setmeal_id` bigint NULL DEFAULT NULL COMMENT '套餐id',
                                 `dish_flavor` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '口味',
                                 `number` int NOT NULL DEFAULT 1 COMMENT '数量',
                                 `amount` decimal(10, 2) NOT NULL COMMENT '金额',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '订单明细表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (1660631493996826625, '辣椒', 'd201fe5a-aa86-45ff-8fa3-6b5f1b898f83.png', 1660631493933912066, 1660617491816136706, NULL, NULL, 1, 6.00);
INSERT INTO `order_detail` VALUES (1660633760233730049, '辣椒', 'd201fe5a-aa86-45ff-8fa3-6b5f1b898f83.png', 1660633760166621185, 1660617491816136706, NULL, NULL, 0, 6.00);
INSERT INTO `order_detail` VALUES (1660633760271478785, '生菜', '57539dfb-b6d5-4fd3-9257-4452f1936663.png', 1660633760166621185, 1660617669176475650, NULL, NULL, 0, 3.00);
INSERT INTO `order_detail` VALUES (1660633760271478786, '番茄', 'b60c164a-6448-4582-8764-83c835e890ee.png', 1660633760166621185, 1660617379534618626, NULL, NULL, 0, 5.00);
INSERT INTO `order_detail` VALUES (1662441525772046339, '辣椒', 'ac8d05eb-3995-450d-afca-ca2ce931f0d3.jpg', 1662441525772046338, 1660617491816136706, NULL, NULL, 1, 6.00);
INSERT INTO `order_detail` VALUES (1662441525839155202, '番茄', '247c3995-9f32-43fc-9569-7dd13bf3e06f.jpg', 1662441525772046338, 1660617379534618626, NULL, NULL, 1, 5.00);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
                           `id` bigint NOT NULL COMMENT '主键',
                           `number` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '订单号',
                           `status` int NOT NULL DEFAULT 1 COMMENT '订单状态 1待付款，2待派送，3已派送，4已完成，5已取消',
                           `user_id` bigint NOT NULL COMMENT '下单用户',
                           `address_book_id` bigint NOT NULL COMMENT '地址id',
                           `order_time` datetime NOT NULL COMMENT '下单时间',
                           `checkout_time` datetime NOT NULL COMMENT '结账时间',
                           `pay_method` int NOT NULL DEFAULT 1 COMMENT '支付方式 1微信,2支付宝',
                           `voucher_id` bigint NULL DEFAULT 0 COMMENT '（关联优惠券id）\r\n',
                           `amount` decimal(10, 2) NOT NULL COMMENT '实收金额',
                           `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
                           `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                           `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                           `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                           `consignee` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1563375134074400770, '1563375134074400770', 2, 1563023776787914754, 1563375034338045953, '2022-08-27 11:57:33', '2022-08-27 11:57:33', 1, 150.00, '多送点米饭', '15038294881', '中国', NULL, '龙');
INSERT INTO `orders` VALUES (1658134133815144449, '1658134133815144449', 2, 1658133953334243329, 1658134109513347074, '2023-05-15 23:35:59', '2023-05-15 23:35:59', 1, 198.00, '', '17516136029', '河南大学', NULL, '余文格');
INSERT INTO `orders` VALUES (1658134427613556737, '1658134427613556737', 4, 1658133953334243329, 1658134109513347074, '2023-05-15 23:37:09', '2023-05-15 23:37:09', 1, 5.00, '', '17516136029', '河南大学', NULL, '余文格');
INSERT INTO `orders` VALUES (1660278311856734209, '1660278311856734209', 2, 1660254483675910146, 1660278270626725889, '2023-05-21 21:36:11', '2023-05-21 21:36:11', 1, 336.00, '', '14384381314', '河南周党', NULL, '任宇轩');
INSERT INTO `orders` VALUES (1660294727318134785, '1660294727318134785', 2, 1660254483675910146, 1660278270626725889, '2023-05-21 22:41:24', '2023-05-21 22:41:24', 1, 40.00, '', '14384381314', '河南周党', NULL, '任宇轩');
INSERT INTO `orders` VALUES (1660296467136405505, '1660296467136405505', 2, 1660254483675910146, 1660278270626725889, '2023-05-21 22:48:19', '2023-05-21 22:48:19', 1, 160.00, '', '14384381314', '河南周党', NULL, '任宇轩');
INSERT INTO `orders` VALUES (1660296982675087361, '1660296982675087361', 2, 1660254483675910146, 1660278270626725889, '2023-05-21 22:50:22', '2023-05-21 22:50:22', 1, 22.00, '', '14384381314', '河南周党', NULL, '任宇轩');
INSERT INTO `orders` VALUES (1660631493933912066, '1660631493933912066', 2, 1660254483675910146, 1660631374253641730, '2023-05-22 20:59:36', '2023-05-22 20:59:36', 1, 6.00, '', '13145215201', '[默认]福禄蔬菜店', NULL, '小明');
INSERT INTO `orders` VALUES (1660633760166621185, '1660633760166621185', 2, 1660254483675910146, 1660631374253641730, '2023-05-22 21:08:36', '2023-05-22 21:08:36', 1, 0.00, '', '13145215201', '[默认]福禄蔬菜店', NULL, '小明');
INSERT INTO `orders` VALUES (1662441525772046338, '1662441525772046338', 2, 1662440622457368577, 1662441491194204162, '2023-05-27 20:52:01', '2023-05-27 20:52:01', 1, 11.00, '', '13598573736', '[默认]福禄蔬菜店', '用户9394', '11');

-- ----------------------------
-- Table structure for setmeal
-- ----------------------------
DROP TABLE IF EXISTS `setmeal`;
CREATE TABLE `setmeal`  (
                            `id` bigint NOT NULL COMMENT '主键',
                            `category_id` bigint NOT NULL COMMENT '菜品分类id',
                            `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '套餐名称',
                            `price` decimal(10, 2) NOT NULL COMMENT '套餐价格',
                            `status` int NULL DEFAULT NULL COMMENT '状态 0:停用 1:启用',
                            `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '编码',
                            `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述信息',
                            `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
                            `create_time` datetime NOT NULL COMMENT '创建时间',
                            `update_time` datetime NOT NULL COMMENT '更新时间',
                            `create_user` bigint NOT NULL COMMENT '创建人',
                            `update_user` bigint NOT NULL COMMENT '修改人',
                            `is_deleted` int NOT NULL DEFAULT 0 COMMENT '是否删除',
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE INDEX `idx_setmeal_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '套餐' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of setmeal
-- ----------------------------
INSERT INTO `setmeal` VALUES (1660618941640228866, 1660618611955351553, '组合1', 2300.00, 1, '', '好吃', '4cbe2d88-3c42-4dcb-8605-a65bcc383ab0.jpg', '2023-05-22 20:09:43', '2023-05-27 19:22:24', 1, 1, 0);
INSERT INTO `setmeal` VALUES (1660619043222077441, 1660618651818016769, '组合2', 3300.00, 1, '', '好吃', 'fffb896f-54e5-4f55-81a6-cab69410b258.jpg', '2023-05-22 20:10:07', '2023-05-27 19:22:33', 1, 1, 0);
INSERT INTO `setmeal` VALUES (1660619136000081921, 1660618713402982402, '组合3', 3100.00, 1, '', '好吃', '008d25e4-7309-4fe7-b382-b098a2ccc99c.jpg', '2023-05-22 20:10:30', '2023-05-27 19:22:43', 1, 1, 0);

-- ----------------------------
-- Table structure for setmeal_dish
-- ----------------------------
DROP TABLE IF EXISTS `setmeal_dish`;
CREATE TABLE `setmeal_dish`  (
                                 `id` bigint NOT NULL COMMENT '主键',
                                 `setmeal_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '套餐id ',
                                 `dish_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜品id',
                                 `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '菜品名称 （冗余字段）',
                                 `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '菜品原价（冗余字段）',
                                 `copies` int NOT NULL COMMENT '份数',
                                 `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
                                 `create_time` datetime NOT NULL COMMENT '创建时间',
                                 `update_time` datetime NOT NULL COMMENT '更新时间',
                                 `create_user` bigint NOT NULL COMMENT '创建人',
                                 `update_user` bigint NOT NULL COMMENT '修改人',
                                 `is_deleted` int NOT NULL DEFAULT 0 COMMENT '是否删除',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '套餐菜品关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of setmeal_dish
-- ----------------------------
INSERT INTO `setmeal_dish` VALUES (1662418973016211458, '1660618941640228866', '1660617669176475650', '生菜', 300.00, 1, 0, '2023-05-27 19:22:24', '2023-05-27 19:22:24', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1662418973016211459, '1660618941640228866', '1660617491816136706', '辣椒', 600.00, 1, 0, '2023-05-27 19:22:24', '2023-05-27 19:22:24', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1662419012232953857, '1660619043222077441', '1660617765574164482', '胡萝卜', 600.00, 1, 0, '2023-05-27 19:22:33', '2023-05-27 19:22:33', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1662419012232953858, '1660619043222077441', '1660617864924643330', '莴苣', 800.00, 1, 0, '2023-05-27 19:22:33', '2023-05-27 19:22:33', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1662419053962084353, '1660619136000081921', '1660618014178951169', '四季豆', 900.00, 1, 0, '2023-05-27 19:22:43', '2023-05-27 19:22:43', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1662419053962084354, '1660619136000081921', '1660617946629685250', '豌豆', 700.00, 1, 0, '2023-05-27 19:22:43', '2023-05-27 19:22:43', 1, 1, 0);

-- ----------------------------
-- Table structure for shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart`  (
                                  `id` bigint NOT NULL COMMENT '主键',
                                  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '名称',
                                  `image` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
                                  `user_id` bigint NOT NULL COMMENT '主键',
                                  `dish_id` bigint NULL DEFAULT NULL COMMENT '菜品id',
                                  `setmeal_id` bigint NULL DEFAULT NULL COMMENT '套餐id',
                                  `dish_flavor` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '口味',
                                  `number` int NOT NULL DEFAULT 1 COMMENT '数量',
                                  `amount` decimal(10, 2) NOT NULL COMMENT '金额',
                                  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '购物车' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shopping_cart
-- ----------------------------
INSERT INTO `shopping_cart` VALUES (1591734498166288385, '儿童套餐A计划', '61d20592-b37f-4d72-a864-07ad5bb8f3bb.jpg', 1591734480315330561, NULL, 1415580119015145474, NULL, 1, 40.00, '2022-11-13 18:07:32');
INSERT INTO `shopping_cart` VALUES (1662423269090832385, '组合1', '4cbe2d88-3c42-4dcb-8605-a65bcc383ab0.jpg', 1660254483675910146, NULL, 1660618941640228866, NULL, 1, 23.00, '2023-05-27 19:39:28');
INSERT INTO `shopping_cart` VALUES (1662464456875159554, '辣椒', 'ac8d05eb-3995-450d-afca-ca2ce931f0d3.jpg', 1662463976728014850, 1660617491816136706, NULL, NULL, 1, 6.00, '2023-05-27 22:23:08');
INSERT INTO `shopping_cart` VALUES (1662744164724383745, '白菜', 'ccf157c2-1489-416c-b4b0-410d54126508.jpg', 1662450171847495682, 1660617575987429378, NULL, NULL, 1, 3.00, '2023-05-28 16:54:36');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `id` bigint NOT NULL COMMENT '主键',
                         `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '姓名',
                         `phone` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
                         `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别',
                         `id_number` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '身份证号',
                         `avatar` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '头像',
                         `status` int NULL DEFAULT 0 COMMENT '状态 0:禁用，1:正常',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1662450171847495682, '用户1428', '13598573736', NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (1662463976728014850, '用户1040', '13598573739', NULL, NULL, NULL, 1);

-- ----------------------------
-- Table structure for voucher
-- ----------------------------
DROP TABLE IF EXISTS `voucher`;
CREATE TABLE `voucher`  (
                            `id` bigint NOT NULL,
                            `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '（名称）\r\n',
                            `price` decimal(10, 0) NOT NULL COMMENT '（金额）\r\n',
                            `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '（图片路径）',
                            `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '（描述）\r\n（种类非必须以后可能加）\r\n',
                            `type` int NULL DEFAULT NULL COMMENT '（图片）\r\n',
                            `status` int NOT NULL COMMENT '（状态0表示停用，1启用（创建时默认））\r\n',
                            `create_time` datetime NOT NULL COMMENT '创建时间',
                            `update_time` datetime NOT NULL COMMENT '更新时间',
                            `create_user` bigint NOT NULL COMMENT '创建人',
                            `update_user` bigint NOT NULL COMMENT '修改人',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '新增个优惠券功能' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of voucher
-- ----------------------------
INSERT INTO `voucher` VALUES (1660621147063992322, '六一特价优惠', 1000, '4e3d5f70-c3fa-4161-8b6a-23872fdae015.jpeg', '满79元可用哦', NULL, 0, '2023-05-22 20:18:29', 1);
INSERT INTO `voucher` VALUES (1660621390836940802, '五一特惠', 2000, 'c1c082bd-df28-4302-bdd0-3458099aeefb.jpeg', '五一特惠', NULL, 1, '2023-05-22 20:19:27', 1);
INSERT INTO `voucher` VALUES (1660621652498595842, '六一八优惠', 3000, '3e13cd5d-29fb-4e5a-a7ca-a2763a107c7c.jpeg', '六一八特惠', NULL, 1, '2023-05-22 20:20:29', 1);

-- ----------------------------
-- Table structure for voucher_user
-- ----------------------------
DROP TABLE IF EXISTS `voucher_user`;
CREATE TABLE `voucher_user`  (
                                 `id` bigint NOT NULL,
                                 `voucher_id` bigint NOT NULL DEFAULT 0 COMMENT '（关联优惠券id）\r\n',
                                 `user_id` bigint NOT NULL DEFAULT 0 COMMENT '（关联用户id）\r\n',
                                 `status` int NOT NULL DEFAULT 0 COMMENT '（用户是否使用0没用1用了）\r\n',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '优惠券和用户关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of voucher_user
-- ----------------------------
INSERT INTO `voucher_user` VALUES (1662450171902021633, 1660621652498595842, 1662450171847495682, 0);
INSERT INTO `voucher_user` VALUES (1662463976786735105, 1660621652498595842, 1662463976728014850, 0);

SET FOREIGN_KEY_CHECKS = 1;
