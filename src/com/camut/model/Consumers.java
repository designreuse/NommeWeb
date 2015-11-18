package com.camut.model;

// default package

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @entity Consumers . 
 * @author 王频
 * @createTime 2015-05-25
 * @author 
 * @updateTime 
 * @memo 
 */
@Entity
@Table(name = "tbl_consumers", catalog = "nomme")
public class Consumers extends IdEntity implements java.io.Serializable {

	@Override
	public String toString() {
		return "Consumers [email=" + email + ", password=" + password + ", phone=" + phone + ", otherCode=" + otherCode + ", loginType=" + loginType
				+ ", lastName=" + lastName + ", firstName=" + firstName + ", status=" + status + ", score=" + score + ", memo=" + memo
				+ ", mobileToken=" + mobileToken + ", regDate=" + regDate + ", lastLoginDate=" + lastLoginDate + ", mobileType=" + mobileType
				+ ", consumersAddressesSet=" + consumersAddressesSet + ", nickname=" + nickname + ", stripeCustomerId=" + stripeCustomerId + "]";
	}

	// Fields
	/**
	 * 
	 */
	private static final long serialVersionUID = 7326122489293145405L;
	private String email;// 登陆邮箱
	private String password;// 密码
	private String phone;// 联系电话
	private String otherCode;// 第三方id
	private Integer loginType;// 第三方类型
	private String lastName;// 姓
	private String firstName;// 名
	private Integer status;// 状态
	private double score;// 积分
	private String memo;// 备注
	private String mobileToken;// 设备号
	private Date regDate;// 注册时间
	private Date lastLoginDate;// 最后一次登陆时间
	private String mobileType;
	private Set<ConsumersAddress> consumersAddressesSet;//与地址一对多的关系
	private String nickname;//昵称
	private String stripeCustomerId;//stripe的客户编号
	// Property accessors

	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password", length = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "phone", length = 30)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "other_Code", length = 50)
	public String getOtherCode() {
		return this.otherCode;
	}

	public void setOtherCode(String otherCode) {
		this.otherCode = otherCode;
	}

	@Column(name = "login_type")
	public Integer getLoginType() {
		return this.loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	@Column(name = "lastname", length = 50)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "firstname", length = 50)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "score", precision = 18, scale = 3)
	public double getScore() {
		return this.score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	@Column(name = "memo", length = 200)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "mobile_token", length = 200)
	public String getMobileToken() {
		return this.mobileToken;
	}

	public void setMobileToken(String mobileToken) {
		this.mobileToken = mobileToken;
	}

	@Column(name = "mobile_type", length = 200)
	public String getMobileType() {
		return mobileType;
	}

	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "reg_date", length = 10)
	public Date getRegDate() {
		return this.regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "last_login_date", length = 10)
	public Date getLastLoginDate() {
		return this.lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="consumers")
	public Set<ConsumersAddress> getConsumersAddressesSet() {
		return consumersAddressesSet;
	}

	public void setConsumersAddressesSet(Set<ConsumersAddress> consumersAddressesSet) {
		this.consumersAddressesSet = consumersAddressesSet;
	}

	@Column(name = "nickname", length = 100)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(name = "stripe_customer_id")
	public String getStripeCustomerId() {
		return stripeCustomerId;
	}

	public void setStripeCustomerId(String stripeCustomerId) {
		this.stripeCustomerId = stripeCustomerId;
	}


}