package com.base.mchtApi.util.repayment.util.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * string操作工具
 *
 * @author xie
 */
@Slf4j
public class Strings {

	/**
	 * 判断一个字符串是否全部由数字组成
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		int begin = 0;
		if (str == null || str.trim().equals("")) {
			return false;
		}
		str = str.trim();
		if (str.startsWith("+") || str.startsWith("-")) {
			if (str.length() == 1) {
				return false;
			}
			begin = 1;
		}
		for (int i = begin; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断一个卡号是否信用卡
	 *
	 * @param cardNo
	 * @return
	 */
	public static boolean isCreditCard(String cardNo) {

		if (!isNumber(cardNo)) {
			return false;
		}

		int len = cardNo.length();
		int weight = 1;
		int total = 0;

		for (int i = 0; i < len; i++) {

			if (i == 0) {
				if (len % 2 == 0) {
					weight = 2;
				}
			}
			else {
				if (i % 2 == 0) {
					weight = 2;
				}
				else {
					weight = 1;
				}
			}

			int tmp = cardNo.charAt(i) - '0';
			tmp = tmp * weight;
			if (tmp > 9) {
				tmp = tmp - 9;
			}

			total += tmp;

		}

		if (total % 10 == 0) {
			return true;
		}

		return false;
	}

	/**
	 * 判断一个对象是否为空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(Object str) {
		return str == null || str.toString().equals("");
	}

	/**
	 * 字符串化一个对象
	 *
	 * @param o
	 * @return
	 */
	public static String toString(Object o) {
		if (o == null) {
			return "";
		}
		return o.toString().trim();
	}

	/**
	 * 对一个字符串左补字符c，使其长度达到length
	 *
	 * @param input
	 * @param c
	 * @param length
	 * @return
	 */
	public static String padLeft(String input, char c, int length) {
		String output = input;
		if (output.length() > length) {
			output = output.substring(output.length() - length);
		}
		while (output.length() < length) {
			output = c + output;
		}
		return output;
	}

	/**
	 * 对一个字符串右补字符c，使其长度达到length
	 *
	 * @param input
	 * @param c
	 * @param length
	 * @return
	 */
	public static String padRight(String input, char c, int length) {
		String output = input;
		if (output.length() > length) {
			output = output.substring(output.length() - length);
		}
		while (output.length() < length) {
			output = output + c;
		}
		return output;
	}

	/**
	 * 字符串话一个异常类型的对象，显示其详细的调用堆栈
	 *
	 * @param e
	 * @return
	 */
	public static String toString(Throwable e) {
		StringBuffer stack = new StringBuffer();
		stack.append(e);
		stack.append("\r\n");

		Throwable rootCause = e.getCause();

		while (rootCause != null) {
			stack.append("Root Cause:\r\n");
			stack.append(rootCause);
			stack.append("\r\n");
			stack.append(rootCause.getMessage());
			stack.append("\r\n");
			stack.append("StackTrace:\r\n");
			stack.append(rootCause);
			stack.append("\r\n");
			rootCause = rootCause.getCause();
		}

		for (int i = 0; i < e.getStackTrace().length; i++) {
			stack.append(e.getStackTrace()[i].toString());
			stack.append("\r\n");
		}
		return stack.toString();
	}

	/**
	 * 字符串化一个对象，并将字符串末尾的空格全部去掉
	 *
	 * @param obj
	 * @return
	 */
	public static String trim(Object obj) {
		if (Strings.isNullOrEmpty(obj)) {
			return "";
		}
		else {
			return obj.toString().trim();
		}
	}

	/**
	 * 字符串化一个对象，并将字符串开始和末尾的空格全部去掉
	 *
	 * @param obj
	 * @return
	 */
	public static String trimHeadEnd(Object obj) {
		if (Strings.isNullOrEmpty(obj)) {
			return "";
		}
		else {
			String ret = obj.toString().trim();
			while (ret.startsWith(" ")) {
				ret = ret.substring(1);
			}
			return ret;
		}
	}

	/**
	 * 判断目标字符串是否包含规则字符串，正则表达式匹配
	 *
	 * @param target
	 * @param pattern
	 * @return
	 */
	public static String getMatching(String target, String pattern) {
		StringBuffer result = new StringBuffer();
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(target);
		if (m.find()) {
			result.append(m.group());
		}
		return result.toString();
	}

	/**
	 * 随机生成一个长度为len的由16进制字符组成的字符串
	 *
	 * @param len
	 * @return
	 */
	public static String randomHex(int len) {
		String str = "";
		java.util.Random rander = new java.util.Random(System.currentTimeMillis());
		for (int i = 0; i < len; i++) {
			str += HEXCHAR[rander.nextInt(16)];
		}
		return str.toUpperCase();
	}

	private static char[] HEXCHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 获取本机IP
	 *
	 * @return
	 */
	public static String getLocalIP() {

		String ip = "";
		try {

			ip = InetAddress.getLocalHost().getHostAddress();

			if (Strings.isNullOrEmpty(ip) || ip.contains("127.0.0.1")) {
				for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
						.hasMoreElements();) {
					NetworkInterface intf = en.nextElement();
					for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
						InetAddress inetAddress = enumIpAddr.nextElement();
						if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()
								&& inetAddress.isSiteLocalAddress()) {
							ip = inetAddress.getHostAddress();
							if (!Strings.isNullOrEmpty(ip) && !ip.contains("127.0.0.1")) {
								break;
							}
						}

					}
					if (!Strings.isNullOrEmpty(ip) && !ip.contains("127.0.0.1")) {
						break;
					}
				}
			}
		}
		catch (Exception e) {
			log.error(Strings.toString(e));
		}
		return ip;
	}

	/**
	 * 截取并返回给定字符串的前缀 源字符串长度大于20，则返回前15位 源字符串长度大于12，则返回前8位 源字符串长度大于7，则返回前7位 否则全部返回
	 *
	 * @param full
	 * @return
	 */
	public static String toPfx(String full) {
		if (Strings.isNullOrEmpty(full)) {
			return "";
		}
		else if (full.length() > 20) {
			return full.substring(0, 15);
		}
		else if (full.length() > 12) {
			return full.substring(0, 8);
		}
		else if (full.length() > 7) {
			return full.substring(0, 7);
		}
		else {
			return full;
		}
	}

	/**
	 * 对敏感的数据进行脱敏处理
	 *
	 * @param full
	 * @return
	 */
	public static String toSafe(String full) {
		if (Strings.isNullOrEmpty(full)) {
			return "";
		}
		else if (full.length() > 20) {
			return full.substring(0, 5) + "************" + full.substring(full.length() - 5);
		}
		else if (full.length() > 12) {
			return full.substring(0, 4) + "********" + full.substring(full.length() - 4);
		}
		else if (full.length() > 7) {
			return full.substring(0, 7) + "****";
		}
		else {
			return "****";
		}
	}

	/**
	 * 获取http请求的绝对路径
	 *
	 * @param request
	 * @return
	 */

	public static String getContextPath(HttpServletRequest request) {

		// 由于9080端口，所以不返回端口
		log.info(request.getRequestURL().toString());
		log.info(request.getHeader("X-Scheme"));
		if (!Strings.isNullOrEmpty(request.getHeader("X-Scheme"))) {
			return request.getHeader("X-Scheme") + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/";
		}
		else {
			return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/";
		}

		/*
		 * String a =
		 * request.getScheme()+"://"+request.getServerName()+request.getContextPath()+"/";
		 * if("1".equals(Config.getSysVal("Switch.Base_Href_Port"))) { a =
		 * request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.
		 * getContextPath()+"/"; } return a;
		 */
	}

	/**
	 * 判断是否合符规则的手机号
	 *
	 * @param mobileNo
	 * @return
	 */
	public static boolean isLegalMobile(String mobileNo) {

		if (Strings.isNullOrEmpty(mobileNo)) {
			return false;
		}

		if (!Strings.isNumber(mobileNo)) {
			return false;
		}

		return true;

	}

	public static String format(String str, int beginSize, int endSize, String leftFill, String rightFill,
			boolean cutLeft) throws Exception {

		while (beginSize > 0) {
			str = leftFill + str;
			beginSize--;
		}
		if (str.getBytes("gbk").length > endSize) {

			byte[] temp = str.getBytes();
			byte[] newbyte = new byte[endSize];
			if (cutLeft) {
				for (int i = newbyte.length - 1, j = temp.length - 1; i >= 0; i--, j--) {
					newbyte[i] = temp[j];
				}
			}
			else {
				for (int i = 0; i < newbyte.length; i++) {
					newbyte[i] = temp[i];
				}
			}
			str = new String(newbyte);
		}

		while (str.getBytes("gbk").length < endSize) {
			str += rightFill;
		}
		return str;
	}

	public static String getMacString(String str) {
		if (Strings.isNullOrEmpty(str)) {
			return "";
		}
		else {
			return str + " ";
		}
	}

	/*
	 * public static String downloadDecompression(String filename) throws IOException{ StringBuffer
	 * buff = new StringBuffer(); InputStream is = null; try { is = new FileInputStream(new
	 * File(filename)); is = new ResolveZFile(is); BufferedReader br = new BufferedReader(new
	 * InputStreamReader(is)); String line = ""; while((line = br.readLine())!= null){
	 * buff.append(line); buff.append("\n"); } } catch (FileNotFoundException e) {
	 * log.error(filename + " 文件不存在."); } catch (Exception e) { log.error("", e); } finally {
	 * if(null != is) is.close(); } return buff.toString(); }
	 */

	public static StringBuffer readFile(String filename) throws IOException {
		StringBuffer buff = new StringBuffer();
		InputStream is = null;
		try {
			is = new FileInputStream(new File(filename));
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = "";
			while ((line = br.readLine()) != null) {
				buff.append(line);
				buff.append("\n");
			}
		}
		catch (FileNotFoundException e) {
			log.error(filename + " 文件不存在.");
		}
		catch (Exception e) {
			log.error("", e);
		}
		finally {
			if (null != is) {
				is.close();
			}
		}
		return buff;
	}

	public static String unReckonZip(String filename) throws IOException {
		StringBuffer buff = new StringBuffer();
		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(new File(filename)));
			ZipEntry zipEntry = zis.getNextEntry();
			if (zipEntry != null) {
				InputStreamReader isr = new InputStreamReader(zis);
				BufferedReader br = new BufferedReader(isr);
				String str = null;
				while ((str = br.readLine()) != null) {
					buff.append("\n" + str);
				}
				return buff.toString().substring(1);
			}
		}
		catch (FileNotFoundException e) {
			log.error(filename + " 文件不存在.");
		}
		return buff.toString();
	}

	public static String[] getFileName(String path, String name1, String name2) {
		String[] names = null;
		final String a = name1;
		final String b = name2;
		File directory = new File(path);
		FilenameFilter filefilter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return (name.startsWith(a) && name.endsWith(b));
			}
		};
		names = directory.list(filefilter);
		return names;
	}

	public static StringBuffer readFile(String filename, String encode) throws IOException {
		StringBuffer buff = new StringBuffer();
		InputStream is = null;
		if (Strings.isNullOrEmpty(encode)) {
			encode = "gbk";
		}
		try {
			is = new FileInputStream(new File(filename));
			BufferedReader br = new BufferedReader(new InputStreamReader(is, encode));
			String line = "";
			while ((line = br.readLine()) != null) {
				buff.append(line);
				buff.append("\n");
			}
		}
		catch (FileNotFoundException e) {
			log.error(filename + " 文件不存在.");
		}
		catch (Exception e) {
			log.error("", e);
		}
		finally {
			if (null != is) {
				is.close();
			}
		}
		return buff;
	}

	public static String[] readGZIPFile(String path, String encode) {
		String[] lines = null;
		InputStream in = null;
		try {
			if (Strings.isNullOrEmpty(encode)) {
				encode = "gbk";
			}
			in = new GZIPInputStream(new FileInputStream(path));
			String tmp = null;
			StringBuffer sb = new StringBuffer();
			InputStreamReader isr = new InputStreamReader(in, encode);
			BufferedReader br = new BufferedReader(isr);
			while (null != (tmp = br.readLine())) {
				sb.append(tmp + "\n");
			}
			lines = sb.toString().split("\n");
		}
		catch (Exception e) {
			log.error("readGZIPFile:", e);
		}
		finally {
			if (null != in) {
				try {
					in.close();
				}
				catch (IOException e) {
					log.error("readGZIPFile:", e);
				}
			}
		}
		return lines;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {/*
											 * System.out.println(formatAmt(12.3,16));
											 * System.out.println(formatAmt(12,16));
											 * System.out.println(formatAmt(12.34,16));
											 * System.out.println(formatAmt(0,16));
											 * System.out.println("".getBytes().length);
											 * System.out.println("1".getBytes().length);
											 * System.out.println("23".getBytes().length);
											 * System.out.println("才".getBytes().length);
											 * System.out.println(Strings.formatAmt(12.23));
											 */
		// System.out.println(Strings.isCreditCard("5309900599078506"));

		// String a = "ksdjfoshgafsdfiu";
		System.out.println(Character.getNumericValue('c'));
		gg('c');

	}

	/**
	 * @param a
	 */
	public static void gg(int a) {
		System.out.println(a);
	}

}
