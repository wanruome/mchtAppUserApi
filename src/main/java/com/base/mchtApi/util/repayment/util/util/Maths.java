package com.base.mchtApi.util.repayment.util.util;

import java.math.BigDecimal;

/**
 * 数字计算工具类
 * @author xie
 *
 */
public class Maths {

	/**
	 * 两个double数值相加
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double add(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 两个BigDecimal数值相加
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static BigDecimal add(BigDecimal d1, BigDecimal d2) {
		if (d1 == null) {
			d1 = BigDecimal.valueOf(0);
		}
		if (d2 == null) {
			d2 = BigDecimal.valueOf(0);
		}

		return d1.add(d2);
	}

	/**
	 * 两个double数值相减
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double sub(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 两个BigDecimal数值相减
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static BigDecimal sub(BigDecimal d1, BigDecimal d2) {
		if (d1 == null) {
			d1 = BigDecimal.valueOf(0);
		}

		if (d2 == null) {
			d2 = BigDecimal.valueOf(0);
		}

		return d1.subtract(d2);
	}

	/**
	 * 两个double数值相乘
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double mul(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 两个BigDecimal数值相乘
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static BigDecimal mul(BigDecimal d1, BigDecimal d2) {
		if (d1 == null) {
			d1 = BigDecimal.valueOf(0);
		}

		if (d2 == null) {
			d2 = BigDecimal.valueOf(0);
		}

		return d1.multiply(d2);
	}

	/**
	 * 两个double数值相除
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double div(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 两个BigDecimal数值相除，保留2位小数，四舍五入
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static BigDecimal div(BigDecimal d1, BigDecimal d2) {
		if (d1 == null) {
			d1 = BigDecimal.valueOf(0);
		}

		if (d2 == null) {
			d2 = BigDecimal.valueOf(0);
		}

		return d1.divide(d2, 2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 两个BigDecimal数值相除，保留scale位小数，四舍五入
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static BigDecimal div(BigDecimal d1, BigDecimal d2,int scale) {
		if (d1 == null) {
			d1 = BigDecimal.valueOf(0);
		}

		if (d2 == null) {
			d2 = BigDecimal.valueOf(0);
		}

		return d1.divide(d2, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 对double数值取scale位有效小数，并且四舍五入
	 * @param d1
	 * @param scale
	 * @return
	 */
	public static double round(double d1, int scale) {
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal one = new BigDecimal("1");
		return b1.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 对BigDecimal数值取scale位有效小数，并且四舍五入
	 * @param d1
	 * @param scale
	 * @return
	 */
	public static BigDecimal round(BigDecimal d1, int scale) {
		BigDecimal one = new BigDecimal("1");
		return d1.divide(one, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Maths.sub(new BigDecimal("100"), new BigDecimal("101")).doubleValue());
		System.out.println(Maths.round(Maths.mul(new BigDecimal(0.8188), new BigDecimal(100)),2));
		System.out.println(div(new BigDecimal(81.88), new BigDecimal(100),4));
	}
}
