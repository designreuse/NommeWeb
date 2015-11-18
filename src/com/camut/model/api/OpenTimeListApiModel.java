package com.camut.model.api;

public class OpenTimeListApiModel {

	private String openTime;  //营业时间
	private Integer week;  // 星期 
	private Integer type;//类型
	
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	

}
