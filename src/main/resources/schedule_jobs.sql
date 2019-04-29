/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50724
Source Host           : 118.89.44.24:3306
Source Database       : TestBy_yang

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-03-28 12:50:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for schedule_jobs
-- ----------------------------
DROP TABLE IF EXISTS `schedule_jobs`;
CREATE TABLE `schedule_jobs` (
  `id` varchar(255) NOT NULL COMMENT 'id',
  `job_time` varchar(255) DEFAULT NULL COMMENT '任务时间',
  `job_cron` varchar(255) DEFAULT NULL COMMENT 'job cron表达式',
  `job_group` varchar(255) DEFAULT NULL COMMENT 'job 组',
  `job_name` varchar(255) DEFAULT NULL COMMENT 'job 名称',
  `job_data` varchar(999) DEFAULT NULL COMMENT 'job 数据',
  `job_status` varchar(255) DEFAULT NULL COMMENT 'job 状态 1 运行  2停止',
  `ts` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  `job_weekend` varchar(255) DEFAULT NULL COMMENT '周末  1做  2不做',
  `job_holiday` varchar(255) DEFAULT NULL COMMENT '节假日 1 做  2 不做',
  `job_day` varchar(255) DEFAULT NULL COMMENT '哪天不做 json数组["2019-03-28"]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of schedule_jobs
-- ----------------------------
INSERT INTO `schedule_jobs` VALUES ('5db2569a-5ad3-4ae4-9e31-0ce66c87eca7', '2019-03-14 20:00:45', '*/8 * * * * ?', 'T', 'T1', 'i am is t1', '2', '2019-03-28 10:24:44', '1', '1', null);
INSERT INTO `schedule_jobs` VALUES ('6d3f5efc-2e6e-404e-8385-bf35cb1a39e7', '2019-03-14 20:01:29', '*/8 * * * * ?', 'T', 'T2', 'i am is  T2', '2', '2019-03-28 10:24:53', '1', '1', null);
INSERT INTO `schedule_jobs` VALUES ('a3007c2c-5c43-4fc9-93be-fa023eed17dd', '2019-03-14 19:52:11', '*/8 * * * * ?', 'T', 'push1', '{\"touser\":[\"aa\",\"bb\"],\"title\":\"testone\",\"type\":\"3\",\"content\":\"test tow\",\"url\":\"https://baidu.com\"}', '1', '2019-03-28 11:52:01', '1', '1', null);
INSERT INTO `schedule_jobs` VALUES ('ff2cdfb9-fcd3-47b2-8024-99d41cd32b3a', '2019-03-14 19:54:39', '*/8 * * * * ?', 'T', 'push2', 'i am is t4600000', '2', '2019-03-28 11:40:33', '1', '1', '[\"2019-03-29\"]');
