package model;

import org.apache.commons.math3.stat.inference.TTest;

public class AnalysisRequests implements Requests 
{
	
	
	
	
	
	public double tTest(double[] timeSeries1, double[] timeSeries2) {	
		// Extract a double (type) array from each input time series
		TTest tTest = new TTest();
		return tTest.tTest(timeSeries1, timeSeries2);
	}
	
	
}
