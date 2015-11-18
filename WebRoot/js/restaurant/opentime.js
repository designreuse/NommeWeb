
//营业时间页面的js
		//var appPath = "";
		
		$('#starttime,#endtime').datetimepicker({
			format : "hh:ii",
			weekStart : 1,
			autoclose : 1,
			startView : 1,
			minView : 0,
			maxView : 1,
			forceParse : 0
		});
		$.ajax({
			type : "POST",
			url : appPath+"/opentime/getAllOpenTime",
			success : function(msg) {
				var msg = $.parseJSON(msg);
				for ( var m in msg) {
					var role = msg[m].type;
					if (role==1) {//delivery
						$("#delivery").css("display","block");
						if ($.trim($("#d" + msg[m].week).text())!='') {
							$("#d" + msg[m].week).text(
									$("#d" + msg[m].week).text() + ", "
											+ msg[m].starttime + "-"
											+ msg[m].endtime);
						} else {
							$("#d" + msg[m].week).text(
									msg[m].starttime + "-" + msg[m].endtime);
						}
					}
					else if (role==2) {//pickup
						$("#pickup").css("display","block");
						if ($.trim($("#p" + msg[m].week).text())!='') {
							$("#p" + msg[m].week).text(
									$("#p" + msg[m].week).text() + ", "
											+ msg[m].starttime + "-"
											+ msg[m].endtime);
						} else {
							$("#p" + msg[m].week).text(
									msg[m].starttime + "-" + msg[m].endtime);
						}

					}
					else if (role==3) {//reservation
						$("#reservation").css("display","block");
						if ($.trim($("#r" + msg[m].week).text())!='') {
							$("#r" + msg[m].week).text(
									$("#r" + msg[m].week).text() + ", "
											+ msg[m].starttime + "-"
											+ msg[m].endtime);
						} else {
							$("#r" + msg[m].week).text(
									msg[m].starttime + "-" + msg[m].endtime);
						}
					}
				}
			}
		});

		$("#sub").click(function() {
			var startTime = $.trim($("#starttime").val());
			var endTime = $.trim($("#endtime").val());
			var temp = false;
			if(startTime.split(":")[0] < endTime.split(":")[0]){//开始小时小于结束小时
				temp = true;
			}else if(startTime.split(":")[0] == endTime.split(":")[0]){//开始小时等于结束小时
				if(startTime.split(":")[1] < endTime.split(":")[1]){
					temp = true;
				}
			}
			if(temp){
				$('#myModal').modal('hide')
				$("#bg").css("display","block");
				$("#show").css("display","block");
				$.ajax({
					type : "POST",
					url : appPath+"/opentime/addOpenTime",
					data : $("#fm").serialize(),
					success : function(msg) {
						var msg = $.parseJSON(msg);
						if (!msg.success) {
							alert(msg.errorMsg);
						} else {
							$("#bg").css("display","none");
							$("#show").css("display","none");
							location = appPath+"/opentime/toOpenTime";
						}
					}
				});
			}else{
				$("#endtime").popover('destroy');
				$("#endtime").popover({
					content : "Please selsect a latter time!"
				});
				$("#endtime").popover('show');
				$("#endtime").addClass("readBorder");
				setTimeout(function(){
					$("#endtime").popover('destroy');
				},3000);

			}
			
		});

		$("#close").click(function() {
			$("#starttime").val('');
			$("#endtime").val('');
			$("input[type='checkbox']").iCheck('unCheck');
		});

		$("fieldset button").click(
				function() {
					var type;
					var week;
					var obj = $(this).parent("div").prev("div").attr("id");
					week = obj.charAt(1);
					if (obj.charAt(0) == 'd') {
						type = 1;
					} else if (obj.charAt(0) == 'p') {
						type = 2;
					} else {
						type = 3;
					}
					
					$.ajax({
						type : "POST",
						url : appPath+"/opentime/deleteOpenTime",
						data : {
							type : type,
							week : week
						},
						success : function(msg) {
							var msg = $.parseJSON(msg);
							if (!msg.success) {
								validateErrorMsg($(this), msg.errorMsg);
							} else {
								$("#"+obj).text('');
							}
						}
					});

				});
		
		$('#myModal').on('hidden.bs.modal', function(e) {
			$("#week").val(1);
		})