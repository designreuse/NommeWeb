/**
 * 
 */
package com.camut.model.api;

/**
 * @ClassName CartDishGarnishApiModel.java
 * @author wangpin
 * @createtime Jul 22, 2015
 * @author
 * @updateTime
 * @memo
 */
public class CartDishGarnishApiModel implements Comparable<CartDishGarnishApiModel>{

	private int garnishItemId;//配菜id
	private String garnishName;//配菜名称
	private int count;//配菜数量
	public int getGarnishItemId() {
		return garnishItemId;
	}
	public void setGarnishItemId(int garnishItemId) {
		this.garnishItemId = garnishItemId;
	}
	public String getGarnishName() {
		return garnishName;
	}
	public void setGarnishName(String garnishName) {
		this.garnishName = garnishName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public int compareTo(CartDishGarnishApiModel o) {
		// TODO Auto-generated method stub
		return this.garnishItemId-o.garnishItemId;
	}
	
	
}
