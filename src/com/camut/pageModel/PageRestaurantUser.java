/**
 * 
 */
package com.camut.pageModel;

import java.util.Date;

/**
 * @ClassName PageRestaurantUser.java
 * @author wangpin
 * @createtime Jun 3, 2015
 * @author
 * @updateTime
 * @memo
 */
public class PageRestaurantUser {
	
	private long id;
	private  String code;// 员工工号;
	private String firstName;// 名
	private String lastName;// 姓
	private String password;// 密码
	private Date createtime;// 创建时间
	private Integer status;// -1:失效 0：正常; 1:待审核-1:invalid 1:valid/normal
	private Date lastLoginTime;// 最后登录时间
	private Integer type;// 员工类型,1代表管理员，2代表普通员工
	private Integer role;//权限，001只有预定权限，011表示预定和外卖权限，111三种权限都有，100只有自取权限
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	
	
}
