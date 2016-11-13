package com.pai.base.db.api.constants;

public class OnlineUserIdHolder {
	private static ThreadLocal<String> onlineUserIdHolder	= new ThreadLocal<String>();
	
	public static String getUserId() {
		return onlineUserIdHolder.get();
	}

	public static void setUserId(String userId){
		onlineUserIdHolder.set(userId);
	}
}
