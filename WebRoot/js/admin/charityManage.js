	function statusFormatter(value){
  		if(value=='1'){
  			return 'Active';
  		}
  		if(value=='0'){
  			return 'Disabled';
  		}
  	}
	
$(function(){
	var operatorType = 1;
	var currentCharityId = 0;
	var url = "";
	var charityStatus = 1; 
	
	function showPopover(ele, data, direction) {//显示提示信息的方法
		ele.popover('destroy');
		ele.popover({
			content : data,
			placement : direction
		});
		ele.popover('show');
	}
	
	$("#addCharity").click(function(){
		operatorType = 1;
		$("h4[name='modal-title']").text("Add Charity");
		currentCharityId = 0;
		url = appPath + "/admin/addCharity";
		$("#charityModal").modal('show');
	})
	
	$("#editCharity").click(function(){
		var charity = $('#charityTable').bootstrapTable('getSelections')[0];
		if(charity){
			operatorType = 2;
			url = appPath + "/admin/editCharity";
			$("h4[name='modal-title']").text("Edit Charity");
			currentCharityId = charity.id;
			$("input[name='charityName']").val(charity.charityName);
			$("input[name='phone']").val(charity.phone);
			$("input[name='address']").val(charity.address);
			$("input[name='description']").val(charity.description);
			if(charity.status == 0){
				charityStatus = 0;
				$("div[name='status'] input[value='0']").iCheck('check');
			}else if(charity.status == 1){
				charityStatus = 1;
				$("div[name='status'] input[value='1']").iCheck('check');
			}
			$("#charityModal").modal('show');
		}
	});
	
	$("input[name='status']").on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
		charityStatus = event.target.defaultValue;
	}); 
	
	var flag1 = false;
	$("#sub").click(function(){
		if(!flag1){
			flag1 = validateForm($("input[name='charityName']").attr('name'));
		}
		if(flag1){
			$.ajax({
				type:'post',
				url: url,
				data: {id: currentCharityId,
					charityName: $.trim($("input[name='charityName']").val()),
					address: $.trim($("input[name='address']").val()),
					phone: $.trim($("input[name='phone']").val()),
					description: $.trim($("input[name='description']").val()),
					status: charityStatus},
				success:function(data){
					var msg = $.parseJSON(data);
					if(msg.success){
						$("#charityModal").modal('hide');
						$("#charityTable").bootstrapTable('refresh');
					}else{
						showPopover($("#sub"), msg.errorMsg, "top");
						setTimeout(function(){
							$("#sub").popover('destroy');
						}, 2000);
					}
				}
			})
		}
	});
	
	
	$("input[name='charityName']").focus(function(){
		$(this).popover('destroy');
	}).blur(function(){
		flag1 = validateForm($(this).attr('name'));
		//showPopover($(this), "Required","right");
	});
	
	function validateForm(ele){
		var flag = false;
		if(ele=='charityName'){
			var eleValue = $.trim($("input[name='"+ele+"']").val());
			if(eleValue==""){
				showPopover($("input[name='"+ele+"']"), "Required","right");
				return false;
			}else{
				return true;
			}
		}
		var charityName = $("div[name='charityName']").val();
		var charityName = $("div[name='phone']").val();
		var charityName = $("div[name='address']").val();
		var charityName = $("div[name='description']").val();
		
	
	};
	
	$("#refresh").click(function(){
		$("#charityTable").bootstrapTable('refresh');
	});
	
	$("#deleteCharity").click(function(){
		var charity = $('#charityTable').bootstrapTable('getSelections')[0];
		if(charity){
			$.ajax({
				type:'post',
				url: appPath+'/admin/deleteCharity',
				data: {charityId: charity.id},
				success:function(data){
					var msg = $.parseJSON(data);
					if(msg.success){
						$("#charityTable").bootstrapTable('refresh');
					}else{
						$("#attentionInfo").empty().text(msg.errorMsg);
						$("#deleteCharityModal").modal('show');
					}
				}
			})
		}
	});
	
	//信息确认模态框关闭时  触发展开显示订单详情模态框内容
	$('#charityModal').on('hidden.bs.modal', function() {
		initForm();
	})
	function initForm(){
		$("#formElements input[type='text']").each(function(){
			var elementId = $(this).attr("id");
			$(this).val("");
			$(this).popover('destroy');
		})
		$("div[name='status'] input[value='1']").iCheck('check');
	}
	
	
	
	
	
	
})