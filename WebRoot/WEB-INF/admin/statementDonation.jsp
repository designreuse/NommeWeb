<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>My JSP 'donationStatement.jsp' starting page</title>

	<jsp:include page="../inputjs.jsp"></jsp:include>
	<jsp:include page="../inputcss.jsp"></jsp:include>
	<script type="text/javascript" src="${ctx}/js/admin/statementDonation.js"></script>
	<link href="${ctx}/css/datatimepicker/bootstrap-datetimepicker.css">
  </head>
  
  <body>
    <div id="toolbar">
    	<div class="row" style="width:1200px;">
    		<div class="col-md-4" style="width:280px;padding-right: 5px;" >
		    	<div style="margin-top: 4px;margin-left: 5px;margin-right: 5px;" class="row">
		    		<div class="col-md-3" style="font-size: 16px;font-weight: bold; padding-left:0px; padding-right:5px;width:120px;" align="right">
		    			Select Month:
		    		</div>
		    		<div class="col-md-3" style="padding: 0px;width:115px;">
				    	<input size="16" type="text" id="searchMonth" value="" readonly style="height: 32px; line-height:1em; padding-left: 20px;font-size: 20px;width:115px;">
		    		</div>
		    	</div>
		    </div>
		    <!-- <div class="col-md-1" style="padding-left:0px;">
		    	<button type="button" id="searchByMonth" class="btn btn-default mybt"  data-method="refresh"  style="width:86px;margin-top: 4px;">
		       		<i class="glyphicon glyphicon-search"></i> Search
				</button>
		    </div> -->
		    <div class="col-md-1" style="padding-left:0px;">
		    	<button type="button" id="detail" class="btn btn-default mybt"  data-method="refresh"  style="width:86px;margin-top: 4px;">
		       		<i class="glyphicon glyphicon-list"></i> Detail
				</button>
		    </div>
		    
		    <div class="col-md-4">
		   
		    </div>
		</div>
	</div>
  	
    <div>
     	<table id="donationStatementTable"
     		data-toggle="table" 
     		data-url="${ctx}/adminStatement/getStatementCharityOrganization?searchKey=${sessionScope.searchMonth}"
     		data-click-to-select="true"
     		data-pagination="true"
			data-side-pagination="server"
     		data-toolbar="#toolbar"
     		data-page-size="10"
     		data-page-list="[10,20,50,100]">
     		<thead>
     			<tr data-height="15">
     				<th data-field="state" data-radio="true"></th>
			        <th data-field="associations"  data-width="300" >Associations</th>
			        <th data-field="count" data-formatter="countFormatter" data-align="center" data-width="150">Count</th>
			        <th data-field="amount" data-formatter="amountFormatter" data-width="100" data-align="right">Amount</th>
     			</tr>
     		</thead>
		</table>
	</div>
	<div>
    	<input type="hidden" name="responseMonth" value="${sessionScope.searchMonth}">
	</div>
	
	<div class="modal" id="donationModal" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:800px;">
			<form id="adminForm" action="" method="POST">
				<div class="modal-content" style="width:800px;">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" style="font-weight: 400;">Donation</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" name="id" id="charityId" value="0"><!-- 管理员ID -->
						<div class="row" style="margin-bottom: 10px;">
							<div class="col-md-12">
								<div class="col-md-4" style="width:280px;padding-right:0px;padding-left: 0px;" >
							    	<div style="margin-top: 4px;margin-left: 0px;margin-right: 5px;" class="row">
							    		<div class="col-md-3" style="font-size: 16px;font-weight: bold; padding-left:0px;margin-top:5px; padding-right:5px;width:120px;" align="left">
							    			Select Month:
							    		</div>
							    		<div class="col-md-3" style="padding: 0px;width:115px;">
									    	<input size="16" type="text" id="modal-searchMonth" value="" readonly style="margin-top:0px; height: 32px; line-height:1em; padding-left: 20px;font-size: 20px;width:115px;">
							    		</div>
							    	</div>
							    </div>
							    <div class="col-md-1" style="padding-left:0px;">
							    	<button type="button" id="modal-searchButton" class="btn btn-default mybt"  data-method="refresh"  style="width:86px;margin-top: 3px;height:34px;">
							       		<i class="glyphicon glyphicon-search"></i> Search
									</button>
							    </div>
							</div>
						</div>
						<div class="row" style="margin-top: 3px;">
							<div class="col-md-12">
								<span style="font-size: 20px;">Association:</span>
								<span style="font-size: 20px;font-weight: bold;" name="association" ></span>
							</div>
						</div>
						<div class="row" style="margin-top: 3px;">
							<div class="col-md-12">
								<span style="font-size: 20px;padding-left: 60px;">Phone:</span>
								<span style="font-size: 20px;font-weight: bold;" name="phone"></span>
							</div>
						</div>
						<div class="row" style="margin-top: 3px;margin-bottom: 3px;">
							<div class="col-md-12">
								<span style="font-size: 20px;padding-left: 40px;">Address:</span>
								<span style="font-size: 20px;font-weight: bold;" name="address"></span>
							</div>
						</div>
						<div class="row" style="margin-top: 3px;margin-bottom: 3px;">
							<div class="col-md-4">
								<span style="font-size: 20px; padding-left:60px;">Count:</span>
								<span style="font-size: 20px;font-weight: bold;" name="count"></span>
							</div>
							<div class="col-md-8">
								<span style="font-size: 20px; padding-left:10px;">Amount:</span>
								<span style="font-size: 20px;font-weight: bold;" name="amount"></span>
							</div>
						</div>
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12">
								
								<table id="oneCharityDonationTable"
						     		data-toggle="table" 
						     		<%-- data-url="${ctx}/adminStatement/getOneCharityDonation?searchKey=${sessionScope.searchMonth}" --%>
						     		data-click-to-select="true"
						     		data-pagination="true"
									data-side-pagination="server"
						     		data-page-size="10"
						     		data-page-list="[10,20,50,100]">
						     		<thead>
						     			<tr data-height="15">
									        <th data-field="createDate" data-align="center" data-width="100" data-sortable="true">Date</th>
									        <th data-field="restaurantName" data-align="center" data-width="200" data-sortable="true">Restaurant</th>
									        <th data-field="count" data-align="center" data-width="100" data-sortable="true">Count</th>
									        <th data-field="amount" data-formatter="amountFormatter" data-align="center" data-width="150" data-sortable="true">Amount</th>
						     			</tr>
						     		</thead>
								</table>
								
								
								
							</div>
						</div>
						
						
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal" style="width:80px;">Close</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	
	
  </body>
</html>
