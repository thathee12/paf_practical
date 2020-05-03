package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Appointment {

	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/practical", "root", "1Thathi/");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	public String insertAppointment(String placed_date, String appoint_date, String cause, String patientID,
			String doctorID, String day) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "ERROR while connecting to the database for inserting!";
			}

			// create a prepared statement
			String query = " insert into appointments(`appointmentID`,`pADate`,`aDate`,`aCause`,`aPatient`,`aDoctor`)"
					+ " values (?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, placed_date);

			// preparedStmt.setDate(2, java.sql.Date(date1.getTime());

			preparedStmt.setString(3, appoint_date);
			preparedStmt.setInt(4, Integer.parseInt(doctorID));
			preparedStmt.setInt(5, Integer.parseInt(patientID));
			preparedStmt.setString(6, cause);
			
			// execute the statement
			preparedStmt.execute();
			con.close();

			String newAppoint = readAppointment();   
			output = "{\"status\":\"success\", \"data\": \"" +      
			newAppoint+ "\"}"; 

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":         "
					+ "\"Error while inserting the appointment.\"}"; 
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String readAppointment() {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for reading!";
			}

			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Appointment ID</th><th>Placed Date</th>"
					+ "<th>Appointment Date</th><th>Patient ID</th><th>Doctor ID</th><th>Cause</th>"
					+ "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from appointments";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String appointmentID = Integer.toString(rs.getInt("appointmentID"));
				String pADate = rs.getString("placedDate");
				String aDate = rs.getString("appointDate");
				String aPatient = Integer.toString(rs.getInt("doctorID"));
				String aDoctor = Integer.toString(rs.getInt("patientID"));
				String aCause = rs.getString("casuse");

				// Add into the html table
				output += "<tr><td><input id='hidAppointIDUpdate'" + " name ='hidAppointIDUpdate' type='hidden' "
						+ "value='" + appointmentID + "'>";
				output += "<td>" + pADate + "</td>";
				output += "<td>" + aDate + "</td>";
				output += "<td>" + aPatient + "</td>";
				output += "<td>" + aDoctor + "</td>";
				output += "<td>" + aCause + "</td>";

				// buttons
				output += "<td><input name='btnUpdate' type='button'       "
						+ "value='Update'           "
						+ "class='btnUpdate btn btn-secondary'></td>      "
						+ "<td><input name='btnRemove' type='button'       "
						+ "value='Remove'           "
						+ "class='btnRemove btn btn-danger' data-appointmentid='"
						+ appointmentID + "'>" + "</td></tr>";
			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the appointments!";
			System.err.println(e.getMessage());
		}

		return output;
	}

	/*public String updateAppointment(String ID, String placed_date, String appoint_date, String cause, String patientID,
			String doctorID, String day) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating";
			}

			// create a prepared statement
			String query = "UPDATE appointment SET pADate=?,aDate=?,aCause=?,aPatient=?,aDoctor=?,aDay=?"
					+ "WHERE appointmentID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, placed_date);

			// preparedStmt.setDate(2, java.sql.Date(date1.getTime());

			preparedStmt.setString(2, appoint_date);
			preparedStmt.setString(3, cause);
			preparedStmt.setInt(4, Integer.parseInt(patientID));
			preparedStmt.setString(5, doctorID);
			preparedStmt.setString(6, day);
			preparedStmt.setInt(7, Integer.parseInt(ID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the appointment.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteAppointment(String ID) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from appointment where appointmentID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(ID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the item.";
			System.err.println(e.getMessage());
		}

		return output;
	}*/

}