package com.pai.base.api.model;

public interface Page {
	/**
	 * 第一页
	 */
	public final static int FIRST_PAGE = 1;
	 /**
     * 默认每页显示的记录数
     */
    public static final int DEFAULT_PAGE_SIZE = 20;
    
    /**
     * 返回每页大小
     * @return 
     */
    public Integer getPageSize();
    /**
     * 返回总页数
     * @return 
     */
    public Integer getTotal();
    /**
     * 返回总页码
     * @return 
     */
    public Integer getPageNo();
    /**
     * 是否显示总记录数
     * @return 
     */
    public boolean isShowTotal();
    /**
     * 获取当前页的偏移量
     * @return 
     */
    public Integer getStartIndex();
    
    public boolean getIsLast();
}
