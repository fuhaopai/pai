package com.pai.base.core.util;

public class DirUtils {
	/**
	 * 获得上一级的目录
	 * @param currentDirPath
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String getUpLevelDir(String currentDirPath){
		String str = currentDirPath.substring(0, currentDirPath.length() - 1);
		String upLevelDir = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		return upLevelDir;
	}
	
}
