package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class MySQLQuery {
	private Statement statement;
	
	public MySQLQuery(DatabaseConnection connection) {
		setConnection(connection.getConnection());
	}

	public void setConnection(Connection connection) {
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> queryLocations() {
		String query = "SELECT DISTINCT location_name FROM data";
		ArrayList<String> locations = new ArrayList<>();
		try {
			ResultSet returned = statement.executeQuery(query);
			while(returned.next()) {
				locations.add(returned.getString(1));
			}
			Collections.sort(locations);
			return locations;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<String> queryTimes() {
		String query = "SELECT DISTINCT refdate FROM data";
		ArrayList<String> times = new ArrayList<>();
		try {
			ResultSet returned = statement.executeQuery(query);
			while(returned.next()) {
				times.add(returned.getString(1).substring(0, returned.getString(1).length()-3));
			}
			Collections.sort(times);
			return times;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ResultSet queryNHPI(String locationName, String fromDate, String toDate) {
		String locName = locationName.replace("'", "''");
		String query = String.format("SELECT refdate, property_value FROM data WHERE location_name='%s' AND refdate BETWEEN '%s-01' AND '%s-01'", locName, fromDate, toDate);
		try {
			return statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
