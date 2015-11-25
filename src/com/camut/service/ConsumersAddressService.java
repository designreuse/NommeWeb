package com.camut.service;

import java.util.Date;
import java.util.List;

import com.camut.model.ConsumersAddress;
import com.camut.model.api.ConsumersAddressApiModel;
import com.camut.model.api.ConsumersAddressDefaultApiModel;
import com.camut.pageModel.PageConsumersAddress;

/**
 * @dao ConsumersAddressService.java
 * @author zyf
 * @createtime 03 03, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface ConsumersAddressService {

	/**
	 * @Title: getConsumersAddressById
	 * @Description: 通id查找用户 地址
	 * @param: consumerId
	 * @param: restaurantId
	 * @return: List<ConsumersAddressApiModel>
	 */
	public List<ConsumersAddressApiModel> getConsumersAddressById(String consumerUuid, String restaurantUuid);
	
	/**
	 * @Title: getPageConsumersAddressListById
	 * @Description: 通id查找用户 地址
	 * @param: consumerId
	 * @param: restaurantId
	 * @return: List<PageConsumersAddress>
	 */
	public List<PageConsumersAddress> getPageConsumersAddressListByConsumerUuid(String consumerUuid);
	
	/**
	 * @Title: getPageConsumersAddressById
	 * @Description: 网页获取用户地址列表
	 * @param: @param consumerId
	 * @param: @return
	 * @return List<PageConsumersAddress>  
	 */
	public List<PageConsumersAddress> getPageConsumersAddressByConsumerUuid(String consumerUuid);
	
	/**
	 * @Title: addConsumersAddress
	 * @Description: 添加用户 地址
	 * @param:    consumersAddress
	 * @return: -1表示添加失败 ，1表示添加成功
	 */
	public int addConsumersAddress(ConsumersAddressApiModel consumersAddressApiModel);
	
	/**
	 * @Title: updateConsumersAddress
	 * @Description: 用户地址修改
	 * @param:    consumersAddress
	 * @return: int -1修改失败 1修改成功
	 */
	public int updateConsumersAddress(ConsumersAddressApiModel consumersAddressApiModel);
	
	/**
	 * @Title: deleteConsumersAddress
	 * @Description: 用户地址删除
	 * @param:    id
	 * @return: int -1删除失败 ，1删除成功
	 */
	public int deleteConsumersAddressById(long addressId);
	
	/**
	 * @Title: getAddressById
	 * @Description: 根据id查找地址
	 * @param:    
	 * @return: ConsumersAddress
	 */
	public ConsumersAddress getAddressById(long id);
	
	/**
	 * @Title: getPageAddressById
	 * @Description: 根据id获取页面地址对象
	 * @param: @param id
	 * @param: @return
	 * @return PageConsumersAddress  
	 */
	public PageConsumersAddress getPageAddressById(long id);
	
	/**
	 * @Title: updateWebConsumersAddress
	 * @Description: 修改用户地址
	 * @param: @param consumersAddress
	 * @param: @return
	 * @return int  
	 */
	public int updateWebConsumersAddress(ConsumersAddress consumersAddress, String consumerUuid);
	
	/**
	 * @Title: addWebConsumersAddress
	 * @Description: 新增用户地址
	 * @param: @param consumersAddress
	 * @param: @param consumerId
	 * @param: @return
	 * @return int  
	 */
	public int addWebConsumersAddress(ConsumersAddress consumersAddress, String consumerUuid);
	
	/**
	 * @Title: getConsumersAddressDefault
	 * @Description: 获取用户默认地址
	 * @param:    consumersid
	 * @return: ConsumersAddress
	 */
	public ConsumersAddressDefaultApiModel getConsumersAddressDefault(String consumerUuid, String restaurantUuid);
	
	/**
	 * @Title: getConsumersAddressDefault
	 * @Description: 获取用户在配送范围内的地址
	 * @param:    consumersid
	 * @return: ConsumersAddress
	 */
	public List<ConsumersAddressApiModel>  getConsumersAddressInDistance(String consumerUuid, String restaurantUuid);
	
	/**
	 * @Title: ConsumersAddressDefaultApiModel
	 * @Description: 获取用户默认地址
	 * @param:    consumersid
	 * @return: ConsumersAddress
	 */
	public ConsumersAddressDefaultApiModel getConsumersAddressDefaultByConsumerUuid(String consumerUuid);
	
	
	/**
	 * @Title: getCurrentLocalTimeFromConsumersAddressDefaultByConsumerUuid
	 * @Description: get the local time from customer default address
	 * @param: consumerUuid
	 * @return: Date
	 */
	public Date getCurrentLocalTimeFromConsumersDefaultAddress(String consumerUuid);

}
