<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML>
<html>
  <head>
    <title>My JSP 'address.jsp' starting page</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
	<!-- huadong2 -->
	<div class="tab-pane">
		<div align="right" class="rest-miaodi rest-miaodi-gao" style="margin-right: 15px;">
			<button class="btn btn-success-hui btn-lg" id="add-new-address" type="submit">Add New Address</button>
		</div>
		
		<div name= "new-address-content">
			<c:if test="${fn:length(addressList)<=0}">
				<div class="userpadd">
					<div class="row">
						<div class="col-md-12" align="center" style="padding-top: 20px;padding-bottom: 20px;font-size: 30px;">
							No Address Information !
						</div>
					</div>
				</div>
			
			</c:if>
			<c:if test="${fn:length(addressList)>0}">
				<c:forEach items="${addressList}" var="address" begin="0"  step="1" varStatus="status">
					<div class="userpadd">
						<div class="row">
							<div class="col-md-10"> 
								<p>
								<div class="address-title-username">Name:</div>
								<div class="address-userName">
									<c:if test="${! empty address.consignee}">${address.consignee}</c:if>
									<c:if test="${empty address.consignee}">&nbsp;\</c:if>
								</div>
								</p><p>
								<div class="address-title">Address:</div>
								<div class="address-address-list">
									${address.address}
								</div>
								</p><p> 
								<%-- <div class="address-title">Apartment, Suite #:</div><div class="address-address-list">${address.floor}</div>
								</p><p>  --%>
								<div class="address-title">Phone:</div>
								<div class="address-address-list">
									<c:if test="${! empty address.phone}">${address.phone}</c:if>
									<c:if test="${empty address.phone}">&nbsp;\</c:if>
								</div>
								</p>
								<c:if test="${address.isDefault==1}"><!-- 等于1时，为默认的地址 -->
									<p> 
									<div class="address-title">Default:</div>
									<div class="address-address-list">
										<input type="checkbox" name="" checked="checked" disabled="disabled"/>
									</div>
									</p>
								</c:if>
							</div>
							<div class="col-md-2" style="padding-top: 25px;">
								<button class="btn btn-success-hui btn-lg btn-block" value="${address.addressId}"
									type="submit" name="address-edit" alt="${status.index}">Edit</button>
									<br>
								<button class="btn btn-success-hui btn-lg btn-block" value="${address.addressId}"
									type="submit" name="address-remove">Remove</button>
							</div>
						
						</div>
						<div name="address-edit-content-${status.index}">
							
						</div>
					</div>
				</c:forEach>
			</c:if>

		</div>
	</div>
	<!-- huadong2 out -->
  </body>
</html>
