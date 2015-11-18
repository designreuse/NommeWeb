<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
  <head>
    <title>My JSP 'areaAndTaxPage.jsp' starting page</title>
	<jsp:include page="../inputjs.jsp"></jsp:include>
	<jsp:include page="../inputcss.jsp"></jsp:include>
  </head>
  
  <body>
  	<!-- class="contant" -->
  		<div id="custom-toolbar">
			<button type="button" id="addArea" class="btn btn-default mybt"
				data-toggle="modal" data-target="#areaModal" title="New Area" >
				<i class="fa fa-plus"></i> Add
			</button>
			<button type="button" id="modifyArea" class="btn btn-default mybt"
				title="Edit Area">
				<i class="fa fa-pencil"></i> Edit
			</button>
			<button type="button" id="deleteArea" class="btn btn-default mybt"
				data-toggle="modal" data-target="#deleteAreaModal" title="Delete Area">
				<i class="fa fa-minus"></i> Delete
			</button>
			<button type="button" id="refresh" class="btn btn-default mybt"  data-method="refresh"
				title="refresh">
        		<i class="fa fa-refresh"></i> Refresh
   			 </button>
		</div>
		
		<table id="areaTable" 
			data-toggle="table" data-url="${ctx}/admin/getallareas"
			data-click-to-select="true"
			data-pagination="true" 
			data-striped="true"
			data-toolbar="#custom-toolbar"
			data-page-size="20"
			>
    	<thead>
    		<tr>
    			<th data-field="state" data-radio="true"></th>
    			<th data-field="tree">Area Name</th>
    			<th data-field="tax" data-formatter="taxForMatter">Tax</th>
    			<th data-field="taxMode" data-formatter="modeForMatter" >Tax Mode</th>
    		</tr>
    	</thead>
		</table>
		
		<!-- 删除区域税率的模态框 -->
		<div class="modal" id="deleteAreaModal" tabindex="-2" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
			<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" style="font-weight: 400;">Delete Area</h4>
						</div>
						<div class="modal-body">
							<input type="hidden" name="id" id="areaid"><!-- 区域ID -->
							<input type ="hidden" name="pid" id = "pid">
							<div style="text-align:center;"><h3><span id="attentionInfo">Do you want to delete this Area ?</span></h3></div>
							<div id="info" style="text-align:center; color:#FA6A6A;"></div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal" style="width:80px;">Close</button>
							<button type="button" id="subDelete" class="btn bg-olive" style="width:80px;">Delete</button>
						</div>
					</div>
			</div>
		</div>
		
		<!-- 新增和修改区域税率的模态框 -->
		<div class="modal" id="areaModal" tabindex="-1" role="dialog"
			aria-labelledby="mySmallModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<form id="areaForm" action="" method="POST">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" style="font-weight: 400;">New Admin</h4>
						</div>
						<div class="modal-body">
							<input type="hidden" name="id" id="areaid"><!-- 区域ID -->
							<input type ="hidden" name="pid" id = "pid">
							<div class="row" id= "selectElement">
								<div class="col-md-3"><label class="modalField">Country:</label></div>
								
								<div class="col-md-9">
									<select class="form-control" id="areaSelect">
									</select>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3"><label class="modalField">Area Name:</label></div>
								<div class="col-md-9">
									<input type="text" name="areaName" id="areaName">
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-3"><label class="modalField">Tax:</label></div>
								<div class="col-md-9">
									<input type="text" name="tax" id="tax">
								</div>
							</div>
							<div class="row taxModeDiv" style="display:none;">
								<div class="col-md-3"><label class="modalField">Tax Mode:</label></div>
								<div class="taxModeGroup" style="margin-top: 12px;margin-left: 20px;">&nbsp;&nbsp;
									<span class="disposePatterns" id="parentPatternA"><input type="radio" name="taxMode" id="taxMode1" value="1">&nbsp;HST</span>
									<span style="display:block;width:25px;height:10px;float: left;"></span>&nbsp;
									<span class="disposePatterns" id="parentPatternB"><input type="radio" name="taxMode" id="taxMode2" value="2">&nbsp;GST/PST</span>
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
		
		<script type="text/javascript">
			function modeForMatter (value,row,index){
				if(value==0)
					return "/";
				if(value==1)
					return "HST";
				if(value==2)
					return "GST/PST";
			}
			function taxForMatter (value,row,index){
				return parseFloat(value).toFixed(2);
			}
		
		
			$(function(){
				flag1 = false;
				flag2 = false;
				var areaUrl;	
				var taxmode;
				var newTaxMode;
				$("#addArea").click(function(){
					$(".taxModeDiv").hide();
					$("#tax").popover('destroy');
					$("#areaName").popover('destroy');
					$("#areaModal h4").text("Add Area");
					areaUrl = "${ctx}/admin/addAreas";
					$("#areaForm")[0].reset();//重置表单
					$(".taxModeGroup input[value='1']").iCheck('check');
					$("#areaSelect").empty();
					$("#selectElement").show();
					$("<option value='0'>New Country</option>").appendTo("#areaSelect");
					$.ajax({
						type:"get",
						async: false,
						url: "${ctx}/admin/getallparentarea",
						success: function(data){
							var obj = jQuery.parseJSON(data);
							 var parentAreasList = (obj.rows); 
				                 if(parentAreasList != null && parentAreasList.length > 0){  
				                    for(var i = 0; i< parentAreasList.length; i++){  
				                        $("<option value='"+parentAreasList[i].id+"'>"+parentAreasList[i].areaName+"</option>").appendTo("#areaSelect");  
				                    }  
				                }   
						}
					});
				});
				
				$("#refresh").click(function () {//表格工具栏刷新按钮
					$("#areaTable").bootstrapTable('refresh', {
	                    url: '${ctx}/admin/getallareas'
	                });
	            });
			
				function showPopover(ele, data,direction){//显示提示信息的方法
					ele.popover('destroy');
					ele.popover({
						content: data,
						placement: direction
					});
					ele.popover('show');
				}
				
				$("#modifyArea").click(function(){//点击修改按钮，装载数据到表单
					$("#tax").popover('destroy');
					$("#areaName").popover('destroy');
					$("#areaModal h4").text("Edit Area");
					areaUrl = "${ctx}/admin/modifyareas";
					$("#areaForm")[0].reset();//重置表单
					$("#areaSelect").empty();//清空下拉框内容
					var area = $("#areaTable").bootstrapTable('getSelections')[0];//行点击时获取出行和数据
					if(area == undefined){
						return;
					}else{//如果获取到了一行数据
						$("#areaid").val(area.id);//设置区域的id到表单
						var pid = area.pid;//父类Id
						$("#pid").val(pid);//设置pid到表单
						//alert(pid);
						if(pid==0){
							$("#selectElement").hide();
						}else{
							$.ajax({//加载下拉框父类区域数据
								type:"get",
								async: false,
								url: "${ctx}/admin/getallparentarea",
								success: function(data){
									var obj = jQuery.parseJSON(data);
									 var parentAreasList = (obj.rows);
						                 if(parentAreasList != null && parentAreasList.length > 0){
						                    for(var i = 0; i< parentAreasList.length; i++){
						                        $("<option value='"+parentAreasList[i].id+"'>"+parentAreasList[i].areaName+"</option>").appendTo("#areaSelect");  
						                    }  
						                } 
						         	$("#areaSelect option").each(function(){
										if($(this).val() == pid){
											$(this).attr("selected","selected");
										}
									});
									$("#selectElement").show();
								}
							});
						}
						
						if(area.taxMode==0){
							$(".taxModeDiv").hide();
							newTaxMode = 0;
						}else if(area.taxMode==1){
							newTaxMode = 1;
							$(".taxModeGroup input[value='1']").iCheck('check');
							$(".taxModeDiv").show();
						}else if(area.taxMode==2){
							newTaxMode = 2;
							$(".taxModeGroup input[value='2']").iCheck('check');
							$(".taxModeDiv").show();
						}
						
						
						$("#areaName").val(area.areaName);
						$("#tax").val(area.tax);
						$("#areaModal").modal('show');
					}
				});
				
				$(".taxModeGroup input[name='taxMode']").on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
					newTaxMode = event.target.defaultValue;
				}); 
				
				$("#areaSelect").change(function(){
					var pid = $("#areaSelect").val();
					$("#pid").val(pid);
					if(pid==0){
						newTaxMode = 0;
						$(".taxModeDiv").hide();
					}else{
						$(".taxModeDiv").show();
						newTaxMode = 1;
					}
					
				});
				
				$("#areaName").focus(function(){
					$("#areaName").popover('destroy');
				});
				$("#areaName").blur(function(){//区域名称的鼠标离开事件
					var pid = $.trim($("#pid").val());
					var id = $.trim($("#areaid").val());
					var areaName = $.trim($("#areaName").val());
					
					if(areaName==""){
						showPopover($("#areaName"), "Required","right");
						flag1 = false;
					}
					else{
						$.ajax({
							type: "post",
							url: "${ctx}/admin/areanameexist",
							data: {pid:pid, id:id, areaName:areaName},
							success: function(data){
								var msg = jQuery.parseJSON(data);
								if(msg.success){
									$("#areaName").popover('destroy');
									flag1 = true;
								}else{
									showPopover($("#areaName"), "This areaname id used","right");
									flag1 = false;
								}
							}
						});
					}
				});
				
				$("#tax").focus(function(){
					$("#tax").popover('destroy');
				});
				$("#tax").blur(function(){
					var tax = $.trim($("#tax").val());
					if(tax==""){
						flag2 = false;
						showPopover($("#tax"), "Required","right");
					
					}else{
						if(tax>=0.00 && tax<1){
							flag2 = true;
							$("#tax").popover('destroy');
						}else{
							flag2 = false;
							showPopover($("#tax"), "Tax format error","right");
						}
					}
				});
				
			
				$("#sub").click(function(){
					if(!flag1){//用于验证areaName
						var pid = $.trim($("#areaSelect").val());
						var id = $.trim($("#areaid").val());
						var areaName = $.trim($("#areaName").val());
						
						if(areaName==""){
							showPopover($("#areaName"), "Required","right");
							flag1 = false;
						}
						else{
							$.ajax({
								type: "post",
								async: false,
								url: "${ctx}/admin/areanameexist",
								data: {pid:pid, id:id, areaName:areaName},
								success: function(data){
									var msg = jQuery.parseJSON(data);
									if(msg.success){
										$("#areaName").popover('destroy');
										flag1 = true;
									}else{
										showPopover($("#areaName"), "This areaname id used","right");
										flag1 = false;
									}
								}
							});
						}
					}
					if(!flag2){
						var tax = $.trim($("#tax").val());
						if(tax==""){
							flag2 = false;
							showPopover($("#tax"), "Required","right");
						
						}else{
							if(tax>0.001 && tax<1){
								flag2 = true;
								$("#tax").popover('destroy');
							}else{
								flag2 = false;
								showPopover($("#tax"), "Tax format error","right");
							}
						}
					}
					if(flag1 && flag2){//如果验证通过就可以提交
						var pid = $("#pid").val();
						var areaname = $("#areaName").val();
						var tax = $("#tax").val();
						var id = $("#areaid").val();
							$.ajax({
								type: "post",
								url: areaUrl,
								data: {pid:pid, areaName:areaname, tax:tax, id:id, taxMode:newTaxMode},
								success: function(data){
									var msg = jQuery.parseJSON(data);
									if(msg.success){//已经新增成功
										$("#sub").popover('destroy');
										showPopover($("#sub"), "Success","top");
										setTimeout(function(){
											$("#areaModal").modal('hide');
											$("#sub").popover('destroy');
										},1000);
										$("#areaTable").bootstrapTable('refresh',{ //刷新表格数据
						                    url: '${ctx}/admin/getallareas'
						                });
									}else{//新增失败
										$("#sub").popover('destroy');
										showPopover($("#sub"), "Error","top");
									}
								}
							});
					}
				});
				
				
				$("#deleteArea").click(function(){
					$("#info").empty();
				});
				
				$("#subDelete").click(function(){
					$("#info").empty();
					var area = $("#areaTable").bootstrapTable('getSelections')[0];//行点击时获取出行和数据
					$.ajax({
						type: "post",
						url: "${ctx}/admin/deletearea",
						async: false,
						data:{pid:area.pid,id:area.id},
						success: function(data){
							var msg = jQuery.parseJSON(data);
							if(msg.success){//说明成功了
								$("#deleteAreaModal").modal('hide');
								$("#areaTable").bootstrapTable('refresh',{ //刷新表格数据
				                    url: '${ctx}/admin/getallareas'
				                });
							}else{//出错，可能是删除出错，可能是删除的父区域里面有子区域没有删除
								var arrorMessage = msg.errorMsg;
								$("#info").append(arrorMessage);
							}
							
						}
					});
				});
			});
		
		
		</script>
  </body>
</html>
