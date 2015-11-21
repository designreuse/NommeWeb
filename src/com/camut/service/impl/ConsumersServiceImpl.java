package com.camut.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.ClassificationDao;
import com.camut.dao.ConsumersDao;
import com.camut.dao.ViewConsumerClassificationDao;
import com.camut.framework.constant.GlobalConstant;
import com.camut.model.Classification;
import com.camut.model.Consumers;
import com.camut.model.ViewConsumerClassification;
import com.camut.model.api.ConsumersApiModel;
import com.camut.model.api.ViewConsumerClassifitionApiModel;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageStatementConsumer;
import com.camut.service.ConsumersService;
import com.camut.utils.MD5Util;
import com.camut.utils.StringUtil;

/**
 * @ServiceImpl ConsumersServiceImpl.java
 * @author wangpin
 * @createtime May 26, 2015
 * @author
 * @updateTime
 * @memo
 */
@Service
public class ConsumersServiceImpl implements ConsumersService {

	@Autowired
	private ConsumersDao consumersDao;// 自动注入consumersDao
	
	@Autowired
	private ViewConsumerClassificationDao viewConsumerClassificationDao;
	
	@Autowired
	private ClassificationDao classificationDao;

	public Consumers login(Consumers consumers){
		if (consumers.getEmail() != null && consumers.getEmail().length() > 0) {
			Consumers consumersRes = consumersDao.getConsumersByLoginName(consumers.getEmail());			
			if (consumersRes != null && consumersRes.getPassword().equals(MD5Util.md5(consumers.getPassword()))) {
				consumersDao.updateLastLoginDate(consumersRes);// 修改最后一次登陆时间
				return consumersRes;
			}
		}		
		return null;
	}
	
	/**
	 * @Description: 会员登录方法 customer login
	 * @param: consumers
	 * @return: int -1表示用户名不存在，0表示密码错误，1代表登录成功 
	 * -1: no such customer, 0: wrong,password, 1: success
	 */
	@Override
	public int consumersLogin(Consumers consumers) {
		if (consumers.getEmail() != null && consumers.getEmail().length() > 0) {
			Consumers consumers2 = consumersDao.getConsumersByLoginName(consumers.getEmail());
			if (consumers2 != null) {//用户名存在
				if (consumers2.getPassword().equals(MD5Util.md5(consumers.getPassword()))) {
					consumersDao.updateLastLoginDate(consumers2);// 修改最后一次登陆时间
					return 1;
				} else {//密码错误
					return 0;
				}
			}
		}
		return -1;
	}

	/**
	 * @Title: addConsumers
	 * @Description: 会员注册 customer registration
	 * @param: consumers 对象
	 * @return: int -1表示注册失败 ，0表示会员email已存在，1表示注册成功   
	 * 			int -1: failed, 0:exist, 1:success
	 */
	@Override
	public int addConsumers(Consumers consumers) {
		if (consumersDao.getConsumersByLoginName(consumers.getEmail()) != null) {
			return 0;
		}
		return consumersDao.addConsumers(consumers);
	}

	/**
	 * @Title: getConsumersByLoginName
	 * @Description: 根据登录名获取Consumers对象 get Consumers By Login Name
	 * @param:  loginName
	 * @return: Consumers类 return null if the consumer does not exist
	 */
	@Override
	public Consumers getConsumersByLoginName(String loginName) {
		return consumersDao.getConsumersByLoginName(loginName);
	}

	/**
	 * @Title: getByLoginName
	 * @Description: 根据第三方ID获取Consumers对象
	 * @param:    mobileToken
	 * @return: Consumers
	 */
	@Override
	public Consumers getConsumersByOtherCode(String otherCode,int loginType) {
		if(otherCode != null && otherCode.length() > 0){
			return consumersDao.getConsumersByOtherCode(otherCode,loginType);
		}
		return null;
	}

	/**
	 * @Title: updateConsumers
	 * @Description: 用户信息修改
	 * @param:    consumers
	 * @return: int -1修改失败 1修改成功
	 */
	@Override
	public int updateConsumers(ConsumersApiModel consumersApiModel) {
		if(consumersApiModel != null){
			Consumers consumers2 = consumersDao.getConsumersByUuid(consumersApiModel.getConsumerUuid());
			if(consumers2 != null){
				consumers2.setFirstName(consumersApiModel.getFirstName());
				consumers2.setLastName(consumersApiModel.getLastName());
				consumers2.setEmail(consumersApiModel.getEmail());
				consumers2.setPhone(consumersApiModel.getPhone());
				if(StringUtil.isNotEmpty(consumersApiModel.getPassword()) &&
				   StringUtil.isNotEmpty(consumersApiModel.getNewpassword())){
					//判断用户输入的旧密码是否正确
					if(consumers2.getPassword().equals(MD5Util.md5(consumersApiModel.getPassword()))){
						consumers2.setPassword(MD5Util.md5(consumersApiModel.getNewpassword()));
					} else {
						return -1;
					}
				}
				return consumersDao.updateConsumers(consumers2);
			}
		}
		return -1;
	}
	
	/**
	 * @Title: getConsumersById
	 * @Description: 通id查找用户
	 * @param:    id
	 * @return: Consumers
	 */
	@Override
	public Consumers getConsumersById(long id) {
		if(id != 0){
			return consumersDao.getConsumersById(id);
		}
		return null;
	}

	/**
	 * @Title: getConsumersByUuid
	 * @Description: 通id查找用户
	 * @param:    id
	 * @return: Consumers
	 */
	@Override
	public Consumers getConsumersByUuid(String consumerUuid) {
		if(StringUtil.isNotEmpty(consumerUuid)){
			return consumersDao.getConsumersByUuid(consumerUuid);
		}
		return null;
	}
	
	/**
	 * @Title: forgetPassword
	 * @Description: 判断email是否存在
	 * @param:    email
	 * @return: 1：存在邮箱地址 -1：不存在邮箱地址
	 */
	@Override
	public int getConsumersByEmail(String email) {
		if (consumersDao.getConsumersByLoginName(email) != null) {
			return 1;
		}
		return -1;
	}

	/**
	 * @Title: updateConsumersPassword
	 * @Description: 根据email修改密码
	 * @param:    consumers
	 * @return:  1：修改成功 -1：修改失败
	 */
	@Override
	public int updateConsumersPassword(ConsumersApiModel consumersApiModel) {
		if(consumersApiModel.getEmail() != null && consumersApiModel.getEmail().length() > 0){
			Consumers consumers2 = consumersDao.getConsumersByLoginName(consumersApiModel.getEmail());
			if(consumers2 != null){
				consumers2.setStatus(0);
				consumers2.setPassword(MD5Util.md5(consumersApiModel.getNewpassword()));
				return consumersDao.updateConsumers(consumers2);
			}
		}
		return -1;
	}

	/**
	 * @Title: saveTokenAndType
	 * @Description: 用户登录保存token和type
	 * @param:    consumers
	 * @return: Consumers
	 */
	@Override
	public String saveTokenAndType(Consumers consumers) {
		return consumersDao.saveTokenAndType(consumers);
	}

	/**
	 * @Title: updateTokenAndType
	 * @Description: 修改token和type
	 * @param:    consumers
	 * @return: Consumers
	 */
	@Override
	public Consumers updateTokenAndType(Consumers consumers) {
		return consumersDao.updateTokenAndType(consumers);
	}


	/**
	 * @Title: addConsumerForNomme
	 * @Description: 增加会员
	 * @param:    Consumers
	 * @return: int -1增加失败，1增加成功
	 */
	@Override
	public int addConsumerForNomme(Consumers consumers) {
		if(consumers!=null){
			consumers.setStatus(0);
			return consumersDao.addConsumerForNomme(consumers);
		}
		return -1;
	}
	

	/**
	 * @Title: consumersLoginByToken
	 * @Description: 第三方登录
	 * @param:  consumers 对象  
	 * @return: int -1表示token不存在，1代表登录成功 
	 */
	@Override
	public int consumersLoginByToken(Consumers consumers) {
		if (consumers.getOtherCode() != null && consumers.getOtherCode().length() > 0 && consumers.getLoginType() != null && consumers.getLoginType() > 0) {
			Consumers consumers2 = consumersDao.getConsumersByOtherCode(consumers.getOtherCode(), consumers.getLoginType());
			if (consumers2 != null) {//用户名存在
				return 1;
			}
		}
		return -1;
	}
	
	/**
	 * @Title: resetPassword
	 * @Description: 用户设置新密码
	 * @param: String newPassword
	 * @param: String email
	 * @return int  
	 */
	public int resetPassword(String newPassword, String email){
		int temp = 0;
		Consumers consumers = consumersDao.getConsumersByLoginName(email);
		if(consumers!=null){
			consumers.setPassword(MD5Util.md5(newPassword));
			// -1修改失败 1修改成功
			temp = consumersDao.updateConsumers(consumers);
		}else{
			temp = -1;
		}
		return temp;
	}

	/**
	 * @Title: getShortcutMenu
	 * @Description:根据用户id获取点过餐的餐厅集合
	 * @param:    
	 * @return: List<ViewConsumerClassifitionApiModel>
	 */
	@Override
	public List<ViewConsumerClassifitionApiModel> getShortcutMenu(String consumerUuid,Integer type) {
		List<ViewConsumerClassification> list =null;
		if(StringUtil.isNotEmpty(consumerUuid)){
			list = viewConsumerClassificationDao.getClassificationNames(consumerUuid);
		}
		List<ViewConsumerClassifitionApiModel> apiModels = new ArrayList<ViewConsumerClassifitionApiModel>();
		int num = 4;
		if(type!=null && type==3){//当是网站调用的时候需要显示5个
			num = 5;
		}
		if (list!=null&&list.size()>0) {
			if(list.size()>=num){//够展示的4个
				for(int i=0;i<num;i++){
					ViewConsumerClassifitionApiModel apiModel = new ViewConsumerClassifitionApiModel();
					apiModel.setClassification(list.get(i).getClassificationName());
					apiModel.setClassificationId(list.get(i).getClassificationId());
					if (type!=null && type==2) {//android
						apiModel.setImageUrl(GlobalConstant.DOMAIN_NAME + list.get(i).getAndroidImageUrl());
						apiModel.setHoverImageUrl(GlobalConstant.DOMAIN_NAME + list.get(i).getAndroidHoverImageUrl());
					}else if(type!=null && type==3){//web
						apiModel.setImageUrl(GlobalConstant.DOMAIN_NAME + list.get(i).getWebImageUrl());
						apiModel.setClassificationId(list.get(i).getClassificationId());
						apiModel.setClassification(list.get(i).getClassificationName());
					}else{//ios
						apiModel.setImageUrl(GlobalConstant.DOMAIN_NAME + list.get(i).getIosImageUrl());
						apiModel.setHoverImageUrl(GlobalConstant.DOMAIN_NAME + list.get(i).getIosHoverImageUrl());
					}
					//apiModel.setImageUrl(list.get(i).getImageUrl());
					apiModels.add(apiModel);
				}
			}
			else{
				//不够四个
				for(ViewConsumerClassification viewConsumerClassification:list){
					ViewConsumerClassifitionApiModel apiModel = new ViewConsumerClassifitionApiModel();
					apiModel.setClassification(viewConsumerClassification.getClassificationName());
					apiModel.setClassificationId(viewConsumerClassification.getClassificationId());
					//apiModel.setImageUrl(viewConsumerClassification.getImageUrl());
					if (type!=null && type==2) {//android
						apiModel.setImageUrl(GlobalConstant.DOMAIN_NAME + viewConsumerClassification.getAndroidImageUrl());
						apiModel.setHoverImageUrl(GlobalConstant.DOMAIN_NAME + viewConsumerClassification.getAndroidHoverImageUrl());
					}else if(type!=null && type==3){//web
						apiModel.setImageUrl(GlobalConstant.DOMAIN_NAME + viewConsumerClassification.getWebImageUrl());
						apiModel.setClassificationId(viewConsumerClassification.getClassificationId());
						apiModel.setClassification(viewConsumerClassification.getClassificationName());
					}else{
						apiModel.setImageUrl(GlobalConstant.DOMAIN_NAME + viewConsumerClassification.getIosImageUrl());
						apiModel.setHoverImageUrl(GlobalConstant.DOMAIN_NAME + viewConsumerClassification.getIosHoverImageUrl());
					}
					apiModels.add(apiModel);
				}
				List<Classification> list1 = classificationDao.getAllClassification();
				out:
				for(Classification classification:list1){
					for(ViewConsumerClassifitionApiModel v:apiModels){
						if(classification.getId()==v.getClassificationId()){//分类重复，跳出
							continue out;
						}
					}
					ViewConsumerClassifitionApiModel apiModel = new ViewConsumerClassifitionApiModel();
					apiModel.setClassification(classification.getClassificationName());
					apiModel.setClassificationId(classification.getId().intValue());
					//apiModel.setImageUrl(classification.getImageUrl());
					if (type!=null && type==2) {//android
						apiModel.setImageUrl(GlobalConstant.DOMAIN_NAME + classification.getAndroidImageUrl());
						apiModel.setHoverImageUrl(GlobalConstant.DOMAIN_NAME + classification.getAndroidHoverImageUrl());
					}else if(type!=null && type==3){//web
						apiModel.setImageUrl(GlobalConstant.DOMAIN_NAME + classification.getWebImageUrl());
						apiModel.setClassificationId(classification.getId().intValue());
						apiModel.setClassification(classification.getClassificationName());
					}else{
						apiModel.setImageUrl(GlobalConstant.DOMAIN_NAME + classification.getIosImageUrl());
						apiModel.setHoverImageUrl(GlobalConstant.DOMAIN_NAME + classification.getIosHoverImageUrl());
					}
					apiModels.add(apiModel);
					if(apiModels.size()==num){
						break;
					}
				}
			}
			
		}
		else{
			List<Classification> list1 = classificationDao.getAllClassification();
			if(list1!=null && list1.size()>0){
				for(int i=0;i<num;i++){
					ViewConsumerClassifitionApiModel apiModel = new ViewConsumerClassifitionApiModel();
					apiModel.setClassification(list1.get(i).getClassificationName());
					apiModel.setClassificationId(list1.get(i).getId().intValue());
					//apiModel.setImageUrl(list1.get(i).getImageUrl());
					if (type!=null && type==2) {//android
						apiModel.setImageUrl(GlobalConstant.DOMAIN_NAME + list1.get(i).getAndroidImageUrl());
						apiModel.setHoverImageUrl(GlobalConstant.DOMAIN_NAME + list1.get(i).getAndroidHoverImageUrl());
					}else if(type!=null && type==3){//web
						apiModel.setImageUrl(GlobalConstant.DOMAIN_NAME + list1.get(i).getWebImageUrl());
						apiModel.setClassificationId(list1.get(i).getId().intValue());
						apiModel.setClassification(list1.get(i).getClassificationName());
					}else{
						apiModel.setImageUrl(GlobalConstant.DOMAIN_NAME + list1.get(i).getIosImageUrl());
						apiModel.setHoverImageUrl(GlobalConstant.DOMAIN_NAME + list1.get(i).getIosHoverImageUrl());
					}
					apiModels.add(apiModel);
				}
			}
		}
		return apiModels;
	}

	/**
	 * @Title: updateConsumersForNomme
	 * @Description: 修改平台用户的信息
	 * @param:    Consumers consumers
	 * @return: int -1失败 1成功
	 */
	@Override
	public int updateConsumersForNomme(Consumers consumers) {
		if(consumers!=null){
			return consumersDao.updateConsumers(consumers);
		}
		return -1;
	}
	
	/**
	 * @Title: getConsumersTotalNo
	 * @Description:获取注册用户总数量 
	 * @param: @return
	 * @return int  
	 */
	@Override
	public int getConsumersTotalNo(){
		return consumersDao.getConsumersTotalNo();
	}
	
	/**
	 * @Title: getStatementAllConsumers
	 * @Description: 获取管理员的用户报表数据加载到表格
	 * @param: @return
	 * @return List<PageStatementConsumer>  
	 */
	@Override
	public List<PageStatementConsumer> getStatementAllConsumers(PageFilter pf){
		return consumersDao.getStatementAllConsumers(pf);
	}
	
}
