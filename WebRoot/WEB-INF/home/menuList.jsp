<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML>
<html>
  <head>
  </head>
  
  <body>
  		<!-- 第一个  Coupons-->
		<!-- <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true"> -->
			
			
			
		<!-- </div> -->
		<div class="row lcx-m">
			<div class="col-md-4" align="right" style="padding-top: 5px;">
				<span style="font-size: 18px;">Menu Sections</strong>
			</div>
			<div class="col-md-5">
				<select class="form-control" id = "dishMenuSelect">
					 <c:forEach items="${menuList}" var="str" begin="0"  step="1" varStatus="status">
					 	<option value="menu-${status.index}">${str.menuName}</option>
					 </c:forEach>
				</select>
			</div>
			<div class="col-md-3">
				<button id = "expandAllMenuSections" type="button" class="btn xc-btn-default">Expand All</button>
			</div>
			<!-- <div class="col-md-7">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="Search for..." Style="heigth:30px">
					<span class="input-group-btn">
						<button class="btn xc-btn-success" type="button" Style="heigth:36px">
							<img src="${ctx}/index/images/xc-fdj.png" width="18" height="18">
						</button>
					</span>
				</div>
			</div> -->
		</div>
  	
  	
    <c:forEach items="${menuList}" var="str" begin="0"  step="1" varStatus="status">
		<c:if test="${status.index==0}"><!-- 如果是第一条是要展开的 -->
			<div class="panel-remade panel-default-remade" name="menu-${status.index}">
				<div class="" role="tab" id="heading-${status.count}" style="border-radius: 4px;">
					<h4 class="rest-panel-1">
						<a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapse-${status.count}"
							aria-expanded="true" aria-controls="collapse-${status.count}">${str.menuName}
							<%-- <span style="font-style:italic;font-size: 12px;">&nbsp;&nbsp;${str.describe}</span> --%>
						</a>
						<c:if test="${!empty str.describe}">
							<a class="cursorPointer" style="padding-left: 10px; " title="${str.describe}"><img alt="${str.describe}" src="${ctx}/images/infoGreen.png" style="height: 15px;"> </a>
						
						</c:if>
						<div style="float:right;" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingTwomore">
							<a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapse-${status.count}" aria-expanded="false" aria-controls="collapseTwo">
								<span class="glyphicon glyphicon-chevron-down"></span>
							</a>
						</div>
					</h4>
				</div>
				<div id="collapse-${status.count}" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading-${status.count}">
					<div class="panel-body" style="padding-bottom: 5px;">
						<!--seafood-->
						<div style="position:relative;">
							<div align="right" style=" font-size:18px; padding-top:5px; position:absolute; background-color: #EBECED; right:0px; height:30px; top:-50px;  z-index:1025;">
								<a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapse-1" aria-expanded="false" aria-controls="collapseTwo">
									<span class="glyphicon glyphicon-chevron-up" style="color: #515253;"></span>
								</a>
							</div>
						</div>
						
						<div class="row">
								 <!-- data-toggle="modal" data-target="#myModal1" -->
							<c:forEach items="${str.pageDishList}" var="str2" begin="0"  step="1" varStatus="status2">
								<div class="col-md-4">
									<div class="row" style="padding-bottom: 10px;">
										<div type="button" class="aDishButton" name="aDish" title="${str2.enName}-${str.id}-${str2.id}" >
											<div class="col-md-5">
												<%-- <c:if test="${empty str2.photoUrl}">
													<img src="${ctx}/index/images/default-dish.jpg"  alt="..." class="img-thumbnail">
												</c:if>
												<c:if test="${!empty str2.photoUrl}"> --%>
													<img src="${str2.photoUrl}" onerror="javascript:this.src='${ctx}/images/no-picture.jpg'" alt="str.enName" class="img-thumbnail">
												<%-- </c:if> --%>
											</div>
											<div class="col-md-7">
												<h5 style="margin-top:2px;">
													<c:if test="${!empty str2.enName}">
														${str2.enName}
													</c:if>
													<c:if test="${!empty str2.chName}">
														<br>${str2.chName}
													</c:if>
												</h5>
												<h4>$ <fmt:formatNumber value="${str2.price}" type="currency" pattern="#0.00"/></h4>
											</div>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
						<div class="row">
							
						</div>
						<!--seafood over-->
					</div>
				</div>
			</div>
			
		</c:if>
		<c:if test="${status.index!=0}">
			<div class="panel-remade panel-default-remade"  name="menu-${status.index}"><!-- panel panel-default -->
				<div class="" role="tab" id="heading-${status.count}">
					<h4 class="rest-panel-1">
						<a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapse-${status.count}" title="${str.describe}"
							aria-expanded="true" aria-controls="collapse-${status.count}" style="">${str.menuName}
						</a>
						<c:if test="${!empty str.describe}">
							<a class="cursorPointer" style="padding-left: 10px; " title="${str.describe}"><img alt="${str.describe}" src="${ctx}/images/infoGreen.png" style="height: 15px;"> </a>
						</c:if>
						 
						<div style="float:right;" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingTwomore">
							<a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapse-${status.count}" aria-expanded="false" aria-controls="collapseTwo">
								<span class="glyphicon glyphicon-chevron-down"></span>
							</a>
						</div>
					</h4>
				</div>
				<div id="collapse-${status.count}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading-${status.count}">
					<div class="panel-body">
						<!--seafood-->
						<div style="position:relative;">
						
							<div align="right" style=" font-size:18px; padding-top:5px; position:absolute; background-color: #EBECED; right:0px; height:30px; top:-50px;  z-index:1025;">
								<a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapse-${status.count}" aria-expanded="false" aria-controls="collapseTwo">
									<span class="glyphicon glyphicon-chevron-up" style="color: #515253;"></span>
								</a>
							</div>
						</div>
						<div class="row">
							<c:forEach items="${str.pageDishList}" var="str2" begin="0"  step="1" varStatus="status2">
								<div class="col-md-4">
									<div class="row">
										<div type="button"  class="aDishButton" name="aDish" title="${str2.enName}">
											<div class="col-md-5">
												<%-- <c:if test="${empty str2.photoUrl}">
													<img src="${ctx}/index/images/default-dish.jpg" alt="..." class="img-thumbnail">
												</c:if>
												<c:if test="${!empty str2.photoUrl}"> --%>
													<img src="${str2.photoUrl}" onerror="javascript:this.src='${ctx}/images/no-picture.jpg'" alt="${str2.enName}" class="img-thumbnail">
												<%-- </c:if> --%>
											</div>
											<div class="col-md-7 dish-title">
												<h5 style="margin-top:2px;">
													<c:if test="${!empty str2.enName}">
														${str2.enName}
													</c:if>
													<c:if test="${!empty str2.chName}">
														<br>${str2.chName}
													</c:if>
												</h5>
												<h5>$ ${str2.price}</h5>
											</div>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</c:if>
	</c:forEach>
	
  </body>
  <script src="${ctx}/index/js/menuList.js"></script>
</html>
