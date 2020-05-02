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
					con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf_ca", "root", "1Thathi/");
				} catch (Exception e) {
					e.printStackTrace();
				}

				return con;
			}
			
	public String insertAppointment( String placed_date , String appoint_date, String cause, String patientID, String doctorID, String day) {
				
				String output = "";

				try {
					Connection con = connect();

					if (con == null) {
						return "ERROR while connecting to the database for inserting!";
					}

					// create a prepared statement
					String query = " insert into appointment(`appointmentID`,`pADate`,`aDate`,`aCause`,`aPatient`,`aDoctor`,`aDay`)"
							+ " values (?, ?, ?, ?, ?, ?,?)";

					PreparedStatement preparedStmt = con.prepareStatement(query);
					
					//Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(placed_date); 
					
					
				
					 
					// binding values
					preparedStmt.setInt(1, 0);
					preparedStmt.setString(2,placed_date); 
					
					//preparedStmt.setDate(2, java.sql.Date(date1.getTime()); 
					
					preparedStmt.setString(3,appoint_date); 
					preparedStmt.setString(4, cause);
					preparedStmt.setInt(5, Integer.parseInt(patientID));
					preparedStmt.setString(6,doctorID);
					preparedStmt.setString(7, day);
					
					

					// execute the statement 
					preparedStmt.execute(); con.close();

					output = "Inserted successfully";
					
				} catch (Exception e) {
					output = "ERROR while inserting the Appointment!";
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
					+ "<th>Appointment Date</th><th>Cause</th><th>Patient ID</th><th>Doctor ID</th><th>Day</th>" + "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from appointment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String appointmentID = Integer.toString(rs.getInt("appointmentID"));
				String pADate = rs.getString("pADate");
				String aDate = rs.getString("aDate");
				String aCause = rs.getString("aCause");
				String aPatient = Integer.toString(rs.getInt("aPatient"));
				String aDoctor = rs.getString("aDoctor");
				String aDay = rs.getString("aDay");

				// Add into the html table
				output += "<tr><td>" + appointmentID + "</td>";
				output += "<td>" + pADate + "</td>";
				output += "<td>" + aDate + "</td>";
				output += "<td>" + aCause + "</td>";
				output += "<td>" + aPatient + "</td>";
				output += "<td>" + aDoctor + "</td>";
				output += "<td>" + aDay + "</td>";


				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\"value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"appointment.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"class=\"btn btn-danger\">"
						+ "<input name=\"appointmentID\" type=\"hidden\" value=\"" + appointmentID + "\">" + "</form></td></tr>";
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

	public String updateAppointment(String ID, String placed_date, String appoint_date, String cause, String patientID, String doctorID, String day) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating";
			}

			// create a prepared statement
			String query = "UPDATE appointment SET pADate=?,aDate=?,aCause=?,aPatient=?,aDoctor=?,aDay=?" + "WHERE appointmentID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
		
			preparedStmt.setString(1,placed_date); 
			
			//preparedStmt.setDate(2, java.sql.Date(date1.getTime()); 
			
			preparedStmt.setString(2,appoint_date); 
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
		} 
		catch (Exception e) 
		{
			output = "Error while deleting the item.";
			System.err.println(e.getMessage());
		}

		return output;
	}

			
	

}