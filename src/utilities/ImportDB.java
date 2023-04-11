package utilities;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.opencsv.CSVReader;

/**
 * This class is only meant to be run if you need to import the database from the CSV.
 * Edit the username, password, ip, port and schema as needed.
 *
 */
public class ImportDB {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement pStatement;
    
    // Change these as needed
    private static String username = "";
    private static String password = "";
    private static String ip = "127.0.0.1";
    private static String port = "3306";
    private static String schema = "nhpi";
    private static String address;
    private static final String table = "data";
    private static final String csvFilePath = "18100205.csv";
    
    private ImportDB() {}
    
	public static void main(String[] args) {
		try {
			// get username and password details
			inputDetails();
			address = "jdbc:mysql://" + ip + ":" + port + "/" + schema;
			
			// begin connection to database
			connection = DriverManager.getConnection(address, username, password);
			statement = connection.createStatement();
			
			// output log
			System.out.println("Removing table " + table + " if exists.");
			removeData(table);
			System.out.println("Importing the CSV to the database. Please wait this will take awhile.");
			importData();
			connection.close();
			System.out.println("Finished importing the CSV to the database.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void inputDetails() {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter your username:");
		username = input.nextLine();
		System.out.println("Enter your password:");
		password = input.nextLine();
		
		input.close();
	}
	
	private static boolean importData() {
		createTable(table);
		String insert = "INSERT INTO " + table  + "(refdate, location_name, location_level, property_value)" + "VALUES (?, ?, ?, ?)";
		try {
			pStatement = connection.prepareStatement(insert);
			CSVReader lineReader = new CSVReader(new FileReader(csvFilePath));
			String[] data = lineReader.readNext();
			
			while ((data = lineReader.readNext()) != null) {
				if (!data[3].equals("House only"))
					continue;

				setStatementBatch(data);
				pStatement.addBatch();
			}
			pStatement.executeBatch();
			
			lineReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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
	
	private static void setStatementBatch(String[] data) throws SQLException {
		// REF_DATE
		pStatement.setDate(1, Date.valueOf(data[0] + "-01"));
		
		// GEO
		pStatement.setString(2, data[1]);
		
		// LOCATION TYPE
		pStatement.setString(3, determineType(data[1]));
		
		// VALUE
		if(data[10].isEmpty())
			pStatement.setDouble(4, 0.0);
		else
			pStatement.setDouble(4, Double.parseDouble(data[10]));
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
}
