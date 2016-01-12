<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE HTML>
<html>
	 <head>
	</head>
	<body>
		<div class="modal-content"><!-- G -->
			<div class="rest-modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span class="glyphicon glyphicon-remove-circle"
						aria-hidden="true"> </span>
				</button>
				<h4 class="modal-title" id="myModalLabel"></h4>
			</div>
			<div class="modal-body">
				<input type="hidden" id="cartItemId" value="0">
				<input type="hidden" id="currentDishId" value="${dish.id}">
				<input type="hidden" id="orginalDishPrice" value="${dish.price}">
				<input type="hidden" id="cart-orderId"><!-- dine in 点菜时 保存 reservation订单的id -->
				<div class="row">
					<div class="col-md-4">
						<%-- <c:if test="${empty dish.photoUrl}">
							<img class="aDishButton" src="${ctx}/index/images/restaurst13.jpg" class="img-rounded">
						</c:if>
						<c:if test="${!empty dish.photoUrl}"> --%>
							<img class="aDishButton" src="${dish.photoUrl}" onerror="javascript:this.src='${ctx}/images/no-picture.jpg'" alt="" class="img-rounded">
						<%-- </c:if> --%>
					</div>
					<div class="col-md-8">
						<div class="row">
							<div class="col-md-9" align="left">
								<div class="row">
									<div class="col-md-12">
										<h4 id="dishEnName" style="margin-top: 0px;">
											${dish.enName}
										</h4>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<h4 id="dishEnName" style="margin-top: 0px;">
											${empty dish.chName ? '' : dish.chName}
										</h4>
									</div>
								</div>
							</div>
							<div class="col-md-3" align="right">
								<h4> 
								<span>$ </span>
								<span name="price">
									<fmt:formatNumber value="${dish.price}" type="currency" pattern="#0.00"/>
								</span>
								</h4>
							</div>
						</div>
						<c:if test="${empty dish.enIntro}">
							<div name="enIntro" style="border:0px solid red; height:88px; width:200px;"></div>
						</c:if>
						<c:if test="${!empty dish.enIntro}">
							<div class="panel panel-default" style="margin-bottom: 10px;">
								<div class="panel-body" name="enIntro"  style="height:77px;">
									${dish.enIntro}
								</div>
							</div>
						</c:if>
							<div class="row quantity-content" style="margin-top:5px;">
								<div class="col-md-3" align="left" style="padding-left: 0px;">
									<h4>Quantity</h4>
								</div>
								<div class="col-md-3 quantity" align="left" >
									<div class="input-group" >
										<span class="input-group-btn">
											<button class="btn btn-default edit-quantity" type="button" name="minusQuantity">-</button>
										</span>
										<input type="text" class="count-quantity" id="aDishQuantity" value="1" readonly="readonly"> 
										<span class="input-group-btn">
											<button class="btn btn-default edit-quantity" type="button" name="addQuantity">+</button>
										</span>
									</div>
								</div>
								<div class="col-md-3" align="right">
									<h4>
									<span>$ </span>
									<span id ="aDishTotal">
										<fmt:formatNumber value="${dish.price}" type="currency" pattern="#0.00"/>
									</span>
									</h4>
								</div>
								<div class="col-md-3 quantity" align="right" style="padding-right: 0px;">
									<button type="button" id="subDish" name="add" value="sss" class="btn btn-primary navbar-inverse " style="background-color:#898989;border:0px;">Submit</button>
								</div>
							</div>	
									
							<!-- form-control  -->
					</div>
				</div>
				<div class="row">
					<div class="col-md-12 dishGarnishErrorInfo" align="center" name="errorInfo">
					</div>
				</div>
				
			</div>
	
		<div name = "dishGarnish"><!-- F -->
			<c:forEach items="${dish.garnishList}" var="garnishGroup" begin="0"  step="1" varStatus="status1">
				<c:if test="${garnishGroup.isMust==0}"><!-- 可选 -->
					<div class="bgsearch_list06" name="garnishHeader" id="${garnishGroup.garnishHeaderId}" alt="${garnishGroup.showType}">
						${garnishGroup.garnishMenu}
							(Optional)
						<c:if test="${garnishGroup.count>1}">
							<span style="font-size: 15;font-weight: normal;">&nbsp;&nbsp;Maximum ${garnishGroup.count} items</span>
						</c:if>
						<c:if test="${garnishGroup.count==1}">
							<span style="font-size: 15;font-weight: normal;">&nbsp;&nbsp;Maximum ${garnishGroup.count} items</span>
						</c:if>
					</div>
				</c:if> 
				<c:if test="${garnishGroup.isMust==1}"><!-- 必选 -->
					<div class="bgsearch_list06" name="garnishHeader" id="${garnishGroup.garnishHeaderId}" alt="${garnishGroup.showType}">
						${garnishGroup.garnishMenu}
							(Required)
						<c:if test="${garnishGroup.count>1}">
							<span style="font-size: 15;font-weight: normal;">&nbsp;&nbsp;Choose up to ${garnishGroup.count}</span>
						</c:if>
						<c:if test="${garnishGroup.count==1}">
							<span style="font-size: 15;font-weight: normal;">&nbsp;&nbsp;Choose ${garnishGroup.count}</span>
						</c:if>
					</div>
				</c:if>
				<%-- <c:if test="${garnishGroup.isMust==1 && garnishGroup.showType!=2}"><!-- 可选项 -->
					<div class="bgsearch_list06" name="garnishHeader" id="${garnishGroup.garnishHeaderId}" alt="${garnishGroup.showType}">
						${garnishGroup.garnishMenu}
						<c:if test="${garnishGroup.showType!=2}">
							(Optional)
						</c:if>
						<c:if test="${garnishGroup.showType==2}"><!-- 如果是select后面注释规定必须的数量 -->
							(Choose at ${garnishGroup.count})
						</c:if>
					</div>
				</c:if>	 --%>
				
				
				<%-- <c:if test="${garnishGroup.isMust==1 && garnishGroup.showType!=2}"><!-- 可选项 -->
					<div class="bgsearch_list06" name="garnishHeader" id="${garnishGroup.garnishHeaderId}" alt="${garnishGroup.showType}">
						${garnishGroup.garnishMenu}
						<c:if test="${garnishGroup.showType!=2}">
							(Optional)
						</c:if>
						<c:if test="${garnishGroup.showType==2}"><!-- 如果是select后面注释规定必须的数量 -->
							(Choose at ${garnishGroup.count})
						</c:if>
					</div>
				</c:if>	 --%>
				
				
				<div id="group" style="padding-top: 8px;padding-bottom: 8px;"><!-- E -->
				<!-- ---------------------------------------------------------------------------------------------------- -->
				<c:forEach items="${garnishGroup.pageGarnishItemList}" var="item" begin="0"  step="1" varStatus="status2">
					<c:if test="${(status2.index+1)%3==1||status2.first}">
						<div class="row" id="ttt" style="padding-left: 50px; padding-right: 50px;"><!-- D -->
					</c:if>
					<div class="col-md-4" style="padding-left: 10px; padding-top: 3px;padding-bottom: 3px;"><!-- C -->
						<div><!-- B -->
							<div class="row" ><!-- A -->
								<div class="col-md-2"><!-- div 1-1,1-2,1-3 begin -->
									<div class="radio-checkbox-button"><!-- div 2-1,2-2,2-3 begin -->
										<c:if test="${garnishGroup.showType==0}"><!-- radio -->
											<c:if test="${status2.index==0}">
												<span>
													<input type="radio" value="${item.garnishItemId}" name="${garnishGroup.garnishHeaderId}" alt="${garnishGroup.garnishMenu}">
												</span>
									</div><!-- div 2-1 end -->
								</div><!-- div 1-1 end -->
												<div  class="col-md-10 garnishItemName">
													<div>${item.garnishName}</div>
													<c:if test="${item.addprice!=0}">
														<br>$
														<div id = "addPrice">
															<fmt:formatNumber value="${item.addprice}" type="currency" pattern="#0.00"/>
														</div>
													</c:if>
												</div>
											</c:if>
											<c:if test="${status2.index!=0}">
											<span>
												<input type="radio" value="${item.garnishItemId}" name="${garnishGroup.garnishHeaderId}" alt="${garnishGroup.garnishMenu}">
											</span>
									</div><!-- div 2-2 end -->
								</div><!-- div 1-2 end -->
												<div class="col-md-8 garnishItemName">
													<div>${item.garnishName}</div>
													<c:if test="${item.addprice!=0}">
														<br>$
														<div id = "addPrice">
															<fmt:formatNumber value="${item.addprice}" type="currency" pattern="#0.00"/>
														</div>
													</c:if>
												</div>
											</c:if>
										</c:if>
										<c:if test="${garnishGroup.showType==1}"><!-- checkbox -->
											<span name="${garnishGroup.garnishHeaderId}" alt="${garnishGroup.count}">
												<!-- value存的是配菜的id, name存的是配菜分类头的id -->
												<input type="checkbox" value="${item.garnishItemId}" name="${garnishGroup.garnishHeaderId}" alt="${garnishGroup.garnishMenu}">
											</span>
											</div><!-- div 2-3 end -->
									</div><!-- div 1-3 end -->
											<div class="col-md-8 garnishItemName">
												<div>${item.garnishName}</div>
												<c:if test="${item.addprice!=0}">
													<br>$
													<div id = "addPrice">
														<fmt:formatNumber value="${item.addprice}" type="currency" pattern="#0.00"/>
													</div>
												</c:if>
											</div>
										</c:if>
										
										<c:if test="${garnishGroup.showType==2}"><!-- select -->
											<span name="${garnishGroup.garnishHeaderId}" alt="${garnishGroup.count}">
												<select value="${item.garnishItemId}" name="${garnishGroup.garnishHeaderId}" alt="${garnishGroup.garnishMenu}" class="dishGarnishSelect">
													<option value="0">0</option>
													<option value="1">1</option><option value="2">2</option><option value="3">3</option>
													<option value="4">4</option><option value="5">5</option><option value="6">6</option>
													<option value="7">7</option><option value="8">8</option><option value="9">9</option>
													<option value="10">10</option><option value="11">11</option><option value="12">12</option>
													<option value="13">13</option><option value="14">14</option><option value="15">15</option>
													<option value="16">16</option><option value="17">17</option><option value="18">18</option>
													<option value="19">19</option><option value="20">20</option>
												</select>
											</span>
											</div><!-- div 2-3 end -->
									</div><!-- div 1-3 end -->
											<div class="col-md-8 garnishItemName" style="margin-left: 10px;">
												<div>${item.garnishName}</div>
												<c:if test="${item.addprice!=0}">
													<br>$
													<div id = "addPrice">
														<fmt:formatNumber value="${item.addprice}" type="currency" pattern="#0.00"/>
													</div>
												</c:if>
											</div>
										</c:if>
								</div><!-- A -->
						</div><!-- B -->
					</div><!-- C -->
				
					<c:if test="${(status2.index+1)%3==0 || status2.last}">
						</div><!-- D -->
					</c:if>
				</c:forEach>
					</div><!-- E  -->
			</c:forEach>
		</div><!-- F -->
			<div class="bgsearch_list08">Special requests</div>
			<div class="modal-body">
					<div class="panel-body">
					<textarea class="dishRemark" rows="3" cols="100" id="dishRemark" placeholder="e.g. well done, mild, etc." maxlength="200"></textarea>
					<br>
					<p style="color:red; padding-top: 10px;">
						Additional charge may apply if you order extra meat, more sauce and so on.
					</p>
					</div>
			</div>
			<!-- <div style="height:10px;"></div> -->
		</div><!-- G -->
	<%-- <script type="text/javascript" src="${ctx}/index/js/restaurantMenuCommon.js"></script> --%>
	<%-- <script type="text/javascript" src="${ctx}/index/js/cart.js"></script> --%>
	<script src="${ctx}/index/js/dish.js"></script>
	</body>
</html>
