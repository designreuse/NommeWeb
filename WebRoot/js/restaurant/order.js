
//订单页面的js

	function orderTypeFormatter(value, row, index) {
		if (value === 1) {
			return 'Delivery';
		}
		if (value === 2) {
			return 'Pick up';
		}
		if (value === 3) {
			return 'Dine-in';
		}
	}
	function totalFormatter(value, row, index) {
		return "$" + value.toFixed(2);
	}
	function statusFormatter(value, row, index) {
		// 订单状态 0：订单取消状态 1:未付款 2：已付款 3：已接单 4:拒绝接单 5：退单 6：已退款  7：完成的订单  8：line up  9：现金付款   10:待审核
		if (value === 0) {
			return "Cancel";
		} else if (value === 1) {
			return "Unpaid";
		} else if (value === 2) {
			return "Paid";
		} else if (value === 3) {
			return "Accepted";
		} else if (value === 4) {
			return "Rejected";
		} else if (value === 6) {
			return "Refund";
		} else if (value === 7) {
			return "Complete";
		} else if (value === 8) {
			return "Line up";
		} else if (value === 9) {
			return "Cash";
		} else if (value === 10) {
			return "Pending";
		}
	}
	var status = "";
	var time = "";
	$(function() {
		//var appPath = "";

		$("#status").change(function() {
			status = $(this).val();
			refreshOrderTable();
			/*$('#orderTable').bootstrapTable('refresh', {
				url : appPath+"/order/datatable?status=" + status
			});*/
		});

		$(".form_datetime").datetimepicker({
			format : "MM dd yyyy  HH:ii P", showMeridian : true, autoclose : true, todayBtn : true
		});

		$("#dispose").click(function() {
			var currentOrder = $("#orderTable").bootstrapTable('getSelections')[0];//行点击时获取出行和数据
			if (currentOrder) {//有选中
				$.ajax({
					type : 'post', url : appPath+'/order/getOneOrderDetail', data : {
						orderId : currentOrder.id
					}, success : function(data) {
						$("#item").html(data);
						$("#orderModal").modal('show');
					}
				})
			}
		});
		
		$("#startSearchTime").datetimepicker({
			minView: "month", //选择日期后，不会再跳转去选择时分秒 
			format: 'yyyy-mm-dd',//选择日期后，文本框显示的日期格式 
			autoclose: true, //选择日期后自动关闭 
			todayBtn: true,
			initialDate: new Date()
		});

		$("#startSearchTime").change(function(){
			//tabeDataUrl = appPath+"/order/datatable
			time = $.trim($(this).val());
			refreshOrderTable();
		})
		$("#clearCondition").click(function(){
			time = "";
			status = "";
			$("#startSearchTime").val("");
			$("#status").find("option").eq(0).attr("selected","selected");
			refreshOrderTable()
		})
		
		//根据搜索条件刷新表格数据
		function refreshOrderTable(){
			var tabeDataUrl = appPath+"/order/datatable?orderDate="+time+"&status="+status;//设置请求的映射和参数
			$("#orderTable").bootstrapTable('removeAll');//清楚表格中原有的数据
			$("#orderTable").bootstrapTable('refresh', {//刷新加载新数据
				url: tabeDataUrl
			});
		}
		
	});