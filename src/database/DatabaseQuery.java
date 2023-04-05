package database;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

interface DatabaseQuery {
	public void setConnection(Connection connection);
	public ArrayList<String> queryLocations();
	public ArrayList<String> queryTimes();
	public HashMap<Date, Double> queryNHPI(String locationName, String fromDate, String toDate);
}
