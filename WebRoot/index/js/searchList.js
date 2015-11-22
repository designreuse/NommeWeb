
$(function(){
	//显示等待遮罩层
    $("#bg").css("display", "block");
	$("#show").css("display", "block");
	var image = appPath+"/images/maker.png";
	//加载地图
	var path;
	var total;//餐厅的总数量
	var globalPage=1;//当前页数
	var order=0;//排序规则，0默认距离排序，1字母排序，2价格排序，3评分排序
	var display=1;//定义显示方式，1表示列表显示，2表示九宫格，3地图显示。默认列表
	path=appPath;
	$("a[name='logo']").click(function(){
		window.location = appPath+"/index/index";
	});
	
	$(".search_list-right-can li").click(function(){
		window.location = appPath+"/index/restaurantmenu";
	});
	
	var searchKeywords = ($.cookie("esarchKeywords")).toLowerCase();
	//alert(searchKeywords.toLowerCase());
	$("div[name='cuisineCheckboxList']").find("input").each(function(){
		//alert($(this).val());
		var key =$(this).val().toLowerCase();
		if(searchKeywords.indexOf($(this).val().toLowerCase())>=0){
			$(this).prop("checked","checked");
		}
	});
	
	/*function starClick(obj){
		var num = obj.attr("name");
		var num1 = num.slice(4);
		return num1;
		//alert(num1);
	};*/
	var stars = "0";//定义用于保存选中的评分五角星的序号
	var dollars = "0";//定义用于保存选中的价位等级的序号
	function editStar(stars){//修改标星的方法
		if(stars=="s1"){
			$("span[title='s1']").empty().append("★").css("color","#7FBF2E");
			$("span[title='s2']").empty().append("☆").css("color","#595959");
			$("span[title='s3']").empty().append("☆").css("color","#595959");
			$("span[title='s4']").empty().append("☆").css("color","#595959");
			$("span[title='s5']").empty().append("☆").css("color","#595959");
		}else if(stars=="s2"){
			$("span[title='s1']").empty().append("★").css("color","#7FBF2E");
			$("span[title='s2']").empty().append("★").css("color","#7FBF2E");
			$("span[title='s3']").empty().append("☆").css("color","#595959");
			$("span[title='s4']").empty().append("☆").css("color","#595959");
			$("span[title='s5']").empty().append("☆").css("color","#595959");
		}else if(stars=="s3"){
			$("span[title='s1']").empty().append("★").css("color","#7FBF2E");
			$("span[title='s2']").empty().append("★").css("color","#7FBF2E");
			$("span[title='s3']").empty().append("★").css("color","#7FBF2E");
			$("span[title='s4']").empty().append("☆").css("color","#595959");
			$("span[title='s5']").empty().append("☆").css("color","#595959");
		}else if(stars=="s4"){
			$("span[title='s1']").empty().append("★").css("color","#7FBF2E");
			$("span[title='s2']").empty().append("★").css("color","#7FBF2E");
			$("span[title='s3']").empty().append("★").css("color","#7FBF2E");
			$("span[title='s4']").empty().append("★").css("color","#7FBF2E");
			$("span[title='s5']").empty().append("☆").css("color","#595959");
		}else if(stars=="s5"){
			$("span[title='s1']").empty().append("★").css("color","#7FBF2E");
			$("span[title='s2']").empty().append("★").css("color","#7FBF2E");
			$("span[title='s3']").empty().append("★").css("color","#7FBF2E");
			$("span[title='s4']").empty().append("★").css("color","#7FBF2E");
			$("span[title='s5']").empty().append("★").css("color","#7FBF2E");
		}else{
			$("span[title='s1']").empty().append("☆").css("color","#595959");
			$("span[title='s2']").empty().append("☆").css("color","#595959");
			$("span[title='s3']").empty().append("☆").css("color","#595959");
			$("span[title='s4']").empty().append("☆").css("color","#595959");
			$("span[title='s5']").empty().append("☆").css("color","#595959");
		}
	}
	function editDollar(dollars){//修改标星的方法
		if(dollars=="d1"){
			$("span[title='d1']").css("color","#7FBF2E");
			$("span[title='d2']").css("color","#595959");
			$("span[title='d3']").css("color","#595959");
			$("span[title='d4']").css("color","#595959");
			$("span[title='d5']").css("color","#595959");
		}else if(dollars=="d2"){
			$("span[title='d1']").css("color","#7FBF2E");
			$("span[title='d2']").css("color","#7FBF2E");
			$("span[title='d3']").css("color","#595959");
			$("span[title='d4']").css("color","#595959");
			$("span[title='d5']").css("color","#595959");
		}else if(dollars=="d3"){
			$("span[title='d1']").css("color","#7FBF2E");
			$("span[title='d2']").css("color","#7FBF2E");
			$("span[title='d3']").css("color","#7FBF2E");
			$("span[title='d4']").css("color","#595959");
			$("span[title='d5']").css("color","#595959");
		}else if(dollars=="d4"){
			$("span[title='d1']").css("color","#7FBF2E");
			$("span[title='d2']").css("color","#7FBF2E");
			$("span[title='d3']").css("color","#7FBF2E");
			$("span[title='d4']").css("color","#7FBF2E");
			$("span[title='d5']").css("color","#595959");
		}else if(dollars=="d5"){
			$("span[title='d1']").css("color","#7FBF2E");
			$("span[title='d2']").css("color","#7FBF2E");
			$("span[title='d3']").css("color","#7FBF2E");
			$("span[title='d4']").css("color","#7FBF2E");
			$("span[title='d5']").css("color","#7FBF2E");
		}else{
			$("span[title='d1']").css("color","#595959");
			$("span[title='d2']").css("color","#595959");
			$("span[title='d3']").css("color","#595959");
			$("span[title='d4']").css("color","#595959");
			$("span[title='d5']").css("color","#595959");
		}
	}
	
	$("span[name='star']").mouseover(function(){//鼠标移入五角星
		var s = $(this).attr("title");
		editStar(s);
	});
	$("span[name='star']").mouseout(function(){//鼠标移出五角星
		editStar(stars);
	});
	$("span[name='star']").click(function(){//五角星评分的点击事件
		stars = $(this).attr("title");
		editStar(stars);
		globalPage = 1;
		$("#score").val(stars.charAt(1));
		initRestaurant();
	});
	$("span[name='clearStar']").click(function(){//五角星评分的清楚按钮
		$("span[name='star']").empty().append("☆").css("color","#595959");
		stars = 0;
		$("#score").val('');
		globalPage = 1;
		initRestaurant();
	});
	
	$("span[name='dollar']").mouseover(function(){//鼠标移入$符号
		var s = $(this).attr("title");
		editDollar(s);
	});
	$("span[name='dollar']").mouseout(function(){//鼠标移出$符号
		editDollar(dollars);
	});
	$("span[name='dollar']").click(function(){//$符号的点击事件
		dollars = $(this).attr("title");
		editDollar(dollars);
		globalPage = 1;
		$("#avgPrice").val(dollars.charAt(1)*10+10);
		initRestaurant();
	});
	$("span[name='clearDollar']").click(function(){//$的清除按钮
		$("span[name='dollar']").css("color","#595959");
		dollars = 0;
		$("#avgPrice").val('');
		globalPage = 1;
		initRestaurant();
	});
	$("span[name='clearCuisine']").click(function(){//餐厅类型的清除按钮
		$("div[name='cuisineCheckboxList'] input[type='checkbox']").attr("checked",false);
		types = [];
		globalPage = 1;
		$("#classificationName").val(types);
		initRestaurant();
	});
	
	$("span[name='clearFeatures']").click(function(){//最下面条件的清除按钮
		$("div[name='featuresCheckboxList'] input[type='checkbox']").attr("checked",false);
		globalPage = 1;
		$("#discountNum").val('');
		$("#opentime").val('');
		$("#isreservation").val('');
		initRestaurant();
	});
	
	$(".showType").click(function(){//点击效果
		$(this).css('background','#aaaaaa');
		$(this).css('opacity','0.7');
		$(".showType").not($(this)).css('background','none');
		$(".showType").not($(this)).css('opacity','1');
	});
	
	var types = [];
	(function(){
		if($("#only").val()){
			$("div[name='cuisineCheckboxList'] input[value='"+$("#only").val()+"']").prop('checked',true);
		}
		if($("#classificationName").val()){
			types.push($("#classificationName").val());
		}
		
	})();
	$("div[name='cuisineCheckboxList'] input[type='checkbox']").click(function(){//餐厅类型的点击事件
		var thisVal = $(this).val();
		globalPage = 1;
		if($(this).prop('checked')){//判断多选控件是否选中
			types.push(thisVal)
		}
		else{
			$.each(types,function(index,item){
				if(item==thisVal){
					types.splice(index,1);
				}
			})
		}
		$("#classificationName").val(types);
		initRestaurant();
	});
	
	$.ajax({//默认列表加载页面
        type:"post",
        url:path+"/searchList/banner",     
        data:$("#fm").serialize()+"&offset="+globalPage+"&order="+order,
        success:function(msg){
        	$("#bg").css("display", "none");
			$("#show").css("display", "none");
        	total = $(msg).filter("#total").val();
        	$("#totalRestaurants").text(total+" restaurants near you");
            $("#showBanner").html(msg);
            initPaginator();
        }
    })
	
    $("a[href='#showSudoku']").click(function(){//九宫格点击事件
    	$("#bg").css("display", "block");
		$("#show").css("display", "block");
    	display = 2;
    	$("#pp").css('display','block');
    	$("#totalRestaurants").css('display','block');
    	//动态加载店铺信息
		$.ajax({
        	type:"post",
        	url:path+"/searchList/sudoku",     
        	data:$("#fm").serialize()+"&offset="+globalPage+"&order="+order,
       		success:function(msg){
       			$("#bg").css("display", "none");
				$("#show").css("display", "none");
				total = $(msg).filter("#total").val();
        		$("#totalRestaurants").text(total+" restaurants near you");
           		$("#restaurants").html(msg);
        	}
    	})

    });
    
    $("a[href='#showBanner']").click(function(){//列表点击事件
    	$("#bg").css("display", "block");
		$("#show").css("display", "block");
    	display = 1;
    	$("#pp").css('display','block');
    	$("#totalRestaurants").css('display','block');
    	//动态加载店铺信息
		$.ajax({
        	type:"post",
        	url:path+"/searchList/banner",     
        	data:$("#fm").serialize()+"&offset="+globalPage+"&order="+order,
       		success:function(msg){
       			$("#bg").css("display", "none");
				$("#show").css("display", "none");
				total = $(msg).filter("#total").val();
        		$("#totalRestaurants").text(total+" restaurants near you");
            	$("#showBanner").html(msg);
        	}
   		})

    });
    
    
    $("a[href='#map']").click(function(){//地图点击事件
    	$("#bg").css("display", "block");
		$("#show").css("display", "block");
    	display = 3;
    	$("#pp").css('display','none');
    	$("#totalRestaurants").css('display','none');
    	//动态加载店铺信息
		$.ajax({
        	type:"post",
        	url:path+"/searchList/map",     
        	data:$("#fm").serialize()+"&distance=1",
       		success:function(msg){
       			$("#bg").css("display", "none");
				$("#show").css("display", "none");
       			var vrs = $.parseJSON(msg);
       			var mapOptions = {
					zoom : 12,
					center : new google.maps.LatLng($("#restaurantLat").val(), $("#restaurantLng").val()),
					mapTypeId : google.maps.MapTypeId.ROADMAP
				};
				
				var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
				var mark = new google.maps.Marker({
      					position: new google.maps.LatLng($("#restaurantLat").val(), $("#restaurantLng").val()),
      					map: map,
      					icon: image,
      					title:"My Location"
  					});
				
       			$.each(vrs,function(index,item){
       				var photourl = item.logourl;
       				var str = '<span style="color: #aaaaaa">☆☆☆☆☆</span>';
       				var count=0;
       				var classification='';
       				if(!photourl){
       					photourl= path+"/images/no-picture.jpg";
       				}
       				if(item.score){
       					if(item.score<1.5){
       						str='<span style="color: #7FBF2E">★</span><span style="color: #aaaaaa">☆☆☆☆</span>';
       					}
       					else if(item.score<2.5){
       						str='<span style="color: #7FBF2E">★★</span><span style="color: #aaaaaa">☆☆☆</span>';
       					}
       					else if(item.score<3.5){
       						str='<span style="color: #7FBF2E">★★★</span><span style="color: #aaaaaa">☆☆</span>';
       					}
       					else if(item.score<4.5){
       						str='<span style="color: #7FBF2E">★★★★</span><span style="color: #aaaaaa">☆</span>';
       					}
       					else{
       						str='<span style="color: #7FBF2E">★★★★★</span>';
       					}
       				}
       				if(item.scoreCount){
       					count = item.scoreCount;
       				}
       				if(item.classificationName){
       					classification = item.classificationName;
       				}
       				var contentString = "<div id='content'>"
       					+"<div style='float:left;'>"
       						+"<img src="+photourl+" onerror=javascript:this.src='"+appPath+"/images/no-picture.jpg' width='100px'>"
       					+"</div>" 
       					+"<div style='float:right;margin-left:20px;'>" 
       						+"<h5>"+item.restaurantName+"</h5>"
       						+str
       						+"<span style='font-size: xx-small'> "
       							+count+" Ratings"
       						+"</span>"
       						+"<br/>"
       						+"<h7>"+classification+"</h7>"
       					+"</div>"
       				+"</div>";
       				var infowindow = new google.maps.InfoWindow({
									content : contentString,
									maxWidth:300
								});
       				var marker = new google.maps.Marker({
      					position: new google.maps.LatLng(item.restaurantLat,item.restaurantLng),
      					map: map
  					});
  					google.maps.event.addListener(marker, 'mouseover',
									function() {
										infowindow.open(map, marker);
									});
					google.maps.event.addListener(marker, 'mouseout',
									function() {
										infowindow.close();
									});
					google.maps.event.addListener(marker, 'click',
									function() {
										window.location = path+"/index/restaurantmenu?restaurantUuid="+item.restaurantUuid;
									});
       			})
       			
       			
        	}
   		})

    });
    
    $("#coupons").click(function(){//是否有折扣的点击事件
    	globalPage = 1;
    	if($(this).prop('checked')){//选中
    		$("#discountNum").val("1");
    	}else{
    		$("#discountNum").val("");
    	}
    	initRestaurant();
    });
    
    $("#open").click(function(){//当前营业时间的点击事件
    	globalPage = 1;
    	if($(this).prop('checked')){//选中
    		$("#opentime").val("1");
    	}else{
    		$("#opentime").val("");
    	}
    	initRestaurant();
    });
    
    $("#delivery").click(function(){//筛选是否有外卖服务的点击事件
    	globalPage = 1;
    	if($(this).prop('checked')){//选中
    		$("#isdelivery").val("1");
    	}else{
    		$("#isdelivery").val("");
    	}
    	initRestaurant();
    });
    
     $("#reservation").click(function(){//筛选是否有预定服务的点击事件
    	globalPage = 1;
    	if($(this).prop('checked')){//选中
    		$("#isreservation").val("1");
    	}else{
    		$("#isreservation").val("");
    	}
    	initRestaurant();
    });
    
    $("#orderbydistance").click(function(){//按距离排序
    	$("span[name='sort-method']").empty().append("Distance");
    	globalPage = 1;
    	order = 0;
    	initRestaurant();
    });
    
    $("#orderbya").click(function(){//按字母排序
    	$("span[name='sort-method']").empty().append("A - Z");
    	globalPage = 1;
    	order = 1;
    	initRestaurant();
    });
    
    $("#orderbyprice").click(function(){//按平均价格排序
    	$("span[name='sort-method']").empty().append("Price");
    	globalPage = 1;
    	order = 2;
    	initRestaurant();
    });
    
    $("#orderbyrating").click(function(){//按评分排序
    	$("span[name='sort-method']").empty().append("Rating");
    	globalPage = 1;
    	order = 3;
    	initRestaurant();
    });
    
    
    function initRestaurant(){
    	//显示等待遮罩层
    	$("#bg").css("display", "block");
		$("#show").css("display", "block");
    	var url;
		var selector;
		if(display==1){//列表
			url = path+"/searchList/banner";
			selector = $("#showBanner");
		}
		else if(display==2){//九宫格
			url = path+"/searchList/sudoku";
			selector = $("#restaurants");
		}
		else if(display==3){//地图显示
			$.ajax({
        	type:"post",
        	url:path+"/searchList/map",     
        	data:$("#fm").serialize()+"&distance=1",
       		success:function(msg){
       			
       			$("#bg").css("display", "none");
				$("#show").css("display", "none");
       			var vrs = $.parseJSON(msg);
       			var mapOptions = {
					zoom : 12,
					center : new google.maps.LatLng($("#restaurantLat").val(), $("#restaurantLng").val()),
					mapTypeId : google.maps.MapTypeId.ROADMAP
				};

				var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
				var mark = new google.maps.Marker({
      					position: new google.maps.LatLng($("#restaurantLat").val(), $("#restaurantLng").val()),
      					map: map,
      					icon: image,
      					title:"My Location"
  					});
				
       			$.each(vrs,function(index,item){
       				var photourl = item.logourl;
       				var str = '<span style="color: #cccccc">☆☆☆☆☆</span>';
       				var count=0;
       				var classification='';
       				if(!photourl){
       					photourl= path+"/index/images/can-img01.jpg";
       				}
       				if(item.score){
       					if(item.score<1.5){
       						str='<span style="color: #7FBF2E">★</span><span style="color: #cccccc">☆☆☆☆</span>';
       					}
       					else if(item.score<2.5){
       						str='<span style="color: #7FBF2E">★★</span><span style="color: #cccccc">☆☆☆</span>';
       					}
       					else if(item.score<3.5){
       						str='<span style="color: #7FBF2E">★★★</span><span style="color: #cccccc">☆☆</span>';
       					}
       					else if(item.score<4.5){
       						str='<span style="color: #7FBF2E">★★★★</span><span style="color: #cccccc">☆</span>';
       					}
       					else{
       						str='<span style="color: #7FBF2E">★★★★★</span>';
       					}
       				}
       				if(item.scoreCount){
       					count = item.scoreCount;
       				}
       				if(item.classificationName){
       					classification = item.classificationName;
       				}
       				var contentString = "<div id='content'><div style='float:left;'><img src="+photourl+" width='100px'></div>" +
       						"<div style='float:right;margin-left:20px;'><h5>"+item.restaurantName+"</h5>"+str+"<span style='font-size: xx-small'> "+count+" Ratings</span><br/>" +
       								"<h7>"+classification+"</h7></div></div>";
       				var infowindow = new google.maps.InfoWindow({
									content : contentString,
									maxWidth:300
								});
       				var marker = new google.maps.Marker({
      					position: new google.maps.LatLng(item.restaurantLat,item.restaurantLng),
      					map: map
  					});
  					google.maps.event.addListener(marker, 'mouseover',
									function() {
										infowindow.open(map, marker);
									});
					google.maps.event.addListener(marker, 'mouseout',
									function() {
										infowindow.close();
									});
					google.maps.event.addListener(marker, 'click',
									function() {
										window.location = path+"/index/restaurantmenu?restaurantUuid="+item.restaurantUuid+"&distance="+item.apart;
									});
       			})
        	}
   		})
   		return;
		}
		$.ajax({
        type:"post",
        url:url,     
        data:$("#fm").serialize()+"&offset="+globalPage+"&order="+order,
        success:function(msg){
        	$("#bg").css("display", "none");
			$("#show").css("display", "none");
        	total = $(msg).filter("#total").val();//获取子页面的餐厅总数
        	$("#totalRestaurants").text(total+" restaurants near you");
           	selector.html(msg);
           	initPaginator();
        }
    })
    }
    
    function initPaginator(){
    	var options = {
            totalPages: Math.ceil(total/9),
            bootstrapMajorVersion:3,
            size:'large',
            alignment:'center',
            onPageClicked: function(e,originalEvent,type,page){
            			globalPage = page;
            			initRestaurant();
					}
				}
				$('#paginator').bootstrapPaginator(options);
			}
    //切换显示下拉列表的See All /See Less
    var seeAll = true;
    $("a[href='#collapseExample']").click(function(){
    	if(seeAll){
    		seeAll = false;
    		$(this).find("span").eq(0).empty().text("See Less");
    	}else{
    		seeAll = true;
    		$(this).find("span").eq(0).empty().text("See All");
    	}
    })
    
	
});

