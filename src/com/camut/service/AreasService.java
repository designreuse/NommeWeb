package com.camut.service;

import java.util.List;

import com.camut.model.Areas;
import com.camut.model.ViewArea;
import com.camut.pageModel.PageAreas;
import com.camut.pageModel.PageMessage;
import com.camut.pageModel.PageModel;

public interface AreasService {

	/**
	 * @Title: updateAreas
	 * @Description: 修改区域
	 * @param:  Areas
	 * @return: areas 
	 */
	public void updateAreas(Areas areas);
	
	/**
	 * @Title: deleteAreas
	 * @Description: 根据区域名删除区域
	 * @param:  Areas
	 * @return: -1表示删除失败 
	 */
	public int deleteAreas(String areaName);
	
	/**
	 * @Description: 获取所有父类区域
	 * @param @return   
	 * @return PageModel  
	 * @author wj
	 * @date 2015-6-8
	 */
	public PageModel getAllParentAreas();
	
	/**
	 * @Description: 加载区域数据到表格
	 * @param @param pageFilter
	 * @param @return   
	 * @return PageModel  
	 * @author wj
	 * @date 2015-6-8
	 */
	public List<PageAreas> getAllAreas();
	
	/**
	 * @Description: 新增一个区域和税率
	 * @param @param pageAreas
	 * @param @return   
	 * @return PageModel  
	 * @author wj
	 * @date 2015-6-9
	 */
	public PageMessage addAreas(PageAreas pageAreas);
	
	/**
	 * @Description: 增加区域税率时校验名称在同一个父类区域中是否被使用了
	 * @param pageAreas
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-9
	 */
	public PageMessage areaNameExist(PageAreas pageAreas);
	
	/**
	 * @Title: getSonAreas
	 * @Description: 找出所有的子区域
	 * @param:    
	 * @return: List<Areas>
	 */
	public List<PageAreas> getSonAreas();
	
	/**
	 * @Description: 
	 * @param @param pageAreas   
	 * @return void  
	 * @author wj
	 * @date 2015-6-10
	 */
	public PageMessage modifyAreas(PageAreas pageAreas);
	
	/**
	 * @Description: 删除区域税率
	 * @param @param pageAreas
	 * @param @return   
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-10
	 */
	public PageMessage deleteArea(PageAreas pageAreas);
	
	/**
	 * @Title: getAreasById
	 * @Description: 通过主键找区域
	 * @param:    
	 * @return: Areas
	 */
	public Areas getAreasById(long id);
	
	/**
	 * @Title: getViewAreaList
	 * @Description: 获取所有区域税率视图数据
	 * @param: @return
	 * @return List<ViewArea>  
	 */
	public List<ViewArea> getViewAreaList();
	
}
