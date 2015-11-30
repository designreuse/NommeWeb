<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML>
<html>
  <head>
    <title>userStatement.html</title>
	<jsp:include page="../inputjs.jsp"></jsp:include>
	<jsp:include page="../inputcss.jsp"></jsp:include>
	

  </head>
  
  <body>
	  <div style="font-weight: bolder; font-size: 18px;margin-top: 5px;margin-bottom: 5px;">
		
		   &nbsp;Domino Mail Server: ${sessionScope.consumersTotalNo}
		
	</div>
    <div name="data-table">
			
		<table data-toggle="table" id="userStatementTable"
			data-url="${ctx}/adminStatement/getStatementAllConsumers"
			data-click-to-select="true"
			data-pagination="true"
			data-side-pagination="server"
			data-striped="true"
			data-page-size="10"
			data-page-list="[10, 20, 50, 100]">
			<thead>
				<tr>
					<!-- <th data-field="state" data-radio="true"></th> -->
					
					<th data-field="firstName" >First Name</th>
					<th data-field="lastName" >Last Name</th>
					<th data-field="phone" >Phone Number</th>
					<th data-field="email" >Email</th>
					<th data-field="regDate" >Regist Date</th>
					<th data-field="lastLoginDate" >Last Login Date</th>
					<th data-field="completedOrderQuantity" data-sortable="true" data-align="right">Completed Quantity</th>
					<th data-field="completedOrderAmount"  data-formatter="priceFormatter" data-align="right">Completed Amount</th>
					<th data-field="unfinishedOrderQuantity" data-sortable="true" data-align="right">Unfinished Quantity</th>
					<th data-field="unfinishedOrderAmount" data-formatter="priceFormatter" data-align="right">Unfinished Amount</th>
					<th data-field="refundOrderQuantity" data-sortable="true" data-align="right">Refund Quantity</th>
					<th data-field="refundOrderAmount" data-formatter="priceFormatter" data-align="right">Refund Amount</th>
					<th data-field="donateAmount" data-formatter="priceFormatter" data-align="right">Donate Amount</th>
					<th data-field="mobileType" data-formatter="deviceTypeFormatter"data-align="center">Device Type</th>
				</tr>
			</thead>
		</table>
    </div>
    
  </body>
  <script>
	  function priceFormatter(value) {
	      // 16777215 == ffffff in decimal
	      return "$"+value;
	  }
	  function deviceTypeFormatter(value){
		  if(value==0){
			  return "Andraid";
		  }else if(value == 1){
			  return "IOS";
		  }else if(value ==2){
			  return "Web";
		  }
	  }
  </script>
</html>
