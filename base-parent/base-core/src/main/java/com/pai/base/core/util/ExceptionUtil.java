package com.pai.base.core.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * <pre> 
 * 描述：从exception中获得打印信息
 * 构建组：base-core
 * 作者：唯心
 * 邮箱:  craft6@qq.com
 * 日期:2014-8-5-下午6:47:19
 * 版权：唯心六艺工作室版权所有
 * </pre>
 */
public class ExceptionUtil {

	/**
	 * 获取exception的详细错误信息。
	 * 
	 * @param e
	 * @return
	 */
	public static String getExceptionMessage(Exception e) {

		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw, true));
		String str = sw.toString();

		return str;
	}
}
