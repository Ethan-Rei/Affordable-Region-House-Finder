package windows;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JComboBox;

public class WindowHelper {
	
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
	private static final Calendar calendar = Calendar.getInstance();
	
	// may be unused
	public static ArrayList<Date> getLastViableDate(String location, Date startDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		// Guaranteed that location is present within the loadedData hashmap
		ArrayList<Date> viableDates = new ArrayList<Date>();
		Date currentDate = startDate;
		calendar.setTime(startDate);
		do {
			viableDates.add(currentDate);
			calendar.add(Calendar.MONTH, 1);
			currentDate = calendar.getTime();
		} while(loadedData.get(location).containsKey(currentDate));
		
		return viableDates;
		
	}
	
	public static void populateDateBoxes(JComboBox<String> locBox, JComboBox<String> startBox, JComboBox<String> endBox, HashMap<String, HashMap<Date, Double>> loadedData) {
		startBox.removeAllItems();
		endBox.removeAllItems();
		String pickedLocation = locBox.getSelectedItem().toString();
		Set<Date> validDatesSet = loadedData.get(pickedLocation).keySet();
		ArrayList<Date> validDates = new ArrayList<Date>(validDatesSet);
		Collections.sort(validDates);
		for (Date validDate: validDates) {
			startBox.addItem(dateFormat.format(validDate));
			endBox.addItem(dateFormat.format(validDate));
		}
		startBox.setSelectedItem(dateFormat.format(validDates.get(0)));
		endBox.setSelectedItem(dateFormat.format(validDates.get(0)));
	}

	public static void populateLocBox(JComboBox<String> locbox, HashMap<String, HashMap<Date, Double>> loadedData) {
		Set<String> validLocationsSet = loadedData.keySet();
		ArrayList<String> validLocations = new ArrayList<String>(validLocationsSet);
		Collections.sort(validLocations);
		for (String validLocation: validLocations) {
			locbox.addItem(validLocation);
		}
	}
	
	public static double[] getNHPIInRange(String location, Date start, Date end, HashMap<String, HashMap<Date, Double>> loadedData) {
		HashMap<Date, Double> loc = loadedData.get(location);
		ArrayList<Double> allValues = new ArrayList<>();
		
		for (HashMap.Entry<Date, Double> entry : loc.entrySet()) {
	        Date date = entry.getKey();
	        Double value = entry.getValue();
	        if (date.compareTo(start) >= 0 && date.compareTo(end) <= 0) {
	            allValues.add(value);
	        }
	    }
	    double[] result = new double[allValues.size()];
	    for (int i = 0; i < result.length; i++) {
	        result[i] = allValues.get(i);
	    }
	    return result;
	}
}
