<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<!DOCTYPE HTML>
<html>
  <head>
    <title>My JSP 'editBasisInfo.jsp' starting page</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%-- <script src="${ctx}/index/js/jquery.min.js"></script> --%>
	<%-- <script src="${ctx}/index/js/bootstrap.min.js"></script> --%>
	<%-- <script src="${ctx}/index/js/commjs.js"></script> --%>
	<%-- <script src="${ctx}/index/js/user.js"></script> --%>

  </head>
  
  <body>
    <div  class="tab-pane" ><!-- style="margin-top: 500px;" -->
		<div align="center" class="rest-miaodi rest-miaodi-gao"></div>
		<div style="margin-left: 10px;margin-right: 10px;">
			<div class="userpadd">
				<div class="row">
					<div class="col-md-8">
						<p><strong>Name</strong></p>
						<p name="name-show">${consumer.firstName} ${consumer.lastName}</p>
						
						<div class="hidden-style" name="name-edit-div">
							
							<div class="row">
								<div class="col-md-6">
									<p>First Name</p>
									<p>
										<input type="text" class="form-control" id="input-firstName">
									</p>
								</div>
								<div class="col-md-6">
									<p>Last Name</p>
									<p>
										<input type="text" class="form-control" id="input-lastName">
									</p>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3"></div>
								<div class="col-md-3">
									<button class="btn btn-success-hui btn-lg btn-block" id="name-cancel">Cancel</button>
								</div>
								<div class="col-md-3">
									<button class="btn btn-success-hui btn-lg btn-block" id="name-save">Save</button>
								</div>
							</div>
						</div>
					</div>
					
					<div class="col-md-2"></div>
					<div class="col-md-2" style="margin-top:10px;" align="right">
						<button class="btn btn-success-hui btn-lg btn-block" type="submit" id="name-edit">Edit</button>
					</div>
					<div class="row rest-miaodi" style="display:none;">
					
				</div>
				</div>
			</div>

			<div class="userpadd">
				<div class="row">
					<div class="col-md-8">
						<p><strong>Email</strong></p>
						<p id="orginal-email">${consumer.email}</p>
						<div class="hidden-style" name="email-edit-div">
							<p>New Email: (Required)</p>
							<p>
								<input type="email" class="form-control"
									id="input-email">
							</p>
							<div class="row">
								<div class="col-md-3"></div>
								<div class="col-md-3">
									<button class="btn btn-success-hui btn-lg btn-block" id="email-cancel">Cancel</button>
								</div>
								<div class="col-md-3">
									<button class="btn btn-success-hui btn-lg btn-block" id="email-save">Save</button>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-2"></div>
					<div class="col-md-2" style="margin-top:10px;" align="right">
						<button class="btn btn-success-hui btn-lg btn-block" id="email-edit">Edit</button>
					</div>
				</div>
			</div>
			
			<div class="userpadd">
				<div class="row">
					<div class="col-md-8">
						<p><strong>Phone</strong></p>
						<p id="orginal-phone">${consumer.phone}</p>
						<div class="hidden-style" name="phone-edit-div">
							<p>Please Enter New Phone Number: (Required)</p>
							<p>
								<input type="text" class="form-control" id="input-phone" placeholder="(xxx) xxx-xxxx">
							</p>
							<div class="row">
								<div class="col-md-3"></div>
								<div class="col-md-3">
									<button class="btn btn-success-hui btn-lg btn-block" id="phone-cancel">Cancel</button>
								</div>
								<div class="col-md-3">
									<button class="btn btn-success-hui btn-lg btn-block" id="phone-save">Save</button>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-2"></div>
					<div class="col-md-2" style="margin-top:10px;" align="right">
						<button class="btn btn-success-hui btn-lg btn-block" id="phone-edit">Edit</button>
					</div>
				</div>
			</div>
			
			<div class="userpadd">
				<div class="row">
					<div class="col-md-8">
						<p><strong>Password</strong></p>
						<p>********************</p>
						
						<div class="hidden-style" name="password-edit-div">
							<p>Orginal Password (Required)</p>
							<p>
								<input type="password" class="form-control" maxlength="20" id="input-password0">
							</p>
							<p>New Password (Required)</p>
							<p>
								<input type="password" class="form-control" maxlength="20" id="input-password1">
							</p>
							<p>Repeat New Password (Required)</p>
							<p>
								<input type="password" class="form-control" maxlength="20" id="input-password2">
							</p>
							
							<div class="row">
								<div class="col-md-3"></div>
								<div class="col-md-3">
									<button class="btn btn-success-hui btn-lg btn-block" id="password-cancel">Cancel</button>
								</div>
								<div class="col-md-3">
									<button class="btn btn-success-hui btn-lg btn-block" id="password-save">Save</button>
								</div>
							</div>
						
						</div>
						
						
					</div>
					<div class="col-md-2"></div>
					<div class="col-md-2" style="margin-top:10px;" align="right">
						<button class="btn btn-success-hui btn-lg btn-block" id="password-edit">Edit</button>
					</div>
				</div>
			</div>
			
			<div class="userpadd">
				<div class="row">
					<div class="col-md-8">
						<p><strong>Special requests</strong></p>
						<p name="specislRequest-show">${consumer.memo}</p>
						
						<div class="hidden-style" name="specislRequest-edit-div">
							<p>Edit Special Request(Optional)</p>
							<p>
								<textarea class="form-control" rows="3" id="specislRequest-text" placeholder="e.g. No MSG"></textarea>
							</p>
							
							<div class="row">
								<div class="col-md-3"></div>
								<div class="col-md-3">
									<button class="btn btn-success-hui btn-lg btn-block" id="specislRequest-cancel">Cancel</button>
								</div>
								<div class="col-md-3">
									<button class="btn btn-success-hui btn-lg btn-block" id="specislRequest-save">Save</button>
								</div>
							</div>
						
						</div>
						
						
					</div>
					<div class="col-md-2"></div>
					<div class="col-md-2" style="margin-top:10px;" align="right">
						<button class="btn btn-success-hui btn-lg btn-block" id="specislRequest-edit">Edit</button>
					</div>
				</div>
			</div>
			
			
			
			
			
			
			
		  	<%-- <div class="row">
		  		<div class="col-md-3"><strong>Order</div>
		  		<div class="col-md-1"><strong>Type</strong></div>
		  		<div class="col-md-2"><strong>Restaurant</strong></div>
		  		<div class="col-md-2"><strong>Create Date</strong></div>
		  		<div class="col-md-1"><strong>Status</strong></div>
		  		<div class="col-md-1"><strong>Total</strong></div>
		  		<div class="col-md-2">
		  			<!-- <button class="btn btn-success-hui btn-block" name="orderDetail">Order Details</button> -->
		  			<strong>Details</strong>
		  		</div>
		  		
		  	</div>
		  	<!-- <div class="zhucyemian-miaodi"></div> -->
		  	<c:if test="${fn:length(ordersInfo)<=0}">
				<input type="hidden" name="orderType" value="0">
				<div class="zhucyemian-miaodi"></div>
				<div class="row">
					<div class="col-md-12" align="center">
						Empty
					</div>
					
				</div>
				<div class="zhucyemian-miaodi"></div>
			</c:if>
			<c:if test="${fn:length(ordersInfo)>0}">
				<c:forEach items="${ordersInfo}" var="order" begin="0"  step="1" varStatus="status">
				<div class="zhucyemian-miaodi"></div>
					<div class="row">
				  		<div class="col-md-3">${order.orderNo}</div>
				  		<div class="col-md-1">${order.orderType}</div>
				  		<div class="col-md-2">${order.restaurantName}</div>
				  		<div class="col-md-2">${order.createDate}</div>
				  		<div class="col-md-1">${order.status}</div>
				  		<div class="col-md-1">${order.total}</div>
				  		<div class="col-md-2">
				  			<button class="btn btn-success-hui btn-block" name="orderDetail" value="${order.id}">Order Details</button>
				  		</div>
				  	</div>
				</c:forEach>
			
			
			
			
			
			</c:if> --%>
			</div>	
		</div>
	
	
	
  </body>
</html>
