/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月21日 下午10:02:09
 */
package com.base.mchtApi.util.repayment.util;

import com.alibaba.fastjson.JSONObject;

public class RepeyMentTest {
	public static JSONObject createSucess() {
		JSONObject responseMap = new JSONObject();
		responseMap.put("responseCode", RepayMentConfig.RES_SUCCESS);
		responseMap.put("responseRemark", "成功");
		responseMap.put("bankName", "工商银行");
		responseMap.put("accountType", "01");
		return responseMap;
	}

	public static JSONObject createBindResult() {
		JSONObject responseMap = new JSONObject();
		responseMap.put("responseCode", RepayMentConfig.RES_SUCCESS);
		responseMap.put("responseRemark", "成功");
		responseMap.put("bankName", "农业银行");
		responseMap.put("accountType", "2");
		return responseMap;
	}

	public static JSONObject createUnBind() {
		JSONObject responseMap = new JSONObject();
		responseMap.put("responseCode", RepayMentConfig.RES_SUCCESS);
		responseMap.put("responseRemark", "成功");
		responseMap.put("bankName", "农业银行");
		responseMap.put("accountType", "2");
		return responseMap;
	}
}
