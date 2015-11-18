<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html class="bg-black">
<head>
<meta charset="UTF-8">
<jsp:include page="../inputcss.jsp"></jsp:include>
<link href="${ctx }/framework/select2/select2.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.form-group {
	width: 400px;
}
</style>
</head>

<body>
	<div id="bg"></div>
	<div id="show">
		<img src="${ctx}/images/loader.gif"><span style="color: #eeeeee;font-size: large;">&nbsp;Loading...</span>
	</div>
	<div class="form-box" style="width: 860px;margin-left: 10%">
		<form action="">
			<div class="form-group" style="float:right;">
				<div class="row">
					<div class="col-md-3">
						<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Last name</label>
					</div>
					<div class="col-md-9">
						<input type="text" name="last_name" class="form-control" id="lastName" placeholder="last_name" data-placement="right" />
					</div>
				</div>
			</div>

			<div class="form-group">
				<div class="row">
					<div class="col-md-3">
						<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">First name</label>
					</div>
					<div class="col-md-9">
						<input type="text" name="first_name" class="form-control" id="firstName" placeholder="first_name" data-placement="right" />
					</div>
				</div>
			</div>
			
			<div class="form-group" style="float:right;">
				<div class="row">
					<div class="col-md-3">
						<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Birth day</label>
					</div>
					<div class="col-md-9">
						<input type="text" name="day" class="form-control" id="day" placeholder="birth day" data-placement="right" />
					</div>
				</div>
			</div>

			<div class="form-group">
				<div class="row">
					<div class="col-md-3">
						<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Birth month</label>
					</div>
					<div class="col-md-9">
						<input type="text" name="month" class="form-control" id="month" placeholder="birth month" data-placement="right" />
					</div>
				</div>
			</div>
			
			<div class="form-group" style="float:right;">
				<div class="row">
					<div class="col-md-3">
						<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Personal id</label>
					</div>
					<div class="col-md-9">
						<input type="text" name="personal_id_number" class="form-control" id="personal_id_number" placeholder="123123123" data-placement="right" />
					</div>
				</div>
			</div>
			

			<div class="form-group">
				<div class="row">
					<div class="col-md-3">
						<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Birth year</label>
					</div>
					<div class="col-md-9">
						<input type="text" name="year" class="form-control" id="year" placeholder="birth year" data-placement="right" />
					</div>
				</div>
			</div>
			
			<div class="form-group" style="float:right;">
				<div class="row">
					<div class="col-md-3">
						<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Address</label>
					</div>
					<div class="col-md-9">
						<input type="text" name="line1" class="form-control" id="line1" placeholder="Street address/PO Box/Company name" data-placement="right" />
					</div>
				</div>
			</div>
			
			<div class="form-group">
				<div class="row">
					<div class="col-md-3">
						<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Type</label>
					</div>
					<div class="col-md-9">
						<select name="type" class="form-control" id="type"  data-placement="right">
							<option value="company">company</option>
							<option value="individual">individual</option>
						</select>
					</div>
				</div>
			</div>
			
			<div class="form-group"  style="float:right;">
				<div class="row">
					<div class="col-md-3">
						<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">State</label>
					</div>
					<div class="col-md-9">
						<select name="state" class="form-control" id="state" data-placement="right">
							<option value="AB">AB</option>
							<option value="BC">BC</option>
							<option value="MB">MB</option>
							<option value="NB">NB</option>
							<option value="NL">NL</option>
							<option value="NS">NS</option>
							<option value="NT">NT</option>
							<option value="NU">NU</option>
							<option value="ON">ON</option>
							<option value="PE">PE</option>
							<option value="QC">QC</option>
							<option value="SK">SK</option>
							<option value="YT">YT</option>
						</select>
					</div>
				</div>
			</div>
			
			<div class="form-group">
				<div class="row">
					<div class="col-md-3">
						<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">City</label>
					</div>
					<div class="col-md-9">
						<input type="text" name="city" class="form-control" id="city" placeholder="city" data-placement="right" />
					</div>
				</div>
			</div>
			
			<div class="form-group" style="float:right;">
				<div class="row">
					<div class="col-md-3">
						<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Tax id</label>
					</div>
					<div class="col-md-9">
						<input type="text" name="business_tax_id" class="form-control" id="business_tax_id" placeholder="business tax id" data-placement="right" />
					</div>
				</div>
			</div>

			
			<div class="form-group">
				<div class="row">
					<div class="col-md-3">
						<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Postal code</label>
					</div>
					<div class="col-md-9">
						<input type="text" name="postal_code" class="form-control" id="postalCode" placeholder="postal code" data-placement="right" />
					</div>
				</div>
			</div>
			<div class="form-group" style="float:right;">
				<div class="row">
					<div class="col-md-3">
						<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Transit num</label>
					</div>
					<div class="col-md-9">
						<input type="text" name="routing_number" class="form-control" id="routingNumber" placeholder="transit number 12345-000" data-placement="right" />
					</div>
				</div>
			</div>
			
			<div class="form-group" >
				<div class="row">
					<div class="col-md-3">
						<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Name</label>
					</div>
					<div class="col-md-9">
						<input type="text" name="business_name" class="form-control" id="businessName" placeholder="The legal name of the company" data-placement="right" />
					</div>
				</div>
			</div>
			
			
			
			
			<div class="form-group">
				<div class="row">
					<div class="col-md-3">
						<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">account num</label>
					</div>
					<div class="col-md-9">
						<input type="text" name="account_number" class="form-control" id="accountNumber" placeholder="bank account number" data-placement="right" />
					</div>
				</div>
			</div>

			
			<input type="hidden" name="ip"/>
	
		</form>
	</div>

	<div style="margin-left: 10%" id="have">
		<span style="color: #999999">By registering your account, you agree to our</span> <a> Terms of Service</a> <span style="color: #999999"> and
			the </span> <a id="locationAccount" href="javascript:void(0)">Stripe Connected Account Agreement</a>. <br /> <br />
		<button class="btn-primary" style="margin-left: 30%" id="register" data-placement="top" >Register your account</button>
	</div>
	
	<div style="margin-left: 10%;display: none;" id="noHave" >
		<button class="btn-primary" style="margin-left: 30%" id="update" data-placement="top" >Modify your account</button>
	</div>

	<jsp:include page="../inputjs.jsp"></jsp:include>
	<script type="text/javascript" src="${ctx}/js/form_validation.js"></script>
	<script type="text/javascript" src="${ctx}/framework/select2/select2.js"></script>
	<script type="text/javascript" src="https://pv.sohu.com/cityjson?ie=utf-8" charset="gb2312"></script> 
	
	<script type="text/javascript" src="${ctx}/js/restaurant/stripeaccount.js"></script>

</body>
</html>
