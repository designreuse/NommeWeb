/**
 * 
 */
package com.camut.dao;

import com.camut.model.CartHeader;
import com.camut.model.api.CartApiModel;

/**
 * @ClassName CartHeaderDao.java
 * @author wangpin
 * @createtime Jul 22, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface CartHeaderDao {

	/**
	 * @Title: getCartHeaderByMobileToken
	 * @Description:根据设备号查询购物车头
	 * @param:    String
	 * @return: CartHeader
	 */
	public CartHeader getCartHeaderByMobileToken(String mobileToken,String consumerUuid);
	
	/**
	 * @Title: getCartHeaderByMobileToken
	 * @Description:根据设备号查询购物车头
	 * @param:    String
	 * @return: CartHeader
	 */
	public CartHeader getCartHeaderByMobileToken(String mobileToken);
	
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
	 * @Title: addCartHeader
	 * @Description: 增加购物车头
	 * @param:    CartHeader
	 * @return: int 1成功 -1失败
	 */
	public int addCartHeader(CartHeader cartHeader);
	
	/**
	 * @Title: addCartHeader
	 * @Description: 修改购物车头
	 * @param:    CartHeader
	 * @return: int 1成功 -1失败
	 */
	public int updateCartHeader(CartHeader cartHeader);
	
	/**
	 * 删除菜品信息
	 * @param cartId
	 * @param dishId
	 * @return
	 */
	public int deleteCartDish(long cartId,int dishId);
	
	/**
	 * @Title: getCartHeaderById；
	 * @Description: 根据id获取对象
	 * @param:    cartHeaderId
	 * @return: CartHeader
	 */
	public CartHeader getCartHeaderById(long cartHeaderId);

	/**
	 * 获取购物车价格
	 * @param mobileToken
	 * @return
	 */
	public CartApiModel getCartInfoForSql(String mobileToken,String consumerUuid);

	/**
	 * 根据设备好删除配送的菜品
	 * @param mobileToken
	 * @return
	 */
	public int  deleteFreeCartItem(String consumerUuid);
	
	/**
	 * 将token保存的已有的用户
	 * @param mobileToken  consumerId
	 * @return
	 */
	public void saveTokenToConsumerId(CartHeader cartHeader);
	
	/**
	 * @Title: getCartHeaderById；
	 * @Description: 根据id获取对象
	 * @param:    cartHeaderId
	 * @return: CartHeader
	 */
	public CartHeader getCartHeaderByConsumerUuid(String consumerUuid);
	

	/**
	 * @Title: getWebCartHeaderByConsumerUuid
	 * @Description: 根据id获取对象
	 * @param:    cartHeaderId
	 * @return: CartHeader
	 */
	public CartHeader getWebCartHeaderByConsumerUuid(String consumerUuid);
	
	
}
