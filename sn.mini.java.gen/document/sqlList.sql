create database if not exists mengyi_common; 

drop table if exists advertisement;
CREATE TABLE `advertisement` (
  `adv_id` bigint(20) NOT NULL,
  `adv_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:手机App启动广告图片',
  `adv_cloud_id` bigint(20) NOT NULL COMMENT '云节点ID',
  `adv_file_url` varchar(255) NOT NULL COMMENT '文件URL',
  PRIMARY KEY (`adv_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists cloud_info;
CREATE TABLE `cloud_info` (
  `cloud_id` bigint(20) NOT NULL COMMENT '云节点ID',
  `cloud_name` varchar(255) NOT NULL COMMENT '云节点名称',
  `cloud_host` varchar(255) NOT NULL COMMENT '云节点域名',
  `cloud_host_cdn` varchar(255) DEFAULT NULL,
  `cloud_code` varchar(255) NOT NULL COMMENT '云节点验证字符串',
  `cloud_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '云节点类型。 0；系统云，1：蒙以云，2：其它云',
  PRIMARY KEY (`cloud_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists email_verify;
CREATE TABLE `email_verify` (
  `email_verify_id` bigint(20) NOT NULL COMMENT '验证码ID',
  `email_verify_val` varchar(100) NOT NULL COMMENT '发送验证码油箱地址',
  `email_verify_code` varchar(10) NOT NULL COMMENT '以送验证码内容',
  `email_verify_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '发送验证码时间',
  `email_verify_count` int(11) NOT NULL DEFAULT '0' COMMENT '已发送次数',
  PRIMARY KEY (`email_verify_id`),
  UNIQUE KEY `UQ_email_verify_val` (`email_verify_val`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists hot_search_info;
CREATE TABLE `hot_search_info` (
  `hot_id` bigint(20) NOT NULL,
  `hot_name` varchar(20) NOT NULL,
  `hot_value` double NOT NULL,
  PRIMARY KEY (`hot_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists init_parameter;
CREATE TABLE `init_parameter` (
  `init_id` int(11) NOT NULL AUTO_INCREMENT,
  `init_name` varchar(100) NOT NULL,
  `init_value` varchar(255) NOT NULL,
  `init_introduction` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`init_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

drop table if exists label_info;
CREATE TABLE `label_info` (
  `label_id` bigint(20) NOT NULL,
  `label_name` varchar(20) NOT NULL,
  `label_value` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`label_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists phone_verify;
CREATE TABLE `phone_verify` (
  `verify_id` bigint(20) NOT NULL COMMENT '验证码ID',
  `verify_phone` varchar(20) NOT NULL COMMENT '发送验证码手机号',
  `verify_code` varchar(10) NOT NULL COMMENT '以送验证码内容',
  `verify_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '发送验证码时间',
  `verify_count` int(11) NOT NULL DEFAULT '0' COMMENT '已发送次数',
  PRIMARY KEY (`verify_id`),
  UNIQUE KEY `UQ_verify_phone` (`verify_phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists queue_in_delete;
CREATE TABLE `queue_in_delete` (
  `queue_in_id` bigint(20) NOT NULL,
  `queue_in_cloud_id` bigint(20) NOT NULL,
  `queue_in_file_url` varchar(255) NOT NULL,
  `queue_in_update` bigint(20) NOT NULL,
  `queue_in_count` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`queue_in_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists queue_out_delete;
CREATE TABLE `queue_out_delete` (
  `queue_out_id` bigint(20) NOT NULL,
  `queue_out_cloud_id` bigint(20) NOT NULL,
  `queue_out_file_url` varchar(255) NOT NULL,
  `queue_out_update` bigint(20) NOT NULL,
  `queue_out_count` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`queue_out_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists region_info;
CREATE TABLE `region_info` (
  `region_id` bigint(20) NOT NULL,
  `region_name` varchar(100) NOT NULL,
  `region_city_name` varchar(20) DEFAULT NULL,
  `region_parent_id` bigint(20) DEFAULT '0',
  `region_id_uri` varchar(100) NOT NULL,
  `region_name_uri` varchar(255) NOT NULL,
  `region_lng` double NOT NULL DEFAULT '0',
  `region_lat` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists report_info;
CREATE TABLE `report_info` (
  `report_id` bigint(20) NOT NULL,
  `report_target_id` bigint(20) NOT NULL COMMENT '举报目标ID',
  `report_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '举报对象0-知识圈, 1:tdy',
  `report_describe` text NOT NULL COMMENT '举报描述',
  `report_cloud_id` bigint(20) DEFAULT '0' COMMENT '附件上传云节点ID',
  `report_image_url` text COMMENT '举报图片-选填，可多选',
  `report_link_list` varchar(255) DEFAULT NULL COMMENT '联系方式',
  `report_time` datetime NOT NULL COMMENT '举报时间',
  PRIMARY KEY (`report_id`),
  KEY `IX_report_target_id` (`report_target_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='举报';

drop table if exists school_info;
CREATE TABLE `school_info` (
  `school_id` bigint(20) NOT NULL COMMENT '学校/单位ID',
  `school_name` varchar(100) NOT NULL COMMENT '学校/单位名称',
  `school_count` int(11) NOT NULL DEFAULT '0' COMMENT '学校/单位使用次数',
  PRIMARY KEY (`school_id`),
  UNIQUE KEY `UQ_school_name` (`school_name`),
  KEY `IX_school_name` (`school_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists send_email_logger;
CREATE TABLE `send_email_logger` (
  `email_logger_id` bigint(20) NOT NULL,
  `email_logger_nick` varchar(100) NOT NULL,
  `email_logger_target` varchar(100) NOT NULL,
  `email_logger_subject` varchar(255) NOT NULL,
  `email_logger_content` text NOT NULL,
  `email_logger_time` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`email_logger_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists send_mobile_logger;
CREATE TABLE `send_mobile_logger` (
  `mobile_logger_id` bigint(20) NOT NULL,
  `mobile_logger_target` varchar(20) NOT NULL,
  `mobile_logger_content` text NOT NULL,
  `mobile_logger_time` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`mobile_logger_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

