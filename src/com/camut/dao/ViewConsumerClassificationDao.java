/**
 * 
 */
package com.camut.dao;

import java.util.List;

import com.camut.model.ViewConsumerClassification;

/**
 * @ClassName ViewConsumerClassificationDao.java
 * @author wangpin
 * @createtime Jul 22, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface ViewConsumerClassificationDao {

	/**
	 * @Title: getClassificationNames
	 * @Description:获取会员点过餐的餐厅的集合
	 * @param:    int
	 * @return: List<String>
	 */
	public List<ViewConsumerClassification> getClassificationNames(int consumerId);
	
	/**
	 * @Title: getClassificationNames
	 * @Description:获取会员点过餐的餐厅集合android
	 * @param:    int
	 * @return: List<String>
	 */
	public List<ViewConsumerClassification> getClassificationNamesAndroid(int consumerId);
}
