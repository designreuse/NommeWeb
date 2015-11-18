/**
 * 
 */
package com.camut.pageModel;

/**
 * @ClassName PageDishMenu.java
 * @author wangpin
 * @createtime Jun 17, 2015
 * @author
 * @updateTime
 * @memo
 */
public class PageDishMenu {

	private long id;//主键
	private String menuName;// 菜单名称
	private String describe;//菜品分类描述
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
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	
}
