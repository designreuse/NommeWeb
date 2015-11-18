package com.camut.model.api;

import java.io.Serializable;

/**
 * @entity GarnishHeaderApiModel . 
 * @author zyf	
 * @createTime 2015-05-29
 * @author 
 * @updateTime 
 * @memo 
 */
public class GarnishHeaderApiModel implements Serializable,Comparable<GarnishHeaderApiModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4532377053010747307L;
	
	private String garnishName;// 配菜名称
	private double addPrice;// 增加价格 the price of the ingredient
	private long garnishItemId;// //配菜id
	public String getGarnishName() {
		return garnishName;
	}
	public void setGarnishName(String garnishName) {
		this.garnishName = garnishName;
	}
	public double getAddPrice() {
		return addPrice;
	}
	public void setAddPrice(double addPrice) {
		this.addPrice = addPrice;
	}
	public long getGarnishItemId() {
		return garnishItemId;
	}
	public void setGarnishItemId(long garnishItemId) {
		this.garnishItemId = garnishItemId;
	}
	@Override
	public int compareTo(GarnishHeaderApiModel o) {
		// TODO Auto-generated method stub
		return (int) (this.garnishItemId-o.garnishItemId);
	}
	
	
}
