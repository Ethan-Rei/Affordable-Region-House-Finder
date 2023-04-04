package visuals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.DoubleStream;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import javax.swing.JTable;

public class TabularViewFactory {
	private static final Calendar calendar = Calendar.getInstance();
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");;
	private static final StandardDeviation sd = new StandardDeviation();
	
	public static JTable getStatsView (String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		Pair<ArrayList<String>, ArrayList<String>> listData = getData(locationName, startDate, endDate, loadedData);
		ArrayList<String> nhpiStrings = listData.getRight();
		double[] nhpiDouble = new double[nhpiStrings.size()];
		for (int i = 0; i < nhpiDouble.length; i++) {
			nhpiDouble[i] = Double.parseDouble(nhpiStrings.get(i));
		}
		
		// Find statistics
		String std = String.format("%.4f", sd.evaluate(nhpiDouble));
		
		String min = String.format("%.4f", Arrays.stream(nhpiDouble).min().getAsDouble());
		
		String max = String.format("%.4f", Arrays.stream(nhpiDouble).max().getAsDouble());
		
		String avg = String.format("%.4f", DoubleStream.of(nhpiDouble).average().orElse(0.0));
		
		// prepare table parameters
		Object[][] data = {
				{"Min", min},
				{"Max", max},
				{"StDev", std},
				{"Mean", avg},
		};
		String[] columns = {"Stat", "Value"};
		
		JTable table = new JTable(data, columns);
		return table;
	}
	
	public static JTable getDataView (String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		Pair<ArrayList<String>, ArrayList<String>> listData = getData(locationName, startDate, endDate, loadedData);
		ArrayList<String> dates = listData.getLeft();
		ArrayList<String> nhpi = listData.getRight();
		
		// prepare table parameters
		Object[][] data = new Object[dates.size()][];
		String[] columns = {"Dates", "NHPI"};
		
		for (int i = 0; i < dates.size(); i++) {
			data[i] = new Object[] {dates.get(i), nhpi.get(i)};
		}
		
		JTable table = new JTable(data, columns);
		return table;
	}
	
	private static Pair<ArrayList<String>, ArrayList<String>> getData(String locationName, Date startDate, Date endDate, HashMap<String, HashMap<Date, Double>> loadedData) {
		Date currentDate = startDate;
		ArrayList<String> dates = new ArrayList<String>();
		ArrayList<String> nhpi = new ArrayList<String>();
		do {
			calendar.setTime(currentDate);
			dates.add(dateFormat.format(currentDate));
			nhpi.add(loadedData.get(locationName).get(currentDate).toString());
			calendar.add(Calendar.MONTH, 1);
			currentDate = calendar.getTime();
		} while(currentDate.compareTo(endDate) <= 0);
		return Pair.of(dates, nhpi);
	}
	
}
