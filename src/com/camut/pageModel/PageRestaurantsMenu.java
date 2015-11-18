package com.camut.pageModel;

import java.util.List;


public class PageRestaurantsMenu implements Comparable<PageRestaurantsMenu> {
	private long  id;//菜单分类id
	private String menuName;//菜单分类名称
	private long restaurantId;//商家名称
	private String describe;//菜品描述
	private List<PageDish> pageDishList;//菜单分类下面的所有菜品的集合
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}
	public List<PageDish> getPageDishList() {
		return pageDishList;
	}
	public void setPageDishList(List<PageDish> pageDishList) {
		this.pageDishList = pageDishList;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	@Override
	public int compareTo(PageRestaurantsMenu o) {
		// TODO Auto-generated method stub
		return (int)this.id-(int)o.id;
	}
	
	
}
