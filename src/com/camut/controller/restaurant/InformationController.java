/**
 * 
 */
package com.camut.controller.restaurant;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.camut.framework.constant.MessageConstant;
import com.camut.model.Chain;
import com.camut.model.Classification;
import com.camut.model.Restaurants;
import com.camut.model.RestaurantsUser;
import com.camut.model.ViewArea;
import com.camut.pageModel.PageAreas;
import com.camut.pageModel.PageClassification;
import com.camut.pageModel.PageMessage;
import com.camut.pageModel.PageRestaurant;
import com.camut.service.AreasService;
import com.camut.service.ChainService;
import com.camut.service.ClassificationService;
import com.camut.service.RestaurantsService;
import com.camut.utils.ImageUtils;

/**
 * @ClassName InformationController.java
 * @author wangpin
 * @createtime Jun 9, 2015
 * @author
 * @updateTime
 * @memo
 */
@Controller("InformationController")
@RequestMapping("information")
public class InformationController extends BaseController {

	@Autowired
	private RestaurantsService restaurantsService;
	
	@Autowired
	private ChainService chainService;
	
	@Autowired
	private AreasService areasService;
	
	@Autowired
	private ClassificationService classificationService;
	
	/*
	 * @Title: toInformation
	 * @Description: 返回商家信息页面
	 * @param:    
	 * @return: String
	 */
	@RequestMapping(value="information",method=RequestMethod.GET)
	public String toInformation(){
		return "restaurant/basicinformation";
	}
	
	/*
	 * @Title: getPageRestaurant
	 * @Description: 返回商家的页面对象
	 * @param:	
	 * @return: PageRestaurant
	 */
	@RequestMapping(value="getPageRestaurant",method=RequestMethod.POST)
	@ResponseBody
	public PageRestaurant getPageRestaurant(HttpServletRequest request){
			PageRestaurant pageRestaurant = new PageRestaurant();
			List<Long> list = new ArrayList<Long>();
			Restaurants restaurants = this.getRestaurants(request.getSession(), request);
			if (restaurants.getViewArea()!=null) {
				pageRestaurant.setAreasId(restaurants.getViewArea().getAreaId().intValue());
			}
			if(restaurants!=null){
				BeanUtils.copyProperties(restaurants, pageRestaurant);
			}
			if(restaurants.getClassificationsSet()!=null){
				for(Classification c:restaurants.getClassificationsSet()){
					list.add(c.getId());
				}
				pageRestaurant.setClassificationId(list);
			}
		return pageRestaurant;
	}
	
	/*
	 * @Title: getAllChain
	 * @Description: 返回所有的连锁店
	 * @param:    
	 * @return: List<Chain>
	 */
	@RequestMapping(value="getAllChain",method=RequestMethod.POST)
	@ResponseBody
	public List<Chain> getAllChain(){
		return chainService.getAllChain();
	}
	
	/*
	 * @Title: getAllChain
	 * @Description: 返回所有的子区域
	 * @param:    
	 * @return: List<Chain>
	 */
	@RequestMapping(value="getAllAreas",method=RequestMethod.POST)
	@ResponseBody
	public List<PageAreas> getAllAreas(){
		List<PageAreas> list =  areasService.getSonAreas();
		return list;
	}
	
	/*
	 * @Title: updateRestaurant
	 * @Description: 修改商家信息
	 * @param:    
	 * @return: PageMessage
	 */
	@RequestMapping(value="updateRestaurant",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage updateRestaurant(HttpServletRequest request,Restaurants restaurants,Long areasId){
		PageMessage pm = new PageMessage();
		restaurants.setIsdelivery(restaurants.getIsdelivery()==null?0:1);
		restaurants.setIspickup(restaurants.getIspickup()==null?0:1);
		restaurants.setIsreservation(restaurants.getIsreservation()==null?0:1);
		Set<Classification> set = new HashSet<Classification>();
		if(areasId!=null){
			ViewArea viewArea = new ViewArea();
			viewArea.setAreaId(areasId.intValue());
			restaurants.setViewArea(viewArea);
		}
		RestaurantsUser restaurantsUser = ((RestaurantsUser)request.getSession().getAttribute("restaurantsUser"));
		Restaurants restaurants1 = this.getRestaurants(request.getSession(), request);
		restaurants.setLogourl(restaurants1.getLogourl());
		restaurants.setModdy(restaurantsUser.getFirstName()+" "+restaurantsUser.getLastName());
		restaurants.setStatus(restaurants1.getStatus());
		restaurants.setModon(new Date());
		restaurants.setCreatetime(restaurants1.getCreatetime());
		restaurants.setStripeAccount(restaurants1.getStripeAccount());
		String[] ClassificationId = request.getParameterValues("ClassificationId");
		for(String id:ClassificationId){
			Classification classification = new Classification();
			classification.setId(Long.parseLong(id));
			set.add(classification);
		}
		restaurants.setClassificationsSet(set);
		int flag = restaurantsService.updateRestaurants(restaurants);
		if(flag==-1){
			pm.setErrorMsg(MessageConstant.UPDATE_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	
	@RequestMapping(value="uploadLogo",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage uploadLogo(HttpServletRequest request){
		PageMessage pm = new PageMessage();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
		MultipartFile file = multipartRequest.getFile("logo");
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		String tmpDir = restaurants.getId()+"";
		String logourl = "";
		try {
			logourl = ImageUtils.saveImage(file, request, "upload", tmpDir, true);
		} catch (Exception e) {
			return null;
		}
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		restaurants.setLogourl(basePath+logourl);
		int flag = restaurantsService.updateRestaurants(restaurants);
		if(flag==-1){
			return null;
		}
		return pm;
	}
	
	/**
	 * @Title: getAllClassification
	 * @Description:获取所有餐厅分类
	 * @param:    
	 * @return: List<PageClassification>
	 */
	@RequestMapping(value="getAllClassification",method=RequestMethod.POST)
	@ResponseBody
	public List<PageClassification> getAllClassification(){
		return classificationService.getAllClassification();
	}
	
	/**
	 * @Title: getAllClassification
	 * @Description:获取所有区域
	 * @param:    
	 * @return: List<PageClassification>
	 */
	@RequestMapping(value="getAreas",method=RequestMethod.POST)
	@ResponseBody
	public List<ViewArea> getAreas(){
		return areasService.getViewAreaList();
	}
}