/*
MySQL Backup
Source Server Version: 5.5.25
Source Database: webauth
Date: 2018/6/22 17:50:19
*/

SET FOREIGN_KEY_CHECKS=0;

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
  `LAST_AUTH_UUID` varchar(64) DEFAULT NULL COMMENT '最后授权的设备ID',
  `LAST_AUTH_TIME` varchar(19) DEFAULT NULL COMMENT '最后授权的时间',
  `REGISTER_TIME` varchar(19) DEFAULT NULL COMMENT '注册时间',
  `UPDATE_TIME` varchar(19) DEFAULT NULL COMMENT '更新时间',
  `VERSION` int(11) DEFAULT NULL COMMENT '更新版本控制',
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
INSERT INTO `tbl_login_app_info` VALUES ('1000','聚钱包','550e1bafe077ff0b0b67f4e32f29d751','1','1','1','1','1','','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCburMmm/OZD1xO4dwLZK5Mon65SWze66Y5z30fxy1M8lIUrR5s3ZzO7YJfjRRZQdCjrwRENhqe0aBdmYypN/tocHZ5nvL9HHZ1fmb7X2j1PGgAP559XVAOlh2waQwPkXn5vAUXSkFoRRNlBaWAocrmLPyi5ZdgxmUXE6jspG4EaQIDAQAB','1','20180619110302','1');
INSERT INTO `tbl_login_user_account` VALUES ('100002',NULL,'13355667777','yangmi@126.com','0e1BFrW/fWq0TD1b21uD6HSIkjFn1gGDGES4i+mRsxkDW+/8PRBdLQ==','1','0','111122223333444455556666777788889999','20180622161319232','20180622153151543','20180622161256520','10');
INSERT INTO `tbl_login_user_token` VALUES ('100000','100002','1000','1000_100002_111122223333444455556666777788889999','1','vClSilbMVjBgrVWL','20180625161319252','1','20180622154023298','7');
INSERT INTO `tbl_msg_auth_info` VALUES ('100002','4','13355667777',NULL,'100000','54091940','20180623160134791','0',NULL,'8'), ('111122223333444455556666777788889999','1','13355667777',NULL,NULL,'70952126','20180622153308371','0',NULL,'4'), ('111122223333444455556666777788889999','2','13355667777',NULL,NULL,'32456606','20180622162625279','0',NULL,'3');
INSERT INTO `tbl_msg_funtion_info` VALUES ('1','注册用户','0','0','account','/app/userAccount/doRegister','亲爱的用户，{appName}欢迎你注册。您本次的验证码为:{msgCode}，验证码有效期为{msgValidTime}。','1'), ('2','找回密码','0','0','account','/app/userAccount/doFindPwd','{appName}提醒您正在{msgType}找回密码。您本次的验证码为:{msgCode}，验证码有效期为{msgValidTime}。','1'), ('3','登录操作','0','0','account','/app/userAccount/doLogin','亲爱的用户，{appName}欢迎你登录。您本次的验证码为:{msgCode}，验证码有效期为{msgValidTime}。','1'), ('4','修改密码','1','0',NULL,'/app/userAccount/doModifyPwd','{appName}提醒您正在{msgType}修改密码。您本次的验证码为:{msgCode}，验证码有效期为{msgValidTime}。','1'), ('5','修改手机号','1','1','newMobile','/app/userAccount/doModifyMobile','{appName}提醒您正在{msgTypeEmail}修改手机号。您本次的验证码为:{msgCode}，验证码有效期为{msgValidTime}。','1'), ('6','修改邮箱','1','0',NULL,'/app/userAccount/doModifyEmail','{appName}提醒您正在{msgTypeMobile}修改邮箱。您本次的验证码为:{msgCode}，验证码有效期为{msgValidTime}。','1'), ('7','修改用户名','1','1',NULL,'/app/userAccount/doModifyName','{appName}提醒您正在通过{msgType}修改用户名。您本次的验证码为:{msgCode}，验证码有效期为{msgValidTime}。','1'), ('99','执行变更操作','2','0',NULL,'/app/msg/doGetMsgToken','{appName}提醒您正在执行{msgFunction}操作。您本次的验证码为:{msgCode}，验证码有效期为{msgValidTime}。','1');
INSERT INTO `tbl_msg_send_info` VALUES ('100000',NULL,'111122223333444455556666777788889999','1','1',NULL,'13355667777','70952126','亲爱的用户，浙江盛炬支付欢迎你注册。您本次的验证码为:70952126，验证码有效期为16分钟。','20180622153308371','0','20180622','20180622151708371'), ('100001','100002','111122223333444455556666777788889999','4','1',NULL,'13355667777','54091940','浙江盛炬支付提醒您正在通过手机修改密码。您本次的验证码为:54091940，验证码有效期为16分钟。','20180622160134791','0','20180622','20180622154534791'), ('100002',NULL,'111122223333444455556666777788889999','2','1',NULL,'13355667777','32456606','浙江盛炬支付提醒您正在通过手机找回密码。您本次的验证码为:32456606，验证码有效期为16分钟。','20180622162625279','0','20180622','20180622161025279');
INSERT INTO `tbl_repayment_bankcard` VALUES ('100010','100002','zcLCnrPKHmcMJx4ku7PwbJW5YymEhPTf','UCXaDcgNYhH2BIMYp31X0w==','Wx5wPHQGQaSULNoO8fcUlw==','suZWepuqcYs=','河南省-洛阳市','0000','成功','01','招商银行','1',NULL,NULL,'20180622165013668','3'), ('100014','100002','zcLCnrPKHmcMJx4ku7PwbNrYCz7FABuq','UCXaDcgNYhHZKgD8ZNIeIA==','Wx5wPHQGQaTMmG7Kriqs7JW5YymEhPTf','5J5cloD15MY=','河南省-洛阳市1','0000','成功','01','招商银行','1','ff5571e07c827e20796e6c54f830f0fa','20180622170217423','20180622170217423','2'), ('100015','100002','zcLCnrPKHmcMJx4ku7PwbLyuwT/bVIiS','UCXaDcgNYhGo0QISkekRFQ==','Wx5wPHQGQaTBYjgbYjyRzpW5YymEhPTf','FwxC+odmV2s=','河南省-洛阳市2','0000','成功','01','招商银行','1','dbe46f4a92cbc6cbd232c9dd9fe379be','20180622170313309','20180622170313309','1');
INSERT INTO `tbl_repayment_bankcard_temp` VALUES ('000000000123','100002','zcLCnrPKHmcMJx4ku7PwbLyuwT/bVIiS','UCXaDcgNYhGo0QISkekRFQ==','Wx5wPHQGQaTBYjgbYjyRzpW5YymEhPTf','FwxC+odmV2s=','河南省-洛阳市2','0000','成功','02','','1','20180622170103277','20180622170103277'), ('00001','100002','zcLCnrPKHmcMJx4ku7PwbJW5YymEhPTf','UCXaDcgNYhH2BIMYp31X0w==','Wx5wPHQGQaSULNoO8fcUlw==','suZWepuqcYs=','河南省-洛阳市','E000','银行卡号已绑定','01','招商银行','1','20180622162447303','20180622162447303'), ('100001','100002','zcLCnrPKHmcMJx4ku7PwbJW5YymEhPTf','UCXaDcgNYhH2BIMYp31X0w==','Wx5wPHQGQaSULNoO8fcUlw==','suZWepuqcYs=','河南省-洛阳市','0000','成功','01','招商银行','1','20180622162601023','20180622162601023'), ('100002','100002','zcLCnrPKHmcMJx4ku7PwbJW5YymEhPTf','UCXaDcgNYhH2BIMYp31X0w==','Wx5wPHQGQaSULNoO8fcUlw==','suZWepuqcYs=','河南省-洛阳市','0000','成功','01','招商银行','1','20180622162628541','20180622162628541'), ('100003','100002','zcLCnrPKHmcMJx4ku7PwbJW5YymEhPTf','UCXaDcgNYhH2BIMYp31X0w==','Wx5wPHQGQaSULNoO8fcUlw==','suZWepuqcYs=','河南省-洛阳市','0000','成功','01','招商银行','1','20180622162704366','20180622162704366'), ('100005','100002','zcLCnrPKHmcMJx4ku7PwbJW5YymEhPTf','UCXaDcgNYhH2BIMYp31X0w==','Wx5wPHQGQaSULNoO8fcUlw==','suZWepuqcYs=','河南省-洛阳市','0000','成功','01','招商银行','1','20180622163711157','20180622163711157'), ('100007','100002','zcLCnrPKHmcMJx4ku7PwbJW5YymEhPTf','UCXaDcgNYhH2BIMYp31X0w==','Wx5wPHQGQaSULNoO8fcUlw==','suZWepuqcYs=','河南省-洛阳市','0000','成功','01','招商银行','1','20180622164214735','20180622164214735'), ('100008','100002','zcLCnrPKHmcMJx4ku7PwbJW5YymEhPTf','UCXaDcgNYhH2BIMYp31X0w==','Wx5wPHQGQaSULNoO8fcUlw==','suZWepuqcYs=','河南省-洛阳市','0000','成功','01','招商银行','1','20180622164456367','20180622164456367'), ('100011','100002','zcLCnrPKHmcMJx4ku7PwbJW5YymEhPTf','UCXaDcgNYhH2BIMYp31X0w==','Wx5wPHQGQaSULNoO8fcUlw==','suZWepuqcYs=','河南省-洛阳市','0000','成功','01','招商银行','1','20180622164926447','20180622164926447'), ('123','100002','zcLCnrPKHmcMJx4ku7PwbNrYCz7FABuq','UCXaDcgNYhHZKgD8ZNIeIA==','Wx5wPHQGQaTMmG7Kriqs7JW5YymEhPTf','5J5cloD15MY=','河南省-洛阳市1','0000','成功','02','','1','20180622170036472','20180622170036472');
INSERT INTO `tbl_system_config` VALUES ('appinfo.modify.limitone','1','Boolean类型。应用信息修改时候是否限制只能逐项修改。'), ('keypair.publickey_get_skip_time','1s','时间类型。公钥获取时公钥重新生成的间隔时间。'), ('keypair.publickey_valid_time','3d','时间类型。公钥有效期。'), ('msg.verify_code_length','8','Integer类型。验证码的位数。'), ('msg.verify_code_valid_time','16m','时间类型。验证码的有效期。'), ('msgsend.limitcount_email','20','Integer类型。邮箱验证码每天发送次数限制。'), ('msgsend.limitcount_mobile','20','Integer类型。手机验证码每天发送次数限制。'), ('msgsend.limitcount_user','20','Integer类型。用户验证码每天发送次数限制。'), ('msgsend.limitcount_uuid','20','Integer类型。设备验证码每天发送次数限制。'), ('sms.service.async','1','Boolean类型。是否异步发送短信。'), ('sms.service.debug','1','是否调试模式。调试模式不发送短信'), ('sms.service.systemId','81','String类型。本系统在短信服务器中的系统标识。'), ('sms.service.url','http://192.168.100.196:8092/evasp/message/MongateCsSpSendSmsNew','String类型。短信验证服务器地址。'), ('systemlog.async','1','Boolean类型。系统功能日志是否异步记录。'), ('user.pwd_encrypt_default','RSAMD5','String类型。用户密码默认加密方式。'), ('user.pwd_encrypt_method','|MD5|RSA|RSAMD5|3DES|3DESMD5|','String类型。用户密码加密方式。'), ('user.pwd_err_limit','5','Integer类型。用户密码最大错误次数。'), ('user.pwd_max_length','16','Integer类型。用户密码最大长度。'), ('user.pwd_min_length','6','Integer类型。用户密码最小长度。'), ('user.pwd_min_rule','2','Integer类型。用户密码复杂度。0不限制，其他为至少几种组合。'), ('user.uuid_authtime','1h','Integer类型。用户密码最小长度。'), ('usertoken.deletetime','1mon','时间类型。token的设备授信有效期，超过此日期设备不受信任。'), ('usertoken.validtime','3d','时间类型。token有效期。');
INSERT INTO `tbl_system_log` VALUES ('100000',NULL,'13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"msgVerifyCode\":\"78796080\",\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e412','你还没有获取短信验证码','app/userAccount/doLogin','83','20180622','20180622151632697'), ('100001',NULL,'13355667777','111122223333444455556666777788889999','66778899','2','用户注册','{\"msgVerifyCode\":\"61280659\",\"accountType\":\"1\",\"appid\":\"66778899\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\",\"email\":\"yangmi@126.com\"}','e412','你还没有获取短信验证码','app/userAccount/doRegister','38','20180622','20180622151645209'), ('100002',NULL,'13355667777','111122223333444455556666777788889999',NULL,'3','发送短信验证码','{\"msgFunction\":\"1\",\"uuid\":\"111122223333444455556666777788889999\",\"msgAddr\":\"13355667777\"}','0000','OK','app/msg/doMsgSend','122','20180622','20180622151708417'), ('100003','100000','13355667777','111122223333444455556666777788889999','66778899','2','用户注册','{\"msgVerifyCode\":\"70952126\",\"accountType\":\"1\",\"appid\":\"66778899\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\",\"email\":\"yangmi@126.com\"}','0000','OK','app/userAccount/doRegister','211','20180622','20180622151738120'), ('100005','100001','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','44','20180622','20180622152713631'), ('100006','100002','13355667777','111122223333444455556666777788889999','66778899','2','用户注册','{\"msgVerifyCode\":\"70952126\",\"accountType\":\"1\",\"appid\":\"66778899\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\",\"email\":\"yangmi@126.com\"}','0000','OK','app/userAccount/doRegister','647','20180622','20180622153151592'), ('100007','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','92','20180622','20180622153216598'), ('100008','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','e404','密码错误','app/userAccount/doLogin','74004','20180622','20180622153403899'), ('100009','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','0000','OK','app/userAccount/doLogin','712','20180622','20180622154023422'), ('100010','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','0000','OK','app/userAccount/doLogin','128','20180622','20180622154028056'), ('100011','100002','13355667777','111122223333444455556666777788889999','1000','3','发送短信验证码','{\"tokenId\":\"100000\",\"appId\":\"1000\",\"msgFunction\":\"4\",\"userId\":\"100002\",\"uuid\":\"111122223333444455556666777788889999\",\"msgAddr\":\"13355667777\"}','e401','请求参数错误','app/msg/doMsgSend','50','20180622','20180622154309343'), ('100012','100002','13355667777','111122223333444455556666777788889999','1000','3','发送短信验证码','{\"signInfo\":\"2593bf6d9c19e8b34305d76b4c54d071\",\"tokenId\":\"100000\",\"appId\":\"1000\",\"msgFunction\":\"4\",\"userId\":\"100002\",\"uuid\":\"111122223333444455556666777788889999\",\"msgAddr\":\"13355667777\"}','e499','系统异常','app/msg/doMsgSend','182','20180622','20180622154403792'), ('100013','100002','13355667777','111122223333444455556666777788889999','1000','3','发送短信验证码','{\"signInfo\":\"2593bf6d9c19e8b34305d76b4c54d071\",\"tokenId\":\"100000\",\"appId\":\"1000\",\"msgFunction\":\"4\",\"userId\":\"100002\",\"uuid\":\"111122223333444455556666777788889999\",\"msgAddr\":\"13355667777\"}','0000','OK','app/msg/doMsgSend','59101','20180622','20180622154612527'), ('100014','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"D5N24W5jomZSr07JgyGNH0HgC65trprqo17inWD2yN8CAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','0000','OK','app/userAccount/doLogin','82','20180622','20180622160525677'), ('100015',NULL,'13355667777','111122223333444455556666777788889999',NULL,'3','发送短信验证码','{\"msgFunction\":\"2\",\"uuid\":\"111122223333444455556666777788889999\",\"msgAddr\":\"13355667777\"}','0000','OK','app/msg/doMsgSend','64','20180622','20180622161025313'), ('100016','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录','{\"accountType\":\"1\",\"appId\":\"1000\",\"termType\":\"1\",\"pwdEncrypt\":\"3DESMD5\",\"pwd\":\"3kP59DrB/S/5M0sEzzCOf1wRedxKvFtJdexotlkUY/gCAoRMzrJZGQ==\",\"uuid\":\"111122223333444455556666777788889999\",\"account\":\"13355667777\"}','0000','OK','app/userAccount/doLogin','97','20180622','20180622161319314');
INSERT INTO `tbl_system_log_function` VALUES ('1','用户登录','account','app/userAccount/doLogin','1','1'), ('2','用户注册','account','app/userAccount/doRegister','1','1'), ('3','发送短信验证码','msgAddr','app/msg/doMsgSend','1','1');
INSERT INTO `tbl_system_log_matter` VALUES ('100001',NULL,'13355667777','111122223333444455556666777788889999','66778899','2','用户注册',NULL,'e412','你还没有获取短信验证码','app/userAccount/doRegister','38','20180622','20180622151645209'), ('100003','100000','13355667777','111122223333444455556666777788889999','66778899','2','用户注册',NULL,'0000','OK','app/userAccount/doRegister','211','20180622','20180622151738120'), ('100005','100001','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','44','20180622','20180622152713631'), ('100006','100002','13355667777','111122223333444455556666777788889999','66778899','2','用户注册',NULL,'0000','OK','app/userAccount/doRegister','647','20180622','20180622153151592'), ('100007','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','92','20180622','20180622153216598'), ('100008','100002','13355667777','111122223333444455556666777788889999','1000','1','用户登录',NULL,'e404','密码错误','app/userAccount/doLogin','74004','20180622','20180622153403899');
INSERT INTO `tbl_util_seq` VALUES ('RM_BANKCARD_TEMP_NEW_PK','100015','16'), ('SEQ_LOGIN_TOKEN_NEW_PK','100000','1'), ('SEQ_LOGIN_USER_NEW_PK','100002','3'), ('SEQ_MSG_INFO_NEW_PK','100002','3'), ('SYSTEM_LOG_NEW_PK','100016','16');
INSERT INTO `tbl_uuid_keypair` VALUES ('111122223333444455556666777788889999','3DES','AoyF+4BkvJtFtg5/1XWwYTFMQAs9rpfI','AoyF+4BkvJtFtg5/1XWwYTFMQAs9rpfI','20180622151543750','1'), ('111122223333444455556666777788889999','RSA','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnuGM50ORkhZzP8oSyvgwcecGJXi9EyUsQjYKKXoQeXwRw75OQh4ONMx1nXPcBCkQItEYevF7VmkAeW8nTcQjYWKEXdZ9ZHE7BHVB1Yw74B9kkqS/b0SLaUjsKHpDODV1w9AmtrSs7uSVCxTUSKSE2qUu7NKrf+p+/0OZkXF6UJwIDAQAB','MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKe4YznQ5GSFnM/yhLK+DBx5wYleL0TJSxCNgopehB5fBHDvk5CHg40zHWdc9wEKRAi0Rh68XtWaQB5bydNxCNhYoRd1n1kcTsEdUHVjDvgH2SSpL9vRItpSOwoekM4NXXD0Ca2tKzu5JULFNRIpITapS7s0qt/6n7/Q5mRcXpQnAgMBAAECgYEAk0PLnFBWugy3i78d1PQaHkaqMLuKNZOpXXdjT6t9xk0X0TILF7RlRe3Du6ZdG4SwCBSCHSq4+kTeV8mwhP+TCzeXB9HDdCFVesqYPF9X4ZPhfVylLvTTYpmqX18SCgoxaejDzydEbGUDdz3XFjmpl99/klCWaV3i9Rs54z0s1UECQQDSynJHY/EDcZU9rJnnuFzHc3K6jUq1bumMoMm/Pfz1XnQQ8f+gDvXx/24bJMcwQTyjfzgQChCKqwdiE7UflDM7AkEAy7EixlRvldlzn34+PxC0CokjJxrzynCte+im5rPCaWk0yh5MziKytocdgvUeloEoWy6gU4hjArXj974n5Gt8BQJACD2FziioCVxtWyXeNmLMrbdjqstPOlyFoMvAV0uTEfyJSQhM1/L9xmIX6dO8PbSvLC0TAf2CvAkHltvyXrOqdQJBAKh7HAhwTtvjwX9rLJF9f243gJwX7w7DpLuY9HReu6/1FXUZGu5mROw1VnO7zFExA/Bn4LU3Wq4jRSzmXJjTTwECQQDLl+CBOV5wnVxVxTzqlMlaSFk8J7T4Cmj2968krPh1omSxB/zI9FKo8PHDZ8YfEz1vSkkH4AAKRzUaN3s92dGq','20180622151505312','1');
