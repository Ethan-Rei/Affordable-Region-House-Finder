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
			statement = connection.createStatement();
		} catch (SQLException e) {
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
}
