/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月12日 下午4:55:51
 */
package com.newpay.webauth.config;

import java.text.SimpleDateFormat;

import com.ruomm.base.tools.TimeUtils;

public class AppConfig {
	public static String CompanyName() {
		return ConfigUtil.getValString("CompanyName", "浙江盛炬支付");
	}

	public static Long KeyPairPublicKeyValidTime() {
		return ConfigUtil.getValLongTime("keypair.publickey_valid_time");
	}

	public static Long KeyPairPublicKeyGetSkipTime() {
		return ConfigUtil.getValLongTime("keypair.publickey_get_skip_time");
	}

	public static String UserPwdEncryptMethod() {
		return ConfigUtil.getValString("user.pwd_encrypt_method");
	}

	public static Integer UserPwdMinLength() {
		return ConfigUtil.getValInteger("user.pwd_min_length");
	}

	public static Integer UserPwdMaxLength() {
		return ConfigUtil.getValInteger("user.pwd_max_length", 24);
	}

	public static Integer UserPwdMinRule() {
		return ConfigUtil.getValInteger("user.pwd_min_rule");
	}

	public static Integer UserPwdErrCountLimit() {
		return ConfigUtil.getValInteger("user.pwd_err_count_limit", 5);
	}

	public static Long UserPwdErrTimeLimit() {
		return ConfigUtil.getValLongTime("user.pwd_err_time_limit", TimeUtils.VALUE_DAYTimeMillis / 4);
	}

	public static Integer UserIdCardInfoReturn() {
		return ConfigUtil.getValInteger("user.idcard_info_return", 1);
	}

	public static Integer PayInfoPayPwdErrCountLimit() {
		return ConfigUtil.getValInteger("payinfo.paypwd_err_count_limit", 5);
	}

	public static Long PayInfoPayPwdErrTimeLimit() {
		return ConfigUtil.getValLongTime("payinfo.paypwd_err_time_limit", TimeUtils.VALUE_DAYTimeMillis / 4);
	}

	public static Long UserUuidAuthTime() {
		return ConfigUtil.getValLongTime("user.uuid_authtime");
	}

	public static Long UserToken_ValidTime() {
		return ConfigUtil.getValLongTime("usertoken.validtime");
	}

	public static Long UserToken_DeleteTime() {
		return ConfigUtil.getValLongTime("usertoken.deletetime");
	}

	public static Integer VerfiyCodeLength() {
		int value = ConfigUtil.getValInteger("msg.verify_code_length", 6);
		if (value < 4) {
			value = 4;
		}
		if (value > 10) {
			value = 10;
		}
		return value;
	}

	public static Long VerfiyCodeValidTime() {
		return ConfigUtil.getValLongTime("msg.verify_code_valid_time", 15 * 60 * 1000l);
	}

	public static Integer MsgSendLimitCountEmail() {
		return ConfigUtil.getValInteger("msgsend.limitcount_email");
	}

	public static Integer MsgSendLimitCountMobile() {
		return ConfigUtil.getValInteger("msgsend.limitcount_mobile");
	}

	public static Integer MsgSendLimitCountUuid() {
		return ConfigUtil.getValInteger("msgsend.limitcount_uuid");
	}

	public static Integer MsgSendLimitCountUser() {
		return ConfigUtil.getValInteger("msgsend.limitcount_user");
	}

	public static String SmsServicrUrl() {
		return ConfigUtil.getValString("sms.service.url");
	}

	public static String SmsServicrSystemId() {
		return ConfigUtil.getValString("sms.service.systemId");
	}

	public static Boolean SmsServiceAsync() {
		return ConfigUtil.getValBoolean("sms.service.async", true);
	}

	public static Boolean SmsServiceDebug() {
		return ConfigUtil.getValBoolean("sms.service.debug", false);
	}

	public static Boolean AppInfoModifyLimitOne() {
		return ConfigUtil.getValBoolean("appinfo.modify.limitone", true);
	}

	public static Boolean SystemLogAsync() {
		return ConfigUtil.getValBoolean("systemlog.async", true);
	}

	public static String RepayMentCityVersion() {
		return ConfigUtil.getValString("repayment.cityversion");
	}

	public static String FileCoreUploadFilePath() {

		return ConfigUtil.getValString("filecore.upload_file_path");
	}

	public static Long RequestTimeStampOffSet() {
		return ConfigUtil.getValLongTime("request.timestamp_offset", 0l);
	}

	public static Boolean UserLoginVerifyLocation() {
		return ConfigUtil.getValBoolean("user.login.verifylocation", false);
	}

	public static String UserLoginVerifyTime() {
		return ConfigUtil.getValString("user.login.verifytime");
	}

	public static String BaiduLocationAK() {
		return ConfigUtil.getValString("baidu.location.ak");
	}

	public static final Integer OKHTTP_CONNECT_TIMEOUT = 5;
	public static final Integer OKHTTP_WRITE_TIMEOUT = 15;
	public static final Integer OKHTTP_READ_TIMEOUT = 15;
	public static final String REQUEST_FIELD_SIGN_INFO = "signInfo";
	public static final String REQUEST_FIELD_APP_ID = "appId";
	public static final String REQUEST_FIELD_UUID = "uuid";
	public static final String REQUEST_FIELD_USER_ID = "userId";
	public static final String REQUEST_FIELD_TOKEN_ID = "tokenId";
	public static final String REQUEST_FIELD_TIMESTAMP = "timeStamp";
	public static final String REQUEST_FIELD_VERIFY_CODE = "msgVerifyCode";
	public static final String REQUEST_FIELD_UUID_ENCRYPT = "uuidEncrypt";
	public static final long REQUEST_TIMESTAMP_MIN_OFFSET = 60000l;
	public static SimpleDateFormat SDF_DB_DATE = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat SDF_DB_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	public static SimpleDateFormat SDF_LOGIN_VERIFY = new SimpleDateFormat("HH:mm");
	public static String PWD_ENCRYPT_NONE = "NONE";
	public static String PWD_ENCRYPT_MD5 = "MD5";
	public static String PWD_ENCRYPT_RSA = "RSA";
	public static String PWD_ENCRYPT_RSAMD5 = "RSAMD5";
	public static String PWD_ENCRYPT_3DES = "3DES";
	public static String PWD_ENCRYPT_3DESMD5 = "3DESMD5";
	public static String ACCOUNT_TYPE_MOBILE = "1";
	public static String ACCOUNT_TYPE_EMAIL = "2";
	public static String ACCOUNT_TYPE_NAME = "3";
	public static String ACCOUNT_TYPE_USERID = "4";
	public static String TERM_TYPE_ANDROID = "1";
	public static String TERM_TYPE_IPHONE = "2";
	public static String TERM_TYPE_WEB = "3";
	public static String TERM_TYPE_ALL = "4";

}
