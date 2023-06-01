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

 Date: 01/06/2023 13:57:08
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
INSERT INTO `address_book` VALUES (1664147019171667969, 1663887502152310785, '福禄蔬菜店', 1, '13598573731', NULL, NULL, NULL, NULL, NULL, NULL, '[默认]福禄蔬菜店', '公司', 1, '2023-06-01 13:49:02', '2023-06-01 13:49:04', 1663887502152310785, 1663887502152310785, 0);
INSERT INTO `address_book` VALUES (1664147265624776705, 1664147221026742273, '福禄蔬菜店', 1, '13598573732', NULL, NULL, NULL, NULL, NULL, NULL, '[默认]福禄蔬菜店', '公司', 1, '2023-06-01 13:50:01', '2023-06-01 13:50:03', 1664147221026742273, 1664147221026742273, 0);

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
INSERT INTO `category` VALUES (1660617006304477186, 1, '豆荚类', 3, '2023-05-22 20:02:02', '2023-05-31 23:12:08', 1, 1662450171847495682);
INSERT INTO `category` VALUES (1660618611955351553, 2, '蔬菜组合1', 6, '2023-05-22 20:08:25', '2023-05-31 21:16:17', 1, 1);
INSERT INTO `category` VALUES (1660618651818016769, 2, '蔬菜组合2', 6, '2023-05-22 20:08:34', '2023-05-31 21:16:29', 1, 1);
INSERT INTO `category` VALUES (1660618713402982402, 2, '蔬菜组合3', 6, '2023-05-22 20:08:49', '2023-05-31 21:16:43', 1, 1);
INSERT INTO `category` VALUES (1663896867106316289, 1, '瓜果类', 5, '2023-05-31 21:15:01', '2023-05-31 21:15:01', 1, 1);
INSERT INTO `category` VALUES (1663896913046528001, 1, '核果类', 4, '2023-05-31 21:15:12', '2023-05-31 21:15:12', 1, 1);
INSERT INTO `category` VALUES (1663897007305121793, 1, '浆果类', 4, '2023-05-31 21:15:35', '2023-05-31 21:15:35', 1, 1);
INSERT INTO `category` VALUES (1663897097470074881, 1, '橘果类', 4, '2023-05-31 21:15:56', '2023-05-31 21:15:56', 1, 1);
INSERT INTO `category` VALUES (1663897143221542914, 1, '仁果类', 4, '2023-05-31 21:16:07', '2023-05-31 21:16:07', 1, 1);

-- ----------------------------
-- Table structure for dish
-- ----------------------------
DROP TABLE IF EXISTS `dish`;
CREATE TABLE `dish`  (
                         `id` bigint NOT NULL COMMENT '主键',
                         `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '果蔬名称',
                         `category_id` bigint NOT NULL COMMENT '果蔬分类id',
                         `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '果蔬价格',
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
INSERT INTO `dish` VALUES (1663899571895451649, '甜椒', 1660616884568997889, 400.00, '', '7f72e12f-f622-4dfc-b3a8-4132a7a0da08.jpg', '甜椒味美价廉', 1, 0, '2023-05-31 21:25:46', '2023-05-31 21:25:46', 1, 1, 0);
INSERT INTO `dish` VALUES (1663899808374505474, '茄子', 1660616884568997889, 300.00, '', '2e0c0653-933e-4e05-b0d9-8869007fda7c.jpg', '茄子味道好，十里飘香', 1, 0, '2023-05-31 21:26:43', '2023-05-31 21:26:43', 1, 1, 0);
INSERT INTO `dish` VALUES (1663899948481036290, '香瓜茄', 1660616884568997889, 600.00, '', '820160a3-19d8-4481-8c46-2bd53a34fef6.jpg', '香瓜茄，带给你不一样的茄子风味', 1, 0, '2023-05-31 21:27:16', '2023-05-31 21:27:16', 1, 1, 0);
INSERT INTO `dish` VALUES (1663900285287841794, '荠菜', 1660616925413130242, 50.00, '', '99016c7a-46c8-4fab-8234-39b69559c410.jpg', '五毛大甩卖', 1, 0, '2023-05-31 21:28:36', '2023-05-31 21:28:36', 1, 1, 0);
INSERT INTO `dish` VALUES (1663900422009569281, '韭菜', 1660616925413130242, 200.00, '', '46000de0-fa80-4079-bfc6-d2134dae2539.jpg', '搭配鸡蛋，好吃翻倍', 1, 0, '2023-05-31 21:29:09', '2023-05-31 21:29:09', 1, 1, 0);
INSERT INTO `dish` VALUES (1663900683348262914, '木耳菜', 1660616925413130242, 300.00, '', '214edde2-5639-46f2-a30a-69e150e3a7b9.jpg', '补钙壮骨是木耳菜的重要功效之一', 1, 0, '2023-05-31 21:30:11', '2023-05-31 21:30:11', 1, 1, 0);
INSERT INTO `dish` VALUES (1663900866366717954, '空心菜', 1660616925413130242, 200.00, '', '2bbe54bb-1895-4467-87a8-45554a42dee2.jpg', '空心菜含有丰富营养，含有大量的纤维素和半纤维素、胶浆、果胶等食用纤维素，这些营养对人体胃肠有很大好处', 1, 0, '2023-05-31 21:30:55', '2023-05-31 21:30:55', 1, 1, 0);
INSERT INTO `dish` VALUES (1663901041575378945, '萝卜', 1660616958896259073, 200.00, '', 'f89bc1a9-1408-4284-a251-62997aeee59b.jpg', '白萝卜，脆爽可口', 1, 0, '2023-05-31 21:31:37', '2023-05-31 21:31:37', 1, 1, 0);
INSERT INTO `dish` VALUES (1663901198039695362, '竹笋', 1660616958896259073, 500.00, '', 'fa87e41f-72d6-4733-9c77-10af8af79401.jpg', '不常见的美味', 1, 0, '2023-05-31 21:32:14', '2023-05-31 21:32:14', 1, 1, 0);
INSERT INTO `dish` VALUES (1663901589804466178, '菜豆', 1660617006304477186, 300.00, '', '4e92b6ae-5b3d-4c4b-a852-46bb242f2a30.jpg', '菜豆适合与富含氨基酸的食物一起烹调，可以明显提高豌豆的营养价值', 1, 0, '2023-05-31 21:33:47', '2023-05-31 21:33:47', 1, 1, 0);
INSERT INTO `dish` VALUES (1663901699141582850, '大豆', 1660617006304477186, 400.00, '', '06024398-e9c0-4a0d-a218-64a1c43cd6d1.jpg', '可做菜，可磨豆浆', 1, 0, '2023-05-31 21:34:14', '2023-05-31 21:34:14', 1, 1, 0);
INSERT INTO `dish` VALUES (1663901871116435458, '梨子', 1663897143221542914, 700.00, '', '655e9e2e-ac2a-4f28-80cf-194b8ea25814.jpg', '好吃，脆甜', 1, 0, '2023-05-31 21:34:55', '2023-05-31 21:34:55', 1, 1, 0);
INSERT INTO `dish` VALUES (1663901964653608962, '枇杷', 1663897143221542914, 1000.00, '', 'b894fbd7-f788-46b4-b5ca-d1e1f9030ff2.jpg', '酸酸甜甜，很好吃', 1, 0, '2023-05-31 21:35:17', '2023-05-31 21:37:15', 1, 1, 0);
INSERT INTO `dish` VALUES (1663902742759874562, '山楂', 1663897143221542914, 600.00, '', '0fef7ab4-6d7a-4e1a-904f-4fa6cbf453ca.jpg', '防癌抗癌、治疗痛经', 1, 0, '2023-05-31 21:38:22', '2023-05-31 21:38:22', 1, 1, 0);
INSERT INTO `dish` VALUES (1663902823265345537, '苹果', 1663897143221542914, 900.00, '', '0d4432c1-2efc-40bd-a32c-f48fb80f5588.jpg', '脆甜可口', 1, 0, '2023-05-31 21:38:42', '2023-05-31 21:38:42', 1, 1, 0);
INSERT INTO `dish` VALUES (1663902933634260994, '橙子', 1663897097470074881, 1000.00, '', '5bdb6b8f-b713-4763-984b-2d52f96c7a48.jpg', '可吃，可榨汁', 1, 0, '2023-05-31 21:39:08', '2023-05-31 21:39:08', 1, 1, 0);
INSERT INTO `dish` VALUES (1663903054186946562, '橘子', 1663897097470074881, 800.00, '', '14a8f83e-2854-4593-8641-7da7adb656f1.jpg', '橘子日常小零食', 1, 0, '2023-05-31 21:39:37', '2023-05-31 21:39:37', 1, 1, 0);
INSERT INTO `dish` VALUES (1663903169517723649, '柠檬', 1663897097470074881, 1000.00, '', '43c99973-2746-49e2-bbd2-7d4aad99eaf3.jpg', '超酸的哦，胆小误入', 1, 0, '2023-05-31 21:40:04', '2023-05-31 21:40:04', 1, 1, 0);
INSERT INTO `dish` VALUES (1663903256549531649, '柚子', 1663897097470074881, 1800.00, '', 'fdea5b53-0057-4566-989f-3a06daabd3bd.jpg', '好吃，料多', 1, 0, '2023-05-31 21:40:25', '2023-05-31 21:40:25', 1, 1, 0);
INSERT INTO `dish` VALUES (1663903452318670850, '草莓', 1663897007305121793, 1500.00, '', '5d76cc24-f484-469c-89e9-8baf104a936d.jpg', '保护视力、防便秘，好吃不贵', 1, 0, '2023-05-31 21:41:12', '2023-05-31 21:41:12', 1, 1, 0);
INSERT INTO `dish` VALUES (1663903665607417857, '龙眼', 1663897007305121793, 1600.00, '', '2f45880c-317c-4d0d-aca6-9729e3755475.jpg', '果肉白色或浅黄色，质地柔软，味甜，含有丰富的营养成分', 1, 0, '2023-05-31 21:42:02', '2023-05-31 21:42:02', 1, 1, 0);
INSERT INTO `dish` VALUES (1663903803239309314, '猕猴桃', 1663897007305121793, 1800.00, '', '3e0c246e-9138-449f-b5e8-c17463ccaf4b.jpg', '营养丰富、口感清爽的水果，也称为奇异果', 1, 0, '2023-05-31 21:42:35', '2023-05-31 21:42:35', 1, 1, 0);
INSERT INTO `dish` VALUES (1663904030373453825, '香蕉', 1663897007305121793, 1500.00, '', 'ae26b635-583e-4f00-830f-0c1a313ff576.jpg', 'banana软糯细嫩、甜度适中的果肉，富含碳水化合物、钾、维生素C', 1, 0, '2023-05-31 21:43:29', '2023-05-31 21:43:29', 1, 1, 0);
INSERT INTO `dish` VALUES (1663904163991396353, '杨桃', 1663897007305121793, 2000.00, '', 'd1cdd1bf-f2f4-46ad-b54c-41ba3ab97619.jpg', '果肉清脆多汁，口感酸甜适中，富含维生素C、膳食纤维', 1, 0, '2023-05-31 21:44:01', '2023-05-31 21:44:01', 1, 1, 0);
INSERT INTO `dish` VALUES (1663904330777894914, '李子', 1663896913046528001, 1600.00, '', '688908a4-2c31-4aee-abff-d92d20fcb007.jpg', '果肉鲜嫩多汁，口感甜酸适中，富含碳水化合物、维生素C、钾、磷、镁', 1, 0, '2023-05-31 21:44:41', '2023-05-31 21:44:41', 1, 1, 0);
INSERT INTO `dish` VALUES (1663904485841313794, '桃子', 1663896913046528001, 1700.00, '', '8a018dc3-f43b-489f-bc0b-203f2647a950.jpg', '果肉鲜嫩多汁，口感甜美，富含碳水化合物、维生素C、维生素A、钾等营养成分', 1, 0, '2023-05-31 21:45:18', '2023-05-31 21:45:18', 1, 1, 0);
INSERT INTO `dish` VALUES (1663904686555537409, '樱桃', 1663896913046528001, 2000.00, '', '2f9587ca-db63-4142-984e-e627e1872d80.jpg', '樱桃可以直接生食，也可以用于制作樱桃酒、樱桃派、樱桃冰淇淋等美食', 1, 0, '2023-05-31 21:46:06', '2023-05-31 21:46:06', 1, 1, 0);
INSERT INTO `dish` VALUES (1663904868596719618, '杏子', 1663896913046528001, 1600.00, '', '72e32a99-aae3-4f5e-b109-bea21e3dfede.jpg', '口感甜美或略带酸味，富含碳水化合物、维生素C、维生素E', 1, 0, '2023-05-31 21:46:49', '2023-05-31 21:46:49', 1, 1, 0);
INSERT INTO `dish` VALUES (1663905186822758401, '哈密瓜', 1663896867106316289, 2200.00, '', '89fc6518-91a5-487e-8492-0145df30644f.jpg', '哈密瓜可以直接生食，也可以制成哈密瓜冰淇淋、哈密瓜果汁、哈密瓜沙拉等美食。它不仅营养丰富，而且带有浓郁的香气和独特的口感', 1, 0, '2023-05-31 21:48:05', '2023-05-31 21:48:05', 1, 1, 0);
INSERT INTO `dish` VALUES (1663905274005561345, '西瓜', 1663896867106316289, 1700.00, '', 'e16cb23e-97f9-4761-93b3-d4efac54f840.jpg', '缓解疲劳，美容养颜，消暑必备', 1, 0, '2023-05-31 21:48:26', '2023-05-31 21:48:26', 1, 1, 0);
INSERT INTO `dish` VALUES (1663905519233933314, '香瓜', 1663896867106316289, 1800.00, '', '7cef0eee-3c56-4526-b2e9-3547add8af3d.jpg', '含有多种天然酵素和抗氧化成分，能够促进消化、调节血压、增强免疫力', 1, 0, '2023-05-31 21:49:24', '2023-05-31 21:49:24', 1, 1, 0);

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
INSERT INTO `dish_flavor` VALUES (1663901041575378946, 1663901041575378945, '', '[]', '2023-05-31 21:31:37', '2023-05-31 21:31:37', 1, 1, 0);

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
                         `create_time` datetime NOT NULL COMMENT '创建时间',
                         `update_time` datetime NOT NULL COMMENT '更新时间',
                         `create_user` bigint NOT NULL COMMENT '创建人',
                         `update_user` bigint NOT NULL COMMENT '修改人',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通知表单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of info
-- ----------------------------
INSERT INTO `info` VALUES (1663888140823175170, '双十一特惠', '双十一特惠，快来领取优惠券进行大抢购了，快来快来！！', '2023-05-31 20:40:21', '2023-05-31 20:40:21', 1663887502152310785, 1663887502152310785);
INSERT INTO `info` VALUES (1663888350509015042, '五一大特惠', '五一大促销特惠，快来领取优惠券进行大抢购了，快来快来！！', '2023-05-31 20:41:11', '2023-05-31 20:41:11', 1663887502152310785, 1663887502152310785);
INSERT INTO `info` VALUES (1663906075297980418, '六一八特惠', '六一八超值特惠，发放优惠券啦，快来抢购吧', '2023-05-31 21:51:37', '2023-05-31 21:51:37', 1662450171847495682, 1662450171847495682);
INSERT INTO `info` VALUES (1664148662764191745, '新用户', '欢迎来到福禄蔬菜店，祝你有一个愉快的购物体验', '2023-06-01 13:55:34', '2023-06-01 13:55:34', 1, 1);

-- ----------------------------
-- Table structure for info_user
-- ----------------------------
DROP TABLE IF EXISTS `info_user`;
CREATE TABLE `info_user`  (
                              `id` bigint NOT NULL,
                              `info_id` bigint NOT NULL DEFAULT 0 COMMENT '（关联信息id）\r\n',
                              `user_id` bigint NOT NULL DEFAULT 0 COMMENT '（关联用户id）\r\n',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通知和用户关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of info_user
-- ----------------------------
INSERT INTO `info_user` VALUES (1663887502282334209, 1664148662764191745, 1663887502152310785);
INSERT INTO `info_user` VALUES (1663887502282334210, 1664148662764191745, 1662450171847495682);
INSERT INTO `info_user` VALUES (1664147221093851138, 1664148662764191745, 1664147221026742273);

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
INSERT INTO `order_detail` VALUES (1663849253782499330, '番茄', '247c3995-9f32-43fc-9569-7dd13bf3e06f.jpg', 1663849253719584770, 1660617379534618626, NULL, NULL, 1, 5.00);
INSERT INTO `order_detail` VALUES (1663849391271784451, '辣椒', 'ac8d05eb-3995-450d-afca-ca2ce931f0d3.jpg', 1663849391271784450, 1660617491816136706, NULL, NULL, 1, 6.00);
INSERT INTO `order_detail` VALUES (1663849391271784452, '番茄', '247c3995-9f32-43fc-9569-7dd13bf3e06f.jpg', 1663849391271784450, 1660617379534618626, NULL, NULL, 1, 5.00);
INSERT INTO `order_detail` VALUES (1663849391271784453, '白菜', 'ccf157c2-1489-416c-b4b0-410d54126508.jpg', 1663849391271784450, 1660617575987429378, NULL, NULL, 1, 3.00);
INSERT INTO `order_detail` VALUES (1663849391271784454, '生菜', '6304ab48-0e4b-4b22-9670-6068acb8900a.jpg', 1663849391271784450, 1660617669176475650, NULL, NULL, 1, 3.00);
INSERT INTO `order_detail` VALUES (1663851837297573890, '胡萝卜', 'b604b687-74bc-4028-bed2-3938c26e9d7e.jpg', 1663851837234659329, 1660617765574164482, NULL, NULL, 1, 6.00);
INSERT INTO `order_detail` VALUES (1663851837297573891, '莴苣', 'e636ebaa-f0ea-494c-9ea5-be1fd9599462.jpg', 1663851837234659329, 1660617864924643330, NULL, NULL, 1, 8.00);
INSERT INTO `order_detail` VALUES (1663855207357997057, '番茄', '247c3995-9f32-43fc-9569-7dd13bf3e06f.jpg', 1663855207316054018, 1660617379534618626, NULL, NULL, 1, 5.00);
INSERT INTO `order_detail` VALUES (1663856946421190658, '四季豆', 'cbed3853-2f4d-45a6-963a-7bf60e556ad3.jpg', 1663856946421190657, 1660618014178951169, NULL, NULL, 1, 9.00);
INSERT INTO `order_detail` VALUES (1663905763845742595, '苹果', '0d4432c1-2efc-40bd-a32c-f48fb80f5588.jpg', 1663905763845742594, 1663902823265345537, NULL, NULL, 1, 9.00);
INSERT INTO `order_detail` VALUES (1663905763912851457, '空心菜', '2bbe54bb-1895-4467-87a8-45554a42dee2.jpg', 1663905763845742594, 1663900866366717954, NULL, NULL, 1, 2.00);
INSERT INTO `order_detail` VALUES (1663925461337624577, '香瓜茄', '820160a3-19d8-4481-8c46-2bd53a34fef6.jpg', 1663925461274710017, 1663899948481036290, NULL, NULL, 1, 6.00);
INSERT INTO `order_detail` VALUES (1663925461337624578, '茄子', '2e0c0653-933e-4e05-b0d9-8869007fda7c.jpg', 1663925461274710017, 1663899808374505474, NULL, NULL, 1, 3.00);
INSERT INTO `order_detail` VALUES (1663925461337624579, '甜椒', '7f72e12f-f622-4dfc-b3a8-4132a7a0da08.jpg', 1663925461274710017, 1663899571895451649, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (1663925461337624580, '番茄', '247c3995-9f32-43fc-9569-7dd13bf3e06f.jpg', 1663925461274710017, 1660617379534618626, NULL, NULL, 1, 5.00);
INSERT INTO `order_detail` VALUES (1664147067951423490, '香瓜茄', '820160a3-19d8-4481-8c46-2bd53a34fef6.jpg', 1664147067951423489, 1663899948481036290, NULL, NULL, 1, 6.00);
INSERT INTO `order_detail` VALUES (1664147067951423491, '茄子', '2e0c0653-933e-4e05-b0d9-8869007fda7c.jpg', 1664147067951423489, 1663899808374505474, NULL, NULL, 1, 3.00);
INSERT INTO `order_detail` VALUES (1664147324907069442, '香瓜茄', '820160a3-19d8-4481-8c46-2bd53a34fef6.jpg', 1664147324907069441, 1663899948481036290, NULL, NULL, 1, 6.00);

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
INSERT INTO `orders` VALUES (1663849253719584770, '1663849253719584770', 2, 1662450171847495682, 1662744768192454658, '2023-05-31 18:05:50', '2023-05-31 18:05:50', 1, 0, 5.00, '', '13598573736', '[默认]福禄蔬菜店', '用户1428', '11');
INSERT INTO `orders` VALUES (1663849391271784450, '1663849391271784450', 2, 1662450171847495682, 1662744768192454658, '2023-05-31 18:06:22', '2023-05-31 18:06:22', 1, 0, 17.00, '', '13598573736', '[默认]福禄蔬菜店', '用户1428', '11');
INSERT INTO `orders` VALUES (1663851837234659329, '1663851837234659329', 2, 1662450171847495682, 1662744768192454658, '2023-05-31 18:16:06', '2023-05-31 18:16:06', 1, 1663014201209217025, 0.00, '', '13598573736', '[默认]福禄蔬菜店', '用户1428', '11');
INSERT INTO `orders` VALUES (1663855207316054018, '1663855207316054018', 2, 1662450171847495682, 1662744768192454658, '2023-05-31 18:29:29', '2023-05-31 18:29:29', 1, 1663014201209217025, 0.00, '', '13598573736', '[默认]福禄蔬菜店', '用户1428', '11');
INSERT INTO `orders` VALUES (1663856946421190657, '1663856946421190657', 2, 1662450171847495682, 1662744768192454658, '2023-05-31 18:36:24', '2023-05-31 18:36:24', 1, 0, 9.00, '', '13598573736', '[默认]福禄蔬菜店', '用户1428', '11');
INSERT INTO `orders` VALUES (1663905763845742594, '1663905763845742594', 2, 1662450171847495682, 1662744768192454658, '2023-05-31 21:50:23', '2023-05-31 21:50:23', 1, 0, 11.00, '', '13598573736', '[默认]福禄蔬菜店', '用户1428', '11');
INSERT INTO `orders` VALUES (1663925461274710017, '1663925461274710017', 2, 1662450171847495682, 1662744768192454658, '2023-05-31 23:08:39', '2023-05-31 23:08:39', 1, 1663014201209217025, 0.00, '', '13598573736', '[默认]福禄蔬菜店', '用户1428', '11');
INSERT INTO `orders` VALUES (1664147067951423489, '1664147067951423489', 2, 1663887502152310785, 1664147019171667969, '2023-06-01 13:49:14', '2023-06-01 13:49:14', 1, 1663014201209217025, 0.00, '', '13598573731', '[默认]福禄蔬菜店', '用户1995', '福禄蔬菜店');
INSERT INTO `orders` VALUES (1664147324907069441, '1664147324907069441', 2, 1664147221026742273, 1664147265624776705, '2023-06-01 13:50:15', '2023-06-01 13:50:15', 1, 0, 6.00, '', '13598573732', '[默认]福禄蔬菜店', '用户3674', '福禄蔬菜店');

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
-- Table structure for setmeal_dish_copy1
-- ----------------------------
DROP TABLE IF EXISTS `setmeal_dish_copy1`;
CREATE TABLE `setmeal_dish_copy1`  (
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
-- Records of setmeal_dish_copy1
-- ----------------------------

-- ----------------------------
-- Table structure for setmeal_dish_copy2
-- ----------------------------
DROP TABLE IF EXISTS `setmeal_dish_copy2`;
CREATE TABLE `setmeal_dish_copy2`  (
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
-- Records of setmeal_dish_copy2
-- ----------------------------

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
INSERT INTO `shopping_cart` VALUES (1663031459893481474, '番茄', '247c3995-9f32-43fc-9569-7dd13bf3e06f.jpg', 1, 1660617379534618626, NULL, NULL, 1, 5.00, '2023-05-29 11:56:12');
INSERT INTO `shopping_cart` VALUES (1663847303573680130, '辣椒', 'ac8d05eb-3995-450d-afca-ca2ce931f0d3.jpg', 1, 1660617491816136706, NULL, NULL, 1, 6.00, '2023-05-31 17:58:05');
INSERT INTO `shopping_cart` VALUES (1663902192748249089, '竹笋', 'fa87e41f-72d6-4733-9c77-10af8af79401.jpg', 1, 1663901198039695362, NULL, NULL, 1, 5.00, '2023-05-31 21:36:11');
INSERT INTO `shopping_cart` VALUES (1663924699928842242, '杨桃', 'd1cdd1bf-f2f4-46ad-b54c-41ba3ab97619.jpg', 1, 1663904163991396353, NULL, NULL, 1, 20.00, '2023-05-31 23:05:37');
INSERT INTO `shopping_cart` VALUES (1663924714030092290, '香蕉', 'ae26b635-583e-4f00-830f-0c1a313ff576.jpg', 1, 1663904030373453825, NULL, NULL, 1, 15.00, '2023-05-31 23:05:41');
INSERT INTO `shopping_cart` VALUES (1664146818331615234, '香瓜茄', '820160a3-19d8-4481-8c46-2bd53a34fef6.jpg', 1662450171847495682, 1663899948481036290, NULL, NULL, 1, 6.00, '2023-06-01 13:48:14');
INSERT INTO `shopping_cart` VALUES (1664146821833859074, '茄子', '2e0c0653-933e-4e05-b0d9-8869007fda7c.jpg', 1662450171847495682, 1663899808374505474, NULL, NULL, 1, 3.00, '2023-06-01 13:48:15');
INSERT INTO `shopping_cart` VALUES (1664147087215861761, '茄子', '2e0c0653-933e-4e05-b0d9-8869007fda7c.jpg', 1663887502152310785, 1663899808374505474, NULL, NULL, 1, 3.00, '2023-06-01 13:49:19');

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
INSERT INTO `user` VALUES (1663887502152310785, '用户1995', '13598573731', NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (1664147221026742273, '用户3674', '13598573732', NULL, NULL, NULL, 1);

-- ----------------------------
-- Table structure for voucher
-- ----------------------------
DROP TABLE IF EXISTS `voucher`;
CREATE TABLE `voucher`  (
                            `id` bigint NOT NULL,
                            `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '（名称）\r\n',
                            `price` decimal(10, 0) NOT NULL COMMENT '（金额）\n',
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
INSERT INTO `voucher` VALUES (1663014201209217025, '6.18大促销', 30, 'd4690bc9-8d0f-4347-9204-7f9470d4d86a.jpeg', '6.18大促销，快来抢购了', NULL, 1, '2023-05-29 10:47:38', '2023-06-01 13:53:05', 1, 1);
INSERT INTO `voucher` VALUES (1663015441292943361, '双11优惠', 100, 'f437e556-d561-4733-80de-b14b38aacbc4.jpeg', '双11优惠，非常划算，速来抢购啦！', NULL, 0, '2023-05-29 10:52:33', '2023-06-01 13:52:55', 1, 1);
INSERT INTO `voucher` VALUES (1664147932250677249, '新用户优惠', 1000, 'd0613057-79f9-4109-b30e-06b90ee47077.jpeg', '新用户优惠', NULL, 1, '2023-06-01 13:52:40', '2023-06-01 13:53:03', 1664147221026742273, 1664147221026742273);

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
INSERT INTO `voucher_user` VALUES (1662450171902021633, 1663014201209217025, 1662450171847495682, 1);
INSERT INTO `voucher_user` VALUES (1662463976786735105, 1663014201209217025, 1662463976728014850, 1);
INSERT INTO `voucher_user` VALUES (1663887502219419650, 1663014201209217025, 1663887502152310785, 1);
INSERT INTO `voucher_user` VALUES (1664147221026742274, 1663014201209217025, 1664147221026742273, 1);

SET FOREIGN_KEY_CHECKS = 1;
