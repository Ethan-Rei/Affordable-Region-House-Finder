package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class MySQLConnection implements DatabaseConnection {
    private Connection connection;
	
	public MySQLConnection () throws SQLException {
		// Connect using login details from login file
		String address = "jdbc:mysql://" + Database.loginDetails.getIP() + ":" + Database.loginDetails.getPort() + "/" + Database.loginDetails.getSchema();
		connection = DriverManager.getConnection(address, Database.loginDetails.getUsername(), Database.loginDetails.getPassword());
	}
	
	@Override
	public Connection getConnection() {
		return connection;
	}
	
}
