package com.camut.model.api;

import java.util.Date;

/**
 * @entity DishApiModel . 
 * @author zyf	
 * @createTime 2015-05-30
 * @author 
 * @updateTime 
 * @memo 
 */
public class DishApiModel implements Comparable<DishApiModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7931178112930827393L;
	private long dishId=0;//菜品id
	private Long restaurantId;// 商家编号
	private String enName="";// 菜品名称
	private String chName="";// 菜品名称 繁体中文
	private String enIntro="";// 菜品介绍
	private Date createdate=new Date();// 创建时间
	private double price;// 价格
	private Integer status;// 状态,1:正常 2：下架
	private Integer spicy;// 辣度
	private Integer isMsg;// 是否含有味精，0不含，1含
	private Integer isStarch;// 是否包含淀粉 is gluten free
	private String isPickup="";// 外带或者自取或者就餐
	private Date modon=new Date();// 操作时间
	private String modby="";// 操作人
	private long menuId; // 菜品menu编号
	private String menuName;  // 菜品Menu名称
	private String photoUrl="";// 图片地址
	private String describe="";// 菜品分类描述
	
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public long getDishId() {
		return dishId;
	}
	public void setDishId(long dishId) {
		this.dishId = dishId;
	}
	public Long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	
	public String getIsPickup() {
		return isPickup;
	}
	public void setIsPickup(String isPickup) {
		this.isPickup = isPickup;
	}
	public Date getModon() {
		return modon;
	}
	public void setModon(Date modon) {
		this.modon = modon;
	}
	public String getModby() {
		return modby;
	}
	public void setModby(String modby) {
		this.modby = modby;
	}

	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public long getMenuId() {
		return menuId;
	}
	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}
	@Override
	public int compareTo(DishApiModel o) {
		// TODO Auto-generated method stub
		return (int) (this.menuId-o.menuId);
	}
	
	
	
}
