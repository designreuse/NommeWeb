package com.camut.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camut.dao.CharityDao;
import com.camut.dao.OrderDao;
import com.camut.dao.RestaurantsDao;
import com.camut.model.Charity;
import com.camut.model.OrderHeader;
import com.camut.model.Restaurants;
import com.camut.model.api.CharityAllApiModel;
import com.camut.model.api.CharityApiModel;
import com.camut.pageModel.PageCharity;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageStatementCharitys;
import com.camut.service.CharityService;
import com.camut.utils.StringUtil;
/**
 * @ClassName CharityServiceImpl.java
 * @author zyf
 * @createtime 2015-08-11
 * @author
 * @updateTime
 * @memo
 */
@Service
public class CharityServiceImpl implements CharityService {

	@Autowired private CharityDao charityDao;
	@Autowired private OrderDao orderDao;
	@Autowired private RestaurantsDao restaurantsDao;
	
	/**
	 * @Title: getCharity
	 * @Description: 显示所有慈善机构
	 * @param:    
	 * @return: List<CharityApiModel>
	 */
	@Override
	public CharityAllApiModel getCharity(String orderId) {
		CharityAllApiModel charityAllApiModel = new CharityAllApiModel();
		List<CharityApiModel> camList = new ArrayList<CharityApiModel>();
		List<Charity> cList = charityDao.getCharity();
		OrderHeader oh = orderDao.getOrderById(Long.parseLong(orderId));
		if(oh==null){
			return null;
		}
		Restaurants r = restaurantsDao.getRestaurantsById(oh.getRestaurantId());//获取商家对象
		for (Charity charity : cList) {
			CharityApiModel cam = new CharityApiModel();
			cam.setCharityName(charity.getCharityName());
			cam.setCharityId(charity.getId());
			camList.add(cam);
		}
		charityAllApiModel.setRestaurantName(r.getRestaurantName());
		charityAllApiModel.setTotal((Math.round(oh.getTotal()*0.05*100))/100.0);
		charityAllApiModel.setOrderDate(StringUtil.transformDateToAMPMString(oh.getOrderDate()));
		charityAllApiModel.setCharity(camList);
		List<Charity> oftenList = charityDao.getOftenCharity();
		charityAllApiModel.setOftenCharity(oftenList);
		
		return charityAllApiModel;
	}

	/**
	 * @Title: getCharity
	 * @Description: 显示所有慈善机构
	 * @param:    
	 * @return: List<CharityApiModel>
	 */
	@Override
	public List<Charity> getAllCharity() {
		try {
			return charityDao.getCharity();
		} catch (Exception e) {
			
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @Title: getAllPageCharity
	 * @Description: 后台管理，获取分页的慈善机构列表
	 * @param: @param pf
	 * @param: @return
	 * @return List<PageCharity>  
	 */
	public List<PageCharity>getAllPageCharity(PageFilter pf){
		//List<PageCharity> pageList = new ArrayList<PageCharity>();
		List<PageCharity> pageList = charityDao.getAllPageCharity(pf);
		/*if(list!=null && list.size()>0){
			for (Charity charity : list) {
				PageCharity pc = new PageCharity();
				org.springframework.beans.BeanUtils.copyProperties(charity, pc);
				pageList.add(pc);
			}
			return pageList;
		}*/
		return pageList;
	}

	/**
	 * @Title: getOftenCharity
	 * @Description: 获取常用的慈善机构（3个）
	 * @param:    
	 * @return: List<Charity>
	 */
	@Override
	public List<Charity> getOftenCharity() {
		return charityDao.getOftenCharity();
	}
	
	/**
	 * @Title: getcount
	 * @Description: 获取数据总条数
	 * @param: @return
	 * @return int  
	 */
	@Override
	public int getCount (){
		return charityDao.getCount();
	}

	/**
	 * @Title: getCharityByCharityName
	 * @Description: 通过名称获取慈善机构对象 
	 * @param: @param charityName
	 * @param: @return
	 * @return Charity  
	 */
	@Override
	public Charity getCharityByCharityName(String charityName){
		return charityDao.getCharityByCharityName(charityName);
	}
	
	/**
	 * @Title: addCharity
	 * @Description: //增加慈善机构 
	 * @param: @param pc
	 * @param: @return
	 * @return long  
	 */
	@Override
	public long addCharity(PageCharity pc){
		Charity charity = new Charity();
		charity.setCharityName(pc.getCharityName());
		charity.setPhone(pc.getPhone());
		charity.setAddress(pc.getAddress());
		charity.setDescription(pc.getDescription());
		charity.setStatus(pc.getStatus());
		return charityDao.addCharity(charity);
	}
	
	/**
	 * @Title: updateCharity
	 * @Description: 修改慈善机构
	 * @param: @return
	 * @return int  
	 */
	@Override
	public int updateCharity(PageCharity pc){
		Charity charity = charityDao.getCharityByCharityId(Long.parseLong(pc.getId()));
		charity.setId(Long.parseLong(pc.getId()));
		charity.setCharityName(pc.getCharityName());
		charity.setPhone(pc.getPhone());
		charity.setAddress(pc.getAddress());
		charity.setDescription(pc.getDescription());
		charity.setStatus(pc.getStatus());
		return charityDao.updateCharity(charity);
	}
	
	/**
	 * @Title: deleteCharity
	 * @Description: 删除慈善机构
	 * @param: @param charityId
	 * @param: @return
	 * @return int  
	 */
	@Override
	public int deleteCharity(int charityId){
		Charity charity = charityDao.getCharityByCharityId(charityId);
		if(charity!=null){
			return charityDao.deleteCharity(charity);
		}else{
			return -1;
		}
	}
	
	/**
	 * @Title: getCharityList
	 * @Description: 根据时间查询慈善机构报表一数据集合
	 * @param: @param searchKey
	 * @param: @param pf
	 * @param: @return
	 * @return List<PageStatementCharitys>  
	 */
	@Override
	public List<PageStatementCharitys> getCharityList(String searchKey, PageFilter pf){
		return charityDao.getCharityList(searchKey, pf);
	}
	
	/**
	 * @Title: getOneCharityDonationTitle
	 * @Description: 获取单个慈善机构捐款信息的头信息
	 * @param: @return
	 * @return Map<String,Object>  
	 */
	@Override
	public Map<String, Object> getOneCharityDonationTitle(String searchMonth, String charityId){
		return charityDao.getOneCharityDonationTitle(searchMonth, Long.parseLong(charityId));
	}
	
	/**
	 * @Title: getOneCharityDonation
	 * @Description: 获取单个慈善机构捐款信息
	 * @param: @param searchMonth
	 * @param: @param charityId
	 * @param: @return
	 * @return PageModel  
	 */
	@Override
	public List<Map<String, Object>> getOneCharityDonation(String searchMonth, String charityId, PageFilter pf){
		if(StringUtil.isNotEmpty(charityId) && StringUtil.isNotEmpty(searchMonth)){
			return charityDao.getOneCharityDonation(searchMonth, charityId, pf);
		}else{
			return null;
		}
	}
	
	
	
	
}
