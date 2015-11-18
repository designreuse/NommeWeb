<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nomme System Manager</title>
<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
<!-- bootstrap 3.0.2 -->
<link href="${ctx }/css/bootstrap.min.css" rel="stylesheet" type="text/css" />

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->
<style type="text/css">

</style>
</head>
<body style="background-color: #eeeeee;">
	<div style="margin-left: 10px;margin-top: 10px;">
		<img src="${ctx}/index/images/xc-logo-black.png" width="150px;"/>
	</div>

	<fieldset style="margin-left: 10%;margin-top: 10%;">
		<legend><h2>THANK YOU</h2></legend>
		<span>We received your request to create a new account.</span><br/>
		<span>You will soon receive an e-mail containing instructions on how to activate your new account.</span>
	</fieldset>
	
	<button id="ok" class="btn btn-primary" style="width: 200px;margin-left: 45%;margin-top: 20%">OK</button>


	<!-- jQuery 1.11.3 -->
	<script src="${ctx }/framework/jquery-1.11.3.js" type="text/javascript"></script>
	<!-- Bootstrap -->
	<script src="${ctx }/framework/bootstrap.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/js/form_validation.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#ok").click(function(){
				location = "${ctx}/restaurant/login";
			});
		});
	</script>

</body>
</html>
