
//重置密码页面的js

		$(function() {
			//var appPath = "";
			
			var flag1 = false;
			var flag2 = false;
			var flag3 = false;
			
			$("#verificationCode").blur(function(){//验证码输入判断
				if($.trim($(this).val())==""){
					flag1 = false;
					$("#password1").attr("type","hidden");
					$("#password2").attr("type","hidden");
					$(this).popover('destroy');
					$(this).popover({
						content:" Required",
						placement: "right"
					});
					$(this).popover('show');
				}else if($.trim($(this).val()) == $("#validate").val()){
					flag1 = true;
					$(this).popover('destroy');
					$("#password1").attr("type","password");
					$("#password2").attr("type","password");
				}else {
					flag1 = false;
					$("#password1").attr("type","hidden");
					$("#password2").attr("type","hidden");
					$(this).popover('destroy');
					$(this).popover({
						content:" Wrong code",
						placement: "right"
					});
					$(this).popover('show');
				}
				
			});
			
			$("#password1").blur(function(){//新密码输入判断
				var p1 = $.trim($(this).val());
				if(p1.length<6 || p1.length>18){
					flag2 = false;
					$(this).popover('destroy');
					$(this).popover({
						content: "Please typing 6--18 characters",
						placement: "right"
					});
					$(this).popover('show');
				}else if(/^[A-Za-z0-9]+$/.test($(this).val())){
					flag2 = true;
					$(this).popover('destroy');
				}else{
					flag2 = false;
					$(this).popover('destroy');
					$(this).popover({
						content: "Allow only letters and Numbers",
						placement: "right"
					});
					$(this).popover('show');
				}
			});
			$("#password2").blur(function(){//重复新密码输入判断
				if($.trim($(this).val()) != $.trim($("#password1").val())){
					flag3 = false;
					$(this).popover({
						content: "The two passwords differ",
						placement: "right"
					});
					$(this).popover('show');
				}else{
					flag3 = true;
					$(this).popover('destroy');
				}
			});
			
			$("#commit").click(function(){//异步提交重置密码
				if(flag1 && flag2 && flag3){
					$.ajax({
						type:"POST",
						data: {newPassword:$("#password2").val()},
						url:appPath+'/restaurant/doresetpassword',
						success:function(data){
							var msg = jQuery.parseJSON(data);
							if(msg.success){
								 $("#commit").popover('destroy');
								$("#commit").popover({
									content: "Reset Password OK And Goto Sign In",
									placement: "top"
								});
								$("#commit").popover('show');
								setTimeout(function(){
									window.location=appPath+"/restaurant/login";
								},2000); 
							} else{
								$("#commit").popover('destroy');
								$("#commit").popover({
									content: "Reset Password Error Please Try Again",
									placement: "top"
								});
								$("#commit").popover('show');
							} 
						}
					});
				}
			});

		});