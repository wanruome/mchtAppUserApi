/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月24日 上午9:23:42
 */
package com.newpay.webauth.dal.core;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class PwdErrParse {
	private boolean isValid;
	private Integer pwdErrCount;
	private String pwdErrTime;
	private String lastAuthTime;
	private JSONObject returnResp;
}
