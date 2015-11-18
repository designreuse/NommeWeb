
function amountFormatter(value){
	if(value){
		return "$"+value;
	}else{
		return "$0.00";
	}
}

function countFormatter(value){
	if(value){
		return value;
	}else{
		return "0";
	}
}


$(function(){
	var initrequestURL = "";
	//默认时间范围是当月一号到当天
	(function initSelectTime(){
		var today = new Date();
		//var searchMonth = today.getFullYear()+"-"+(today.getMonth()+1);
		var searchMonth = $.trim($("input[name='responseMonth']").val());
		$("#searchMonth").val(searchMonth);
		//initrequestURL = appPath+"/adminStatement/getStatementCharityOrganization?searchKey="+searchMonth;
	})()
	
	//初始化时间控件1
	$("#searchMonth, #modal-searchMonth").datetimepicker({
		minView: "year", //选择日期后，不会再跳转去选择日 
		startView: "year",
		format: 'yyyy-mm', //选择日期后，文本框显示的日期格式 
		autoclose: true, //选择日期后自动关闭 
		todayBtn: false,
		initialDate: new Date()
	});
	
	//$("#searchByMonth").click(function(){
	$("#searchMonth").change(function(){
		var searchKey = $.trim($("#searchMonth").val());
		var tabeDataUrl = appPath+"/adminStatement/getStatementCharityOrganization?searchKey="+searchKey;//设置请求的映射和参数
        $("#donationStatementTable").bootstrapTable('removeAll');//清除表格中原有的数据
        $("#donationStatementTable").bootstrapTable('refresh', {//刷新加载新数据
            url: tabeDataUrl
        });
		
	});
	var currentCharityId = 0;
	$("#detail").click(function(){
		var charity = $('#donationStatementTable').bootstrapTable('getSelections')[0];
		$("#modal-searchMonth").val($.trim($("#searchMonth").val()));
		if(charity){
			currentCharityId = charity.id;
			loadDonationTableTitle();
		}
	});
	
	$("#modal-searchButton").click(function(){
		loadDonationTableTitle();
	});
	
	function loadDonationTableTitle(){
		if(currentCharityId>0){
			var searchMonth = $.trim($("#modal-searchMonth").val());
			//alert(currentCharityId);
			$.ajax({
				type: 'post',
				async: false,
				url: appPath + "/adminStatement/getOneCharityDonationTitle",
				data:{searchMonth:searchMonth, charityId:currentCharityId},
				success:function(data){
					var msg = $.parseJSON(data);
					console.log(msg);
					if(msg.success){
						$("span[name='association']").text(msg.association);
						$("span[name='address']").text(msg.address);
						$("span[name='phone']").text(msg.phone);
						$("span[name='count']").text(msg.count);
						if(msg.count==0){
							$("span[name='amount']").text("$0.00");
						}else{
							$("span[name='amount']").text("$"+msg.amount);
						}
						loadDonationTableData(searchMonth,currentCharityId);
						$("#donationModal").modal('show');
					}
				}
			});
		}
	}
	
	function loadDonationTableData(searchMonth,currentCharityId){
		var tabeDataUrl = appPath+"/adminStatement/getOneCharityDonation?searchMonth="+searchMonth+"&charityId="+currentCharityId;//设置请求的映射和参数
        $("#oneCharityDonationTable").bootstrapTable('removeAll');//清除表格中原有的数据
        $("#oneCharityDonationTable").bootstrapTable('refresh', {//刷新加载新数据
            url: tabeDataUrl
        });
	}
	
})