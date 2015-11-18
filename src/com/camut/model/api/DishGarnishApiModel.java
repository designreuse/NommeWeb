/**
 * 
 */
package com.camut.model.api;

/**
 * @ClassName DishGarnishApiModel.java
 * @author wangpin
 * @createtime Aug 29, 2015
 * @author
 * @updateTime
 * @memo
 */
public class DishGarnishApiModel {

	private int dishId;//菜品id
	private String garnishItemId;//配菜的id
	private int isMust;//是否必须
	private int maxCount;//最大数量
	private String garnishHeaderName;//配菜分类名称
	private int garnishHeaderId;//配菜分类id
	public int getDishId() {
		return dishId;
	}
	public void setDishId(int dishId) {
		this.dishId = dishId;
	}
	public String getGarnishItemId() {
		return garnishItemId;
	}
	public void setGarnishItemId(String garnishItemId) {
		this.garnishItemId = garnishItemId;
	}
	public int getIsMust() {
		return isMust;
	}
	public void setIsMust(int isMust) {
		this.isMust = isMust;
	}
	public int getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}
	public String getGarnishHeaderName() {
		return garnishHeaderName;
	}
	public void setGarnishHeaderName(String garnishHeaderName) {
		this.garnishHeaderName = garnishHeaderName;
	}
	public int getGarnishHeaderId() {
		return garnishHeaderId;
	}
	public void setGarnishHeaderId(int garnishHeaderId) {
		this.garnishHeaderId = garnishHeaderId;
	}
	
	
	
	
	
}
