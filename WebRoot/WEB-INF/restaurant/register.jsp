<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html class="bg-black">
<head>
<meta charset="UTF-8">
<title>Nomme System Manager</title>
<jsp:include page="../inputcss.jsp"></jsp:include>
<style type="text/css">
.popover-content {
	color: #FA6A6A;
}

.form-group {
	width: 300px;
}
</style>
</head>
<body class="bg-black">
	<div id="bg"></div>
	<div id="show">
		<img src="${ctx}/images/loader.gif"><span style="color: #eeeeee;font-size: large;">&nbsp;Loading...</span>
	</div>


	<div class="form-box" id="login-box" style="width: 660px;">
		<div class="header">Register New Membership</div>
		<form id="fm" method="post">
			<fieldset>
				<div class="body bg-gray" style="height: 440px;">
					<legend>Restaurant information</legend>
					<div class="form-group" style="margin-top: 0px;width: 460px;">
						<div class="row">
							<div class="col-md-4">
								<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Select Area</label>
							</div>
							<div class="col-md-8">
								<select name="area" id="area" class="form-control"  data-placement="right">
								</select>
							</div>
						</div>
						
					</div>
					
					<div class="form-group" style="float: left;margin-bottom: 0px;margin-top: 0px;">
						<input type="text" name="restaurantName" class="form-control" placeholder="Restaurant name" data-placement="right" />
					</div>
					<div class="form-group" style="float: right;margin-bottom: 0px;margin-top: 0px;">
						<input type="text" name="restaurantContact" class="form-control" placeholder="Contact" data-placement="right" />
					</div>
					<div class="form-group" style="float: left;margin-bottom: 35px;">
						<input type="text" name="restaurantPhone" class="form-control" placeholder="Phone" data-placement="right" />
					</div>
					<div class="form-group" style="float: right;">
						<input type="text" name="restaurantAddress" class="form-control" placeholder="Address" data-placement="right" />
					</div>
					<legend>administrator information </legend>

					<div class="form-group" style="float: left;margin-bottom: 0px;margin-top: 0px;">
						<input type="text" name="firstName" class="form-control" placeholder="Frist name" data-placement="right" />
					</div>
					<div class="form-group" style="float: right;margin-bottom: 0px;margin-top: 0px;">
						<input type="text" name="lastName" class="form-control" placeholder="Last name" data-placement="right" />
					</div>
					<div class="form-group" style="float: left;margin-bottom: 20px;">
						<input type="password" name="password" class="form-control" placeholder="Password" data-placement="right" />
					</div>
					<div class="form-group" style="float: right;">
						<input type="password" name="password2" class="form-control" placeholder="Retype password" data-placement="right" />
					</div>
					<div class="form-group" style="width: 620px;">
						<input type="text" name="code" class="form-control" placeholder="Email" data-placement="right" />
					</div>
				</div>
				<div class="footer">

					<button type="button" id="sub" class="btn bg-olive btn-block" data-placement="top">Register</button>

					<a href="${ctx }/restaurant/login" class="text-center">I already have a restaurant account</a>
				</div>
			</fieldset>
		</form>
	</div>


	<!-- jQuery 1.11.3 -->
	<script src="${ctx }/framework/jquery-1.11.3.js"></script>
	<!-- Bootstrap -->
	<script src="${ctx }/framework/bootstrap.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/js/form_validation.js"></script>
	<script type="text/javascript" src="${ctx}/js/restaurant/register.js"></script>
	<script src="${ctx }/js/appPath.js" type="text/javascript"></script>
</body>
</html>
