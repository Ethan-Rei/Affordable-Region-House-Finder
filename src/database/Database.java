package database;

import java.sql.ResultSet;
import java.sql.SQLException;

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
		query = new MySQLQuery(connection);
	}
	
	public static Database getInstance() {
		if (singleton == null) {
			singleton = new Database(); 
		}
		return singleton;
	}
	
	public ResultSet query(String locationName, String fromDate, String toDate) {
		return query.query(locationName, fromDate, toDate);
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
