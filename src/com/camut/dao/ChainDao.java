/**
 * 
 */
package com.camut.dao;

import java.util.List;

import com.camut.model.Chain;

/**
 * @ClassName ChainDao.java
 * @author wangpin
 * @createtime Jun 10, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface ChainDao {

	/**
	 * @Title: getAllChain
	 * @Description: 获取所有的连锁店信息
	 * @param:    
	 * @return: List<Chain>
	 */
	public List<Chain> getAllChain();
	
	/**
	 * @Description: 通过id获取Chain对象 
	 * @param @param id
	 * @param @return   
	 * @return Chain  
	 * @author wj
	 * @date 2015-6-11
	 */
	public Chain getById(long id);
	
	/**
	 * @Description:修改chain 
	 * @param @param chain
	 * @param @return   
	 * @return int  
	 * @author wj
	 * @date 2015-6-11
	 */
	public int updateChain(Chain chain);
	
	/**
	 * @Description: 新增Chain
	 * @param @param chain
	 * @param @return   
	 * @return int  
	 * @author wj
	 * @date 2015-6-11
	 */
	public int addChain(Chain chain);
	
	/**
	 * @Title: deleteChain
	 * @Description: 删除Chain
	 * @param: @param chain
	 * @return int  
	 */
	public int deleteChain(Chain chain);
}
