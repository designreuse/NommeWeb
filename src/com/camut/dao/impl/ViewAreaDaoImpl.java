package com.camut.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.camut.dao.ViewAreaDao;
import com.camut.model.ViewArea;

@Repository
public class ViewAreaDaoImpl extends BaseDao<ViewArea> implements ViewAreaDao {

	/**
	 * @Title: getViewAreaList
	 * @Description: 获取所有区域税率视图数据
	 * @param: @return
	 * @return List<ViewArea>  
	 */
	@Override
	public List<ViewArea> getViewAreaList() {
		// TODO Auto-generated method stub
		String hql = "from ViewArea v order by v.areaName";
		return this.find(hql);
	}

}
