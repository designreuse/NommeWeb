<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html class="bg-black">
<head>
<meta charset="UTF-8">
<title>Nomme System Manager</title>
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport'>
<!-- bootstrap 3.0.2 -->
<link href="${ctx }/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />
<!-- font Awesome -->
<link href="${ctx }/css/font-awesome.min.css" rel="stylesheet"
	type="text/css" />
<!-- Theme style -->
<link href="${ctx }/css/AdminLTE.css" rel="stylesheet" type="text/css" />

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->
</head>
<!-- <div class="alert alert-danger " id="error"></div> -->
<body class="bg-black">


	<div class="form-box" id="login-box">
		<div class="header">Reset Password</div>
		<form action="${ctx}/admin/dologin" method="post">
			<div class="body bg-gray">
				<div class="form-group">
					<input type="text" class="form-control" name="verificationCode" id="verificationCode" placeholder="Typing verification code">
				</div>
				<div class="form-group">
					<input type="hidden" name="password1" id="password1"
						class="form-control" placeholder="New Password"  />
				</div>
				<div class="form-group">
					<input type="hidden" name="password2" id="password2"
						class="form-control" placeholder="Repeat New Password"  />
				</div>
			</div>
			<div class="footer">
				<button type="button" class="btn bg-olive btn-block" id="commit">Reset</button>
				<p>
					<a href="${ctx}/admin/login">Sign In</a>
				</p>
			</div>
		</form>
	</div>


	<!-- jQuery 1.11.3 -->
	<script src="${ctx }/framework/jquery-1.11.3.js"></script>
	<!-- Bootstrap -->
	<script src="${ctx }/framework/bootstrap.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
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
				}else if($.trim($(this).val()) == "${verificationCode}"){
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
						url:'${ctx}/admin/doresetpassword',
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
									window.location="${ctx}/admin/login";
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
	</script>
	<style type="text/css">
		.popover-content {
			color: #FA6A6A;
		}
	</style>

</body>
</html>
