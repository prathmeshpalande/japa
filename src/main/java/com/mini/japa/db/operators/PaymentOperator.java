package com.mini.japa.db.operators;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PaymentOperator {

	public int transfer(String fromPhone, String toPhone, double amount) {

		int responseCode = 1;

		Connection conn = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/upaydb", "root", "admin");
			conn = DriverManager.getConnection("jdbc:mysql://upay.cuadni5olhbe.us-east-2.rds.amazonaws.com:3306/upaydb", "upay", "rusprapalosw$");

			CallableStatement statement = conn.prepareCall("{call performtransaction(?, ?, ?)}");

			// setting input parameters on the statement object
			statement.setString(1, fromPhone);
			statement.setString(2, toPhone);
			statement.setDouble(3, amount);
//			statement.registerOutParameter(4, java.sql.Types.INTEGER);

			statement.execute();
			
//			responseCode = "" + statement.getInt(4);
			
			statement.close();

//			responseCode = 1;

			// db parameters
//			String url = "jdbc:sqlite:D:/Softwares/SQLite DBs/MiniPaytm DB/MiniPaytmDB.db";
//			// create a connection to the database
//			conn = DriverManager.getConnection(url);

//			System.out.println("Connection to SQLite has been established.");
//
//			String query = "insert into users values (?, ?, ?, ?, ?, ?, ?)";
//
//			PreparedStatement preparedStmt = conn.prepareStatement(query);
//
//			preparedStmt.setString(1, name);
//			preparedStmt.setString(2, aadhaar);
//			preparedStmt.setString(3, email);
//			preparedStmt.setString(4, password);
//			preparedStmt.setString(5, phone);
//			preparedStmt.setString(6, dob);
//			preparedStmt.setDouble(7, 0.0);
//
//			preparedStmt.execute();

//			responseCode = 1;

		} catch (SQLException e) {
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println(e.getMessage());
			responseCode = e.getErrorCode();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public int addMoney(String toPhone, double amount) {
		
		int responseCode = 1;
		
		Connection conn = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/upaydb", "root", "admin");
			conn = DriverManager.getConnection("jdbc:mysql://upay.cuadni5olhbe.us-east-2.rds.amazonaws.com:3306/upaydb", "upay", "rusprapalosw$");
			
			CallableStatement statement = conn.prepareCall("{call performaddmoney(?, ?)}");

			// setting input parameters on the statement object
			statement.setString(1, toPhone);
//			statement.setString(2, toPhone);
			statement.setDouble(2, amount);
//			statement.registerOutParameter(4, java.sql.Types.INTEGER);

			statement.execute();
			
//			String query = "update users set balance = balance + ? where phone = ?";
			
//			PreparedStatement addMoneyPreparedStatement = conn.prepareStatement(query);
//			
//			addMoneyPreparedStatement.setDouble(1, amount);
//			addMoneyPreparedStatement.setString(2, phone);
//			
//			addMoneyPreparedStatement.execute();
			
		} catch (SQLException e) {
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println(e.getMessage());
			responseCode = e.getErrorCode();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

}
