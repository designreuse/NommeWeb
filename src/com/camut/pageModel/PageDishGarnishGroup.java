package com.camut.pageModel;

import java.util.List;

public class PageDishGarnishGroup {
	public long garnishHeaderId;//配菜分类Id
	private String menu;//配菜分类名称
	public Integer showType;// 展现形式，0:单选 1：多选; 0:radio 1:checkbox
	public Integer isMust;// 是否必须，0:必须，1：不是必须
	public List<PageDishGarnish> pageDishGarnishList;
	public long getGarnishHeaderId() {
		return garnishHeaderId;
	}
	public void setGarnishHeaderId(long garnishHeaderId) {
		this.garnishHeaderId = garnishHeaderId;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public Integer getShowType() {
		return showType;
	}
	public void setShowType(Integer showType) {
		this.showType = showType;
	}
	public Integer getIsMust() {
		return isMust;
	}
	public void setIsMust(Integer isMust) {
		this.isMust = isMust;
	}
	public List<PageDishGarnish> getPageDishGarnishList() {
		return pageDishGarnishList;
	}
	public void setPageDishGarnishList(List<PageDishGarnish> pageDishGarnishList) {
		this.pageDishGarnishList = pageDishGarnishList;
	}

	
	
}
