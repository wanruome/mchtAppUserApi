/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月14日 上午10:57:58
 */
package com.ruomm.base.tools;

import java.util.Random;
import java.util.UUID;

public class TokenUtil {
	public static final String CHARSFORTOKEN = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	public static final int CHARSFORTOKENSIZE = 62;
	public static final int DEFAULT_TOKEN_SIZE = 32;
	public static final int DEFAULT_VERIFY_TOKEN_SIZE = 16;
	public static final int DEFAULT_SYSRELOAD_TOKEN_SIZE = 8;

	private TokenUtil() {

	}

	public static String generateLoginToken() {
		return generateToken(DEFAULT_TOKEN_SIZE);
	}

	public static String generateSysreloadToken() {
		return generateToken(DEFAULT_SYSRELOAD_TOKEN_SIZE);
	}

	public static String generateMsgAuthToken() {
		return generateToken(DEFAULT_VERIFY_TOKEN_SIZE);
	}

	private static String generateToken(int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(CHARSFORTOKEN.charAt(random.nextInt(CHARSFORTOKENSIZE)));
		}
		return sb.toString();
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
