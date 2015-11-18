package com.camut.dao;

import java.util.List;
import java.util.Map;

import com.camut.model.Charity;
import com.camut.pageModel.PageCharity;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageStatementCharitys;

/**
 * @ClassName CharityDao.java
 * @author zyf
 * @createtime 2015-08-11
 * @author
 * @updateTime
 * @memo
 */
public interface CharityDao {

	/**
	 * @Title: getCharity
	 * @Description: 显示所有慈善机构
	 * @param:    
	 * @return: List<Charity>
	 */
	public List<Charity> getCharity();
	
	/**
	 * @Title: getAllPageCharity
	 * @Description: 后台管理，获取分页的慈善机构列表
	 * @param: @param pf
	 * @param: @return
	 * @return List<Charity>  
	 */
	public List<PageCharity>getAllPageCharity(PageFilter pf);
	
	/**
	 * @Title: getOftenCharity
	 * @Description: 获取常用的慈善机构（3个）
	 * @param:    
	 * @return: List<Charity>
	 */
	public List<Charity> getOftenCharity();
	
	/**
	 * @Title: getCount
	 * @Description: 获取数据总条数
	 * @param: @return
	 * @return int  
	 */
	public int getCount();
	
	/**
	 * @Title: getCharityByCharityName
	 * @Description: 通过名称获取慈善机构对象 
	 * @param: @param charityName
	 * @param: @return
	 * @return Charity  
	 */
	public Charity getCharityByCharityName(String charityName);
	
	/**
	 * @Title: getCharityByCharityId
	 * @Description: 
	 * @param: @param charityId
	 * @param: @return
	 * @return Charity  
	 */
	public Charity getCharityByCharityId(long charityId);
	
	/**
	 * @Title: addCharity
	 * @Description: //增加慈善机构 
	 * @param: @param pc
	 * @param: @return
	 * @return long  
	 */
	public long addCharity(Charity charity);
	
	
	/**
	 * @Title: updateCharity
	 * @Description: 修改慈善机构
	 * @param: @return
	 * @return int  
	 */
	public int updateCharity(Charity charity);
	
	/**
	 * @Title: deleteCharity
	 * @Description: 删除慈善机构
	 * @param: @param charityId
	 * @param: @return
	 * @return int  
	 */
	public int deleteCharity(Charity charity);
	
	/**
	 * @Title: getCharityList
	 * @Description: 根据时间查询慈善机构报表一数据集合
	 * @param: @param searchKey
	 * @param: @param pf
	 * @param: @return
	 * @return List<PageStatementCharitys>  
	 */
	public List<PageStatementCharitys> getCharityList(String searchKey, PageFilter pf);
	
	/**
	 * @Title: getOneCharityDonationTitle
	 * @Description: 获取单个慈善机构捐款信息的头信息
	 * @param: @return
	 * @return Map<String,Object>  
	 */
	public Map<String, Object> getOneCharityDonationTitle(String searchMonth, long charityId);
	
	/**
	 * @Title: getOneCharityDonation
	 * @Description: 获取单个慈善机构捐款信息
	 * @param: @param searchMonth
	 * @param: @param charityId
	 * @param: @return
	 * @return PageModel  
	 */
	public List<Map<String, Object>> getOneCharityDonation(String searchMonth, String charityId, PageFilter pf);
	
	
	
}
