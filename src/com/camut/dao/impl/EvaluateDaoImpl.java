package com.camut.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.camut.dao.EvaluateDao;
import com.camut.model.Consumers;
import com.camut.model.Evaluate;
import com.camut.model.Restaurants;
import com.camut.model.api.EvaluateApiModel;
import com.camut.pageModel.PagePastOrderInfo;

/**
 * @dao EvaluateDaoImpl.java
 * @author zhangyunfei
 * @createtime 6 05, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class EvaluateDaoImpl extends BaseDao<Evaluate> implements EvaluateDao {

	private long count = 0;
	
	/**
	 * @Title: getCount
	 * @Description:获取记录条数 
	 * @param: @return
	 * @return long  
	 */
	public long getCount() {
		return count;
	}


	/**
	 * @Title: addEvaluate
	 * @Description: 发布评论
	 * @param:  evaluateApiModel
	 * @return: -1表示评论失败 ，1表示评论成功
	 */
	@Override
	public int addEvaluate(EvaluateApiModel evaluateApiModel) {
		try {
			Evaluate evaluate = new Evaluate();
			Consumers consumers = new Consumers();
			consumers.setId(evaluateApiModel.getConsumerId());
			evaluate.setConsumers(consumers);
			Restaurants restaurants = new Restaurants();
			restaurants.setId(evaluateApiModel.getRestaurantId());
			evaluate.setRestaurants(restaurants);
			evaluate.setOrderHeaderId(evaluateApiModel.getOrderHeaderId());
			evaluate.setStatus(1);
			evaluate.setCreatetime(new Date());
			evaluate.setContent(evaluateApiModel.getContent());
			evaluate.setScore(Double.parseDouble(evaluateApiModel.getScore()));
			this.save(evaluate);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * @Title: getEvaluateByRestaurantId
	 * @Description: 店铺评论
	 * @param:  restaurantId
	 * @return: List<Evaluate>
	 */
	@Override
	public List<Evaluate> getEvaluateByRestaurantId(long restaurantId) {
		String hql = "from Evaluate f where f.restaurants.id=:restaurantId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("restaurantId", restaurantId);
		List<Evaluate> eList = this.find(hql, map);
		return eList;
	}

	/**
	 * @Title: getEvaluate
	 * @Description: 根据商家id与用户id查找对象
	 * @param:  restaurantId  consumerId
	 * @return: Evaluate
	 */
	@Override
	public Evaluate getEvaluate(long restaurantId, long consumerId) {
		String hql = "from Evaluate f where f.restaurants.id=:restaurantId and f.consumers.id=:consumerId ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("restaurantId", restaurantId);
		map.put("consumerId", consumerId);
		return this.get(hql, map);
	}
	
	/**
	 * @Title: getEvaluateByOrderId
	 * @Description: 验证某个订单是否已经有评论了
	 * @param: @param orderId
	 * @param: @return
	 * @return Evaluate  
	 */
	public Evaluate getEvaluateByOrderId(int orderId){
		String hql = "from Evaluate f where f.orderHeaderId=:orderId ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		return this.get(hql, map);
	}
	
	/**
	 * @Title: getEvaluatePagingByRestaurantId
	 * @Description: 获取分页的商家评论
	 * @param: @param restaurantId
	 * @param: @param offset
	 * @param: @param limit
	 * @param: @return
	 * @return List<EvaluateApiModel>  
	 */
	@SuppressWarnings("unchecked")
	public List<EvaluateApiModel> getEvaluatePagingByRestaurantId(long restaurantId, int offset, int limit){
		String hql = "select count(*) from Evaluate f where f.restaurants.id=:restaurantId and f.status=1";
		//select count(*) from tbl_evaluate f where f.restaurants_id=16 and f.status=1
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("restaurantId", restaurantId);
		count = this.count(hql, map);
	
		
		
		String sql = "select a.content as content, a.score as score, a.createtime as createtime, " +
				"b.firstname as firstName, b.lastname as lastName "+
				"from tbl_consumers b right JOIN tbl_evaluate  a "+
				"ON a.consumers_id = b.id where a.restaurants_id =:restaurantId "+ 
				"and a.`status`=1 ORDER BY a.createtime desc limit :beginIndex, :rows";
		
		//int page = pf.getOffset();//当前页码
		//int rows = pf.getLimit();//每页数量
		int beginIndex = (offset-1)*limit;
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter("restaurantId", restaurantId);
		query.setParameter("beginIndex", beginIndex);
		query.setParameter("rows", limit);
		query.setResultTransformer(Transformers.aliasToBean(EvaluateApiModel.class));
		query.addScalar("content",new org.hibernate.type.StringType());
		query.addScalar("score",new org.hibernate.type.StringType());
		query.addScalar("createtime",new org.hibernate.type.DateType());
		query.addScalar("firstName",new org.hibernate.type.StringType());
		query.addScalar("lastName",new org.hibernate.type.StringType());
		
		return query.list();
	}
	
	
	
}
