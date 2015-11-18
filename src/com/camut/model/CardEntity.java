/**
 * 
 */
package com.camut.model;

/**
 * @ClassName CardEntity.java 信用卡对象
 * @author wangpin
 * @createtime Jul 28, 2015
 * @author
 * @updateTime
 * @memo
 */
public class CardEntity {

	private String number;//卡号
	private Integer exp_month;//过期月
	private Integer exp_year;//过期年
	private String cvc;//信用卡cvc
	private String last4;//卡号后四位
	private String id;//卡的id
	private String type;//卡的类型
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Integer getExp_month() {
		return exp_month;
	}
	public void setExp_month(Integer exp_month) {
		this.exp_month = exp_month;
	}
	public Integer getExp_year() {
		return exp_year;
	}
	public void setExp_year(Integer exp_year) {
		this.exp_year = exp_year;
	}
	public String getCvc() {
		return cvc;
	}
	public void setCvc(String cvc) {
		this.cvc = cvc;
	}
	public String getLast4() {
		return last4;
	}
	public void setLast4(String last4) {
		this.last4 = last4;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "CardEntity [number=" + number + ", exp_month=" + exp_month + ", exp_year=" + exp_year + ", cvc=" + cvc + ", last4=" + last4 + ", id="
				+ id + ", type=" + type + "]";
	}
	
	
}
