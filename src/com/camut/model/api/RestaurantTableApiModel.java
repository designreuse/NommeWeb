package com.camut.model.api;

/**
 * @entity RestaurantTableApiModel . 
 * @author zyf
 * @createTime 2015-07-8
 * @author 
 * @updateTime 
 * @memo 
 */
public class RestaurantTableApiModel {

    private Integer acceptanceNum;//桌位容纳人数
    private Integer tableNum;//桌位数量
    
	public Integer getAcceptanceNum() {
		return acceptanceNum;
	}
	public void setAcceptanceNum(Integer acceptanceNum) {
		this.acceptanceNum = acceptanceNum;
	}
	public Integer getTableNum() {
		return tableNum;
	}
	public void setTableNum(Integer tableNum) {
		this.tableNum = tableNum;
	}
    
}
