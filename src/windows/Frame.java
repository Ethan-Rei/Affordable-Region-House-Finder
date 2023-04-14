package windows;

import javax.swing.*;

import visuals.TimeSeriesData;
import visuals.Visualization;

import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

abstract class Frame {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
    private static final Calendar calendar = Calendar.getInstance();
    
    private ArrayList<Date> getLastViableDate(Date pickedDate, HashMap<Date, Double> loadedData) {
        // Guaranteed that location is present within the loadedData hashmap
        ArrayList<Date> viableDates = new ArrayList<Date>();
        Date currentDate = pickedDate;
        calendar.setTime(currentDate);
        do {
            viableDates.add(currentDate);
            calendar.add(Calendar.MONTH, 1);
            currentDate = calendar.getTime();
        } while(loadedData.containsKey(currentDate));

        return viableDates;
    }

    public void populateEndDate(JComboBox<String> locBox, JComboBox<String> startBox, JComboBox<String> endBox) {
        // Get the previous pick
        String prevPicked = getPrevPick(endBox);
        endBox.removeAllItems();

        String pickedLocation = locBox.getSelectedItem().toString();
        String startDate = startBox.getSelectedItem().toString();
        HashMap<Date, Double> loadedData = getAllDataForLocation(pickedLocation);
        try {
            Date pickedDate = dateFormat.parse(startDate);
            ArrayList<Date> validDates = getLastViableDate(pickedDate, loadedData);
            for (Date validDate: validDates) {
                endBox.addItem(dateFormat.format(validDate));
            }
            setEndBoxValue(validDates, prevPicked, startDate, endBox);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String getPrevPick(JComboBox<String> box) {
        if (box.getSelectedItem() != null)
            return box.getSelectedItem().toString();
        return "";
    }

    private void setEndBoxValue(ArrayList<Date> validDates, String prevPicked, String startDate, JComboBox<String> endBox) {
        // Only change end box if the previous picked value was less than start date or new time series range
        ArrayList<String> endBoxDates = new ArrayList<>();
        for (Date date: validDates)
            endBoxDates.add(dateFormat.format(date));

        if (endBoxDates.contains(prevPicked) && startDate.compareTo(prevPicked) < 0)
            endBox.setSelectedItem(prevPicked);
    }

    public void populateDateBoxes(JComboBox<String> locBox, JComboBox<String> startBox, JComboBox<String> endBox) {
        ActionListener startBoxListener = startBox.getActionListeners()[0];
        startBox.removeActionListener(startBoxListener);
        startBox.removeAllItems();
        endBox.removeAllItems();
        
        HashMap<Date, Double> dates = getAllDataForLocation(locBox.getSelectedItem().toString());
    	Set<Date> validDatesSet = dates.keySet();
    	ArrayList<Date> validDates = new ArrayList<>(validDatesSet);
    	Collections.sort(validDates);
        for(Date validDate: validDates) {
        	startBox.addItem(dateFormat.format(validDate));
        }
        startBox.setSelectedItem(null);
        startBox.addActionListener(startBoxListener);
    }

    public void populateLocBox(JComboBox<String> locBox) {
        ArrayList<TimeSeriesData> loadedTS = MainWindow.getInstance().getLoadedTimeSeries();
        
        // get unique locations
        ArrayList<String> validLocations = new ArrayList<>();
        for (TimeSeriesData validLocation: loadedTS) {
            if (!validLocations.contains(validLocation.getLocation()))
            	validLocations.add(validLocation.getLocation());
        }
        
        for (String location: validLocations) {
            locBox.addItem(location);
        }

        locBox.setSelectedItem(null);
    }

    public ArrayList<Double> getNHPIInRangeArrayList(Date startDate, Date endDate, HashMap<Date, Double> loadedData) {
        ArrayList<Double> allValues = new ArrayList<>();

        for (HashMap.Entry<Date, Double> entry : loadedData.entrySet()) {
            Date date = entry.getKey();
            Double value = entry.getValue();
            if (date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0) {
                allValues.add(value);
            }
        }
        
		return allValues;
    }
    
    public double[] getNHPIInRange(Date startDate, Date endDate, HashMap<Date, Double> loadedData) {
        ArrayList<Double> allValues = getNHPIInRangeArrayList(startDate, endDate, loadedData);
        
        double[] result = new double[allValues.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = allValues.get(i);
        }
        return result;
    }

    public ArrayList<Date> getDatesInRange(TimeSeriesData timeSeries) {
        ArrayList<Date> dates = new ArrayList<Date>();
        Date currentDate = timeSeries.getStartDateAsDate();
        do {
            calendar.setTime(currentDate);
            dates.add(currentDate);
            calendar.add(Calendar.MONTH, 1);
            currentDate = calendar.getTime();
        } while(currentDate.compareTo(timeSeries.getEndDateAsDate()) <= 0);
        return dates;
    }
    
    public ArrayList<TimeSeriesData> getAllTSForLocation(String location) {
    	ArrayList<TimeSeriesData> found = new ArrayList<>();
    	for (TimeSeriesData ts: MainWindow.getInstance().getLoadedTimeSeries()) {
    		if (ts.getLocation().equals(location))
    			found.add(ts);
    	}
    	
    	return found;
    }
    
    public HashMap<Date, Double> getAllDataForLocation(String location) {
    	HashMap<Date, Double> allDates = new HashMap<>();
    	
    	ArrayList<TimeSeriesData> found = getAllTSForLocation(location);
        for (TimeSeriesData ts: found) {
        	allDates.putAll(ts.getLoadedData());
        }
    	
    	return allDates;
    }
    
    public TimeSeriesData getSelectedTimeSeries(String selected) {
		for (TimeSeriesData ts: MainWindow.getInstance().getLoadedTimeSeries()) {
			if(ts.toString().equals(selected))
				return ts;
		}
		
		return null;
    }
    
    public void populateLoadedChartsBox(JComboBox<String> boxChart) {
		int count = 1;
		for (Visualization chart: MainWindow.getInstance().getCharts()) {
			boxChart.addItem("(" + count + ") " + chart.getTimeSeries().toString());
			count++;
		}
		
		boxChart.setSelectedItem(null);
    }
    
    public void populateLoadedTSBox(JComboBox<String> boxTimeSeries) {
		ArrayList<TimeSeriesData> timeSeries = MainWindow.getInstance().getLoadedTimeSeries();
		
		for (TimeSeriesData ts: timeSeries) {
			boxTimeSeries.addItem(ts.toString());
		}
		
		boxTimeSeries.setSelectedItem(null);
    }
    
}
