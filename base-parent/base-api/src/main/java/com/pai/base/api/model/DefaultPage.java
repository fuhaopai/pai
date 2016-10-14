package com.pai.base.api.model;

public class DefaultPage implements Page{    
	
    /** 页号 */
    protected int pageNo = FIRST_PAGE;
    /** 分页大小 */
    protected int pageSize = DEFAULT_PAGE_SIZE;
    
    protected Integer totalCount;
    
    protected boolean isShowTotal;
    
    public DefaultPage(){}
    
    public DefaultPage(int pageSize_){
    	pageSize = pageSize_;
    }
    
    public DefaultPage(int pageNo_,int pageSize_){
    	this.pageNo = pageNo_;
    	this.pageSize = pageSize_;
    }
    
    public DefaultPage(int pageNo_,int pageSize_, boolean isShowTotal_){
    	this.pageNo = pageNo_;
    	this.pageSize = pageSize_;
    	this.isShowTotal = isShowTotal_;
    }
    
	public Integer getPageSize() {
		return pageSize;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotal() {
		if(isShowTotal){
			return totalCount;
		}
		throw new RuntimeException("total not support");
	}

	public Integer getPageNo() {
		return pageNo;
	}
	
	public void setShowTotal(boolean isShowTotal) {
		this.isShowTotal = isShowTotal;
	}

	public boolean isShowTotal() {
		return isShowTotal;
	}

	public Integer getStartIndex() {
        if(pageNo >= 1){
            return (pageNo-1) * pageSize;
        }
        return 0;
	}

	public boolean getIsLast() {
		if(totalCount==null || totalCount==0){
			return false;
		}
		if(pageNo*pageSize>=totalCount){
			return true;
		}
		return false;
	}

}
