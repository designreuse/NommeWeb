package com.camut.service;

import java.util.List;

import com.camut.model.Classification;
import com.camut.pageModel.PageClassification;
import com.camut.pageModel.PageMessage;

public interface ClassificationService {
	
	/**
	 * @Title: getAllClassification
	 * @Description: 获取所有的餐厅菜系分类
	 * @param: @return
	 * @return List<PageRestaurantClassification>  
	 */
	public List<PageClassification> getAllClassification ();
	
	/**
	 * @Title: classificationNameIsUsedInOtherId
	 * @Description: 判断菜系名是否已经被使用了; 
	 * @param: PageRestaurantClassification pc
	 * @return int  
	 */
	public PageMessage classificationNameIsUsed(PageClassification pc);
	
	/**
	 * @Title: addClassification
	 * @Description: 新增菜系名称
	 * @param: PageRestaurantClassification pc
	 * @return PageMessage  
	 */
	public PageMessage addClassification(PageClassification pc);
	
	/**
	 * @Title: editClassification
	 * @Description: 修改餐厅菜系分类
	 * @param: PageClassification pc
	 * @return PageMessage  
	 */
	public PageMessage editClassification(PageClassification pc);
	
	/**
	 * @Title: deleteClassification
	 * @Description: 删除餐厅菜系分类
	 * @param: PageClassification pc
	 * @return PageMessage  
	 */
	public PageMessage deleteClassification(PageClassification pc);
	
	/**
	 * @Title: getClassificationById
	 * @Description: 通过id获取菜系分类 
	 * @param: @param classificationId
	 * @param: @return
	 * @return Classification  
	 */
	public Classification getClassificationById(long classificationId);

	/**
	 * @Title: updateClassification
	 * @Description: 修改商家分类
	 * @param: @param classification
	 * @param: @return
	 * @return int  
	 */
	public int updateClassification(Classification classification);
	
	
	
}
