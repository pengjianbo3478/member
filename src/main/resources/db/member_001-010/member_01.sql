--App版本更新表	locked
CREATE TABLE `app_update` (
  `id` varchar(32) NOT NULL COMMENT '主键ID uuid',
  `version` varchar(10) NOT NULL COMMENT '版本号',
  `client_id` varchar(20) NOT NULL COMMENT '客户端ID gllife.app.ios 苹果客户端\r\ngllife.app.android 安卓客户端',
  `description` varchar(1000) NOT NULL COMMENT '版本描述',
  `operation_type` int(11) NOT NULL COMMENT '升级类型 1非强制 2强制',
  `download_url` varchar(256) NOT NULL COMMENT '下载地址',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `version_num` int(11) NOT NULL COMMENT '数字版本号　规则：小版本号×１００　＋　中版本号×１００００　＋　大版本×１００００００',
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`),
  KEY `version` (`version`),
  KEY `client_id` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--用户表字段更新   locked
ALTER TABLE `user` 
DROP COLUMN `recommend_organ`,
DROP COLUMN `recommend_merchant`,
ADD COLUMN	`recommend_shop_manager` varchar(32) DEFAULT NULL COMMENT '商家店长ID',
ADD COLUMN `recommend_agent_type` varchar(2) DEFAULT NULL COMMENT '推荐机构类型\r\n1：省级运营商\r\n2：市级运营商\r\n3：县级运营商\r\n4：企业协会\r\n5：联盟商家商户\r\n6：员工\r\n7：兼职业务员\r\n8：渠道机构:80001\r\n9：电子商城:50001\r\n10：积分商城:40001\r\n11：政策机构:60001\r\n12：银行机构：70001\r\n13：支付公司\r\n',
ADD COLUMN `recommend_agent_id` varchar(32) DEFAULT NULL COMMENT '推荐商家',
ADD COLUMN `buy_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '记录用户是否发生过交易',

--评论表字段更新	locked
ALTER TABLE `comment`
MODIFY COLUMN `create_time`  datetime NOT NULL COMMENT '创建日期（发表日期）' AFTER `payment_no`,
ADD COLUMN `status`  int NOT NULL DEFAULT 0 COMMENT '评论状态\r\n0默认用户还没有评论\r\n1用户真实评论' AFTER `real_name`,
ADD COLUMN `del_flag`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标志\r\n0可用\r\n1删除' AFTER `status`,
ADD INDEX (`payment_no`),
DROP COLUMN `real_name`;


--用户表字段更新  unlocked
ALTER TABLE `user`
ADD COLUMN `comment_personal_total`  integer NOT NULL DEFAULT 0 COMMENT '个人评论总分' ,
ADD COLUMN `comment_personal_count`  integer NOT NULL DEFAULT 0 COMMENT '个人评论次数' ;


ALTER TABLE `user` ADD COLUMN `id_address`  varchar(100) DEFAULT NULL COMMENT '身份证地址' ;


--获取评论模板 视图增加排序
ALTER 
ALGORITHM=UNDEFINED 
DEFINER=`member_logic`@`%` 
SQL SECURITY DEFINER 
VIEW `v_comment_labels_template` AS 
select `a`.`base_id` AS `base_id`,`b`.`category` AS `category`,`b`.`name` AS `name`,`a`.`industry` AS `industry` from (`comment_labels_template` `a` left join `comment_labels_base` `b` on((`a`.`base_id` = `b`.`id`))) order by a.base_id ;



--新建标志用户开会标记表
CREATE TABLE `external_account` (
  `user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '用户id',
  `external_channel` varchar(32) DEFAULT NULL COMMENT '外部支付通道id(付费通10019999)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_channel_index` (`user_id`,`external_channel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户外部通道开户表'

