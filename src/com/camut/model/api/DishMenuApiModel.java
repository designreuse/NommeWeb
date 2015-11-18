package com.camut.model.api;

import java.util.ArrayList;
import java.util.List;

public class DishMenuApiModel implements Comparable<DishMenuApiModel>{

	private long restaurantMenuId=0;//菜品 分类编号	
	private String menuName="";//菜品分类名称
	private String menuDiscription = "";//菜品分类描述
	private List<DishApiModel> content=new ArrayList<DishApiModel>();
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
	public List<DishApiModel> getContent() {
		return content;
	}
	public void setContent(List<DishApiModel> content) {
		this.content = content;
	}
	public String getMenuDiscription() {
		return menuDiscription;
	}
	public void setMenuDiscription(String menuDiscription) {
		this.menuDiscription = menuDiscription;
	}
	@Override
	public int compareTo(DishMenuApiModel o) {
		// TODO Auto-generated method stub
		return (int) (this.restaurantMenuId-o.restaurantMenuId);
	}
	
	
	
	
	
}
