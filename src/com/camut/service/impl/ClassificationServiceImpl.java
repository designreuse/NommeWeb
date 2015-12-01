package com.camut.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.ClassificationDao;
import com.camut.framework.constant.GlobalConstant;
import com.camut.model.Classification;
import com.camut.pageModel.PageClassification;
import com.camut.pageModel.PageMessage;
import com.camut.service.ClassificationService;
import com.camut.utils.StringUtil;

@Service
public class ClassificationServiceImpl implements ClassificationService {

	@Autowired ClassificationDao classificationDao;
	
	/**
	 * @Title: getAllClassification
	 * @Description: 获取所有的餐厅菜系分类
	 * @param: @return
	 * @return List<PageRestaurantClassification>  
	 */
	@Override
	//@Cacheable(value = "cacheTest",key="'ClassificationList'")
	public List<PageClassification> getAllClassification() {
		List<Classification> classifications = classificationDao.getAllClassification();
		List<PageClassification> list = new ArrayList<PageClassification>();
		for(Classification classification:classifications){
			PageClassification pageClassification = new PageClassification();
			if(classification!=null){
				BeanUtils.copyProperties(classification, pageClassification);
			}
			if(StringUtil.isNotEmpty(classification.getAndroidImageUrl())){
				pageClassification.setAndroidImageUrl(GlobalConstant.DOMAIN_NAME + classification.getAndroidImageUrl());
			}
			if(StringUtil.isNotEmpty(classification.getAndroidHoverImageUrl())){
				pageClassification.setAndroidHoverImageUrl(GlobalConstant.DOMAIN_NAME + classification.getAndroidHoverImageUrl());
			}
			if(StringUtil.isNotEmpty(classification.getIosImageUrl())){
				pageClassification.setIosImageUrl(GlobalConstant.DOMAIN_NAME + classification.getIosImageUrl());
			}
			if(StringUtil.isNotEmpty(classification.getIosHoverImageUrl())){
				pageClassification.setIosHoverImageUrl(GlobalConstant.DOMAIN_NAME + classification.getIosHoverImageUrl());
			}
			if(StringUtil.isNotEmpty(classification.getWebImageUrl())){
				pageClassification.setWebImageUrl(GlobalConstant.DOMAIN_NAME + classification.getWebImageUrl());
			}
			
			list.add(pageClassification);
		}
		return list;
	}

	/**
	 * @Title: classificationNameIsUsedInOtherId
	 * @Description: 判断菜系名是否已经被使用了; 
	 * @param: PageRestaurantClassification prc
	 * @return int  
	 */
	@Override
	public PageMessage classificationNameIsUsed(PageClassification pc) {
		// TODO Auto-generated method stub
		PageMessage pm = new PageMessage();
		String name = pc.getClassificationName();
		long id = (long)pc.getId();
		int temp = classificationDao.classificationNameIsUsed(name,id);
		if(temp>0){
			pm.setSuccess(false);
		}else{
			pm.setSuccess(true);
		}
		return pm;
	}
	

	/**
	 * @Title: addClassification
	 * @Description: 新增菜系名称
	 * @param: PageRestaurantClassification prc
	 * @return PageMessage  
	 */
	public PageMessage addClassification(PageClassification pc){
		PageMessage pm = new PageMessage();
		Classification c = new Classification();
		c.setClassificationName(pc.getClassificationName().trim());
		long temp = classificationDao.addClassification(c);
		if(temp>0){
			pm.setSuccess(true);
		}else{
			pm.setSuccess(false);
		}
		return pm;
	}
	
	
	/**
	 * @Title: editClassification
	 * @Description: 修改餐厅菜系分类
	 * @param: PageClassification pc
	 * @return PageMessage  
	 */
	public PageMessage editClassification(PageClassification pc){
		PageMessage pm = new PageMessage();
		Classification c = classificationDao.getClassificationById(pc.getId());
		c.setClassificationName(pc.getClassificationName());
		int temp = classificationDao.editClassification(c);
		if(temp>0){
			pm.setSuccess(true);
		}else{
			pm.setSuccess(false);
		}
		return pm;
	}
	
	
	/**
	 * @Title: deleteClassification
	 * @Description: 删除餐厅菜系分类
	 * @param: PageClassification pc
	 * @return PageMessage  
	 */
	public PageMessage deleteClassification(PageClassification pc){
		PageMessage pm = new PageMessage();
		Classification c = classificationDao.getClassificationById(pc.getId());
		int temp = classificationDao.deleteClassification(c);
		if(temp>0){
			pm.setSuccess(true);
		}else{
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: getClassificationById
	 * @Description: 通过id获取菜系分类 
	 * @param: @param classificationId
	 * @param: @return
	 * @return Classification  
	 */
	public Classification getClassificationById(long classificationId){
		return classificationDao.getClassificationById(classificationId);
	}
	
	/**
	 * @Title: updateClassification
	 * @Description: 修改商家分类
	 * @param: @param classification
	 * @param: @return
	 * @return int  
	 */
	public int updateClassification(Classification classification){
		return classificationDao.updateClassification(classification);
	}
	

}
