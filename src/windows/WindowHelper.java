package windows;

import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JComboBox;

public class WindowHelper {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
	private static final Calendar calendar = Calendar.getInstance();
	
	private static ArrayList<Date> getLastViableDate(String location, Date startDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		// Guaranteed that location is present within the loadedData hashmap
		ArrayList<Date> viableDates = new ArrayList<Date>();
		Date nextDate = startDate;
		calendar.setTime(startDate);
		do {
			viableDates.add(nextDate);
			calendar.add(Calendar.MONTH, 1);
			nextDate = calendar.getTime();
		} while(loadedData.get(location).containsKey(nextDate));
		
		return viableDates;
		
	}
	
	public static void populateEndDate(JComboBox<String> locbox, JComboBox<String> startbox, JComboBox<String> endbox, ActionEvent e, HashMap<String, HashMap<Date, Double>> loadedData) {
		System.out.println("Here");
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
}
