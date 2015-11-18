package com.camut.pageModel;

import java.io.Serializable;

/**
 * @ClassName Classification.java
 * @author wangpin
 * @createtime Jun 27, 2015
 * @author
 * @updateTime
 * @memo
 */
public class PageClassification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;//主键
	private String classificationName;//分类名称
	private String iosImageUrl;// 图片地址
	private String iosHoverImageUrl;//获得焦点的图片地址
	private String androidImageUrl;// 图片地址
	private String androidHoverImageUrl;//获得焦点的图片地址
	private String webImageUrl;// 图片地址
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getClassificationName() {
		return classificationName;
	}
	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}
	public String getIosImageUrl() {
		return iosImageUrl;
	}
	public void setIosImageUrl(String iosImageUrl) {
		this.iosImageUrl = iosImageUrl;
	}
	public String getIosHoverImageUrl() {
		return iosHoverImageUrl;
	}
	public void setIosHoverImageUrl(String iosHoverImageUrl) {
		this.iosHoverImageUrl = iosHoverImageUrl;
	}
	public String getAndroidImageUrl() {
		return androidImageUrl;
	}
	public void setAndroidImageUrl(String androidImageUrl) {
		this.androidImageUrl = androidImageUrl;
	}
	public String getAndroidHoverImageUrl() {
		return androidHoverImageUrl;
	}
	public void setAndroidHoverImageUrl(String androidHoverImageUrl) {
		this.androidHoverImageUrl = androidHoverImageUrl;
	}
	public String getWebImageUrl() {
		return webImageUrl;
	}
	public void setWebImageUrl(String webImageUrl) {
		this.webImageUrl = webImageUrl;
	}
	

	
	
}
