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

	public String query(String query) {
		// This method will change to whatever type we need when we use it in our views.
		// I left an example for reference, we can delete it later.
		// The below example simply queries the database, then executes the query.
		// I made it print out only the "Name" column for my own schema.
		
		String output = "";
		
		try {
			ResultSet eventSet = statement.executeQuery(query);
			
			while(eventSet.next()) {
				output += eventSet.getString("GEO") + "\n";
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
}
