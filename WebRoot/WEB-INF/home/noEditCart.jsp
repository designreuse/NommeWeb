<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML>
<html>
<head>
</head>
<body>
	<h4>
		<img src="${ctx}/index/images/restaurst06.jpg"> When
	</h4>
	<div class="row controls">
		<div class="col-md-2"></div>
		<div class="col-md-10" align="right" style="padding-left:25px;height:36px;width:270px;margin-bottom:2px; line-height:36px;
			margin-bottom:10px;
			border:1px solid #dddddd;background-color:#ffffff; border-radius:4px;text-align: center;font-size: 16px;">
			${cartHeader.orderDate}
		</div>
	</div>
	<div></div>
	<div class="zhucyemian-miaodi"></div>
	<h4>
		<img src="${ctx}/index/images/restaurst07.jpg"> Order
	</h4>
	<div class="rest-lef-zykuan" style="margin-left:0px;margin-right:0px;">
		<!-- 购物车中菜品数量 -->
		<c:if test="${fn:length(cartHeader.item)<=0}">
		<input type="hidden" name="orderType" value="0">
		<div class="zhucyemian-miaodi"></div>
			<div class="row">
				<div class="col-md-12" align="center">
					Empty
				</div>
				
			</div>
			<div class="zhucyemian-miaodi"></div>
		</c:if>
		<c:if test="${fn:length(cartHeader.item)>0}"><!-- 如果购物车中没有菜品就设置ordert=0，如果有菜品就设置取出来的购物车的类型值 -->
			<input type="hidden" name="orderType" value="${cartHeader.orderType}">
			<input type="hidden" name="cartId" value="${cartHeader.cartId}">
			<div class="row">
				<div class="col-md-12" align="center">
					<a name="cart-restaurantName" href="#" value="${cartHeader.restaurantUuid}" style="color:#7DD700;">
						<h4>Order from ${cartHeader.restaurantName}</h4>
					</a>
				</div>
			</div>
			<div class="zhucyemian-miaodi"></div>
			<!-- 购物车中的菜品列表 -->
			<c:forEach items="${cartHeader.item}" var="cartItem" begin="0"  step="1" varStatus="status">
				<div class="row" style="vertical-align:middle;">
				
					<div class="col-md-3 show-subItems">
						<div>
							<input type="hidden" name="cartItemId"  value="${cartItem.cartItemId}">
							<input type="hidden" name="dishId"  value="${cartItem.dishId}">
							<img name="cartItem-image-noEdit" src="${cartItem.photoUrl}" onerror="javascript:this.src='${ctx}/images/no-picture.jpg'" style="height: 60px;">
						</div>
						
						<c:if test="${!empty cartItem.subItem}">
							<div class="show-subItem">
								<div class="row">
								<c:forEach items="${cartItem.subItem}" var="subitem" begin="0"  step="1" varStatus="status">
									<div class="col-md-8"  >
										${subitem.garnishName}
									</div>
									<div class="col-md-4"  >
										
											${subitem.count}
										
									</div>
								</c:forEach>
								</div>
							</div>
						</c:if>
					</div>
				
				
					<%-- <div class="col-md-3">
						<img src="${cartItem.photoUrl}" onerror="javascript:this.src='${ctx}/images/no-picture.jpg'" style="height: 60px;">
					</div>
					<!-- 显示配菜信息 开始 -->
					<c:if test="${!empty cartItem.subItem}">
						<div class="show-subItem">
							<div class="row">
							<c:forEach items="${cartItem.subItem}" var="subitem" begin="0"  step="1" varStatus="status">
								<div class="col-md-8"  >
									${subitem.garnishName}
								</div>
								<div class="col-md-4"  >
									<c:if test="${subitem.count > 1}">
										*${subitem.count}
									</c:if>
								</div>
							</c:forEach>
							</div>
						</div>
					</c:if>
					<!-- 显示配菜信息 结束 --> --%>
					<div class="col-md-6 youmian-miaobian">
						<div align="center">
							<strong>${cartItem.dishName}</strong>
						</div>
						<div class="zhucyemian-miaodi"></div>
						<p>
							<input type="hidden" name="cartItemId"  value="${cartItem.cartItemId}">
							<input type="hidden" name="dishId"  value="${cartItem.dishId}">
							<%-- <img class="aDishButton" name="editCartDish" src="${ctx}/index/images/restaurst10.jpg" alt="Edit">
							<img class="aDishButton" name="deleteCartDish" src="${ctx}/index/images/restaurst11.jpg" alt="Delete"> --%>
						</p>
					</div>
					<div class="col-md-3" style="padding-left: 15px;">
						<div style="vertical-align: middle;">
						<div class="row">
							<div class="col-md-12" style="padding-top: 7px;">
							<span>Qty: </span> <span>${cartItem.num}</span>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12" style="padding-top: 7px;">
								<span>$ </span>
								<span><fmt:formatNumber value="${cartItem.aDishTotalFee}" type="currency" pattern="#0.00"/></span>
							</div>
						</div>
						</div>
					</div>
				</div>
				<c:if test="${!empty cartItem.instruction}">
					<div class="row" style="vertical-align:middle;">
						<div class="col-md-12" style="padding-top: 4px;word-wrap:break-word;word-break:break-all;">
							<span style="font-weight: bold;">Instruction: </span>${cartItem.instruction}
						</div>
					</div>
				</c:if>
				<div class="zhucyemian-miaodi"></div>
			</c:forEach>
		</c:if>
		<div name="discount-dish-content"></div><!-- 用于存放赠送的菜品 -->
	</div>

	<!-- 优惠券 -->
	<c:if test="${fn:length(cartHeader.discountList)>0}">
		<h4 class="rest-h4" style="text-align: center;">
		<img src="${ctx}/index/images/restaurst12.jpg" style="float:left;padding-left: 20px;">
		<span href="#myModa2" style="color:#FFFFFF;" data-toggle="modal" data-target="#myModa2">Use Coupon</span>
		</h4>
	</c:if>
		
	
	<div name="discountContent" class="rest-lef-zykuan" style="margin-left:0px;margin-right:0px;">
		<%-- <c:forEach items="${cartHeader.pageDiscountList}" var="discount" begin="0" step="1" varStatus="status"> --%>
			<c:if test="${!empty discount.content}">
				<div class="row">
					<!-- <div class="col-md-1">
						value 保存的是优惠券的id title保存的优惠券的类型  1、现金抵用券 2、打折券 3、赠送菜品
						<input type="radio" checked="true">
					</div> -->
					<div class="col-md-12" align="center">
						${discount.content}
					</div>
				</div>
				
				<div class="zhucyemian-miaodi"></div>
			</c:if>	
		<%-- </c:forEach> --%>
	</div>
	<div class="rest-lef-zykuan" style="margin-left:0px;margin-right:0px;">
		<div class="row">
			<div class="col-md-5">
				<h5>Subtotal:</h5>
			</div>
			<div class="col-md-7" align="right">
				<span>$ </span>
				<c:if test="${empty cartHeader.total}">
					<span id="subTotal">0.00</span>
				</c:if>
				<c:if test="${!empty cartHeader.total}">
				<span id="subTotal"><fmt:formatNumber value="${cartHeader.total}" type="currency" pattern="#0.00"/></span>
				<input type="hidden" id = "hiddenSubTotal" value="<fmt:formatNumber value='${cartHeader.total}' type='currency' pattern='#0.00'/>"><!-- 保留一个原始的菜品价，目的为了切换优惠券时方便计算 -->
				</c:if>
			</div>
		</div>
		<div class="row">
			<div class="col-md-5">
				<h5>Delivery Fee:</h5>
			</div>
			<div class="col-md-7" align="right">
				<span>$ </span>
				<%-- <span id="deliveryFee"><fmt:formatNumber value="${cartHeader.logistics}" type="currency" pattern="#0.00"/></span> --%>
				<c:if test="${cartHeader.orderType != 1}">
					<c:if test="${empty cartHeader.logistics}">
						<span id="deliveryFee">0.00</span>
					</c:if>
					<c:if test="${!empty cartHeader.logistics}">
						<span id="deliveryFee"><fmt:formatNumber value="${cartHeader.logistics}" type="currency" pattern="#0.00"/></span>
					</c:if>
				</c:if>
				<c:if test="${cartHeader.orderType == 1}">
					<span id="deliveryFee"><fmt:formatNumber value="${cartHeader.logistics}" type="currency" pattern="#0.00"/></span>
				</c:if>
			</div>
		</div>
		<div class="row">
			<div class="col-md-5">
				<h5>Sales Tax:</h5>
			</div>
			<div class="col-md-7" align="right">
				<span>$ </span>
				<c:if test="${empty cartHeader.tax}">
					<span id="salesTax">0.00</span>
				</c:if>
				<c:if test="${!empty cartHeader.total}">
					<span id="salesTax"><fmt:formatNumber value="${cartHeader.tax}" type="currency" pattern="#0.00"/></span>
				</c:if>
			</div>
		</div>

		<div class="row">
			<div class="col-md-4">
				<strong>TOTAL:</strong>
			</div>
			<div class="col-md-8" align="right">
				
				<span style=" color:#67A224; font-size:16px; font-weight:900;">$ </span>
				<c:if test="${empty cartHeader.tax}">
					<span id="cartTotal" style=" color:#67A224; font-size:16px; font-weight:900;">0.00</span>
				</c:if>
				<c:if test="${!empty cartHeader.total}">
					<span id="cartTotal" style=" color:#67A224; font-size:16px; font-weight:900;"><fmt:formatNumber value="${cartHeader.allTotal}" type="currency" pattern="#0.00"/></span>
				</c:if>
				
			</div>
		</div>
		<!-- <div class="zhucyemian-miaodi"></div> -->
		
	</div>


	<script type="text/javascript" src="${ctx}/index/js/cart.js"></script>
</body>
</html>
