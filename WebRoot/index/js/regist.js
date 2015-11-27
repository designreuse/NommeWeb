$(function(){
	$("a[name='logo']").click(function(){
		window.location = appPath+"/index/index";
	});
	
	$("#statementPage").click(function(){
		window.location = appPath+"/index/statement";
	});
	
	
	var checkOutOrderType = $("#checkOutOrderType").val();//获取购物车的订单类型
	if(checkOutOrderType==1){//订单类型为delivery时，显示填写地址信息
		$("#deliveryAddressContent").css("display","block");
	}
	
//------------------------------------------如果是去了支付页面再返回填地址的页面，此处获取之前用户填写的地址信息-------------------------------------------------------
	var sessionCartAddress = $.trim($("#sessionCartAddress").text());
	var orgCartAddress = null;
	if(sessionCartAddress.length>0){
		orgCartAddress = $.parseJSON(sessionCartAddress);
		//console.log(orgCartAddress);
	}
	
	var consumerUuid = $("#currentConsumerUuid").val();
	var restaurantUuid = $.cookie("restaurantUuid");
	$("button[name='changeOrder']").click(function(){
		window.location = appPath+"/index/restaurantmenu?restaurantUuid="+restaurantUuid;
	});
	
	function refreshCart (){
		if(consumerUuid){
			$.ajax({
				type: 'post',
				async: false,
				url: appPath+'/consumers/showRegistCart',
				data: {consumerUuid:consumerUuid},
				success: function(data){
					$("#cartContent").html(data);
				}
			})
		}
	}
	refreshCart();
	
	var newDeliveryFee = 0;
	var addressList = null;
	var mapStreet ;//= $.cookie("map-street");//首页街名路名
	var mapCity ;//= $.cookie("map-city");//首页城市
	var mapProvince ;//= $.cookie("map-province");//首页省
	var mapFullAddress = mapStreet+" "+mapCity+" "+mapProvince;//$.cookie("indexAddress");//首页传过来的地址
	var mapLng;//= $.cookie("map-Lat");//首页传的经度
	var mapLat;// = $.cookie("map-Lng");//首页传的纬度
	var taxRate = $.cookie("taxRate");//商家税率
	var consumerName = $("#address-name").val();
	var consumerPhone = $("#address-phone").val();
	var currentAddressId;//当前select选中的地址的Id
	
//----------------------------------------获取用户在商家配送范围内的地址加载到下拉框------------------------------------------------------
	function uploadAddressSelect(){
		//var indexAddress = $.cookie("indexAddress");
		//var lat = $.cookie("Lat");
		//var lng = $.cookie("Lng");
		if(consumerUuid){//判断是否登录
			$.ajax({
				type: 'post',
				async: false,
				url: appPath+'/consumers/getConsumersAddressList',
				data: {consumerUuid:consumerUuid},
				success: function(data){
					if(data){
						addressList = $.parseJSON(data);
						var content = $("#addressList");
						/*if(mapFullAddress){//如果获得了首页的地址，就将首页的地址加到
							strOptions0 = "<option value='0'>"+mapFullAddress+"</option>";
							//$("#address-address").val(holdAddress);
							$("#address-street").val(mapStreet);
							$("#address-city").val(mapCity);
							$("#address-province").val(mapProvince);
						}*/
						var orgAddressOption = "";
						if(orgCartAddress && orgCartAddress.city && orgCartAddress.street && orgCartAddress.province){//判断如果是返回到的regist页面，能获取到之前填写的地址信息
							//orgAddressOption = "<option value='-1'>"+orgCartAddress.street+" "+orgCartAddress.floor+" "+orgCartAddress.city+" "+orgCartAddress.province+" &nbsp;&nbsp;(Last time address)</option>";
							$("#address-street").val(orgCartAddress.street);
							$("#address-city").val(orgCartAddress.city);
							$("#address-province").val(orgCartAddress.province);
							$("#address-floor").val(orgCartAddress.floor);
							$("#memo").val(orgCartAddress.memo);
							/*if(orgCartAddress.isSaveAddress){
								$("#saveAddress").attr("disabled",true);
							}*/
						}
						var strOptions = "";//非默认地址选项
						var strDefaultAddress = "";//默认地址选项
						for(var i = 0;i<addressList.length;i++){
							
							
							if(addressList[i].isDefault==1){
								/*if(!mapFullAddress && i==0){//如果没有获取到google定位的地址，就使用第一个填充
									$("#address-street").val(mapStreet);
									$("#address-city").val(mapCity);
									$("#address-province").val(mapProvince);
								}*/
								strDefaultAddress += "<option value='"+ addressList[i].addressId +"'>"+addressList[i].address +"</option>";
							}else{
								strOptions += "<option value='"+ addressList[i].addressId +"'>"+addressList[i].fullAddress +"</option>";
							}
						}
						strOptions =orgAddressOption + strDefaultAddress + strOptions;
						$("#addressList").append(strOptions);
					}
				}
			})
		}
	};
	
	(function initUploadAddressSelect(){
		if(checkOutOrderType == 1){//delivery
			uploadAddressSelect();
		}
	})()

	
	//获取一个 对象 保存着当前地址的信息 用于加载运费
	var subTotal = $.trim($("#subTotal").text());
	var addressObj = new Object();
	var selectFullAddress = "";//下拉框的street+city+province
	var oldStreet;
	var oldFloor;
	var oldCity;
	var oldProvince;
//---------------------------------------------------获取当前Address的Id 并联带获取当前地址的配送费----------------------------------------------
	function getCurrentAddressId(){
		addressObj.subTotal = subTotal;
		$("#addressList").find("option").each(function(){
			if($(this).prop("selected") == true){
				currentAddressId = $(this).val();
				if(currentAddressId==-1){//该选项为之前提交订单时设置的地址
					addressObj.lng = orgCartAddress.lng;
					addressObj.lat = orgCartAddress.lat;
					/*if(addressObj.isSaveAddress==1){//如果之前设置过保存地址并提交，那么初始化出来的原来填的地址禁用保存地址选项
						$("#saveAddress").attr("disabled",true);
					}*/
					$("#address-street").val(orgCartAddress.street);
					$("#address-city").val(orgCartAddress.city);
					$("#address-province").val(orgCartAddress.province);
					$("#address-floor").val(orgCartAddress.floor);
					$("#memo").val(orgCartAddress.memo);
					oldStreet = orgCartAddress.street;
					oldFloor = orgCartAddress.floor;
					oldCity = orgCartAddress.city;
					oldProvince = orgCartAddress.province;
					//console.log(addressObj+"addressObj");
					//console.log(oldStreet+oldFloor+oldCity+oldProvince);
					getDeliveryFee(addressObj);
					
				}/*else if(currentAddressId==0){//
					//$("#saveAddress").removeAttr("disabled");
					selectFullAddress = mapStreet+" "+mapCity+" "+mapProvince;
					$("#address-name").val(consumerName);
					$("#address-phone").val(consumerPhone);
					$("#address-street").val(mapStreet);
					$("#address-city").val(mapCity);
					$("#address-province").val(mapProvince);
					addressObj.lng = mapLng;
					addressObj.lat = mapLat;
					console.log(addressObj);
					getDeliveryFee(addressObj);
				}*/else if(currentAddressId>0){
					$("#saveAddress").removeAttr("checked");
					for(var k = 0; k < addressList.length; k++){
						if(addressList[k].addressId ==currentAddressId){
							selectFullAddress = addressList[k].street+" "+addressList[k].city+" "+addressList[k].province;
							$("#address-street").val(addressList[k].street);
							$("#address-city").val(addressList[k].city);
							$("#address-province").val(addressList[k].province);
							$("#address-floor").val(addressList[k].floor);
							addressObj.lng = addressList[k].lng;
							addressObj.lat = addressList[k].lat;
							oldStreet = addressList[k].street;
							oldFloor = addressList[k].floor;
							oldCity = addressList[k].city;
							oldProvince = addressList[k].province;
							//console.log(addressObj.lng+"addressObj");
							//console.log(oldStreet+oldFloor+oldCity+oldProvince);
							getDeliveryFee(addressObj);
						}
					}
				}
			}
		})
	}
	(function initGetCurrentAddressId(){
		if(checkOutOrderType == 1){//delivery
			getCurrentAddressId();
		}
	})()
	$("#addressList").change(function(){
		getCurrentAddressId();
	})
	//显示新增地址
	$("#newAddress").click(function(){
		$("button[name='gotoPay']").attr("disabled","true");
		currentAddressId = 0;
		$("#address-street").val("");
		$("#address-floor").val("");
		$("#address-city").val("");
		$("#address-province").val("");
		oldStreet = "";
		oldCity = "";
		oldProvince = "";
		$("#selectAddress").css("display","block");
		$("#addressList").css("display","none");
		$("#address-street").removeAttr("readonly");
		$("#address-floor").removeAttr("readonly");
		$("#address-city").removeAttr("readonly");
		$("#address-province").removeAttr("readonly");
		$("#save-address-div").css("display","block");
		$(this).css("display","none")
	})
	//显示选择地址
	$("#selectAddress").click(function(){
		$("#newAddress").css("display","block");
		$("#addressList").css("display","block");
		$(this).css("display","none");
		$("div[title='tip']").remove();
		$("#address-street").attr("readonly","readonly");
		$("#address-floor").attr("readonly","readonly");
		$("#address-city").attr("readonly","readonly");
		$("#address-province").attr("readonly","readonly");
		$("#save-address-div").css("display","none");
		$("#saveAddress").removeAttr("checked");
		$("#addressList").change();
	})
	
	var overRange=false;//超出配送距离
	var deliveryFee = 0;//配送费
	
	
//----------------------------------下拉列表中的地址选中后判断是否在配送范围内并计算运费 刷新购物车中总费用----------------------------
	function getDeliveryFee(obj1){
		$("#bg").css("display", "block");//显示等待层
		$("#show").css("display", "block");
		$.ajax({
			type: "post",
			url: appPath+"/consumers/getDeliveryFee",
			async: false,
			data:{lng:obj1.lng, lat:obj1.lat,subTotal:obj1.subTotal, restaurantUuid:restaurantUuid},//restaurantUuid在上面用已用cookie获取
			success: function(data){
				$("#bg").css("display", "none");//隐藏等待层
				$("#show").css("display", "none");
				var msg = $.parseJSON(data);
				if(msg.success){//未超出配送范围
					$("#over-range-message").attr("style","display:none;");
					showFees(msg.errorMsg);//调用显示新的费用的方法更新各种显示费用
					$("button[name='gotoPay']").removeAttr("disabled");
				}else{//超出范围不给送
					$("button[name='gotoPay']").attr("disabled","true");
					var errorMessage = msg.errorMsg;//失败时获取后台保存的错误信息
					if(errorMessage){//如果有返回错误信息，就显示返回的错误信息
						//$("#over-range-message").text(errorMessage);
						$("#over-range-message").text("Your address is not within the restaurant’s delivery service area");
					}else{//如果没有获取到错误信息显示下面的文字
						$("#over-range-message").text("Your address is not within the restaurant’s delivery service area");
					}
					$("#over-range-message").attr("style","display:block;");
					setTimeout(function(){
						$("#over-range-message").attr("style","display:none;");
					},5000);
				}
			}
		})
	}
	
	function showFees(deliveryFee){//传入新的送餐费，显示新的 送餐费， 税额，amount
		var subTotal = parseFloat($("#subTotal").text());
		var deliveryFee = parseFloat(deliveryFee);
		//var taxRate = parseFloat(taxRate);//税率
		var taxFee = (subTotal+deliveryFee)*taxRate;
		var amount = (subTotal + deliveryFee + parseFloat(taxFee.toFixed(2))).toFixed(2);
		$("#deliveryFee").text((deliveryFee).toFixed(2));
		$("#salesTax").empty().text((taxFee).toFixed(2));
		$("#cartTotal").text(amount);
	}
	
	/*$("#addressList").change(function(){
		var currentAddressId = $(this).val();
		var currentAddressObj = null;
		if(currentAddressId ==0){//说明选择的是首页的地址
			$("#address-address").val(holdAddress);
			$("#address-name").val(consumername);
			obj1.lng = holdLng;
			obj1.lat = holdLat;
			getDeliveryFee(obj1);
		}else{
			for ( var i in addressList) {
				if(addressList[i].addressId == currentAddressId){
					var currentAddressObj = addressList[i];
					holdAddress = currentAddressObj.fullAddress;
					$("#address-address").val(currentAddressObj.fullAddress);
					$("#address-floor").val(currentAddressObj.floor);
					$("#address-name").val(currentAddressObj.consignee);
					$("#address-phone").val(currentAddressObj.phone);
					obj1.lng = currentAddressObj.lng;
					obj1.lat = currentAddressObj.lat;
					getDeliveryFee(obj1);
				}
			}
		}
	})*/

//---------------------------修改省市区输入框内容后，验证是否可用并计算出 修改的地址经纬度和运费   并 刷新购物车费用或禁用递交按钮------------------
	var flagStreet = false;
	var flagCity = false;
	var flagProvince = false;
	var editLat;
	var editLng;
	
	$("#address-floor").blur(function(){
		var newFloor = $.trim($(this).val());
		/*if(newFloor != oldFloor){
			$("#saveAddress").removeAttr("disabled");
		}*/
	}).focus(function(){
		oldFloor = $.trim($(this).val());
		//hidetips("address-floor");
	})
	
	$("#address-street").blur(function(){
		var newStreet = $.trim($(this).val());
		if(newStreet == ""){
			$("button[name='gotoPay']").attr("disabled","true");
			showtips("address-street","Street requeied",120,37);
		}
		if(newStreet !="" && newStreet != oldStreet){
			editAndValidateAddress();
		}
	}).focus(function(){
		oldStreet = $.trim($(this).val());
		hidetips("address-street");
	})
	
	$("#address-city").blur(function(){
		var newCity = $.trim($(this).val());
		if(newCity == ""){
			$("button[name='gotoPay']").attr("disabled","true");
			showtips("address-city","City requeied",120,37);
		}
		if(newCity!="" && newCity != oldCity){
			editAndValidateAddress();
		}
	}).focus(function(){
		oldCity = $.trim($(this).val());
		hidetips("address-city");
	})
	
	$("#address-province").blur(function(){
		var newProvince = $.trim($(this).val());
		if(newProvince == ""){
			$("button[name='gotoPay']").attr("disabled","true");
			showtips("address-province","Province requeied",80,37);
		}
		if(newProvince!="" && newProvince != oldProvince){
			editAndValidateAddress();
		}
	}).focus(function(){
		oldProvince = $.trim($(this).val());
		hidetips("address-province");
	})
	/*$("#address-floor").blur(function(){
		alert($("#saveAddress").prop("checked"));
	});*/
	
	function editAndValidateAddress(){
		if($.trim($("#address-street").val())==""){
			flagStreet = false;
			showtips("address-street","Street requeied",120,37);
			$("#saveAddress").removeAttr("checked");
			return;
		}else{
			flagStreet = true;
		}
		if($.trim($("#address-city").val())==""){
			flagCity = false;
			showtips("address-city","City requeied",120,37);
			$("#saveAddress").removeAttr("checked");
			//$("#saveAddress").attr("disabled",true);
			return;
		}else{
			flagCity = true;
		}
		if($.trim($("#address-province").val())==""){
			flagProvince = false;
			showtips("address-province","Provincere queied",80,37);
			$("#saveAddress").removeAttr("checked");
			//$("#saveAddress").attr("disabled",true);
			return;
		}else{
			flagProvince = true;
		}
		
		GetNewDeliveryFeeAndLatLng();
		//console.log(addressObj);
	};
	
//----------------------当省市区 失去焦点时 内容和 下拉出来自动填充的地址不一样时，重新获取经纬度并计算运费 刷新购物车----------------------
	function GetNewDeliveryFeeAndLatLng(){
		$("#bg").css("display", "block");//显示等待层
		$("#show").css("display", "block");
		var editAddressObj = new Object();
		if(flagStreet && flagCity && flagProvince){
			var street = $.trim($("#address-street").val());
			var city = $.trim($("#address-city").val());
			var province = $.trim($("#address-province").val());
			
			var newFullAddress = street+" "+city+" "+province;
			if(newFullAddress != selectFullAddress){
				$.ajax({
					type: "post",
					async: false,
					data: {address:newFullAddress, subTotal:subTotal, restaurantUuid:restaurantUuid},
					url: appPath+"/consumers/getLatLng",
					success: function(data){
						$("#bg").css("display", "none");//隐藏等待层
						$("#show").css("display", "none");
						var msg = $.parseJSON(data);
						if(msg.success){//根据输入的的地址解析成功
							//$("#saveAddress").removeAttr("disabled");
							var latLngAndFeeArray = msg.errorMsg.split("==");//后台数据结构：lat-lng-distanceFee;
							editAddressObj.lat = latLngAndFeeArray[0];
							editAddressObj.lng = latLngAndFeeArray[1];
							editAddressObj.street = street;
							editAddressObj.city = city;
							editAddressObj.province = province;
							editAddressObj.fullAddress = newFullAddress;
							editAddressObj.floor = $.trim($("#address-floor").val());
							showFees(latLngAndFeeArray[2]);
							$("button[name='gotoPay']").removeAttr("disabled");//设置按钮可用
						}else{//根据输入的的地址解析失败
							$("button[name='gotoPay']").attr("disabled","true");
							$("#over-range-message").text("Your place is over range of this restaurant delivery distance");
							$("#over-range-message").attr("style","display:block;");
							setTimeout(function(){
								$("#over-range-message").attr("style","display:none;");;
							},5000);
						}
					}
					
				})
				addressObj = editAddressObj;//设置当前地址信息对象到全局变量
			}else{
				$("#bg").css("display", "none");//隐藏等待层
				$("#show").css("display", "none");
			}
		}else{
			$("#bg").css("display", "none");//隐藏等待层
			$("#show").css("display", "none");
		}
	}
	
	var flagAddressEmail = false;
	var flagAddressName = false;
	var flagAddressPhone = false;
	$("#address-email").focus(function(){
		hidetips("address-email");
	}).blur(function(){
		flagAddressEmail = loginEmailValidate("address-email",110,35);
	})
	$("#address-name").focus(function(){
		hidetips("address-name");//参数说明: 表单元素id值
	}).blur(function(){
		flagAddressName = nameValidate("address-name",110,35);
	})
	$("#address-phone").focus(function(){
		hidetips("address-phone");//参数说明: 表单元素id值
	}).blur(function(){
		flagAddressPhone = phoneNumberValidate("address-phone",110,35);
	})
	
	$("button[name='gotoPay']").click(function(){
		
		if(!flagAddressEmail){
			flagAddressEmail = loginEmailValidate("address-email",110,35);
			if(!flagAddressEmail){
				return;
			}
		}
		if(!flagAddressName){
			flagAddressName = nameValidate("address-name",110,35);
			if(!flagAddressName){
				return;
			}
		}
		if(!flagAddressPhone){
			flagAddressPhone =  phoneNumberValidate("address-phone",110,35);
			if(!flagAddressPhone){
				return;
			}
		}
		if(flagAddressEmail && flagAddressName && flagAddressPhone){
			$("#bg").css("display", "block");//显示等待层
			$("#show").css("display", "block");
			var cartObj= new Object();
			cartObj.id = $("input[name='cartId']").val();
			cartObj.name = $("#address-name").val();
			cartObj.email = $("#address-email").val();
			cartObj.phone = $("#address-phone").val();
			cartObj.memo = $("#memo").val();
			cartObj.subTotal = $("#subTotal").text();
			cartObj.deliveryFee = $("#deliveryFee").text();
			cartObj.salesTax = $("#salesTax").text();
			cartObj.amount = $("#cartTotal").text();
			cartObj.orderType = checkOutOrderType;//保存订单类型
			if(checkOutOrderType == 1){//delivery 类型
				cartObj.street = $.trim($("#address-street").val());
				cartObj.floor = $("#address-floor").val();
				cartObj.city = $.trim($("#address-city").val());
				cartObj.province = $.trim($("#address-province").val());
				cartObj.addressId = currentAddressId;
				cartObj.address = cartObj.street+" "+cartObj.floor+" "+cartObj.city+" "+cartObj.province;
				cartObj.lat = addressObj.lat;
				cartObj.lng = addressObj.lng;
				cartObj.isSaveAddress = $("#saveAddress").prop("checked");//选中为true 未选为false
			}
			var jsonCartObj = JSON.stringify(cartObj);
			//console.log(jsonCartObj);
			$.ajax({
				type: 'post',
				url: appPath+"/consumers/gotoPayment",
				data:{jsonCartObj:jsonCartObj},
				success: function(data){
					
					var msg = $.parseJSON(data);
					if(msg.success){
						window.location.replace(appPath+"/payment/getPayment");
					}else{
						$("#bg").css("display", "none");//隐藏等待层
						$("#show").css("display", "none");
						$("#over-range-message").text("Error");
						$("#over-range-message").attr("style","display:block;");
					}
				}
			})
		}
		
		
	});
	
	
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
	//$("#bg").css("display", "none");//隐藏等待层
	//	$("#show").css("display", "none");
	
	//验证邮箱格式
	function loginEmailValidate(elementId,x,y){
		var flag = false;
		var emailRegExp = /^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/;
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
		var phoneExpReg = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
		var val = $.trim($("#"+elementId).val());
		if(val==""){
			flag=false;
			showtips(elementId,'Required',x,y);
		}else if(val.match(phoneExpReg)){
			flag = true;
			hidetips(elementId);
		}else{
			flag = false;
			showtips(elementId,"Invalid Phone",x,y);
		}
		return flag;
	}
	
	
	
})