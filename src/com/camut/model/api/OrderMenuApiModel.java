package com.camut.model.api;

/**
 * @entity OrderListApiModel . 
 * @author zyf	
 * @createTime 2015-06-08
 * @author 
 * @updateTime 
 * @memo 
 */
public class OrderMenuApiModel implements Comparable<OrderMenuApiModel>{
	
	private long id;//菜品ID
	private String restaurantUuid=""; //店铺编号
	private Integer dishId=1; //菜品编号
	private String chName="";// 菜品名称 繁体中文
	private double price=1.2;// 价格
	private Integer status=1;// 状态,1:正常 2：下架
	private Integer score;// 评分 rating
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRestaurantUuid() {
		return restaurantUuid;
	}
	public void setRestaurantUuid(String restaurantUuid) {
		this.restaurantUuid = restaurantUuid;
	}
	public Integer getDishId() {
		return dishId;
	}
	public void setDishId(Integer dishId) {
		this.dishId = dishId;
	}
	public String getChName() {
		return chName;
	}
	public void setChName(String chName) {
		this.chName = chName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	@Override
	public int compareTo(OrderMenuApiModel o) {
		// TODO Auto-generated method stub
		return this.dishId-o.dishId;
	}
	
}
