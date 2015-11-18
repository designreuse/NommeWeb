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
 * @entity OrderItem .
 * @author 王频
 * @createTime 2015-05-25
 * @author
 * @updateTime
 * @memo order details
 */
@Entity
@Table(name = "dat_order_item", catalog = "nomme")
public class OrderItem extends IdEntity implements java.io.Serializable,Comparable<OrderItem> {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3428512099517127556L;
	private OrderHeader orderHeader;// 与订单头多对一的关系; many OrderItem : 1
									// OrderHeader
	private Integer dishId;// 菜品编号
	private Integer num;// 数量
	private double price;// 单价
	private Integer status;// 1:未支付 2：已支付 3：取消 4：已退款 5：配送中 6：完成; 1:not paid
							// 2:paid 3:cancel 4:refund 5:on the way 6:done
	private Date createtime;// 创建时间
	private Set<OrderDishGarnish> orderDishGarnishs = new HashSet<OrderDishGarnish>();// 与订单配菜一对多;
																			// many
																			// OrderItem:
																			// many
																			// GarnishItem(Ingredients)
	private String instruction;// 特殊需求

	// Property accessors

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	public OrderHeader getOrderHeader() {
		return this.orderHeader;
	}

	public void setOrderHeader(OrderHeader orderHeader) {
		this.orderHeader = orderHeader;
	}

	@Column(name = "dish_id")
	public Integer getDishId() {
		return this.dishId;
	}

	public void setDishId(Integer dishId) {
		this.dishId = dishId;
	}

	@Column(name = "num")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime", length = 10)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="orderItem")
	public Set<OrderDishGarnish> getOrderDishGarnishs() {
		return orderDishGarnishs;
	}

	public void setOrderDishGarnishs(Set<OrderDishGarnish> orderDishGarnishs) {
		this.orderDishGarnishs = orderDishGarnishs;
	}

	@Column(name = "instruction", length = 200)
	public String getInstruction() {
		return this.instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	@Column(name = "price", precision = 18)
	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	
	@Override
	public int compareTo(OrderItem o) {
		return dishId-o.dishId;
	}

}