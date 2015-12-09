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
	<!-- Daterange picker -->
	<link href="${ctx }/framework/select2/select2.css" rel="stylesheet" type="text/css" />
	<!-- Sparkline -->
	<script src="${ctx }/framework/select2/select2.js" type="text/javascript"></script>
	
	<style type="text/css">
		
		.iradio_minimal{
			background-color: #EAEAEC;
		}
		
		
	</style>
  </head>
  
  <body>
	<div style="margin-top:10px;margin-left: 10px;">
	<input type="hidden" id = "restaurantUuid1">
		<div  style="width:140px;  float:left;">
			<div style="font-size:16; font-weight: lighter;">Restaurant Name:</div>
		</div>
		<div >
			<select class="select2RestauramtName" style="width: 300px;height:40px;">
			</select>
		</div>
		<div name = "msg" id = "msg" style="float:right;color: #FA6A6A; font-weight:bold;font-size:30px; display: hidden;"></div>
	</div>
		
		
	</div>
  	<!-- 审核商家管理员的模态框 -->
	<div class="modal" id="auditModal" tabindex="-1" role="dialog"
			aria-labelledby="mySmallModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<form id="adminForm" action="" method="POST">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" style="font-weight: 400;">Verify Admin User</h4>
						</div>
						<div class="modal-body">
							<input type="hidden" name="id" id="id"><!-- 管理员ID -->
							<div class="row">
								<div class="col-md-3"><label class="modalField">User Name:</label></div>
								<div class="col-md-9">
									<div id="userName" style="font-size: 23; font-weight: bold;margin-top: 15px;"></div>
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
	<!-- 新增、修改模态框 -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header"
					style="background-color: #3D9970; border-bottom: 4px solid #307959;">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel" style="font-weight: 600;">Add
						new employee</h4>
				</div>
				<div class="modal-body">
					<form id="fm">
						<!-- <div class="form-group" style="display: hidden;"> -->
							<div class="row"  >
								
								<div class="col-md-9" >
									<input type="hidden" name="restaurantUuid"/>
								</div>
							</div>
						<!-- </div> -->
					
					<div class="form-group">
						<div class="row">
							<div class="col-md-3">
								<label class="control-label" 
										style="margin-top:18px; float: right;color: #555555;font-size: 16;font-weight: lighter;">Type</label>
							</div>
							<div class="col-md-9">
								<select id="addSelect"  class="form-control" name="type" data-placement="right" style="height: 37px;width: 300px;border: 0px;">
									<option value="2" selected="selected">Employee</option>
									<option value="1">Administrator</option>
										
								</select>
							</div>
						</div>
					</div>
					
						<div class="form-group">
							<div class="row">
								<div class="col-md-3">
									<label class="control-label"
										style="margin-top:18px; float: right;color: #555555;font-size: 16;font-weight: lighter;">User Id
										</label>
								</div>
								<div class="col-md-9">
									<input type="text" name="code" class="form-control"
										placeholder="Employee code" data-placement="right" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-md-3">
									<label class="control-label"
										style="margin-top:18px; float: right;color: #555555;font-size: 16;font-weight: lighter;">First
										name</label>
								</div>
								<div class="col-md-9">
									<input type="text" name="firstName" class="form-control"
										placeholder="First name" data-placement="right" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-md-3">
									<label class="control-label"
										style="margin-top:18px; float: right;color: #555555;font-size: 16;font-weight: lighter;">Last
										name</label>
								</div>
								<div class="col-md-9">
									<input type="text" name="lastName" class="form-control"
										placeholder="Last name" data-placement="right" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-md-3">
									<label class="control-label"
										style="margin-top:18px; float: right;color: #555555;font-size: 16;font-weight: lighter;">Password</label>
								</div>
								<div class="col-md-9">
									<input type="password" name="password" class="form-control"
										placeholder="Password" data-placement="right" /> 
									<input type="hidden" name="uuid"> 
								</div>
							</div>
						</div>
						
						<div>
							<div class="row">
								<div class="col-md-3">
									<label
										style="margin-top:5px; color: #555555;font-size: 16;float: right;font-weight: lighter;">Permission</label>
								</div>
								<div class="col-md-9" style="margin-top:5px;">
									<label style="color: #555555;"> <input type="checkbox"
										name="role1"> Reservation
									</label> <label style="color: #555555;"> <input type="checkbox"
										name="role2"> Delivery
									</label> <label style="color: #555555;"> <input type="checkbox"
										name="role3"> Pick-up
									</label>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer" style="margin-top: 0px;">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button id="sub2" type="button" class="btn btn-primary"
						data-placement="top">&nbsp;Save&nbsp;</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 删除用户模态框 -->
	<div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel"
						style="font-weight: 400;">Delete Employee</h4>
				</div>
				<div class="modal-body">
					<div style="text-align:center;"><h3><span id="attentionInfo">Sure to delete the
						employee?</span></h3></div>
				</div>
				<div class="modal-footer">
					<button type="button" name="ok" class="btn btn-primary"
						data-dismiss="modal" style="margin-left: 190px;">Yes</button>
					<button type="button" name="no" class="btn btn-primary"
						style="margin-left: 20px;width: 50px;">No</button>
				</div>
			</div>
		</div>
	</div>
	
	<div id="tableContent" style="display:none;">
	<!-- 表格工具栏 -->
	
	<div id="custom-toolbar">
			<button type="button" id="audit" class="btn btn-default mybt"
				title="Verify">
				<i class="fa fa-check "></i> Verify
			</button>
			<button type="button" name="add"
				class="btn btn-default mybt"
				title="add new employee" 
				data-toggle="modal" data-target="#myModal">
				<i class="fa fa-plus "></i> Add
			</button>
			<button type="button" name="update"
				class="btn btn-default mybt"
				title="modify employee"><i class="fa fa-pencil"></i> Edit
			</button>
			<!-- <button type="button" name="delete"
				class="btn btn-default mybt"
				title="delete employee" data-placement="right"><i class="fa fa-minus"></i> Delete
			</button> -->
			
			<button type="button" id="refresh" class="btn btn-default mybt"  data-method="refresh"
				title="refresh">
        		<i class="fa fa-refresh"></i> Refresh
   			 </button>
   			 
		</div>
		
  	<!-- 表格 -->
  	
     	<table data-toggle="table" id="adminUserTable"
     		data-toolbar ="#custom-toolbar"
     		data-click-to-select="true"
     		data-pagination="true"
     		data-page-size="20">
     		<thead>
     			<tr>
     				<th data-field="state" data-radio="true"></th>
     				<th data-formatter="numberFormatter">No</th>
			        <th data-field="code" data-halign="center" data-align="left" data-sortable="true" >User Name </th>
			        <th data-field="firstName" data-align="center" data-sortable="true">First Name</th>
			        <th data-field="lastName" >Last Name</th>
			        <th data-field="createtime">Created Time</th>
					<!-- <th data-field="lastLoginTime">Lastlogintime</th> -->
			        <th data-field="type"data-formatter="userTypeFormatter" >User Type</th>
			        <th data-field="role" data-formatter="rolesForMatter" >Responsibilities</th>
			        <th data-field="status" data-formatter="statusFormatter" data-sortable="true">Status</th>
     			</tr>
     		</thead>
     	</table>
   	</div>
  <script>
     	
     	//var rowData = $("#restaurantTable").bootstrapTable('getSelections')[0];//行点击时获取出行和数据
     	//工具不同的值在单元格中显示不同的信息
     	//var restaurantId;
     	function statusFormatter(value,row){//显示状态
     		if(value === 0){
     			return "Active";
     		}
     		if(value === 2){
     			return "Disable";
     		}
			if(value === 1){
     			return "Pending";
     		}
		}
     	function numberFormatter(value,row,index){//显示序号
     		return index+1;
		}
     	
		function userTypeFormatter(value,row){//显示用户类型
     		if(value === 1){
     			return "Admin";
     		}
     		if(value === 2){
     			return "Employee";
     		}
		}
		function rolesForMatter(value, row, index) {
			var role = value.toString(2);
			if (role.length == 1) {
				role = '00' + role;
			} else if (role.length == 2) {
				role = '0' + role;
			}
			var strArray = new Array();
			if (role.charAt(0) == 1) {
				strArray.push('Reservation');
			}
			if (role.charAt(1) == 1) {
				strArray.push('Delivery');
			}
			if (role.charAt(2) == 1) {
				strArray.push('Pick-up');
			} 
			return strArray.toString();
		}
		
		
     	
	    $(function(){
	    	var tabeDataUrl;//刷新表格的URL
	    	var oldStatu;
	    	var newStatu;
	    	var restaurantUserUuid;
	    	var restaurantUuid ;
	    	
	    	$(".select2RestauramtName").select2({//使select具有select2的样式和功能
	    		placeholder: "Select a restaurant"
	    		
	    		  //allowClear: true
	    	});
	    	 
	    	$.ajax({//异步获取数据装载到select2的选项中
	    		type: 'get',
	    		async: false,
	    		url: '${ctx}/admin/getallrestaurantsname',
	    		success: function(data){
	    			var result = jQuery.parseJSON(data);
	    			var opt = "<option value=0>Select a restaurant</option>";
    				$(".select2RestauramtName").append(opt);
	    			for ( var i in result) {
	    				var a = result[i];
	    				var opt = "<option value="+a.restaurantUuid+">"+a.restaurantName+"</option>";
	    				$(".select2RestauramtName").append(opt);
					}
	    		}
	    	});

	    	$(".select2RestauramtName").on("select2:select", function (e) { log("select2:select", e); });
	    	//select2的选择事件，事件触发将获得对应商家的id，然后去后台搜索出对应的商家管理员并加载到表格中显示
	    	function log (name, evt) {
	    		restaurantUuid = evt.params.data.id;
	    		//$("input[name='restaurantId']").val(evt.params.data.id);
	    		//$("#restaurantId2").val(evt.params.data.id);
	    		//$("input[name='restaurantId']").val(evt.params.data.id);
	    		$("#restaurantUuid1").val(evt.params.data.id);
	    		if(restaurantUuid!=undefined && restaurantUuid!=0){
	    			$("#tableContent").show();
	    			tabeDataUrl = "${ctx}/admin/getrestaurantadminsbyrestaurantUuid?restaurantUuid="+restaurantUuid;
		    		$("#adminUserTable").bootstrapTable('refresh', {
	                    url: '${ctx}/admin/getrestaurantadminsbyrestaurantUuid?restaurantUuid='+restaurantUuid
	                });
	    		}
	    	} 
	    	
	    	$("#refresh").click(function(){//表格工具栏刷新按钮
				$("#adminUserTable").bootstrapTable('refresh',{
	            	url: tabeDataUrl
	        	});
	        });
	    	
	    	$("#audit").click(function(){//审核按钮点击事件
	    		$("#userName").empty();
	    		$("input[name='status']").iCheck('uncheck'); //移除 checked 状态
	    		var rowData = $("#adminUserTable").bootstrapTable('getSelections')[0];//行点击时获取出行和数据
	    		if(rowData){
	    			oldStatu = rowData.status;
	    			restaurantUserUuid = rowData.restaurantUserUuid;
	    		 	$("#userName").append(rowData.code);//获取名称加载到模态框
	    		 	$("#id").val(rowData.id);//获取餐厅的id
	    		 	if (oldStatu == 0) {
						$(".statusGroup input[value='0']").iCheck('check');
					}else if(oldStatu == 1){
						$(".statusGroup input[value='1']").iCheck('check');
					}else{
						$(".statusGroup input[value='2']").iCheck('check');
					}
					$(".statusGroup input[name='status']").on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
						newStatu = event.target.defaultValue;
					}); 
	    			$("#auditModal").modal('show');
	    			 
	    		 }
	    	});
	    	
	    	$("#sub").click(function(){
	    		if(newStatu!=oldStatu){
	    			$.ajax({
	 	    			type: 'POST',
	 	    			async: false,
	 	    			data: {restaurantUserUuid:restaurantUserUuid, statu:newStatu,restaurantUuid:restaurantUuid},
	 	    			url: '${ctx}/admin/auditrestaurantadmin',
	 	    			success:function(data){
	 	    				var msg = jQuery.parseJSON(data);
	 	    				if(msg.success){
	 	    					$("#auditModal").modal('hide');
		 	   	    			$("#adminUserTable").bootstrapTable('refresh',{
		 	   			            url: tabeDataUrl
		 	   			        });
	 	    				}else{
	 	    					$("#auditModal").modal('hide');
	 	    				}
	 	    				
	 	    			}
	 	    		});
	    		}
	    	});
	    	
	    	
	    	 
	    });
	  //---------------------------------------------------------------------------------//
	  $(function(){
		  
	  
	    var flag1 = false;
		var flag2 = false;
		var flag3 = false;
		var flag4 = false;
		var url;

		$("#addSelect").change(function(){
			if($(this).val()==1){
				$("#fm input[type='checkbox']").iCheck('check');
				$("#fm input[type='checkbox']").iCheck('disable');
				if($.trim($("input[name='code']").val())!=''){
					if(!/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/.test($("input[name='code']").val())){
						$("input[name='code']").popover('destroy');
						$("input[name='code']").popover({
							content:"Please enter the correct email!"
						});
						$("input[name='code']").popover('show');
					}
				}
			}
			else{
				$("#fm input[type='checkbox']").iCheck('uncheck');
				$("#fm input[type='checkbox']").iCheck('enable');
				$("input[name='code']").popover('destroy');
			}
			
		});
		
		
		

		$("input[name='code']").blur(function() {//$("#restaurantId1").val(
			if ($.trim($(this).val()) == "") {
				$(this).popover('destroy');
				$(this).popover({
					content : "Please fill in this field!"
				});
				$(this).popover('show');
			} else {
				if ($(this).lenth >= 200) {
					$(this).popover('destroy');
					$(this).popover({
						content : "The length is not greater than 200!"
					});
					$(this).popover('show');
				} else {
					if($("#addSelect").val()==1){//选择管理员，验证邮箱
						if(!/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/.test($("input[name='code']").val())){
							$("input[name='code']").popover('destroy');
							$("input[name='code']").popover({
								content:"Please enter the correct email!"
							});
							$("input[name='code']").popover('show');
						}
						else{
							$.ajax({
								  url: "${ctx}/restaurant/checkLoginName",
								  type:"POST",
								  cache: false,
								  data:{
									  'code':$("input[name='code']").val(),
									  'uuid' : $("input[name='uuid']").val(),
									  'type' : $("#addSelect").val(),
								  	  'restaurantUuid' : $("#restaurantUuid1").val()
								  },
								  success: function(msg){
									  var msg = jQuery.parseJSON(msg);
									  if(!msg.success){
										  flag1 = false;
										 $("input[name='code']").popover({
											  content:msg.errorMsg
										  });
										  $("input[name='code']").popover('show');
									  }
									  else{
										  flag1 = true;
										  $("input[name='code']").popover('destroy');
									  }
								  }
								});
						}
					}
					else{//选择普通员工
						$.ajax({
							url : "${ctx}/restaurant/checkCodeForEmployee",
							type : "post",
							cache : false,
							data : {
								'code' : $("input[name='code']").val(),
								'uuid' : $("input[name='uuid']").val(),
								'type' : $("#addSelect").val(),
								'restaurantUuid' : $("#restaurantUuid1").val()
							},
							success : function(msg) {
								var msg = jQuery.parseJSON(msg);
								if (!msg.success) {
									flag1 = false;
									$("input[name='code']").popover({
										content : msg.errorMsg
									});
									$("input[name='code']").popover('show');
								} else {
									flag1 = true;
									$("input[name='code']").popover('destroy');
								}
							}
						});
					}
					
				}

			}
		}).focus(function() {
			$("input[name='code']").popover('destroy');
		});

		$("input[name='firstName']").blur(function() {
			if ($.trim($(this).val()) == "") {
				$(this).popover('destroy');
				$(this).popover({
					content : "Please fill in this field!"
				});
				$(this).popover('show');
			} else {
				if ($(this).val().length >= 50) {
					$(this).popover('destroy');
					$(this).popover({
						content : "The length is not greater than 50!"
					});
					$(this).popover('show');
				} else {
					flag2 = true;
					$(this).popover('destroy');
				}
			}
		}).focus(function() {
			$(this).popover('destroy');
		});

		$("input[name='lastName']").blur(function() {
			if ($.trim($(this).val()) == "") {
				$(this).popover('destroy');
				$(this).popover({
					content : "Please fill in this field!"
				});
				$(this).popover('show');
			} else {
				if ($(this).val().length >= 50) {
					$(this).popover('destroy');
					$(this).popover({
						content : "The length is not greater than 50!"
					});
					$(this).popover('show');
				} else {
					flag3 = true;
					$(this).popover('destroy');
				}

			}
		}).focus(function() {
			$(this).popover('destroy');
		});

		$("input[name='password']").blur(
				function() {
					if ($.trim($(this).val()) == "") {
						$(this).popover({
							content : "Please fill in this field!"
						});
						$(this).popover('show');
					} else {
						if ($.trim($(this).val()).length < 6
								|| $(this).val().length > 32) {
							$(this).popover({
								content : "Password length of 6 to 32!"
							});
							$(this).popover('show');
						} else {
							flag4 = true;
							$(this).popover('destroy');
						}

					}
				}).focus(function() {
			$(this).popover('destroy');
		});
		
		$("#addSelect").blur(function(){
			if($(this).val()==''){
				$(this).popover({
					content : "Please fill in this field!"
				});
				$(this).popover('show');
			}
			else{
				flag5 = true;
				$(this).popover('destroy');
			}
		}).focus(function() {
			$(this).popover('destroy');
		});

		
			$("#sub2").click(function() {
					$("#sub2").popover('destroy');
					if ($.trim($("input[name='code']").val()) == "") {

						$("input[name='code']").popover('destroy');
						$("input[name='code']").popover({
							content : "Please fill in this field!"
						});
						flag1 = false;
						$("input[name='code']").popover('show');
					} else {
						if ($("input[name='code']").lenth >= 200) {
							$("input[name='code']").popover('destroy');
							$("input[name='code']").popover({
								content : "The length is not greater than 200!"
							});
							flag1 = false;
							$("input[name='code']").popover('show');
						} else {
							if ($("#addSelect").val() == 1) {//选择管理员，验证邮箱
								if (!/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/.test($("input[name='code']").val())) {
									$("input[name='code']").popover('destroy');
									$("input[name='code']").popover({
										content : "Please enter the correct email!"
									});
									$("input[name='code']").popover('show');
								} else {
									$.ajax({
										url : "${ctx}/restaurant/checkLoginName",
										type : "POST",
										cache : false,
										async : false,
										data : {
											'code' : $("input[name='code']").val(),
											'uuid' : $("input[name='uuid']").val(),
											'type' : $("#addSelect").val(),
											'restaurantUuid' : $("#restaurantUuid1").val()
										},
										success : function(msg) {
											flag1 = false;
											var msg = jQuery.parseJSON(msg);
											if (!msg.success) {
												$("input[name='code']").popover({
													content : msg.errorMsg
												});
												$("input[name='code']").popover('show');
											} else {
												flag1 = true;
												$("input[name='code']").popover('destroy');
											}
										}
									});
								}
							} else {//选择普通员工
								$.ajax({
									url : "${ctx}/restaurant/checkCodeForEmployee",
									type : "post",
									cache : false,
									async : false,
									data : {
										'code' : $("input[name='code']").val(),
										'uuid' : $("input[name='uuid']").val(),
										'type' : $("#addSelect").val(),
										'restaurantUuid' : $("#restaurantUuid1").val()
									},
									success : function(msg) {
										flag1 = false;
										var msg = jQuery.parseJSON(msg);
										if (!msg.success) {
											$("input[name='code']").popover({
												content : msg.errorMsg
											});
											$("input[name='code']").popover('show');
										} else {
											flag1 = true;
											$("input[name='code']").popover('destroy');
										}
									}
								});
							}

						}

					}

					if ($.trim($("input[name='firstName']").val()) == "") {
						$("input[name='firstName']").popover('destroy');
						$("input[name='firstName']").popover({
							content : "Please fill in this field!"
						});
						flag2 = false;
						$("input[name='firstName']").popover('show');
					} else {
						if ($("input[name='firstName']").val().length >= 50) {
							$("input[name='firstName']").popover('destroy');
							$("input[name='firstName']").popover({
								content : "The length is not greater than 50!"
							});
							flag2 = false;
							$("input[name='firstName']").popover('show');
						} else {
							flag2 = true;
							$("input[name='firstName']").popover('destroy');
						}
					}

					if ($.trim($("input[name='lastName']").val()) == "") {
						$("input[name='lastName']").popover('destroy');
						$("input[name='lastName']").popover({
							content : "Please fill in this field!"
						});
						flag3 = false;
						$("input[name='lastName']").popover('show');
					} else {
						if ($("input[name='lastName']").val().length >= 50) {
							$("input[name='lastName']").popover('destroy');
							$("input[name='lastName']").popover({
								content : "The length is not greater than 50!"
							});
							flag3 = false;
							$("input[name='lastName']").popover('show');
						} else {
							flag3 = true;
							$("input[name='lastName']").popover('destroy');
						}

					}

					if ($.trim($("input[name='password']").val()) == "") {
						$("input[name='password']").popover({
							content : "Please fill in this field!"
						});
						flag4 = false;
						$("input[name='password']").popover('show');
					} else {
						if ($.trim($("input[name='password']").val()).length < 6 || $("input[name='password']").val().length > 32) {
							$("input[name='password']").popover({
								content : "Password length of 6 to 30!"
							});
							flag4 = false;
							$("input[name='password']").popover('show');
						} else {
							flag4 = true;
							$("input[name='password']").popover('destroy');
						}

					}
					$("#addSelect").attr('disabled', false);
					if (flag1 && flag2 && flag3 && flag4) {
						$("input[name='restaurantUuid']").val($("#restaurantUuid1").val());//设置商家id
						if ($("#fm input[name='role1']").prop('checked')) {
							$("#fm input[name='role1']").val(4);
						}
						if ($("#fm input[name='role2']").prop('checked')) {
							$("#fm input[name='role2']").val(2);
						}
						if ($("#fm input[name='role3']").prop('checked')) {
							$("#fm input[name='role3']").val(1);
						}
						$.ajax({
							type : "POST",
							url : url,
							data : $("#fm").serialize(),
							success : function(msg) {
								var msg = jQuery.parseJSON(msg);
								if (!msg.success) {
									$("#sub2").popover({
										content : msg.errorMsg
									});
									$("#sub2").popover('show');
								} else {
									$('#myModal').modal('hide')
									$('table').bootstrapTable('refresh');
								}
							}
						});
					}

				});

				$("button[name='add']").click(function() {
					$("#sub2").popover('destroy');
					$("#fm input[type='checkbox']").iCheck('enable');
					$("#myModal h4").text("Add new employee");
					$("#fm input").val('');//重置表单
					$("#addSelect").val(2);//默认普通员工
					$("#addSelect").attr("disabled", false);
					$("#fm input[type='checkbox']").iCheck('uncheck');
					$("input").popover('destroy');//去除错误提示
					url = "${ctx}/restaurant/addRestaurantUser";
				});

				$("button[name='update']").click(function() {
					$("#sub2").popover('destroy');
					$("#fm input[type='checkbox']").iCheck('enable');
					$("#myModal h4").text("Modify employee");
					$("#fm input").val('');//重置表单
					$("#fm input[type='checkbox']").iCheck('uncheck');
					$("input").popover('destroy');//去除错误提示
					var user = $('table').bootstrapTable('getSelections')[0];
					var statu = user.status;
					if (user != undefined && statu == 0) {
						$('#myModal').modal('show');
						$("input[name='code']").val(user.code);
						$("input[name='firstName']").val(user.firstName);
						$("input[name='lastName']").val(user.lastName);
						$("input[name='password']").val(user.password);
						$("input[name='uuid']").val(user.restaurantUserUuid);
						$("#addSelect").val(user.type);
						$("#addSelect").attr("disabled", true);

						var role1 = user.role.toString(2);
						if (role1.length == 1) {
							role1 = '00' + role1;
						} else if (role1.length == 2) {
							role1 = '0' + role1;
						}
						if (role1.charAt(0) == 1) {
							$("#fm input[name='role1']").iCheck('check');
						}
						if (role1.charAt(1) == 1) {
							$("#fm input[name='role2']").iCheck('check');
						}
						if (role1.charAt(2) == 1) {
							$("#fm input[name='role3']").iCheck('check');
						}

						if (user.type == 1) {//判断是否是管理员
							$("#fm input[type='checkbox']").iCheck('disable');
						}
					} else if (statu != 0) {
						//$(this).popover('destroy');
						$("#msg").append("Status not active");
						//$("#msg").arrt("style","float:right;color: #FA6A6A; display: show");
						setTimeout(function() {
							$("#msg").empty();
						}, 2000);

					}
					url = "${ctx}/restaurant/updateRestaurantUser";

				});

				var user1 = '';
				$("button[name='delete']").click(function() {
					user1 = $('table').bootstrapTable('getSelections')[0];
					$("button[name='delete']").popover('destroy');
					if (user1 != undefined && user1 != '' && user1.type != 1) {
						$('#myModal1').modal('show');
					}
				});
				$("button[name='no']").click(function() {
					$('#myModal1').modal('hide');
				});
				$("button[name='ok']").click(function() {
					$.ajax({
						type : "POST",
						url : "${ctx}/restaurant/deleteEmployee",
						data : {
							'uuid' : user1.restaurantUserUuid
						},
						success : function(msg) {
							var msg = jQuery.parseJSON(msg);
							if (!msg.success) {
								$("button[name='delete']").popover({
									content : msg.errorMsg
								});
								$("button[name='delete']").popover('show');
							} else {
								$('#myModal1').modal('hide')
								$('table').bootstrapTable('refresh');
							}
						}
					});
				});
			});
		</script>
  </body>
</html>
