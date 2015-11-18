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
		<div class="header">Sign In</div>
		<form action="${ctx}/admin/dologin" method="post">
			<div class="body bg-gray">
				<div class="form-group">
					<input type="text" class="form-control" name="loginname" id="email"
						placeholder="Email">
				</div>
				<div class="form-group">
					<input type="password" name="password" id="password"
						class="form-control" placeholder="Password" />
				</div>
				<div class="form-group">
					<input type="checkbox" name="rememberMe" id="rememberMe" />
					Remember me
				</div>
			</div>
			<div class="footer">
				<button type="submit" class="btn bg-olive btn-block" id="commit">Sign
					me in</button>
				<p>
					<a data-toggle="modal" id="openModal" href="#mymodal-link">I forgot my
						password</a>
				</p>
			</div>
		</form>
	</div>

	<div class="modal" tabindex="-1" role="dialog"
		aria-labelledby="myLargeModalLabel" aria-hidden="true"
		id="mymodal-link">
		<div class="modal-dialog">
			<div class="modal-content">
				
				<div class="modal-header" style="background-color:#3D9970">
				
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">Please leave your email address to
						reset password !</h4>
				</div>
				<div class="modal-body" style="background-color:#EAEAEC;">
					<input name="sendEmail" id="sendEmail" placeholder="Enter your email address (username)">
				</div>
				<div class="modal-footer">
					<button type="button" id="send" class="btn bg-olive btn-block" style="width:100px; float:right; margin-right: 20px">Send Email</button>
				</div>
			</div>
		</div>
	</div>




	<!-- jQuery 1.11.3 -->
	<script src="${ctx }/framework/jquery-1.11.3.js"></script>
	<!-- Bootstrap -->
	<script src="${ctx }/framework/bootstrap.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			
			function showPopover(ele, data,direction){//显示提示信息的方法
				ele.popover('destroy');
				ele.popover({
					content: data,
					placement: direction
				});
				ele.popover('show');
			}
			
			$("#openModal").click(function(){//打开模态框清除输入框提示
				$("#sendEmail").popover('destroy');
				$("#sendEmail").val("");
				var emailAddress = $("#email").val();
				//如果输入的是一个邮箱，在打开模态框的时候就将之前输入的Email填到模态框中
				if(/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/.test(emailAddress)){
					$("#sendEmail").val(emailAddress);
				}
			});

			if ("${errorMsg}" != "") {
				$("#commit").popover({
					content : "${errorMsg}",
					placement : "top"
				});
				$("#commit").popover('show');
			}
			$("input[name='loginname']").focus(function() {
				$("#commit").popover('destory');
			});
			$("input[name='password']").focus(function() {
				$("#commit").popover('destroy');
			});
			$("input[name='loginname']").val("${loginname}");
			$("input[name='password']").val("${password}");
			
			
			$("input[name='sendEmail']").focus(function(){
				$("input[name='sendEmail']").popover('destroy');
			}); 
			
			
			$("#send").blur(function(){
				$(this).popover('destroy');
			});
			
			/* var flag = false;
			$("#sendEmail").blur(function(){
				var emailAddress = $.trim($(this).val());
				if(emailAddress==""){
					flag = false;
					showPopover($(this),"Required","right");
				}else if(/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/.test(emailAddress)){
					flag=true;
				}else{
					flag=false;
					showPopover($(this),"Invalid Email","right");
				}
			}); */
			
			$("#send").click(function(){//请求后台发送邮件
				var flag = false;
				var emailAddress = $.trim($("#sendEmail").val());
				if(emailAddress==""){
					flag = false;
					showPopover($(this),"Required","right");
				}else if(/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/.test(emailAddress)){
					flag=true;
				}else{
					flag=false;
					showPopover($(this),"Invalid Email","right");
				}
				/* 
			
				if(address==""){
					flag = false;
					showPopover($("#sendEmail"),"Required","right");
				} */
				if(flag){
					$("#send").attr("disabled","disabled");
					$.ajax({
						type:"POST",
						data: {emailAddress:emailAddress},
						url:'${ctx}/admin/sendemail',
						success:function(data){
							var msg = jQuery.parseJSON(data);
							 if(msg.success){
								$("#send").popover({
									content:"Email send success. Please Ckeck Your Email!",
									placement : "top"
								});
								$("#send").popover('show');
								setTimeout( function(){
									
									 window.location="${ctx}/admin/resetPage?email="+emailAddress;
								}, 2000);
								
							}else{
								$("#send").removeAttr("disabled");
								$("input[name='sendEmail']").popover({
									content:msg.errorMsg,
									placement : "right"
								});
								$("input[name='sendEmail']").popover('show');
								
							} 
						}
					});
					//不等待后台发送Email完成，先直接跳转到重置密码页面
					
				}
				
			});
			
			
			
			
			
		});
	</script>
	<style type="text/css">
.popover-content {
	color: #FA6A6A;
	width: 170px;
}
#sendEmail{
	width:300px;
	border:0px solid red;
	height:35px;
	padding-left: 10px;
	margin-top: 10px;
	margin-left: 130px;
	margin-bottom: 10px;
}

.modal-footer{
	margin-top: 0px;
}
.modal-header{
	border-radius:5px 5px 0px 0px;
	border-bottom: 5px solid #307959;
	height:66px;
	padding-top: 18px;
}

.msg {
	
}
</style>

</body>
</html>
