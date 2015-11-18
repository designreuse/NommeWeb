package com.camut.pageModel;

import java.util.List;

public class PageOpentimeClassify {

	private List<PageOpenTime> deliveryOpentimeList;//外卖营业时间集合
	private List<PageOpenTime> pickupOpentimeList;//堂自取营业时间集合
	private List<PageOpenTime> reservationOpentimeList;//堂食营业时间集合
	public List<PageOpenTime> getDeliveryOpentimeList() {
		return deliveryOpentimeList;
	}
	public void setDeliveryOpentimeList(List<PageOpenTime> deliveryOpentimeList) {
		this.deliveryOpentimeList = deliveryOpentimeList;
	}
	public List<PageOpenTime> getPickupOpentimeList() {
		return pickupOpentimeList;
	}
	public void setPickupOpentimeList(List<PageOpenTime> pickupOpentimeList) {
		this.pickupOpentimeList = pickupOpentimeList;
	}
	public List<PageOpenTime> getReservationOpentimeList() {
		return reservationOpentimeList;
	}
	public void setReservationOpentimeList(
			List<PageOpenTime> reservationOpentimeList) {
		this.reservationOpentimeList = reservationOpentimeList;
	}
	
	
	
}
