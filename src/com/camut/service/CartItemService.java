package com.camut.service;

import java.util.List;
import com.camut.model.CartHeader;
import com.camut.model.CartItem;
import com.camut.model.api.CartItemApiModel;

public interface CartItemService {

	/**
	 * @Title: deleteCartItemByCartHeaderId
	 * @Description:删除购物车信息
	 * @param:    cartHeaderId
	 * @return: int -1失败
	 */
	public int deleteCartItemByCartHeaderId(long cartHeaderId);
	
	/**
	 * @Title: getCatrItemById
	 * @Description: 通过购物车条目的Id获取一个购物车条目
	 * @param: long cartItemId
	 * @return CartItemApiModel  
	 */
	public CartItemApiModel getCatrItemById(long cartItemId);
	
	/**
	 * @Title: editCartItem
	 * @Description: 修改一个购物车中的菜品信息
	 * @param: String cartHeader
	 * @return int  
	 */
	public int editCartItem(String cartHeader);
	
	/**getCartItemByHeaderId
	 * @Title: getCartItemByHeaderId
	 * @Description:根据购物车头获取所有的购物车详情
	 * @param:    
	 * @return: List<CartItem>
	 */
	public List<CartItem> getCartItemByHeaderId(CartHeader cartHeader);
	
}
