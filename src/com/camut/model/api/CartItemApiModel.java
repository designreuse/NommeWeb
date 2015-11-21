package com.camut.model.api;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CartItemApiModel.java
 * @author wangpin
 * @createtime Jul 22, 2015
 * @author
 * @updateTime
 * @memo
 */
public class CartItemApiModel implements Comparable<CartItemApiModel> {

	private Integer dishId;// 菜品id
	private String dishName;//菜品名称
	private Integer num;// 数量
	private double unitprice;// 单价
	private String photoUrl; //菜品图片路径
	private double aDishTotalFee;//一个菜品的总价 (主菜配菜总价*数量）
	private Integer cartItemId;//购物车条目id
	private String dishRemark;//顾客对菜品的特殊需求备注信息
	private List<CartDishGarnishApiModel> subItem = new ArrayList<CartDishGarnishApiModel>(0);//与购物车配菜一对多
	private String showDish;
	private String instruction;//特殊需求
	
	
	public String getShowDish() {
		return showDish;
	}
	public void setShowDish(String showDish) {
		this.showDish = showDish;
	}
	public Integer getDishId() {
		return dishId;
	}
	public void setDishId(Integer dishId) {
		this.dishId = dishId;
	}
	public String getDishName() {
		return dishName;
	}
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public double getUnitprice() {
		return unitprice;
	}
	public void setUnitprice(double unitprice) {
		this.unitprice = unitprice;
	}
	public List<CartDishGarnishApiModel> getSubItem() {
		return subItem;
	}
	public void setSubItem(List<CartDishGarnishApiModel> subItem) {
		this.subItem = subItem;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public double getaDishTotalFee() {
		return aDishTotalFee;
	}
	public void setaDishTotalFee(double aDishTotalFee) {
		this.aDishTotalFee = aDishTotalFee;
	}
	public Integer getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(Integer cartItemId) {
		this.cartItemId = cartItemId;
	}
	public String getDishRemark() {
		return dishRemark;
	}
	public void setDishRemark(String dishRemark) {
		this.dishRemark = dishRemark;
	}
	//实现接口
	@Override
	public int compareTo(CartItemApiModel o) {
		// TODO Auto-generated method stub
		return (int)(o.dishId-this.dishId);
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	
	
}
