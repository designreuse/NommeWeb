package com.camut.dao;

import java.util.List;
import com.camut.model.Areas;

/**
 * @dao AreasDao.java
 * @author zhangyunfei 
 * @createtime May 28, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface AreasDao {
	
	/**
	 * @Title: getByAreaName
	 * @Description: 根据区域名获取对象，用来判断区域名是否存在
	 * @param:  Areas
	 * @return: areas 
	 */
	public Areas getByAreaName(String areaName);
	
	/**
	 * @Title: addAreas
	 * @Description: 添加区域
	 * @param:  Areas
	 * @return: int -1表示添加失败 ，0区域名已存在，1添加成功  
	 */
	public int addAreas(Areas areas);

	/**
	 * @Title: updateAreas
	 * @Description: 修改区域
	 * @param:  Areas
	 * @return: areas 
	 */
	public int updateAreas(Areas areas);
	
	/**
	 * @Title: deleteAreas
	 * @Description: 根据区域名删除区域
	 * @param:  Areas
	 * @return: -1表示删除失败 
	 */
	public int deleteAreas(String areaName);
	
	/**
	 * @Title: getAllParentAreas
	 * @Description: 获取所有父类区域，每个父区域都包含各自所有的子区域，所以可以用来获取所有区域
	 * @param:  Areas
	 * @return: -1表示删除失败 
	 * @author wj
	 * @date 2015-6-8
	 */
	public List<Areas> getAllParentAreas();
	
	/**
	 * @Description: 通过父类id获取旗下的所有子类
	 * @param pid
	 * @return List<Areas>  
	 * @author wj
	 * @date 2015-6-8
	 */
	public List<Areas> getByParentId(long pid);
	
	/**
	 * @Description: 通过id取得区域
	 * @param @param id
	 * @param @return   
	 * @return Areas  
	 * @author wj
	 * @date 2015-6-10
	 */
	public Areas getById(long id);
	
	/**
	 * @Description: 通过id删除区域
	 * @param @param id
	 * @param @return   
	 * @return int  
	 * @author wj
	 * @date 2015-6-10
	 */
	public int deleteByAreas(Areas areas);
	
}
