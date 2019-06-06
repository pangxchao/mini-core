create database if not exists mengyi_group; 

drop table if exists group_enter_info;
CREATE TABLE `group_enter_info` (
  `group_id` bigint(20) NOT NULL COMMENT '集团ID',
  `group_name` varchar(20) NOT NULL COMMENT '集团名称',
  `group_address` varchar(100) DEFAULT NULL COMMENT '集团地址',
  `group_zipcode` varchar(10) DEFAULT NULL COMMENT '集团邮编',
  `group_linkman` varchar(10) DEFAULT NULL COMMENT '集团联系人',
  `group_phone` varchar(20) DEFAULT NULL COMMENT '集团联系电话',
  `group_email` varchar(50) DEFAULT NULL COMMENT '集团联系邮箱地址',
  `group_job` varchar(50) DEFAULT NULL COMMENT '集团联系人职位',
  `group_auth_num` int(11) NOT NULL DEFAULT '0' COMMENT '集团购买用户数',
  `group_give_num` int(11) NOT NULL DEFAULT '0' COMMENT '赠送用户数',
  `group_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '集团拥有者ID',
  `group_buy_time` bigint(20) DEFAULT NULL COMMENT '购买时间',
  `group_expired` bigint(20) DEFAULT NULL COMMENT '到期时间',
  `group_expired_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '将VIP时间写入用户数据',
  `group_cloud_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '集团专属云节点',
  `group_tool_num` int(11) NOT NULL DEFAULT '0' COMMENT '集团使用制作工具数量',
  `group_is_join` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否台允许加入',
  `group_join_verify` tinyint(4) NOT NULL DEFAULT '1' COMMENT '加入时是否需要验证',
  `group_promise` varchar(255) DEFAULT NULL COMMENT '集团承诺书附件地址',
  `group_promise_cloud_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '承诺书附件所有云节点ID',
  `group_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态, 0-未上传承诺书,1-已上传承诺书,2-承诺书已审核通过,3-承诺书审核未通过',
  `group_update_time` timestamp NOT NULL DEFAULT '2019-01-01 00:00:00' COMMENT '最后修改时间',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

drop table if exists group_enter_user;
CREATE TABLE `group_enter_user` (
  `ug_id` bigint(20) NOT NULL,
  `ug_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `ug_group_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '集团ID',
  `ug_identity` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-申请加入，1-普通用户，2-管理员',
  `ug_use_tool` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1-可以使用制作工具，0-不可以使用制作工具',
  `ug_create` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建日期',
  PRIMARY KEY (`ug_id`),
  UNIQUE KEY `UQ_group_enter_user` (`ug_user_id`,`ug_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists group_gen_info;
CREATE TABLE `group_gen_info` (
  `gen_id` bigint(20) NOT NULL,
  `gen_group_id` bigint(20) NOT NULL,
  `gen_start` int(11) NOT NULL DEFAULT '0',
  `gen_number` int(11) NOT NULL DEFAULT '0',
  `gen_next` int(11) NOT NULL DEFAULT '0',
  `gen_prefix` varchar(20) NOT NULL,
  `gen_create` bigint(20) NOT NULL,
  PRIMARY KEY (`gen_id`),
  KEY `IX_gen_group_id` (`gen_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into group_enter_info(group_id, group_name, group_address, group_zipcode, group_linkman, group_phone, group_email, group_job, group_auth_num, group_give_num, group_user_id, group_buy_time, group_expired, group_expired_type, group_cloud_id, group_tool_num, group_is_join, group_join_verify, group_promise, group_promise_cloud_id, group_status, group_update_time)
 values('359318568388526083', '测试集团', '1111222', null, '1111', '13200000001', null, null, '10', '5', '329476854563996172', '1537200000000', '1538236800000', '0', '0', '0', '1', '0', null, '0', '0', '2019-01-01 00:00:00.0');

insert into group_enter_user(ug_id, ug_user_id, ug_group_id, ug_identity, ug_use_tool, ug_create)
 values('359318676547043843', '329480881800478988', '359318568388526083', '1', '1', '1537274648307');

insert into group_gen_info(gen_id, gen_group_id, gen_start, gen_number, gen_next, gen_prefix, gen_create)
 values('359318676547044099', '359318568388526083', '1', '1', '2', 'cs_', '1537274648307');

