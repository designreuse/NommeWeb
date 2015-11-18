package com.camut.service.impl;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.CartDishGarnishDao;
import com.camut.dao.CartHeaderDao;
import com.camut.model.CartDishGarnish;
import com.camut.model.CartHeader;
import com.camut.model.CartItem;
import com.camut.service.CartDishGarnishService;
import com.camut.utils.StringUtil;

@Service
public class CartDishGarnishServiceImpl implements CartDishGarnishService {

	@Autowired private CartDishGarnishDao cartDishGarnishDao;
	
	@Autowired private CartHeaderDao cartHeaderDao;
	
	
	
	
	/**
	 * @Title: deleteCartDishGarnish
	 * @Description: 删除购物车配菜关系
	 * @param:    cartItemId
	 * @return: int -1失败
	 */
	@Override
	public int deleteCartDishGarnishByCartHeaderId(long cartHeaderId) {
		if( cartHeaderId > 0){
			CartHeader ch = cartHeaderDao.getCartHeaderById(cartHeaderId);
			Set<CartItem> cartItems = ch.getCartItems();
			String s = "";
			for (CartItem cartItem : cartItems) {
				s += cartItem.getId()+",";
			}
			if(StringUtil.isNotEmpty(s)){
				s = s.substring(0, s.length()-1);
				return cartDishGarnishDao.deleteCartDishGarnish(s);
			}			
		}
		return -1;
	}
	
	/**
	 * @Title: deleteCartDishGarnish
	 * @Description: 删除购物车配菜关系
	 * @param:    mobileToken
	 * @return: int -1失败
	 */
	public int deleteCartDishGarnish(String mobileToken){
		if( StringUtil.isNotEmpty(mobileToken)){
			CartHeader ch = cartHeaderDao.getCartHeaderByMobileToken(mobileToken);
			if(ch!=null){
				Set<CartItem> cartItems = ch.getCartItems();
				String s = "";
				for (CartItem cartItem : cartItems) {
					s += cartItem.getId()+",";
				}
				if(StringUtil.isNotEmpty(s)){
					s = s.substring(0, s.length()-1);
					return cartDishGarnishDao.deleteCartDishGarnish(s);
				}		
			}				
		}
		return -1;
	}

	
	/**
	 * @Title: getCartDishGarnishList
	 * @Description: 根据购物车条目获取条目的配菜
	 * @param: @param CartItemId
	 * @param: @return
	 * @return List<CartDishGarnishService>  
	 */
	public List<CartDishGarnish> getCartDishGarnishList(long CartItemId){
		return cartDishGarnishDao.getCartDishGarnishList(CartItemId);
	}
}
