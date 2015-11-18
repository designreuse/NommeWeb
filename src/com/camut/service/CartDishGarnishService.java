package com.camut.service;

import java.util.List;

import com.camut.model.CartDishGarnish;

public interface CartDishGarnishService {

	/**
	 * @Title: deleteCartDishGarnish
	 * @Description: 删除购物车配菜关系
	 * @param:    cartItemId
	 * @return: int -1失败
	 */
	public int deleteCartDishGarnishByCartHeaderId(long cartHeaderId);
	
	/**
	 * @Title: deleteCartDishGarnish
	 * @Description: 删除购物车配菜关系
	 * @param:    mobileToken
	 * @return: int -1失败
	 */
	public int deleteCartDishGarnish(String mobileToken);
	
	/**
	 * @Title: getCartDishGarnishList
	 * @Description: 根据购物车条目获取条目的配菜
	 * @param: @param CartItemId
	 * @param: @return
	 * @return List<CartDishGarnishService>  
	 */
	public List<CartDishGarnish> getCartDishGarnishList(long CartItemId);
	
	
}
