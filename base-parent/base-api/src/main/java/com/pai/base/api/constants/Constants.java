package com.pai.base.api.constants;

/**
 * 
 * <pre> 
 * 构建组：base-api
 * 作者：fuhao
 * 日期：2016年12月28日-下午3:26:47
 * </pre>
 */
public class Constants {
	public final static String DEFAULT_ENCODING="UTF-8";
	public final static String PAI_AUTH_USER = "PAI_AUTH_USER";
	public final static String PAI_AUTH_RES_URL = "PAI_AUTH_RES_URL_";
	public final static String PAI_AUTH_RES = "PAI_AUTH_RES_";
	
	public static class EncryptType{
		public final static String MD5="md5";
		public final static String SHA="sha";
	} 
	
	public static class MAIL{
		public final static String SERVERIP = "mail.serverIp";
		public final static String PORT = "mail.port";
		public final static String SSL = "mail.ssl";
		public final static String USERNAME = "mail.username";
		public final static String PASSWORD = "mail.password";
		public final static String FROM = "mail.from";
		public final static String FROM_NAME = "mail.fromName";
		public final static String IS_DEBUG = "mail.isDebug";
	}
}
