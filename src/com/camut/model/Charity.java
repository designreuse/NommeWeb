package com.camut.model;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 慈善机构表
 * Charity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_charity")
public class Charity extends IdEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2995528286586949015L;
	private String charityName;//机构名称
	private String address;//地址
	private String phone;//电话
	private String description;//描述
	private String status;//状态

	@Column(name = "charity_name")
	public String getCharityName() {
		return this.charityName;
	}

	public void setCharityName(String charityName) {
		this.charityName = charityName;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	

}