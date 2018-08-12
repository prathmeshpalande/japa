package com.mini.japa.db.operators;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PassbookOperator {

	public Map<Integer, List<String>> getPassbook(String phone) {
		
		Map<Integer, List<String>> response = new HashMap<Integer, List<String>>();
		
		Connection conn = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/upaydb", "root", "admin");
			conn = DriverManager.getConnection("jdbc:mysql://upay.cuadni5olhbe.us-east-2.rds.amazonaws.com:3306/upaydb", "upay", "rusprapalosw$");
			
			String query = "select * from transactionhistory where fromphone = ? or tophone = ?";
			
			PreparedStatement transactionHistoryPreparedStatement = conn.prepareStatement(query);
			
			transactionHistoryPreparedStatement.setString(1, phone);
			transactionHistoryPreparedStatement.setString(2, phone);
			
			ResultSet rs = transactionHistoryPreparedStatement.executeQuery();
			
			while(rs.next()) {
				
				int key = rs.getInt(1);
				
				List<String> valueList = new ArrayList<String>();
				
				valueList.add(rs.getString(2));
				valueList.add(rs.getString(3));
				valueList.add("" + rs.getDouble(4));
				valueList.add("" + rs.getObject(5));
				
				response.put(key, valueList);
				
			}
			
		} catch (SQLException e) {
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println(e.getMessage());
			ArrayList<String> errorList = new ArrayList<String>();
			errorList.add("Error");
			response.put(e.getErrorCode(), errorList);
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
				ArrayList<String> errorList = new ArrayList<String>();
				errorList.add("Error");
				response.put(ex.getErrorCode(), errorList);
			}
		}
		
		return response;
		
	}
	
	public double getBalance(String phone) {
		
		Connection conn = null;
		double response = -1;
		
		try {

			Class.forName("com.mysql.jdbc.Driver");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/upaydb", "root", "admin");
			conn = DriverManager.getConnection("jdbc:mysql://upay.cuadni5olhbe.us-east-2.rds.amazonaws.com:3306/upaydb", "upay", "rusprapalosw$");
			
			String query = "select balance from users where phone = ?";
			
			PreparedStatement transactionHistoryPreparedStatement = conn.prepareStatement(query);
			
			transactionHistoryPreparedStatement.setString(1, phone);
			
			ResultSet rs = transactionHistoryPreparedStatement.executeQuery();
			
			rs.next();
			
			response = rs.getDouble(1);
			
		} catch (SQLException e) {
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println(e.getMessage());
			ArrayList<String> errorList = new ArrayList<String>();
			errorList.add("Error");
			response = -1;
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
				ArrayList<String> errorList = new ArrayList<String>();
				errorList.add("Error");
				response = -1;
			}
		}
		
		return response;
		
	}
	
}
