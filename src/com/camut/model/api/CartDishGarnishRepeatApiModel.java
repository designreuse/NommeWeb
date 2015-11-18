package com.camut.model.api;

public class CartDishGarnishRepeatApiModel implements Comparable<CartDishGarnishRepeatApiModel>{

	private long garnishItemId;//配菜id
	private int count;//配菜数量
	
	public long getGarnishItemId() {
		return garnishItemId;
	}
	public void setGarnishItemId(long garnishItemId) {
		this.garnishItemId = garnishItemId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public int compareTo(CartDishGarnishRepeatApiModel o) {
		// TODO Auto-generated method stub
		return (int) (this.garnishItemId-o.garnishItemId);
	}
}
