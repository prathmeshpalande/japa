package com.mini.japa.db.operators;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class UserOperator {

	public int registerUser(String name, String aadhaar, String email, String password, String phone, String dob) {

		int responseCode = 0;

		Connection conn = null;
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:mysql://upay.cuadni5olhbe.us-east-2.rds.amazonaws.com:3306/upaydb", "upay", "rusprapalosw$");
			System.out.println("Connection to SQLite has been established.");

			String query = "insert into users values (?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setString(1, name);
			preparedStmt.setString(2, aadhaar);
			preparedStmt.setString(3, email);
			preparedStmt.setString(4, password);
			preparedStmt.setString(5, phone);
			preparedStmt.setString(6, dob);
			preparedStmt.setDouble(7, 100.0);

			preparedStmt.execute();

			preparedStmt.close();

			responseCode = 1;

		} catch (SQLException e) {
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println(e.getMessage());
			responseCode = e.getErrorCode();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println("Error Code: " + ex.getErrorCode());
				System.out.println(ex.getMessage());
				responseCode = ex.getErrorCode();
			}
		}

		return responseCode;

	}

	public int authUser(String phone, String password) {

		int responseCode = 0;

		Connection conn = null;
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:mysql://upay.cuadni5olhbe.us-east-2.rds.amazonaws.com:3306/upaydb", "upay", "rusprapalosw$");

			System.out.println("Connection to SQLite has been established.");

			String query = "select phone, password from users where phone = ?";

			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setString(1, phone);

			ResultSet rs = preparedStmt.executeQuery();

			rs.next();

			String dbPass = rs.getString(2);

			if (dbPass.equals(password))
				responseCode = 1;
			else
				responseCode = -1;

			rs.close();
			preparedStmt.close();

		} catch (SQLException e) {
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println(e.getMessage());
			responseCode = 0;
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println("Error Code: " + ex.getErrorCode());
				System.out.println(ex.getMessage());
				responseCode = 0;
			}
		}

		return responseCode;

	}
	
	public Object validateSignup(String phone) {
		
		Object response = 0;
		
		Connection conn = null;
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:mysql://upay.cuadni5olhbe.us-east-2.rds.amazonaws.com:3306/upaydb", "upay", "rusprapalosw$");

			System.out.println("Connection to SQLite has been established.");

			String query = "select phone from users where phone = ?";

			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setString(1, phone);

			ResultSet rs = preparedStmt.executeQuery();
			
			response= (rs.next()) ? false : true;
			
			preparedStmt.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println(e.getMessage());
			response = e.getErrorCode();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println("Error Code: " + ex.getErrorCode());
				System.out.println(ex.getMessage());
				response = ex.getErrorCode();
			}
		}

		return response;
		
	}
	
	public Map<String, Object> getProfile(String phone) {
		
		int responseCode = 0;
		String responseMessage = "Some error occured! Please check the tomcat log for more details.";
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> responseData = new HashMap<String, Object>();
		
		Connection conn = null;
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:mysql://upay.cuadni5olhbe.us-east-2.rds.amazonaws.com:3306/upaydb", "upay", "rusprapalosw$");

			System.out.println("Connection to SQLite has been established.");
			
			String querySelectProfile = "select u_name, aadhaar, email, phone, dob, balance from users where phone = ?";
			
			PreparedStatement preparedStatementSelectProfile = conn.prepareStatement(querySelectProfile);
			preparedStatementSelectProfile.setString(1, phone);
			
			ResultSet resultSetSelectProfile = preparedStatementSelectProfile.executeQuery();
			
			if(resultSetSelectProfile.next()) {
				
				String userName = resultSetSelectProfile.getString(1);
				String aadhaar = resultSetSelectProfile.getString(2);
				String email = resultSetSelectProfile.getString(3);
				String dbPhone = resultSetSelectProfile.getString(4);
				String dob = resultSetSelectProfile.getString(5);
				double balance = resultSetSelectProfile.getDouble(6);
				
				responseCode = 1;
				responseData.put("u_name", userName);
				responseData.put("aadhaar", aadhaar);
				responseData.put("email", email);
				responseData.put("phone", dbPhone);
				responseData.put("dob", dob);
				responseData.put("balance", balance);
				
				response.put("response_code", responseCode);
				response.put("response_data", responseData);
			}
			
		} catch (SQLException e) {
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println("Error Code: " + ex.getErrorCode());
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			}
		}
		
		if(responseCode == 0) {
			response.put("response_code", responseCode);
			responseData.put("response_message", responseMessage);
			response.put("response_data", responseData);
		}
		
		return response;
		
	}

	public Map<String, Object> resetPassword(String phone, String oldPassword, String newPassword) {
		
		int responseCode = 0;
		String responseMessage = "Some error occured! Please check the tomcat log for more details.";
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> responseData = new HashMap<String, Object>();
		
		Connection conn = null;
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:mysql://upay.cuadni5olhbe.us-east-2.rds.amazonaws.com:3306/upaydb", "upay", "rusprapalosw$");

			System.out.println("Connection to SQLite has been established.");
			
			String querySelectPassword = "select password from users where phone = ?";
			
			PreparedStatement preparedStatementSelectPassword = conn.prepareStatement(querySelectPassword);
			preparedStatementSelectPassword.setString(1, phone);
			
			ResultSet resultSetSelectQuery = preparedStatementSelectPassword.executeQuery();
			
			if(resultSetSelectQuery.next() ) {
				
				String dbPassword = resultSetSelectQuery.getString(1);
				
				if(oldPassword.equals(dbPassword)) {
					
					String queryUpdatePassword = "update users set password = ? where phone = ?";
					
					PreparedStatement preparedStatementUpdatePassword = conn.prepareStatement(queryUpdatePassword);
					preparedStatementUpdatePassword.setString(1, newPassword);
					preparedStatementUpdatePassword.setString(2, phone);
					
					preparedStatementUpdatePassword.execute();
					
					responseCode = 1;
					responseMessage = "Password Reset Successfully! You may now proceed to login again.";
				}
				else {
					responseCode = 0;
					responseMessage = "Incorrect Old Password! Please enter the correct password and try again.";
				}
				
			}
			else {
				responseCode = 0;
				responseMessage = "User does not exist! Please check the phone number and try again.";
			}

		} catch (SQLException e) {
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println("Error Code: " + ex.getErrorCode());
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			}
		}
		
		response.put("response_code", responseCode);
		responseData.put("response_message", responseMessage);
		response.put("response_data", responseData);
		
		return response;
		
	}
}
