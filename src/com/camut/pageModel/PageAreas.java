package com.camut.pageModel;

public class PageAreas implements Comparable<PageAreas>{
	private String id;//区域的id
	
	private String pid;//父类区域id
	private String parentAreaName;//父类区域名称
	private String areaName;//区域名称
	private String parentTax;//父税率
	private String tax;//子税率 例：0.05;
	private String tree;//用于显示子区域名称
	private int taxMode;//税额计算显示方式  1表示： HST  2表示：GST+PST  0表示：父区域（不用于显示 ） 
	
	
	public String getParentAreaName() {
		return parentAreaName;
	}
	public void setParentAreaName(String parentAreaName) {
		this.parentAreaName = parentAreaName;
	}
	public String getParentTax() {
		return parentTax;
	}
	public void setParentTax(String parentTax) {
		this.parentTax = parentTax;
	}
	public String getTree() {
		return tree;
	}
	public void setTree(String tree) {
		this.tree = tree;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public int getTaxMode() {
		return taxMode;
	}
	public void setTaxMode(int taxMode) {
		this.taxMode = taxMode;
	}
	
	
	/**
	 * @Title: compareTo
	 * @Description: 排序比较
	 * @param:
	 * @return:
	 */
	@Override
	public int compareTo(PageAreas o) {
		return this.getAreaName().compareTo(o.getAreaName());

	}

}
