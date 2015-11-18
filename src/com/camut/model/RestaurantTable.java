package com.camut.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * @entity RestaurantTable . 
 * @author 王频
 * @createTime 2015-05-25
 * @author 
 * @updateTime 
 * @memo 
 */
@Entity
@Table(name="tbl_restaurant_table"
    ,catalog="nomme"
)

public class RestaurantTable extends IdEntity implements java.io.Serializable {


    // Fields    

	 /**
	 * 
	 */
	private static final long serialVersionUID = -1946073436417100708L;
	private Restaurants restaurants;//与商家多对一
     private Integer acceptanceNum;//桌位容纳人数
     private Integer tableNum;//桌位数量


    // Property accessors

    
    @Column(name="acceptance_num", nullable=false)

    public Integer getAcceptanceNum() {
        return this.acceptanceNum;
    }
    
    public void setAcceptanceNum(Integer acceptanceNum) {
        this.acceptanceNum = acceptanceNum;
    }
    
    @Column(name="table_num", nullable=false)

    public Integer getTableNum() {
        return this.tableNum;
    }
    
    public void setTableNum(Integer tableNum) {
        this.tableNum = tableNum;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="restaurant_id")
	public Restaurants getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(Restaurants restaurants) {
		this.restaurants = restaurants;
	}
   

    






}