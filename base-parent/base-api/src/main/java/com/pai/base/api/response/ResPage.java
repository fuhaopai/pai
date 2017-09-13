package com.pai.base.api.response;

import java.io.Serializable;
import java.util.List;

import com.pai.base.api.annotion.doc.AutoDocField;

/**
 * 响应分页信息
 *
 * @date 2017-06-09
 */
public class ResPage<T> implements Serializable{

	private static final long serialVersionUID = 5L;

	/**
     * 当前页码
     */
    @AutoDocField("当前页码")
    private Integer pageNum;

    /**
     * 页面大小
     */
    @AutoDocField("页面大小")
    private Integer pageSize;

    /**
     * 总数
     */
    @AutoDocField("总数")
    private Integer total;

    /**
     * 数据列表
     */
    @AutoDocField("数据列表")
    private List<T> dataList;
    
    public ResPage(){}
    
    public ResPage(Integer pageNum, Integer pageSize, Integer total,
			List<T> dataList) {
		super();
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.total = total;
		this.dataList = dataList;
	}

	public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        return "ResPage{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", dataList=" + dataList +
                '}';
    }
}
