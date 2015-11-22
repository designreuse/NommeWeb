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
					<input type="hidden" id="validate" value="${verificationCode}">
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
					<a href="${ctx}/restaurant/login">Sign In</a>
				</p>
			</div>
		</form>
	</div>


	<!-- jQuery 1.11.3 -->
	<script src="${ctx }/framework/jquery-1.11.3.js"></script>
	<!-- Bootstrap -->
	<script src="${ctx }/framework/bootstrap.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/js/restaurant/resetPassword.js"></script>
	<script src="${ctx }/js/appPath.js" type="text/javascript"></script>
	<style type="text/css">
		.popover-content {
			color: #FA6A6A;
		}
	</style>

</body>
</html>
