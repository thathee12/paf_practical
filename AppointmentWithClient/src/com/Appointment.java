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

	public String insertAppointment(String placedDate, String appointDate,String doctorID, String patientID,String cause) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "ERROR while connecting to the database for inserting!";
			}

			// create a prepared statement
			String query = " insert into appointments( `aID`,`placedDate`,`appointDate`,`doctorID`,`patientID`,`cause`)"
					+ " values (?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, placedDate);
			preparedStmt.setString(3, appointDate);
			preparedStmt.setInt(4, Integer.parseInt(doctorID));
			preparedStmt.setInt(5, Integer.parseInt(patientID));
			preparedStmt.setString(6, cause);
			
			// execute the statement
			preparedStmt.execute();
			con.close();

			String newAppoint = readAppointment();   
			output = "{\"status\":\"success\", \"data\": \"" +      
			newAppoint + "\"}"; 

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":"
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
			output = "<table border= '1'><tr><th>&nbsp;Placed &nbsp;Date&nbsp;</th>"
					+"<th>&nbsp;Appointment &nbsp;Date&nbsp;</th><th>&nbsp;Doctor &nbsp;ID&nbsp;</th>"
							+ "<th>&nbsp;Patient &nbsp;ID&nbsp;</th><th>&nbsp;Cause&nbsp;</th>"
					+ "<th>&nbsp;Update&nbsp;</th><th>&nbsp;Remove&nbsp;</th></tr>";

			String query = "select * from appointments";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String aID = Integer.toString(rs.getInt("aID"));
				String placedDate = rs.getString("placedDate");
				String appointDate = rs.getString("appointDate");
				String doctorID = Integer.toString(rs.getInt("doctorID"));
				String patientID = Integer.toString(rs.getInt("patientID"));
				String cause = rs.getString("cause");

				// Add into the html table
				output += "<tr><td><input id='hidAppointIDUpdate' name ='hidAppointIDUpdate' type='hidden' value='"+ aID + "'>" + placedDate + "</td>";
				output += "<td>" + appointDate + "</td>";
				output += "<td>" + doctorID + "</td>";
				output += "<td>" + patientID + "</td>";
				
				output += "<td>" + cause + "</td>";

				// buttons
				output += "<td><input name='btnUpdate' type='button'"
						+ "value='Update'"
						+ "class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button'"
						+ "value='Remove'"
						+ "class='btnRemove btn btn-danger' data-appointmentid='"
						+ aID + "'>" + "</td></tr>";
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

	public String updateAppointment(String ID, String placedDate, String appointDate,String doctorID, String patientID,String cause) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating";
			}

			// create a prepared statement
			String query = "UPDATE appointments SET "
					+ "placedDate=?,appointDate=?,doctorID=?,patientID=?,cause=? WHERE aID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, placedDate);
			preparedStmt.setString(2, appointDate);
			preparedStmt.setInt(3, Integer.parseInt(doctorID));
			preparedStmt.setInt(4, Integer.parseInt(patientID));
			preparedStmt.setString(5, cause);	
			preparedStmt.setInt(6, Integer.parseInt(ID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newAppoint = readAppointment();    
			output = "{\"status\":\"success\", \"data\": \"" +       
					newAppoint + "\"}";   
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":         "
					+ "\"Error while updating the Appointment.\"}"; 
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteAppointment(String aID) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from appointments where aID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(aID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newAppoint = readAppointment();    
			output = "{\"status\":\"success\", \"data\": \"" +       
					newAppoint + "\"}";  

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":         "
					+ "\"Error while deleting the Appointment.\"}";
			System.err.println(e.getMessage());
		}

		return output;
		//updated
	}

}