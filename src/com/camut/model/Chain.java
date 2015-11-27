package com.camut.model;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @entity Chain . 
 * @author 王频
 * @createTime 2015-05-25
 * @author 
 * @updateTime 
 * @memo 
 */
@Entity
@Table(name = "tbl_chain")
public class Chain extends IdEntity implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -2513085744328233047L;
	private String chainname;// 连锁店名称

	// Constructors

	/** default constructor */
	public Chain() {
	}

	/** full constructor */
	public Chain(String chainname) {
		this.chainname = chainname;
	}

	// Property accessors

	@Column(name = "chainname", length = 50)
	public String getChainname() {
		return this.chainname;
	}

	public void setChainname(String chainname) {
		this.chainname = chainname;
	}

}