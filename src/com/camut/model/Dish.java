package com.camut.model;

// default package

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * @entity Dish .
 * @author 王频
 * @createTime 2015-05-25
 * @author
 * @updateTime
 * @memo
 */
@Entity
@Table(name = "dat_dish", catalog = "nomme")
public class Dish extends IdEntity implements java.io.Serializable,Comparable<Dish> {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2865117859135458421L;
	private RestaurantsMenu restaurantsMenu;// 与菜品分类多对一关系 many-to-one: many dish
											// to one restaurantsMenu
	private Long restaurantId;// 商家编号
	private String enName;// 菜品名称
	private String chName;// 菜品名称 繁体中文
	private String enIntro;// 菜品介绍
	private Date createdate;// 创建时间
	private double price;// 价格
	private Integer status;// 状态,0:正常 1：下架
	private Integer spicy;// 辣度，0不辣，1微辣，2中辣，3特辣
	private Integer isMsg;// 是否含有味精，0不含，1含
	private Integer isStarch;// 是否包含淀粉 is gluten free
	private Integer isPickup;// 外带或者自取或者就餐
	private Date modon;// 操作时间
	private String modby;// 操作人
	private String photoUrl;// 图片地址
	// 1 dish has many DishGarnish; 1-to-many;
	// DishGarnish is the relationship between dish and GarnishItem(ingredients)
	private Set<DishGarnish> dishGarnishsSet = new HashSet<DishGarnish>();// 与菜品配菜关系表一对多

	// Property accessors

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "restaurant_menu_id")
	public RestaurantsMenu getRestaurantsMenu() {
		return this.restaurantsMenu;
	}

	public void setRestaurantsMenu(RestaurantsMenu restaurantsMenu) {
		this.restaurantsMenu = restaurantsMenu;
	}

	@Column(name = "restaurant_id")
	public Long getRestaurantId() {
		return this.restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	@Column(name = "en_name", length = 200)
	public String getEnName() {
		return this.enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	@Column(name = "ch_name", length = 200)
	public String getChName() {
		return this.chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}

	@Column(name = "en_intro", length = 65535)
	public String getEnIntro() {
		return this.enIntro;
	}

	public void setEnIntro(String enIntro) {
		this.enIntro = enIntro;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdate", length = 10)
	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@Column(name = "price", precision = 18)
	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "spicy")
	public Integer getSpicy() {
		return this.spicy;
	}

	public void setSpicy(Integer spicy) {
		this.spicy = spicy;
	}

	@Column(name = "is_msg")
	public Integer getIsMsg() {
		return this.isMsg;
	}

	public void setIsMsg(Integer isMsg) {
		this.isMsg = isMsg;
	}

	@Column(name = "is_starch")
	public Integer getIsStarch() {
		return this.isStarch;
	}

	public void setIsStarch(Integer isStarch) {
		this.isStarch = isStarch;
	}

	@Column(name = "is_pickup")
	public Integer getIsPickup() {
		return this.isPickup;
	}

	public void setIsPickup(Integer isPickup) {
		this.isPickup = isPickup;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "modon", length = 10)
	public Date getModon() {
		return this.modon;
	}

	public void setModon(Date modon) {
		this.modon = modon;
	}

	@Column(name = "modby", length = 200)
	public String getModby() {
		return this.modby;
	}

	public void setModby(String modby) {
		this.modby = modby;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "dish")
	public Set<DishGarnish> getDishGarnishsSet() {
		return dishGarnishsSet;
	}

	public void setDishGarnishsSet(Set<DishGarnish> dishGarnishsSet) {
		this.dishGarnishsSet = dishGarnishsSet;
	}

	@Column(name = "photo_url", length = 200)
	public String getPhotoUrl() {
		return this.photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	/**@Title: compareTo
	 * @Description: 
	 * @param:    
	 * @return:   
	 */
	@Override
	public int compareTo(Dish o) {
		return (int)(this.id-o.id);
	}
}