
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
		}//其他情况0：默认是Pick up，过是2：也是默认pick up
		$("#cartContent").attr("name",cartOrderType);
	})()
	
	
	
	/*刷新购物车方法*/
	function refreshCart (){
		//var consumerLng = -115.12954;
		//var consumerLat = 51.07751;
		var consumerLng = $.cookie("Lng");
		var consumerLat = $.cookie("Lat");
		var consumerId = $("#currentConsumerId").val();
		$("#bg").css("display", "block");//显示等待遮罩层
		$("#show").css("display", "block");
		$.ajax({
    		type: 'post',
    		url: appPath+'/consumers/showCart',
    		data: {consumerLng:consumerLng,consumerLat:consumerLat,consumerId:consumerId},
    		success: function(data){
    			$("#bg").css("display", "none");
    			$("#show").css("display", "none");
    			$("#cartContent").html(data);
    		}
    	})
	}
	/*function refreshCart(distance){
		$.ajax({
    		type: 'post',
    		url: appPath+'/consumers/showCart',
    		data: {distance:distance},
    		success: function(data){
    			
    			$("#cartContent").html(data);
    		}
    	})
	}*/
	
	/*删除一个购物车条目*/
	$("img[name='deleteCartDish']").click(function(){
		$("#bg").css("display", "block");
		$("#show").css("display", "block");
		var cartItemId = $(this).parent().find("input[name='cartItemId']").val();
		$.ajax({
			type: 'post',
			url: appPath+'/consumers/deleteCartItem',
			data:{cartItemId:cartItemId},
			success:function(data){
				var msg = $.parseJSON(data)
				if(msg.success){
					refreshCart();
				}
			}
		})
	})
	
	/*修改一个购物车条目*/
	$("img[name='editCartDish'],#cartItem-image").click(function(){
		var cartItemId = $(this).parent().find("input[name='cartItemId']").val();
		var didshId = $(this).parent().find("input[name='dishId']").val();
		$("#bg").css("display", "block");
		$("#show").css("display", "block");
		$("#dishDialogContent").empty();
		$.ajax({//第一步加载点击的菜品，并弹出dish模态框
			type:'post',
			url:appPath+'/restaurantMenu/getOneDish',
			data:{dishId:didshId},
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
									alert(subItem.num);
									if($(element).val()==subItem.garnishItemId){//如果元素的value属性等于购物车中的配菜id，就将这个元素选中
										$(element).prop("checked",true);
										var tempPrice1 = $(element).parent().parent().parent().next().find("#addPrice").text();//增加价格
										if(tempPrice1){//如果有加价就加上
											aDishPrice += parseFloat(tempPrice1);
										}
										if(subItem.num>0){
											
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
		    		}
				});
			}
		})
	})
	
	function showFreeDish(dishId){
		$.ajax({
			type: 'post',
			url: appPath+"/restaurantMenu/getDiscountDish",
			data: {dishId:dishId},
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
								//"<strong>Complimentary</strong>"+
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
		//var consumerLng = $.cookie("Lng");//经度
		//var consumerLat = $.cookie("Lat");//纬度
		//var restaurantId = $("#restaurantId").val();//餐厅id
		/*$.ajax({//根据优惠后的菜品总价重新获取送餐费
			type: 'post',
			async: false,
			url: appPath+"/restaurantMenu/getDelivaryFee",
			data: {restaurantId:restaurantId,subTotal:subTotal,consumerLng:consumerLng,consumerLat:consumerLat},
			success: function(data){
				var msg = $.parseJSON(data);//$("div[name='discount-content']")
				console.log("配送费："+msg);
				if(msg){
					deliveryFe = parseFloat(msg);
				}else{
					deliveryFe = parseFloat(0);
				}
			}	
		})*/
		$("#deliveryFee").text(deliveryFe.toFixed(2));//重新设置配送费
		$("#salesTax").text(taxFee);//重新设置税额
		$("#cartTotal").text((subTotal+parseFloat(taxFee)).toFixed(2));//重新设置 最终金额
		//$("#cartTotal").text((subTotal+deliveryFe+parseFloat(taxFee)).toFixed(2));//重新设置 最终金额
		//console.log($("#show"));
		$("#bg").css("display", "none");
		$("#show").css("display", "none");
	}
	
	var oldDiscountId;
	var newDiscountId;
	var discountType;//如果是3：赠送菜品 需要刷新购物车
	//title保存的优惠券的类型  1、现金抵用券 2、打折券 3、赠送菜品
	
	/*radio 类型的优惠券信息点击事件*/
	$("input[name='discountItem']").on("mousedown",function(){
		//var oldId = $(this).val();//获取配菜分类头id
		$("input[name='discountItem']").each(function(){
			if($(this).prop("checked")==true){
				oldDiscountId = $(this).val();//获取原先的优惠券Id
				discountType = $(this).attr("title");//获取优惠券的优惠类型 1、现金抵用券 2、打折券 3、赠送菜品
				//console.log("oldId:"+oldDistanceId);
			}
		});
	}).on("click",function(){
		$("input[name='discountItem']").each(function(){
			if($(this).prop("checked")==true){
				newDiscountId = $(this).val();//获取新点击的优惠券Id
				if( $(this).attr("title")==3){
					discountType = $(this).attr("title");
				}
				//console.log(discountType);
				if(oldDiscountId != newDiscountId){
					var consumerId = $("#currentConsumerId").val();
					$.ajax({
						type:'post',
						url:appPath+"/consumers/chooseDiscount",
						async: false,
						data:{"oldDiscountId":oldDiscountId,"newDiscountId":newDiscountId, "consumerId":consumerId},
						success: function(data){
							var msg = $.parseJSON(data);
							//console.log(msg);
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
		
	
	$("a[name='cart-restaurantName']").click(function(){
		var cartRestaurantId = $("input[name='restaurantId']").val();
		var currentRestaurantId = $("#restaurantId").val();//当前餐厅id
		
		if(cartRestaurantId!=currentRestaurantId){
			//alert($("input[name='restaurantId']");
			$("#getoCartRestaurant").submit();
		}
	});


	(function getCartId (){
		var cartRestaurantId = $("input[name='cartId']").val();
		//alert(cartRestaurantId);
		//console.log("cartRestaurantId:"+cartRestaurantId);
		if(cartRestaurantId){
			//alert(cartRestaurantId);
			$.cookie("cartRestaurantId", cartRestaurantId,{expires:10,path: '/' });  //设置购物车的商家Id到cookie
		}else{
			$.cookie("cartRestaurantId", "0",{expires:10,path: '/' });
		}
	})();
		
		
})
