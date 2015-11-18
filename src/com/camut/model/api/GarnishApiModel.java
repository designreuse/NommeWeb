package com.camut.model.api;

/**
 * @entity GarnishHeader . 
 * @author 王频
 * @createTime 2015-6-13
 * @author 
 * @updateTime 
 * @memo ingredients
 */
public class GarnishApiModel implements Comparable<GarnishApiModel>{

	//private long dishId;//菜品id
	private String garnishMenu;// 配菜分类名称
	private Integer showType;// 展现形式，0:单选 1：多选  2:下拉框   0:radio 1:checkbox 2:dropdown
	private Integer isMust;// 是否必须，
	private Integer maxCount;// 最多选择的个数
	private long garnishMenuId;//配菜分类id
	
	
	public Integer getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
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
	public Integer getIsMust() {
		return isMust;
	}
	public void setIsMust(Integer isMust) {
		this.isMust = isMust;
	}
	public long getGarnishMenuId() {
		return garnishMenuId;
	}
	public void setGarnishMenuId(long garnishMenuId) {
		this.garnishMenuId = garnishMenuId;
	}
	@Override
	public int compareTo(GarnishApiModel o) {
		// TODO Auto-generated method stub
		return (int) (this.garnishMenuId-o.garnishMenuId);
	}
	
	
	
}
