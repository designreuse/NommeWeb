package com.camut.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.camut.dao.CartDishGarnishDao;
import com.camut.model.CartDishGarnish;

/**
 * @ClassName CartDishGarnishDaoImpl.java
 * @author wangpin
 * @createtime Jul 22, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class CartDishGarnishDaoImpl extends BaseDao<CartDishGarnish> implements CartDishGarnishDao {

	/**
	 * @Title: addCartDishGarnish
	 * @Description: 增加购物车配菜关系
	 * @param:    CartDishGarnish
	 * @return: int -1失败
	 */
	@Override
	public int addCartDishGarnish(CartDishGarnish cartDishGarnish) {
		try {
			this.save(cartDishGarnish);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: deleteCartDishGarnish
	 * @Description: 删除购物车配菜关系
	 * @param:    cartItemId
	 * @return: int -1失败
	 */
	@Override
	public int deleteCartDishGarnish(String cartItemId) {
		try {
			String hql = "delete from CartDishGarnish c where c.cartItem.id in ("+cartItemId+")";
			this.executeHql(hql);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}

	@Override
	public int deleteCartDishGarnish(Integer garnishItemId, Integer cartItemId) {
		try {
			String sql = "delete from res_cart_dish_garnish where garnish_item_id="+garnishItemId+" and res_cart_dish_garnish.cart_item_id="+cartItemId;
			this.executeSql(sql);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}
	
	/**
	 * @Title: getCartDishGarnishList
	 * @Description: 根据购物车条目获取条目的配菜
	 * @param: @param CartItemId
	 * @param: @return
	 * @return List<CartDishGarnishService>  
	 */
	@Override
	public List<CartDishGarnish> getCartDishGarnishList(long cartItemId1){
		int cartItemId = (int)cartItemId1;
		String hql="from CartDishGarnish a where a.id.cartItemId=:cartItemId ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cartItemId", cartItemId);
		return this.find(hql, map);
	}
}
