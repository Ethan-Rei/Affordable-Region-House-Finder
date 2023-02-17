package model;

import org.apache.commons.math3.stat.inference.TTest;
import weka.classifiers.functions.LinearRegression;

public class AnalysisRequests implements Requests 
{
	
	private TTest tTest = new TTest();
	private AnalysisRequests singleton;
	
	private AnalysisRequests() {
		// Train the data on creation of object for now
		
	}
	
	public AnalysisRequests getAnalysisRequests( ) {
		if (singleton != null) { return new AnalysisRequests(); }
		return singleton;
	}
	
	public double tTest(double[] timeSeries1, double[] timeSeries2) {	
		// Extract a double (type) array from each input time series
		
		// Returns the p value of the TTest
		return tTest.tTest(timeSeries1, timeSeries2);
	}
	
}
