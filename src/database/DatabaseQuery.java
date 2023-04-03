package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

interface DatabaseQuery {
	public void setConnection(Connection connection);
	public ResultSet query(String locationName, String fromDate, String toDate);
	public ArrayList<String> queryLocations();
	public ArrayList<String> queryTimes();
	public ResultSet queryNHPI(String locationName, String fromDate, String toDate);
}
