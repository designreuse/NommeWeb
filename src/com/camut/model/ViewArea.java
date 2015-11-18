package com.camut.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewAreaId entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "view_area", catalog = "nomme")
public class ViewArea implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5606288652577756343L;
	private Integer areaId;
	private String areaName;
	private Double pst;
	private Integer pid;
	private String PAreaName;
	private Double gst;
	private Integer taxMode;
	
	@Id
	@Column(name = "areaId", nullable = false)
	public Integer getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	@Column(name = "area_name", nullable = false, length = 50)
	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name = "pst", nullable = false, precision = 18)
	public Double getPst() {
		return this.pst;
	}

	public void setPst(Double pst) {
		this.pst = pst;
	}

	@Column(name = "pid")
	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Column(name = "p_area_name", length = 50)
	public String getPAreaName() {
		return this.PAreaName;
	}

	public void setPAreaName(String PAreaName) {
		this.PAreaName = PAreaName;
	}

	@Column(name = "gst", precision = 18)
	public Double getGst() {
		return this.gst;
	}

	public void setGst(Double gst) {
		this.gst = gst;
	}

	@Column(name = "tax_mode", nullable = false)
	public Integer getTaxMode() {
		return this.taxMode;
	}

	public void setTaxMode(Integer taxMode) {
		this.taxMode = taxMode;
	}

}