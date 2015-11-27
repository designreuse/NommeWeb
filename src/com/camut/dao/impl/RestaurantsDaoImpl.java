/**
 * 
 */
package com.camut.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.camut.dao.RestaurantsDao;
import com.camut.model.Restaurants;
import com.camut.pageModel.PageFilter;
import com.camut.utils.StringUtil;

/**
 * @ClassName RestaurantsDaoImpl.java
 * @author wangpin
 * @createtime May 27, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class RestaurantsDaoImpl extends BaseDao<Restaurants> implements RestaurantsDao {

	private long count;
	/**
	 * @Title: getCount
	 * @Description: 获取总记录条数
	 * @param: 
	 * @return int  
	 */
	public long getCount(){
		return count;
	}
	
	/* (non-Javadoc)
	 * @see com.camut.service.RestaurantsService#getAllRestaurants()
	 */
	@Override
	public List<Restaurants> getAllRestaurants(PageFilter pf) {
		int page = (pf.getOffset()/pf.getLimit())+1;//第几页
		int rows = pf.getLimit();//每页行数
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = "from Restaurants r where r.status>=0";
		String countHql = "select count(*) "+hql; 
		if(StringUtil.isNotEmpty(pf.getSearch())){
			String search = pf.getSearch();
			hql += " and r.restaurantName like '%" + search + "%' ";
		}
		
		String orderField = pf.getSort();//排序字段
		if(StringUtil.isNotEmpty(orderField)){
			hql += " order by r."+orderField;
		}else{
			hql += " order by r.restaurantName";
		}
		String  sort = pf.getOrder();//正序 asc  倒序 desc
		if(StringUtil.isNotEmpty(sort)){
			hql += " " + sort;
		}else{
			hql += " desc";
		}
		count = this.count(countHql, map).intValue();
		return this.find(hql, map, page, rows);
		
	}
	
	/* (non-Javadoc)
	 * @see com.camut.dao.RestaurantsDao#getRestaurantsById(java.lang.String)
	 */
	@Override
	public Restaurants getRestaurantsById(long id) {
		if(id>0){
			String hql = "from Restaurants r where r.id=:id and r.status>=0";
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", id);
			return this.get(hql, map);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.camut.dao.RestaurantsDao#getRestaurantsById(java.lang.String)
	 */
	@Override
	public Restaurants getRestaurantsByUuid(String restaurantUuid) {
		if(StringUtil.isNotEmpty(restaurantUuid)){
			String hql = "from Restaurants r where r.uuid=:restaurantUuid and r.status>=0";
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("restaurantUuid", restaurantUuid);
			return this.get(hql, map);
		}
		return null;
	}

	/*
	 * @Title: addRestaurants
	 * @Description: 增加商家
	 * @param:    restaurants
	 * @return: int 返回的主键
	 */
	@Override
	public String addRestaurants(Restaurants restaurants) {
			try {
				Serializable serializable = this.save(restaurants);
				return serializable.toString();
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}
	}

	/*
	 * @Title: deleteRestaurants
	 * @Description: 删除商家; delete a restaurant
	 * @param:    Restaurants
	 * @return: int -1删除失败 1删除成功; -1: failed, 1: success
	 */
	@Override
	public int deleteRestaurants(Restaurants restaurants) {
			restaurants.setStatus(-1);
			try {
				this.update(restaurants);
				return 1;
			} catch (Exception e) {
				// TODO: handle exception
				return -1;
			}
	}

	/*
	 * @Title: updateRestaurants
	 * @Description: 修改商家资料; update restaurants' info
	 * @param:    Restaurants
	 * @return: int -1修改失败 1修改成功; -1:failed, 1:success
	 */
	@Override
	public int updateRestaurants(Restaurants restaurants) {
		try {
			this.update(restaurants);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}

	/* @Title: getRestaurantsByName
	 * @Description: 通过店名找商家
	 * @param:    
	 * @return:   
	 */
	@Override
	public Restaurants getRestaurantsByName(String restaurantsName) {
		String hql = "from Restaurants r where r.restaurantName=:restaurantName and r.status=0)";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("restaurantName", restaurantsName);
		return this.get(hql, map);
	}

	/*
	 * @Title: restaurantsUserLogin
	 * @Description: 商家员工登陆,根据登陆名查找对象,验证用的
	 * @param:    String
	 * @return: RestaurantsUser
	 */
	@Override
	public Restaurants getRestaurantsByNameForCheck(String restaurantsName) {
		String hql = "from Restaurants r where r.restaurantName=:restaurantName and (r.status=0 or r.status=1)";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("restaurantName", restaurantsName);
		return this.get(hql, map);
	}
	
	/**
	 * @Title: getRestaurantByIdToAudit
	 * @Description: 通过id获取餐厅用于审核，不管是什么状态的都可以找到
	 * @param: id
	 * @return Restaurants  
	 */
	public Restaurants getRestaurantByUuidToAudit(String uuid){
		if(StringUtil.isNotEmpty(uuid)){
			String hql = "from Restaurants r where r.uuid=:uuid and r.status>=-1";
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("uuid", uuid);
			return this.get(hql, map);
		}
		return null;
	}
	
	/**
	 * @Title: auditRestaurant
	 * @Description: 获取商家信息到前台表格用于下拉框选择
	 * @param: 
	 * @return List<PageRestaurantSelect> 
	 */
	public List<Restaurants> getAllRestaurantsName(){
		String hql = "from Restaurants r where r.status>=0 order by r.restaurantName";
		return this.find(hql);
	}

}
