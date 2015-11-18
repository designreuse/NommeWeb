package com.camut.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.camut.dao.ClassificationDao;
import com.camut.model.Classification;

@Repository
public class ClassificationDaoImpl extends BaseDao<Classification> implements ClassificationDao {

	/**
	 * @Title: getAllClassification
	 * @Description: 获取所有的餐厅菜系分类
	 * @param: @return
	 * @return List<Classification>  
	 */
	@Override
	public List<Classification> getAllClassification() {
		String hql = "from Classification c";
		List<Classification> classificationList = this.find(hql);
		return classificationList;
	}
	
	/**
	 * @Title: getClassificationById
	 * @Description: 通过Id获取餐厅菜系分类
	 * @param: long id
	 * @return Classification  
	 */
	public Classification getClassificationById(long id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		String hql = "from Classification c where c.id=:id";
		Classification c= this.get(hql, map);
		return c;
	}

	/**
	 * @Title: classificationNameIsUsedInOtherId
	 * @Description: 判断菜系名是否已经被使用了; 
	 * @param: PageRestaurantClassification prc
	 * @return int  
	 */
	@Override
	public int classificationNameIsUsed(String name, long id) {
		String hql = "from Classification cf where cf.classificationName=:name and cf.id!=:id ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("id", id);
		List<Classification> list = this.find(hql, map);
		if(list.size()>0){
			return 1;
		}else{
			return 0;
		}
	}
	
	/**
	 * @Title: addClassification
	 * @Description: 新增菜系名称
	 * @param: PageRestaurantClassification prc
	 * @return PageMessage  
	 */
	public long addClassification(Classification c){
		
		try{
			return (Long)this.save(c);
		}catch(Exception e){
			return -1;
		}
		
	}
	
	/**
	 * @Title: editClassification
	 * @Description: 修改餐厅菜系分类
	 * @param: Classification c
	 * @return int  
	 */
	public int editClassification(Classification c){
		if(c!=null){
			try{
				this.update(c);
				return 1;
			}catch(Exception e){
				return-1;
			}
		}else{
			return -1;
		}
	}
	
	/**
	 * @Title: deleteClassification
	 * @Description: 删除餐厅菜系分类
	 * @param: PageClassification pc
	 * @return PageMessage  
	 */
	public int deleteClassification(Classification c){
		if(c!=null){
			try{
				this.delete(c);
				return 1;
			}catch(Exception e){
				return-1;
			}
		}else{
			return -1;
		}
	}
	
	/**
	 * @Title: updateClassification
	 * @Description: 修改商家分类
	 * @param: @param classification
	 * @param: @return
	 * @return int  
	 */
	public int updateClassification(Classification classification){
		try {
			this.update(classification);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}
	
	
	

}
