package com.camut.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.CartDishGarnishDao;
import com.camut.dao.CartHeaderDao;
import com.camut.dao.CartItemDao;
import com.camut.dao.DiscountDao;
import com.camut.dao.DishDao;
import com.camut.dao.DishGarnishDao;
import com.camut.dao.DistancePriceDao;
import com.camut.dao.RestaurantsDao;
import com.camut.dao.ViewRestaurantDao;
import com.camut.framework.constant.GlobalConstant;
import com.camut.model.CartDishGarnish;
import com.camut.model.CartHeader;
import com.camut.model.CartItem;
import com.camut.model.Discount;
import com.camut.model.Restaurants;
import com.camut.model.api.CartApiModel;
import com.camut.model.api.CartHeaderLoginApiModel;
import com.camut.pageModel.PageCartHeader;
import com.camut.service.CartHeaderService;
import com.camut.utils.StringUtil;

@Service
public class CartHeaderServiceImpl implements CartHeaderService {

	@Autowired private CartHeaderDao cartHeaderDao;
	@Autowired private DistancePriceDao distancePriceDao;
	@Autowired private ViewRestaurantDao viewRestaurantDao;
	@Autowired private DishDao dishDao;
	@Autowired private CartDishGarnishDao cartDishGarnishDao;
	@Autowired private DishGarnishDao dishGarnishDao;
	@Autowired private DiscountDao discountDao;
	@Autowired private RestaurantsDao restaurantsDao;
	@Autowired private CartItemDao cartItemDao;
	
	/**
	 * @Title: getCartHeaderByMobileToken
	 * @Description:根据设备号查询购物车头
	 * @param:    mobileToken
	 * @return: CartHeaderLoginApiModel
	 */
	@Override
	public CartHeaderLoginApiModel getCartHeaderByMobileToken(String mobileToken) {
		if (mobileToken != null && mobileToken.length() > 0) {
			CartHeaderLoginApiModel chlam = new CartHeaderLoginApiModel();
			CartHeader cartHeader = cartHeaderDao.getCartHeaderByMobileToken(mobileToken);
			if(cartHeader != null){
				if (cartHeader.getConsumerId() != null) {
					chlam.setConsumerId(cartHeader.getConsumerId());
				} else {
					chlam.setConsumerId(0);
				}
				chlam.setOrderType(cartHeader.getOrderType());
				return chlam;
			}
			return chlam;
		}
		return null;
	}

	/**
	 * @Title: getCartHeader
	 * @Description:根据设备号查询对象
	 * @param:    mobileToken
	 * @return: CartHeader
	 */
	@Override
	public CartHeader getCartHeader(String mobileToken) {
		return cartHeaderDao.getCartHeaderByMobileToken(mobileToken);
	}
	
	/**
	 * @Title: deleteCartHeaderByMobileToken
	 * @Description:删除购物车信息
	 * @param:    String
	 * @return: CartHeader
	 */
	@Override
	public int deleteCartHeaderByMobileToken(String mobileToken) {
		if(mobileToken != null && mobileToken.length() > 0){
			return cartHeaderDao.deleteCartHeaderByMobileToken(mobileToken);
		}
		return -1;
	}
	
	/**
	 * @Title: deleteCartHeaderById
	 * @Description:删除购物车信息
	 * @param:    String
	 * @return: CartHeader
	 */
	@Override
	public int deleteCartHeader(CartHeader cartHeader) {
		if(cartHeader!=null){
			return cartHeaderDao.deleteCartHeader(cartHeader);
		}
		return -1;
	}

	/**
	 * 删除购物车菜品信息
	 * @param mobileToken
	 * @param dishId
	 * @return
	 */
	@Override
	public int deleteCartDish(String mobileToken, int dishId, Integer consumerId) {
		// 获取cartheader
		CartHeader cartHeader = null;// cartHeaderDao.getCartHeaderByMobileToken(mobileToken,consumerId);
		if (consumerId != null) {
			cartHeader = cartHeaderDao.getCartHeaderByConsumerId(consumerId);
		} else {
			cartHeader = cartHeaderDao.getCartHeaderByMobileToken(mobileToken);
		}
		if (cartHeader != null) {
			double fee = 0;
			for (CartItem cartItem : cartHeader.getCartItems()) {
				if (cartItem.getDishId() == dishId) {
					fee = cartItem.getUnitprice();
					for (CartDishGarnish subItem : cartItem.getCartDishGarnishs()) {
						int i = cartDishGarnishDao.deleteCartDishGarnish(subItem.getId().getGarnishItemId(), subItem.getId().getCartItemId());
						if (i != 1) {
							return -1;
						}
					}
					break;
				}
			}

			// 删除cartitem
			int i = cartHeaderDao.deleteCartDish(cartHeader.getId(), dishId);
			// 总价减去删除菜品的价格
			if (i == 1) {
				cartHeader.setDishFee(StringUtil.convertLastDouble(cartHeader.getDishFee() - fee));
				//判断之前已经选择的优惠
				boolean flag = true;
				if (cartHeader.getDiscountId()!=null) {
					Discount discount = discountDao.getDiscount(cartHeader.getDiscountId());
					//没有达到消费金额
					if (cartHeader.getDishFee()<discount.getConsumePrice()) {
						cartHeader.setDiscountId(null);
						//送菜的优惠要删除赠送的菜品
						if (discount.getType()==GlobalConstant.DISCOUNT_TYPE_3) {
							for(CartItem cartItem:cartHeader.getCartItems()){
								if (cartItem.getDishId()==discount.getDishId()&& cartItem.getUnitprice()==0) {
									int j = cartItemDao.deleteById(cartItem.getId());
									if (j!=1) {
										flag = false;
									}
									break;
								}
							}
						}
					}
				}
				if (flag) {
					return cartHeaderDao.updateCartHeader(cartHeader);
				}
			}
		}

		return -1;
	}
	
	/**
	 * @Title: updateCartHeader
	 * @Description: 修改购物车头信息
	 * @param: @param cartHeader
	 * @param: @return
	 * @return int  
	 */
	public int updateCartHeader(CartHeader cartHeader){
		if(cartHeader!=null){
			int temp = cartHeaderDao.updateCartHeader(cartHeader);
			return temp;
		}
		return -1;
	}
	

	@Override
	public CartApiModel reCalcCost(String mobileToken, double restaurantLat,
			double restaurantLng, long discountId, long restaurantId,Integer consumerId) {
		
		Discount discount=discountDao.getDiscount(discountId);
		CartApiModel cartApiModel=cartHeaderDao.getCartInfoForSql(mobileToken,consumerId);
		//查看是否有送菜
		cartHeaderDao.deleteFreeCartItem(consumerId);
		
		//计算折扣后的总价
		if(discount.getType()==GlobalConstant.DISCOUNT_TYPE_1){
			cartApiModel.setTotal(StringUtil.convertLastDouble(cartApiModel.getTotal()-discount.getPrice()));
			cartApiModel.setDiscount(discount.getPrice());
			CartHeader cartHeader = cartHeaderDao.getCartHeaderByConsumerId(consumerId);
			if (cartHeader!=null) {
				cartHeader.setDiscountId((int)discountId);
				cartHeaderDao.updateCartHeader(cartHeader);
			}
		}else if(discount.getType()==GlobalConstant.DISCOUNT_TYPE_2){
			cartApiModel.setDiscount(StringUtil.convertLastDouble(cartApiModel.getTotal()*(discount.getDiscount()/100)));
			cartApiModel.setTotal(StringUtil.convertLastDouble(cartApiModel.getTotal()-cartApiModel.getDiscount()));			
			CartHeader cartHeader = cartHeaderDao.getCartHeaderByConsumerId(consumerId);
			if (cartHeader!=null) {
				cartHeader.setDiscountId((int)discountId);
				cartHeaderDao.updateCartHeader(cartHeader);
			}
			
		}else{
			CartHeader cartHeader = cartHeaderDao.getCartHeaderByConsumerId(consumerId);
			cartHeader.setDiscountId((int)discountId);
			CartItem cartItem=new CartItem();
			cartItem.setcartHeader(cartHeader);
			cartItem.setDishId(discount.getDishId());
			cartItem.setNum(1);
			cartItem.setUnitprice(0);
			cartItemDao.addCartItem(cartItem);
		}
		
		return cartApiModel;
	}


	/**
	 * @Title: getPageCartHeaderByMobileToken
	 * @Description: 通过设备号获取PageCartHeader
	 * @param: @param mobileToken
	 * @param: @return
	 * @return PageCartHeader  
	 */
	/*public PageCartHeader getPageCartHeaderByMobileToken(String mobileToken){
		CartHeader cartHeader = cartHeaderDao.getCartHeaderByMobileToken(mobileToken);
		if(cartHeader!=null){
			PageCartHeader pageCartHeader = new PageCartHeader();
			pageCartHeader.setId(cartHeader.getId());
			pageCartHeader.setMobileToken(cartHeader.getMobileToken());
			pageCartHeader.setDishFee(cartHeader.getDishFee());
			pageCartHeader.setLogistics(cartHeader.getLogistics());
			pageCartHeader.setTax(cartHeader.getTax());
			pageCartHeader.setTotal(cartHeader.getTotal());
			
			Set<CartItem> cartItemSet = cartHeader.getCartItems();
			List<PageCartItem> cartItemList = new ArrayList<PageCartItem>();
			if(cartItemSet.size()>0){
				String dishIds = "";
				for(CartItem cartItem : cartItemSet){
					dishIds += cartItem.getId();
					PageCartItem pageCartItem = new PageCartItem();
					pageCartItem.setDishId(cartItem.getDishId());
					pageCartItem.setUnitprice(cartItem.getUnitprice());//菜品配菜只和的单价
					pageCartItem.setNum(cartItem.getNum());
					pageCartItem.set
					BeanUtils.copyProperties(cartItem, pageCartItem);
				}
				
				
			}
			pageCartHeader.setCartItems(cartItemList);
			//------------------------------------
			double totalPrice=0;
			if(cartItemSet.size()>0){}
			for (CartItem cartItem : cartItemSet){//遍历购物车条目
				double aDishFee = 0;//存储一个购物车条目菜品的价格
				double aDishGarnishFee = 0;//存储一个菜品下的所有配菜金额
				PageCartItem pageCartItem = new PageCartItem();
				pageCartItem.setDishId(cartItem.getDishId());
				pageCartItem.setNum(cartItem.getNum());
				
				//BeanUtils.copyProperties(cartItem, cartItemApiModel);
				Dish dish = dishDao.getDishById((long)cartItem.getDishId());
				if(dish!=null){
					pageCartItem.setDishName(dish.getEnName());
					pageCartItem.setUnitprice(dish.getPrice());
					pageCartItem.setPhotoUrl(dish.getPhotoUrl());
					pageCartItem.setCartItemId(cartItem.getId().intValue());
					aDishFee = dish.getPrice();
					//totalPrice += dish.getPrice();//*cartItem.getNum(); 
				}
				//Set<CartDishGarnishApiModel> cartDishGarnishApiModels = new HashSet<CartDishGarnishApiModel>();
				// 遍历购物车配菜表
				for (CartDishGarnish cartDishGarnish : cartItem.getCartDishGarnishs()) {
					//CartDishGarnishApiModel cartDishGarnishApiModel = new CartDishGarnishApiModel();
					//cartDishGarnishApiModel.setCount(cartDishGarnish.getGarnishNum());
					//cartDishGarnishApiModel.setGarnishItemId(cartDishGarnish.getGarnishItem().getId().intValue());
					//cartDishGarnishApiModel.setGarnishName(cartDishGarnish.getGarnishItem().getGarnishName());
					//cartDishGarnishApiModels.add(cartDishGarnishApiModel);
					DishGarnish dishGarnish = dishGarnishDao.getDishGarnishByDishIdAndGarnishItemId(cartItem.getDishId(), cartDishGarnish.getCartItem().getId());
					if(dishGarnish!=null){
						aDishGarnishFee += dishGarnish.getAddprice()*cartDishGarnish.getGarnishNum();
						//totalPrice += dishGarnish.getAddprice()*cartDishGarnish.getGarnishNum();
					}
				}
				aDishFee = (aDishFee + aDishGarnishFee) * cartItem.getNum();
				//得到购物车的所有菜品和配菜的总价
				totalPrice += aDishFee;
				//cartItemApiModel.setaDishTotalFee(aDishFee);
				//cartItemApiModel.setSubItem(cartDishGarnishApiModels);
				//cartItemApiModels.add(cartItemApiModel);
			}
			//cartHeaderApiModel.setTotal(totalPrice);
			//cartHeaderApiModel.setItem(cartItemApiModels);
		}
		return null;
	}*/


	
	/**
	 * @Title: getCatrHeaderByConsumerId
	 * @Description: 通过用户id获取购物车
	 * @param: @param consumerId
	 * @param: @return
	 * @return CartHeader  
	 */
	public CartHeader getCatrHeaderByConsumerId(int consumerId){
		CartHeader cartHeader = cartHeaderDao.getCartHeaderByConsumerId(consumerId);
		return cartHeader;
	}

	/**
	 * 根据用户id查找对象
	 * @param mobileToken  consumerId
	 * @return
	 */
	@Override
	public CartHeader getCartHeaderByConsumerId(int consumerId) {
		if(consumerId > 0){
			return cartHeaderDao.getCartHeaderByConsumerId(consumerId);
		}
		return null;
	}


	/**
	 * 将token保存的已有的用户
	 * @param mobileToken  consumerId
	 * @return
	 */
	@Override
	public void saveTokenToConsumerId(CartHeader cartHeader) {
		if(cartHeader != null){
			cartHeaderDao.saveTokenToConsumerId(cartHeader);
		}
	}
	
	/**
	 * @Title: getCartHeaderById
	 * @Description: 通过id获取购物车头
	 * @param: @param cartHeaderId
	 * @param: @return
	 * @return CartHeader  
	 */
	public CartHeader getCartHeaderById(long cartHeaderId){
		return cartHeaderDao.getCartHeaderById(cartHeaderId);
	}

	/**
	 * 根据用户id查找对象
	 * @param mobileToken  consumerId
	 * @return
	 */
	@Override
	public PageCartHeader getPageCartHeaderByConsumerId(int consumerId) {
		CartHeader cartHeader = cartHeaderDao.getCartHeaderByConsumerId(consumerId);
		if (cartHeader!=null) {
			PageCartHeader pageCartHeader = new PageCartHeader();
			Restaurants restaurants = restaurantsDao.getRestaurantsById(cartHeader.getRestaurantId());
			BeanUtils.copyProperties(cartHeader, pageCartHeader);
			pageCartHeader.setRestaurantName(restaurants.getRestaurantName());
			return pageCartHeader;
		}
		return null;
	}
	
	/**
	 * @Title: getWebCartHeaderByConsumerId
	 * @Description: 获取用于web页面显示
	 * @param: @param consumerId
	 * @param: @return
	 * @return CartHeader  
	 */
	@Override
	public CartHeader getWebCartHeaderByConsumerId(int consumerId){
		CartHeader cartHeader = cartHeaderDao.getWebCartHeaderByConsumerId(consumerId);
		return cartHeader;
	}

}
