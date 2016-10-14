package com.pai.base.core.util.string;

import java.math.BigDecimal;
import java.util.Date;

import com.pai.base.core.util.date.DateConverter;

/**
 * @功能描述：字符串转换器
 * @开发公司：唯心六艺软件 - Craft6.cn
 * @作者：颜超敏
 * @创建时间：2014-06-04 15:58:45
 */
public class StringConverter {
	/**
	 * 字符串转换整型
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static Integer toInteger(String str) {
		try {
			return Integer.valueOf(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 字符串转换短整型
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static Short toShort(String str) {
		try {
			return Short.valueOf(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 字符串转换长整型
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static Long toLong(String str) {
		try {
			return Long.valueOf(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 字符串转换BigDecimal
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static BigDecimal toBigDecimal(String str) {
		try {
			return new BigDecimal(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 字符串转换为日期型
	 * 
	 * @param str
	 * @return
	 */
	public static Date toDate(String str) {
		try {
			return DateConverter.toDate(str);
		} catch (Exception e) {
			return null;
		}
	}
}
