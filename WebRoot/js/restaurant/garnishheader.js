//配菜分类js

	function typeForMatter(value, row, index){
		if(value==0){
			return 'radio';
		}
		else if(value==1){
			return 'checkbox';
		}
		else if(value==2){
			return 'select';
		}
	}
	function mustForMatter(value, row, index){
		return value == 1?'yes':'no';
	}
	
	$(function(){
		//var appPath = "";
		
		var url;
		
		$("button[name='add']").click(function(){
		$("#myModalLabel").text(" Add new Ingredient category");
		url = appPath+"/garnish/addGarnishHeader";
		//$("#ismust").prop("disabled",true);//默认radio，必须的
	});
	
	$("button[name='update']").click(function(){
		//$("#ismust").prop("disabled",false);
		$("#myModalLabel").text(" modify the Ingredient category");
		url = appPath+"/garnish/updateGarnishHeader";
		var garnishHeader = $('table').bootstrapTable('getSelections')[0];
		if(garnishHeader.showType==1 || garnishHeader.showType==2){
			$("#countDisplay").css('display','block');
		}
		
		if(garnishHeader){
			for(var p in garnishHeader){//循环表单注入值
				$("#"+p).val(garnishHeader[p]);
			}
			if($("#showType").val()==0){//radio
				$('#count').val(1);
				$('#countDisplay').css('display','none');
			}
			else{
				$('#countDisplay').css('display','block');
			}
			$('#myModal').modal('show');
		}
	});
	
	
	$("#count").focus(function(){
		$(this).val("")
	});
	
	
	$("#showType").change(function(){
		if($(this).val()==0){//radio
			//$("#ismust").val(0);
			$('#count').val(1);
			$('#countDisplay').css('display','none');
		}
		else if($(this).val()==1){//checkbox
			$('#countDisplay').css('display','block');
			//$("#ismust").prop("disabled",false);
			$('#count').val(0);
		}
		else{//select
			$('#count').val(0);
			$('#countDisplay').css('display','block');
		}
	});
	
	function validateMyForm(){//表单提交时验证
		var flag1=false;
		var flag2=true;
		if(validateEmpty($("#garnishMenu"))){
			if(validateMaxLength($("#garnishMenu"),255)){
				flag1=true;
			}
		}
		
		//显示类型为checkbox或者是select时 
		if($('#showType').val()==1 || $('#showType').val()==2){
			flag2=false;
			if(validateEmpty($('#count'))){
				if(validateDeceimal($('#count'))){
					flag2=true;
				}
			}
			
		}
		
		return flag1&&flag2;
		
	}
	
	$('#myModal').on('hidden.bs.modal', function(e) {
		$("#garnishMenu").popover('destroy');
		$("#count").popover('destroy');
		$("input").val('');//重置表单
		$("#count").val(1);
		$("select").val(0);//恢复默认
		$("#countDisplay").css('display','none');
	});
	
	
	
	var g;
	$("button[name='delete']").click(function(){
		g =  $('table').bootstrapTable('getSelections')[0];
		if(g){
			$.ajax({
				type : "POST",
				url : appPath+"/garnish/checkGarnishHeader",
				data : {
					'id':g.id
				},
				success : function(msg) {
					if(msg==-1){
						$('#myModal1').modal('show');
					}
					else{
						$('#myModal2').modal('show');
					}
				}
			});
		}
	});
	
	$("button[name='no']").click(function(){
		$('#myModal1').modal('hide');
	});
	
	$("button[name='ok']").click(function(){
		$('#myModal1').modal('hide')
		$("#bg").css("display", "block");
		$("#show").css("display", "block");
		$.ajax({
			type : "POST",
			url : appPath+"/garnish/deleteGarnishHeader",
			data : {
				'id':g.id
			},
			success : function(msg) {
				$("#bg").css("display", "none");
				$("#show").css("display", "none");
				var msg = jQuery.parseJSON(msg);
				if (!msg.success) {
					alert(msg.errorMsg);
				} else {
					$("#myModal1").modal('hide')
					$('table').bootstrapTable('refresh');
				}
			}
		});
	});
	
	$("#sub").click(function(){
		if(validateMyForm()){
			$("#ismust").removeAttr("disabled"); 
			$('#myModal').modal('hide')
			$("#bg").css("display", "block");
			$("#show").css("display", "block");
			$.ajax({
				type : "POST",
				url : url,
				data : $("#fm").serialize(),
				success : function(msg) {
					$("#bg").css("display", "none");
					$("#show").css("display", "none");
					var msg = jQuery.parseJSON(msg);
					if (!msg.success) {
						alert(msg.errorMsg);
					} else {
						$('table').bootstrapTable('refresh');
					}
				}
			});
		}
	});
	});
	
	