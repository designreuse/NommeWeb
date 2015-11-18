package com.camut.model;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @entity Classification .
 * @author 王频
 * @createTime 2015-05-25
 * @author
 * @updateTime
 * @memo
 */
@Entity
@Table(name = "sys_classification", catalog = "nomme")
public class Classification extends IdEntity implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -5725009317930114818L;
	private String classificationName;// 分类名称
	private String iosImageUrl;// 图片地址
	private String iosHoverImageUrl;//获得焦点的图片地址
	private String androidImageUrl;// 图片地址
	private String androidHoverImageUrl;//获得焦点的图片地址
	private String webImageUrl;// 图片地址
	private String webHoverImageUrl;//获得焦点的图片地址

	// Property accessors

	@Column(name = "classification_name", nullable = false, length = 50)
	public String getClassificationName() {
		return this.classificationName;
	}

	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}

	@Column(name = "ios_image_url", length = 255)
	public String getIosImageUrl() {
		return iosImageUrl;
	}
	public void setIosImageUrl(String iosImageUrl) {
		this.iosImageUrl = iosImageUrl;
	}

	@Column(name = "ios_hover_image_url", length = 255)
	public String getIosHoverImageUrl() {
		return iosHoverImageUrl;
	}
	public void setIosHoverImageUrl(String iosHoverImageUrl) {
		this.iosHoverImageUrl = iosHoverImageUrl;
	}

	@Column(name = "android_image_url", length = 200)
	public String getAndroidImageUrl() {
		return androidImageUrl;
	}
	public void setAndroidImageUrl(String androidImageUrl) {
		this.androidImageUrl = androidImageUrl;
	}

	@Column(name = "android_hover_image_url", length = 200)
	public String getAndroidHoverImageUrl() {
		return androidHoverImageUrl;
	}
	public void setAndroidHoverImageUrl(String androidHoverImageUrl) {
		this.androidHoverImageUrl = androidHoverImageUrl;
	}

	@Column(name = "web_image_url", length = 200)
	public String getWebImageUrl() {
		return webImageUrl;
	}
	public void setWebImageUrl(String webImageUrl) {
		this.webImageUrl = webImageUrl;
	}

	@Column(name = "web_hover_image_url", length = 200)
	public String getWebHoverImageUrl() {
		return webHoverImageUrl;
	}
	public void setWebHoverImageUrl(String webHoverImageUrl) {
		this.webHoverImageUrl = webHoverImageUrl;
	}

	
	
	
	

}