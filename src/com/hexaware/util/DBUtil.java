package com.hexaware.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {

	static Connection connection;

	public static Connection getConnection() {

		// Specify the path to the properties file
		String filename = "db.Properties";

		// Load properties from the file
		Properties props = new Properties();
		FileInputStream fis;

		try {
			fis = new FileInputStream(filename);
			props.load(fis);

			// Retrieve database connection properties from the loaded properties
			String url = props.getProperty("db.url");
			String un = props.getProperty("db.username");
			String pass = props.getProperty("db.password");

			connection = DriverManager.getConnection(url, un, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return connection;
	}

	public static void main(String[] args) {
		System.out.println(getConnection());
	}
}
