/*
 Navicat Premium Data Transfer

 Source Server         : mmq
 Source Server Type    : MySQL
 Source Server Version : 50528
 Source Host           : localhost:3306
 Source Schema         : booksharesystem

 Target Server Type    : MySQL
 Target Server Version : 50528
 File Encoding         : 65001

 Date: 25/02/2020 15:07:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `authorityName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sourceUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '资源地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES (1, '查看书籍', 'http://localhost:8080/bookBaseControl/selectBookByName');
INSERT INTO `authority` VALUES (2, '添加书籍', 'http://localhost:8080/bookBaseControl/addBook');
INSERT INTO `authority` VALUES (3, '审核书籍', 'http://localhost:8080/bookBaseControl/auditBookSource');

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `bId` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `bName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '书名',
  `bookPbulish` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '出版社',
  `bookIntroduction` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '书介绍',
  `writer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '作者',
  `bookOtherImportantPath` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '书籍其他重要信息的地址，包括图片信息等等后期使用ngnix反向代理',
  `bookAccount` int(11) DEFAULT 0 COMMENT '书籍数量',
  `bookPrice` int(10) NOT NULL DEFAULT 1 COMMENT '书籍价格',
  PRIMARY KEY (`bId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, '三国演义', '四川大学出版社', '999', '罗贯中', '001', 18, 3);
INSERT INTO `book` VALUES (2, '水浒传', '北京大学出版社', 'MMQ', '施耐庵', '002', 222, 1);
INSERT INTO `book` VALUES (3, '人类历史', 'mmqUniversity', 'Mmq', 'manmeng', '66', 222, 1);
INSERT INTO `book` VALUES (4, 'java', 'MmqUsversity', 'MM', 'manmengqing', '666', 2, 1);
INSERT INTO `book` VALUES (5, 'manzhoudiguo', 'MMQGuservistiy', 'Mmq', '罗贯中', 'yyyyyy', 77, 1);
INSERT INTO `book` VALUES (6, '满蒙清', '中华', 'Mmq', 'MMQ', '满蒙', 22, 1);
INSERT INTO `book` VALUES (7, '满蒙自传', 'MmqPublish', '它是一本关Mmq的书', 'MMQ', '满蒙请贵特殊的地址', 11, 1);
INSERT INTO `book` VALUES (21, '888', '我的出版社MMQ', '我是一个日本人', 'mmqgMCq', '我是存储地址', 0, 1);
INSERT INTO `book` VALUES (22, 'MMQGmmq', '吉林出版社', '这是一个关于mmq的记录以及他的一些重要信息，目前将处于蛰伏阶段', 'mmq', 'http://127.0.0.1:8080/upload', 0, 1);

-- ----------------------------
-- Table structure for booksource
-- ----------------------------
DROP TABLE IF EXISTS `booksource`;
CREATE TABLE `booksource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sourceTime` datetime DEFAULT NULL COMMENT '书籍来源时间',
  `userId` int(11) DEFAULT NULL COMMENT '赠书用户id',
  `bId` int(11) DEFAULT NULL COMMENT '赠送书籍id',
  `boolPass` int(1) NOT NULL DEFAULT 0 COMMENT '书籍是否被审核，默认为零，审核后自动将审核人的id插入',
  `bookAccount` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of booksource
-- ----------------------------
INSERT INTO `booksource` VALUES (17, '2019-12-12 20:50:15', 1, 21, 0, NULL);
INSERT INTO `booksource` VALUES (18, '2020-02-21 09:25:58', 1, 22, 0, 1);

-- ----------------------------
-- Table structure for borringstatus
-- ----------------------------
DROP TABLE IF EXISTS `borringstatus`;
CREATE TABLE `borringstatus`  (
  `bId` int(11) NOT NULL COMMENT '书籍id',
  `userId` int(11) NOT NULL COMMENT '用户id',
  `borrwingStatus` bit(1) NOT NULL DEFAULT b'0' COMMENT '用户借阅状态',
  `loanHour` datetime DEFAULT NULL COMMENT '借阅时间',
  `returnTime` datetime DEFAULT NULL COMMENT '归还时间',
  `sendStatus` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`bId`, `userId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of borringstatus
-- ----------------------------
INSERT INTO `borringstatus` VALUES (1, 1, b'0', '2020-02-24 08:41:42', NULL, b'0');

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `collegeName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '学院名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderTime` datetime DEFAULT NULL COMMENT '订单生成时间',
  `orderStatus` bit(1) DEFAULT NULL COMMENT '订单状态',
  `bId` int(11) DEFAULT NULL COMMENT '外键',
  `userId` int(11) DEFAULT NULL COMMENT '外键 借书人id',
  `distrbutionId` int(11) DEFAULT 1 COMMENT '外键 配送员id',
  `orderBool` int(255) NOT NULL DEFAULT 1,
  `bookAccount` int(255) NOT NULL DEFAULT 0,
  `isPay` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (1, '2020-02-24 08:30:48', b'1', 1, 1, 1, 0, 12, b'1');
INSERT INTO `order` VALUES (2, NULL, b'0', 1, 4, NULL, 1, 12, b'0');
INSERT INTO `order` VALUES (3, NULL, b'0', 1, 1, NULL, 1, 12, b'0');
INSERT INTO `order` VALUES (4, '2020-02-17 12:42:01', b'0', 1, 1, 1, 1, 10, b'0');
INSERT INTO `order` VALUES (5, NULL, b'0', 1, 1, NULL, 1, 1, b'0');
INSERT INTO `order` VALUES (6, NULL, b'0', 2, 1, NULL, 1, 1, b'0');
INSERT INTO `order` VALUES (7, NULL, b'0', 3, 1, NULL, 1, 1, b'0');
INSERT INTO `order` VALUES (8, NULL, b'0', 5, 1, NULL, 1, 1, b'0');

-- ----------------------------
-- Table structure for remak
-- ----------------------------
DROP TABLE IF EXISTS `remak`;
CREATE TABLE `remak`  (
  `id` int(11) NOT NULL,
  `orderId` int(11) DEFAULT NULL COMMENT '外键',
  `userId` int(11) DEFAULT NULL COMMENT '外键 ',
  `bId` int(11) DEFAULT NULL COMMENT '外键',
  `deliveryId` int(11) DEFAULT NULL COMMENT '外键配送员id',
  `administrtorId` int(11) DEFAULT NULL COMMENT '外键 管理员id',
  `userToOrder` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `userToDelivery` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `administrtorToUser` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for remarktobook
-- ----------------------------
DROP TABLE IF EXISTS `remarktobook`;
CREATE TABLE `remarktobook`  (
  `orderId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `bId` int(11) NOT NULL,
  `remarkToBookContent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`orderId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of remarktobook
-- ----------------------------
INSERT INTO `remarktobook` VALUES (1, 1, 1, '77777');

-- ----------------------------
-- Table structure for remarktodeveliver
-- ----------------------------
DROP TABLE IF EXISTS `remarktodeveliver`;
CREATE TABLE `remarktodeveliver`  (
  `orderId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `develiverManId` int(11) NOT NULL,
  `gradeClass` int(255) NOT NULL DEFAULT 0,
  PRIMARY KEY (`orderId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `r_id` int(11) NOT NULL,
  `roleName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '普通用户');
INSERT INTO `role` VALUES (2, '一般管理员');
INSERT INTO `role` VALUES (3, '超级管理员');

-- ----------------------------
-- Table structure for roleauthority
-- ----------------------------
DROP TABLE IF EXISTS `roleauthority`;
CREATE TABLE `roleauthority`  (
  `roleId` int(255) NOT NULL COMMENT '外键',
  `authorityId` int(11) NOT NULL COMMENT '外键',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `cancelTime` datetime DEFAULT NULL COMMENT '取消时间',
  PRIMARY KEY (`authorityId`, `roleId`) USING BTREE,
  INDEX `roleId`(`roleId`) USING BTREE,
  CONSTRAINT `roleauthority_ibfk_2` FOREIGN KEY (`authorityId`) REFERENCES `authority` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `roleauthority_ibfk_3` FOREIGN KEY (`roleId`) REFERENCES `role` (`r_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of roleauthority
-- ----------------------------
INSERT INTO `roleauthority` VALUES (2, 1, '2019-12-03 14:21:31', '2019-12-03 20:21:48');
INSERT INTO `roleauthority` VALUES (2, 2, '2019-12-02 16:22:07', '2019-12-02 20:22:16');
INSERT INTO `roleauthority` VALUES (2, 3, '2019-12-18 19:13:41', '2019-12-02 19:13:45');

-- ----------------------------
-- Table structure for special
-- ----------------------------
DROP TABLE IF EXISTS `special`;
CREATE TABLE `special`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `specialName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '专业名',
  `collegeId` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `studentId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `wechatNumber` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `dirmitoryNumber` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  `integration` int(255) DEFAULT 20,
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `headPortrait` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `deliveManGrade` int(255) DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `roleId`(`roleId`) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `role` (`r_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'mmq', '201731771141', '17746720936', 'manmeng', '555', 2, 42, NULL, NULL, 0);
INSERT INTO `user` VALUES (2, 'mmq王钦', '201731771160', 'manmengqinggui', 'manmengqinggui', '3-324', 2, 34, NULL, NULL, 0);
INSERT INTO `user` VALUES (3, 'mmq宋镜', '201731773259', '17746720986', 'manmengqingguimmq', '3-528', 2, 20, NULL, NULL, 17);
INSERT INTO `user` VALUES (4, '满蒙清', '201731771259', '17746720936', 'manmengqingguimmq', '3-mmq', 2, 20, NULL, NULL, 0);
INSERT INTO `user` VALUES (5, 'manmengqinggui', '201731771259', '17746720936', 'manmengqingguimmq', '3-mmq', 1, 20, NULL, NULL, 0);
INSERT INTO `user` VALUES (6, 'manmengqinggui', '201731771259', '17746720936', 'manmengqingguimmq', '3-mmq', 1, 20, NULL, NULL, 0);
INSERT INTO `user` VALUES (8, 'manmengqinggui', '201731771259', '17746720936', 'manmengqingguimmq', '3-mmq', 1, 20, NULL, NULL, 0);
INSERT INTO `user` VALUES (9, 'manmengqinggui', '201731771259', '17746720936', 'manmengqingguimmq', '3-mmq', 1, 20, NULL, NULL, 0);
INSERT INTO `user` VALUES (10, 'manmengqinggui', '201731771259', '17746720936', 'manmengqingguimmq', '3-mmq', 1, 20, NULL, NULL, 0);
INSERT INTO `user` VALUES (11, 'manmengqinggui', '201731771259', '17746720936', 'manmengqingguimmqs', '3-mmq', 1, 20, NULL, NULL, 0);
INSERT INTO `user` VALUES (12, 'manmengqinggui', '201731771259', '17746720936', 'manmengqingguimmq', '3-mmq', 1, 20, NULL, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
