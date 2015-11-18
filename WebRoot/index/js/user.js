$(function(){
//-------------------------------分页使用的全局变量-------------------------------------
	var consumerId = $("#currentConsumerId").val();//当前用户Id
	var total= 0;//数据总条数
	var offset = 1;//当前页码 初始为第一页
	var limit = 15;//每页记录数
	var statusType ;//订单状态类型 1：当前订单  2：历史订单
	var url ;//获取分页的订单内容
	var currentOrderId ;//当前订单Id
	var stars = 5;//定义用于保存选中的评分五角星的序号
	var editPasswordFlag = false;//修改密码用的标记
	var currentFavouriteId;//当前收藏Id
	var currentAddressId; //当前维护的地址的Id
	var currentCardId; //当前的信用卡Id
	var refundOrderSuccess = false;//退单退款成功标记
	
	$("a[name='logo']").click(function(){
		window.location = appPath+"/index/index";
	});
	//获取当前用户Id
	function Consumer(){
		//this.consumerId = $("#currentConsumerId").val();
	}
	//从其他页面快捷跳入当前页时选择了那个选项，打开页面时跳到对应选项卡
	(function(){
		var clickFlag = $("#clickFlag").val();
		$("#tabs li").removeAttr("class");
		if(clickFlag == 1){
			$("a[href='#Profile']").parent().attr("class","active");
			profilClick();
			//$("a[href='#Profile']").click();
		}else if(clickFlag == 2){
			$("a[href='#Addresses']").parent().attr("class","active");
			addressClick();
			//$("a[href='#Addresses']").click();
		}else if(clickFlag == 3){
			$("a[href='#Payments']").parent().attr("class","active");
			paymentClick();
			//$("a[href='#Payments']").click();
		}else if(clickFlag == 4){
			$("a[href='#Reservations']").parent().attr("class","active");
			currentOrdersClick()
			//ordersClick();
		}else if(clickFlag == 5){
			$("a[href='#orders']").parent().attr("class","active");
			pastOrdersClick();
			//ordersClick();
		}else if(clickFlag == 6){
			$("a[href='#Favourites']").parent().attr("class","active");
			favouritesClick();
			//$("a[href='#Favorites']").click();
		}else{
			$("a[href='#Profile']").parent().attr("class","active");
			profilClick();
		}
	})()
	//左侧导航按钮
	$("a[href='#Profile']").click(function(){
		profilClick();
	});
	$("a[href='#Addresses']").click(function(){
		addressClick();
	})
	$("a[href='#Payments']").click(function(){
		paymentClick();
	})
	$("a[href='#orders']").click(function(){
		pastOrdersClick();
	})
	$("a[href='#Reservations']").click(function(){
		currentOrdersClick();
	})
	$("a[href='#Favourites']").click(function(){
		favouritesClick();
	})
	
	
//----------------------------------------表单验证提示信息的显示和隐藏---------------------------------------------------	
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
		var tip = "<div name='" + name + "' style='position:absolute;left:"+x+";top:"+y+";z-index:8000' title='tip'>"+
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
//---------------------------------------------基础信息维护---------------------------------------------------
	//修改用户基础信息
	var updatesuccess = false;
	function editConsumer(consumer){
		updatesuccess = false;
		var jsonConsumer = JSON.stringify(consumer); 
		$.ajax({
			type: 'post',
			async: false,
			url: appPath+'/consumers/editConsumerBasisInfo',
			data: {context:jsonConsumer},
			success: function(data){
				var msg = $.parseJSON(data);
				updatesuccess = msg.success;
			}
		})
	}
	//基础信息修改Edit按钮
	$("#pageContent").on("click","#name-edit", function(){
		//alert($("p[name='name-show']").text());
		hidetips("input-firstName");
		hidetips("input-lastName");
		hidetips("name-save");
		$("#input-firstName").val("");$("#input-lastName").val("");
		$(this).css("display","none");
		$("div[name='name-edit-div']").css("display","block");
	})
	$("#pageContent").on("click","#email-edit",function(){
		hidetips("input-email");
		$("#input-email").val("");
		$(this).css("display","none");
		$("div[name='email-edit-div']").css("display","block");
	})
	$("#pageContent").on("click","#phone-edit",function(){
		hidetips("input-phone");
		hidetips("phone-save");
		$("#input-phone").val("");
		$(this).css("display","none");
		$("div[name='phone-edit-div']").css("display","block");
	})
	$("#pageContent").on("click","#password-edit", function(){
		hidetips("input-password0");
		hidetips("input-password1");
		hidetips("input-password2");
		$("#input-password0").val("");
		$("#input-password1").val("");
		$("#input-password2").val("");
		$(this).css("display","none");
		$("div[name='password-edit-div']").css("display","block");
	})
	$("#pageContent").on("click","#specislRequest-edit",function(){
		$("#specislRequest-text").val("");
		$(this).css("display","none");
		$("div[name='specislRequest-edit-div']").css("display","block");
	})
	//基础信息取消Cancle按钮
	$("#pageContent").on("click", "#name-cancel", function(){
		$("#name-edit").css("display","block");
		$("div[name='name-edit-div']").css("display","none");
	})
	$("#pageContent").on("click", "#email-cancel", function(){
		$("#email-edit").css("display","block");
		$("div[name='email-edit-div']").css("display","none");
	})
	$("#pageContent").on("click", "#phone-cancel", function(){
		$("#phone-edit").css("display","block");
		$("div[name='phone-edit-div']").css("display","none");
	})
	$("#pageContent").on("click", "#password-cancel", function(){
		$("#password-edit").css("display","block");
		$("div[name='password-edit-div']").css("display","none");
	})
	$("#pageContent").on("click", "#specislRequest-cancel", function(){
		$("#specislRequest-edit").css("display","block");
		$("div[name='specislRequest-edit-div']").css("display","none");
	})

//------------------------------------基础信息    保存  Save按钮------------------------------------------------
	
	$("#pageContent").on("focus","#input-firstName",function(){
		hidetips("input-firstName");
	})
	$("#pageContent").on("focus","#input-lastName",function(){
		hidetips("input-lastName");
	})
	$("#pageContent").on("click","#name-save", function(){
		var firstName = $.trim($("#input-firstName").val());
		var lastName = $.trim($("#input-lastName").val());
		if(firstName.length==0){
			showtips("input-firstName",'Required',20,38);
			return;
		}
		if(lastName.length==0){
			showtips("input-lastName",'Required',160,38);
			return;
		}
		if(firstName!="" && lastName!=""){
			var consumer = new Consumer();
			consumer.firstName = firstName;
			consumer.lastName = lastName;
			editConsumer(consumer);
			if(updatesuccess){
				$("p[name='name-show']").text($("#input-firstName").val()+" "+$("#input-lastName").val());
				$("#name-edit").css("display","block");
				$("div[name='name-edit-div']").css("display","none");
			}else{
				showtips("name-save",'Edit name error',20,38);
			}
		}
	})
	
	//修改Email
	$("#pageContent").on("focus","#input-email",function(){
		hidetips("input-email");
	})
	$("#pageContent").on("click","#email-save",function(){
		var orginalEmail = $.trim($("#orginal-email").text());
		var newEmail = $.trim($("#input-email").val());
		var emailRegExp = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
		if(newEmail.length==0){
			showtips("input-email","Required",20,38);
		}else if(orginalEmail==newEmail){
			$("div[name='email-edit-div']").css("display","none");
			$("#email-edit").css("display","block");
		}else if((emailRegExp.test(newEmail))){//邮箱非空，和原来的不一样，格式正确时后台验证是否已经被使用
			$.ajax({
				type: 'post',
				url: appPath+"/consumers/validateExistAndSaveEmail",
				data:{email:newEmail},
				success: function(data){
					var msg = $.parseJSON(data);
					//alert(msg.success);
					if(msg.success){
						$("#orginal-email").text(newEmail);
						$("div[name='email-edit-div']").css("display","none");
						$("#email-edit").css("display","block");
					}else{//如果出错
						showtips("input-email",msg.errorMsg,20,38);
					}
				}
			})
		}else{
			showtips("input-email",'Invalid Email',20,38);
		}
	})
	
	//修改Phone
	$("#pageContent").on("focus","#input-phone",function(){
		hidetips("input-phone");
		
	})
	$("#pageContent").on("click","#phone-save",function(){
		var orginalPhone = $.trim($("#orginal-phone").text());
		var newPhone = $.trim($("#input-phone").val());
		var phoneExpReg = /(^[0-9]{3}\ ?[0-9]{3}-?[0-9]{4}$)|(^[0-9]{3}-[0-9]{3}-?[0-9]{4}$)|(^\([0-9]{3}\)\ ?[0-9]{3}-?[0-9]{4}$)/;
		if(newPhone.length==0){
			showtips("input-phone","Required",20,38);
		}else if(orginalPhone==newPhone){
			$("div[name='phone-edit-div']").css("display","none");
			$("#phone-edit").css("display","block");
		}else if(phoneExpReg.test(newPhone)){//正则表达式验证填写的电话格式正确
			var consumer = new Consumer();
			consumer.phone = newPhone;
			editConsumer(consumer);
			if(updatesuccess){
				$("#orginal-phone").text(newPhone);
				$("#phone-edit").css("display","block");
				$("div[name='phone-edit-div']").css("display","none");
			}else{
				showtips("input-phone","Edit error",20,38);
			}
		}else{//号码非空，和原来的不一样，但格式不正确
			showtips("input-phone","Not a phone",20,38);
		}
	})
	
	//修改密码
	
	$("#pageContent").on("blur","#input-password0",function(){
		var p0 = $.trim($("#input-password0").val());
		if(p0.length<6){//长度不合理
			showtips("input-password0","Please typing at least 6 characters",20,38);
		}else{
			$.ajax({//验证原密码是否正确
				type:'post',
				url: appPath+'/consumers/verifyPassword',
				data: {password:p0},
				success:function(data){
					var msg = $.parseJSON(data);
					if(msg.success){
						editPasswordFlag = true;
					}else{
						showtips("input-password0","Wrong password",20,38);
						editPasswordFlag = false;
					}
				}
			})
		}
	}).on("focus","#input-password0",function(){
		hidetips("input-password0");
	});
	
	$("#pageContent").on("focus","#input-password1",function(){
		hidetips("input-password1");
	});
	$("#pageContent").on("focus","#input-password2",function(){
		hidetips("input-password2");
	});
	
	$("#pageContent").on("click","#password-save",function(){
		var password1 = $.trim($("#input-password1").val());
		var password2 = $.trim($("#input-password2").val());
		if(password1.length==0){
			showtips("input-password1","Required",20,38);
			return;
		}else if(password1.length<6){
			showtips("input-password1","Please typing at least 6 characters",20,38);
			return
		}
		if(password2.length==0){
			showtips("input-password2","Required",20,38);
			return;
		}else if(password2.length<6){
			showtips("input-password2","Please enter at least 6 characters",20,38);
			return;
		}
		if(password1 != password2){
			showtips("input-password2","The two password don't match",20,38);
			return;
		}
		if(editPasswordFlag){
			var consumer = new Consumer();
			consumer.password = password2;
			editConsumer(consumer);
			if(updatesuccess){
				$("#password-edit").css("display","block");
				$("div[name='password-edit-div']").css("display","none");
			}else{
				showtips("password-save","Edit password error",20,38);
			}
		}
	});
	
	//修改个人备注
	$("#pageContent").on("click", "#specislRequest-save", function(){//specislRequest-save
		var consumer = new Consumer();
		consumer.specislRequest = $("#specislRequest-text").val();
		editConsumer(consumer);
		if(updatesuccess){
			$("p[name='specislRequest-show']").text($("#specislRequest-text").val());
			$("#specislRequest-edit").css("display","block");
			$("div[name='specislRequest-edit-div']").css("display","none");
		}else{
			//alert("Error");
			showtips("specislRequest-save","Edit special requests error",20,38);
		}
	})
	
//-------------------------------------------------左侧导航栏点击后调用的右侧显示的方法---------------------------------------------
	function profilClick(){
		$("#bg").css("display", "block");//显示等待层
		$("#show").css("display", "block");
		$.ajax({
			type: 'post',
			url: appPath+"/consumers/editBasisInfoPage",
			success:function(data){
				$("#bg").css("display", "none");//隐藏等待层
				$("#show").css("display", "none");
				$("#no-paging-div-content").html(data);
				$("#no-paging-div-content").attr("style","display:block");
				if($("#paging-div-content").attr("style")!="display:none"){
					$("#paging-div-content").attr("style","display:none");
				}
			}
		})
	}
	function addressClick(){
		$("#bg").css("display", "block");//显示等待层
		$("#show").css("display", "block");
		$.ajax({
			type: 'post',
			url: appPath+"/consumers/getAddressPage",
			success:function(data){
				$("#bg").css("display", "none");//隐藏等待层
				$("#show").css("display", "none");
				$("#no-paging-div-content").html(data);
				$("#no-paging-div-content").attr("style","display:block");
				if($("#paging-div-content").attr("style")!="display:none"){
					$("#paging-div-content").attr("style","display:none");
				}
			}
		})
	}
	function paymentClick(){
		$("#bg").css("display", "block");//显示等待层
		$("#show").css("display", "block");
		$.ajax({
			type: 'post',
			url: appPath+"/consumers/getPaymentPage",
			success:function(data){
				$("#bg").css("display", "none");//隐藏等待层
				$("#show").css("display", "none");
				$("#no-paging-div-content").html(data);
				$("#no-paging-div-content").attr("style","display:block");
				if($("#paging-div-content").attr("style")!="display:none"){
					$("#paging-div-content").attr("style","display:none");
				}
			}
		})
	}
	function currentOrdersClick(){
		url = appPath + '/consumers/getCurrentAndPastOrders';
		statusType = "1";
		getPagingDatas(url,offset,limit,statusType);
		$("#paging-div-content").attr("style","display:block");
		if($("#no-paging-div-content").attr("style")!="display:none"){
			$("#no-paging-div-content").attr("style","display:none");
		}
	}
	function pastOrdersClick(){
		url = appPath + '/consumers/getCurrentAndPastOrders';
		statusType = "2";
		getPagingDatas(url,offset,limit,statusType);
		$("#paging-div-content").attr("style","display:block");
		if($("#no-paging-div-content").attr("style")!="display:none"){
			$("#no-paging-div-content").attr("style","display:none");
		}
	}
	
	function favouritesClick(){
		url = appPath+"/consumers/getFavouritesPage",
		getPagingDatas(url,offset,limit,statusType);
		$("#paging-div-content").attr("style","display:block");
		if($("#no-paging-div-content").attr("style")!="display:none"){
			$("#no-paging-div-content").attr("style","display:none");
		}
	}
	

	
	
//-------------------------------------------------分页控件------------------------------------------------------------
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
	function getPagingDatas(url,currentPage,limit,statusType){
		$("#bg").css("display", "block");//显示等待层
		$("#show").css("display", "block");
		
		$.ajax({
			type:'post',
			url: url,
			async:false,
			data: {"offset":currentPage, "limit":limit,"statusType":statusType},
			success:function(data){
				$("#bg").css("display", "none");//隐藏等待层
				$("#show").css("display", "none");
				$("#sun-page-div").empty().html(data);
				total = $("#total1").val();
				initPaginatior(currentPage);//分页初始化方法
			}
		})
	}
	
//---------------------------------------------order 每条数据点击弹出详细信息--------------------------------------------------------------
	$("#pageContent").on("click","button[name='orderDetail']",function(){
		$("#bg").css("display", "block");//显示等待层
		$("#show").css("display", "block");
		
		currentOrderId = $(this).val();//设置当前订单Id到全局变量currentOrderId
		$("#opererate-info").empty();
		$("#operate-info").empty();
		$.ajax({
			type: 'post',
			url: appPath + '/consumers/getOneOrderDetail',
			data: {orderId:currentOrderId},
			success: function(data){
				$("div[name='ordreDetailDiv']").html(data);
				$("#bg").css("display", "none");//隐藏等待层
				$("#show").css("display", "none");
				$("#orderDetailModal").modal('show');
			}
		})
		
	})
	
	
//----------------------------------------------------订单模态框按钮事件---------------------------------------------------------
	//订单详情模态框 cancel按钮
	$("#orderDetailModal").on("click","button[name='modal-cancel']",function(){
		$("#orderDetailModal").modal('hide');
		if(refundOrderSuccess){
			$("a[href='#Reservations']").click();
		}
	})
	
	//订单详情模态框   重复下单按钮
	$("#orderDetailModal").on("click","button[name='modal-repeat']",function(){
		$("#bg").css("display", "block");//显示等待层
		$("#show").css("display", "block");
		$.ajax({
			type:'post',
			url:appPath+'/consumers/repeatOrder',
			data:{orderId:currentOrderId},
			success:function(data){
				
				var msg = $.parseJSON(data);
				if(msg.success){//成功，跳转到商家选菜页面
					$("input[name='restaurantId']").val(msg.flag);
					$("#repeat-goto-restaurantMenu").submit();
				}else{//显示错误信息
					$("#bg").css("display", "none");//隐藏等待层
					$("#show").css("display", "none");
					var str = "<div class='row' style='margin-top: 15px;'>"
				 		+ "<div class='col-md-12' align='center'>"
				 			+"<span id='refundSuccess' style='font-weight: bold;color:red;font-size:16px;'>"
				 				+msg.errorMsg
				 			+"</span>"
			 			+"</div>"
			 		 +"</div>";
					$("#opererate-info").empty().append(str);
				}
			}
		})
		//重复下单，现在不需要再次确认了
		/*showtips("confirmModal-confirm","Repeat Order failed",50,48);
		setTimeout(function(){
			hidetips("confirmModal-confirm");
		},2000);*/
		/*$("#myModalLabel-confirmOperation").text("Repeat Order");
		$("span[name='operationInfoDiv']").text("Do you want to repeat this order? ");
		$("button[name='confirmModal-confirm']").val("1");
		$("div[name='ordreDetailDiv']").css("display","none");
		$("#confirmModal").modal('show');*/
	})
	
	//订单详情模态框    退款按钮
	$("#orderDetailModal").on("click","button[name='modal-refund']",function(){
		refundOrderSuccess = false;
		$("#myModalLabel-confirmOperation").text("Cancel Order");
		$("span[name='operationInfoDiv']").text("Do you want to cancel this order?");
		$("button[name='confirmModal-confirm']").val("2");
		$("#refundSuccess").parent().parent().parent().css("display","none");
		$("div[name='ordreDetailDiv']").css("display","none");
		$("div[name='orderDetailModal-2']").css("width","600px");
		$("#opererate-info").empty();
		$("#confirmModal").modal('show');
	})
	
	//订单详情模态框    接受排队取号按钮
	$("#orderDetailModal").on("click","button[name='modal-confirm-lineup']",function(){
		submitConfirmOperator(3, currentOrderId);
		//$("#myModalLabel-confirmOperation").text("Accept the queue number");
		//$("span[name='operationInfoDiv']").text("Please confirm your acceptance of the queue number!");
		//$("button[name='confirmModal-confirm']").val("3");
		//$("div[name='ordreDetailDiv']").css("display","none");
		//$("#confirmModal").modal('show');
		//$("#lineupModal").modal('show');
	})
	//订单详情模态框    拒绝排队取号按钮
	$("#orderDetailModal").on("click","button[name='modal-cancel-lineup']",function(){
		//submitConfirmOperator(3, currentOrderId);
		$("#bg").css("display", "block");//隐藏等待层
		$("#show").css("display", "block");
		$.ajax({
			type: 'post',
			async: false,
			url: appPath+'/payment/refundByOrder',
			data: {orderId:currentOrderId},
			success:function(data){
				$("#bg").css("display", "none");//隐藏等待层
				$("#show").css("display", "none");
				var msg = $.parseJSON(data);
				if(msg.success){//成功，显示退款成功
					refundOrderSuccess = true;
					$("div[name='orderDetailModal-2']").css("width","800px");
					var str = "<div class='row' style='margin-top: 15px;'>"
						 		+ "<div class='col-md-12' align='center'>"
						 			+"<span id='refundSuccess' style='font-weight: bold;color:#7DD700;font-size:16px;'>"
						 				+"Your order has been cancelled."
						 			+"</span>"
					 			+"</div>"
					 		 +"</div>";
					 $("#opererate-info").empty().append(str);
					 $("button[name='modal-confirm']").attr("disabled","disabled");
					 //取消订单成功刷新页面
					 $("button[name='modal-confirm-lineup']").attr("disabled","disabled");
					 $("button[name='modal-cancel-lineup']").attr("disabled","disabled");
					 setTimeout(function(){
						 $("a[href='#Reservations']").click();
						 $("#orderDetailModal").modal('hide');
					 },1000);
					 //currentOrdersClick();
				}else{//显示错误信息
					//$("#confirmModal").modal('hide');
					$("div[name='orderDetailModal-2']").css("width","800px");
					var str = "<div class='row' style='margin-top: 15px;'>"
						 		+ "<div class='col-md-12' align='center'>"
						 			+"<span id='refundSuccess' style='font-weight: bold;color:red;font-size:16px;'>"
						 				+"Cancelled failed!"
						 			+"</span>"
					 			+"</div>"
					 		 +"</div>";
					$("#opererate-info").empty().append(str);
					setTimeout(function(){
						$("#opererate-info").empty();
					 },1000);
				}
			}
		})
	})
	
	/*$("button[name='lineupModal-accept']").click(function(){
		
	})*/
	
	//订单详情模态框    不接受排队取号按钮
	//$("#orderDetailModal").on("click","button[name='modal-reject']",function(){
	//$("button[name='lineupModal-reject']").click(function(){
		//alert("erer");
		/*$("span[name='operationInfoDiv']").text("Please confirm reject this line up order !");
		$("button[name='confirmModal-confirm']").val("4");
		$("div[name='ordreDetailDiv']").css("display","none");
		$("#confirmModal").modal('show');*/
		
			//var currentDate = new Date().getTime();
			//var orderDateStr = $("#orderDetail-orderDate").val();
			//orderDateStr = orderDateStr.replace(/-/g,"/");
			//var orderDate = new Date(orderDateStr).getTime();
			//if((orderDate - currentDate) >= 1000*60*30){//新的就餐时间和当前时间相比如果小于半个小时，不给取消
				
			/*}else{
				$("#lineupModal").modal('hide');
				//$("div[name='orderDetailModal-2']").css("width","800px");
				var str = "<div class='row' style='margin-top: 15px;'>"
					 		+ "<div class='col-md-12' align='center'>"
					 			+"<span id='refundSuccess' style='font-weight: bold;color:red;font-size:16px;'>"
					 				+"Refund failed, Orderdate relative to the current time less than half an hour!"
					 			+"</span>"
				 			+"</div>"
				 		 +"</div>";
				$("#opererate-info").empty().append(str);
				$("#opererate-info").attr("style","width: 760px; display: block;");
				$("#bg").css("display", "none");//隐藏等待层
				$("#show").css("display", "none");
			}*/
		
		
	//})
	
	//订单详情模态框  评分按钮
	$("#orderDetailModal").on("click","button[name='modal-rating']",function(){
		$("div[name='ordreDetailDiv']").css("display","none");
		$("span[alt='1']").text("★").css("color","#7DD700");
		$("span[alt='2']").text("★").css("color","#7DD700");
		$("span[alt='3']").text("★").css("color","#7DD700");
		$("span[alt='4']").text("★").css("color","#7DD700");
		$("span[alt='5']").text("★").css("color","#7DD700");
		$("#rating_text").val("");
		$("#ratingModal").modal('show');
	})
	
	//评分评论模态框   提交评分 
	$("button[name='ratingModal-submit']").click(function(){
		var review = $.trim($("#rating_text").val());
		var restaurantId = $("input[name='modal-restaurantId']").val();
		$.ajax({
			type: 'post',
			url: appPath+'/consumers/rating',
			data: {orderId:currentOrderId, restaurantId:restaurantId, stars:stars, review:review},
			success: function(data){
				var msg = $.parseJSON(data);
				if(msg.success){//发表评论成功
					$("button[name='modal-rating']").attr("name","modal-cancel").text("Close");
					$("button[name='ratingModal-cancel']").click();
				}else{
					showtips("ratingModal-submit","Rating failed",50,48);
					setTimeout(function(){
						hidetips("ratingModal-submit");
					},2000);
				}
			}
		})
	})
	
	//信息确认模态框  确认操作的按钮
	$("button[name='confirmModal-confirm']").click(function(){
		$("#bg").css("display", "block");//显示等待层
		$("#show").css("display", "block");
		var operatorType = $(this).val();
		submitConfirmOperator(operatorType, currentOrderId, currentFavouriteId, currentAddressId,currentCardId);
	})
	//信息确认模态框  cancel按钮
	$("button[name='confirmModal-cancel']").click(function(){
		$("#confirmModal").modal('hide');
	})
	
	//信息确认模态框关闭时  触发展开显示订单详情模态框内容
	$('#confirmModal').on('hidden.bs.modal', function() {
		$("div[name='ordreDetailDiv']").css("display","block");
		$("div[name='orderDetailModal-2']").css("width","800px");
	})
	//评论模态框关闭  触发展开显示订单详情模态框内容
	$('#ratingModal').on('hidden.bs.modal', function() {
		$("div[name='ordreDetailDiv']").css("display","block");
	})
	
	//评论模态框关闭  触发展开显示订单详情模态框内容
	$('#confirmModal').on('show.bs.modal', function() {
		$("button[name='confirmModal-cancel']").removeAttr("disabled");
	})
	//评论模态框关闭  触发展开显示订单详情模态框内容
	$('#lineupModal').on('hidden.bs.modal', function() {
		$("div[name='ordreDetailDiv']").css("display","block");
	})
	
	//operatorType 1:重复下单   2：退款   3：接受lineup(排队) 4：删除用户收藏  5：删除用户地址  6:删除用户信用卡
	function submitConfirmOperator(operatorType, orderHeaderId, favouriteId, addressId,cardId){
	
		if(operatorType=="1"){
			/*$.ajax({
				type:'post',
				url:appPath+'/consumers/repeatOrder',
				data:{orderId:currentOrderId},
				success:function(data){
					$("#bg").css("display", "none");//隐藏等待层
					$("#show").css("display", "none");
					var msg = $.parseJSON(data);
					if(msg.success){//成功，跳转到商家选菜页面
						$("input[name='restaurantId']").val(msg.flag);
						$("#repeat-goto-restaurantMenu").submit();
					}else{//显示错误信息
						showtips("confirmModal-confirm","Repeat Order failed",50,48);
						setTimeout(function(){
							hidetips("confirmModal-confirm");
						},2000);
					}
				}
			})*/
		}else if(operatorType=="2"){
			var currentDate = new Date().getTime();
			var orderDateStr = $("#orderDetail-orderDate").val();
			orderDateStr = orderDateStr.replace(/-/g,"/");
			var orderDate = new Date(orderDateStr).getTime();
			if((orderDate - currentDate) >= 1000*60*30){
				$("button[name='confirmModal-confirm']").attr("disabled",true);
				$.ajax({
					type: 'post',
					async: false,
					url: appPath+'/payment/refundByOrder',
					data: {orderId:currentOrderId},
					success:function(data){
						$("#bg").css("display", "none");//隐藏等待层
						$("#show").css("display", "none");
						var msg = $.parseJSON(data);
						if(msg.success){//成功，显示退款成功
							refundOrderSuccess = true;
							$("#confirmModal").modal('hide');
							$("div[name='orderDetailModal-2']").css("width","800px");
							$("button[name='modal-refund']").attr("disabled","disabled");
							
							var str = "<div class='row' style='margin-top: 15px;'>"
								 		+ "<div class='col-md-12' align='center'>"
								 			+"<span id='refundSuccess' style='font-weight: bold;color:#7DD700;font-size:16px;'>"
								 				+"Your order has been cancelled."
								 			+"</span>"
							 			+"</div>"
							 		 +"</div>";
							 $("#opererate-info").empty().append(str);
							 //取消订单成功刷新页面
							 setTimeout(function(){
								 $("a[href='#Reservations']").click();
							 },2000);
							 //currentOrdersClick();
						}else{//显示错误信息
							$("#confirmModal").modal('hide');
							$("div[name='orderDetailModal-2']").css("width","800px");
							var str = "<div class='row' style='margin-top: 15px;'>"
								 		+ "<div class='col-md-12' align='center'>"
								 			+"<span id='refundSuccess' style='font-weight: bold;color:red;font-size:16px;'>"
								 				+"Refund failed!"
								 			+"</span>"
							 			+"</div>"
							 		 +"</div>";
							$("#opererate-info").empty().append(str);
						}
					}
				})
			}else{
				$("#confirmModal").modal('hide');
				$("div[name='orderDetailModal-2']").css("width","800px");
				var str = "<div class='row' style='margin-top: 15px;'>"
					 		+ "<div class='col-md-12' align='center'>"
					 			+"<span id='refundSuccess' style='font-weight: bold;color:red;font-size:16px;'>"
					 				+"It is too late to cancel your order now!"
					 			+"</span>"
				 			+"</div>"
				 		 +"</div>";
				$("#opererate-info").empty().append(str);
				//$("#opererate-info").attr("style","width: 760px; display: block;");
				$("#bg").css("display", "none");//隐藏等待层
				$("#show").css("display", "none");
			}
		}else if(operatorType=="3"){
			$.ajax({
				type:'post',
				url:appPath+'/api/consumer/savelineup',
				data:{orderId:currentOrderId},
				success:function(data){
					$("#bg").css("display", "none");//隐藏等待层
					$("#show").css("display", "none");
					var msg = $.parseJSON(data);
					if(msg.flag==1){//成功，确认接受排队
						
						//$("#lineupModal").modal('hide');
						var str = "<div class='row' style='margin-top: 15px;'>"
							 		+ "<div class='col-md-12' align='center'>"
							 			+"<span id='refundSuccess' style='font-weight: bold;color:#7DD700;font-size:16px;'>"
							 				+"Success!"
							 			+"</span>"
						 			+"</div>"
						 		 +"</div>";
						$("#opererate-info").empty().append(str);
						$("button[name='modal-confirm-lineup']").attr("disabled","disabled");
						$("button[name='modal-cancel-lineup']").attr("disabled","disabled");
						setTimeout(function(){
							 $("a[href='#Reservations']").click();
							 $("#orderDetailModal").modal('hide');
						 },1000);
					}else{//显示错误信息
						//$("#lineupModal").modal('hide');
						var str = "<div class='row' style='margin-top: 15px;'>"
					 		+ "<div class='col-md-12' align='center'>"
					 			+"<span id='refundSuccess' style='font-weight: bold;color:red;font-size:16px;'>"
					 				+"Accept order failed"
					 			+"</span>"
				 			+"</div>"
				 		 +"</div>";
						$("#opererate-info").empty().append(str);
						setTimeout(function(){
							$("#opererate-info").empty();
						},2000);
					}
				}
			})
		}else if(operatorType=="4"){
			$("#bg").css("display", "none");//隐藏等待层
			$("#show").css("display", "none");
			favouriteId
			$.ajax({
				type:'post',
				url:appPath+'/consumers/deleteConsumerFavourite',
				data:{favouriteId:favouriteId},
				success:function(data){
					var msg = $.parseJSON(data);
					if(msg.success){//成功
						$("#confirmModal").modal('hide');
						favouritesClick();
					}else{//失败
						$("#confirmModal").modal('hide');
					}
				}
			})
		}else if(operatorType=="5"){
			$("#bg").css("display", "none");//隐藏等待层
			$("#show").css("display", "none");
			$.ajax({
				type:'post',
				url:appPath+'/consumers/deleteAddress',
				data:{addressId:currentAddressId},
				success:function(data){
					var msg = $.parseJSON(data);
					if(msg.success){//成功
						$("#confirmModal").modal('hide');
						addressClick();
					}else{//失败
						$("#confirmModal").modal('hide');
					}
				}
			})
		}else if(operatorType=="6"){
			$("#bg").css("display", "none");//隐藏等待层
			$("#show").css("display", "none");
			$.ajax({
				type: 'post',
				url: appPath+'/payment/deleteCard',
				data:{cardId:cardId,consumerId:consumerId},
				success:function(data){
					var msg = $.parseJSON(data);
					if(msg.success){//成功
						$("#confirmModal").modal('hide');
						paymentClick();
					}else{//失败
						$("#confirmModal").modal('hide');
					}
				}
			})
		}
	}
	
//-----------------------------------------------------删除收藏--------------------------------------------------------
	$("#pageContent").on("click","button[name='deleteFavourite']",function(){
		$("span[name='operationInfoDiv']").text("Please confirm delete this favourite !");
		$("button[name='confirmModal-confirm']").val("4");
		currentFavouriteId = $(this).val();
		$("div[name='ordreDetailDiv']").css("display","none");
		$("#confirmModal").modal('show');
	})
	
	
	function editStar(stars){//修改标星的方法
		if(stars=="1"){
			$("span[alt='1']").empty().append("★").css("color","#7DD700");
			$("span[alt='2']").empty().append("☆").css("color","#7DD700");
			$("span[alt='3']").empty().append("☆").css("color","#7DD700");
			$("span[alt='4']").empty().append("☆").css("color","#7DD700");
			$("span[alt='5']").empty().append("☆").css("color","#7DD700");
		}else if(stars=="2"){
			$("span[alt='1']").empty().append("★").css("color","#7DD700");
			$("span[alt='2']").empty().append("★").css("color","#7DD700");
			$("span[alt='3']").empty().append("☆").css("color","#7DD700");
			$("span[alt='4']").empty().append("☆").css("color","#7DD700");
			$("span[alt='5']").empty().append("☆").css("color","#7DD700");
		}else if(stars=="3"){
			$("span[alt='1']").empty().append("★").css("color","#7DD700");
			$("span[alt='2']").empty().append("★").css("color","#7DD700");
			$("span[alt='3']").empty().append("★").css("color","#7DD700");
			$("span[alt='4']").empty().append("☆").css("color","#7DD700");
			$("span[alt='5']").empty().append("☆").css("color","#7DD700");
		}else if(stars=="4"){
			$("span[alt='1']").empty().append("★").css("color","#7DD700");
			$("span[alt='2']").empty().append("★").css("color","#7DD700");
			$("span[alt='3']").empty().append("★").css("color","#7DD700");
			$("span[alt='4']").empty().append("★").css("color","#7DD700");
			$("span[alt='5']").empty().append("☆").css("color","#7DD700");//595959
		}else if(stars=="5"){
			$("span[alt='1']").empty().append("★").css("color","#7DD700");
			$("span[alt='2']").empty().append("★").css("color","#7DD700");
			$("span[alt='3']").empty().append("★").css("color","#7DD700");
			$("span[alt='4']").empty().append("★").css("color","#7DD700");
			$("span[alt='5']").empty().append("★").css("color","#7DD700");
		}else{
			$("span[alt='1']").empty().append("☆").css("color","#7DD700");
			$("span[alt='2']").empty().append("☆").css("color","#7DD700");
			$("span[alt='3']").empty().append("☆").css("color","#7DD700");
			$("span[alt='4']").empty().append("☆").css("color","#7DD700");
			$("span[alt='5']").empty().append("☆").css("color","#7DD700");
		}
	}
	
	$("span[name='star'").mouseover(function(){//鼠标移入五角星
		var s = $(this).attr("alt");
		editStar(s);
	});
	$("span[name='star'").mouseout(function(){//鼠标移出五角星
		editStar(stars);
	});
	$("span[name='star']").click(function(){//五角星评分的点击事件
		stars = $(this).attr("alt");
		editStar(stars);
	});
	
//---------------------------------------------地址维护----------------------------------------------------
	var addressInputFlagName = false;
	var addressInputFlagStreet = false;
	var addressInputFlagCity = false;
	var addressInputFlagProvince = false;
	var addressInputFlagPhone = false;
	//新增地址按钮
	$("#pageContent").on("click","#add-new-address", function(){
		var cc = $("div[name='address-div']").clone();
		cc.attr("id","current-address-div");
		$("div[name='new-address-content']").append(cc);
		$("#current-address-div").css("display","block");
		$(this).attr("disabled","disabled");
		
		$("#current-address-div input[name='input-addressId']").attr("id","current-input-addressId");
		$("#current-address-div input[name='input-name']").attr("id","current-input-name");
		$("#current-address-div input[name='input-floor']").attr("id","current-input-floor");
		$("#current-address-div input[name='input-street']").attr("id","current-input-street");
		$("#current-address-div input[name='input-city']").attr("id","current-input-city");
		$("#current-address-div input[name='input-province']").attr("id","current-input-province");
		$("#current-address-div input[name='input-phone']").attr("id","current-input-phone");
		$("button[name='address-edit']").attr("disabled","disabled");
		$("button[name='address-remove']").attr("disabled","disabled");
		addressInputFlagName = false;
		addressInputFlagStreet = false;
		addressInputFlagCity = false;
		addressInputFlagProvince = false;
		addressInputFlagPhone = false;
	})
	
	//删除地址
	$("#pageContent").on("click","button[name='address-remove']", function(){
		currentAddressId = $(this).val();
		$("#myModalLabel-confirmOperation").text("Delete Address");
		$("span[name='operationInfoDiv']").text("Do you want to delete this address?");
		$("button[name='confirmModal-confirm']").val("5");
		$("#confirmModal").modal('show');
	})
	
	//地址编辑按钮
	$("#pageContent").on("click","button[name='address-edit']", function(){
		addressInputFlagName = false;
		addressInputFlagStreet = false;
		addressInputFlagCity = false;
		addressInputFlagProvince = false;
		addressInputFlagPhone = false;
		
		currentAddressId =$(this).val();
		var index = $(this).attr("alt");
		$.ajax({
			type: 'post', 
			url: appPath+'/consumers/getAddressDetail',
			data: {addressId:currentAddressId},
			success: function(data){
				var msg = $.parseJSON(data);
				if(msg.addressId){
					
					var editDivName = "address-edit-content-"+index;
					var cc = $("div[name='address-div']").clone();
					cc.attr("id","current-address-div");
					$("div[name='"+editDivName+"']").append(cc);
					$("#current-address-div").css("display","block");
					$("button[name='address-edit']").attr("disabled","disabled");
					$("button[name='address-remove']").attr("disabled","disabled");
					$("#add-new-address").attr("disabled","disabled");
					
					$("#current-address-div input[name='input-addressId']").val(msg.addressId).attr("id","current-input-addressId");
					$("#current-address-div input[name='input-name']").val(msg.consignee).attr("id","current-input-name");
					$("#current-address-div input[name='input-floor']").val(msg.floor).attr("id","current-input-floor");
					$("#current-address-div input[name='input-street']").val(msg.street).attr("id","current-input-street");
					$("#current-address-div input[name='input-city']").val(msg.city).attr("id","current-input-city");
					$("#current-address-div input[name='input-province']").val(msg.province).attr("id","current-input-province");
					$("#current-address-div input[name='input-phone']").val(msg.phone).attr("id","current-input-phone");
					if(msg.isDefault==1){
						$("#current-address-div input[name='input-defaultAddress']").attr("checked","checked");
					}
				}
			}
		})
	})
	
	//取消地址编辑按钮
	$("#pageContent").on("click","button[name='edit-address-cancel']", function(){
		$("#current-address-div").remove();
		$("button[name='address-edit']").removeAttr("disabled");
		$("button[name='address-remove']").removeAttr("disabled");
		$("#add-new-address").removeAttr("disabled");
	})
	
	$("#pageContent").on("blur","#current-address-div input[name='input-name']",function(){
		if($.trim($(this).val()).length==0){
			showtips("current-input-name",'Required',130,38);
			addressInputFlagName = false;
		}else{
			addressInputFlagName = true;
		}
	}).on("focus","#current-address-div input[name='input-name']",function(){
		hidetips("current-input-name");
	})
	$("#pageContent").on("blur","#current-address-div input[name='input-street']",function(){
		if($.trim($(this).val()).length==0){
			showtips("current-input-street",'Required',130,38);
			addressInputFlagStreet = false;
		}else{
			addressInputFlagStreet = true;
		}
	}).on("focus","#current-address-div input[name='input-street']",function(){
		hidetips("current-input-street");
	})
	$("#pageContent").on("blur","#current-address-div input[name='input-city']",function(){
		if($.trim($(this).val()).length==0){
			showtips("current-input-city",'Required',130,38);
			addressInputFlagCity = false;
		}else{
			addressInputFlagCity = true;
		}
	}).on("focus","#current-address-div input[name='input-city']",function(){
		hidetips("current-input-city");
	})
	$("#pageContent").on("blur","#current-address-div input[name='input-province']",function(){
		if($.trim($(this).val()).length==0){
			showtips("current-input-province",'Required',130,38);
			addressInputFlagProvince = false;
		}else{
			addressInputFlagProvince = true;
		}
	}).on("focus","#current-address-div input[name='input-province']",function(){
		hidetips("current-input-province");
	})
	$("#pageContent").on("blur","#current-address-div input[name='input-phone']",function(){
		if($.trim($(this).val()).length==0){
			showtips("current-input-phone",'Required',130,38);
			addressInputFlagPhone = false;
		}else{
			addressInputFlagPhone = true;
		}
	}).on("focus","#current-address-div input[name='input-phone']",function(){
		hidetips("current-input-phone");
	})
	
	$("#pageContent").on("click","button[name='edit-address-save']", function(){
		if(!addressInputFlagName && $("#current-address-div input[name='input-name']").val().length==0){
			hidetips("current-input-name");
			showtips("current-input-name",'Required',130,38);
			return;
		}else{
			addressInputFlagName = true;
		}
		if(!addressInputFlagStreet && $("#current-address-div input[name='input-street']").val().length==0){
			hidetips("current-input-street");
			showtips("current-input-street",'Required',130,38);
			return;
		}else{
			addressInputFlagStreet = true;
		}
		if(!addressInputFlagCity && $("#current-address-div input[name='input-city']").val().length==0){
			hidetips("current-input-city");
			showtips("current-input-city",'Required',130,38);
			return;
		}else{
			addressInputFlagCity = true;
		}
		if(!addressInputFlagProvince && $("#current-address-div input[name='input-province']").val().length==0){
			hidetips("current-input-province");
			showtips("current-input-province",'Required',130,38);
			return;
		}else{
			addressInputFlagProvince = true;
		}
		if(!addressInputFlagPhone && $("#current-address-div input[name='input-phone']").val().length==0){
			hidetips("current-input-phone");
			showtips("current-input-phone",'Required',130,38);
			return;
		}else{
			addressInputFlagPhone = true;
		}
		
		if(addressInputFlagName && addressInputFlagStreet && addressInputFlagCity && addressInputFlagProvince && addressInputFlagPhone){
			//是否选为默认地址  （选中：true 不选：false）
			var checkDefault = ($("#current-address-div input[name='input-defaultAddress']").prop("checked"));
			//当前地址在数据库重的id （新增的没有值）
			var addressId = $("#current-input-addressId").val();
			var name = $.trim($("#current-address-div input[name='input-name']").val());
			var floor = $.trim($("#current-address-div input[name='input-floor']").val());
			var street = $.trim($("#current-address-div input[name='input-street']").val());
			var city = $.trim($("#current-address-div input[name='input-city']").val());
			var province = $.trim($("#current-address-div input[name='input-province']").val());
			var phone = $.trim($("#current-address-div input[name='input-phone']").val());
			$.ajax({
				type: 'post',
				url: appPath+'/consumers/addOrUpdateAddress',
				data: {addressId:addressId, floor:floor,street:street,city:city,province:province,phone:phone,name:name,checkDefault:checkDefault},
				success: function(data){
					var msg = $.parseJSON(data);
					if(msg.success){
						addressClick();
					}else{
						$("#current-address-div div[name='save-address-errorInfo']").css("display","block");
					}
				}
			})
		}
	})

//----------------------------------------------------信用卡信息维护-------------------------------------------------------------
	
	$("#pageContent").on("click","#add-new-payment", function(){
		
		$("div[name='new-payment-div']").css("display","block");
		$(this).attr("disabled","disabled");
		$("button[name='payment-remove']").attr("disabled","disabled");
	})
	
	$("#pageContent").on("click","button[name='add-payment-cancel']", function(){
		$("#number").val("");
		$("#year").val("");
		$("#month").val("");
		$("#cvv").val("");
		$("div[name='new-payment-div']").css("display","none");
		$("button[name='payment-remove']").removeAttr("disabled");
		$("#add-new-payment").removeAttr("disabled");
	})
	
	$("#pageContent").on("click","button[name='payment-remove']", function(){
		currentCardId = $(this).val();
		$("#myModalLabel-confirmOperation").text("Delete credit card");
		$("span[name='operationInfoDiv']").text("Are you sure you want to delete this credit card ?");
		$("button[name='confirmModal-confirm']").val("6");
		$("#confirmModal").modal('show');
		
	})
	
	//-----------------------------------------------------------------------------------------------
	$("#pageContent").on("click","button[name='add-payment-save']", function(){
		$("#bg").css("display", "block");//显示等待层
		$("#show").css("display", "block");
		var number = $("#pageContent #number").val();
		var month = $("#pageContent #month").val();
		var year = $("#pageContent #year").val();
		var cvv = $("#pageContent #cvv").val();
		$.ajax({
			type: 'post',
			url: appPath+"/api/consumer/stripe/addCard",
			data: {number:number,exp_month:month,exp_year:year,cvc:cvv,consumerId:consumerId},
			success: function(data){
				$("#bg").css("display", "none");//隐藏等待层
				$("#show").css("display", "none");
				var msg = $.parseJSON(data);
				if(msg.flag!=1){
					$("div[name='save-card-errorInfo']").text(msg.resultMessage);
					$("#pageContent div[name='save-card-errorInfo']").css("display","block");
					setTimeout(function(){
						$("#pageContent div[name='save-card-errorInfo']").css("display","none");
					},3000);
				}else{
					$("#confirmModal").modal('hide');
					paymentClick();
				}
			}
		})
	})
	
	$("#pageContent").on("click","span[name='favourite-restaurantName']", function(){
		var restaurantId = $(this).attr("id");
		window.location = appPath+'/index/restaurantmenu?restaurantId='+restaurantId;
	})
	
	
	
	
	
});