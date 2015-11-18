package com.camut.model.api;

import java.io.Serializable;

/**
 * @entity CustomerBankaccount . 
 * @author zyf	
 * @createTime 2015-05-29
 * @author 
 * @updateTime 
 * @memo 
 */
public class ConsumerBankaccountApiModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2075864345127902494L;
	private long consumerId;//用户id
	private String card="";// 卡号
	private String expiratioin="";// 有效日期
	private String cvv="";// 卡号最后三位
	private Integer isDefault;// 是否默认 1:是,0否
	private String paycardToken;//token
	
	public long getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(long consumerId) {
		this.consumerId = consumerId;
	}
	public String getPaycardToken() {
		return paycardToken;
	}
	public void setPaycardToken(String paycardToken) {
		this.paycardToken = paycardToken;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getExpiratioin() {
		return expiratioin;
	}
	public void setExpiratioin(String expiratioin) {
		this.expiratioin = expiratioin;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	
}
