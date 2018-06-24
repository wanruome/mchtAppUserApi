/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月23日 下午11:26:54
 */
package com.newpay.webauth.dal.core;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class PwdRuleParse {
	private boolean valid;
	private JSONObject returnResp;

}
