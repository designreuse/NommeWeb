<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>Nomme</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ceshi</title>
<link rel="stylesheet" href="${ctx}/index/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/index/css/search_list.css">
<link rel="stylesheet" href="${ctx}/index/css/commcss.css">
<link rel="stylesheet" href="${ctx }/css/datatimepicker/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="${ctx}/css/loading.css">
<link rel="stylesheet" href="${ctx}/index/css/bootstrap-combined.min.css">

<script src="${ctx}/index/js/jquery.min.js"></script>
<script src="${ctx}/index/js/jquery.cookie.js"></script>
<script src="${ctx}/index/js/bootstrap.min.js"></script>
<script src="${ctx}/framework/plugins/datatimepicker/bootstrap-datetimepicker.js" type="text/javascript"></script> 
<script src="${ctx}/framework/plugins/paginator/bootstrap-paginator.js"></script>
<script src="${ctx}/index/js/commjs.js"></script>

</head>
<body>
	<!-- 等待遮罩层 -->
	<div id="bg"></div>
	<div id="show">
		<img src="${ctx}/images/loader.gif"><span style="color: #eeeeee;font-size: large;">&nbsp;Loading...</span>
	</div>

	<div class="search_top">
		<div class="container">
			<div class="row">
				<div class="col-md-5">
					<h4>
						<a name="logo" href="#">
							<img src="${ctx}/index/images/xc-logo.png">
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
							<button class="dropdown-toggle" type="button" id="dropdownMenu1"
								data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
								<span name="currentLoginUserName"></span>
								<span class="caret"></span>
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
					<!-- 插入用户帐号相关功能模态框 -->
					<jsp:include page="./inputjsp.jsp"></jsp:include>
				</div>
			</div>
		</div>

	</div>
	<!-- center kai-->

	<div class="container bgbai">
		<div class="row">
			<div class="col-md-8" style="padding:0;">
				<div style="padding:10px;">
					<div class="row">
						<div class="col-md-2" style="padding-top: 15px;padding-right:10px;" align="right" ><!-- 返回商家列表页 -->
							<button type="button" class="btn xc-btn-default" name="back-searchlist">
								<span class="glyphicon glyphicon-chevron-left" style="font-size: 16px;color:#7DD700;font-weight: bold;">
								</span>&nbsp;&nbsp;Back</button>
							
						</div>
						<div class="col-md-6" style="padding-left: 0px;" align="left" ><!-- 商家名称 -->
							<h3 style="padding-left: 15px;">${restaurant.restaurantName}</h3>
						</div>
						<div class="col-md-2" style="padding-top: 15px;" align="right"><!-- 收藏按钮 -->
							<a class="cursorPointer" style="display:bloack;"><img  name="favoriteImg" title="Favorite" src="${ctx}/index/images/collect-0.jpg" ></a>
						</div>
						<div class="col-md-2" style="padding-top: 15px;" align="right">
							<button type="button" class="btn xc-btn-success" name="reservation">Reservation</button>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4" align="center">
							<img src="${restaurant.logourl}" class="thumbnail" onerror="javascript:this.src='${ctx}/images/no-picture.jpg'" style="height:180px;width:180px;">
						</div>
						<div class="col-md-8 restaurant-introduction">
							<h4 style="margin-top: 0px;">
								<c:if test="${!empty restaurant.chainName}">
									Chain Name: ${restaurant.chainName}
								</c:if>
								<div class="row" style="font-size:26px;font-weight:bold;">
									<div class="col-md-12" align="left">
										<c:if test="${restaurant.score==5}">
											<span style="color: #7FF700">★★★★★</span>
										</c:if>
										<c:if test="${restaurant.score==4}">
											<span style="color: #7FF700">★★★★</span><span style="color: #7FF700">☆</span>
										</c:if>
										<c:if test="${restaurant.score==3}">
											<span style="color: #7FF700">★★★</span><span style="color: #7FF700">☆☆</span>
										</c:if>
										<c:if test="${restaurant.score==2}">
											<span style="color: #7FF700">★★</span><span style="color: #7FF700">☆☆☆</span>
										</c:if>
										<c:if test="${restaurant.score==1}">
											<span style="color: #7FF700">★</span><span style="color: #7FF700">☆☆☆☆</span>
										</c:if>
										<c:if test="${restaurant.score==0}">
											<span style="color: #7FF700">☆☆☆☆☆</span>
										</c:if>
									</div>
								</div>	
								<br>
								<div class="row" >
									<div class="col-md-12" align="left">
										Address :&nbsp;&nbsp;${restaurant.restaurantAddress}
									</div>
								</div>
								<div class="rest-miaodi" style="margin-top:5px;margin-bottom:5px;"></div>
								<div class="row" style="margin-top: 5px;">
									<div class="col-md-12" align="left">
										Estimate Delivery Time :&nbsp;&nbsp;${restaurant.deliverTime} Minutes
									</div>
									<br>
								</div>
								<c:if test="${restaurant.isdelivery==1}">
									<div class="row" style="margin-top:5px;">
										<div class="col-md-12" align="left">
											Deliver Distance :&nbsp;&nbsp;
											<fmt:formatNumber value="${restaurant.distance}" type="currency" pattern="#0.00"/> km
										</div>
									</div>
									<div class="row" style="margin-top: 5px;">
										<div class="col-md-12" align="left">
											Min. Delivery Order :&nbsp;&nbsp;$
											<span id="minDeliveryFee">
												<fmt:formatNumber value="${restaurant.deliverPrice}" type="currency" pattern="#0.00"/>
											</span>
										</div>
									</div>
								</c:if>
							</h4>
							
						</div>
					</div>
				</div>
				<!--xialakaishi-->
			
				<div role="tabpanel">
					<!-- Nav tabs -->
					<ul class="rest-xiala rest-xiala-jg" role="tablist">
						<li role="presentation" class="active">
							<a href="#menu" aria-controls="menu" role="tab" data-toggle="tab">Menu</a>
						</li>
						<li role="presentation">
							<a href="#about" aria-controls="about" role="tab" data-toggle="tab">About Us</a>
						</li>
						<li role="presentation" name="li3">
							<a href="#reviews" aria-controls="reviews" role="tab" data-toggle="tab">Reviews</a>
						</li>
					</ul>
					<!-- 商家消息显示 店铺公告 -->
					<c:if test="${!empty restaurant.notice}">
						<div class="row">
							<div class="col-md-12" style="color:#67A224"><!-- 店铺公告 -->
								<div class="userpadd">Notice:&nbsp;&nbsp;${restaurant.notice}</div>
							</div>
						</div>
						
					</c:if>
					<!-- Tab panes -->
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active" id="menu"><!-- 第一个选项卡 Menu -->
							<div class="panel-group" id="" role="tablist" aria-multiselectable="true">
								<div class="panel panel-default">
				<!-- 优惠券标题 -->
				<div class="" role="tab" id="headingOne">
					<h4 class="rest-panel-one">
						<a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="false"
							aria-controls="collapseOne" > Coupons
							<span class="glyphicon glyphicon-chevron-down"></span>
						</a>
					</h4>
				</div>
				<!-- 优惠券展开内容 -->
				<div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
					<div class="panel-body">
						<div style="position:relative;">
							<div align="right" style=" font-size:18px; padding-top:15px; position:absolute; 
								background-color: #EBECED; right:0px; height:49px; top:-64px; z-index:1025;">
								<a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
									<span class="glyphicon glyphicon-chevron-up" style="color:#FF9C00;"></span>
								</a>
							</div>
						</div>
						
							<!-- 显示当前商家所有优惠信息 -->
							<c:forEach items="${restaurant.pageDiscountList}" var="discount" begin="0" step="1" varStatus="status">
								<div class="row ">
									<div class = "col-md-12">
										<h5>${discount.content}</h5>
									</div>
								</div>
								<div class="rest-miaodi"></div>
							</c:forEach>
					
					</div>
				</div>
			</div>
							
				<div id="accordion"><!-- 封装菜品列表的容器 -->
				</div>
			</div>
		</div>
		<div role="tabpanel" class="tab-pane" id="about"><br><!-- 第二个选项卡 About -->
			<div class="userpadd">
				<h3 align="left" class="rest-miaodi">Introduction</h3>
				${restaurant.features}
				
				<h3 align="left" class="rest-miaodi">Business Hours</h3>
				<div class="row">
					<div class="col-md-4" align="center">
						<strong>Pick Up</strong>
					</div>
					<div class="col-md-4" align="center">
						<strong>Delivery</strong>
					</div>
					<div class="col-md-4" align="center">
						<strong>Reservation</strong>
					</div>
				
				</div>
				<div class="row">
					<div class="col-md-4">
						<c:forEach items="${opentimeClassify.pickupOpentimeList}" var="time">
							<div class="row">
								<div class="col-md-3" align="right">
								<c:if test="${time.week == 7}">Sun.</c:if>
								<c:if test="${time.week == 1}">Mon.</c:if>
								<c:if test="${time.week == 2}">Tues.</c:if>
								<c:if test="${time.week == 3}">wed.</c:if>
								<c:if test="${time.week == 4}">Thur.</c:if>
								<c:if test="${time.week == 5}">Fri.</c:if>
								<c:if test="${time.week == 6}">Sat.</c:if>
								</div>
								<div class="col-md-9">
									${time.starttime} -- ${time.endtime}
								</div>
							</div>
						</c:forEach>
					</div>
					<div class="col-md-4">
						<c:forEach items="${opentimeClassify.deliveryOpentimeList}" var="time">
							<div class="row">
								<div class="col-md-3" align="right">
								<c:if test="${time.week == 7}">Sun.</c:if>
								<c:if test="${time.week == 1}">Mon.</c:if>
								<c:if test="${time.week == 2}">Tues.</c:if>
								<c:if test="${time.week == 3}">wed.</c:if>
								<c:if test="${time.week == 4}">Thur.</c:if>
								<c:if test="${time.week == 5}">Fri.</c:if>
								<c:if test="${time.week == 6}">Sat.</c:if>
								</div>
								<div class="col-md-9">
									${time.starttime} -- ${time.endtime}
								</div>
							</div>
						</c:forEach>
					</div>
					<div class="col-md-4">
						<c:forEach items="${opentimeClassify.reservationOpentimeList}" var="time">
							<div class="row">
								<div class="col-md-3" align="right">
								<c:if test="${time.week == 7}">Sun.</c:if>
								<c:if test="${time.week == 1}">Mon.</c:if>
								<c:if test="${time.week == 2}">Tues.</c:if>
								<c:if test="${time.week == 3}">wed.</c:if>
								<c:if test="${time.week == 4}">Thur.</c:if>
								<c:if test="${time.week == 5}">Fri.</c:if>
								<c:if test="${time.week == 6}">Sat.</c:if>
								</div>
								<div class="col-md-9">
									${time.starttime} -- ${time.endtime}
								</div>
							</div>
						</c:forEach>
					</div>
				
				</div>
				
				<%-- <c:forEach items="${openTimeList}" var="openTime" >
					<div class="row">
						<div class="col-md-3">
							${openTime.week}
							
					<!-- 		
							deliveryOpentimeList;//外卖营业时间集合
	private List<PageOpenTime> pickupOpentimeList;//堂自取营业时间集合
	private List<PageOpenTime> reservationOpentimeList
							 week;// 星期 //this one actually is day: Monday, Tuesday...Sunday
			private String starttime;// 开始时间
			private String endtime;// 结束时间
			private Integer type;/ -->
						</div>
						<div class="col-md-6">
							${openTime.starttime} ---- ${openTime.endtime}
						</div>
					</div>
				
				
				</c:forEach>
				 --%>
			</div>
		</div>
						<div role="tabpanel" class="tab-pane" id="reviews"><!-- 第三个选项卡 Reviews -->
							<div class="zhucyemian-miaodi"></div>
							<!-- id="paging-div-content" 这段为分页显示评论 -->
							<!-- <div id="paging-div-content" >
								<div id="sun-page-div">
									
								</div>
								<div class="row">
						  			<div class="col-md-11">
										<div id="paging" style="float:right;"></div>
						  			</div>
						  			<div class="col-md-1"></div>
						  		</div>
							</div> -->
							<c:forEach items="${restaurant.pageEvaluateList}" var="evaluate" begin="0"  step="1" varStatus="status">
								<!-- 显示评论页面的div -->
								
								<div class="row evaluate_content" >
									<div class="col-md-8" style="padding:10px;" >
										${evaluate.content}
									</div>
									<div class="col-md-4" style="padding:10px;">
										<%-- <div style="color:#7DD700">
											<c:if test="${evaluate.score}==1.0">★☆☆☆☆</c:if>
											<c:if test="${evaluate.score}==2.0">★★☆☆☆</c:if>
											<c:if test="${evaluate.score}==3.0">★★★☆☆</c:if>
											<c:if test="${evaluate.score}==4.0">★★★★☆</c:if>
											<c:if test="${evaluate.score}==5.0">★★★★★</c:if>
										</div> --%>
										<div style="width:50px;text-align:right;float:left;padding-right:10px;">Score:</div>${evaluate.score}<br>
										<div style="width:50px;text-align:right;float:left;padding-right:10px;" >Date:</div>${evaluate.createtime}<br>
										<div style="width:50px;text-align:right;float:left;padding-right:10px;" >name:</div>${evaluate.consumer}
									</div>
									<div class="zhucyemian-miaodi"></div>
								</div>
								
							</c:forEach>
							
							
						</div>
						<!-- <div role="tabpanel" class="tab-pane" id="settings">4</div> -->
					</div>
				</div>
				<!--xialajieshu-->
				<!--Collapse kaishi-->
				<!--Collapse jieshu-->
			</div>
			<div class="col-md-4 rest-leftdi" style="padding:0px;">
				<h3 align="center" class="rest-miaodi">Order Information</h3>
				<div role="tabpanel">
					<!-- Nav tabs -->
						<!-- <div style="float:left; position:relative; z-index:4000;">
							<div style="background-color: yello;height: 30px;width: 80px;float:left;">A</div>
							<div style="background-color: yello;height: 30px;width: 80px;float:left;">b</div>
							<div style="background-color: yello;height: 30px;width: 80px;float:left;">C</div>
						</div> -->
					<div role="tablist">
						<ul class="rest-left-xiala rest-left-xiala-jg" role="tablist">
							<li role="presentation" name="type-pickup" class="active">
								<a href="#biaodan" aria-controls="biaodan" role="tab" data-toggle="tab" id="pickupButton">Pick Up</a>
							</li>
							<li role="presentation" name="type-delivery">
								<a href="biaodan" aria-controls="biaodan" role="tab" data-toggle="tab" id="deliveryButton">Delivery</a>
							</li>
							<li role="presentation" name="type-dinein">
								<a href="#tianxie" aria-controls="tianxie" role="tab" data-toggle="tab" id="dineInButton">Dine In</a>
							</li>
						</ul>
					</div>

					<div class="tab-content rest-lef-hua" style="padding-left: 0px;padding-right: 0px;">
						<div role="tabpanel" class="tab-pane " id="dizhi">
							<!-- <div class="row rest-lef-shkuan">
								<div class="col-md-12"> -->
									<h4>
										<img src="${ctx}/index/images/restaurst05.jpg"> Address
									</h4>
								<!-- </div>
								
							</div> -->
							<div class="panel panel-default">
								
								<div class="panel-body" name="googleMapPlace"></div>
							</div>

						</div>
						<div role="tabpanel" class="tab-pane active" id="biaodan">
							<div class="rest-lef-zykuan">
								<h4>
									<img src="${ctx}/index/images/restaurst06.jpg"> When
								</h4>
								<div class="row controls">
									<div class="col-md-7" align="right">
										<select id="orderTime-day" style="padding-left:30px;height:36px;width:180px;margin-bottom:2px;border-radius:4px;text-align: center;font-size: 16px;">
										</select>
									</div>
									<div class="col-md-5" align="left">
										<select id="orderTime-hourAndMinutes" style="text-align:center; height:36px;width:120px;margin-bottom:2px;border-radius:4px;text-align: center;">
										</select>
									</div>
									<!-- 之前使用timePicker控件产生的时间
									<div class="col-md-1"></div>
									<div class="col-md-10">
										<div class="timeContent">
											<input size="16" type="text" value="" readonly class="form_datetime timePicker" id="orderTime" style="cursor:pointer;">
											<span style="width:10px;color:#ffffff;margin-left:10px;"><i class="glyphicon glyphicon-calendar"></i> </span>
										</div>
									</div>
									<div class="col-md-1"></div> -->
								</div>
								
								<div></div>
							</div>
						</div>
						<div role="tabpanel" class="tab-pane" id="tianxie" >
							<div class="rest-lef-zykuan">
								<h4>
									<img src="${ctx}/index/images/restaurst06.jpg"> Reservation Orders
								</h4>
								
									<div class="row">
										<div class="col-md-12" align="center">
											<div class="controls">
												<select id="reservationOrderSelect" style="height:36px;width:290px;margin-bottom:2px;border-radius:4px;text-align: center;">
													
												</select>
											</div>
										</div>
									</div>
								<!-- <div class="row" >
									
									<div class="col-md-1"></div>
									<div class="col-md-10">
										<div class="timeContent">
											<input size="16" type="text" value="" readonly class="form_datetime timePicker" id="orderTime">
											<sapn style="width:10px;color:#ffffff;margin-left:10px;"><i class="glyphicon glyphicon-calendar"></i> </sapn>
										</div>
									</div>
									<div class="col-md-1"></div>
								</div> -->
							
							
						</div>
					</div>

				</div>
				</div>
				<!-- <input type="button" id="refreshCart" value="refreshCart"> -->
				
	<!-- 购物车 -->
				<input type="hidden" id="reservationOrderId" value=""><!-- 保存reservation订单的id值 -->
				<div id="cartContent" name=""><!-- 此处的name属性保存的是订单类型，默认是pickup 2  /  Devlivery 1 ; Pick Up 2 ;Reservation 3-->
					<!-- 插入购物车相关功能页面-->
					<%-- <jsp:include page="./cart.jsp"></jsp:include> --%>
				</div>
				<div align="right" style="margin:15px 0;padding-right: 10px;">
					<button type="button" class="btn xc-btn-success" name="checkout">Checkout</button>
				</div>
				<div id = "checkoutInfo" style="display:none">
					<div class="row" style="padding-bottom: 10px; color: red;">
						<div class="col-md-12" align="center"  name="checkoutInfo-text">
							
						</div>
					
					</div>
				
				</div>
				<!-- <div id = "timeoutInfo" style="display:none">
					<div class="row" style="padding-bottom: 10px; color: red;">
						<div class="col-md-12" align="center"  name="">
							Is not in besness hours
						</div>
					
					</div>
				
				</div> -->
			</div><!-- 右侧功能结束 -->
			
			
		</div>
		<!-- 菜品模态框开始 Modalkaishi -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<input type="hidden" id = "orderType" value="2"><!-- 保存订单类型 -->
			<div class="modal-dialog" style="width:800px;" id= "dishDialogContent">
				
			</div>
		</div>
		<!-- Modaljieshu -->
		<!-- reservation 订桌模态框 -->
		<div class="modal fade" id="reservationModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width:500px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 id="myModalLabel3" align="center">Reservation</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-6">
								<p>First Name</p>
								<div class="controls">
									<input type="email" class="form-control" id="res-firstName" placeholder="First Name"
										style="height:40px;margin-bottom:10px;">
								</div>
							</div>
							<div class="col-md-6">
								<p>Last Name</p>
								<div class="controls">
									<input type="email" class="form-control" id="res-lastName" placeholder="Last Name"
										style="height:40px;margin-bottom:10px;">
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-6">
								<p>Phone Number</p>
								<div class="controls">
									<input type="text" class="form-control" id="res-phone" placeholder="(xxx)xxx-xxxx"
										maxlength="13" style="height:40px;margin-bottom:10px;">
								</div>
							</div>
							<div class="col-md-6">
								<p>Email</p>
								<div class="controls">
									<input type="email" class="form-control" id="res-email" placeholder="Enter email"
										style="height:40px;margin-bottom:10px;">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<p>When</p>
								<div class="controls">
									<select class="form-control" id="res-orderDay" style="height:40px;margin-bottom:10px;"></select>
										<!-- <input size="16" type="text" value="" readonly class="form_datetime form-control" id="res-time"
										 style="cursor:pointer;background-color: #fff;margin-bottom:10px;height:45px;" placeholder="Choose a time in the future"> -->
								</div>
							</div>
							<div class="col-md-6">
								<p>&nbsp;</p>
								<div class="controls">
									<!-- <input type="text" class="form-control" id="res-peopleNumber" placeholder="Enter a number" maxlength="3"
										style="height:45px;margin-bottom:10px;"> -->
									<select class="form-control" id="res-orderHourAndMinutes" style="height:40px;margin-bottom:10px;"></select>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<p>Table</p>
								<div class="controls">
									<!-- <input type="text" class="form-control" id="res-peopleNumber" placeholder="Enter a number" maxlength="3"
										style="height:45px;margin-bottom:10px;"> -->
										<select class="form-control" id="res-peopleNumber" style="height:40px;margin-bottom:10px;">
										</select>
								</div>
							</div>
							<div class="col-md-6"></div>
						</div>
						
						<p>Special request</p>
						<div class="controls">
							<textarea rows="3" class="form-control" id="res-specialRrequest"  maxlength="300" style="border-radius:4px;border:1px solid #ccc; margin-bottom: 20px;"></textarea>
						</div>
						
						
						<div style=" padding:0 15px 15px;">
							<button id="res-complete" class="btn btn-large btn-block btn-primary" type="button"
								style="height:45px; background:#88878C; border:#88878C;width:457px;margin-left:-14px;" 
								>Complete reservation</button>
							<div align="center" style="margin:10px 0;">
								<strong>OR</strong>
							</div>
							<button class="btn btn-default btn-block" type="button" id="res-toSelectFood"
								style="height:45px; border:#6C9C46 2px solid; color:#6C9C46;width:457px;margin-left:-14px;">Order your food now</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- reservation 确认是否清空购物车模态框 -->
		<div class="modal fade" id="isEmptyCartModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width:500px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 id="myModalLabel" align="center" name="confirmClearCart">Confirm Cart</h4>
					</div>
					<div class="modal-body">
						<h4>
							<div class="controls" name="clearCartTip" style="width:428; margin-left: 15px;margin-bottom: 20px;">
								
							</div>
						</h4>
						<div style=" padding:0 15px 15px;">
							<button id="clearCart" class="btn btn-large btn-block btn-primary" type="button"
								style="height:45px; background:#88878C; border:#88878C" 
								></button>
							<div align="center" style="margin:10px 0;">
								<strong>OR</strong>
							</div>
							<button class="btn btn-default btn-block" type="button" id="stayOrginalCart"
								style="height:45px; border:#6C9C46 2px solid; color:#6C9C46;">stay </button>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		
		<!-- reservation 如果是当天的订单，等待商家确认订桌的模态框 -->
		<div class="modal fade" id="waitAffirmModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width:500px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 id="myModalLabel2" align="center">Reservation is successful</h4>
					</div>
					<div class="modal-body">
						<h4>
							<div class="controls" name="waitAffirmTip" style="width:428; margin-left: 15px;margin-bottom: 20px;">
								You may pre-order your food as soon as</br> the restaurant confirms your reservation!
							</div>
						</h4>
						<div style=" padding:0 15px 15px;">
							<button id="closeWaitAffirm" class="btn btn-large btn-block btn-primary" type="button"
								style="height:45px; background:#88878C; border:#88878C" >
								Check Current Orders
							</button>
							<div align="center" style="margin:10px 0;">
								<strong>OR</strong>
							</div>
							<button class="btn btn-default btn-block" type="button" aria-label="Close" data-dismiss="modal"
								style="height:45px; border:#6C9C46 2px solid; color:#6C9C46;">
								Close
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- reservation 如果不是当天的订单，提示是否现在点菜的模态框 -->
		<div class="modal fade" id="isOrderDishModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width:500px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 id="myModalLabel3" align="center">Reservation success</h4>
					</div>
					<div class="modal-body">
						<h4>
							<div class="controls" name="isOrderDishTip" style="width:428; margin-left: 15px;margin-bottom: 20px;text-align: center;">
								A table has reserved for you!
							</div>
						</h4>
						<div style=" padding:0 15px 15px;">
							<button class="btn btn-default btn-block" type="button" id="closeIsOrderDishModal"
								class="btn btn-large btn-block btn-primary"
								style="height:45px; background:#88878C; border:#88878C; color:#ffffff;">
								<!-- <span style="color:#ffffff;"></span> -->OK
							</button>
							<!-- <button id="isOrderDishToChooseDish" class="btn btn-large btn-block btn-primary" type="button"
								style="height:45px; background:#88878C; border:#88878C">
								Order Dish
							</button>
							<div align="center" style="margin:10px 0;">
								<strong>OR</strong>
							</div>
							<button class="btn btn-default btn-block" type="button" id="closeIsOrderDishModal"
								style="height:45px; border:#6C9C46 2px solid; color:#6C9C46;">
								Check current Order
							</button> -->
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- dinein点击时如果没有 reservation ，提示先去增加reservation订单 -->
		<div class="modal fade" id="noReservationPromptDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width:500px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 id="myModalLabel3" align="center">No Reservation </h4>
					</div>
					<div class="modal-body">
						<h4>
							<div class="controls" name="isOrderDishTip" style="width:428; margin-left: 15px;margin-bottom: 20px;">
								Please make a reservation now!
							</div>
						</h4>
						<div style=" padding:0 15px 15px;">
							<button id="closeNoReservationPromptDialog" class="btn btn-large btn-block btn-primary" type="button"
								style="height:45px; background:#88878C; border:#88878C">
								Close
							</button>
							<div align="center" style="margin:10px 0;">
								<strong>OR</strong>
							</div>
							<button class="btn btn-default btn-block" type="button" id="makeReservation"
								style="height:45px; border:#6C9C46 2px solid; color:#6C9C46;">
								Reservation
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- reservation订单选择 模态框 -->
		<!-- <div class="modal fade" id="reservationOrdersModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width:500px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 id="myModalLabel" align="center">Choose reservation Order</h4>
					</div>
					<div class="modal-body" id = "chooseReservationModalBody">
						<div class="row">
							<div class="col-md-12">
								<p>Upcomming Reservations</p>
								<div class="controls">
									<select class="form-control" id="reservationOrderSelect" style="height:45px;margin-bottom:20px;" >
										
									</select>
								</div>
							</div>
						</div>
						<div style="padding:0 15px 15px;">
							<button id="choosedAnReservationOrder" class="btn btn-large btn-block btn-primary" type="button"
								style="height:45px; width:458px; margin-left:-12px; background:#88878C; border:#88878C">
								Submit
							</button>
						</div>
					</div>
				</div>
			</div>
		</div> -->
	</div>
	
	
	<div class="search-bottom" align="center">
		<p>
			<a href="#"><span>Contact Us</span></a>　　About　　FAQ　　Terms of Use　　Privacy Policy
		</p>
	</div>

	<!-- center jieshu-->
	<!-- dibu kaishi-->
 	<textarea id="currentRestaurantInfo" style="display: none">${restaurantJSON}</textarea>
 	
 	
 	<input type="hidden" id = "isdelivery" value="${restaurant.isdelivery}">
 	<input type="hidden" id = "ispickup" value="${restaurant.ispickup}">
 	<input type="hidden" id = "isreservation" value="${restaurant.isreservation}">
 	<%-- <input type="hidden" id = "cartOrderType" value="${cartHeaderType}"> --%><!-- 保存购物车的订单类型 -->
 	<input type="hidden" id = "consumer-firstName" value="${consumer.firstName}">
 	<input type="hidden" id = "consumer-lastName" value="${consumer.lastName}">
 	<input type="hidden" id = "consumer-email" value="${consumer.email}">
 	<input type="hidden" id = "consumer-phone" value="${consumer.phone}">
 	
 	<input type="hidden" id = "distance">
 	<input type="hidden" id = "reservationOrderId" value="0">
 	<input type="hidden" id = "taxRate" value="${restaurant.taxRate}">
 	<%-- <input type="hidden" id = "canDelivery" value="${restaurant.canDelivery}"> --%>
  	<!-- <input type="hidden" id="sessionId"> -->
 	
	<script src="${ctx}/index/js/restaurantsMenu.js"></script>
	<script type="text/javascript" src="${ctx}/index/js/cart.js"></script>
</body>
</html>
