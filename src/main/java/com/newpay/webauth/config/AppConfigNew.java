/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月22日 上午12:31:11
 */
package com.newpay.webauth.config;

public class AppConfigNew {
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

	public static Integer UserPwdErrLimit() {
		return ConfigUtil.getValInteger("user.pwd_err_limit");
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

	public Integer VerfiyCodeLength() {
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

	public static Integer MSGSEND_LIMITCOUNT_EMAIL() {
		return ConfigUtil.getValInteger("msgsend.limitcount_email");
	}

	public static Integer MSGSEND_LIMITCOUNT_MOBILE() {
		return ConfigUtil.getValInteger("msgsend.limitcount_mobile");
	}

	public static Integer MSGSEND_LIMITCOUNT_UUID() {
		return ConfigUtil.getValInteger("msgsend.limitcount_uuid");
	}

	public static Integer MSGSEND_LIMITCOUNT_USER() {
		return ConfigUtil.getValInteger("msgsend.limitcount_user");
	}

	public static String SMS_SERVICE_URL() {
		return ConfigUtil.getValString("sms.service.url");
	}

	public static String SMS_SERVICE_SYSTEMID() {
		return ConfigUtil.getValString("sms.service.systemId");
	}

	public static Boolean SMS_SERVICE_ASYNC() {
		return ConfigUtil.getValBoolean("sms.service.async", true);
	}

	public static Integer APPINFO_MODIFY_LIMIT_ONE() {
		return ConfigUtil.getValInteger("appinfo.modify.limitone");
	}

	public static Boolean SYSTEMLOG_ASYNC() {
		return ConfigUtil.getValBoolean("systemlog.async");
	}

}
