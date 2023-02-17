package utilities;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.opencsv.CSVReader;

public class ImportDB {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement pStatement;
    
    // Change these as needed
    private final static String ip = "127.0.0.1";
    private final static String port = "3306";
    private final static String schema = "nhpi";
    private final static String username = "root";
    private final static String password = "root1234";
    private final static String address = "jdbc:mysql://" + ip + ":" + port + "/" + schema;
    
    private ImportDB() {}
    
	public static void main(String[] args) {
		try {
			connection = DriverManager.getConnection(address, username, password);
			statement = connection.createStatement();
			removeData("data");
			importData("18100205.csv", "data");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static boolean importData(String path, String table) {
		if (tableExists(table.toLowerCase()))
			return true;
		int counter = 1;
		createTable("data");
		String insert = "INSERT INTO " + table 
				+ "(refdate, location_name, location_level, property_value)"
				+ "VALUES (?, ?, ?, ?)";
		try {
			pStatement = connection.prepareStatement(insert);
			CSVReader lineReader = new CSVReader(new FileReader(path));
			String[] data = lineReader.readNext();
			
			while ((data = lineReader.readNext()) != null) {
				pStatement.setDate(1, Date.valueOf(data[0] + "-01"));
				
				// we need to do something for these 2
				// the first is the province but the second is city, etc
				pStatement.setString(2, data[1]);
				pStatement.setString(3, data[1]);
				if(!data[10].isEmpty()) {
					pStatement.setDouble(4, Double.parseDouble(data[10]));
				}
					
				else
					pStatement.setDouble(4, 0.0);
				
				pStatement.addBatch();
				counter++;
			}
			pStatement.executeBatch();
			
			lineReader.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	private static boolean removeData(String table) {
		try {
			statement.executeUpdate("DROP TABLE IF EXISTS " + table);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private static boolean createTable(String table) {
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
	
	private static boolean tableExists(String table) {
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