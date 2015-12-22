var appPath = "";//TODO:path

$(function(){
	$("button[name='logo']").click(function(){
		window.location = appPath+"/index/index";
	});
	
	/**各页面公用的登录、注册、找回密码功能 开始*/
	/*login*/
	var flag1 = false;//登录的Email
	var flag2 = false;//登录的password
	/*create count*/
	var flag3 = false;
	var flag4 = false;
	var flag5 = false;
	var flag6 = false;
	var flag7 = false;
	var flag8 = false;
	/*reset password*/
	var flag9 = false;
	var flag10 = false;
	var flag11 = false;
	var flag12 = false;
	
	//如果地址栏因为点击退出按钮而变成、camut/index/signOut，这个地址会调用controll中的注销方法"signOut"
	//这样会清除session中的consumer的值，在第三方登陆时会清除当前session中当前用户的数据
	//因此判断如果是camut/index/signOut就跳转到camut/index/index
	if(location.href.indexOf("signOut")>=0){
		window.location=appPath+"/index/index";
	}
	
	var currentNickname = $("#currentNickname").val();
	var currentFirstName = $("#currentFirstName").val();
	var currentLastName = $("#currentLastName").val();
	if(currentFirstName!=""){
		$("span[name='currentLoginUserName']").empty().append("Hi "+currentFirstName);
	}else{
		$("span[name='currentLoginUserName']").empty().append("Hi "+currentNickname);
	}
	
	$("div[name='twitter']").click(function(){//twitter第三方登陆
		location = appPath+"/thirdLogin/twitterLogin?originalUrlForTwitter="+location.href;
			});
			
	$("div[name='facebook']").click(function(){//facebook第三方登陆
		location = appPath+"/thirdLogin/facebookLogin?originalUrlForFacbook="+location.href;
			});
	
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
		var tip = "<div name='" + name + "' style='position:absolute;left:"+x+";top:"+y+";' title='tip'>"+
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
	function hidetips(tipId){
		var name = tipId+"-tip";
		//var tips = document.getElementsByName(name);
		$("div[name='"+name+"']").remove();
		//$(tips).remove();
	}
	
	$("#signIn0").click(function(){
		hidetips("loginInputEmail1");//参数说明: 表单元素id值
		hidetips("loginInputPassword1");//参数说明: 表单元素id值
		$("#loginInputEmail1").val("");
		$("#loginInputPassword1").val("");
	});
	//验证邮箱格式
	function loginEmailValidate(elementId,x,y){
		var flag = false;
		var emailRegExp = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
		// /^[a-z]([a-z0-9]*[-_]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\.][a-z]{2,3}([\.][a-z]{2})?$/i; 
		// /^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/;
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
	//创建新账号时验证邮箱是否已经被使用
	function emailExistValidate(id){
		var flag = false;
		var email = $.trim($("#"+id).val());
		$.ajax({
			type:'post',
			async:false,
			data:{email:email},
			url:appPath+'/index/emailExist',
			success:function(data){
				var msg = $.parseJSON(data);
				if(msg.success){//验证成功未被使用
					flag = true;
				}else{//已被使用
					flag = false;
					showtips("email", msg.errorMsg,110,38)
				}
			}
		});
		return flag;
	}
	
	//验证密码格式
	function passwordValidate(elementId,x,y){
		return true;
		var flag = false;
		var passwordRegExp = /^[A-Za-z0-9]+$/;
		var password = $.trim($("#"+elementId).val())
		if(password==""){
			flag=false;
			showtips(elementId,'Required',x,y);
		}else if(password.length<6){
			flag=false;
			showtips(elementId,'Password must be at least 6 characters long',x,y);
		}else if(passwordRegExp.test(password)){
			flag = true;
			hidetips(elementId);
		}else{
			flag=false;
			showtips(elementId,'Password must contain only digits and letters',x,y);
		}
		return flag;
	}
	
	//验证第二次输入的密码是否和第一次的相等
	function confirmPasswordValidate(password1,password2){
		var flag = false;
		var pwd1 = $.trim($("#"+password1).val());
		var pwd2 = $.trim($("#"+password2).val());
		if(pwd2==pwd1){
			flag = true;
		}else{
			flag = false;
			showtips(password2,"Please enter the same new password",110,38)
		}
		return flag;
	}
	
	$("#loginInputEmail1").focus(function(){
		hidetips("loginInputEmail1");//参数说明: 表单元素id值
	}).blur(function(){
		flag1 = loginEmailValidate("loginInputEmail1",80,48);
	});
	
	$("#loginInputPassword1").focus(function(){
		hidetips("loginInputPassword1");
		
	}).blur(function(){
		flag2 = passwordValidate("loginInputPassword1",80,48);
	});
	
	
	$("#loginSignIn1").click(function(){//登录
		//alert($.cookie("user"));
		
		var email = $.trim($("#loginInputEmail1").val());
		var password = $.trim($("#loginInputPassword1").val());
		var loginType = 1;
		//var autoLogin ;
		var flagAutoLogin = $("#autoLogin").is(":checked");
		/*alert(flagAutoLogin);
		if(flagAutoLogin){
			autoLogin = 
		}*/
		if(!flag1){//如果账号的验证为false
			flag1 = loginEmailValidate("loginInputEmail1",80,48);
		}else if(!flag2){//如果密码的验证为false
			//调用密码验证方法
			flag2 = passwordValidate("loginInputPassword1",80,48);
		}else if(flag1 && flag2){//如果账号密码前端都验证正确
			$.ajax({
				type:"post",
				async:false,
				url:appPath+"/index/login",
				data:{email:email,password:password,loginType:loginType,autoLogin:flagAutoLogin},
				success:function(data){
					var msg = $.parseJSON(data);
					if(msg.flag == -1){//登录名不存在
						flag1 = false;
						//调用显示提示的方法，参数说明：目标元素id，提示文字，横向偏移位置，纵向偏移位置
						showtips('loginInputEmail1',msg.errorMsg,80,48);
					}else if(msg.flag == 0){//密码错误
						flag2 = false;
						showtips('loginInputPassword1',msg.errorMsg,80,48);
					}else if(msg.flag == 1){//登录成功
						//$("#myModal").modal('hide');
						//setTimeout(function(){window.location =location.href;},100);
						window.location =location.href;
						/*if("restaurantmenu".indexOf(location.href)>0)//如果实在商家详情页面时
						showfavorite ();//调用restaurantMenu.js中的显示收藏红星的方法
*/					}
				}
			})
		}
	});
	//点击登录页面的Create Account 初始化创建用户账户的表单
	$("#gotoCreateModal").click(function(){
		$("#loginInputEmail1").val("");hidetips("loginInputEmail1");
		$("#loginInputPassword1").val("");hidetips("loginInputPassword1");
		$("#firstName").val("");hidetips("firstName");
		$("#lastName").val("");hidetips("lastName");
		$("#email").val("");hidetips("email");
		$("#phone").val("");hidetips("phone");
		$("#password1").val("");hidetips("password1");
		$("#password2").val("");hidetips("password2");
		$("#myModal").modal('hide');
		$("#createAccountModal").modal('show');
		
	})
	
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
		var phoneExpReg = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
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
	
	
	$("#firstName").focus(function(){
		hidetips("firstName");//参数说明: 表单元素id值
	}).blur(function(){
		flag3 = nameValidate("firstName",110,38);
	})
	$("#lastName").focus(function(){
		hidetips("lastName");//参数说明: 表单元素id值
	}).blur(function(){
		flag4 = nameValidate("lastName",110,38);
	});
	$("#email").focus(function(){
		hidetips("email");
	}).blur(function(){
		var email = $(this).val();
		flag5 = loginEmailValidate("email",110,38);
		if(flag5){
			flag5 = emailExistValidate("email");
		}
		
	});
	
	
	$("#phone").focus(function(){
		hidetips("phone");
	}).blur(function(){
		flag6 = phoneNumberValidate("phone",110,38);
	});
	$("#password1").focus(function(){
		hidetips("password1");
	}).blur(function(){
		flag7 = passwordValidate("password1",110,38);
	});
	$("#password2").focus(function(){
		hidetips("password2");
	}).blur(function(){
		flag8 = confirmPasswordValidate("password1","password2");
	});
	
	
	$("#createAccount").click(function(){
		if(!flag3){
			flag3 = nameValidate("firstName",110,38);
		}
		if(!flag4){
			flag4 = nameValidate("lastName",110,38);
		}
		if(!flag5){
			flag5 = loginEmailValidate("email",110,38);
			if(flag5){//普通格式验证成功的情况下去后台验证该邮箱是否已被使用
				flag5 = emailExistValidate("email");
			}
		}
		if(!flag6){
			flag6 = phoneNumberValidate("phone",110,38);
		}
		if(!flag7){
			flag7 = passwordValidate("password1",110,38);
		}
		if(!flag8){
			flag8 = confirmPasswordValidate("password1","password2");
		}
		if(flag3&&flag4&&flag5&&flag6&&flag7&&flag8){
			var val1 = $("#firstName").val();var val2 = $("#lastName").val();var val3 = $("#email").val();
			var val4 = $("#phone").val();var val5 = $("#password1").val();var val6 = $("#password1").val();
			$.ajax({
				type:'post',
				async:false,
				data: {firstName:val1,lastName:val2,email:val3,phone:val4,password1:val5,password2:val6},
				url:appPath+"/index/createAccount",
				success:function(data){
					var msg = $.parseJSON(data);
					if(msg.success){//创建账号成功
						$("#createAccountModal").modal('hide');
						$("#loginInputEmail1").val(val3);
						$("#myModal").modal('show');
					}else{
						showtips("createAccount",msg.errorMsg,110,45);
					}
				}
			})
		}

	});
	
	$("a[name='forgotPassword']").click(function(){//忘记密码按钮
		//var email = $("#loginInputPassword1");
		hidetips("forgetPasswordEmail");
		hidetips("sendEmail");
		hidetips("verificationCode");
		hidetips("newPassword1");
		hidetips("newPassword2");
		hidetips("setNewPassword");
		$("#forgetPasswordEmail").val("");
		$("#myModal").modal('hide');
		var flag = loginEmailValidate("loginInputEmail1");
		if(flag){
			$("#forgetPasswordEmail").val( $("#loginInputEmail1").val());
		}
		$("#forgotPasswordModal").modal('show');
	});
	
	
	$("#sendEmail").click(function(){//发送邮件验证码
		var val = $("#forgetPasswordEmail").val();
		hidetips("verificationCode");
		var flag9 = loginEmailValidate("forgetPasswordEmail",110,38);
		if(flag9){
			$.ajax({
				type:'post',
				async:false,
				data:{email:val},
				url:appPath+'/index/sendResetPasswordVerificationCode',
				success:function(data){
					var msg = $.parseJSON(data);
					if(msg.success){//验证成功
						flag9 = true;
						$("#showCodeInput").attr("style","display:block");
						//$("#verificationCode").focus();
					}else{//已被使用
						flag9 = false;
						showtips("forgetPasswordEmail", msg.errorMsg,110,38);
					}
				}
			});
		}
	});
	
	
	//验证码的验证方法
	$("#verificationCode").keyup(function(){
		var val = $.trim($("#verificationCode").val());
		if(val.length==6){
			$.ajax({
				type:'post',
				data:{VerificationCode:val},
				url:appPath+"/index/checkVerificationCode",
				success:function(data){
					var msg = $.parseJSON(data);
					if(msg.success){
						hidetips("verificationCode");
						$("#showPasswordInput").attr("style","display:block");
					}else{
						$("#showPasswordInput").attr("style","display:none");
						showtips("verificationCode",msg.errorMsg,110,38);
					}
				}
			});
		}
	}).focus(function(){
		hidetips("verificationCode");
	});
	
	
	$("#newPassword1").focus(function(){
		hidetips("newPassword1");
	}).blur(function(){
		flag10 = passwordValidate("newPassword1",110,38);
	});
	$("#newPassword2").focus(function(){
		hidetips("newPassword2");
	}).blur(function(){
		flag11 = confirmPasswordValidate("newPassword1","newPassword2");
	});
	
	$("#setNewPassword").click(function(){
		if(!flag10){
			flag10 = passwordValidate("newPassword1",110,38);
		}
		if(!flag11){
			flag11 = confirmPasswordValidate("newPassword1","newPassword2");
		}
		if(flag10 && flag11){
			var newPassword = $("#newPassword1").val();
			$.ajax({
				type:'post',
				async:false,
				url:appPath+"/index/resetPassword",
				data:{newPassword:newPassword},
				success:function(data){
					var msg = $.parseJSON(data);
					if(msg.success){
						$("#setNewPassword").empty().append("Success");
						window.location=appPath+"/index/index";
					}else{
						showtips("setNewPassword",msg.errorMsg,110,38);
					}
				}
			});
		}
		
	});
	/**各页面公用的登录、注册、找回密码功能 结束*/
	
	//Contact Us
	$("#index-contactus").click(function(){
		$("#myContactUs").modal('show');
	});
	
	//About 
	$("#index-about").click(function(){
		$("#myAbout").modal('show');
	});
	
	//FAQ 
	$("#index-faq").click(function(){
		$("#myFaq").modal('show');
	});	

	//TERMS OF USE 
	$("#index-termofuse").click(function(){
		$("#myTermOfUse").modal('show');
	});		
	
	//Privacy Policy
	$("#index-privacypolicy").click(function(){
		$("#myPrivacyPolicy").modal('show');
	});
	
		
});
