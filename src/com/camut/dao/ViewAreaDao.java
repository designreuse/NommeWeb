package com.camut.dao;

import java.util.List;

import com.camut.model.ViewArea;

public interface ViewAreaDao {
	
	/**
	 * @Title: getViewAreaList
	 * @Description: 获取所有区域税率视图数据
	 * @param: @return
	 * @return List<ViewArea>  
	 */
	
	public List<ViewArea> getViewAreaList();

}
