/**
 * 
 */
package com.camut.model;

/**
 * @ClassName ChargeEntity.java 收款对象
 * @author wangpin
 * @createtime Jul 28, 2015
 * @author
 * @updateTime
 * @memo
 */
public class ChargeEntity {

	private Integer amount;//收款金额，美分
	private String accountId;//商家托管账户id
	private String customerId;//stripe的客户id
	private String cardId;//stripe的卡id
	private String token;//卡的token
	private Integer save;//是否保存卡
	private Integer applicatonFee;//平台收取的费用
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getSave() {
		return save;
	}
	public void setSave(Integer save) {
		this.save = save;
	}
	public Integer getApplicatonFee() {
		return applicatonFee;
	}
	public void setApplicatonFee(Integer applicatonFee) {
		this.applicatonFee = applicatonFee;
	}

	
	
	
}
