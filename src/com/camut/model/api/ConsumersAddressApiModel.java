package com.camut.model.api;

import java.io.Serializable;

/**
 * @entity ConsumersAddressApiModel . 
 * @author zyf	
 * @createTime 2015-05-29
 * @author 
 * @updateTime 
 * @memo 
 */
public class ConsumersAddressApiModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5596032932563131857L;
	private long consumerId;
	private long addressId;
	private String street;// 街道
	private Integer isDefault;// 是否默认1:是,2否
	private String floor;// 楼
	private String city;// 城市
	private String province;// 省
	private String address;
	private String consignee;//收货人
	private String phone;//收件人电话 
	private double distance;//距离
	
	public long getAddressId() {
		return addressId;
	}
	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}
	public long getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(long consumerId) {
		this.consumerId = consumerId;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getAddress() {
		return this.street+" "+this.floor+" "+this.city+" "+this.province+" ("+this.distance+"KM Away)";
	}
	
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	/*@Override
	public String toString() {
		return "ConsumersAddressApiModel [consumerId=" + consumerId + ", addressId=" + addressId + ", street=" + street + ", isDefault=" + isDefault
				+ ", floor=" + floor + ", city=" + city + ", province=" + province + ", address=" + address + ", consignee=" + consignee + ", phone="
				+ phone + "]";
	}*/
	@Override
	public String toString() {
		return "ConsumersAddressApiModel [consumerId=" + consumerId + ", addressId=" + addressId + ", street=" + street + ", isDefault=" + isDefault 
				+ ", floor=" + floor + ", city=" + city + ", province=" + province + ", address=" + address + ", consignee=" + consignee + ", phone="
				+ phone + ", distance=" + distance + "]";
	}
	
	
	
}
