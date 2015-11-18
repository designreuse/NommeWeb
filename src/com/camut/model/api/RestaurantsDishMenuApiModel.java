package com.camut.model.api;

public class RestaurantsDishMenuApiModel implements Comparable<RestaurantsDishMenuApiModel>{

	private long restaurantMenuId = 0;//菜品分类id
	private String menuName = "";// 菜单名称
	private Object content;
	public long getRestaurantMenuId() {
		return restaurantMenuId;
	}
	public void setRestaurantMenuId(long restaurantMenuId) {
		this.restaurantMenuId = restaurantMenuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	@Override
	public int compareTo(RestaurantsDishMenuApiModel o) {
		// TODO Auto-generated method stub
		return (int) (this.restaurantMenuId-o.restaurantMenuId);
	}

}
