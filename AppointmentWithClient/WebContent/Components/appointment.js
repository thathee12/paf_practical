/**
 * 
 */

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