
//注册stripe账户页面的js

		$(function() {
			//var appPath = "";
			
			//获取客户端ip
			$("input[name='ip']").val(returnCitySN['cip']);
			$("#bg").css("display", "block");
			$("#show").css("display", "block");
			$.ajax({
				   type: "POST",
				   url: appPath+"/stripe/getAccountDetail",
				   success: function(msg){
					   $("#bg").css("display", "none");
					   $("#show").css("display", "none");
					 if(msg){//注册过账户
						 var pageObj = {};
						 var obj = {};
						 $("#have").css('display','none');
					 	 $("#noHave").css('display','block');
						 var msg = $.parseJSON(msg);
						 var legalEntity = msg.legalEntity;
						 //循环注入已经有的值
						 $.each(legalEntity,function(index,item){
							 $('#'+index).val(item);
							 if(index=='dob'){
								 $.each(legalEntity.dob,function(index,item){
									 $('#'+index).val(item);
								 });
							 }
							 if(index=='address'){
								 $.each(legalEntity.address,function(index,item){
									 $('#'+index).val(item);
								 });
							 }
						 });
						 var bank = msg.externalAccounts.data[0];
						 $("#personal_id_number").val('*********');
						 $("#business_tax_id").val('*********');
						 $("#routingNumber").val('*****-***');
						 $("#accountNumber").val('******'+bank.last4);
						 
						 $("#firstName,#lastName,#day,#month,#year").attr('readonly','readonly');
						 $("input,select").not($("#firstName,#lastName,#day,#month,#year")).focus(function(){
								$(this).val('');
							}).blur(function(){
								if($(this).val()==''){
									var t = $(this);
									$.each(pageObj,function(index,item){
										if(t.attr('name')==index){
											t.val(item);
										}
									});
								}
							});
						
						
						$.each($("input,select").not($("input[type='hidden']")),function(){
								var name = $(this).attr('name');
								pageObj[name] = $(this).val();
							});
						$("#update").click(function(){
							var flag = true;
							if(validateMyForm()){
								$.each($("input,select").not($("input[type='hidden']")),function(){
									var name = $(this).attr('name');
									//判断哪些字段修改了
									if(pageObj[name]!=$(this).val()){
										if(name=='account_number'){
											if(pageObj['routing_number']==$("#routingNumber").val()){//只修改了一个
												alert("please modify routingNumber");
												flag = false;
												return;
											}
										}
										if(name=='routing_number'){
											if(pageObj['account_number']==$("#accountNumber").val()){//只修改了一个
												alert("please modify accountNumber");
												flag = false;
												return;
											}
										}
										obj[name] = $(this).val();
									}
								});
								
								if(flag){
									$("#bg").css("display", "block");
									$("#show").css("display", "block");
									$.ajax({
										   type: "POST",
										   url: appPath+"/stripe/updateAccount",
										   data: {
											   'context':JSON.stringify(obj)
										   },
										   success: function(msg){
										     var msg = $.parseJSON(msg);
										     if(!msg.success){
										    	 $("#update").popover({
														content :msg.errorMsg
													});
													$("#update").popover('show');
													setTimeout(function(){
														$("#update").popover('destroy');
													},10000)
										     }
										     else{
										    	 location =  appPath+"/stripe/getStripeAccount";
										     }
										   }
										});
								}
									
									
							}
							
						});
					 }
					 else{//没有注册过账户
						 $("#have").css('display','block');
					 	 $("#noHave").css('display','none');
					 	 
					 	$('#locationAccount').click(function() {
							parent.location = "https://stripe.com/connect/account-terms";
						});
					 	
					 	$("#register").click(function(){
							if(validateMyForm()){
								$("#bg").css("display", "block");
								$("#show").css("display", "block");
								//创建页面对象
								var obj = new Object();
								$.each($("input,select"),function(){
									var name = $(this).attr('name');
									obj[name] = $(this).val();
								});
								$.ajax({
									   type: "POST",
									   url: appPath+"/stripe/registerAccount",
									   data: {
										   'context':JSON.stringify(obj)
									   },
									   success: function(msg){
									     var msg = $.parseJSON(msg);
									     if(!msg.success){
									    	 $("#bg").css("display", "none");
												$("#show").css("display", "none");
									    	 $("#register").popover({
													content :msg.errorMsg
												});
												$("#register").popover('show');
												setTimeout(function(){
													$("#register").popover('destroy');
												},10000)
									     }
									     else{
									    	 location =  appPath+"/stripe/getStripeAccount";
									     }
									   }
									});
								
							}
						});
					 }
					  
				   }
				});
			
			function validateMyForm(){
				var flag = true;
				$("input,select").each(function(){//所有字段不能为空
					if(!validateEmpty($(this))){
						flag = false;
						return flag;
					}
				});
				if(!flag){
					return false;
				}
				return true;
			}
			
			
			
		});