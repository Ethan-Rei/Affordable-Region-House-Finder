package windows;

import java.awt.event.ActionListener;
import java.text.ParseException;
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
	
	public static void populateEndDate(JComboBox<String> locBox, JComboBox<String> startBox, JComboBox<String> endBox, HashMap<String, HashMap<Date, Double>> loadedData) {
		// Don't change end date box every time
		String prevPicked = "";
		if (endBox.getSelectedItem() != null)
			prevPicked = endBox.getSelectedItem().toString();
		
		endBox.removeAllItems();
		
		String pickedLocation = locBox.getSelectedItem().toString();
		String startDate = startBox.getSelectedItem().toString();
		ArrayList<Date> validDates = null;
		try {
			Date pickedDate = dateFormat.parse(startDate);
			validDates = getLastViableDate(pickedLocation, pickedDate, loadedData);
			
			for (Date validDate: validDates) {
				endBox.addItem(dateFormat.format(validDate));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// Only change end box if the previous picked value was less than start date or new time series range
		ArrayList<String> endBoxDates = new ArrayList<>();
		for (Date date: validDates)
			endBoxDates.add(dateFormat.format(date));
		
		if (endBoxDates.contains(prevPicked) && startDate.compareTo(prevPicked) < 0)
			endBox.setSelectedItem(prevPicked);
			
		
	}
	
	public static void populateDateBoxes(JComboBox<String> locBox, JComboBox<String> startBox, JComboBox<String> endBox, HashMap<String, HashMap<Date, Double>> loadedData) {
		// Fix NullPointerException by removing the listener, then re-adding it at end of method
		ActionListener startBoxListener = startBox.getActionListeners()[0];
		startBox.removeActionListener(startBoxListener);
		
		startBox.removeAllItems();
		endBox.removeAllItems();
		String pickedLocation = locBox.getSelectedItem().toString();
		Set<Date> validDatesSet = loadedData.get(pickedLocation).keySet();
		ArrayList<Date> validDates = new ArrayList<Date>(validDatesSet);
		Collections.sort(validDates);
		for (Date validDate: validDates) {
			startBox.addItem(dateFormat.format(validDate));
		}
		
		startBox.setSelectedItem(null);
		startBox.addActionListener(startBoxListener);
	}

	public static void populateLocBox(JComboBox<String> locBox, HashMap<String, HashMap<Date, Double>> loadedData) {
		Set<String> validLocationsSet = loadedData.keySet();
		ArrayList<String> validLocations = new ArrayList<String>(validLocationsSet);
		Collections.sort(validLocations);
		for (String validLocation: validLocations) {
			locBox.addItem(validLocation);
		}
		
		locBox.setSelectedItem(null);
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
	
	public static ArrayList<Double> getNHPIInRangeArrayList(String location, Date start, Date end, HashMap<String, HashMap<Date, Double>> loadedData) {
		double[] nhpiArray = getNHPIInRange(location, start, end, loadedData);
		ArrayList<Double> result = new ArrayList<Double>();
		for (double nhpi : nhpiArray) {
			result.add(nhpi);
		}
	    return result;
	}

	
	public static ArrayList<Date> getDatesInRange(Date startDate, Date endDate) {
		ArrayList<Date> dates = new ArrayList<Date>();
		Date currentDate = startDate;
		do {
			calendar.setTime(currentDate);
			dates.add(currentDate);
			calendar.add(Calendar.MONTH, 1);
			currentDate = calendar.getTime();
		} while(currentDate.compareTo(endDate) <= 0);
		return dates;
	}
}
