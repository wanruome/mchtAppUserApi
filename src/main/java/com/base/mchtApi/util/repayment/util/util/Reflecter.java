package com.base.mchtApi.util.repayment.util.util;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * 反射工具库
 *
 * @author xie
 */
@Slf4j
public class Reflecter {

	// 变量以_开头的，表示临时变量
	// 变量以_结束的，去掉最后的_

	// 支持int|long|boolean|byte|short|float|char|
	// Integer|Long|Boolean|Byte|Short|Float|String|Date|BigDecimal|BigInteger|List|

	/**
	 * 判断是否基本的数据类型，可用于反射机制，如是则直接变为字符串；否则需要深层反射
	 *
	 * @param gt
	 * @return
	 */
	public static boolean isBaseType(Type gt) {

		return gt.equals(int.class) || gt.equals(long.class) || gt.equals(double.class) || gt.equals(boolean.class)
				|| gt.equals(byte.class) || gt.equals(short.class) || gt.equals(float.class) || gt.equals(char.class)
				|| gt.equals(java.lang.Integer.class) || gt.equals(java.lang.Long.class)
				|| gt.equals(java.lang.Double.class) || gt.equals(java.lang.Boolean.class)
				|| gt.equals(java.lang.Byte.class) || gt.equals(java.lang.Short.class)
				|| gt.equals(java.lang.Float.class) || gt.equals(java.lang.Character.class)
				|| gt.equals(java.lang.String.class) || gt.equals(java.math.BigDecimal.class)
				|| gt.equals(java.math.BigInteger.class);
	}

	private static boolean isIgnore(Type gt, Class<?>[] ignores) {

		if (ignores != null) {

			for (Class<?> ignore : ignores) {

				if (gt.equals(ignore)) {
					return true;
				}
			}
		}

		return gt.equals(Log.class);
	}

	/**
	 * 判断是否数字类型的对象，对于自动组装sql的需求里，如果是数字类型的对象，则sql语句不需要对该对象的值加引号
	 *
	 * @param gt
	 * @return
	 */
	public static boolean isNumberType(Type gt) {

		return gt.equals(int.class) || gt.equals(long.class) || gt.equals(double.class) || gt.equals(short.class)
				|| gt.equals(float.class) || gt.equals(java.lang.Integer.class) || gt.equals(java.lang.Long.class)
				|| gt.equals(java.lang.Double.class) || gt.equals(java.lang.Short.class)
				|| gt.equals(java.lang.Float.class) || gt.equals(java.math.BigDecimal.class)
				|| gt.equals(java.math.BigInteger.class);
	}

	/**
	 * 判断是否日期的数据类型，可用于反射机制，如是则可以通过DateUtil类变换为一定格式的字符串
	 *
	 * @param gt
	 * @return
	 */
	public static boolean isDate(Type gt) {

		if (gt instanceof Class) {

			Class<?> c = (Class<?>) gt;

			return c.equals(java.util.Date.class) || c.equals(java.sql.Date.class) || c.equals(java.sql.Time.class)
					|| c.equals(java.sql.Timestamp.class) || c.getName().equals("com.ibm.db2.jcc.DBTimestamp");// ibm
																												// jdk
		}
		else {

			return gt.equals(java.util.Date.class) || gt.equals(java.sql.Date.class) || gt.equals(java.sql.Time.class)
					|| gt.equals(java.sql.Timestamp.class) || gt.equals("com.ibm.db2.jcc.DBTimestamp");// ibm
																										// jdk
		}
	}

	/**
	 * 判断是否集合类
	 *
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public static boolean isCollection(Class<?> c) throws Exception {

		return Collection.class.isAssignableFrom(c);

	}

	/**
	 * 判断是否map类
	 *
	 * @param c
	 * @return
	 */
	public static boolean isMap(Class<?> c) {

		return Map.class.isAssignableFrom(c);

	}

	/**
	 * 字符串化一个对象，对日期类型的对象通过DataUtil类进行转化
	 *
	 * @param o
	 * @return
	 */
	public static String toString(Object o) {

		if (o == null) {
			return "";
		}

		Type gt = o.getClass();
		if (isNumberType(gt)) {
			return o.toString();
		}
		else if (isDate(gt)) {
			return DateUtil.yyyy_MM_dd_HH_mm_ss((Date) o);
		}
		else {
			return o.toString();
		}
	}

	/**
	 * 对一个对象进行组装sql语句时的值映射，可用于对db对象进行sql化时的操作
	 *
	 * @param o
	 * @return
	 */

	/**
	 * 根据该对象参数对应db的类型，将该对象参数转化为db对象
	 *
	 * @param typeName
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static Object toDbObject(String typeName, Object value) throws Exception {

		if ("BIGINT".equalsIgnoreCase(typeName)) {
			return Long.parseLong(value.toString());
		}
		else if ("DECIMAL".equalsIgnoreCase(typeName)) {
			return new BigDecimal(value.toString());
		}
		else if ("INTEGER".equalsIgnoreCase(typeName)) {
			return Integer.parseInt(value.toString());
		}
		else if ("SMALLINT".equalsIgnoreCase(typeName)) {
			return Short.parseShort(value.toString());
		}
		else if ("TIMESTAMP".equalsIgnoreCase(typeName)) {
			if (Strings.isNullOrEmpty(value)) {
				return null;
			}
			else if ("NULL".equalsIgnoreCase(value.toString())) {
				return null;
			}
			else if ("CURRENT TIMESTAMP".equalsIgnoreCase(value.toString())) {
				return new Date();
			}
			else {
				return DateUtil.yyyy_MM_dd_HH_mm_ss(value.toString());
			}
		}

		return value;
	}

	/**
	 * 根据该字符串参数对应db的类型，将该字符串参数转化为db对象
	 *
	 * @param typeName
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static Object toDbObject(String typeName, String value) throws Exception {

		return toDbObject(typeName, (Object) value);
	}

	/**
	 * 在java与db的相互反射中，对于java的临时变量，以_开头；对于java语法的关键字，以_结尾（比如default）
	 *
	 * @param c
	 * @return
	 */
	public static ArrayList<String> getUsefulFiledName(Class<?> c) {

		ArrayList<String> ret = new ArrayList<String>();

		Field[] fs = c.getDeclaredFields();
		for (Field f : fs) {

			String fn = f.getName();
			if (fn.startsWith("_")) {
				continue;
			}
			if (fn.endsWith("_")) {
				fn = fn.substring(0, fn.length() - 1);
			}

			ret.add(fn);
		}

		return ret;
	}

	// 通过setter获取单个obj
	/**
	 * 在db值以java hashMap形式向java bean的反射中， 将haspMap values的key与class c的变量对应起来，
	 * 将values的value赋予c的值，args是c构造函数的参数
	 *
	 * @param c
	 * @param values
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static Object setter(Class<?> c, HashMap<String, Object> values, Object... args) throws Exception {

		log.debug("reflect a class:" + c.getName());

		Object o = makeInstance(c.getName(), args);

		Field[] fs = c.getDeclaredFields();
		for (Field f : fs) {
			String fn = f.getName();
			Object val = values.get(fn);
			if (val == null && fn.endsWith("_")) {
				val = values.get(fn.substring(0, fn.length() - 1));
			}
			if (val == null) {
				val = values.get(fn.toUpperCase());
			}
			if (val == null && fn.endsWith("_")) {
				val = values.get(fn.substring(0, fn.length() - 1).toUpperCase());
			}

			log.debug("field name-value:" + fn + "-" + val);

			if (f != null && !Strings.isNullOrEmpty(val) && !f.toGenericString().contains(" final ")) {

				Type gt = f.getGenericType();

				boolean af = f.isAccessible();
				f.setAccessible(true);

				if (gt.equals(int.class) || gt.equals(java.lang.Integer.class)) {
					f.set(o, Integer.parseInt(val.toString()));
				}
				else if (gt.equals(long.class) || gt.equals(java.lang.Long.class)) {
					f.set(o, Long.parseLong(val.toString()));
				}
				else if (gt.equals(double.class) || gt.equals(java.lang.Double.class)) {
					f.set(o, Double.parseDouble(val.toString()));
				}
				else if (gt.equals(boolean.class) || gt.equals(java.lang.Boolean.class)) {
					f.set(o, Boolean.parseBoolean(val.toString()));
				}
				else if (gt.equals(byte.class) || gt.equals(java.lang.Byte.class)) {
					f.set(o, Byte.parseByte(val.toString()));
				}
				else if (gt.equals(short.class) || gt.equals(java.lang.Short.class)) {
					f.set(o, Short.parseShort(val.toString()));
				}
				else if (gt.equals(float.class) || gt.equals(java.lang.Float.class)) {
					f.set(o, Float.parseFloat(val.toString()));
				}
				else if (gt.equals(char.class) || gt.equals(java.lang.Character.class)) {
					f.set(o, val.toString().toCharArray()[0]);
				}
				else if (gt.equals(java.lang.String.class)) {
					f.set(o, val.toString());
				}
				else if (gt.equals(java.math.BigDecimal.class)) {
					f.set(o, new BigDecimal(val.toString()));
				}
				else if (gt.equals(java.math.BigInteger.class)) {
					f.set(o, new BigInteger(val.toString()));
				}
				else if (isDate(gt)) {
					f.set(o, DateUtil.yyyy_MM_dd_HH_mm_ss(val.toString()));
				}

				f.setAccessible(af);
			}
		}

		return o;
	}

	// 通过setter获取单个obj
	/**
	 * 在db值(单记录)以java arrayList(key和value)形式向java bean的反射中， 将arrayList key与class c的变量对应起来，
	 * 将arrayList的value赋予c的值，args是c构造函数的参数
	 *
	 * @param c
	 * @param cols
	 * @param vals
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static Object setter(Class<?> c, ArrayList<String> cols, ArrayList<Object> vals, Object... args)
			throws Exception {

		if (cols.size() != vals.size()) {
			log.warn("cols size invalid by vals size,cs:" + cols.size() + " by vs: " + vals.size());
			return null;
		}

		HashMap<String, Object> values = new HashMap<String, Object>();
		for (int i = 0; i < cols.size(); i++) {
			values.put(cols.get(i), vals.get(i));
		}

		return setter(c, values);
	}

	// 通过setter获取obj集合
	/**
	 * 在db值(多记录)以java arrayList(key和value)形式向java bean及hashMap的反射中， 将arrayList cols与class c的变量对应起来，
	 * 将arrayList的vals赋予c的值并形成hashMap，args是c构造函数的参数
	 *
	 * @param c
	 * @param keyName
	 * @param cols
	 * @param vals
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String, Object> setter(Class<?> c, String keyName, ArrayList<String> cols,
			ArrayList<Map<String, ArrayList<Object>>> vals, Object... args) throws Exception {

		if (cols.size() != vals.get(0).size()) {
			log.warn("cols size invalid by vals size,cs:" + cols.size() + " by vs(0): " + vals.get(0).size());
			return null;
		}

		HashMap<String, Object> ret = new HashMap<String, Object>();

		int keyIndex = 0;
		for (int i = 0; i < cols.size(); i++) {
			if (keyName.equalsIgnoreCase(cols.get(i))) {
				keyIndex = i;
				break;
			}
		}

		for (int i = 0; i < vals.size(); i++) {
			for (Map.Entry<String, ArrayList<Object>> entity : vals.get(i).entrySet()) {
				ArrayList<Object> val = entity.getValue();
				String key = val.get(keyIndex).toString();

				HashMap<String, Object> values = new HashMap<String, Object>();
				for (int j = 0; j < cols.size(); j++) {
					values.put(cols.get(j), val.get(j));
				}

				ret.put(key, setter(c, values));
			}
		}

		return ret;
	}

	/**
	 * 通过反射机制制造类c的实例
	 *
	 * @param c
	 *            类c
	 * @param args
	 *            类c构造函数的变量
	 * @return
	 * @throws Exception
	 */
	public static Object makeInstance(Class<?> c, Object... args) throws Exception {

		if (args == null || args.length == 0) {
			return c.newInstance();
		}

		Constructor<?>[] cts = c.getDeclaredConstructors();
		for (Constructor<?> ct : cts) {

			Class<?>[] pts = ct.getParameterTypes();
			if (args != null && args.length == pts.length) {

				log.debug(c.getName() + " param length:" + args.length);
				return c.getConstructor(pts).newInstance(args);
			}
		}

		return c.newInstance();
	}

	/**
	 * 通过反射机制制造类className的实例
	 *
	 * @param className
	 *            类c
	 * @param args
	 *            类c构造函数的变量
	 * @return
	 * @throws Exception
	 */
	public static Object makeInstance(String className, Object... args) throws Exception {

		log.debug("make instance:" + className);

		Class<?> c = Class.forName(className);

		return makeInstance(c, args);
	}

	/**
	 * 将json格式的字符串转化为hashMap
	 *
	 * @param datas
	 * @return
	 */
	// public static Map<String, Object> jsonToMap(String datas) {
	//
	// JSONObject obj = JSONObject.parseObject(datas);
	//
	// return jsonToMap(obj);
	// }

	// /**
	// * 将json对象转化为hashMap
	// *
	// * @param obj
	// * @return
	// */
	// public static Map<String, Object> jsonToMap(JSONObject obj) {
	//
	// Map<String, Object> ret = new HashMap<String, Object>();
	//
	// if (obj == null)
	// return ret;
	//
	// Set<String> keys = obj.keySet();
	// for (String key : keys) {
	// Object value = obj.get(key);
	// try {
	//
	// if (key.toLowerCase().endsWith("time") || key.toLowerCase().endsWith("date")) {
	// String[] formats = { "yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd",
	// "yyyyMMdd",
	// "yyyyMMddHHmmss" };
	// for (int i = 0; i < formats.length; i++) {
	// try {
	// if (formats[i].length() == Strings.trim(obj.get(key)).length()) {
	// SimpleDateFormat sdf = new SimpleDateFormat(formats[i]);
	// ret.put(key, sdf.parse(Strings.trim(obj.get(key))));
	// break;
	// }
	// }
	// catch (Exception e) {
	// log.error(Strings.toString(e));
	// }
	// }
	// if (null == ret.get(key)) {
	// ret.put(key, value);
	// }
	// }
	// else {
	// ret.put(key, value);
	// }
	// }
	// catch (Exception e) {
	// log.error(e);
	// ret.put(key, value);
	// }
	// }
	//
	// return ret;
	// }

	/**
	 * 将json对象转化为Map
	 *
	 * @param obj
	 * @return
	 */
	public static Map<String, String> jsonToMapStr(String datas) {
		Map<String, String> ret = new HashMap<String, String>();
		try {
			JSONObject obj = JSONObject.parseObject(datas);
			if (null != obj) {
				Set<String> keys = obj.keySet();
				for (String key : keys) {
					Object value = obj.get(key);
					if (null == value) {
						value = "";
					}
					ret.put(key, Strings.toString(value));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 合并两个集合a和b，b放于a的后面
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] concat(T[] a, T[] b) {

		final int alen = a.length;
		final int blen = b.length;
		if (alen == 0) {
			return b;
		}
		if (blen == 0) {
			return a;
		}
		final T[] result = (T[]) Array.newInstance(a.getClass().getComponentType(), alen + blen);
		System.arraycopy(a, 0, result, 0, alen);
		System.arraycopy(b, 0, result, alen, blen);
		return result;

	}

	/**
	 * 获取map的值集合
	 *
	 * @param map
	 * @return
	 */
	public static Collection<?> getMapValues(Map<?, ?> map) {

		List<Object> list = new ArrayList<Object>();

		Iterator<?> iter = map.values().iterator();

		while (iter.hasNext()) {
			list.add(iter.next());
		}

		return list;
	}

	/**
	 * 克隆一个集合
	 *
	 * @param src
	 *            源集合
	 * @param subClass
	 *            子类
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Collection<?> cloneCollection(Collection<?> src, Class<?> subClass) throws Exception {

		if (src == null) {
			return null;
		}

		Collection<Object> ret = (Collection<Object>) makeInstance(src.getClass());

		Iterator<?> iter = src.iterator();

		while (iter.hasNext()) {

			ret.add(clone(iter.next(), null, subClass));
		}

		return ret;
	}

	/**
	 * 克隆一个集合
	 *
	 * @param src
	 *            源集合
	 * @return
	 * @throws Exception
	 */
	public static Collection<?> cloneCollection(Collection<?> src) throws Exception {
		if (src == null) {
			return null;
		}
		Class<?> subClass = src.iterator().next().getClass();
		return cloneCollection(src, subClass);
	}

	/**
	 * 克隆一个map
	 *
	 * @param src
	 *            源map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<?, ?> cloneMap(Map<?, ?> src) throws Exception {

		if (src == null) {
			return null;
		}

		Map<Object, Object> ret = (Map<Object, Object>) makeInstance(src.getClass());
		Set<?> keys = src.keySet();

		for (Object key : keys) {
			ret.put(clone(key), clone(src.get(key)));
		}

		return ret;
	}

	/**
	 * 克隆一个对象； 被克隆对象的变量，基础类型、时间、集合、map、对象及其变量均可能被克隆
	 *
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static Object clone(Object src) throws Exception {

		if (src == null) {
			return null;
		}

		return clone(src, null, src.getClass());
	}

	/**
	 * 克隆一个对象，并忽略克隆该对象的一些变量； 被克隆对象的变量，基础类型、时间、集合、map、对象及其变量均可能被克隆
	 *
	 * @param src
	 *            源对象
	 * @param ignores
	 *            被忽略克隆的源对象的变量的类名
	 * @return
	 * @throws Exception
	 */
	public static Object clone(Object src, Class<?>[] ignores) throws Exception {

		if (src == null) {
			return null;
		}

		return clone(src, ignores, src.getClass());
	}

	/**
	 * 克隆一个对象的内容到另一个对象，要求两个对象的变量名称具有重合特性； 被克隆对象的变量，基础类型、时间、集合、map、对象及其变量均可能被克隆
	 *
	 * @param src
	 *            源对象
	 * @param ignores
	 *            被忽略克隆的源对象的变量的类名
	 * @param destClass
	 *            目标对象的类
	 * @param destObjectArgs
	 *            目标对象类构造函数的参数
	 * @return
	 * @throws Exception
	 */
	public static Object clone(Object src, Class<?>[] ignores, Class<?> destClass, Object... destObjectArgs)
			throws Exception {

		if (src == null) {
			return null;
		}

		if (isIgnore(src.getClass(), ignores)) {
			return src;
		}

		if (isBaseType(src.getClass()) || isDate(src.getClass())) {
			return src;
		}

		log.debug("clone a class:" + src.getClass() + "->" + destClass);

		Object dest = makeInstance(destClass, destObjectArgs);

		Field[] fsSrc = concat(src.getClass().getDeclaredFields(), src.getClass().getSuperclass().getDeclaredFields());
		Field[] fsTmp = concat(destClass.getDeclaredFields(), destClass.getSuperclass().getDeclaredFields());
		HashMap<String, Field> fsDes = new HashMap<String, Field>();
		for (Field f : fsTmp) {
			fsDes.put(f.getName(), f);
		}
		for (Field fSrc : fsSrc) {

			boolean afSrc = fSrc.isAccessible();
			fSrc.setAccessible(true);
			try {
				Field f = fsDes.get(fSrc.getName());

				Object val = fSrc.get(src);
				if (f == null) {
					log.debug("field is null :" + fSrc.getName());
				}
				if (val == null) {
					log.debug("value is null :" + fSrc.getName());
				}

				if (f != null && val != null && !f.toGenericString().contains(" final ")) {

					Type gt = f.getGenericType();

					boolean af = f.isAccessible();
					f.setAccessible(true);

					if (isBaseType(gt) || isDate(gt)) {
						f.set(dest, val);
					}
					else if (isCollection(val.getClass())) {

						f.set(dest, cloneCollection((Collection<?>) val));
					}
					else if (isMap(val.getClass())) {
						f.set(dest, cloneMap((Map<?, ?>) val));
					}
					else {
						Object o = clone(val, ignores, val.getClass());
						f.set(dest, o);
					}

					f.setAccessible(af);
				}
			}
			catch (Exception e) {
				log.error(Strings.toString(e));
			}
			finally {
				fSrc.setAccessible(afSrc);
			}
		}

		return dest;
	}
}
