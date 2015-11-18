<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
  <head>
    <title>My JSP 'payment.jsp' starting page</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
    <!-- huadong3-->
	<div class="tab-pane" id="Payments">

		<div align="ringt" class="rest-miaodi rest-miaodi-gao"  style="margin-right: 15px;">
			<button class="btn btn-success-hui btn-lg" id="add-new-payment" style="float: right;" type="submit">Add New Payment</button>
		</div>
		<div class="userpadd" name="new-payment-div" style="display:none;">
			<div style="margin:0px; float:right;width:200px;">
				<img src="/camut/img/credit/bankIcon.png" width="150px;">
			</div>
			<h3 align="left" class="rest-miaodi">Add New Payments</h3>
			<div id="cardDetail" style="margin-top: 20px;">
				<div class="row">
					<div class="col-md-6">
						<p>Card Number</p>
						<p>
							<div name="number-div" >
								
								<div class="col-md-10" style="padding: 0px;">
									<input class="form-control" id="number" type="text" maxlength="20" data-stripe="number" placeholder="Card Number">
								</div>
								<div class="col-md-2" style="padding-top: 4px;padding-left: 10px;">
									<div id="amexdisplay" style="display: none;">
										<img src="${ctx}/img/credit/american-express.png" width="45px;">
									</div>
									<div id="mastercarddisplay" style="display: none;">
										<img src="${ctx}/img/credit/mastercard.png" width="45px;">
									</div>
									<div id="visadisplay" style="display: none;">
										<img src="${ctx}/img/credit/visa.png" width="45px;">
									</div>
								</div>
							</div>
							<!-- <input type="text" class="form-control" placeholder="Card Number" id="card-number"> -->
						</p>
					</div>
					<div class="col-md-4">
						<div class="row">
							<div class="col-md-6"  style="padding-left: 5px;">
								<p>Expiration Month</p>
								<p>
									<input class="form-control"  id="month" type="text" maxlength="2" data-stripe="exp-month" placeholder="Exp-(MM)" />
								</p>
							</div>
							<div class="col-md-6" style="padding-left: 5px;">
								<p>Expiration Year</p>
								<p>
									<input class="form-control"  id="year" type="text" maxlength="4" data-stripe="exp-year" placeholder="Exp-(YYYY)"/>
								</p>
							</div>
						</div>
					</div>
					<div class="col-md-2">
						<p style="letter-spacing: 2px;">CVV</p>
						<p>
							<input class="form-control" id="cvv" type="text" maxlength="3" data-stripe="cvc" placeholder="CVV" />
						</p>
					</div>
				</div>
				<div class="row" style="margin-top: 20px;">
					<div class="col-md-4"></div>
					<div class="col-md-2">
						<button class="btn btn-success-hui btn-lg btn-block" name="add-payment-cancel">Cancel</button>
					</div>
					<div class="col-md-2">
						<button class="btn btn-success-hui btn-lg btn-block" name="add-payment-save">Save</button>
					</div>
				</div>
				<div class="row" >
					<div class="col-md-12" align="center">
						<div name="save-card-errorInfo" style="font-size: 20px;font-weight: bold;color:#7DD700;display:none; margin-bottom: 5px;margin-bottom: 5px;">
							Save card error !
						</div>
					</div>
				</div>
				
			</div>
		</div>	
			
			<!-- <div class="row">
				<div class="col-md-12">
					<div class="row">
						<div class="col-md-8">
							<p>Card Number</p>
							<p>
								<input type="email" class="form-control"
									id="exampleInputEmail1">
							</p>
						</div>
						<div class="col-md-2">
							<p>Expiration</p>
							<p>
								<input type="email" class="form-control"
									id="exampleInputEmail1">
							</p>
						</div>
						<div class="col-md-2">
							<p>CVV</p>
							<p>
								<input type="email" class="form-control"
									id="exampleInputEmail1">
							</p>
						</div>
					</div>
					
				</div>
			</div> -->
		

	</div>

	<div class="userpadd" id="Payments">
		<h3 align="left" class="rest-miaodi">Your Cards</h3>
		<c:forEach items="${cards}" var="card">
			<div class="row" style="border: 1px solid #dddddd;border-radius:4px;height: 70px;margin-left: 70px;margin-right: 70px;margin-top: 20px;margin-bottom: 10px;background-color: #eeeeee;">
				<%-- <div class="col-md-1" style="padding-top: 13px;">
					<input type="radio" name="card"> <input type="hidden" name="cardId" value="${card.id}">
				</div> --%>
				<div class="col-md-3" style="padding-top: 9px;">
					<div>
						<c:if test="${card.type=='Visa'}">
							<img src="${ctx}/img/credit/visa.png" width="77px;">
						</c:if>
						<c:if test="${card.type=='MasterCard'}">
							<img src="${ctx}/img/credit/mastercard.png" width="77px;">
						</c:if>
						<c:if test="${card.type=='American Express'}">
							<img src="${ctx}/img/credit/american-express.png" width="77px;">
						</c:if>
					</div>
				</div>
				
				<div class="col-md-7" style="padding-top: 10px;">
					<div style="font-size: x-large;color: #444444;padding-top: 12px;float:left;">***************&nbsp;</div>
					<div style="font-size: x-large;color: #444444;padding-top: 5px;">${card.last4}</div>
				</div>
				
				<div class="col-md-2" style="padding-top: 17px;">
					<!-- <button class="btn-primary" style="width: 60px;" name="deleteCard">delete</button> -->
					<button class="btn btn-success-hui btn-lg btn-block" value="${card.id}"
							type="submit" name="payment-remove">Remove</button>
				</div>
	
			</div>
		</c:forEach>
	</div>
	
  </body>
</html>
