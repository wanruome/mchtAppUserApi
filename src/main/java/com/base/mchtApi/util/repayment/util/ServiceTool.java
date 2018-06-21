package com.base.mchtApi.util.repayment.util;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.mchtApi.util.repayment.util.crpyter.Rsa;
import com.base.mchtApi.util.repayment.util.crpyter.TripleDes;
import com.base.mchtApi.util.repayment.util.util.Base64;
import com.base.mchtApi.util.repayment.util.util.Strings;
import com.ruomm.base.tools.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceTool {

	public static JSONObject parseResponse(String response, String[] signFields, String myPfxPath, String myPfxPwd,
			String easyPublicKeyPath) {

		if (StringUtils.isBlank(response)) {
			JSONObject map = new JSONObject();
			map.put("responseCode", RepayMentConfig.RES_ERROR_NO_DATA);
			map.put("responseRemark", RepayMentConfig.RES_ERROR_NO_DATA_REMARK);
			return map;
		}
		JSONObject map = null;
		if (response.startsWith("{") && response.endsWith("}")) {
			try {
				map = JSON.parseObject(response);
			}
			catch (Exception e) {
				e.printStackTrace();

			}
		}
		else {
			try {
				String des3KeyRsaEncryptedString = response.split("\\|")[0];
				String des3EncryptString = response.split("\\|")[1];
				String des3Key = Rsa.decrypt(des3KeyRsaEncryptedString, myPfxPath, myPfxPwd);
				String json = TripleDes.decrypt(des3Key, des3EncryptString);
				log.info("返回报文解析：" + json);
				map = JSON.parseObject(json);
			}
			catch (Exception e) {
				e.printStackTrace();

			}
		}
		if (null == map) {
			map = new JSONObject();
		}
		if (map.size() <= 0) {
			map.put("responseCode", RepayMentConfig.RES_ERROR_PARSE_ERROR);
			map.put("responseRemark", RepayMentConfig.RES_ERROR_PARSE_ERROR_REMARK);
			return map;
		}
		else if (RepayMentConfig.RES_SUCCESS.equals(map.get("responseCode"))) {
			if (!verify(map, signFields, Strings.toString(map.get("signMsg")), easyPublicKeyPath)) {
				// 转换成功信息
				map.put("responseCode", RepayMentConfig.RES_ERROR_SIGN_ERROR);
				map.put("responseRemark", RepayMentConfig.RES_ERROR_SIGN_ERROR_REMARK);
			}
			else {
				// 转换成功信息
				map.put("responseRemark", RepayMentConfig.RES_SUCCESS_REMARK);
			}
		}
		// 出去掉签名信息
		map.remove("signMsg");

		return map;
	}

	// public static Map<String, String> parseResponse(String response, String[] signFields, String
	// myPfxPath,
	// String myPfxPwd, String easyPublicKeyPath) {
	//
	// if (StringUtils.isBlank(response)) {
	// LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
	// map.put("responseCode", RepayMentConfig.RES_ERROR_NO_DATA);
	// map.put("responseRemark", RepayMentConfig.RES_ERROR_NO_DATA_REMARK);
	// return map;
	// }
	// Map<String, String> map = null;
	// if (response.startsWith("{") && response.endsWith("}")) {
	// map = Reflecter.jsonToMapStr(response);
	// }
	// else {
	// try {
	// String des3KeyRsaEncryptedString = response.split("\\|")[0];
	// String des3EncryptString = response.split("\\|")[1];
	// String des3Key = Rsa.decrypt(des3KeyRsaEncryptedString, myPfxPath, myPfxPwd);
	// String json = TripleDes.decrypt(des3Key, des3EncryptString);
	// map = Reflecter.jsonToMapStr(json);
	// }
	// catch (Exception e) {
	// e.printStackTrace();
	//
	// }
	// if (null == map) {
	// map = new HashMap<String, String>();
	// }
	//
	// }
	// if (map.size() <= 0) {
	// map.put("responseCode", RepayMentConfig.RES_ERROR_PARSE_ERROR);
	// map.put("responseRemark", RepayMentConfig.RES_ERROR_PARSE_ERROR_REMARK);
	// return map;
	// }
	// else if (RepayMentConfig.RES_SUCCESS.equals(map.get("responseCode"))) {
	// if (!verify(map, signFields, Strings.toString(map.get("signMsg")), easyPublicKeyPath)) {
	// //转换成功信息
	// map.put("responseCode", RepayMentConfig.RES_ERROR_SIGN_ERROR);
	// map.put("responseRemark", RepayMentConfig.RES_ERROR_SIGN_ERROR_REMARK);
	// }
	// else{
	// //转换成功信息
	// map.put("responseRemark", RepayMentConfig.RES_SUCCESS_REMARK);
	// }
	// }
	// //出去掉签名信息
	// map.remove("signMsg");
	//
	//
	// return map;
	// }

	public static String encrypt(String json, String easyPublicKeyPath) throws Exception {

		String des3Key = Base64.encode(Strings.randomHex(24).getBytes());
		String des3EncryptString = TripleDes.encrypt(des3Key, json);
		String des3KeyRsaEncryptedString = Rsa.encrypt(des3Key, easyPublicKeyPath);

		return des3KeyRsaEncryptedString + "|" + des3EncryptString;
	}

	private static String toSignString(Object bean, String[] signFields) {

		if (null == bean) {
			return null;
		}
		else if (bean instanceof JSONObject) {
			return toSignStringJSONObject((JSONObject) bean, signFields);
		}
		else if (bean instanceof Map) {
			return toSignStringMap((Map<?, ?>) bean, signFields);
		}
		else if (bean instanceof String) {
			return (String) bean;
		}
		else {
			return String.valueOf(bean);
		}
	}

	private static String toSignStringMap(Map<?, ?> bean, String[] signFields) {
		StringBuffer signString = new StringBuffer();
		for (String field : signFields) {
			String tmp = null == bean.get(field) ? null : String.valueOf(bean.get(field));
			if (!Strings.isNullOrEmpty(tmp)) {
				signString.append(tmp).append(" ");
			}
		}

		String string = signString.toString();
		if (string.length() > 0) {
			string = string.substring(0, string.length() - 1);
		}

		log.info("msg sign string:[" + string + "]");

		return string;

	}

	private static String toSignStringJSONObject(JSONObject bean, String[] signFields) {

		StringBuffer signString = new StringBuffer();
		for (String field : signFields) {
			String tmp = null == bean.get(field) ? null : String.valueOf(bean.get(field));
			if (!Strings.isNullOrEmpty(tmp)) {
				signString.append(tmp).append(" ");
			}
		}

		String string = signString.toString();
		if (string.length() > 0) {
			string = string.substring(0, string.length() - 1);
		}

		log.info("msg sign string:[" + string + "]");

		return string;
	}

	public static String sign(Object bean, String[] signFields, String myPfxPath, String myPfxPwd) throws Exception {

		String string = toSignString(bean, signFields);

		String ret = Rsa.sign(string, myPfxPath, myPfxPwd, algorithm);

		return ret;
	}

	private static String algorithm = "SHA1withRSA";// SHA1withRSA//SHA1withRSA

	public static boolean verify(Object bean, String[] signFields, String signMsg, String publicKey) {
		if (null == signFields || signFields.length <= 0) {
			return true;
		}
		boolean flag = false;
		try {
			String string = toSignString(bean, signFields);

			flag = Rsa.verify(signMsg, publicKey, string, algorithm);
		}
		catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;

	}
}
