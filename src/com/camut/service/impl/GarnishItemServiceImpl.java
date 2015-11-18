package com.camut.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.GarnishItemDao;
import com.camut.model.GarnishItem;
import com.camut.service.GarnishItemService;

@Service
public class GarnishItemServiceImpl implements GarnishItemService {

	@Autowired GarnishItemDao garnishItemDao;
	
	/**
	 * @Title: getGarnishItemById
	 * @Description: 根据id获取对象
	 * @param:    id
	 * @return: GarnishItem
	 */
	@Override
	public GarnishItem getGarnishItemById(long id) {
		return garnishItemDao.getGarnishItemById(id);
	}
	
}
