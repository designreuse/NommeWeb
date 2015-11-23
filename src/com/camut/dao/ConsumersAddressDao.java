package com.camut.dao;

import java.util.List;

import com.camut.model.ConsumersAddress;
import com.camut.model.api.ConsumersAddressApiModel;
import com.camut.model.api.ConsumersAddressDefaultApiModel;

/**
 * @dao ConsumersAddressDao.java
 * @author zyf
 * @createtime 03 03, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface ConsumersAddressDao {

	/**
	 * @Title: getConsumersAddressById
	 * @Description: 通id查找用户 地址
	 * @param:    id
	 * @return: List<ConsumersAddress>
	 */
	public List<ConsumersAddress> getConsumersAddressByUuid(String consumerUuid);
	
	/**
	 * @Title: addConsumersAddress
	 * @Description: 添加用户 地址
	 * @param:    id
	 * @return: -1表示添加失败 ，1表示添加成功
	 */
	public int addConsumersAddress(ConsumersAddress consumersAddress);
	
	/**
	 * @Title: getConsumersAddress
	 * @Description: 根据id查询地址
	 * @param:    id
	 * @return: consumersAddress
	 */
	public ConsumersAddress getConsumersAddress(long id);
	
	/**
	 * @Title: updateConsumersAddress
	 * @Description: 用户地址修改
	 * @param:    consumersAddress
	 * @return: int -1修改失败 1修改成功
	 */
	public int updateConsumersAddress(ConsumersAddress consumersAddress);
	
	/**
	 * @Title: deleteConsumersAddress
	 * @Description: 用户地址删除
	 * @param:    consumersid
	 * @return: int -1删除失败 ，1删除成功
	 */
	public int deleteConsumersAddressById(long addressId);
	
	/**
	 * @Title: setConsumersDefaultAddressNotDefault
	 * @Description: 修改增加地址时，如果设置了新地址为默认，就在保存新地址前调用该方法 将原来的默认地址设为非默认
	 * @param: @param consumerId
	 * @param: @return
	 * @return int  
	 */
	public int setConsumersDefaultAddressNotDefault(String consumersUuid);
	
	/**
	 * @Title: getConsumersAddressDefault
	 * @Description: 获取用户默认地址
	 * @param:    consumersid
	 * @return: ConsumersAddress
	 */
	public ConsumersAddressDefaultApiModel getConsumersAddressDefault(String consumersUuid, String restaurantUuid);
	
	/**
	 * @Title: getConsumersAddressDefault
	 * @Description: 获取用户在配送范围内的地址
	 * @param: consumersid
	 * @return: ConsumersAddress
	 */
	public List<ConsumersAddressApiModel> getConsumersAddressInDistance(String consumersUuid,String restaurantUuid);
	
	/**
	 * @Title: ConsumersAddress
	 * @Description: 通id查找用户 默认地址
	 * @param:    id
	 * @return: List<ConsumersAddress>
	 */
	public ConsumersAddress getConsumersAddressDefaultByUuid(String consumerUuid);
	
}
