package com.camut.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.druid.support.json.JSONUtils;
import com.camut.dao.CartDishGarnishDao;
import com.camut.dao.CartItemDao;
import com.camut.model.CartDishGarnish;
import com.camut.model.CartDishGarnishId;
import com.camut.model.CartHeader;
import com.camut.model.CartItem;
import com.camut.model.GarnishItem;
import com.camut.model.api.CartDishGarnishApiModel;
import com.camut.model.api.CartItemApiModel;
import com.camut.service.CartItemService;
import com.camut.utils.StringUtil;

@Service
public class CartItemServiceImpl implements CartItemService {

	@Autowired private CartItemDao cartItemDao;
	@Autowired private CartDishGarnishDao cartDishGarnishDao;
	//@Autowired private 
	/**
	 * @Title: deleteCartItemByCartHeaderId
	 * @Description:删除购物车信息
	 * @param:    cartHeaderId
	 * @return: int -1失败
	 */
	@Override
	public int deleteCartItemByCartHeaderId(long cartHeaderId) {
		if(cartHeaderId > 0){
			return cartItemDao.deleteCartItemByCartHeaderId(cartHeaderId);
		}
		return -1;
	}

	/**
	 * @Title: getCatrItemById
	 * @Description: 通过购物车条目的Id获取一个购物车条目
	 * @param: long cartItemId
	 * @return CartItemApiModel  
	 */
	public CartItemApiModel getCatrItemById(long cartItemId){
		CartItemApiModel cartItemApiModel = new CartItemApiModel();
		if(cartItemId > 0){
			CartItem cartItem = cartItemDao.getCatrItemById(cartItemId);
			if(cartItem!=null){
				cartItemApiModel.setDishId(cartItem.getDishId());
				cartItemApiModel.setNum(cartItem.getNum());
				cartItemApiModel.setCartItemId(cartItem.getId().intValue());
				cartItemApiModel.setDishRemark(cartItem.getInstruction());
				Set<CartDishGarnish> cartDishGarnishs = cartItem.getCartDishGarnishs();
				if(cartDishGarnishs.size()>0){
					List<CartDishGarnishApiModel> subItem = new ArrayList<CartDishGarnishApiModel>(0);
					for (CartDishGarnish cartDishGarnish : cartDishGarnishs) {
						CartDishGarnishApiModel apiModel = new CartDishGarnishApiModel(); 
						apiModel.setCount(cartDishGarnish.getGarnishNum());
						apiModel.setGarnishItemId(cartDishGarnish.getGarnishItem().getId().intValue());
						subItem.add(apiModel);
					}
					Collections.sort(subItem);
					cartItemApiModel.setSubItem(subItem);
				}
			}
		}
		return cartItemApiModel;
	}
	
	/**
	 * @Title: editCartItem
	 * @Description: 修改一个购物车中的菜品信息
	 * @param: String cartHeader
	 * @return int  
	 */
	public int editCartItem(String cartHeader){
		//int temp = 0;
		@SuppressWarnings("unchecked")
		Map<String,Object> map =  (Map<String, Object>) JSONUtils.parse(cartHeader);//cartHeader.
		String item = map.get("item").toString();
		if(StringUtil.isNotEmpty(item)){
			@SuppressWarnings("unchecked")
			//Map<String,Object> map2 = (Map<String, Object>) JSONUtils.parse(item);
			//List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("item"); 
			Map<String,Object> map2 = (Map<String, Object>) map.get("item");
			
			
			String cartItemId = map2.get("cartItemId").toString().trim();
			if(StringUtil.isNotEmpty(cartItemId)){//如果购物车条目的id存在，则首先删除此条目下的所有配菜信息
				int cartItemId2 = Integer.parseInt(cartItemId);
				int temp1 = cartDishGarnishDao.deleteCartDishGarnish(cartItemId);//1成功，-1失败
				if(temp1>0){//删除完条目下的所有配菜
					CartItem cartItem = cartItemDao.getCatrItemById(cartItemId2);
					if(cartItem!=null){
						String num = map2.get("num").toString();
						int a = Integer.parseInt(num);
						cartItem.setNum(Integer.parseInt(map2.get("num").toString()));
						//cartItem.setNum(Integer.parseInt(map2.get("num").toString()));
						cartItem.setUnitprice(Double.parseDouble(map2.get("unitprice").toString()));
						cartItem.setInstruction(map2.get("instruction").toString());
						int newCartItemId = cartItemDao.addCartItem(cartItem);
						
						List<Map<String,Object>> subItemList = (List<Map<String, Object>>) map2.get("subItem");
						for(Map<String,Object> mapList:subItemList){
							CartDishGarnish cartDishGarnish = new CartDishGarnish();
							GarnishItem garnishItem = new GarnishItem();
							cartDishGarnish.setCartItem(cartItem);
							if (mapList.get("count")!=null) {
								cartDishGarnish.setGarnishNum(Integer.parseInt(mapList.get("count").toString()));
							}
							if(mapList.get("garnishItemId")!=null){
								garnishItem.setId(Long.parseLong(mapList.get("garnishItemId").toString()));
							}
							cartDishGarnish.setGarnishItem(garnishItem);
							CartDishGarnishId cartDishGarnishId = new CartDishGarnishId();
							cartDishGarnishId.setCartItemId(cartItem.getId().intValue());
							cartDishGarnishId.setGarnishItemId(Integer.parseInt(mapList.get("garnishItemId").toString()));
							cartDishGarnish.setId(cartDishGarnishId);
							int flag2 = cartDishGarnishDao.addCartDishGarnish(cartDishGarnish);
							if (flag2==-1) {
								return -1;
							}
						}
					}else{
						return -1;
					}
				}else{
					return -1;
				}
			}else{
				return -1;
			}
			return 1;
		}
		return -1;
	}

	/**getCartItemByHeaderId
	 * @Title: getCartItemByHeaderId
	 * @Description:根据购物车头获取所有的购物车详情
	 * @param:    
	 * @return: List<CartItem>
	 */
	@Override
	public List<CartItem> getCartItemByHeaderId(CartHeader cartHeader) {
		if (cartHeader!=null) {
			return cartItemDao.getCartItemByHeaderId(cartHeader);
		}
		return null;
	}
	
	

}
