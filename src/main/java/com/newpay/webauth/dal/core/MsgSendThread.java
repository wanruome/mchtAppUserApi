/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月21日 下午4:07:57
 */
package com.newpay.webauth.dal.core;

import java.util.HashMap;

import com.newpay.webauth.config.AppConfig;
import com.ruomm.base.http.ResponseData;
import com.ruomm.base.http.okhttp.DataOKHttp;
import com.ruomm.base.http.okhttp.OkHttpConfig;

import okhttp3.RequestBody;

public class MsgSendThread extends Thread {
	private String content;
	private String mobile;
	private String functionId;

	public MsgSendThread(String content, String mobile, String functionId) {
		super();
		this.content = content;
		this.mobile = mobile;
		this.functionId = functionId;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		doMsgSend(mobile, content, functionId);

	}

	public static boolean doMsgSend(String mobile, String content, String functionId) {
		try {
			HashMap<String, String> map = new HashMap<>();
			map.put("pszMobis", mobile);
			map.put("pszMsg", content);
			map.put("iMobiCount", "1");
			map.put("systemId", AppConfig.SmsServicrSystemId());
			map.put("functionId", functionId);
			// String urlGet = OkHttpConfig.createRequestUrlForGet(AppConfig.SMS_SERVICE_URL, map);
			RequestBody mRequestBody = OkHttpConfig.createRequestFormBody(map);
			ResponseData responseData = new DataOKHttp().setUrl(AppConfig.SmsServicrUrl())
					.setRequestBody(mRequestBody).doHttp(String.class);
			if ("0000".equals(responseData.getResultObject())) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
}
