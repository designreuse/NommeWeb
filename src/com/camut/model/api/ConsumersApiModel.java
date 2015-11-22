package com.camut.model.api;

import java.io.Serializable;

/**
 * @entity Admin . 
 * @author yyl	
 * @createTime 2015-05-29
 * @author 
 * @updateTime 
 * @memo 
 */
public class ConsumersApiModel implements Serializable {
	
	private static final long serialVersionUID = 6083168761250689926L;
	private long consumerId=0;
	private String email="";// 登陆邮箱
	private String phone="";// 联系电话
	private String lastName="";// 姓
	private String firstName="";// 名
	private String showName="";
	private String newpassword;//新密码
	private String password;//原始密码
	private String nickName="";//昵称
	private String consumerUuid;//通用唯一识别码
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public long getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(long consumerId) {
		this.consumerId = consumerId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getConsumerUuid() {
		return consumerUuid;
	}
	public void setConsumerUuid(String consumerUuid) {
		this.consumerUuid = consumerUuid;
	}
	
	
}
