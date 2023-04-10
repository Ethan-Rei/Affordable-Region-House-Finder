package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLQuery {
	private Statement statement;
	
	public MySQLQuery(DatabaseConnection connection) {
		try {
			statement = connection.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet query(String query) {
		try {
			return statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
