<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
  <head>
    <title>My JSP 'restaurantManage.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<jsp:include page="../inputjs.jsp"></jsp:include>
	<jsp:include page="../inputcss.jsp"></jsp:include>
	<style type="text/css">
		.iradio_minimal{
			background-color: #EAEAEC;
		}
		.dropdown-menu{
			height:88px;
		}
	</style>
  </head>
  
  <body>
  	<!-- 新增用户的模态框 -->
		<div class="modal" id="auditModal" tabindex="-1" role="dialog"
			aria-labelledby="mySmallModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<form id="adminForm" action="" method="POST">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" style="font-weight: 400;">Verify Restaurant</h4>
						</div>
						<div class="modal-body">
							<input type="hidden" name="id" id="id"><!-- 管理员ID -->
							<div class="row">
								<div class="col-md-3"><label class="modalField">Restaurant:</label></div>
								<div class="col-md-9">
									<div id="restaurantName" style="font-size: 23; font-weight: bold;margin-top: 15px;"></div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3"><label class="modalField">Status:</label></div>
								<div class="col-md-9" >
									<div class="statusGroup radio" >
										<span><input type="radio" name="status" value="2">&nbsp;Disabled&nbsp;</span><!-- 冻结无效状态 -->
										<span><input type="radio" name="status" value="1">&nbsp;Pending&nbsp;</span><!-- 待审核 -->
										<span><input type="radio" name="status" value="0">&nbsp;Active&nbsp;</span><!-- 审核通过 -->
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal" style="width:80px;">Close</button>
							<button type="button" id="sub" class="btn bg-olive"
								style="width:80px;">Save</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	<!-- 删除商家模态框 -->
	<div class="modal" id="deleteModal" tabindex="-2" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" style="font-weight: 400;">Delete Restaurant</h4>
					</div>
					<div class="modal-body">
						<div style="text-align:center;"><h3><span id="attentionInfo">Do you want to delete this Restaurant ?</span></h3></div>
						<div id="info" style="text-align:center; color:#FA6A6A;"></div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal" style="width:80px;">Close</button>
						<button type="button" id="subDelete" class="btn bg-olive" style="width:80px;">Delete</button>
					</div>
				</div>
		</div>
	</div>
	<div id="custom-toolbar">
		<button type="button" id="audit" class="btn btn-default mybt" title="Verify">
			<i class="fa fa-check "></i> Verify
		</button>
		
		<button type="button" id="delete" class="btn btn-default mybt"  data-method="refresh" title="delete">
       		<i class="fa fa-minus"></i> Delete
		</button>
  			<button type="button" id="refresh" class="btn btn-default mybt"  data-method="refresh" title="refresh">
       		<i class="fa fa-refresh"></i> Refresh
  			</button>
	</div>
  
     <div>
     	<table id="restaurantTable" data-toggle="table" data-url="${ctx}/admin/getallrestaurants"
     		data-search="true"
			data-side-pagination="server"
     		data-pagination="true"
     		data-click-to-select="true"
     		data-toolbar="#custom-toolbar"
     		data-page-list="10,20,30"
     		data-page-size="10">
     		<thead>
     			<tr>
     				<th data-field="state" data-radio="true"></th>
     				<th data-formatter="numberFormatter" data-width="30">No.</th>
			        <th data-field="restaurantName" data-sortable="true" data-width="250">Restaurant Name</th>
			        <th data-field="restaurantContact" data-align="center" data-width="150">Contact</th>
			        <th data-field="restaurantPhone" data-width="150">Phone</th>
			        <th data-field="restaurantEmail" data-width="200">Email</th>
			        <th data-field="restaurantAddress" data-width="400">Address</th>
			        <th data-field="status" data-formatter="statusFormatter" data-sortable="true" data-width="100">Status</th>
     			</tr>
     		</thead>
     	</table>
     </div>
     <script>
     	
     	//var rowData = $("#restaurantTable").bootstrapTable('getSelections')[0];//行点击时获取出行和数据
     	//根据不同的值在单元格中显示不同的信息
     	function statusFormatter(value,row){
     		if(value === 0){
     			return "Active";//已审核可用
     		}else if(value === 2){
     			return "Disable";//不可用
     		}else if(value === 1){
     			return "pending";//待审核
     		}
		}
     	function numberFormatter(value,row,index){//显示序号
     		return index+1;
		} 
     	
     	function distanceFormatter(value,row,index){
      		return value+" km";
 		}
     	function feeFormatter(value,row,index){
      		return "$"+value;
 		}
     	
	    $(function(){
	    	var oldStatu;
	    	var newStatu;
	    	var restaurantUuid;
	    	var currentRestaurantUuid;
	     
	    	$("#refresh").click(function () {//表格工具栏刷新按钮
				$("#restaurantTable").bootstrapTable('refresh',{
	            	url: '${ctx}/admin/getallrestaurants'
	            });
	        });
	    	$("#audit").click(function () {
	    		$("#restaurantName").empty();
	    		var rowData = $("#restaurantTable").bootstrapTable('getSelections')[0];//行点击时获取出行和数据
	    		if(rowData){
		    		oldStatu = rowData.status;
		    		restaurantUuid = rowData.restaurantUuid;
	    		 	$("#restaurantName").append(rowData.restaurantName);//获取名称加载到模态框
	    		 	$("#id").val(rowData.id);//获取餐厅的id
	    		 	if (oldStatu == 0) {
						$(".statusGroup input[value='0']").iCheck('check');
					}else if(oldStatu == 1){
						$(".statusGroup input[value='1']").iCheck('check');
					}else{
						$(".statusGroup input[value='2']").iCheck('check');
					}
	    			$("#auditModal").modal('show');
	    		}
	    	});
	    	$(".statusGroup input[name='status']").on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
				newStatu = event.target.defaultValue;
			}); 
	    	$("#sub").click(function(){
	    		if(newStatu != oldStatu){
	    			$.ajax({
	 	    			type: 'POST',
	 	    			async: false,
	 	    			data: {restaurantUuid:restaurantUuid, statu:newStatu},
	 	    			url: '${ctx}/admin/auditrestaurant',
	 	    			success:function(data){}
	 	    		});
	    			$("#auditModal").modal('hide');
	    			$("#restaurantTable").bootstrapTable('refresh',{
		            	url: '${ctx}/admin/getallrestaurants'
					});
	    		}else{
	    			$("#auditModal").modal('hide');
	    		}
	    	});
	    	
	    	$("#delete").click(function(){
				var currentRestaurant = $("#restaurantTable").bootstrapTable('getSelections')[0];//行点击时获取出行和数据
				if(currentRestaurant!=null){
					currentRestaurantUuid = currentRestaurant.restaurantUuid;
					$("#deleteModal").modal('show');
				}
			});
			
			$("#subDelete").click(function(){
				$.ajax({
					type:'post',
					async: false,
					url: '${ctx}/admin/deleterestaurant',
					data:{restaurantUuid:currentRestaurantUuid},
					success: function(data){
						var msg = jQuery.parseJSON(data);
						if(msg.success){//已经删除成功
							$("#deleteModal").modal('hide');
							$("#restaurantTable").bootstrapTable('refresh',{ //刷新表格数据
			                    url: '${ctx}/admin/getallrestaurants'
			                });
						}else{//删除失败
							$("#subDelete").popover('destroy');
							showPopover($("#subDelete"), "Error","top");
						}
					}
				});
			});
	    	 
	       
	    });
	</script>
  </body>
</html>
