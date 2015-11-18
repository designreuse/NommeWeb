package com.camut.pageModel;

public class PageFilter implements java.io.Serializable {

	private int offset;//current page 偏移数据量
	private int limit;//rows of per page 每页显示记录数
	private String sort;//the sort column 排序字段
	private String order;// asc/desc
	private String search; // search key words 搜索框查询的关键字
	

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
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
