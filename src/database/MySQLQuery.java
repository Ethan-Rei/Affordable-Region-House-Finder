package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLQuery implements DatabaseQuery {
	private Statement statement;
	
	public MySQLQuery(DatabaseConnection connection) {
		setConnection(connection.getConnection());
	}

	@Override
	public void setConnection(Connection connection) {
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ResultSet query(String locationName, String fromDate, String toDate) {
		String queryString = String.format("SELECT refdate, location_name, property_value FROM data WHERE location_name='%s' BETWEEN '%s-01' AND '%s-01'", locationName, fromDate, toDate);  
		try {
			return statement.executeQuery(queryString);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	

}
