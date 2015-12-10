/**
 * 
 */
package com.camut.pageModel;

import java.util.Date;
import java.util.List;

/**
 * @ClassName PageDish.java
 * @author wangpin
 * @createtime Jun 18, 2015
 * @author
 * @updateTime
 * @memo
 */
public class PageDish implements Comparable<PageDish>{

	private long id;//主键
	private Integer dishMenuId;//所属分类id
	private String dishMenuName;//所属分类名称
	private String enName;// 菜品名称
	private String chName;// 菜品名称 繁体中文
	private String enIntro;// 菜品介绍
	private Date createdate;// 创建时间
	private double price;// 价格
	private Integer spicy;// 辣度
	private Integer isMsg;// 是否含有味精，0不含，1含
	private Integer isStarch;// 是否包含淀粉 is gluten free
	private Integer isPickup;// 外带或者自取或者就餐
	private String photoUrl;//图片地址
	private Integer status;//上架或者下架
	
	private List<PageDishGarnish> garnishList;//菜品对应的配菜集合
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getChName() {
		return chName;
	}
	public void setChName(String chName) {
		this.chName = chName;
	}
	public String getEnIntro() {
		return enIntro;
	}
	public void setEnIntro(String enIntro) {
		this.enIntro = enIntro;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Integer getSpicy() {
		return spicy;
	}
	public void setSpicy(Integer spicy) {
		this.spicy = spicy;
	}
	
	public Integer getIsMsg() {
		return isMsg;
	}
	public void setIsMsg(Integer isMsg) {
		this.isMsg = isMsg;
	}
	public Integer getIsStarch() {
		return isStarch;
	}
	public void setIsStarch(Integer isStarch) {
		this.isStarch = isStarch;
	}
	public Integer getIsPickup() {
		return isPickup;
	}
	public void setIsPickup(Integer isPickup) {
		this.isPickup = isPickup;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public Integer getDishMenuId() {
		return dishMenuId;
	}
	public void setDishMenuId(Integer dishMenuId) {
		this.dishMenuId = dishMenuId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDishMenuName() {
		return dishMenuName;
	}
	public void setDishMenuName(String dishMenuName) {
		this.dishMenuName = dishMenuName;
	}
	public List<PageDishGarnish> getGarnishList() {
		return garnishList;
	}
	public void setGarnishList(List<PageDishGarnish> garnishList) {
		this.garnishList = garnishList;
	}
	/* @Title: compareTo
	 * @Description: 
	 * @param:    
	 * @return:   
	 */
	@Override
	public int compareTo(PageDish o) {
		return (int)(this.id-o.id);
	}
	
	
	
	
}
