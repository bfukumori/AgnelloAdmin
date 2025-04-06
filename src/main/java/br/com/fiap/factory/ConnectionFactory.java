package br.com.fiap.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
//	private static final String URL = "jdbc:mysql://145.223.95.225:3306/fiapdb";
//	private static final String USER = "fiapuser";
//	private static final String PWD = "userpass";

	private static final String URL = "jdbc:mysql://localhost:3306/meu_banco";
	private static final String USER = "root";
	private static final String PWD = "";

	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(URL, USER, PWD);
		} catch (ClassNotFoundException e) {
			throw new SQLException("MySQL JDBC Driver not found", e);
		}
	}

}
