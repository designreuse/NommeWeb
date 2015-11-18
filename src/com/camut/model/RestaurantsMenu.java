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
 * @entity RestaurantsMenu .
 * @author 王频
 * @createTime 2015-05-25
 * @author
 * @updateTime
 * @memo
 */
@Entity
@Table(name = "tbl_restaurant_menu", catalog = "nomme")
public class RestaurantsMenu extends IdEntity implements java.io.Serializable,Comparable<RestaurantsMenu> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1522336450341747317L;
	// Fields

	/**
	 * 
	 */
	private Restaurants restaurants;// 与商家多对一 many RestaurantsMenu : 1
									// Restaurants
	private String menuName;// 菜单名称
	private Set<Dish> dishs = new HashSet<Dish>(0);// 与菜品信息一对多的关系; 1
													// RestaurantsMenu: many
													// Dish
	private String describe;// 菜品分类描述

	// Property accessors

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurants_uuid")
	public Restaurants getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(Restaurants restaurants) {
		this.restaurants = restaurants;
	}

	@Column(name = "menu_name", length = 50)
	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurantsMenu")
	public Set<Dish> getDishs() {
		return this.dishs;
	}

	public void setDishs(Set<Dish> dishs) {
		this.dishs = dishs;
	}

	@Column(name = "describ", length = 65535)
	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	/**@Title: compareTo
	 * @Description: 
	 * @param:    
	 * @return:   
	 */
	@Override
	public int compareTo(RestaurantsMenu o) {
		return (int)(this.id-o.id);
	}

}