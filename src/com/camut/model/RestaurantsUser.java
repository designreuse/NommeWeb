package com.camut.model;

// default package

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @entity RestaurantsUser .
 * @author 王频
 * @createTime 2015-05-25
 * @author
 * @updateTime
 * @memo
 */
@Entity
@Table(name = "tbl_restaurants_user", catalog = "nomme")
public class RestaurantsUser extends IdEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4005254613897374419L;
	private Restaurants restaurants;// 与商家多对一; many RestaurantsUser : 1
									// Restaurants
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
	// Property accessors

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurants_id")
	public Restaurants getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(Restaurants restaurants) {
		this.restaurants = restaurants;
	}

	@Column(name = "code", length = 10)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "first_name", length = 50)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", length = 50)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "password", length = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime", length = 10)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login_time", length = 10)
	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modon", length = 10)
	public Date getModon() {
		return this.modon;
	}

	public void setModon(Date modon) {
		this.modon = modon;
	}

	@Column(name = "modby", length = 200)
	public String getModby() {
		return this.modby;
	}

	public void setModby(String modby) {
		this.modby = modby;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "role", nullable = false)
	public Integer getRole() {
		return this.role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}
	
	@Column(name = "token")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Column(name = "devicetype")
	public String getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
	
	
}