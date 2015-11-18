package com.camut.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.camut.dao.ViewConsumerClassificationDao;
import com.camut.model.ViewConsumerClassification;

/**
 * @ClassName ViewConsumerClassificationDaoImpl.java
 * @author wangpin
 * @createtime Jul 22, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class ViewConsumerClassificationDaoImpl extends BaseDao<ViewConsumerClassification> implements ViewConsumerClassificationDao {

	/**
	 * @Title: getClassificationNames
	 * @Description:获取会员点过餐的餐厅集合ios
	 * @param:    int
	 * @return: List<String>
	 */
	@Override
	public List<ViewConsumerClassification> getClassificationNames(int consumerId) {
		String hql = "from ViewConsumerClassification v where v.consumerId=:consumerId";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("consumerId", consumerId);
		return this.find(hql, map);
	}
	
	/**
	 * @Title: getClassificationNamesAndroid
	 * @Description:获取会员点过餐的餐厅集合android
	 * @param:    int
	 * @return: List<String>
	 */
	@Override
	public List<ViewConsumerClassification> getClassificationNamesAndroid(int consumerId) {
		String hql = "from ViewConsumerClassification v where v.consumerId=:consumerId";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("consumerId", consumerId);
		return this.find(hql, map);
	}

}
