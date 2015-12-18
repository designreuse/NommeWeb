package com.camut.dao.impl;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.camut.dao.CharityDao;
import com.camut.model.Charity;
import com.camut.model.api.CharityApiModel;
import com.camut.pageModel.PageCharity;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageStatementCharitys;
import com.camut.utils.StringUtil;

/**
 * @ClassName CharityDaoImpl.java
 * @author zyf
 * @createtime 2015-08-11
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class CharityDaoImpl extends BaseDao<Charity> implements CharityDao {

	private int count;//查询出的总数量
	
	/**
	 * @Title: getcount
	 * @Description: 获取数据总条数
	 * @param: @return
	 * @return int  
	 */
	@Override
	public int getCount (){
		return this.count;
	}
	
	/**
	 * @Title: getCharity
	 * @Description: 显示所有慈善机构
	 * @param:    
	 * @return: List<Charity>
	 */
	@Override
	public List<Charity> getCharity() {
		String hql = "from Charity c ORDER BY c.charityName ASC";
		return this.find(hql);
	}
	
	/**
	 * @Title: getAllCharityFirstCharacters
	 * @Description: Gets the list of unique first characters for all charities.
	 * @return: List<String>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllCharityFirstCharacters() {
		String sql = "SELECT DISTINCT(LEFT(charity_name,1)) as charityName ";
		sql += "FROM tbl_charity ";
		sql += "ORDER BY charityName ASC ";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		return query.list();
	}
	
	/**
	 * @Title: getAllPageCharity
	 * @Description: 后台管理，获取分页的慈善机构列表
	 * @param: @param pf
	 * @param: @return
	 * @return List<Charity>  
	 */
	public List<PageCharity>getAllPageCharity(PageFilter pf){
		//String hql = "from Charity limit :offset,:limit orderby Charity.charityName";
		//String hql = "from Charity orderby Charity.charityName";
		//Map<String,Object> map = new HashMap<String, Object>();
		//map.put("offset", pf.getOffset());
		//map.put("limit", pf.getLimit());
		String sql = "select a.id as id, a.charity_name as charityName, a.address as address, a.phone as phone, " +
				"a.description as description, a.status as status from tbl_charity a limit :offset,:rows";
		
		
		count = this.countBySql("select count(*) from tbl_charity a").intValue();
		
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter("offset", pf.getOffset());
		query.setParameter("rows", pf.getLimit());
		query.setResultTransformer(Transformers.aliasToBean(PageCharity.class));
		query.addScalar("id",new org.hibernate.type.StringType());
		query.addScalar("charityName",new org.hibernate.type.StringType());
		query.addScalar("address",new org.hibernate.type.StringType());
		query.addScalar("phone",new org.hibernate.type.StringType());
		query.addScalar("description",new org.hibernate.type.StringType());
		query.addScalar("status",new org.hibernate.type.StringType());
		try {
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @Title: getOftenCharity
	 * @Description: 获取常用的慈善机构（3个）
	 * @param:    
	 * @return: List<Charity>
	 */
	@Override
	public List<Charity> getOftenCharity() {
		String sql = "select a.id as charityId, a.charity_name as charityName from tbl_charity a "+
				"LEFT JOIN (select COUNT(c.charity_id) as orderNum, c.charity_id as charityId" +
				" from tbl_order_charity c GROUP BY c.charity_id ) b"+
				" ON b.charityId=a.id ORDER BY b.orderNum desc LIMIT 0,3 ";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(CharityApiModel.class));
		query.addScalar("charityId", new org.hibernate.type.LongType());
		query.addScalar("charityName", new org.hibernate.type.StringType());
		try {
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
		String hql = "from Charity c where c.charityName=:charityName ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("charityName", charityName);
		return this.get(hql, map);
	}
	
	/**
	 * @Title: getCharityByCharityId
	 * @Description: 
	 * @param: @param charityId
	 * @param: @return
	 * @return Charity  
	 */
	@Override
	public Charity getCharityByCharityId(long charityId){
		String hql = "from Charity c where c.id=:charityId ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("charityId", charityId);
		return this.get(hql, map);
	}
	
	/**
	 * @Title: addCharity
	 * @Description: //增加慈善机构 
	 * @param: @param pc
	 * @param: @return
	 * @return long  
	 */
	@Override
	public long addCharity(Charity charity){
		try{
			long id = (Long) this.save(charity);
			return id;
		}catch(Exception e){
			return -1;
		}
	}
	
	/**
	 * @Title: updateCharity
	 * @Description: 修改慈善机构
	 * @param: @return
	 * @return int  
	 */
	@Override
	public int updateCharity(Charity charity){
		try {
			this.update(charity);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}
	
	
	/**
	 * @Title: deleteCharity
	 * @Description: 删除慈善机构
	 * @param: @param charityId
	 * @param: @return
	 * @return int  
	 */
	@Override
	public int deleteCharity(Charity charity){
		try {
			this.delete(charity);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
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
		if(StringUtil.isEmpty(searchKey)){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			searchKey = format.format(new Date());
		}
		String sql = "select c.id as id, c.charity_name as associations, b.a_count as count, b.a_amount as amount from tbl_charity c "+
					 "LEFT JOIN (" +
					 	"select a.charity_id as a_id, sum(a.money) as a_amount, count(a.order_id) as a_count,oh.createdate as createdate "+
						"from tbl_order_charity a LEFT JOIN dat_order_header oh on oh.id=a.order_id " +
						"where DATE_FORMAT(oh.createdate,'%Y-%m')='" + searchKey + "' GROUP BY a.charity_id " +
					 ") b "+
					 "on b.a_id=c.id ";
					 
		count = this.countBySql("select count(*) from ("+sql+") cou").intValue();		 
		
		sql += " limit :offset,:rows";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter("offset", pf.getOffset());
		query.setParameter("rows", pf.getLimit());
		query.setResultTransformer(Transformers.aliasToBean(PageStatementCharitys.class));
		Field fields[] = PageStatementCharitys.class.getDeclaredFields();
		for (Field field : fields) {
			query.addScalar(field.getName(), new org.hibernate.type.StringType());
		}
		/*query.addScalar("id",new org.hibernate.type.StringType()); 
		query.addScalar("associations",new org.hibernate.type.StringType()); 
		query.addScalar("count",new org.hibernate.type.StringType()); 
		query.addScalar("amount",new org.hibernate.type.StringType()); */
		
		return query.list();
	}
	

	/**
	 * @Title: getOneCharityDonationTitle
	 * @Description: 获取单个慈善机构捐款信息的头信息
	 * @param: @return
	 * @return Map<String,Object>  
	 */
	@Override
	public Map<String, Object> getOneCharityDonationTitle(String searchMonth, long charityId){
		String sql = "select c.charity_name as association, c.address as address, c.phone as phone, format(ifnull(b.a_amount,0.00),2) as amount, ifnull(b.a_count,0) as count from tbl_charity c "
					+"left join ( "
						+"select a.charity_id as a_id, sum(a.money) as a_amount, count(a.order_id) as a_count from tbl_order_charity a "
						+"LEFT JOIN dat_order_header oh on a.order_id=oh.id where DATE_FORMAT(oh.createdate,'%Y-%m')=:searchMonth and a.charity_id=:charityId GROUP BY a.charity_id "
					+") b "
					+"on b.a_id=c.id where c.id=:charityId2";
		
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter("searchMonth", searchMonth);
		query.setParameter("charityId", charityId);
		query.setParameter("charityId2", charityId);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) query.uniqueResult();
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
		String orderBy = "createDate";
		String ascOrDesc ="asc";
		int offset = 0;
		int rows = 10;
		if("restaurantName".equals(pf.getSort())){
			orderBy = "restaurantName";
		}else if("count".equals(pf.getSort())){
			orderBy = "count";
		}else if("amount".equals(pf.getSort())){
			orderBy = "amount";
		}
		if("desc".equals(pf.getOrder())){
			ascOrDesc ="desc";
		}
		
		String sql = "select c.createDate as createDate, count(c.createDate) as count, format(sum(c.money),2) as amount, c.resName as restaurantName  from ("
							+"select DATE_FORMAT(b.createDate,'%Y-%m-%d') as createDate, a.money as money, b.resName as resName "
								+"from ( "
									+"select res.restaurant_name as resName, oh.id as ohId, oh.createdate as createDate "
									+"from dat_order_header oh "
									+"LEFT JOIN dat_restaurants res on oh.restaurant_uuid=res.uuid "
								+")b "
							+"RIGHT JOIN tbl_order_charity a on a.order_id=b.ohId "
							+"where DATE_FORMAT(b.createDate,'%Y-%m')=:searchMonth and a.charity_id=:charityId "
					+") c "
					+"group by createDate, restaurantName ORDER BY "+orderBy+" "+ascOrDesc+" ";  
		
		String sql2 = "select count(*) as count from("+sql+") d";
		SQLQuery query2 = this.getCurrentSession().createSQLQuery(sql2);
		query2.setParameter("searchMonth", searchMonth);
		query2.setParameter("charityId", charityId);
		//query2.setParameter("orderBy", orderBy);
		//query2.setParameter("ascOrDesc", ascOrDesc);
		count = Integer.parseInt(query2.list().get(0).toString());
		
		sql += "limit :offset,:rows";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter("searchMonth", searchMonth);
		query.setParameter("charityId", charityId);
		//query.setParameter("orderBy", orderBy);
		//query.setParameter("ascOrDesc", ascOrDesc);
		query.setParameter("offset", pf.getOffset());
		query.setParameter("rows", pf.getLimit());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	
}
