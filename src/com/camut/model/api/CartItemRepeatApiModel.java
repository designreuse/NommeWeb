package com.camut.model.api;

import java.util.ArrayList;
import java.util.List;

public class CartItemRepeatApiModel implements Comparable<CartItemRepeatApiModel>{

	private Integer dishId;// 菜品id
	private Integer num;// 数量
	private double unitprice;// 单价
	private String instruction;
	private List<CartDishGarnishRepeatApiModel> subItem = new ArrayList<CartDishGarnishRepeatApiModel>();
	
	public Integer getDishId() {
		return dishId;
	}
	public void setDishId(Integer dishId) {
		this.dishId = dishId;
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
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public List<CartDishGarnishRepeatApiModel> getSubItem() {
		return subItem;
	}
	public void setSubItem(List<CartDishGarnishRepeatApiModel> subItem) {
		this.subItem = subItem;
	}
	@Override
	public int compareTo(CartItemRepeatApiModel o) {
		// TODO Auto-generated method stub
		return this.dishId-o.dishId;
	}
	
}
