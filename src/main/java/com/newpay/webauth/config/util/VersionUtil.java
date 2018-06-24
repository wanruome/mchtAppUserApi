/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月24日 下午9:43:47
 */
package com.newpay.webauth.config.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ruomm.base.tools.StringUtils;

public class VersionUtil {
	public static long getVersion(String versionName) {
		if (StringUtils.isEmpty(versionName)) {
			return 0;
		}
		// String regexString = "[\\.0-9\\s ]{3,20}";
		/**
		 * 为了支持beta4 1.32m之类的格式去掉空格匹配
		 */
		String regexString = "[\\.0-9]{1,20}";
		Pattern pattern = Pattern.compile(regexString);

		Matcher matcher = pattern.matcher(versionName);
		int matcherSize = 0;
		int start = 0;
		int end = 0;
		while (matcher.find())// 查找符合pattern的字符串
		{
			int size = matcher.end() - matcher.start();

			if (matcherSize < size) {
				matcherSize = size;
				start = matcher.start();
				end = matcher.end();
			}
			System.out.println("The result is here :" + matcher.group() + "\n" + "It starts from " + matcher.start()
					+ " to " + matcher.end() + ".\n");
		}
		if (end > start && start >= 0) {
			String realString = versionName.substring(start, end).replace(" ", "");
			List<String> listVersion = getListString(realString, "\\.");
			if (listVersion.size() == 0) {
				return 0;
			}
			else {
				long versionValue = 0;
				long[] verBaseValue = new long[] { 1000 * 1000 * 1000l, 1000 * 1000l, 1000l, 1 };
				for (int i = 0; i < 4 && i < listVersion.size(); i++) {
					versionValue = versionValue + Long.valueOf(listVersion.get(i)) * verBaseValue[i];
				}
				return versionValue;
			}
		}
		else {
			return 0;
		}

	}

	public static List<String> getListString(String arg, String split) {
		List<String> list = new ArrayList<String>();
		if (StringUtils.isEmpty(arg)) {
			return list;
		}
		else {
			String[] strings = arg.split(split);
			for (String string : strings) {
				if (!StringUtils.isEmpty(string)) {
					list.add(string);
				}
			}
		}
		return list;
	}
}
