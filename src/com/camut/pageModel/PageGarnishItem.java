package com.camut.pageModel;

public class PageGarnishItem implements Comparable<PageGarnishItem>{
	
	private long garnishItemId;//配菜id(ingredient id)
	private String garnishName;// 配菜名称(ingredient name)
	private double addprice;// 增加价格 the price of the ingredient
	private int num;//增加的数量
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public long getGarnishItemId() {
		return garnishItemId;
	}
	public void setGarnishItemId(long garnishItemId) {
		this.garnishItemId = garnishItemId;
	}
	public String getGarnishName() {
		return garnishName;
	}
	public void setGarnishName(String garnishName) {
		this.garnishName = garnishName;
	}
	public double getAddprice() {
		return addprice;
	}
	public void setAddprice(double addprice) {
		this.addprice = addprice;
	}
	/* @Title: compareTo
	 * @Description: 
	 * @param:    
	 * @return:   
	 */
	@Override
	public int compareTo(PageGarnishItem o) {
		return (int)(this.garnishItemId-o.garnishItemId);
	}
	
	
	
}
