package com.mini.japa.db.operators;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class ClickStreamOperator {

	public int registerClickStream(String phone, Map<String, Object> clickStreamMap) {

		int responseCode = 1;

//		for (Map.Entry<String, Object> entry : clickStreamMap.entrySet()) {
//			System.out.println(entry.getKey() + "/" + entry.getValue());
//		}

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
			conn = DriverManager.getConnection("jdbc:mysql://upay.cuadni5olhbe.us-east-2.rds.amazonaws.com:3306/upaydb",
					"upay", "rusprapalosw$");
//			conn = DriverManager.getConnection(url);

			System.out.println("Connection to SQLite has been established.");

			for(Map.Entry<String, Object> entry: clickStreamMap.entrySet()) {
				
				String query = "insert into clickstream (timestamp, phone, clickedobject) values (?, ?, ?)";
				
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				
				preparedStmt.setString(1, entry.getKey());
				preparedStmt.setString(2, phone);
				preparedStmt.setString(3, "" + entry.getValue());
				
				preparedStmt.execute();

				preparedStmt.close();
				
			}
			
//			String query = "insert into clickstream (timestamp, phone, clickedobject) values (?, ?, ?)";

//			PreparedStatement preparedStmt = conn.prepareStatement(query);

//			preparedStmt.setString(1, clickStreamMap.get(key));

//			ResultSet rs = preparedStmt.executeQuery();

//			rs.next();
//
//			String dbPass = rs.getString(2);
//
//			if (dbPass.equals(password))
//				responseCode = 1;
//			else
//				responseCode = -1;
//
//			rs.close();
//			preparedStmt.close();

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
