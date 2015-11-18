package com.camut.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camut.dao.ConsumersAddressDao;
import com.camut.dao.ConsumersDao;
import com.camut.dao.RestaurantsDao;
import com.camut.model.Consumers;
import com.camut.model.ConsumersAddress;
import com.camut.model.Restaurants;
import com.camut.model.api.ConsumersAddressApiModel;
import com.camut.model.api.ConsumersAddressDefaultApiModel;
import com.camut.pageModel.PageConsumersAddress;
import com.camut.service.ConsumersAddressService;
import com.camut.utils.CommonUtil;
import com.camut.utils.GetLatLngByAddress;
import com.camut.utils.StringUtil;

/**
 * @dao ConsumersAddressServiceImpl.java
 * @author zyf
 * @createtime 03 03, 2015
 * @author
 * @updateTime
 * @memo
 */
@Service
public class ConsumersAddressServiceImpl implements ConsumersAddressService {

	@Autowired private ConsumersAddressDao consumersAddressDao;// 自动注入consumersAddressDao
	@Autowired private ConsumersDao consumersDao;
	@Autowired private RestaurantsDao restaurantsDao;

	/**
	 * @Title: getConsumersAddressById
	 * @Description: 通id查找用户 地址
	 * @param: id
	 * @return: List<ConsumersAddressApiModel>
	 */
	@Override
	public List<ConsumersAddressApiModel> getConsumersAddressById(long consumerId, long restaurantId) {
		List<ConsumersAddressApiModel> caamList = new ArrayList<ConsumersAddressApiModel>();
		Restaurants restaurants = new Restaurants();
		if(restaurantId > 0){
			restaurants = restaurantsDao.getRestaurantsById(restaurantId);
		}
		if(consumerId > 0){
			List<ConsumersAddress> caList = consumersAddressDao.getConsumersAddressById(consumerId);
			for (ConsumersAddress consumersAddress : caList) {
				ConsumersAddressApiModel caam = new ConsumersAddressApiModel();
				caam.setStreet(consumersAddress.getStreet());
				if(consumersAddress.getIsdefault()==null){
					caam.setIsDefault(2);
				}else {
					caam.setIsDefault(consumersAddress.getIsdefault());
				}
				caam.setConsignee(consumersAddress.getConsignee());
				caam.setPhone(consumersAddress.getPhone());
				caam.setIsDefault(consumersAddress.getIsdefault());
				caam.setFloor(consumersAddress.getFloor());
				caam.setCity(consumersAddress.getCity());
				caam.setProvince(consumersAddress.getProvince());
				caam.setConsumerId(consumersAddress.getConsumers().getId());
				caam.setAddressId(consumersAddress.getId());
				//距离
				double distance = 0;
				if(consumersAddress.getLat() != null && consumersAddress.getLng() != null && restaurants.getRestaurantLng() != null && restaurants.getRestaurantLat() != null){
					distance = CommonUtil.getDistance(consumersAddress.getLat(), consumersAddress.getLng(), restaurants.getRestaurantLng(), restaurants.getRestaurantLat());
				}
				caam.setDistance(distance);
				caamList.add(caam);
			}
		}
		return caamList;
	}
	
	/**
	 * @Title: getPageConsumersAddressListById
	 * @Description: 通id查找用户 地址
	 * @param: consumerId
	 * @param: restaurantId
	 * @return: List<PageConsumersAddress>
	 */
	public List<PageConsumersAddress> getPageConsumersAddressListById(long consumerId, long restaurantId){
		List<PageConsumersAddress> caamList = new ArrayList<PageConsumersAddress>();
		Restaurants restaurants = new Restaurants();
		if(restaurantId > 0){
			restaurants = restaurantsDao.getRestaurantsById(restaurantId);
		}
		if(consumerId > 0){
			List<ConsumersAddress> caList = consumersAddressDao.getConsumersAddressById(consumerId);
			for (ConsumersAddress consumersAddress : caList) {
				PageConsumersAddress caam = new PageConsumersAddress();
				caam.setStreet(consumersAddress.getStreet());
				if(consumersAddress.getIsdefault()==null){
					caam.setIsDefault(2);
				}else {
					caam.setIsDefault(consumersAddress.getIsdefault());
				}
				caam.setConsignee(consumersAddress.getConsignee());
				caam.setPhone(consumersAddress.getPhone());
				caam.setIsDefault(consumersAddress.getIsdefault());
				caam.setFloor(consumersAddress.getFloor());
				caam.setCity(consumersAddress.getCity());
				caam.setProvince(consumersAddress.getProvince());
				caam.setConsumerId(consumersAddress.getConsumers().getId());
				caam.setAddressId(consumersAddress.getId());
				//距离
				/*double distance = 0;
				if(consumersAddress.getLat() != null && consumersAddress.getLng() != null && restaurants.getRestaurantLng() != null && restaurants.getRestaurantLat() != null){
					distance = CommonUtil.getDistance(consumersAddress.getLat(), consumersAddress.getLng(), restaurants.getRestaurantLng(), restaurants.getRestaurantLat());
				}*/
				//caam.setDistance(distance);
				caamList.add(caam);
			}
		}
		return caamList;
	}
	
	
	
	
	/**
	 * @Title: getPageConsumersAddressById
	 * @Description: 网页获取用户地址列表
	 * @param: @param consumerId
	 * @param: @return
	 * @return List<PageConsumersAddress>  
	 */
	public List<PageConsumersAddress> getPageConsumersAddressById(long consumerId){
		List<ConsumersAddress> caList = consumersAddressDao.getConsumersAddressById(consumerId);
		List<PageConsumersAddress> pcaList = new ArrayList<PageConsumersAddress>();
		for (ConsumersAddress consumersAddress : caList) {
			PageConsumersAddress pca = new PageConsumersAddress();
			pca.setStreet(consumersAddress.getStreet());
			if(consumersAddress.getIsdefault()==null){
				pca.setIsDefault(2);
			}
			//pca.setIsDefault(consumersAddress.getIsdefault());
			pca.setFloor(consumersAddress.getFloor());
			pca.setCity(consumersAddress.getCity());
			pca.setProvince(consumersAddress.getProvince());
			pca.setConsumerId(consumersAddress.getConsumers().getId());
			pca.setAddressId(consumersAddress.getId());
			pca.setLat(consumersAddress.getLat()+"");
			pca.setLng(consumersAddress.getLng()+"");
			pca.setPhone(consumersAddress.getPhone());
			pca.setZipcode(consumersAddress.getZipcode());
			pca.setConsignee(consumersAddress.getConsignee());
			pca.setFullAddress(consumersAddress.getStreet()+" "+consumersAddress.getCity()+" "+consumersAddress.getProvince());
			pcaList.add(pca);
		}
		return pcaList;
	}

	/**
	 * @Title: addConsumersAddress
	 * @Description: 添加用户 地址
	 * @param: id
	 * @return: -1表示添加失败 ，1表示添加成功
	 */
	@Override
	public int addConsumersAddress(ConsumersAddressApiModel consumersAddressApiModel) {
		ConsumersAddress consumersAddress = new ConsumersAddress();
		Consumers consumers = new Consumers();
		consumers.setId(consumersAddressApiModel.getConsumerId());
		consumersAddress.setConsumers(consumers);
		consumersAddress.setStreet(consumersAddressApiModel.getStreet());
		consumersAddress.setFloor(consumersAddressApiModel.getFloor());
		consumersAddress.setCity(consumersAddressApiModel.getCity());
		consumersAddress.setProvince(consumersAddressApiModel.getProvince());
		consumersAddress.setFullAddress(consumersAddressApiModel.getStreet()+",#"+consumersAddressApiModel.getFloor()+","+consumersAddressApiModel.getCity()+","+consumersAddressApiModel.getProvince());
		consumersAddress.setIsdefault(2);
		//获取地址经纬度
		String address = consumersAddressApiModel.getFloor()+consumersAddressApiModel.getStreet()+consumersAddressApiModel.getProvince()+consumersAddressApiModel.getCity();
		Map<String, Object> map = GetLatLngByAddress.getCoordinate(address, false);//地址,是否使用代理，默认不使用
		if(map.get("status").toString().equals("OK")){//能获取到经纬度
			Object o=  map.get("result");
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> list = (List<Map<String,Object>>) o;
			Map<String, Object> map2 = list.get(0);
			String lat = map2.get("lat").toString();
			String lng = map2.get("lng").toString();
			consumersAddress.setLat(Double.parseDouble(lat));
			consumersAddress.setLng(Double.parseDouble(lng));
		}
		//consumersAddress.setLat(51.1);
		//consumersAddress.setLng(-114.15);
		return consumersAddressDao.addConsumersAddress(consumersAddress);
	}

	/**
	 * @Title: updateConsumersAddress
	 * @Description: 用户地址修改
	 * @param: consumersAddress
	 * @return: int -1修改失败 1修改成功
	 */
	@Override
	public int updateConsumersAddress(ConsumersAddressApiModel consumersAddressApiModel) {
		if (consumersAddressApiModel != null && consumersAddressApiModel.getAddressId() > 0) {
			ConsumersAddress consumersAddress = consumersAddressDao.getConsumersAddress(consumersAddressApiModel.getAddressId());
			if (consumersAddress != null) {
				BeanUtils.copyProperties(consumersAddressApiModel, consumersAddress);
				return consumersAddressDao.updateConsumersAddress(consumersAddress);
			}
		}
		return -1;
	}

	/**
	 * @Title: deleteConsumersAddress
	 * @Description: 用户地址删除
	 * @param: id
	 * @return: int -1删除失败 ，1删除成功
	 */
	@Override
	public int deleteConsumersAddressById(long addressId) {
		if (addressId > 0) {
			return consumersAddressDao.deleteConsumersAddressById(addressId);
		}
		return -1;
	}

	/**
	 * @Title: getAddressById
	 * @Description: 根据id查找地址
	 * @param:    
	 * @return: ConsumersAddress
	 */
	@Override
	public ConsumersAddress getAddressById(long id) {
		if (id!=0) {
			return consumersAddressDao.getConsumersAddress(id);
		}
		return null;
	}
	
	/**
	 * @Title: getPageAddressById
	 * @Description: 根据id获取页面地址对象
	 * @param: @param id
	 * @param: @return
	 * @return PageConsumersAddress  
	 */
	public PageConsumersAddress getPageAddressById(long id){
		ConsumersAddress ca = consumersAddressDao.getConsumersAddress(id);
		if(ca!=null){
			PageConsumersAddress pca = new PageConsumersAddress();
			pca.setAddressId(ca.getId());
			pca.setIsDefault(ca.getIsdefault());
			pca.setFloor(ca.getFloor());
			pca.setStreet(ca.getStreet());
			pca.setCity(ca.getCity());
			pca.setProvince(ca.getProvince());
			pca.setPhone(ca.getPhone());
			pca.setConsignee(ca.getConsignee());
			return pca;
		}else{
			return null;
		}
	}
	
	/**
	 * @Title: updateWebConsumersAddress
	 * @Description: 修改用户地址
	 * @param: @param consumersAddress
	 * @param: @return
	 * @return int  
	 */
	public int updateWebConsumersAddress(ConsumersAddress consumersAddress,long consumerId){
		if(consumersAddress.getIsdefault()==1){//如果干修改的新地址设为默认的，将原来地址设为非默认
			int temp = consumersAddressDao.setConsumersDefaultAddressNotDefault(consumerId);
			if(temp>0){
				int temp2 = consumersAddressDao.updateConsumersAddress(consumersAddress);
				if(temp2>0){
					return 1;
				}else{
					return -1;
				}
			}else{
				return -1;
			}
		}else{
			int temp2 = consumersAddressDao.updateConsumersAddress(consumersAddress);
			if(temp2>0){
				return 1;
			}else{
				return -1;
			}
		}
	}
	
	
	/**
	 * @Title: addWebConsumersAddress
	 * @Description: 新增用户地址
	 * @param: @param consumersAddress
	 * @param: @param consumerId
	 * @param: @return
	 * @return int  
	 */
	public int addWebConsumersAddress(ConsumersAddress consumersAddress, long consumerId){
		if(consumersAddress.getIsdefault()==1){//如果修改的新地址设为默认的，将原来地址设为非默认
			int temp = consumersAddressDao.setConsumersDefaultAddressNotDefault(consumerId);
			if(temp>0){
				int temp2 = consumersAddressDao.addConsumersAddress(consumersAddress);
				if(temp2>0){
					return 1;
				}else{
					return -1;
				}
			}else{
				return -1;
			}
		}else{
			int temp2 = consumersAddressDao.addConsumersAddress(consumersAddress);
			if(temp2>0){
				return 1;
			}else{
				return -1;
			}
		}
	}
	
	/**
	 * @Title: getConsumersAddressDefault
	 * @Description: 获取用户默认地址
	 * @param:    consumersid
	 * @return: ConsumersAddress
	 */
	@Override
	public ConsumersAddressDefaultApiModel getConsumersAddressDefault(long consumerId,long restaurantId) {
		return consumersAddressDao.getConsumersAddressDefault(consumerId, restaurantId);
	}
	
	/**
	 * @Title: getConsumersAddressDefault
	 * @Description: 获取用户在配送范围内的地址
	 * @param:    consumersid
	 * @return: ConsumersAddress
	 */
	@Override
	public List<ConsumersAddressApiModel> getConsumersAddressInDistance(long consumerId, long restaurantId) {
		return consumersAddressDao.getConsumersAddressInDistance(consumerId, restaurantId);
	}
	
	/**
	 * @Title: ConsumersAddressDefaultApiModel
	 * @Description: 获取用户默认地址
	 * @param:    consumersid
	 * @return: ConsumersAddress
	 */
	@Override
	public ConsumersAddressDefaultApiModel getConsumersAddressDefaultById(long consumerId) {
		ConsumersAddress ca = consumersAddressDao.getConsumersAddressDefaultById(consumerId);
		ConsumersAddressDefaultApiModel cadam = new ConsumersAddressDefaultApiModel();
		if(ca != null){
			cadam.setAddressId(ca.getId());
			cadam.setAddress(ca.getFullAddress());
			return cadam;
		}
		return null;
	}

}
