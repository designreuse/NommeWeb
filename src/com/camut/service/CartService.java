package com.camut.service;

import com.camut.model.api.CartHeaderApiModel;
import com.camut.model.api.ResultApiModel;

/**
 * @ClassName CartService.java
 * @author wangpin
 * @createtime Jul 22, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface CartService {

	/**
	 * @Title: getCartHeaderApiModel
	 * @Description: 获取购物车信息
	 * @param:    String，int
	 * @return: CartHeaderApiModel
	 */
	public CartHeaderApiModel getCartHeaderApiModel(String mobileToken, String consumerUuid);
	
	/**
	 * @Title: getCartHeaderApiModel
	 * @Description: 获取购物车信息
	 * @param:    String，int
	 * @return: CartHeaderApiModel
	 */
	public CartHeaderApiModel getCartHeaderApiModelByConsumerUuid(String consumerUuid);//double consumerLng, double consumerLat, 
	
	/**
	 * @Title: getCartHeaderApiModel
	 * @Description: 获取购物车信息
	 * @param:    String，int
	 * @return: CartHeaderApiModel
	 */
	public CartHeaderApiModel getRegistCartHeaderApiModel(String consumerUuid);//, Double consumerLng, Double consumerLat
	
	/**
	 * @Title: getCartHeaderByMobileToken
	 * @Description: 根据设备号获取购物车头
	 * @param:    String
	 * @return: CartHeader
	 */
	public ResultApiModel addAndCheckCart(String context);
	
	/**
	 * @Title: deleteCatrItem
	 * @Description: 删除一条购物车中的菜品
	 * @param: long cartItemId
	 * @return int  
	 */
	public int deleteCatrItem(String cartItemId);
	
	/**
	 * @Title: addAndCheckCart
	 * @Description: 增加购物车信息
	 * @param: String context
	 * @return: int 1成功 0失败
	 */
	public int addWebCart(String context);
	
	/**
	 * @Title: deleteCartByConsumerId
	 * @Description: 通过用户Id删除购物车
	 * @param: @return
	 * @return int  
	 */
	public int deleteCartByConsumerUuid(String consumerUuid);
	
	/**
	 * @Title: deleteCartByConsumerId
	 * @Description: 删除购物车
	 * @param: @return
	 * @return int  
	 */
	public int deleteCartHeader(String mobileToken, String consumerUuid);
	
}
