package database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Database {
	private static Database singleton;
	private DatabaseConnection connection;
	private DatabaseQuery query;
	public static final DatabaseLogin loginDetails = new DatabaseLogin("login.txt");

	private Database() {
		try {
			connection = new MySQLConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		query = new MySQLQueryAdapter(connection);
	}
	
	public static Database getInstance() {
		if (singleton == null) {
			singleton = new Database(); 
		}
		return singleton;
	}
	
	public ArrayList<String> queryLocations() {
		return query.queryLocations();
	}
	
	public ArrayList<String> queryTimes() {
		return query.queryTimes();
	}
	
	public HashMap<Date, Double> queryNHPI(String locationName, String fromDate, String toDate) {
		return query.queryNHPI(locationName, fromDate, toDate);
	}
	
	public static void testSQLConnection() throws SQLException {
		new MySQLConnection();
	}
	
	public void closeConnection() {
		try {
			connection.getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
