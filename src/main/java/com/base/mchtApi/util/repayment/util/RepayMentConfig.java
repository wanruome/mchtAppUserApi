/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年5月29日 上午10:26:04
 */
package com.base.mchtApi.util.repayment.util;

import com.ruomm.base.tools.EncryptUtils;

public class RepayMentConfig {
	public static String RES_SUCCESS = "0000";
	public static String RES_SUCCESS_REMARK = "成功";
	public static String RES_ERROR_HISBIND = "E000";
	public static String RES_ERROR_HISBIND_MSG = "银行卡号已绑定";
	public static String RES_ERROR_NO_DATA = "E001";
	public static String RES_ERROR_PARSE_ERROR = "E002";
	public static String RES_ERROR_SIGN_ERROR = "E004";
	public static String RES_ERROR_PARSE_ERROR_REMARK = "数据解析错误";
	public static String RES_ERROR_NO_DATA_REMARK = "没有数据返回";
	public static String RES_ERROR_SIGN_ERROR_REMARK = "数据验签失败";

	public static String getCardFinger(String cardNo) {
		String realCard = null == cardNo ? "" : cardNo;
		String fingerStr = "SjNewPay" + realCard + "2018";
		return EncryptUtils.encodingMD5(fingerStr);
	}

}
