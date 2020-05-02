<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Appointment Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/appointment.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Appointment Management</h1>
				
				<script>
				$(function () {
					  $("#placedDate").datepicker();
					  $("#appointDate").datepicker();
					});
</script>

				<form id="formAppoint" name="formAppoint">
					Placed Date: <input id="placedDate" name="placedDate" type="text"
						class="form-control form-control-sm"> 
						<br> 
					Appointment Date: <input id="appointDate" name="appointDate" type="text"
						class="form-control form-control-sm"> 
						<br> 
					Doctor ID: <input id="doctorID" name="doctorID" type="text"
						class="form-control form-control-sm"> 
						<br> 
					Patient ID: <input id="patientID" name="patientID" type="text"
						class="form-control form-control-sm"> 
						<br> 
					Cause: <input id="cause" name="casuse" type="text"
						class="form-control form-control-sm"> 
						<br> 
						<input id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidAppointIDSave" name="hidAppointIDSave" value="">
				</form>
</body>
</html>