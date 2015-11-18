
	/*var index = 1;
	function numberFormatter(value){
		return index++;
	}*/
	function orderTypeFormatter(value){
		if(value==1){
			return "Delivery";
		}else if(value==2){
			return "Pick Up";
		}else if(value==3){
			return "Dine In";
		}
	}
	function paymentFormatter(value){
		if(value==0){
			return "Cash";
		}else if(value==1){
			return "Credit Card";
		}
	}
	function moneyFormatter(value){
		if(!value){
			return "$0.00";
		}else{
			return "$"+value;
		}
	}
	
$(function(){
	
	var initrequestURL = "";
	//默认时间范围是当月一号到当天
	(function initSelectTime(){
		var today = new Date();
		var strDate = today.getFullYear()+"-"+(today.getMonth()+1)+"-"+"01";
		var endDate = today.getFullYear()+"-"+(today.getMonth()+1)+"-"+today.getDate();
		$("#startSearchTime").val(strDate);
		$("#endSearchTime").val(endDate);
		initrequestURL = appPath+"/adminStatement/getStatementAllOrders?searchKey="+strDate+"==="+endDate;
	})()
	
	//初始化时间控件1
	$("#startSearchTime").datetimepicker({
		minView: "month", //选择日期后，不会再跳转去选择时分秒 
		format: 'yyyy-mm-dd',//选择日期后，文本框显示的日期格式 
		autoclose: true, //选择日期后自动关闭 
		todayBtn: true,
		initialDate: new Date()
	});
	//初始化时间控件2
	$("#endSearchTime").datetimepicker({
		minView: "month", //选择日期后，不会再跳转去选择时分秒 
		format: 'yyyy-mm-dd',//选择日期后，文本框显示的日期格式 
		autoclose: true, //选择日期后自动关闭 
		todayBtn: true,
		initialDate: new Date()
	});
	//开始时间change事件
	$("#startSearchTime").change(function(){
		if(!$.trim($("#endSearchTime").val())){
			$("#endSearchTime").val($(this).val());
		}
	})
	
	var orderByTypeClick = 0;
	var orderByPaymentClick = 0;
	
	//搜索按钮
	$("#searchByTime").click(function(){
		if(verifyTime()){//返回true，验证通过
			var searchKey = getSearchKey();// + "===" + orderByPaymentClick;
			loadData(searchKey);
		}
	})
	
	//验证时间开始时间小于等于结束时间
	function verifyTime(){
		var flag = true;
		if(!$.trim($("#startSearchTime").val()) || !$.trim($("#endSearchTime").val())){
			return true;
		}
		var startTime = $.trim($("#startSearchTime").val()).split("-");
		var endTime = $.trim($("#endSearchTime").val()).split("-");
		if(startTime[0]<=endTime[0]){
			if(startTime[1]<=endTime[1]){
				if(startTime[2]<=endTime[2]){
					//flag = true;
				}else{
					flag = false;
				}
			}else{
				flag = false;
			}
		}else{
			flag = false;
		}
		return flag;
	}
	//获取用于搜索的时间参数值
	function getSearchKey(){
		var startTime = $.trim($("#startSearchTime").val());
		var endTime = $.trim($("#endSearchTime").val());
		return startTime+"==="+endTime;
	}
	
	//带参数刷新表格数据
	function loadData(searchKey){
		var tabeDataUrl = appPath+"/adminStatement/getStatementAllOrders?searchKey="+searchKey;//设置请求的映射和参数
        $("#restaurantStatementTable").bootstrapTable('removeAll');//清楚表格中原有的数据
        $("#restaurantStatementTable").bootstrapTable('refresh', {//刷新加载新数据
            url: tabeDataUrl
        });
        //表格刷新后显示总金额
        showAmount();
	}
	
	
	$("#restaurantStatementTable").on('load-success.bs.table', function () {
		showAmount();
    })
	function showAmount(){
		$.ajax({
			type:'post',
			url: appPath+'/adminStatement/getStatementOrdersAmount',
			success:function(data){
				var msg = jQuery.parseJSON(data);
				if(msg.errorMsg.length>0){//已经删除成功
					$("#amount").empty().text(msg.errorMsg);
				}else{//删除失败
					$("#amount").empty().text(0.00);
				}
			}
		
		})
	}
	


	
})
