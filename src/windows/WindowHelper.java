package windows;

import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JComboBox;

public class WindowHelper {
	
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
	private static final Calendar calendar = Calendar.getInstance();
	
	private static ArrayList<Date> getLastViableDate(String location, Date startDate, HashMap<String, HashMap<Date, Double>> loadedData) {
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
	
	public static void populateEndDate(JComboBox<String> locbox, JComboBox<String> startbox, JComboBox<String> endbox, ActionEvent e, HashMap<String, HashMap<Date, Double>> loadedData) {
		endbox.removeAllItems();
		String pickedLocation = locbox.getSelectedItem().toString();
		try {
			Date pickedDate = dateFormat.parse(startbox.getSelectedItem().toString());
			ArrayList<Date> validDates = getLastViableDate(pickedLocation, pickedDate, loadedData);
			for (Date validDate: validDates) {
				endbox.addItem(dateFormat.format(validDate));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

	public static void populateStartDate(JComboBox<String> locbox, JComboBox<String> startbox, ActionEvent e, HashMap<String, HashMap<Date, Double>> loadedData) {
		startbox.removeAllItems();
		String pickedLocation = locbox.getSelectedItem().toString();
		Set<Date> validDates = loadedData.get(pickedLocation).keySet();
		for (Date validDate: validDates) {
			startbox.addItem(dateFormat.format(validDate));
		}
	}

	public static void populateLocBox(JComboBox<String> locbox, HashMap<String, HashMap<Date, Double>> loadedData) {
		Set<String> validLocations = loadedData.keySet();
		for (String validLocation: validLocations) {
			locbox.addItem(validLocation);
		}
	}
}
