package com.camut.dao;

import java.util.List;
import com.camut.model.CartDishGarnish;

/**
 * @ClassName CartDishGarnishDao.java
 * @author wangpin
 * @createtime Jul 22, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface CartDishGarnishDao {

	/**
	 * @Title: addCartDishGarnish
	 * @Description: 增加购物车配菜关系
	 * @param:    CartDishGarnish
	 * @return: int -1失败
	 */
	public int addCartDishGarnish(CartDishGarnish cartDishGarnish);
	
	/**
	 * @Title: deleteCartDishGarnish
	 * @Description: 删除购物车配菜关系
	 * @param:    cartItemId
	 * @return: int -1失败
	 */
	public int deleteCartDishGarnish(String cartItemId);
	
	/**
	 * @Title: deleteCartDishGarnish
	 * @Description: 删除购物车配菜关系
	 * @param:    cartItemId
	 * @return: int -1失败
	 */
	public int deleteCartDishGarnish(Integer garnishItemId,Integer cartItemId);
	
	/**
	 * @Title: getCartDishGarnishList
	 * @Description: 根据购物车条目获取条目的配菜
	 * @param: @param CartItemId
	 * @param: @return
	 * @return List<CartDishGarnishService>  
	 */
	public List<CartDishGarnish> getCartDishGarnishList(long CartItemId);
	
}
