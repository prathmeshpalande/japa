package com.mini.paytm.db.operators;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserOperator {

	public int registerUser(String name, String aadhaar, String email, String password, String phone, String dob) {

		int responseCode = 0;

		Connection conn = null;
		try {
			// db parameters

			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/upaydb", "root", "admin");
			conn = DriverManager.getConnection("jdbc:mysql://upay.cuadni5olhbe.us-east-2.rds.amazonaws.com:3306/upaydb", "upay", "rusprapalosw$");

//			String url = "jdbc:sqlite:D:/Softwares/SQLite DBs/MiniPaytm DB/MiniPaytmDB.db";
//			// create a connection to the database
//			conn = DriverManager.getConnection(url);

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
			// db parameters
//			String url = "jdbc:sqlite:D:/Softwares/SQLite DBs/MiniPaytm DB/MiniPaytmDB.db";
			// create a connection to the database
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/upaydb", "root", "admin");
			conn = DriverManager.getConnection("jdbc:mysql://upay.cuadni5olhbe.us-east-2.rds.amazonaws.com:3306/upaydb", "upay", "rusprapalosw$");
//			conn = DriverManager.getConnection(url);

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

}
