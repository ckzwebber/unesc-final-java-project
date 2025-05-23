package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public static Connection getConnection(String ipAddress, String port, String database, String user, String password)
			throws SQLException {
		return DriverManager.getConnection("jdbc:postgresql://" + ipAddress + ":" + port + "/" + database, user, password);

	}
}
