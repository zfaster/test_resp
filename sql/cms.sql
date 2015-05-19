/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : cms

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2015-02-09 14:59:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_article
-- ----------------------------
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article` (
  `articleId` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `content` longtext,
  `source` varchar(255) DEFAULT NULL,
  `keyword` varchar(255) DEFAULT NULL,
  `intro` varchar(2000) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `author` varchar(100) DEFAULT NULL,
  `recommend` tinyint(1) DEFAULT NULL,
  `headline` tinyint(1) DEFAULT NULL,
  `leaveNumber` int(11) DEFAULT NULL,
  `clickNumber` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `deploytime` datetime DEFAULT NULL,
  PRIMARY KEY (`articleId`)
) ENGINE=InnoDB AUTO_INCREMENT=21720 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_attachment
-- ----------------------------
DROP TABLE IF EXISTS `t_attachment`;
CREATE TABLE `t_attachment` (
  `attachId` varchar(36) NOT NULL,
  `articleId` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `contenttype` varchar(155) DEFAULT NULL,
  `uploadtime` datetime DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `content` longtext,
  PRIMARY KEY (`attachId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category` (
  `categoryid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `description` longtext,
  `pcid` int(11) DEFAULT NULL,
  PRIMARY KEY (`categoryid`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_category_article
-- ----------------------------
DROP TABLE IF EXISTS `t_category_article`;
CREATE TABLE `t_category_article` (
  `categoryId` int(11) DEFAULT NULL,
  `articleId` int(11) DEFAULT NULL,
  UNIQUE KEY `channel_article` (`categoryId`,`articleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_chatfile
-- ----------------------------
DROP TABLE IF EXISTS `t_chatfile`;
CREATE TABLE `t_chatfile` (
  `fileid` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `relativePath` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `createrId` int(11) DEFAULT NULL,
  `reciverId` int(11) DEFAULT NULL,
  `contentType` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`fileid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_chatlog
-- ----------------------------
DROP TABLE IF EXISTS `t_chatlog`;
CREATE TABLE `t_chatlog` (
  `logid` varchar(36) NOT NULL,
  `serverId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `begintime` datetime DEFAULT NULL,
  `endtime` datetime DEFAULT NULL,
  PRIMARY KEY (`logid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_message
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message` (
  `msgid` varchar(36) NOT NULL,
  `reciverId` int(11) DEFAULT NULL,
  `senderId` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `sendTime` datetime DEFAULT NULL,
  `flag` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`msgid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_score
-- ----------------------------
DROP TABLE IF EXISTS `t_score`;
CREATE TABLE `t_score` (
  `scoreId` varchar(36) NOT NULL,
  `scoreUser` int(11) DEFAULT NULL,
  `scoreBy` int(11) DEFAULT NULL,
  `scoreTime` datetime DEFAULT NULL,
  PRIMARY KEY (`scoreId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
