package utilities;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.opencsv.CSVReader;

public class ImportDB {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement pStatement;
    private final static String loginFile = "login.txt";
    
    // Change these as needed
    private static String ip;
    private static String schema;
    private static String port;
    private static String username;
    private static String password;
    private static String address;
    
    private ImportDB() {}
    
	public static void main(String[] args) {
		try {
			// get sql login details from login.txt
			File login = new File(loginFile);
			Scanner fileReader = new Scanner(login);
			ip = fileReader.nextLine();
			schema = fileReader.nextLine();
			port = fileReader.nextLine();
			username = fileReader.nextLine();
			password = fileReader.nextLine();
			address = "jdbc:mysql://" + ip + ":" + port + "/" + schema;
			fileReader.close();
			
			// begin connection to database
			connection = DriverManager.getConnection(address, username, password);
			statement = connection.createStatement();
			removeData("data");
			importData("18100205.csv", "data");
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String determineType(String GEO) {
		if (GEO.contains("Region"))
			return "Region";
		if (GEO.contains(","))
			return "City";
		if (GEO.contains("Canada"))
			return "Country";
		return "Province";
	}
	
	private static boolean importData(String path, String table) {
		if (tableExists(table.toLowerCase()))
			return true;
		createTable("data");
		String insert = "INSERT INTO " + table 
				+ "(refdate, location_name, location_level, property_value)"
				+ "VALUES (?, ?, ?, ?)";
		try {
			pStatement = connection.prepareStatement(insert);
			CSVReader lineReader = new CSVReader(new FileReader(path));
			String[] data = lineReader.readNext();
			
			while ((data = lineReader.readNext()) != null) {
				if (!data[3].equals("House only") || data[10].isEmpty())
					continue;
				
				// REF_DATE
				pStatement.setDate(1, Date.valueOf(data[0] + "-01"));
				
				// GEO
				pStatement.setString(2, data[1]);
				
				// LOCATION TYPE
				pStatement.setString(3, determineType(data[1]));
				
				// VALUE
				pStatement.setDouble(4, Double.parseDouble(data[10]));
				
				pStatement.addBatch();
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
