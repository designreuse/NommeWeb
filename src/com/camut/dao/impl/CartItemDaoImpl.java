/**
 * 
 */
package com.camut.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.camut.dao.CartItemDao;
import com.camut.model.CartHeader;
import com.camut.model.CartItem;

/**
 * @ClassName CartItemDaoImpl.java
 * @author wangpin
 * @createtime Jul 22, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class CartItemDaoImpl extends BaseDao<CartItem> implements CartItemDao {

	/**
	 * @Title: addCartItem
	 * @Description: 增加购物车详情
	 * @param:    CartItem
	 * @return: int -1失败
	 */
	@Override
	public int addCartItem(CartItem cartItem) {
		try {
			return Integer.parseInt(this.save(cartItem).toString());
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * @Title: deleteCartItemByCartHeaderId
	 * @Description:删除购物车信息
	 * @param:    cartHeaderId
	 * @return: int -1失败
	 */
	@Override
	public int deleteCartItemByCartHeaderId(long cartHeaderId) {
		try {
			String hql = "delete from CartItem c where c.cartHeader.id="+cartHeaderId;
			this.executeHql(hql);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * @Title: getCatrItemById
	 * @Description:通过Id获取一个购物车条目 
	 * @param: long cartItemId
	 * @return CartItem  
	 */
	public CartItem getCatrItemById(long cartItemId){
		String hql = "from CartItem ci where ci.id=:cartItemId";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cartItemId", cartItemId);
		return this.get(hql, map);
	}
	
	/**
	 * @Title: deleteById
	 * @Description: 通过di删除购物车条目
	 * @param: @param cartItemId
	 * @param: @return
	 * @return int  
	 */
	public int deleteById(long cartItemId){
		try {
			String hql = "delete from CartItem c where c.id="+cartItemId;
			this.executeHql(hql);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}

	/**getCartItemByHeaderId
	 * @Title: getCartItemByHeaderId
	 * @Description:根据购物车头获取所有的购物车详情
	 * @param:    
	 * @return: List<CartItem>
	 */
	@Override
	public List<CartItem> getCartItemByHeaderId(CartHeader cartHeader) {
		String hql = "from CartItem c where c.cartHeader=:cartHeader";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cartHeader", cartHeader);
		return this.find(hql, map);
	}

}
