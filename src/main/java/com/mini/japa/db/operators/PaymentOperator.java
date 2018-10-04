package com.mini.japa.db.operators;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PaymentOperator {

	public int transfer(String fromPhone, String toPhone, double amount) {

		int responseCode = 1;

		UserOperator userOperator = new UserOperator();

		
		Object doesNotToPhoneExist = userOperator.validateSignup(toPhone);
		
		System.out.println("DOES USER NOT EXIST: " + doesNotToPhoneExist);

		if (!(boolean) doesNotToPhoneExist) {

			Connection conn = null;
			try {

				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(
						"jdbc:mysql://upay.cuadni5olhbe.us-east-2.rds.amazonaws.com:3306/upaydb", "upay",
						"rusprapalosw$");

				CallableStatement statement = conn.prepareCall("{call performtransaction(?, ?, ?)}");

				statement.setString(1, fromPhone);
				statement.setString(2, toPhone);
				statement.setDouble(3, amount);

				statement.execute();

				statement.close();

			} catch (SQLException e) {
				System.out.println("Error Code: " + e.getErrorCode());
				System.out.println(e.getMessage());
				responseCode = e.getErrorCode();
			} catch (ClassNotFoundException e) {
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
		else {
			responseCode = -1;
			return responseCode;
		}
	}

	public int addMoney(String toPhone, double amount) {

		int responseCode = 1;

		Connection conn = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://upay.cuadni5olhbe.us-east-2.rds.amazonaws.com:3306/upaydb",
					"upay", "rusprapalosw$");

			CallableStatement statement = conn.prepareCall("{call performaddmoney(?, ?)}");

			statement.setString(1, toPhone);
			statement.setDouble(2, amount);

			statement.execute();

		} catch (SQLException e) {
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println(e.getMessage());
			responseCode = e.getErrorCode();
		} catch (ClassNotFoundException e) {
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
