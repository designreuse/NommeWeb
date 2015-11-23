package com.camut.service;

import java.util.List;
import com.camut.model.api.EvaluateApiModel;

/**
 * @dao EvaluateService.java
 * @author zhangyunfei
 * @createtime 6 05, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface EvaluateService {

	/**
	 * @Title: addEvaluate
	 * @Description: 发布评论
	 * @param:  evaluateApiModel
	 * @return: -1表示评论失败 ，1表示评论成功
	 */
	public int addEvaluate(EvaluateApiModel evaluateApiModel);
	
	/**
	 * @Title: getEvaluateByRestaurantId
	 * @Description: 店铺评论
	 * @param:  restaurantId
	 * @return: List<EvaluateApiModel>
	 */
	public List<EvaluateApiModel> getEvaluateByRestaurantId(String restaurantUuid);
	
	/**
	 * @Title: existEvaluateByOrderId
	 * @Description: 验证某个订单是否已经有评论了
	 * @param: @return
	 * @return int  
	 */
	public int existEvaluateByOrderId(int orderId);
	
	/**
	 * @Title: getEvaluatePagingByRestaurantId
	 * @Description: 获取分页的商家评论
	 * @param: @param restaurantId
	 * @param: @param offset
	 * @param: @param limit
	 * @param: @return
	 * @return List<EvaluateApiModel>  
	 */
	public List<EvaluateApiModel> getEvaluatePagingByRestaurantId(String restaurantUuid, int offset, int limit);

	/**
	 * @Title: getCount
	 * @Description:获取记录条数 
	 * @param: @return
	 * @return long  
	 */
	public long getCount();


}
