$(document).ready(function()
{
	$("#alertSuccess").hide();
	$("#alertError").hide();
});

//SAVE ============================================ 
$(document).on("click", "#btnSave", function(event)
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 
	
	// Form validation-------------------  
	var status = validateItemForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 
	
	//If valid
	var type = ($("#hidAppointIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "AppointmentAPI",
		type : type,
		data : $("#formAppoint").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onItemSaveComplete(response.responseText, status);
		}
	});
});

function validateItemForm() {  
	// CODE  
	if ($("#placedDate").val().trim() == "")  
	{   
		return "Insert placed date"; 
	} 
	return true;
}

$(document).on("click", ".btnRemove", function(event) 
{  $.ajax(  {  
	url : "AppointmentAPI",   
	type : "DELETE",   
	data : "aID=" + $(this).data("appointmentid"),   
	dataType : "text",   complete : function(response, status)   
	{    
		onItemDeleteComplete(response.responseText, status);   
		}  
	}); 
}); 

$(document).on("click", ".btnUpdate", function(event) {     
	$("#hidAppointIDSave").val($(this).closest("tr").find('#hidAppointUpdate').val());    
	$("#placedDate").val($(this).closest("tr").find('td:eq(0)').text());    
	$("#appointDate").val($(this).closest("tr").find('td:eq(1)').text());    
	$("#doctorID").val($(this).closest("tr").find('td:eq(2)').text());    
	$("#patientID").val($(this).closest("tr").find('td:eq(3)').text()); 
	$("#cause").val($(this).closest("tr").find('td:eq(4)').text());
}); 


function onItemSaveComplete(response, status) {

	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();

			$("#divAppointmentGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidAppointIDSave").val("");
	$("#formAppoint")[0].reset();
}

function onItemDeleteComplete(response, status) {

	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();

			$("#divAppointmentGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while delete..");
		$("#alertError").show();
	}

}


