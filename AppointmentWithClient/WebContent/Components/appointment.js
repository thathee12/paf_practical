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
		$("#alertError").show();   return;  
	} 
	
	//If valid
	var type = ($("#hidAppointmentIDSave").val() == "") ? "POST" : "PUT";

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

	$("#hidAppointmentIDSave").val("");
	$("#formAppoint")[0].reset();
}

var resultSet = JSON.parse(response);

if (resultSet.status.trim() == "success") {
	$("#alertSuccess").text("Successfully saved.");
	$("#alertSuccess").show();

	$("#divAppointmentGrid").html(resultSet.data);
} else if (resultSet.status.trim() == "error") {
	$("#alertError").text(resultSet.data);
	$("#alertError").show();
} else if (status == "error") {
	$("#alertError").text("Error while saving.");
	$("#alertError").show();
} else {
	$("#alertError").text("Unknown error while saving..");
	$("#alertError").show();
}

$("#hidAppointmentIDSave").val("");
$("#formAppoint")[0].reset();

