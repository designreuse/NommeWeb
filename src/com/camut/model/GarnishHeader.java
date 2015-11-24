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
 * @entity GarnishHeader .
 * @author 王频
 * @createTime 2015-05-25
 * @author
 * @updateTime
 * @memo ingredients
 */
@Entity
@Table(name = "tbl_garnish_header")
public class GarnishHeader extends IdEntity implements java.io.Serializable,Comparable<GarnishHeader> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7728917194159623928L;
	// Fields
	private String garnishMenu;// 配菜分类名称
	private Integer showType;// 展现形式，0:单选 1：多选;2:select 0:radio 1:checkbox
	private Integer ismust;// 是否必须，1:必须，0：不是必须
	private Restaurants restaurants;// 与商家多对一 many GarnishHeader : 1 Restaurants
	private Set<GarnishItem> garnishItems = new HashSet<GarnishItem>();// 与配菜分类项目一对多关系;
																		// 1
																		// GarnishHeader
																		// :
																		// many
																		// GarnishItem
	private Integer count;// 最多选择的个数

	// Property accessors

	@Column(name = "garnish_menu")
	public String getGarnishMenu() {
		return this.garnishMenu;
	}

	public void setGarnishMenu(String garnishMenu) {
		this.garnishMenu = garnishMenu;
	}

	@Column(name = "show_type")
	public Integer getShowType() {
		return this.showType;
	}

	public void setShowType(Integer showType) {
		this.showType = showType;
	}

	@Column(name = "ismust")
	public Integer getIsmust() {
		return this.ismust;
	}

	public void setIsmust(Integer ismust) {
		this.ismust = ismust;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "garnishHeader")
	public Set<GarnishItem> getGarnishItems() {
		return this.garnishItems;
	}

	public void setGarnishItems(Set<GarnishItem> garnishItems) {
		this.garnishItems = garnishItems;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_uuid")
	public Restaurants getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(Restaurants restaurants) {
		this.restaurants = restaurants;
	}

	@Column(name = "count")
	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	/** @Title: compareTo
	 * @Description: 
	 * @param:    
	 * @return:   
	 */
	@Override
	public int compareTo(GarnishHeader o) {
		return (int)(this.id-o.id);
	}

}