/**
 * 
 */
package com.camut.model.api;

import java.io.Serializable;
import com.camut.utils.StringUtil;

/**
 * @ClassName ViewConsumerClassifitionApiModel.java
 * @author wangpin
 * @createtime Jul 22, 2015
 * @author
 * @updateTime
 * @memo
 */
public class ViewConsumerClassifitionApiModel implements  Serializable{

	private String classification;//餐厅分类名称
	private int classificationId;//餐厅分类id
	private String imageUrl;//图片地址
	private String hoverImageUrl;//
	public String getHoverImageUrl() {
		return hoverImageUrl;
	}
	public void setHoverImageUrl(String hoverImageUrl) {
		this.hoverImageUrl = hoverImageUrl;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public int getClassificationId() {
		return classificationId;
	}
	public void setClassificationId(int classificationId) {
		this.classificationId = classificationId;
	}
	public String getImageUrl() {
		if(StringUtil.isEmpty(this.imageUrl)){
			return "";
		}
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
}
