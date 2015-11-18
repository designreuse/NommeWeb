/**
 * 
 */
package com.camut.service;

import java.util.List;

import com.camut.model.Consumers;
import com.camut.model.api.ConsumersApiModel;
import com.camut.model.api.ViewConsumerClassifitionApiModel;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageStatementConsumer;

/**
 * @Service ConsumersService.java
 * @author wangpin
 * @createtime May 26, 2015 2:39:08 PM
 * @author
 * @updateTime
 * @memo
 */
public interface ConsumersService {

	
	public Consumers login(Consumers consumers);
	
	/**
	 * @Title: consumersLogin
	 * @Description: 会员登录方法 customer login
	 * @param: consumers   
	 * @return: int -1表示用户名不存在，0表示密码错误，1代表登录成功
	 * 				-1: no such customer, 0: wrong password, 1: success
	 */
	public int consumersLogin(Consumers consumers);
	
	/**
	 * @Title: addConsumers
	 * @Description: 会员注册 customer registration
	 * @param:  consumers 对象  
	 * @return: int -1表示注册失败 ，0表示会员email已存在，1表示注册成功   -1: registration failed
	 */
	public int addConsumers(Consumers consumers);
	
	/**
	 * @Title: getConsumersByLoginName
	 * @Description: 根据登录名获取Consumers对象 get Consumers By Login Name
	 * @param:  loginName
	 * @return: Consumers类 return null if the consumer does not exist
	 */
	public Consumers getConsumersByLoginName(String loginName);
	
	/**
	 * @Title: getByLoginName
	 * @Description: 根据第三方ID获取Consumers对象
	 * @param:    mobileToken
	 * @return: Consumers
	 */
	public Consumers getConsumersByOtherCode(String otherCode,int loginType);
	
	/**
	 * @Title: updateConsumers
	 * @Description: 用户信息修改
	 * @param:    consumers
	 * @return: int -1修改失败 1修改成功
	 */
	public int updateConsumers(ConsumersApiModel consumersApiModel);
	
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
	 * @Title: getConsumersByEmail
	 * @Description: 判断email是否存在
	 * @param:    email
	 * @return: 1：存在邮箱地址 -1：不存在邮箱地址
	 */
	public int getConsumersByEmail(String email);
	
	/**
	 * @Title: updateConsumersPassword
	 * @Description: 根据email修改密码
	 * @param:    consumers
	 * @return:  1：修改成功 -1：修改失败
	 */
	public int updateConsumersPassword(ConsumersApiModel consumersApiModel);
	
	/**
	 * @Title: saveTokenAndType
	 * @Description: 用户登录保存token和type
	 * @param:    consumers
	 * @return: Consumers
	 */
	public long saveTokenAndType(Consumers consumers);
	
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
	 * @Title: consumersLoginByToken
	 * @Description: 第三方登录
	 * @param:  consumers 对象  
	 * @return: int -1表示token不存在，1代表登录成功 
	 */
	public int consumersLoginByToken(Consumers consumers);
	
	/**
	 * @Title: resetPassword
	 * @Description: 用户设置新密码
	 * @param: String newPassword
	 * @param: String email
	 * @return int  
	 */
	public int resetPassword(String newPassword, String email);
	
	/**
	 * @Title: getShortcutMenu
	 * @Description:根据用户id获取点过餐的餐厅集合
	 * @param:    
	 * @return: List<ViewConsumerClassifitionApiModel>
	 */
	public List<ViewConsumerClassifitionApiModel> getShortcutMenu(String consumerUuid,Integer type);
	
	/**
	 * @Title: updateConsumersForNomme
	 * @Description: 修改平台用户的信息
	 * @param:    Consumers consumers
	 * @return: int -1失败 1成功
	 */
	public int updateConsumersForNomme(Consumers consumers);
	
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
