package com.camut.dao;

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

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:spring.xml","classpath*:spring-hibernate.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class AreasDaoTest {

	@Autowired private AreasDao areasDao;
	
	/**
	 * @Title: getByAreaName
	 * @Description: 根据区域名获取对象，用来判断区域名是否存在
	 * @param:  Areas
	 * @return: areas 
	 */
	//@Test
	public void getByAreaName() throws Exception{
		String areaName = "Canada";
		Areas areas = areasDao.getByAreaName(areaName);
		if(areas != null){
			System.out.println("ok");
		}
	}
	
	/**
	 * @Title: addAreas
	 * @Description: 添加区域
	 * @param:  Areas
	 * @return: int -1表示添加失败 ，0区域名已存在，1添加成功  
	 */
	//@Test
	public void addAreas() throws Exception {
		Areas areas = new Areas();
		areas.setAreaName("zxc");
		areas.setTax(0.2);
		areas.setTaxMode(2);
		int flag = areasDao.addAreas(areas);
		if (flag == -1) {
			System.out.println("添加失败");
		} else if (flag == 0) {
			System.out.println("区域名已存在");
		} else if (flag == 1) {
			System.out.println("添加成功");
		}
	}
	
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
		areas.setAreaName("Canada");
		areas.setTax(0.3);
		areas.setTaxMode(1);
		int flag = areasDao.updateAreas(areas);
		if (flag == -1) {
			System.out.println("修改失败");
		} else if (flag == 1) {
			System.out.println("修改成功");
		}
	}
	
	/**
	 * @Title: deleteAreas
	 * @Description: 根据区域名删除区域
	 * @param:  Areas
	 * @return: -1表示删除失败 
	 */
	//@Test
	public void deleteAreas() throws Exception {
		String areaName = "Ontario";
		int flag = areasDao.deleteAreas(areaName);
		if(flag == 1){
			System.out.println("删除成功");
		}else if (flag == -1) {
			System.out.println("删除失败");
		}
	}
	
	/**
	 * @Title: getAllParentAreas
	 * @Description: 获取所有父类区域，每个父区域都包含各自所有的子区域，所以可以用来获取所有区域
	 * @param:  Areas
	 * @return: -1表示删除失败 
	 * @author wj
	 * @date 2015-6-8
	 */
	//@Test
	public void getAllParentAreas() throws Exception {
		List <Areas> aList = areasDao.getAllParentAreas();
		System.out.println(aList.size());
	}
	
	/**
	 * @Description: 通过父类id获取旗下的所有子类
	 * @param pid
	 * @return List<Areas>  
	 * @author wj
	 * @date 2015-6-8
	 */
	//@Test
	public void getByParentId() throws Exception {
		long pid = 1;
		List<Areas> aList = areasDao.getByParentId(pid);
		System.out.println(aList.size());
	}
	
	/**
	 * @Description: 通过id取得区域
	 * @param @param id
	 * @param @return   
	 * @return Areas  
	 * @author wj
	 * @date 2015-6-10
	 */
	//@Test
	public void getById() throws Exception {
		long id = 1;
		Areas areas = areasDao.getById(id);
		if (areas != null) {
			System.out.println("ok");
		} else {
			System.out.println("no");
		}
	}
	
	/**
	 * @Description: 通过id删除区域
	 * @param @param id
	 * @param @return   
	 * @return int  
	 * @author wj
	 * @date 2015-6-10
	 */
	@Test
	public void deleteByAreas() throws Exception {
		Areas areas = new Areas();
		areas.setId((long) 2);
		int flag = areasDao.deleteByAreas(areas);
		if (flag == 1) {
			System.out.println("删除成功");
		}
		if (flag == -1) {
			System.out.println("删除失败");
		} else {

		}
	}
	
}
