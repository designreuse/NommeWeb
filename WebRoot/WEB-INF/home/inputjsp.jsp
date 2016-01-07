<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!-- 登录模态框 Modal -->
<div class="login-modal modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:500px;">
		<div class="modal-content">
			<div class="modal-header" style="border-bottom:0;">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="myModalLabel">Please Sign In</h4>
			</div>
			<div class="modal-body">
				<div class="controls">
					<input type="email" class="form-control"
						id="loginInputEmail1" placeholder="Email Address"
						style="height:45px;">
				</div>
				<div class="controls">
					<input type="password" class="form-control"
						id="loginInputPassword1" placeholder="Password" maxlength="20"
						style="height:45px;">
				</div>
				<div align="left" style="margin:10px 5px;">
					<span style="float:left;text-align:left;">
						<input type="checkbox" id = "autoLogin">
						<span>Remember Me</span>
					</span>
					<span style="float:right;">
						<a href="javascript:void(0)" name="forgotPassword" class="forgotPassword" >Forgot your password</a>
					</span>
				</div>
			</div>
			<div id="loginModalButtons" style=" padding:15px 25px 0px 25px;">
				<button id="loginSignIn1" class="btn btn-large btn-block btn-primary" type="button"
					>Login</button>
				<div style="margin:10px 0;border-top:1px solid #eeeeee;padding-top: 15px">
					<strong>OR ARE YOU A NEW USER?</strong>
				</div>
				<div>
					<span>Create an account to save your location, favorite dishes, restaurants and much more...</span>
				</div>
				<div>
					<button class="btn btn-large btn-block btn-primary" type="button" id="gotoCreateModal">Create Account</button>
				</div>
				<div class="thirdPartyLogin" style="margin-top:15px;width:100px;height:35px;margin-left: 370px;">
					<div class="facebook-logo" name="facebook">
					</div>
					<div class="twitter-logo" name="twitter">
					</div>
				</div>
				
			</div>
		</div>
	</div>
</div>

<%-- <div align="right" style="margin-top:15px; z-index:1000;">
					<div class="loginEffect" >
						
						<div name="twitter"></div>
						<div name="facebook"></div>
					</div>
					<img src="${ctx}/index/images/third-login-1.jpg">
					
				</div> --%>
				

<!-- 新建账号的模态框 -->
<div class="login-modal modal fade" id="createAccountModal" tabindex="-2" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:500px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="myModalLabel" align="center">Create Account</h4>
			</div>
			<div class="modal-body">
			<form id="createAccountInfo">
				<p>First Name</p>
				<div class="controls">
					<input type="email" class="form-control" id="firstName" placeholder="First Name"
						style="height:35px;margin-bottom:10px;">
				</div>
				<p>Last Name</p>
				<div class="controls">
					<input type="email" class="form-control" id="lastName" placeholder="Last Name"
						style="height:35px;margin-bottom:10px;">
				</div>
				<p>Email</p>
				<div class="controls">
					<input type="email" class="form-control" id="email" placeholder="Enter email"
						style="height:35px;margin-bottom:10px;">
				</div>
				<p>Phone Number</p>
				<div class="controls">
					<input type="text" class="form-control" id="phone" placeholder="(xxx) xxx-xxxx"
						style="height:35px;margin-bottom:10px;">
				</div>
				<!-- <div align="left" style="margin:10px 0;">
					password
				</div> -->
				<p>Password</p>
				<div class="controls">
					<input type="password" class="form-control" id="password1" placeholder="Password" 
						maxlength="20" style="height:35px;margin-bottom:10px;">
				</div>
				<p>Confirm Password</p>
				<div class="controls">
					<input type="password" class="form-control" id="password2" placeholder="Confirm Password"
						maxlength="20" style="height:35px;margin-bottom:10px;">
				</div>
			</form>
			</div>
			<div style=" padding:0px 20px 15px 20px;">
				<!-- <button class="btn btn-large btn-block btn-primary"
					type="button"
					style="height:45px; background:#88878C; border:#88878C">Sign
					In</button>
				<div align="center" style="margin:10px 0;">
					<strong>OR</strong>
				</div> -->
				<button class="btn btn-large btn-block btn-primary" type="button" id = "createAccount">Create
					Account</button>
			</div>
			</br>
		</div>
	</div>
</div>

<!-- 忘记密码获 取新密码模态框 -->
<div class="login-modal modal fade" id="forgotPasswordModal" tabindex="-3" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:500px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="myModalLabel" align="center">Reset Password</h4>
			</div>
			<div class="modal-body" name="createAccountInfo">
			
				<p>Email</p>
				<div class="controls">
					<input type="email" class="form-control" id="forgetPasswordEmail" placeholder="Get verification code by email"
						style="height:45px;margin-bottom:10px;">
				</div>
				<div style=" padding:5px 5px 15px 5px;">
					<button id="sendEmail" class="btn btn-large btn-block btn-primary" type="button"
						>Send Email</button>
				</div>
				<div id = "showCodeInput" style="display:none; padding-top: 15px;">
				<p>Verification code</p>
				<div class="controls">
					<input type="email" class="form-control" maxlength="6"
						id="verificationCode" placeholder="Check email and enter verification code"
						style="height:45px;margin-bottom:10px;">
				</div>
				</div>
				<!--  当验证码输入正确后，显示下面的填新密码的input框 -->
				<div id = "showPasswordInput" style="display:none;">
				<p>Password</p>
				<div class="controls">
					<input type="password" class="form-control"
						id="newPassword1" placeholder="New Password"
						style="height:45px;margin-bottom:10px;">
				</div>
				<p>Confirm Password</p>
				<div class="controls">
					<input type="password" class="form-control"
						id="newPassword2" placeholder="Confirm New Password"
						style="height:45px;margin-bottom:10px;">
				</div>
				
				<div style=" padding:10px 15px 15px 5px;">
					<button class="btn btn-large btn-block btn-primary" type="button" id="setNewPassword">Set New Password</button>
				</div>
				</div>
			</div>
			</br>
		</div>
	</div>
</div>

<!-- 获取session中当前登录的用户的nickname，firstName，lastName -->
<input type="hidden" id = "currentNickname" value=${sessionScope.consumer.nickname}>
<input type="hidden" id = "currentFirstName" value=${sessionScope.consumer.firstName}>
<input type="hidden" id = "currentLastName" value=${sessionScope.consumer.lastName}>
<input type="hidden" id = "currentConsumerUuid" value=${sessionScope.consumer.uuid}>
<input type="hidden" id = "restaurantUuid" value=${restaurant.restaurantUuid}>
<input type="hidden" id = "restaurantTaxRate" value=${restaurant.taxRate}><!-- 店家税率 -->
<input type="hidden" id = "googleAutocompletePlace" value="No place information">