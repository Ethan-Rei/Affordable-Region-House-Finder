package database;

import java.sql.Connection;
import java.sql.ResultSet;

public interface DatabaseQuery {
	public void setConnection(Connection connection);
	public ResultSet query(String query);
}
