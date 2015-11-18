package com.camut.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.EvaluateDao;
import com.camut.model.Evaluate;
import com.camut.model.api.EvaluateApiModel;
import com.camut.service.EvaluateService;

/**
 * @dao EvaluateServiceImpl.java
 * @author zhangyunfei
 * @createtime 6 05, 2015
 * @author
 * @updateTime
 * @memo
 */
@Service
public class EvaluateServiceImpl implements EvaluateService {

	@Autowired
	private EvaluateDao evaluateDao;// 自动注入evaluateDao

	/**
	 * @Title: addEvaluate
	 * @Description: 发布评论
	 * @param:  evaluateApiModel
	 * @return: -1表示评论失败 ，1表示评论成功
	 */
	@Override
	public int addEvaluate(EvaluateApiModel evaluateApiModel) {
		if (evaluateApiModel != null) {
			return evaluateDao.addEvaluate(evaluateApiModel);
		}
		return -1;
	}

	/**
	 * @Title: getEvaluateByRestaurantId
	 * @Description: 店铺评论
	 * @param:  restaurantId
	 * @return: List<EvaluateApiModel>
	 */
	@Override
	public List<EvaluateApiModel> getEvaluateByRestaurantId(long restaurantId) {
		List<Evaluate> eList = evaluateDao.getEvaluateByRestaurantId(restaurantId);
		List<EvaluateApiModel> eamList = new ArrayList<EvaluateApiModel>();
		if(eList != null){
			for (Evaluate evaluate : eList) {
				EvaluateApiModel eam = new EvaluateApiModel();
				eam.setContent(evaluate.getContent());
				eam.setScore(String.valueOf(evaluate.getScore()));
				eam.setCreatetime(evaluate.getCreatetime());
				eam.setConsumerId(evaluate.getConsumers().getId());
				eam.setRestaurantId(evaluate.getId());
				eam.setFirstName(evaluate.getConsumers().getFirstName());
				eam.setLastName(evaluate.getConsumers().getLastName());
				eam.getShowName();
				eamList.add(eam);
			}
		}
		return eamList;
	}
	
	/**
	 * @Title: existEvaluateByOrderId
	 * @Description: 验证某个订单是否已经有评论了
	 * @param: @return
	 * @return int  1:存在 -1：不存在 0：传入的orderId参数有问题
	 */
	public int existEvaluateByOrderId(int orderId){
		if(orderId>0){
			Evaluate evaluate = evaluateDao.getEvaluateByOrderId(orderId);
			if(evaluate != null){
				return 1;
			}else{
				return -1;
			}
		}else{
			return 0;
		}
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
	@Override
	public List<EvaluateApiModel> getEvaluatePagingByRestaurantId(long restaurantId, int offset, int limit) {
		return evaluateDao.getEvaluatePagingByRestaurantId(restaurantId, offset, limit);
	
	}
	
	/**
	 * @Title: getCount
	 * @Description:获取记录条数 
	 * @param: @return
	 * @return long  
	 */
	public long getCount(){
		return evaluateDao.getCount();
	}
	
	
}
