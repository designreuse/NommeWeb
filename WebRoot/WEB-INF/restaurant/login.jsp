<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html class="bg-black">
<head>
<meta charset="UTF-8">
<title>Nomme System Manager</title>
<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
<!-- bootstrap 3.0.2 -->
<link href="${ctx }/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<!-- font Awesome -->
<link href="${ctx }/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<!-- Theme style -->
<link href="${ctx }/css/AdminLTE.css" rel="stylesheet" type="text/css" />

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->
<style type="text/css">
.popover-content {
	color: #FA6A6A;
}

#sendEmail {
	width: 300px;
	border: 0px solid red;
	height: 35px;
	padding-left: 10px;
	margin-top: 10px;
	margin-left: 130px;
	margin-bottom: 10px;
}

.modal-footer {
	margin-top: 0px;
}

.modal-header {
	border-radius: 5px 5px 0px 0px;
	border-bottom: 5px solid #307959;
	height: 66px;
	padding-top: 18px;
}

.msg {
	
}
</style>
</head>
<body class="bg-black">
	<div class="form-box" id="login-box">
		<div class="header">Sign In</div>
		<form id="fm" method="post">
			<div class="body bg-gray">
				<div class="form-group">
					<input type="text" name="code" class="form-control" id="code" placeholder="Email" value="${code}"/>
				</div>
				<div class="form-group">
					<input type="password" name="password" class="form-control" placeholder="Password" value="${password}"/>
				</div>
				<div class="form-group">
					<input type="checkbox" name="remember_me" /> Remember me
				</div>
			</div>
			<div class="footer">
				<button type="button" id="sub" class="btn bg-olive btn-block" data-placement="top">Sign me in</button>

				<p>
					<a data-toggle="modal" href="#mymodal-link" id="openModal">I forgot my password</a>
				</p>

				<a href="${ctx }/restaurant/register" class="text-center">Register a new membership</a>
			</div>
		</form>
	</div>

	<div class="modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" id="mymodal-link">
		<div class="modal-dialog">
			<div class="modal-content">

				<div class="modal-header" style="background-color:#3D9970">

					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">Please leave your email address to reset password !</h4>
				</div>
				<div class="modal-body" style="background-color:#EAEAEC;">
					<input name="sendEmail" id="sendEmail" placeholder="Enter your email address (username)">
				</div>
				<div class="modal-footer">
					<button type="button" id="send" class="btn bg-olive btn-block" style="width:100px; float:right; margin-right: 20px">Send
						Email</button>
				</div>
			</div>
		</div>
	</div>


	<!-- jQuery 1.11.3 -->
	<script src="${ctx }/framework/jquery-1.11.3.js" type="text/javascript"></script>
	<!-- Bootstrap -->
	<script src="${ctx }/js/appPath.js" type="text/javascript"></script>
	<script src="${ctx }/framework/bootstrap.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/js/form_validation.js"></script>
	<script type="text/javascript" src="${ctx}/js/restaurant/login.js"></script>

</body>
</html>
