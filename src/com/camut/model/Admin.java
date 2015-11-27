package com.camut.model;

// default package

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @entity Admin . 
 * @author 王频
 * @createTime 2015-05-25
 * @author 
 * @updateTime 
 * @memo 
 */
@Entity
@Table(name = "sys_admin")
public class Admin extends IdEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 620394634070144408L;
	// Fields

	private String loginname;// 登录名
	private String firstName;// 名
	private String lastName;// 姓
	private String password;// 密码
	private Integer sex;// 性别 1：女，0：男; 1:female, 0:male
	private Integer age;// 年龄
	private Integer usertype;// 用户类型，0:超级管理员 1：管理员 ;0: Admin,1:Staff
	private Integer state;// 状态，1:失效 0：正常
	private Date modon;// 操作时间
	private String modby;// 操作人

	// Constructors

	/** default constructor */
	public Admin() {
	}

	/** full constructor */
	public Admin(String loginname, String firstName, String lastName, String password, Integer sex, Integer age, Integer usertype, Integer state, Date modon, String modby) {
		this.loginname = loginname;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.sex = sex;
		this.age = age;
		this.usertype = usertype;
		this.state = state;
		this.modon = modon;
		this.modby = modby;
	}

	// Property accessors

	@Column(name = "loginname", length = 50)
	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Column(name = "first_name", length = 50)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", length = 10)
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

	@Column(name = "sex")
	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Column(name = "age")
	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "usertype")
	public Integer getUsertype() {
		return this.usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Temporal(TemporalType.DATE)
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

}