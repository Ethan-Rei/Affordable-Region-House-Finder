package windows;

import javax.swing.*;

import visuals.TimeSeriesData;
import visuals.Visualization;

import java.text.SimpleDateFormat;
import java.util.*;

abstract class Frame {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
    protected static final Calendar calendar = Calendar.getInstance();

    protected ArrayList<Double> getNHPIInRangeArrayList(Date startDate, Date endDate, HashMap<Date, Double> loadedData) {
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

    protected double[] getNHPIInRange(Date startDate, Date endDate, HashMap<Date, Double> loadedData) {
        ArrayList<Double> allValues = getNHPIInRangeArrayList(startDate, endDate, loadedData);
        
        double[] result = new double[allValues.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = allValues.get(i);
        }
        return result;
    }

    private ArrayList<TimeSeriesData> getAllTSForLocation(String location) {
    	ArrayList<TimeSeriesData> found = new ArrayList<>();
    	for (TimeSeriesData ts: MainWindow.getInstance().getLoadedTimeSeries()) {
            if (ts.getLocation().equals(location))
                found.add(ts);
        }
    	return found;
    }

    protected HashMap<Date, Double> getAllDataForLocation(String location) {
    	HashMap<Date, Double> allDates = new HashMap<>();
    	
    	ArrayList<TimeSeriesData> found = getAllTSForLocation(location);
        for (TimeSeriesData ts: found) {
        	allDates.putAll(ts.getLoadedData());
        }
    	
    	return allDates;
    }

    protected TimeSeriesData getSelectedTimeSeries(String selected) {
		for (TimeSeriesData ts: MainWindow.getInstance().getLoadedTimeSeries()) {
			if(ts.toString().equals(selected))
				return ts;
		}
		
		return null;
    }

    protected void populateLoadedChartsBox(JComboBox<String> boxChart) {
		int count = 1;
		for (Visualization chart: MainWindow.getInstance().getCharts()) {
			boxChart.addItem("(" + count + ") " + chart.getTimeSeries().toString());
			count++;
		}
		
		boxChart.setSelectedItem(null);
    }

    protected void populateLoadedTSBox(JComboBox<String> boxTimeSeries) {
		ArrayList<TimeSeriesData> timeSeries = MainWindow.getInstance().getLoadedTimeSeries();
		
		for (TimeSeriesData ts: timeSeries) {
			boxTimeSeries.addItem(ts.toString());
		}
		
		boxTimeSeries.setSelectedItem(null);
    }
    
}
