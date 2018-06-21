/**
 *	@copyright wanruome-2017
 * 	@author wanruome
 * 	@create 2017年3月23日 下午4:54:13
 */
package com.ruomm.base.http.okhttp;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.newpay.webauth.config.AppConfig;
import com.ruomm.base.tools.StringUtils;

import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class OkHttpConfig {
	private static OkHttpClient mOkHttpClient;

	public static OkHttpClient getOkHttpClient() {
		if (null == mOkHttpClient) {

			mOkHttpClient = new OkHttpClient();

			mOkHttpClient.newBuilder().connectTimeout(AppConfig.OKHTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
					.writeTimeout(AppConfig.OKHTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
					.readTimeout(AppConfig.OKHTTP_READ_TIMEOUT, TimeUnit.SECONDS);
		}
		else {
			mOkHttpClient.newBuilder().connectTimeout(AppConfig.OKHTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
					.writeTimeout(AppConfig.OKHTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
					.readTimeout(AppConfig.OKHTTP_READ_TIMEOUT, TimeUnit.SECONDS);
		}
		return mOkHttpClient;
	}

	public static RequestBody attachFormRequestForamtBody(Map<String, Object> hashMap) {

		Builder mBuilder = new FormBody.Builder();
		Set<String> sets = hashMap.keySet();
		for (String key : sets) {
			Object value = hashMap.get(key);
			String val = null == value ? null : String.valueOf(value);
			if (StringUtils.isEmpty(val)) {
				mBuilder.add(key, "");
			}
			else {
				mBuilder.add(key, val);
			}

		}
		return mBuilder.build();
	}

	// 构建Post请求参数
	public static RequestBody createRequestFormBody(Map<String, String> map) {

		okhttp3.FormBody.Builder mBuilder = new FormBody.Builder();
		Set<String> sets = map.keySet();
		for (String key : sets) {
			String value = map.get(key);
			if (StringUtils.isEmpty(value)) {
				mBuilder.add(key, "");
			}
			else {
				mBuilder.add(key, value);
			}

		}
		return mBuilder.build();
	}

	// 获取真实的Get请求路径
	public static String createRequestUrlForGet(String url, Map<String, String> map) {
		if (null == map || map.size() <= 0) {
			return url;
		}
		return url + "?" + createRequestBodyFormatString(map);
	}

	// 构建Get请求参数
	private static String createRequestBodyFormatString(Map<String, String> hashMap) {
		StringBuilder buf = new StringBuilder();
		Set<String> sets = hashMap.keySet();
		int sizeSets = sets.size();
		int index = 1;
		for (String key : sets) {

			buf.append(key).append("=");
			String value = hashMap.get(key);
			if (!StringUtils.isEmpty(value)) {
				buf.append(value);
			}
			if (index != sizeSets) {
				buf.append("&");
			}
			index++;

		}
		return buf.toString();
	}

}
