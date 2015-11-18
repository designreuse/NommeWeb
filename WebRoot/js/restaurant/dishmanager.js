
//菜品管理页面的js

	$(function(){
		//var appPath = "";
		var flag = false;
		var url;
		var array = new Array();
		$("#back").click(function() {
			history.back();
		});
		
		//增加菜品时价格框中增加显示美元符号
		$("#priceShow").change(function(){
			var price = $.trim($(this).val());
			if(price){
				if(price.indexOf("$")==0){//开头有$符号
					var unitPrice = price.substring(1);
					$("#price").val(unitPrice);
				}else{//开头没有$符号
					$(this).val("$"+price);
					$("#price").val(price);
				}
			}
		})
		
		
		if ($("#type").val()== "add") {//增加菜品
			$("#garnishtable").css('display','none');
			url = appPath+"/dish/addDish";
			$("#cancel").css("display", "none");
			$('#photo').fileinput({
				language : 'es',
				uploadUrl : appPath+'/dish/addDishPhoto',
				allowedFileExtensions : [ 'jpg', 'png', 'gif' ],
				maxFileSize : 1024,
				maxFilesNum : 1,
				showUpload : false,
				dropZoneTitle : 'Upload photo here...'
			}).on('fileuploaded', function(event, data, previewId, index) {
				$("#photoUrl").val(data.response);
				$.ajax({
					type : "POST",
					url : appPath+"/dish/addDish",
					data : $("#fm").serialize(),
					success : function(msg) {
						var msg = $.parseJSON(msg);
						if (!msg.success) {
							validateErrorMsg($("#save"), msg.errorMsg);
						} else {
							var dishid = msg.flag;
							$.ajax({
								type : "POST",
								url : appPath+"/dish/addDishGarnish",
								data : {
									dishGarnish:JSON.stringify(array),
									dishId:dishid
								},
								success : function(msg) {
									$("#bg").css("display","none");
									$("#show").css("display","none");
									var msg = $.parseJSON(msg);
									if (!msg.success) {
										validateErrorMsg($("#save"), msg.errorMsg);
									} else {
										location = appPath+"/dish/toDish";
									}
								}
							});
						}
					}
				});
			}).on('filebatchselected', function(event, files) {
				flag = true;
			}).on('fileclear', function(event) {
				flag = false;
			});

			$.ajax({//异步加载所有的菜品分类
				type : "POST",
				url : appPath+"/dish/getAllDishMenu",
				success : function(msg) {
					var dishMenu = $.parseJSON(msg);
					for ( var d in dishMenu) {
						$("#dishMenuId").append(
								"<option value="+dishMenu[d].id+">"
										+ dishMenu[d].menuName + "</option>");
					}
				}
			});
			
			$.ajax({//异步加载所有的配菜分类
				type : "POST",
				url : appPath+"/garnish/dataTableGarnishHeader",
				success : function(msg) {
					var garnishMenu = $.parseJSON(msg);
					for ( var g in garnishMenu) {
						$("#garnishMenu").append(
								"<option value="+garnishMenu[g].id+">"
										+ garnishMenu[g].garnishMenu + "</option>");
					}
				}
			});
		} else if($("#type").val()=="update") {//修改菜品
			url = appPath+"/dish/updateDish";
			var dish;
			$.ajax({//异步加载所有的菜品分类
				type : "POST",
				url : appPath+"/dish/getDishById",
				data:{
					id:$("#mDishId").val()
				},
				success : function(msg) {
					dish = $.parseJSON(msg);
					modifyDish(dish);
				}
			});
		
			function modifyDish(dish){
				for ( var d in dish) {
					$("#" + d).val(dish[d]);
					if(d=="price"){
						$("#priceShow").val("$"+dish[d]);
					}
				}
				 if(dish.garnishList.length==0){
					$("#garnishtable").css('display','none');
				} 
				$('#table').bootstrapTable('load', dish.garnishList);
				for(var dl in dish.garnishList){
					var dgo = new garnishObj(dish.garnishList[dl].id,dish.garnishList[dl].name,dish.garnishList[dl].addprice,dish.garnishList[dl].menu,dish.garnishList[dl].garnishHeaderId);
					array.push(dgo);
				}
				
				var pickUp = dish.isPickup.toString(2);
				if (pickUp.length == 1) {
					pickUp = '00' + pickUp;
				} else if (pickUp.length == 2) {
					pickUp = '0' + pickUp;
				}
				if (pickUp.charAt(0) == 1) {
					$("input[name='role3']").iCheck('check');
				}
				if (pickUp.charAt(1) == 1) {
					$("input[name='role2']").iCheck('check');
				}
				if (pickUp.charAt(2) == 1) {
					$("input[name='role1']").iCheck('check');
				}
				$.ajax({//异步加载所有的菜品分类
					type : "POST",
					url : appPath+"/dish/getAllDishMenu",
					success : function(msg) {
						var dishMenu = $.parseJSON(msg);
						for ( var d in dishMenu) {
							if (dish.dishMenuId == dishMenu[d].id) {
								$("#dishMenuId").append(
										"<option value="+dishMenu[d].id+" selected='selected'>"
												+ dishMenu[d].menuName
												+ "</option>");
							} else {
								$("#dishMenuId").append(
										"<option value="+dishMenu[d].id+">"
												+ dishMenu[d].menuName
												+ "</option>");
							}
						}
					}
				});
				
				$.ajax({//异步加载所有的配菜分类
					type : "POST",
					url : appPath+"/garnish/dataTableGarnishHeader",
					success : function(msg) {
						var garnishMenu = $.parseJSON(msg);
						for ( var g in garnishMenu) {
							$("#garnishMenu").append(
									"<option value="+garnishMenu[g].id+">"
											+ garnishMenu[g].garnishMenu + "</option>");
						}
					}
				});
				
				$("#enIntroDiv").css("margin-top", "0px");
				if (dish.photoUrl) {
					$("#inputdiv").css("display", "block");
					$("#inputfm").css("display", "none");
					$("#imgdiv").append(
							"<img alt='photo' src="+dish.photoUrl+" width='250'>");
					$('#photo').fileinput({
						language : 'es',
						uploadUrl : appPath+'/dish/addDishPhoto',
						allowedFileExtensions : [ 'jpg', 'png', 'gif' ],
						maxFileSize : 1024,
						maxFilesNum : 1,
						showUpload : false,
						dropZoneTitle : 'Upload photo here...'
					}).on('fileuploaded', function(event, data, previewId, index) {
						$("#photoUrl").val(data.response);
						$.ajax({
							type : "POST",
							url : appPath+"/dish/updateDish",
							data : $("#fm").serialize(),
							success : function(msg) {
								var msg = $.parseJSON(msg);
								if (!msg.success) {
									alert(msg.errorMsg);
								} else {
									var dishid = msg.flag;
									$.ajax({
										type : "POST",
										url : appPath+"/dish/addDishGarnish",
										data : {
											dishGarnish:JSON.stringify(array),
											dishId:dishid
										},
										success : function(msg) {
											$("#bg").css("display","none");
											$("#show").css("display","none");
											var msg = $.parseJSON(msg);
											if (!msg.success) {
												validateErrorMsg($("#save"), msg.errorMsg);
											} else {
												location = appPath+"/dish/toDish";
											}
										}
									});
								}
							}
						});
					}).on('filebatchselected', function(event, files) {
						flag = true;
					}).on('fileclear', function(event) {
						flag = false;
					});
				} else {
					$("#cancel").css("display", "none");
					$('#photo').fileinput({
						language : 'es',
						uploadUrl : appPath+'/dish/addDishPhoto',
						allowedFileExtensions : [ 'jpg', 'png', 'gif' ],
						maxFileSize : 1024,
						maxFilesNum : 1,
						showUpload : false,
						dropZoneTitle : 'Upload photo here...'
					}).on('fileuploaded', function(event, data, previewId, index) {
						$("#photoUrl").val(data.response);
						$.ajax({
							type : "POST",
							url : appPath+"/dish/updateDish",
							data : $("#fm").serialize(),
							success : function(msg) {
								var msg = $.parseJSON(msg);
								if (!msg.success) {
									validateErrorMsg($("#save"), msg.errorMsg);
								} else {
									var dishid = msg.flag;
									$.ajax({
										type : "POST",
										url : appPath+"/dish/addDishGarnish",
										data : {
											dishGarnish:JSON.stringify(array),
											dishId:dishid
										},
										success : function(msg) {
											$("#bg").css("display","none");
											$("#show").css("display","none");
											var msg = $.parseJSON(msg);
											if (!msg.success) {
												validateErrorMsg($("#save"), msg.errorMsg);
											} else {
												location = appPath+"/dish/toDish";
											}
										}
									});
								}
							}
						});
					}).on('filebatchselected', function(event, files) {
						flag = true;
					}).on('fileclear', function(event) {
						flag = false;
					});
				}
			}
			
		}
			
			
		//修改菜品-------------------------------------------------------------------
		$("button[name='delete']").click(function(){//删除已经选择的配菜
			var garnishO = $('table').bootstrapTable('getSelections')[0];
			if(garnishO){
				for(var arr in array){
					if(garnishO.id==array[arr].id){
						array.splice(arr,1);
					}
				}
			}
			$('#table').bootstrapTable('load', array);
		});

		$("#replace").click(function() {
			$("#inputdiv").css("display", "none");
			$("#inputfm").css("display", "block");

		});

		$("#cancel").click(function() {
			$("#inputdiv").css("display", "block");
			$("#inputfm").css("display", "none");
			$('#photo').fileinput('clear');

		});

		$("#enName").blur(function() {
			if (validateEmpty($(this))) {
				if (validateMaxLength($(this), 200)) {
					$(this).popover('destroy');
				}
			}
		}).focus(function() {
			$(this).popover('destroy');
		});

		$("#chName").blur(function() {
			if (validateMaxLength($(this), 200)) {
				$(this).popover('destroy');
			}
		}).focus(function() {
			$(this).popover('destroy');
		});

		$("#price").blur(function() {
			if (validateEmpty($(this))) {
				if (validateDeceimal($(this))) {
					if (validateMaxLength($(this))) {
						$(this).popover('destroy');
					}
				}
			}
		}).focus(function() {
			$(this).popover('destroy');
		});

		$("#dishMenuId").blur(function() {
			if (validateEmpty($(this))) {
				$(this).popover('destroy');
			}
		}).focus(function() {
			$(this).popover('destroy');
		});

		function validateMyForm() {
			var flag1 = false;
			var flag2 = false;
			var flag3 = false;
			var flag4 = false;
			if (validateEmpty($("#enName"))) {
				if (validateMaxLength($("#enName"), 200)) {
					flag1 = true;
				}
			}
			if (validateMaxLength($("#chName"), 200)) {
				flag2 = true;
			}
			if (validateEmpty($("#price"))) {
				if ($("#price").val()>0 && validateDeceimal($("#price"))) {
					if (validateMaxLength($("#price"))) {
						flag3 = true;
					}
				}else{
					$("#priceShow").popover('destroy');
					$("#priceShow").popover({
								content :"Please enter the correct number!"
							});
					$("#priceShow").popover('show');
				}
			}
			if (validateEmpty($("#dishMenuId"))) {
				flag4 = true;
			}
			return flag1 && flag2 && flag3 && flag4;
		}

		$("#save").click(function() {
			//alert("aaa");
			if (validateMyForm()) {
				$("#bg").css("display","block");
				$("#show").css("display","block");
				if (flag) {
					$('#photo').fileinput('upload');
				} else {
					$.ajax({
						type : "POST",
						url : url,
						data : $("#fm").serialize(),
						success : function(msg) {
							var msg = $.parseJSON(msg);
							if (!msg.success) {
								validateErrorMsg($("#save"), msg.errorMsg);
							} else {
								var dishid = msg.flag;
								$.ajax({
									type : "POST",
									url : appPath+"/dish/addDishGarnish",
									data : {
										dishGarnish:JSON.stringify(array),
										dishId:dishid
									},
									success : function(msg) {
										$("#bg").css("display","none");
										$("#show").css("display","none");
										var msg = $.parseJSON(msg);
										if (!msg.success) {
											validateErrorMsg($("#save"), msg.errorMsg);
										} else {
											location = appPath+"/dish/toDish";
										}
									}
								});
							}
						}
					});
				}
			}
		});
		
		
		//select事件
		$("#garnishMenu").change(function(){
			$("#garnishDiv").empty();
			$.ajax({//异步加载所有的配菜
				type : "POST",
				url : appPath+"/garnish/getGarnishByHeader",
				data:{
					id:$(this).val()
				},
				success : function(msg) {
					garnish = $.parseJSON(msg);
					for ( var g in garnish) {
						$("#garnishDiv").append("<div class='row' id="+garnish[g].id+"><div class='col-md-3' style='margin-top:15px;'><span>"+garnish[g].garnishName+
								"</span></div><div class='col-md-1' style='margin-top:8px;'><input type='checkbox' style='height:20px;width:20px;'/></div>"+
								"<div class='col-md-8'><input type='text' style='width:157px;' placeholder='$0.00'/></div></div>");
					}
					$("#garnishDiv").append("<br><input type='checkbox' id='selectAll' style='height:20px;width:20px;'>select all");
					
					$("#selectAll").click(function(){
						if($(this).prop("checked")){
							$("#garnishDiv input[type='checkbox']").prop('checked',true);
						}
						else{
							$("#garnishDiv input[type='checkbox']").prop('checked',false);
						}
					});
					
					$("#sub").click(function(){
						var priceflag = true;
						for ( var ga in garnish) {
							if($("#"+garnish[ga].id+" input[type='checkbox']").prop('checked')){
								$("#garnishtable").css('display','block');
								var id = garnish[ga].id;
								var name = garnish[ga].garnishName;
								var garnishHeaderId = garnish[ga].garnishHeaderId;
								if($("#"+garnish[ga].id+" input[type='text']").val()){
									if(!validateDeceimal($("#"+garnish[ga].id+" input[type='text']"))){
										priceflag = false;
									}
									var addprice = $("#"+garnish[ga].id+" input[type='text']").val()
								}
								else{
									var addprice = 0;
								}
								var menu = garnish[ga].garnishHeaderName;
								if(priceflag){
									for(var a in array){
										if(id==array[a].id){
											array.splice(a,1);
										}
									}
									var garnishO = new garnishObj(id,name,addprice,menu,garnishHeaderId);
									array.push(garnishO);
								}
							}
						}
						if(priceflag){
							$('#table').bootstrapTable('load', array);
							$("#garnishDiv").empty();
							$("#garnishMenu").val('');
							$('#myModal').modal('hide');
						}
					});
				}
			});
			
		});
		
		
		function garnishObj(id,name,addprice,menu,garnishHeaderId){ //构造配菜信息对象
			this.id=id,
			this.name=name, 
			this.addprice=addprice,
			this.menu=menu,
			this.garnishHeaderId=garnishHeaderId
			} 

		$("#cancel1").click(function(){
			$("#garnishDiv").empty();
			$("#garnishMenu").val('');
		});
		
	});
	
