package com.camut.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.OpenTimeDao;
import com.camut.dao.RestaurantsDao;
import com.camut.framework.constant.GlobalConstant;
import com.camut.model.OpenTime;
import com.camut.model.Restaurants;
import com.camut.model.api.OpenTimeApiModel;
import com.camut.pageModel.PageOpenTime;
import com.camut.service.OpenTimeService;
import com.camut.utils.GoogleTimezoneAPIUtil;
import com.camut.utils.StringUtil;

/**
 * @dao OpenTimeServiceImpl.java
 * @author zhangyunfei
 * @createtime 6 08, 2015
 * @author
 * @updateTime
 * @memo
 */
@Service
public class OpenTimeServiceImpl implements OpenTimeService {

	@Autowired
	private OpenTimeDao openTimeDao;

	@Autowired
	private RestaurantsDao restaurantsDao;

	/**
	 * @Title: selectOpenTime
	 * @Description: 营业时间显示
	 * @param: restaurantId
	 * @return: List<OpenTimeApiModel>
	 */
	@Override
	public List<String> selectOpenTime(String restaurantUuid, int type, String date) {
		if(date != null){
			Calendar c = Calendar.getInstance();
			DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			Date newdate;
			try {
				newdate = fmt.parse(date);
				c.setTime(newdate);
				int week = c.get(Calendar.DAY_OF_WEEK);
				switch (week) {
				case 1:
					week = 7;
					break;
				case 2:
					week = 1;
					break;
				case 3:
					week = 2;
					break;
				case 4:
					week = 3;
					break;
				case 5:
					week = 4;
					break;
				case 6:
					week = 5;
					break;
				case 7:
					week = 6;
					break;
				}
				List<OpenTime> otList = openTimeDao.getOpenTime(restaurantUuid, type, week);
				List<String> timeList = new ArrayList<>();
				for (OpenTime openTime : otList) {
					OpenTimeApiModel otam = new OpenTimeApiModel();
					otam.setType(openTime.getType());
					otam.setWeek(openTime.getWeek());

					String startTime = openTime.getStarttime();// 开始时间
					String endTime = openTime.getEndtime();// 结束时间
					// Date currentDate = new Date();

					SimpleDateFormat format = new SimpleDateFormat("HH:mm");
					Date date1 = format.parse(startTime);
					Date date2 = format.parse(endTime);
					// Date currentDate = format.p

					
					long longTime1 = date1.getTime();
					long longTime2 = date2.getTime();

					if (type == 1) {// 外送 时间间隔30分钟

						do {
							String temp = format.format(new Date(longTime1));
							String t = temp + "";
							timeList.add(t);
							longTime1 += 1000 * 60 * 30;// 每次加30分钟
							System.out.println(temp);
						} while (longTime1 < longTime2);

					}
					if (type == 2 || type == 3) {// 自取，到点就餐 15分钟间隔
						do {
							String temp = format.format(new Date(longTime1));
							String t = temp + "";
							timeList.add(t);
							longTime1 += 1000 * 60 * 15;// 每次加30分钟
							System.out.println(temp);
						} while (longTime1 < longTime2);
					}
				}
				return timeList;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @Title: getAllOpenTime
	 * @Description: 获取商家的营业时间
	 * @param: Restaurants
	 * @return: PageOpenTime
	 */
	@Override
	public List<PageOpenTime> getAllOpenTime(String restaurantUuid) {
		//if(restaurants!=null && restaurants.getId()!=null){
		if(StringUtil.isNotEmpty(restaurantUuid)){
			List<OpenTime> openTimes = openTimeDao.selectOpenTime(restaurantUuid);
			List<PageOpenTime> list = new ArrayList<PageOpenTime>();
			for (OpenTime ot : openTimes) {
				PageOpenTime pageOpenTime = new PageOpenTime();
				BeanUtils.copyProperties(ot, pageOpenTime);
				int startH = Integer.parseInt( ot.getStarttime().split(":")[0]);
				String startM =  ot.getStarttime().split(":")[1];
				if(startH-12<0){//判断是am还是pm
					pageOpenTime.setStarttime(ot.getStarttime()+"AM");
				}
				else if(startH-12==0){
					pageOpenTime.setStarttime(ot.getStarttime()+"PM");
				}
				else{
					pageOpenTime.setStarttime(startH-12+":"+startM+"PM");
				}
				
				int endH = Integer.parseInt( ot.getEndtime().split(":")[0]);
				String endM =  ot.getEndtime().split(":")[1];
				if(endH-12<0){//判断是am还是pm
					pageOpenTime.setEndtime(ot.getEndtime()+"AM");
				}
				else if(endH-12==0){
					pageOpenTime.setEndtime(ot.getEndtime()+"PM");
				}
				else{
					pageOpenTime.setEndtime(endH-12+":"+endM+"PM");
				}
				list.add(pageOpenTime);
			}
			return list;
		}
		return null;
	}

	/**
	 * @Title: addOpenTime
	 * @Description: 增加营业时间
	 * @param:    OpenTime
	 * @return: int
	 */
	@Override
	public int addOpenTime(OpenTime openTime) {
		if(openTime!=null){
			return openTimeDao.addOpenTime(openTime);
		}
		return -1;
	}

	/**
	 * @Title: deleteOpenTime
	 * @Description: 删除营业时间
	 * @param:    OpenTime
	 * @return: int
	 */
	@Override
	public int deleteOpenTime(OpenTime openTime) {
		if(openTime!=null){
			return openTimeDao.deleteOpenTime(openTime);
		}
		return -1;
	}

	/**
	 * @Title: getOpenTime
	 * @Description: 获取商家的营业时间（moer）
	 * @param:    RestaurantId
	 * @return: List<OpenTimeListApiModel>
	 */
	@Override
	public List<Map<String, Object>> getOpenTime(String restaurantUuid) {
		List<OpenTime> otList = openTimeDao.selectOpenTime(restaurantUuid);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if(otList != null){
			List<OpenTimeApiModel> otamList = new ArrayList<OpenTimeApiModel>();
			// 1 外送 2 自取 3 预定
			for (OpenTime openTime : otList) {
				OpenTimeApiModel otam = new OpenTimeApiModel();
				otam.setOpenTimeId(openTime.getId());
				otam.setWeek(openTime.getWeek());
				StringBuffer sb = new StringBuffer();
				sb.append(openTime.getStarttime());
				sb.append(" ~ ");
				sb.append(openTime.getEndtime());
				otam.setOpentime(sb.toString());
				otam.setType(openTime.getType());
				otamList.add(otam);
			}
			
			List<OpenTimeApiModel> tmpList = new ArrayList<OpenTimeApiModel>();
			int num = 0;
			boolean flag = false;
			for (OpenTimeApiModel item : otamList) {
				if (num == 0) {
					tmpList.add(item);
					num++;
				} else {
					for (OpenTimeApiModel openTimeApiModel : tmpList) {
						if (openTimeApiModel.getType() == item.getType()
								&& openTimeApiModel.getWeek() == item.getWeek()) {
							flag = true;
							break;
						} else {
							flag = false;
						}
					}
					if (flag) {
						for (OpenTimeApiModel openTimeApiModel : tmpList) {
							if (openTimeApiModel.getType() == item.getType()
									&& openTimeApiModel.getWeek() == item.getWeek()) {
								StringBuffer sb = new StringBuffer();
								sb.append(openTimeApiModel.getOpentime());
								sb.append("  ");
								sb.append(item.getOpentime());
								openTimeApiModel.setOpentime(sb.toString());
							}
						}
					} else {
						tmpList.add(item);
					}
				}
			}
			Map<String, Object> mapde=new HashMap<String, Object>();
			Map<String, Object> mappi=new HashMap<String, Object>();
			Map<String, Object> mapre=new HashMap<String, Object>();
			List<OpenTimeApiModel> listTypede=new ArrayList<OpenTimeApiModel>();
			List<OpenTimeApiModel> listTypepi=new ArrayList<OpenTimeApiModel>();
			List<OpenTimeApiModel> listTypere=new ArrayList<OpenTimeApiModel>();
			for (OpenTimeApiModel openTimeApiModel : tmpList) {
				Map<String, Object> map=new HashMap<String, Object>();
				if (GlobalConstant.TYPE_DELIVERY == openTimeApiModel.getType()) {
					listTypede.add(openTimeApiModel);
				}
				if (GlobalConstant.TYPE_PICKUP == openTimeApiModel.getType()) {				
					listTypepi.add(openTimeApiModel);
				}
				if (GlobalConstant.TYPE_RESERVATION == openTimeApiModel.getType()) {
					listTypere.add(openTimeApiModel);
				}
			}
			if(listTypede!=null&&listTypede.size()>0){
				mapde.put("type", GlobalConstant.TYPE_DELIVERY);
				mapde.put("name", "Delivery");
				mapde.put("content", listTypede);
				result.add(mapde);
			}
			if(listTypepi!=null&&listTypepi.size()>0){
				mappi.put("type", GlobalConstant.TYPE_PICKUP);
				mappi.put("name", "Pick up");
				mappi.put("content", listTypepi);
				result.add(mappi);
			}
			if(listTypere!=null&&listTypere.size()>0){
				mapre.put("type", GlobalConstant.TYPE_RESERVATION);
				mapre.put("name", "Reservation");
				mapre.put("content", listTypere);
				result.add(mapre);
			}
		}
		return result;
	}

	/**
	 * @Title: orderDateAtOpenTime
	 * @Description: 判断订单时间是否在营业时间内
	 * @param:    Date orderDate,int restaurantId
	 * @return: int -1不在 1在-2 30只能下30分钟后的单 -3只能下15分钟后的单
	 */
	@Override
	public int orderDateAtOpenTime(Date orderDate, String restaurantUuid,int ordertype) {
	
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(orderDate);
		int week = calendar.get(Calendar.DAY_OF_WEEK)-1;
		if (week==0) {
			week = 7;
		}
		//根据商家id和订单类型找出营业时间
		List<OpenTime> list = openTimeDao.getOpenTime(restaurantUuid, ordertype, week);
		String yyyymmdd = new SimpleDateFormat("yyyy-MM-dd").format(orderDate);
		if (orderDate.before(new Date())) {
			return -4;
		}
		
		if (ordertype==1) {//delivery
			if ( orderDate.before(new Date(new Date().getTime()+30*60*1000))) {
				return -2;
			}
		}
		else if(ordertype==2 || ordertype==3){//pick-up or dine in
			if ( orderDate.before(new Date(new Date().getTime()+15*60*1000))) {
				return -3;
			}
		}
		long longOrderDate = orderDate.getTime();
		if (list!=null && list.size()>0) {
			for (OpenTime openTime : list) {
				try {
						long startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(yyyymmdd+" "+openTime.getStarttime()).getTime();
						long endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(yyyymmdd+" "+openTime.getEndtime()).getTime();
						//在营业时间内
						if (longOrderDate>=startTime && longOrderDate<=endTime) {
							return 1;
						}
				} catch (Exception e) {
					// TODO: handle exception
					return -1;
				}
			}
		}
		
		return -1;
	}

	/**
	 * @Title: getOpenTimeByOrderDate
	 * @Description: 返回营业时间段
	 * @param:    
	 * @return: String[]
	 */
	@Override
	public String[] getOpenTimeByOrderDate(Date orderDate, String restaurantUuid, int type) {
		Calendar calendar = Calendar.getInstance();
		TimeZone originalTimeZone = calendar.getTimeZone();
		Date originalTime = calendar.getTime();

		// Adjust times for restaurant local time
		// TODO: This assumes users are in the same time zone as the
		// restaurant!!
		Restaurants restaurant = restaurantsDao.getRestaurantsById(restaurantUuid);
		double latitude = restaurant.getRestaurantLat();
		double longitude = restaurant.getRestaurantLng();
		TimeZone restaurantTimezone = GoogleTimezoneAPIUtil.getTimeZoneFromGeoLocation(latitude, longitude);
		calendar.setTimeZone(restaurantTimezone);

		// Adjust the current calendar time using the difference between the two
		// time zones.
		int millisecondsOffset = restaurantTimezone.getRawOffset() - originalTimeZone.getRawOffset();
		calendar.add(Calendar.MILLISECOND, millisecondsOffset);

		// Apply daylight saving time correction, depending on the daylight
		// saving time status of each zone
		if (originalTimeZone.inDaylightTime(originalTime) && restaurantTimezone.inDaylightTime(calendar.getTime())) {
			int offset = restaurantTimezone.getDSTSavings() - originalTimeZone.getDSTSavings();
			calendar.add(Calendar.MILLISECOND, offset);
			millisecondsOffset += offset;
		} else if (!originalTimeZone.inDaylightTime(originalTime)
				&& restaurantTimezone.inDaylightTime(calendar.getTime())) {
			int offset = restaurantTimezone.getDSTSavings();
			calendar.add(Calendar.MILLISECOND, offset);
			millisecondsOffset += offset;
		} else if (originalTimeZone.inDaylightTime(originalTime)
				&& !restaurantTimezone.inDaylightTime(calendar.getTime())) {
			int offset = 0 - originalTimeZone.getDSTSavings();
			calendar.add(Calendar.MILLISECOND, offset);
			millisecondsOffset += offset;
		}
		int hoursOffset = (int) TimeUnit.MILLISECONDS.toHours(millisecondsOffset);

		try {
			// 注释掉的是之前做的开始时间结束时间都减少15分钟的方案， 现在是不减时间的
			String orderDateStr = new SimpleDateFormat("yyyy-MM-dd").format(orderDate);
			// 传来的日期如果是今天之前的日期直接返回null
			if (orderDate.before(new SimpleDateFormat("yyyy-MM-dd")
					.parse(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())))) {
				return null;
			}
			// 找出订单日期的星期
			// calendar.setTime(orderDate);
			int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			if (week == 0) {
				week = 7;
			}
			// 根据餐厅id，类型，星期找出当天的营业时间
			List<OpenTime> list = openTimeDao.getOpenTime(restaurantUuid, type, week);

			StringBuffer buffer = new StringBuffer();
			String startStr = "";

			Date now = calendar.getTime();
			for (OpenTime openTime : list) {
				calendar.setTime(now);

				Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(orderDateStr + " " + openTime.getEndtime());

				if (calendar.getTime().after(end)) {
					continue;
				}

				// 现在时间加半小时在开门时间之后
				if (calendar.getTime().after(
						new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(orderDateStr + " " + openTime.getStarttime()))) {

					if (type == GlobalConstant.TYPE_PICKUP) {
						calendar.add(Calendar.MINUTE, 20);
					} else if (type == GlobalConstant.TYPE_DELIVERY) {
						calendar.add(Calendar.MINUTE, 45);
					} else {
						//// calendar.setTime(new Date());
					}

					// This will still return correct time zone, but
					// overadjusted, we have to scale back
					int serverH = calendar.get(Calendar.HOUR_OF_DAY);

					// Adjust overflow in each direction
					if (serverH - hoursOffset < 0) {
						serverH += 24;
					} else if (serverH - hoursOffset > 24) {
						serverH -= 24;
					}

					// Apply hour offset to get to the current time zone
					int currentH = serverH - hoursOffset;
					int currentM = calendar.get(Calendar.MINUTE);

					if (currentM > 0 && currentM <= 15) {
						startStr = currentH + ":15";
					} else if (currentM > 15 && currentM <= 30) {
						startStr = currentH + ":30";
					} else if (currentM > 30 && currentM <= 45) {
						startStr = currentH + ":45";
					} else if (currentM > 45 && currentM <= 59) {
						currentH += 1;
						startStr = currentH + ":00";
					} else {
						startStr = currentH + ":15";
					}
				} else {
					int startH = Integer.parseInt(openTime.getStarttime().split(":")[0]);
					int startM = Integer.parseInt(openTime.getStarttime().split(":")[1]);
					startStr = startH + ":" + startM;
				}
				Date start = new SimpleDateFormat("HH:mm").parse(startStr);
				calendar.setTime(start);

				while (!(calendar.getTime().after(new SimpleDateFormat("HH:mm").parse(openTime.getEndtime())))) {
					buffer.append(new SimpleDateFormat("HH:mm").format(calendar.getTime()) + ",");
					calendar.add(Calendar.MINUTE, 15);
				}
			}
			String[] strs = buffer.toString().split(",");
			// 去除可能存在的重复
			List<String> list1 = new ArrayList<String>();
			for (String str : strs) {
				if (!list1.contains(str)) {
					list1.add(str);
				}
			}
			return (String[]) list1.toArray(new String[list1.size()]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
