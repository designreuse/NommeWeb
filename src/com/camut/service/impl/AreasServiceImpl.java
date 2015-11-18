package com.camut.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.AreasDao;
import com.camut.dao.ViewAreaDao;
import com.camut.framework.constant.MessageConstant;
import com.camut.model.Areas;
import com.camut.model.ViewArea;
import com.camut.pageModel.PageAreas;
import com.camut.pageModel.PageMessage;
import com.camut.pageModel.PageModel;
import com.camut.service.AreasService;
import com.camut.utils.StringUtil;

/**
 * @ServiceImpl AreasServiceImpl.java
 * @author zhangyunfei
 * @createtime May 28, 2015 
 * @author
 * @updateTime
 * @memo
 */
@Service
public class AreasServiceImpl  implements AreasService {

	@Autowired
	private AreasDao areasDao;//自动注入areasDao

	@Autowired
	private ViewAreaDao viewAreaDao; 
	/**
	 * @Title: updateAreas
	 * @Description: 修改区域; update area info
	 * @param:  Areas
	 * @return: areas 
	 */
	@Override
	public void updateAreas(Areas areas) {
		areasDao.updateAreas(areas);
	}

	/**
	 * @Title: deleteAreas
	 * @Description: 根据区域名删除区域
	 * @param:  Areas
	 * @return: -1表示删除失败 ; -1: failed
	 */
	@Override
	public int deleteAreas(String areaName) {
		return areasDao.deleteAreas(areaName);
	}
	
	/**
	 * @Description: 获取所有父类区域
	 * @param @return   
	 * @return PageModel  
	 * @author wj
	 * @date 2015-6-8
	 */
	@Override
	public PageModel getAllParentAreas() {
		PageModel pm = new PageModel();
		List<PageAreas> pageAreasList = new ArrayList<PageAreas>();
		List <Areas> li = areasDao.getAllParentAreas();
		for (Areas areas : li) {
			PageAreas pageAreas= new PageAreas();
			pageAreas.setAreaName(areas.getAreaName());
			pageAreas.setId(areas.getId()+"");
			pageAreas.setTax(areas.getTax()+"");
			pageAreasList.add(pageAreas);
		}
		pm.setRows(pageAreasList);
		return pm;
	}

	/**
	 * @Description: 加载区域数据到表格
	 * @param @param pageFilter
	 * @param @return   
	 * @return PageModel  
	 * @author wj
	 * @date 2015-6-8
	 */
	@Override
	public List<PageAreas> getAllAreas(){
		List<Areas> listP = areasDao.getAllParentAreas();
		List<PageAreas> pageList = new ArrayList<PageAreas>();
		if(listP.size()>0){
			for (Areas a : listP) {
				PageAreas pageArea = new PageAreas();
				pageArea.setAreaName(a.getAreaName());
				pageArea.setTree(a.getAreaName());
				pageArea.setId(a.getId()+"");
				pageArea.setPid(0+"");//获取pid,因为pid为空，所以设成0
				pageArea.setTax(a.getTax()+"");
				pageArea.setTaxMode(a.getTaxMode());//设置税额显示方式
				pageList.add(pageArea);//父区域添加到list中去
				Set<Areas> set = a.getAreasSet();//获得父区域中的所有子区域
				
				if(set.size()>0){//如果有子区域就遍历
					List<PageAreas> pageList2 = new ArrayList<PageAreas>();
					for (Areas a2 : set) {
						pageArea = new PageAreas();
						pageArea.setAreaName(a2.getAreaName());
						pageArea.setTree("&nbsp;┗━ " + a2.getAreaName());
						pageArea.setId(a2.getId()+"");
						pageArea.setTax(a2.getTax()+"");
						pageArea.setPid(a.getId()+"");
						pageArea.setTaxMode(a2.getTaxMode());//设置税额显示方式
						pageList2.add(pageArea);
					}
					Collections.sort(pageList2);
					pageList.addAll(pageList2);
				}
			}
		}
		return pageList;
	}
	
	/**
	 * @Description: 新增一个区域和税率
	 * @param @param pageAreas
	 * @param @return   
	 * @return PageModel  
	 * @author wj
	 * @date 2015-6-9
	 */
	public PageMessage addAreas(PageAreas pageAreas){
		Areas areas = new Areas();
		String strPid = pageAreas.getPid();
		long pid = 0;
		if(StringUtil.isNotEmpty(strPid)){//判断传入的pid是否有值，如果有值说明是新增的某个子区域，如果没值说明新增的父区域（设置：pid=0）;
			pid = Long.parseLong(pageAreas.getPid());
		}
		if(pid>0){//说明新增的是某个父区域下的子区域
			Areas parentAreas = new Areas();
			parentAreas.setId(pid);
			areas.setParent(parentAreas);
		}
		areas.setAreaName(pageAreas.getAreaName());
		areas.setTax(Double.parseDouble(pageAreas.getTax()));
		areas.setTaxMode(pageAreas.getTaxMode());
		int temp = areasDao.addAreas(areas);
		PageMessage pm = new PageMessage();
		if(temp>0){
			pm.setSuccess(true);
		}else{
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Description: 增加区域税率时校验名称在同一个父类区域中是否被使用了
	 * @param @param pageAreas
	 * @param @return   
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-9
	 */
	public PageMessage areaNameExist(PageAreas pageAreas){
		boolean flag = true;
		String strPid = pageAreas.getPid();
		String newName = pageAreas.getAreaName().toUpperCase();
		String newStrId = pageAreas.getId();
		if(StringUtil.isNotEmpty(strPid)){
			Long pid = Long.parseLong(strPid);
			if(pid==0){//说明是修改的父区域
				List<Areas> pList =  areasDao.getAllParentAreas();
				if(pList.size()>0){
					for (Areas areas : pList) {//遍历对比有没有相同的名称的父类区域
						String oldName = areas.getAreaName().trim().toUpperCase();//(areas.getAreaName()).trim().toUpperCase();
						long oldId = areas.getId();
						if(StringUtil.isNotEmpty(newStrId)){//说明id有值,shi修改的
							newStrId = newStrId.trim();
							long newId = Long.parseLong(newStrId);
							if(oldName.equals(newName) && oldId != newId){
								flag = false;//如果有相同；
								break;
							}
						}else{//id没值说明是新增的
							if(oldName.equals(newName)){//只要判断和其他父类区域名相同，相同时返回false
								flag = false;//如果有相同；
								break;
							}
						}
						
					}
				}
			}else{//如果pid有值且不为0 说明是要验证一个子区域的名称在改父区域下是否有重复
				List<Areas> areaslist = areasDao.getByParentId(Long.parseLong(pageAreas.getPid()));//通过pid获取一个父类下的所有子类
				if(areaslist.size()>0){//如果有子类
					for (Areas areas : areaslist) {//遍历对比有没有相同的名称
						String oldName = areas.getAreaName().trim().toUpperCase();//(areas.getAreaName()).trim().toUpperCase();
						long oldId = areas.getId();
						if(StringUtil.isNotEmpty(newStrId)){//说明id有值,shi修改的
							long newId = Long.parseLong(newStrId);
							if(oldName.equals(newName) && oldId != newId){
								flag = false;//如果有相同；
								break;
							}
						}else{//id没值说明是新增的
							if(oldName.equals(newName)){//只要判断和其他父类区域名相同，相同时返回false
								flag = false;//如果有相同；
								break;
							}
						}
					}
				}
			}
		}
		PageMessage pm = new PageMessage();
		pm.setSuccess(flag);
		return pm;
	}
	
	/**
	 *  @Title: getSonAreas
	 * @Description: 找出所有的子区域
	 * @param:    
	 * @return:   
	 */
	@Override
	public List<PageAreas> getSonAreas() {
		List<Areas> list = areasDao.getAllParentAreas();
		Set<Areas> set = list.get(0).getAreasSet();
		List<PageAreas> list1 = new ArrayList<PageAreas>();
		for(Areas areas:set){
			PageAreas areas2 = new PageAreas();
			areas2.setId(areas.getId()+"");
			areas2.setAreaName(areas.getAreaName());
			list1.add(areas2);
		}
		Collections.sort(list1);
		return list1;
	}
	
	/**
	 * @Description: 修改区域税率
	 * @param @param pageAreas   
	 * @return void  
	 * @author wj
	 * @date 2015-6-10
	 */
	public PageMessage modifyAreas(PageAreas pageAreas){
		PageMessage pm = new PageMessage();
		long id = Long.parseLong(pageAreas.getId().trim());
		Areas areas = areasDao.getById(id);
		areas.setAreaName(pageAreas.getAreaName().trim());
		areas.setTax(Double.parseDouble(pageAreas.getTax().toString()));
		areas.setTaxMode(pageAreas.getTaxMode());
		String strPid = pageAreas.getPid();
		if(StringUtil.isNotEmpty(strPid)){
			Long pid = Long.parseLong(strPid);
			if(pid!=0){//说明是修改的z子区域
				Areas a = new Areas();
				a.setId(pid);
				areas.setParent(a);
			}
		}
		areasDao.updateAreas(areas);
		return pm;
	}
	
	/**
	 * @Description: 删除区域税率
	 * @param @param pageAreas
	 * @param @return   
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-10
	 */
	public PageMessage deleteArea(PageAreas pageAreas){
		PageMessage pm = new PageMessage();
		int temp = 0;
		if(pageAreas.getId()!=""){
			long id = Long.parseLong(pageAreas.getId());
			Areas areas = areasDao.getById(id);
			Set<Areas> set = areas.getAreasSet();
			if(set.size()>0){//说明是一个父区域，并且里面还包含有子区域
				pm.setSuccess(false);
				pm.setErrorMsg(MessageConstant.AREA_CONTAIN);
				return pm;
			}
			else{
				temp = areasDao.deleteByAreas(areas);
			}
		}
		if(temp>0){//删除成功
			pm.setSuccess(true);
		}else{
			pm.setSuccess(false);
			pm.setErrorMsg(MessageConstant.OPERATION_FAILED);
		}
		return pm;
	}

	/**
	 * @Title: getAreasById
	 * @Description: 通过主键找区域
	 * @param:    
	 * @return:   
	 */
	@Override
	public Areas getAreasById(long id) {
		if(id!=0){
			return areasDao.getById(id);
		}
		return null;
	}

	/**
	 * @Title: getViewAreaList
	 * @Description: 获取所有区域税率视图数据
	 * @param: @return
	 * @return List<ViewArea>  
	 */
	@Override
	public List<ViewArea> getViewAreaList() {
		List<ViewArea>  viewAreaList = viewAreaDao.getViewAreaList();
		return viewAreaList;
	}
	
}
