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
 * @entity GarnishItem .
 * @author 王频
 * @createTime 2015-05-25
 * @author
 * @updateTime
 * @memo ingredient
 */
@Entity
@Table(name = "dat_garnish_item", catalog = "nomme")
public class GarnishItem extends IdEntity implements java.io.Serializable,Comparable<GarnishItem>{

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3597012621077583970L;
	private GarnishHeader garnishHeader;// 与配菜分类头多对一关系 many
										// GarnishItem(ingredients) : 1
										// GarnishHeader
	private String garnishName;// 配菜名称(ingredient name)
	private Set<OrderDishGarnish> orderDishGarnishs = new HashSet<OrderDishGarnish>();// 与订单详情配菜一对多
	// many
	// GarnishItem(ingredients)
	// : many
	// OrderItem
	private Set<DishGarnish> dishGarnishsSet = new HashSet<DishGarnish>();// 与菜品配菜一对多
																			// 1
																			// GarnishItem
																			// :
																			// many
																			// DishGarnish
	private Integer status;// 状态，0有效，1无效

	// Property accessors

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "garnish_id")
	public GarnishHeader getGarnishHeader() {
		return this.garnishHeader;
	}

	public void setGarnishHeader(GarnishHeader garnishHeader) {
		this.garnishHeader = garnishHeader;
	}

	@Column(name = "garnish_name")
	public String getGarnishName() {
		return this.garnishName;
	}

	public void setGarnishName(String garnishName) {
		this.garnishName = garnishName;
	}

	

	@OneToMany(fetch=FetchType.LAZY,mappedBy="garnishItem")
	public Set<OrderDishGarnish> getOrderDishGarnishs() {
		return orderDishGarnishs;
	}

	public void setOrderDishGarnishs(Set<OrderDishGarnish> orderDishGarnishs) {
		this.orderDishGarnishs = orderDishGarnishs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "garnishItem")
	public Set<DishGarnish> getDishGarnishsSet() {
		return dishGarnishsSet;
	}

	public void setDishGarnishsSet(Set<DishGarnish> dishGarnishsSet) {
		this.dishGarnishsSet = dishGarnishsSet;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/** @Title: compareTo
	 * @Description: 
	 * @param:    
	 * @return:   
	 */
	@Override
	public int compareTo(GarnishItem o) {
		return (int)(this.id-o.id);
	}


}