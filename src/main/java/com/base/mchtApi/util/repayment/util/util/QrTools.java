/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月13日 下午2:28:53
 */
package com.base.mchtApi.util.repayment.util.util;

import java.util.Random;

import com.ruomm.base.tools.StringUtils;

public class QrTools {
	public static String createNewQrCode(String qrHeader, int length) {

		StringBuilder sb = new StringBuilder();
		int headerLenght = StringUtils.getLength(qrHeader);
		if (headerLenght > 0) {
			sb.append(qrHeader);
		}
		Random random = new Random();
		for (int i = headerLenght; i < length; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();

	}
}
