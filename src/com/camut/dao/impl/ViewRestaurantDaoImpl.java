/**
 * 
 */
package com.camut.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.camut.dao.ViewRestaurantDao;
import com.camut.framework.constant.GlobalConstant;
import com.camut.model.ViewRestaurant;
import com.camut.model.api.SearchRestaurantsApiModel;
import com.camut.pageModel.PageFilter;

/**
 * @ClassName ViewRestaurantDaoImpl.java
 * @author wangpin
 * @createtime Jul 9, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class ViewRestaurantDaoImpl extends BaseDao<ViewRestaurant> implements ViewRestaurantDao {

	private long count;//总数量
	
	/**
	 * @Title: getRestaurants
	 * @Description: 获取商家信息
	 * @param: 
	 * @return: List<ViewRestaurant>
	 */
	@Override
	public List<ViewRestaurant> getRestaurants(ViewRestaurant viewRestaurant,PageFilter pf) {
		String hql = "from ViewRestaurant v where v.status=0";
		if(viewRestaurant.getClassificationName()!=null && !"".equals(viewRestaurant.getClassificationName())){
			hql += " and v.classificationName is not null and";
			if (viewRestaurant.getClassificationName().contains(",")) {//不止一个条件
				String[] classificationNames = viewRestaurant.getClassificationName().split(",");
				for (int i=0;i<classificationNames.length;i++) {
					if(i==0){//第一次循环
						hql += " (v.classificationName like '%"+classificationNames[i].trim()+"%' or";
					}
					else if(i==classificationNames.length-1){//最后一次循环
						hql += " v.classificationName like '%"+classificationNames[i].trim()+"%')";
					}
					else{
						hql += " v.classificationName like '%"+classificationNames[i].trim()+"%' or";
					}
				}
			}
			else{//只有一个条件
				hql += " v.classificationName like '%"+viewRestaurant.getClassificationName().trim()+"%'";
			}
			
		}
		//按评分搜索的条件不为空
		if(viewRestaurant.getScore()!=null && !"".equals(viewRestaurant.getScore())){
			hql += " and v.score is not null and v.score>="+viewRestaurant.getScore();
		}
		
		//按价格搜索的条件不为空
		if(viewRestaurant.getAvgPrice()!=null && !"".equals(viewRestaurant.getAvgPrice())){
			if(viewRestaurant.getAvgPrice()==20){//一个$
				hql += " and v.avgPrice is not null and v.avgPrice<="+20;
			}
			else if(viewRestaurant.getAvgPrice()==60){//5个$
				hql += " and v.avgPrice is not null and v.avgPrice>"+0;
			}
			else{
				hql += " and v.avgPrice is not null and v.avgPrice<="+viewRestaurant.getAvgPrice();
						//+" and  v.avgPrice>"+(viewRestaurant.getAvgPrice()-10);
			}
		}
		
		//是否有优惠的条件不为空
		if(viewRestaurant.getDiscountNum()!=null && !"".equals(viewRestaurant.getDiscountNum())){
			hql += " and v.discountNum is not null and v.discountNum>0";
		}
		
		//是否当前为营业时间不为空
		if(viewRestaurant.getOpentime()!=null && !"".equals(viewRestaurant.getOpentime())){
			hql += " and v.opentime is not null and v.opentime>0";
		}
		
		//筛选外送服务的选项不为空
		if(viewRestaurant.getIsdelivery()!=null && !"".equals(viewRestaurant.getIsdelivery())){
			hql += " and v.isdelivery is not null and v.isdelivery=1";
		}
		
		//筛选预定服务的选项不为空
		if (viewRestaurant.getIsreservation()!=null && !"".equals(viewRestaurant.getIsreservation())) {
			hql += " and v.isreservation is not null and v.isreservation=1";
		}
		
		//如果是地图显示，显示输入坐标的5公里之内的餐厅
		if(viewRestaurant.getDistance()!=null && !"".equals(viewRestaurant.getDistance())){
			hql += " and v.distance is not null and ACOS(SIN(("+viewRestaurant.getRestaurantLat().toString()+
					" * 3.1415) / 180 ) *SIN((v.restaurantLat * 3.1415) / 180 ) +COS(("+viewRestaurant.getRestaurantLat().toString()+
					" * 3.1415) / 180 ) * COS((v.restaurantLat * 3.1415) / 180 ) *COS(("+viewRestaurant.getRestaurantLng().toString()+
					" * 3.1415) / 180 - (v.restaurantLng * 3.1415) / 180 ) ) * 6378.140<="+GlobalConstant.GOOGLEMAP_MAX_DISTANCE;
		}
		
		count = this.count("select count(*) "+hql);
		//默认按照距离排序,排序规则
		if(pf.getOrder()!=null && !"".equals(pf.getOrder())){
			if(!pf.getOrder().equals("0")){
				if(pf.getOrder().equals("1")){//按字母排序
					hql += " order by v.restaurantName";
				}
				else if(pf.getOrder().equals("2")){//按价格排序
					hql += " order by v.avgPrice";
				}
				else if(pf.getOrder().equals("3")){//按评分排序
					hql += " order by v.score desc";
				}
			}
			else{//默认按距离排序
				hql += " order by ACOS(SIN(("+viewRestaurant.getRestaurantLat().toString()+
						" * 3.1415) / 180 ) *SIN((v.restaurantLat * 3.1415) / 180 ) +COS(("+viewRestaurant.getRestaurantLat().toString()+
						" * 3.1415) / 180 ) * COS((v.restaurantLat * 3.1415) / 180 ) *COS(("+viewRestaurant.getRestaurantLng().toString()+
						" * 3.1415) / 180 - (v.restaurantLng * 3.1415) / 180 ) ) * 6378.140 asc";
				
			}
		}
		if(viewRestaurant.getDistance()!=null && !"".equals(viewRestaurant.getDistance())){
			return this.find(hql);
		}else{
			return this.find(hql, pf.getOffset(),9);
		}
	}


	/**
	 * @Title: getTotal
	 * @Description: 获取商家总数量
	 * @param:    
	 * @return: int
	 */
	@Override
	public long getTotal() {
		return count;
	}
	
	/**
	 * @Title: getRestaurantsById
	 * @Description: 通过商家Id获取商家
	 * @param: long id
	 * @return ViewRestaurant  
	 */
	public ViewRestaurant getRestaurantsById(long id){
		String hql = "from ViewRestaurant vr where vr.id=:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return this.get(hql, map);
	}

	/**
	 * @Title: getViewRestaurants
	 * @Description: 搜索
	 * @param:    viewRestaurant pf
	 * @return: List<ViewRestaurant>
	 */
	@Override
	public List<ViewRestaurant> getViewRestaurants(SearchRestaurantsApiModel viewRestaurant, PageFilter pf) {
		String hql = "from ViewRestaurant v where v.status=0";
		if(viewRestaurant.getClassification()!=null && !"".equals(viewRestaurant.getClassification())){
			hql += " and v.classificationName is not null and";
			if (viewRestaurant.getClassification().contains(",")) {//不止一个条件
				String[] classificationNames = viewRestaurant.getClassification().split(",");
				for (int i=0;i<classificationNames.length;i++) {
					if(i==0){//第一次循环
						hql += " (v.classificationName like '%"+classificationNames[i]+"%' or";
					}
					else if(i==classificationNames.length-1){//最后一次循环
						hql += " v.classificationName like '%"+classificationNames[i]+"%')";
					}
					else{
						hql += " v.classificationName like '%"+classificationNames[i]+"%' or";
					}
				}
			}
			else{//只有一个条件
				hql += " v.classificationName like '%"+viewRestaurant.getClassification()+"%'";
			}
		}
		
		//是否有优惠的条件不为空
		if(viewRestaurant.getIsDiscount()!=null && !"".equals(viewRestaurant.getIsDiscount())&&!"0".equals(viewRestaurant.getIsDiscount())){
			hql += " and v.discountNum is not null and v.discountNum>0";
		}
		//是否当前为营业时间不为空
		if(viewRestaurant.getIsOpen()!=null && !"".equals(viewRestaurant.getIsOpen()) && !"0".equals(viewRestaurant.getIsOpen())){
			hql += " and v.opentime is not null and v.opentime>0";
		}
		//筛选外送服务的选项不为空
		if(viewRestaurant.getIsDelivery()!=null && !"".equals(viewRestaurant.getIsDelivery())&& !"0".equals(viewRestaurant.getIsDelivery())){
			hql += " and v.isdelivery is not null and v.isdelivery=1";
		}
		//筛选预定服务的选项不为空
		if (viewRestaurant.getIsReservation()!=null && !"".equals(viewRestaurant.getIsReservation())&& !"0".equals(viewRestaurant.getIsReservation())) {
			hql += " and v.isreservation is not null and v.isreservation=1";
		}
		count = this.count("select count(*) "+hql);
		//如果是地图显示，显示输入坐标的5公里之内的餐厅

		if ("1".equals(viewRestaurant.getAvgPrice())||"1".equals(viewRestaurant.getLetter())||
				"1".equals(viewRestaurant.getStars())||"1".equals(viewRestaurant.getDistance())) {
			hql += " order by";
			//按价格排序
			if("1".equals(viewRestaurant.getAvgPrice())){
				hql += " v.avgPrice";
			}
			//按照首字母排序
			else if("1".equals(viewRestaurant.getLetter())){
				hql += " v.restaurantName";
			}
			//按评分排序
			else if("1".equals(viewRestaurant.getStars())){
				hql += " v.score desc";
			}
			//按距离排序
			else if("1".equals(viewRestaurant.getDistance())){
				hql += " ACOS(SIN(("+viewRestaurant.getRestaurantLat().toString()+
						" * 3.1415) / 180 ) *SIN((v.restaurantLat * 3.1415) / 180 ) +COS(("+viewRestaurant.getRestaurantLat().toString()+
						" * 3.1415) / 180 ) * COS((v.restaurantLat * 3.1415) / 180 ) *COS(("+viewRestaurant.getRestaurantLng().toString()+
						" * 3.1415) / 180 - (v.restaurantLng * 3.1415) / 180 ) ) * 6378.140 asc";
			}
			
		}
		else{
			hql += " order by ACOS(SIN(("+viewRestaurant.getRestaurantLat().toString()+
					" * 3.1415) / 180 ) *SIN((v.restaurantLat * 3.1415) / 180 ) +COS(("+viewRestaurant.getRestaurantLat().toString()+
					" * 3.1415) / 180 ) * COS((v.restaurantLat * 3.1415) / 180 ) *COS(("+viewRestaurant.getRestaurantLng().toString()+
					" * 3.1415) / 180 - (v.restaurantLng * 3.1415) / 180 ) ) * 6378.140 asc";
		}
		
		
		return this.find(hql, pf.getOffset(),20);
	}
	

}
