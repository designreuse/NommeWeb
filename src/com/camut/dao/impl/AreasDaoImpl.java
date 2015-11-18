package com.camut.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.camut.dao.AreasDao;
import com.camut.model.Areas;

/**
 * @daoimpl AreasDaoImpl.java
 * @author zyf
 * @createtime May 28, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class AreasDaoImpl extends BaseDao<Areas> implements AreasDao {

	/**
	 * @Title: getByAreaName
	 * @Description: 根据区域名获取对象，用来判断区域名是否存在; get Areas by name
	 * @param:  Areas
	 * @return: areas 
	 */
	@Override
	public Areas getByAreaName(String areaName) {
		String hql = "from Areas a where a.areaName=:areaName";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaName", areaName);
		return this.get(hql, map);
	}
	
	/**
	 * @Title: addAreas
	 * @Description: 添加区域; add an area
	 * @param:  Areas
	 * @return: int -1表示添加失败 ，0区域名已存在，1添加成功; -1:failed, 0:exist, 1: succeed
	 */
	@Override
	public int addAreas(Areas areas) {
		if (areas != null) {
			try {
				this.save(areas);
				return 1;
			} catch (Exception e) {
				return -1;
			}
		}
		return -1;
	}

	/**
	 * @Title: updateAreas
	 * @Description: 修改区域; update area name
	 * @param:  Areas
	 * @return: areas 
	 */
	@Override
	public int updateAreas(Areas areas) {
		try {
			this.update(areas);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * @Title: deleteAreas
	 * @Description: 根据区域名删除区域 delete an area by name
	 * @param:  Areas
	 * @return: -1表示删除失败 ; -1:failed
	 */
	@Override
	public int deleteAreas(String areaName) {
		if (areaName != null && areaName.length() > 0) {
			String hql = "from Areas a where a.areaName=:areaName";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("areaName", areaName);
			Areas a = this.get(hql, map);
			try {
				this.delete(a);
				return 1;
			} catch (Exception e) {
				return -1;
			}
		}
		return -1;
	}
	
	/**
	 * @Description: 获取所有父类区域，加载区域数据到表格
	 * @param @return   
	 * @return PageModel  
	 * @author wj
	 * @date 2015-6-8
	 */
	@Override
	public List<Areas> getAllParentAreas() {
		String hql = "from Areas a where a.parent.id=null";
		List <Areas> list = this.find(hql);
		return list;
	}
	
	/**
	 * @Description: 通过父类id获取旗下的所有子类
	 * @param pid
	 * @return List<Areas>  
	 * @author wj
	 * @date 2015-6-8
	 */
	@Override
	public List<Areas> getByParentId (long pid) {
		String hql = "from Areas a where a.parent.id=:pid";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pid", pid);
		List<Areas> list = this.find(hql,map);
		return list;
	}
	
	/**
	 * @Description: 通过id取得区域
	 * @param @param id
	 * @param @return   
	 * @return Areas  
	 * @author wj
	 * @date 2015-6-10
	 */
	public Areas getById(long id){
		String hql = "from Areas a where a.id=:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		Areas areas = this.get(hql,map);
		return areas;
	}
	
	/**
	 * @Description: 通过id删除区域
	 * @param @param id
	 * @param @return   
	 * @return int  
	 * @author wj
	 * @date 2015-6-10
	 */
	public int deleteByAreas(Areas areas){
		try{
			this.delete(areas);
			return 1;
		}catch(Exception e){
			return -1;
		}
	}

}
