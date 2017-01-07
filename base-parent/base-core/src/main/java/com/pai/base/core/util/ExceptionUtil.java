package com.pai.base.core.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * <pre> 
 * 描述：从exception中获得打印信息
 * 构建组：base-core
 * 开发公司:π
 * 开发人员:FUHAO
 * 创建时间:2016-09-29 18:00:11
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
