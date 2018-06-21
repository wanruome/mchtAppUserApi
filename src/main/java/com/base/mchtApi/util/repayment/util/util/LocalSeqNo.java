package com.base.mchtApi.util.repayment.util.util;

import java.util.LinkedHashMap;
import java.util.Random;

/**
 * 使用随机数方法获取的流水号
 * @author xie
 *
 */
public class LocalSeqNo {

	/**
	 * 
	 * @param len
	 * @return
	 */
	public static String next(int len) {
		String str = "";
		Random rander = new Random(System.currentTimeMillis());
		for (int i = 0; i < len; i++) {
			str += rander.nextInt(9);
		}
		return str;
	}
	

	private static LinkedHashMap<String,Integer> seq = new LinkedHashMap<String,Integer>();
	public synchronized static String next(String code,int len) {
		
		Integer num = seq.get(code);
		if(num == null)
			num = 0;
		num++;
		seq.put(code, num);
		
		return Strings.padLeft(num+"", '0', len);
	}
}
