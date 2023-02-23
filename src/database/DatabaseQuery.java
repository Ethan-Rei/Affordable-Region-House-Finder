package database;

import java.sql.Connection;
import java.sql.ResultSet;

interface DatabaseQuery {
	public void setConnection(Connection connection);
	public ResultSet query(String locationName, String fromDate, String toDate);
}
