package database;

import java.sql.ResultSet;

public interface DatabaseConnection {
	public ResultSet query(String query);
}
