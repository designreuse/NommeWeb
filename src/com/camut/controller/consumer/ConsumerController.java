package com.camut.controller.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.camut.pageModel.PageMessage;
import com.camut.service.ConsumersFavoritesService;
import com.camut.utils.StringUtil;

@Controller("ConsumerController")
@RequestMapping("/consumer")
public class ConsumerController {
	@Autowired public ConsumersFavoritesService consumersFavoritesService;
	
	/**
	 * @Title: getFavoritesByConsumerId
	 * @Description: 通过用户通过商家id和用户id查找当前用户是否收藏了当前商家
	 * @param: @param consumerId 
	 * @param: @param restaurantId
	 * @return PageMessage
	 */
	@RequestMapping(value="existFavorites", method=RequestMethod.POST)
	@ResponseBody
	public PageMessage existFavoritesByConsumerIdAndrestaurantId(String consumerId, String restaurantId){
		PageMessage pm = new PageMessage();
		if(StringUtil.isNotEmpty(consumerId) && StringUtil.isNotEmpty(restaurantId)){
			long cId = Long.parseLong(consumerId);
			int rId = Integer.parseInt(restaurantId);
			int temp = consumersFavoritesService.existFavoritesByConsumerIdAndrestaurantId(cId,rId);
			if(temp>0){
				pm.setSuccess(true);
			}else{
				pm.setSuccess(false);
			}
		}else{
			pm.setSuccess(false);
		}
		return pm;
	}
	/**
	 * @Title: addConsumerFavorite
	 * @Description: 用户新增收藏店铺
	 * @param: @param consumerId
	 * @param: @param restaurantId
	 * @return PageMessage  
	 */
	@RequestMapping(value="addConsumerFavorite", method=RequestMethod.POST)
	@ResponseBody
	public PageMessage addConsumerFavorite(String consumerId, String restaurantId){
		PageMessage pm = new PageMessage();
		if(StringUtil.isNotEmpty(consumerId) && StringUtil.isNotEmpty(restaurantId)){
			long cId = Long.parseLong(consumerId);
			int rId = Integer.parseInt(restaurantId);
			long temp = consumersFavoritesService.addConsumerFavorite(cId,rId);
			if(temp>=0){
				pm.setSuccess(true);
			}else{
				pm.setSuccess(false);
			}
		}else{
			pm.setSuccess(false);
		}
		return pm;
	}
	/**
	 * @Title: deleteConsumerFavorite
	 * @Description: 用户删除收藏店铺
	 * @param: @param consumerId
	 * @param: @param restaurantId
	 * @return PageMessage  
	 */
	@RequestMapping(value="deleteConsumerFavorite", method=RequestMethod.POST)
	@ResponseBody
	public PageMessage deleteConsumerFavorite(String consumerId, String restaurantId){
		PageMessage pm = new PageMessage();
		if(StringUtil.isNotEmpty(consumerId) && StringUtil.isNotEmpty(restaurantId)){
			int cId = Integer.parseInt(consumerId);
			int rId = Integer.parseInt(restaurantId);
			int temp = consumersFavoritesService.deleteConsumerFavorite(cId,rId);
			if(temp>=0){
				pm.setSuccess(true);
			}else{
				pm.setSuccess(false);
			}
		}else{
			pm.setSuccess(false);
		}
		return pm;
	}
	
	
	
}
