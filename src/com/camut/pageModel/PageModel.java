package com.camut.pageModel;

import java.io.Serializable;
import java.util.List;

public class PageModel implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3653150286812547858L;

	//rows of every pages每页显示数量
	private Integer pageSize = 20;

	//current page当前页数
	private Integer currentPageIndex = 0;

	//search result 查询结果集
	private List<?> rows;

	//total number of 总记录数
	private long total;

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public Integer getPageSize() {
		return pageSize;
	}
	
	public Integer getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(Integer currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	@Override
	public String toString() {
		return "PageModel [pageSize=" + pageSize + ", currentPageIndex=" + currentPageIndex + ", rows=" + rows + ", total=" + total + "]";
	}
	
	

}
