package com.camut.dao;

import java.util.List;

import com.camut.model.Evaluate;
import com.camut.model.api.EvaluateApiModel;

/**
 * @dao EvaluateDao.java
 * @author zhangyunfei
 * @createtime 6 05, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface EvaluateDao {

	/**
	 * @Title: addEvaluate
	 * @Description: 发布评论
	 * @param:  
	 * @return: -1表示评论失败 ，1表示评论成功
	 */
	public int addEvaluate(EvaluateApiModel evaluateApiModel);
	
	/**
	 * @Title: getEvaluateByRestaurantId
	 * @Description: 店铺评论
	 * @param:  restaurantId
	 * @return: List<Evaluate>
	 */
	public List<Evaluate> getEvaluateByRestaurantId(long restaurantId);
	
	/**
	 * @Title: getEvaluate
	 * @Description: 根据商家id与用户id查找对象
	 * @param:  restaurantId  consumerId
	 * @return: Evaluate
	 */
	public Evaluate getEvaluate(long restaurantId, long consumerId);
	
	/**
	 * @Title: getEvaluateByOrderId
	 * @Description: 验证某个订单是否已经有评论了
	 * @param: @param orderId
	 * @param: @return
	 * @return Evaluate  
	 */
	public Evaluate getEvaluateByOrderId(int orderId);
	
	/**
	 * @Title: getEvaluatePagingByRestaurantId
	 * @Description: 获取分页的商家评论
	 * @param: @param restaurantId
	 * @param: @param offset
	 * @param: @param limit
	 * @param: @return
	 * @return List<EvaluateApiModel>  
	 */
	public List<EvaluateApiModel> getEvaluatePagingByRestaurantId(long restaurantId, int offset, int limit);
	
	/**
	 * @Title: getCount
	 * @Description:获取记录条数 
	 * @param: @return
	 * @return long  
	 */
	public long getCount();
	
}


