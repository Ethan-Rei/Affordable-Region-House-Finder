package database;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public interface DatabaseQuery {
	public ArrayList<String> getAllLocations();
	public ArrayList<String> getAllTimes();
	public HashMap<Date, Double> getNHPI(String locationName, String fromDate, String toDate);
}
