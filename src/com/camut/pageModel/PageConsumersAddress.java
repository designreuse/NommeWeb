package com.camut.pageModel;

public class PageConsumersAddress {

	private long consumerId=0;
	private long addressId=0;
	private String street;// 街道
	private Integer isDefault;// 是否默认
	private String floor;// 楼
	private String city;// 城市
	private String province;// 省
	private String consignee;// 收货人
	private String zipcode;// 邮编
	private String phone;// 联系电话
	private String address="";
	private String fullAddress;//所有地址
	private String lat;//地址所在纬度
	private String lng;//地址所在经度
	private String memo;//订单备注
	private String isSaveAddress;//是否保存地址 1：是 0：否
	
	
	public String getAddress() {
		return this.street+" "+this.floor+" "+this.city+" "+this.province;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
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
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public long getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(long consumerId) {
		this.consumerId = consumerId;
	}
	public long getAddressId() {
		return addressId;
	}
	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}
	public String getFullAddress() {
		return fullAddress;
	}
	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getIsSaveAddress() {
		return isSaveAddress;
	}
	public void setIsSaveAddress(String isSaveAddress) {
		this.isSaveAddress = isSaveAddress;
	}
	
	
}
