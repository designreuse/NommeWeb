/**
 * 
 */
package com.camut.dao;

import java.util.List;

import com.camut.model.CartHeader;
import com.camut.model.CartItem;

/**
 * @ClassName CartItemDao.java
 * @author wangpin
 * @createtime Jul 22, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface CartItemDao {

	/**
	 * @Title: addCartItem
	 * @Description: 增加购物车详情
	 * @param:    CartItem
	 * @return: int -1失败
	 */
	public int addCartItem(CartItem cartItem);
	
	/**
	 * @Title: deleteCartItemByCartHeaderId
	 * @Description:删除购物车信息
	 * @param:    cartHeaderId
	 * @return: int -1失败
	 */
	public int deleteCartItemByCartHeaderId(long cartHeaderId);
	
	/**
	 * @Title: getCatrItemById
	 * @Description:通过Id获取一个购物车条目 
	 * @param: long cartItemId
	 * @return CartItem  
	 */
	public CartItem getCatrItemById(long cartItemId);
	
	/**
	 * @Title: deleteById
	 * @Description: 通过di删除购物车条目
	 * @param: @param cartItemId
	 * @param: @return
	 * @return int  
	 */
	public int deleteById(long cartItemId);
	
	/**getCartItemByHeaderId
	 * @Title: getCartItemByHeaderId
	 * @Description:根据购物车头获取所有的购物车详情
	 * @param:    
	 * @return: List<CartItem>
	 */
	public List<CartItem> getCartItemByHeaderId(CartHeader cartHeader);
}
