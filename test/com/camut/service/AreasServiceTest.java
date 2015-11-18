package com.camut.service;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import com.camut.model.Areas;
import com.camut.pageModel.PageAreas;
import com.camut.pageModel.PageMessage;
import com.camut.pageModel.PageModel;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:spring.xml","classpath*:spring-hibernate.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class AreasServiceTest {

	@Autowired AreasService areasService;
	
	/**
	 * @Title: updateAreas
	 * @Description: 修改区域
	 * @param:  Areas
	 * @return: areas 
	 */
	//@Test
	public void updateAreas() throws Exception {
		Areas areas = new Areas();
		areas.setId((long)1);
		areas.setAreaName("无锡");
		areas.setTax(2.2);
		areas.setTaxMode(2);
		areasService.updateAreas(areas);
	}
	
	/**
	 * @Title: deleteAreas
	 * @Description: 根据区域名删除区域
	 * @param:  Areas
	 * @return: -1表示删除失败 
	 */
	//@Test
	public void deleteAreas() throws Exception {
		String areaName = "常州";
		int flag = areasService.deleteAreas(areaName);
		if(flag == 1){
			System.out.println("ok");
		}else {
			System.out.println("no");
		}
	}
	
	/**
	 * @Description: 获取所有父类区域
	 * @param @return   
	 * @return PageModel  
	 * @author wj
	 * @date 2015-6-8
	 */
	//@Test
	public void getAllParentAreas() throws Exception {
		PageModel pm = areasService.getAllParentAreas();
		System.out.println(pm.getRows().size());
	}
	
	/**
	 * @Description: 加载区域数据到表格
	 * @param @param pageFilter
	 * @param @return   
	 * @return PageModel  
	 * @author wj
	 * @date 2015-6-8
	 */
	//@Test
	public void getAllAreas() throws Exception {
		List<PageAreas> pageList = areasService.getAllAreas();
		System.out.println(pageList.size());
	}
	
	/**
	 * @Description: 新增一个区域和税率
	 * @param @param pageAreas
	 * @param @return   
	 * @return PageModel  
	 * @author wj
	 * @date 2015-6-9
	 */
	//@Test
	public void addAreas() throws Exception {
		PageAreas pageAreas = new PageAreas();
		pageAreas.setPid("1");
		pageAreas.setAreaName("常州");
		pageAreas.setTax("0.7");
		pageAreas.setTaxMode(1);
		PageMessage pm = areasService.addAreas(pageAreas);
		System.out.println(pm.getSuccess());
	}
	
	/**
	 * @Description: 增加区域税率时校验名称在同一个父类区域中是否被使用了
	 * @param pageAreas
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-9
	 */
	//@Test
	public void areaNameExist() throws Exception {
		PageAreas pageAreas = new PageAreas();
		pageAreas.setPid("1");
		pageAreas.setAreaName("Ontarios");
		pageAreas.setTax("0.7");
		pageAreas.setTaxMode(1);
		PageMessage pm = areasService.areaNameExist(pageAreas);
		System.out.println(pm.getSuccess());
	}
	
	/**
	 * @Title: getSonAreas
	 * @Description: 找出所有的子区域
	 * @param:    
	 * @return: List<Areas>
	 */
	//@Test
	public void getSonAreas() throws Exception {
		List<PageAreas> list1 = areasService.getSonAreas();
		System.out.println(list1.size());
	}
	
	/**
	 * @Description: 修改区域税率
	 * @param @param pageAreas   
	 * @return void  
	 * @author wj
	 * @date 2015-6-10
	 */
	//@Test
	public void modifyAreas() throws Exception {
		PageAreas pageAreas = new PageAreas();
		pageAreas.setId("2");
		pageAreas.setPid("1");
		pageAreas.setAreaName("Ontarios");
		pageAreas.setTax("0.7");
		pageAreas.setTaxMode(1);
		PageMessage pm = areasService.modifyAreas(pageAreas);
		System.out.println(pm.getSuccess());
	}
	
	/**
	 * @Description: 删除区域税率
	 * @param @param pageAreas
	 * @param @return   
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-10
	 */
	//@Test
	public void deleteArea() throws Exception {
		PageAreas pageAreas = new PageAreas();
		pageAreas.setId("2");
		PageMessage pm = areasService.deleteArea(pageAreas);
		System.out.println(pm.getSuccess());
	}

	/**
	 * @Title: getAreasById
	 * @Description: 通过主键找区域
	 * @param:    
	 * @return: Areas
	 */
	@Test
	public void getAreasById() throws Exception {
		long id = 1;
		Areas areas = areasService.getAreasById(id);
		if(areas != null){
			System.out.println("ok");
		}else {
			System.out.println("no");
		}
	}
	
}
