package com.base.mchtApi.util.repayment.util.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

/**
 * 日期 时间操作类
 *
 * @author xie
 */
@Slf4j
public class DateUtil {

	/**
	 * 获取两个时间的毫秒差
	 *
	 * @param a
	 * @param b
	 * @return 字符串
	 */
	public static long getMilliseconds(Date a, Date b) {
		return Math.abs(a.getTime() - b.getTime());
	}

	/**
	 * 对日期now增加天数days
	 *
	 * @param now
	 * @param days
	 * @return 字符串
	 */
	public static Date addDays(Date now, int days) {
		Date rs = new Date();
		rs.setTime(now.getTime() + days * 1000L * 60 * 60 * 24);
		return rs;
	}

	/**
	 * 获取两个时间的日差
	 *
	 * @param a
	 * @param b
	 * @return 字符串
	 */
	public static long getDisInDay(Date a, Date b) {

		long quot = 0;
		try {
			quot = a.getTime() - b.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		}
		catch (Exception e) {
			log.error(Strings.toString(e));
		}
		return Math.abs(quot);
	}

	/**
	 * 获取两个时间字符串的日差
	 *
	 * @param a
	 * @param b
	 * @return 字符串
	 */
	public static long getDisInDay_yyyyMMdd(String a, String b) {

		long quot = 0;
		try {
			Date date1 = yyyyMMdd(a);
			Date date2 = yyyyMMdd(b);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		}
		catch (Exception e) {
			log.error(Strings.toString(e));
		}
		return Math.abs(quot);
	}

	/**
	 * 获取两个时间字符串的日差
	 *
	 * @param a
	 * @param b
	 * @return 字符串
	 */
	public static long getDisInDay_yyyy_MM_dd(String a, String b) {

		long quot = 0;
		try {
			Date date1 = yyyy_MM_dd(a);
			Date date2 = yyyy_MM_dd(b);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		}
		catch (Exception e) {
			log.error(Strings.toString(e));
		}
		return Math.abs(quot);
	}

	/**
	 * 获取两个时间的日差
	 *
	 * @param a
	 * @param b
	 * @return 字符串
	 */
	public static long getDisInDayNoAbs(Date big, Date small) {

		long quot = 0;
		try {
			quot = big.getTime() - small.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		}
		catch (Exception e) {
			log.error(Strings.toString(e));
		}
		return quot;
	}

	/**
	 * 获取两个时间的分钟差
	 *
	 * @param a
	 * @param b
	 * @return 字符串
	 */
	public static long getDisInMin(Date a, Date b) {

		long quot = 0;
		try {
			quot = Math.abs(a.getTime() - b.getTime());
			quot = quot / 1000 / 60;
		}
		catch (Exception e) {
			log.error(Strings.toString(e));
		}
		return Math.abs(quot);
	}

	/**
	 * 获取两个时间字符串的分钟差
	 *
	 * @param a
	 * @param b
	 * @return 字符串
	 */
	public static long getDisInMin(String a, String b) {

		long quot = 0;
		try {
			Date date1 = yyyy_MM_dd_HH_mm_ss(a);
			Date date2 = yyyy_MM_dd_HH_mm_ss(b);
			quot = Math.abs(date1.getTime() - date2.getTime());
			quot = quot / 1000 / 60;
		}
		catch (Exception e) {
			log.error(Strings.toString(e));
		}
		return Math.abs(quot);
	}

	/**
	 * 获取两个时间字符串的秒差
	 *
	 * @param a
	 * @param b
	 * @return 字符串
	 */
	public static long getDisInSec(String a, String b) {

		long quot = 0;
		try {
			Date date1 = yyyy_MM_dd_HH_mm_ss(a);
			Date date2 = yyyy_MM_dd_HH_mm_ss(b);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000;
		}
		catch (Exception e) {
			log.error(Strings.toString(e));
		}
		return Math.abs(quot);
	}

	// beg&end format excemple:00:10,23:10
	/**
	 * 判断当前时间是否在[beg,end]时间区间内
	 *
	 * @param beg
	 *            开始时分，比如：00:10，0点10分开始
	 * @param end
	 *            结束时分，比如23:10，23点10分结束
	 * @return 字符串
	 */
	public static boolean isNowInTimeZone(String beg, String end) {

		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int min = Calendar.getInstance().get(Calendar.MINUTE);

		if (hour < Integer.parseInt(beg.split(":")[0])
				|| (hour == Integer.parseInt(beg.split(":")[0]) && min <= Integer.parseInt(beg.split(":")[1]))) {
			return false;
		}
		if (hour > Integer.parseInt(end.split(":")[0])
				|| (hour == Integer.parseInt(end.split(":")[0]) && min >= Integer.parseInt(end.split(":")[1]))) {
			return false;
		}

		return true;
	}

	/**
	 * 将时间time格式化为HHmmss格式
	 *
	 * @param time
	 * @return 字符串
	 */
	public static String HHmmss(Date time) {
		if (time == null) {
			return "";
		}
		SimpleDateFormat hhmmss = new SimpleDateFormat("HHmmss");
		return hhmmss.format(time);
	}

	/**
	 * 将时间time格式化为HH:mm格式
	 *
	 * @param time
	 * @return 字符串
	 */
	public static String HH_mm(Date time) {
		if (time == null) {
			return "";
		}
		SimpleDateFormat hh1mm = new SimpleDateFormat("HH:mm");
		return hh1mm.format(time);
	}

	/**
	 * 将时间date格式化为MMddHHmmss格式
	 *
	 * @param date
	 * @return 字符串
	 */
	public static String MMddHHmmss(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat mmddHHmmss = new SimpleDateFormat("MMddHHmmss");
		return mmddHHmmss.format(date);
	}

	/**
	 * 将时间date格式化为MMdd格式
	 *
	 * @param date
	 * @return 字符串
	 */
	public static String MMdd(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat mmdd = new SimpleDateFormat("MMdd");
		return mmdd.format(date);
	}

	/**
	 * 将时间date格式化为yyyy格式
	 *
	 * @param date
	 * @return 字符串
	 */
	public static String yyyy(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
		return yyyy.format(date);
	}

	/**
	 * 将时间date格式化为yyyyMM格式
	 *
	 * @param date
	 * @return 字符串
	 */
	public static String yyyyMM(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat yyyyMM = new SimpleDateFormat("yyyyMM");
		return yyyyMM.format(date);
	}

	/**
	 * 将时间date格式化为yyyy-MM-dd格式
	 *
	 * @param date
	 * @return 字符串
	 */
	public static String yyyy_MM_dd(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat yyyy1MM1dd = new SimpleDateFormat("yyyy-MM-dd");
		return yyyy1MM1dd.format(date);
	}

	/**
	 * 将时间date格式化为yyMMdd格式
	 *
	 * @param date
	 * @return 字符串
	 */
	public static String yyMMdd(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat yyMMdd = new SimpleDateFormat("yyMMdd");
		return yyMMdd.format(date);
	}

	/**
	 * 将时间date格式化为yy格式
	 *
	 * @param date
	 * @return 字符串
	 */
	public static String yy(Date year) {
		if (year == null) {
			return "";
		}
		SimpleDateFormat yy = new SimpleDateFormat("yy");
		return yy.format(year);
	}

	/**
	 * 将时间date格式化为yyyy-MM-dd HH:mm:ss格式
	 *
	 * @param date
	 * @return 字符串
	 */
	public static String yyyy_MM_dd_HH_mm_ss(Date time) {
		if (time == null) {
			return "";
		}
		SimpleDateFormat yyyy1MM1dd1HH1mm1ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return yyyy1MM1dd1HH1mm1ss.format(time);
	}

	/**
	 * 将时间date格式化为yyMMddHHmmss格式
	 *
	 * @param date
	 * @return 字符串
	 */
	public static String yyMMddHHmmss(Date time) {
		if (time == null) {
			return "";
		}
		SimpleDateFormat yyMMddHHmmss = new SimpleDateFormat("yyMMddHHmmss");
		return yyMMddHHmmss.format(time);
	}

	/**
	 * 将时间date格式化为yyyyMMdd格式
	 *
	 * @param date
	 * @return 字符串
	 */
	public static String yyyyMMdd(Date time) {
		if (time == null) {
			return "";
		}
		SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
		return yyyyMMdd.format(time);
	}

	/**
	 * 将时间date格式化为yyyy年MM月dd日 HH时mm分ss秒格式
	 *
	 * @param date
	 * @return 字符串
	 */
	public static String china_yyyy_MM_dd_HH_mm_ss(Date time) {
		SimpleDateFormat china1yyyy1MM1dd1HH1mm1ss = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		return china1yyyy1MM1dd1HH1mm1ss.format(time);
	}

	/**
	 * 将时间date格式化为MM月dd日秒格式
	 *
	 * @param date
	 * @return 字符串
	 */
	public static String china_MM_dd(Date time) {
		SimpleDateFormat china1MM1dd = new SimpleDateFormat("MM月dd日");
		return china1MM1dd.format(time);
	}

	/**
	 * 将时间date格式化为HH:mm:ss.SSS日秒格式
	 *
	 * @param date
	 * @return 字符串
	 */
	public static String HH_mm_ss_SSS(Date time) {
		SimpleDateFormat hh1mm1ss1SSS = new SimpleDateFormat("HH:mm:ss.SSS");
		return hh1mm1ss1SSS.format(time);
	}

	/**
	 * 将时间date格式化为HH:mm:ss日秒格式
	 *
	 * @param date
	 * @return 字符串
	 */
	public static String HH_mm_ss(Date time) {
		SimpleDateFormat hh1mm1ss = new SimpleDateFormat("HH:mm:ss");
		return hh1mm1ss.format(time);
	}

	/**
	 * 将时间date格式化为HHmmssSSS日秒格式
	 *
	 * @param date
	 * @return 字符串
	 */
	public static String HHmmssSSS(Date time) {
		SimpleDateFormat hhmmssSSS = new SimpleDateFormat("HHmmssSSS");
		return hhmmssSSS.format(time);
	}

	/**
	 * 将时间date格式化为yyyyMMddHHmmss日秒格式
	 *
	 * @param date
	 * @return 字符串
	 */
	public static String yyyyMMddHHmmss(Date time) {
		SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
		if (null == time) {
			return "";
		}
		return yyyyMMddHHmmss.format(time);
	}

	/**
	 * 将yyyyMMdd格式的字符串转为日期类
	 *
	 * @param date
	 * @return 日期类
	 */
	public static Date yyyyMMdd(String date) throws ParseException {
		if (Strings.isNullOrEmpty(date)) {
			return null;
		}
		SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
		return yyyyMMdd.parse(date);
	}

	/**
	 * 将yyyy-MM-dd HH:mm:ss格式的字符串转为日期类
	 *
	 * @param date
	 * @return 日期类
	 */
	public static Date yyyy_MM_dd_HH_mm_ss(String date) throws ParseException {
		if (Strings.isNullOrEmpty(date)) {
			return null;
		}
		SimpleDateFormat yyyy1MM1dd1HH1mm1ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return yyyy1MM1dd1HH1mm1ss.parse(date);
	}

	/**
	 * 将yyyyMMddHHmmss格式的字符串转为日期类
	 *
	 * @param date
	 * @return 日期类
	 */
	public static Date yyyyMMddHHmmss(String date) throws ParseException {
		if (Strings.isNullOrEmpty(date)) {
			return null;
		}
		SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
		return yyyyMMddHHmmss.parse(date);
	}

	/**
	 * 将MMddHHmmss格式的字符串转为日期类，年份取当前机器时间的年份
	 *
	 * @param date
	 * @return 日期类
	 */
	public static Date MMddHHmmss(String date) throws ParseException {
		if (Strings.isNullOrEmpty(date)) {
			return null;
		}
		return yyyyMMddHHmmss(Calendar.getInstance().get(Calendar.YEAR) + date);
	}

	/**
	 * 将yyyy-MM-dd格式的字符串转为日期类
	 *
	 * @param date
	 * @return 日期类
	 */
	public static Date yyyy_MM_dd(String date) throws ParseException {
		if (Strings.isNullOrEmpty(date)) {
			return null;
		}
		SimpleDateFormat yyyy1MM1dd = new SimpleDateFormat("yyyy-MM-dd");
		return yyyy1MM1dd.parse(date);
	}

	/**
	 * 将HH:mm:ss格式的字符串转为日期类
	 *
	 * @param date
	 * @return 日期类
	 */
	public static Date HH_mm_ss(String date) throws ParseException {
		if (Strings.isNullOrEmpty(date)) {
			return null;
		}
		SimpleDateFormat hh1mm1ss = new SimpleDateFormat("HH:mm:ss");
		return hh1mm1ss.parse(date);
	}

	/**
	 * 将EEE MMM dd HH:mm:ss zzz yyyy格式的字符串转为日期类
	 *
	 * @param date
	 * @return 日期类
	 */
	public static Date EEEMMM(String date) throws ParseException {
		if (Strings.isNullOrEmpty(date)) {
			return null;
		}
		SimpleDateFormat eeemmm = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		return eeemmm.parse(date);
	}

	/**
	 * 由于部分金融机构（比如银联）的交易清算日期只有MMdd，为了能区分年，所以需要计算出跨年清算交易真正的清算年月日
	 *
	 * @param transTime
	 *            交易时间
	 * @param mmdd
	 *            交易由金融机构返回的清算日期
	 * @return 清算日期，yyyyMMdd格式
	 */
	public static Date toSettDate(Date transTime, String mmdd) throws ParseException {
		Date date = null;
		String settleDate = "";
		Calendar orderTime = Calendar.getInstance();
		orderTime.setTime(transTime);
		Calendar nextdate = Calendar.getInstance();
		nextdate.setTime(transTime);
		nextdate.add(Calendar.DAY_OF_MONTH, 1);
		if ((nextdate.get(Calendar.YEAR) > orderTime.get(Calendar.YEAR)) && (!MMdd(orderTime.getTime()).equals(mmdd))) {
			settleDate = nextdate.get(Calendar.YEAR) + "" + mmdd;
		}
		else {
			settleDate = orderTime.get(Calendar.YEAR) + "" + mmdd;
		}
		date = yyyyMMdd(settleDate);
		return date;
	}

	public static Date pattenToDate(String patten, String date) throws ParseException {
		if (Strings.isNullOrEmpty(date)) {
			return null;
		}
		SimpleDateFormat fmt = new SimpleDateFormat(patten);
		return fmt.parse(date);
	}

	public static Date fmt_yyyyMMddHHmmss(Date datetime) throws ParseException {
		return yyyyMMddHHmmss(yyyyMMddHHmmss(datetime));
	}

	public static String padSettleDate(String settDate) throws ParseException {

		if (settDate.length() == 4) {
			Calendar now = Calendar.getInstance();
			String year = DateUtil.yyyy(now.getTime());
			String date = DateUtil.MMdd(now.getTime());
			long dis = DateUtil.getDisInDay(now.getTime(), DateUtil.yyyyMMdd(DateUtil.yyyy(now.getTime()) + settDate));
			System.out.println(dis);
			if (dis > 1 && "0101".equals(date)) {// 跨年
				now.add(Calendar.YEAR, -1);
				year = DateUtil.yyyy(now.getTime());
			}
			settDate = year + settDate;
		}

		return settDate;
	}

	public static void main(String[] args) throws ParseException {

		System.out.println(padSettleDate("0102"));
	}
}
