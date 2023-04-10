package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import windows.WindowFrame;

public class MySQLQueryDecorator implements DatabaseQuery {
	
	private MySQLQuery query;
	
	public MySQLQueryDecorator (DatabaseConnection connection) {
		this.query = new MySQLQuery(connection);
	}

	@Override
	public ArrayList<String> getAllLocations() {
		String queryLoc = "SELECT DISTINCT location_name FROM data";
		int locColumn = 1;
		
		ResultSet returned = query.query(queryLoc);
		ArrayList<String> locations = new ArrayList<>();
		
		try {
			while(returned.next()) {
				locations.add(returned.getString(locColumn));
			}
			Collections.sort(locations);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return locations;
	}

	@Override
	public ArrayList<String> getAllTimes() {
		String queryTimes = "SELECT DISTINCT refdate FROM data";
		int timeColumn = 1;
		
		ArrayList<String> times = new ArrayList<>();
		ResultSet returned = query.query(queryTimes);
		try {
			while(returned.next()) {
				String row = returned.getString(timeColumn);
				times.add(row.substring(0, row.length() - 3));
			}
			Collections.sort(times);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return times;
	}

	@Override
	public HashMap<Date, Double> getNHPI(String locationName, String fromDate, String toDate) {
		String locName = locationName.replace("'", "''");
		String queryNHPI = String.format("SELECT refdate, property_value FROM data WHERE location_name='%s' AND refdate BETWEEN '%s-01' AND '%s-01'", locName, fromDate, toDate);
		int dateColumn = 1;
		int nhpiColumn = 2;
		
		ResultSet returned = query.query(queryNHPI);
		HashMap<Date, Double> nhpiValues = new HashMap<>();
		try {
			while (returned.next()) {
				String rowDate = returned.getString(dateColumn);
				Date refdate = WindowFrame.dateFormat.parse(rowDate.substring(0, rowDate.length() - 3));
				nhpiValues.put(refdate, returned.getDouble(nhpiColumn));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return nhpiValues;
	}

}
