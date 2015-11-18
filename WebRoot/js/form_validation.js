

function validateEmpty(obj) {
	if ($.trim(obj.val()).length <= 0) {
		//alert("false");
		obj.popover('destroy');
		obj.popover({
					content :"Please fill in this field!"
				});
		obj.popover('show');
		return false;
	} else {
		//alert("true");
		return true;
	}
}

function validateMaxLength(obj, max) {
	if(obj.val().length>max){
		obj.popover('destroy');
		obj.popover({
					content :"The length is not greater than "+max+"!"
				});
		obj.popover('show');
		return false;
	}
	else{
		return true;
	}
}

function validateMinLength(obj, min) {
	if(obj.val().length<min){
		obj.popover('destroy');
		obj.popover({
					content :"The length is not less than "+min+"!"
				});
		obj.popover('show');
		return false;
	}
	else{
		return true;
	}
}

function validateEmail(obj) {
	if (!/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/
			.test(obj.val())) {
		obj.popover('destroy');
		obj.popover({
					content :"Please enter the correct email!"	
				});
		obj.popover('show');
		return false;
	} else {
		return true;
	}
}

function validateDeceimal(obj) {
	if (!/^(?:(?!0)\d*|0|\s*)(?:\.\d+)?$/.test(obj.val())) {
		obj.popover('destroy');
		obj.popover({
					content :"Please enter the correct number!"
				});
		obj.popover('show');
		return false;
	} else {
		return true;
	}
}

function validateNumber(obj){
	if(!/^\+?\d*$/.test(obj.val())){
		obj.popover('destroy');
		obj.popover({
					content :"Please enter the positive integer!"
				});
		obj.popover('show');
		return false;
	}
	else{
		return true;
	}
}



function validateErrorMsg(obj, msg) {
	obj.popover('destroy');
	obj.popover({
				content : msg
			});
	obj.popover('show');
}

