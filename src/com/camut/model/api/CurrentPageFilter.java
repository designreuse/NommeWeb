package com.camut.model.api;

/**
 * @dao CurrentPageFilter.java
 * @author zyf
 * @createtime 6 29, 2015
 * @author
 * @updateTime
 * @memo
 */
public class CurrentPageFilter {

	private int currentPageIndex;
	private int rows;
	private String sort;//the sort clumn 排序字段
	private String order;// asc/desc
	private String search; // search key words 搜索框查询的关键字
	public int getCurrentPageIndex() {
		return currentPageIndex;
	}
	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	
}
