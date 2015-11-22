<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
  <head>
    <title>My JSP 'chainManage.jsp' starting page</title>
	
	<jsp:include page="../inputcss.jsp"></jsp:include>
  </head>
  
  <body>
    <div id="custom-toolbar">
		<button type="button" id="addChain" class="btn btn-default mybt"
			data-toggle="modal" data-target="#chainModal" title="New Chain">
			<i class="fa fa-plus "></i> Add
		</button>
		<button type="button" id="modifyChain" class="btn btn-default mybt"
			 title="Edit Chain">
			<i class="fa fa-pencil "></i> Edit
		</button>
		<button type="button" id="deleteChain" class="btn btn-default mybt"
				title="Delete Area">
				<i class="fa fa-minus"></i> Delete
		</button>
		<button type="button" id="refresh" class="btn btn-default mybt"  data-method="refresh"
			title="refresh">
        	<i class="fa fa-refresh"></i> Refresh
   		</button>
	</div>
    <table id="table-chain"  data-toolbar="#custom-toolbar">
	    <!-- <thead>
	        <tr>
	            <th data-field="state" data-checkbox="true"></th>
	            <th data-field="id" data-align="right" data-sortable="true">chainname</th>
	           
	        </tr>
	    </thead> -->
	</table>
	<!-- 删除连锁店名称模态框 -->
	<div class="modal" id="deleteChainModal" tabindex="-2" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" style="font-weight: 400;">Delete Chain Store</h4>
					</div>
					<div class="modal-body">
						<div style="text-align:center;"><h3><span id="attentionInfo">Do you want to delete this Chain Store ?</span></h3></div>
						<div id="info" style="text-align:center; color:#FA6A6A;"></div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal" style="width:80px;">Close</button>
						<button type="button" id="subDelete" class="btn bg-olive" style="width:80px;">Delete</button>
					</div>
				</div>
		</div>
	</div>
	<!-- 新增连锁店名称的模态框 -->
		<div class="modal" id="chainModal" tabindex="-1" role="dialog"
			aria-labelledby="mySmallModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<form id="chainForm" action="" method="POST">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" style="font-weight: 400;">New Chain</h4>
						</div>
						<div class="modal-body">
							<input type="hidden" name="id" id="chainid"><!-- 连锁店ID -->
							<div class="row" id= "selectElement">
								<div class="col-md-3"><label class="modalField">Chain Name:</label></div>
								<div class="col-md-9">
									<input type="text" name="chainname" id="chainName">
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
	<jsp:include page="../inputjs.jsp"></jsp:include>
	<script type="text/javascript">
	$(function(){
		var currentChainId;
		var url = "";
		var flag1 = false;
		var oldChainName = "";
		function showPopover(ele, data, direction){//显示提示信息的方法
			ele.popover('destroy');
			ele.popover({
				content: data,
				placement: direction
			});
			ele.popover('show');
		}
		
		
		$("#refresh").click(function(){
			$("#table-chain").bootstrapTable('refresh', {
                url: '${ctx}/admin/getallchain'
            });
		}); 
		$("#addChain").click(function(){
			$("#chainName").popover('destroy');
			url="${ctx}/admin/addchain";
			$("#chainModal h4").text("Add Chain");
			chainUrl = "${ctx}/admin/addchain";
			$("#chainForm")[0].reset();//重置表单
			$("#chainid").val('0');
		
		});
		
		$("#modifyChain").click(function(){
			var currentChain = $("#table-chain").bootstrapTable('getSelections')[0];//行点击时获取出行和数据
			if(currentChain!=undefined && currentChain!=null){
				$("#chainName").popover('destroy');
				url="${ctx}/admin/modifychain";
				$("#chainModal h4").text("Edit Chain");
				chainUrl = "${ctx}/admin/modifychain";
				$("#chainForm")[0].reset();//重置表单
				var chain = $("#table-chain").bootstrapTable('getSelections')[0];//行点击时获取出行和数据
				oldChainName = chain.chainname;
				if(chain!=null){
					$("#chainName").val(chain.chainname);
					$("#chainid").val(chain.id);
					$("#chainModal").modal('show');
				}
			}
		}); 
		$("#chainName").focus(function(){
			$(this).popover('destroy');
		});
		
		$("#chainName").blur(function(){
			var id = $("#chainid").val();
			var chainname = $.trim($("#chainName").val());
			if(chainname==""){
				flag1 = false;
				showPopover($("#chainName"), "Required", "right");
			}else{
				$.ajax({
					type: "post",
					url: "${ctx}/admin/chainnameexist",
					data: {id:id, chainname:chainname},
					success: function(data){
						var msg = jQuery.parseJSON(data);
						if(msg.success){
							flag1 = true;
							$("#chainName").popover('destroy');
							
						}else{
							flag1 = false;
							$("#chainName").popover('destroy');
							showPopover($("#chainName"), "This name is used !", "right");
						}
					}
				});
			}
			
		});
		
		$("#sub").click(function(){
			var id = $("#chainid").val();
			var chainname = $.trim($("#chainName").val());
			if(oldChainName==chainname){
				flag1 = false;
				showPopover($("#chainName"), "Unmodified", "right");
			}else{
				if(!flag1){
					if(chainname==""){
						flag1 = false;
						showPopover($("#chainName"), "Required", "right");
					}else{
						$.ajax({
							type: "post",
							async: false,
							url: "${ctx}/admin/chainnameexist",
							data: {id:id, chainname:chainname},
							success: function(data){
								var msg = jQuery.parseJSON(data);
								if(msg.success){
									flag1 = true;
									$("#chainName").popover('destroy');
								}else{
									flag1 = false;
									$("#chainName").popover('destroy');
									showPopover($("#chainName"), "This name is used !", "right");
								}
							}
						});
					}
				}
				if(flag1){
					$.ajax({
						type: "post",
						url: url,
						data: {id:id, chainname:chainname},
						success: function(data){
							var msg = jQuery.parseJSON(data);
							
							if(msg.success){
								$("#sub").popover('destroy');
								showPopover($("#sub"), "Success", "top");
								$("#table-chain").bootstrapTable('refresh', {//刷新表格
					                url: '${ctx}/admin/getallchain'
					            });
								setTimeout(function(){
									$("#chainModal").modal('hide');
									$("#sub").popover('destroy');
								},1000);
								
							}else{
								$("#sub").popover('destroy');
								showPopover($("#sub"), "Option Error", "top");
							}
						}
					});
				
				
				}
			}
			
		
		});
		
		$("#deleteChain").click(function(){
			var currentChain = $("#table-chain").bootstrapTable('getSelections')[0];//行点击时获取出行和数据
			if(currentChain!=null){
				currentChainId = currentChain.id;
				$("#deleteChainModal").modal('show');
			}
		});
		
		$("#subDelete").click(function(){
			$.ajax({
				type:'post',
				async: false,
				url: '${ctx}/admin/deletechain',
				data:{id:currentChainId},
				success: function(data){
					var msg = jQuery.parseJSON(data);
					if(msg.success){//已经删除成功
						$("#deleteChainModal").modal('hide');
						$("#table-chain").bootstrapTable('refresh',{ //刷新表格数据
		                    url: '${ctx}/admin/getallchain'
		                });
					}else{//删除失败
						$("#subDelete").popover('destroy');
						showPopover($("#subDelete"), "Error","top");
					}
				}
			});
		});
					
	       /*  $("#via-javascript-table").next().click(function () {
	            $(this).hide(); */
	
	            $("#table-chain").bootstrapTable({
	                method: 'get',
	                url: '${ctx}/admin/getallchain',
	                cache: false,
	                height: 532,
	                striped: true,
	                pagination: true,
	                pageSize: 20,
	                //pageList: [10, 25, 50, 100, 200],
	               /*  search: true,
	                showColumns: true,
	                showRefresh: true, */
	                //minimumCountColumns: 2,
	                clickToSelect: true,
	                columns: [{
	                    field: 'state',
	                    radio: true
	                }, {
	                    field: 'chainname',
	                    title: 'Chain store name',
	                    align: 'left',
	                    valign: 'bottom',
	                    //sortable: true
	                }]
	            });
	    });
	</script>
  </body>
</html>
