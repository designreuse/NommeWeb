/**
 * 
 */
package com.camut.dao;

import java.util.List;

import com.camut.model.Consumers;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageStatementConsumer;

/**
 * @dao ConsumersDao.java
 * @author wangpin
 * @createtime May 26, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface ConsumersDao {

	/**
	 * @Title: getConsumersByLoginName
	 * @Description: 根据登录名获取Consumers对象 get Consumers By Login Name
	 * @param:  loginName
	 * @return: Consumers类 return null if the consumer does not exist
	 */
	public Consumers getConsumersByLoginName(String loginName);
	
	/**
	 * @Title: addConsumers
	 * @Description: 会员注册 customer registration
	 * @param:  consumers 对象  
	 * @return: int -1表示注册失败 ，0表示会员email已存在，1表示注册成功   -1: registration failed
	 */
	public int addConsumers(Consumers consumers);
	
	/**
	 * @Title: uodateLastLoginDate
	 * @Description: 会员登录修改最后一次登陆时间
	 * @param:    consumers
	 * @return: void
	 */
	public void updateLastLoginDate(Consumers consumers);
	
	/**
	 * @Title: getByLoginName
	 * @Description: 会员登录
	 * @param:    consumers
	 * @return: Consumers
	 */
	public Consumers getByLoginName(Consumers consumers);
	
	/**
	 * @Title: getConsumersByOtherCode
	 * @Description: 根据第三方ID获取Consumers对象
	 * @param:    otherCode   loginName
	 * @return: Consumers
	 */
	public Consumers getConsumersByOtherCode(String otherCode,int loginType);
	
	/**
	 * @Title: updateConsumers
	 * @Description: 用户信息修改
	 * @param:    consumers
	 * @return: int -1修改失败 1修改成功
	 */
	public int updateConsumers(Consumers consumers);
	
	/**
	 * @Title: getConsumersById
	 * @Description: 通id查找用户
	 * @param:    id
	 * @return: Consumers
	 */
	public Consumers getConsumersById(long id);
	
	/**
	 * @Title: getConsumersByuuId
	 * @Description: 通uuid查找用户
	 * @param:    uuid
	 * @return: Consumers
	 */
	public Consumers getConsumersByUuid(String consumerUuid);
	
	/**
	 * @Title: saveTokenAndType
	 * @Description: 用户登录保存token和type
	 * @param:    consumers
	 * @return: id
	 */
	public String saveTokenAndType(Consumers consumers);
	
	/**
	 * @Title: updateTokenAndType
	 * @Description: 修改token和type
	 * @param:    consumers
	 * @return: Consumers
	 */
	public Consumers updateTokenAndType(Consumers consumers);
	
	/**
	 * @Title: addConsumerForNomme
	 * @Description: 增加会员
	 * @param:    Consumers
	 * @return: int -1增加失败，1增加成功
	 */
	public int addConsumerForNomme(Consumers consumers);
	
	/**
	 * @Title: getConsumersTotalNo
	 * @Description:获取注册用户总数量 
	 * @param: @return
	 * @return int  
	 */
	public int getConsumersTotalNo();
	
	/**
	 * @Title: getStatementAllConsumers
	 * @Description: 获取管理员的用户报表数据加载到表格
	 * @param: @return
	 * @return List<PageStatementConsumer>  
	 */
	public List<PageStatementConsumer> getStatementAllConsumers(PageFilter pf);
	
}
