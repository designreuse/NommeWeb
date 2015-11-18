package com.camut.dao;

import java.util.List;
import com.camut.model.Classification;

public interface ClassificationDao {
	
	/**
	 * @Title: getAllClassification
	 * @Description: 获取所有的餐厅菜系分类
	 * @param: @return
	 * @return List<Classification>  
	 */
	public List<Classification> getAllClassification ();
	
	/**
	 * @Title: getClassificationById
	 * @Description: 通过Id获取餐厅菜系分类
	 * @param: long id
	 * @return Classification  
	 */
	public Classification getClassificationById(long id);
	
	/**
	 * @Title: classificationNameIsUsedInOtherId
	 * @Description: 判断菜系名是否已经被使用了; 
	 * @param: PageRestaurantClassification prc
	 * @return int  
	 */
	public int classificationNameIsUsed(String name, long id);
	
	/**
	 * @Title: addClassification
	 * @Description: 新增菜系名称
	 * @param: PageRestaurantClassification prc
	 * @return PageMessage  
	 */
	public long addClassification(Classification c);
	
	/**
	 * @Title: editClassification
	 * @Description: 修改餐厅菜系分类
	 * @param: Classification c
	 * @return int  
	 */
	public int editClassification(Classification c);
	
	/**
	 * @Title: deleteClassification
	 * @Description: 删除餐厅菜系分类
	 * @param: PageClassification pc
	 * @return PageMessage  
	 */
	public int deleteClassification(Classification c);
	
	/**
	 * @Title: updateClassification
	 * @Description: 修改商家分类
	 * @param: @param classification
	 * @param: @return
	 * @return int  
	 */
	public int updateClassification(Classification classification);
	
	
	
	
}
