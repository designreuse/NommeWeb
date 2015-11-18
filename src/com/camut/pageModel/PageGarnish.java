/**
 * 
 */
package com.camut.pageModel;

/**
 * @ClassName PageGarnish.java
 * @author wangpin
 * @createtime Jun 15, 2015
 * @author
 * @updateTime
 * @memo
 */
public class PageGarnish implements Comparable<PageGarnish> {

	private long id;//主键
	private String garnishName;// 配菜名称(ingredient name)
	private String garnishHeaderName;//所属配菜头名称
	private Integer garnishHeaderId;//所属配菜头id
	private Integer showType;// 展现形式，0:单选 1：多选; 0:radio 1:checkbox
	private Integer ismust;// 是否必须，0:必须，1：不是必须
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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getGarnishName() {
		return garnishName;
	}
	public void setGarnishName(String garnishName) {
		this.garnishName = garnishName;
	}
	public String getGarnishHeaderName() {
		return garnishHeaderName;
	}
	public void setGarnishHeaderName(String garnishHeaderName) {
		this.garnishHeaderName = garnishHeaderName;
	}
	public Integer getGarnishHeaderId() {
		return garnishHeaderId;
	}
	public void setGarnishHeaderId(Integer garnishHeaderId) {
		this.garnishHeaderId = garnishHeaderId;
	}
	/** @Title: compareTo
	 * @Description: 
	 * @param:    
	 * @return:   
	 */
	@Override
	public int compareTo(PageGarnish o) {
		return this.garnishName.compareTo(o.garnishName);
	}
	
	
}
