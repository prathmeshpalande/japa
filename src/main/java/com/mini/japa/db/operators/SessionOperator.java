package com.mini.japa.db.operators;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.common.hash.Hashing;

public class SessionOperator {

	public boolean createSession(String phone) {

		boolean response = true;

		Connection conn = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://upay.cuadni5olhbe.us-east-2.rds.amazonaws.com:3306/upaydb", "upay", "rusprapalosw$");

			long expiry = System.currentTimeMillis() + 60000;
			String toHash = phone + expiry;
			
			String sha256hex = Hashing.sha256()
					  .hashString(toHash, StandardCharsets.UTF_8)
					  .toString();
			
			System.out.println("sha256hex: " + sha256hex + "\nLen: " + sha256hex.length());
			String queryInsertSession = "insert into sessions values (?, ?)";
			
			PreparedStatement preparedStatementInsertSession = conn.prepareStatement(queryInsertSession);
			preparedStatementInsertSession.setString(1, sha256hex);
			preparedStatementInsertSession.setString(2, "" + expiry);
			
			preparedStatementInsertSession.execute();
			preparedStatementInsertSession.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
			response = false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			response = false;
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return response;

	}
	
	public boolean validateSession(String sessionKey) {

		boolean response = false;

		Connection conn = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://upay.cuadni5olhbe.us-east-2.rds.amazonaws.com:3306/upaydb", "upay", "rusprapalosw$");
			
			String queryValidateSession = "select * from sessions where session_key = ?";
			
			PreparedStatement preparedStatementValidateSession = conn.prepareStatement(queryValidateSession);
			preparedStatementValidateSession.setString(1, sessionKey);
			
			ResultSet resultSetValidateSession = preparedStatementValidateSession.executeQuery();
			if(resultSetValidateSession.next() && System.currentTimeMillis() < Long.parseLong(resultSetValidateSession.getString(2)))
				response = true;
			
			preparedStatementValidateSession.close();
			resultSetValidateSession.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return response;

	}
	
}
