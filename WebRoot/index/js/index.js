/*(function removeCookieLatLng(){
	$.cookie("map-Lat", "",{expires:10, path: '/' }); 
	$.cookie("map-Lng", "",{expires:10, path: '/' }); 
})()*/
/*google map begin*/
var placeSearch, autocomplete;
var Lat,Lng;
var browserLat,browserLng;

var options = {
	//如果知道精确到城市级别就将下面一行取消注释
	//types: ['(cities)'],
	//定义搜索的最大范围为加拿大境内
	componentRestrictions: {country: 'ca'}
};


function initialize() {
 // Create the autocomplete object, restricting the search
 // to geographical location types.
	var url = location.href;
	if(url.indexOf("false")>0){
		$("#autocomplete").css("border","1px solid red");
	}
	/**浏览器定位获取经纬度*/
	(function geolocate(){//用于获取浏览器当前访问附近服务器的经纬度
		$.cookie("map-Lat", "",{expires:10, path: '/' }); 
		$.cookie("map-Lng", "",{expires:10, path: '/' }); 
		if (navigator.geolocation) {
		    navigator.geolocation.getCurrentPosition(function(position) {
		    	var geolocation = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
		    	browserLat = position.coords.latitude;
		    	browserLng = position.coords.longitude;
		    	$.cookie("browser-Lat", browserLat,{expires:10, path: '/' });  
		    	$.cookie("browser-Lng", browserLng,{expires:10, path: '/' });  
		    });
		}
	})()
	
	
	//geolocate();//调用浏览器定位的方法
	autocomplete = new google.maps.places.Autocomplete(
		/** @type {HTMLInputElement} */
		(document.getElementById('autocomplete')),options
	);
 // When the user selects an address from the dropdown,
 // populate the address fields in the form.
 google.maps.event.addListener(autocomplete, 'place_changed', function() {
   fillInAddress();
 });
}
/**获取输入框输入的地址的详细信息*/
function fillInAddress() {
	// Get the place details from the autocomplete object.
	var place = autocomplete.getPlace();//place里面包含了经纬度，省市区名称，邮编等信息
	Lat = place.geometry.location.lat();//纬度A
	Lng = place.geometry.location.lng();//经度F
	//console.log(Lat+"--"+Lng);
	var placeArr = place.address_components;
	placeSearch = place.formatted_address;
	//console.log(placeArr[0].long_name+" "+placeArr[1].long_name +" "+placeArr[3].long_name+" "+placeArr[6].long_name);
	//$.cookie("map-Lat", Lat,{expires:10,path: '/' });  //设置纬度到cookie
	//$.cookie("map-Lng", Lng,{expires:10, path: '/' });  //设置经度到cookie
	//$.cookie("map-street", placeArr[0].long_name+" "+placeArr[1].long_name,{expires:10,path: '/' });
	//$.cookie("map-city", placeArr[3].long_name,{expires:10,path: '/' });
	//$.cookie("map-province", placeArr[6].long_name,{expires:10,path: '/' });
	//$.cookie("map-country", placeArr[7].long_name,{expires:10,path: '/' }); 
	//$.cookie("map-zip", placeArr[8].long_name,{expires:10,path: '/' }); 
	
	
	
	
}

/*google map end*/

$(function(){
	 $('input, textarea').placeholder();
	
	$("#autocomplete").focus(function(){
		$(this).css("border","0px");
	})
	
	// 搜索按钮
	$("button[name='searchBtn']").click(function(){
		//设置搜索的关键字，用于后面的searchList页面，初始化选中的关键字
		$.cookie("esarchKeywords", $.trim($("#xc-biaodan").val()),{expires:10, path: '/' });
		checkLatLng();
		var classificationName = $("#xc-biaodan").val();
		//如果没有获取到经纬度
		if(!Lat && !Lng){
			$("input[name='classificationName']").val($("#xc-biaodan").val());
			$("input[name='restaurantLat']").val(browserLat);
			$("input[name='restaurantLng']").val(browserLng);
			$("#search-restaurants-form").submit();
		}else if(!Lat && !Lng && !browserLat && !browserLng){//如果没有得到经纬度要求用户输入地址信息，调用googleAPI获得经纬度
			$("#autocomplete").css("border","1px solid red");
		}else{
			$("input[name='classificationName']").val(classificationName);
			$("input[name='restaurantLat']").val(Lat);
			$("input[name='restaurantLng']").val(Lng);
			$("#search-restaurants-form").submit();
		}
	});
	
	$("a[title='foodClassification']").click(function(){//5个食物按钮
		//设置搜索的关键字，用于后面的searchList页面，初始化选中的关键字
		//var esarchKeywords = $.trim($(this).attr("name"));
		$.cookie("esarchKeywords", $.trim($(this).attr("name")),{expires:10, path: '/' });
		checkLatLng();
		var classificationName = $(this).attr("name");
		//console.log($.cookie("map-Lat")+"  ||| "+$.cookie("map-Lng"))
		if(!Lat && !Lng){
			$("input[name='classificationName']").val(classificationName);
			$("input[name='restaurantLat']").val(browserLat);
			$("input[name='restaurantLng']").val(browserLng);
			$("#search-restaurants-form").submit();
		}else if(!Lat && !Lng && !browserLat && !browserLng){//如果没有得到经纬度要求用户输入地址信息，调用googleAPI获得经纬度
			$("#autocomplete").css("border","1px solid red");
		}else{
			$("input[name='classificationName']").val(classificationName);
			$("input[name='restaurantLat']").val(Lat);
			$("input[name='restaurantLng']").val(Lng);
			$("#search-restaurants-form").submit();
		}
	});
	
	//页面中下部登录按钮
	$("#index-singin").click(function(){
		$("#myModal").modal('show');
	});

	//Contact Us
	$("#index-contactus").click(function(){
		$("#myContactUs").modal('show');
	});
	
	//About 
	$("#index-about").click(function(){
		$("#myAbout").modal('show');
	});
	
	//FAQ 
	$("#index-faq").click(function(){
		$("#myFaq").modal('show');
	});	

	//TERMS OF USE 
	$("#index-termofuse").click(function(){
		$("#myTermOfUse").modal('show');
	});		
	
	//Privacy Policy
	$("#index-privacypolicy").click(function(){
		$("#myPrivacyPolicy").modal('show');
	});
		
	
	function checkLatLng(){
		//如果没有填写地址信息，直接将浏览器获取到的经纬度用于商家查找
		if(placeSearch && placeSearch.length>0){//如果有地址文字信息
			if(Lat && Lng && Lat.length>0 && Lng.length>0){
				$.cookie("map-Lat", Lat,{expires:10, path: '/' });
				$.cookie("map-Lng", Lng,{expires:10, path: '/' });
			}
		}else{
			//console.log("browserLat:"+browserLat+"  browserLng:"+browserLng)
			if(browserLat && browserLng){
				$.cookie("map-Lat", browserLat,{expires:10, path: '/' });
				$.cookie("map-Lng", browserLng,{expires:10, path: '/' });
			}
		}
		
	}

})
