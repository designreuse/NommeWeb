

$(function(){
	var currentCuisineId;
	var cuisineUrl = "";
	var flag1 = false;
	var oldClassificationName = "";
	function showPopover(ele, data, direction){//显示提示信息的方法
		ele.popover('destroy');
		ele.popover({
			content: data,
			placement: direction
		});
		ele.popover('show');
	}
	
	
	$("#refresh").click(function(){
		$("#cuisineTable").bootstrapTable('refresh', {
			url: appPath+'/admin/getallclassification'
		});
	}); 
	$("#addCuisine").click(function(){
		$("#classificationName").popover('destroy');
		oldClassificationName = "";
		cuisineUrl= appPath+"/admin/addclassification";
		$("#cuisineModal h4").text("Add Cuisine");
		//chainUrl = "${ctx}/admin/addchain";
		$("#cuisineForm")[0].reset();//重置表单
		$("#classificationId").val("-1");
	
	});
	
	$("#modifyCuisine").click(function(){
		$("#classificationName").popover('destroy');
		cuisineUrl = appPath+"/admin/modifyclassification";
		$("#cuisineModal h4").text("Edit Cuisine");
		chainUrl = appPath+"/admin/modifycuisine";
		$("#cuisineForm")[0].reset();//重置表单
		var cuisine = $("#cuisineTable").bootstrapTable('getSelections')[0];//行点击时获取出行和数据
		if(cuisine!=null){
			oldClassificationName = cuisine.classificationName;
			$("#classificationName").val(cuisine.classificationName);
			$("#classificationId").val(cuisine.id);
			$("#cuisineModal").modal('show');
		}
	}); 
	
	$("#classificationName").focus(function(){
		$(this).popover('destroy');
	});
	
	
	
	
	$("#classificationName").blur(function(){
		var classificationId = $("#classificationId").val();
		var classificationName = $.trim($("#classificationName").val());
		if(classificationName==""){
			flag1 = false;
			showPopover($("#classificationName"), "Required", "right");
		}else{
			$.ajax({
				type: "post",
				url: appPath+"/admin/classificationnameexist",
				data: {id:classificationId, classificationName:classificationName},
				success: function(data){
					var msg = jQuery.parseJSON(data);
					if(msg.success){
						flag1 = true;
						$("#classificationName").popover('destroy');
						
					}else{
						flag1 = false;
						$("#classificationName").popover('destroy');
						showPopover($("#classificationName"), "This name is used !", "right");
					}
				}
			});
		}
		
	});
	$("#sub").click(function(){
		flag1 = false;
		var classificationId = $.trim($("#classificationId").val());
		var classificationName = $.trim($("#classificationName").val());
		if(classificationName==""){
			flag1 = false;
			showPopover($("#classificationName"), "Required", "right");
			return;
		}
		if(oldClassificationName==classificationName){
			$("#cuisineModal").modal('hide');
			return;
		}
		if(!flag1){//用于验证classificationName
			
			if(classificationName!=""){//如果没有填
				//flag1 = false;
				//showPopover($("#classificationName"), "Required", "right");
			/* }else {*/
				$.ajax({
					type: "post",
					async: false,
					url: appPath+"/admin/classificationnameexist",
					data: {id:classificationId, classificationName:classificationName},
					success: function(data){
						var msg = jQuery.parseJSON(data);
						if(msg.success){
							flag1 = true;
							$("#classificationName").popover('destroy');
							
						}else{
							flag1 = false;
							$("#classificationName").popover('destroy');
							showPopover($("#classificationName"), "This name is used !", "right");
						}
					}
				});
			}
		}
		if(flag1){//如果验证通过就可以提交
			$.ajax({
				type: "post",
				async: false,
				url: cuisineUrl,
				data: {id:classificationId, classificationName:classificationName},
				success: function(data){
					var msg = jQuery.parseJSON(data);
					if(msg.success){//已经新增成功
						$("#sub").popover('destroy');
						showPopover($("#sub"), "Success","top");
						setTimeout(function(){
							$("#cuisineModal").modal('hide');
							$("#sub").popover('destroy');
						},1000);
						$("#cuisineTable").bootstrapTable('refresh',{ //刷新表格数据
		                    url: appPath+"/admin/getallclassification"
		                });
					}else{//新增失败
						$("#sub").popover('destroy');
						showPopover($("#sub"), "Error","top");
					}
				}
			});
		}
	});
	$("#deleteCuisine").click(function(){
		var cuisine = $("#cuisineTable").bootstrapTable('getSelections')[0];//行点击时获取出行和数据
		if(cuisine!=null){
			currentCuisineId = cuisine.id;
			$("#deleteCuisineModal").modal('show');
		}
	});
	
	$("#subDelete").click(function(){
		$.ajax({
			type:'post',
			async: false,
			url: appPath+"/admin/deleteclassification",
			data:{id:currentCuisineId},
			success: function(data){
				var msg = jQuery.parseJSON(data);
				if(msg.success){//已经新增成功
					$("#deleteCuisineModal").modal('hide');
					$("#cuisineTable").bootstrapTable('refresh',{ //刷新表格数据
	                    url: appPath+'/admin/getallclassification'
	                });
				}else{//新增失败
					$("#subDelete").popover('destroy');
					showPopover($("#subDelete"), "Error","top");
				}
			}
		});
	});
	
/*	$("#input-image").fileinput({
		
	    uploadUrl: appPath+'/admin/uploadImage', // you must set a valid URL here else you will get an error
	    allowedFileExtensions : ['jpg', 'png','gif'],
	    overwriteInitial: false,
	    maxFileSize: 1000,
	    maxFilesNum: 1,
	    showUpload: true,
	    showCaption: true,
	 
	    
	    //allowedFileTypes: ['image', 'video', 'flash'],
	    slugCallback: function(filename) {
	        return filename.replace('(', '_').replace(']', '_');
	    }
	});*/
	
	
	var classificationId = "";
	var imageType = "";
	var showImageDiv = "";
	$("#modifyImage").click(function(){
		$("#classificationName").popover('destroy');
		cuisineUrl=appPath+"/admin/modifyclassification";
		$("#cuisineModal h4").text("Edit Cuisine");
		chainUrl = appPath+"/admin/modifycuisine";
		$("#cuisineForm")[0].reset();//重置表单
		$("input[name='classification'").iCheck('uncheck');
		var cuisine = $("#cuisineTable").bootstrapTable('getSelections')[0];//行点击时获取出行和数据
		if(cuisine!=null){
			oldClassificationName = cuisine.classificationName;
			$("#image-classificationName").text(cuisine.classificationName);
			$("#image-classificationId").val(cuisine.id);
			classificationId = cuisine.id;
			$("div[name='show-ios-image']").css("background-image","url("+cuisine.iosImageUrl+")");
			$("div[name='show-ios-hover-image']").css("background-image","url("+cuisine.iosHoverImageUrl+")");
			$("div[name='show-android-image']").css("background-image","url("+cuisine.androidImageUrl+")");
			$("div[name='show-android-hover-image']").css("background-image","url("+cuisine.androidHoverImageUrl+")");
			$("div[name='show-web-image']").css("background-image","url("+cuisine.webImageUrl+")");
			$("#cuisineImageModal").modal('show');
		}
		
		//initFileUpload();
	}); 
	//获取即将处理的分类类型的value值
	$("input[name='classification']").on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
		imageType = event.target.defaultValue;
		if(imageType=="1-1"){
			showImageDiv = $("div[name='show-ios-image']");
		}else if(imageType=="1-2"){
			showImageDiv = $("div[name='show-ios-hover-image']");
		}else if(imageType=="2-1"){
			showImageDiv = $("div[name='show-android-image']");
		}else if(imageType=="2-2"){
			showImageDiv = $("div[name='show-android-hover-image']");
		}else if(imageType=="3-1"){
			showImageDiv = $("div[name='show-web-image']");
		}
		//alert(showImageDiv.attr("name"));
	}); 
	
	$("#submitImage").click(function(){
		SubmitImage();
	})
	
	function SubmitImage() {
		if ($("#input-image").val() == "") {
			alert("Please choose a file");
			return;
		}
		if(imageType.length<=0){
			alert("Please elsect a will set target images");
			return;
		}
		var loadedImageUrl = "";
		var filepath = $("#input-image").val();
		var extStart = filepath.lastIndexOf(".");
		var imageFile = document.getElementById("input-image").files;//获取文件
		var ext = filepath.substring(extStart, filepath.length)
				.toUpperCase();
		
		if (ext != ".JPG" && ext != ".JPEG" && ext != ".PNG") {
			alert("上传文件格式不正确！请上传JPG 或者 png 文件！");
			return;
		} else {
			//showImageDiv.removeAttr("style");
			$.ajaxFileUpload({
				url : appPath+'/admin/uploadImage', //用于文件上传的服务器端请求地址
				secureuri : false, //是否需要安全协议，一般设置为false
				fileElementId : 'input-image', //文件上传域的ID
				dataType : 'json', //返回值类型 一般设置为json data : goodsId = 1,
				data:{
					'imageType':imageType,
			    	'classificationId':classificationId,
			    	'imageFile':imageFile
			    	},
				success : function(data, status){ //服务器成功响应处理函数
					if (data.success) {
						loadedImageUrl = data.errorMsg;
						if (loadedImageUrl.length>0){
							//showImageDiv.css("background-image","url("+loadedImageUrl+")");
							showImageDiv.css("background-image","url("+loadedImageUrl+"?temp=" + Math.random()+")");
						}
					} 
					if (!data.success) {
						$.messager.alert('信息', data.errorMsg,'info');
					} else {
						if (data.isDownload != undefined) {
							$.messager.alert(
											'导入有误，请检查错误数据',
											"</br><a style='margin-left: 50px;margin-top:10px ' href='${ctx}/humanNew/downloaderror.do'>下载错误数据</a>",
											'info');
						} else {
							$('#dg').datagrid('reload');
							$.messager.alert('信息','导入成功！', 'info');
						}
					}
				}
			});
		}
	}
	//修改图片的模态框关闭时触发刷新表格
	$("#cuisineImageModal").on('hidden.bs.modal',function(){
		$("#refresh").click();
	});

	//显示选择的文件的名称
	$("#input-image").change(function(){
		var fileURI = $.trim($(this).val());
		var indexStr = fileURI.lastIndexOf("\\")+1;
		
		var fileName = fileURI.substring(indexStr);
		if(fileName.length>25){//如果名称太长，截取末尾25个字符，前面加省略号
			var subIndex = (fileName.length)-1-20;
			fileName = "..."+fileName.substring(subIndex);
		}
		$("#show-file-name").empty().text(fileName);
	})
	
	
});
