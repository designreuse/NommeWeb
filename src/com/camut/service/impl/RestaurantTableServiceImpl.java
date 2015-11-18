/**
 * 
 */
package com.camut.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camut.dao.RestaurantTableDao;
import com.camut.framework.constant.GlobalConstant;
import com.camut.model.RestaurantTable;
import com.camut.model.Restaurants;
import com.camut.model.api.RestaurantTableApiModel;
import com.camut.model.api.TableEntity;
import com.camut.pageModel.PageTable;
import com.camut.service.RestaurantTableService;

/**
 * @ClassName RestaurantTableServiceImpl.java
 * @author wangpin
 * @createtime Jun 24, 2015
 * @author
 * @updateTime
 * @memo
 */
@Service
public class RestaurantTableServiceImpl implements RestaurantTableService {

	@Autowired
	private RestaurantTableDao restaurantTableDao;
	/**
	 * @Title: getRestaurantTable
	 * @Description: 获取商家的桌位信息
	 * @param:    Restaurants
	 * @return: RestaurantTable
	 */
	@Override
	public List<PageTable> getRestaurantTable(Restaurants restaurants) {
		if(restaurants!=null){
			List<RestaurantTable> list = restaurantTableDao.getRestaurantTable(restaurants);
			List<PageTable> list2 = new ArrayList<PageTable>();
			for(RestaurantTable restaurantTable:list){
				PageTable pageTable = new PageTable();
				if(restaurantTable!=null){//防止出现空指针
					BeanUtils.copyProperties(restaurantTable, pageTable);
				}
				list2.add(pageTable);
			}
			return list2;
		}
		return null;
	}
	/**
	 * @Title: addRestaurantTable
	 * @Description: 增加桌位信息
	 * @param:    RestaurantTable
	 * @return: int
	 */
	@Override
	public int addRestaurantTable(RestaurantTable restaurantTable) {
		if(restaurantTable!=null){
			return restaurantTableDao.addRestaurantTable(restaurantTable);
		}
		return -1;
	}
	/**
	 * @Title: updateRestaurantTable
	 * @Description:修改桌位信息
	 * @param:    RestaurantTable
	 * @return: int
	 */
	@Override
	public int updateRestaurantTable(RestaurantTable restaurantTable) {
		if(restaurantTable!=null){
			return restaurantTableDao.updateRestaurantTable(restaurantTable);
		}
		return -1;
	}
	
	/**
	 * @Title: getRestaurantTableById
	 * @Description: 通过id找桌位信息
	 * @param:    long
	 * @return: PageTable
	 */
	@Override
	public PageTable getRestaurantTableById(long id) {
			RestaurantTable restaurantTable = restaurantTableDao.getRestaurantTableById(id);
			if(restaurantTable!=null){
				PageTable pageTable = new PageTable();
				BeanUtils.copyProperties(restaurantTable, pageTable);
				return pageTable;
			}
		return null;
	}
	
	/**
	 * @Title: deleteRestaurantTable
	 * @Description: 删除桌位信息
	 * @param:    RestaurantTable
	 * @return: int
	 */
	@Override
	public int deleteRestaurantTable(RestaurantTable restaurantTable) {
		if(restaurantTable!=null){
			return restaurantTableDao.deleteRestaurantTable(restaurantTable);
		}
		return -1;
	}
	
	/**
	 * @Title: getRestaurantTableList
	 * @Description: 获取商家的桌位信息
	 * @param:    Restaurants
	 * @return: RestaurantTable
	 */
	@Override
	public List<RestaurantTableApiModel> getRestaurantTableList(Restaurants restaurants,String orderDate) {
		List<RestaurantTable> rtList = restaurantTableDao.getRestaurantTable(restaurants);
		//根据订单类型和订单时间获取桌位信息。
		List<TableEntity> tables=restaurantTableDao.getRestaurantTableNumberByOrderTypeAndOrderDate(restaurants.getId(),GlobalConstant.TYPE_RESERVATION, orderDate);
		List<RestaurantTableApiModel> rtamList = new ArrayList<RestaurantTableApiModel>();
		if(rtList != null){
			for (RestaurantTable restaurantTable : rtList) {//循环所有座位
				RestaurantTableApiModel rtam = new RestaurantTableApiModel();
				rtam.setAcceptanceNum(restaurantTable.getAcceptanceNum());
				rtam.setTableNum(restaurantTable.getTableNum());
				if(tables!=null){
					for (TableEntity item : tables) {
						if(restaurantTable.getAcceptanceNum()==item.getNumber()){
							rtam.setTableNum(rtam.getTableNum()-item.getCount());
						}
					}
				}
				if(rtam.getTableNum()>0){
					rtamList.add(rtam);
				}
			}
		}
		return rtamList;
	}

}
