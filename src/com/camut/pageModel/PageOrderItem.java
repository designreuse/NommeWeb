package com.camut.pageModel;

import java.util.Set;

public class PageOrderItem implements Comparable<PageOrderItem>{
	private Integer orderHeaderId; //订单头id
	private Integer dishId;// 菜品编号
	private String enName;// 菜品名称
	private String chName;// 菜品名称 繁体中文
	private String instruction;// 特殊需求
	private Integer num;// 数量
	private double price;// 单价
	private Integer status;// 1:未支付 2：已支付 3：取消 4：已退款 5：配送中 6：完成; 1:not paid
							// 2:paid 3:cancel 4:refund 5:on the way 6:done
	private Set<PageGarnishItem> pageGarnishItemsSet; // 与配菜分类多对多;
	
	public Integer getOrderHeaderId() {
		return orderHeaderId;
	}
	public void setOrderHeaderId(Integer orderHeaderId) {
		this.orderHeaderId = orderHeaderId;
	}
	public Integer getDishId() {
		return dishId;
	}
	public void setDishId(Integer dishId) {
		this.dishId = dishId;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getChName() {
		return chName;
	}
	public void setChName(String chName) {
		this.chName = chName;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
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
	public Set<PageGarnishItem> getPageGarnishItemsSet() {
		return pageGarnishItemsSet;
	}
	public void setPageGarnishItemsSet(Set<PageGarnishItem> pageGarnishItemsSet) {
		this.pageGarnishItemsSet = pageGarnishItemsSet;
	}
	@Override
	public int compareTo(PageOrderItem o) {
		// TODO Auto-generated method stub
		return this.orderHeaderId-o.orderHeaderId;
	}
	
	
	

}
