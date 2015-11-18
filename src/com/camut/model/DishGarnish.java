package com.camut.model;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @entity DishGarnish . 
 * @author 王频
 * @createTime 2015-05-25
 * @author 
 * @updateTime 
 * @memo 菜品配菜关系表： relationship between dish and garnishItem(ingredients)
 */
@Entity
@Table(name = "res_dish_garnish", catalog = "nomme")
public class DishGarnish extends IdEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5226956550880296452L;
	private Dish dish;// 与菜品信息多对一 many DishGarnish : 1 Dish
	private GarnishItem garnishItem;// 与配菜多对一 1 DishGarnish : many GarnishItem(ingredients)
	private double addprice;// 增加价格 the price of the ingredient
	private Integer garnishHeaderId;//配菜分类主键
	
	
	//构造方法
	public DishGarnish() {
		super();
	}
	
	public DishGarnish(Dish dish, GarnishItem garnishItem, double addprice, Integer garnishHeaderId) {
		super();
		this.dish = dish;
		this.garnishItem = garnishItem;
		this.addprice = addprice;
		this.garnishHeaderId = garnishHeaderId;
	}

	public DishGarnish(Dish dish, GarnishItem garnishItem, double addprice) {
		super();
		this.dish = dish;
		this.garnishItem = garnishItem;
		this.addprice = addprice;
	}
	
	
	
	// Property accessors
	
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dish_id")
	public Dish getDish() {
		return dish;
	}


	public void setDish(Dish dish) {
		this.dish = dish;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "garnish_id")
	public GarnishItem getGarnishItem() {
		return garnishItem;
	}

	public void setGarnishItem(GarnishItem garnishItem) {
		this.garnishItem = garnishItem;
	}

	@Column(name = "addprice", precision = 18)
	public double getAddprice() {
		return this.addprice;
	}

	public void setAddprice(double addprice) {
		this.addprice = addprice;
	}

	@Column(name = "garnish_header_id")
	public Integer getGarnishHeaderId() {
		return garnishHeaderId;
	}

	public void setGarnishHeaderId(Integer garnishHeaderId) {
		this.garnishHeaderId = garnishHeaderId;
	}
	
	

}