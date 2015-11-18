package com.camut.model;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 捐款历史表 OrderCharity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_order_charity", catalog = "nomme")
public class OrderCharity extends IdEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6029088656156612456L;
    private Integer orderId;
    private Integer charityId;
    private Double money;
    private Integer consumerId;


   // Constructors

   /** default constructor */
   public OrderCharity() {
   }

   
   /** full constructor */
   public OrderCharity(Integer orderId, Integer charityId,  Integer consumerId ,double money) {
       this.orderId = orderId;
       this.charityId = charityId;
       this.money = money;
       this.consumerId = consumerId;
   }


   
   @Column(name="order_id")

   public Integer getOrderId() {
       return this.orderId;
   }
   
   public void setOrderId(Integer orderId) {
       this.orderId = orderId;
   }
   
   @Column(name="charity_id")

   public Integer getCharityId() {
       return this.charityId;
   }
   
   public void setCharityId(Integer charityId) {
       this.charityId = charityId;
   }
   
   @Column(name="money", precision=22, scale=0)

   public Double getMoney() {
       return this.money;
   }
   
   public void setMoney(Double money) {
       this.money = money;
   }
   
   @Column(name="consumer_id")

   public Integer getConsumerId() {
       return this.consumerId;
   }
   
   public void setConsumerId(Integer consumerId) {
       this.consumerId = consumerId;
   }
}