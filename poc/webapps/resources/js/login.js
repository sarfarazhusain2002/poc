$(document).ready(function() {
	
	
	$("#submitLogin").click(function(){
		
		var empId = $('#username').val();
		var url =  "rest/login";
	    $.post(url,
	    {
	    	username: $('#username').val(),
	    	password: $('#password').val()	    	
	    },
	    function(data, status){
	        if(data == "Success"){
	        	var url = 'leaveManagement.html?empId=' + encodeURIComponent(empId);
				$(location).attr('href', url);
	        }else{
	        	$('#errMsg').css('display', 'block');
	        	$('#errMsg').html(data);
	        }
	    });
	});
	
	$("#resetBtn").click(function(){
		$('#errMsg').css('display', 'none');
		$('#username').val('');
		$('#password').val('');
	});
	
});