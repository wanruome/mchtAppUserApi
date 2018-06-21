package com.ruomm.base.http;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruomm.base.tools.StringUtils;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpConfig {
	public final static int Code_Success = 200;
	public final static int Code_NoSend = -200;
	public final static int Code_SendError = -400;
	public final static int Code_FileCreateError = -409;

	private final static String ParseString = "parseString";
	private final static String ParseByte = "parseByte";

	public static Object parseResponseData(byte[] resourceByte, Class<?> cls, String charsetName) {
		if (null == cls) {
			return resourceByte;
		}
		if (String.class.getName().equals(cls.getName())) {
			return byteToString(resourceByte, charsetName);
		}
		if (JSONObject.class.getName().equals(cls.getName())) {
			return parseToJSONObject(getRealJsonString(byteToString(resourceByte, charsetName)));
		}
		if (JSONArray.class.getName().equals(cls.getName())) {
			return parseToJSONArray(getRealJsonString(byteToString(resourceByte, charsetName)));
		}
		Method method = null;
		{
			if (null != cls) {
				try {
					method = cls.getDeclaredMethod(ParseByte, byte[].class);
				}
				catch (Exception e) {
					method = null;
				}
			}
		}
		if (null == method) {
			// 默认解析为JSON对象
			return parseTextToJson(getRealJsonString(byteToString(resourceByte, charsetName)), cls);
		}
		else {
			Object objinstance = null;
			try {
				Constructor<?> constructor = cls.getDeclaredConstructor();
				if (null != constructor) {
					constructor.setAccessible(true);
					objinstance = constructor.newInstance();
				}
				method.setAccessible(true);
				method.invoke(objinstance, resourceByte);
			}
			catch (Exception e) {
				objinstance = null;
			}

			return objinstance;
		}

	}

	public static Object parseResponseText(String resourceString, Class<?> cls) {
		if (null == cls) {
			return resourceString;
		}
		if (String.class.getName().equals(cls.getName())) {
			return resourceString;
		}
		if (JSONObject.class.getName().equals(cls.getName())) {
			return parseToJSONObject(resourceString);
		}
		if (JSONArray.class.getName().equals(cls.getName())) {
			return parseToJSONArray(resourceString);
		}
		Method method = null;
		try {
			method = cls.getDeclaredMethod(ParseString, String.class);
		}
		catch (Exception e) {
			method = null;
		}
		if (null == method) {
			return parseTextToJson(resourceString, cls);
		}
		else {
			Object objinstance = null;
			try {
				Constructor<?> constructor = cls.getDeclaredConstructor();
				if (null != constructor) {
					constructor.setAccessible(true);
					objinstance = constructor.newInstance();
				}
				method.setAccessible(true);
				method.invoke(objinstance, resourceString);
			}
			catch (Exception e) {
				objinstance = null;
			}

			return objinstance;
		}
	}

	private static String byteToString(byte[] resourceByte, String charsetName) {
		String temp = null;
		try {
			if (null == charsetName || charsetName.length() <= 0) {
				temp = new String(resourceByte);
			}
			else {
				temp = new String(resourceByte, Charset.forName(charsetName));
			}
		}
		catch (Exception e) {
			temp = null;
		}
		return temp;
	}

	private static String getRealJsonString(String responseString) {
		String jsonString = null;
		if (!StringUtils.isEmpty(responseString) && !responseString.startsWith("\"")
				&& !responseString.endsWith("\"")) {
			jsonString = responseString;
		}
		else if (!StringUtils.isEmpty(responseString) && responseString.startsWith("\"")
				&& responseString.endsWith("\"")) {
			try {
				jsonString = responseString.substring(1, responseString.length() - 1);
			}
			catch (Exception e) {
				jsonString = null;
			}

		}
		else if (!StringUtils.isEmpty(responseString)) {
			jsonString = responseString;
		}
		else {
			jsonString = null;
		}
		return jsonString;
	}

	private static JSONObject parseToJSONObject(String responseString) {
		String jsonString = getRealJsonString(responseString);
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}
		JSONObject object = null;
		try {
			object = JSON.parseObject(jsonString);
		}
		catch (Exception e) {
			object = null;
		}
		return object;
	}

	private static JSONArray parseToJSONArray(String responseString) {
		String jsonString = getRealJsonString(responseString);
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}
		JSONArray object = null;
		try {
			object = JSON.parseArray(jsonString);
		}
		catch (Exception e) {
			object = null;
		}
		return object;
	}

	private static Object parseTextToJson(String responseString, Class<?> cls) {
		String jsonString = getRealJsonString(responseString);
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}
		Object object = null;
		try {
			if (jsonString.startsWith("[")) {
				object = JSON.parseArray(jsonString, cls);
			}
			else {
				object = JSON.parseObject(jsonString, cls);
			}
		}
		catch (Exception e) {
			object = null;
		}
		return object;
	}

	public static Request getOkHttpRequest(String url, HashMap<String, String> hashMap, boolean isPost) {
		if (isPost) {
			return getOkHttpRequestPost(url, hashMap);
		}
		else {
			return getOkHttpRequestGet(url, hashMap);
		}
	}

	private static Request getOkHttpRequestPost(String url, HashMap<String, String> hashMap) {
		try {
			RequestBody body = attachFormRequestForamtBody(hashMap);
			return new Request.Builder().url(url).post(body).build();
		}
		catch (Exception e) {
			return null;
		}

	}

	private static Request getOkHttpRequestGet(String url, HashMap<String, String> hashMap) {
		try {
			String realUrl = attachFormRequestUrl(url, hashMap);
			return new Request.Builder().url(realUrl).get().build();
		}
		catch (Exception e) {
			return null;
		}

	}

	// 获取真实的Get请求路径
	private static String attachFormRequestUrl(String url, HashMap<String, String> hashMap) {
		if (null == hashMap || hashMap.size() <= 0) {
			return url;
		}
		return url + "?" + attachFormRequestFormatString(hashMap);
	}

	// 构建Post请求参数
	private static RequestBody attachFormRequestForamtBody(HashMap<String, String> hashMap) {

		okhttp3.FormBody.Builder mBuilder = new FormBody.Builder();
		Set<String> sets = hashMap.keySet();
		for (String key : sets) {
			String value = hashMap.get(key);
			if (StringUtils.isEmpty(value)) {
				mBuilder.add(key, "");
			}
			else {
				mBuilder.add(key, value);
			}

		}
		return mBuilder.build();
	}

	// 构建Get请求参数
	private static String attachFormRequestFormatString(HashMap<String, String> hashMap) {
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
