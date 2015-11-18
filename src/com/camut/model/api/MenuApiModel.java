package com.camut.model.api;

import java.io.Serializable;

/**
 * @entity MenuApiModel . 
 * @author zyf	
 * @createTime 2015-05-29
 * @author 
 * @updateTime 
 * @memo 
 */
public class MenuApiModel implements Serializable,Comparable<MenuApiModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8622355594162470933L;
	private long id=0;
	private Integer restaurantId=1;// 商家编号
	private Integer dishId=1;//菜品编号
	private String chName="";// 菜品名称 繁体中文
	private String enIntro="";// 菜品介绍
	private double price=1;// 价格
	private Integer status=1;// 状态,1:正常 2：下架
	private Integer score=0;// 评分 
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Integer getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
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
	public String getEnIntro() {
		return enIntro;
	}
	public void setEnIntro(String enIntro) {
		this.enIntro = enIntro;
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
	public int compareTo(MenuApiModel o) {
		// TODO Auto-generated method stub
		return this.dishId-o.dishId;
	}
	
	
}
