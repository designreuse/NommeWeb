/**
 * 
 */
package com.camut.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.camut.dao.ChainDao;
import com.camut.model.Chain;

/**
 * @ClassName ChainDaoImpl.java
 * @author wangpin
 * @createtime Jun 10, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class ChainDaoImpl extends BaseDao<Chain> implements ChainDao {

	/**
	 * @Title: getAllChain
	 * @Description: 获取所有的连锁店信息
	 * @param:    
	 * @return: List<Chain>
	 */
	@Override
	public List<Chain> getAllChain() {
		String hql = "from Chain";
		return this.find(hql);
	}
	
	/**
	 * @Description: 通过id获取Chain对象 
	 * @param id
	 * @return Chain  
	 * @author wj
	 * @date 2015-6-11
	 */
	@Override
	public Chain getById(long id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		String hql = "from Chain c where c.id=:id";
		Chain chain = this.get(hql, map);
		return chain;
	}
	
	/**
	 * @Description:修改chain 
	 * @param chain
	 * @return int  
	 * @author wj
	 * @date 2015-6-11
	 */
	public int updateChain(Chain chain){
		try{
			this.update(chain);
			return 1;
		}catch(Exception e){
			return -1;
		}
	}
	
	/**
	 * @Description: 新增Chain
	 * @param  chain
	 * @return int  
	 * @author wj
	 * @date 2015-6-11
	 */
	public int addChain(Chain chain){
		try{
			this.save(chain);
			return 1;
		}catch(Exception e){
			return -1;
		}
	}
	
	/**
	 * @Title: deleteChain
	 * @Description: 删除Chain
	 * @param: @param chain
	 * @return int  
	 */
	public int deleteChain(Chain chain){
		try{
			this.delete(chain);
			return 1;
		}catch(Exception e){
			return -1;
		}
	}
	

}
