package database;

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
		query = new MySQLQueryDecorator(connection);
	}
	
	public static Database getInstance() {
		if (singleton == null) {
			singleton = new Database(); 
		}
		return singleton;
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
	
	public DatabaseQuery getQuery() {
		return query;
	}
}
