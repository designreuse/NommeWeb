/**
 * 
 */
package com.camut.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camut.dao.ChainDao;
import com.camut.model.Chain;
import com.camut.pageModel.PageMessage;
import com.camut.service.ChainService;

/**
 * @ClassName ChainServiceImpl.java
 * @author wangpin
 * @createtime Jun 10, 2015
 * @author
 * @updateTime
 * @memo
 */
@Service
public class ChainServiceImpl implements ChainService {

	@Autowired
	private ChainDao chainDao;//自动注入ChainDao
	/*
	 * @Title: getAllChain
	 * @Description: 获取所有的连锁店信息
	 * @param:    
	 * @return: List<Chain>
	 */
	@Override
	public List<Chain> getAllChain() {
		return chainDao.getAllChain();
	}
	
	/**
	 * @Description: 增加修改连锁店名称时校验名称是否被使用了
	 * @param @param chain
	 * @param @return   
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-11
	 */
	@Override
	public PageMessage chainNameExist(Chain chain){
		PageMessage pm = new PageMessage();
		List<Chain> list = chainDao.getAllChain();
		boolean f = true;
		for (Chain ch : list) {
			long id = chain.getId();
			String chainname = chain.getChainname();
			if(chainname.equals(ch.getChainname()) && id!=ch.getId()){//如果找到有相同名称，并且id不相同时，说明名称重复了，不可使用
				f = false;
				break;
			}
		}
		pm.setSuccess(f);
		return pm;
	}
	
	/**
	 * @Description: 增加连锁店名称
	 * @param @param pageAreas
	 * @param @return   
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-9
	 */
	@Override
	public PageMessage addChain(Chain newChain){
		PageMessage pm = new PageMessage();
		Chain chain = new Chain();
		chain.setChainname(newChain.getChainname());
		int temp = chainDao.addChain(chain);
		if(temp>0){
			pm.setSuccess(true);
		}else{
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Description: 修改连锁店名称
	 * @param @param pageAreas
	 * @param @return   
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-9
	 */
	@Override
	public PageMessage modifyChain(Chain newChain){
		PageMessage pm = new PageMessage();
		long id = newChain.getId();
		Chain chain = chainDao.getById(id);
		chain.setChainname(newChain.getChainname());
		int temp = chainDao.updateChain(chain);
		if(temp>0){
			pm.setSuccess(true);
		}else{
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: deleteChain
	 * @Description: 删除连锁店名称
	 * @param: long id
	 * @return PageMessage  
	 */
	public PageMessage deleteChain(long id){
		PageMessage pm = new PageMessage();
		if(id>0){
			Chain chain = chainDao.getById(id);
			if(chain!=null){
				int temp = chainDao.deleteChain(chain);
				if(temp>0){
					pm.setSuccess(true);
				}else{
					pm.setSuccess(false);
				}
			}else{
				pm.setSuccess(false);
			}
			
		}else{
			pm.setSuccess(false);
		}
		return pm;
	}
	

}
