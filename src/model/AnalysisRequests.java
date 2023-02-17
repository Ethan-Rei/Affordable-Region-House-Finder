package model;

import org.apache.commons.math3.stat.inference.TTest;
import weka.core.Instances;
import weka.classifiers.functions.LinearRegression;

public class AnalysisRequests implements Requests 
{
	
	private TTest tTest = new TTest();
	private Instances testData;
	private LinearRegression linearRegressionModel;
	private static AnalysisRequests singleton = null;
	
	private AnalysisRequests() {
		// Train the model on creation of object for now
		
		// Create an array list of Attributes to train the model
		// A string and the capacity of attributes
		
		testData = new Instances(null, null, 0);
		linearRegressionModel = new LinearRegression();
		try {
			linearRegressionModel.buildClassifier(testData);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static AnalysisRequests getAnalysisRequests( ) {
		if (singleton != null) { return new AnalysisRequests(); }
		return singleton;
	}
	
	public double tTest(double[] timeSeries1, double[] timeSeries2) {	
		// Extract a double (type) array from each input time series
		
		// Returns the p value of the TTest
		return tTest.tTest(timeSeries1, timeSeries2);
	}
	
}
