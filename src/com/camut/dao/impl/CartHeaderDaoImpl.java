package com.camut.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;
import com.camut.dao.CartHeaderDao;
import com.camut.model.CartHeader;
import com.camut.model.api.CartApiModel;
import com.camut.utils.Log4jUtil;

/**
 * @ClassName CartHeaderDaoImpl.java
 * @author wangpin
 * @createtime Jul 22, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class CartHeaderDaoImpl extends BaseDao<CartHeader> implements CartHeaderDao {

	/**
	 * @Title: getCartHeaderByMobileToken
	 * @Description:根据设备号查询购物车头
	 * @param:    String
	 * @return: CartHeader
	 */
	@Override
	public CartHeader getCartHeaderByMobileToken(String mobileToken,String consumerUuid) {
		String hql = "from CartHeader c where c.consumerUuid=:consumerUuid";// and c.consumerId is null";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("consumerUuid", consumerUuid);
		return this.get(hql, map);
	}
	
	/**
	 * @Title: getCartHeaderByMobileToken
	 * @Description:根据设备号查询购物车头
	 * @param:    String
	 * @return: CartHeader
	 */
	@Override
	public CartHeader getCartHeaderByMobileToken(String mobileToken) {
		String hql = "from CartHeader c where c.mobileToken=:mobileToken";// and c.consumerId is null";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobileToken", mobileToken);
		return this.get(hql, map);
	}

	/**
	 * @Title: deleteCartHeaderByMobileToken
	 * @Description:删除购物车信息
	 * @param:    String
	 * @return: CartHeader
	 */
	@Override
	public int deleteCartHeaderByMobileToken(String mobileToken) {
		String hql = "delete from CartHeader c where c.mobileToken=:mobileToken";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobileToken", mobileToken);
		try {
			this.executeHql(hql,map);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * @Title: deleteCartHeader
	 * @Description:删除购物车信息
	 * @param:    cartHeader
	 * @return: int
	 */
	public int deleteCartHeader(CartHeader cartHeader){
		if(cartHeader!=null){
			try {
				this.delete(cartHeader);
				return 1;
			} catch (Exception e) {
				return -1;
			}
			
		}else{
			return -1;
		}
	}

	/**
	 * @Title: addCartHeader
	 * @Description: 增加购物车头
	 * @param:    CartHeader
	 * @return:  -1失败
	 */
	@Override
	public int addCartHeader(CartHeader cartHeader) {
		try {
			return Integer.parseInt(this.save(cartHeader).toString());
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * @Title: addCartHeader
	 * @Description: 修改购物车头
	 * @param:    CartHeader
	 * @return: int 1成功 -1失败
	 */
	@Override
	public int updateCartHeader(CartHeader cartHeader) {
		try {
			this.update(cartHeader);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * 删除菜品信息
	 * @param cartId
	 * @param dishId
	 * @return
	 */
	@Override
	public int deleteCartDish(long cartId, int dishId) {
		String hql="delete from CartItem c where c.cartHeader.id=:cartId and c.dishId=:dishId";
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("cartId", cartId);
		map.put("dishId", dishId);
		try {
			this.executeHql(hql,map);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
		
	}

	/**
	 * @Title: getCartHeaderById；
	 * @Description: 根据id获取对象
	 * @param:    cartHeaderId
	 * @return: CartHeader
	 */
	@Override
	public CartHeader getCartHeaderById(long cartHeaderId) {
		String hql = "from CartHeader c where c.id=:cartHeaderId";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cartHeaderId", cartHeaderId);
		return this.get(hql, map);
	}

	@Override
	public CartApiModel getCartInfoForSql(String mobileToken,String consumerUuid) {
		String sql = "select a.mobile_token as mobileToken,sum(unitprice)  as total " +
				",a.restaurant_uuid as restaurantUuid from dat_cart_header a JOIN dat_cart_item b ON a.id=b.cart_id " +
				"where a.consumer_uuid=:consumerUuid " +
				"GROUP BY a.id,a.mobile_token,a.restaurant_uuid";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);		
		query.setParameter("consumerUuid", consumerUuid);
		query.setResultTransformer(Transformers.aliasToBean(CartApiModel.class));
		query.addScalar("mobileToken",new org.hibernate.type.StringType());		
		query.addScalar("total",new org.hibernate.type.DoubleType());		
		query.addScalar("restaurantUuid",new org.hibernate.type.StringType());
		List<CartApiModel> list=query.list();
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public int deleteFreeCartItem(String consumerUuid) {
		String sql="delete from dat_cart_item  where cart_id=(select id from dat_cart_header where consumer_uuid="+consumerUuid+") and unitprice =0";
		try {
			return this.executeSql(sql);
		} catch (Exception e) {
			// TODO: handle exception
			Log4jUtil.error(e);
			return -1;
		}
		
	}

	/**
	 * 根据用户id查找对象
	 * @param mobileToken  consumerId
	 * @return
	 */
	@Override
	public CartHeader getCartHeaderByConsumerUuid(String consumerUuid) {
		String hql = "from CartHeader c where c.consumerUuid=:consumerUuid";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("consumerUuid", consumerUuid);
		return this.get(hql, map);
	}	

	/**
	 * 根据用户id查找对象
	 * @param mobileToken  consumerId
	 * @return
	 */
	@Override
	public CartHeader getWebCartHeaderByConsumerUuid(String consumerUuid) {
		
		String hql = "from CartHeader c where c.consumerUuid=:consumerUuid"; //and c.mobileToken is null";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("consumerUuid", consumerUuid);
		return this.get(hql, map);
	}	

	
	/**
	 * 将token保存的已有的用户
	 * @param mobileToken  consumerId
	 * @return
	 */
	@Override
	public void saveTokenToConsumerId(CartHeader cartHeader) {
		try {
			//Map<String, Object> map=new HashMap<String, Object>();
			//map.put("mobileToken", cartHeader.getMobileToken());
			//map.put("id", cartHeader.getId());
			this.executeHql("update CartHeader item set item.mobileToken='"+cartHeader.getMobileToken()+"' where item.id="+cartHeader.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




}
