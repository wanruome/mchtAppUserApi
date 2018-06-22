/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月22日 下午1:22:50
 */
package com.newpay.webauth.config;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

import com.ruomm.base.tools.BaseWebUtils;
import com.ruomm.base.tools.DesUtil;
import com.ruomm.base.tools.FileUtils;
import com.ruomm.base.tools.RSAUtils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class EncryptConfig {
	private static boolean isRead = false;
	private static String STR_PWD_PUBLICKEY = null;
	private static String STR_PWD_PRIVATEKEY = null;
	private static String STR_PWD_3DES = null;
	private static String STR_PAYPWD_PUBLICKEY = null;
	private static String STR_PAYPWD_PRIVATEKEY = null;
	private static String STR_PAYPWD_3DES = null;
	private static String STR_REPAYMENT_PUBLICKEY = null;
	private static String STR_REPAYMENT_PRIVATEKEY = null;
	private static String STR_REPAYMENT_3DES = null;
	private static PublicKey PWD_PUBLICKEY = null;
	private static PrivateKey PWD_PRIVATEKEY = null;
	private static String PWD_3DES = null;
	private static PublicKey PAYPWD_PUBLICKEY = null;
	private static PrivateKey PAYPWD_PRIVATEKEY = null;
	private static String PAYPWD_3DES = null;
	private static PublicKey REPAYMENT_PUBLICKEY = null;
	private static PrivateKey REPAYMENT_PRIVATEKEY = null;
	private static String REPAYMENT_3DES = null;

	public static String encryptPWD(String data) {
		return DesUtil.encryptString(data, getPwd3DES());
	}

	public static String decryptPWD(String dataEncrypt) {
		return DesUtil.decryptString(dataEncrypt, getPwd3DES());
	}

	// public static String encryptPayPwd(String data) {
	// return DesUtil.encryptString(data, getPayPwd3DES());
	// }
	//
	// public static String decryptPayPwd(String dataEncrypt) {
	// return DesUtil.decryptString(dataEncrypt, getPayPwd3DES());
	// }

	public static String encryptRepayment(String data) {
		return DesUtil.encryptString(data, getRepayMent3DES());
	}

	public static String decryptRepayment(String dataEncrypt) {
		return DesUtil.decryptString(dataEncrypt, getRepayMent3DES());
	}

	private static PublicKey getPwdPublicKey() {
		if (!isRead) {
			unZip();
		}
		if (null == PWD_PUBLICKEY) {
			PWD_PUBLICKEY = RSAUtils.loadPublicKey(STR_PWD_PUBLICKEY);
		}
		return PWD_PUBLICKEY;

	}

	private static PrivateKey getPwdPrivateKey() {
		if (!isRead) {
			unZip();
		}
		if (null == PWD_PRIVATEKEY) {
			PWD_PRIVATEKEY = RSAUtils.loadPrivateKey(STR_PWD_PRIVATEKEY);
		}
		return PWD_PRIVATEKEY;

	}

	private static String getPwd3DES() {
		if (!isRead) {
			unZip();
		}
		if (null == PWD_3DES) {
			PWD_3DES = STR_PWD_3DES;
		}
		return PWD_3DES;
	}

	private static PublicKey getPayPwdPublicKey() {
		if (!isRead) {
			unZip();
		}
		if (null == PAYPWD_PUBLICKEY) {
			PAYPWD_PUBLICKEY = RSAUtils.loadPublicKey(STR_PAYPWD_PUBLICKEY);
		}
		return PAYPWD_PUBLICKEY;

	}

	private static PrivateKey getPayPwdPrivateKey() {
		if (!isRead) {
			unZip();
		}
		if (null == PAYPWD_PRIVATEKEY) {
			PAYPWD_PRIVATEKEY = RSAUtils.loadPrivateKey(STR_PAYPWD_PRIVATEKEY);
		}
		return PAYPWD_PRIVATEKEY;

	}

	private static String getPayPwd3DES() {
		if (!isRead) {
			unZip();
		}
		if (null == PAYPWD_3DES) {
			PAYPWD_3DES = STR_PAYPWD_3DES;
		}
		return PAYPWD_3DES;
	}

	private static PublicKey getRepayMentPublicKey() {
		if (!isRead) {
			unZip();
		}
		if (null == REPAYMENT_PUBLICKEY) {
			REPAYMENT_PUBLICKEY = RSAUtils.loadPublicKey(STR_REPAYMENT_PUBLICKEY);
		}
		return REPAYMENT_PUBLICKEY;

	}

	private static PrivateKey getRepayMentPrivateKey() {
		if (!isRead) {
			unZip();
		}
		if (null == REPAYMENT_PRIVATEKEY) {
			REPAYMENT_PRIVATEKEY = RSAUtils.loadPrivateKey(STR_REPAYMENT_PRIVATEKEY);
		}
		return REPAYMENT_PRIVATEKEY;

	}

	private static String getRepayMent3DES() {
		if (!isRead) {
			unZip();
		}
		if (null == REPAYMENT_3DES) {
			REPAYMENT_3DES = STR_REPAYMENT_3DES;
		}
		return REPAYMENT_3DES;

	}

	private static void unZip() {
		if (isRead) {
			return;
		}
		try {
			String sourcePath = BaseWebUtils.getClassesRoot() + "config/keystore/encrptyConfig.dat";
			String dest = BaseWebUtils.getClassesRoot() + "config/keystore/tempencrptyconfig/";
			ZipFile zfile = new ZipFile(sourcePath);
			if (!zfile.isValidZipFile()) {
				throw new ZipException("压缩文件不合法，可能已经损坏！");
			}
			File file = new File(dest);
			if (file.isDirectory() && !file.exists()) {
				file.mkdirs();
			}
			if (zfile.isEncrypted()) {
				zfile.setPassword("ndCdexwLwvFyMNpg");
			}
			zfile.extractAll(dest);
			STR_PWD_PUBLICKEY = FileUtils.readFile(dest + "pwd_publickey.key");
			STR_PWD_PRIVATEKEY = FileUtils.readFile(dest + "pwd_privatekey.key");
			STR_PWD_3DES = FileUtils.readFile(dest + "pwd_3des.key");
			STR_PAYPWD_PUBLICKEY = FileUtils.readFile(dest + "paypwd_publickey.key");
			STR_PAYPWD_PRIVATEKEY = FileUtils.readFile(dest + "paypwd_privatekey.key");
			STR_PAYPWD_3DES = FileUtils.readFile(dest + "paypwd_3des.key");
			STR_REPAYMENT_PUBLICKEY = FileUtils.readFile(dest + "repayment_publickey.key");
			STR_REPAYMENT_PRIVATEKEY = FileUtils.readFile(dest + "repayment_privatekey.key");
			STR_REPAYMENT_3DES = FileUtils.readFile(dest + "repayment_3des.key");
			FileUtils.delAllFile(dest);
			isRead = true;
		}
		catch (Exception e) {
			e.printStackTrace();
			isRead = false;
		}

	}
}
