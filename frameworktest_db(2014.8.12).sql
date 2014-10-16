/*
MySQL Data Transfer
Source Host: localhost
Source Database: frameworktest_db
Target Host: localhost
Target Database: frameworktest_db
Date: 2014/8/12 21:07:22
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for book_info
-- ----------------------------
CREATE TABLE `book_info` (
  `id` int(11) NOT NULL default '0',
  `author_id` int(11) default NULL,
  `name` varchar(50) default NULL,
  `publish` varchar(50) default NULL,
  `publishtime` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(50) default NULL,
  `pass` varchar(50) default NULL,
  `weight` double default NULL,
  `birth` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', 'YangPeng', '12178', '57.5', '2015-10-12 08:22:12');
