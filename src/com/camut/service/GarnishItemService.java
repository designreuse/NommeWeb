package com.camut.service;

import com.camut.model.GarnishItem;

public interface GarnishItemService {
	
	/**
	 * @Title: getGarnishItemById
	 * @Description: 根据id获取对象
	 * @param:    id
	 * @return: GarnishItem
	 */
	public GarnishItem getGarnishItemById(long id);
	
}
