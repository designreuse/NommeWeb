<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>Nomme</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${ctx}/index/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/index/css/search_list.css">
<link rel="stylesheet" href="${ctx}/index/css/commcss.css">
<link rel="stylesheet" href="${ctx}/css/loading.css">

<style type="text/css">
.popover-content{
	color: #FA6A6A;
}

#charityDisplay{
	border-radius:2px;
	background-color:#dddddd;
	width:600px;
	height:350px;
	position: fixed;
	top: 20%;
	left: 30%;
	padding: 8px;
	z-index: 10003;
	overflow: hidden;
}

#charityAfter{
	border-radius:2px;
	background-color:#dddddd;
	width:600px;
	height:350px;
	position: fixed;
	top: 20%;
	left: 30%;
	padding: 8px;
	z-index: 10003;
	overflow: hidden;
}
</style>

<script src="${ctx}/index/js/jquery.min.js"></script>
<script src="${ctx}/index/js/bootstrap.min.js"></script>
<script src="${ctx}/index/js/payment.js"></script>
<script src="${ctx}/index/js/commjs.js"></script>
<script src="${ctx}/index/js/jquery.cookie.js"></script>
<script type="text/javascript" src="https://js.stripe.com/v2/"></script>
<script type="text/javascript" src="${ctx}/framework/plugins/creditcard/jquery.creditCardValidator.js"></script>
</head>

<body>
	<div id="bg"></div>
	<div id="show">
		<img src="${ctx}/images/loader.gif"><span style="color: #eeeeee;font-size: large;">&nbsp;Loading...</span>
	</div>
	 
	<div id="display" style="display: none">
		<div id="bg" style="display: block;">
		</div>
		<div id="charityDisplay">
			<div id="charityNum" style="margin: 0px auto;width: 400px;margin-top: 50px;font-size: medium;color: #666666;">
				Order placed. You can view your order information at My Nomme.
				<br>
				Please choose where Nomme should donate to:
			</div>
			<div style="width: 150px;margin-top: 50px;margin-left: 160px;" class="row" >
				<div class="col-md-4">
					<select id="az" style="height: 35px;">
					</select>
				</div>
				<div class="col-md-8">
					<select id="charity" style="height: 35px;width: 180px;">
					</select>
				</div>
			</div> 
			<div id="charityButtonArea" style="margin: 0px auto;width: 260px;margin-top: 20px;">
				<button type="button" name="noDonation" class="btn xc-btn-success btn-lg btn-block" data-placement="top">NO THANKS</button>
				<button type="button" name="sure" class="btn xc-btn-success btn-lg btn-block" data-placement="top">OK</button>
			</div>
		</div>
	</div>

	<div id="displayAfter" style="display: none">
		<div id="bg" style="display: block;"></div>
		<div id="charityAfter">
			<div style="margin: 0px auto;width: 450px;margin-top: 50px;font-size: large;color: #666666;">
				Thank you for your contribution!
			</div>
			<div style="margin: 0px auto;width: 260px;margin-top: 50px;">
				<button type="button" name="sureAfter" class="btn xc-btn-success btn-lg btn-block" data-placement="top">OK</button>
			</div>
		</div>
	</div>



	<div class="search_top">

		<div class="container">
			<div class="row">
				<div class="col-md-5">
					<h4>
						<a name="logo" href="#"> <img src="${ctx}/index/images/xc-logo.png">
						</a>
					</h4>
				</div>
				<div class="col-md-7">
					<div class="xc-top-login" style="display: ${empty sessionScope.consumer?'block':'none'}">
						<li><a href="#myModal" data-toggle="modal" data-target="#myModal" id="signIn0">Sign in</a></li>
					</div>
					<!--登陆后显示-->
					<div class="xc-top-login-in" style="display: ${empty sessionScope.consumer?'none':'block'}">
						<li>
							<div class="dropdown">
								<button class="dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
									<span name="currentLoginUserName"></span> <span class="caret"></span>
								</button>
								<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
									<li><a href="${ctx}/index/user?flag=1">Profile</a></li>
									<li><a href="${ctx}/index/user?flag=2">Addresses</a></li>
									<li><a href="${ctx}/index/user?flag=3">Payments</a></li>
									<li><a href="${ctx}/index/user?flag=4">Current Orders</a></li>
									<li><a href="${ctx}/index/user?flag=5">Order History</a></li>
									<li><a href="${ctx}/index/user?flag=6">Favorites</a></li>
									<li><a href="${ctx}/index/signOut">Sign Out</a></li>
								</ul>
							</div>
						</li>
					</div>
					<!--login Modal -->
					<jsp:include page="./inputjsp.jsp"></jsp:include>

				</div>
			</div>
		</div>

	</div>
	<!-- center kai-->

	<div class="container bgbai">
		<div class="row">
			<div class="col-md-8">
				<input type="hidden" id="consumerUuid" value="${cart.consumerUuid}"> 
				<input type="hidden" id="restaurantUuid1" value="${cart.restaurantUuid}"> 
				<input type="hidden" id="orderId">
				
				<c:if test="${cart.orderType==1}"><h4 align="left">Delivery Details</h4>
				<h3 align="left">Check your payment info to complete your order</h3>
				<p></p>
				<div class="zhucyemian-miaodi"></div>
				<p></p>
					<DIV class="row">
					<div class="col-md-6 col-md-offset-1">
						<div style="padding-left:15px;">
							Name: ${cart.peopleName}<br>
							Email: ${cart.email}<br>
							Phone: ${cart.phone}<br>
							<c:if test="${cart.orderType==1}">
								Address: ${cart.address}
							</c:if>
							 <button id="edit" class="btn xc-btn-success btn-sm btn-block" style="width: 50px;margin-left: 70%">Edit</button>
						</div>
					</div>
					<div class="col-md-5">
						<div>Note</div>
						<ul class="list-group">
							<li class="list-group-item">
								${cart.memo}
							</li>
						</ul>
					</div>
				</DIV>
					
				</c:if> 
				
				<c:if test="${cart.orderType==2}"><h4 align="left">Pick-up Details</h4>
				<h3 align="left">Check your payment info to complete your order</h3>
				<p></p>
				<div class="zhucyemian-miaodi"></div>
				<p></p>		

				<DIV class="row">
					<div class="col-md-6 col-md-offset-1">
						<div style="padding-left:15px;">
							Name: ${cart.peopleName}<br>
							Email: ${cart.email}<br>
							Phone: ${cart.phone}<br>
							<c:if test="${cart.orderType==1}">
								Address: ${cart.address}
							</c:if>
							 <button id="edit" class="btn xc-btn-success btn-sm btn-block" style="width: 50px;margin-left: 70%">Edit</button>
						</div>
					</div>
					<div class="col-md-5">
						<div>Special requests</div>
						<ul class="list-group">
							<li class="list-group-item">
								${cart.memo}
							</li>
						</ul>
					</div>
				</DIV>
				</c:if> 
				<%-- <c:if test="${cart.orderType==3}">Reservation</c:if> --%>

				<div class="zhucyemian-miaodi"></div>
				<p></p>

				<h4 align="left">Payment Information</h4>
				<div role="tabpanel">

					<!-- Nav tabs -->

					<div class="btn-group" data-toggle="buttons">
						<label class="btn btn-default active" style="padding: 0px;"> <input type="radio" name="options" id="option1" autocomplete="off" checked
							>
							<a href="#home" aria-controls="home" role="tab" data-toggle="tab"><span style="color:#333333;display:block; width: 94px;height: 32px;padding-top: 6px;">Credit card</span></a></label> 
							<c:if test="${cart.orderType!=3}">
							<label class="btn btn-default" style="padding: 0px;"> <input type="radio" name="options" id="option3" autocomplete="off"><a href="#profile"
							aria-controls="profile" role="tab" data-toggle="tab"><span style="color:#333333;display:block; width:60px;height: 32px;padding-top: 6px;" >Cash</span></a></label>
							</c:if>
					</div>

					<!-- Tab panes -->
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active" id="home">
							<div style="margin: 10px;">
								<img src="${ctx}/img/credit/bankIcon.png" width="20%">
							</div>
							<!--所有可以用的卡  -->
							<c:if test="${!empty cards}">
								<div style="border: 1px solid #dddddd;background-color: #dddddd">
									<h4 id="dropDown" style="cursor: pointer;">
										<a id="lbCardSelection" style="margin-left: 5%">Use a Saved Credit Card</a>
									</h4>
								</div>
								<div id="sonCards" style="border: 1px solid #dddddd;border-radius: 5px;display: none;">

									<c:forEach items="${cards}" var="card">
										<div class="row"
											style="border: 1px solid #dddddd;border-radius:4px;height: 50px;margin-left: 70px;margin-right: 70px;margin-top: 10px;margin-bottom: 10px;background-color: #eeeeee;">
											<div class="col-md-1" style="padding-top: 13px;">
												<input type="radio" name="card"> <input type="hidden" name="cardId" value="${card.id}">
											</div>
											<div class="col-md-2" style="padding-top: 9px;">
												<div>
													<c:if test="${card.type=='Visa' }">
														<img src="${ctx}/img/credit/visa.png" width="50px;">
													</c:if>
													<c:if test="${card.type=='MasterCard' }">
														<img src="${ctx}/img/credit/mastercard.png" width="50px;">
													</c:if>

												</div>

											</div>
											
											<div class="col-md-1" style="padding-top: 10px;">
											</div>
											
											<div class="col-md-6" style="padding-top: 10px;">
												<span style="font-size: x-large;color: #444444;"> ***************${card.last4} </span>
											</div>
											
											<div class="col-md-2" style="padding-top: 10px;">
											<!-- <button class="btn-primary" style="width: 60px;" name="deleteCard">delete</button> -->
											</div>

										</div>
									</c:forEach>

								</div>
							</c:if>
							<div id="cardDetail" style="margin-top: 20px;">
								<form method="POST" id="payment-form">
									<div class="row">
										<div class="col-md-5">
											<div id="numberdiv" class="row" style="border: 1px solid #cccccc;height:32px;margin-left: 5px;border-radius:4px;">
												<div class="col-md-2" style="padding-top: 4px;padding-left: 10px;">
													
													<div id="mastercarddisplay" style="display: none;">
														<img src="${ctx}/img/credit/mastercard.png" width="35px;">
													</div>
													<div id="visadisplay" style="display: none;">
														<img src="${ctx}/img/credit/visa.png" width="35px;">
													</div>
												</div>
												<div class="col-md-10" style="padding: 0px;">
													<input id="number" type="text" maxlength="20" data-stripe="number" style="border-width: 0px;outline: none;padding-top: 4px;width: 100%"
														placeholder="Card Number">
												</div>
											</div>

										</div>

										<div class="col-md-4">
											<div class="row">
												<div class="col-md-6" style="padding-right: 5px;padding-left:50px;">
													<div style="border: 1px solid #cccccc;height: 32px;padding-top: 3px;padding-left: 3px;border-radius:4px;">
														<input id="month" type="text" maxlength="2" data-stripe="exp-month" placeholder="Exp-(MM)"
															style="width: 100%;border-width: 0px;outline: none;" />
													</div>
												</div>

												<div class="col-md-6" style="padding-left: 5px;padding-right: 40px;">
													<div style="border: 1px solid #cccccc;height: 32px;padding-top: 3px;padding-left: 3px;border-radius:4px;">
														<input id="year" type="text" maxlength="4" data-stripe="exp-year" placeholder="Exp-(YYYY)"
															style="width: 100%;border-width: 0px;outline: none;" />
													</div>

												</div>
											</div>
										</div>
										<div class="col-md-2" style="padding-right: 65px;">
											<div style="border: 1px solid #cccccc;height: 32px;padding-left: 8px;padding-top: 3px;border-radius:4px;">
												<input id="cvv" type="text" maxlength="3" data-stripe="cvc" placeholder="CVV" style="width: 100%;border-width: 0px;outline: none;">
											</div>

										</div>
									</div>
									<div>
										<div class="checkbox">
											<label> <input name="save" type="checkbox"> Save card details
											</label>
										</div>
									</div>
							</div>
							<p></p>
							<div class="zhucyemian-miaodi"></div>
							<p></p>

							<h4 align="left">Add a Tip</h4>
							<P>Tip with cash? Skip to next step.</P>
							<DIV class="row">
								<div class="col-md-1 payment-zicu" align="right">$</div>
								<div class="col-md-2">
									<input type="text" class="form-control payment-biao" id="tipNumber" onKeyPress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46)event.returnValue=false">
								</div>
								<div class="col-md-6">
									<button type="button" id="tip10" class="btn btn-default btn-lg">10%</button>
									<button type="button" id="tip15" class="btn btn-default btn-lg">15%</button>
									<button type="button" id="tip20" class="btn btn-default btn-lg">20%</button>
								</div>
							</DIV>

							<DIV class="row" style="margin-top:50px; margin-bottom:30px;">

								<div class="col-md-8 col-md-offset-2" align="center">
									<button type="button" name="placeOrder" class="btn xc-btn-success btn-lg btn-block" data-placement="top">Place Your Order</button>
								</div>

							</DIV>


							</form>

						</div>
						<div role="tabpanel" class="tab-pane" id="profile">
							<div style="margin-left: 10%;margin-right: 10%;margin-top: 30%">
								<button type="button" name="placeOrderByCash" class="btn xc-btn-success btn-lg btn-block" data-placement="top">Place Your Order</button>
							</div>
						</div>

					</div>

				</div>
			</div>


			<div class="col-md-4 rest-leftdi" style="padding:0px;">


				<%-- <div class="rest-lef-zykuan">

					<h4>
				<div class="row">
				<div class="col-md-6">
					<h4>
						<img src="${ctx}/index/images/restaurst07.jpg"> Order
					</h4>
					<div class="zhucyemian-miaodi"></div>
					</div> --%>
					<div class="row">
						<div class="col-md-4 zuomia-shangnei "  align="center">
							<button type="button" name="changeOrder" class="btn xc-btn-success btn-sm btn-block" style="height:30px;margin-left: 130%;margin-bottom: 10px;">Change</button>
						</div>
					</div>
					<div class="zhucyemian-miaodi"></div>
					<div id="cartContent" style="padding-left: 10px;padding-right: 10px;"><!-- 此处的name属性保存的是订单类型，默认是pickup 2  /  Devlivery 1 ; Pick Up 2 ;Reservation 3-->
					<!-- 插入购物车相关功能页面-->
					</div>
					
				<div style="height:150px;"></div>


			</div>
		</div>
	</div>

	<!-- center jieshu-->


	<!-- dibu kaishi-->

	<div class="search-bottom" align="center">
		<a href="#"><span>Contact Us</span></a>
		<a><span>About</span></a>　　FAQ　　Terms of Use　　Privacy policy
	</div>

</body>
</html>
