/*
 Navicat Premium Data Transfer

 Source Server         : mysql8.0.21
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : biography

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 27/10/2020 14:33:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bio_category
-- ----------------------------
DROP TABLE IF EXISTS `bio_category`;
CREATE TABLE `bio_category`
(
    `id`            bigint UNSIGNED                                        NOT NULL COMMENT '类别id',
    `category_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别名称',
    `is_deleted`    tinyint UNSIGNED                                       NOT NULL DEFAULT 0 COMMENT '0-未删除，1-已删除',
    `gmt_create`    datetime(0)                                            NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '记录创建时间',
    `gmt_modified`  datetime(0)                                            NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '记录修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `bio_category_category_name_uindex` (`category_name`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bio_category
-- ----------------------------

-- ----------------------------
-- Table structure for bio_comment
-- ----------------------------
DROP TABLE IF EXISTS `bio_comment`;
CREATE TABLE `bio_comment`
(
    `id`                 bigint UNSIGNED                                         NOT NULL COMMENT '评论id',
    `bio_id`             bigint UNSIGNED                                         NOT NULL COMMENT '自传id',
    `commentator`        varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '评论者的昵称',
    `email`              varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '评论者邮箱',
    `comment_body`       varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论的内容',
    `gmt_comment_create` datetime(0)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '评论创建的时间',
    `comment_status`     tinyint UNSIGNED                                        NOT NULL DEFAULT 0 COMMENT '是否审核通过 0-未审核 1-审核通过',
    `reply_body`         varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '回复内容',
    `gmt_reply_create`   datetime(0)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '回复时间',
    `is_deleted`         tinyint UNSIGNED                                        NOT NULL DEFAULT 0 COMMENT '0-未删除，1-已删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_bio_comment_bio_idx` (`bio_id`) USING BTREE,
    CONSTRAINT `fk_bio_comment_bio` FOREIGN KEY (`bio_id`) REFERENCES `biography` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bio_comment
-- ----------------------------

-- ----------------------------
-- Table structure for bio_tag
-- ----------------------------
DROP TABLE IF EXISTS `bio_tag`;
CREATE TABLE `bio_tag`
(
    `id`           bigint UNSIGNED                                        NOT NULL COMMENT '标签的id',
    `tag_name`     varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `is_deleted`   tinyint UNSIGNED                                       NOT NULL DEFAULT 0 COMMENT '0-未删除，1-已删除',
    `gmt_create`   datetime(0)                                            NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '记录创建时间',
    `gmt_modified` datetime(0)                                            NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '记录修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `tag)name_UNIQUE` (`tag_name`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bio_tag
-- ----------------------------

-- ----------------------------
-- Table structure for bio_tag_relation
-- ----------------------------
DROP TABLE IF EXISTS `bio_tag_relation`;
CREATE TABLE `bio_tag_relation`
(
    `bio_id`       bigint UNSIGNED NOT NULL COMMENT '自传id',
    `tag_id`       bigint UNSIGNED NOT NULL COMMENT '标签id',
    `gmt_create`   datetime(0)     NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '记录创建时间',
    `gmt_modified` datetime(0)     NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '记录修改时间',
    PRIMARY KEY (`bio_id`, `tag_id`) USING BTREE,
    INDEX `fk_bio_tag_tag_idx` (`tag_id`) USING BTREE,
    CONSTRAINT `fk_bio_tag_bio` FOREIGN KEY (`bio_id`) REFERENCES `biography` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_bio_tag_tag` FOREIGN KEY (`tag_id`) REFERENCES `bio_tag` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bio_tag_relation
-- ----------------------------

-- ----------------------------
-- Table structure for biography
-- ----------------------------
DROP TABLE IF EXISTS `biography`;
CREATE TABLE `biography`
(
    `id`             bigint UNSIGNED                                         NOT NULL COMMENT '自传id',
    `title`          varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
    `content`        mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL COMMENT '正文',
    `category_id`    bigint UNSIGNED                                         NOT NULL COMMENT '类别id',
    `category_name`  varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '自传所属类别名称(冗余字段)',
    `pen_name`       varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '笔名',
    `privacy_level`  tinyint UNSIGNED                                        NOT NULL DEFAULT 0 COMMENT '0-公开，1-私密',
    `status`         tinyint UNSIGNED                                        NOT NULL DEFAULT 0 COMMENT '0-草稿，1-发布',
    `views`          bigint UNSIGNED                                         NOT NULL DEFAULT 0 COMMENT '阅读量',
    `enable_comment` tinyint UNSIGNED                                        NOT NULL DEFAULT 0 COMMENT '0-允许评论，1-不允许评论',
    `note`           varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '备注',
    `is_deleted`     tinyint UNSIGNED                                        NOT NULL DEFAULT 0 COMMENT '0-未删除，1-已删除',
    `gmt_create`     datetime(0)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '记录创建时间',
    `gmt_modified`   datetime(0)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '记录修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_biography_category_idx` (`category_id`) USING BTREE,
    CONSTRAINT `fk_biography_category` FOREIGN KEY (`category_id`) REFERENCES `bio_category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of biography
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`           bigint UNSIGNED                                        NOT NULL COMMENT '角色id',
    `role_name`    varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
    `is_deleted`   tinyint UNSIGNED                                       NOT NULL DEFAULT 0 COMMENT '0-未删除，1-已删除',
    `gmt_create`   datetime(0)                                            NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '记录创建时间',
    `gmt_modified` datetime(0)                                            NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '记录修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `role_name_UNIQUE` (`role_name`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role`
VALUES (1, 'ROLE_ADMIN', 0, '2020-10-26 21:09:32', '2020-10-26 21:09:32');
INSERT INTO `role`
VALUES (2, 'ROLE_USER', 0, '2020-10-26 21:09:53', '2020-10-26 21:09:53');
INSERT INTO `role`
VALUES (3, 'ROLE_COMPANY', 0, '2020-10-26 21:10:07', '2020-10-26 21:10:07');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`              bigint UNSIGNED                                         NOT NULL COMMENT '用户id',
    `username`        varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户名',
    `password`        varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
    `email`           varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '邮箱',
    `invitation_code` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '邀请码',
    `points`          int UNSIGNED                                            NOT NULL DEFAULT 0 COMMENT '积分',
    `is_locked`       tinyint UNSIGNED                                        NOT NULL DEFAULT 0 COMMENT '0-未锁定，1-已锁定',
    `gmt_create`      datetime(0)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '记录创建时间',
    `gmt_modified`    datetime(0)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '记录修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `username_UNIQUE` (`username`) USING BTREE,
    UNIQUE INDEX `email_UNIQUE` (`email`) USING BTREE,
    UNIQUE INDEX `invitation_code_UNIQUE` (`invitation_code`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for user_active
-- ----------------------------
DROP TABLE IF EXISTS `user_active`;
CREATE TABLE `user_active`
(
    `id`             bigint UNSIGNED NOT NULL COMMENT '用户活跃表id',
    `user_id`        bigint UNSIGNED NOT NULL COMMENT '关联用户表id',
    `bio_num`        int UNSIGNED    NOT NULL DEFAULT 0 COMMENT '发表自传数量',
    `comment_num`    int UNSIGNED    NOT NULL DEFAULT 0 COMMENT '评论数量',
    `Invitation_num` int UNSIGNED    NOT NULL DEFAULT 0 COMMENT '邀请数量',
    `gmt_create`     datetime(0)     NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '记录创建时间',
    `gmt_modified`   datetime(0)     NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '记录修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `user_active_user_idx` (`user_id`) USING BTREE,
    CONSTRAINT `user_active_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_active
-- ----------------------------

-- ----------------------------
-- Table structure for user_active_token
-- ----------------------------
DROP TABLE IF EXISTS `user_active_token`;
CREATE TABLE `user_active_token`
(
    `id`          bigint UNSIGNED                                         NOT NULL COMMENT '激活用户的id',
    `expiry_date` datetime(0)                                             NOT NULL COMMENT '过期时间',
    `token`       varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `user_id`     bigint UNSIGNED                                         NOT NULL COMMENT '关联用户表id',
    `gmt_create`  datetime(0)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '记录创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `account_active_token_user_idx` (`user_id`) USING BTREE,
    CONSTRAINT `account_active_token_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_active_token
-- ----------------------------

-- ----------------------------
-- Table structure for user_friend_relation
-- ----------------------------
DROP TABLE IF EXISTS `user_friend_relation`;
CREATE TABLE `user_friend_relation`
(
    `user_id`      bigint UNSIGNED                                                                   NOT NULL COMMENT '关联的用户表id',
    `friend_id`    bigint UNSIGNED                                                                   NOT NULL COMMENT '用户朋友id',
    `user_group`   enum ('GROUP_FRIEND','GROUP_RELATIVE') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户分组',
    `friend_group` enum ('GROUP_FRIEND','GROUP_RELATIVE') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '朋友分组',
    `is_deleted`   tinyint UNSIGNED                                                                  NOT NULL DEFAULT 0 COMMENT '0-未删除，1-已删除',
    `gmt_create`   datetime(0)                                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '记录创建时间',
    `gmt_modified` datetime(0)                                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '记录修改时间',
    PRIMARY KEY (`user_id`, `friend_id`) USING BTREE,
    INDEX `fk_user_friend_user_friend_idx` (`friend_id`) USING BTREE,
    CONSTRAINT `fk_user_friend_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_user_friend_user_friend` FOREIGN KEY (`friend_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_friend_relation
-- ----------------------------

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`
(
    `id`           bigint UNSIGNED                                         NOT NULL COMMENT '用户信息id',
    `user_id`      bigint UNSIGNED                                         NOT NULL COMMENT '关联的用户id',
    `birthday`     date                                                    NULL     DEFAULT NULL COMMENT '生日',
    `organization` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '所属组织',
    `hobby`        varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '爱好',
    `gender`       tinyint UNSIGNED                                        NOT NULL DEFAULT 0 COMMENT '0-未设置，1-男，2-女',
    `tel`          varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '电话',
    `address`      varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '地址',
    `gmt_create`   datetime(0)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '记录创建时间',
    `gmt_modified` datetime(0)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '记录修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `user_id_UNIQUE` (`user_id`) USING BTREE,
    CONSTRAINT `fk_user_info_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`
(
    `user_id`      bigint UNSIGNED NOT NULL COMMENT '用户角色关联表用户id',
    `role_id`      bigint UNSIGNED NOT NULL COMMENT '用户角色关联表角色id',
    `gmt_create`   datetime(0)     NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '记录创建时间',
    `gmt_modified` datetime(0)     NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '记录修改时间',
    PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
    INDEX `fk_user_role_role_idx` (`role_id`) USING BTREE,
    CONSTRAINT `fk_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
