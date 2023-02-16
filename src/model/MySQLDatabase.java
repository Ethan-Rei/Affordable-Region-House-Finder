package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLDatabase implements Database {
    private Connection connection;
    private Statement statement;
    private PreparedStatement pStatement;
    
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
	public boolean importData(String path, String table) {
		if (tableExists(table))
			return true;
		
		createTable("data");
		String insert = "INSERT INTO " + table 
				+ "(refdate, location_name, location_level, property_value)"
				+ "VALUES (?, ?, ?, ?)";
		try {
			pStatement = connection.prepareStatement(insert);
			BufferedReader lineReader = new BufferedReader(new FileReader(path));
			String lineText = lineReader.readLine();
			
			while ((lineText = lineReader.readLine()) != null) {
				String[] data = lineText.split(",");
				pStatement.setDate(1, Date.valueOf(data[0] + "-01"));
				
				// we need to do something for these 2
				// the first is the province but the second is city, etc
				pStatement.setString(2, data[1]);
				pStatement.setString(3, data[1]);
				
				pStatement.setDouble(4, Double.parseDouble(data[11]));
				
				pStatement.addBatch();
			}
			pStatement.executeBatch();
			
			lineReader.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	@Override
	public boolean removeData(String table) {
		try {
			statement.executeUpdate("DROP TABLE IF EXISTS " + table);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean createTable(String table) {
		String query = "CREATE TABLE " + table + "("
				+ "refdate DATE NOT NULL,"
				+ "location_name varchar(100) NOT NULL,"
				+ "location_level varchar(50) NOT NULL,"
				+ "property_value decimal(6,2)"
				+ ");";
		
		try {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private boolean tableExists(String table) {
		try {
			ResultSet tables = statement.executeQuery("show tables;");
			
			while(tables.next()) {
				if (tables.getString(1).toLowerCase().equals(table))
					return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
