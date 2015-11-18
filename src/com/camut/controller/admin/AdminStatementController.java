package com.camut.controller.admin;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageMessage;
import com.camut.pageModel.PageModel;
import com.camut.pageModel.PageStatementCharitys;
import com.camut.pageModel.PageStatementConsumer;
import com.camut.service.CharityService;
import com.camut.service.ConsumersService;
import com.camut.service.OrderService;
import com.camut.utils.StringUtil;

@Controller("AdminStatementController")
@RequestMapping("/adminStatement")
public class AdminStatementController {
	
	@Autowired ConsumersService consumersService;
	@Autowired OrderService orderService; 
	@Autowired CharityService charityService; 
	/**
	 * @Title: userStatementPage
	 * @Description: 打开管理员 的用户报表页面
	 * @param: @return
	 * @return String  
	 */
	@RequestMapping(value="/userStatementPage")
	public String userStatementPage(HttpSession session){
		int consumersTotalNo = consumersService.getConsumersTotalNo();
		session.setAttribute("consumersTotalNo", consumersTotalNo);
		return "admin/statementUser";
	}
	
	/**
	 * @Title: restaurantStatementPage
	 * @Description: 打开管理员 的商家报表页面
	 * @param: @return
	 * @return String  
	 */
	@RequestMapping(value="/restaurantStatementPage")
	public String restaurantStatementPage(HttpSession session){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String endDate = format.format(calendar.getTime());
		//System.out.println("当前时间："+endDate);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		String startDate = format.format(calendar.getTime());
		//System.out.println("当月第一天："+startDate);
		session.setAttribute("startDate", startDate);
		session.setAttribute("endDate", endDate);
		return "admin/statementRestaurant";
	}
	
	/**
	 * @Title: getStatementAllConsumers
	 * @Description: 获取管理员的用户报表数据加载到表格
	 * @param: @param session
	 * @param: @return
	 * @return List<PageStatementConsumer>  
	 */
	@RequestMapping(value="getStatementAllConsumers", method=RequestMethod.GET)
	@ResponseBody
	public PageModel getStatementAllConsumers(HttpSession session, PageFilter pf){
		List<PageStatementConsumer> StatementConsumers = consumersService.getStatementAllConsumers(pf);
		PageModel pm = new PageModel();
		pm.setRows(StatementConsumers);
		pm.setTotal(consumersService.getConsumersTotalNo());
		return pm;
	}
	
	/**
	 * @Title: getStatementAllOrders
	 * @Description: //管理员获取所有商家的所有订单 
	 * @param: @param pf
	 * @param: @return
	 * @return PageModel  
	 */
	@RequestMapping(value="getStatementAllOrders", method=RequestMethod.GET)
	@ResponseBody
	public PageModel getStatementAllOrders(String searchKey, PageFilter pf){
		PageModel pm = new PageModel();
		pm.setRows(orderService.getStatementAllOrders(searchKey, pf));
		pm.setTotal((long)orderService.getCount());
		return pm;
		
	}
	
	/**
	 * @Title: getStatementOrdersAmount
	 * @Description: //获取报表搜索出的订单的总金额
	 * @param: @return
	 * @return PageMessage  
	 */
	@RequestMapping(value="getStatementOrdersAmount", method=RequestMethod.POST)
	@ResponseBody
	public PageMessage getStatementOrdersAmount(){
		PageMessage pm = new PageMessage();
		double amount = orderService.getStatementOrdersAmount();
		DecimalFormat format = new DecimalFormat("#.00");
		String strAmount = format.format(amount);
		pm.setErrorMsg(strAmount);
		return pm;
	}
	
	/**
	 * @Title: donationStatementPage
	 * @Description: 打开管理员 的捐款报表页面
	 * @param: @return
	 * @return String  
	 */
	@RequestMapping(value="/donationStatementPage")
	public String donationStatementPage(HttpSession session, PageFilter pf){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String searchMonth = format.format(calendar.getTime());
		session.setAttribute("searchMonth", searchMonth);
		return "admin/statementDonation";
	}
	
	/**
	 * @Title: getStatementCharityOrganization
	 * @Description: 获取所有的慈善机构报表
	 * @param: @param searchKey
	 * @param: @param pf
	 * @param: @return
	 * @return PageModel  
	 */
	@RequestMapping(value="getStatementCharityOrganization")
	@ResponseBody
	public PageModel getStatementCharityOrganization(String searchKey, PageFilter pf){
		PageModel pm = new PageModel();
		pm.setRows(charityService.getCharityList(searchKey, pf));
		pm.setTotal(charityService.getCount());
		return pm;
	}
	
	/**
	 * @Title: getOneCharityDonationTitle
	 * @Description: 获取单个慈善机构捐款信息的头信息
	 * @param: @param searchMonth
	 * @param: @param charityId
	 * @return void  
	 */
	@RequestMapping(value="getOneCharityDonationTitle", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getOneCharityDonationTitle(String searchMonth, String charityId){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtil.isNotEmpty(searchMonth) && StringUtil.isNotEmpty(charityId)){
			map = charityService.getOneCharityDonationTitle(searchMonth, charityId);
			if(map!=null){
				map.put("success", "true");
			}else{
				map.put("success", "false");
			}
		}else{
			map.put("success", "false");
		}
		return map;
	}
	
	/**
	 * @Title: getOneCharityDonation
	 * @Description: 获取单个慈善机构捐款信息
	 * @param: @param searchMonth
	 * @param: @param charityId
	 * @param: @return
	 * @return PageModel  
	 */
	@RequestMapping(value="getOneCharityDonation")
	@ResponseBody
	public PageModel getOneCharityDonation(String searchMonth, String charityId, PageFilter pf){
		PageModel pm = new PageModel();
		pm.setRows(charityService.getOneCharityDonation(searchMonth, charityId, pf));
		pm.setTotal(charityService.getCount());
		return pm;
	}
	
	
}
