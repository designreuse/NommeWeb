/**
 * 
 */
package com.camut.pageModel;

import java.util.List;

/**
 * @ClassName PageDishGarnish.java
 * @author wangpin
 * @createtime Jun 26, 2015
 * @author
 * @updateTime
 * @memo
 */
public class PageDishGarnish implements Comparable<PageDishGarnish>{

	private long id;//配菜的id
	private String name;//配菜名称
	private String menu;//配菜分类名称
	private double addprice;//增加的价格
	
	//---------------------------------------
	private long garnishHeaderId;//配菜分类Id
	private String garnishMenu;// 配菜分类名称
	private Integer showType;// 展现形式，0:单选 1：多选; 0:radio 1:checkbox
	private Integer isMust;// 是否必须，0:必须，1：不是必须
	private Integer count;// 最多选择的个数
	private List<PageGarnishItem> pageGarnishItemList;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public double getAddprice() {
		return addprice;
	}
	public void setAddprice(double addprice) {
		this.addprice = addprice;
	}
	public long getGarnishHeaderId() {
		return garnishHeaderId;
	}
	public void setGarnishHeaderId(long garnishHeaderId) {
		this.garnishHeaderId = garnishHeaderId;
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
	public List<PageGarnishItem> getPageGarnishItemList() {
		return pageGarnishItemList;
	}
	public void setPageGarnishItemList(List<PageGarnishItem> pageGarnishItemList) {
		this.pageGarnishItemList = pageGarnishItemList;
	}
	public String getGarnishMenu() {
		return garnishMenu;
	}
	public void setGarnishMenu(String garnishMenu) {
		this.garnishMenu = garnishMenu;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	/* @Title: compareTo
	 * @Description: 
	 * @param:    
	 * @return:   
	 */
	@Override
	public int compareTo(PageDishGarnish o) {
		return (int)(this.id-o.id);
	}
	
	
}
