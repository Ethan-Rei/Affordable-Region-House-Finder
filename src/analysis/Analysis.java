package analysis;

import database.Database;

import java.sql.*;
import org.apache.commons.math3.stat.inference.TTest;


public class Analysis
{
	// Better to put monthConverter in a date converter class
	private TTest ttest;
	private static Analysis singleton = null;
	
	private Analysis() {
		ttest = new TTest();
	}
	
	public static Analysis getAnalysisRequests() {
		if (singleton != null) { return new Analysis(); }
		return singleton;
	}
	

	public double tTest(double[] timeSeries1, double[] timeSeries2) {	
		// Returns the p value of the TTest
		return ttest.tTest(timeSeries1, timeSeries2);
	}
	
	public static void main(String[] args) {
		Analysis analysis = new Analysis();
		Database database = Database.getInstance();
		ResultSet torontoSet = database.query("Toronto, Ontario", "2000-01", "2020-01");
		double[] torontoArr = ResultSetToArray.getDoubleArray(torontoSet);
		ResultSet hamiltonSet = database.query("Hamilton, Ontario", "2000-01", "2020-01");
		double[] hamiltonArr = ResultSetToArray.getDoubleArray(hamiltonSet);
		double ttestResult = analysis.tTest(torontoArr, hamiltonArr);
		
		System.out.println("Comparison between Toronto and Hamilton");
		if (ttestResult < 0.5) {
			System.out.println(String.format("Passed the ttest with p value of %f", ttestResult));
		}
		else { System.out.println(String.format("Failed the ttest with p value of %f", ttestResult)); }
	}
}
