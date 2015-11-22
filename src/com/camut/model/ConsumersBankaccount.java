package com.camut.model;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @entity ConsumersBankaccount . 
 * @author 王频
 * @createTime 2015-05-25
 * @author 
 * @updateTime 
 * @memo 
 */
@Entity
@Table(name = "tbl_customer_bankaccount", catalog = "nomme")
public class ConsumersBankaccount extends IdEntity implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7554718311036809822L;
	// Fields

	private Consumers consumers;// 与用户多对一，单向关联
	private String token;// 账户token
	private String card;// 卡号
	private String expiratioin;// 有效日期
	private String cvv;// 卡号最后三位
	private Integer isDefault;// 是否默认 1:是,2否

	// Property accessors

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "consumer_uuid")
	public Consumers getConsumers() {
		return consumers;
	}

	public void setConsumers(Consumers consumers) {
		this.consumers = consumers;
	}

	@Column(name = "token", length = 50)
	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Column(name = "card", length = 30)
	public String getCard() {
		return this.card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	@Column(name = "expiratioin", length = 10)
	public String getExpiratioin() {
		return this.expiratioin;
	}

	public void setExpiratioin(String expiratioin) {
		this.expiratioin = expiratioin;
	}

	@Column(name = "cvv", length = 3)
	public String getCvv() {
		return this.cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	@Column(name = "is_default")
	public Integer getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

}