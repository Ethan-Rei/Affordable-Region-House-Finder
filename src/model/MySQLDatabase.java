package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLDatabase implements Database {
    private Connection connection;
    private Statement statement;
    
    // Change these as needed
    private final String ip = "127.0.0.1";
    private final String port = "3306";
    private final String schema = "nhpi";
    private final String username = "root";
    private final String password = "root1234";
    private final String address = "jdbc:mysql://" + ip + ":" + port + "/" + schema;
	
	public MySQLDatabase () {
		try {
			connection = DriverManager.getConnection(address, username, password);
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ResultSet query(String query) {
		try {
			return statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void importData(String path) {
		// TODO Auto-generated method stub
		
	}
	
}
