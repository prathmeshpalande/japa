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

		Map<String, String> namesMap = new HashMap<String, String>();

		Connection conn = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://upay.cuadni5olhbe.us-east-2.rds.amazonaws.com:3306/upaydb",
					"upay", "rusprapalosw$");
			
			populateFromName(conn, phone, namesMap);
			populateToName(conn, phone, namesMap);

			String queryPassbook = "select * from transactionhistory where fromphone = ? or tophone = ?";

			PreparedStatement transactionHistoryPreparedStatement = conn.prepareStatement(queryPassbook);

			transactionHistoryPreparedStatement.setString(1, phone);
			transactionHistoryPreparedStatement.setString(2, phone);

			ResultSet rsPassbook = transactionHistoryPreparedStatement.executeQuery();

			while (rsPassbook.next()) {

					int key = rsPassbook.getInt(1);

					List<String> valueList = new ArrayList<String>();

					String fromPhone = rsPassbook.getString(2);
					String toPhone = rsPassbook.getString(3);

					valueList.add(rsPassbook.getString(2));
					valueList.add(rsPassbook.getString(3));
					valueList.add("" + rsPassbook.getDouble(4));
					valueList.add("" + rsPassbook.getObject(5));

					String nameToBeAdded = "Error: Name could not be fetched!";
					if (fromPhone.equals(phone))
						nameToBeAdded = toPhone;
					else
						nameToBeAdded = fromPhone;

					valueList.add(namesMap.get(nameToBeAdded));

					response.put(key, valueList);
			}
			
			transactionHistoryPreparedStatement.close();
			rsPassbook.close();

		} catch (SQLException e) {
			System.out.println("Error Code: " + e.getStackTrace());
			System.out.println(e.getMessage());
			e.printStackTrace();
			ArrayList<String> errorList = new ArrayList<String>();
			errorList.add("Error");
			response.put(e.getErrorCode(), errorList);
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
				ex.printStackTrace();
				ArrayList<String> errorList = new ArrayList<String>();
				errorList.add("Error");
				response.put(ex.getErrorCode(), errorList);
			}
		}

		return response;

	}

	public boolean populateFromName(Connection conn, String phone, Map<String, String> namesMap) {

		String queryFromName = "select distinct(u.u_name) as u_name_f, phone\n"
				+ "from users as u, transactionhistory as t\n"
				+ "where t.tophone not like ? and u.phone = t.fromphone;";

		try {

			PreparedStatement fromNamePreparedStatement = conn.prepareStatement(queryFromName);

			fromNamePreparedStatement.setString(1, phone);

			ResultSet rsFromName = fromNamePreparedStatement.executeQuery();

			while (rsFromName.next())
				namesMap.put(rsFromName.getString(2), rsFromName.getString(1));
			
			fromNamePreparedStatement.close();
			rsFromName.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	public boolean populateToName(Connection conn, String phone, Map<String, String> namesMap) {
		
		try {
			String queryToName = "select distinct(u.u_name) as u_name_t, phone\n"
					+ "from users as u, transactionhistory as t\n"
					+ "where t.fromphone not like ? and u.phone = t.tophone;";

			PreparedStatement toNamePreparedStatement = conn.prepareStatement(queryToName);

			toNamePreparedStatement.setString(1, phone);

			ResultSet rsToName = toNamePreparedStatement.executeQuery();

			while (rsToName.next())
				namesMap.put(rsToName.getString(2), rsToName.getString(1));
			
			toNamePreparedStatement.close();
			rsToName.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	public double getBalance(String phone) {

		Connection conn = null;
		double response = -1;

		try {

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://upay.cuadni5olhbe.us-east-2.rds.amazonaws.com:3306/upaydb",
					"upay", "rusprapalosw$");

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
