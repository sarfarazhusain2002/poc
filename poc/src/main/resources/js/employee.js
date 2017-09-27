	calculateDays = function(){
		 var start = $('#startDate').datepicker('getDate');
		    var end   = $('#endDate').datepicker('getDate');
		    if(!start || !end)
		        return;
		    var days = (end - start)/1000/60/60/24;
		    $('#duration').val(days+1);
	};
	
	$(document).ready(function() {
		
		$('#divShowApply').css('display','block');
		$('#blDivMain').css('display','block');		
		$('#divShowBalance').css('display','none');
		$('#divShowHistory').css('display','none');
		$('#divShowApproval').css('display','none');
		
		
		
		$("#startDate").datepicker({
			showOn : "button",
			buttonImage : "resources/calendar.gif",
			buttonImageOnly : true,
			buttonText : "Select date",
			//dateFormat: 'dd-mm-yy',
			onSelect: calculateDays
		});

		$("#endDate").datepicker({
			showOn : "button",
			buttonImage : "resources/calendar.gif",
			buttonImageOnly : true,
			buttonText : "Select date",
			//dateFormat: 'dd-mm-yy',
			onSelect: calculateDays
		});
		
		getEmployeeDetails = function(){
						
			var url = document.location.href,
	        params = url.split('?')[1].split('&'),
	        data = {}, tmp;
		    for (var i = 0, l = params.length; i < l; i++) {
		         tmp = params[i].split('=');
		         
		         		    }
			$.ajax({
		        type:"GET",
		        url : "rest/empDetails",
		        data : "empId="+tmp[1],
		        async: false,
		        success : function(response) {
					var data = response;
					$('#empName').html(data.name);					
					$('#superVisorId').html(data.superVisorId);
					$('#empId').html(data.empId);
					if(data.roleName == 'Supervisor') 
						$("#td_1").show();
					else
						$("#td_1").hide();	
		        },
		        error: function() {
		            alert('Error occured');
		        }
		    });
			
		};
		
		Approval = function(transaction_id,status){
			$.ajax({  
		        type: "GET",  
		        url: "rest/supervisor/"+transaction_id+"/"+status,  
		        dataType: "json",  
		        success: function(data){  
		        	if(data){
		                var len = data.length;
		                var txt = "";
		                var leavetxt = "";
		                var strApproved = "Approved";
		                var strRejected = "Rejected";                
		                 $('#LeaveDetails').html('');
		                 
		                 $('#empTable').html('');
		                
		                if(len > 0){
		                    for(var i=0;i<len;i++){
		                       
		                            txt += "<tr><td>"+data[i].name+"</td><td>"+data[i].email+"</td><td>"+data[i].phoneNo+"</td></tr>";
		                            for(var a=0;a<data[i].hcmLeaveTransactions.length;a++){
		                            	var p1 = "'"+data[i].hcmLeaveTransactions[a].transactionId+"','"+strApproved+"'";
		                            	var p2 = "'"+data[i].hcmLeaveTransactions[a].transactionId+"','"+strRejected+"'";
		                            	leavetxt += '<tr><td>'+data[i].hcmLeaveTransactions[a].remarks+'</td><td>'+data[i].hcmLeaveTransactions[a].starDate+'</td><td>'+data[i].hcmLeaveTransactions[a].endDate+'</td><td>'+data[i].hcmLeaveTransactions[a].status+'</td><td><a href="#" onClick ="Approval('+p1+')">Approved</a></td><td><a href="#" onClick="Approval('+p2+')">Reject</a></td></tr>';
		                            }
		                        
		                    }
		                    if(txt != ""){
		                    	var empTableHead = "<tr><th>Name</th><th>Email</th><th>Phone No</th></tr>";
		                    	$("#empTable").append(empTableHead).removeClass("hidden");
		                        $("#empTable").append(txt);
		                    }
		                    if(leavetxt != ""){
		                    	var heading = '<tr><th>Comments</th><th>Start Date</th><th>End Date</th><th>Status</th><th>Approved</th><th>Reject</th></tr>';
		                    	$("#LeaveDetails").append(heading).removeClass("hidden");
		                       $("#LeaveDetails").append(leavetxt);
		                    }
		                    
		                }
		            } 
		          
		        },  
		        error: function(e){  
		          alert('Error121212: ' + e);  
		        }  
		      });

		};
		
		var allReportees = [];
		getLeavePendingList = function(){
			$.ajax({  
		        type: "GET",  
		        url: "rest/supervisor/"+$('#superVisorId')[0].outerText,  
		        dataType: "json",  
		        success: function(data){  
		        	if(data){
		                var len = data.length;
		                var txt = "";
		                var empSelectList = "<select id='empSelectList' onChange='javascript:empSelectListClick()'>";
		                var leavetxt = "";
		                var strApproved = "Approved";
		                var strRejected = "Rejected";
		                $('#LeaveDetails').html('');
		                $('#empTable').html('');
		                 
		                
		                if(len > 0){
		                	allReportees = data;
		                	var firstEmp = data[0].empId;
		                	
		                    for(var i=0;i<len;i++){
		                    		empSelectList += "<option value='"+data[i].empId+"'>"+data[i].name+"</option>";
		                    }
		                    empSelectList += "</select>"; 
		                    $('#empSelectListDiv').html(empSelectList);
		                    showReporteesDetails(firstEmp, data);
		                }
		            } 
		          
		        },  
		        error: function(e){  
		          alert('Error121212: ' + e);  
		        }  
		      });
		}
		
		empSelectListClick = function(){
			
			$.ajax({  
		        type: "GET",  
		        url: "rest/supervisor/"+$('#superVisorId')[0].outerText,  
		        dataType: "json",  
		        success: function(data){  
		        	if(data){
		                var len = data.length;
		                
		                if(len > 0){
		                	var empId = $('#empSelectList').val();
		        			showReporteesDetails(empId, data);		                	
		                }
		            } 
		          
		        },  
		        error: function(e){  
		          alert('Error: ' + e);  
		        }  
		      });
			
			
			
		};
		
		showReporteesDetails = function(empId, data){
			var len = data.length;
			
            var txt = "";
            var leavetxt = "";
            var strApproved = "Approved";
            var strRejected = "Rejected";
            $('#LeaveDetails').html('');
            $('#empTable').html('');
            
			for(var i=0;i<len;i++){
        		if(data[i].empId == empId){
                    txt += "<tr><td>"+data[i].name+"</td><td>"+data[i].email+"</td><td>"+data[i].phoneNo+"</td></tr>";
                    for(var a=0;a<data[i].hcmLeaveTransactions.length;a++){
                    	var p1 = "'"+data[i].hcmLeaveTransactions[a].transactionId+"','"+strApproved+"'";
                    	var p2 = "'"+data[i].hcmLeaveTransactions[a].transactionId+"','"+strRejected+"'";
                    	leavetxt += '<tr><td>'+data[i].hcmLeaveTransactions[a].leaveType+'</td><td>'+data[i].hcmLeaveTransactions[a].remarks+'</td><td>'+data[i].hcmLeaveTransactions[a].starDate+'</td><td>'+data[i].hcmLeaveTransactions[a].endDate+'</td><td>'+data[i].hcmLeaveTransactions[a].status+'</td><td><a href="#" onClick ="Approval('+p1+')">Approved</a></td><td><a href="#" onClick="Approval('+p2+')">Reject</a></td></tr>';
                    }
        		}
            
	        }
	        if(txt != ""){
	        	var empTableHead = "<tr><th>Name</th><th>Email</th><th>Phone No</th></tr>";
	        	$("#empTable").append(empTableHead).removeClass("hidden");
	            $("#empTable").append(txt);
	        }
	        
	        
	        if(leavetxt != ""){
	        	var heading = '<tr><th>Leave Type</th><th>Remarks</th><th>Start Date</th><th>End Date</th><th>Status</th><th>Approved</th><th>Reject</th></tr>';
	        	$("#LeaveDetails").append(heading).removeClass("hidden");
	            $("#LeaveDetails").append(leavetxt);
	        }else{
	        	leavetxt = "No pending items found.";
	        	$("#LeaveDetails").append(leavetxt);
	        }
		};
		
		
		getLeaveTypes = function(){
			
			$.ajax({
		        type:"GET",
		        url : "rest/getLeaveTypes",
		        async: false,
		        success : function(response) {
					var data = response;
					$('#spanLeaveType').html('');
					var select = '<select id="leaveTypeId">';
					
					for(var i=0; i<data.length; i++){
						select += '<option value="'+data[i].id+'">'+data[i].type+'</option>';
					}
					select += '</select>';
					
					$('#spanLeaveType').append(select);	
					var empId = $('#empId')[0].outerText;
					getLeaveBalanceByLeaveType(2);
		        },
		        error: function() {
		            alert('Error occured');
		        }
		    });
			
		};
		
		getViewBalance = function(){
			
			$.ajax({
		        type:"GET",
		        url : "rest/viewBalanceLeaves",
		        data : "empId="+$('#empId')[0].outerText,
		        async: false,
		        success : function(response) {
					var data = response;
					$('#tblShowBalance').html('');
					$('#tblShowBalance').append('<tr><th>Leave Type</th><th>Leave Taken</th><th>Leave Balance</th></tr>');
					for(var i=0; i<data.length; i++){
						$('#tblShowBalance').append('<tr><td>'+data[i].leaveType+'</td><td>'+data[i].totalLeavesTaken+'</td><td>'+data[i].totalLeavesBalance+'</td></tr>');
					}
		        },
		        error: function() {
		            alert('Error occured');
		        }
		    });
			
		};
		
		getViewHistory = function(){
			
			$.ajax({
		        type:"GET",
		        url : "rest/viewHistory",
		        data : "empId="+$('#empId')[0].outerText,
		        async: false,
		        success : function(response) {
		        	var data = response;
		        	$('#tblShowHistory').html('');
		        	$('#tblShowHistory').append('<tr><th>Leave Type</th><th>Start Date</th><th>End Date</th><th>Status</th></tr>');
					for(var i=0; i<data.length; i++){
						$('#tblShowHistory').append('<tr><td>'+data[i].leaveType+'</td><td>'+data[i].startDate+'</td><td>'+data[i].endDate+'</td><td>'+data[i].status+'</td></tr>');
					}
		        			            
		        },
		        error: function() {
		            alert('Error occured');
		        }
		    });
			
		};
		
		getLeaveBalanceByLeaveType = function(empId){
			
			$.ajax({
		        type:"GET",
		        url : "rest/getLeaveBalanceByLeaveType",
		        data : "empId="+empId+ "&leaveType="+$('#leaveTypeId').val(),
		        async: false,
		        success : function(response) {
		        	$('#blDiv').html('');
		        	$('#blDiv').css('display', 'inline');
					$('#blDiv').html(response);		            
		        },
		        error: function() {
		            alert('Error occured');
		        }
		    });
			
		};
		
		getEmployeeDetails();
		getLeaveTypes();
		
		$('#leaveTypeId').change(function() {
			var empId = $('#empId')[0].outerText;
			getLeaveBalanceByLeaveType(empId);
		});
		
		
		$("#submit").click(function(){
			
			$('#msg').css('display', 'none');
			var url =  "rest/applyLeaves";
		    $.post(url,
		    {
		    	leaveTypeId: $('#leaveTypeId').val(),
		    	startDate: $('#startDate').val(),
		    	endDate: $('#endDate').val(),
		    	duration: $('#duration').val(),
		    	comments: $('#comments').val(),
		    	empId: $('#empId')[0].outerText,
		    	superVisorId: $('#superVisorId')[0].outerText
		    	
		    	
		    },
		    function(data, status){
		        $('#msg').html(data);
		        $('#msg').css('display', 'block');
		        $('#blDiv').css('display', 'none');
		        $('#blDivMain').css('display','none');
		        $('#divShowApply').css('display', 'none');
		    });
		});
		
		$('ul li a').click(function() {
		    $('ul li.active').removeClass('active');
		    $(this).closest('li').addClass('active');
		});
		
		showApplyLeaves = function(){
			$('ul li').addClass('active');
			$('#msg').html('');
	        $('#msg').css('display', 'none');
			$('#divShowApply').css('display','block');
			$('#blDivMain').css('display','block');			
			$('#divShowBalance').css('display','none');
			$('#divShowHistory').css('display','none');
			$('#divShowApproval').css('display','none');
			
			var empId = $('#empId')[0].outerText;
			getLeaveBalanceByLeaveType(empId);
		}
		
		showViewBalance = function(){
			$('#msg').html('');
	        $('#msg').css('display', 'none');
			$('#divShowApply').css('display','none');
			$('#blDivMain').css('display','none');
			$('#divShowBalance').css('display','block');
			$('#divShowHistory').css('display','none');
			$('#divShowApproval').css('display','none');
			getViewBalance();
		}
		
		showHistory = function(){
			$('#msg').html('');
	        $('#msg').css('display', 'none');
			$('#divShowApply').css('display','none');
			$('#blDivMain').css('display','none');
			$('#divShowBalance').css('display','none');
			$('#divShowHistory').css('display','block');
			$('#divShowApproval').css('display','none');
			getViewHistory();
		}
		
		showApproval = function(){
			$('#msg').html('');
	        $('#msg').css('display', 'none');
			$('#divShowApply').css('display','none');
			$('#blDivMain').css('display','none');
			$('#divShowBalance').css('display','none');
			$('#divShowHistory').css('display','none');
			$('#divShowApproval').css('display','block');
			getLeavePendingList();
		}
	});