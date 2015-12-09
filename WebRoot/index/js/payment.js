
$(function(){

	//等待效果
	$("#bg").css("display", "block");
	$("#show").css("display", "block");
	
	$.ajax({
		type: "GET",
		url: appPath+'/payment/getAllCharityFirstLetters',
		success: function(msg){
			var charityFirstLetters = $.parseJSON(msg);
			for(var i = 0; i < charityFirstLetters.length; i++) {
				$("#az").append("<option value="+charityFirstLetters[i]+">"+charityFirstLetters[i]+"</option>");
			}
			
			// Now that the first letters are populated, get the selected first letter's default charity.
			$.ajax({
				 type: "POST",
				 url: appPath+'/payment/getAllCharity',
				 data:{
					'letter':$("#az").val()
				 },
				 success: function(msg){
					var charitys = $.parseJSON(msg);
					$.each(charitys,function(index,item){
						$("#charity").append("<option value="+item.id+">"+item.charityName+"</option>");
					});
				 }
			});
		}
	});
	
	$("#az").change(function(){
		$("#charity").empty();
		$.ajax({
  			 type: "POST",
   			 url: appPath+'/payment/getAllCharity',
   			 data:{
   			 	'letter':$(this).val()
   			 },
   			 success: function(msg){
   			 	var charitys = $.parseJSON(msg);
   			 	$.each(charitys,function(index,item){
   			 		$("#charity").append("<option value="+item.id+">"+item.charityName+"</option>");
   			 	});
     			
   			 }
		});
	});
		
	
	$("a[name='logo']").click(function(){
		window.location = appPath+"/index/index";
	});
	
	$("#edit").click(function(){
		window.location.replace(appPath+"/index/regist");
	});
	
	$("button[name='changeOrder']").click(function(){
		window.location = appPath+"/index/restaurantmenu?restaurantUuid="+$("#restaurantUuid1").val();
	});
	
	$("#dropDown").click(function(){
		$("#dropDown span").toggle();
		$("#sonCards,#cardDetail").toggle(300);
		
	});

	
	//现金付款
	$("button[name='placeOrderByCash']").click(function(){
		$("#bg").css("display", "block");
		$("#show").css("display", "block");
		$.ajax({
  			 type: "POST",
   			 url: appPath+'/payment/payByCash',
  			 data: {
  			 	'amount':(parseFloat($("#cartTotal").text())*100).toFixed(0),
   				'consumerUuid':$("#consumerUuid").val(),
   				'orderId':$("#orderId").val()
  			 },
   			 success: function(msg){
   			$("#bg").css("display", "none");
			$("#show").css("display", "none");
     		msg = $.parseJSON(msg);
     		if(msg.flag>0){//订单已经增加过
     				$("#orderId").val(msg.flag);
     			}
     		if(!msg.success){//不成功
     			$("button[name='placeOrder']").popover({
					content :msg.errorMsg
				});
				$("button[name='placeOrder']").popover('show');
				setTimeout(function(){
					$("button[name='placeOrder']").popover('destroy');
				},5000)
     		}
     		else{
     			//捐款
     			$.cookie("cart-orderId", 0,{expires:10,path: '/' });
     			$("#charityNum").append("You will donate $"+(parseFloat($("#subTotal").text())*0.05).toFixed(2));
     			$("#display").css('display','block');
     		}
   			 }
		});
	});
	
	//加载购物车
	
	$.ajax({
				type: 'post',
				url: appPath+'/consumers/showRegistCart',
				data: {
						'consumerUuid':$("#consumerUuid").val()
						},
				success: function(data){
					$("#bg").css("display", "none");
					$("#show").css("display", "none");
					$("#cartContent").html(data);
					var totle = parseFloat($("#subTotal").text());
					var amount = parseFloat($("#cartTotal").text());
					$("#tipNumber").keyup(function(){
						var add = 0;
						if($(this).val()){
							add = parseFloat($(this).val());
						}
						$("#cartTotal").text((amount+add).toFixed(2));
					});
					//Math.floor(a*b*100+0.5)/100.0
					$("#tip10").click(function(){
						var add = totle*0.1;
						$("#tipNumber").val(myToFixed2(add));
						$("#cartTotal").text(myToFixed2(amount+add));
					});
					
					$("#tip15").click(function(){
						var add = totle*0.15;
						$("#tipNumber").val(myToFixed2(add));
						$("#cartTotal").text(myToFixed2(amount+add));
					});
					
					$("#tip20").click(function(){
						var add = totle*0.2;
						$("#tipNumber").val(myToFixed2(add));
						$("#cartTotal").text(myToFixed2(amount+add));
					});
					
					$("a[href='#profile']").click(function(){
						$("#tipNumber").val('');
						$("#cartTotal").text(myToFixed2(amount));
					});
				}
			})
	
	var flag = false;//卡号的标记
	var flag1 = false;//过期月的标记
	var flag2 = false;//过期年的标记
	var flag3 = false;//cvv的标记
	var save = 0;//保存卡号的标记，默认不保存
	
	$("#month").keyup(function(){
		if($(this).val()!=''){
			//判断月份为数字并且小于12
		if(/^\d+$/.test($(this).val()) && $(this).val()<=12 && $(this).val()>0){
			$(this).parent().css('border-color','#cccccc');
			flag1 = true;
		}
		else{
			$(this).parent().css('border-color','red');
			flag1 = false;
		}
		}
		else{
			$(this).parent().css('border-color','#cccccc');
			flag1 = false;
		}
		
	});	
	
	$("#year").keyup(function(){
		if($(this).val()!=''){
			//得到目前的年份四位数表示形式
		var year = new Date().getFullYear();
		if(/^\d+$/.test($(this).val()) && $(this).val()>=year){
			$(this).parent().css('border-color','#cccccc');
			flag2 = true;
		}
		else{
			$(this).parent().css('border-color','red');
			flag2 = false;
		}
		}
		else{
			$(this).parent().css('border-color','#cccccc');
			flag2 = false;
		}
		
		
		
		
	});	
	
	$("#cvv").keyup(function() {
				if ($(this).val() != '') {
					if (/^\d+$/.test($(this).val())
							&& $(this).val().length == 3) {
						$(this).parent().css('border-color', '#cccccc');
						flag3 = true;
					} else {
						$(this).parent().css('border-color', 'red');
						flag3 = false;
					}
				} else {
					$(this).parent().css('border-color', '#cccccc');
					flag3 = false;
				}

			});	
	

	var name;
	var valid = false;
	$("#number").validateCreditCard(function(result){//验证信用卡的方法
		//获取卡的类型
		name = result.card_type == null ? '-' : result.card_type.name;
		valid = result.valid;
		
		if(name=='mastercard'){
			$("#mastercarddisplay").css('display','block');
		}
		else{
			$("#mastercarddisplay").css('display','none');
		}
		if(name=='visa'){
			$("#visadisplay").css('display','block');
		}
		else{
			$("#visadisplay").css('display','none');
		}
		//判断是一个正确的卡号并且是接受卡的类型
		if($(this).val()!=''){
			if(valid && (name=='mastercard' || name=='visa')){
			$("#numberdiv").css('border-color','#cccccc');
			flag = true;
		}
		else{
			$("#numberdiv").css('border-color','red');
			flag = false;
		}
		}
		else{
			$("#numberdiv").css('border-color','#cccccc');
			flag = false;
		}
		
	});
	
	// Determine which Stripe key to use from the backend.
	$.ajax({
		type: 'POST',
		url: appPath+'/payment/getStripePublishableKey',
		async: false,
		success: function(data){
			var publishableKey = data;
			publishableKey = publishableKey.replace(/"/g, "");	// Remove the quotes that bound it.
			Stripe.setPublishableKey(publishableKey);
		}
	});
	
  $("button[name='placeOrder']").click(function(){
  	if($("#cardDetail").css('display')=='block'){//输入卡的详细信息付款
  		
  		function stripeResponseHandler(status, response) {
  		  var $form = $('#payment-form');
  		  if (response.error) {
  		    var param = response.error.param;
  		    if(param=='number'){
  		    	$("#numberdiv").css('border-color','red');
  		    }
  		    else if(param=='exp_month'){
  		    	$("#month").parent().css('border-color','red');
  		    }
  		    else if(param=='exp_year'){
  		    	$("#year").parent().css('border-color','red');
  		    }
  		    else if(param=='cvc'){
  		    	$("#cvv").parent().css('border-color','red');
  		    } else {
  		    	// Show the Stripe error message.
	  			$("button[name='placeOrder']").popover({
	  		    	content: response.error.message
		  		});
				$("button[name='placeOrder']").popover('show');
				setTimeout(function(){
					$("button[name='placeOrder']").popover('destroy');
				},5000)
  		    }
  		    $("button[name='placeOrder']").prop('disabled', false);
  		  } else {
  		    var token = response.id;
  		    $("#bg").css("display", "block");
  			$("#show").css("display", "block");
  		    $.ajax({
  		  		type: "POST",
  		   		url: appPath+"/payment/payByDetail",
  		   		data: {
  		   			'token':token,
  		   			'amount':(parseFloat($("#cartTotal").text())*100).toFixed(0),
  		   			'consumerUuid':$("#consumerUuid").val(),
  		   			'save':save,
  		   			'orderId':$("#orderId").val()
  		   		},
  		   		success: function(msg){
  		   			$("#bg").css("display", "none");
  					$("#show").css("display", "none");
  		   			$("button[name='placeOrder']").prop('disabled', false);
  		     		msg = $.parseJSON(msg);
  		     		if(msg.flag>0){//订单已经增加过
  		     				$("#orderId").val(msg.flag);
  		     			}
  		     		if(!msg.success){//不成功
  		     			$("button[name='placeOrder']").popover({
  							content :msg.errorMsg
  						});
  						$("button[name='placeOrder']").popover('show');
  						setTimeout(function(){
  							$("button[name='placeOrder']").popover('destroy');
  						},5000)
  		     		}
  		     		else{
  		     			//捐款
  		     			$.cookie("cart-orderId", 0,{expires:10,path: '/' })
  		     			$("#charityNum").append("You will donate $"+(parseFloat($("#subTotal").text())*0.05).toFixed(2));
  		     			$("#display").css('display','block');
  		     		}
  		   		}
  			});
  		  }
  		};
  		
  		if($("input[name='save']").prop("checked")){
	  		save = 1;
	  	}
  		if($("#number").val()=='' ){
	  		$("#numberdiv").css('border-color','red');
	  	}
	  	if($("#month").val()==''){
	  		$("#month").parent().css('border-color','red');
	  	}
	  	if($("#year").val()==''){
	  		$("#year").parent().css('border-color','red');
	  	}
	  	if($("#cvv").val()==''){
	  		$("#cvv").parent().css('border-color','red');
	  	}
	  	if(flag && flag1 && flag2 && flag3){//表单验证通过
	  		var $form = $("#payment-form");

	  		$("button[name='placeOrder']").prop('disabled', true);

    		Stripe.card.createToken($form, stripeResponseHandler);
	  	}
  	}
  	else{//用存好的卡付款
  		var cardId = '';
  		$("#sonCards input[type='radio']").each(function(){
  			if($(this).prop('checked')){
  				cardId = $(this).next().val();
  			}
  		});
  		if (cardId=='') {
  			$("button[name='placeOrder']").popover({
					content :'please select a card'
				});
				$("button[name='placeOrder']").popover('show');
				setTimeout(function(){
					$("button[name='placeOrder']").popover('destroy');
				},3000)
  		}
  		else{
  			$("button[name='placeOrder']").prop('disabled',true);
  			$("#bg").css("display", "block");
			$("#show").css("display", "block");
  	$.ajax({
  		type: "POST",
   		url: appPath+"/payment/payByCardId",
   		data: {
   			'amount':(parseFloat($("#cartTotal").text())*100).toFixed(0),
   			'consumerUuid':$("#consumerUuid").val(),
   			'cardId':cardId,
   			'orderId':$("#orderId").val()
   		},
   		success: function(msg){
   			$("#bg").css("display", "none");
			$("#show").css("display", "none");
   			$("button[name='placeOrder']").prop('disabled', false);
     		msg = $.parseJSON(msg);
     		if(msg.flag>0){//订单已经增加过
     				$("#orderId").val(msg.flag);
     			}
     		if(!msg.success){//不成功
     			$("button[name='placeOrder']").popover({
					content :msg.errorMsg
				});
				$("button[name='placeOrder']").popover('show');
				setTimeout(function(){
					$("button[name='placeOrder']").popover('destroy');
				},5000)
     		}
     		else{
     			//捐款
     			$.cookie("cart-orderId", 0,{expires:10,path: '/' });
     			$("#charityNum").append("You will donate $"+(parseFloat($("#subTotal").text())*0.05).toFixed(2));
     			$("#display").css('display','block');
     		}
   		}
	});
  		}
  		
  	
  	}
  });
  
  //删除卡
  $("button[name='deleteCard']").click(function(){
  	var cardId = $(this).parent().parent().find("input[name='cardId']").val();
  	$("#bg").css("display", "block");
	$("#show").css("display", "block");
  	 $.ajax({
  		type: "POST",
   		url: appPath+"/payment/deleteCard",
   		data: {
   			'consumerUuid':$("#consumerUuid").val(),
   			'cardId':cardId
   		},
   		success: function(msg){
   			$("#bg").css("display", "none");
			$("#show").css("display", "none");
     		msg = $.parseJSON(msg);
     		if(!msg.success){//不成功
     			$("button[name='placeOrder']").popover({
					content :msg.errorMsg
				});
				$("button[name='placeOrder']").popover('show');
				setTimeout(function(){
					$("button[name='placeOrder']").popover('destroy');
				},5000)
     		}
     		else{
     			//删除成功
     			location = appPath+"/payment/getPayment?consumerId=54";
     		}
   		}
	});
  });
  
  $("button[name='noDonation']").click(function(){
	  $.cookie("selectReservationOrderId", "",{expires:10,path: '/' }); 
	  $.cookie("orderSelectDate", "",{expires:10,path: '/' }); 
	  $.cookie("orderSelectTime", "",{expires:10,path: '/' }); 
	  location.replace(appPath+"/index/user?flag=4");
  });
  
  $("button[name='sure']").click(function(){
	  var s = $("#charity > option");
	  if(s.length>0){
		  $.ajax({
			  type : "POST",
			  url : appPath + "/payment/saveOrderCharity",
			  data : {'orderId' : $("#orderId").val(),
						'charityId' : $("#charity").val()
			  		},
			  success : function(msg) {
				  $("#display").css('display','none');
				  $("#displayAfter").css('display','block');
			  }
		  });
	  }
  });
  
  //四舍五入，保留两位小数
  function myToFixed2(num){
	  return (Math.round(num*100))/100.0;
  }
  
  
  //跳转用户管理页面
  $("button[name='sureAfter']").click(function(){
	  	$.cookie("selectReservationOrderId", "",{expires:10,path: '/' }); 
		$.cookie("orderSelectDate", "",{expires:10,path: '/' }); 
		$.cookie("orderSelectTime", "",{expires:10,path: '/' }); 
  		location.replace(appPath+"/index/user?flag=4");
  });
  

	
});