package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.math3.stat.inference.TTest;
import weka.core.Instances;
import weka.classifiers.functions.LinearRegression;


public class AnalysisRequests implements Requests 
{
	// Better to put monthConverter in a date converter class
	private HashMap<String, Integer> monthConverter;
	private TTest tTest = new TTest();
	private Instances testData;
	private LinearRegression linearRegressionModel;
	private static AnalysisRequests singleton = null;
	
	private AnalysisRequests() {
		// Setup the dictionary to reference
		monthConverterSetup();

		// Train the model on creation of object for now
		
		// Create an array list of Attributes to train the model
		// A string and the capacity of attributes
		
		String filename = "MontrealHouseNHPI.txt";
		ArrayList<Date> dates = new ArrayList<Date>();
		ArrayList<Double> nhpis = new ArrayList<Double>();
		
        try {
        	BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            String[] values;
            while ((line = reader.readLine()) != null) {
                values = line.split(" ", 0);
                nhpis.add(Double.parseDouble(values[0]));
                dates.add(convertDate(values[1]));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		testData = new Instances(null, null, 0);
		linearRegressionModel = new LinearRegression();
		try {
			linearRegressionModel.buildClassifier(testData);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static AnalysisRequests getAnalysisRequests() {
		if (singleton != null) { return new AnalysisRequests(); }
		return singleton;
	}
	
	// We can remove this and put this in its own class
	private void monthConverterSetup() {
		monthConverter = new HashMap<String, Integer>();
		monthConverter.put("Jan", 1);
		monthConverter.put("Feb", 2);
		monthConverter.put("Mar", 3);
		monthConverter.put("Apr", 4);
		monthConverter.put("May", 5);
		monthConverter.put("Jun", 6);
		monthConverter.put("Jul", 7);
		monthConverter.put("Aug", 8);
		monthConverter.put("Sep", 9);
		monthConverter.put("Oct", 10);
		monthConverter.put("Nov", 11);
		monthConverter.put("Dec", 12);
	}
	
	// Similarly with this
	private Date convertDate(String strDate) {
		String[] values = strDate.split(",", 0);
		int year = Integer.parseInt(values[1]);
		int month = monthConverter.get(values[0]);
		Calendar calendar = Calendar.getInstance();
		calendar.set((year < 23? year + 2000: year + 1900), month, 1);
		return calendar.getTime();
	}

	public double tTest(double[] timeSeries1, double[] timeSeries2) {	
		// Extract a double (type) array from each input time series
		
		// Returns the p value of the TTest
		return tTest.tTest(timeSeries1, timeSeries2);
	}
	
	public static void main(String[] args) {
		
	}
	
}
