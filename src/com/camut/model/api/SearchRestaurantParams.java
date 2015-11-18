package com.camut.model.api;

public class SearchRestaurantParams {
	
	private Double restaurantLng;//
	private Double restaurantLat;//
	private Integer sortType;//
	private String classification;//
	private Integer currentPageIndex;//
	
	public Double getRestaurantLng() {
		return restaurantLng;
	}
	public void setRestaurantLng(Double restaurantLng) {
		this.restaurantLng = restaurantLng;
	}
	public Double getRestaurantLat() {
		return restaurantLat;
	}
	public void setRestaurantLat(Double restaurantLat) {
		this.restaurantLat = restaurantLat;
	}
	public Integer getSortType() {
		return sortType;
	}
	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public Integer getCurrentPageIndex() {
		return currentPageIndex;
	}
	public void setCurrentPageIndex(Integer currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}
	
	
  	/*经度：<input type="text" name="restaurantLng" value="-114.06415">
  	纬度：<input type="text" name="restaurantLat" value="51.04500">
  	排序方式（默认1：距离排序）：<input type="text" name="distance" value="1">
  	餐厅分类：<input type="text" name="classification" value="Italian,Pizza,Sea food">
  	第几页：<input type="text" name="currentPageIndex" value="1">
  	<!-- 菜系ID：<input type="checkbox" name="classificationId" value="1"> -->
		<input type="submit" value="提交" >*/

}
