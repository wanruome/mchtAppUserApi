/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月25日 下午7:25:07
 */
package com.newpay.webauth.dal.core;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class LoginUuidParse {
	private boolean isValid;
	private boolean isNeedVerifyCode;
	private JSONObject returnResp;
}
