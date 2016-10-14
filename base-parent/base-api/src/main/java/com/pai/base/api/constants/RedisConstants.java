package com.pai.base.api.constants;

public class RedisConstants {	
	/**
	 * system数据库
	 */
	public final static int SYSTEM=0;
	/**
	 * product数据库
	 */
	public final static int PRODUCT=1;
	/**
	 * API系统的OnlineEntity
	 */
	public final static int ONLINE_ENTITY=2;	
	/**
	 * 购物车数据库
	 */
	public final static int CART = 3;
	/**
	 * member数据库
	 */
	public final static int MEMBER=4;
	/**
	 * member数据库
	 */
	public final static int EAV=5;
	/**
	 * 产品浏览轨迹和分析数据库
	 */
	public final static int DA=6;
	
	/**
	 * 访问来源轨迹库
	 */
	public final static int FROM_TRACK=7;
	/**
	 * BBS项目
	 */
	public final static int BBS=8;
	
	/**
	 * 同步服务错误日志库
	 */
	public final static int SYNC_ERROR_lOG = 15;
	
	/***
	 * SKGCacheEvict.type常量属性
	 */
	public final static String EVICT_ALL_TYPE="all";
	/***
	 * SKGCacheEvict.type常量属性
	 */
	public final static String EVICT_KEY_TYPE="key";
	/***
	 * SKGCacheEvict.type常量属性
	 */
	public final static String EVICT_PREFIX_TYPE="prefix";
	
}
