/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月21日 下午1:46:28
 */
package com.base.mchtApi.util.repayment.util;

import com.ruomm.base.tools.PropertyReader;

public class RepayMentConstant {
	public static PropertyReader configProperty = new PropertyReader("config/app/yufuRepayMent.properties");
	public static String REPAYMENT_MCHT_PUBLICKEY_PATH = null;
	public static String REPAYMENT_MCHT_PRIVATEKEY_PWD = null;
	public static String REPAYMENT_MCHT_PRIVATEKEY_PATH = null;
	public static String REPAYMENT_EASY_PUBLICKEY_PATH = null;
	public static String REPAYMENT_MACHNTNO = null;
	public static String REPAYMENT_YUFU_URL = null;
	public static String REPAYMENT_YUFU_NOTIRY_URL = null;
	public static Integer REPAYMENT_NOPWDAMOUNT = 50000;
	public static Integer REPAYMENT_LIMITAMOUNT = 500000;
	public static Boolean REPAYMENT_AUTOUNLOCK = false;
	public static Boolean REPAYMENT_AUTOUNLOCK_ASYNC = false;
	public static Boolean REPAYMENT_QRCODE_TEST = null;

	static {
		forceLoadProperty();
	}

	public static void resetPropertyData() {
		REPAYMENT_MCHT_PUBLICKEY_PATH = null;
		REPAYMENT_MCHT_PRIVATEKEY_PWD = null;
		REPAYMENT_MCHT_PRIVATEKEY_PATH = null;
		REPAYMENT_EASY_PUBLICKEY_PATH = null;
		REPAYMENT_MACHNTNO = null;
		REPAYMENT_YUFU_URL = null;
		REPAYMENT_YUFU_NOTIRY_URL = null;
		REPAYMENT_NOPWDAMOUNT = 50000;
		REPAYMENT_LIMITAMOUNT = 500000;
		REPAYMENT_AUTOUNLOCK = null;
		REPAYMENT_AUTOUNLOCK_ASYNC = null;
		REPAYMENT_QRCODE_TEST = null;
	}

	public synchronized static void forceLoadProperty() {
		try {
			resetPropertyData();
			configProperty.forceLoadProperty();
			REPAYMENT_MCHT_PUBLICKEY_PATH = configProperty.getValString("repayment.mchtPublicKeyPath");
			REPAYMENT_MCHT_PRIVATEKEY_PATH = configProperty.getValString("repayment.mchtPrivateKeyPath");
			REPAYMENT_MCHT_PRIVATEKEY_PWD = configProperty.getValString("repayment.mchtPrivateKeyPwd");
			REPAYMENT_EASY_PUBLICKEY_PATH = configProperty.getValString("repayment.easyPublicKeyPath");
			REPAYMENT_MACHNTNO = configProperty.getValString("repayment.machantNo");
			REPAYMENT_YUFU_URL = configProperty.getValString("repayment.yufuUrl");
			REPAYMENT_YUFU_NOTIRY_URL = configProperty.getValString("repayment.yufuNotifyUrl");
			REPAYMENT_NOPWDAMOUNT = configProperty.getValInteger("repayment.noPwdAmount", 500 * 100);
			REPAYMENT_LIMITAMOUNT = configProperty.getValInteger("repayment.limitAmount", 5000 * 100);
			REPAYMENT_AUTOUNLOCK = configProperty.getValBoolean("repayment.autounlock", false);
			REPAYMENT_AUTOUNLOCK_ASYNC = configProperty.getValBoolean("repayment.autounlock.async", false);
			REPAYMENT_QRCODE_TEST = configProperty.getValBoolean("repayment.qrcodetest", false);
		}
		catch (Exception e) {
			e.printStackTrace();
			resetPropertyData();
		}

	}
}
