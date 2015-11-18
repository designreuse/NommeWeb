package com.camut.model.api;

/**
 * @entity DistancePriceApiModel .
 * @author zyf
 * @createTime 2015-07-2
 * @author
 * @updateTime
 * @memo delivery fee based on the distance
 */
public class DistancePriceApiModel {

	private double startDistance;// 开始距离
	private double endDistance;// 结束距离
	private double orderPrice;// 订单价格
	private double upPrice;// 达到订单价格配送价
	private double notupPrice;// 没有达到订单价格配送价
	public double getStartDistance() {
		return startDistance;
	}
	public void setStartDistance(double startDistance) {
		this.startDistance = startDistance;
	}
	public double getEndDistance() {
		return endDistance;
	}
	public void setEndDistance(double endDistance) {
		this.endDistance = endDistance;
	}
	public double getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}
	public double getUpPrice() {
		return upPrice;
	}
	public void setUpPrice(double upPrice) {
		this.upPrice = upPrice;
	}
	public double getNotupPrice() {
		return notupPrice;
	}
	public void setNotupPrice(double notupPrice) {
		this.notupPrice = notupPrice;
	}

}
