package com.camut.service.impl;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.ConsumersAddressDao;
import com.camut.dao.ConsumersDao;
import com.camut.dao.DiscountDao;
import com.camut.dao.DishDao;
import com.camut.dao.DishGarnishDao;
import com.camut.dao.EvaluateDao;
import com.camut.dao.OrderDao;
import com.camut.dao.OrderItemDao;
import com.camut.dao.RestaurantsDao;
import com.camut.framework.constant.GlobalConstant;
import com.camut.model.Discount;
import com.camut.model.Dish;
import com.camut.model.DishGarnish;
import com.camut.model.Evaluate;
import com.camut.model.OrderDishGarnish;
import com.camut.model.OrderHeader;
import com.camut.model.OrderItem;
import com.camut.model.Restaurants;
import com.camut.model.api.CartDishGarnishApiModel;
import com.camut.model.api.CartItemApiModel;
import com.camut.model.api.OrderDetailsApiModel;
import com.camut.service.OrderItemService;
import com.camut.utils.StringUtil;

/**
 * @dao OrderItemServiceImpl.java
 * @author zhangyunfei
 * @createtime 6 10, 2015
 * @author
 * @updateTime
 * @memo
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired private OrderItemDao orderItemDao;
	@Autowired private OrderDao orderDao;// 自动注入orderDao
	@Autowired private RestaurantsDao restaurantsDao;
	@Autowired private ConsumersDao consumersDao;
	@Autowired private ConsumersAddressDao consumersAddressDao;
	@Autowired private DishGarnishDao dishGarnishDao;
	@Autowired private DishDao dishDao;
	@Autowired private DiscountDao discountDao; 
	@Autowired private EvaluateDao evaluateDao;
	
	/**
	 * @Title: selectHistoryOrder
	 * @Description: 订单详情
	 * @param:  id
	 * @return: OrderDetailsApiModel
	 */
	@Override
	public OrderDetailsApiModel selectHistoryOrder(long id) {
		if (id != 0) {
			OrderHeader orderHeader = orderDao.getOrderById(id);
			Evaluate evaluate = evaluateDao.getEvaluateByOrderId((int)id);
			OrderDetailsApiModel odam = new OrderDetailsApiModel();	
			if(orderHeader != null){
				//判断该订单是否已经评论了0：否  1：是
				if(evaluate != null){
					odam.setIsRating(1);
				} else {
					odam.setIsRating(0);
				}
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa",Locale.CANADA);
				Restaurants restaurants = restaurantsDao.getRestaurantsByUuid(orderHeader.getRestaurantUuid());
				odam.setOrderId(id);
				odam.setRestaurantUuid(orderHeader.getRestaurantUuid());
				String rejection = "";
				if(StringUtil.isNotEmpty(orderHeader.getRejection())){
					rejection = orderHeader.getRejection();
				}
				String memo = "";
				if(StringUtil.isNotEmpty(orderHeader.getMemo())){
					memo = orderHeader.getMemo();
				}
				odam.setRejection(rejection+"\n"+memo);

				if(StringUtil.isNotEmpty(orderHeader.getMemo())){
					odam.setIntruction(orderHeader.getMemo());
				}else {
					odam.setIntruction("");
				}
				//判断订单状态
				String statusStr = "";
				switch (orderHeader.getStatus()) {
				case 0:
					statusStr = "Cancelled";//取消状态
					break;
				case 1:
					statusStr = "Unpaid";//未付款
					break;
				case 2:
					statusStr = "Paid";//已付款
					break;
				case 3:
					statusStr = "Accepted";//已接单
					break;
				case 4:
					statusStr = "Rejected";//拒绝接单
					break;
				case 6:
					statusStr = "Refund";//已退款
					break;
				case 7:
					statusStr = "Completed";//完成的订单
					break;
				case 8:
					statusStr = "Line Up";//LineUp
					break;
				case 9:
					statusStr = "Cash Order";//现金支付
					break;
				case 10:
					statusStr = "Pending";//待审核
					break;
				}
				if(StringUtil.isNotEmpty(restaurants.getRestaurantName())){
					odam.setRestaurantName(restaurants.getRestaurantName() + " (" + statusStr + ")");
				} else {
					odam.setRestaurantName("");
				}
				if(orderHeader.getNumber() != null){
					odam.setNumber(orderHeader.getNumber());
				}
				if(orderHeader.getCreatedate() != null){
					String createDate = dateFormat1.format(orderHeader.getCreatedate());
					odam.setCreatedate(createDate);
				}
				
				if(orderHeader.getOrderType() == 1){
					odam.setRestaurantAddress(orderHeader.getAddress());
				} else {
					odam.setRestaurantAddress(restaurants.getRestaurantAddress());
				}
				if(orderHeader.getAddress() != null){
					odam.setConsumersAddress(orderHeader.getAddress());
				}
				if(orderHeader.getStatus() != null){
					odam.setStatus(orderHeader.getStatus());
				}
				//如果有优惠券Id 就将优惠信息保存到返回的订单信息中
				//if(StringUtil.isNotEmpty(orderHeader.getDiscountId()+"")){
				if(orderHeader.getDiscountId()!=null && (orderHeader.getDiscountId()+"").length()>0 && Long.parseLong(orderHeader.getDiscountId()+"")>0){	
					Discount discount = discountDao.getDiscount((long)(orderHeader.getDiscountId()));
					if(discount!=null){
						odam.setDiscountText(discount.getContent());
					}
				}
				if(orderHeader.getOrderDate() != null){
					odam.setOrderDate(orderHeader.getOrderDate()+"");
					String orderDate = dateFormat1.format(orderHeader.getOrderDate());
					String discountStr = null;
					if(orderHeader.getDiscountId() != null){
						Discount discount = discountDao.getDiscount((long)(orderHeader.getDiscountId()));
						if(discount != null && StringUtil.isNotEmpty(discount.getContent())){
							discountStr = discount.getContent();
						}
					}
					if(orderHeader.getOrderType() == 3){
						int number = 0;
						if(orderHeader.getNumber() != null){
							number = orderHeader.getNumber();
						}
						String num = null;
						if(StringUtil.isNotEmpty(orderHeader.getRejection())){
							int a = orderHeader.getRejection().indexOf("Queue number");
							if(a<0){
								num = null;
							} else {
								num = orderHeader.getRejection().substring(a);
								num = num.trim();
							}
						}
						if(StringUtil.isNotEmpty(num)){
							if(StringUtil.isNotEmpty(discountStr)){
								odam.setOrderDate(orderDate+" / "+ number + " People\n" + num + "\n" + discountStr);
							} else {
								odam.setOrderDate(orderDate+" / "+ number + " People\n" + num);
							}
						} else {
							if(StringUtil.isNotEmpty(discountStr)){
								odam.setOrderDate(orderDate+" / "+ number + " People" + "\n" + discountStr);
							} else {
								odam.setOrderDate(orderDate+" / "+ number + " People");
							}
						}
					} else {
						if(StringUtil.isNotEmpty(discountStr)){
							odam.setOrderDate(orderDate + "\n" + discountStr);
						} else {
							odam.setOrderDate(orderDate);
						}
					}
				}
				if(orderHeader.getOrderType() != null){
					odam.setOrderType((int)orderHeader.getOrderType());
					if (orderHeader.getOrderType()==1) {
						odam.setOrderTypeStr("Delivery");
					}
					else if (orderHeader.getOrderType()==2) {
						odam.setOrderTypeStr("Pick-up");
					}
					else if(orderHeader.getOrderType()==3){
						if (orderHeader.getOrderItems()!=null && orderHeader.getOrderItems().size()>0) {
							odam.setOrderTypeStr("Dine in");
						}
						else{
							odam.setOrderTypeStr("Reservation");
						}
					}
				}
				//金额
				if(orderHeader.getTotal() > 0){
					odam.setTotal(StringUtil.convertLastDouble(orderHeader.getTotal()));
				}		
				if(orderHeader.getTip() > 0){
					odam.setTip(StringUtil.convertLastDouble(orderHeader.getTip()));
				}
				if(orderHeader.getLogistics() >0){
					odam.setDeliveryfee(StringUtil.convertLastDouble(orderHeader.getLogistics()));
				}
				odam.setTax(StringUtil.convertLastDouble(orderHeader.getTax()));
				odam.setAmount(StringUtil.convertLastDouble(orderHeader.getAmount()));
				
				odam.setPayment(orderHeader.getPayment());
				odam.setConsumerUuid(orderHeader.getConsumers().getUuid());
				if(orderHeader.getPhoneNumber() != null){
					odam.setConsumersIdPhone(orderHeader.getPhoneNumber());
				} else {
					odam.setConsumersIdPhone("");
				}
				/*String firstName = "";
				if(StringUtil.isNotEmpty(orderHeader.getConsumers().getFirstName())){
					firstName = orderHeader.getConsumers().getFirstName();
				}
				String lastName = "";
				if(StringUtil.isNotEmpty(orderHeader.getConsumers().getLastName())){
					lastName = orderHeader.getConsumers().getLastName();
				}*/
				if(StringUtil.isNotEmpty(orderHeader.getPeopleName())){
					odam.setConsumersName(orderHeader.getPeopleName());
				}
				if(orderHeader.getOrderItems()!=null && orderHeader.getOrderItems().size()>0){
					for (OrderItem orderItem : orderHeader.getOrderItems()) {
						CartItemApiModel item=new CartItemApiModel();
						item.setDishId(orderItem.getDishId());
						item.setNum(orderItem.getNum());
						item.setUnitprice(orderItem.getPrice());
						item.setDishRemark(orderItem.getInstruction());
						Dish dish = dishDao.getDishById(orderItem.getDishId());
						item.setDishName(dish.getEnName());
						String str="";
						if(orderItem.getOrderDishGarnishs()!=null && orderItem.getOrderDishGarnishs().size()>0){
							for (OrderDishGarnish subItem : orderItem.getOrderDishGarnishs()) {
								CartDishGarnishApiModel tmp=new  CartDishGarnishApiModel();
								tmp.setCount(subItem.getGarnishNum());
								tmp.setGarnishItemId(subItem.getId().getGarnishItemId());
								DishGarnish dishGarnish = dishGarnishDao.getDishGarnishByDishIdAndGarnishItemId(orderItem.getDishId(), tmp.getGarnishItemId());
								if(dishGarnish!=null&&dishGarnish.getGarnishItem()!=null){
									tmp.setGarnishName(dishGarnish.getGarnishItem().getGarnishName());
								}					
								if(subItem.getGarnishNum()>1){
									str+=(tmp.getGarnishName()+" ("+subItem.getGarnishNum()+")");
									str+="\n";
								}else{
									str+=tmp.getGarnishName();
									str+="\n";
								}
								item.getSubItem().add(tmp);
							}
							Collections.sort(item.getSubItem());
						}
						if(str.length()>1){
							str = str.substring(0,str.length()-1);
						}
						item.setShowDish(str);
						odam.getItem().add(item);
					}
					Collections.sort(odam.getItem());
				}
				//ChargeId
				if(StringUtil.isNotEmpty(orderHeader.getChargeId())){
					odam.setChargeId(orderHeader.getChargeId());
				}
				return odam;
			}
			return odam;
		}
		return null;
	}
	
	@Override
	public long addOrderItem(OrderItem orderItem) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public OrderItem getOrderItemById(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @Title: selectHistoryOrder
	 * @Description: 订单详情
	 * @param:  id
	 * @return: OrderDetailsApiModel
	 */
	@Override
	public OrderDetailsApiModel selectHistoryOrderWebUsed(long id) {
		if (id != 0) {
			OrderHeader orderHeader = orderDao.getOrderById(id);
			OrderDetailsApiModel odam = new OrderDetailsApiModel();	
			if(orderHeader != null){
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa",Locale.CANADA);
				Restaurants restaurants = restaurantsDao.getRestaurantsByUuid(orderHeader.getRestaurantUuid());
				odam.setOrderId(id);
				odam.setRestaurantUuid(orderHeader.getRestaurantUuid());
				String rejection = "";
				if(StringUtil.isNotEmpty(orderHeader.getRejection())){
					rejection = orderHeader.getRejection();
					odam.setRejection(rejection);
				}
				String memo = "";
				if(StringUtil.isNotEmpty(orderHeader.getMemo())){
					memo = orderHeader.getMemo();
					odam.setIntruction(memo);
				}

				if(StringUtil.isNotEmpty(orderHeader.getMemo())){
					odam.setIntruction(orderHeader.getMemo());
				}else {
					odam.setIntruction("");
				}
				if(StringUtil.isNotEmpty(restaurants.getRestaurantName())){
					odam.setRestaurantName(restaurants.getRestaurantName());
				} else {
					odam.setRestaurantName("");
				}		
				if(orderHeader.getNumber() != null){
					odam.setNumber(orderHeader.getNumber());
				}
				if(orderHeader.getCreatedate() != null){
					String createDate = dateFormat1.format(orderHeader.getCreatedate());
					odam.setCreatedate(createDate);
				}
				if(StringUtil.isNotEmpty(restaurants.getRestaurantAddress())){
					odam.setRestaurantAddress(restaurants.getRestaurantAddress());
				} else {
					odam.setRestaurantAddress("");
				}
				if(orderHeader.getTotal() > 0){
					odam.setTotal(orderHeader.getTotal());
				}		
				if(orderHeader.getTip() > 0){
					odam.setTip(StringUtil.convertLastDouble(orderHeader.getTip()));
				}
				if(orderHeader.getLogistics() >0){
					odam.setDeliveryfee(orderHeader.getLogistics());
				}
				if(orderHeader.getAddress() != null){
					odam.setConsumersAddress(orderHeader.getAddress());
				}
				if(orderHeader.getStatus() != null){
					odam.setStatus(orderHeader.getStatus());
				}
				if(orderHeader.getOrderDate() != null){
					odam.setOrderDateStr(orderHeader.getOrderDate()+"");
					String orderDate = dateFormat1.format(orderHeader.getOrderDate());
					if(orderHeader.getOrderType() == 3){
						int number = 0;
						if(orderHeader.getNumber() != null){
							number = orderHeader.getNumber();
						}
						odam.setOrderDate(orderDate+" / "+ number + " People");
					} else {
						odam.setOrderDate(orderDate);
					}
		
				}
				if(orderHeader.getOrderType() != null){
					odam.setOrderType((int)orderHeader.getOrderType());
					if (orderHeader.getOrderType()==1) {
						odam.setOrderTypeStr("Delivery");
					}
					else if (orderHeader.getOrderType()==2) {
						odam.setOrderTypeStr("Pick-up");
					}
					else if(orderHeader.getOrderType()==3){
						if (orderHeader.getOrderItems()!=null && orderHeader.getOrderItems().size()>0) {
							odam.setOrderTypeStr("Dine in");
						}
						else{
							odam.setOrderTypeStr("Reservation");
						}
					}
				}
				odam.setTax(orderHeader.getTax());
				odam.setAmount(orderHeader.getAmount());
				odam.setPayment(orderHeader.getPayment());
				odam.setConsumerUuid(orderHeader.getConsumers().getUuid());
				if(orderHeader.getPhoneNumber() != null){
					odam.setConsumersIdPhone(orderHeader.getPhoneNumber());
				} else {
					odam.setConsumersIdPhone("");
				}
				/*String firstName = "";
				if(StringUtil.isNotEmpty(orderHeader.getConsumers().getFirstName())){
					firstName = orderHeader.getConsumers().getFirstName();
				}
				String lastName = "";
				if(StringUtil.isNotEmpty(orderHeader.getConsumers().getLastName())){
					lastName = orderHeader.getConsumers().getLastName();
				}*/
				if(StringUtil.isNotEmpty(orderHeader.getPeopleName())){
					odam.setConsumersName(orderHeader.getPeopleName());
				}
				//如果有优惠券Id 就将优惠信息保存到返回的订单信息中
				if(orderHeader.getDiscountId()!=null && (orderHeader.getDiscountId()+"").length()>0&& Long.parseLong(orderHeader.getDiscountId()+"")>0){
					Discount discount = discountDao.getDiscount((long)(orderHeader.getDiscountId()));
					if(discount!=null){
						odam.setDiscountText(discount.getContent());
					}
				}
				if(orderHeader.getOrderItems()!=null && orderHeader.getOrderItems().size()>0){
					for (OrderItem orderItem : orderHeader.getOrderItems()) {
						CartItemApiModel item=new CartItemApiModel();
						item.setDishId(orderItem.getDishId());
						item.setNum(orderItem.getNum());
						item.setUnitprice(orderItem.getPrice());
						item.setDishRemark(orderItem.getInstruction());
						Dish dish = dishDao.getDishById(orderItem.getDishId());
						item.setDishName(dish.getEnName());
						item.setInstruction(orderItem.getInstruction());
						String str="";
						if(orderItem.getOrderDishGarnishs()!=null && orderItem.getOrderDishGarnishs().size()>0){
							for (OrderDishGarnish subItem : orderItem.getOrderDishGarnishs()) {
								CartDishGarnishApiModel tmp=new  CartDishGarnishApiModel();
								tmp.setCount(subItem.getGarnishNum());
								tmp.setGarnishItemId(subItem.getId().getGarnishItemId());
								DishGarnish dishGarnish = dishGarnishDao.getDishGarnishByDishIdAndGarnishItemId(orderItem.getDishId(), tmp.getGarnishItemId());
								if(dishGarnish!=null&&dishGarnish.getGarnishItem()!=null){
									tmp.setGarnishName(dishGarnish.getGarnishItem().getGarnishName());
								}
								if(subItem.getGarnishNum()>1){
									str+=(tmp.getGarnishName()+" * "+subItem.getGarnishNum());
									str+="\n";
								}else{
									str+=tmp.getGarnishName();
									str+="\n";
								}
								item.getSubItem().add(tmp);
							}
						}
						if(str.length()>1){
							str = str.substring(0,str.length()-1);
						}
						item.setShowDish(str);
						odam.getItem().add(item);
					}
					if(orderHeader.getDiscountId()!=null && orderHeader.getDiscountId()>0){
						int discountId = orderHeader.getDiscountId();
						Discount discount = discountDao.getDiscount((long)discountId);
						if(discount.getType()== GlobalConstant.DISCOUNT_TYPE_3){
							Dish dish = dishDao.getDishById((long)discount.getDishId());
							if(dish != null){
								CartItemApiModel item=new CartItemApiModel();
								item.setNum(1);
								item.setDishName(dish.getEnName());
								item.setInstruction("Coupon Dish");
								item.setUnitprice(0);
								odam.getItem().add(item);
							}
						}
					}
					
				}
				return odam;
			}
			return odam;
		}
		return null;
	}

}
