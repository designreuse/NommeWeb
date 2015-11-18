/**
 * 
 */
package com.camut.pageModel;

/**
 * @ClassName PageGarnishHeader.java
 * @author wangpin
 * @createtime Jun 15, 2015
 * @author
 * @updateTime
 * @memo
 */
public class PageGarnishHeader implements Comparable<PageGarnishHeader> {

	private long id;//主键
	private String garnishMenu;// 配菜分类名称
	private Integer showType;// 展现形式，0:单选 1：多选; 0:radio 1:checkbox
	private Integer ismust;// 是否必须，0:必须，1：不是必须
	private Integer count;//最多选择的数量
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getGarnishMenu() {
		return garnishMenu;
	}
	public void setGarnishMenu(String garnishMenu) {
		this.garnishMenu = garnishMenu;
	}
	public Integer getShowType() {
		return showType;
	}
	public void setShowType(Integer showType) {
		this.showType = showType;
	}
	public Integer getIsmust() {
		return ismust;
	}
	public void setIsmust(Integer ismust) {
		this.ismust = ismust;
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
	public int compareTo(PageGarnishHeader o) {
		return (int)(this.id-o.id);
	}
	
	
	
}
