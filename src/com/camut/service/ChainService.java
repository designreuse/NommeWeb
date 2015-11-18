/**
 * 
 */
package com.camut.service;

import java.util.List;

import com.camut.model.Chain;
import com.camut.pageModel.PageMessage;

/**
 * @ClassName ChainService.java
 * @author wangpin
 * @createtime Jun 10, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface ChainService {
	
	/**
	 * @Title: getAllChain
	 * @Description: 获取所有的连锁店信息
	 * @param:    
	 * @return: List<Chain>
	 */
	public List<Chain> getAllChain();
	
	/**
	 * @Description: 增加修改连锁店名称时校验名称是否被使用了
	 * @param @param chain
	 * @param @return   
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-11
	 */
	public PageMessage chainNameExist(Chain chain);
	
	/**
	 * @Description: 增加连锁店名称
	 * @param @param pageAreas
	 * @param @return   
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-9
	 */
	public PageMessage addChain(Chain newChain);
	
	/**
	 * @Description: 修改连锁店名称
	 * @param @param pageAreas
	 * @param @return   
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-9
	 */
	public PageMessage modifyChain(Chain chain);
	
	/**
	 * @Title: deleteChain
	 * @Description: 删除连锁店名称
	 * @param: long id
	 * @return PageMessage  
	 */
	public PageMessage deleteChain(long id);
	
	
}
