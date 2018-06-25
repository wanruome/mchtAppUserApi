/*
MySQL Backup
Source Server Version: 5.5.25
Source Database: webauth
Date: 2018/6/25 16:45:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Table structure for `tbl_app_version`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_app_version`;
CREATE TABLE `tbl_app_version` (
  `APP_NAME` varchar(32) NOT NULL,
  `APP_TYPE` varchar(16) NOT NULL DEFAULT '',
  `APP_VERSION` varchar(32) DEFAULT NULL,
  `DOWN_RUL` varchar(256) DEFAULT NULL,
  `UPDATE_STATUS` int(1) DEFAULT NULL,
  `UPDATE_TIME` varchar(19) DEFAULT NULL,
  `CREATE_TIME` varchar(19) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`APP_NAME`,`APP_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_feedback`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_feedback`;
CREATE TABLE `tbl_feedback` (
  `FEEDBACK_ID` varchar(16) NOT NULL,
  `USER_ID` varchar(16) DEFAULT NULL,
  `UUID` varchar(64) DEFAULT NULL,
  `APP_ID` varchar(16) DEFAULT NULL,
  `CONTACT` varchar(64) DEFAULT NULL,
  `FEEDBACK_TITLE` varchar(64) DEFAULT NULL,
  `FEEDBACK_CONTENT` varchar(1024) DEFAULT NULL,
  `STATUS` int(1) DEFAULT NULL,
  `CREATE_TIME` varchar(19) DEFAULT NULL,
  `UPDATE_TIME` varchar(19) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`FEEDBACK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_login_app_info`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_login_app_info`;
CREATE TABLE `tbl_login_app_info` (
  `APP_ID` varchar(16) NOT NULL COMMENT '应用编码',
  `APP_NAME` varchar(64) DEFAULT NULL COMMENT '应用名称',
  `APP_PWD` varchar(64) DEFAULT NULL COMMENT '应用密码',
  `TERM_LIMIT` int(3) DEFAULT NULL COMMENT '总登录限制',
  `TERM_ANDROID_LIMIT` int(2) DEFAULT NULL COMMENT 'Android登录限制',
  `TERM_IPHONE_LIMIT` int(2) DEFAULT NULL COMMENT 'IPHONE登录限制',
  `TERM_WEB_LIMIT` int(2) DEFAULT NULL COMMENT 'WEB登录限制',
  `NEW_KILL_OUT` int(1) DEFAULT NULL COMMENT '新登录踢出老的登录',
  `NOTIFY_URL` varchar(256) DEFAULT NULL COMMENT '通知地址',
  `PUBLIC_KEY` varchar(512) DEFAULT NULL COMMENT 'RSA1024公钥',
  `STATUS` int(1) DEFAULT NULL COMMENT '状态。',
  `CREATE_TIME` varchar(19) DEFAULT NULL COMMENT '创建时间',
  `VERSION` int(11) DEFAULT NULL COMMENT '更新版本控制',
  PRIMARY KEY (`APP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_login_user_account`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_login_user_account`;
CREATE TABLE `tbl_login_user_account` (
  `LOGIN_ID` varchar(16) NOT NULL COMMENT '用户id',
  `LOGIN_NAME` varchar(32) DEFAULT NULL COMMENT '用户名称',
  `LOGIN_MOBILE` varchar(16) DEFAULT NULL COMMENT '用户手机号',
  `LOGIN_EMAIL` varchar(64) DEFAULT NULL,
  `LOGIN_PWD` varchar(64) DEFAULT NULL COMMENT '用户密码',
  `STATUS` int(1) DEFAULT NULL COMMENT '状态',
  `PWD_ERR_COUNT` int(11) DEFAULT NULL COMMENT '密码错误次数',
  `PWD_ERR_TIME` varchar(19) DEFAULT NULL,
  `LAST_AUTH_UUID` varchar(64) DEFAULT NULL COMMENT '最后授权的设备ID',
  `LAST_AUTH_TIME` varchar(19) DEFAULT NULL COMMENT '最后授权的时间',
  `REGISTER_TIME` varchar(19) DEFAULT NULL COMMENT '注册时间',
  `UPDATE_TIME` varchar(19) DEFAULT NULL COMMENT '更新时间',
  `VERSION` int(11) DEFAULT NULL COMMENT '更新版本控制',
  `IDCARD_NAME` varchar(64) DEFAULT NULL,
  `IDCARD_NO` varchar(64) DEFAULT NULL,
  `NICK_NAME` varchar(32) DEFAULT NULL,
  `HEAD_IMG` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`LOGIN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_login_user_token`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_login_user_token`;
CREATE TABLE `tbl_login_user_token` (
  `TOKEN_ID` varchar(16) NOT NULL DEFAULT '' COMMENT 'TokenId',
  `USER_ID` varchar(16) NOT NULL COMMENT '用户ID',
  `APP_ID` varchar(16) NOT NULL COMMENT '应用ID',
  `UUID` varchar(64) DEFAULT NULL COMMENT '设备号UUID',
  `TERM_TYPE` int(2) NOT NULL COMMENT '终端类型',
  `TOKEN` varchar(64) DEFAULT NULL COMMENT 'Token值',
  `VALID_TIME` varchar(19) DEFAULT NULL COMMENT '有效时间',
  `LOGIN_STATUS` int(1) DEFAULT NULL COMMENT '登录状态',
  `CREATE_TIME` varchar(19) DEFAULT NULL COMMENT '创建时间',
  `VERSION` int(11) DEFAULT NULL COMMENT '更新版本控制',
  PRIMARY KEY (`TOKEN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_manager_token`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_manager_token`;
CREATE TABLE `tbl_manager_token` (
  `MANAGER` varchar(255) NOT NULL DEFAULT '',
  `AUTH_TOKEN` varchar(32) DEFAULT '',
  `CREATE_TIME` varchar(19) DEFAULT NULL,
  `UPDATE_TIME` varchar(19) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`MANAGER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_msg_auth_info`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_msg_auth_info`;
CREATE TABLE `tbl_msg_auth_info` (
  `UUID` varchar(64) NOT NULL DEFAULT '' COMMENT '设备号或用户号',
  `FUNCTION_ID` varchar(20) NOT NULL DEFAULT '' COMMENT '功能ID',
  `MSG_ADDR` varchar(64) NOT NULL DEFAULT '' COMMENT '发送地址',
  `FUNCTION_TO_ID` varchar(20) DEFAULT NULL COMMENT '授权功能ID',
  `SESSION_TOKEN_ID` varchar(64) DEFAULT NULL COMMENT '登录用户的Token值',
  `MSG_CODE` varchar(16) DEFAULT NULL COMMENT '验证码',
  `MSG_VALID_TIME` varchar(19) DEFAULT NULL COMMENT '有效时间',
  `MSG_STATUS` int(1) DEFAULT NULL COMMENT '有效状态',
  `MSG_TOKEN` varchar(64) DEFAULT NULL COMMENT '授权的TOKEN值',
  `VERISON` int(11) DEFAULT NULL COMMENT '更新版本控制',
  PRIMARY KEY (`UUID`,`FUNCTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_msg_funtion_info`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_msg_funtion_info`;
CREATE TABLE `tbl_msg_funtion_info` (
  `FUNCTION_ID` varchar(16) NOT NULL DEFAULT '' COMMENT '功能ID',
  `FUNCTION_NAME` varchar(32) DEFAULT NULL COMMENT '功能名称',
  `AUTH_TYPE` int(1) DEFAULT NULL COMMENT '授权类型',
  `NEED_MSGTOKEN` int(1) DEFAULT NULL COMMENT '往不同手机邮箱上发送，是否需要本账号的授权码',
  `VERIFY_FIELD_NAME` varchar(20) DEFAULT NULL COMMENT '验证码校验字段，若是不填写则不校验，填写则需要和特定请求字段校验',
  `MAPPING` varchar(64) DEFAULT NULL COMMENT '验证码使用的Mapping请求地址',
  `TEMPLATE` varchar(512) DEFAULT NULL COMMENT '信息模板',
  `STATUS` int(1) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`FUNCTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_msg_send_info`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_msg_send_info`;
CREATE TABLE `tbl_msg_send_info` (
  `MSG_ID` varchar(16) NOT NULL DEFAULT '' COMMENT '短信ID',
  `USER_ID` varchar(16) DEFAULT NULL COMMENT '用户ID',
  `UUID` varchar(64) DEFAULT NULL COMMENT '设备UUID',
  `FUNCTION_ID` varchar(16) DEFAULT NULL COMMENT '功能ID',
  `MSG_TYPE` int(11) DEFAULT NULL COMMENT '短信类型',
  `MSG_TOKEN` varchar(64) DEFAULT NULL COMMENT '授权TOKEN',
  `MSG_ADDR` varchar(64) DEFAULT NULL COMMENT '发送地址',
  `MSG_CODE` varchar(16) DEFAULT NULL COMMENT '验证码',
  `MSG_CONTENT` varchar(256) DEFAULT NULL COMMENT '信息内容',
  `MSG_VALID_TIME` varchar(19) DEFAULT NULL COMMENT '有效时间',
  `MSG_STATUS` int(1) DEFAULT NULL COMMENT '状态',
  `CREATE_DATE` varchar(12) DEFAULT NULL COMMENT '创建日期',
  `CREATE_TIME` varchar(19) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`MSG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_repayment_bankcard`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_repayment_bankcard`;
CREATE TABLE `tbl_repayment_bankcard` (
  `CARD_INDEX` varchar(16) NOT NULL DEFAULT '',
  `USER_ID` varchar(64) DEFAULT NULL,
  `BANKCARD_NO` varchar(128) DEFAULT NULL,
  `BANKCARD_MOBILE` varchar(128) DEFAULT NULL,
  `BANKCARD_IDCARD` varchar(128) DEFAULT NULL,
  `BANKCARD_NAME` varchar(128) DEFAULT NULL,
  `AREA` varchar(64) DEFAULT NULL,
  `RESPONESE_CODE` varchar(6) DEFAULT NULL,
  `RESPONSE_REMARK` varchar(64) DEFAULT NULL,
  `BANKCARD_TYPE` varchar(6) DEFAULT NULL,
  `BANKNAME` varchar(128) DEFAULT NULL,
  `BIND_STATUS` int(1) DEFAULT NULL COMMENT '1.绑定 2.解绑',
  `CARD_FINGER` varchar(32) DEFAULT NULL,
  `CREATE_TIME` varchar(19) DEFAULT NULL,
  `UPDATE_TIME` varchar(19) DEFAULT NULL,
  `VERSION` int(1) DEFAULT NULL,
  PRIMARY KEY (`CARD_INDEX`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_repayment_bankcard_temp`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_repayment_bankcard_temp`;
CREATE TABLE `tbl_repayment_bankcard_temp` (
  `SEQUENCE_NO` varchar(16) NOT NULL DEFAULT '',
  `USER_ID` varchar(64) DEFAULT NULL,
  `BANKCARD_NO` varchar(128) DEFAULT NULL,
  `BANKCARD_MOBILE` varchar(128) DEFAULT NULL,
  `BANKCARD_IDCARD` varchar(128) DEFAULT NULL,
  `BANKCARD_NAME` varchar(128) DEFAULT NULL,
  `AREA` varchar(64) DEFAULT NULL,
  `RESPONESE_CODE` varchar(6) DEFAULT NULL,
  `RESPONSE_REMARK` varchar(64) DEFAULT NULL,
  `BANKCARD_TYPE` varchar(6) DEFAULT NULL,
  `BANKNAME` varchar(128) DEFAULT NULL,
  `STATUS` int(1) DEFAULT NULL COMMENT '0.用户递交了请求 1.服务器返回了正确的结果 2.返回了错误的结果',
  `CREATE_TIME` varchar(19) DEFAULT NULL,
  `UPDATE_TIME` varchar(19) DEFAULT NULL,
  PRIMARY KEY (`SEQUENCE_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_repayment_citys`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_repayment_citys`;
CREATE TABLE `tbl_repayment_citys` (
  `ID` varchar(255) NOT NULL,
  `PROVINCE` varchar(32) NOT NULL,
  `CITY` varchar(64) NOT NULL,
  `CODE` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_repayment_payinfo`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_repayment_payinfo`;
CREATE TABLE `tbl_repayment_payinfo` (
  `USER_ID` varchar(16) NOT NULL,
  `PAY_PWD` varchar(256) DEFAULT NULL,
  `PWD_ERR_COUNT` int(11) DEFAULT NULL,
  `PWD_ERR_TIME` varchar(19) DEFAULT NULL,
  `NO_PWD_FLAG` int(1) DEFAULT NULL,
  `NO_PWD_AMOUNT` int(11) DEFAULT NULL,
  `LIMIT_AMOUNT` int(11) DEFAULT NULL,
  `UPDATE_TIME` varchar(19) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_system_config`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_system_config`;
CREATE TABLE `tbl_system_config` (
  `CONFIG_KEY` varchar(32) NOT NULL,
  `CONFIG_VAL` varchar(256) DEFAULT NULL,
  `REMARK` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CONFIG_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_system_log`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_system_log`;
CREATE TABLE `tbl_system_log` (
  `LOG_ID` varchar(16) NOT NULL,
  `USER_ID` varchar(32) DEFAULT NULL,
  `LOG_KEY_VALUE` varchar(64) DEFAULT NULL,
  `UUID` varchar(64) DEFAULT NULL,
  `APP_ID` varchar(16) DEFAULT NULL,
  `FUNCTION_ID` varchar(64) DEFAULT NULL,
  `FUNCTION_NAME` varchar(64) DEFAULT NULL,
  `REQUEST_INFO` varchar(4096) DEFAULT NULL,
  `RESULT_CODE` varchar(6) DEFAULT NULL,
  `RESULT_MSG` varchar(256) DEFAULT NULL,
  `MAPPING` varchar(256) DEFAULT NULL,
  `EXCUTE_TIME` varchar(10) DEFAULT NULL,
  `CREATE_DATE` varchar(19) DEFAULT NULL,
  `CREATE_TIME` varchar(19) DEFAULT NULL,
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_system_log_function`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_system_log_function`;
CREATE TABLE `tbl_system_log_function` (
  `FUNCTION_ID` varchar(6) NOT NULL DEFAULT '',
  `FUNCTION_NAME` varchar(32) DEFAULT NULL,
  `LOG_KEY_FIELD_NAME` varchar(20) DEFAULT NULL,
  `MAPPING` varchar(256) DEFAULT NULL,
  `REQUEST_LOG` int(1) DEFAULT NULL,
  `STATUS` int(1) DEFAULT NULL,
  PRIMARY KEY (`FUNCTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_system_log_matter`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_system_log_matter`;
CREATE TABLE `tbl_system_log_matter` (
  `LOG_ID` varchar(32) NOT NULL,
  `USER_ID` varchar(32) DEFAULT NULL,
  `LOG_KEY_VALUE` varchar(64) DEFAULT NULL,
  `UUID` varchar(64) DEFAULT NULL,
  `APP_ID` varchar(16) DEFAULT NULL,
  `FUNCTION_ID` varchar(64) DEFAULT NULL,
  `FUNCTION_NAME` varchar(64) DEFAULT NULL,
  `REQUEST_INFO` varchar(4096) DEFAULT NULL,
  `RESULT_CODE` varchar(6) DEFAULT NULL,
  `RESULT_MSG` varchar(256) DEFAULT NULL,
  `MAPPING` varchar(256) DEFAULT NULL,
  `EXCUTE_TIME` varchar(10) DEFAULT NULL,
  `CREATE_DATE` varchar(19) DEFAULT NULL,
  `CREATE_TIME` varchar(19) DEFAULT NULL,
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_util_seq`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_util_seq`;
CREATE TABLE `tbl_util_seq` (
  `SEQ_NAME` varchar(32) NOT NULL DEFAULT '',
  `SEQ_VALUE` int(11) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_uuid_keypair`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_uuid_keypair`;
CREATE TABLE `tbl_uuid_keypair` (
  `UUID` varchar(64) NOT NULL DEFAULT '',
  `KEY_TYPE` varchar(10) NOT NULL DEFAULT '',
  `PUBLIC_KEY` varchar(512) DEFAULT NULL,
  `PRIVATE_KEY` varchar(2048) DEFAULT NULL,
  `KEY_VERSION` varchar(20) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`UUID`,`KEY_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records 
-- ----------------------------
INSERT INTO `tbl_app_version` VALUES ('商户服务APP','1','3.69.3','http://w.test1.com/','1',NULL,NULL,NULL), ('商户服务APP','2','3.69.3','http://w.test1.com/','1','','',NULL), ('商户服务APP','3','3.69.3','http://w.test1.com/','1','','',NULL);
INSERT INTO `tbl_feedback` VALUES ('100000','100002','111122223333444455556666777788889999','1000','62260900000000482','181000000002','5102657901283032','1','20180624205921835','20180624205921835','1'), ('100001','100002','111122223333444455556666777788889999','1000','62260900000000482','181000000002','5102657901283032','1','20180624205926322','20180624205926322','1');
INSERT INTO `tbl_login_app_info` VALUES ('1000','聚钱包','550e1bafe077ff0b0b67f4e32f29d751','1','1','1','1','1','','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCburMmm/OZD1xO4dwLZK5Mon65SWze66Y5z30fxy1M8lIUrR5s3ZzO7YJfjRRZQdCjrwRENhqe0aBdmYypN/tocHZ5nvL9HHZ1fmb7X2j1PGgAP559XVAOlh2waQwPkXn5vAUXSkFoRRNlBaWAocrmLPyi5ZdgxmUXE6jspG4EaQIDAQAB','1','20180619110302','1');
INSERT INTO `tbl_login_user_account` VALUES ('100002',NULL,'13355667777','yangmi@126.com','0e1BFrW/fWq0TD1b21uD6HSIkjFn1gGDGES4i+mRsxkDW+/8PRBdLQ==','1','0','none','111122223333444455556666777788889999','20180624172108440','20180622153151543','20180622161256520','47',NULL,NULL,NULL,NULL), ('100003',NULL,'13355667799','yang@126.com','0e1BFrW/fWq0TD1b21uD6HSIkjFn1gGDGES4i+mRsxkDW+/8PRBdLQ==','1','0','none','111122223333444455556666777788889999','20180625155053393','20180625154651702','20180625164223582','6','DKJaHaFBfXs=','2kOL/O7kVYkDAuFP6abHpadJdKVsWOiX','yang','http://www.baidu.com/');
INSERT INTO `tbl_login_user_token` VALUES ('100000','100002','1000','1000_100002_111122223333444455556666777788889999','1','RlExJaXAzBqtoUoD','20180627172108464','1','20180622154023298','24'), ('100001','100003','1000','1000_100003_111122223333444455556666777788889999','1','FbybowrddhePYHpp','20180628155053435','1','20180625155053435','1');
INSERT INTO `tbl_manager_token` VALUES ('sysreload','GDXugPawrEREgjTl','20180625164201177','20180625164215566','2');
INSERT INTO `tbl_msg_auth_info` VALUES ('100002','4','13355667777',NULL,'100000','54091940','20180624160134791','1',NULL,'8'), ('100002','9','13355667777',NULL,'100000','48809484','20180624125249503','0',NULL,'2'), ('111122223333444455556666777788889999','1','13355667799',NULL,NULL,'60760159','20180625160042061','0',NULL,'8'), ('111122223333444455556666777788889999','2','13355667777',NULL,NULL,'32456606','20180629162625279','1',NULL,'3'), ('111122223333444455556666777788889999','3','13355667777',NULL,NULL,'32456606','20180629162625279','0',NULL,'4');
INSERT INTO `tbl_msg_funtion_info` VALUES ('1','注册用户','0','0','account','/app/userAccount/doRegister','亲爱的用户，{appName}欢迎您注册。您本次的验证码为:{msgCode}，验证码有效期为{msgValidTime}。','1'), ('2','找回密码','0','0','account','/app/userAccount/doFindPwd','{appName}提醒您正在{msgType}找回密码。您本次的验证码为:{msgCode}，验证码有效期为{msgValidTime}。','1'), ('3','登录操作','0','0','account','/app/userAccount/doLogin','亲爱的用户，{appName}欢迎您登录。您本次的验证码为:{msgCode}，验证码有效期为{msgValidTime}。','1'), ('4','修改密码','1','0',NULL,'/app/userAccount/doModifyPwd','{appName}提醒您正在{msgType}修改密码。您本次的验证码为:{msgCode}，验证码有效期为{msgValidTime}。','1'), ('5','修改手机号','1','1','newMobile','/app/userAccount/doModifyMobile','{appName}提醒您正在{msgTypeEmail}修改手机号。您本次的验证码为:{msgCode}，验证码有效期为{msgValidTime}。','1'), ('6','修改邮箱','1','0',NULL,'/app/userAccount/doModifyEmail','{appName}提醒您正在{msgTypeMobile}修改邮箱。您本次的验证码为:{msgCode}，验证码有效期为{msgValidTime}。','1'), ('7','修改用户名','1','1',NULL,'/app/userAccount/doModifyName','{appName}提醒您正在通过{msgType}修改用户名。您本次的验证码为:{msgCode}，验证码有效期为{msgValidTime}。','1'), ('8','修改支付密码','1','0',NULL,'/app/repaymentPayInfo/doModifyPayPwd','{appName}提醒您正在{msgType}修改支付密码。您本次的验证码为:{msgCode}，验证码有效期为{msgValidTime}。','1'), ('9','找回支付密码','1','0',NULL,'/app/repaymentPayInfo/doFindPayPwd','{appName}提醒您正在{msgType}找回支付密码。您本次的验证码为:{msgCode}，验证码有效期为{msgValidTime}。','1'), ('99','执行变更操作','2','0',NULL,'/app/msg/doGetMsgToken','{appName}提醒您正在执行{msgFunction}操作。您本次的验证码为:{msgCode}，验证码有效期为{msgValidTime}。','1');
INSERT INTO `tbl_msg_send_info` VALUES ('100000',NULL,'111122223333444455556666777788889999','1','1',NULL,'13355667777','70952126','亲爱的用户，浙江盛炬支付欢迎你注册。您本次的验证码为:70952126，验证码有效期为16分钟。','20180622153308371','0','20180622','20180622151708371'), ('100001','100002','111122223333444455556666777788889999','4','1',NULL,'13355667777','54091940','浙江盛炬支付提醒您正在通过手机修改密码。您本次的验证码为:54091940，验证码有效期为16分钟。','20180622160134791','0','20180622','20180622154534791'), ('100002',NULL,'111122223333444455556666777788889999','2','1',NULL,'13355667777','32456606','浙江盛炬支付提醒您正在通过手机找回密码。您本次的验证码为:32456606，验证码有效期为16分钟。','20180622162625279','0','20180622','20180622161025279'), ('100003','100002','111122223333444455556666777788889999','9','1',NULL,'13355667777','48809484','浙江盛炬支付提醒您正在通过手机找回支付密码。您本次的验证码为:48809484，验证码有效期为16分钟。','20180624125249503','0','20180624','20180624123649503'), ('100004',NULL,'111122223333444455556666777788889999','1','1',NULL,'13355667799','60760159','亲爱的用户，浙江盛炬支付欢迎您注册。您本次的验证码为:60760159，验证码有效期为16分钟。','20180625160042061','0','20180625','20180625154442061');
INSERT INTO `tbl_repayment_bankcard` VALUES ('100010','100002','zcLCnrPKHmcMJx4ku7PwbJW5YymEhPTf','UCXaDcgNYhH2BIMYp31X0w==','Wx5wPHQGQaSULNoO8fcUlw==','suZWepuqcYs=','河南省-洛阳市','0000','成功','01','招商银行','1',NULL,NULL,'20180622165013668','3'), ('100014','100002','zcLCnrPKHmcMJx4ku7PwbNrYCz7FABuq','UCXaDcgNYhHZKgD8ZNIeIA==','Wx5wPHQGQaTMmG7Kriqs7JW5YymEhPTf','5J5cloD15MY=','河南省-洛阳市1','0000','成功','01','招商银行','1','ff5571e07c827e20796e6c54f830f0fa','20180622170217423','20180622170217423','2'), ('100015','100002','zcLCnrPKHmcMJx4ku7PwbLyuwT/bVIiS','UCXaDcgNYhGo0QISkekRFQ==','Wx5wPHQGQaTBYjgbYjyRzpW5YymEhPTf','FwxC+odmV2s=','河南省-洛阳市2','0000','成功','01','招商银行','1','dbe46f4a92cbc6cbd232c9dd9fe379be','20180622170313309','20180622170313309','1');
INSERT INTO `tbl_repayment_bankcard_temp` VALUES ('000000000123','100002','zcLCnrPKHmcMJx4ku7PwbLyuwT/bVIiS','UCXaDcgNYhGo0QISkekRFQ==','Wx5wPHQGQaTBYjgbYjyRzpW5YymEhPTf','FwxC+odmV2s=','河南省-洛阳市2','0000','成功','02','','1','20180622170103277','20180622170103277'), ('00001','100002','zcLCnrPKHmcMJx4ku7PwbJW5YymEhPTf','UCXaDcgNYhH2BIMYp31X0w==','Wx5wPHQGQaSULNoO8fcUlw==','suZWepuqcYs=','河南省-洛阳市','E000','银行卡号已绑定','01','招商银行','1','20180622162447303','20180622162447303'), ('100001','100002','zcLCnrPKHmcMJx4ku7PwbJW5YymEhPTf','UCXaDcgNYhH2BIMYp31X0w==','Wx5wPHQGQaSULNoO8fcUlw==','suZWepuqcYs=','河南省-洛阳市','0000','成功','01','招商银行','1','20180622162601023','20180622162601023'), ('100002','100002','zcLCnrPKHmcMJx4ku7PwbJW5YymEhPTf','UCXaDcgNYhH2BIMYp31X0w==','Wx5wPHQGQaSULNoO8fcUlw==','suZWepuqcYs=','河南省-洛阳市','0000','成功','01','招商银行','1','20180622162628541','20180622162628541'), ('100003','100002','zcLCnrPKHmcMJx4ku7PwbJW5YymEhPTf','UCXaDcgNYhH2BIMYp31X0w==','Wx5wPHQGQaSULNoO8fcUlw==','suZWepuqcYs=','河南省-洛阳市','0000','成功','01','招商银行','1','20180622162704366','20180622162704366'), ('100005','100002','zcLCnrPKHmcMJx4ku7PwbJW5YymEhPTf','UCXaDcgNYhH2BIMYp31X0w==','Wx5wPHQGQaSULNoO8fcUlw==','suZWepuqcYs=','河南省-洛阳市','0000','成功','01','招商银行','1','20180622163711157','20180622163711157'), ('100007','100002','zcLCnrPKHmcMJx4ku7PwbJW5YymEhPTf','UCXaDcgNYhH2BIMYp31X0w==','Wx5wPHQGQaSULNoO8fcUlw==','suZWepuqcYs=','河南省-洛阳市','0000','成功','01','招商银行','1','20180622164214735','20180622164214735'), ('100008','100002','zcLCnrPKHmcMJx4ku7PwbJW5YymEhPTf','UCXaDcgNYhH2BIMYp31X0w==','Wx5wPHQGQaSULNoO8fcUlw==','suZWepuqcYs=','河南省-洛阳市','0000','成功','01','招商银行','1','20180622164456367','20180622164456367'), ('100011','100002','zcLCnrPKHmcMJx4ku7PwbJW5YymEhPTf','UCXaDcgNYhH2BIMYp31X0w==','Wx5wPHQGQaSULNoO8fcUlw==','suZWepuqcYs=','河南省-洛阳市','0000','成功','01','招商银行','1','20180622164926447','20180622164926447'), ('123','100002','zcLCnrPKHmcMJx4ku7PwbNrYCz7FABuq','UCXaDcgNYhHZKgD8ZNIeIA==','Wx5wPHQGQaTMmG7Kriqs7JW5YymEhPTf','5J5cloD15MY=','河南省-洛阳市1','0000','成功','02','','1','20180622170036472','20180622170036472');
INSERT INTO `tbl_repayment_citys` VALUES ('001','﻿北京','北京市',NULL), ('002','上海','上海市',NULL), ('003','天津','天津市',NULL), ('004','重庆','重庆市',NULL), ('005','安徽省','安庆市',NULL), ('006','安徽省','蚌埠市',NULL), ('007','安徽省','亳州市',NULL), ('008','安徽省','巢湖市',NULL), ('009','安徽省','池州市',NULL), ('010','安徽省','滁州市',NULL), ('011','安徽省','阜阳市',NULL), ('012','安徽省','合肥市',NULL), ('013','安徽省','淮北市',NULL), ('014','安徽省','淮南市',NULL), ('015','安徽省','黄山市',NULL), ('016','安徽省','六安市',NULL), ('017','安徽省','马鞍山市',NULL), ('018','安徽省','宿州市',NULL), ('019','安徽省','铜陵市',NULL), ('020','安徽省','芜湖市',NULL), ('021','安徽省','宣城市',NULL), ('022','澳门特别行政区','澳门半岛',NULL), ('023','澳门特别行政区','澳门离岛市',NULL), ('024','福建省','福州市',NULL), ('025','福建省','龙岩市',NULL), ('026','福建省','南平市',NULL), ('027','福建省','宁德市',NULL), ('028','福建省','莆田市',NULL), ('029','福建省','泉州市',NULL), ('030','福建省','三明市',NULL), ('031','福建省','厦门市',NULL), ('032','福建省','漳州市',NULL), ('033','甘肃省','白银市',NULL), ('034','甘肃省','定西市',NULL), ('035','甘肃省','甘南藏族自治州',NULL), ('036','甘肃省','嘉峪关市',NULL), ('037','甘肃省','金昌市',NULL), ('038','甘肃省','酒泉市',NULL), ('039','甘肃省','兰州市',NULL), ('040','甘肃省','临夏回族自治州',NULL), ('041','甘肃省','陇南市',NULL), ('042','甘肃省','平凉市',NULL), ('043','甘肃省','庆阳市',NULL), ('044','甘肃省','天水市',NULL), ('045','甘肃省','武威市',NULL), ('046','甘肃省','张掖市',NULL), ('047','广东省','深圳市',NULL), ('048','广东省','广州市',NULL), ('049','广东省','东莞市',NULL), ('050','广东省','佛山市',NULL), ('051','广东省','潮州市',NULL), ('052','广东省','河源市',NULL), ('053','广东省','惠州市',NULL), ('054','广东省','江门市',NULL), ('055','广东省','揭阳市',NULL), ('056','广东省','茂名市',NULL), ('057','广东省','梅州市',NULL), ('058','广东省','清远市',NULL), ('059','广东省','汕头市',NULL), ('060','广东省','汕尾市',NULL), ('061','广东省','韶关市',NULL), ('062','广东省','阳江市',NULL), ('063','广东省','云浮市',NULL), ('064','广东省','湛江市',NULL), ('065','广东省','肇庆市',NULL), ('066','广东省','中山市',NULL), ('067','广东省','珠海市',NULL), ('068','广西壮族自治区','百色市',NULL), ('069','广西壮族自治区','北海市',NULL), ('070','广西壮族自治区','崇左市',NULL), ('071','广西壮族自治区','防城港市',NULL), ('072','广西壮族自治区','贵港市',NULL), ('073','广西壮族自治区','桂林市',NULL), ('074','广西壮族自治区','河池市',NULL), ('075','广西壮族自治区','贺州市',NULL), ('076','广西壮族自治区','来宾市',NULL), ('077','广西壮族自治区','柳州市',NULL), ('078','广西壮族自治区','南宁市',NULL), ('079','广西壮族自治区','钦州市',NULL), ('080','广西壮族自治区','梧州市',NULL), ('081','广西壮族自治区','玉林市',NULL), ('082','贵州省','安顺市',NULL), ('083','贵州省','毕节地区',NULL), ('084','贵州省','贵阳市',NULL), ('085','贵州省','六盘水市',NULL), ('086','贵州省','黔东南苗族侗族自治州',NULL), ('087','贵州省','黔南布依族苗族自治州',NULL), ('088','贵州省','黔西南布依族苗族自治州',NULL), ('089','贵州省','铜仁地区',NULL), ('090','贵州省','遵义市',NULL), ('091','海南省','白沙黎族自治县',NULL), ('092','海南省','保亭黎族苗族自治县',NULL), ('093','海南省','澄迈县',NULL), ('094','海南省','昌江黎族自治县',NULL), ('095','海南省','儋州市',NULL), ('096','海南省','定安县',NULL), ('097','海南省','东方市',NULL), ('098','海南省','海口市',NULL), ('099','海南省','乐东黎族自治县',NULL), ('100','海南省','临高县',NULL);
INSERT INTO `tbl_repayment_citys` VALUES ('101','海南省','陵水黎族自治县',NULL), ('102','海南省','南沙群岛',NULL), ('103','海南省','琼海市',NULL), ('104','海南省','琼中黎族苗族自治县',NULL), ('105','海南省','三亚市',NULL), ('106','海南省','屯昌县',NULL), ('107','海南省','万宁市',NULL), ('108','海南省','文昌市',NULL), ('109','海南省','五指山市',NULL), ('110','海南省','西沙群岛',NULL), ('111','海南省','中沙群岛的岛礁及其海域',NULL), ('112','河北省','保定市',NULL), ('113','河北省','沧州市',NULL), ('114','河北省','承德市',NULL), ('115','河北省','邯郸市',NULL), ('116','河北省','衡水市',NULL), ('117','河北省','廊坊市',NULL), ('118','河北省','秦皇岛市',NULL), ('119','河北省','石家庄市',NULL), ('120','河北省','唐山市',NULL), ('121','河北省','邢台市',NULL), ('122','河北省','张家口市',NULL), ('123','河南省','安阳市',NULL), ('124','河南省','鹤壁市',NULL), ('125','河南省','焦作市',NULL), ('126','河南省','开封市',NULL), ('127','河南省','漯河市',NULL), ('128','河南省','洛阳市',NULL), ('129','河南省','南阳市',NULL), ('130','河南省','平顶山市',NULL), ('131','河南省','濮阳市',NULL), ('132','河南省','三门峡市',NULL), ('133','河南省','商丘市',NULL), ('134','河南省','新乡市',NULL), ('135','河南省','信阳市',NULL), ('136','河南省','许昌市',NULL), ('137','河南省','郑州市',NULL), ('138','河南省','周口市',NULL), ('139','河南省','驻马店市',NULL), ('140','黑龙江省','大庆市',NULL), ('141','黑龙江省','大兴安岭地区',NULL), ('142','黑龙江省','哈尔滨市',NULL), ('143','黑龙江省','鹤岗市',NULL), ('144','黑龙江省','黑河市',NULL), ('145','黑龙江省','佳木斯市',NULL), ('146','黑龙江省','鸡西市',NULL), ('147','黑龙江省','牡丹江市',NULL), ('148','黑龙江省','齐齐哈尔市',NULL), ('149','黑龙江省','七台河市',NULL), ('150','黑龙江省','双鸭山市',NULL), ('151','黑龙江省','绥化市',NULL), ('152','黑龙江省','伊春市',NULL), ('153','湖北省','武汉市',NULL), ('154','湖北省','恩施土家族苗族自治州',NULL), ('155','湖北省','鄂州市',NULL), ('156','湖北省','黄冈市',NULL), ('157','湖北省','黄石市',NULL), ('158','湖北省','荆门市',NULL), ('159','湖北省','荆州市',NULL), ('160','湖北省','潜江市',NULL), ('161','湖北省','神农架林区',NULL), ('162','湖北省','十堰市',NULL), ('163','湖北省','随州市',NULL), ('164','湖北省','天门市',NULL), ('165','湖北省','襄阳市',NULL), ('166','湖北省','咸宁市',NULL), ('167','湖北省','仙桃市',NULL), ('168','湖北省','孝感市',NULL), ('169','湖北省','宜昌市',NULL), ('170','湖南省','常德市',NULL), ('171','湖南省','长沙市',NULL), ('172','湖南省','郴州市',NULL), ('173','湖南省','衡阳市',NULL), ('174','湖南省','怀化市',NULL), ('175','湖南省','娄底市',NULL), ('176','湖南省','邵阳市',NULL), ('177','湖南省','湘潭市',NULL), ('178','湖南省','湘西土家族苗族自治州',NULL), ('179','湖南省','益阳市',NULL), ('180','湖南省','永州市',NULL), ('181','湖南省','岳阳市',NULL), ('182','湖南省','张家界市',NULL), ('183','湖南省','株洲市',NULL), ('184','吉林省','白城市',NULL), ('185','吉林省','白山市',NULL), ('186','吉林省','长春市',NULL), ('187','吉林省','吉林市',NULL), ('188','吉林省','辽源市',NULL), ('189','吉林省','四平市',NULL), ('190','吉林省','松原市',NULL), ('191','吉林省','通化市',NULL), ('192','吉林省','延边朝鲜族自治州',NULL), ('193','江苏省','苏州市',NULL), ('194','江苏省','常州市',NULL), ('195','江苏省','淮安市',NULL), ('196','江苏省','连云港市',NULL), ('197','江苏省','南京市',NULL), ('198','江苏省','南通市',NULL), ('199','江苏省','宿迁市',NULL), ('200','江苏省','泰州市',NULL);
INSERT INTO `tbl_repayment_citys` VALUES ('201','江苏省','无锡市',NULL), ('202','江苏省','徐州市',NULL), ('203','江苏省','盐城市',NULL), ('204','江苏省','扬州市',NULL), ('205','江苏省','镇江市',NULL), ('206','江西省','南昌市',NULL), ('207','江西省','抚州市',NULL), ('208','江西省','赣州市',NULL), ('209','江西省','吉安市',NULL), ('210','江西省','景德镇市',NULL), ('211','江西省','九江市',NULL), ('212','江西省','萍乡市',NULL), ('213','江西省','上饶市',NULL), ('214','江西省','新余市',NULL), ('215','江西省','宜春市',NULL), ('216','江西省','鹰潭市',NULL), ('217','辽宁省','沈阳市',NULL), ('218','辽宁省','鞍山市',NULL), ('219','辽宁省','本溪市',NULL), ('220','辽宁省','朝阳市',NULL), ('221','辽宁省','大连市',NULL), ('222','辽宁省','丹东市',NULL), ('223','辽宁省','抚顺市',NULL), ('224','辽宁省','阜新市',NULL), ('225','辽宁省','葫芦岛市',NULL), ('226','辽宁省','锦州市',NULL), ('227','辽宁省','辽阳市',NULL), ('228','辽宁省','盘锦市',NULL), ('229','辽宁省','铁岭市',NULL), ('230','辽宁省','营口市',NULL), ('231','内蒙古自治区','阿拉善盟',NULL), ('232','内蒙古自治区','包头市',NULL), ('233','内蒙古自治区','巴彦淖尔市',NULL), ('234','内蒙古自治区','赤峰市',NULL), ('235','内蒙古自治区','鄂尔多斯市',NULL), ('236','内蒙古自治区','呼和浩特市',NULL), ('237','内蒙古自治区','呼伦贝尔市',NULL), ('238','内蒙古自治区','通辽市',NULL), ('239','内蒙古自治区','乌海市',NULL), ('240','内蒙古自治区','乌兰察布市',NULL), ('241','内蒙古自治区','锡林郭勒盟',NULL), ('242','内蒙古自治区','兴安盟',NULL), ('243','宁夏回族自治区','固原市',NULL), ('244','宁夏回族自治区','石嘴山市',NULL), ('245','宁夏回族自治区','吴忠市',NULL), ('246','宁夏回族自治区','银川市',NULL), ('247','宁夏回族自治区','中卫市',NULL), ('248','青海省','果洛藏族自治州',NULL), ('249','青海省','海北藏族自治州',NULL), ('250','青海省','海东地区',NULL), ('251','青海省','海南藏族自治州',NULL), ('252','青海省','海西蒙古族藏族自治州',NULL), ('253','青海省','黄南藏族自治州',NULL), ('254','青海省','西宁市',NULL), ('255','青海省','玉树藏族自治州',NULL), ('256','山东省','青岛市',NULL), ('257','山东省','滨州市',NULL), ('258','山东省','德州市',NULL), ('259','山东省','东营市',NULL), ('260','山东省','菏泽市',NULL), ('261','山东省','济南市',NULL), ('262','山东省','济宁市',NULL), ('263','山东省','莱芜市',NULL), ('264','山东省','聊城市',NULL), ('265','山东省','临沂市',NULL), ('266','山东省','日照市',NULL), ('267','山东省','泰安市',NULL), ('268','山东省','潍坊市',NULL), ('269','山东省','威海市',NULL), ('270','山东省','烟台市',NULL), ('271','山东省','枣庄市',NULL), ('272','山东省','淄博市',NULL), ('273','山西省','长治市',NULL), ('274','山西省','大同市',NULL), ('275','山西省','晋城市',NULL), ('276','山西省','晋中市',NULL), ('277','山西省','临汾市',NULL), ('278','山西省','吕梁市',NULL), ('279','山西省','朔州市',NULL), ('280','山西省','太原市',NULL), ('281','山西省','忻州市',NULL), ('282','山西省','阳泉市',NULL), ('283','山西省','运城市',NULL), ('284','陕西省','安康市',NULL), ('285','陕西省','宝鸡市',NULL), ('286','陕西省','汉中市',NULL), ('287','陕西省','商洛市',NULL), ('288','陕西省','铜川市',NULL), ('289','陕西省','渭南市',NULL), ('290','陕西省','西安市',NULL), ('291','陕西省','咸阳市',NULL), ('292','陕西省','延安市',NULL), ('293','陕西省','榆林市',NULL), ('294','四川省','成都市',NULL), ('295','四川省','阿坝藏族羌族自治州',NULL), ('296','四川省','巴中市',NULL), ('297','四川省','达州市',NULL), ('298','四川省','德阳市',NULL), ('299','四川省','甘孜藏族自治州',NULL), ('300','四川省','广安市',NULL);
INSERT INTO `tbl_repayment_citys` VALUES ('301','四川省','广元市',NULL), ('302','四川省','乐山市',NULL), ('303','四川省','凉山彝族自治州',NULL), ('304','四川省','泸州市',NULL), ('305','四川省','眉山市',NULL), ('306','四川省','绵阳市',NULL), ('307','四川省','南充市',NULL), ('308','四川省','内江市',NULL), ('309','四川省','攀枝花市',NULL), ('310','四川省','遂宁市',NULL), ('311','四川省','雅安市',NULL), ('312','四川省','宜宾市',NULL), ('313','四川省','自贡市',NULL), ('314','四川省','资阳市',NULL), ('315','台湾省','高雄市',NULL), ('316','台湾省','高雄县市',NULL), ('317','台湾省','花莲县',NULL), ('318','台湾省','嘉义市',NULL), ('319','台湾省','嘉义县',NULL), ('320','台湾省','基隆市',NULL), ('321','台湾省','金门县',NULL), ('322','台湾省','连江县市',NULL), ('323','台湾省','苗栗县',NULL), ('324','台湾省','南投县',NULL), ('325','台湾省','澎湖县',NULL), ('326','台湾省','屏东县',NULL), ('327','台湾省','台北市',NULL), ('328','台湾省','台北县市',NULL), ('329','台湾省','台东县',NULL), ('330','台湾省','台南市',NULL), ('331','台湾省','台南县市',NULL), ('332','台湾省','台中市',NULL), ('333','台湾省','台中县市',NULL), ('334','台湾省','桃园县',NULL), ('335','台湾省','新竹市',NULL), ('336','台湾省','新竹县',NULL), ('337','台湾省','宜兰县',NULL), ('338','台湾省','云林县',NULL), ('339','台湾省','彰化县',NULL), ('340','西藏自治区','阿里地区',NULL), ('341','西藏自治区','昌都地区',NULL), ('342','西藏自治区','拉萨市',NULL), ('343','西藏自治区','林芝地区',NULL), ('344','西藏自治区','那曲地区',NULL), ('345','西藏自治区','日喀则地区',NULL), ('346','西藏自治区','山南地区',NULL), ('347','香港特别行政区','九龙',NULL), ('348','香港特别行政区','香港岛',NULL), ('349','香港特别行政区','新界',NULL), ('350','新疆维吾尔自治区','阿克苏地区',NULL), ('351','新疆维吾尔自治区','阿拉尔市',NULL), ('352','新疆维吾尔自治区','阿勒泰地区',NULL), ('353','新疆维吾尔自治区','巴音郭楞蒙古自治州',NULL), ('354','新疆维吾尔自治区','博尔塔拉蒙古自治州',NULL), ('355','新疆维吾尔自治区','昌吉回族自治州',NULL), ('356','新疆维吾尔自治区','哈密地区',NULL), ('357','新疆维吾尔自治区','和田地区',NULL), ('358','新疆维吾尔自治区','喀什地区',NULL), ('359','新疆维吾尔自治区','克拉玛依市',NULL), ('360','新疆维吾尔自治区','克孜勒苏柯尔克孜自治州',NULL), ('361','新疆维吾尔自治区','石河子市',NULL), ('362','新疆维吾尔自治区','塔城地区',NULL), ('363','新疆维吾尔自治区','吐鲁番地区',NULL), ('364','新疆维吾尔自治区','图木舒克市',NULL), ('365','新疆维吾尔自治区','五家渠市',NULL), ('366','新疆维吾尔自治区','乌鲁木齐市',NULL), ('367','新疆维吾尔自治区','伊犁哈萨克自治州',NULL), ('368','云南省','保山市',NULL), ('369','云南省','楚雄彝族自治州',NULL), ('370','云南省','大理白族自治州',NULL), ('371','云南省','德宏傣族景颇族自治州',NULL), ('372','云南省','迪庆藏族自治州',NULL), ('373','云南省','红河哈尼族彝族自治州',NULL), ('374','云南省','昆明市',NULL), ('375','云南省','丽江市',NULL), ('376','云南省','临沧市',NULL), ('377','云南省','怒江傈傈族自治州市',NULL), ('378','云南省','普洱市',NULL), ('379','云南省','曲靖市',NULL), ('380','云南省','文山壮族苗族自治州',NULL), ('381','云南省','西双版纳傣族自治州',NULL), ('382','云南省','玉溪市',NULL), ('383','云南省','昭通市',NULL), ('384','浙江省','宁波市',NULL), ('385','浙江省','杭州市',NULL), ('386','浙江省','湖州市',NULL), ('387','浙江省','嘉兴市',NULL), ('388','浙江省','金华市',NULL), ('389','浙江省','丽水市',NULL), ('390','浙江省','衢州市',NULL), ('391','浙江省','绍兴市',NULL), ('392','浙江省','台州市',NULL), ('393','浙江省','温州市',NULL), ('394','浙江省','舟山市',NULL);
INSERT INTO `tbl_repayment_payinfo` VALUES ('100002','8jACYx5ouUf7kH9U6FSlbJDS7JdYJ1aOzAqc+4F/sSy9QV1yT+T+Sw==','0','none','0','50000','500000','20180624134025292','36');
INSERT INTO `tbl_system_config` VALUES ('appinfo.modify.limitone','1','Boolean类型。应用信息修改时候是否限制只能逐项修改。'), ('filecore.upload_file_path','D:\\temp\\','String类型。上传文件路径。'), ('keypair.publickey_get_skip_time','1s','时间类型。公钥获取时公钥重新生成的间隔时间。'), ('keypair.publickey_valid_time','3d','时间类型。公钥有效期。'), ('msg.verify_code_length','8','Integer类型。验证码的位数。'), ('msg.verify_code_valid_time','16m','时间类型。验证码的有效期。'), ('msgsend.limitcount_email','20','Integer类型。邮箱验证码每天发送次数限制。'), ('msgsend.limitcount_mobile','20','Integer类型。手机验证码每天发送次数限制。'), ('msgsend.limitcount_user','20','Integer类型。用户验证码每天发送次数限制。'), ('msgsend.limitcount_uuid','20','Integer类型。设备验证码每天发送次数限制。'), ('payinfo.paypwd_err_count_limit','3','Integer类型。支付密码最大错误次数。'), ('payinfo.paypwd_err_time_limit','1h','时间类型。支付密码最大错误次数清理间隔时间。'), ('repayment.cityversion','1','Integer类型。预付城市数据库版本。'), ('sms.service.async','1','Boolean类型。是否异步发送短信。'), ('sms.service.debug','1','是否调试模式。调试模式不发送短信'), ('sms.service.systemId','81','String类型。本系统在短信服务器中的系统标识。'), ('sms.service.url','http://192.168.100.196:8092/evasp/message/MongateCsSpSendSmsNew','String类型。短信验证服务器地址。'), ('systemlog.async','1','Boolean类型。系统功能日志是否异步记录。'), ('user.idcard_info_return','2','Integer类型。0不返回身份信息，1返回身份信息 2.返回身份信息掩码'), ('user.pwd_encrypt_default','RSAMD5','String类型。用户密码默认加密方式。'), ('user.pwd_encrypt_method','|MD5|RSA|RSAMD5|3DES|3DESMD5|','String类型。用户密码加密方式。'), ('user.pwd_err_count_limit','5','Integer类型。用户密码最大错误次数。'), ('user.pwd_err_time_limit','6h','时间类型。密码最大错误次数清理间隔时间。'), ('user.pwd_max_length','16','Integer类型。用户密码最大长度。'), ('user.pwd_min_length','6','Integer类型。用户密码最小长度。'), ('user.pwd_min_rule','2','Integer类型。用户密码复杂度。0不限制，其他为至少几种组合。'), ('user.uuid_authtime','1h','Integer类型。用户密码最小长度。'), ('usertoken.deletetime','1mon','时间类型。token的设备授信有效期，超过此日期设备不受信任。'), ('usertoken.validtime','3d','时间类型。token有效期。');
INSERT INTO `tbl_system_log` VALUES ('100000',NULL,'13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"msgVerifyCode\":\"78796080\",\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e412','你还没有获取短信验证码','app/userAccount/doLogin','83','20180622','20180622151632697'), ('100001',NULL,'13355667777','111122223333444455556666777788889999','66778899','2','用户注册','{\"msgVerifyCode\":\"61280659\",\"accountType\":\"1\",\"appid\":\"66778899\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\",\"email\":\"yangmi@126.com\"}','e412','你还没有获取短信验证码','app/userAccount/doRegister','38','20180622','20180622151645209'), ('100002',NULL,'13355667777','111122223333444455556666777788889999',NULL,'3','发送短信验证码','{\"msgFunction\":\"1\",\"uuid\":\"111122223333444455556666777788889999\",\"msgAddr\":\"13355667777\"}','0000','OK','app/msg/doMsgSend','122','20180622','20180622151708417'), ('100003','100000','13355667777','111122223333444455556666777788889999','66778899','2','用户注册','{\"msgVerifyCode\":\"70952126\",\"accountType\":\"1\",\"appid\":\"66778899\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\",\"email\":\"yangmi@126.com\"}','0000','OK','app/userAccount/doRegister','211','20180622','20180622151738120'), ('100005','100001','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','44','20180622','20180622152713631'), ('100006','100002','13355667777','111122223333444455556666777788889999','66778899','2','用户注册','{\"msgVerifyCode\":\"70952126\",\"accountType\":\"1\",\"appid\":\"66778899\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\",\"email\":\"yangmi@126.com\"}','0000','OK','app/userAccount/doRegister','647','20180622','20180622153151592'), ('100007','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','92','20180622','20180622153216598'), ('100008','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','74004','20180622','20180622153403899'), ('100009','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','0000','OK','app/userAccount/doLogin','712','20180622','20180622154023422'), ('100010','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','0000','OK','app/userAccount/doLogin','128','20180622','20180622154028056'), ('100011','100002','13355667777','111122223333444455556666777788889999','1000','3','发送短信验证码','{\"tokenId\":\"100000\",\"appId\":\"1000\",\"msgFunction\":\"4\",\"userId\":\"100002\",\"uuid\":\"111122223333444455556666777788889999\",\"msgAddr\":\"13355667777\"}','e401','请求参数错误','app/msg/doMsgSend','50','20180622','20180622154309343'), ('100012','100002','13355667777','111122223333444455556666777788889999','1000','3','发送短信验证码','{\"signInfo\":\"2593bf6d9c19e8b34305d76b4c54d071\",\"tokenId\":\"100000\",\"appId\":\"1000\",\"msgFunction\":\"4\",\"userId\":\"100002\",\"uuid\":\"111122223333444455556666777788889999\",\"msgAddr\":\"13355667777\"}','e499','系统异常','app/msg/doMsgSend','182','20180622','20180622154403792'), ('100013','100002','13355667777','111122223333444455556666777788889999','1000','3','发送短信验证码','{\"signInfo\":\"2593bf6d9c19e8b34305d76b4c54d071\",\"tokenId\":\"100000\",\"appId\":\"1000\",\"msgFunction\":\"4\",\"userId\":\"100002\",\"uuid\":\"111122223333444455556666777788889999\",\"msgAddr\":\"13355667777\"}','0000','OK','app/msg/doMsgSend','59101','20180622','20180622154612527'), ('100014','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"D5N24W5jomZSr07JgyGNH0HgC65trprqo17inWD2yN8CAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','0000','OK','app/userAccount/doLogin','82','20180622','20180622160525677'), ('100015',NULL,'13355667777','111122223333444455556666777788889999',NULL,'3','发送短信验证码','{\"msgFunction\":\"2\",\"uuid\":\"111122223333444455556666777788889999\",\"msgAddr\":\"13355667777\"}','0000','OK','app/msg/doMsgSend','64','20180622','20180622161025313'), ('100016','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','0000','OK','app/userAccount/doLogin','97','20180622','20180622161319314'), ('100017','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e409','需要验证码登录','app/userAccount/doLogin','526','20180622','20180622225245897'), ('100018','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','0000','OK','app/userAccount/doLogin','157066','20180622','20180622231251319'), ('100019','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','0000','OK','app/userAccount/doLogin','101','20180622','20180622231310333'), ('100020','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e409','需要验证码登录','app/userAccount/doLogin','281','20180624','20180624000635887'), ('100021',NULL,'13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"msgVerifyCode\":\"54091940\",\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e412','你还没有获取短信验证码','app/userAccount/doLogin','36','20180624','20180624000754641'), ('100022',NULL,'13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"msgVerifyCode\":\"54091940\",\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e412','你还没有获取短信验证码','app/userAccount/doLogin','16','20180624','20180624000807275'), ('100023','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','0000','OK','app/userAccount/doLogin','99','20180624','20180624000923090'), ('100024','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e409','需要验证码登录','app/userAccount/doLogin','33442','20180624','20180624103709165'), ('100025','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','35416','20180624','20180624103804301'), ('100026','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','0000','OK','app/userAccount/doLogin','538','20180624','20180624104148240'), ('100027','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','31','20180624','20180624104206594'), ('100028','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','31','20180624','20180624104218442'), ('100029','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','31','20180624','20180624104227317'), ('100030','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','32','20180624','20180624104235468'), ('100031','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','31','20180624','20180624104243629'), ('100032','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','31','20180624','20180624104251153'), ('100033','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e409','密码错误次数过多，请稍后重试或找回密码','app/userAccount/doLogin','16','20180624','20180624104300061'), ('100034','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','32','20180624','20180624104329758'), ('100035','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','31','20180624','20180624104339582'), ('100036','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','0000','OK','app/userAccount/doLogin','62','20180624','20180624104347246'), ('100037','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"wmmuTiwk48Wg5HTPGAIZlw/mXVNUd17fPgZ2soWlifQCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','462','20180624','20180624105750168'), ('100038','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"wmmuTiwk48Wg5HTPGAIZlw/mXVNUd17fPgZ2soWlifQCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','31','20180624','20180624105807214'), ('100039','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"wmmuTiwk48Wg5HTPGAIZlw/mXVNUd17fPgZ2soWlifQCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','32','20180624','20180624105816393'), ('100040','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"wmmuTiwk48Wg5HTPGAIZlw/mXVNUd17fPgZ2soWlifQCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','31','20180624','20180624105819401'), ('100041','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"wmmuTiwk48Wg5HTPGAIZlw/mXVNUd17fPgZ2soWlifQCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','31','20180624','20180624105822078'), ('100042','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"wmmuTiwk48Wg5HTPGAIZlw/mXVNUd17fPgZ2soWlifQCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','15','20180624','20180624105824718'), ('100043','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"wmmuTiwk48Wg5HTPGAIZlw/mXVNUd17fPgZ2soWlifQCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','31','20180624','20180624105827392'), ('100044','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"wmmuTiwk48Wg5HTPGAIZlw/mXVNUd17fPgZ2soWlifQCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e409','密码错误次数过多，请稍后重试或找回密码','app/userAccount/doLogin','15','20180624','20180624105830590'), ('100045','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e409','密码错误次数过多，请稍后重试或找回密码','app/userAccount/doLogin','16','20180624','20180624105837282'), ('100046','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','0000','OK','app/userAccount/doLogin','106','20180624','20180624105904622'), ('100047','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"JU9KssNUKvsjA54rm5uoTc8n+muNBm4W1clGz/k/lFYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','31','20180624','20180624105915275'), ('100048','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','0000','OK','app/userAccount/doLogin','62','20180624','20180624105922458'), ('100049','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','476','20180624','20180624111553388'), ('100050','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','31','20180624','20180624111555553'), ('100051','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','31','20180624','20180624111559000'), ('100052','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','31','20180624','20180624111619261'), ('100053','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','32','20180624','20180624111622143'), ('100054','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e409','密码错误次数过多，4分钟后重试或找回密码','app/userAccount/doLogin','15','20180624','20180624111625346'), ('100055','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e409','密码错误次数过多，4分钟后重试或找回密码','app/userAccount/doLogin','15','20180624','20180624111631560'), ('100056','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e409','密码错误次数过多，4分钟后重试或找回密码','app/userAccount/doLogin','15','20180624','20180624111635043'), ('100057','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e409','密码错误次数过多，3分钟后重试或找回密码','app/userAccount/doLogin','16','20180624','20180624111720348'), ('100058','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e409','密码错误次数过多，2分钟后重试或找回密码','app/userAccount/doLogin','16','20180624','20180624111754101'), ('100059','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e409','密码错误次数过多，2分钟后重试或找回密码','app/userAccount/doLogin','15','20180624','20180624111757841'), ('100060',NULL,'13355667777','111122223333444455556666777788889999',NULL,'3','发送短信验证码','{\"msgFunction\":\"9\",\"uuid\":\"111122223333444455556666777788889999\",\"msgAddr\":\"13355667777\"}','e401','请求参数错误','app/msg/doMsgSend','77','20180624','20180624123557176'), ('100061','100002','13355667777','111122223333444455556666777788889999','1000','3','发送短信验证码','{\"signInfo\":\"9c4b27b4f4d5827cc05c391ac33d2736\",\"tokenId\":\"100000\",\"appId\":\"1000\",\"msgFunction\":\"9\",\"userId\":\"100002\",\"uuid\":\"111122223333444455556666777788889999\",\"msgAddr\":\"13355667777\"}','0000','OK','app/msg/doMsgSend','116','20180624','20180624123649553'), ('100062','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','184','20180624','20180624123801818'), ('100063','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"cZJ11EK6ms97PYhn9Tb4obpQvPOetARV92XlusTw6pYCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','449','20180624','20180624153914820'), ('100064','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e409','需要验证码登录','app/userAccount/doLogin','69','20180624','20180624153939361'), ('100065',NULL,'13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"msgVerifyCode\":\"54091940\",\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e412','你还没有获取短信验证码','app/userAccount/doLogin','49','20180624','20180624153947805'), ('100066',NULL,'13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"msgVerifyCode\":\"54091940\",\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e412','你还没有获取短信验证码','app/userAccount/doLogin','89','20180624','20180624165828177'), ('100067',NULL,'13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"msgVerifyCode\":\"54091940\",\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e412','你还没有获取短信验证码','app/userAccount/doLogin','85','20180624','20180624171954476'), ('100068','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e409','需要验证码登录','app/userAccount/doLogin','465','20180624','20180624172005307'), ('100069','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"msgVerifyCode\":\"32456606\",\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','0000','OK','app/userAccount/doLogin','134','20180624','20180624172108540'), ('100070',NULL,'13355667799','111122223333444455556666777788889999',NULL,'3','发送短信验证码','{\"msgFunction\":\"1\",\"uuid\":\"111122223333444455556666777788889999\",\"msgAddr\":\"13355667799\"}','0000','OK','app/msg/doMsgSend','441','20180625','20180625154442122'), ('100071',NULL,'13355667799','111122223333444455556666777788889999','66778899','2','用户注册','{\"idCardName\":\"jianli\",\"headImg\":\"jianli\",\"msgVerifyCode\":\"60760159\",\"nickName\":\"jianli\",\"accountType\":\"1\",\"appid\":\"66778899\",\"idCardNo\":\"410327198310091454\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"jBYnQBhN36j73Zj/aDpA3fz5k7I/kzl2njlEp5Um00ISX8IrhK/K2A==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667799\",\"email\":\"yangmi@126.com\"}','e405','密码解析错误','app/userAccount/doRegister','101','20180625','20180625154537870'), ('100072',NULL,'13355667799','111122223333444455556666777788889999','66778899','2','用户注册','{\"idCardName\":\"jianli\",\"headImg\":\"jianli\",\"msgVerifyCode\":\"60760159\",\"nickName\":\"jianli\",\"accountType\":\"1\",\"appid\":\"66778899\",\"idCardNo\":\"410327198310091454\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"pKUBoIQqmIb4w8e+tno3WM+zT6AGHt5j+MeOeNdiDs3Y+heFqXz2gQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667799\",\"email\":\"yangmi@126.com\"}','e412','短信验证码已经失效，请重新获取','app/userAccount/doRegister','17','20180625','20180625154617841'), ('100073',NULL,'13355667799','111122223333444455556666777788889999','66778899','2','用户注册','{\"idCardName\":\"jianli\",\"headImg\":\"jianli\",\"msgVerifyCode\":\"60760159\",\"nickName\":\"jianli\",\"accountType\":\"1\",\"appid\":\"66778899\",\"idCardNo\":\"410327198310091454\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"pKUBoIQqmIb4w8e+tno3WM+zT6AGHt5j+MeOeNdiDs3Y+heFqXz2gQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667799\",\"email\":\"yangmi@126.com\"}','e402','邮箱已经被注册了','app/userAccount/doRegister','189','20180625','20180625154629181'), ('100074','100003','13355667799','111122223333444455556666777788889999','66778899','2','用户注册','{\"idCardName\":\"jianli\",\"headImg\":\"jianli\",\"msgVerifyCode\":\"60760159\",\"nickName\":\"jianli\",\"accountType\":\"1\",\"appid\":\"66778899\",\"idCardNo\":\"410327198310091454\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"pKUBoIQqmIb4w8e+tno3WM+zT6AGHt5j+MeOeNdiDs3Y+heFqXz2gQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667799\",\"email\":\"yang@126.com\"}','0000','OK','app/userAccount/doRegister','62','20180625','20180625154651722'), ('100075','100003','13355667799','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"pKUBoIQqmIb4w8e+tno3WM+zT6AGHt5j+MeOeNdiDs3Y+heFqXz2gQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667799\"}','0000','OK','app/userAccount/doLogin','143','20180625','20180625155053498');
INSERT INTO `tbl_system_log_function` VALUES ('1','用户登录','account','app/userAccount/doLogin','1','1'), ('2','用户注册','account','app/userAccount/doRegister','1','1'), ('3','发送短信验证码','msgAddr','app/msg/doMsgSend','1','1');
INSERT INTO `tbl_system_log_matter` VALUES ('100001',NULL,'13355667777','111122223333444455556666777788889999','66778899','2','用户注册',NULL,'e412','你还没有获取短信验证码','app/userAccount/doRegister','38','20180622','20180622151645209'), ('100003','100000','13355667777','111122223333444455556666777788889999','66778899','2','用户注册',NULL,'0000','OK','app/userAccount/doRegister','211','20180622','20180622151738120'), ('100005','100001','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','44','20180622','20180622152713631'), ('100006','100002','13355667777','111122223333444455556666777788889999','66778899','2','用户注册',NULL,'0000','OK','app/userAccount/doRegister','647','20180622','20180622153151592'), ('100007','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','92','20180622','20180622153216598'), ('100008','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','74004','20180622','20180622153403899'), ('100025','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','35416','20180624','20180624103804301'), ('100027','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','31','20180624','20180624104206594'), ('100028','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','31','20180624','20180624104218442'), ('100029','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','31','20180624','20180624104227317'), ('100030','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','32','20180624','20180624104235468'), ('100031','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','31','20180624','20180624104243629'), ('100032','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','31','20180624','20180624104251153'), ('100034','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','32','20180624','20180624104329758'), ('100035','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','31','20180624','20180624104339582'), ('100037','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','462','20180624','20180624105750168'), ('100038','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','31','20180624','20180624105807214'), ('100039','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','32','20180624','20180624105816393'), ('100040','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','31','20180624','20180624105819401'), ('100041','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','31','20180624','20180624105822078'), ('100042','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','15','20180624','20180624105824718'), ('100043','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','31','20180624','20180624105827392'), ('100047','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','31','20180624','20180624105915275'), ('100049','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','476','20180624','20180624111553388'), ('100050','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','31','20180624','20180624111555553'), ('100051','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','31','20180624','20180624111559000'), ('100052','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','31','20180624','20180624111619261'), ('100053','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','32','20180624','20180624111622143'), ('100062','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','184','20180624','20180624123801818'), ('100063','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','449','20180624','20180624153914820'), ('100071',NULL,'13355667799','111122223333444455556666777788889999','66778899','2','用户注册',NULL,'e405','密码解析错误','app/userAccount/doRegister','101','20180625','20180625154537870'), ('100072',NULL,'13355667799','111122223333444455556666777788889999','66778899','2','用户注册',NULL,'e412','短信验证码已经失效，请重新获取','app/userAccount/doRegister','17','20180625','20180625154617841'), ('100073',NULL,'13355667799','111122223333444455556666777788889999','66778899','2','用户注册',NULL,'e402','邮箱已经被注册了','app/userAccount/doRegister','189','20180625','20180625154629181'), ('100074','100003','13355667799','111122223333444455556666777788889999','66778899','2','用户注册',NULL,'0000','OK','app/userAccount/doRegister','62','20180625','20180625154651722');
INSERT INTO `tbl_util_seq` VALUES ('FEED_BACK_NEW_PK','100001','2'), ('RM_BANKCARD_TEMP_NEW_PK','100015','16'), ('SEQ_LOGIN_TOKEN_NEW_PK','100001','2'), ('SEQ_LOGIN_USER_NEW_PK','100003','4'), ('SEQ_MSG_INFO_NEW_PK','100004','5'), ('SYSTEM_LOG_NEW_PK','100075','75');
INSERT INTO `tbl_uuid_keypair` VALUES ('111122223333444455556666777788889999','3DES','KiV2Mi+Fj6LjBOmYhmIlLB+D1QsBbR9/','KiV2Mi+Fj6LjBOmYhmIlLB+D1QsBbR9/','20180625154554134','2'), ('111122223333444455556666777788889999','RSA','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCL4fdfChuLXHKFsTArBUh/33thO/eN2ArAE8Xu8E0pssFTHUNstjXZsKUVqIlDlyczFGbsOzpzYpWJvpkZVe5z8oyBoeSHYYR4K9hGgHI9zvX792ohhEPnaJhbW2qFdnZ0O4hEC5c+CdIDQuiP1lz4I1paTqXQrGCMhPhaefPITwIDAQAB','MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIvh918KG4tccoWxMCsFSH/fe2E7943YCsATxe7wTSmywVMdQ2y2NdmwpRWoiUOXJzMUZuw7OnNilYm+mRlV7nPyjIGh5IdhhHgr2EaAcj3O9fv3aiGEQ+domFtbaoV2dnQ7iEQLlz4J0gNC6I/WXPgjWlpOpdCsYIyE+Fp588hPAgMBAAECgYAvxkJFTF9x7mYSsRyBZPGI8tvhrqhy4nlxdo9gduPzvOB4MYNoqqajrcgEKKaQ1hwGPw6T2fqxk9b8z4Lce1PP9pFniMykBy75aGaB19ewxiQqGerFkTmEx+abbUeof9K4AAQMPXX2P7T5Ui9y/nLF/yrva2/xy/xZFgRKUEKfiQJBAMMe2EtHKqodlBICLXBS75XvvjbKktdjEbuZ013Ho9THCyvkpnpry7ZLWvealOI3mVpgL8GWZT7VIy+J6wCc6gsCQQC3hwczl4IeRL56wa37wgQrIPqOwiBop3FGVyqJzH4cTS2Igegwdycv9B99ypBwkxI/Zjf5XekrezoX/HWe9QlNAkA95/ZmA+FUcef9wrUZ8yZSourtxV3LeoIwzEBUe7fOFYzE01nCc5sBbm0hK+la0JsWInhkakwbIFJ2jcKCwkY7AkBg7/llzsjzo+vYeySirb/1591wOilUHd0/Aht93X1fBYTfbX5u2wDf21om3y+bxpME2cEG/guC4/uzX63g4ByhAkAk8MZEas2mNSinjaO/PM9WjXgqQVxr88IeB3Ylt13TK8rY1HAYFVWrgjeP1G2Qa6sZPBk+YQLEkCrr+JDp46GP','20180624001004905','2');
