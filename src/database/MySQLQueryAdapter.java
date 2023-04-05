package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import windows.WindowHelper;

public class MySQLQueryAdapter implements DatabaseQuery {
	
	private MySQLQuery query;
	
	public MySQLQueryAdapter (DatabaseConnection connection) {
		this.query = new MySQLQuery(connection);
	}
	
	@Override
	public void setConnection(Connection connection) {
		this.query.setConnection(connection);
	}

	@Override
	public ArrayList<String> queryLocations() {
		return this.query.queryLocations();
	}

	@Override
	public ArrayList<String> queryTimes() {
		return this.query.queryTimes();
	}

	@Override
	public HashMap<Date, Double> queryNHPI(String locationName, String fromDate, String toDate) {
		ResultSet queryResultSet = this.query.queryNHPI(locationName, fromDate, toDate);
		HashMap<Date, Double> queryAdapted = new HashMap<>();
		try {
			while (queryResultSet.next()) {
				Date refdate = WindowHelper.dateFormat.parse(queryResultSet.getString(1).substring(0, queryResultSet.getString(1).length()-3));
				queryAdapted.put(refdate, queryResultSet.getDouble(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryAdapted;
	}

}
