package com.camut.model;

// default package

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @entity Areas .
 * @author 王频
 * @createTime 2015-05-25
 * @author
 * @updateTime
 * @memo
 */
@Entity
@Table(name = "sys_areas", catalog = "nomme")
public class Areas extends IdEntity implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -8914384718841966779L;
	private Areas parent;// 自连接
	private Set<Areas> areasSet = new HashSet<Areas>();// 自连接
	private String areaName;// 区域名称
	private double tax;// 税率
	private Integer taxMode;// 税金计算风格 1表示： HST 2表示：GST+PST 0表示：父区域（不用于显示 ）

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
	public Set<Areas> getAreasSet() {
		return areasSet;
	}

	public void setAreasSet(Set<Areas> areasSet) {
		this.areasSet = areasSet;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pid")
	public Areas getParent() {
		return parent;
	}

	public void setParent(Areas parent) {
		this.parent = parent;
	}

	@Column(name = "area_name", nullable = false, length = 50)
	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name = "tax", nullable = false, precision = 18)
	public double getTax() {
		return this.tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	@Column(name = "tax_mode", nullable = false, length = 5)
	public Integer getTaxMode() {
		return taxMode;
	}

	public void setTaxMode(Integer taxMode) {
		this.taxMode = taxMode;
	}



}