/**
 * 
 */
package com.camut.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.camut.dao.ConsumersDao;
import com.camut.model.Consumers;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageStatementConsumer;
import com.camut.utils.MD5Util;
import com.camut.utils.StringUtil;

/**
 * @daoimpl ConsumersDaoImpl.java
 * @author wangpin
 * @createtime May 26, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class ConsumersDaoImpl extends BaseDao<Consumers> implements	ConsumersDao {

	/**
	 * (non-Javadoc)
	 * @see
	 * com.camut.dao.ConsumersDao#getConsumersByLoginName(com.camut.model.Consumers* )
	 * @Description: 根据登录名获取Consumers对象 get Consumers By Login Name
	 * @param: loginName
	 * @return: Consumers类 return null if the consumer does not exist
	 */
	@Override
	public Consumers getConsumersByLoginName(String loginName) {
		String hql = "from Consumers c where c.email=:email";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", loginName);
		return this.get(hql, map);
	}

	/**
	 * @Title: addConsumers
	 * @Description: 会员注册 customer registration
	 * @param:  consumers 对象  
	 * @return: int -1表示注册失败 ，0表示会员email已存在，1表示注册成功   -1: registration failed
	 */
	@Override
	public int addConsumers(Consumers consumers) {
		try {
			consumers.setStatus(0);
			consumers.setUuid(StringUtil.getUUID());
			consumers.setPassword(MD5Util.md5(consumers.getPassword()));
			consumers.setRegDate(new Date());
			this.save(consumers);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: uodateLastLoginDate
	 * @Description: 会员登录修改最后一次登陆时间
	 * @param:    consumers
	 * @return: void
	 */
	@Override
	public void updateLastLoginDate(Consumers consumers) {
		consumers.setLastLoginDate(new Date());
		consumers.setMobileType("2");
		try {
			this.update(consumers);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * @Title: getByLoginName
	 * @Description: 会员登录
	 * @param:    consumers
	 * @return: Consumers
	 */
	@Override
	public Consumers getByLoginName(Consumers consumers) {
		String hql = "from Consumers c where c.email=:email and c.status=0";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", consumers.getEmail());
		return this.get(hql, map);
	}

	/**
	 * @Title: getConsumersByOtherCode
	 * @Description: 根据第三方ID获取Consumers对象
	 * @param:    mobileToken
	 * @return: Consumers
	 */
	@Override
	public Consumers getConsumersByOtherCode(String otherCode, int loginType) {
		String hql = "from Consumers c where c.otherCode=:otherCode and c.loginType=:loginType";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("otherCode", otherCode);
		map.put("loginType", loginType);
		return this.get(hql, map);
	}

	/**
	 * @Title: updateConsumers
	 * @Description: 用户信息修改
	 * @param:    consumers
	 * @return: int -1修改失败 1修改成功
	 */
	@Override
	public int updateConsumers(Consumers consumers) {
		try {
			this.update(consumers);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * @Title: getConsumersById
	 * @Description: 通id查找用户
	 * @param:    id
	 * @return: Consumers
	 */
	@Override
	public Consumers getConsumersById(long id) {
		String hql = "from Consumers c where c.id=:id and c.status=0";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return this.get(hql, map);
	}
	
	/**
	 * @Title: getConsumersByuuId
	 * @Description: 通uuid查找用户
	 * @param:    uuid
	 * @return: Consumers
	 */
	@Override
	public Consumers getConsumersByUuid(String consumerUuid) {
		String hql = "from Consumers c where c.uuid=:consumerUuid and c.status=0";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("consumerUuid", consumerUuid);
		return this.get(hql, map);
	}

	/**
	 * @Title: saveTokenAndType
	 * @Description: 用户登录保存token和type
	 * @param:    consumers
	 * @return: id
	 */
	@Override
	public long saveTokenAndType(Consumers consumers) {
		try {
			consumers.setStatus(0);
			consumers.setRegDate(new Date());
			return (Long) this.save(consumers);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * @Title: updateTokenAndType
	 * @Description: 修改token和type
	 * @param:    consumers
	 * @return: Consumers
	 */
	@Override
	public Consumers updateTokenAndType(Consumers consumers) {
		try {
			this.update(consumers);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Title: addConsumerForNomme
	 * @Description: 增加会员
	 * @param:    Consumers
	 * @return: int -1增加失败，1增加成功
	 */
	@Override
	public int addConsumerForNomme(Consumers consumers) {
		try {
			this.save(consumers);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}
	
	/**
	 * @Title: getConsumersTotalNo
	 * @Description:获取注册用户总数量 
	 * @param: @return
	 * @return int  
	 */
	@Override
	public int getConsumersTotalNo(){
		String sql = "select count(*) from tbl_consumers";
		try {
			return this.countBySql(sql).intValue();
		} catch (Exception e) {
			return -1;
		}
		
	}
	
	/**
	 * @Title: getStatementAllConsumers
	 * @Description: 获取管理员的用户报表数据加载到表格
	 * @param: @return
	 * @return List<PageStatementConsumer>  
	 */
	@Override
	public List<PageStatementConsumer> getStatementAllConsumers(PageFilter pf){
		String orderColumn = "b.count1";//排序字段
		if("refundOrderQuantity".equals(pf.getSort())){
			orderColumn = "d.count1";
		}else if("unfinishedOrderQuantity".equals(pf.getSort())){
			orderColumn = "c.count1";
		}
		String ascOrDesc = "asc";
		if("desc".equals(pf.getOrder())){
			ascOrDesc = "desc";
		}
		String sql = "select a.id as consumerId, a.firstname as firstName, a.lastname as lastName, a.phone as phone, a.reg_date as regDate, a.last_login_date as lastLoginDate, "
				+"ifnull(b.count1,0) as completedOrderQuantity, ifnull(b.amount,0.00) as completedOrderAmount, "
				+"ifnull(c.count1,0) as unfinishedOrderQuantity, ifnull(c.amount,0.00) as unfinishedOrderAmount, "
				+"ifnull(d.count1,0) as refundOrderQuantity, ifnull(d.amount,0.00) as refundOrderAmount, ifnull(e.oc_money,0.00) as donateAmount, "
				+"ifnull(a.mobile_type,2) as mobileType "//如果移动设备类型为空，就默认显示为使用web登录的，0：Android 1:IOS
			+"from tbl_consumers a "
		+"LEFT JOIN (select oh.consumer_uuid as oh_consumerUuid, oh.`status`, count(oh.consumer_uuid) as count1, FORMAT(sum(oh.amount), 2) as amount from dat_order_header oh WHERE oh.`status`=7 GROUP BY oh.consumer_uuid) b "
			+"on b.oh_consumerUuid = a.uuid "//完成的订单
		+"LEFT JOIN (select oh.consumer_uuid as oh_consumerUuid, oh.`status`, count(oh.consumer_uuid) as count1, FORMAT(sum(oh.amount), 2) as amount from dat_order_header oh WHERE oh.`status` in (1,2,3,8,9,10) GROUP BY oh.consumer_uuid) c "
			+"on c.oh_consumerUuid = a.uuid "//未完成的订单
		+"LEFT JOIN (select oh.consumer_uuid as oh_consumerUuid, oh.`status`, count(oh.consumer_uuid) as count1, FORMAT(sum(oh.amount), 2) as amount from dat_order_header oh WHERE oh.`status` in (0,4,6) GROUP BY oh.consumer_uuid) d "
			+"on d.oh_consumerUuid = a.uuid "//退款的订单
		+"LEFT JOIN (select oc.consumer_uuid as oc_consumerUuid, FORMAT(sum(oc.money), 2) as oc_money from tbl_order_charity oc where oc.consumer_uuid >0 GROUP BY oc.consumer_uuid) e "
			+"on e.oc_consumerUuid = a.uuid "//捐款
				+"order by "+orderColumn+" "+ascOrDesc+" limit :offset,:rows";//排序
		
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		
		//query.setParameter("orderColumn", orderColumn);
		//query.setParameter("ascOrDesc", pf.getOrder());
		query.setParameter("offset", pf.getOffset());
		query.setParameter("rows", pf.getLimit());
		query.setResultTransformer(Transformers.aliasToBean(PageStatementConsumer.class));
		query.addScalar("consumerId",new org.hibernate.type.StringType());
		query.addScalar("firstName",new org.hibernate.type.StringType());
		query.addScalar("lastName",new org.hibernate.type.StringType());
		query.addScalar("phone",new org.hibernate.type.StringType());
		query.addScalar("regDate",new org.hibernate.type.StringType());
		query.addScalar("lastLoginDate",new org.hibernate.type.StringType());
		query.addScalar("completedOrderQuantity",new org.hibernate.type.StringType());
		query.addScalar("completedOrderAmount",new org.hibernate.type.StringType());
		query.addScalar("unfinishedOrderQuantity",new org.hibernate.type.StringType());
		query.addScalar("unfinishedOrderAmount",new org.hibernate.type.StringType());
		query.addScalar("refundOrderQuantity",new org.hibernate.type.StringType());
		query.addScalar("refundOrderAmount",new org.hibernate.type.StringType());
		query.addScalar("donateAmount",new org.hibernate.type.StringType());
		query.addScalar("mobileType",new org.hibernate.type.StringType());
		return query.list();
	}
	
	

}
