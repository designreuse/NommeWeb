package com.camut.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.camut.dao.ConsumersAddressDao;
import com.camut.model.ConsumersAddress;
import com.camut.model.api.ConsumersAddressApiModel;
import com.camut.model.api.ConsumersAddressDefaultApiModel;
import com.camut.utils.Log4jUtil;

/**
 * @dao ConsumersAddressDaoImpl.java
 * @author zyf
 * @createtime 03 03, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class ConsumersAddressDaoImpl extends BaseDao<ConsumersAddress> implements ConsumersAddressDao {

	/**
	 * @Title: getConsumersAddressById
	 * @Description: 通id查找用户 地址
	 * @param: id
	 * @return: List<ConsumersAddress>
	 */
	@Override
	public List<ConsumersAddress> getConsumersAddressByUuid(String consumerUuid) {
		String hql = "from ConsumersAddress ca where ca.consumers.uuid=:consumerUuid order by ca.isdefault asc";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("consumerUuid", consumerUuid);
		List<ConsumersAddress> acList = this.find(hql, map);
		return acList;
	}

	/**
	 * @Title: addConsumersAddress
	 * @Description: 添加用户 地址
	 * @param: id
	 * @return: -1表示添加失败 ，1表示添加成功
	 */
	@Override
	public int addConsumersAddress(ConsumersAddress consumersAddress) {
		try {
			this.save(consumersAddress);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * @Title: getConsumersAddress
	 * @Description: 根据id查询地址
	 * @param:    id
	 * @return: consumersAddress
	 */
	@Override
	public ConsumersAddress getConsumersAddress(long id) {
		String hql = "from ConsumersAddress ca where ca.id=:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return this.get(hql, map);
	}
	
	/**
	 * @Title: updateConsumersAddress
	 * @Description: 用户地址修改
	 * @param: consumersAddress
	 * @return: int -1修改失败 1修改成功
	 */
	@Override
	public int updateConsumersAddress(ConsumersAddress consumersAddress) {
		try {
			this.update(consumersAddress);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * @Title: deleteConsumersAddress
	 * @Description: 用户地址删除
	 * @param: id
	 * @return: int -1删除失败 ，1删除成功
	 */
	@Override
	public int deleteConsumersAddressById(long addressId) {
		String hql = "from ConsumersAddress ca where ca.id=:addressId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("addressId", addressId);
		try {
			ConsumersAddress address = this.get(hql, map);
			if(address!=null){
				this.delete(address);
			}else{
				return -1;
			}
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * @Title: setConsumersDefaultAddressNotDefault
	 * @Description: 修改增加地址时，如果设置了新地址为默认，就在保存新地址前调用该方法 将原来的默认地址设为非默认
	 * @param: @param consumerId
	 * @param: @return
	 * @return int  
	 */
	public int setConsumersDefaultAddressNotDefault(String consumersUuid){
		String hql = "from ConsumersAddress ca where ca.consumers.uuid=:consumersUuid and ca.isdefault=:isdefault";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("consumersUuid", consumersUuid);
		map.put("isdefault", 1);
		List<ConsumersAddress> list = this.find(hql, map);
		boolean flag = true; 
		for (ConsumersAddress consumersAddress : list) {
			consumersAddress.setIsdefault(2);
			try {
				this.update(consumersAddress);
				
			} catch (Exception e) {
				// TODO: handle exception
				flag = false;
			}
		}
		if(flag){
			return 1;
		}else{
			return -1;
		}
	}

	/**
	 * @Title: getConsumersAddressDefault
	 * @Description: 获取用户默认地址
	 * @param:    consumersid
	 * @return: ConsumersAddress
	 */
	@Override
	public ConsumersAddressDefaultApiModel getConsumersAddressDefault(String consumerUuid,String restaurantUuid) {
		String sql = "select t.id as addressId,t.full_address as address " +
				"from (select restaurant_lng as rlng,restaurant_lat as rlat,distance from dat_restaurants where uuid="+restaurantUuid+") v LEFT JOIN "+ 
				"(select * from tbl_consumers_address where consumer_uuid='"+consumerUuid+"') t ON "+
				"ACOS(SIN((t.lat * 3.1415) / 180 ) *SIN((v.rlat * 3.1415) / 180 ) +COS((t.lat"+
				"* 3.1415) / 180 ) * COS((v.rlat * 3.1415) / 180 ) *COS((t.lng"+
				"* 3.1415) / 180 - (v.rlng * 3.1415) / 180 ) ) * 6378.140<=v.distance where t.isdefault=1";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(ConsumersAddressDefaultApiModel.class));
		query.addScalar("addressId", new org.hibernate.type.LongType());
		query.addScalar("address", new org.hibernate.type.StringType());
		try {
			return (ConsumersAddressDefaultApiModel) query.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			Log4jUtil.error(e);
			return null;
		}
	}

	/**
	 * @Title: getConsumersAddressDefault
	 * @Description: 获取用户在配送范围内的地址
	 * @param:    consumersid
	 * @return: ConsumersAddress
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  List<ConsumersAddressApiModel>  getConsumersAddressInDistance(String consumerUuid,String restaurantUuid) {
		String sql = "select t.consumer_id as consumerId,t.id as addressId,t.street,t.floor, "+
				"t.city,t.province,t.isdefault as isDefault "+
				"from (select restaurant_lng as rlng,restaurant_lat as rlat,distance from dat_restaurants where uuid="+restaurantUuid+") v LEFT JOIN "+ 
				"(select * from tbl_consumers_address where consumer_uuid='"+consumerUuid+"') t ON "+
				"ACOS(SIN((t.lat * 3.1415) / 180 ) *SIN((v.rlat * 3.1415) / 180 ) +COS((t.lat "+
				"* 3.1415) / 180 ) * COS((v.rlat * 3.1415) / 180 ) *COS((t.lng "+
				"* 3.1415) / 180 - (v.rlng * 3.1415) / 180 ) ) * 6378.140<=v.distance";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(ConsumersAddressApiModel.class));
		query.addScalar("consumerId", new org.hibernate.type.LongType());
		query.addScalar("addressId", new org.hibernate.type.LongType());
		query.addScalar("street", new org.hibernate.type.StringType());
		query.addScalar("isDefault", new org.hibernate.type.IntegerType());
		query.addScalar("floor", new org.hibernate.type.StringType());
		query.addScalar("city", new org.hibernate.type.StringType());
		query.addScalar("province", new org.hibernate.type.StringType());
		try {
			return query.list();
		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * @Title: ConsumersAddress
	 * @Description: 通id查找用户 默认地址
	 * @param:    id
	 * @return: List<ConsumersAddress>
	 */
	@Override
	public ConsumersAddress getConsumersAddressDefaultByUuid(String consumerUuid) {
		String hql = "from ConsumersAddress ca where ca.consumers.uuid=:consumerUuid and ca.isdefault=1 order by ca.isdefault asc";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("consumerUuid", consumerUuid);
		return this.get(hql, map);
	}



}
