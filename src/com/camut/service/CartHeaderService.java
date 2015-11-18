package com.camut.service;

import com.camut.model.CartHeader;
import com.camut.model.api.CartApiModel;
import com.camut.model.api.CartHeaderLoginApiModel;
import com.camut.pageModel.PageCartHeader;

public interface CartHeaderService {

	/**
	 * @Title: getCartHeaderByMobileToken
	 * @Description:根据设备号查询购物车头
	 * @param:    mobileToken
	 * @return: CartHeaderLoginApiModel
	 */
	public CartHeaderLoginApiModel getCartHeaderByMobileToken(String mobileToken);
	
	/**
	 * @Title: getCartHeader
	 * @Description:根据设备号查询对象
	 * @param:    mobileToken
	 * @return: CartHeader
	 */
	public CartHeader getCartHeader(String mobileToken);
	
	/**
	 * @Title: deleteCartHeaderByMobileToken
	 * @Description:删除购物车信息
	 * @param:    String
	 * @return: CartHeader
	 */
	public int deleteCartHeaderByMobileToken(String mobileToken);
	
	/**
	 * @Title: deleteCartHeader
	 * @Description:删除购物车信息
	 * @param:    cartHeader
	 * @return: int
	 */
	public int deleteCartHeader(CartHeader cartHeader);
	
	/**
	 * 删除菜品并且重新获取配菜费
	 * @param mobileTOken
	 * @param Dish
	 * @param distance
	 * @return
	 */

	public int deleteCartDish(String mobileToken,int dishId,String consumerUuid);

	/**
	 * @Title: updateCartHeader
	 * @Description: 修改购物车头信息
	 * @param: @param cartHeader
	 * @param: @return
	 * @return int  
	 */
	public int updateCartHeader(CartHeader cartHeader);
	
	/**
	 * 重新计算价格
	 * @return
	 */
	public CartApiModel reCalcCost(String mobileToken,double restaurantLat,double restaurantLng,long discountId,String restaurantUuid,String consumerUuid);

	/**
	 * @Title: getPageCartHeaderByMobileToken
	 * @Description: 通过设备号获取PageCartHeader
	 * @param: @param mobileToken
	 * @param: @return
	 * @return PageCartHeader  
	 */
	//public PageCartHeader getPageCartHeaderByMobileToken(String mobileToken);
	
	/**
	 * @Title: getCatrHeaderByConsumerUuid
	 * @Description: 通过用户id获取购物车
	 * @param: @param consumerId
	 * @param: @return
	 * @return CartHeader  
	 */
	public CartHeader getCatrHeaderByConsumerUuid(String consumerUuid);

	/**
	 * 根据用户id查找对象
	 * @param mobileToken  consumerId
	 * @return
	 */
	public CartHeader getCartHeaderByConsumerUuid(String consumerUuid);

	/**
	 * 将token保存的已有的用户
	 * @param mobileToken  consumerId
	 * @return
	 */
	public void saveTokenToConsumerId(CartHeader cartHeader);
	
	/**
	 * @Title: getCartHeaderById
	 * @Description: 通过id获取购物车头
	 * @param: @param cartHeaderId
	 * @param: @return
	 * @return CartHeader  
	 */
	public CartHeader getCartHeaderById(long cartHeaderId);
	
	/**
	 * 根据用户id查找对象
	 * @param mobileToken  consumerId
	 * @return
	 */
	public PageCartHeader getPageCartHeaderByConsumerUuid(String consumerUuid);
	
	/**
	 * @Title: getWebCartHeaderByConsumerUuid
	 * @Description: 获取用于web页面显示
	 * @param: @param consumerId
	 * @param: @return
	 * @return CartHeader  
	 */
	public CartHeader getWebCartHeaderByConsumerUuid(String consumerUuid);
	
	
	
}
