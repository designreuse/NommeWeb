package com.camut.model.api;

import java.util.Date;

public class RestaurantsUserApiModel {

	private long restaurantId; //店家id
	private String code;// 员工工号; staff code
	private String firstName;// 名
	private String lastName;// 姓
	private String password;// 密码
	private Integer status;//  -1为无效删除，0为有效(说明已经审核通过)，1待审核（新注册的商家状态为1）,2 冻结 、未审核通过-1:invalid 1:valid/normal
	private Date createtime;// 创建时间
	private Date lastLoginTime;// 最后登录时间
	private Date modon;// 操作时间
	private String modby;// 操作人
	private Integer type;// 员工类型,1代表管理员，2代表普通员工
	private Integer role;//权限，001只有预定权限，011表示预定和外卖权限，111三种权限都有，100只有自取权限
	private String token;//设备编号
	private String devicetype;//设备类型
	public long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Date getModon() {
		return modon;
	}
	public void setModon(Date modon) {
		this.modon = modon;
	}
	public String getModby() {
		return modby;
	}
	public void setModby(String modby) {
		this.modby = modby;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
	
	
	
}
