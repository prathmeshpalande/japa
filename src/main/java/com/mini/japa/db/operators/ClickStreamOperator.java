package com.mini.japa.db.operators;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class ClickStreamOperator {

	public int registerClickStream(String phone, Map<String, Object> clickStreamMap) {

		int responseCode = 1;

		Connection conn = null;
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:mysql://upay.cuadni5olhbe.us-east-2.rds.amazonaws.com:3306/upaydb",
					"upay", "rusprapalosw$");

			System.out.println("Connection to SQLite has been established.");

			for(Map.Entry<String, Object> entry: clickStreamMap.entrySet()) {
				
				String query = "insert into clickstream (timestmp, phone, clickedobject) values (?, ?, ?)";
				
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				
				preparedStmt.setString(1, entry.getKey());
				preparedStmt.setString(2, phone);
				preparedStmt.setString(3, "" + entry.getValue());
				
				preparedStmt.execute();

				preparedStmt.close();
				
			}

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
