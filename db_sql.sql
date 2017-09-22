create database if not exists ding_isv_access default charset utf8 collate utf8_general_ci;

use ding_isv_access;

create table `isv_app` (
  `id` bigint(20) not null auto_increment comment 'pl',
  `gmt_create` datetime not null comment '创建时间',
  `gmt_modified` datetime not null comment '修改时间',
  `suite_key` varchar(128) not null comment '微应用套件key',
  `app_id` bigint(20) not null comment 'appid,此id来自于开发者中心',
  primary key (`id`),
  unique key `u_suite_app` (`suite_key`,`app_id`)
) engine=innodb default charset=utf8 comment='isv创建的app';

create table `isv_biz_lock` (
  `id` bigint(20) unsigned not null auto_increment comment '主键',
  `gmt_create` datetime not null comment '创建时间',
  `gmt_modified` datetime not null comment '修改时间',
  `lock_key` varchar(256) not null comment '锁key',
  `expire` datetime default null comment '过期时间,null表示不过期',
  primary key (`id`),
  unique key `u_lock_key` (`lock_key`(191))
) engine=innodb auto_increment=10 default charset=utf8 comment='db锁';

create table `isv_corp` (
  `id` bigint(20) not null auto_increment comment '主键',
  `gmt_create` datetime not null comment '创建时间',
  `gmt_modified` datetime not null comment '修改时间',
  `corp_id` varchar(128) not null comment '钉钉企业id',
  `invite_code` varchar(64) default null comment '企业邀请码',
  `industry` varchar(256) default null comment '企业所属行业',
  `corp_name` varchar(256) default null comment '企业名称',
  `invite_url` varchar(1024) default null comment '企业邀请链接',
  `corp_logo_url` varchar(1024) default null comment '企业logo链接',
  primary key (`id`),
  unique key `u_corp_id` (`corp_id`)
) engine=innodb auto_increment=59 default charset=utf8 comment='企业信息表';


create table `isv_corp_app` (
  `id` bigint(20) not null auto_increment comment '主键',
  `gmt_create` datetime not null comment '创建时间',
  `gmt_modified` datetime not null comment '修改时间',
  `agent_id` bigint(20) not null comment '钉钉企业使用的微应用id',
  `agent_name` varchar(128) not null comment '钉钉企业使用的微应用名称',
  `logo_url` varchar(1024) default null comment '钉钉企业使用的微应用图标',
  `app_id` bigint(20) not null comment '钉钉企业使用的微应用原始id',
  `corp_id` varchar(128) not null comment '使用微应用的企业id',
  primary key (`id`),
  unique key `u_corp_app` (`corp_id`,`app_id`)
) engine=innodb auto_increment=56 default charset=utf8 comment='企业微应用信息表';


create table `isv_corp_suite_auth` (
  `id` bigint(20) not null auto_increment comment '主键',
  `gmt_create` datetime not null comment '创建时间',
  `gmt_modified` datetime not null comment '修改时间',
  `corp_id` varchar(255) not null comment '企业corpid',
  `suite_key` varchar(100) not null comment '套件key',
  `permanent_code` varchar(255) not null comment '临时授权码或永久授权码value',
  primary key (`id`),
  unique key `u_corp_suite` (`corp_id`(191),`suite_key`)
) engine=innodb auto_increment=47 default charset=utf8 comment='企业对套件的授权记录';


create table `isv_corp_suite_auth_faile` (
  `id` bigint(20) not null auto_increment comment '主键',
  `gmt_create` datetime not null comment '创建时间',
  `gmt_modified` datetime not null comment '修改时间',
  `suite_key` varchar(100) not null comment '套件key',
  `corp_id` varchar(100) not null comment '企业id',
  `faile_info` varchar(256) default null comment '失败信息',
  `auth_faile_type` varchar(128) not null comment '授权失败类型',
  `suite_push_type` varchar(128) not null comment '推送类型',
  primary key (`id`),
  unique key `u_c_s_f_p` (`suite_key`,`corp_id`,`auth_faile_type`,`suite_push_type`)
) engine=innodb auto_increment=36 default charset=utf8 comment='企业对套件的授权失败记录';


create table `isv_corp_suite_callback` (
  `id` bigint(20) unsigned not null auto_increment comment '主键',
  `gmt_create` datetime not null comment '创建时间',
  `gmt_modified` datetime not null comment '修改时间',
  `corp_id` varchar(128) not null comment '企业corpid',
  `suite_key` varchar(128) not null comment '套件key',
  `callback_tag` varchar(1024) not null comment '注册事件tag,json结构存储',
  primary key (`id`)
) engine=innodb default charset=utf8 comment='企业注册回调事件';


create table `isv_corp_suite_jsapi_ticket` (
  `id` bigint(20) not null auto_increment comment '主键',
  `gmt_create` datetime not null comment '创建时间',
  `gmt_modified` datetime not null comment '修改时间',
  `suite_key` varchar(100) not null comment '套件key',
  `corp_id` varchar(100) not null comment '钉钉企业id',
  `corp_jsapi_ticket` varchar(256) not null comment '企业js_ticket',
  `expired_time` datetime not null comment '过期时间',
  primary key (`id`),
  unique key `u_suite_corp` (`suite_key`,`corp_id`)
) engine=innodb auto_increment=12206 default charset=utf8 comment='企业使用jsapi的js ticket表';


create table `isv_corp_token` (
  `id` bigint(20) not null auto_increment comment '主键',
  `gmt_create` datetime not null comment '创建时间',
  `gmt_modified` datetime not null comment '修改时间',
  `suite_key` varchar(100) not null comment '套件key',
  `corp_id` varchar(100) not null comment '钉钉企业id',
  `corp_token` varchar(256) not null comment '企业token',
  `expired_time` datetime not null comment '过期时间',
  primary key (`id`),
  unique key `u_suite_corp` (`suite_key`,`corp_id`)
) engine=innodb auto_increment=1823 default charset=utf8 comment='套件能够访问企业数据的accesstoken';


create table `isv_suite` (
  `id` bigint(20) not null auto_increment comment '主键',
  `gmt_create` datetime not null comment '创建时间',
  `gmt_modified` datetime not null comment '修改时间',
  `suite_name` varchar(255) not null comment '套件名字',
  `suite_key` varchar(100) not null comment 'suite 的唯一key',
  `suite_secret` varchar(256) not null comment 'suite的唯一secrect，与key对应',
  `encoding_aes_key` varchar(256) not null comment '回调信息加解密参数',
  `token` varchar(128) not null comment '已填写用于生成签名和校验毁掉请求的合法性',
  `event_receive_url` varchar(256) not null comment '回调地址',
  primary key (`id`),
  unique key `u_suite_key` (`suite_key`)
) engine=innodb auto_increment=3 default charset=utf8 comment='套件信息表';


create table `isv_suite_ticket` (
  `id` bigint(20) not null auto_increment comment '主键',
  `gmt_create` datetime not null comment '创建时间',
  `gmt_modified` datetime not null comment '修改时间',
  `suite_key` varchar(100) not null comment '套件suitekey',
  `ticket` varchar(100) not null comment '套件ticket',
  primary key (`id`),
  unique key `u_suite_key` (`suite_key`)
) engine=innodb auto_increment=9 default charset=utf8 comment='用于接收推送的套件ticket';


create table `isv_suite_token` (
  `id` bigint(20) not null auto_increment comment '主键',
  `gmt_create` datetime not null comment '创建时间',
  `gmt_modified` datetime not null comment '修改时间',
  `suite_key` varchar(100) not null comment '套件key',
  `suite_token` varchar(256) not null comment '套件token',
  `expired_time` datetime not null comment '过期时间',
  primary key (`id`),
  unique key `u_suite_key` (`suite_key`)
) engine=innodb auto_increment=3 default charset=utf8 comment='套件的accesstoken表';



create table `qrtz_calendars` (
  `sched_name` varchar(64) not null,
  `calendar_name` varchar(64) not null,
  `calendar` blob not null,
  primary key (`sched_name`,`calendar_name`)
) engine=innodb default charset=utf8;



create table `qrtz_job_details` (
  `sched_name` varchar(64) not null,
  `job_name` varchar(64) not null,
  `job_group` varchar(64) not null,
  `description` varchar(250) default null,
  `job_class_name` varchar(250) not null,
  `is_durable` varchar(1) not null,
  `is_nonconcurrent` varchar(1) not null,
  `is_update_data` varchar(1) not null,
  `requests_recovery` varchar(1) not null,
  `job_data` blob,
  primary key (`sched_name`,`job_name`,`job_group`),
  key `idx_qrtz_j_req_recovery` (`sched_name`,`requests_recovery`) using btree,
  key `idx_qrtz_j_grp` (`sched_name`,`job_group`) using btree
) engine=innodb default charset=utf8;


create table `qrtz_triggers` (
  `sched_name` varchar(64) not null,
  `trigger_name` varchar(64) not null,
  `trigger_group` varchar(64) not null,
  `job_name` varchar(64) not null,
  `job_group` varchar(64) not null,
  `description` varchar(250) default null,
  `next_fire_time` bigint(13) default null,
  `prev_fire_time` bigint(13) default null,
  `priority` int(11) default null,
  `trigger_state` varchar(16) not null,
  `trigger_type` varchar(8) not null,
  `start_time` bigint(13) not null,
  `end_time` bigint(13) default null,
  `calendar_name` varchar(64) default null,
  `misfire_instr` smallint(2) default null,
  `job_data` blob,
  primary key (`sched_name`,`trigger_name`,`trigger_group`),
  key `idx_qrtz_t_j` (`sched_name`,`job_name`,`job_group`) using btree,
  key `idx_qrtz_t_jg` (`sched_name`,`job_group`) using btree,
  key `idx_qrtz_t_c` (`sched_name`,`calendar_name`) using btree,
  key `idx_qrtz_t_g` (`sched_name`,`trigger_group`) using btree,
  key `idx_qrtz_t_state` (`sched_name`,`trigger_state`) using btree,
  key `idx_qrtz_t_n_state` (`sched_name`,`trigger_name`,`trigger_group`,`trigger_state`) using btree,
  key `idx_qrtz_t_n_g_state` (`sched_name`,`trigger_group`,`trigger_state`) using btree,
  key `idx_qrtz_t_next_fire_time` (`sched_name`,`next_fire_time`) using btree,
  key `idx_qrtz_t_nft_st` (`sched_name`,`trigger_state`,`next_fire_time`) using btree,
  key `idx_qrtz_t_nft_misfire` (`sched_name`,`misfire_instr`,`next_fire_time`) using btree,
  key `idx_qrtz_t_nft_st_misfire` (`sched_name`,`misfire_instr`,`next_fire_time`,`trigger_state`) using btree,
  key `idx_qrtz_t_nft_st_misfire_grp` (`sched_name`,`misfire_instr`,`next_fire_time`,`trigger_group`,`trigger_state`) using btree,
  constraint `qrtz_triggers_ibfk_1` foreign key (`sched_name`, `job_name`, `job_group`) references `qrtz_job_details` (`sched_name`, `job_name`, `job_group`) on delete no action on update no action
) engine=innodb default charset=utf8;




create table `qrtz_fired_triggers` (
  `sched_name` varchar(64) not null,
  `entry_id` varchar(95) not null,
  `trigger_name` varchar(64) not null,
  `trigger_group` varchar(64) not null,
  `instance_name` varchar(64) not null,
  `fired_time` bigint(13) not null,
  `sched_time` bigint(13) not null,
  `priority` int(11) not null,
  `state` varchar(16) not null,
  `job_name` varchar(64) default null,
  `job_group` varchar(64) default null,
  `is_nonconcurrent` varchar(1) default null,
  `requests_recovery` varchar(1) default null,
  primary key (`sched_name`,`entry_id`),
  key `idx_qrtz_ft_trig_inst_name` (`sched_name`,`instance_name`) using btree,
  key `idx_qrtz_ft_inst_job_req_rcvry` (`sched_name`,`instance_name`,`requests_recovery`) using btree,
  key `idx_qrtz_ft_j_g` (`sched_name`,`job_name`,`job_group`) using btree,
  key `idx_qrtz_ft_jg` (`sched_name`,`job_group`) using btree,
  key `idx_qrtz_ft_t_g` (`sched_name`,`trigger_name`,`trigger_group`) using btree,
  key `idx_qrtz_ft_tg` (`sched_name`,`trigger_group`) using btree
) engine=innodb default charset=utf8;





create table `qrtz_locks` (
  `sched_name` varchar(64) not null,
  `lock_name` varchar(40) not null,
  primary key (`sched_name`,`lock_name`)
) engine=innodb default charset=utf8;


create table `qrtz_paused_trigger_grps` (
  `sched_name` varchar(64) not null,
  `trigger_group` varchar(64) not null,
  primary key (`sched_name`,`trigger_group`)
) engine=innodb default charset=utf8;


create table `qrtz_scheduler_state` (
  `sched_name` varchar(64) not null,
  `instance_name` varchar(64) not null,
  `last_checkin_time` bigint(13) not null,
  `checkin_interval` bigint(13) not null,
  primary key (`sched_name`,`instance_name`)
) engine=innodb default charset=utf8;


create table `qrtz_simple_triggers` (
  `sched_name` varchar(64) not null,
  `trigger_name` varchar(64) not null,
  `trigger_group` varchar(64) not null,
  `repeat_count` bigint(7) not null,
  `repeat_interval` bigint(12) not null,
  `times_triggered` bigint(10) not null,
  primary key (`sched_name`,`trigger_name`,`trigger_group`),
  constraint `qrtz_simple_triggers_ibfk_1` foreign key (`sched_name`, `trigger_name`, `trigger_group`) references `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) on delete no action on update no action
) engine=innodb default charset=utf8;

create table `qrtz_cron_triggers` (
  `sched_name` varchar(64) not null,
  `trigger_name` varchar(64) not null,
  `trigger_group` varchar(64) not null,
  `cron_expression` varchar(120) not null,
  `time_zone_id` varchar(80) default null,
  primary key (`sched_name`,`trigger_name`,`trigger_group`),
  constraint `qrtz_cron_triggers_ibfk_1` foreign key (`sched_name`, `trigger_name`, `trigger_group`) references `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) on delete no action on update no action
) engine=innodb default charset=utf8;


create table `qrtz_blob_triggers` (
  `sched_name` varchar(64) not null,
  `trigger_name` varchar(64) not null,
  `trigger_group` varchar(64) not null,
  `blob_data` blob,
  primary key (`sched_name`,`trigger_name`,`trigger_group`),
  key `sched_name` (`sched_name`,`trigger_name`,`trigger_group`) using btree,
  constraint `qrtz_blob_triggers_ibfk_1` foreign key (`sched_name`, `trigger_name`, `trigger_group`) references `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) on delete no action on update no action
) engine=innodb default charset=utf8;


create table `qrtz_simprop_triggers` (
  `sched_name` varchar(64) not null,
  `trigger_name` varchar(64) not null,
  `trigger_group` varchar(64) not null,
  `str_prop_1` varchar(512) default null,
  `str_prop_2` varchar(512) default null,
  `str_prop_3` varchar(512) default null,
  `int_prop_1` int(11) default null,
  `int_prop_2` int(11) default null,
  `long_prop_1` bigint(20) default null,
  `long_prop_2` bigint(20) default null,
  `dec_prop_1` decimal(13,4) default null,
  `dec_prop_2` decimal(13,4) default null,
  `bool_prop_1` varchar(1) default null,
  `bool_prop_2` varchar(1) default null,
  primary key (`sched_name`,`trigger_name`,`trigger_group`),
  constraint `qrtz_simprop_triggers_ibfk_1` foreign key (`sched_name`, `trigger_name`, `trigger_group`) references `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) on delete no action on update no action
) engine=innodb default charset=utf8;

create table `isv_channel` (
  `id` bigint(20) not null auto_increment comment 'pl',
  `gmt_create` datetime not null comment '创建时间',
  `gmt_modified` datetime not null comment '修改时间',
  `suite_key` varchar(128) not null comment '微应用套件key',
  `app_id` bigint(20) not null comment '服务窗appid,此id来自于开发者中心',
  primary key (`id`),
  unique key `u_suite_app` (`suite_key`,`app_id`) using btree
) engine=innodb auto_increment=2 default charset=utf8 comment='isv创建的服务窗app';


create table `isv_corp_suite_jsapi_channel_ticket` (
  `id` bigint(20) not null auto_increment comment '主键',
  `gmt_create` datetime not null comment '创建时间',
  `gmt_modified` datetime not null comment '修改时间',
  `suite_key` varchar(100) not null comment '套件key',
  `corp_id` varchar(100) not null comment '钉钉企业id',
  `corp_channel_jsapi_ticket` varchar(256) not null comment '企业服务窗js_ticket',
  `expired_time` datetime not null comment '过期时间',
  primary key (`id`),
  unique key `u_suite_corp` (`suite_key`,`corp_id`) using btree
) engine=innodb auto_increment=3 default charset=utf8 comment='企业使用服务窗jsapi的js ticket表';


create table `isv_corp_channel_app` (
  `id` bigint(20) not null auto_increment comment '主键',
  `gmt_create` datetime not null comment '创建时间',
  `gmt_modified` datetime not null comment '修改时间',
  `agent_id` bigint(20) not null comment '钉钉企业使用的服务窗应用id',
  `agent_name` varchar(128) not null comment '钉钉企业使用的服务窗应用名称',
  `logo_url` varchar(1024) default null comment '钉钉企业使用的服务窗应用图标',
  `app_id` bigint(20) not null comment '钉钉企业使用的服务窗应用原始id',
  `corp_id` varchar(128) not null comment '使用微应用的企业id',
  primary key (`id`),
  unique key `u_corp_app` (`corp_id`,`app_id`) using btree
) engine=innodb auto_increment=64 default charset=utf8 comment='企业服务窗应用信息表';

alter table `isv_corp_suite_auth`
modify column `permanent_code`  varchar(255) character set utf8 collate utf8_general_ci null comment '临时授权码或永久授权码value' after `suite_key`,
add column `ch_permanent_code`  varchar(255) null comment '企业服务窗永久授权码' after `permanent_code`;
