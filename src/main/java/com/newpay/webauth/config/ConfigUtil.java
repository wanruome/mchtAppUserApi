/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月22日 上午12:07:33
 */
package com.newpay.webauth.config;

import java.util.HashMap;
import java.util.Map;

import com.newpay.webauth.config.listener.SpringContextHolder;
import com.newpay.webauth.services.ConfigKeyValueService;
import com.ruomm.base.tools.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfigUtil {
	private static Map<String, String> configKeyValueMap = null;

	public static void forceLoadProperty() {
		configKeyValueMap = null;
		loadPropertySynchronized();
	}

	private static void loadProperty() {
		if (null != configKeyValueMap) {
			return;
		}
		else {
			loadPropertySynchronized();
		}
	}

	private static synchronized void loadPropertySynchronized() {
		if (null != configKeyValueMap) {
			return;
		}
		configKeyValueMap = new HashMap<>();
		ConfigKeyValueService configKeyValueService = SpringContextHolder.getBean(ConfigKeyValueService.class);
		configKeyValueMap = configKeyValueService.loadAllConfig();
		log.info("加载数据库SystemConfig配置内容完成...........");
	};

	public static String getValString(String key) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		loadProperty();
		return configKeyValueMap.get(key);
	}

	public static String getValString(String key, String defaultVal) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		loadProperty();
		String value = configKeyValueMap.get(key);
		if (StringUtils.isEmpty(value)) {
			return defaultVal;
		}
		else {
			return value;
		}
	}

	public static Integer getValInteger(String key) {
		return getValInteger(key, null);
	}

	public static Integer getValInteger(String key, Integer defaultVal) {
		String valueString = getValString(key);
		if (StringUtils.isEmpty(valueString)) {
			return defaultVal;
		}
		Integer value = null;
		try {
			value = Integer.valueOf(valueString);
		}
		catch (Exception e) {
			e.printStackTrace();
			value = null;
		}
		if (value == null) {
			return defaultVal;
		}
		else {
			return value;
		}
	}

	public static Long getValLong(String key) {
		return getValLong(key, null);
	}

	public static Long getValLong(String key, Long defaultVal) {
		String valueString = getValString(key);
		if (StringUtils.isEmpty(valueString)) {
			return defaultVal;
		}
		Long value = null;
		try {
			value = Long.valueOf(valueString);
		}
		catch (Exception e) {
			e.printStackTrace();
			value = null;
		}
		if (value == null) {
			return defaultVal;
		}
		else {
			return value;
		}
	}

	public static Long getValLongTime(String key) {
		return getValLongTime(key, null);
	}

	public static Long getValLongTime(String key, Long defaultVal) {
		String valueString = getValString(key);
		if (StringUtils.isEmpty(valueString)) {
			return defaultVal;
		}
		Long value = null;
		try {
			if (valueString.toLowerCase().endsWith("s")) {
				value = Long.valueOf(valueString.substring(0, valueString.length() - 1)) * 1000;
			}
			else if (valueString.toLowerCase().endsWith("m")) {
				value = Long.valueOf(valueString.substring(0, valueString.length() - 1)) * 1000 * 60;
			}
			else if (valueString.toLowerCase().endsWith("h")) {
				value = Long.valueOf(valueString.substring(0, valueString.length() - 1)) * 1000 * 3600;
			}
			else if (valueString.toLowerCase().endsWith("d")) {
				value = Long.valueOf(valueString.substring(0, valueString.length() - 1)) * 1000 * 3600 * 24;
			}
			else if (valueString.toLowerCase().endsWith("w")) {
				value = Long.valueOf(valueString.substring(0, valueString.length() - 1)) * 1000 * 3600 * 24 * 7;
			}
			else if (valueString.toLowerCase().endsWith("mon")) {
				value = Long.valueOf(valueString.substring(0, valueString.length() - 3)) * 1000 * 3600 * 24 * 30;
			}
			else if (valueString.toLowerCase().endsWith("y")) {
				value = Long.valueOf(valueString.substring(0, valueString.length() - 1)) * 1000 * 3600 * 365;
			}
			else {
				value = Long.valueOf(valueString.substring(0, valueString.length() - 1)) * 1000;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			value = null;
		}
		if (value == null) {
			return defaultVal;
		}
		else {
			return value;
		}

	}

	public static Float getValFloat(String key) {
		return getValFloat(key, null);
	}

	public static Float getValFloat(String key, Float defaultVal) {
		String valueString = getValString(key);
		if (StringUtils.isEmpty(valueString)) {
			return defaultVal;
		}
		Float value = null;
		try {
			value = Float.valueOf(valueString);
		}
		catch (Exception e) {
			e.printStackTrace();
			value = null;
		}
		if (value == null) {
			return defaultVal;
		}
		else {
			return value;
		}
	}

	public static Double getValDouble(String key) {
		return getValDouble(key, null);
	}

	public static Double getValDouble(String key, Double defaultVal) {
		String valueString = getValString(key);
		if (StringUtils.isEmpty(valueString)) {
			return defaultVal;
		}
		Double value = null;
		try {
			value = Double.valueOf(valueString);
		}
		catch (Exception e) {
			e.printStackTrace();
			value = null;
		}
		if (value == null) {
			return defaultVal;
		}
		else {
			return value;
		}
	}

	public static Boolean getValBoolean(String key) {
		return getValBoolean(key, false);

	}

	public static Boolean getValBoolean(String key, boolean defaultVal) {
		String value = getValString(key);
		if (StringUtils.isEmpty(value)) {
			return defaultVal;
		}
		else if ("1".equals(value) || "true".equals(value.toLowerCase())) {
			return true;
		}
		else if ("0".equals(value) || "false".equals(value.toLowerCase())) {
			return false;
		}
		else {
			return defaultVal;
		}

	}
}
