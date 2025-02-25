package external.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionProvider {
	private static String url = "jdbc:postgresql://localhost:5432/employeedb";
	private static String user = "abu_mukhlef";
	private static String password = "";

	public static Connection getConect() throws SQLException, ClassNotFoundException {
		try {
			Class.forName("org.postgresql.Driver");
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("Error connecting to the database: \n" + e.getMessage());
			throw e;
		}
	}

}
