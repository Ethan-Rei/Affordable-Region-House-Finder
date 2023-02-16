package model;

import org.apache.commons.math3.stat.inference.TTest;

public class AnalysisRequests implements Requests 
{
	
	
	
	
	
	public double tTest() {
		
		// Extract a double (type) array from each input time series
		TTest tTest = new TTest();
		double[] arr1 = new double[10];
		double[] arr2 = new double[10];
		return tTest.tTest(arr1, arr2);
	}
}
