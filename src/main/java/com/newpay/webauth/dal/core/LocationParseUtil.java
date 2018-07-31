/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年7月31日 上午10:33:38
 */
package com.newpay.webauth.dal.core;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.newpay.webauth.config.AppConfig;
import com.newpay.webauth.dal.response.ResultFactory;
import com.ruomm.base.http.ResponseData;
import com.ruomm.base.http.okhttp.DataOKHttp;
import com.ruomm.base.http.okhttp.OkHttpConfig;

public class LocationParseUtil {
	public static LocationParse parseLocationByLatLng(String lat, String lng) {
		LocationParse locationParse = new LocationParse();
		locationParse.setLat(lat);
		locationParse.setLng(lng);
		if (null == AppConfig.UserToken_LoginVerifyLocation() || !AppConfig.UserToken_LoginVerifyLocation()) {
			locationParse.setValid(true);
			locationParse.setVerifyLocation(false);
			return locationParse;
		}
		locationParse.setVerifyLocation(true);
		locationParse.setValid(false);
		double latValue = 0;
		double lngValue = 0;
		try {
			latValue = Double.valueOf(lat);
			lngValue = Double.valueOf(lng);
		}
		catch (Exception e) {
			latValue = 0;
			lngValue = 0;
		}
		if ((latValue < 0.5 && latValue > -0.5) || (lngValue < 0.5 && lngValue > -0.5)) {
			locationParse.setReturnResp(ResultFactory.toNackPARAM("定位信息不正确，请开启定位后重新提交"));
			return locationParse;
		}
		Map<String, String> map = new HashMap<String, String>();
		// map.put("callback", "renderReverse");
		map.put("location", lat + "," + lng);
		map.put("output", "json");
		map.put("ak", AppConfig.BaiduLocationAK());
		String url = OkHttpConfig.createRequestUrlForGet("http://api.map.baidu.com/geocoder/v2/", map);
		ResponseData responseData = new DataOKHttp().setUrl(url).setPost(false).doHttp(JSONObject.class);
		System.out.println(responseData);
		if (null == responseData || null == responseData.getResultObject()) {
			locationParse.setReturnResp(ResultFactory.toNackCORE("获取定位信息失败"));
			return locationParse;
		}
		JSONObject jsonObject = (JSONObject) responseData.getResultObject();
		JSONObject resultJsonObject = jsonObject.getJSONObject("result");
		if (!"0".equals(jsonObject.getString("status")) || null == resultJsonObject) {
			locationParse.setReturnResp(ResultFactory.toNackCORE("获取定位信息失败"));
			return locationParse;
		}

		JSONObject addressComponent = resultJsonObject.getJSONObject("addressComponent");
		if (null == addressComponent) {
			locationParse.setReturnResp(ResultFactory.toNackCORE("解析定位信息失败"));
			return locationParse;
		}
		// if (null == addressComponent || !"中国".equals(addressComponent.getString("country"))) {
		// locationParse.setReturnResp(ResultFactory.toNack(ResultFactory.ERR_LOCATION_CHANGE,
		// "地区位置变换，需要重新登录"));
		// return locationParse;
		// }
		locationParse.setValid(true);
		locationParse.setCountry(addressComponent.getString("country"));
		locationParse.setProvince(addressComponent.getString("province"));
		locationParse.setCity(addressComponent.getString("city"));
		locationParse.setDetailAdress(resultJsonObject.getString("formatted_address"));
		return locationParse;
	}
}
