$(function(){
	//显示等待遮罩层
    $("#bg").css("display", "block");
	$("#show").css("display", "block");
	$.cookie("restaurantUuid", $("#restaurantUuid").val(),{expires:10,path: '/' });  //设置商家Id到cookie
	$.cookie("taxRate",$("#taxRate").val(),{expires:10,path: '/' });//设置商家税率
	
	
	//var stayOrClearCartClick = false;//用于判断是否清楚购物车模态框关闭时，是否的点击的清空或保留的两个按钮
	//var showLoginModalByDineInClick = false;//用于判断跳出登录的模态框是否是Dine in 点击触发的，如果是，则在关闭时要检查是否登录了，如果模态框关闭且是未登录，则要跳回到原来的类型去
	//var flagOpenTime = false;//标记商家营业时间是否可用
	//var orderTimeIsAfterNow = false;//订单时间是否在现在时间之后
	//var currentPreSelectFood = "";//reservation 点击pre-select food 时保存当前订单的字符串，用于加载到dinein下拉框
	var restaurantUuid = $("#restaurantUuid").val();
	var checkoutClick = false;//用于判断是否是点击了checkout才显示的登录模态框
	var orderTimeIsOk = false;//订单的时间是否在店家营业时间内
	
	var cartOrderType = $("input[name='orderType']").val();//$("#orderType").val();//购物车中的订单类型
	var resNewReservation = false;//reservation仅订桌按钮
	var newOrderType = 2;//
	var reservationOrderId = 0;

//------------------------------------打开页面根据商家拥有的订餐权限 初始化 pickup Delivery Dinein 按钮 和加载菜品----------------------------
	/*(function(){
		if($("#ispickup").val()==0){//没有pickup权限
			$("#pickupButton").removeAttr("aria-controls");
			$("#pickupButton").removeAttr("role");
			$("#pickupButton").removeAttr("data-toggle");
			$("#pickupButton").removeAttr("href");
			$("#pickupButton").removeAttr("id");
		}else{//有pickup权限
				$("li[name='type-pickup']").attr("class","active");
				$("li[name='type-delivery']").removeAttr("class");
				$("li[name='type-dinein']").removeAttr("class");
				$("#biaodan").attr("class","tab-pane active");
				$("#tianxie").attr("class","tab-pane");
			
		}
		if($("#isdelivery").val()==0){//没有Delivery权限
			$("#deliveryButton").removeAttr("aria-controls");
			$("#deliveryButton").removeAttr("role");
			$("#deliveryButton").removeAttr("data-toggle");
			$("#deliveryButton").removeAttr("href");
			$("#deliveryButton").removeAttr("id");
			
		}else{
			if($("#ispickup").val()==0){
				$("li[name='type-pickup']").removeAttr("class");
				$("li[name='type-delivery']").attr("class","active");
				$("li[name='type-dinein']").removeAttr("class");
				$("#biaodan").attr("class","tab-pane active");
				$("#tianxie").attr("class","tab-pane");
			}
		}
		if($("#isreservation").val()==0){
			$("button[name='reservation']").attr("disabled",true);
			$("#dineInButton").removeAttr("aria-controls");
			$("#dineInButton").removeAttr("role");
			$("#dineInButton").removeAttr("data-toggle");
			$("#dineInButton").removeAttr("href");
			$("#dineInButton").removeAttr("id");
		}else{
			if($("#ispickup").val()==0 && $("#isdelivery").val()==0){
				$("li[name='type-pickup']").removeAttr("class");
				$("li[name='type-delivery']").removeAttr("class");
				$("li[name='type-dinein']").attr("class","active");
				
				$("#biaodan").attr("class","tab-pane");
				$("#tianxie").attr("class","tab-pane  active");
			}
		}
	})()*/
	
	//判断购物车中的订单类型，初始化对应订单按钮
	/*if(cartOrderType=="1"){//delivery
		$("li[name='type-delivery']").attr("class","active");
		$("li[name='type-dinein']").removeAttr("class");
		$("li[name='type-pickup']").removeAttr("class");
		
		$("#biaodan").attr("class","tab-pane active");
		$("#tianxie").attr("class","tab-pane");
		
	}else if(cartOrderType=="3"){//dine in
		$("li[name='type-pickup']").removeAttr("class");
		$("li[name='type-delivery']").removeAttr("class");
		$("li[name='type-dinein']").attr("class","active");
		
		$("#tianxie").attr("class","tab-pane active");
		$("#biaodan").attr("class","tab-pane");
	}*/
	
	/*异步加载菜品分类和分类下面的菜品*/
	(function getDishes(){
		$("#bg").css("display", "block");
		$("#show").css("display", "block");
		var restaurantUuid = $("#restaurantUuid").val();
		$.ajax({
			type:'post',
			url:appPath+'/restaurantMenu/getDishes',
			data:{"restaurantUuid":restaurantUuid},
			success:function(data){
				$("#accordion").html(data);
				$("#bg").css("display", "none");//隐藏等待层
				$("#show").css("display", "none");
			}
		})
	})()
	
	var consumerLng = $.cookie("map-Lng");//? $.cookie("map-Lng") : $.cookie("browser-Lng");
	var consumerLat = $.cookie("map-Lat");//? $.cookie("map-Lat") : $.cookie("browser-Lat");
	/*刷新购物车方法*/
	function refreshCart (){
		var consumerUuid = $("#currentConsumerUuid").val();
		$("#bg").css("display", "block");//显示等待遮罩层
		$("#show").css("display", "block");
		$.ajax({
    		type: 'post',
    		url: appPath+'/consumers/showCart',
    		data: {"consumerLng":consumerLng,"consumerLat":consumerLat,"consumerUuid":consumerUuid,"orderType":newOrderType},
    		success: function(data){
    			$("#bg").css("display", "none");
    			$("#show").css("display", "none");
    			$("#cartContent").html(data);
    			var cartRestaurantUuid = $("#cartContent input[name='cartRestaurantUuid']").val();
    			
    			//以下初始化购物车按钮 10月10日改
    			cartOrderType = $("input[name='orderType']").val();
    			if(cartOrderType=="1"){//delivery
    				$("li[name='type-delivery']").attr("class","active");
    				$("li[name='type-dinein']").removeAttr("class");
    				$("li[name='type-pickup']").removeAttr("class");
    				$("#biaodan").attr("class","tab-pane active");
    				$("#tianxie").attr("class","tab-pane");
    			}else if(cartOrderType=="3"){//dine in
    				$("li[name='type-pickup']").removeAttr("class");
    				$("li[name='type-delivery']").removeAttr("class");
    				$("li[name='type-dinein']").attr("class","active");
    				$("#tianxie").attr("class","tab-pane active");
    				$("#biaodan").attr("class","tab-pane");
    				showReservationOrders ();
    			}
    		}
    	})
	}
	refreshCart ();
	
	//左上角返回键
	$("button[name='back-searchlist']").click(function(){
		history.back();
	})
	
	/*当前用户收藏按钮开关*/
	function showfavorite (){
		var restaurantUuid = $("#restaurantUuid").val();
		var consumerUuid = $("#currentConsumerUuid").val();
		$.ajax({
			type:'post',
			url:appPath+"/consumer/existFavorites",
			data:{"consumerUuid":consumerUuid,"restaurantUuid":restaurantUuid},
			success:function(data){
				var msg = $.parseJSON(data);
				if(msg.success){
					$("img[name='favoriteImg']").attr("src",appPath+"/index/images/collect-1.jpg");
				}else{
					$("img[name='favoriteImg']").attr("src",appPath+"/index/images/collect-0.jpg");
				}
			}
		});
		
	}
	//index页面的google自动匹配的地址载入到右侧Address中
	$("div[name='googleMapPlace']").empty().append($("#googleAutocompletePlace").val());
	
	if($("#currentConsumerUuid").val()){//如果用户id有值是true说明是已经登录了
		showfavorite ();
	}
	/*返回首页*/
	$("a[name='logo']").click(function(){
		window.location = appPath+"/index/index";
	});
	
	/*切换是否是收藏*/
	$("img[name='favoriteImg']").click(function(){
		if(!$("#currentConsumerUuid").val()){//判断是否已经登录，如果没有登录弹出登录框
			$("#myModal").modal('show');
		}else{//如果登录了再点收藏按钮
			var src = $(this).attr("src");
			var restaurantUuid = $("#restaurantUuid").val();
			var consumerUuid = $("#currentConsumerUuid").val();
			if(src==appPath+"/index/images/collect-1.jpg"){//如果是已经是红色的点击就是删除变灰色
				$.ajax({
					type:'post',
					url:appPath+"/consumer/deleteConsumerFavorite",
					data:{"consumerUuid":consumerUuid,"restaurantUuid":restaurantUuid},
					success:function(data){
						var msg = $.parseJSON(data);
						if(msg.success){
							$("img[name='favoriteImg']").attr("src",appPath+"/index/images/collect-0.jpg");
						}
					}
				})
			}else{//如果是已经是灰色的点击就是增加收藏变红色
				//addConsumerFavorite
				$.ajax({
					type:'post',
					url:appPath+"/consumer/addConsumerFavorite",
					data:{"consumerUuid":consumerUuid,"restaurantUuid":restaurantUuid},
					success:function(data){
						var msg = $.parseJSON(data);
						if(msg.success){
							$("img[name='favoriteImg']").attr("src",appPath+"/index/images/collect-1.jpg");
						}
					}
				})
			}
		}
	});
	
	function getOrderDayTime(orderDay,orderType,hourElementId){
		$("#"+hourElementId).empty();
		$.ajax({
			type:'post',
			async:false,
			url: appPath+'/consumers/getRestaurantOpenTimeOfOneDay',
			data: {"orderDay":orderDay,"restaurantUuid":restaurantUuid,"type":orderType},
			success: function(data){
				var msg = $.parseJSON(data);
				//console.log(data);
				if(msg.errorMsg.length>1){
					var openTimeArray = msg.errorMsg.split("==");
					var timeOptions = "";
					for ( var int = 0; int < openTimeArray.length; int++) {
						timeOptions += "<option value='"+openTimeArray[int]+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+openTimeArray[int]+"</option>";
					}
				}else{
					timeOptions = "<option value='0'>Closed Today</option>";
				}
				$("#"+hourElementId).append(timeOptions);
			}
		})
	}
	
	/**加载时间下拉框*/
	function loadOrderDate(orderType,dayElementId,hourElementId){
		var n = 365; //Allow order dates for up to 1 year in the future.
		var dateSelect = $("#"+dayElementId);//获取页面select元素
		var orderDay ;
		dateSelect.empty();
		var d = new Date();//当前时间
		for (var i =0; i <= n; i++) {
			if(i>0){
				d.setDate(d.getDate()+1);//每循环一次加一天
			}
			var month = d.getMonth()+1+"";
			if(month.length<2){
				month = "0"+month;
			}
			var day = d.getDate();
			day = day+"";
			if(day.length<2){
				day = "0"+day;
			}
			var dayStr = d.getFullYear()+"-"+month+"-"+day;
			dateSelect.append("<option value='"+dayStr+"'>"+dayStr+"</option>");
			if(i==0){
				orderDay = $.trim(dayStr);
			}
		};
		getOrderDayTime(orderDay,orderType,hourElementId);
	}
	
	if($("#orderType").val()==1 || $("#orderType").val()==2){
		loadOrderDate($("#orderType").val(),"orderTime-day","orderTime-hourAndMinutes");//加载订单小时：分钟
		initSelectedDeliveryAndPickUpOrderDate();//选择之前选择的时间
	}
	
	$("#orderTime-day").change(function(){
		var orderType = $("#orderType").val();
		var orderDay = $.trim($(this).val());
		getOrderDayTime(orderDay,orderType,"orderTime-hourAndMinutes");
	})
	$("#res-orderDay").change(function(){
		var orderType = 3;
		var orderDay = $.trim($(this).val());
		getOrderDayTime(orderDay,orderType,"res-orderHourAndMinutes");//加载时：分下拉框
		loadReserationPeopleNumber();//加载桌位下拉框
		
	})
	$("#res-orderHourAndMinutes").change(function(){
		loadReserationPeopleNumber();//加载桌位下拉框
		
	})
	
	/*异步加载显示可用的reservation订单*/
	function showReservationOrders (){
		var restaurantUuid = $("#restaurantUuid").val();
		var consumerUuid =  $("#currentConsumerUuid").val();
		if($.cookie("cart-orderId")>0){
			reservationOrderId = $.cookie("cart-orderId");
		}
		$.ajax({
			type: 'post',
			async: false,
			url: appPath+"/consumers/getUnpaidReservationOrders",
			data: {"restaurantUuid":restaurantUuid,"consumerUuid":consumerUuid,"currentReservationOrderNumber":reservationOrderId},
			success: function(data){						
				if(data.length==0){
					$("#reservationOrderSelect").empty().append("<option value='0'>&nbsp;&nbsp;&nbsp;&nbsp;No reservation orders</option>");
					if(!resNewReservation){
						resNewReservation = false;
						$("#noReservationPromptDialog").modal('show');
					}
				}else{
					var msg = $.parseJSON(data);
					$("#reservationOrderSelect").empty();
					for (var j=0; j<msg.length; j++ ){
						var temp = msg[j];
						var statusString = "Pending";
						if (temp.status == 3) {
							statusString = "Accepted";
						}
						$("#reservationOrderSelect").append("<option value='"+temp.id+"'>&nbsp;&nbsp;&nbsp;&nbsp;"+temp.strOrderDate+" - "+temp.number+" people (" + statusString + ")</option>");
					}
					var currentReservationOrderId = $.cookie("currentReservationOrderId");
					$("#reservationOrderSelect").find("option[value='"+currentReservationOrderId+"']").attr("selected",true); 
				}
				$("#reservationOrdersModal").modal('show');
			}
		})
	}
	
	$("#closeNoReservationPromptDialog").click(function(){
		$("#noReservationPromptDialog").modal('hide');
	});
	$("#makeReservation").click(function(){
		$("#noReservationPromptDialog").modal('hide');
		$("button[name='reservation']").click();
	})
	
	/*选择一个reservation订单并点击提交按钮时设置购物车的外层div的alt属性为 reservation订单的id值*/
	$("#choosedAnReservationOrder").click(function(){
		reservationOrderId = $("#reservationOrderSelect").val();
		$("#reservationOrderId").val(reservationOrderId);
		$.cookie("cart-orderId", reservationOrderId,{expires:10,path: '/' })
		$("#orderType").val(3);
		$("#reservationOrdersModal").modal('hide');
	})
	
	$("#clearCart").click(function(){
		clearCart();
	})
	/*清空购物车*/
	function clearCart(){
		var consumerUuid = $("#currentConsumerUuid").val();
		$.ajax({
			type:'post',
			url:appPath+'/consumers/clearCart',
			data:{"consumerUuid":consumerUuid},
			success:function(data){
				refreshCart ();
				getDishes(changOrderType.isPickup);
				//隐藏等待层
				$("#bg").css("display", "none");
				$("#show").css("display", "none");
			}
		})
		$("#isEmptyCartModal").modal('hide');
	}
	$("#stayOrginalCart").click(function(){
		var cartRestaurantUuid = $("input[name='cartRestaurantUuid']").val();
		if(cartRestaurantUuid){
			window.location = appPath+"/index/restaurantmenu?restaurantUuid="+cartRestaurantUuid;
		}
	})
	
	
	/*类型按钮 Delivery*/
	$("#deliveryButton").click(function(){
		$("input[name='orderType']").val(1);
		$("#orderType").val(1);
		loadOrderDate(1,"orderTime-day","orderTime-hourAndMinutes");
		initSelectedDeliveryAndPickUpOrderDate();
		newOrderType = 1;
		updateCartOrderType(newOrderType)
		refreshCart();
		$("#biaodan").attr("class","tab-pane active");
		$("#tianxie").attr("class","tab-pane");
		$("#dishDialogContent #cart-orderId").val("0");
	})
	/*类型按钮 Pick up*/
	$("#pickupButton").click(function(){
		$("#biaodan").attr("class","tab-pane active");
		$("#tianxie").attr("class","tab-pane");
		reservationOrderId = 0;
		$("input[name='orderType']").val(2);
		$("#orderType").val(2);
		newOrderType = 2;
		updateCartOrderType(newOrderType)
		refreshCart();
		loadOrderDate(2,"orderTime-day","orderTime-hourAndMinutes");
		initSelectedDeliveryAndPickUpOrderDate();
	})
	/*类型按钮 Dine in*/
	$("#dineInButton").click(function(){
		$("input[name='orderType']").val(3);
		$("#orderType").val(3);
		newOrderType = 3;
		updateCartOrderType(newOrderType)
		refreshCart();
		showReservationOrders ();
	})
	
	//初始化之前选择的日期
	function initSelectedDeliveryAndPickUpOrderDate(){
		var date = $.cookie("orderSelectDate");
		$("#orderTime-day").find("option").each(function(){
			if($(this).val() == date){
				$(this).attr("selected","selected");
				getOrderDayTime(date,$("#orderType").val(),"orderTime-hourAndMinutes");
				initSelectedDeliveryAndPickUpOrderDateHourAndMinutes();
			}
		});
	}
	//初始化之前选择的时：分
	function initSelectedDeliveryAndPickUpOrderDateHourAndMinutes(){
		var time = $.cookie("orderSelectTime");
		$("#orderTime-hourAndMinutes").find("option").each(function(){
			if($(this).val() == time){
				$(this).attr("selected","selected");
			}
		});
	}
	//初始化dinein下拉框中之前选择的reservation订单
	function initSelectedReservationOrder(){
		var reservationOrderId = $.cookie("selectReservationOrderId");
		$("#reservationOrderSelect").find("option").each(function(){
			if($(this).val() == reservationOrderId){
				$(this).attr("selected","selected");
			}
		})
	}
	
	//记录选择的orderTate时间
	function saveChooseOrderDate(orderType){
		if(orderType==3){//reservatrion,dine in
			var reservationOrderId = $("#reservationOrderSelect").val();
			$.cookie("selectReservationOrderId", reservationOrderId,{expires:10,path: '/' }); 
		}else{
			var date = $.trim($("#orderTime-day").val());
			var time = $.trim($("#orderTime-hourAndMinutes").val());
			//alert(date+"==="+time);
			$.cookie("orderSelectDate", date,{expires:10,path: '/' }); 
			$.cookie("orderSelectTime", time,{expires:10,path: '/' }); 
		}
	}
	
	//更新购物车的订单类型
	function updateCartOrderType(newOrderType){
		var cartId = $("input[name='cartId']").val();
		var oldCartType =  $("input[name='orderType']").val();
		if(oldCartType != orderType && cartId >0){
			$.ajax({
				type: 'post',
				async: false,
				url: appPath+'/consumers/updateCartOrderType',
				data: {"cartId":cartId,"orderType":newOrderType},
				success: function(data){
					var msg = $.parseJSON(data);
					if(msg.success){
					}
				}
			})
		}
	}

	
//-----------------------------------------------------验证方法----------------------------------------------------------------
	//显示表单验证提示信息的元素的字符串形式
	function addtips(name,str,x,y){
		/*参数说明：name:为当前的提示单元定义一个name属性(为表单元素的id值+"-tip")	,
				str:错误提示语句,
				x:相对于表单元素的相对定位水平偏移量（单位px,
				y:相对于表单元素的相对定位垂直偏移量（单位px）*/
		//提示框使用的class属性（即css样式，在commcss.css文件中定义了相应的样式名称）
		var easytipClass = "easytip";
		//提示框小三角的class属性
		var easytipArrowClass = "easytip-arrow";
		var tip = "<div name='" + name + "' style='z-index:3000;position:absolute;left:"+x+";top:"+y+";' title='tip'>"+
					"<div class='" + easytipClass + "'>"+str+"</div>"+
					"<div class='" + easytipArrowClass + "'></div>"+
				  "</div>"
		return tip;
	}
	//表单验证不通过时显示提示信息的方法
	function showtips(id,str,x,y){
		/*参数说明：id:当前表单原始的id属性的值
				str:错误提示语句,
				easytipClass:提示框使用的class属性（即css样式，在commcss.css文件中定义了相应的样式名称）,
				easytipArrowClass:提示框小三角的class属性,
				l:相对于表单元素的相对定位水平偏移量（单位px,
				t:相对于表单元素的相对定位垂直偏移量（单位px）*/
		var ele = document.getElementById(id);
		//得到提示框相对于当前表单的位置
	    var l=ele.offsetLeft+x+"px";
	    var t=ele.offsetTop+y+"px";
	    var newtip = addtips(id+"-tip",str,l,t);
	    $(ele).after(newtip);
	}
	//隐藏错误提示信息
	function hidetips(tipId){
		var name = tipId+"-tip";
		var tips = document.getElementsByName(name);
		$(tips).remove();
	}
	
	//验证firstName和lastName
	function nameValidate(elementId,x,y){
		var flag = false;
		var val = $.trim($("#"+elementId).val())
		if(val==""){
			flag=false;
			showtips(elementId,'Required',x,y);
		}else{
			flag = true;
			hidetips(elementId);
		}
		return flag;
	}
	//验证电话号码
	function phoneNumberValidate(elementId,x,y){
		var falg = false;
		var val = $.trim($("#"+elementId).val());
		if(val==""){
			flag=false;
			showtips(elementId,'Required',x,y);
		}else{
			flag = true;
			hidetips(elementId);
		}
		return flag;
	}
	//验证邮箱格式
	function loginEmailValidate(elementId,x,y){
		var flag = false;
		var emailRegExp = /^[a-zA-Z\d]+(\.[a-zA-Z\d]+)*@([\da-zA-Z](-[\da-zA-Z])?)+(\.{1,2}[a-zA-Z]+)+$/;
		var email = $.trim($("#"+elementId).val())
		if(email==""){
			flag=false;
			if(x&&y){
				showtips(elementId,'Required',x,y);
			}
			//参数说明: 表单元素id值,提示信息，横向偏移量，纵向偏移量，提示框的class属性值，提示框三角的class属性值，
		}else if(emailRegExp.test(email)){
			flag = true;
			if(x&&y){
				hidetips(elementId);
			}
		}else{
			flag=false;
			if(x&&y){
				showtips(elementId,'Invalid Email',x,y);
			}
		}
		return flag;
	}
	//验证人数 （正整数）
	function peopleNumberValidate(elementId,x,y){
		var flag = false;
		var numberRegExp = /^[0-9]*[1-9][0-9]*$/;
		var number = $.trim($("#"+elementId).val())
		if(number==""){
			flag=false;
			if(x&&y){
				showtips(elementId,'Required',x,y);
			}
			//参数说明: 表单元素id值,提示信息，横向偏移量，纵向偏移量，提示框的class属性值，提示框三角的class属性值，
		}else if(numberRegExp.test(number)){
			flag = true;
			if(x&&y){
				hidetips(elementId);
			}
		}else{
			flag=false;
			if(x&&y){
				showtips(elementId,'Invalid number',x,y);
			}
		}
		return flag;
	}
	
	//验证订桌的日期
	var isTodayReservation = false;//存储reservation订单是否是当天的订单,默认不是
	function reservationTimeValidate(elementId,x,y){
		var flag = false;
		var selectTime = $.trim($("#"+elementId).val());
		if(selectTime){
			
			var timeArray = selectTime.split(" ");//0:日   1: 月 2 年 5时分 6：AM/PM
			//January February  March  April  May  June  July  Aguest  September  October  November  December
			
			var selectDate = formatTime(timeArray);//获取用户选择的订桌时间，并转成标准日期格式 最后转换成毫秒数
			var nowTime = new Date();
			
			//判断是否是当天的reservation订单
			if(selectDate.getFullYear()==nowTime.getFullYear() && selectDate.getMonth()==nowTime.getMonth() && selectDate.getDate()==nowTime.getDate()){
				isTodayReservation = true;//如果年月日都相同，设置是否为当天订单为true
			}
			
			var tempMilliseconds = parseFloat(selectDate.getTime() - nowTime.getTime());
			if(tempMilliseconds>0 && tempMilliseconds/(parseFloat(1000*60*30))>=1){
				flag = true;
			}else{
				flag = false;
				showtips(elementId,'please select 30 minutes later',x,y);
			}
			
		}else{
			flag = false;
			showtips(elementId,'Required',x,y);
		}
		return flag;
	}
	
	//---------------------------------------------------Reservatin  订桌位--------------------------------------------------------

	var flag1 = false;//firstName
	var flag2 = false;//lastName
	var flag3 = false;//phone
	var flag4 = false;//email
	var flag5 = false;//time
	var flag6 = false;//peopleNumber
	
	/*reservation 预定座位的按钮点击事件*/
	$("button[name='reservation']").click(function(){
		resNewReservation = false;
		if(!$("#currentConsumerUuid").val()){//判断是否已经登录，如果没有登录弹出登录框
			$("#myModal").modal('show');
		}else{//如果登录了再点收藏按钮
			flag1 = false;//firstName
			flag2 = false;//lastName
			flag3 = false;//phone
			flag4 = false;//email
			flag5 = false;//time
			flag6 = false;//peopleNumber
			$("#reservationModal input").val("");//清除input内容
			$("#res-orderDay").empty();
			$("#res-orderHourAndMinutes").empty();
			$("#res-peopleNumber").empty();
			$("textarea").val("");//清除textarea内容
			$("#reservationModal div[title='tip']").remove();//清除错误提示
			
			$("#res-firstName").val($("#consumer-firstName").val());
			$("#res-lastName").val($("#consumer-lastName").val());
			$("#res-phone").val($("#consumer-phone").val());
			$("#res-email").val($("#consumer-email").val());
			loadOrderDate(3,"res-orderDay","res-orderHourAndMinutes");
			loadReserationPeopleNumber();
			$("#reservationModal").modal('show');
		}
	})
	
	$("#res-firstName").focus(function(){
		hidetips("res-firstName");//参数说明: 表单元素id值
	}).blur(function(){
		flag1 = nameValidate("res-firstName",110,44);
	})
	$("#res-lastName").focus(function(){
		hidetips("res-lastName");//参数说明: 表单元素id值
	}).blur(function(){
		flag2 = nameValidate("res-lastName",110,38);
	});
	$("#res-phone").focus(function(){
		hidetips("res-phone");
	}).blur(function(){
		flag3 = phoneNumberValidate("res-phone",110,38);
	});
	$("#res-email").focus(function(){
		hidetips("res-email");//参数说明: 表单元素id值
	}).blur(function(){
		flag4 = loginEmailValidate("res-email",110,38);
	});
	
	
	/*保存订桌信息*/
	
	function addReservation(type){
		var orderId;
		if(!flag1){
			flag1 = nameValidate("res-firstName",110,44);
		}
		if(!flag2){
			flag2 = nameValidate("res-lastName",110,50);
		}
		if(!flag3){
			flag3 = phoneNumberValidate("res-phone",110,38);
		}
		if(!flag4){
			flag4 = loginEmailValidate("res-email",110,38);
		}
		if($("#res-orderHourAndMinutes").val()==0){
			showtips("res-orderDay",'Please select another day time',110,44);
			flag5 = false;
		}else{
			flag5 = true;
		}

		if(flag1 && flag2 && flag3 && flag4 && flag5 && flag6){
			var resObj = new Object();
			resObj.firstName = $.trim($("#res-firstName").val());
			resObj.lastName = $.trim($("#res-lastName").val());
			resObj.phoneNumber = $.trim($("#res-phone").val());
			resObj.email = $.trim($("#res-email").val());
			resObj.firstName = $.trim($("#res-firstName").val());
			resObj.reservationTime = $.trim($("#res-orderDay").val()) + " " + $.trim($("#res-orderHourAndMinutes").val());
			resObj.peopleNumber = $.trim($("#res-peopleNumber").val());
			resObj.specialRequest = $.trim($("#res-specialRrequest").val());
			resObj.restaurantUuid = $("#restaurantUuid").val();//商家id
			resObj.consumerUuid = $("#currentConsumerUuid").val();//用户id
			resObj.orderType = 3;//订单类型，reservation：3
			var jsonResObj = JSON.stringify(resObj);
			$.ajax({
				type: 'post',
				async: false,
				url: appPath+"/consumers/addReservation",
				data: {"jsonResObj":jsonResObj},
				success: function(data){
					var msg = $.parseJSON(data);
					if(msg.success){//保存reservation成功
						orderId = msg.flag;
						$("#reservationOrderId").val(msg.flag);//设置当前reservation订单id到购物车上面的一个隐藏控件
						$.cookie("cart-orderId", msg.flag,{expires:10,path: '/' })
						$.cookie("currentReservationOrderId", msg.flag,{expires:10,path: '/' });  //设置商家Id到cookie
						$("#dineInButton").click();
					}else{
						//如果保存reservation不成功，就显示提示信息
						if(type==1){
							showtips("res-complete",'Error',350,48);
							setTimeout(function(){
								hidetips("res-complete");
								$("#sub").popover('destroy');
							},2000);
						}else if(type==2){
							showtips("res-toSelectFood",'Error',350,48);
							setTimeout(function(){
								hidetips("res-toSelectFood");
								$("#sub").popover('destroy');
							},2000);
						}
					}
				}
			})
		}
		return orderId;
	}
	
	/**调用时间数据加载reservation的可用桌位*/
	function loadReserationPeopleNumber(){
		//时间正确加载的情况下，加载桌位
		if($.trim($("#res-orderHourAndMinutes").val())!=0){
			var orderDate = $.trim($("#res-orderDay").val()) + " " + $.trim($("#res-orderHourAndMinutes").val());
			$.ajax({
				type: 'post',
				url: appPath+'/api/restaurant/tablecount',
				data:{"restaurantUuid":restaurantUuid,"orderDate":orderDate},// restaurantUuid,String orderDate
				success:function(data){
					var msg = $.parseJSON(data);
					if(msg.flag==1){//获取到有座位
						flag6 = true;//选座位为true
						hidetips("res-peopleNumber");
						var tableList = msg.body;//acceptanceNum;//桌位容纳人数
						var str = "";
						for ( var i=0; i<tableList.length; i++) {
							if(tableList[i].tableNum>0){
								str += "<option value= '"+tableList[i].acceptanceNum+"'>Table for "+tableList[i].acceptanceNum+"</option>";
							}
						}
						$("#res-peopleNumber").empty().append(str);
					}else{//当前时间没有桌位
						flag6 = false;//选座位为false，并提示没有可用座位
						showtips("res-peopleNumber",'No tables available at this time',110,38);
					}
				}
			})
		}
	}
	
	//选择Reservation时间
	$("#res-orderDay, #res-orderHourAndMinutes").change(function(){//tablecount
		orderTimeIsOk = false;//初始化订单的时间是否在店家营业时间内为false
		hidetips("res-peopleNumber");
		var orderTime = $.trim($("#res-time").val());
		var orderType=3;//订单类型为3
		if($("#res-orderHourAndMinutes").val()==0){//不在店家营业时间范围内
			showtips("res-orderDay",'Please select another day time',110,44);
			flag5 = false;
			return;
		}else{
			hidetips("res-orderDay");
			flag5 = true;
		}
	})
	
	/*订桌模态框点击   仅提交订桌*/
	$("#res-complete").click(function(){
		reservationOrderId = 0;
		resNewReservation = true;
		var orderId =  addReservation(1);
		if(orderId>0){//如果保存成功，orderId有值
			//$("#reservationOrderId").val();
			$("#bg").css("display", "none");
			$("#show").css("display", "none");
			if(isTodayReservation){//true： 如果是当天的订单,弹出等待商家确认的提示框
				$("#reservationModal").modal('hide');
				$("#waitAffirmModal").modal('show');//显示等待商家确认
			}else{//不是当天的，弹出询问是否限制点菜模态框
				$("#reservationModal").modal('hide');
				$("#isOrderDishModal").modal('show');//显示是否现在点菜
			}
		}
	})
	
	/*订桌模态框点击 提交订桌并点菜    ||  当不是当天的订单并选择现在点菜时*/
	$("#res-toSelectFood").click(function(){
		resNewReservation = true;
		isByNewReservation = true;
		var orderType=3;//订单类型为3
		var orderId = addReservation(2);//创建订单
		if(orderId>0){//如果保存成功，orderId有值并且大于0 
			reservationOrderId = orderId;
			$("#dishDialogContent #cart-orderId").val(orderId);
			$.cookie("cart-orderId", orderId,{expires:10,path: '/' });//保存reservation的订单id 
			$("#reservationOrderId").val(orderId);//设置当前保存成功的reservation订单Id到购物车上面的一个隐藏控件，用于checkout的时候将购物车关联得到该id的订单
			$("#reservationModal").modal('hide');
			$("#dineInButton").click();
		}
	})
	
	
	/*是当天订单  || 不是当天订单模态框中选择不点菜时，跳转到用户页面*/
	$("#closeWaitAffirm,#closeIsOrderDishModal").click(function(){
		window.location = appPath+"/index/user?flag=4";
	})
	
	/*reservation成功后是否点菜的去点菜按钮*/
	$("#isOrderDishToChooseDish").click(function(){
		$("#isOrderDishModal").modal('hide');
		orderType = 3;
		$("#dineInButton").click();
	})

	
	$("#myModal").on('hidden.bs.modal',function(){

	});
	
	/*转到下一个页面 填写收货人信息*/
	$("button[name='checkout']").click(function(){
		checkoutClick = true;
		orderTimeIsOk = false;//订单的时间是否在店家营业时间内
		//var cartOrderType = $("#orderType").val();//获取到购物车的类型，如果购物车为空取到的值为0,Delivery：1 ; Pick Up :2 ;Reservation:3
		var cartOrderType = $("input[name='orderType']").val();
		var orderId = $("#reservationOrderSelect option:selected").val();
		var minDeliveryPrice = parseFloat($.trim($("#minDeliveryFee").text()));
		var subTotal =  parseFloat($.trim($("#subTotal").text()));
		var orderTime ;
		if(cartOrderType == 3){//reservation的情况
			
			orderTimeIsOk = false;
			if(orderId==0||orderId == undefined){
				$("div[name='checkoutInfo-text']").text("Please selecte an reservation order");
				$("#checkoutInfo").attr("style","display:block;");
				setTimeout(function(){
					$("#checkoutInfo").attr("style","display:none;");
				},3000);
				return;
			}else{
				orderTime = $.trim($("#reservationOrderSelect").find('option:selected').text().split(" ---")[0]);
			}
		}else if($("#orderTime-hourAndMinutes").val()==0){//未选择订餐时间
			
			$("div[name='checkoutInfo-text']").text("Please selecte another day time");
			$("#checkoutInfo").attr("style","display:block;");
			setTimeout(function(){
				$("#checkoutInfo").attr("style","display:none;");
			},3000);
			orderTimeIsOk = false;
			return;
		}else {
			orderTime = $.trim($("#orderTime-day").val())+" "+$.trim($("#orderTime-hourAndMinutes").val());
		}
		if(!$("#currentConsumerUuid").val()){//判断是否已经登录，如果没有登录弹出登录框
			$("#myModal").modal('show');
			return;
		}else if(parseFloat($("#subTotal").text())<=0){//未点菜
			$("div[name='checkoutInfo-text']").text("Your order is empty");
			$("#checkoutInfo").attr("style","display:block;");
			setTimeout(function(){
				$("#checkoutInfo").attr("style","display:none;");
			},3000);
			return;
		}else if(cartOrderType==1 && subTotal<minDeliveryPrice){//Delivery订单时，菜金未达到最低外送价格时不给下单
			$("div[name='checkoutInfo-text']").text("Min. delivery order price: $ "+minDeliveryPrice);
			$("#checkoutInfo").attr("style","display:block;");
			setTimeout(function(){
				$("#checkoutInfo").attr("style","display:none;");
			},3000);
			return;
		}else if(orderTimeIsOk){//不在店家营业时间范围内
			$("div[name='checkoutInfo-text']").text("The restaurant is not open at the selected time");
			$("#checkoutInfo").attr("style","display:block;");
			setTimeout(function(){
				$("#checkoutInfo").attr("style","display:none;");
			},5000);
			return;
		}else{
			$("#bg").css("display", "block");//显示等待遮罩层
			$("#show").css("display", "block");
			//保存菜品到购物车并调到填地址页面
			var subTotal = $("#subTotal").text();
			var deliveryFee = $("#deliveryFee").text();
			var salesTax = parseFloat($("#salesTax").text());
			var cartTotal = parseFloat(salesTax) + parseFloat(subTotal);
			var consumerUuid = $("#currentConsumerUuid").val();
			var cartOrderType2 = $("input[name='orderType']").val();
			$.cookie("subTotal", subTotal,{expires:10,path: '/' });//设置菜品的总金额到cookie用于后一个页面计算配送费
			saveChooseOrderDate(cartOrderType);//保存当前选中的时间，用于其他页面点击chang your order 时初始化购物车的时间
			$.ajax({
				type: 'post',
				url: appPath+'/consumers/checkout',
				data: {"dishFee":subTotal,"logistics":deliveryFee,"tax":salesTax,"total":cartTotal,"consumerUuid":consumerUuid,"orderTime":orderTime,"orderId":orderId,"orderType":cartOrderType2},
				success: function(data){
					var msg = $.parseJSON(data);
					if(msg.success){
						$("#bg").css("display", "none");//隐藏等待层
						$("#show").css("display", "none");
						if(cartOrderType!=3){
							window.location = appPath+"/index/regist";
									//consumerId=+$("#currentConsumerId").val();cartId="+msg.flag;
						}else{
							window.location = appPath+"/payment/getPayment";
						}
					}
				}
			})
		}
	});
	
	$("#reservationOrderSelect").change(function(){//修改选择一个reservation订单时间，将其保存到购物车中去
		reservationOrderId=$(this).val();
		$("#reservationOrderId").val(reservationOrderId);
		$.cookie("currentReservationOrderId", reservationOrderId,{expires:10,path: '/' });  //设置商家Id到cookie
	})
});


/*购物车的JS*/
$(function(){
	var checkoutClick = false;//用于判断是否是点击了checkout才显示的登录模态框
	
	
	/*自运行匿名函数，获取购物车的订单类型，并将获取到的类型赋值到restaurantMenu.jsp页面的id="cartContent"元素的name属性上;
	 * 用于比较新加的菜品订单类型是否与元购物车中的订单类型相同*/
	(function getOrderType(){
		var cartOrderType = $("input[name='orderType']").val();
		if(cartOrderType==1){//如果购物车中的类型是1：Delivery
			$("#deliveryButton").click();
		}else if(cartOrderType==3){//如果购物车中的类型是1：Dine in
			$("#dineInButton").click();
		}//其他情况0：默认是Pick up 如果是2：也是默认pick up
		$("#cartContent").attr("name",cartOrderType);
	})()
	
	/*删除一个购物车条目*/
	$("#cartContent").on("click", "img[name='deleteCartDish']", function(){
	//$("img[name='deleteCartDish']").click(function(){
		$("#bg").css("display", "block");
		$("#show").css("display", "block");
		var cartItemId = $(this).parent().find("input[name='cartItemId']").val();
		$.ajax({
			type: 'post',
			url: appPath+'/consumers/deleteCartItem',
			data:{"cartItemId":cartItemId},
			success:function(data){
				var msg = $.parseJSON(data)
				if(msg.success){
					refreshCart();
				}
			}
		})
	})
	
	/*修改一个购物车条目*/
	$("#cartContent").on("click", "img[name='editCartDish'], img[name='cartItem-image']", function(){
	//$("img[name='editCartDish'],#cartItem-image").click(function(){
		var cartItemId = $(this).parent().find("input[name='cartItemId']").val();
		var didshId = $(this).parent().find("input[name='dishId']").val();
		$("#bg").css("display", "block");
		$("#show").css("display", "block");
		$("#dishDialogContent").empty();
		$.ajax({//第一步加载点击的菜品，并弹出dish模态框
			type:'post',
			url:appPath+'/restaurantMenu/getOneDish',
			data:{"dishId":didshId},
			success:function(data2){
				$("#dishDialogContent").html(data2);
				$("#myModal1").modal('show');
				$("#bg").css("display", "none");
				$("#show").css("display", "none");
				$.ajax({//显示初始菜品后异步请求购物车中的当前菜品的数据，并修改所有选项
					type:'post',
					url:appPath+'/consumers/getOneCartItem',
					data: {cartItemId:cartItemId},
					success: function(data){
						var cartItemObject = $.parseJSON(data);
						var countEditCheckbox = 0;
						var newGarnishId;
						var oldGarnishId;
						//console.log(cartItemObject);//调试时显示出购物车条目对象
						$("#cartItemId").val(cartItemId);
						$("#dishRemark").text(cartItemObject.dishRemark);
						if(cartItemObject.num!=1){//
							$("#aDishQuantity").val(cartItemObject.num);
						}
						var aDishPrice = parseFloat($("#orginalDishPrice").val());
						var subItems = cartItemObject.subItem;//购物车中选中的配菜的集合，有数量和id两个属性
						var elements = $("div[name='dishGarnish'] input, div[name='dishGarnish'] select");
						for(var i = 0;i<subItems.length;i++){//购物车数据循环
							var subItem = subItems[i];
							
							for(var j= 0;j<elements.length;j++){//页面元素循环
								var element = elements[j];//获取页面的元素
								var elementTagName = $(element).prop("tagName");
								if(elementTagName=="INPUT" || elementTagName =="input"){//如果当前元素是radio、checkbox
									if($(element).val()==subItem.garnishItemId){//如果元素的value属性等于购物车中的配菜id，就将这个元素选中
										
										$(element).prop("checked",true);
										var tempPrice1 = $(element).parent().parent().parent().next().find("#addPrice").text();//增加价格
										if(tempPrice1){//如果有加价就加上
											aDishPrice += parseFloat(tempPrice1);
										}
									}
								}else if(elementTagName=="SELECT" || elementTagName =="select"){//如果当前元素是select
									if($(element).attr("value")==subItem.garnishItemId){
										$(element).find("option").each(function(){
											if(subItem.count == $(this).val()){
												var tempPrice2 = $(element).parent().parent().parent().next().find("#addPrice").text();//增加价格
												if(tempPrice2){//如果有加价就加上
													aDishPrice += parseFloat(tempPrice2)*parseFloat(subItem.count);
												}
												$(this).prop("selected",true);
											}
										})
									}
								}
							}
						}
						
						$("span[name='price']").text((aDishPrice).toFixed(2));//显示计算后的总单价
						$("#aDishTotal").text((aDishPrice * cartItemObject.num).toFixed(2));//显示总 单价*数量=总价
						$("#subDish").attr("name","edit");//标记菜品的提交按钮为修改购物车
						
						$("#dishDialog div[alt='1']").each(function(){
							var garnishGroupId = $(this).attr("id")
							var checkboxElements = $("input[name='"+garnishGroupId+"']");
							var checkedNum = 0;
							for(var m = 0;m<checkboxElements.length;m++){
								var currentEle = checkboxElements[m];
								if($(currentEle).prop("checked")){
									checkedNum++;
								}
							}
							var maxNum = checkboxElements.eq(0).parent().attr("alt");
							if(maxNum<=checkedNum){
								for(var m=0;m<checkboxElements.length;m++){
									var currentEle = checkboxElements[m];
									if(!$(currentEle).prop("checked")){
										$(currentEle).attr("disabled","disabled");
									}
								}
							}
						})
						
		    		}
				});
			}
		})
	})
	
	function showFreeDish(dishId){
		$.ajax({
			type: 'post',
			url: appPath+"/restaurantMenu/getDiscountDish",
			data: {"dishId":dishId},
			success: function(data){
				var msg = $.parseJSON(data);
				if(msg){
					var photoshopUrl = appPath+"/index/images/restaurst09.jpg";
					if(msg.photoUrl){
						photoshopUrl = msg.photoUrl;
					}
					var str = "<div class='row' style='vertical-align:middle;'>"+
						"<div class='col-md-3'>"+
							"<img src="+photoshopUrl+" style='height: 60px;' "+
							"onerror=javascript:this.src='"+appPath+"/images/no-picture.jpg'>"+
						"</div>"+
						"<div class='col-md-6 youmian-miaobian'>"+
							"<div align='center'>"+
								"<strong>"+msg.enName+"</strong>"+
							"</div>"+
							"<div class='zhucyemian-miaodi'></div>"+
							"<p>"+
								"<input type='hidden' name='dishId'  value="+msg.id+">"+
							"</p>"+
						"</div>"+
						"<div class='col-md-3' style='padding-left: 15px;'>"+
							"<div style='vertical-align: middle;'>"+
							"<div class='row'>"+
								"<div class='col-md-12' style='padding-top: 7px;'>"+
								"<span>Qty: </span> <span>1</span>"+
								"</div>"+
							"</div>"+
							"<div class='row'>"+
								"<div class='col-md-12' style='padding-top: 7px;'>"+
									"<span>$ </span>"+
									"<span>0.00</span>"+
								"</div>"+
							"</div>"+
							"</div>"+
						"</div>"+
						"</div>"+
						"<div class='zhucyemian-miaodi'></div>";
					$("div[name='discount-dish-content']").empty().append(str);
				}
				
			}
		})
	}

	/*优惠券信息点击事件的修改方法*/
	function editDiscount (newDiscountObj, oldDiscountObj){//选择优惠券
		$("#bg").css("display", "block");//显示等待遮罩层
		$("#show").css("display", "block");
		var taxRate = parseFloat($("#taxRate").val());
		//console.log("taxRate:"+taxRate);
		if(!oldDiscountObj){//如果是第一次点击
			if(newDiscountObj.type == 1){ //现金抵用券
				$("#subTotal").text((parseFloat($("#hiddenSubTotal").val()) - parseFloat(newDiscountObj.price)).toFixed(2));
			}else if(newDiscountObj.type == 2){//打折券
				var tempTotal = parseFloat($("#hiddenSubTotal").val());//获取隐藏的原始购物车的菜品的总价
				//console.log("tempTotal:"+tempTotal);
				//console.log("newDiscountObj.discount:"+(tempTotal * newDiscountObj.discount));
				//console.log("discount:"+newDiscountObj.discount);
				$("#subTotal").text((Math.round((tempTotal-(tempTotal*parseFloat(newDiscountObj.discount))/100)*100)/100).toFixed(2));
			}else if(newDiscountObj.type == 3){//赠送菜
				var dishId = newDiscountObj.dishId;
				showFreeDish(dishId);
			}
		}else{//如果是第二次点击重新选择其他优惠券
			$("div[name='discount-dish-content']").empty();//用于存放赠送的菜品
			if(newDiscountObj.type == 1){ //现金抵用券
				$("#subTotal").text((parseFloat($("#hiddenSubTotal").val()) - parseFloat(newDiscountObj.price)).toFixed(2));
			}else if(newDiscountObj.type == 2){//打折券
				var tempTotal = parseFloat($("#hiddenSubTotal").val());//获取隐藏的原始购物车的菜品的总价
				//console.log("tempTotal:"+subTotal);
				//console.log("newDiscountObj.discount:"+newDiscountObj.discount);
				$("#subTotal").text((Math.round((tempTotal-(tempTotal*parseFloat(newDiscountObj.discount))/100)*100)/100).toFixed(2));
			}else if(newDiscountObj.type == 3){//赠送菜
				$("#subTotal").text($("#hiddenSubTotal").val());
				var dishId = newDiscountObj.dishId;
				showFreeDish(dishId);
			}
		}
		//根据优惠后的菜品总价（Subtotal)计算 Delivery Fee:  Sales Tax:  TOTAL:
		var subTotal = parseFloat($("#subTotal").text());//获取菜品总价
		var taxFee = (Math.round(subTotal*taxRate*100)/100).toFixed(2);
		var deliveryFe = parseFloat($("#deliveryFee").text());
		
		$("#deliveryFee").text(deliveryFe.toFixed(2));//重新设置配送费
		$("#salesTax").text(taxFee);//重新设置税额
		$("#cartTotal").text((subTotal+parseFloat(taxFee)).toFixed(2));//重新设置 最终金额
		$("#bg").css("display", "none");
		$("#show").css("display", "none");
	}
	
	var oldDiscountId;
	var newDiscountId;
	var discountType;//如果是3：赠送菜品 需要刷新购物车
	//title保存的优惠券的类型  1、现金抵用券 2、打折券 3、赠送菜品
	
	/*radio 类型的优惠券信息点击事件*/
	$("#cartContent").on("mousedown", "input[name='discountItem']", function(){
		$("input[name='discountItem']").each(function(){
			if($(this).prop("checked")==true){
				oldDiscountId = $(this).val();//获取原先的优惠券Id
				discountType = $(this).attr("title");//获取优惠券的优惠类型 1、现金抵用券 2、打折券 3、赠送菜品
				//console.log("oldId:"+oldDistanceId);
			}
		});
	}).on("click","input[name='discountItem']", function(){
		$("input[name='discountItem']").each(function(){
			if($(this).prop("checked")==true){
				newDiscountId = $(this).val();//获取新点击的优惠券Id
				if( $(this).attr("title")==3){
					discountType = $(this).attr("title");
				}
				if(oldDiscountId != newDiscountId){
					var consumerUuid = $("#currentConsumerUuid").val();
					$.ajax({
						type:'post',
						url:appPath+"/consumers/chooseDiscount",
						async: false,
						data:{"oldDiscountId":oldDiscountId,"newDiscountId":newDiscountId, "consumerUuid":consumerUuid},
						success: function(data){
							var msg = $.parseJSON(data);
							var newDiscountObj = null;
							var oldDiscountObj = null;
							if(msg.length == 2){//说明有new有old
								if(newDiscountId == msg[0].id){
									newDiscountObj = msg[0];
									oldDiscountObj = msg[1];
								}else{
									newDiscountObj = msg[1];
									oldDiscountObj = msg[0];
								}
							}
							if(msg.length == 1){//说明只有new，是第一次选择
								newDiscountObj = msg[0];
							}
							editDiscount (newDiscountObj, oldDiscountObj)
						}
					})
				}
			}
		})
	});
	$("#cartContent").on("click", "a[name='cart-restaurantName']", function(){
		var cartRestaurantUuid = $("input[name='restaurantUuid']").val();
		var currentRestaurantUuid = $("#restaurantUuid").val();//当前餐厅id
		
		if(cartRestaurantUuid!=currentRestaurantUuid){
			$("#getoCartRestaurant").submit();
		}
	});

	(function getCartId (){
		var cartRestaurantUuid = $("input[name='cartRestaurantUuid']").val();
		//console.log("cartRestaurantUuid:"+cartRestaurantUuid);
		if(cartRestaurantUuid){
			//alert(cartRestaurantUuid);
			$.cookie("cartRestaurantUuid", cartRestaurantUuid,{expires:10,path: '/' });  //设置购物车的商家Id到cookie
		}else{
			$.cookie("cartRestaurantUuid", "0",{expires:10,path: '/' });
		}
	})();
	
	var consumerLng = $.cookie("map-Lng");//? $.cookie("map-Lng") : $.cookie("browser-Lng");
	var consumerLat = $.cookie("map-Lat");//? $.cookie("map-Lat") : $.cookie("browser-Lat");
	/*刷新购物车方法*/
	function refreshCart (){
		var consumerUuid = $("#currentConsumerUuid").val();
		var newOrderType = $("input[name='orderType']").val();
		$("#bg").css("display", "block");//显示等待遮罩层
		$("#show").css("display", "block");
		$.ajax({
    		type: 'post',
    		url: appPath+'/consumers/showCart',
    		data: {"consumerLng":consumerLng,"consumerLat":consumerLat,"consumerUuid":consumerUuid,"orderType":newOrderType},
    		success: function(data){
    			$("#bg").css("display", "none");
    			$("#show").css("display", "none");
    			$("#cartContent").html(data);
    			var cartRestaurantUuid = $("#cartContent input[name='cartRestaurantUuid']").val();
    			
    			//以下初始化购物车按钮 10月10日改
    			cartOrderType = $("input[name='orderType']").val();
    			if(cartOrderType=="1"){//delivery
    				$("li[name='type-delivery']").attr("class","active");
    				$("li[name='type-dinein']").removeAttr("class");
    				$("li[name='type-pickup']").removeAttr("class");
    				$("#biaodan").attr("class","tab-pane active");
    				$("#tianxie").attr("class","tab-pane");
    			}else if(cartOrderType=="3"){//dine in
    				$("li[name='type-pickup']").removeAttr("class");
    				$("li[name='type-delivery']").removeAttr("class");
    				$("li[name='type-dinein']").attr("class","active");
    				$("#tianxie").attr("class","tab-pane active");
    				$("#biaodan").attr("class","tab-pane");
    				showReservationOrders ();
    			}
    		}
    	})
	}
})

$(function(){
	
	var total= 0;//数据总条数
	var offset = 1;//当前页码 初始为第一页
	var limit = 15;//每页记录数
	
	function initPaginatior(currentPage){//参数 currentPage：当前页
		var options = {
				currentPage: currentPage,//当前页
				totalPages: Math.ceil(total/limit),//总页数
				numberOfPages:5,//显示出来的按钮个数
				size:'normal',
				alignment:'right',
				onPageClicked: function(event, originalEvent, type, page){
					getPagingDatas(url,page,limit,statusType)
				}
		}
		$('#paging').bootstrapPaginator(options);
	}
	//异步获取订单列表
	function getPagingDatas(url, restaurantUuid, currentPage, limit){
		$.ajax({
			type:'post',
			url: url,
			async:false,
			data: {"restaurantUuid":restaurantUuid, "offset":currentPage, "limit":limit},
			success:function(data){
				/*$("#bg").css("display", "none");//隐藏等待层
				$("#show").css("display", "none");*/
				$("#sun-page-div").empty().html(data);
				total = $("#total1").val();
				initPaginatior(currentPage);//分页初始化方法
			}
		})
	}
})

