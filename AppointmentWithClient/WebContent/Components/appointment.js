$(document).ready(function() {
	$("#alertSuccess").hide();
	$("#alertError").hide();
});

// SAVE 
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateAppointForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	// If valid
	var type = ($("#hidAppointIDSave").val() == "") ? "POST" : "PUT";

	$.ajax(
{
		url : "AppointmentAPI",
		type : type,
		data : $("#formAppoint").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onAppointSaveComplete(response.responseText, status);
		}
	});
});

//DELETE
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "AppointmentAPI",
		type : "DELETE",
		data : "aID=" + $(this).data("appointmentid"),
		dataType : "text",
		complete : function(response, status) {
			onAppointDeleteComplete(response.responseText, status);
		}
	});
});

$(document)
		.on(
				"click",
				".btnUpdate",
				function(event) {
					$("#hidAppointIDSave").val(
							$(this).closest("tr").find('#hidAppointIDUpdate')
									.val());
					$("#placedDate").val(
							$(this).closest("tr").find('td:eq(0)').text());
					$("#appointDate").val(
							$(this).closest("tr").find('td:eq(1)').text());
					$("#doctorID").val(
							$(this).closest("tr").find('td:eq(2)').text());
					$("#patientID").val(
							$(this).closest("tr").find('td:eq(3)').text());
					$("#cause").val(
							$(this).closest("tr").find('td:eq(4)').text());

				});

function onAppointSaveComplete(response, status) {

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

function onAppointDeleteComplete(response, status) {

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
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}

}

// Validate the form
function validateAppointForm() {
	// CODE
	if ($("#placedDate").val().trim() == "") {
		return "Insert placed date";
	}
	if ($("#appointDate").val().trim() == "") {
		return "Insert placed appointment date";
	}
	if ($("#doctorID").val().trim() == "") {
		return "Insert placed doctor ID";
	}
	var tmpDocId = $("#doctorID").val().trim();
	if (!$.isNumeric(tmpDocId)) {
		return "Insert a valid numerical value for doctor ID.";
	}

	if ($("#patientID").val().trim() == "") {
		return "Insert placed patient ID";
	}
	var tmpPid = $("#patientID").val().trim();
	if (!$.isNumeric(tmpPid)) {
		return "Insert a valid numerical value for patient ID.";
	}
	if ($("#cause").val().trim() == "") {
		return "Insert the cause of appointment";
	}

	return true;
}