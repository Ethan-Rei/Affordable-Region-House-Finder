package database;

import java.sql.Connection;
import java.sql.DriverManager;
public class MySQLDatabase implements DatabaseConnection {
    private Connection connection;
    
    // Change these as needed
    private final String ip = "127.0.0.1";
    private final String port = "3306";
    private final String schema = "nhpi";
    private final String username = "root";
    private final String password = "gaMc*m%u8n3g%5";
    private final String address = "jdbc:mysql://" + ip + ":" + port + "/" + schema;
	
	public MySQLDatabase () {
		try {
			connection = DriverManager.getConnection(address, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Connection getConnection() {
		return connection;
	}

	
}
