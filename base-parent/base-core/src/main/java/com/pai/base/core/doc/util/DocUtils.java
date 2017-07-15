package com.pai.base.core.doc.util;

import java.lang.reflect.Field;

public class DocUtils {
	
	/**
	 * 将字符串第一个字母变大写
	 * 
	 */
	public static String upperCaseFirstWord(String word) {
		String first = word.substring(0, 1);
		return first.toUpperCase() + word.substring(1);
	}

	/**
	 * 判断属性是否有get方法
	 * 
	 */
	public static boolean isFieldHaveGetMethod(Class<?> c, Field f) {
		try {
			c.getDeclaredMethod("get" + upperCaseFirstWord(f.getName()));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断属性是否有set方法
	 * 
	 */
	public static boolean isFieldHaveSetMethod(Class<?> c, Field f) {
		try {
			c.getDeclaredMethod("set" + upperCaseFirstWord(f.getName()), f.getType());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
