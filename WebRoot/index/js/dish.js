$(function(){
	
	var flag1 = true;
	
	/*自动执行的匿名函数, 判断是否有select类型的选项，如果有就将flag1初始化为false*/
	(function(){
		if($("div[name='garnishHeader'][alt='2']").text()){
			flag1 = false;
		}
	})();
	
	/*自动执行的匿名函数用于将默认必选的配菜的价格加到单价中*/
	(function(){
		//var price = parseFloat($("span[name='price']").text())//菜品原单价
		$("input").each(function(){
			if($(this).prop("checked")==true){
				var addPrice = $(this).parent().parent().parent().next().find("#addPrice").text();//增加价格
				if(addPrice){
					$("span[name='price']").text((parseFloat($("span[name='price']").text()) + parseFloat(addPrice)).toFixed(2));
					showADishTotal();
				}
			}
		})
	})()


	/*操作修改数量*/
	function showADishQuantity(quantity){
		$("#aDishQuantity").val(quantity);
		showADishTotal();
	}
	/*操作修改总价*/
	function showADishTotal(){
		var price = $.trim($("span[name='price']").text());
		
		var quantity = parseFloat($("#aDishQuantity").val());
		$("#aDishTotal").text((price * quantity).toFixed(2));
	}
	/*增加数量*/
	$("button[name='addQuantity']").click(function(){
		showADishQuantity(parseInt($("#aDishQuantity").val())+1);
	})
	/*减少数量*/
	$("button[name='minusQuantity']").click(function(){
		if($("#aDishQuantity").val()>1){
			showADishQuantity(parseInt($("#aDishQuantity").val())-1);
		}
	})
	
	/*异步装载出来的菜品详情模态框中checkbox的点击事件，并处理最多可选数量*/
	//$("#myModal1").on("click","input[type='checkbox']",function(){
	
	
	
	
	$("input[type='checkbox']").click(function(){
		/*checkbox 类型的配菜增加价格计算总价*/
		var dishQuanity = parseFloat($("#aDishQuantity").val());//数量
		var addPrice = $(this).parent().parent().parent().next().find("#addPrice").text();//增加价格
		if(addPrice){//如果有增加价格
			if($(this).prop("checked")==true){
				$("span[name='price']").text((parseFloat($("span[name='price']").text()) + parseFloat(addPrice)).toFixed(2));
				showADishTotal();
			}else{
				$("span[name='price']").text((parseFloat($("span[name='price']").text()) - parseFloat(addPrice)).toFixed(2));
				showADishTotal();
			}
		}
		/*checkbox 判断最多可选数目*/
		var max = $(this).parent().attr("alt");//最多可选的配菜数量
		if(max==0){
			max = 10000;
		}
		var menuId =  $(this).parent().attr("name");//配菜头id
		var checked = $("span[name='"+menuId+"'] input[checked]");
		var count = 0;
		$("span[name='"+menuId+"'] input").each(function(){
			if($(this).prop("checked")==true){
				count++;
			}
		})
		if(count>=max){//如果达到最大可选数量后，将其他的设成不可用
			$("span[name='"+menuId+"'] input").each(function(){
				if($(this).prop("checked")!=true){
					$(this).attr("disabled","disabled");
				}
			})
		}else{//如果数量小于最大可选数量，将所有未选择的设置成可选
			$("span[name='"+menuId+"'] input").each(function(){
				if($(this).prop("checked")!=true){
					$(this).attr("disabled",false);
				}
			})
		}
	})
	
	/*radio 类型的配菜增加价格计算总价*/
	var orginalRadioGarnishPrice ;
	$("input[type='radio']").on("mousedown",function(){
		orginalRadioGarnishPrice = 0;
		var alts = $(this).attr("name");//获取配菜分类头id
		$("input[name='"+alts+"']").each(function(){
			if($(this).prop("checked")==true){
				var addPrice = $.trim($(this).parent().parent().parent().next().find("#addPrice").text());//增加价格
				//console.log("addPrice mouseDown: " + addPrice);
				if(addPrice){//如果有加价就减去
					orginalRadioGarnishPrice = addPrice;
				}
			}
		});
	}).on("click",function(){//value=配菜id, name=配菜分类头id
		var alts = $(this).attr("name");//获取配菜分类头id
		$("input[name='"+alts+"']").each(function(){
			if($(this).prop("checked")==true){
				var addPrice = $.trim($(this).parent().parent().parent().next().find("#addPrice").text());//增加价格
				//console.log("addPrice click: " + addPrice);
				if(!addPrice){
					addPrice = 0;
				}
				if(orginalRadioGarnishPrice==undefined){
					orginalRadioGarnishPrice = 0;
				}
				//if(addPrice){//如果有加价就加上
					/*if(!orginalRadioGarnishPrice){
						orginalRadioGarnishPrice = 0;
					}*/
					//console.log("addPrice:"+ addPrice +" orginalRadioGarnishPrice:"+ orginalRadioGarnishPrice)
					//console.log(parseFloat($.trim($("span[name='price']").text()))+3);
					var temp = parseFloat(addPrice) - parseFloat(orginalRadioGarnishPrice);
					//console.log("temp:"+temp)
					var dishQuanity = parseFloat($("#aDishQuantity").val());
					$("span[name='price']").text((parseFloat($.trim($("span[name='price']").text())) + temp).toFixed(2));
					showADishTotal();
				//}
			}
		});
	});
	
	/*下拉类型的配菜的change事件*/
	var originalSelectTotal = 0;
	$("select").mousedown(function(){
		originalSelectTotal = $(this).find("option:selected").val();
	}).change(function(){
		var total = parseInt($(this).parent().attr("alt"));//这一组配菜规定的数量
		var count = 0;
		var headerName = $(this).attr("alt");//这组配菜的配菜头名称
		$(this).parent().parent().parent().parent().parent().parent().parent().parent().find("option:selected").each(function(){
			count += parseInt($(this).val());
		});
		//console.log("total:"+total);
		//console.log("count:"+count);
		if(count > total){
			flag1 = false;
			var header = $(this).attr("name");
			$("div[name='errorInfo']").empty().append("Maximum "+total+" items from "+headerName);
		}else{
			flag1 = true;
			$("div[name='errorInfo']").empty();
		}
		var dishQuantity = parseFloat($("#aDishQuantity").val());//数量
		var thisGarnishQuantity = $(this).find("option:selected").val();
		var tempQuantity = thisGarnishQuantity -originalSelectTotal;//新选的数量减去原来的数量得到的值乘以单价加到菜品单价中
		//console.log(thisGarnishQuantity);
		var addPrice = $(this).parent().parent().parent().next().find("#addPrice").text();//增加价格
		if(addPrice){//如果有加价
			var addPrice2 = parseFloat(addPrice)*parseFloat(tempQuantity);
			$("span[name='price']").text((parseFloat($("span[name='price']").text()) + addPrice2).toFixed(2));
			showADishTotal();
		}
	})
	
	/*菜品对象*/
	function Dish(dishId,cartItemId, num,unitprice,instruction,subItem){
		this.dishId = dishId;//菜品Id
		this.cartItemId = cartItemId;//购物车条目id
		this.num = num;//数量
		this.unitprice = unitprice;//一个菜品总单价
		this.instruction = instruction;//特殊需求
		this.subItem = subItem;//配菜集合
	}
	/*配菜对象*/
	function GarnishItem(garnishItemId,count){
		this.garnishItemId = garnishItemId;
		this.count = count;
	}
	
	/*刷新购物车方法*/
	function refreshCart(){
		var consumerLng = $.cookie("Lng");
		var consumerLat = $.cookie("Lat");
		var consumerUuid = $("#currentConsumerUuid").val();
		$.ajax({
    		type: 'post',
    		url: appPath+'/consumers/showCart',
    		data: {consumerLng:consumerLng,consumerLat:consumerLat,consumerUuid:consumerUuid},
    		success: function(data){
    			$("#cartContent").html(data);
    		}
    	})
	}
	
	
	/*添加购物车方法*/
	function addCartItem(cartHeader){//添加菜品到购物车传入一个组装好的购物车头对象
		//console.log(cartHeader);
		var strCartHeader = JSON.stringify(cartHeader);
		$.ajax({
			type: 'post',
			url: appPath+"/consumers/addCart",
			data: {cartHeader:strCartHeader},
			success: function(data){
				var msg = $.parseJSON(data);
				if(msg.success){
					$("#myModal1").modal('hide');
					$("#sessionId").val(msg.errorMsg);//返回的errorMsg中存的是session.getId()的值
					refreshCart();
				}
				else{
					$("#myModal1").modal('hide');
				}
			}
			
		})
	}
	
	/*修改购物车方法*/
	function editCartItem(cartHeader){
		//alert("修改购物车");
		var strCartHeader = JSON.stringify(cartHeader);
		//console.log(strCartHeader);
		$.ajax({
			type: 'post',
			url: appPath+"/consumers/editCartItem",
			data: {cartHeader:strCartHeader},
			success: function(data){
				var msg = $.parseJSON(data);
				if(msg.success){
					$("#myModal1").modal('hide');
					$("#sessionId").val(msg.errorMsg);//返回的errorMsg中存的是session.getId()的值
					refreshCart();
				}
				else{
					$("#myModal1").modal('hide');
				}
			}
			
		})
	}
	
	/*提交按钮，添加菜品到购物车*/
	$("#subDish").click(function(){
		//console.log("aa");
		var cartRestaurantUuid = $("#cartContent input[name='cartRestaurantUuid']").val();
		if(!cartRestaurantUuid){
			cartRestaurantUuid = 0;
		}
		var currentRestaurantUuid = $("#restaurantUuid").val();
		//alert(cartRestaurantId+" -- "+currentRestaurantId)
		//alert($("#cartContent input[name='cartId']").val());
		if(!$("#currentConsumerUuid").val()){//判断是否已经登录，如果没有登录弹出登录框
			$("#myModal1").modal('hide');
			$("#myModal").modal('show');
			
		}else if(cartRestaurantUuid!=currentRestaurantUuid &&cartRestaurantUuid!=0){
			//alert(currentRestaurantId +"---"+cartRestaurantId);
			var cartChangeTip = "You’ve changed your order. Do you wish to cancel your previous order?";
			$("div[name='clearCartTip']").text(cartChangeTip);
			$("h4[name='confirmClearCart']").text("Change The Order");
			$("#clearCart").text("Cancel Previous Order");
			$("#stayOrginalCart").text("Back To Previous Order");
			$("#isEmptyCartModal").modal('show');
			$("#myModal1").modal('hide');
			
		}else{
			$("div[name='errorInfo']").empty();
			var flag2 = true;//标记input组的
			//如果存在select类型的选项，flag1 会设为false,从而进入验证，如过选择了select选项并且数量正确，则flag1为true 不进入验证
			if(!flag1){//有select类型的配菜选项
				var selectGroupss = $("div[alt='2']");//获取到类型为select的所有组
				for(var k=0; k<selectGroupss.length; k++){//循环每一个select配菜组
					var garnishGroupId = $(selectGroupss[k]).attr("id");
					
					var selects = $("select[name="+garnishGroupId+"]");//这是同一组的多个select元素
					var atMostCount = parseInt(selects.eq(0).parent().attr("alt"));//这是最大可选数量
					var text = selects.eq(0).attr("alt")
					var count = 0;//记录已选的数量
					$(selectGroupss[k]).next().find("option:selected").each(function(){
						count += parseInt($(this).val());
					});
					//如果选则的数量大于最多可选数量，显示提示信息
					if(count>atMostCount){
						flag1 = false;//You at most select "+total+" items from "+headerName
						$("div[name='errorInfo']").empty().append("Maximum "+atMostCount+" items from "+text);
						break;
					}else{//如果数量想等，设置flag1=true,当前组通过验证进入下一个组验证
						flag1 = true;
					}
					//如果这一组是必选项//且是下拉类型的控件
					if($("#"+garnishGroupId).text().indexOf("Required")>0 && ($("#"+garnishGroupId).attr("alt")==2)){
						if(count == 0){//必选但是数量为0，说明没有选则退出验证循环，显示错误信息
							flag1 = false;
							//$("div[name='errorInfo']").empty().append("You at last select 1 items from "+text);
							$("div[name='errorInfo']").empty().append("Please choose the required info.");
							break;
						}else{
							flag1 = true;
						}
						
					}else{
						flag1 = true;
					}
				}
			}
			if(flag1){//判断下拉类型的选项都正确 的情况下再验证所有radio、checkbox类型
				var fdiv = $("div[name='garnishHeader']");
				for(var i = 0; i<fdiv.length;i++){
					var garnishGroupId = $(fdiv[i]).attr("id");
					if($("#"+garnishGroupId).text().indexOf("Required")>0 && ($("#"+garnishGroupId).attr("alt")!=2)){//如果这一组是必选项
						var inputs =  $("input[name='"+garnishGroupId+"']");
						
						var hasChecked = false;
						var text = inputs.eq(0).attr("alt");//获取配菜组头信息，用于构造提示信息
						//console.log(text);
						for(var j=0; j<inputs.length;j++){
							var input = $(inputs[j]);
							if($(inputs[j]).prop("checked")==true){//如果有一个是被选中的
								hasChecked = true;
								break;
							}
						}
						if(!hasChecked){//如果处理完毕，仍然是false，说明是没有一个是选中的
							//var errorInfo= "You must select at lease 1 from "+text+" group."
							var errorInfo = "Please choose the required info."
							$("div[name='errorInfo']").empty().append(errorInfo);//显示有必选项没有选择的错误提示
							flag2 = false;
							break;
						}
					}
				}
			}
			
			if(flag1 && flag2){//验证select（flag1）和 radio、checkbox(flag2) 都通过时
				var orginalOrderType = $("#cartContent").attr("name");
				var newOrderType = $("#orderType").val();
				//如果原来购物车中有商品，并且新添加菜品的订单类型和购物车中的订单类型不同时，需要提示是否清空原来购物车中的菜品
				//如果选择清空，则清空原来的购物车，并将新选的菜品添加到新的购物车中
				//如果选择不清空，则关闭当前的菜品模态框并切换到原来的订单类型的菜品列表下
				/*if(orginalOrderType && orginalOrderType!=newOrderType){//有商品，并且新添加菜品的订单类型和购物车中的订单类型不同时
					
				}*/
				var garnishItemList = new Array();//创建配菜对象集合
				$("div[name='garnishHeader']").each(function(){//获取所有配菜分类并遍历
					$(this).next().find("input").each(function(){//获取当前分类头下的所有input并且遍历
						var garnishItem = new GarnishItem();
						if($(this).prop("checked")){//如果checkbox是被选中的
							garnishItem.garnishItemId = $(this).val();
							garnishItem.count = 1;
						}
						if(garnishItem.garnishItemId){
							garnishItemList.push(garnishItem);
						}
					})
					$(this).next().find("select").each(function(){
						
						var number = $(this).val();//获取数量
						if(number!=0){
							var garnishItem = new GarnishItem();
							garnishItem.garnishItemId = $(this).attr("value");//获取存储配菜id
							garnishItem.count = number;//存储配菜数量
							garnishItemList.push(garnishItem);
						}
					})
				});
				
				//创建菜品对象并传值
				//Dish(dishId,cartItemId, num,unitprice,instruction,subItem){
					
				var unitprice = (parseFloat($("#aDishQuantity").val()) * parseFloat($("span[name='price']").text())).toFixed(2);
				var dish = new Dish($("#currentDishId").val(),$("#cartItemId").val(),$("#aDishQuantity").val(), unitprice, $("#dishRemark").val(), garnishItemList);
				function CartHeader (orderType,consumerUuid,restaurantUuid,total,tax,logistics,item,orderId){
					this.orderType = orderType;//订单类型
					this.consumerUuid = consumerUuid;//用户Id
					this.restaurantUuid = restaurantUuid;//商家id
					this.tax = tax;//税额
					this.total = total;//总金额
					this.item = item;//菜品
					this.orderId = orderId;//关联的reservation订单的Id
				} 
				//获得订单类型 
				var orderType = $("#orderType").val();
				//计算税额
				var tax = parseFloat($("#aDishTotal").text())*parseFloat($("#restaurantTaxRate").val());
				var orderId =  $.cookie("cart-orderId");
				//创建购物车对象
				var cartHeader = new CartHeader(orderType,$("#currentConsumerUuid").val(),$("#restaurantUuid").val(), 0,tax,0,dish,orderId);
				//console.log(cartHeader);
				if($(this).attr("name")=="add"){//说明是新增
					addCartItem(cartHeader);
				}else{//说明是修改购物车中的菜品
					editCartItem(cartHeader);
				}
			}
		}
	})
	
})