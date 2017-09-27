package com.oxy.hcm.utils.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlConnectionUtils {

	private static Connection connection = null;

	private MySqlConnectionUtils() {
		// Private Constructor
	}

	public static Connection getConnection() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://127.4.171.2:3306/poc", "adminhiRknj9", "aCBd6XZgtYBa");

		} catch (Exception e) {
			System.out.println(e);
		}

		return connection;
	}
	public static void closeConnection(Connection dbconn) {

		try{
			if(dbconn!=null)
				dbconn.close();
		}catch(SQLException se){
			se.printStackTrace();
		}//end finally try

	}
	public static void main(String args[]) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hcm_app", "root", "root");
			// here sonoo is database name, root is username and password
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from hcm_emp");
			while (rs.next())
				System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
