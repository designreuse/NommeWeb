package com.camut.model.api;

/**
 * @entity RestaurantsMenuApiModel . 
 * @author zyf
 * @createTime 2015-07-2
 * @author 
 * @updateTime 
 * @memo 
 */
public class RestaurantsMenuApiModel implements Comparable<RestaurantsMenuApiModel>{

	private long restaurantMenuId = 0;//菜品分类id
	private String menuName = "";// 菜单名称
	private String menuDiscription="";
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
	
	
	public String getMenuDiscription() {
		return menuDiscription;
	}
	public void setMenuDiscription(String menuDiscription) {
		this.menuDiscription = menuDiscription;
	}
	@Override
	public int compareTo(RestaurantsMenuApiModel o) {
		// TODO Auto-generated method stub
		return (int) (this.restaurantMenuId-o.restaurantMenuId);
	}
	
}
