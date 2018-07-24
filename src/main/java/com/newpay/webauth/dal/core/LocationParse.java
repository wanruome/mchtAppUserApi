/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年7月23日 下午4:56:47
 */
package com.newpay.webauth.dal.core;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class LocationParse {
	private boolean isValid;
	private String lat;
	private String lng;
	private boolean verifyLocation;
	private String country;
	private String province;
	private String city;
	private String detailAdress;
	private JSONObject returnResp;
}
